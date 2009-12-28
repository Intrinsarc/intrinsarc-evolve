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
import com.hopstepjump.repositorybase.*;

public class ClassPartHelper extends ClassifierConstituentHelper
{
  private NodeCreateFacet objectCreator;

  public ClassPartHelper(BasicNodeFigureFacet classifierFigure, FigureFacet container, SimpleDeletedUuidsFacet deleted, boolean top, NodeCreateFacet creator)
  {
    super(
        classifierFigure,
        container, 
        container.isShowing(),
        findParts(container.getContainerFacet()).iterator(),
        ConstituentTypeEnum.DELTA_PART,
        deleted,
        top);
    this.objectCreator = creator;
  }

  @Override
  public Command makeAddCommand(
      DEStratum perspective,
      Set<FigureFacet> currentInContainerIgnoringDeletes,
      final BasicNodeFigureFacet classifierFigure,
      FigureFacet container,
      DeltaPair addOrReplace,
      boolean top)
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
    
    // find the location, relative to the parent classifier by looking in this diagram,
    // and in the home package diagram
    if (existing == null)
      existing = ClassPortHelper.findExisting(classifierFigure.getDiagram(), addOrReplace.getUuid());
    if (existing == null)
    {
      // look in the home diagram
      Classifier cls = (Classifier) (getPossibleDeltaSubject(addOrReplace.getConstituent().getRepositoryObject())).getOwner();
      
      DiagramFacet homeDiagram =
        GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(new DiagramReference(cls.getOwner().getUuid()));
      if (homeDiagram != null)
        existing = ClassPortHelper.findExisting(homeDiagram, addOrReplace.getUuid());
    }
    
    // don't try too much harder if we haven't found it
    UDimension simpleOffset = container.getFullBounds().getPoint().subtract(topLeft);
    UDimension partOffset = new UDimension(20, 8);
    UDimension newOffset = full.getDimension();
    UDimension partSize = null;
    // if we have found a part to use, use the max of each dimension
    if (existing != null)
    {
      newOffset = newOffset.maxOfEach(ClassPortHelper.getTopContainerBounds(existing).getDimension());
      partOffset = getOffsetFromSimple(existing);
      partSize = existing.getFullBounds().getDimension();
    }
    final UBounds newBounds = new UBounds(topLeft, newOffset).centreToPoint(full.getMiddlePoint());
    
    // make a composite to hold all the changes
    CompositeCommand add = new CompositeCommand("", "");
    add.addCommand(new NodeAutoSizeCommand(classifierFigure.getFigureReference(), false, "", ""));
    
    // resize to fit the offset at least
    add.addCommand(ClassPortHelper.makeResizingCommand(classifierFigure.getFigureReference(), newBounds));
    
    // now add the part
    final UPoint partTop = newBounds.getTopLeftPoint().add(simpleOffset).add(partOffset);
    final FigureReference partReference = classifierFigure.getDiagram().makeNewFigureReference();
    final FigureReference classifierReference = classifierFigure.getFigureReference();
    final Element subject = (Element) addOrReplace.getConstituent().getRepositoryObject();

    add.addCommand(
      new AbstractCommand("", "")
      {
        private Command cmd;
       
        public void execute(boolean isTop)
        {
          FigureFacet classifier = GlobalDiagramRegistry.registry.retrieveFigure(classifierReference);
          cmd = createPart(classifier, subject, partReference, partTop);
        }

        public void unExecute()
        {
          cmd.unExecute();
        }
      });
    
    
    // resize to match the other part
    final UDimension finalPartSize = partSize;
    if (partSize != null)
    {
      add.addCommand(new AbstractCommand("", "")
      {
        private Command cmd;
        
        public void execute(boolean isTop)
        {
          FigureFacet part = GlobalDiagramRegistry.registry.retrieveFigure(partReference);
          cmd = ClassPortHelper.makeResizingCommand(part.getFigureReference(), new UBounds(partTop, finalPartSize));
          cmd.execute(true);
        }

        public void unExecute()
        {
          cmd.unExecute();
        }
      });     
    }
    
    return add;
  }
  
  protected Command createPart(
      FigureFacet owner,
      Element subject,
      FigureReference partRef,
      UPoint top)
  {
    CompositeCommand cmd = new CompositeCommand("", "");
    
    // create the component
    Command objectCreate = new NodeCreateFigureCommand(
        subject,
        partRef,
        owner.getFigureReference(),
        objectCreator,
        top,
        false,
        new PersistentProperties(),
        null,
        "",
        "");
    cmd.addCommand(objectCreate);
    objectCreate.execute(true);

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
        cmd.addCommand(insertFigure(c.getFigureFacet(), partRef));
        break;
      }
    }
    return cmd;
  }
  
  protected Command insertFigure(FigureFacet owner, FigureReference reference)
  {
    // insert the component into the package
    FigureFacet contained = GlobalDiagramRegistry.registry.retrieveFigure(reference);
    ContainerFacet accepting = owner.getContainerFacet().getAcceptingSubcontainer(
        new ContainedFacet[]{contained.getContainedFacet()});
    ContainerAddCommand addCmd = new ContainerAddCommand(accepting.getFigureFacet().getFigureReference(),
        new FigureReference[]{reference}, "", "");
    addCmd.execute(true);
    return addCmd;
  }
  
  private UDimension getOffsetFromSimple(FigureFacet figure)
  {
    // if figure is null, give a standard response
    if (figure == null)
      return new UDimension(10, 10);
    
    // yuck -- maybe make an element navigation language? AMcV 9/11/07
    FigureFacet simple =
      figure.getContainedFacet().getContainer().getFigureFacet();
    return
      figure.getFullBounds().getTopLeftPoint().subtract(
        simple.getFullBounds().getTopLeftPoint());
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
