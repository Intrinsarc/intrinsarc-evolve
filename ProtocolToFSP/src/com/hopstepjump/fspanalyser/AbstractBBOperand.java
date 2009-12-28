package com.hopstepjump.fspanalyser;

import java.util.*;

public abstract class AbstractBBOperand extends BBMetaModelElement
{
  /** association constituents::InteractionFragment[0..-1] */
  protected List<BBInteractionFragment> constituents = new ArrayList<BBInteractionFragment>();


  /**
   * the constructor
   */
  public AbstractBBOperand()
  {
  }




  /**
    * add a BBInteractionFragment to constituents
    * @param constituent the BBInteractionFragment to add
    */
  public void addConstituent(BBInteractionFragment constituent)
  {
    this.constituents.add(constituent);
  }

  /**
    * get the list of BBInteractionFragments
    * @return  the BBInteractionFragments
    */
  public List<BBInteractionFragment> getConstituents()
  {
    return constituents;
  }

  public String toString(String prefix)
  {
    StringBuffer buf = new StringBuffer();
    buf.append(prefix + "Operand");

    buf.append("\n");
    if (constituents.size() != 0)
    {
      buf.append(prefix + "    --constituents\n");
      for (BBMetaModelElement node : constituents)
      {
        buf.append(node.toString(prefix + "        ") + "");
      }
    }

    return buf.toString();
  }

}
