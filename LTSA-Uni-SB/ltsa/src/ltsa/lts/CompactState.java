package ltsa.lts;

import java.io.*;
import java.util.*;

public class CompactState implements Automata {
	public String name;
	public int maxStates;
	public String[] alphabet;
	public EventState[] states; // each state is to a vector of <event,
	// nextstate>
	int endseq = -9999; // number of end of sequence state if any

	public CompactState() {
		// null constructor
	}

	public CompactState(int size, String name, MyHashStack statemap,
			MyList transitions, String[] alphabet, int endSequence) {
		this.alphabet = alphabet;
		this.name = name;
		maxStates = size;
		states = new EventState[maxStates];
		while (!transitions.empty()) {
			final int fromState = transitions.getFrom();
			final int toState = transitions.getTo() == null ? -1 : statemap
					.get(transitions.getTo());
			states[fromState] = EventState.add(states[fromState],
					new EventState(transitions.getAction(), toState));
			transitions.next();
		}
		endseq = endSequence;
	}

	public void reachable() {
		final MyIntHash otn = EventState.reachable(states);
		// System.out.println("reachable states "+otn.size()+" total states
		// "+maxStates);
		// always do reachable for better layout!!
		// if (otn.size() == maxStates) return;
		final EventState[] oldStates = states;
		maxStates = otn.size();
		states = new EventState[maxStates];
		for (int oldi = 0; oldi < oldStates.length; ++oldi) {
			final int newi = otn.get(oldi);
			if (newi > -2) {
				states[newi] = EventState.renumberStates(oldStates[oldi], otn);
			}
		}
		if (endseq > 0)
			endseq = otn.get(endseq);
	}

	// change (a ->(tau->P|tau->Q)) to (a->P | a->Q)
	public void removeNonDetTau() {
		if (!hasTau())
			return;
		while (true) {
			boolean canRemove = false;
			for (int i = 0; i < maxStates; i++)
				// remove reflexive tau
				states[i] = EventState.remove(states[i], new EventState(
						Declaration.TAU, i));
			final BitSet tauOnly = new BitSet(maxStates);
			for (int i = 1; i < maxStates; ++i) {
				if (EventState.hasOnlyTauAndAccept(states[i], alphabet)) {
					tauOnly.set(i);
					canRemove = true;
				}
			}
			if (!canRemove)
				return;
			for (int i = 0; i < maxStates; ++i) {
				if (!tauOnly.get(i))
					states[i] = EventState.addNonDetTau(states[i], states,
							tauOnly);
			}
			final int oldSize = maxStates;
			reachable();
			if (oldSize == maxStates)
				return;
		}
	}

	public void removeDetCycles(String action) {
		final int act = eventNo(action);
		if (act >= alphabet.length)
			return;
		for (int i = 0; i < states.length; ++i) {
			if (!EventState.hasNonDetEvent(states[i], act))
				states[i] = EventState
						.remove(states[i], new EventState(act, i));
		}
	}

	// check if has only single terminal accept state
	// also if no accept states - treats as safety property so that TRUE
	// generates a null constraint
	public boolean isSafetyOnly() {
		int terminalAcceptStates = 0;
		int acceptStates = 0;
		for (int i = 0; i < maxStates; i++) {
			if (EventState.isAccepting(states[i], alphabet)) {
				++acceptStates;
				if (EventState.isTerminal(i, states[i]))
					++terminalAcceptStates;
			}
		}
		return (terminalAcceptStates == 1 && acceptStates == 1)
				|| acceptStates == 0;
	}

	// precondition - isSafetyOnly()
	// translates acceptState to ERROR state
	/*
	 * public void makeSafety() { for (int i = 0; i<maxStates; i++) { if
	 * (EventState.isAccepting(states[i],alphabet)) { states[i] = new
	 * EventState(Declaration.TAU,Declaration.ERROR); } } }
	 */
	/* This version handles FALSE 13th June 2004 */
	public void makeSafety() {
		int acceptState = -1;
		for (int i = 0; i < maxStates; i++) {
			if (EventState.isAccepting(states[i], alphabet)) {
				acceptState = i;
				break;
			}
		}
		if (acceptState >= 0)
			states[acceptState] = EventState.removeAccept(states[acceptState]);
		for (int i = 0; i < maxStates; i++) {
			EventState.replaceWithError(states[i], acceptState);
		}
		reachable();
	}

