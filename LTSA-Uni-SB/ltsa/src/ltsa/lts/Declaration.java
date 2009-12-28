package ltsa.lts;

import java.util.*;

public abstract class Declaration implements Cloneable {
	public final static int TAU = 0;
	public final static int ERROR = -1;
	public final static int STOP = 0;
	public final static int SUCCESS = 1;

	public void explicitStates(StateMachine m) {
		// nothing by default
	}

	// makes sure aliases refer to the same state
	public void crunch(StateMachine m) {
		// nothing by default
	}

	public void transition(StateMachine m) {
		// nothing by default
	}

	@Override
	protected Declaration clone() {
		try {
			return (Declaration) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(
					"Internal error: Declaration cannot be cloned: "
							+ e.getMessage(), e);
		}
	}

	/* ----------------------------------------------------------------------- */

	public static class StateDefn extends Declaration {
		Symbol name;
		boolean accept = false;
		ActionLabels range; // use label with no name
		StateExpr stateExpr;

		private void check_put(String s, StateMachine m) {
			if (m.explicit_states.containsKey(s))
				Diagnostics.fatal("duplicate definition -" + name, name);
			else
				m.explicit_states.put(s, m.stateLabel.label());
		}

		@Override
		public void explicitStates(StateMachine m) {
			if (range == null) {
				String s = name.toString();
				if (s.equals("STOP") || s.equals("ERROR") || s.equals("END"))
					Diagnostics.fatal("reserved local process name -" + name,
							name);
				check_put(s, m);
			} else {
				Hashtable<String, Value> locals = new Hashtable<String, Value>();
				range.initContext(locals, m.constants);
				while (range.hasMoreNames()) {
					check_put(name.toString() + "." + range.nextName(), m);
				}
				range.clearContext();
			}
		}

		private void crunchAlias(StateExpr st, String n,
				Hashtable<String, Value> locals, StateMachine m) {
			String s = st.evalName(locals, m);
			Integer i = m.explicit_states.get(s);
			if (i == null) {
				if (s.equals("STOP")) {
					m.explicit_states.put("STOP", i = m.stateLabel.label());
				} else if (s.equals("ERROR")) {
					m.explicit_states.put("ERROR", i = new Integer(
							Declaration.ERROR));
				} else if (s.equals("END")) {
					m.explicit_states.put("END", i = m.stateLabel.label());
				} else {
					m.explicit_states.put("ERROR", i = new Integer(
							Declaration.ERROR));
					Diagnostics.warning(s + " defined to be ERROR",
							"definition not found- " + s, st.name);
				}
			}
			CompactState mach = null;
			if (st.processes != null)
				mach = st.makeInserts(locals, m);
			if (mach != null)
				m.preAddSequential(m.explicit_states.get(n), i, mach);
			else
				m.aliases.put(m.explicit_states.get(n), i);
		}

		@Override
		public void crunch(StateMachine m) {
			if (stateExpr.name == null && stateExpr.boolexpr == null)
				return;
			Hashtable<String, Value> locals = new Hashtable<String, Value>();
			if (range == null)
				crunchit(m, locals, stateExpr, name.toString());
			else {
				range.initContext(locals, m.constants);
				while (range.hasMoreNames()) {
					String s = "" + name + "." + range.nextName();
					crunchit(m, locals, stateExpr, s);
				}
				range.clearContext();
			}
		}

		private void crunchit(StateMachine m, Hashtable<String, Value> locals,
				StateExpr st, String s) {
			if (st.name != null)
				crunchAlias(st, s, locals, m);
			else if (st.boolexpr != null) {
				if (Expression.evaluate(st.boolexpr, locals, m.constants) != 0)
					st = st.thenpart;
				else
					st = st.elsepart;
				if (st != null)
					crunchit(m, locals, st, s);
			}
		}

		@Override
		public void transition(StateMachine m) {
			int from;
			if (stateExpr.name != null)
				return; // this is an alias definition
			Hashtable<String, Value> locals = new Hashtable<String, Value>();
			if (range == null) {
				from = m.explicit_states.get("" + name).intValue();
				stateExpr.firstTransition(from, locals, m);
				if (accept) {
					if (!m.alphabet.containsKey("@"))
						m.alphabet.put("@", m.eventLabel.label());
					Symbol e = new Symbol(Symbol.IDENTIFIER, "@");
					m.transitions.addElement(new Transition(from, e, from));
				}
			} else {
				range.initContext(locals, m.constants);
				while (range.hasMoreNames()) {
					from = m.explicit_states.get(
							"" + name + "." + range.nextName()).intValue();
					stateExpr.firstTransition(from, locals, m);
				}
				range.clearContext();
			}
		}

