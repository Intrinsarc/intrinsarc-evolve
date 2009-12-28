package ltsa.lts;

import java.util.*;

public class Analyser implements Animator, Automata {
	private final CompositeState cs; // the composite being operated on
	private final CompactState[] sm; // array of state machines to be composed
	private final LTSOutput output; // interface for text output
	private final Hashtable<String, BitSet> alphabet = new Hashtable<String, BitSet>(); // map
	// action
	// name
	// to
	// bitmap
	// of
	// shared
	// machines
	private final Hashtable<String, Integer> actionMap = new Hashtable<String, Integer>(); // map
	// action
	// name
	// to
	// new
	// number;
	private final int[] actionCount; // number of machines which share this
	// action;
	private final String[] actionName; // map number to name;
	private final int Nmach; // number of machines to be composed
	private final int[] Mbase; // array of [Nmach] coding bases
	private MyHashStack analysed; // record reachable states
	private int stateCount = 0; // number of states analysed
	private final boolean[] violated; // true ifthis property already violated
	private boolean deadlockDetected = false;
	private final static int SUCCESS = 0;
	private final static int DEADLOCK = 1;
	private final static int ERROR = 2;
	private final static int FOUND = 3;
	// animation variables
	private final EventManager eman;
	// maximal progress - ie. this action is low priority
	private boolean lowpriority = true; // specified actions are low priority
	private Vector<String> priorLabels = null;
	private BitSet highAction = null; // actions with high priority
	private int acceptEvent = -1; // number of acceptance label @NAME
	private int asteriskEvent = -1; // number of asterisk event
	private final BitSet visible; // BitSet of visible actions
	private final StateCodec coder;
	private boolean canTerminate = false; // alpha(nonTerm) subset alpha(term)

	public static boolean partialOrderReduction = false;
	public static boolean preserveObsEquiv = true;

	private PartialOrder partial = null;

	public Analyser(CompositeState cs, LTSOutput output, EventManager eman) {
		this(cs, output, eman, false);
	}

