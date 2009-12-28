package com.hopstepjump.fspanalyser;


public abstract class AbstractBBInteractionFragment extends BBMetaModelElement
{
  /** association combined::CombinedFragment[0..1] */
  protected BBCombinedFragment combined = null;

  /** association interaction::Interaction[0..1] */
  protected BBInteraction interaction = null;


  /**
   * the constructor
   */
  public AbstractBBInteractionFragment()
  {
  }




  /**
    * set combined
    * @param combined the BBCombinedFragment to add
    */
  public void setCombined(BBCombinedFragment combined)
  {
    this.combined = combined;
  }

  /**
    * get the BBCombinedFragment
    * @return the BBCombinedFragment
    */
  public BBCombinedFragment getCombined()
  {
    return combined;
  }

  /**
    * set interaction
    * @param interaction the BBInteraction to add
    */
  public void setInteraction(BBInteraction interaction)
  {
    this.interaction = interaction;
  }

  /**
    * get the BBInteraction
    * @return the BBInteraction
    */
  public BBInteraction getInteraction()
  {
    return interaction;
  }

  public String toString(String prefix)
  {
    StringBuffer buf = new StringBuffer();
    buf.append(prefix + "InteractionFragment");

    buf.append("\n");
    if (combined != null)
    {
      buf.append(prefix + "    --combined " + combined.getName() + "\n");
    }

    if (interaction != null)
    {
      buf.append(prefix + "    --interaction " + interaction.getName() + "\n");
    }

    return buf.toString();
  }

}
