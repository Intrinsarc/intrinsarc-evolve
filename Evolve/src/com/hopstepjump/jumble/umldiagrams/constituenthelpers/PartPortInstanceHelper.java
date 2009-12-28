package com.hopstepjump.jumble.umldiagrams.constituenthelpers;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.portnode.*;
import com.hopstepjump.repositorybase.*;

public class PartPortInstanceHelper
{
  private BasicNodeFigureFacet partFigure;
  private FigureFacet container;
  private boolean top;

  public PartPortInstanceHelper(
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
  
  public CompositeCommand makeUpdateCommand(SimpleDeletedUuidsFacet deleted, boolean locked)
  {
    // if the container isn't visible, don't bother
    if (!container.isShowing())
      return null;
    
    // get the full set of ports
    Set<FigureFacet> current = getCurrentlyDisplayed();
    Set<DeltaPair> possiblePorts = resolvePossiblePorts();
    Set<String> uuids = new HashSet<String>();
    for (DeltaPair pair : possiblePorts)
      uuids.add(pair.getConstituent().getUuid());
    
    // work out what we need to delete
    CompositeCommand cmd = new CompositeCommand("", "");
    
    // delete if this shouldn't be here
    for (FigureFacet f : current)
    {
      // don't delete if this is deleted -- this is covered elsewhere
      Element subject = (Element) f.getSubject();
      
      if (!subject.isThisDeleted() &&
          !uuids.contains(subject.getUuid()))
          cmd.addCommand(f.formDeleteCommand());
    }
    
    if (!locked)
    {
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
	        cmd.addCommand(
	            makeAddCommand(
	                current,
	                partFigure,
	                container,
	                port,
	                top));
	      }
	    }
    }
    
    return cmd;
  }

  private DEElement getComponentType()
	{
    Property property = (Property) partFigure.getSubject();
    if (property.undeleted_getType() == null)
    	return null;
    return GlobalDeltaEngine.engine.locateObject(property.undeleted_getType()).asElement();
	}

	private Command makeAddCommand(
      Set<FigureFacet> currentInContainerIgnoringDeletes,
      BasicNodeFigureFacet partFigure,
      FigureFacet container,
      Port port,
      boolean top)
  {
    // now add the port
    Port originalSubject = (Port) ClassifierConstituentHelper.getOriginalSubject(port);
    final UPoint portTop = translateLocation(currentInContainerIgnoringDeletes, port, originalSubject);
    final FigureReference portReference = partFigure.getDiagram().makeNewFigureReference(); 
    return
      new AddPortCommand(
        container.getFigureReference(),
        portReference,
        new PortInstanceCreatorGem().getNodeCreateFacet(),
        null,
        port,
        null,
        "",
        "",
        portTop);
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
  
  private UPoint translateLocation(Set<FigureFacet> currentInContainerIgnoringDeletes, Port subject, Port originalSubject)
  {
    // get the current sizes
    FigureFacet existing = null;

    // look to see if there was something there with that id, first
    for (FigureFacet f : currentInContainerIgnoringDeletes)
    {
      if (ClassifierConstituentHelper.getOriginalSubject(f.getSubject()) == originalSubject)
      {
        existing = f;
        break;
      }
    }

    // look in the current diagram
    if (existing == null)
      existing = ClassPortHelper.findExisting(partFigure.getDiagram(), subject.getUuid());
    if (existing == null)
    {
      // look in the home diagram
      Classifier type = (Classifier) ClassifierConstituentHelper.getPossibleDeltaSubject(subject).getOwner();
      
      Package owner = (Package) GlobalSubjectRepository.repository.findOwningElement(type, PackageImpl.class);
      DiagramFacet homeDiagram =
        GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(new DiagramReference(owner.getUuid()));
      if (homeDiagram != null)
        existing = ClassPortHelper.findExisting(homeDiagram, subject.getUuid());
    }
    
    // if no existing is found, just place it somewhere relatively safe
    if (existing == null)
      return container.getFullBounds().getTopLeftPoint();
    
    // translate the location into a pro-rate location of the current container
    UPoint existingTop = existing.getFullBounds().getTopLeftPoint();
    UBounds containerBounds = existing.getContainedFacet().getContainer().getFigureFacet().getFullBounds();
    double xRatio = (existingTop.getX() - containerBounds.getX()) / (containerBounds.getWidth()); 
    double yRatio = (existingTop.getY() - containerBounds.getY()) / (containerBounds.getHeight());
    
    UBounds oldBounds = container.getFullBounds();
    UDimension size = oldBounds.getDimension();
    UPoint top = oldBounds.getTopLeftPoint();
    return new UPoint(
        top.getX() + xRatio * size.getWidth(), top.getY() + yRatio * size.getHeight());
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
