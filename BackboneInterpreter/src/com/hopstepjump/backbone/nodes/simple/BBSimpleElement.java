package com.hopstepjump.backbone.nodes.simple;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.deltaengine.base.*;

public abstract class BBSimpleElement extends BBSimpleObject
{
	public abstract String getUuid();
	public abstract Class getImplementationClass();
	public abstract String getImplementationClassName();
	public abstract void resolveImplementation(BBSimpleElementRegistry registry) throws BBImplementationInstantiationException;
  public abstract boolean isBean();
	public abstract DEElement getComplex();
}
