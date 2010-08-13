package com.intrinsarc.evolve.umldiagrams.constituenthelpers;

import java.util.*;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.umldiagrams.base.*;
import com.intrinsarc.evolve.umldiagrams.portnode.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;

public class PartPortInstanceHelper
{
  private BasicNodeFigureFacet partFigure;
  private FigureFacet container;
	private SimpleDeletedUuidsFacet deleted;
	private boolean suppressUnlessElsewhere;

  public PartPortInstanceHelper(
      BasicNodeFigureFacet partFigure,
      FigureFacet container,
      SimpleDeletedUuidsFacet deleted,
      boolean suppressUnlessElsewhere)
  {
    this.partFigure = partFigure;
    this.container = container;
    this.deleted = deleted;
    this.suppressUnlessElsewhere = suppressUnlessElsewhere;
  }
  
  public boolean isShowingAllConstituents()
  {
    // get the possible attributes
    Set<FigureFacet> current = getCurrentlyDisplayed();
    Set<DeltaPair> possiblePorts = resolvePossiblePorts();
    
    for (DeltaPair pair : possiblePorts)
    {
      // now look to see that it is displayed
      if (!containedWithin(current, (Port) pair.getConstituent().getRepositoryObject()))
        return false;
    }
    return true;
  }
  
  public Set<String> getConstituentUuids()
  {
    Set<DeltaPair> possiblePorts = resolvePossiblePorts();
    Set<String> uuids = new HashSet<String>();
    for (DeltaPair pair : possiblePorts)
      uuids.add(pair.getUuid());
    return uuids;
  }
  
  public void makeUpdateTransaction(SimpleDeletedUuidsFacet deleted, boolean locked)
  {
    // if the container isn't visible, don't bother
    if (!container.isShowing())
      return;
    
    // get the full set of ports
    Set<FigureFacet> current = getCurrentlyDisplayed();
    Set<DeltaPair> possiblePorts = resolvePossiblePorts();
    Set<String> uuids = new HashSet<String>();
    for (DeltaPair pair : possiblePorts)
      uuids.add(pair.getConstituent().getUuid());
    
    // find out what is visually suppressed
    Set<String> suppressed = ClassifierConstituentHelper.getVisuallySuppressed(
    		getVisualPerspective(),
    		getComponentType(),
    		ConstituentTypeEnum.DELTA_PORT);
    
    // work out what we need to add
    for (DeltaPair pair : possiblePorts)
    {
      Port port = (Port) pair.getConstituent().getRepositoryObject();
      
      if (!containedWithin(current, port) && !deleted.isDeleted(suppressed, pair.getUuid()))
      {
      	FigureFacet toReplace = ClassifierConstituentHelper.willReplaceCurrentlyDisplayed(current, pair);
      	if (toReplace != null)
      	{
      		// replace in-situ even if locked -- we are already displaying it after all...
					PersistentFigure pfig = toReplace.makePersistentFigure();
					pfig.setSubject(pair.getConstituent().getRepositoryObject());
					toReplace.acceptPersistentFigure(pfig);
          toReplace.getDiagram().forceAdjust(toReplace);
      	}
      	else
      	if (!locked)
          makeAddTransaction(
              current,
              partFigure,
              container,
              port,
              pair.getUuid());
      }
    }

    // work out what we need to delete
    // delete if this shouldn't be here
    for (FigureFacet f : current)
    {
      // don't delete if this is deleted -- this is covered elsewhere
      Element subject = (Element) f.getSubject();
      
      if (subject != null && !subject.isThisDeleted() && !uuids.contains(subject.getUuid()))
        f.formDeleteTransaction();
    }
  }

  private DEElement getComponentType()
	{
    Property property = (Property) partFigure.getSubject();
    if (property.undeleted_getType() == null)
    	return null;
    return GlobalDeltaEngine.engine.locateObject(property.undeleted_getType()).asElement();
	}

