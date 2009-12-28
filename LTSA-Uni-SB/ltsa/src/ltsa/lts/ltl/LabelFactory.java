package ltsa.lts.ltl;

import java.util.*;

import ltsa.lts.*;

public class LabelFactory {

	SortedSet<Formula> allprops;
	FormulaFactory fac;
	Vector<String> alphaX;
	String name;
	HashMap<String, BitSet> tr; // transition sets
	BitSet[] ps; // proposition sets
	BitSet[] nps; // not proposition sets
	Vector<CompactState> propProcs; // process per proposition
	SortedSet<String> allActions; // application alphabet of all propositions

	public LabelFactory(String n, FormulaFactory f,
			Vector<String> alphaExtension) {
		allprops = f.getProps();
		fac = f;
		name = n;
		alphaX = alphaExtension;
		tr = new HashMap<String, BitSet>();
		initPropSets();
		compileProps();
	}

	HashMap<String, BitSet> getTransLabels() {
		return tr;
	}

	Vector<String> getPrefix() {
		final Vector<String> v = new Vector<String>();
		final Formula f = allprops.first();
		v.add("_" + f);
		return v;
	}

	String makeLabel(SortedSet<Formula> props) {
		final StringBuffer sb = new StringBuffer();
		final Iterator<Formula> ii = allprops.iterator();
		boolean isMore = false;
		final BitSet labels = new BitSet();
		int m = 0;
		while (ii.hasNext()) {
			final Formula f = ii.next();
			if (props.contains(f)) {
				if (isMore) {
					sb.append("&");
					labels.and(ps[m]);
				} else {
					labels.or(ps[m]);
					isMore = true;
				}
				sb.append(f);
			} else if (props.contains(fac.makeNot(f))) {
				if (isMore) {
					sb.append("&");
					labels.and(nps[m]);
				} else {
					labels.or(nps[m]);
					isMore = true;
				}
				sb.append("!" + f);
			}
			++m;
		}
		final String s = sb.toString();
		tr.put(s, labels);
		return s;
	}

	public String[] makeAlphabet() {
		return makeAlphabet(null, null, null);
	}

	private String[] makeAlphabet(PredicateDefinition p, BitSet tt, BitSet ft) {
		int extra = 0;
		if (p == null) {
			extra = 1; // accept label
		} else {
			extra = p.trueActions.size() + p.falseActions.size();
		}
		final int len = (1 << allprops.size()) + 1 + extra; // labels + tau +
		// extra
		final String alpha[] = new String[len];
		for (int i = 0; i < len - extra; i++) {
			final StringBuffer sb = new StringBuffer();
			final Iterator<Formula> ii = allprops.iterator();
			boolean isMore = false;
			int m = 0;
			while (ii.hasNext()) {
				final Formula f = ii.next();
				if (isMore)
					sb.append(".");
				isMore = true;
				sb.append("_" + f + "." + (i >> m) % 2);
				++m;
			}
			alpha[i + 1] = sb.toString();
		}
		alpha[0] = "tau";
		if (p == null) {
			alpha[len - 1] = "@" + name;
		} else {
			int pos = len - extra;
			Iterator<String> ii = p.falseActions.iterator();
			while (ii.hasNext()) {
				alpha[pos] = ii.next();
				ft.set(pos);
				++pos;
			}
			ii = p.trueActions.iterator();
			while (ii.hasNext()) {
				alpha[pos] = ii.next();
				tt.set(pos);
				++pos;
			}
		}
		return alpha;
	}

	void initPropSets() {
		final int len = allprops.size();
		ps = new BitSet[len];
		nps = new BitSet[len];
		final BitSet trueSet = new BitSet(1 << len);
		for (int m = 0; m < len; ++m) {
			ps[m] = new BitSet(1 << len);
			nps[m] = new BitSet(1 << len);
		}
		for (int i = 0; i < (1 << len); ++i) {
			trueSet.set(i);
			for (int m = 0; m < len; ++m)
				if (((i >> m) % 2) == 1) {
					ps[m].set(i);
				} else {
					nps[m].set(i);
				}
		}
		tr.put("true", trueSet);
	}

	private final List<PredicateDefinition> fluents = new ArrayList<PredicateDefinition>();

	public PredicateDefinition[] getFluents() {
		if (fluents.size() == 0)
			return null;
		final PredicateDefinition[] pds = new PredicateDefinition[fluents
				.size()];
		for (int i = 0; i < pds.length; ++i)
			pds[i] = fluents.get(i);
		return pds;
	}

