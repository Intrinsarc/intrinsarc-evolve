package ltsa.lts.ltl;

import java.util.*;

import ltsa.lts.*;

public class DcltlDefinition {
	Symbol name;
	String formula;

	static Hashtable<String, DcltlDefinition> definitions;

	private DcltlDefinition(Symbol n, String f) {
		name = n;
		formula = f;
	}

	public static void put(Symbol n, String f) {
		if (definitions == null) {
			definitions = new Hashtable<String, DcltlDefinition>();
		}

		if (definitions.containsKey(n.toString())) {
			Diagnostics.fatal(
					"duplicate Did/Can LTL property definition: " + n, n);
		} else {
			definitions.put(n.toString(), new DcltlDefinition(n, f));
		}
	}

	public static String get(String n) {
		return definitions.get(n).formula;
	}

	public static void init() {
		definitions = null;
	}

	public static String[] names() {
		if (definitions == null)
			return null;
		int n = definitions.size();
		String na[];
		if (n == 0)
			return null;
		else
			na = new String[n];
		Enumeration<String> e = definitions.keys();
		int i = 0;
		while (e.hasMoreElements())
			na[i++] = e.nextElement();
		return na;
	}
}
