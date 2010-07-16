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

  public PartSlotHelper(
      BasicNodeFigureFacet partFigure,
      FigureFacet container)
  {
    this.partFigure = partFigure;
    this.container = container;
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
  
  public void makeUpdateTransaction(SimpleDeletedUuidsFacet deleted, boolean locked)
  {
    // if the container isn't visible, don't bother
    if (!container.isShowing())
      return;
    
    // get the full set of attributes
    Set<FigureFacet> current = getCurrentlyDisplayed();
    Set<Property> possibleAttributes = resolvePossibleAttributes();
    
    // delete if this shouldn't be here
    for (FigureFacet f : current)
    {
      // don't delete if this is deleted -- this is covered elsewhere
      Element subject = (Element) f.getSubject();
      
      if (!subject.isThisDeleted())
      {
        if (!isOwned(possibleAttributes, (Slot) subject))
          f.formDeleteTransaction();
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
            makeAddTransaction(
                current,
                partFigure,
                container,
                slot);
	        }
	      }
	    }
    }
  }

  public void makeAddTransaction(
      Set<FigureFacet> currentInContainerIgnoringDeletes,
      BasicNodeFigureFacet partFigure,
      FigureFacet container,
      Slot slot)
  {
  	// find the slot
		DEStratum perspective = GlobalDeltaEngine.engine.locateObject(partFigure.getDiagram().getLinkedObject()).asStratum();
		FigureFacet classifierFigure = partFigure.getContainedFacet().getContainer().getContainedFacet().getContainer().getFigureFacet();
		
		// see if we can find the original part first
		DEComponent component = GlobalDeltaEngine.engine.locateObject(classifierFigure.getSubject()).asComponent();
		String partUuid = ((Element) partFigure.getSubject()).getUuid();
		FigureFacet[] figures = ClassifierConstituentHelper.findClassAndConstituentFigure(classifierFigure, perspective, component, partUuid, null, false);
		if (figures == null)
			return;
		
		// now look for the slot
		FigureFacet slotFigure = ClassifierConstituentHelper.findSubfigure(figures[1], slot);
		if (slotFigure == null)
			return;
			
    // look for the location amongst existing elements which this might be replacing
    UPoint location = slotFigure.getFullBounds().getTopLeftPoint().subtract(new UDimension(0, 1));
    
    AddFeatureTransaction.add(
        container,
        partFigure.getDiagram().makeNewFigureReference(),
        new SlotCreatorGem().getNodeCreateFacet(),
        null,
        slot,
        null,
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
