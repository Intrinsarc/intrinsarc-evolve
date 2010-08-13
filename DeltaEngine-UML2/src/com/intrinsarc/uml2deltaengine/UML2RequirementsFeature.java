package com.intrinsarc.uml2deltaengine;

import java.util.*;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.repositorybase.*;

public class UML2RequirementsFeature extends DERequirementsFeature
{
	private org.eclipse.uml2.RequirementsFeature subject;
  private Set<String> replacedUuids;

  /** the deltas for the difference calculations */
  private boolean initialiseDeltas;
  private Deltas subfeatures;
	private Deltas appliedStereotypes;
  
  public UML2RequirementsFeature(org.eclipse.uml2.RequirementsFeature subject)
  {
    super();
    this.subject = subject;
  }
  
  @Override
  public List<DEElement> getRawSubstitutes()
  {
    // filter owned dependencies
    List<DEElement> substitutes = new ArrayList<DEElement>();
    for (Object obj : subject.undeleted_getOwnedAnonymousDependencies())
    {
      Dependency dep = (Dependency) obj;
      if (dep.isReplacement())
        substitutes.add((DEElement) getEngine().locateObject(dep.getDependencyTarget()));
    }
    return substitutes;
  }

  @Override
  public List<DEElement> getRawResembles()
  {
    // filter owned dependencies
    List<DEElement> resemblances = new ArrayList<DEElement>();
    for (Object obj : subject.undeleted_getOwnedAnonymousDependencies())
    {
      Dependency dep = (Dependency) obj;
      if (dep.isResemblance())
      {
        DEElement elem = getEngine().locateObject(dep.getDependencyTarget()).asElement();
        if (elem != null && elem.asRequirementsFeature() != null)
          resemblances.add(elem);
      }
    }
    return resemblances;
  }
  
  @Override
  public String getRawName()
  {
    return subject.getName();
  }

  @Override
  public DEObject getParent()
  {
    // look up to see if we can find a stratum parent
    Element parent = subject.getOwner();
    while (parent != null && UML2DeltaEngine.isRawPackage(parent))
    	parent = parent.getOwner();
    if (parent == null)
      return null;
    
    return getEngine().locateObject(parent);
  }

  @Override
  public Object getRepositoryObject()
  {
    return subject;
  }

  public String getUuid()
  {
    return subject.getUuid();
  }

  @Override
  public DEElement asElement()
  {
    return this;
  }
  
  public IDeltas getDeltas(ConstituentTypeEnum type)
  {
    if (!initialiseDeltas)
    {
      initialiseDeltas();
      initialiseDeltas = true;
    }
    
    switch (type)
    {
	  	case DELTA_APPLIED_STEREOTYPE:
	  		return appliedStereotypes;
      case DELTA_REQUIREMENT_FEATURE_LINK:
        return subfeatures;
    }

    return super.getDeltas(type);
  }  

  @SuppressWarnings("unchecked")
  private void initialiseDeltas()
  {
    // handle subfeatures
    subfeatures = UML2Component.createDeltas(
        this,
        ConstituentTypeEnum.DELTA_REQUIREMENT_FEATURE_LINK,
        subject.undeleted_getSubfeatures(),
        subject.undeleted_getDeltaDeletedSubfeatures(),
        subject.undeleted_getDeltaReplacedSubfeatures(),
        null);
    
    // handle stereotypes
  	Set<DeltaPair> stereos = new HashSet<DeltaPair>();
  	for (DEAppliedStereotype appl : getReplacedAppliedStereotypes())
  		stereos.add(new DeltaPair(appl.getUuid(), appl));
  	appliedStereotypes = new Deltas(
    		this,
    		new HashSet<DeltaPair>(),
    		new HashSet<String>(),
    		stereos,
    		ConstituentTypeEnum.DELTA_APPLIED_STEREOTYPE);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Set<String> getReplaceUuidsOnly()
  {
    if (replacedUuids == null)
    {
      replacedUuids = new LinkedHashSet<String>();
      UML2Component.addReplacedUuids(replacedUuids, subject.undeleted_getDeltaReplacedSubfeatures());
    }
    
    return replacedUuids;
  }

	@Override
	public Set<DEElement> getPossibleImmediateSubElements()
	{
		// look in the reverse direction at all resemblances
		Set<DEElement> immediate = new HashSet<DEElement>();
		for (Object obj : subject.undeleted_getReverseDependencies())
		{
			Dependency dep = (Dependency) obj;
			if (dep.isResemblance())
			{
				// get the owner of the dependency
				if (dep.getOwner() instanceof RequirementsFeature)
				{
					NamedElement real = CommonRepositoryFunctions.translateFromSubstitutingToSubstituted((NamedElement) dep.getOwner());
					if (real instanceof RequirementsFeature)
						immediate.add(getEngine().locateObject(real).asElement());
				}
			}
		}
		return immediate;
	}

	@Override
	public List<DEElement> getSubstituters()
	{
		// look back to find any elements that substitute this
		List<DEElement> substituters = new ArrayList<DEElement>();
		for (Object obj : subject.undeleted_getReverseDependencies())
		{
			Dependency dep = (Dependency) obj;
			if (dep.isReplacement())
			{
				Element elem = dep.getOwner();
				if (elem instanceof RequirementsFeature)
					substituters.add(getEngine().locateObject(elem).asElement());
			}
		}
		return substituters;
	}

	public List<DEAppliedStereotype> getReplacedAppliedStereotypes()
	{
  	List<DEAppliedStereotype> stereos = new ArrayList<DEAppliedStereotype>();
  	for (Object obj : subject.undeleted_getAppliedBasicStereotypes())
  		stereos.add(new UML2AppliedStereotype((Stereotype) obj, subject));
  	return stereos;
	}

	@Override
	public boolean isRawRetired()
	{		
		return subject.isRetired();
	}

	@Override
	public boolean isRawAbstract()
	{
		// features are never abstract
		return false;
	}
}
