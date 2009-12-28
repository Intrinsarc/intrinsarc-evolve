package ltsa.lts;

import java.util.*;

class StateMachine {

	String name;
	String kludgeName;
	Hashtable<String, Integer> alphabet = new Hashtable<String, Integer>();
	Vector<String> hidden;
	Relation<String, String> relabels;
	Hashtable<String, Integer> explicit_states = new Hashtable<String, Integer>();
	Hashtable<String, Value> constants; // a bit of a kludge, should not be here
	Counter eventLabel = new Counter(0);
	Counter stateLabel = new Counter(0);
	Vector<Transition> transitions = new Vector<Transition>();
	boolean isProperty = false;
	boolean isMinimal = false;
	boolean isDeterministic = false;
	boolean exposeNotHide = false;
	Hashtable<Integer, CompactState> sequentialInserts;
	Hashtable<Integer, Integer> preInsertsLast;
	Hashtable<Integer, CompactState> preInsertsMach;
	Hashtable<Integer, Integer> aliases = new Hashtable<Integer, Integer>();

	public static LTSOutput output;

	public StateMachine(ProcessSpec spec, Vector<Value> params) {
		name = spec.getname();
		if (params != null) {
			spec.doParams(params);
			kludgeName = name + paramString(params);
		} else
			kludgeName = name;
		make(spec);
	}

	public StateMachine(ProcessSpec spec) {
		// compute machine name
		name = spec.getname();
		kludgeName = name;
		make(spec);
	}

	private void make(ProcessSpec spec) {
		constants = spec.constants;
		alphabet.put("tau", eventLabel.label());
		// compute explicit states
		spec.explicitStates(this);
		// crunch aliases
		spec.crunch(this);
		// relabel states in contiguous range from zero
		renumber();
		// compute transitions
		spec.transition(this);
		// alphabet extensions
		spec.addAlphabet(this);
		// alphabet relabels;
		spec.relabelAlphabet(this);
		// alphabet concealments
		spec.hideAlphabet(this);
		isProperty = spec.isProperty;
		isMinimal = spec.isMinimal;
		isDeterministic = spec.isDeterministic;
		exposeNotHide = spec.exposeNotHide;
	}

	public CompactState makeCompactState() {
		CompactState c = new CompactState();
		c.name = kludgeName;
		c.maxStates = stateLabel.lastLabel().intValue();
		Integer ii = explicit_states.get("END");
		if (ii != null)
			c.endseq = ii.intValue();
		c.alphabet = new String[alphabet.size()];
		Enumeration<String> e = alphabet.keys();
		while (e.hasMoreElements()) {
			String s = e.nextElement();
			int j = alphabet.get(s).intValue();
			if (s.equals("@"))
				s = "@" + c.name;
			c.alphabet[j] = s;
		}
		c.states = new EventState[c.maxStates];
		Enumeration<Transition> e2 = transitions.elements();
		while (e2.hasMoreElements()) {
			Transition t = e2.nextElement();
			int ev = alphabet.get("" + t.event).intValue();
			c.states[t.from] = EventState.add(c.states[t.from], new EventState(
					ev, t.to));
		}
		if (sequentialInserts != null)
			c.expandSequential(sequentialInserts);
		if (relabels != null)
			c.relabel(relabels);
		if (hidden != null) {
			if (!exposeNotHide)
				c.conceal(hidden);
			else
				c.expose(hidden);
		}
		if (isProperty) {
			if (c.isNonDeterministic() || c.hasTau())
				Diagnostics
						.fatal("primitive property processes must be deterministic: "
								+ name);
			c.makeProperty();
		}
		check_for_ERROR(c);
		c.reachable();
		if (isMinimal) {
			Minimiser me = new Minimiser(c, output);
			c = me.minimise();
		}
		if (isDeterministic) {
			Minimiser md = new Minimiser(c, output);
			c = md.trace_minimise();
		}
		return c;
	}

	// is the first state = ERROR ie P = ERROR?
	void check_for_ERROR(CompactState c) {
		Integer I = explicit_states.get(name);
		if (I.intValue() == Declaration.ERROR) {
			c.states = new EventState[1];
			c.maxStates = 1;
			c.states[0] = EventState.add(c.states[0], new EventState(
					Declaration.TAU, Declaration.ERROR));
		}
	}

