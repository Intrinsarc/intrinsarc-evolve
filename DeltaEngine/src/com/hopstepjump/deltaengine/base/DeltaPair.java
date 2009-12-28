package com.hopstepjump.deltaengine.base;


public class DeltaPair
{
  private String uuid;
  private DEConstituent constituent;
  private DEConstituent original;
  
  public DeltaPair(String uuid, DEConstituent constituent)
  {
    this.uuid = uuid;
    this.original = constituent;
    this.constituent = constituent;
  }

  public DeltaPair(String uuid, DEConstituent original, DEConstituent constituent)
  {
    this.uuid = uuid;
    this.original = original;
    this.constituent = constituent;
  }

  public String getUuid()
  {
    return uuid;
  }

  public DEConstituent getConstituent()
  {
    return constituent;
  }
  
  public DEConstituent getOriginal()
  {
    return original;
  }
  
  public void setOriginal(DEConstituent original)
  {
    this.original = original;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (!(obj instanceof DeltaPair))
      return false;
    DeltaPair other = (DeltaPair) obj;
    return uuid.equals(other.uuid) && constituent.equals(other.constituent);
  }

  @Override
  public int hashCode()
  {
    return uuid.hashCode() ^ constituent.hashCode();
  }
}
