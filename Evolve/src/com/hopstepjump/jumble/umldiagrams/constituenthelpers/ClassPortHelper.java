package com.hopstepjump.jumble.umldiagrams.constituenthelpers;

import java.util.*;

import org.eclipse.uml2.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.portnode.*;

public class ClassPortHelper extends ClassifierConstituentHelper
{
	public ClassPortHelper(BasicNodeFigureFacet classifierFigure,
			FigureFacet container, SimpleDeletedUuidsFacet deleted, boolean top)
	{
		super(classifierFigure, container, container.isShowing(), container
				.getContainerFacet().getContents(), ConstituentTypeEnum.DELTA_PORT,
				deleted, top);
	}

	@Override
	public Command makeAddCommand(DEStratum perspective,
			Set<FigureFacet> currentInContainerIgnoringDeletes,
			final BasicNodeFigureFacet classifierFigure, FigureFacet container,
			DeltaPair addOrReplace, boolean top)
	{
		// get the current sizes
		FigureFacet existing = null;
		UBounds full = classifierFigure.getFullBounds();
		UPoint topLeft = full.getTopLeftPoint();

		// look to see if there was something there with that id, first
		for (FigureFacet f : currentInContainerIgnoringDeletes)
		{
			// don't delete if this is deleted -- this is covered elsewhere
			Element originalSubject = getOriginalSubject(f.getSubject());

			if (addOrReplace.getUuid().equals(originalSubject.getUuid()))
			{
				existing = f;
				break;
			}
		}

		// find the location, relative to the parent classifier by looking in this
		// diagram,
		// and in the home package diagram
		if (existing == null)
			existing = findExisting(classifierFigure.getDiagram(), addOrReplace
					.getUuid());

		if (existing == null)
		{
			// look in the home diagram
			Classifier cls = (Classifier) ((Port) getOriginalSubject(addOrReplace
					.getConstituent().getRepositoryObject())).getOwner();

			DiagramFacet homeDiagram = GlobalDiagramRegistry.registry
					.retrieveOrMakeDiagram(new DiagramReference(cls.getOwner().getUuid()));

			if (homeDiagram != null)
				existing = findExisting(homeDiagram, addOrReplace.getUuid());
		}

		// don't try too much harder if we haven't found it
		UDimension portOffset = new UDimension(10, 10);
		UDimension newOffset = full.getDimension();
		UDimension portSize = null;

		// if we have found a port to use, use the max of each dimension
		if (existing != null)
		{
			newOffset = newOffset.maxOfEach(getTopContainerBounds(existing)
					.getDimension());
			portOffset = getOffsetFromClassifier(existing);
			portSize = existing.getFullBounds().getDimension();
		}

		final UBounds newBounds = new UBounds(topLeft, newOffset)
				.centreToPoint(full.getMiddlePoint());

		// make a composite to hold all the changes
		CompositeCommand add = new CompositeCommand("", "");
		add.addCommand(new NodeAutoSizeCommand(classifierFigure
				.getFigureReference(), false, "", ""));

		// resize to fit the offset at least
		add.addCommand(makeResizingCommand(classifierFigure.getFigureReference(),
				newBounds));

		// now add the port
		final UPoint portTop = newBounds.getTopLeftPoint().add(portOffset);
		final FigureReference portReference = classifierFigure.getDiagram()
				.makeNewFigureReference();

		add.addCommand(new AddPortCommand(container.getFigureReference(),
				portReference, new PortCreatorGem().getNodeCreateFacet(), null,
				addOrReplace.getConstituent().getRepositoryObject(), null, "", "",
				portTop));

		// resize to match the other port
		if (portSize != null)
			add.addCommand(makeResizingCommand(portReference, new UBounds(portTop,
					portSize)));

		return add;
	}

	public static FigureFacet findExisting(DiagramFacet diagram, String uuid)
	{
		// look through all the diagram figures for the port in its home classifier
		for (FigureFacet figure : diagram.getFigures())
		{
			Object subject = figure.getSubject();

			if (subject instanceof Element)
			{
				Element element = (Element) subject;

				if (uuid.equals(element.getUuid()))
				{
					// is this in its home classifier?
					if (getPossibleDeltaSubject(element).getOwner() == extractVisualClassifier(figure))
						return figure;
				}
			}
		}

		return null;
	}

	public static UDimension getOffsetFromClassifier(FigureFacet figure)
	{
		// if figure is null, give a standard response
		if (figure == null)
			return new UDimension(10, 10);

		// yuck -- maybe make an element navigation language? AMcV 9/11/07
		FigureFacet classifier = figure.getContainedFacet().getContainer()
				.getContainedFacet().getContainer().getFigureFacet();

		return figure.getFullBounds().getTopLeftPoint().subtract(
				classifier.getFullBounds().getTopLeftPoint());
	}

	public static UBounds getTopContainerBounds(FigureFacet figure)
	{
		// if figure is null, give a standard response
		if (figure == null)
			return new UBounds(new UPoint(0, 0), new UDimension(10, 10));

		// yuck -- maybe make an element navigation language? AMcV 9/11/07
		FigureFacet classifier = figure.getContainedFacet().getContainer()
				.getContainedFacet().getContainer().getFigureFacet();

		return classifier.getFullBounds();
	}

	public static Classifier extractVisualClassifier(FigureFacet figureFacet)
	{
		return ClassifierConstituentHelper
				.extractVisualClassifierFromConstituent(figureFacet);
	}

	public static Command makeResizingCommand(final FigureReference reference,
			final UBounds newBounds)
	{
		return new AbstractCommand("", "")
		{
			private Command cmd;

			public void execute(boolean isTop)
			{
				FigureFacet figure = GlobalDiagramRegistry.registry
						.retrieveFigure(reference);

				// this method should only be used inside a command's execution
				ResizingFiguresGem gem = new ResizingFiguresGem(null, figure
						.getDiagram());
				ResizingFiguresFacet facet = gem.getResizingFiguresFacet();
				facet.markForResizing(figure);
				facet.setFocusBounds(newBounds);
				facet.end();
			}

			public void unExecute()
			{
				cmd.unExecute();
			}
		};
	}
}
