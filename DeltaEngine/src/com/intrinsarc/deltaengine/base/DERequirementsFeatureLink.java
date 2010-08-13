package com.intrinsarc.deltaengine.base;


public abstract class DERequirementsFeatureLink extends DEConstituent
{
  public abstract DERequirementsFeature getSubfeature();
  public abstract SubfeatureKindEnum getKind();
  
  public DERequirementsFeatureLink()
  {
    super();
  }

  @Override
  public DERequirementsFeatureLink asRequirementsFeatureLink()
  {
    return this;
  }  
}