	public Analyser(CompositeState cs, LTSOutput output, EventManager eman,
			boolean ia) {
		this.cs = cs;
		this.output = output;
		this.eman = eman;
		// deal with priority labels if any
		if (cs.priorityLabels != null) {
			lowpriority = cs.priorityIsLow;
			priorLabels = cs.priorityLabels;
			highAction = new BitSet();
		}
		sm = new CompactState[cs.machines.size()];
		violated = new boolean[cs.machines.size()];
		final Enumeration<CompactState> e = cs.machines.elements();
		for (int i = 0; e.hasMoreElements(); i++)
			sm[i] = (e.nextElement()).myclone();
		Nmach = sm.length;
		// print composition name
		output.outln("Composition:");
		output.out(cs.name + " = ");
		for (int i = 0; i < sm.length; i++) {
			output.out(sm[i].name);
			if (i < sm.length - 1)
				output.out(" || ");
		}
		output.outln("");
		// print low priority label set
		if (priorLabels != null) {
			if (lowpriority)
				output.out("\t>> ");
			else
				output.out("\t<< ");
			output.outln((new Alphabet(cs.priorityLabels)).toString());
		}
		// print and compute state space size
		Mbase = new int[Nmach];
		output.outln("State Space:");
		for (int i = 0; i < sm.length; i++) {
			output.out(" " + sm[i].maxStates + " ");
			if (i < sm.length - 1)
				output.out("*");
			Mbase[i] = sm[i].maxStates;
		}
		coder = new StateCodec(Mbase);
		output.outln("= 2 ** " + coder.bits());

		// set up shared alphabet structure
		final HashSet<String> terminating = new HashSet<String>();
		final HashSet<String> nonterminating = new HashSet<String>();
		final Counter newLabel = new Counter(0);
		for (int i = 0; i < sm.length; i++) {
			for (int j = 0; j < sm[i].alphabet.length; j++) {
				// compute sets of labels for term and non-terminating processes
				if (sm[i].endseq > 0)
					terminating.add(sm[i].alphabet[j]);
				else
					nonterminating.add(sm[i].alphabet[j]);
				// what machines have what labels
				BitSet b = alphabet.get(sm[i].alphabet[j]);
				if (b == null) {
					b = new BitSet();
					b.set(i);
					final String s = sm[i].alphabet[j];
					alphabet.put(s, b);
					actionMap.put(s, newLabel.label());
				} else
					b.set(i);
			}
		}
		canTerminate = terminating.containsAll(nonterminating);
		actionName = new String[alphabet.size()];
		actionCount = new int[alphabet.size()];
		// output.outln("Alphabet:");
		final Enumeration<String> e2 = alphabet.keys();
		while (e2.hasMoreElements()) {
			final String s = e2.nextElement();
			final BitSet b = alphabet.get(s);
			final int index = actionMap.get(s).intValue();
			actionName[index] = s;
			actionCount[index] = countSet(b);
			if (s.charAt(0) == '@')
				acceptEvent = index; // assumes only one acceptance label
			else if (s.equals("*"))
				if (!ia)
					asteriskEvent = index;
			// output.outln("\t"+s+b);
			// initialise low priority action bitSet
			if (highAction != null) {
				if (!lowpriority) {
					if (CompactState.contains(s, priorLabels))
						highAction.set(index);
				} else if (!CompactState.contains(s, priorLabels))
					highAction.set(index);
			}
		}
		// set priority for tau & accept lebel
		if (highAction != null) {
			if (lowpriority)
				highAction.set(0);
			else
				highAction.clear(0);
			if (acceptEvent > 0)
				highAction.clear(acceptEvent); // accept labels are always low
			// priority
		}
		actionCount[0] = 0; // tau
		// output.out("actionCount:");
		// for(int i=0; i<actionCount.length; i++) {
		// output.out(" "+actionCount[i]);}
		// output.outln("");
		// renumber all transitions with new action numbers
		for (int i = 0; i < sm.length; i++)
			for (int j = 0; j < sm[i].maxStates; j++) {
				EventState p = sm[i].states[j];
				while (p != null) {
					EventState tr = p;
					tr.machine = i;
					tr.event = actionMap.get(sm[i].alphabet[tr.event])
							.intValue();
					while (tr.nondet != null) {
						tr.nondet.event = tr.event;
						tr.nondet.machine = tr.machine;
						tr = tr.nondet;
					}
					p = p.list;
				}
			}
		// compute visible set
		visible = new BitSet(actionName.length);
		for (int i = 1; i < actionName.length; ++i) {
			if (cs.hidden == null) {
				visible.set(i);
			} else if (cs.exposeNotHide) {
				if (CompactState.contains(actionName[i], cs.hidden))
					visible.set(i);
			} else {
				if (!CompactState.contains(actionName[i], cs.hidden))
					visible.set(i);
			}
		}

	}

	private MyList compTrans; // list of transitions

	public CompactState compose() {
		return private_compose(true);
	}

	public CompactState composeNoHide() {
		return private_compose(false);
	}

	private CompactState private_compose(boolean dohiding) {
		output.outln("Composing...");
		final long start = System.nanoTime();
		newState_compose();
		final CompactState c = new CompactState(stateCount, cs.name, analysed,
				compTrans, actionName, endSequence);
		if (dohiding) {
			if (cs.hidden != null) {
				if (!cs.exposeNotHide)
					c.conceal(cs.hidden);
				else
					c.expose(cs.hidden);
			}
		}
		final long finish = System.nanoTime();
		outStatistics(stateCount, compTrans.size());
		output.outln(String.format((Locale) null, "Composed in %.1f ms",
				1e-6 * (finish - start)));
		analysed = null;
		compTrans = null;
		return c;
	}

	public void analyse(ltsa.lts.ltl.FluentTrace tracer) {
		output.outln("Analysing...");
		System.gc(); // garbage collect before start
		final long start = System.nanoTime();
		final int ret = newState_analyse(coder.zero(), null);
		final long finish = System.nanoTime();
		if (ret == DEADLOCK) {
			output.outln("Trace to DEADLOCK:");
			tracer.print(output, trace, true);
		} else if (ret == ERROR) {
			output.outln("Trace to property violation in "
					+ sm[errorMachine].name + ":");
			tracer.print(output, trace, true);
		} else {
			output.outln("No deadlocks/errors");
		}
		output.outln(String.format((Locale) null, "Analysed in %.1f ms",
				1e-6 * (finish - start)));
	}