	// remove acceptance from states with only outgoing tau
	public void removeAcceptTau() {
		for (int i = 1; i < maxStates; ++i) {
			if (EventState.hasOnlyTauAndAccept(states[i], alphabet)) {
				states[i] = EventState.removeAccept(states[i]);
			}
		}
	}

	public boolean hasERROR() {
		for (int i = 0; i < maxStates; i++)
			if (EventState.hasState(states[i], Declaration.ERROR))
				return true;
		return false;
	}

	public void prefixLabels(String prefix) {
		name = prefix + ":" + name;
		for (int i = 1; i < alphabet.length; i++) { // don't prefix tau
			final String old = alphabet[i];
			alphabet[i] = prefix + "." + old;
		}
	}

	private boolean hasduplicates = false;

	public boolean relabelDuplicates() {
		return hasduplicates;
	}

	public void relabel(Relation<String, String> oldtonew) {
		hasduplicates = false;
		if (oldtonew.isRelation())
			relational_relabel(oldtonew);
		else
			functional_relabel(oldtonew);
	}

	private void relational_relabel(Relation<String, String> oldtonew) {
		final Vector<String> na = new Vector<String>();
		// index map old to additional
		final Relation<Integer, Integer> otoni = new Relation<Integer, Integer>();
		na.setSize(alphabet.length);
		int new_index = alphabet.length;
		na.setElementAt(alphabet[0], 0);
		for (int i = 1; i < alphabet.length; i++) {
			int prefix_end = -1;
			Vector<String> v = oldtonew.get(alphabet[i]);
			if (v != null) {
				na.setElementAt(v.firstElement(), i);
				for (int j = 1; j < v.size(); ++j) {
					na.addElement(v.elementAt(j));
					otoni.put(new Integer(i), new Integer(new_index));
					++new_index;
				}
			} else if ((prefix_end = maximalPrefix(alphabet[i], oldtonew)) >= 0) {
				// is it prefix?
				final String old_prefix = alphabet[i].substring(0, prefix_end);
				v = oldtonew.get(old_prefix);
				if (v != null) {
					na.setElementAt((v.firstElement())
							+ alphabet[i].substring(prefix_end), i);
					for (int j = 1; j < v.size(); ++j) {
						na.addElement((v.elementAt(j))
								+ alphabet[i].substring(prefix_end));
						otoni.put(new Integer(i), new Integer(new_index));
						++new_index;
					}
				} else {
					na.setElementAt(alphabet[i], i); // not relabelled
				}
			} else {
				na.setElementAt(alphabet[i], i); // not relabelled
			}
		}
		// install new alphabet
		final String aa[] = new String[na.size()];
		na.copyInto(aa);
		alphabet = aa;
		// add transitions
		addtransitions(otoni);
		checkDuplicates();
	}

	private void functional_relabel(Relation<String, String> oldtonew) {
		for (int i = 1; i < alphabet.length; i++) { // don't relabel tau
			final Vector<String> newlabel = oldtonew.get(alphabet[i]);
			if (newlabel != null && newlabel.size() > 0) {
				assert newlabel.size() == 1;
				alphabet[i] = newlabel.get(0);
			} else
				alphabet[i] = prefixLabelReplace(i, oldtonew);
		}
		checkDuplicates();
	}

	private void checkDuplicates() {
		final Hashtable<String, String> duplicates = new Hashtable<String, String>();
		for (int i = 1; i < alphabet.length; i++) {
			if (duplicates.put(alphabet[i], alphabet[i]) != null) {
				hasduplicates = true;
				crunchDuplicates();
			}
		}
	}