	private void makeAddTransaction(
      Set<FigureFacet> currentInContainerIgnoringDeletes,
      BasicNodeFigureFacet partFigure,
      FigureFacet container,
      Port port,
      String uuid)
  {
		DEStratum perspective = GlobalDeltaEngine.engine.locateObject(partFigure.getDiagram().getLinkedObject()).asStratum();
		FigureFacet classifierFigure = partFigure.getContainedFacet().getContainer().getContainedFacet().getContainer().getFigureFacet();
		
		// see if we can find the original part first
		DEComponent component = GlobalDeltaEngine.engine.locateObject(classifierFigure.getSubject()).asComponent();
		String partUuid = ((Element) partFigure.getSubject()).getUuid();
		FigureFacet[] figures = ClassifierConstituentHelper.findClassAndConstituentFigure(classifierFigure, perspective, component, partUuid, null, suppressUnlessElsewhere);
		if (figures == null)
		{
			// if we haven't found the port, default to the class
			useClassInsteadOfPart(
					perspective,
					classifierFigure,
					currentInContainerIgnoringDeletes,
		      partFigure,
		      container,
		      port);
			return;
		}
		
		// now find the port on the part instance
		FigureFacet fpart = figures[1];
		FigureFacet fport = ClassifierConstituentHelper.findSubfigure(figures[1], port);

		if (fport == null)
		{
			if (suppressUnlessElsewhere)
			{
				deleted.addDeleted(uuid);
				return;
			}

			// default again to the class
			useClassInsteadOfPart(
					perspective,
					classifierFigure,
					currentInContainerIgnoringDeletes,
		      partFigure,
		      container,
		      port);
			return;
		}
		else
		{
			ClassPortHelper.placePort(
					partFigure,
					container,
					fpart,
					fport,
					port,
					new PortInstanceCreatorGem().getNodeCreateFacet(),
					false);
		}
  }

  private void useClassInsteadOfPart(
  		DEStratum perspective,
  		FigureFacet classifierFigure,
  		Set<FigureFacet> currentInContainerIgnoringDeletes,
			BasicNodeFigureFacet partFigure,
			FigureFacet container,
			Port port)
	{
  	Type type = ((Property) partFigure.getSubject()).getType();
		DEComponent component = GlobalDeltaEngine.engine.locateObject(type).asComponent();
		FigureFacet[] figures = ClassifierConstituentHelper.findClassAndConstituentFigure(classifierFigure, perspective, component, port.getUuid(), null, suppressUnlessElsewhere);
		if (figures == null)
		{
			if (suppressUnlessElsewhere)
			{
				deleted.addDeleted(port.getUuid());
				return;
			}
		}

		ClassPortHelper.placePort(
				partFigure,
				container,
				figures == null ? container : figures[0],
				figures == null ? null : figures[1],
				port,
				new PortInstanceCreatorGem().getNodeCreateFacet(),
				false);
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
  
  private Set<DeltaPair> resolvePossiblePorts()
  {
    // get the part type
    Property property = (Property) partFigure.getSubject();
    if (property.undeleted_getType() == null)
      return new HashSet<DeltaPair>();
    
    // locate the component
    DEComponent type = GlobalDeltaEngine.engine.locateObject(property.getType()).asComponent();
    IDeltas deltas = type.getDeltas(ConstituentTypeEnum.DELTA_PORT);
    
    // locate the visual perspective
    Set<DeltaPair> pairs = new HashSet<DeltaPair>();
    for (DeltaPair pair : deltas.getConstituents(getVisualPerspective()))
  		pairs.add(pair);
    
    return pairs;
  }

  private DEStratum getVisualPerspective()
  {
  	return PerspectiveHelper.extractDEStratum(partFigure.getDiagram(), partFigure.getContainedFacet().getContainer());
  }  

	public Map<String, String> getHiddenConstituents()
	{
  	Map<String /* UUID */, String /* name */> hidden = new LinkedHashMap<String, String>();
  	
    Set<FigureFacet> current = getCurrentlyDisplayed();
    
    for (DeltaPair pair : resolvePossiblePorts())
    {
      if (!containedWithin(current, (Port) pair.getConstituent().getRepositoryObject()))
      	hidden.put(pair.getUuid(), pair.getConstituent().getName());
    }
    return hidden;
	}
}
