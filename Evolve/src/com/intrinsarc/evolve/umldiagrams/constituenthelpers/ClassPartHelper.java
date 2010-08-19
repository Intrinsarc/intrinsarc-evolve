package com.intrinsarc.evolve.umldiagrams.constituenthelpers;

import java.awt.*;
import java.util.*;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.umldiagrams.base.*;
import com.intrinsarc.evolve.umldiagrams.classifiernode.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figurefacilities.containment.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creation.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.idraw.nodefacilities.resize.*;
import com.intrinsarc.repositorybase.*;

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
		UDimension half = oldFull.getDimension().multiply(0.5);
		UDimension offset = existing == null ? half : existing.getFullBounds().getPoint().subtract(existFull.getPoint());
		UDimension size = existing == null ? new UDimension(10, 10) : existing.getFullBounds().getDimension();

		// now add the part
		UPoint partTop = newBounds.getPoint().add(offset);
		final FigureReference partReference = classifierFigure.getDiagram().makeNewFigureReference();
    final Element subject = (Element) addOrReplace.getConstituent().getRepositoryObject();

    Color fill = null;
    if (existing != null)
    {
    	PersistentProperties props = existing.makePersistentFigure().getProperties();
    	if (props.contains("fill"))
    		fill = props.retrieve("fill").asColor();
    }
    createPart(
    		classifierFigure,
    		subject,
    		partReference,
    		partTop,
    		fill);

		// resize to match the other part
		if (size != null)
		{
			FigureFacet part = container.getDiagram().retrieveFigure(partReference.getId());
			makeResizingTransaction(part, new UBounds(partTop, size));
		}  	
  }
  
  protected void createPart(
      FigureFacet owner,
      Element subject,
      FigureReference partRef,
      UPoint top,
      Color color)
  {
    // create the component
  	PersistentProperties props = new PersistentProperties();
  	if (color != null)
  		props.add(new PersistentProperty("fill", color, color));
    NodeCreateFigureTransaction.create(
    		owner.getDiagram(),
        subject,
        partRef,
        owner.getFigureReference(),
        StereotypeUtilities.isRawStereotypeApplied(subject, "state-part") ? statePartCreator : partCreator,
        top,
        props,
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