	// >>> AMES: Deadlock Insensitive Analysis, multiple counterexamples
	public void analyse(boolean checkDeadlock, boolean multiCe) {
		output.outln("Analysing...");
		System.gc(); // garbage collect before start
		final long start = System.nanoTime();
		final int ret = newState_analyse(coder.zero(), null, checkDeadlock,
				multiCe);
		final long finish = System.nanoTime();
		if (ret == DEADLOCK) {
			output.outln("Trace to DEADLOCK:");
			printPath(trace);
		} else if (ret == ERROR) {
			output.outln("Trace to property violation in "
					+ sm[errorMachine].name + ":");
			printPath(trace);
		} else {
			output
					.outln("No " + (checkDeadlock ? "deadlocks/" : "")
							+ "errors");
		}
		output.outln(String.format((Locale) null, "Analysed in %.1f ms",
				1e-6 * (finish - start)));
	}

	public void analyse() {
		analyse(true);
	}

	public void analyse(boolean checkDeadlock) {
		analyse(checkDeadlock, false);
	}

	public List<String> getErrorTrace() {
		return trace;
	}

	// Count the number of bits set in a bitSet
	private int countSet(BitSet b) {
		int count = 0;
		for (int i = 0; i < b.size(); i++)
			if (b.get(i))
				count++;
		return count;
	}

	/*
	 * 
	 * state is represented by int[Nmach+1] where Nmach+1 is used to store the
	 * event into this transition
	 */

	private boolean isEND(int[] state) {
		if (!canTerminate)
			return false;
		for (int i = 0; i < Nmach; i++) {
			if (sm[i].endseq >= 0 && sm[i].endseq != state[i])
				return false;
		}
		return true;
	}

	private int[] myclone(int[] x) {
		final int[] tmp = new int[x.length];
		System.arraycopy(x, 0, tmp, 0, x.length);
		return tmp;
	}

	List<int[]> eligibleTransitions(int[] state) {
		List<int[]> asteriskTransitions = null;
		if (partial != null) {
			if (asteriskEvent > 0
					&& EventState.hasEvent(
							sm[Nmach - 1].states[state[Nmach - 1]],
							asteriskEvent)) {
				// do nothing
			} else {
				final List<int[]> parTrans = partial.transitions(state);
				if (parTrans != null)
					return parTrans;
			}
		}
		final int[] ac = myclone(actionCount);
		final EventState[] trs = new EventState[actionCount.length];
		int nsucc = 0; // count of feasible successor transitions
		int highs = 0; // eligible high priority actions
		for (int i = 0; i < Nmach; i++) {// foreach machine
			EventState p = sm[i].states[state[i]];
			while (p != null) { // foreach transition
				final EventState tr = p;
				tr.path = trs[tr.event];
				trs[tr.event] = tr;
				ac[tr.event]--;
				if (tr.event != 0 && ac[tr.event] == 0) {
					nsucc++; // ignoring tau, this transition is possible
					// bugfix 26-mar-04 to handle asterisk + priority
					if (highAction != null && highAction.get(tr.event)
							&& tr.event != asteriskEvent)
						++highs;
				}
				p = p.list;
			}
		}
		if (nsucc == 0 && trs[0] == null)
			return null; // DEADLOCK - no successor states
		int actionNo = 1;
		final List<int[]> transitions = new ArrayList<int[]>(8);
		// we include tau if it is high priority or its low and there are no
		// high priority transitions
		if (trs[0] != null) {
			final boolean highTau = (highAction != null && highAction.get(0));
			if (highTau || highs == 0)
				computeTauTransitions(trs[0], state, transitions);
			if (highTau)
				++highs;
		}
		while (nsucc > 0) { // do this loop once per successor state
			nsucc--;
			// find number of action
			while (ac[actionNo] > 0)
				actionNo++;
			// now compute the state for this action if not excluded tock
			if (highs <= 0 || highAction.get(actionNo)
					|| actionNo == acceptEvent) {
				EventState tr = trs[actionNo];
				boolean nonDeterministic = false;
				while (tr != null) { // test for non determinism
					if (tr.nondet != null) {
						nonDeterministic = true;
						break;
					}
					tr = tr.path;
				}
				tr = trs[actionNo];
				if (!nonDeterministic) {
					final int[] next = myclone(state);
					next[Nmach] = actionNo;
					while (tr != null) {
						next[tr.machine] = tr.next;
						tr = tr.path;
					}
					if (actionNo != asteriskEvent)
						transitions.add(next);
					else {
						asteriskTransitions = new ArrayList<int[]>(1);
						asteriskTransitions.add(next);
					}
				} else if (actionNo != asteriskEvent)
					computeNonDetTransitions(tr, state, transitions);
				else
					computeNonDetTransitions(tr, state,
							asteriskTransitions = new ArrayList<int[]>(4));
			}
			++ac[actionNo];
		}
		if (asteriskEvent < 0)
			return transitions;
		else
			return mergeAsterisk(transitions, asteriskTransitions);
	}

