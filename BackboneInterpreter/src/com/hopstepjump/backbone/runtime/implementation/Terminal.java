package com.hopstepjump.backbone.runtime.implementation;

import java.lang.reflect.*;

import com.hopstepjump.backbone.runtime.api.*;


public class Terminal implements IStateTerminalComponent
{
// start generated code
	private ITransition out_ITransitionRequired;
	public void setOut_ITransition(ITransition out) { out_ITransitionRequired = out; }
	private ITerminal terminal_ITerminalProvided = new ITerminalImpl();
	public ITerminal getTerminal_ITerminal(Class<?> required) { return terminal_ITerminalProvided; }
// end generated code
	
	private boolean current;
	private boolean fired;
	private Method savedMethod;
	private Object[] savedArgs;

	private class ITerminalImpl implements ITerminal
	{
		public boolean isCurrent()
		{
			return current;
		}

		public void moveToNextState()
		{
			if (savedMethod != null)
			{
				try
				{
					current = !(Boolean) savedMethod.invoke(out_ITransitionRequired, savedArgs);
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
				
				if (!current)
				{
					savedMethod = null;
					savedArgs = null;
				}
			}
		}
	}

	private ITransition proxy;
	public ITransition getIn_ITransition(Class<?> required)
	{
		if (proxy == null)
		{
			proxy = (ITransition)
				Proxy.newProxyInstance(
						getClass().getClassLoader(),
						new Class<?>[]{required},
						new InvocationHandler()
						{
							public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
							{
								current = true;
								if (out_ITransitionRequired == null)
									return true;
								
								savedMethod = method;
								savedArgs = args;
								terminal_ITerminalProvided.moveToNextState();
								return current;
							}
						});
		}
		return proxy;
	}
}
