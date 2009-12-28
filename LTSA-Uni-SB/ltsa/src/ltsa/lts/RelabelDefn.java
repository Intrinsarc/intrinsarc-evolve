package ltsa.lts;

import java.util.*;

/* -----------------------------------------------------------------------*/

class RelabelDefn {
	ActionLabels newlabel;
	ActionLabels oldlabel;
	ActionLabels range;
	Vector<RelabelDefn> defns;

	public void makeRelabels(Hashtable<String, Value> constants,
			Relation<String, String> relabels) {
		Hashtable<String, Value> locals = new Hashtable<String, Value>();
		mkRelabels(constants, locals, relabels);
	}

	public void makeRelabels(Hashtable<String, Value> constants,
			Hashtable<String, Value> locals, Relation<String, String> relabels) {
		mkRelabels(constants, locals, relabels);
	}

	private void mkRelabels(Hashtable<String, Value> constants,
			Hashtable<String, Value> locals, Relation<String, String> relabels) {
		if (range != null) {
			range.initContext(locals, constants);
			while (range.hasMoreNames()) {
				range.nextName();
				Enumeration<RelabelDefn> e = defns.elements();
				while (e.hasMoreElements()) {
					RelabelDefn r = e.nextElement();
					r.mkRelabels(constants, locals, relabels);
				}
			}
			range.clearContext();
		} else {
			newlabel.initContext(locals, constants);
			while (newlabel.hasMoreNames()) {
				String newName = newlabel.nextName();
				oldlabel.initContext(locals, constants);
				while (oldlabel.hasMoreNames()) {
					String oldName = oldlabel.nextName();
					relabels.put(oldName, newName);
				}
			}
			newlabel.clearContext();
		}
	}

	public static Relation<String, String> getRelabels(
			Vector<RelabelDefn> relabelDefns,
			Hashtable<String, Value> constants, Hashtable<String, Value> locals) {
		if (relabelDefns == null)
			return null;
		Relation<String, String> relabels = new Relation<String, String>();
		Enumeration<RelabelDefn> e = relabelDefns.elements();
		while (e.hasMoreElements()) {
			RelabelDefn r = e.nextElement();
			r.makeRelabels(constants, locals, relabels);
		}
		return relabels;
	}

	public static Relation<String, String> getRelabels(
			Vector<RelabelDefn> relabelDefns) {
		return getRelabels(relabelDefns, new Hashtable<String, Value>(),
				new Hashtable<String, Value>());
	}
}