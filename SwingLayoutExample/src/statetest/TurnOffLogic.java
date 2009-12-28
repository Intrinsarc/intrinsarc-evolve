package statetest;

import java.awt.event.*;

public class TurnOffLogic
{
// start generated code
	private java.awt.event.ActionListener listener_ActionListenerProvided = new ActionListenerListenerImpl();
	public java.awt.event.ActionListener getListener_ActionListener(Class<?> required) { return listener_ActionListenerProvided; }

	private statetest.ITrafficEvent events_ITrafficEventRequired;
	public void setEvents_ITrafficEvent(statetest.ITrafficEvent events_ITrafficEventRequired) { this.events_ITrafficEventRequired = events_ITrafficEventRequired; }
// end generated code


	private class ActionListenerListenerImpl implements java.awt.event.ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			events_ITrafficEventRequired.turnOff();
		}
	}
}
