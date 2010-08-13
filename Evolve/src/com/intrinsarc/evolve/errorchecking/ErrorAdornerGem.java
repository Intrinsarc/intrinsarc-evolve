package com.intrinsarc.evolve.errorchecking;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.deltaengine.errorchecking.*;
import com.intrinsarc.evolve.repositorybrowser.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class ErrorAdornerGem
{
	public static final Image ERROR_ICON_IMAGE = IconLoader.loadIcon("error.png").getImage();
	public static final Image ALERT_ICON_IMAGE = IconLoader.loadIcon("alert.png").getImage();
	public static final ImageIcon BULLET_ICON = IconLoader.loadIcon("exclamation.png");

	private ExtendedAdornerFacet adornerFacet = new ExtendedAdornerFacetImpl();
	private ToolCoordinatorFacet coordinator;
	private boolean suppressed = true;
	private ErrorRegister errors;

	public ErrorAdornerGem(ToolCoordinatorFacet coordinator, ErrorRegister errors)
	{
		this.coordinator = coordinator;
		this.errors = errors;
	}

	public ExtendedAdornerFacet getExtendedAdornerFacet()
	{
		return adornerFacet;
	}

	private class ExtendedAdornerFacetImpl implements ExtendedAdornerFacet
	{
		public ZNode adornFigure(final FigureFacet figure, int style)
		{
			ZGroup group = new ZGroup();

			ZImage image = new ZImage(
					style == 0 ? ERROR_ICON_IMAGE : ALERT_ICON_IMAGE);
			ZTransformGroup errorTransform = new ZTransformGroup(new ZVisualLeaf(image));

			// use the top left point for a node, and the middle for an arc
			final UPoint top = figure.getLinkingFacet() != null ?
					figure.getLinkingFacet().getMajorPoint(
							CalculatedArcPoints.MAJOR_POINT_MIDDLE) : figure.getFullBounds().getTopLeftPoint();

			errorTransform.setTranslation(top.getX() - 12, top.getY() - 12);
			group.addChild(errorTransform);
			
			final DEObject element = GlobalDeltaEngine.engine.locateObject(figure.getSubject());
			AdornerPopup mouse = new AdornerPopup(coordinator)
			{
				@Override
				public void addText(JPanel panel)
				{
					Tuple<Map<ErrorLocation, Set<ErrorDescription>>, Boolean> tuple = extractErrors(
						coordinator.getCurrentDiagramView().getDiagram(),
						getCurrentRegister(), figure);
					UMLDetailMediator.refreshErrors(
							errors,
							element,
							panel,
							tuple.a,
							true,
							tuple.b ? "..." : null);
				}
			};
			errorTransform.addMouseListener(mouse);
			errorTransform.addMouseMotionListener(mouse);

			return group;
		}

		public ErrorRegister getCurrentRegister()
		{
			if (suppressed)
				return null;
			return errors;
		}

		public void hideErrors()
		{
			suppressed = true;
			errors.clear();
			errors.setEnabled(false);
		}

		public void showErrors()
		{
			suppressed = false;
			errors.setEnabled(true);
		}

		public Map<FigureFacet, Integer> determineAdornments(DiagramFacet diagram, Set<FigureFacet> figures)
		{
			// if the mode is suppressed, then don't bother
			ErrorRegister current = getCurrentRegister();
			Map<FigureFacet, Integer> adorned = new LinkedHashMap<FigureFacet, Integer>();
			if (current == null || suppressed)
				return adorned;

			// see if any figures match against the current subjects with errors
			// only add the adornment to a class
			for (FigureFacet figure : figures)
			{
				if (figure.getSubject() != null)
				{
					Tuple<Map<ErrorLocation, Set<ErrorDescription>>, Boolean> errors = extractErrors(diagram, current, figure);
					if (errors != null && (errors.a != null || errors.b))
						adorned.put(figure, errors.a != null ? 0 : 1);
				}
			}

			return adorned;
		}

		private Tuple<Map<ErrorLocation, Set<ErrorDescription>>, Boolean> extractErrors(
				DiagramFacet diagram,
				ErrorRegister current,
				FigureFacet figure)
		{
			Object object = figure.getSubject();
			IDeltaEngine engine = GlobalDeltaEngine.engine;

			// only named element can have errors against them
			if (object == null || !(object instanceof NamedElement) || GlobalDeltaEngine.engine.locateObject(object) == null)
				return null;

			NamedElement subject = (NamedElement) object;

			// look for the visually nested classifier
			Classifier owningClassifier = null;

			// special handling for arcs
			ContainerFacet container = null;
			if (figure.getLinkingFacet() != null)
			{
				FigureFacet f = extractVisualClassifierFigureFromConnector(figure);
				if (f != null)
					container = f.getContainerFacet();
			}
			else
				if (figure.getContainedFacet() != null)
					container = figure.getContainedFacet().getContainer();

			if (container != null)
			{
				Namespace space = GlobalSubjectRepository.repository.findVisuallyOwningNamespace(diagram, container);
				if (space instanceof Classifier)
					owningClassifier = (Classifier) space;
			}

			// look for a stratum scoped location
			// the error list will also match against any unscoped locations when we
			// supply a scope
			Package visualStratum = GlobalSubjectRepository.repository.findVisuallyOwningPackage(diagram, container);
			ErrorLocation loc = null;
			ErrorLocation fixedLoc = null;
			boolean fixed = diagram.getPossiblePerspective() != null;
			Package fixedPerspective = (Package) diagram.getPossiblePerspective();

			DEObject deObject = engine.locateObject(subject);
			DEStratum visual = engine.locateObject(visualStratum).asStratum();
			if (owningClassifier != null)
			{
				loc = new ErrorLocation(
						visual,
						engine.locateObject(owningClassifier),
						deObject);
				if (fixed && visual != engine.locateObject(fixedPerspective)) 
					fixedLoc =
						new ErrorLocation(
								engine.locateObject(fixedPerspective).asStratum(),
								engine.locateObject(owningClassifier),
								deObject);
			}
			else
			{
				loc = new ErrorLocation(visual, deObject);
				if (fixed && visual != engine.locateObject(fixedPerspective)) 
					fixedLoc =
						new ErrorLocation(
								engine.locateObject(fixedPerspective).asStratum(),
								deObject);
			}

			// see if we can find an error list
			boolean isPackage = deObject.asStratum() != null;
			Map<ErrorLocation, Set<ErrorDescription>> found = current.getErrors(loc, isPackage, false);
			
			// if we are at a fixed perspective, pretend any errors from that perspective belong here also
			if (fixedLoc != null)
			{
				Map<ErrorLocation, Set<ErrorDescription>> fixedErrors = current.getErrors(fixedLoc, isPackage, false);
				if (fixedErrors != null)
				{
					for (ErrorLocation fl : fixedErrors.keySet())
					{
						Set<ErrorDescription> errs = found.get(loc);
						if (errs == null)
						{
							errs = new LinkedHashSet<ErrorDescription>();
							found.put(loc, errs);
						}
						errs.addAll(fixedErrors.get(fl));
					}
				}
			}
			boolean inHierarchy = isPackage && current.isInHierarchyOfErrors(deObject.getUuid());
			
			return new Tuple<Map<ErrorLocation, Set<ErrorDescription>>, Boolean>(found.isEmpty() ? null : found, inHierarchy);
		}

		public boolean isEnabled()
		{
			return !suppressed;
		}
	}

	private FigureFacet extractVisualClassifierFigureFromConnector(FigureFacet linking)
	{
		// start with an anchor, and look upwards to a class-like figure
		FigureFacet figure = linking.getLinkingFacet().getAnchor1().getFigureFacet();

		// traverse up the hierarchy until we find a classifier subject
		for (;;)
		{
			if (figure.getSubject() != null
					&& figure.getSubject() instanceof Classifier)
				return figure;

			if (figure.getContainedFacet() == null)
				break;
			ContainerFacet container = figure.getContainedFacet().getContainer();
			if (container == null)
				break;
			figure = container.getFigureFacet();
		}
		// if we get here, no classifier was found
		return null;
	}
}
