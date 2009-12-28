package com.hopstepjump.jumble.umldiagrams.constituenthelpers;

import java.util.*;

import org.eclipse.uml2.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.featurenode.*;
import com.hopstepjump.jumble.umldiagrams.slotnode.*;
import com.hopstepjump.repositorybase.*;

public class PartSlotHelper
{
  private BasicNodeFigureFacet partFigure;
  private FigureFacet container;
  private boolean top;

  public PartSlotHelper(
      BasicNodeFigureFacet partFigure,
      FigureFacet container,
      boolean top)
  {
    this.partFigure = partFigure;
    this.container = container;
    this.top = top;
  }
  
  public boolean isShowingAllConstituents()
  {
    // get the possible attributes
    Set<FigureFacet> current = getCurrentlyDisplayed();
    Set<Property> possibleAttributes = resolvePossibleAttributes();
    
    InstanceSpecification instance = UMLTypes.extractInstanceOfPart(getPart());
    if (instance != null)
      for (Object obj : instance.undeleted_getSlots())
      {
        Slot slot = (Slot) obj;        
        // now look to see that it is displayed
        if (isOwned(possibleAttributes, slot) && !containedWithin(current, slot))
          return false;
      }
    return true;
  }
  
  private boolean isOwned(Set<Property> possibleAttributes, Slot slot)
  {
    // should this even be here?
    for (Property attr : possibleAttributes)
      if (slot.undeleted_getDefiningFeature().getUuid().equals(attr.getUuid()))
        return true;
    return false;
  }

  private Property getPart()
  {
    return (Property) partFigure.getSubject();
  }
  
  public Set<String> getConstituentUuids()
  {
    InstanceSpecification instance = UMLTypes.extractInstanceOfPart(getPart());
    Set<String> uuids = new HashSet<String>();
    if (instance != null)
      for (Object obj : instance.undeleted_getSlots())
        uuids.add(((Slot) obj).getUuid());
    return uuids;
  }
  
  public CompositeCommand makeUpdateCommand(SimpleDeletedUuidsFacet deleted, boolean locked)
  {
    // if the container isn't visible, don't bother
    if (!container.isShowing())
      return null;
    
    // get the full set of attributes
    Set<FigureFacet> current = getCurrentlyDisplayed();
    Set<Property> possibleAttributes = resolvePossibleAttributes();
    
    // work out what we need to delete
    CompositeCommand cmd = new CompositeCommand("", "");
    
    // delete if this shouldn't be here
    for (FigureFacet f : current)
    {
      // don't delete if this is deleted -- this is covered elsewhere
      Element subject = (Element) f.getSubject();
      
      if (!subject.isThisDeleted())
      {
        if (!isOwned(possibleAttributes, (Slot) subject))
          cmd.addCommand(f.formDeleteCommand());
      }
    }
    
    if (!locked)
    {
	    // work out what we need to add
	    InstanceSpecification instance = UMLTypes.extractInstanceOfPart(getPart());
	    Type type = getPart().undeleted_getType();
	    Set<String> suppressed = type == null ?
	    		new HashSet<String>():
	    		ClassifierConstituentHelper.getVisuallySuppressed(
	    				getVisualPerspective(),
	    				GlobalDeltaEngine.engine.locateObject(type).asElement(),
	    				ConstituentTypeEnum.DELTA_ATTRIBUTE);
	    
	    if (instance != null)
	    {
	      for (Object obj : instance.undeleted_getSlots())
	      {
	        Slot slot = (Slot) obj;
	        String uuid = slot.getUuid();
	        if (isOwned(possibleAttributes, slot) && !deleted.isDeleted(suppressed, uuid) && !containedWithin(current, slot))
	        {
	          cmd.addCommand(
	              makeAddCommand(
	                  current,
	                  partFigure,
	                  container,
	                  slot,
	                  top));
	        }
	      }
	    }
    }
    
    return cmd;
  }

  public Command makeAddCommand(
      Set<FigureFacet> currentInContainerIgnoringDeletes,
      BasicNodeFigureFacet partFigure,
      FigureFacet container,
      Slot slot,
      boolean top)
  {
    // look for the location amongst existing elements which this might be replacing
    UPoint location = null;
    
    for (FigureFacet f : currentInContainerIgnoringDeletes)
    {
      // don't delete if this is deleted -- this is covered elsewhere
      Element originalSubject = (Element) f.getSubject();
      
      if (slot.getUuid().equals(originalSubject.getUuid()))
      {
        location = f.getFullBounds().getTopLeftPoint().subtract(new UDimension(0, 1));
        break;
      }
    }
    
    return
      new AddFeatureCommand(
        container.getFigureReference(),
        partFigure.getDiagram().makeNewFigureReference(),
        new SlotCreatorGem().getNodeCreateFacet(),
        null,
        slot,
        null,
        "",
        "",
        location);
  }

  private Set<FigureFacet> getCurrentlyDisplayed()
  {
    Set<FigureFacet> current = new HashSet<FigureFacet>();
    for (Iterator<FigureFacet> iter = container.getContainerFacet().getContents(); iter.hasNext();)
      current.add(iter.next());
    return current;
  }

  private boolean containedWithin(Set<FigureFacet> current, Element element)
  {
    for (FigureFacet figure : current)
      if (figure.getSubject() == element)
        return true;
    return false;
  }
  
  private Set<Property> resolvePossibleAttributes()
  {
    // get the part type
    Property property = (Property) partFigure.getSubject();
    if (property.undeleted_getType() == null)
      return new HashSet<Property>();
    
    // locate the component
    DEComponent type = GlobalDeltaEngine.engine.locateObject(property.getType()).asComponent();
    IDeltas deltas = type.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE);
    
    // locate the visual perspective
    Set<DeltaPair> pairs = deltas.getConstituents(getVisualPerspective(), true);
    Set<Property> properties = new HashSet<Property>();
    for (DeltaPair pair : pairs)
      properties.add((Property) pair.getOriginal().getRepositoryObject());
    return properties;
  }

  private DEStratum getVisualPerspective()
  {
    return PerspectiveHelper.extractDEStratum(partFigure.getDiagram(), partFigure.getContainedFacet().getContainer());
  }  
}
