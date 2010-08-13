package checks;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import lts.*;

import com.intrinsarc.backbone.runtime.api.*;

import custom.*;

public class ExecuteCheck
{
// start generated code
// attributes
	private Attribute<lts.CompositeState> top;
	private Attribute<java.lang.String> name = new Attribute<java.lang.String>("Run DEFAULT!");
	private Attribute<java.lang.String> icon = new Attribute<java.lang.String>(null);
	private Attribute<java.lang.String> currentDirectory;
// required ports
	private lts.IEventManager events;
	private lts.LTSInput inout;
	private lts.LTSOutput inout_LTSOutputRequired;
	private actions.ICoordinator coordinator;
	private actions.IAction compiler;
	private lts.IAnalyser analyser;
	private ui.IAnimWindow animator;
	private com.intrinsarc.backbone.runtime.api.ICreate animatorCreator;
	private javax.swing.JCheckBoxMenuItem autorunOption;
// provided ports
	private IActionActionImpl action_IActionProvided = new IActionActionImpl();
	private ActionListenerAutorunOptionImpl autorunOption_ActionListenerProvided = new ActionListenerAutorunOptionImpl();
	private EventClientEventListenerImpl eventListener_EventClientProvided = new EventClientEventListenerImpl();
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
	public Attribute<java.lang.String> getCurrentDirectory() { return currentDirectory; }
	public void setCurrentDirectory(Attribute<java.lang.String> currentDirectory) { this.currentDirectory = currentDirectory;}
	public void setRawCurrentDirectory(java.lang.String currentDirectory) { this.currentDirectory.set(currentDirectory);}
	public void setEvents_IEventManager(lts.IEventManager events) { this.events = events; }
	public void setInout_LTSInput(lts.LTSInput inout) { this.inout = inout; }
	public void setInout_LTSOutput(lts.LTSOutput inout_LTSOutputRequired) { this.inout_LTSOutputRequired = inout_LTSOutputRequired; }
	public void setCoordinator_ICoordinator(actions.ICoordinator coordinator) { this.coordinator = coordinator; }
	public void setCompiler_IAction(actions.IAction compiler) { this.compiler = compiler; }
	public void setAnalyser_IAnalyser(lts.IAnalyser analyser) { this.analyser = analyser; }
	public void setAnimator_IAnimWindow(ui.IAnimWindow animator) { this.animator = animator; }
	public void setAnimatorCreator_ICreate(com.intrinsarc.backbone.runtime.api.ICreate animatorCreator) { this.animatorCreator = animatorCreator; }
	public void setAutorunOption_JCheckBoxMenuItem(javax.swing.JCheckBoxMenuItem autorunOption) { this.autorunOption = autorunOption; }
	public actions.IAction getAction_IAction(Class<?> required) { return action_IActionProvided; }
	public java.awt.event.ActionListener getAutorunOption_ActionListener(Class<?> required) { return autorunOption_ActionListenerProvided; }
	public lts.EventClient getEventListener_EventClient(Class<?> required) { return eventListener_EventClientProvided; }
// end generated code
	private Object animatorHandle;
	private Frame custom;
	
	class EventClientEventListenerImpl implements EventClient
	{
		@Override
		public void ltsAction(LTSEvent e)
		{
//			closeWindows(animatorHandle);
//			animatorHandle = null;
		}
		
	}
	
	class ActionListenerAutorunOptionImpl implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
		}		
	}
	
	private class IActionActionImpl implements actions.IAction
	{

		@Override
		public boolean doAction()
		{
			coordinator.clearAndShowOutput();
			if (coordinator.needRecompile())
					compiler.doAction();
			boolean replay;
			if (top.get() != null)
			{
				closeWindows(animatorHandle);
				animatorHandle = animatorCreator.create(null);
				analyser.activate(top.get(), inout_LTSOutputRequired, false);
				replay = checkReplay(analyser.getAnimator());
				
				RunMenu r = null;
				if (RunMenu.menus != null)
					r = (RunMenu) RunMenu.menus.get("DEFAULT");
				
				if (r != null && r.isCustom())
				{
					custom = createCustom(analyser.getAnimator(), r.params, r.actions, r.controls, replay);
					custom.pack();
					custom.setVisible(true);
				}
				else
					createNormal(analyser, r, autorunOption.isSelected(), replay);
			}
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

	private void closeWindows(Object handle)
	{
		if (handle != null)
			animatorCreator.destroy(handle);
		if (custom != null)
		{
			custom.dispose();
			custom = null;
		}
	}

	// ------------------------------------------------------------------------
	private boolean checkReplay(Animator a)
	{
		if (a.hasErrorTrace()) {
			int result = JOptionPane.showConfirmDialog(null,"Do you want to replay the error trace?");
			if (result == JOptionPane.YES_OPTION) {
				return true;
			} else if (result == JOptionPane.NO_OPTION)
				return false;
			else if (result == JOptionPane.CANCEL_OPTION)
				return false;
		}
		return false;
	}
	
	private void createNormal(IAnalyser analyserIAnalyserRequired, RunMenu r, boolean selected, boolean replay)
	{
		animator.activate(r, selected, replay, coordinator.getLocationOnScreen());
	}

	private Frame createCustom(Animator anim, String params, Relation actions, Relation controls, boolean replay)
	{
		CustomAnimator window = null;
		try
		{
			window = new SceneAnimator();
			File f;
			if (params != null)
				f = new File(currentDirectory.get(), params);
			else
				f = null;
			window.init(anim, f, actions, controls, replay); // give it the
			// Animator
			// interface
			return window;
		} 
		catch (Exception e)
		{
			inout_LTSOutputRequired.outln("** Failed to create instance of Scene Animator" + e);
			return null;
		}
	}
}
