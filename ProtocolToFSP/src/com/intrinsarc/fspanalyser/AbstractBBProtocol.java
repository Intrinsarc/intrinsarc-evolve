package com.intrinsarc.fspanalyser;

import java.util.*;

import com.intrinsarc.deltaengine.base.*;

public abstract class AbstractBBProtocol extends BBMetaModelElement
{
  /** attribute name::String */
  protected String name;

  /** association self::Component[1..1] */
  protected DEComponent self = null;

  /** association topFragment::InteractionFragment[1..1] */
  protected BBInteractionFragment topFragment = null;

  /** association actors::ProtocolActor[0..-1] */
  protected List<BBProtocolActor> actors = new ArrayList<BBProtocolActor>();


  /**
   * the constructor
   */
  public AbstractBBProtocol()
  {
  }




  /**
    * set name
    * @param name the String to add
    */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
    * get the String
    * @return the String
    */
  public String getName()
  {
    return name;
  }

  /**
    * set self
    * @param self the BBComponent to add
    */
  public void setSelf(DEComponent self)
  {
    this.self = self;
  }

  /**
    * get the BBComponent
    * @return the BBComponent
    */
  public DEComponent getSelf()
  {
    return self;
  }

  /**
    * set topFragment
    * @param topFragment the BBInteractionFragment to add
    */
  public void setTopFragment(BBInteractionFragment topFragment)
  {
    this.topFragment = topFragment;
  }

  /**
    * get the BBInteractionFragment
    * @return the BBInteractionFragment
    */
  public BBInteractionFragment getTopFragment()
  {
    return topFragment;
  }

  /**
    * add a BBProtocolActor to actors
    * @param actor the BBProtocolActor to add
    */
  public void addActor(BBProtocolActor actor)
  {
    this.actors.add(actor);
  }

  /**
    * get the list of BBProtocolActors
    * @return  the BBProtocolActors
    */
  public List<BBProtocolActor> getActors()
  {
    return actors;
  }

  public String toString(String prefix)
  {
    StringBuffer buf = new StringBuffer();
    buf.append(prefix + "Protocol");

    buf.append(", name = " + name);
    buf.append("\n");
    if (self != null)
    {
      buf.append(prefix + "    --self " + self.getName() + "\n");
    }

    if (topFragment != null)
    {
      buf.append(prefix + "    --topFragment\n");
      buf.append(topFragment.toString(prefix + "        ") + "");
    }

    if (actors.size() != 0)
    {
      buf.append(prefix + "    --actors ");
      for (BBMetaModelElement node : actors)
      {
        buf.append(node.getName() + " ");
      }
      buf.append("\n");
    }

    return buf.toString();
  }

}
