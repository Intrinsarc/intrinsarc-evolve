package ltsa.lts;

import java.util.*;

public class CompositionExpression {
	public final Symbol name;
	public CompositeBody body;
	Hashtable<String, Value> constants;
	Hashtable<String, Value> init_constants = new Hashtable<String, Value>(); // constant
	// table
	Vector<String> parameters = new Vector<String>(); // position of names in
	// constants
	final Hashtable<String, ProcessSpec> processes; // table of process
	// definitions
	final Hashtable<String, CompactState> compiledProcesses; // table of
	// compiled
	// definitions
	final Hashtable<String, CompositionExpression> composites; // table of
	// composite
	// definitions
	final LTSOutput output; // a bit of a hack
	boolean priorityIsLow = true;
	LabelSet priorityActions; // priority action set
	LabelSet alphaHidden; // concealments
	boolean exposeNotHide = false;
	private final boolean makeDeterministic;
	private final boolean makeMinimal;
	private final boolean makeProperty;
	private final boolean makeCompose;

	public CompositionExpression(
			Hashtable<String, CompositionExpression> composites,
			Hashtable<String, ProcessSpec> processes,
			Hashtable<String, CompactState> compiled, LTSOutput output,
			int kind, Symbol name) {
		this.composites = composites;
		this.processes = processes;
		this.compiledProcesses = compiled;
		this.output = output;
		this.makeDeterministic = kind == Symbol.DETERMINISTIC;
		this.makeProperty = kind == Symbol.PROPERTY;
		this.makeMinimal = kind == Symbol.MINIMAL;
		this.makeCompose = kind == Symbol.COMPOSE;
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	CompositeState compose(Vector<Value> actuals) {
		// list of instantiated machines (contains CompactStates and
		// CompositeStates)
		Vector<Object> machines = new Vector<Object>();
		Hashtable<String, Value> locals = new Hashtable<String, Value>();
		constants = (Hashtable<String, Value>) init_constants.clone();
		if (actuals != null)
			doParams(actuals);
		body.compose(this, machines, locals);
		Vector<CompactState> flatmachines = new Vector<CompactState>();
		for (Enumeration<Object> e = machines.elements(); e.hasMoreElements();) {
			Object o = e.nextElement();
			if (o instanceof CompactState)
				flatmachines.addElement((CompactState) o);
			else {
				CompositeState cs = (CompositeState) o;
				for (Enumeration<CompactState> ee = cs.machines.elements(); ee
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

	private void doParams(Vector<Value> actuals) {
		Enumeration<Value> a = actuals.elements();
		Enumeration<String> f = parameters.elements();
		while (a.hasMoreElements() && f.hasMoreElements())
			constants.put(f.nextElement(), a.nextElement());
	}

	private Vector<String> computeAlphabet(LabelSet a) {
		if (a == null)
			return null;
		return a.getActions(constants);
	}

}