	private void crunchDuplicates() {
		final Hashtable<String, Integer> newAlpha = new Hashtable<String, Integer>();
		final Hashtable<Integer, Integer> oldtonew = new Hashtable<Integer, Integer>();
		int index = 0;
		for (int i = 0; i < alphabet.length; i++) {
			if (newAlpha.containsKey(alphabet[i])) {
				oldtonew.put(new Integer(i), newAlpha.get(alphabet[i]));
			} else {
				newAlpha.put(alphabet[i], new Integer(index));
				oldtonew.put(new Integer(i), new Integer(index));
				index++;
			}
		}
		alphabet = new String[newAlpha.size()];
		final Enumeration<String> e = newAlpha.keys();
		while (e.hasMoreElements()) {
			final String s = e.nextElement();
			final int i = newAlpha.get(s).intValue();
			alphabet[i] = s;
		}
		// renumber transitions
		for (int i = 0; i < states.length; i++)
			states[i] = EventState.renumberEvents(states[i], oldtonew);
	}

	// now used only for incremental minimization
	public Vector<String> hide(Vector<String> toShow) {
		final Vector<String> toHide = new Vector<String>();
		for (int i = 1; i < alphabet.length; i++) {
			if (!contains(alphabet[i], toShow))
				toHide.addElement(alphabet[i]);
		}
		return toHide;
	}

	// hides every event but the ones in toShow
	public void expose(Vector<String> toShow) {
		final BitSet visible = new BitSet(alphabet.length);
		for (int i = 1; i < alphabet.length; ++i) {
			if (contains(alphabet[i], toShow))
				visible.set(i);
		}
		visible.set(0);
		dohiding(visible);
	}

	public void conceal(Vector<String> toHide) {
		final BitSet visible = new BitSet(alphabet.length);
		for (int i = 1; i < alphabet.length; ++i) {
			if (!contains(alphabet[i], toHide))
				visible.set(i);
		}
		visible.set(0);
		dohiding(visible);
	}

	private void dohiding(BitSet visible) {
		final Integer tau = new Integer(Declaration.TAU);
		final Hashtable<Integer, Integer> oldtonew = new Hashtable<Integer, Integer>();
		final Vector<String> newAlphabetVec = new Vector<String>();
		int index = 0;
		for (int i = 0; i < alphabet.length; i++) {
			if (!visible.get(i)) {
				oldtonew.put(new Integer(i), tau);
			} else {
				newAlphabetVec.addElement(alphabet[i]);
				oldtonew.put(new Integer(i), new Integer(index));
				index++;
			}
		}
		alphabet = new String[newAlphabetVec.size()];
		newAlphabetVec.copyInto(alphabet);
		// renumber transitions
		for (int i = 0; i < states.length; i++)
			states[i] = EventState.renumberEvents(states[i], oldtonew);
	}

	static boolean contains(String action, Vector<String> v) {
		final Enumeration<String> e = v.elements();
		while (e.hasMoreElements()) {
			final String s = e.nextElement();
			if (s.equals(action) || isPrefix(s, action))
				return true;
		}
		return false;
	}

	// make every state have transitions to ERROR state
	// for actions not already declared from that state
	// properties can terminate in any state,however, we set no end state

	private boolean prop = false;

	public boolean isProperty() {
		return prop;
	}

	public void makeProperty() {
		endseq = -9999;
		prop = true;
		for (int i = 0; i < maxStates; i++)
			states[i] = EventState.addTransToError(states[i], alphabet.length);
	}

	public void unMakeProperty() {
		endseq = -9999;
		prop = false;
		for (int i = 0; i < maxStates; i++)
			states[i] = EventState.removeTransToError(states[i]);
	}

	public boolean isNonDeterministic() {
		for (int i = 0; i < maxStates; i++)
			if (EventState.hasNonDet(states[i]))
				return true;
		return false;
	}

	// output LTS in aldebaran format
	public void printAUT(PrintStream out) {
		out.print("des(0," + ntransitions() + "," + maxStates + ")\n");
		for (int i = 0; i < states.length; i++)
			EventState.printAUT(states[i], i, alphabet, out);
	}

	public CompactState myclone() {
		final CompactState m = new CompactState();
		m.name = name;
		m.endseq = endseq;
		m.prop = prop;
		m.alphabet = new String[alphabet.length];
		for (int i = 0; i < alphabet.length; i++)
			m.alphabet[i] = alphabet[i];
		m.maxStates = maxStates;
		m.states = new EventState[maxStates];
		for (int i = 0; i < maxStates; i++)
			m.states[i] = EventState.union(m.states[i], states[i]);
		return m;
	}