		@Override
		public StateDefn clone() {
			StateDefn sd = (StateDefn) super.clone();
			if (range != null)
				sd.range = range.clone();
			if (stateExpr != null)
				sd.stateExpr = stateExpr.clone();
			return sd;
		}

	}

	/* ----------------------------------------------------------------------- */

	public static class SeqProcessRef {
		Symbol name;
		Vector<Stack<Symbol>> actualParams;

		static LTSOutput output;

		SeqProcessRef(Symbol n, Vector<Stack<Symbol>> params) {
			name = n;
			actualParams = params;
		}

		CompactState instantiate(Hashtable<String, Value> locals,
				Hashtable<String, Value> constants) {
			// compute parameters
			Vector<Value> actuals = paramValues(locals, constants);
			String refname = (actuals == null) ? name.toString() : name
					.toString()
					+ StateMachine.paramString(actuals);
			// have we already compiled it?
			CompactState mach = LTSCompiler.compiled.get(refname);
			if (mach == null) {
				// we have not got one so first see if its a defined process
				ProcessSpec p = LTSCompiler.processes.get(name.toString());
				if (p != null) {
					p = p.clone();
					if (actualParams != null) { // check that parameter arity is
						// correct
						if (actualParams.size() != p.parameters.size())
							Diagnostics.fatal(
									"actuals do not match formal parameters",
									name);
					}
					StateMachine one = new StateMachine(p, actuals);
					mach = one.makeCompactState();
					output.outln("-- compiled:" + mach.name);
				}
			}
			if (mach == null) {
				CompositionExpression ce = LTSCompiler.composites.get(name
						.toString());
				if (ce != null) {
					CompositeState cs = ce.compose(actuals);
					mach = cs.create(output);
				}
			}
			if (mach != null) {
				LTSCompiler.compiled.put(mach.name, mach); // add to compiled
				// processes
				if (!mach.isSequential())
					Diagnostics.fatal("process is not sequential - " + name,
							name);
				return mach.myclone();
			}
			Diagnostics.fatal("process definition not found- " + name, name);
			return null;
		}

		private Vector<Value> paramValues(Hashtable<String, Value> locals,
				Hashtable<String, Value> constants) {
			if (actualParams == null)
				return null;
			Enumeration<Stack<Symbol>> e = actualParams.elements();
			Vector<Value> v = new Vector<Value>();
			while (e.hasMoreElements()) {
				Stack<Symbol> stk = e.nextElement();
				v.addElement(Expression.getValue(stk, locals, constants));
			}
			return v;
		}

	}

	public static class StateExpr extends Declaration {
		// if name !=null then no choices
		Vector<SeqProcessRef> processes;
		Symbol name;
		Vector<Stack<Symbol>> expr; // vector of expressions stacks, one for
		// each subscript
		Vector<ChoiceElement> choices;
		Stack<Symbol> boolexpr;
		StateExpr thenpart;
		StateExpr elsepart;

		public void addSeqProcessRef(SeqProcessRef sp) {
			if (processes == null)
				processes = new Vector<SeqProcessRef>();
			processes.addElement(sp);
		}

		public CompactState makeInserts(Hashtable<String, Value> locals,
				StateMachine m) {
			Vector<CompactState> seqs = new Vector<CompactState>();
			Enumeration<SeqProcessRef> e = processes.elements();
			while (e.hasMoreElements()) {
				SeqProcessRef sp = e.nextElement();
				CompactState mach = sp.instantiate(locals, m.constants);
				if (!mach.isEnd())
					seqs.addElement(mach);
			}
			if (seqs.size() > 0)
				return CompactState.sequentialCompose(seqs);
			return null;
		}

		public Integer instantiate(Integer to, Hashtable<String, Value> locals,
				StateMachine m) {
			if (processes == null)
				return to;
			CompactState seqmach = makeInserts(locals, m);
			if (seqmach == null)
				return to;
			Integer start = m.stateLabel.interval(seqmach.maxStates);
			seqmach.offsetSeq(start.intValue(), to.intValue());
			m.addSequential(start, seqmach);
			return start;
		}

