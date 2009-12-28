package ltsa.custom;

import java.util.*;

import ltsa.lts.*;

public class ControlActionRegistry {

	Hashtable<String, Integer> actionNumber = new Hashtable<String, Integer>(); // maps
	// model
	// action
	// names
	// to
	// menu
	// indices
	Hashtable<String, Integer> controlNumber = new Hashtable<String, Integer>(); // maps
	// control
	// names
	// to
	// indices

	Relation<String, String> actionsToControls; // map of action names to
	// controls
	Relation<String, String> controlsToActions; // map of controls to action
	// names

	int controlMap[][]; // control indices to action indices
	int actionMap[][]; // action indices to control indices

	String actionAlphabet[]; // action indice to action name
	String controlAlphabet[]; // control indice to control name

	boolean controlState[]; // true if signalled
	AnimationMessage msg;

	public ControlActionRegistry(Relation<String, String> actionsToControls,
			AnimationMessage msg) {
		this.actionsToControls = actionsToControls;
		this.msg = msg;
	}

	void getAnimatorControls() {
		int index = 0;
		Vector<String> c = new Vector<String>();
		Enumeration<String> e = controlsToActions.keys();
		while (e.hasMoreElements()) {
			String s = e.nextElement();
			controlNumber.put(s, new Integer(index));
			c.addElement(s);
			++index;
		}
		controlAlphabet = new String[index];
		c.copyInto(controlAlphabet);
	}

	public Vector<String> getControls() {
		Vector<String> c = new Vector<String>();
		Enumeration<String> e = actionsToControls.keys();
		while (e.hasMoreElements()) {
			String s = e.nextElement();
			c.addElement(s);
		}
		return c;
	}

	public int controlled(String name) {
		Integer I = actionNumber.get(name);
		if (I == null)
			return -1;
		return I.intValue();
	}

	void initMap(String[] alpha) {
		actionAlphabet = alpha;
		// create map from action name to indice
		for (int i = 1; i < alpha.length; ++i)
			actionNumber.put(alpha[i], new Integer(i));
		// remove entries in actionsToControls that are not in actionNumber
		Enumeration<String> e = actionsToControls.keys();
		Vector<String> rr = new Vector<String>();
		while (e.hasMoreElements()) {
			String s = e.nextElement();
			if (actionNumber.get(s) == null)
				rr.addElement(s);
		}
		e = rr.elements();
		while (e.hasMoreElements()) {
			actionsToControls.remove(e.nextElement());
		}
		// create inverse mapping
		controlsToActions = actionsToControls.inverse();
		// create control alphabet
		getAnimatorControls();
		controlMap = new int[controlAlphabet.length][];
		controlState = new boolean[controlAlphabet.length];
		// controls are initially true
		for (int i = 0; i < controlState.length; ++i)
			controlState[i] = true;
		// now create map from animation control number to action number
		initControlMap();
		// now create map from action number to animation control number
		initActionMap();
	}

	protected void initControlMap() {
		int temp[];
		Enumeration<String> e = controlsToActions.keys();
		while (e.hasMoreElements()) {
			String control = e.nextElement();
			int ic = controlNumber.get(control).intValue();
			Vector<String> actions = controlsToActions.get(control);
			Vector<String> v = actions;
			temp = new int[v.size()];
			Enumeration<String> en = v.elements();
			int ii = 0;
			while (en.hasMoreElements()) {
				String s = en.nextElement();
				int ia = actionNumber.get(s).intValue();
				temp[ii] = ia;
				++ii;
			}
			controlMap[ic] = temp;
		}
		// print();
	}

	protected void initActionMap() {
		actionMap = new int[actionAlphabet.length][];
		int temp[];
		Enumeration<String> e = actionsToControls.keys();
		while (e.hasMoreElements()) {
			String action = e.nextElement();
			int ia = actionNumber.get(action).intValue();
			Vector<String> v = actionsToControls.get(action);
			temp = new int[v.size()];
			Enumeration<String> en = v.elements();
			int ii = 0;
			while (en.hasMoreElements()) {
				String s = en.nextElement();
				int ic = controlNumber.get(s).intValue();
				temp[ii] = ic;
				++ii;
			}
			actionMap[ia] = temp;
		}
	}

	void print() {
		for (int i = 0; i < controlMap.length; i++) {
			System.out.println(controlAlphabet[i]);
			for (int j = 0; j < controlMap[i].length; j++) {
				System.out.print(" " + actionAlphabet[controlMap[i][j]]);
			}
			System.out.println();
		}
	}

	void mapControl(String name, boolean[] enabled, boolean set) {
		String s;
		if (set)
			s = "-enable-";
		else
			s = "-disabl-";
		msg.debugMsg("-control" + s + name);
		Integer II = controlNumber.get(name);
		if (II == null)
			return;
		int ic = II.intValue(); // the control index number
		controlState[ic] = set;
		if (controlMap[ic] == null)
			return;
		for (int i = 0; i < controlMap[ic].length; ++i) {
			int ia = controlMap[ic][i];
			if (actionMap[ia].length == 1) {
				enabled[ia] = set;
			} else {
				boolean mask = set;
				for (int j = 0; j < actionMap[ia].length; ++j)
					mask = mask && controlState[actionMap[ia][j]];
				enabled[ia] = mask;
			}
		}
	}
}
