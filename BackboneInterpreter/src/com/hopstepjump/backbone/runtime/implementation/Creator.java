package com.hopstepjump.backbone.runtime.implementation;

import java.util.*;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.backbone.nodes.simple.internal.*;
import com.hopstepjump.backbone.runtime.api.*;

public class Creator
{
	private Attribute<Integer> factoryNumber;
	public Attribute<Integer> getFactoryNumber() { return factoryNumber; }
	public void setFactoryNumber(Attribute<Integer> factory) { this.factoryNumber = factory; }

	private BBSimpleInstantiatedFactory context;
	private ICreate create_ICreateProvided = new ICreateImpl();
	public ICreate getCreate_ICreate(Class<?> required) { return create_ICreateProvided; }
	private Object lastMemento;
	
	public Creator(BBSimpleInstantiatedFactory context)
	{
		this.context = context;
	}
	
	private class ICreateImpl implements ICreate
	{
		public Object create(Map<String, Object> values)
		{
			BBSimpleInstantiatedFactory me = new BBSimpleInstantiatedFactory(context, context.getFactory(factoryNumber.get()));
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
}
