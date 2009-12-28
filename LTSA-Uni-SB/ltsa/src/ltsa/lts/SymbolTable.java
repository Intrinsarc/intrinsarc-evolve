package ltsa.lts;

import java.util.*;

public class SymbolTable {

	private static Hashtable<String, Integer> keyword;
	static {
		keyword = new Hashtable<String, Integer>();
		keyword.put("const", Symbol.CONSTANT);
		keyword.put("property", Symbol.PROPERTY);
		keyword.put("range", Symbol.RANGE);
		keyword.put("if", Symbol.IF);
		keyword.put("then", Symbol.THEN);
		keyword.put("else", Symbol.ELSE);
		keyword.put("forall", Symbol.FORALL);
		keyword.put("when", Symbol.WHEN);
		keyword.put("set", Symbol.SET);
		keyword.put("progress", Symbol.PROGRESS);
		keyword.put("menu", Symbol.MENU);
		keyword.put("animation", Symbol.ANIMATION);
		keyword.put("actions", Symbol.ACTIONS);
		keyword.put("controls", Symbol.CONTROLS);
		keyword.put("deterministic", Symbol.DETERMINISTIC);
		keyword.put("minimal", Symbol.MINIMAL);
		keyword.put("compose", Symbol.COMPOSE);
		keyword.put("target", Symbol.TARGET);
		keyword.put("import", Symbol.IMPORT);
		keyword.put("assert", Symbol.ASSERT);
		keyword.put("fluent", Symbol.PREDICATE);
		keyword.put("exists", Symbol.EXISTS);
		keyword.put("rigid", Symbol.RIGID);
		keyword.put("fluent", Symbol.PREDICATE);
		keyword.put("constraint", Symbol.CONSTRAINT);
		keyword.put("initially", Symbol.INIT);
		keyword.put("dcltl", Symbol.DCLTL);
	}

	public static Integer get(String s) {
		return keyword.get(s);
	}
}