	void addSequential(Integer state, CompactState mach) {
		if (sequentialInserts == null)
			sequentialInserts = new Hashtable<Integer, CompactState>();
		sequentialInserts.put(state, mach);
	}

	void preAddSequential(Integer start, Integer end, CompactState mach) {
		if (preInsertsLast == null)
			preInsertsLast = new Hashtable<Integer, Integer>();
		if (preInsertsMach == null)
			preInsertsMach = new Hashtable<Integer, CompactState>();
		preInsertsLast.put(start, end);
		preInsertsMach.put(start, mach);
	}

	private void insertSequential(int[] map) {
		if (preInsertsMach == null)
			return;
		Enumeration<Integer> e = preInsertsMach.keys();
		while (e.hasMoreElements()) {
			Integer start = e.nextElement();
			CompactState mach = preInsertsMach.get(start);
			Integer end = preInsertsLast.get(start);
			Integer newStart = new Integer(map[start.intValue()]);
			mach.offsetSeq(newStart.intValue(), end.intValue() >= 0 ? map[end
					.intValue()] : end.intValue());
			addSequential(newStart, mach);
		}
	}

	private Integer number(Integer alias, Counter newLabel) {
		if (preInsertsMach == null)
			return newLabel.label();
		CompactState mach = preInsertsMach.get(alias);
		if (mach == null)
			return newLabel.label();
		return newLabel.interval(mach.maxStates);
	}

	private void crunch(int index, int[] map) {
		int newi = map[index];
		while (newi >= 0 && newi != map[newi])
			newi = map[newi];
		map[index] = newi;
	}

	private void renumber() { // relabel states
		int map[] = new int[stateLabel.lastLabel().intValue()];
		for (int i = 0; i < map.length; ++i)
			map[i] = i;
		// apply alias
		Enumeration<Integer> e = aliases.keys();
		while (e.hasMoreElements()) {
			Integer targ = e.nextElement();
			Integer alias = aliases.get(targ);
			map[targ.intValue()] = alias.intValue();
		}
		// crunch aliases
		for (int i = 0; i < map.length; ++i)
			crunch(i, map);
		// renumber
		Counter newLabel = new Counter(0);
		Hashtable<Integer, Integer> oldnew = new Hashtable<Integer, Integer>();
		for (int i = 0; i < map.length; ++i) {
			Integer alias = new Integer(map[i]);
			if (!oldnew.containsKey(alias)) {
				Integer newi = map[i] >= 0 ? number(alias, newLabel)
						: new Integer(-1);
				oldnew.put(alias, newi);
				map[i] = newi.intValue();
			} else {
				Integer newi = oldnew.get(alias);
				map[i] = newi.intValue();
			}
		}
		// create offset insert sequential processes
		insertSequential(map);
		// renumber state/local process lookip table
		Enumeration<String> e2 = explicit_states.keys();
		while (e2.hasMoreElements()) {
			String s = e2.nextElement();
			Integer ii = explicit_states.get(s);
			if (ii.intValue() >= 0)
				explicit_states.put(s, new Integer(map[ii.intValue()]));
		}
		stateLabel = newLabel;
	}

	public void print(LTSOutput output) {
		// print name
		output.outln("PROCESS: " + name);
		// print alphabet
		output.outln("ALPHABET:");
		Enumeration<String> e = alphabet.keys();
		while (e.hasMoreElements()) {
			String s = e.nextElement();
			output.outln("\t" + alphabet.get(s) + "\t" + s);
		}
		// print states
		output.outln("EXPLICIT STATES:");
		e = explicit_states.keys();
		while (e.hasMoreElements()) {
			String s = e.nextElement();
			output.outln("\t" + explicit_states.get(s) + "\t" + s);
		}
		// print transitions
		output.outln("TRANSITIONS:");
		Enumeration<Transition> e2 = transitions.elements();
		while (e2.hasMoreElements()) {
			Transition t = e2.nextElement();
			output.outln("\t" + t);
		}
	}

	static String paramString(Vector<Value> v) {
		int max = v.size() - 1;
		StringBuffer buf = new StringBuffer();
		Enumeration<Value> e = v.elements();
		buf.append("(");
		for (int i = 0; i <= max; i++) {
			String s = e.nextElement().toString();
			buf.append(s);
			if (i < max) {
				buf.append(",");
			}
		}
		buf.append(")");
		return buf.toString();
	}

}