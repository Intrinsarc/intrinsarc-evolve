package actions;

import lts.*;

import com.intrinsarc.backbone.runtime.api.*;

public class CompileManager
{
// start generated code
// attributes
	private Attribute<lts.CompositeState> top;
	private Attribute<java.lang.String> name;
	private Attribute<java.lang.String> icon;
// required ports
	private lts.IEventManager events;
	private actions.ICoordinator coordinator;
	private actions.INameList target;
	private actions.IAction parser;
	private lts.LTSCompiler compiler;
	private com.intrinsarc.backbone.runtime.api.ICreate createCompiler;
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
	public void setCoordinator_ICoordinator(actions.ICoordinator coordinator) { this.coordinator = coordinator; }
	public void setTarget_INameList(actions.INameList target) { this.target = target; }
	public void setParser_IAction(actions.IAction parser) { this.parser = parser; }
	public void setCompiler_LTSCompiler(lts.LTSCompiler compiler) { this.compiler = compiler; }
	public void setCreateCompiler_ICreate(com.intrinsarc.backbone.runtime.api.ICreate createCompiler) { this.createCompiler = createCompiler; }
	public actions.IAction getAction_IAction(Class<?> required) { return action_IActionProvided; }
// end generated code


	private class IActionActionImpl implements actions.IAction
	{
		@Override
		public boolean doAction()
		{
			coordinator.clearAndShowOutput();
			if (!parser.doAction())
				return false;

			coordinator.primeText();
			Object handle = createCompiler.create(null);
			try
			{
				top.set(compiler.compile(target.getSelectedItem()));
			}
			catch (LTSException x)
			{
				coordinator.displayError(x);
			}
			finally
			{
				createCompiler.destroy(handle);
			}
			
			if (top.get() != null)
			{
	//xxx			if (animator != null) {
//					animator.dispose();
//					animator = null;
//				}
				if (events != null)
					events.post(new LTSEvent(LTSEvent.INVALID, top.get()));
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