	public int ntransitions() {
		int count = 0;
		for (int i = 0; i < states.length; i++)
			count += EventState.count(states[i]);
		return count;
	}

	public boolean hasTau() {
		for (int i = 0; i < states.length; ++i) {
			if (EventState.hasTau(states[i]))
				return true;
		}
		return false;
	}

	/* ------------------------------------------------------------ */
	private String prefixLabelReplace(int i, Relation<String, String> oldtonew) {
		assert !oldtonew.isRelation;
		final int prefix_end = maximalPrefix(alphabet[i], oldtonew);
		if (prefix_end < 0)
			return alphabet[i];
		final String old_prefix = alphabet[i].substring(0, prefix_end);
		final Vector<String> new_prefixes = oldtonew.get(old_prefix);
		if (new_prefixes == null)
			return alphabet[i];
		assert new_prefixes.size() == 1;
		return new_prefixes.get(0) + alphabet[i].substring(prefix_end);
	}

	private int maximalPrefix(String s, Relation<String, String> oldtonew) {
		final int prefix_end = s.lastIndexOf('.');
		if (prefix_end < 0)
			return prefix_end;
		if (oldtonew.containsKey(s.substring(0, prefix_end)))
			return prefix_end;
		else
			return maximalPrefix(s.substring(0, prefix_end), oldtonew);
	}

	static private boolean isPrefix(String prefix, String s) {
		final int prefix_end = s.lastIndexOf('.');
		if (prefix_end < 0)
			return false;
		if (prefix.equals(s.substring(0, prefix_end)))
			return true;
		else
			return isPrefix(prefix, s.substring(0, prefix_end));
	}

	/* ------------------------------------------------------------ */

	public boolean isErrorTrace(Vector<String> trace) {
		boolean hasError = false;
		for (int i = 0; i < maxStates && !hasError; i++)
			if (EventState.hasState(states[i], Declaration.ERROR))
				hasError = true;
		if (!hasError)
			return false;
		return isTrace(trace, 0, 0);
	}

	private boolean isTrace(Vector<String> v, int index, int start) {
		if (index < v.size()) {
			final String ename = v.elementAt(index);
			final int eno = eventNo(ename);
			if (eno < alphabet.length) { // this event is in the alphabet
				if (EventState.hasEvent(states[start], eno)) {
					final int n[] = EventState.nextState(states[start], eno);
					for (int i = 0; i < n.length; ++i)
						// try each nondet path
						if (isTrace(v, index + 1, n[i]))
							return true;
					return false;
				} else if (eno != Declaration.TAU) // ignore taus
					return false;
			}
			return isTrace(v, index + 1, start);
		} else
			return (start == Declaration.ERROR);
	}

	private int eventNo(String ename) {
		int i = 0;
		while (i < alphabet.length && !ename.equals(alphabet[i]))
			i++;
		return i;
	}

	/* --------------------------------------------------------------- */

	/*
	 * addAcess extends the alphabet by creating a new copy of the alphabet for
	 * each prefix string in pset. Each transition is replicated acording to the
	 * number of prefixes and renumbered with the new action number.
	 */

	public void addAccess(Vector<String> pset) {
		final int n = pset.size();
		if (n == 0)
			return;
		String s = "{";
		final CompactState machs[] = new CompactState[n];
		final Enumeration<String> e = pset.elements();
		int i = 0;
		while (e.hasMoreElements()) {
			final String prefix = e.nextElement();
			s = s + prefix;
			machs[i] = myclone();
			machs[i].prefixLabels(prefix);
			i++;
			if (i < n)
				s = s + ",";
		}
		// new name
		name = s + "}::" + name;
		// new alphabet
		final int alphaN = alphabet.length - 1;
		alphabet = new String[(alphaN * n) + 1];
		alphabet[0] = "tau";
		for (int j = 0; j < n; j++) {
			for (int k = 1; k < machs[j].alphabet.length; k++) {
				alphabet[alphaN * j + k] = machs[j].alphabet[k];
			}
		}
		// additional transitions
		for (int j = 1; j < n; j++) {
			for (int k = 0; k < maxStates; k++) {
				EventState.offsetEvents(machs[j].states[k], alphaN * j);
				states[k] = EventState.union(states[k], machs[j].states[k]);
			}
		}
	}

