package com.hopstepjump.uml2deltaengine;

import java.util.*;

import org.eclipse.uml2.*;

import com.hopstepjump.deltaengine.base.*;

public class UML2Trace extends DETrace
{
  private Dependency subject;
	private List<DEAppliedStereotype> appliedStereotypes;

  public UML2Trace(Dependency subject)
  {
    super();
    this.subject = subject;
    appliedStereotypes = UML2Component.extractStereotypes(subject);
  }

  @Override
  public String getName()
  {
    return null;
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
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		return appliedStereotypes;
	}

	@Override
	public DERequirementsFeature getTarget()
	{
    for (Object obj : subject.getTargets())
    {
    	Element elem = (Element) obj;
    	if (!elem.isThisDeleted())
    		return getEngine().locateObject(elem).asRequirementsFeature();
    }
    return null;
	}
}

