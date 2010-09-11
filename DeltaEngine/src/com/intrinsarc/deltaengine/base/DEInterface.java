package com.intrinsarc.deltaengine.base;


public abstract class DEInterface extends DEElement
{
  @Override
  public DEInterface asInterface()
  {
    return this;
  }
  
	public boolean isLegacyBean(DEStratum perspective)
	{
		DEAppliedStereotype stereo = getAppliedStereotype(perspective);
		if (stereo == null)
			return false;
		return stereo.getBooleanProperty(DEComponent.LEGACY_BEAN_STEREOTYPE_PROPERTY); 
	}
}
