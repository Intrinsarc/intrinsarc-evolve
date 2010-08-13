 package com.intrinsarc.uml2deltaengine;

import java.util.*;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.repositorybase.*;

public class UML2RequirementsFeatureLink extends DERequirementsFeatureLink
{
  private RequirementsFeatureLink subject;
	private List<DEAppliedStereotype> appliedStereotypes;

  public UML2RequirementsFeatureLink(RequirementsFeatureLink subject)
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
  public DERequirementsFeatureLink asRequirementsFeatureLink()
  {
    return this;
  }

 	@Override
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		return appliedStereotypes;
	}

	@Override
	public SubfeatureKindEnum getKind()
	{
		switch (subject.getKind().getValue())
		{
		case RequirementsLinkKind.MANDATORY:
			return SubfeatureKindEnum.MANDATORY;
		case RequirementsLinkKind.OPTIONAL:
			return SubfeatureKindEnum.OPTIONAL;
		case RequirementsLinkKind.ONE_OF:
			return SubfeatureKindEnum.ONE_OF;
		case RequirementsLinkKind.ONE_OR_MORE:
			return SubfeatureKindEnum.ONE_OR_MORE;
		}
		return null;
	}

	@Override
	public DERequirementsFeature getSubfeature()
	{
    Element type = subject.undeleted_getType();
    if (type == null)
      return null;
    
    return getEngine().locateObject(type).asRequirementsFeature();
  }
}

