package ltsa.custom;

import java.util.*;

import ltsa.lts.*;

public class OutputActionRegistry {

	Relation<String, AnimationAction> outputs = new Relation<String, AnimationAction>();
	Relation<String, String> actionMap;
	AnimationMessage msg;

	public OutputActionRegistry(Relation<String, String> actionMap,
			AnimationMessage msg) {
		this.actionMap = actionMap;
		this.msg = msg;
	}

	public void register(String name, AnimationAction action) {
		Vector<AnimationAction> a = outputs.get(name);
		if (a != null) {
			a.addElement(action);
		} else {
			a = new Vector<AnimationAction>();
			a.addElement(action);
			outputs.put(name, a);
		}
	}

	public void doAction(String name) {
		msg.traceMsg(name);
		Vector<String> o = actionMap.get(name);
		if (o == null) {
			return; // if its not mapped don't do it
			// execute(name);
		} else {
			Enumeration<String> e = o.elements();
			while (e.hasMoreElements()) {
				execute(e.nextElement());
			}
		}
	}

	private void execute(String name) {
		msg.debugMsg("-action -" + name);
		Vector<AnimationAction> a = outputs.get(name);
		if (a == null)
			return;
		Enumeration<AnimationAction> e = a.elements();
		while (e.hasMoreElements()) {
			AnimationAction action = e.nextElement();
			action.action();
		}
	}

}
