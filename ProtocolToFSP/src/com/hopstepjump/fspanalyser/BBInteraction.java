package com.hopstepjump.fspanalyser;

import java.io.*;

public class BBInteraction extends AbstractBBInteraction
{
  public BBInteraction()
  {
  }

  public void produceFSP(BBProtocolContext p, PrintStream before, PrintStream during, PrintStream after, String compositionId)
  {
    BBProtocolActor other = first.isSelf() ? second : first;
    
    during.println("  " + p.makeAction(
        compositionId,
        other.getPort().getName(),
        other.getIface().getName(),
        call,
        first.isSelf(),
        message.getName()) + " -> ");
  }
}
