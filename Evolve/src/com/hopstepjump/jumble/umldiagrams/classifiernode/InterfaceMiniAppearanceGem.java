package com.hopstepjump.jumble.umldiagrams.classifiernode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.enhanced.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * 
 * (c) Andrew McVeigh 22-Aug-02
 * 
 */
public class InterfaceMiniAppearanceGem implements Gem
{
	private ClassifierMiniAppearanceFacet miniAppearanceFacet = new ClassifierMiniAppearanceFacetImpl();
	private FigureFacet figureFacet;

	public InterfaceMiniAppearanceGem()
	{
	}

	public void connectFigureFacet(FigureFacet figureFacet)
	{
		this.figureFacet = figureFacet;
	}

	public ClassifierMiniAppearanceFacet getClassifierMiniAppearanceFacet()
	{
		return miniAppearanceFacet;
	}

	private class ClassifierMiniAppearanceFacetImpl implements ClassifierMiniAppearanceFacet
	{
		/**
		 * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#formView(boolean,
		 *      UBounds)
		 */
		public ZNode formView(boolean displayAsIcon, UBounds bounds)
		{
			double x = bounds.getX();
			double y = bounds.getY();
			double width = bounds.getWidth();
			double height = bounds.getHeight();
			ZEllipse ellipse = new ZEllipse(x, y, width, height);

			// draw a circle if there are no links, or not all the links want an
			// invisible anchor
			boolean drawCircle = !displayAsIcon || isCircleVisible();
			if (drawCircle)
			{
				ellipse.setPenPaint(Color.BLACK);
				if (GlobalPreferences.preferences.getRawPreference(
						new Preference("Appearance", "Interface display type", new PersistentProperty("UML2"))).asString().equals("UML2"))
					ellipse.setFillPaint(Color.WHITE);
				else
					ellipse.setFillPaint(Color.BLACK);
			} else
			{
				ellipse.setPenPaint(ScreenProperties.getTransparentColor());
				ellipse.setFillPaint(ScreenProperties.getTransparentColor());
			}

			return new ZVisualLeaf(ellipse);
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#haveMiniAppearance()
		 */
		public boolean haveMiniAppearance()
		{
			return true;
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#getMinimumDisplayOnlyAsIconExtent()
		 */
		public UDimension getMinimumDisplayOnlyAsIconExtent()
		{
			return new UDimension(24, 24);
		}

		/**
		 * @see com.hopstepjump.jumble.umldiagrams.base.ClassifierMiniAppearanceFacet#formShapeForPreview(UBounds)
		 */
		public Shape formShapeForPreview(UBounds bounds)
		{
			double x = bounds.getX();
			double y = bounds.getY();
			double width = bounds.getWidth();
			double height = bounds.getHeight();
			ZEllipse ellipse = new ZEllipse(x - 0.5, y - 0.5, width + 0.9,
					height + 0.9);
			return ellipse.getShape();
		}

		public void addToContextMenu(
				JPopupMenu menu,
				final DiagramViewFacet diagramView,
				final ToolCoordinatorFacet coordinator)
		{
			Utilities.addSeparator(menu);

			menu.add(new AbstractAction("Determine implementation class")
			{
				public void actionPerformed(ActionEvent e)
				{
					SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
					Namespace visual = repository.findVisuallyOwningNamespace(diagramView
							.getDiagram(), figureFacet.getContainedFacet().getContainer());
					String impl = GlobalDeltaEngine.engine.locateObject(
							figureFacet.getSubject()).asElement().getImplementationClass(
							GlobalDeltaEngine.engine.locateObject(visual).asStratum());
					coordinator.invokeErrorDialog(
							"Interface implementation",
							impl == null ? "No implementation set" : "Implementation details:\n    " + impl + "\n");
				}
			});

			menu.add(ComponentMiniAppearanceGem.makeEvolveCommand(coordinator, figureFacet, true, false));
		}

		public boolean isCircleVisible()
		{
			boolean haveLinks = false;
			for (Iterator<LinkingFacet> links = figureFacet.getAnchorFacet()
					.getLinks(); links.hasNext();)
			{
				LinkingFacet link = links.next();
				haveLinks = true;
				Set<String> possible = link.getPossibleDisplayStyles(figureFacet
						.getAnchorFacet());
				if (possible == null
						|| !possible.contains(InterfaceCreatorGem.LINK_STYLE_DIRECT_LARGE))
					return true;
			}

			return !haveLinks;
		}

		public Set<String> getDisplayStyles(boolean displayingOnlyAsIcon,
				boolean anchorIsTarget)
		{
			Set<String> styles = new HashSet<String>();
			styles.add("interface-style");

			// if we are not an icon, or not the target of a dependency don't bother
			if (!displayingOnlyAsIcon || !anchorIsTarget)
				return styles;

			// get the union of possible styles
			for (Iterator<LinkingFacet> links = figureFacet.getAnchorFacet()
					.getLinks(); links.hasNext();)
			{
				LinkingFacet link = links.next();
				Set<String> possible = link.getPossibleDisplayStyles(figureFacet
						.getAnchorFacet());
				if (possible != null)
					styles.addAll(possible);
			}

			// if the circle isn't visible, remove any large socket option and display
			// a small socket
			if (!isCircleVisible())
				styles.remove(InterfaceCreatorGem.LINK_STYLE_DIRECT_LARGE);

			return styles;
		}

		public JList formSelectionList(String textSoFar)
		{
			Collection<NamedElement> elements = GlobalSubjectRepository.repository
					.findElementsStartingWithName(textSoFar, InterfaceImpl.class, false);
			Vector<ElementSelection> listElements = new Vector<ElementSelection>();
			for (NamedElement element : elements)
				if (element != figureFacet.getSubject())
					listElements.add(new ElementSelection(element));
			Collections.sort(listElements);

			return new JList(listElements);
		}

		public Object setText(TextableFacet textable, String text,
				Object listSelection, boolean unsuppress)
		{
			return ComponentMiniAppearanceGem.setElementText(figureFacet, textable,
					text, listSelection, unsuppress);
		}

		public ToolFigureClassification getToolClassification(
				ClassifierSizes sizes,
				boolean displayOnlyIcon,
				boolean suppressAttributes,
				boolean suppressOperations,
				boolean suppressContents,
				boolean hasPorts,
				UPoint point)
		{
			String type = "interface,classifier,element";
			if (sizes.getAttributes().contains(point))
				return new ToolFigureClassification("interface", "attribute");
			if (sizes.getOperations().contains(point))
				return new ToolFigureClassification("interface", "operation");
			UDimension offset = new UDimension(8, 8);
			if (sizes.getContents().addToPoint(offset).addToExtent(
					offset.multiply(2).negative()).contains(point))
				return new ToolFigureClassification(type, null);
			if (!displayOnlyIcon)
				return new ToolFigureClassification(type, null);
			if (displayOnlyIcon)
			{
				if (!suppressAttributes)
					return new ToolFigureClassification(type, "attribute");
				if (!suppressOperations)
					return new ToolFigureClassification(type, "operation");
			}
			return null;
		}
	}
}
