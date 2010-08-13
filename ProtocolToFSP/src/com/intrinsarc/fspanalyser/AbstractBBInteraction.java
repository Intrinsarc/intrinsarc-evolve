package com.intrinsarc.fspanalyser;

import com.intrinsarc.deltaengine.base.*;


public abstract class AbstractBBInteraction extends BBMetaModelElement
{
  /** attribute call::boolean */
  protected boolean call;

  /** association message::Message[1..1] */
  protected DEOperation message = null;

  /** association second::ProtocolActor[1..1] */
  protected BBProtocolActor second = null;

  /** association first::ProtocolActor[1..1] */
  protected BBProtocolActor first = null;


  /**
   * the constructor
   */
  public AbstractBBInteraction()
  {
  }




  /**
    * set call
    * @param call the boolean to add
    */
  public void setCall(boolean call)
  {
    this.call = call;
  }

  /**
    * get the boolean
    * @return the boolean
    */
  public boolean isCall()
  {
    return call;
  }

  /**
    * set message
    * @param message the BBMessage to add
    */
  public void setMessage(DEOperation message)
  {
    this.message = message;
  }

  /**
    * get the BBMessage
    * @return the BBMessage
    */
  public DEOperation getMessage()
  {
    return message;
  }

  /**
    * set second
    * @param second the BBProtocolActor to add
    */
  public void setSecond(BBProtocolActor second)
  {
    this.second = second;
  }

  /**
    * get the BBProtocolActor
    * @return the BBProtocolActor
    */
  public BBProtocolActor getSecond()
  {
    return second;
  }

  /**
    * set first
    * @param first the BBProtocolActor to add
    */
  public void setFirst(BBProtocolActor first)
  {
    this.first = first;
  }

  /**
    * get the BBProtocolActor
    * @return the BBProtocolActor
    */
  public BBProtocolActor getFirst()
  {
    return first;
  }

  public String toString(String prefix)
  {
    StringBuffer buf = new StringBuffer();
    buf.append(prefix + "Interaction");

    buf.append(", call = " + call);
    buf.append("\n");
    if (message != null)
    {
      buf.append(prefix + "    --message " + message.getName() + "\n");
    }

    if (second != null)
    {
      buf.append(prefix + "    --second " + second.getName() + "\n");
    }

    if (first != null)
    {
      buf.append(prefix + "    --first " + first.getName() + "\n");
    }

    return buf.toString();
  }

}
