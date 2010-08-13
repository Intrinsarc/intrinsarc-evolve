package actions;

import java.util.*;

import lts.*;
import lts.ltl.*;

import com.intrinsarc.backbone.runtime.api.*;

public class ParseManager
{
// start generated code
// attributes
	private Attribute<lts.CompositeState> top;
	private Attribute<java.lang.String> name;
	private Attribute<java.lang.String> icon;
// required ports
	private actions.ICoordinator coordinator;
	private lts.LTSCompiler compiler;
	private com.intrinsarc.backbone.runtime.api.ICreate compilerCreator;
	private actions.INameList target;
	private actions.INameList check;
	private actions.INameList liveness;
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
	public void setCoordinator_ICoordinator(actions.ICoordinator coordinator) { this.coordinator = coordinator; }
	public void setCompiler_LTSCompiler(lts.LTSCompiler compiler) { this.compiler = compiler; }
	public void setCompilerCreator_ICreate(com.intrinsarc.backbone.runtime.api.ICreate compilerCreator) { this.compilerCreator = compilerCreator; }
	public void setTarget_INameList(actions.INameList target) { this.target = target; }
	public void setCheck_INameList(actions.INameList check) { this.check = check; }
	public void setLiveness_INameList(actions.INameList liveness) { this.liveness = liveness; }
	public actions.IAction getAction_IAction(Class<?> required) { return action_IActionProvided; }
// end generated code
	
	private static final String DEFAULT = "DEFAULT";


	private class IActionActionImpl implements actions.IAction
	{
		@Override
		public boolean doAction()
		{
			String oldChoice = (String) target.getSelectedItem();
			Hashtable<String, Object> cs = doparse();
			if (cs == null)
				return false;
			target.removeAllItems();
			for (String str : cs.keySet())
				target.addItem(str, true);
			if (oldChoice != null) {
				if ((!oldChoice.equals(DEFAULT)) && cs.containsKey(oldChoice))
					target.setSelectedItem(oldChoice);
			}
			target.notifyListeners();
			
			top.set(null);

			// deal with run menu
			check.removeAllItems();
			String[] run_names = MenuDefinition.names();
			boolean[] run_enabled = MenuDefinition.enabled((String) target.getSelectedItem());
			if (run_names != null) {
				for (int i = 0; i < run_names.length; ++i) {
					check.addItem(run_names[i], run_enabled[i]);
					
				}
			}
			check.notifyListeners();

			// deal with assert menu
			liveness.removeAllItems();
			String[] assert_names = AssertDefinition.names();
			if (assert_names != null) {
				for (int i = 0; i < assert_names.length; ++i)
					liveness.addItem(assert_names[i], true);
			}
			liveness.notifyListeners();
			return true;
		}
		
		private Hashtable doparse()
		{
			coordinator.primeText();
			Object handle = compilerCreator.create(null);
			Hashtable cs = new Hashtable();
			Hashtable ps = new Hashtable();
			try {
				compiler.parse(cs, ps);
			}
			catch (LTSException x)
			{
				coordinator.displayError(x);
				return null;
			}
			finally
			{
				compilerCreator.destroy(handle);
			}
			return cs;
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
