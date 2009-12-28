package ltsa.lts;

import java.util.*;

public class RunMenu {

	public String name;
	public Vector<String> alphabet; // vector of strings
	public String params;
	public Relation<String, String> actions;
	public Relation<String, String> controls;

	public static Hashtable<String, RunMenu> menus; // vector of all menus

	public static void init() {
		menus = new Hashtable<String, RunMenu>();
	}

	public RunMenu(String name, String params,
			Relation<String, String> actions, Relation<String, String> controls) {
		this.name = name;
		this.params = params;
		this.actions = actions;
		this.controls = controls;
		// menus.put(name,this);
	}

	public RunMenu(String name, Vector<String> actions) {
		this.name = name;
		this.alphabet = actions;
		// menus.put(name,this);
	}

	public static void add(RunMenu r) {
		menus.put(r.name, r);
	}

	public boolean isCustom() {
		return params != null;
	}

}