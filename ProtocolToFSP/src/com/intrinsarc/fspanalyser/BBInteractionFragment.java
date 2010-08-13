package com.intrinsarc.fspanalyser;

import java.io.*;

public class BBInteractionFragment extends AbstractBBInteractionFragment
{
  public BBInteractionFragment()
  {
  }

  public BBInteractionFragment(BBInteraction bbInteraction)
  {
    this.interaction = bbInteraction;
  }
  
  public BBInteractionFragment(BBCombinedFragment bbCombined)
  {
    this.combined = bbCombined;
  }
  
  public String toString(String prefix)
  {
    StringBuffer buf = new StringBuffer();
    buf.append(prefix + "InteractionFragment");

    buf.append("\n");
    if (combined != null)
    {
      buf.append(prefix + "    --combined " + combined.getName() + combined.toString(prefix) + "\n");
    }

    if (interaction != null)
    {
      buf.append(prefix + "    --interaction " + interaction.getName() + interaction.toString(prefix) + "\n");
    }

    return buf.toString();
  }

  public boolean isFSPInline()
  {
    // invocations are currently inlined
    return interaction != null;
  }
  
  /** this takes a list of interaction fragments and produces an FSP representation
   *  @param before is used to add text before the current process
   *  @param during is used to insert text directly into the current definition
   *  @param after is used to insert text after into the current definition, but before the final period
   *         (used for local processes)
   */
  public void produceFSP(BBProtocolContext p, PrintStream before, PrintStream during, PrintStream after, String compositionId)
  {
    if (combined != null)
      combined.produceFSP(p, before, during, after, compositionId);
    else
      interaction.produceFSP(p, before, during, after, compositionId);
  }

  public boolean containsInfiniteLoop()
  {
    if (combined == null)
      return false;
    return combined.containsInfiniteLoop();
  }
}