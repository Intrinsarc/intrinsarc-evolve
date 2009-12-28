package com.hopstepjump.uml2deltaengine;

import java.util.*;

import org.eclipse.uml2.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.repositorybase.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("Part")
public class UML2Part extends DEPart
{
  private Property subject;
  private List<DESlot> slots;
	private List<DEAppliedStereotype> appliedStereotypes;

  public UML2Part(Property subject)
  {
    super();
    this.subject = subject;
    appliedStereotypes = UML2Component.extractStereotypes(subject);
  }

  @Override
  public String getName()
  {
    return subject.getName();
  }

  @Override
  public DEObject getParent()
  {
    // if this is a replacement, go a bit higher to get the owner
    if (subject.getOwner() instanceof DeltaReplacedConstituent)
      return getEngine().locateObject(subject.getOwner().getOwner());
    return getEngine().locateObject(subject.getOwner());
  }

  @Override
  public Object getRepositoryObject()
  {
    return subject;
  }

  @Override
  public String getUuid()
  {
    return subject.getUuid();
  }

  @Override
  public DEPort asPort()
  {
    return null;
  }

  @Override
  public DEConnector asConnector()
  {
    return null;
  }

  @Override
  public Set<DeltaPair> getPortRemaps()
  {
    // form this from the remaps
    Set<DeltaPair> remaps = new LinkedHashSet<DeltaPair>();
    for (Object obj : UMLTypes.extractInstanceOfPart(subject).undeleted_getPortRemaps())
    {
      PortRemap remap = (PortRemap) obj;
      DeltaPair pair = new DeltaPair(
          remap.getOriginalPort().getUuid(),
          getEngine().locateObject(remap.getNewPort()).asConstituent());
      pair.setOriginal(getEngine().locateObject(remap.getOriginalPort()).asConstituent());
      remaps.add(pair);
    }
    return remaps;
  }

  @Override
  public DEComponent getType()
  {
    Element type = subject.undeleted_getType();
    if (type == null)
      return null;
    
    return getEngine().locateObject(type).asComponent();
  }

	@Override
	public List<DESlot> getSlots()
	{
		if (slots != null)
			return slots;
		
		IDeltaEngine engine = GlobalDeltaEngine.engine;
		
		slots = new ArrayList<DESlot>();
		InstanceSpecification spec = UMLTypes.extractInstanceOfPart(subject);
		for (Object obj : spec.undeleted_getSlots())
		{
			Slot slot = (Slot) obj;
			slots.add(engine.locateObject(slot).asSlot());
		}
		return slots;
	}
	

	@Override
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		return appliedStereotypes;
	}
}