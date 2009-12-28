package ltsa.lts;

import java.util.*;

public class ProcessRef {
	Symbol name;
	Vector<Stack<Symbol>> actualParams; // Vector of expressions stacks

	@SuppressWarnings("unchecked")
	public void instantiate(CompositionExpression c, Vector<Object> machines,
			LTSOutput output, Hashtable<String, Value> locals) {
		// compute parameters
		Vector<Value> actuals = paramValues(locals, c);
		String refname = (actuals == null) ? name.toString() : name.toString()
				+ StateMachine.paramString(actuals);
		// have we already compiled it?
		CompactState mach = c.compiledProcesses.get(refname);
		if (mach != null) {
			machines.addElement(mach.myclone());
			return;
		}
		// we have not got one so first see if its a process
		ProcessSpec p = c.processes.get(name.toString());
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
		mach = ltsa.lts.ltl.AssertDefinition.compileConstraint(output, name,
				refname, actuals);
		if (mach != null) {
			machines.addElement(mach.myclone()); // pass back clone
			c.compiledProcesses.put(mach.name, mach); // add to compiled
			// processes
			return;
		}
		// it must be a composition
		CompositionExpression ce = c.composites.get(name.toString());
		if (ce == null)
			Diagnostics.fatal("definition not found- " + name, name);
		if (actualParams != null) { // check that parameter arity is correct
			if (actualParams.size() != ce.parameters.size())
				Diagnostics.fatal("actuals do not match formal parameters",
						name);
		}
		CompositeState cs;
		if (ce == c) {
			Hashtable<String, Value> save = (Hashtable<String, Value>) c.constants
					.clone();
			cs = ce.compose(actuals);
			c.constants = save;
		} else
			cs = ce.compose(actuals);
		// dont compose if not necessary, maintain as a list of machines
		if (cs.needNotCreate()) {
			for (Enumeration<CompactState> e = cs.machines.elements(); e
					.hasMoreElements();) {
				mach = e.nextElement();
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

	private Vector<Value> paramValues(Hashtable<String, Value> locals,
			CompositionExpression c) {
		if (actualParams == null)
			return null;
		Enumeration<Stack<Symbol>> e = actualParams.elements();
		Vector<Value> v = new Vector<Value>();
		while (e.hasMoreElements()) {
			Stack<Symbol> stk = e.nextElement();
			v.addElement(Expression.getValue(stk, locals, c.constants));
		}
		return v;
	}

}
