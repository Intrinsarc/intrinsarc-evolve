package com.hopstepjump.jumble.umldiagrams.constituenthelpers;

import java.util.*;

import org.eclipse.uml2.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.containment.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creation.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.classifiernode.*;
import com.hopstepjump.jumble.umldiagrams.colors.*;
import com.hopstepjump.jumble.umldiagrams.portnode.*;
import com.hopstepjump.repositorybase.*;

public class ClassPartHelper extends ClassifierConstituentHelper
{
  private NodeCreateFacet partCreator;
  private NodeCreateFacet statePartCreator;
  private boolean suppressUnlessElsewhere;

  public ClassPartHelper(ToolCoordinatorFacet coordinator, BasicNodeFigureFacet classifierFigure, FigureFacet container, SimpleDeletedUuidsFacet deleted, boolean suppressUnlessElsewhere)
  {
    super(
        classifierFigure,
        container, 
        container.isShowing(),
        findParts(container.getContainerFacet()).iterator(),
        ConstituentTypeEnum.DELTA_PART,
        deleted);
    this.partCreator = new PartCreatorGem(false).getNodeCreateFacet();
    this.statePartCreator = new PartCreatorGem(true).getNodeCreateFacet();
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
		if (existing != null)
			System.out.println("$$ cls = " + figures[0].getSubject() + ", existing = " + existing.getSubject());
		else
			System.out.println("$$ existing == null");
		UDimension half = oldFull.getDimension().multiply(0.5);
		UDimension offset = existing == null ? half : existing.getFullBounds().getPoint().subtract(existFull.getPoint());
		UDimension size = existing == null ? new UDimension(10, 10) : existing.getFullBounds().getDimension();

		// now add the part
		UPoint partTop = newBounds.getPoint().add(offset);
		final FigureReference partReference = classifierFigure.getDiagram().makeNewFigureReference();
    final Element subject = (Element) addOrReplace.getConstituent().getRepositoryObject();

    createPart(classifierFigure, subject, partReference, partTop);

		// resize to match the other part
		if (size != null)
		{
			System.out.println("$$ making resizing transaction: size = " + size);
			FigureFacet part = container.getDiagram().retrieveFigure(partReference.getId());
			makeResizingTransaction(part, new UBounds(partTop, size));
		}  	
  }
  
    protected void createPart(
      FigureFacet owner,
      Element subject,
      FigureReference partRef,
      UPoint top)
  {
    // create the component
    NodeCreateFigureTransaction.create(
    		owner.getDiagram(),
        subject,
        partRef,
        owner.getFigureReference(),
        StereotypeUtilities.isRawStereotypeApplied(subject, "state-part") ? statePartCreator : partCreator,
        top,
        new PersistentProperties(),
        null);

    FigureFacet figure = owner.getDiagram().retrieveFigure(partRef.getId());

    // insert into a container
    ContainerFacet container = owner.getContainerFacet();
    Iterator<FigureFacet> figures = container.getContents();
    while (figures.hasNext())
    {
      FigureFacet contained = figures.next();
      ContainerFacet c = contained.getContainerFacet();
      if (c != null && c.getAcceptingSubcontainer(new ContainedFacet[]{figure.getContainedFacet()}) != null)
      {
        insertFigure(c.getFigureFacet(), partRef);
        break;
      }
    }
  }
  
  protected void insertFigure(FigureFacet owner, FigureReference reference)
  {
    // insert the component into the package
    FigureFacet contained = GlobalDiagramRegistry.registry.retrieveFigure(reference);
    ContainerFacet accepting = owner.getContainerFacet().getAcceptingSubcontainer(
        new ContainedFacet[]{contained.getContainedFacet()});
    ContainerAddTransaction.add(
    		accepting,
        new ContainedFacet[]{contained.getContainedFacet()});
  }
  
  public static Set<FigureFacet> findParts(ContainerFacet container)
  {
    Set<FigureFacet> parts = new HashSet<FigureFacet>();
    for (Iterator<FigureFacet> iter = container.getContents(); iter.hasNext();)
    {
    	FigureFacet figure = iter.next();
    	if (UMLTypes.isPart((Element) figure.getSubject()))
    		parts.add(figure);
    }
  
    return parts;
  }
}
