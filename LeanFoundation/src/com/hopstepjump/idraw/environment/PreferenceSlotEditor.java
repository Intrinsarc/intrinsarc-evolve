package com.hopstepjump.idraw.environment;



public interface PreferenceSlotEditor
{
  public void apply();
  public void refresh();
  public String getInterimValue();
  public Class getType();
}
