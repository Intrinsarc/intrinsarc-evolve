package com.intrinsarc.deltaengine.errorchecking;

import com.intrinsarc.deltaengine.base.*;

public class InterfaceErrorChecker
{
  private ErrorRegister errors;
  private DEStratum perspective;
  private DEInterface iface;

  public InterfaceErrorChecker(
      DEStratum perspective,
      DEInterface iface,
      ErrorRegister errors)
  {
    this.perspective = perspective;
    this.iface = iface;
    this.errors = errors;
  }

  public void performCheck(boolean diagramCheck)
  {
  	// if this is replacing, and we are not at home, don't bother
  	if (iface.isReplacement() && perspective != iface.getHomeStratum() || iface.isRetired(perspective))
  		return;
    
    // all things subsituted or resembled must refer to a composite
    for (DEElement resembled : iface.getRawResembles())
    {
      if (resembled.asInterface() == null)
        errors.addError(
            new ErrorLocation(iface), ErrorCatalog.INTERFACE_RESEMBLES_INTERFACE);
    }
    for (DEElement redef : iface.getReplaces())
    {
      if (redef.asInterface() == null)
        errors.addError(
            new ErrorLocation(iface), ErrorCatalog.INTERFACE_SUBSTITUTES_INTERFACE);
    }
    
    // an interface must be nested inside a stratum or package
    if (iface.getParent().asStratum() == null)
      errors.addError(
          new ErrorLocation(iface.getParent().getParent(), iface.getParent()), ErrorCatalog.ELEMENT_NOT_NESTED);
    
    // an interface must specify an implementation class
    String impl = iface.getImplementationClass(perspective);
    if (impl == null)
    	errors.addError(new ErrorLocation(iface.getParent(), iface), ErrorCatalog.NO_IMPLEMENTATION);
  }
}
