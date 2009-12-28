package com.hopstepjump.fspanalyser;

public class BBGlobalProtocolContext
{
  private int processCount = 0;
  private int endLoopCount = 0;

  public BBGlobalProtocolContext()
  {    
  }

  public String makeNewId()
  {
    return "" + processCount++;
  }
  
  public String makeNewEndLoopAction()
  {
    return "end_loop." + BBProtocolContext.makeAsciiString(endLoopCount++);
  }
}