	/* --------------------------------------------------------------- */

	private void addtransitions(Relation<Integer, Integer> oni) {
		for (int i = 0; i < states.length; i++) {
			final EventState ns = EventState.newTransitions(states[i], oni);
			if (ns != null)
				states[i] = EventState.union(states[i], ns);
		}
	}

	/* --------------------------------------------------------------- */

	public boolean hasLabel(String label) {
		for (int i = 0; i < alphabet.length; ++i)
			if (label.equals(alphabet[i]))
				return true;
		return false;
	}

	public boolean usesLabel(String label) {
		if (!hasLabel(label))
			return false;
		final int en = eventNo(label);
		for (int i = 0; i < states.length; ++i) {
			if (EventState.hasEvent(states[i], en))
				return true;
		}
		return false;
	}

	/* --------------------------------------------------------------- */

	public boolean isSequential() {
		return endseq >= 0;
	}

	public boolean isEnd() {
		return maxStates == 1 && endseq == 0;
	}

	/*----------------------------------------------------------------*/

	public static CompactState sequentialCompose(Vector<CompactState> seqs) {
		if (seqs == null)
			return null;
		if (seqs.size() == 0)
			return null;
		if (seqs.size() == 1)
			return seqs.elementAt(0);
		CompactState machines[] = new CompactState[seqs.size()];
		machines = seqs.toArray(machines);
		final CompactState newMachine = new CompactState();
		newMachine.alphabet = sharedAlphabet(machines);
		newMachine.maxStates = seqSize(machines);
		newMachine.states = new EventState[newMachine.maxStates];
		int offset = 0;
		for (int i = 0; i < machines.length; i++) {
			final boolean last = (i == (machines.length - 1));
			copyOffset(offset, newMachine.states, machines[i], last);
			if (last)
				newMachine.endseq = machines[i].endseq + offset;
			offset += machines[i].states.length;
		}
		return newMachine;
	}

	/*----------------------------------------------------------------*/

	public void expandSequential(Hashtable<Integer, CompactState> inserts) {
		final int ninserts = inserts.size();
		final CompactState machines[] = new CompactState[ninserts + 1];
		final int insertAt[] = new int[ninserts + 1];
		machines[0] = this;
		int index = 1;
		final Enumeration<Integer> e = inserts.keys();
		while (e.hasMoreElements()) {
			final Integer ii = e.nextElement();
			final CompactState m = inserts.get(ii);
			machines[index] = m;
			insertAt[index] = ii.intValue();
			++index;
		}
		/*
		 * System.out.println("Offsets "); for (int i=0; i<machines.length; i++)
		 * { machines[i].printAUT(System.out); System.out.println("endseq
		 * "+machines[i].endseq); }
		 */
		// newalphabet
		alphabet = sharedAlphabet(machines);
		// copy inserted machines
		for (int i = 1; i < machines.length; ++i) {
			final int offset = insertAt[i];
			for (int j = 0; j < machines[i].states.length; ++j) {
				states[offset + j] = machines[i].states[j];
			}
		}
	}

	/*
	 * compute size of sequential composite
	 */
	private static int seqSize(CompactState[] sm) {
		int length = 0;
		for (int i = 0; i < sm.length; i++)
			length += sm[i].states.length;
		return length;
	}

	private static void copyOffset(int offset, EventState[] dest,
			CompactState m, boolean last) {
		for (int i = 0; i < m.states.length; i++) {
			if (!last)
				dest[i + offset] = EventState.offsetSeq(offset, m.endseq,
						m.maxStates + offset, m.states[i]);
			else
				dest[i + offset] = EventState.offsetSeq(offset, m.endseq,
						m.endseq + offset, m.states[i]);
		}
	}

	public void offsetSeq(int offset, int finish) {
		for (int i = 0; i < states.length; i++) {
			EventState.offsetSeq(offset, endseq, finish, states[i]);
		}
	}

