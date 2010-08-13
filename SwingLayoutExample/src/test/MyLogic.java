package test;

import java.awt.event.*;

import com.intrinsarc.backbone.runtime.api.*;

public class MyLogic
{
// start generated code
	private Attribute<String> message;
	public Attribute<String> getMessage() { return message; }
	public void setMessage(Attribute<String> message) { this.message = message;}
	private java.awt.event.ActionListener hit_ActionListenerProvided = new ActionListenerHitImpl();
	public java.awt.event.ActionListener getHit_ActionListener(Class<?> required) { return hit_ActionListenerProvided; }
// end generated code


	private class ActionListenerHitImpl implements java.awt.event.ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("$$ message is: " + message.get());
		}
	}
}
