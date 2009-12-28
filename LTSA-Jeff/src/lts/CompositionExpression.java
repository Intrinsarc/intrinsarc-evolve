package lts;

import java.util.*;

/* -----------------------------------------------------------------------*/
class CompositionExpression {
	Symbol name;
	CompositeBody body;
	Hashtable constants;
	Hashtable init_constants = new Hashtable(); // constant table
	Vector parameters = new Vector(); // position of names in constants
	Hashtable processes; // table of process definitions
	Hashtable compiledProcesses; // table of compiled definitions
	Hashtable composites; // table of composite definitions
	LTSOutput output; // a bit of a hack
	boolean priorityIsLow = true;
	LabelSet priorityActions; // priority action set
	LabelSet alphaHidden; // concealments
	boolean exposeNotHide = false;
	boolean makeDeterministic = false;
	boolean makeMinimal = false;
	boolean makeProperty = false;
	boolean makeCompose = false;

	CompositeState compose(Vector actuals) {
		Vector machines = new Vector(); // list of instantiated machines
		Hashtable locals = new Hashtable();
		constants = (Hashtable) init_constants.clone();
		Vector references; // list of parsed process references
		if (actuals != null)
			doParams(actuals);
		body.compose(this, machines, locals);
		Vector flatmachines = new Vector();
		for (Enumeration e = machines.elements(); e.hasMoreElements();) {
			Object o = e.nextElement();
			if (o instanceof CompactState)
				flatmachines.addElement(o);
			else {
				CompositeState cs = (CompositeState) o;
				for (Enumeration ee = cs.machines.elements(); ee
						.hasMoreElements();) {
					flatmachines.addElement(ee.nextElement());
				}
			}
		}
		String refname = (actuals == null) ? name.toString() : name.toString()
				+ StateMachine.paramString(actuals);
		CompositeState c = new CompositeState(refname, flatmachines);
		c.priorityIsLow = priorityIsLow;
		c.priorityLabels = computeAlphabet(priorityActions);
		c.hidden = computeAlphabet(alphaHidden);
		c.exposeNotHide = exposeNotHide;
		c.makeDeterministic = makeDeterministic;
		c.makeMinimal = makeMinimal;
		c.makeCompose = makeCompose;
		if (makeProperty) {
			c.makeDeterministic = true;
			c.isProperty = true;
		}
		return c;
	}

	private void doParams(Vector actuals) {
		Enumeration a = actuals.elements();
		Enumeration f = parameters.elements();
		while (a.hasMoreElements() && f.hasMoreElements())
			constants.put(f.nextElement(), a.nextElement());
	}

	private Vector computeAlphabet(LabelSet a) {
		if (a == null)
			return null;
		return a.getActions(constants);
	}

}

class ProcessRef {
	Symbol name;
	Vector actualParams; // Vector of expressions stacks

	public void instantiate(CompositionExpression c, Vector machines,
			LTSOutput output, Hashtable locals) {
		// compute parameters
		Vector actuals = paramValues(locals, c);
		String refname = (actuals == null) ? name.toString() : name.toString()
				+ StateMachine.paramString(actuals);
		// have we already compiled it?
		CompactState mach = (CompactState) c.compiledProcesses.get(refname);
		if (mach != null) {
			machines.addElement(mach.myclone());
			return;
		}
		// we have not got one so first see if its a process
		ProcessSpec p = (ProcessSpec) c.processes.get(name.toString());
		if (p != null) {
			if (actualParams != null) { // check that parameter arity is correct
				if (actualParams.size() != p.parameters.size())
					Diagnostics.fatal("actuals do not match formal parameters",
							name);
			}
			if (!p.imported()) {
				StateMachine one = new StateMachine(p, actuals);
				mach = one.makeCompactState();
			} else {
				mach = new AutCompactState(p.name, p.importFile);
			}
			machines.addElement(mach.myclone()); // pass back clone
			c.compiledProcesses.put(mach.name, mach); // add to compiled
			// processes
			if (!p.imported())
				c.output.outln("Compiled: " + mach.name);
			else
				c.output.outln("Imported: " + mach.name);
			return;
		}
		// it could be a constraint
		mach = lts.ltl.AssertDefinition.compileConstraint(output, name,
				refname, actuals);
		if (mach != null) {
			machines.addElement(mach.myclone()); // pass back clone
			c.compiledProcesses.put(mach.name, mach); // add to compiled
			// processes
			return;
		}
		// it must be a composition
		CompositionExpression ce = (CompositionExpression) c.composites
				.get(name.toString());
		if (ce == null)
			Diagnostics.fatal("definition not found- " + name, name);
		if (actualParams != null) { // check that parameter arity is correct
			if (actualParams.size() != ce.parameters.size())
				Diagnostics.fatal("actuals do not match formal parameters",
						name);
		}
		CompositeState cs;
		if (ce == c) {
			Hashtable save = (Hashtable) c.constants.clone();
			cs = ce.compose(actuals);
			c.constants = save;
		} else
			cs = ce.compose(actuals);
		// dont compose if not necessary, maintain as a list of machines
		if (cs.needNotCreate()) {
			for (Enumeration e = cs.machines.elements(); e.hasMoreElements();) {
				mach = (CompactState) e.nextElement();
				mach.name = cs.name + "." + mach.name;
			}
			machines.addElement(cs); // flatten later if correct
		} else {
			mach = cs.create(output);
			c.compiledProcesses.put(mach.name, mach); // add to compiled
			// processes
			c.output.outln("Compiled: " + mach.name);
			machines.addElement(mach.myclone()); // pass back clone
		}
	}

	private Vector paramValues(Hashtable locals, CompositionExpression c) {
		if (actualParams == null)
			return null;
		Enumeration e = actualParams.elements();
		Vector v = new Vector();
		while (e.hasMoreElements()) {
			Stack stk = (Stack) e.nextElement();
			v.addElement(Expression.getValue(stk, locals, c.constants));
		}
		return v;
	}

}