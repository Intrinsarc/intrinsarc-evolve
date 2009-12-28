package com.hopstepjump.fspanalyser;

import java.io.*;
import java.util.*;

public class BBProtocol extends AbstractBBProtocol
{  
  public BBProtocol()
  {
  }

  public String produceFSP(BBGlobalProtocolContext global)
  {
    // make a new context
    BBProtocolContext context = new BBProtocolContext(global);
    
    // defined before, during and after the current definition
    // only before is used at the top level
    ByteArrayOutputStream outerStream = new ByteArrayOutputStream();
    PrintStream before = new PrintStream(outerStream);
    ByteArrayOutputStream duringStream = new ByteArrayOutputStream();
    PrintStream during = new PrintStream(duringStream);
    ByteArrayOutputStream afterStream = new ByteArrayOutputStream();
    PrintStream after = new PrintStream(afterStream);
    
    during.println(name + "_internal = (");
    topFragment.produceFSP(context, before, during, after, context.makeNewId());
    during.println(name + "_end_loop),");
    String endLoopAction = context.makeNewEndLoopAction();
    during.println(name + "_end_loop = (" +
        endLoopAction + " -> " + name + "_end_loop).");
    
    // compose, omitting the synchronisation actions
    during.println("||" + name + "_without_syncs =");
    during.print("  (" + name + "_internal");
    for (String topLevel : context.getTopLevelProcessesToCompose())
      during.print(" || " + topLevel);
    during.println(") \\ {_}.");
    
    // now, relabel all the separate calls to mean the same thing
    during.println("deterministic ||" + name + " = (" + name + "_without_syncs)");
    during.print("  / {c/{");
    Set<String> ids = context.getCompositionIds();
    int size = ids.size();
    int lp = 0;
    for (String id : ids)
    {
      during.print(id);
      if (lp != size - 1)
        during.print(", ");
      lp++;
    }
    during.println("}}.");
    
    
    return outerStream.toString() + duringStream.toString() + afterStream.toString();
  }
}
