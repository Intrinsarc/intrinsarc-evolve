package com.intrinsarc.backbone.runtime.implementation;

import java.lang.reflect.*;

import com.intrinsarc.backbone.runtime.api.*;


public class Terminal implements IStateTerminalComponent, ITerminal
{
// start generated code
	private ITransition out_ITransitionRequired;
	public void setOut(ITransition out) { out_ITransitionRequired = out; }
// end generated code
	
	private boolean current;
	private Method savedMethod;
	private Object[] savedArgs;

	public boolean isCurrent()
	{
		return current;
	}
	
	public void becomeCurrent()
	{
		current = true;
		out_ITransitionRequired.enter();
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

	private ITransition proxy;
	public ITransition getIn(Class<?> required)
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
								moveToNextState();
								return current;
							}
						});
		}
		return proxy;
	}
}
