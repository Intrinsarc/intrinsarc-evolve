package ltsa.lts;

import java.util.*;

public class LabelSet {
	boolean isConstant = false;
	Vector<ActionLabels> labels; // list of unevaluates ActionLabelss, null if
	// this is a constant set
	Vector<String> actions; // list of action names for an evaluated constant
	// set

	static Hashtable<String, LabelSet> constants; // hashtable of constant sets

	public LabelSet(Symbol s, Vector<ActionLabels> lbs) {
		labels = lbs;
		if (constants.put(s.toString(), this) != null) {
			Diagnostics.fatal("duplicate set definition: " + s, s);
		}
		actions = getActions(null); // name must be null here
		isConstant = true;
		labels = null;
	}

	public LabelSet(Vector<ActionLabels> lbs) {
		labels = lbs;
	}

	public Vector<String> getActions(Hashtable<String, Value> params) {
		return getActions(null, params);
	}

	@SuppressWarnings("unchecked")
	public Vector<String> getActions(Hashtable<String, Value> locals,
			Hashtable<String, Value> params) {
		if (isConstant)
			return actions;
		if (labels == null)
			return null;
		Vector<String> v = new Vector<String>();
		Hashtable<String, String> dd = new Hashtable<String, String>(); // detect
		// and
		// discard
		// duplicates
		Hashtable<String, Value> mylocals = locals != null ? (Hashtable<String, Value>) locals
				.clone()
				: null;
		Enumeration<ActionLabels> e = labels.elements();
		while (e.hasMoreElements()) {
			ActionLabels l = e.nextElement();
			l.initContext(mylocals, params);
			while (l.hasMoreNames()) {
				String s = l.nextName();
				if (!dd.containsKey(s)) {
					v.addElement(s);
					dd.put(s, s);
				}
			}
			l.clearContext();
		}
		return v;
	}

	// >>> AMES: Enhanced Modularity
	public static Hashtable<String, LabelSet> getConstants() {
		return constants;
	}
	// <<< AMES
}