		public void firstTransition(int from, Hashtable<String, Value> locals,
				StateMachine m) {
			if (boolexpr != null) {
				if (Expression.evaluate(boolexpr, locals, m.constants) != 0) {
					if (thenpart.name == null)
						thenpart.firstTransition(from, locals, m);
				} else {
					if (elsepart.name == null)
						elsepart.firstTransition(from, locals, m);
				}
			} else
				addTransition(from, locals, m);
		}

		public void addTransition(int from, Hashtable<String, Value> locals,
				StateMachine m) {
			Enumeration<ChoiceElement> e = choices.elements();
			while (e.hasMoreElements()) {
				ChoiceElement d = e.nextElement();
				d.addTransition(from, locals, m);
			}
		}

		public void endTransition(int from, Symbol event,
				Hashtable<String, Value> locals, StateMachine m) {
			if (boolexpr != null) {
				if (Expression.evaluate(boolexpr, locals, m.constants) != 0)
					thenpart.endTransition(from, event, locals, m);
				else
					elsepart.endTransition(from, event, locals, m);
			} else {
				Integer to;
				if (name != null) {
					to = m.explicit_states.get(evalName(locals, m));
					if (to == null) {
						if (evalName(locals, m).equals("STOP")) {
							m.explicit_states.put("STOP", to = m.stateLabel
									.label());
						} else if (evalName(locals, m).equals("ERROR")) {
							m.explicit_states.put("ERROR", to = new Integer(
									Declaration.ERROR));
						} else if (evalName(locals, m).equals("END")) {
							m.explicit_states.put("END", to = m.stateLabel
									.label());
						} else {
							m.explicit_states.put(evalName(locals, m),
									to = new Integer(Declaration.ERROR));
							Diagnostics.warning(evalName(locals, m)
									+ " defined to be ERROR",
									"definition not found- "
											+ evalName(locals, m), name);
						}
					}
					to = instantiate(to, locals, m);
					m.transitions.addElement(new Transition(from, event, to
							.intValue()));
				} else {
					to = m.stateLabel.label();
					m.transitions.addElement(new Transition(from, event, to
							.intValue()));
					addTransition(to.intValue(), locals, m);
				}
			}
		}

		public String evalName(Hashtable<String, Value> locals, StateMachine m) {
			if (expr == null)
				return name.toString();
			else {
				Enumeration<Stack<Symbol>> e = expr.elements();
				String s = name.toString();
				while (e.hasMoreElements()) {
					Stack<Symbol> x = e.nextElement();
					s = s + "." + Expression.getValue(x, locals, m.constants);
				}
				return s;
			}
		}

		@Override
		protected StateExpr clone() {
			StateExpr clone = (StateExpr) super.clone();
			if (choices != null) {
				clone.choices = new Vector<ChoiceElement>(choices.size());
				for (ChoiceElement e : choices) {
					clone.choices.add(e.clone());
				}
			}
			if (thenpart != null)
				clone.thenpart = thenpart.clone();
			if (elsepart != null)
				clone.elsepart = elsepart.clone();

			return clone;
		}

	}

	/* ----------------------------------------------------------------------- */

	public static class ChoiceElement extends Declaration {
		Stack<Symbol> guard;
		ActionLabels action;
		StateExpr stateExpr;

		private void add(int from, Hashtable<String, Value> locals,
				StateMachine m, ActionLabels action) {
			action.initContext(locals, m.constants);
			while (action.hasMoreNames()) {
				String s = action.nextName();
				Symbol e = new Symbol(Symbol.IDENTIFIER, s);
				if (!m.alphabet.containsKey(s))
					m.alphabet.put(s, m.eventLabel.label());
				stateExpr.endTransition(from, e, locals, m);
			}
			action.clearContext();
		}

		public void addTransition(int from, Hashtable<String, Value> locals,
				StateMachine m) {
			if (guard == null
					|| Expression.evaluate(guard, locals, m.constants) != 0) {
				if (action != null) {
					add(from, locals, m, action);
				}
			}
		}

		@Override
		public ChoiceElement clone() {
			ChoiceElement clone = (ChoiceElement) super.clone();
			if (action != null)
				clone.action = action.clone();
			if (stateExpr != null)
				clone.stateExpr = stateExpr.clone();
			return clone;
		}

	}
}
