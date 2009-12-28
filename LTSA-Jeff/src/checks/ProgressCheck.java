package checks;

import java.awt.event.*;

import javax.swing.*;

import com.hopstepjump.backbone.runtime.api.*;

public class ProgressCheck
{
// start generated code
// attributes
	private Attribute<lts.CompositeState> top;
	private Attribute<java.lang.String> name = new Attribute<java.lang.String>("Progress!");
	private Attribute<java.lang.String> icon = new Attribute<java.lang.String>("icon/progress.gif");
// required ports
	private lts.IEventManager events;
	private lts.LTSInput inout;
	private lts.LTSOutput inout_LTSOutputRequired;
	private actions.ICoordinator coordinator;
	private actions.IAction compiler;
	private javax.swing.JCheckBoxMenuItem fairOption;
// provided ports
	private IActionActionImpl action_IActionProvided = new IActionActionImpl();
	private ActionListenerFairOptionImpl fairOption_ActionListenerProvided = new ActionListenerFairOptionImpl();
// setters and getters
	public Attribute<lts.CompositeState> getTop() { return top; }
	public void setTop(Attribute<lts.CompositeState> top) { this.top = top;}
	public void setRawTop(lts.CompositeState top) { this.top.set(top);}
	public Attribute<java.lang.String> getName() { return name; }
	public void setName(Attribute<java.lang.String> name) { this.name = name;}
	public void setRawName(java.lang.String name) { this.name.set(name);}
	public Attribute<java.lang.String> getIcon() { return icon; }
	public void setIcon(Attribute<java.lang.String> icon) { this.icon = icon;}
	public void setRawIcon(java.lang.String icon) { this.icon.set(icon);}
	public void setEvents_IEventManager(lts.IEventManager events) { this.events = events; }
	public void setInout_LTSInput(lts.LTSInput inout) { this.inout = inout; }
	public void setInout_LTSOutput(lts.LTSOutput inout_LTSOutputRequired) { this.inout_LTSOutputRequired = inout_LTSOutputRequired; }
	public void setCoordinator_ICoordinator(actions.ICoordinator coordinator) { this.coordinator = coordinator; }
	public void setCompiler_IAction(actions.IAction compiler) { this.compiler = compiler; }
	public void setFairOption_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem fairOption) { this.fairOption = fairOption; }
	public actions.IAction getAction_IAction(Class<?> required) { return action_IActionProvided; }
	public java.awt.event.ActionListener getFairOption_ActionListener(Class<?> required) { return fairOption_ActionListenerProvided; }
// end generated code


	private class ActionListenerFairOptionImpl implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// not needed - we only need the service interface not the listener
		}
	}
	
	private class IActionActionImpl implements actions.IAction
	{
		@Override
		public boolean doAction()
		{
			lts.ProgressCheck.strongFairFlag = fairOption.isSelected();
			coordinator.clearAndShowOutput();
			if (coordinator.needRecompile())
					compiler.doAction();
			if (top.get() != null)
				top.get().checkProgress(inout_LTSOutputRequired);
			return true;
		}

		@Override
		public String getIcon()
		{
			return icon.get();
		}

		@Override
		public String getName()
		{
			return name.get();
		}
	}
}
