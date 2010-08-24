package com.intrinsarc.backbone.runtime.implementation;

import java.util.*;

import com.intrinsarc.backbone.exceptions.*;
import com.intrinsarc.backbone.nodes.simple.internal.*;
import com.intrinsarc.backbone.runtime.api.*;

public class Creator implements ICreate
{
	private int factoryNumber;
	public int getFactoryNumber() { return factoryNumber; }
	public void setFactoryNumber(int factory) { this.factoryNumber = factory; }

	private BBSimpleInstantiatedFactory context;
	private Object lastMemento;
	
	public Creator(BBSimpleInstantiatedFactory context)
	{
		this.context = context;
	}
	
	public Object create(Map<String, Object> values)
	{
		BBSimpleInstantiatedFactory me = new BBSimpleInstantiatedFactory(context, context.getFactory(factoryNumber));
		try
		{
			me.instantiate(values);
			lastMemento = me;
			return me;
		}
		catch (BBRuntimeException ex)
		{
			// translate this into a runtime exception so the program doesn't have to catch it
			throw new IllegalStateException(ex.getMessage(), ex);
		}
	}

	public void destroy(Object memento)
	{
		BBSimpleInstantiatedFactory me = (BBSimpleInstantiatedFactory) (memento == null ? lastMemento : memento);
		if (me == null)
			throw new IllegalStateException("No last memento available to destroy instantiated factory");
		try
		{
			me.destroy();
			lastMemento = null;
		}
		catch (BBRuntimeException ex)
		{
			// translate this into a runtime exception so the program doesn't have to catch it
			throw new IllegalStateException(ex.getMessage(), ex);
		}
	}
}
