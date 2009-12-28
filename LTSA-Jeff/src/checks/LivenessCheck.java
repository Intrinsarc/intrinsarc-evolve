package checks;

import lts.*;
import lts.ltl.*;

import com.hopstepjump.backbone.runtime.api.*;

public class LivenessCheck
{
// start generated code
// attributes
	private Attribute<lts.CompositeState> top;
	private Attribute<java.lang.String> name = new Attribute<java.lang.String>("LTL property!");
	private Attribute<java.lang.String> icon = new Attribute<java.lang.String>(null);
// required ports
	private lts.IEventManager events;
	private lts.LTSInput inout;
	private lts.LTSOutput inout_LTSOutputRequired;
	private actions.ICoordinator coordinator;
	private actions.IAction compiler;
	private actions.INameList assert_;
// provided ports
	private IActionActionImpl action_IActionProvided = new IActionActionImpl();
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
	public void setAssert__INameList(actions.INameList assert_) { this.assert_ = assert_; }
	public actions.IAction getAction_IAction(Class<?> required) { return action_IActionProvided; }
// end generated code

	private class IActionActionImpl implements actions.IAction
	{
		@Override
		public boolean doAction()
		{
			if (hasLTL2BuchiJar())
			{
				coordinator.clearAndShowOutput();
				if (coordinator.needRecompile())
						compiler.doAction();
				String asserted = assert_.getSelectedItem();
				if (asserted != null)
				{
					CompositeState ltl_property = AssertDefinition.compile(inout_LTSOutputRequired, asserted);
					if (top.get() != null && ltl_property != null)
					{
						top.get().checkLTL(inout_LTSOutputRequired, ltl_property);
						if (events != null)
							events.post(new LTSEvent(LTSEvent.INVALID, top.get()));
					}
				}
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
	
	private boolean hasLTL2BuchiJar() {
		try
		{
			new gov.nasa.ltl.graph.Graph();
			return true;
		}
		catch (NoClassDefFoundError e)
		{
			return false;
		}
	}
}