	private void computeTauTransitions(EventState first, int[] state,
			List<int[]> v) {
		EventState down = first;
		while (down != null) {
			EventState across = down;
			while (across != null) {
				final int[] next = myclone(state);
				next[across.machine] = across.next;
				next[Nmach] = 0; // tau
				v.add(next);
				across = across.nondet;
			}
			down = down.path;
		}
	}

	private void computeNonDetTransitions(EventState first, int[] state,
			List<int[]> v) {
		EventState tr = first;
		while (tr != null) {
			final int[] next = myclone(state);
			next[tr.machine] = tr.next;
			if (first.path != null)
				computeNonDetTransitions(first.path, next, v);
			else {
				next[Nmach] = first.event;
				v.add(next);
			}
			tr = tr.nondet;
		}
	}

	List<int[]> mergeAsterisk(List<int[]> transitions,
			List<int[]> asteriskTransitions) {
		if (transitions == null || asteriskTransitions == null)
			return transitions;
		if (transitions.size() == 0)
			return null;
		int[] asteriskTransition;
		if (asteriskTransitions.size() == 1) {
			asteriskTransition = asteriskTransitions.get(0);
			final Iterator<int[]> e = transitions.iterator();
			while (e.hasNext()) {
				final int[] next = e.next();
				if (!visible.get(next[Nmach])) {
					next[Nmach - 1] = asteriskTransition[Nmach - 1]; // fragile,
					// assumes
					// property
					// is
					// llast
					// machine!!
				}
			}
			return transitions;
		} else {
			final Iterator<int[]> a = asteriskTransitions.iterator();
			final List<int[]> newTransitions = new ArrayList<int[]>();
			while (a.hasNext()) {
				asteriskTransition = a.next();
				final Iterator<int[]> e = transitions.iterator();
				while (e.hasNext()) {
					final int[] next = e.next();
					if (!visible.get(next[Nmach])) {
						next[Nmach - 1] = asteriskTransition[Nmach - 1]; // fragile,
						// assumes
						// property
						// is
						// llast
						// machine!!
					}
					newTransitions.add(myclone(next));
				}
			}
			return newTransitions;
		}
	}

	private void outStatistics(int states, int transitions) {
		final Runtime r = Runtime.getRuntime();
		output.outln("-- States: " + states + " Transitions: " + transitions
				+ " Memory used: " + (r.totalMemory() - r.freeMemory()) / 1000
				+ "K");
	}

	private int endSequence = -99999;

	private int newState_compose() {
		System.gc(); // garbage collect before start
		analysed = new MyHashStack(100001);
		if (partialOrderReduction)
			partial = new PartialOrder(alphabet, actionName, sm,
					new StackChecker(coder, analysed), cs.hidden,
					cs.exposeNotHide, preserveObsEquiv, highAction);
		compTrans = new MyList();
		stateCount = 0;
		analysed.pushPut(coder.zero());
		while (!analysed.empty()) {
			if (analysed.marked()) {
				analysed.pop();
			} else {
				final int[] state = coder.decode(analysed.peek());
				analysed.mark(stateCount++);
				if (stateCount % 10000 == 0) {
					output.out("Depth " + analysed.getDepth() + " ");
					outStatistics(stateCount, compTrans.size());
				}
				// determine eligible transitions
				final List<int[]> transitions = eligibleTransitions(state);
				if (transitions == null) {
					if (!isEND(state)) {
						if (!deadlockDetected)
							output.outln("  potential DEADLOCK");
						deadlockDetected = true;
					} else { // this is the end state
						if (endSequence < 0)
							endSequence = stateCount - 1;
						else {
							analysed.mark(endSequence);
							--stateCount;
						}
					}
				} else {
					final Iterator<int[]> e = transitions.iterator();
					while (e.hasNext()) {
						final int[] next = e.next();
						final byte[] code = coder.encode(next);
						compTrans.add(stateCount - 1, code, next[Nmach]);
						if (code == null) {
							int i = 0;
							while (next[i] >= 0)
								i++;
							if (!violated[i])
								output.outln("  property " + sm[i].name
										+ " violation.");
							violated[i] = true;
						} else if (!analysed.containsKey(code)) {
							analysed.pushPut(code);
						}
					}
				}
			}
		}
		return SUCCESS;
	}

