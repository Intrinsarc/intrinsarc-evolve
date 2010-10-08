package com.hopstepjump.backbone.runtime.implementation;

import java.lang.reflect.*;

import com.hopstepjump.backbone.runtime.api.*;


public class StateDispatcher implements IStateDispatcher
{
// start generated code

	private java.util.List<IEvent> dDispatch_IEventRequired = new java.util.ArrayList<IEvent>();
	public void setDDispatch_IEvent(IEvent event, int index) { PortHelper.fill(this.dDispatch_IEventRequired, event, index); }	
	private ITerminal dStart_ITerminalRequired;
	public void setDStart_ITerminal(ITerminal start) { dStart_ITerminalRequired = start; }
	private java.util.List<ITerminal> dEnd_ITerminalRequired = new java.util.ArrayList<ITerminal>();
	public void setDEnd_ITerminal(ITerminal end, int index) { PortHelper.fill(this.dEnd_ITerminalRequired, end, index); }
// end generated code
	
	private IEvent proxy;
	private boolean currentEntered;
	private IEvent current;

	private InvocationHandler handler = new InvocationHandler()
	{
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
		{
			// possibly still in start or end?
			if (dStart_ITerminalRequired.isCurrent())
				dStart_ITerminalRequired.moveToNextState();
			for (ITerminal end : dEnd_ITerminalRequired)
				if (end.isCurrent())
					end.moveToNextState();

			// find the next state
			IEvent current = null;
			for (IEvent e : dDispatch_IEventRequired)
				if (e.isCurrent())
				{
					current = e;
					break;
				}
			
			if (current != null)
				return method.invoke(current, args);
			return null;
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
		System.out.println("$$ asked for provided event port: " + System.identityHashCode(proxy));
		return proxy;
	}
}
