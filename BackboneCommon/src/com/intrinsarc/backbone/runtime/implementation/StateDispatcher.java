package com.intrinsarc.backbone.runtime.implementation;

import java.lang.reflect.*;

import com.intrinsarc.backbone.runtime.api.*;


public class StateDispatcher implements IStateDispatcher
{
	private java.util.List<IEvent> dDispatch = new java.util.ArrayList<IEvent>();
	public void setDDispatch(IEvent event, int index) { PortHelper.fill(dDispatch, event, index); }
	public void addDDispatch(IEvent event) { PortHelper.fill(dDispatch, event, -1); }
	public void removeDDispatch(IEvent event) { dDispatch.remove(event); }
	private ITerminal dStart;
	public void setDStart(ITerminal start) { dStart = start; }
	private java.util.List<ITerminal> dEnd = new java.util.ArrayList<ITerminal>();
	public void setDEnd(ITerminal end, int index) { PortHelper.fill(dEnd, end, index); }
	public void removeDEnd(ITerminal end) { dEnd.remove(end); }
	
	private IEvent proxy;
	private IEvent current;

	private InvocationHandler handler = new InvocationHandler()
	{
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
		{
			if (method.getName().equals("toString") && (args == null || args.length == 0))
				return "State dispatcher: current state = " + (current == null ? "start" : current);

			// if current is null, use start
			if (current == null)
				dStart.becomeCurrent();
			
			// possibly still in start or end?
			if (current == null || dStart.isCurrent())
			{
				dStart.moveToNextState();
			}
			for (ITerminal end : dEnd)
				if (end.isCurrent())
					end.moveToNextState();

			establishCurrentState();
			if (current != null)
			{
				try
				{
					return method.invoke(current, args);					
				}
				catch (InvocationTargetException ex)
				{
					// get the real reason
					throw ex.getCause();
				}
				finally
				{
					// we may have moved state since and we want the dispatcher to reflect that
					establishCurrentState();
				}
			}
			return null;
		}

		private void establishCurrentState()
		{
			// find the next state
			for (IEvent e : dDispatch)
				if (e.isCurrent())
				{
					current = e;
					break;
				}
		}
	};
	
	public IEvent getDEvents_Provided(Class<?> required)
	{
		if (proxy == null)
		{
			proxy = (IEvent)
				Proxy.newProxyInstance(
						getClass().getClassLoader(),
						new Class<?>[]{required},
						handler);
		}
		return proxy;
	}
}
