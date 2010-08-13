package com.intrinsarc.deltaengine.base;


public abstract class DEConstituent extends DEObject
{
	@Override
  public DEComponent asComponent()
  {
    return null;
  }

  @Override
  public DEConstituent asConstituent()
  {
    return this;
  }

  public DEConstituent()
  {
    super();
  }
  
  public String getRawName()
  {
    return getName();
  }
  
  public DEPort asPort()
  {
    return null;
  }
  
  public DEPart asPart()
  {
    return null;
  }
  
  public DEAttribute asAttribute()
  {
    return null;
  }
  
  public DEOperation asOperation()
  {
    return null;
  }
  
  public DEConnector asConnector()
  {
    return null;
  }
  
	public DERequirementsFeatureLink asRequirementsFeatureLink()
	{
		return null;
	}

	public DEAppliedStereotype asAppliedStereotype()
  {
    return null;
  }
  
  public TagConstituent asTag()
  {
  	return null;
  }

	public DETrace asTrace()
	{
		return null;
	}

	public boolean isSynthetic()
	{
		return false;
	}
	
	public boolean isPullUp()
	{
		return false;
	}

	public boolean includesStereotype(String uuid)
	{
		if (getAppliedStereotypes() == null)
			return false;
		
		for (DEAppliedStereotype applied : getAppliedStereotypes())
			if (applied.getStereotype().getUuid().equals(uuid))
				return true;
		return false;
	}
}