	private void printPath(LinkedList<String> v) {
		final Iterator<String> t = v.iterator();
		while (t.hasNext())
			output.outln("\t" + t.next());
	}

	LinkedList<String> trace;
	int errorMachine;

	// >>> AMES: Multiple counterexamples
	List<List<String>> traces;

	public List<List<String>> getErrorTraces() {
		return traces;
	}

	// <<< AMES

	// >>> AMES: Deadlock Insensitive Analysis, multiple counterexamples
	private int newState_analyse(byte[] fromState, byte[] target,
			boolean checkDeadlock, boolean multiCe) {

		traces = new LinkedList<List<String>>();

		stateCount = 0;
		int nTrans = 0; // number of transitions
		final MyHashQueue hh = new MyHashQueue(100001);
		if (partialOrderReduction)
			partial = new PartialOrder(alphabet, actionName, sm,
					new StackChecker(coder, hh), cs.hidden, cs.exposeNotHide,
					false, highAction);
		hh.addPut(fromState, 0, null);
		MyHashQueueEntry qe = null;
		while (!hh.empty()) {
			qe = hh.peek();
			fromState = qe.key;
			final int[] state = coder.decode(fromState);
			stateCount++;
			if (stateCount % 10000 == 0) {
				output.out("Depth " + hh.depth(qe) + " ");
				outStatistics(stateCount, nTrans);
			}
			// determine eligible transitions
			final List<int[]> transitions = eligibleTransitions(state);
			hh.pop();
			if (transitions == null) {
				if (!isEND(state)) {
					output.out("Depth " + hh.depth(qe) + " ");
					outStatistics(stateCount, nTrans);
					trace = hh.getPath(qe, actionName);
					return DEADLOCK;
				}
			} else {
				final Iterator<int[]> e = transitions.iterator();
				while (e.hasNext()) {
					final int[] next = e.next();
					final byte[] code = coder.encode(next);
					nTrans++;
					if (code == null || StateCodec.equals(code, target)) {
						output.out("Depth " + hh.depth(qe) + " ");
						outStatistics(stateCount, nTrans);
						if (code == null) {
							int i = 0;
							while (next[i] >= 0)
								i++;
							errorMachine = i;
						}
						trace = hh.getPath(qe, actionName);
						trace.add(actionName[next[Nmach]]); // last action to
						// ERROR
						if (code == null)
							return ERROR;
						else
							return FOUND;
					} else if (!hh.containsKey(code)) {
						hh.addPut(code, next[Nmach], qe);
					}
				}
			}
		}
		output.out("Depth " + hh.depth(qe) + " ");
		outStatistics(stateCount, nTrans);
		return SUCCESS;
	}

	private int newState_analyse(byte[] fromState, byte[] target) {
		return newState_analyse(fromState, target, true, false);
	}

	// <<< AMES

	// Implement Automata Interface

	// returns the alphabet
	public String[] getAlphabet() {
		return actionName;
	}

	// returns the transitions from a particular state
	public MyList getTransitions(byte[] state) {
		final List<int[]> ex = eligibleTransitions(coder.decode(state));
		final MyList trs = new MyList();
		if (ex == null)
			return trs;
		final Iterator<int[]> e = ex.iterator();
		while (e.hasNext()) {
			final int[] next = e.next();
			final byte[] code = coder.encode(next);
			if (code == null) {
				int i = 0;
				while (next[i] >= 0)
					i++;
				errorMachine = i;
			}
			trs.add(0, code, next[Nmach]);
		}
		return trs;
	}

	// assumes property is Machine Nmach-1
	public boolean isAccepting(byte[] state) {
		if (acceptEvent < 0)
			return false;
		final int[] ds = coder.decode(state);
		return EventState.hasEvent(sm[Nmach - 1].states[ds[Nmach - 1]],
				acceptEvent);
	}

	public String getViolatedProperty() {
		return sm[errorMachine].name;
	}

