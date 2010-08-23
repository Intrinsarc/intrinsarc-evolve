package com.intrinsarc.backbone.runtime.implementation;

import java.lang.reflect.*;

import com.intrinsarc.backbone.runtime.api.*;


public class StateDispatcher implements IStateDispatcher
{
// start generated code
	private java.util.List<IEvent> dDispatch_IEventRequired = new java.util.ArrayList<IEvent>();
	public void setDDispatch_IEvent(IEvent event, int index) { PortHelper.fill(this.dDispatch_IEventRequired, event, index); }
	public void removeDDispatch_IEvent(IEvent event) { this.dDispatch_IEventRequired.remove(event); }
	private ITerminal dStart_ITerminalRequired;
	public void setDStart_ITerminal(ITerminal start) { dStart_ITerminalRequired = start; }
	private java.util.List<ITerminal> dEnd_ITerminalRequired = new java.util.ArrayList<ITerminal>();
	public void setDEnd_ITerminal(ITerminal end, int index) { PortHelper.fill(this.dEnd_ITerminalRequired, end, index); }
	public void removeDEnd_ITerminal(ITerminal end) { dEnd_ITerminalRequired.remove(end); }
// end generated code
	
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
				dStart_ITerminalRequired.becomeCurrent();
			
			// possibly still in start or end?
			if (current == null || dStart_ITerminalRequired.isCurrent())
			{
				dStart_ITerminalRequired.moveToNextState();
			}
			for (ITerminal end : dEnd_ITerminalRequired)
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
			for (IEvent e : dDispatch_IEventRequired)
				if (e.isCurrent())
				{
					current = e;
					break;
				}
		}
	};
	
	public IEvent getDEvents_IEvent(Class<?> required)
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
