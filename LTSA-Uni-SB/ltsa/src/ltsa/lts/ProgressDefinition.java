package ltsa.lts;

import java.util.*;

/* -----------------------------------------------------------------------*/

class ProgressDefinition {
	Symbol name;
	ActionLabels pactions;
	ActionLabels cactions; // if P then C
	ActionLabels range; // range of tests

	static Hashtable<String, ProgressDefinition> definitions;

	public static void compile() {
		ProgressTest.init();
		Enumeration<ProgressDefinition> e = definitions.elements();
		while (e.hasMoreElements()) {
			ProgressDefinition p = e.nextElement();
			p.makeProgressTest();
		}
	}

	public void makeProgressTest() {
		Vector<String> pa = null;
		Vector<String> ca = null;
		String na = name.toString();
		if (range == null) {
			pa = pactions.getActions(null, null);
			if (cactions != null)
				ca = cactions.getActions(null, null);
			new ProgressTest(na, pa, ca);
		} else {
			Hashtable<String, Value> locals = new Hashtable<String, Value>();
			range.initContext(locals, null);
			while (range.hasMoreNames()) {
				String s = range.nextName();
				pa = pactions.getActions(locals, null);
				if (cactions != null)
					ca = cactions.getActions(locals, null);
				new ProgressTest(na + "." + s, pa, ca);
			}
			range.clearContext();
		}
	}
}