	/*
	 * create shared alphabet for machines & renumber acording to that alphabet
	 */
	private static String[] sharedAlphabet(CompactState[] sm) {
		// set up shared alphabet structure
		final Counter newLabel = new Counter(0);
		final Hashtable<String, Integer> actionMap = new Hashtable<String, Integer>();
		for (int i = 0; i < sm.length; i++) {
			for (int j = 0; j < sm[i].alphabet.length; j++) {
				if (!actionMap.containsKey(sm[i].alphabet[j])) {
					actionMap.put(sm[i].alphabet[j], newLabel.label());
				}
			}
		}
		// copy into alphabet array
		final String[] actionName = new String[actionMap.size()];
		final Enumeration<String> e = actionMap.keys();
		while (e.hasMoreElements()) {
			final String s = e.nextElement();
			final int index = actionMap.get(s).intValue();
			actionName[index] = s;
		}
		// renumber all transitions with new action numbers
		for (int i = 0; i < sm.length; i++) {
			for (int j = 0; j < sm[i].maxStates; j++) {
				EventState p = sm[i].states[j];
				while (p != null) {
					EventState tr = p;
					tr.event = actionMap.get(sm[i].alphabet[tr.event])
							.intValue();
					while (tr.nondet != null) {
						tr.nondet.event = tr.event;
						tr = tr.nondet;
					}
					p = p.list;
				}
			}
		}
		return actionName;

	}

	/** implementation of Automata interface * */

	private byte[] encode(int state) {
		final byte[] code = new byte[4];
		for (int i = 0; i < 4; ++i) {
			code[i] |= (byte) state;
			state = state >>> 8;
		}
		return code;
	}

	private int decode(byte[] code) {
		int x = 0;
		for (int i = 3; i >= 0; --i) {
			x |= (code[i]) & 0xFF;
			if (i > 0)
				x = x << 8;
		}
		return x;

	}

	public String[] getAlphabet() {
		return alphabet;
	}

	public Vector<String> getAlphabetV() {
		final Vector<String> v = new Vector<String>(alphabet.length - 1);
		for (int i = 1; i < alphabet.length; ++i)
			v.add(alphabet[i]);
		return v;
	}

	public MyList getTransitions(byte[] fromState) {
		final MyList tr = new MyList();
		int state;
		if (fromState == null)
			state = Declaration.ERROR;
		else
			state = decode(fromState);
		if (state < 0 || state >= maxStates)
			return tr;
		if (states[state] != null)
			for (final Enumeration<EventState> e = states[state].elements(); e
					.hasMoreElements();) {
				final EventState t = e.nextElement();
				tr.add(state, encode(t.next), t.event);
			}
		return tr;
	}

	public String getViolatedProperty() {
		return null;
	}

	// returns shortest trace to state (vector of Strings)
	public Vector<String> getTraceToState(byte[] from, byte[] to) {
		final EventState trace = new EventState(0, 0);
		return EventState.getPath(trace.path, alphabet);
	}

	// return the number of the END state
	public boolean END(byte[] state) {
		return decode(state) == endseq;
	}

	// return whether or not state is accepting
	public boolean isAccepting(byte[] state) {
		return isAccepting(decode(state));
	}

	// return the number of the START state
	public byte[] START() {
		return encode(0);
	}

	// set the Stack Checker for partial order reduction
	public void setStackChecker(StackCheck s) {
		// null operation
	}

	// returns true if partial order reduction
	public boolean isPartialOrder() {
		return false;
	}

	// diable partial order
	public void disablePartialOrder() {
		// null operation
	}

	// enable partial order
	public void enablePartialOrder() {
		// null operation
	}

	/*-------------------------------------------------------------*/
	// is state accepting
	public boolean isAccepting(int n) {
		if (n < 0 || n >= maxStates)
			return false;
		return EventState.isAccepting(states[n], alphabet);
	}

	public BitSet accepting() {
		final BitSet b = new BitSet();
		for (int i = 0; i < maxStates; ++i)
			if (isAccepting(i))
				b.set(i);
		return b;
	}

}