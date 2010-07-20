package com.hopstepjump.jumble.umldiagrams.constituenthelpers;

import java.util.*;

import org.eclipse.uml2.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.portnode.*;

public class ClassPortHelper extends ClassifierConstituentHelper
{
	private boolean suppressUnlessElsewhere;
	
	public ClassPortHelper(
			BasicNodeFigureFacet classifierFigure,
			FigureFacet container,
			SimpleDeletedUuidsFacet deleted,
			boolean suppressUnlessElsewhere)
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
		FigureFacet[] figures = findClassAndConstituentFigure(classifierFigure, perspective, component, addOrReplace, suppressUnlessElsewhere);
		if (figures == null)
		{
			if (suppressUnlessElsewhere)
			{
				addDeletedUuid(addOrReplace.getUuid());
				return;
			}
			figures = new FigureFacet[]{classifierFigure, null};
		}

		placePort(
				classifierFigure,
				container,
				figures[0],
				figures[1],
				(Port) addOrReplace.getConstituent().getRepositoryObject(),
				new PortCreatorGem().getNodeCreateFacet(),
				true);
	}
	
	public static void placePort(FigureFacet visualOwner, FigureFacet container, FigureFacet fpart, FigureFacet fport, Port port, NodeCreateFacet factory, boolean resizeClassifier)
	{
		// place the port instance
		// we now have the appropriate class figure and constituent figure to take sizing etc from
		UBounds oldFull = visualOwner.getFullBounds();
		UBounds existFull = fpart.getFullBounds();
		UDimension newSize = oldFull.getDimension().
			maxOfEach(fpart.getFullBounds().getDimension());
		
		// resize the class
		UBounds newBounds = new UBounds(new UPoint(0, 0), newSize).centreToPoint(oldFull.getMiddlePoint());
		if (resizeClassifier)
		{
			NodeAutoSizeTransaction.autoSize(visualOwner, false);
			ClassifierConstituentHelper.makeResizingTransaction(visualOwner, newBounds);
		}
		
		// find the offset from the original
		FigureFacet existing = fport;
		UDimension offset = existing == null ? UDimension.ZERO : existing.getFullBounds().getPoint().subtract(existFull.getPoint());
		UDimension size = existing == null ? UDimension.ZERO : existing.getFullBounds().getDimension();

		// now add the port
		UPoint portTop = newBounds.getPoint().add(offset);
		final FigureReference portReference = visualOwner.getDiagram().makeNewFigureReference();

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
        factory,
        props,
        port,
        null,
        portTop);

		// resize to match the other port
		if (size != null)
		{
			FigureFacet portN = container.getDiagram().retrieveFigure(portReference.getId());
			ClassifierConstituentHelper.makeResizingTransaction(portN, new UBounds(portTop, size));
		}
	}
}