	// returns shortest trace to state (vector of Strings)
	public Vector<String> getTraceToState(byte[] from, byte[] to) {
		if (StateCodec.equals(from, to))
			return new Vector<String>();
		final int ret = newState_analyse(from, to);
		if (ret == FOUND) {
			final Vector<String> v = new Vector<String>();
			v.addAll(trace);
			return v;
		}
		return null;
	}

	// return the number of the END state
	public boolean END(byte[] state) {
		return isEND(coder.decode(state));
	}

	// return the number of the START state
	public byte[] START() {
		return coder.zero();
	}

	// set the Stack Checker for partial order reduction
	public void setStackChecker(StackCheck s) {
		if (partialOrderReduction)
			partial = new PartialOrder(alphabet, actionName, sm,
					new StackChecker(coder, s), cs.hidden, cs.exposeNotHide,
					false, highAction);
	}

	// returns true if partial order reduction
	public boolean isPartialOrder() {
		return partialOrderReduction;
	}

	PartialOrder savedPartial = null;

	// disable partial order
	public void disablePartialOrder() {
		savedPartial = partial;
		partial = null;
	}

	// enable partial order
	public void enablePartialOrder() {
		partial = savedPartial;
	}

	// Animator routines
	// ---------------------------------------------------------
	private String[] menuAlpha;
	private Hashtable<Integer, Integer> actionToIndex; // maps action
	// number(key) to index
	private Hashtable<Integer, Integer> indexToAction; // maps index(key) to
	// action number

	// Animator state
	private int[] currentA; // current state
	volatile private List<int[]> choices; // set of eligible choices
	private boolean errorState = false;
	private Enumeration<String> _replay = null; // records replay state
	private String _replayAction = null; // records next replay action

	private void getMenuHash() {
		actionToIndex = new Hashtable<Integer, Integer>();
		indexToAction = new Hashtable<Integer, Integer>();
		for (int i = 1; i < menuAlpha.length; i++) {
			final Integer index = new Integer(i);
			final Integer actionNo = actionMap.get(menuAlpha[i]);
			actionToIndex.put(actionNo, index);
			indexToAction.put(index, actionNo);
		}
	}

	private void getMenu(Vector<String> a) {
		if (a != null) {
			final Vector<String> validAction = new Vector<String>();
			final Enumeration<String> e = a.elements();
			while (e.hasMoreElements()) {
				final String s = e.nextElement();
				if (alphabet.containsKey(s))
					validAction.addElement(s);
			}
			menuAlpha = new String[validAction.size() + 1];
			menuAlpha[0] = "tau";
			for (int i = 1; i < menuAlpha.length; i++)
				menuAlpha[i] = validAction.elementAt(i - 1);
		} else {
			menuAlpha = actionName;
		}
		getMenuHash();
		return;
	}

	// create bitmap of eligible actions in menu from choices
	private BitSet menuActions() {
		final BitSet b = new BitSet(menuAlpha.length);
		if (choices != null) {
			final Iterator<int[]> e = choices.iterator();
			while (e.hasNext()) {
				final int[] next = e.next();
				final Integer actionNo = new Integer(next[Nmach]);
				final Integer index = actionToIndex.get(actionNo);
				if (index != null)
					b.set(index.intValue());
			}
		}
		return b;
	}

	// create bitmap of all eligible actions from choices
	private BitSet allActions() {
		final BitSet b = new BitSet(actionCount.length);
		if (choices != null) {
			final Iterator<int[]> e = choices.iterator();
			while (e.hasNext()) {
				final int[] next = e.next();
				b.set(next[Nmach]);
			}
		}
		return b;
	}

	public BitSet initialise(Vector<String> menu) {
		choices = eligibleTransitions(currentA = coder.decode(coder.zero())); // set
		// state
		// to
		// 0
		if (eman != null)
			eman.post(new LTSEvent(LTSEvent.NEWSTATE, currentA)); // initialise
		// animation
		// to 0
		getMenu(menu);
		// initialise possible replay trace
		if (cs.getErrorTrace() != null) {
			_replay = cs.getErrorTrace().elements();
			if (_replay.hasMoreElements())
				_replayAction = _replay.nextElement();
		}
		return menuActions();
	}

	public BitSet singleStep() {
		if (errorState)
			return null;
		if (nonMenuChoice()) {
			currentA = step(randomNonMenuChoice());
			if (errorState)
				return null;
			choices = eligibleTransitions(currentA);
		}
		return menuActions();
	}

