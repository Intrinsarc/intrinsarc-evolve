package com.hopstepjump.fspanalyser;

import com.hopstepjump.deltaengine.base.*;


public abstract class AbstractBBProtocolActor extends BBMetaModelElement
{
  /** attribute name::String */
  protected String name;

  /** attribute self::boolean */
  protected boolean self;

  /** attribute requiredInterface::boolean */
  protected boolean requiredInterface;

  /** association port::Port[0..1] */
  protected DEPort port = null;

  /** association iface::Interface[0..1] */
  protected DEInterface iface = null;


  /**
   * the constructor
   */
  public AbstractBBProtocolActor()
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
    * @param self the boolean to add
    */
  public void setSelf(boolean self)
  {
    this.self = self;
  }

  /**
    * get the boolean
    * @return the boolean
    */
  public boolean isSelf()
  {
    return self;
  }

  /**
    * set requiredInterface
    * @param requiredInterface the boolean to add
    */
  public void setRequiredInterface(boolean requiredInterface)
  {
    this.requiredInterface = requiredInterface;
  }

  /**
    * get the boolean
    * @return the boolean
    */
  public boolean isRequiredInterface()
  {
    return requiredInterface;
  }

  /**
    * set port
    * @param port the BBPort to add
    */
  public void setPort(DEPort port)
  {
    this.port = port;
  }

  /**
    * get the BBPort
    * @return the BBPort
    */
  public DEPort getPort()
  {
    return port;
  }

  /**
    * set iface
    * @param iface the BBInterface to add
    */
  public void setIface(DEInterface iface)
  {
    this.iface = iface;
  }

  /**
    * get the BBInterface
    * @return the BBInterface
    */
  public DEInterface getIface()
  {
    return iface;
  }

  public String toString(String prefix)
  {
    StringBuffer buf = new StringBuffer();
    buf.append(prefix + "ProtocolActor");

    buf.append(", name = " + name);
    buf.append(", self = " + self);
    buf.append(", requiredInterface = " + requiredInterface);
    buf.append("\n");
    if (port != null)
    {
      buf.append(prefix + "    --port " + port.getName() + "\n");
    }

    if (iface != null)
    {
      buf.append(prefix + "    --iface " + iface.getName() + "\n");
    }

    return buf.toString();
  }

}
