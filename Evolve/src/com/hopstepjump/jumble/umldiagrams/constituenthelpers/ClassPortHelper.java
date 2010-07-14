package com.hopstepjump.jumble.umldiagrams.constituenthelpers;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.portnode.*;

public class ClassPortHelper extends ClassifierConstituentHelper
{
	private boolean suppressUnlessElsewhere;
	
	public ClassPortHelper(BasicNodeFigureFacet classifierFigure,
			FigureFacet container, SimpleDeletedUuidsFacet deleted, boolean suppressUnlessElsewhere)
	{
		super(
				classifierFigure,
				container,
				container.isShowing(),
				container.getContainerFacet().getContents(),
				ConstituentTypeEnum.DELTA_PORT,
				deleted);
		this.suppressUnlessElsewhere = suppressUnlessElsewhere;
	}
	
	@Override
	public void makeAddTransaction(
			DEStratum perspective,
			Set<FigureFacet> currentInContainerIgnoringDeletes,
			final BasicNodeFigureFacet classifierFigure,
			FigureFacet container,
			DeltaPair addOrReplace)
	{
		DEComponent component = GlobalDeltaEngine.engine.locateObject(classifierFigure.getSubject()).asComponent();
		FigureFacet[] figures = findClassAndConstituentFigure(perspective, component, addOrReplace);
		if (figures == null)
		{
			if (suppressUnlessElsewhere)
			{
				addDeletedUuid(addOrReplace.getUuid());
				return;
			}
			figures = new FigureFacet[]{classifierFigure, null};
		}
		
		// we now have the appropriate class figure and constituent figure to take sizing etc from
		UBounds oldFull = classifierFigure.getFullBounds();
		UBounds existFull = figures[0].getFullBounds();
		UDimension newSize = oldFull.getDimension().
			maxOfEach(figures[0].getFullBounds().getDimension());
		
		// resize the class
		UBounds newBounds = new UBounds(new UPoint(0, 0), newSize).centreToPoint(oldFull.getMiddlePoint());
		NodeAutoSizeTransaction.autoSize(classifierFigure, false);
		makeResizingTransaction(classifierFigure, newBounds);
		
		// find the offset from the original
		FigureFacet existing = figures[1];
		UDimension offset = existing == null ? UDimension.ZERO : existing.getFullBounds().getPoint().subtract(existFull.getPoint());
		UDimension size = existing == null ? UDimension.ZERO : existing.getFullBounds().getDimension();

		// now add the port
		UPoint portTop = newBounds.getPoint().add(offset);
		final FigureReference portReference = classifierFigure.getDiagram().makeNewFigureReference();

		// work out the linked text details
		PersistentProperties props = new PersistentProperties();
		if (existing != null)
		{
			PortNodeFacet node = existing.getDynamicFacet(PortNodeFacet.class);
			props.add(new PersistentProperty(">suppressLinkedText", node.isLinkedTextSuppressed(), false));
			props.add(new PersistentProperty(">linkedTextOffset", node.getLinkedTextOffset(), null));			
		}

		AddPortTransaction.add(
				container,
				portReference,
				new PortCreatorGem().getNodeCreateFacet(),
				props,
				addOrReplace.getConstituent().getRepositoryObject(),
				null,
				portTop);

		// resize to match the other port
		if (size != null)
		{
			FigureFacet port = container.getDiagram().retrieveFigure(portReference.getId());
			makeResizingTransaction(port, new UBounds(portTop, size));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////
	//// get rid of...

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

	public static UBounds getTopContainerBounds(FigureFacet figure)
	{
		// if figure is null, give a standard response
		if (figure == null)
			return new UBounds(new UPoint(0, 0), new UDimension(10, 10));

		// yuck -- maybe make an element navigation language? AMcV 9/11/07
		FigureFacet classifier = figure.getContainedFacet().getContainer().getContainedFacet().getContainer().getFigureFacet();

		return classifier.getFullBounds();
	}

	public static Classifier extractVisualClassifier(FigureFacet figureFacet)
	{
		return ClassifierConstituentHelper.extractVisualClassifierFromConstituent(figureFacet);
	}
}