	protected void compileProps() {
		propProcs = new Vector<CompactState>();
		allActions = new TreeSet<String>();
		// Pass 1 PredicateDefinition processes
		Iterator<Formula> ii = allprops.iterator();
		int m = 0;
		while (ii.hasNext()) {
			final Proposition f = (Proposition) ii.next();
			final PredicateDefinition p = PredicateDefinition.get(f.toString());
			if (p != null) {
				fluents.add(p);
				allActions.addAll(p.trueActions);
				allActions.addAll(p.falseActions);
				propProcs.add(makePropProcess(p, m));
			} else if (fac.actionPredicates != null
					&& fac.actionPredicates.containsKey(f.toString())) {
				// only add to alphabet this pass
				final Vector<String> vl = fac.actionPredicates
						.get(f.toString());
				allActions.addAll(vl);
			} else
				Diagnostics.fatal("Proposition " + f + " not found", f.sym);
			++m;
		}
		if (alphaX != null)
			allActions.addAll(alphaX);
		// Pass 2 Action Predicate processes
		ii = allprops.iterator();
		m = 0;
		while (ii.hasNext()) {
			final Proposition f = (Proposition) ii.next();
			PredicateDefinition p = PredicateDefinition.get(f.toString());
			if (p != null) {
				// do nothing this pass
			} else if (fac.actionPredicates != null
					&& fac.actionPredicates.containsKey(f.toString())) {
				final Vector<String> trueActions = fac.actionPredicates.get(f
						.toString());
				final Vector<String> falseActions = new Vector<String>();
				falseActions.addAll(allActions);
				falseActions.removeAll(trueActions);
				p = new PredicateDefinition(new Symbol(Symbol.UPPERIDENT, f
						.toString()), trueActions, falseActions);
				final CompactState cs = makePropProcess(p, m);
				propProcs.add(cs);
			} else
				Diagnostics.fatal("Proposition " + f + " not found", f.sym);
			++m;
		}
		// make sync process
		propProcs.add(makeSyncProcess());
	}

	CompactState makePropProcess(PredicateDefinition p, int m) {
		final CompactState cs = new CompactState();
		cs.name = p.name.toString();
		cs.maxStates = 2;
		cs.states = new EventState[cs.maxStates];
		final BitSet trueTrans = new BitSet();
		final BitSet falseTrans = new BitSet();
		cs.alphabet = makeAlphabet(p, trueTrans, falseTrans);
		final int falseS = p.initial ? 1 : 0;
		final int trueS = p.initial ? 0 : 1;
		for (int i = 0; i < trueTrans.size(); ++i)
			if (trueTrans.get(i))
				cs.states[falseS] = EventState.add(cs.states[falseS],
						new EventState(i, trueS));
		for (int i = 0; i < falseTrans.size(); ++i)
			if (falseTrans.get(i))
				cs.states[trueS] = EventState.add(cs.states[trueS],
						new EventState(i, falseS));
		for (int i = 0; i < falseTrans.size(); ++i)
			if (falseTrans.get(i))
				cs.states[falseS] = EventState.add(cs.states[falseS],
						new EventState(i, falseS));
		for (int i = 0; i < trueTrans.size(); ++i)
			if (trueTrans.get(i))
				cs.states[trueS] = EventState.add(cs.states[trueS],
						new EventState(i, trueS));
		for (int i = 0; i < nps[m].size(); ++i)
			if (nps[m].get(i))
				cs.states[falseS] = EventState.add(cs.states[falseS],
						new EventState(i + 1, falseS));
		for (int i = 0; i < ps[m].size(); ++i)
			if (ps[m].get(i))
				cs.states[trueS] = EventState.add(cs.states[trueS],
						new EventState(i + 1, trueS));
		return cs;
	}

	CompactState makeSyncProcess() {
		final CompactState cs = new CompactState();
		cs.name = "SYNC";
		cs.maxStates = 2;
		cs.states = new EventState[cs.maxStates];
		final String[] propA = makeAlphabet();
		cs.alphabet = new String[propA.length - 1 + allActions.size()];
		cs.alphabet[0] = "tau";
		for (int i = 1; i < (propA.length - 1); ++i) {
			cs.alphabet[i] = propA[i];
			cs.states[1] = EventState.add(cs.states[1], new EventState(i, 0));
		}
		final Iterator<String> it = allActions.iterator();
		for (int i = 0; i < allActions.size(); ++i) {
			cs.alphabet[i + propA.length - 1] = it.next();
			cs.states[0] = EventState.add(cs.states[0], new EventState(i
					+ propA.length - 1, 1));
		}
		return cs;
	}

}