package checks;

import lts.*;

import com.hopstepjump.backbone.runtime.api.*;

public class SupertraceCheck
{
// start generated code
// attributes
	private Attribute<lts.CompositeState> top;
	private Attribute<java.lang.String> name = new Attribute<java.lang.String>("Supertrace!");
	private Attribute<java.lang.String> icon = new Attribute<java.lang.String>(null);
// required ports
	private lts.IEventManager events;
	private lts.LTSInput inout;
	private lts.LTSOutput inout_LTSOutputRequired;
	private actions.ICoordinator coordinator;
	private com.hopstepjump.backbone.runtime.api.ICreate analyserCreator;
	private lts.IAnalyser analyser;
	private actions.IAction compiler;
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
	public void setAnalyserCreator_ICreate(com.hopstepjump.backbone.runtime.api.ICreate analyserCreator) { this.analyserCreator = analyserCreator; }
	public void setAnalyser_IAnalyser(lts.IAnalyser analyser) { this.analyser = analyser; }
	public void setCompiler_IAction(actions.IAction compiler) { this.compiler = compiler; }
	public actions.IAction getAction_IAction(Class<?> required) { return action_IActionProvided; }
// end generated code


	private class IActionActionImpl implements actions.IAction
	{

		@Override
		public boolean doAction()
		{
			coordinator.clearAndShowOutput();
			if (coordinator.needRecompile())
					compiler.doAction();
			if (top.get() != null && top.get().machines.size() > 0)
			{
				Object handle = analyserCreator.create(null);
				analyser.activate(top.get(), inout_LTSOutputRequired, false);
				SuperTrace s = new SuperTrace(analyser.getAnimator(), inout_LTSOutputRequired);
				top.get().setErrorTrace(s.getErrorTrace());
//				analyserCreator.destroy(handle);
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

}
