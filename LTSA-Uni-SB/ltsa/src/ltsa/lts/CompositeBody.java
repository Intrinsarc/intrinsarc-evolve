package ltsa.lts;

import java.util.*;

class CompositeBody {
	// single process reference P
	ProcessRef singleton;
	// list of CompositeBodies ( P || Q)
	Vector<CompositeBody> procRefs;
	// conditional if Bexp then P else Q
	Stack<Symbol> boolexpr;
	CompositeBody thenpart; // overloaded as body of replicator
	CompositeBody elsepart;
	// forall[i:R][j:S].
	ActionLabels range; // used to store forall range/ranges
	// the following are only applied to singletons & procRefs (...)
	ActionLabels prefix; // a:
	ActionLabels accessSet; // S::
	Vector<RelabelDefn> relabelDefns; // list of relabelling defns

	void compose(CompositionExpression c, Vector<Object> machines,
			Hashtable<String, Value> locals) {
		Vector<String> accessors = accessSet == null ? null : accessSet
				.getActions(locals, c.constants);
		Relation<String, String> relabels = RelabelDefn.getRelabels(
				relabelDefns, c.constants, locals);
		// conditional compostion
		if (boolexpr != null) {
			if (Expression.evaluate(boolexpr, locals, c.constants) != 0)
				thenpart.compose(c, machines, locals);
			else if (elsepart != null)
				elsepart.compose(c, machines, locals);
		} else if (range != null) {
			// replicated composition
			range.initContext(locals, c.constants);
			while (range.hasMoreNames()) {
				range.nextName();
				thenpart.compose(c, machines, locals);
			}
			range.clearContext();
		} else {
			// singleton or list
			Vector<Object> tempMachines = getPrefixedMachines(c, locals);
			// apply accessors
			if (accessors != null)
				for (Enumeration<Object> e = tempMachines.elements(); e
						.hasMoreElements();) {
					Object o = e.nextElement();
					if (o instanceof CompactState) {
						CompactState mach = (CompactState) o;
						mach.addAccess(accessors);
					} else {
						CompositeState cs = (CompositeState) o;
						cs.addAccess(accessors);
					}
				}
			// apply relabels
			if (relabels != null)
				for (int i = 0; i < tempMachines.size(); ++i) {
					Object o = tempMachines.elementAt(i);
					if (o instanceof CompactState) {
						CompactState mach = (CompactState) o;
						mach.relabel(relabels);
					} else {
						CompositeState cs = (CompositeState) o;
						CompactState mm = cs.relabel(relabels, c.output);
						if (mm != null)
							tempMachines.setElementAt(mm, i);
					}
				}
			// add tempMachines to machines
			for (Enumeration<Object> e = tempMachines.elements(); e
					.hasMoreElements();) {
				machines.addElement(e.nextElement());
			}
		}
	}

	private Vector<Object> getPrefixedMachines(CompositionExpression c,
			Hashtable<String, Value> locals) {
		if (prefix == null) {
			return getMachines(c, locals);
		} else {
			Vector<Object> pvm = new Vector<Object>();
			prefix.initContext(locals, c.constants);
			while (prefix.hasMoreNames()) {
				String px = prefix.nextName();
				Vector<Object> vm = getMachines(c, locals);
				for (Enumeration<Object> e = vm.elements(); e.hasMoreElements();) {
					Object o = e.nextElement();
					if (o instanceof CompactState) {
						CompactState m = (CompactState) o;
						m.prefixLabels(px);
						pvm.addElement(m);
					} else {
						CompositeState cs = (CompositeState) o;
						cs.prefixLabels(px);
						pvm.addElement(cs);
					}
				}
			}
			prefix.clearContext();
			return pvm;
		}
	}

	private Vector<Object> getMachines(CompositionExpression c,
			Hashtable<String, Value> locals) {
		Vector<Object> vm = new Vector<Object>();
		if (singleton != null) {
			singleton.instantiate(c, vm, c.output, locals);
		} else if (procRefs != null) {
			Enumeration<CompositeBody> e = procRefs.elements();
			while (e.hasMoreElements()) {
				CompositeBody cb = e.nextElement();
				cb.compose(c, vm, locals);
			}
		}
		return vm;
	}

}
