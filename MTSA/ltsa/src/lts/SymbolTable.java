package lts;

import java.util.*;

public class SymbolTable {

	private static Hashtable<String, Integer> keyword;

	public static void init() {
		keyword = new Hashtable<String, Integer>();
		keyword.put("const", new Integer(Symbol.CONSTANT));
		keyword.put("property", new Integer(Symbol.PROPERTY));
		keyword.put("range", new Integer(Symbol.RANGE));
		keyword.put("if", new Integer(Symbol.IF));
		keyword.put("then", new Integer(Symbol.THEN));
		keyword.put("else", new Integer(Symbol.ELSE));
		keyword.put("forall", new Integer(Symbol.FORALL));
		keyword.put("when", new Integer(Symbol.WHEN));
		keyword.put("set", new Integer(Symbol.SET));
		keyword.put("progress", new Integer(Symbol.PROGRESS));
		keyword.put("menu", new Integer(Symbol.MENU));
		keyword.put("animation", new Integer(Symbol.ANIMATION));
		keyword.put("actions", new Integer(Symbol.ACTIONS));
		keyword.put("controls", new Integer(Symbol.CONTROLS));
		keyword.put("deterministic", new Integer(Symbol.DETERMINISTIC));
		keyword.put("minimal", new Integer(Symbol.MINIMAL));
		keyword.put("compose", new Integer(Symbol.COMPOSE));
		keyword.put("target", new Integer(Symbol.TARGET));
		keyword.put("import", new Integer(Symbol.IMPORT));
		keyword.put("assert", new Integer(Symbol.ASSERT));
		keyword.put("fluent", new Integer(Symbol.PREDICATE));
		keyword.put("exists", new Integer(Symbol.EXISTS));
		keyword.put("rigid", new Integer(Symbol.RIGID));
		keyword.put("fluent", new Integer(Symbol.PREDICATE));
		keyword.put("constraint", new Integer(Symbol.CONSTRAINT));
		keyword.put("ltl_property", new Integer(Symbol.LTLPROPERTY));
		keyword.put("initially", new Integer(Symbol.INIT));
		keyword.put("optimistic", new Integer(Symbol.OPTIMISTIC));
		keyword.put("pessimistic", new Integer(Symbol.PESSIMISTIC));
		keyword.put("clousure", new Integer(Symbol.CLOUSURE));
		keyword.put("abstract", new Integer(Symbol.ABSTRACT));
		keyword.put("restricts", new Integer(Symbol.RESTRICTS));
		keyword.put("instances", new Integer(Symbol.INSTANCES));
		keyword.put("prechart", new Integer(Symbol.PRECHART));
		keyword.put("mainchart", new Integer(Symbol.MAINCHART));
		keyword.put("eTS", new Integer(Symbol.E_TRIGGERED_SCENARIO));
		keyword.put("uTS", new Integer(Symbol.U_TRIGGERED_SCENARIO));
		keyword.put("condition", new Integer(Symbol.CONDITION));
		keyword.put("controller", new Integer(Symbol.CONTROLLER));
		keyword.put("goal", new Integer(Symbol.GOAL));
		keyword.put("safety", new Integer(Symbol.SAFETY));
		keyword.put("assume", new Integer(Symbol.ASSUME));
		keyword.put("fault", new Integer(Symbol.FAULT));
		keyword.put("guarantee", new Integer(Symbol.GUARANTEE));
		keyword.put("controllable", new Integer(Symbol.CONTROLLABLE));

	}

	public static Object get(String s) {
		return keyword.get(s);
	}
}