	public BitSet menuStep(int choice) {
		if (errorState)
			return null;
		theChoice = indexToAction.get(new Integer(choice)).intValue();
		currentA = step(theChoice);
		if (errorState)
			return null;
		choices = eligibleTransitions(currentA);
		return menuActions();
	}

	// choice of event to run until nothing but menu event
	int theChoice = 0;

	// action chosen - only valid after a step
	public int actionChosen() {
		return theChoice;
	}

	// action name chosen - only valid after a step
	public String actionNameChosen() {
		return actionName[theChoice];
	}

	public boolean nonMenuChoice() {
		if (errorState)
			return false;
		final BitSet b = allActions();
		for (int i = 0; i < b.size(); i++) {
			if (b.get(i) && !actionToIndex.containsKey(new Integer(i))) {
				theChoice = i;
				return true;
			}
		}
		return false;
	}

	private int randomNonMenuChoice() {
		final BitSet b = allActions();
		final List<Integer> nmc = new ArrayList<Integer>(8);
		for (int i = 0; i < b.size(); i++) {
			final Integer II = new Integer(i);
			if (b.get(i) && !actionToIndex.containsKey(II)) {
				nmc.add(II);
			}
		}
		final int i = (Math.abs(rand.nextInt())) % nmc.size();
		theChoice = nmc.get(i).intValue();
		return theChoice;
	}

	// returns true if next element in the trace is eligible
	public boolean traceChoice() {
		if (errorState)
			return false;
		if (_replay == null)
			return false;
		if (_replayAction != null) {
			final int i = actionMap.get(_replayAction).intValue();
			final BitSet b = allActions();
			if (b.get(i)) {
				theChoice = i;
				return true;
			}
		}
		return false;
	}

	// is there an error trace
	public boolean hasErrorTrace() {
		return (cs.getErrorTrace() != null);
	}

	// execute next step in trace
	public BitSet traceStep() {
		if (errorState)
			return null;
		if (traceChoice()) {
			currentA = step(theChoice);
			if (errorState)
				return null;
			choices = eligibleTransitions(currentA);
			if (_replay.hasMoreElements()) {
				_replayAction = _replay.nextElement();
			} else
				_replayAction = null;
		}
		return menuActions();
	}

	public boolean isError() {
		return errorState;
	}

	/**
	 * return true if END state has been reached
	 */
	public boolean isEnd() {
		return isEND(currentA);
	}

	private int[] thestep(int action) { // take step from current
		if (errorState)
			return currentA;
		if (choices == null) {
			output.outln("DEADLOCK");
			errorState = true;
			return currentA;
		}
		// now compute the state for this action
		final Iterator<int[]> e = choices.iterator();
		while (e.hasNext()) {
			int[] next = e.next();
			if (next[Nmach] == action) {
				next = nonDetSelect(next);
				// output.outln(" "+actionName[action]);
				errorState = (coder.encode(next) == null);
				if (errorState) {/* output.outln("ERROR STATE"); */
					return next;
				}
				return currentA = next;
			}
		}
		return currentA;
	}

	private int[] step(int action) { // take step from current and post it
		final int[] tmp = thestep(action);
		if (eman != null) {
			eman.post(new LTSEvent(LTSEvent.NEWSTATE, tmp, actionName[action]));
		}
		return tmp;
	}

	// if a number of non deterministic events for a single choice
	// choose one of them at random
	Random rand = new Random();

	int[] nonDetSelect(int[] x) {
		final int start = choices.indexOf(x);
		int last = start + 1;
		while (last < choices.size() && x[Nmach] == choices.get(last)[Nmach])
			last++;
		if (start + 1 == last)
			return x;
		// otherwise do random choice
		final int i = start + (Math.abs(rand.nextInt())) % (last - start);
		return choices.get(i);
	}

	public String[] getMenuNames() {
		return menuAlpha;
	}

	public String[] getAllNames() {
		return actionName;
	}

	public boolean getPriority() {
		return lowpriority;
	}

	public BitSet getPriorityActions() {
		if (priorLabels == null)
			return null;
		final BitSet b = new BitSet();
		for (int i = 1; i < actionName.length; i++) {
			final Integer ix = actionToIndex.get(new Integer(i));
			if (ix != null
					&& ((lowpriority && !highAction.get(i)) || (!lowpriority && highAction
							.get(i))))
				b.set(ix.intValue());
		}
		return b;
	}

	public void message(String msg) {
		output.outln(msg);
	}

}