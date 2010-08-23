package com.intrinsarc.backbone.nodes.simple;

import com.intrinsarc.backbone.exceptions.*;
import com.intrinsarc.deltaengine.base.*;

public abstract class BBSimpleElement extends BBSimpleObject
{
	public abstract String getUuid();
	public abstract Class<?> getImplementationClass();
	public abstract String getImplementationClassName();
	public abstract void resolveImplementation(BBSimpleElementRegistry registry) throws BBImplementationInstantiationException;
  public abstract boolean isLegacyBean();
	public abstract DEElement getComplex();
}
