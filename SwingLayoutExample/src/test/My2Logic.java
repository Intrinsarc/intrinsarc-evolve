package test;

import java.awt.event.*;

import com.intrinsarc.backbone.runtime.api.*;

public class My2Logic
{
// start generated code
	private Attribute<String> message = new Attribute<String>("Foo");
	public Attribute<String> getMessage() { return message; }
	public void setMessage(Attribute<String> message) { this.message = message;}
	private Attribute<java.awt.Color> color;
	public Attribute<java.awt.Color> getColor() { return color; }
	public void setColor(Attribute<java.awt.Color> color) { this.color = color;}
	private java.awt.event.ActionListener hit_ActionListenerProvided = new ActionListenerHitImpl();
	public java.awt.event.ActionListener getHit_ActionListener(Class<?> required) { return hit_ActionListenerProvided; }
// end generated code


	private class ActionListenerHitImpl implements java.awt.event.ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("$$ " + message.get() + ", color = " + color.get());
			message.set(message.get() + "X");
		}
	}
}
