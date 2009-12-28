package ltsa.lts;

import java.io.*;
import java.util.*;

import ltsa.lts.Declaration.*;
import ltsa.lts.ltl.*;

public class LTSCompiler {

	private final Lex lex;
	private final LTSOutput output;
	private final String currentDirectory;
	private Symbol current;

	static Hashtable<String, ProcessSpec> processes;
	static Hashtable<String, CompactState> compiled;
	static Hashtable<String, CompositionExpression> composites;

	public LTSCompiler(LTSInput input, LTSOutput output, String currentDirectory) {
		lex = new Lex(input);
		this.output = output;
		this.currentDirectory = currentDirectory;
		Diagnostics.init(output);
		SeqProcessRef.output = output;
		StateMachine.output = output;
		Expression.constants = new Hashtable<String, Value>();
		Range.ranges = new Hashtable<String, Range>();
		LabelSet.constants = new Hashtable<String, LabelSet>();
		ProgressDefinition.definitions = new Hashtable<String, ProgressDefinition>();
		MenuDefinition.definitions = new Hashtable<String, MenuDefinition>();
		PredicateDefinition.init();
		AssertDefinition.init();
		DcltlDefinition.init();
	}

	private Symbol next_symbol() {
		return (current = lex.next_symbol());
	}

	private void push_symbol() {
		lex.push_symbol();
	}

	private void error(String errorMsg) {
		Diagnostics.fatal(errorMsg, current);
	}

	private void current_is(int kind, String errorMsg) {
		if (current.kind != kind)
			error(errorMsg);
	}

	public CompositeState compile(String name) {
		processes = new Hashtable<String, ProcessSpec>(); // processes
		composites = new Hashtable<String, CompositionExpression>(); // composites
		compiled = new Hashtable<String, CompactState>(); // compiled
		doparse(composites, processes, compiled);
		ProgressDefinition.compile();
		MenuDefinition.compile();
		PredicateDefinition.compileAll();
		AssertDefinition.compileAll(output);
		CompositionExpression ce = composites.get(name);
		if (ce == null && composites.size() > 0) {
			final Enumeration<CompositionExpression> e = composites.elements();
			ce = e.nextElement();
		}
		if (ce != null)
			return ce.compose(null);
		else {
			compileProcesses(processes, compiled);
			return noCompositionExpression(compiled);
		}
	}

	// put compiled definitions in Hashtable compiled
	private void compileProcesses(Hashtable<String, ProcessSpec> h,
			Hashtable<String, CompactState> compiled) {
		final Enumeration<ProcessSpec> e = h.elements();
		while (e.hasMoreElements()) {
			final ProcessSpec p = e.nextElement();
			if (!p.imported()) {
				final StateMachine one = new StateMachine(p);
				final CompactState c = one.makeCompactState();
				output.outln("Compiled: " + c.name);
				compiled.put(c.name, c);
			} else {
				final CompactState c = new AutCompactState(p.name, p.importFile);
				output.outln("Imported: " + c.name);
				compiled.put(c.name, c);
			}
		}
		AssertDefinition.compileConstraints(output, compiled);
	}

	public void parse(Hashtable<String, CompositionExpression> composites,
			Hashtable<String, ProcessSpec> processes) {
		doparse(composites, processes, null);
	}

	@SuppressWarnings("fallthrough")
	private void doparse(Hashtable<String, CompositionExpression> composites,
			Hashtable<String, ProcessSpec> processes,
			Hashtable<String, CompactState> compiled) {
		next_symbol();
		ProcessSpec p;
		Symbol last = null;
		while (current.kind != Symbol.EOFSYM) {
			switch (current.kind) {
			case Symbol.CONSTANT:
				next_symbol();
				constantDefinition(Expression.constants);
				break;
			case Symbol.RANGE:
				next_symbol();
				rangeDefinition();
				break;
			case Symbol.SET:
				next_symbol();
				setDefinition();
				break;
			case Symbol.PROGRESS:
				next_symbol();
				progressDefinition();
				break;
			case Symbol.MENU:
				next_symbol();
				menuDefinition();
				break;
			case Symbol.ANIMATION:
				next_symbol();
				animationDefinition();
				break;
			case Symbol.ASSERT:
				next_symbol();
				assertDefinition(false);
				break;
			case Symbol.DCLTL: // NEW (DS)
				next_symbol();
				dcltlDefinition();
				break;
			case Symbol.CONSTRAINT:
				next_symbol();
				assertDefinition(true);
				break;
			case Symbol.PREDICATE:
				next_symbol();
				predicateDefinition();
				break;
			case Symbol.IMPORT:
				next_symbol();
				p = importDefinition();
				if (processes.put(p.name.toString(), p) != null) {
					Diagnostics.fatal(
							"duplicate process definition: " + p.name, p.name);
				}
				break;
			case Symbol.DETERMINISTIC:
			case Symbol.MINIMAL:
			case Symbol.PROPERTY:
			case Symbol.COMPOSE:
				last = current;
				next_symbol();
				// fallthrough
			case Symbol.OR:
				if (current.kind != Symbol.OR) {
					p = stateDefns(last == null ? 0 : last.kind);
					if (processes.put(p.name.toString(), p) != null) {
						Diagnostics.fatal("duplicate process definition: "
								+ p.name, p.name);
					}
				} else {
					final CompositionExpression c = composition(composites,
							processes, compiled, output, last == null ? 0
									: last.kind);
					if (composites.put(c.name.toString(), c) != null) {
						Diagnostics.fatal("duplicate composite definition: "
								+ c.name, c.name);
					}
				}
				break;
			default:
				p = stateDefns(0);
				if (processes.put(p.name.toString(), p) != null) {
					Diagnostics.fatal(
							"duplicate process definition: " + p.name, p.name);
				}
				break;
			}

			next_symbol();
			last = null;
		}
	}

	private CompositeState noCompositionExpression(
			Hashtable<String, CompactState> h) {
		final Vector<CompactState> v = new Vector<CompactState>(16);
		final Enumeration<CompactState> e = h.elements();
		while (e.hasMoreElements()) {
			v.addElement(e.nextElement());
		}
		return new CompositeState(v);
	}

	private CompositionExpression composition(
			Hashtable<String, CompositionExpression> composites2,
			Hashtable<String, ProcessSpec> processes2,
			Hashtable<String, CompactState> compiled2, LTSOutput output2,
			int kind) {
		current_is(Symbol.OR, "'||' expected");
		next_symbol();
		final CompositionExpression c = new CompositionExpression(composites2,
				processes2, compiled2, output2, kind, current);
		current_is(Symbol.UPPERIDENT, "process identifier expected");
		next_symbol();
		paramDefns(c.init_constants, c.parameters);
		current_is(Symbol.BECOMES, "'=' expected");
		next_symbol();
		c.body = compositebody();
		c.priorityActions = priorityDefn(c);
		if (current.kind == Symbol.BACKSLASH || current.kind == Symbol.AT) {
			c.exposeNotHide = (current.kind == Symbol.AT);
			next_symbol();
			c.alphaHidden = labelSet();
		}
		current_is(Symbol.DOT, "'.' expected");
		return c;
	}

	private CompositeBody compositebody() {
		final CompositeBody b = new CompositeBody();
		if (current.kind == Symbol.IF) {
			next_symbol();
			b.boolexpr = new Stack<Symbol>();
			expression(b.boolexpr);
			current_is(Symbol.THEN, "keyword then expected");
			next_symbol();
			b.thenpart = compositebody();
			if (current.kind == Symbol.ELSE) {
				next_symbol();
				b.elsepart = compositebody();
			}
		} else if (current.kind == Symbol.FORALL) {
			next_symbol();
			b.range = forallRanges();
			b.thenpart = compositebody();
		} else {
			// get accessors if any
			if (isLabel()) {
				final ActionLabels el = labelElement();
				if (current.kind == Symbol.COLON_COLON) {
					b.accessSet = el;
					next_symbol();
					if (isLabel()) {
						b.prefix = labelElement();
						current_is(Symbol.COLON, "':' expected");
						next_symbol();
					}
				} else if (current.kind == Symbol.COLON) {
					b.prefix = el;
					next_symbol();
				} else
					error("':' or '::' expected");
			}
			// TODO what's this??
			if (current.kind == Symbol.LROUND) {
				b.procRefs = processRefs();
				b.relabelDefns = relabelDefns();
			} else {
				b.singleton = processRef();
				b.relabelDefns = relabelDefns();
			}
		}
		return b;
	}

	private ActionLabels forallRanges() {
		current_is(Symbol.LSQUARE, "range expected");
		next_symbol();
		final ActionLabels head = range();
		current_is(Symbol.RSQUARE, "']' expected");
		next_symbol();
		ActionLabels next = head;
		while (current.kind == Symbol.LSQUARE) {
			next_symbol();
			final ActionLabels t = range();
			current_is(Symbol.RSQUARE, "']' expected");
			next_symbol();
			next.addFollower(t);
			next = t;
		}
		return head;
	}

	private Vector<CompositeBody> processRefs() {
		final Vector<CompositeBody> procRefs = new Vector<CompositeBody>();
		current_is(Symbol.LROUND, "'(' expected");
		next_symbol();
		if (current.kind != Symbol.RROUND) {
			procRefs.addElement(compositebody());
			while (current.kind == Symbol.OR) {
				next_symbol();
				procRefs.addElement(compositebody());
			}
			current_is(Symbol.RROUND, "')' expected");
		}
		next_symbol();
		return procRefs;
	}

	private Vector<RelabelDefn> relabelDefns() {
		if (current.kind != Symbol.DIVIDE)
			return null;
		next_symbol();
		return relabelSet();
	}

	private LabelSet priorityDefn(CompositionExpression c) {
		if (current.kind != Symbol.SHIFT_RIGHT
				&& current.kind != Symbol.SHIFT_LEFT)
			return null;
		if (current.kind == Symbol.SHIFT_LEFT)
			c.priorityIsLow = false;
		next_symbol();
		return labelSet();
	}

	private Vector<RelabelDefn> relabelSet() {
		current_is(Symbol.LCURLY, "'{' expected");
		next_symbol();
		final Vector<RelabelDefn> v = new Vector<RelabelDefn>();
		v.addElement(relabelDefn());
		while (current.kind == Symbol.COMMA) {
			next_symbol();
			v.addElement(relabelDefn());
		}
		current_is(Symbol.RCURLY, "'}' expected");
		next_symbol();
		return v;
	}

	private RelabelDefn relabelDefn() {
		final RelabelDefn r = new RelabelDefn();
		if (current.kind == Symbol.FORALL) {
			next_symbol();
			r.range = forallRanges();
			r.defns = relabelSet();
		} else {
			r.newlabel = labelElement();
			current_is(Symbol.DIVIDE, "'/' expected");
			next_symbol();
			r.oldlabel = labelElement();
		}
		return r;
	}

	private ProcessRef processRef() {
		final ProcessRef p = new ProcessRef();
		current_is(Symbol.UPPERIDENT, "process identifier expected");
		p.name = current;
		next_symbol();
		p.actualParams = actualParameters();
		return p;
	}

	private Vector<Stack<Symbol>> actualParameters() {
		if (current.kind != Symbol.LROUND)
			return null;
		final Vector<Stack<Symbol>> v = new Vector<Stack<Symbol>>(4);
		next_symbol();
		Stack<Symbol> stk = new Stack<Symbol>();
		expression(stk);
		v.addElement(stk);
		while (current.kind == Symbol.COMMA) {
			next_symbol();
			stk = new Stack<Symbol>();
			expression(stk);
			v.addElement(stk);
		}
		current_is(Symbol.RROUND, "')' expected");
		next_symbol();
		return v;
	}

	private ProcessSpec stateDefns(int kind) {
		final ProcessSpec process = new ProcessSpec(kind);
		current_is(Symbol.UPPERIDENT, "process identifier expected");
		final Symbol temp = current;
		next_symbol();
		paramDefns(process.init_constants, process.parameters);
		push_symbol();
		current = temp;
		process.stateDefns.addElement(stateDefn());
		while (current.kind == Symbol.COMMA) {
			next_symbol();
			process.stateDefns.addElement(stateDefn());
		}
		if (current.kind == Symbol.PLUS) {
			next_symbol();
			process.alphaAdditions = labelSet();
		}
		process.alphaRelabel = relabelDefns();
		if (current.kind == Symbol.BACKSLASH || current.kind == Symbol.AT) {
			process.exposeNotHide = (current.kind == Symbol.AT);
			next_symbol();
			process.alphaHidden = labelSet();
		}
		process.getname();
		current_is(Symbol.DOT, "'.' expected");
		return process;
	}

	private boolean isLabelSet() {
		if (current.kind == Symbol.LCURLY)
			return true;
		if (current.kind != Symbol.UPPERIDENT)
			return false;
		return LabelSet.constants.containsKey(current.toString());
	}

	private boolean isLabel() {
		return (isLabelSet() || current.kind == Symbol.IDENTIFIER || current.kind == Symbol.LSQUARE);
	}

	private ProcessSpec importDefinition() {
		current_is(Symbol.UPPERIDENT, "imported process identifier expected");
		final ProcessSpec p = new ProcessSpec();
		p.name = current;
		next_symbol();
		current_is(Symbol.BECOMES, "'=' expected");
		next_symbol();
		current_is(Symbol.STRING_VALUE, " - imported file name expected");
		p.importFile = new File(currentDirectory, current.toString());
		return p;
	}

	private void animationDefinition() {
		current_is(Symbol.UPPERIDENT, "animation identifier expected");
		final MenuDefinition m = new MenuDefinition();
		m.name = current;
		next_symbol();
		current_is(Symbol.BECOMES, "'=' expected");
		next_symbol();
		current_is(Symbol.STRING_VALUE, " - XML file name expected");
		m.params = current;
		next_symbol();
		if (current.kind == Symbol.TARGET) {
			next_symbol();
			current_is(Symbol.UPPERIDENT, " - target composition name expected");
			m.target = current;
			next_symbol();
		}
		if (current.kind == Symbol.COMPOSE) {
			next_symbol();
			current_is(Symbol.LCURLY, "'{' expected");
			next_symbol();
			current_is(Symbol.UPPERIDENT, "animation name expected");
			Symbol name = current;
			next_symbol();
			m.addAnimationPart(name, relabelDefns());
			while (current.kind == Symbol.OR) {
				next_symbol();
				current_is(Symbol.UPPERIDENT, "animation name expected");
				name = current;
				next_symbol();
				m.addAnimationPart(name, relabelDefns());
			}
			current_is(Symbol.RCURLY, "'}' expected");
			next_symbol();
		}
		if (current.kind == Symbol.ACTIONS) {
			next_symbol();
			m.actionMapDefn = relabelSet();
		}
		if (current.kind == Symbol.CONTROLS) {
			next_symbol();
			m.controlMapDefn = relabelSet();
		}
		push_symbol();
		if (MenuDefinition.definitions.put(m.name.toString(), m) != null) {
			Diagnostics.fatal("duplicate menu/animation definition: " + m.name,
					m.name);
		}
	}

	private void menuDefinition() {
		current_is(Symbol.UPPERIDENT, "menu identifier expected");
		final MenuDefinition m = new MenuDefinition();
		m.name = current;
		next_symbol();
		current_is(Symbol.BECOMES, "'=' expected");
		next_symbol();
		m.actions = labelElement();
		push_symbol();
		if (MenuDefinition.definitions.put(m.name.toString(), m) != null) {
			Diagnostics.fatal("duplicate menu/animation definition: " + m.name,
					m.name);
		}
	}

	private void progressDefinition() {
		current_is(Symbol.UPPERIDENT, "progress test identifier expected");
		final ProgressDefinition p = new ProgressDefinition();
		p.name = current;
		next_symbol();
		if (current.kind == Symbol.LSQUARE)
			p.range = forallRanges();
		current_is(Symbol.BECOMES, "'=' expected");
		next_symbol();
		if (current.kind == Symbol.IF) {
			next_symbol();
			p.pactions = labelElement();
			current_is(Symbol.THEN, "'then' expected");
			next_symbol();
			p.cactions = labelElement();
		} else {
			p.pactions = labelElement();
		}
		if (ProgressDefinition.definitions.put(p.name.toString(), p) != null) {
			Diagnostics.fatal("duplicate progress test: " + p.name, p.name);
		}
		push_symbol();
	}

	private void setDefinition() {
		current_is(Symbol.UPPERIDENT, "set identifier expected");
		final Symbol temp = current;
		next_symbol();
		current_is(Symbol.BECOMES, "'=' expected");
		next_symbol();
		new LabelSet(temp, setValue());
		push_symbol();
	}

	private LabelSet labelSet() {
		if (current.kind == Symbol.LCURLY)
			return new LabelSet(setValue());
		else if (current.kind == Symbol.UPPERIDENT) {
			final LabelSet ls = LabelSet.constants.get(current.toString());
			if (ls == null)
				error("set definition not found for: " + current);
			next_symbol();
			return ls;
		} else {
			error("'{' or set identifier expected");
			return null;
		}
	}

	private Vector<ActionLabels> setValue() {
		current_is(Symbol.LCURLY, "'{' expected");
		next_symbol();
		final Vector<ActionLabels> v = new Vector<ActionLabels>();
		v.addElement(labelElement());
		while (current.kind == Symbol.COMMA) {
			next_symbol();
			v.addElement(labelElement());
		}
		current_is(Symbol.RCURLY, "'}' expected");
		next_symbol();
		return v;
	}

	private ActionLabels labelElement() {
		return labelElement(false);
	}

	private ActionLabels labelElement(final boolean inBrackets) {
		ActionLabels e = null;
		boolean nowInBrackets = false;
		if (inBrackets || current.kind == Symbol.LSQUARE) {
			if (!inBrackets)
				next_symbol();
			e = range();
			if (current.kind == Symbol.RSQUARE)
				next_symbol();
			else if (current.kind == Symbol.COMMA) {
				next_symbol();
				nowInBrackets = true;
			} else
				error("']' or ',' expected");
		} else if (current.kind == Symbol.IDENTIFIER) {
			if ("tau".equals(current.toString()))
				error("'tau' cannot be used as an action label");
			e = new ActionName(current);
			next_symbol();
		} else if (isLabelSet()) {
			final LabelSet left = labelSet();
			if (current.kind == Symbol.BACKSLASH) {
				next_symbol();
				final LabelSet right = labelSet();
				e = new ActionSetExpr(left, right);
			} else {
				e = new ActionSet(left);
			}
		} else {
			error("identifier, label set or range expected");
		}

		if (e == null)
			return e;

		if (nowInBrackets || current.kind == Symbol.DOT
				|| current.kind == Symbol.LSQUARE) {
			if (!nowInBrackets && current.kind == Symbol.DOT)
				next_symbol();
			e.addFollower(labelElement(nowInBrackets));
		}
		return e;
	}

	private void constantDefinition(Hashtable<String, Value> p) {
		current_is(Symbol.UPPERIDENT,
				"constant, upper case identifier expected");
		final Symbol name = current;
		next_symbol();
		current_is(Symbol.BECOMES, "= expected");
		next_symbol();
		final Stack<Symbol> tmp = new Stack<Symbol>();
		simpleExpression(tmp);
		push_symbol();
		if (p.put(name.toString(), Expression.getValue(tmp, null, null)) != null) {
			Diagnostics.fatal("duplicate constant definition: " + name, name);
		}
	}

	private void paramDefns(Hashtable<String, Value> p,
			Vector<String> parameters) {
		if (current.kind == Symbol.LROUND) {
			next_symbol();
			parameterDefinition(p, parameters);
			while (current.kind == Symbol.COMMA) {
				next_symbol();
				parameterDefinition(p, parameters);
			}
			current_is(Symbol.RROUND, "')' expected");
			next_symbol();
		}
	}

	private void parameterDefinition(Hashtable<String, Value> p,
			Vector<String> parameters) {
		current_is(Symbol.UPPERIDENT,
				"parameter or upper case identifier expected");
		final Symbol name = current;
		next_symbol();
		current_is(Symbol.BECOMES, "'=' expected");
		next_symbol();
		final Stack<Symbol> tmp = new Stack<Symbol>();
		expression(tmp);
		push_symbol();
		if (p.put(name.toString(), Expression.getValue(tmp, null, null)) != null) {
			Diagnostics.fatal("duplicate parameter definition: " + name, name);
		}
		if (parameters != null) {
			parameters.addElement(name.toString());
			next_symbol();
		}
	}

	private StateDefn stateDefn() {
		final StateDefn s = new StateDefn();
		current_is(Symbol.UPPERIDENT, "process identifier expected");
		s.name = current;
		next_symbol();
		if (current.kind == Symbol.AT) {
			s.accept = true;
			next_symbol();
		}
		if (current.kind == Symbol.DOT || current.kind == Symbol.LSQUARE) {
			if (current.kind == Symbol.DOT)
				next_symbol();
			s.range = labelElement();
		}
		current_is(Symbol.BECOMES, "'=' expected");
		next_symbol();
		s.stateExpr = stateExpr();
		return s;
	}

	private Stack<Symbol> getEvaluatedExpression() {
		Stack<Symbol> tmp = new Stack<Symbol>();
		simpleExpression(tmp);
		final int v = Expression.evaluate(tmp, null, null);
		tmp = new Stack<Symbol>();
		tmp.push(new Symbol(Symbol.INT_VALUE, v));
		return tmp;
	}

	private void rangeDefinition() {
		current_is(Symbol.UPPERIDENT,
				"range name, upper case identifier expected");
		final Symbol name = current;
		next_symbol();
		current_is(Symbol.BECOMES, "'=' expected");
		next_symbol();
		final Range r = new Range();
		r.low = getEvaluatedExpression();
		current_is(Symbol.DOT_DOT, "'..' expected");
		next_symbol();
		r.high = getEvaluatedExpression();
		if (Range.ranges.put(name.toString(), r) != null) {
			Diagnostics.fatal("duplicate range definition: " + name, name);
		}
		push_symbol();
	}

	private ActionLabels range() { // this is a mess.. needs to be rewritten
		ActionLabels r;
		Stack<Symbol> low = null;
		Stack<Symbol> high = null;
		if (current.kind != Symbol.IDENTIFIER) {
			if (isLabelSet()) {
				r = new ActionSet(labelSet());
			} else if (current.kind == Symbol.UPPERIDENT
					&& Range.ranges.containsKey(current.toString())) {
				r = new ActionRange(Range.ranges.get(current.toString()));
				next_symbol();
			} else {
				low = new Stack<Symbol>();
				expression(low);
				r = new ActionExpr(low);
			}
			if (current.kind == Symbol.DOT_DOT) {
				next_symbol();
				high = new Stack<Symbol>();
				expression(high);
				r = new ActionRange(low, high);
			}
		} else {
			final Symbol varname = current;
			next_symbol();
			if (current.kind == Symbol.COLON) {
				next_symbol();
				if (isLabelSet()) {
					r = new ActionVarSet(varname, labelSet());
				} else if (current.kind == Symbol.UPPERIDENT
						&& Range.ranges.containsKey(current.toString())) {
					r = new ActionVarRange(varname, Range.ranges.get(current
							.toString()));
					next_symbol();
				} else {
					low = new Stack<Symbol>();
					expression(low);
					current_is(Symbol.DOT_DOT, "'..' expected");
					next_symbol();
					high = new Stack<Symbol>();
					expression(high);
					r = new ActionVarRange(varname, low, high);
				}
			} else {
				push_symbol();
				current = varname;
				low = new Stack<Symbol>();
				expression(low);
				if (current.kind == Symbol.DOT_DOT) {
					next_symbol();
					high = new Stack<Symbol>();
					expression(high);
					r = new ActionRange(low, high);
				} else
					r = new ActionExpr(low);
			}
		}
		return r;
	}

	private StateExpr stateExpr() {
		return stateExpr(false);
	}

	private StateExpr stateExpr(boolean inChoice) {
		StateExpr s = new StateExpr();
		if (current.kind == Symbol.UPPERIDENT) {
			stateRef(s);
		} else if (current.kind == Symbol.IF) {
			next_symbol();
			s.boolexpr = new Stack<Symbol>();
			expression(s.boolexpr);
			current_is(Symbol.THEN, "keyword 'then' expected");
			next_symbol();
			s.thenpart = stateExpr();
			if (current.kind == Symbol.ELSE) {
				next_symbol();
				s.elsepart = stateExpr();
			} else {
				final Symbol stop = new Symbol(Symbol.UPPERIDENT, "STOP");
				final StateExpr se = new StateExpr();
				se.name = stop;
				s.elsepart = se;
			}
		} else if (current.kind == Symbol.LROUND) {
			next_symbol();
			s = stateExpr(false);
			current_is(Symbol.RROUND, "')' expected");
			next_symbol();
		} else if (current.kind == Symbol.IDENTIFIER
				|| current.kind == Symbol.LSQUARE
				|| current.kind == Symbol.WHEN || isLabelSet()) {
			s.choices = new Vector<ChoiceElement>(2);
			s.choices.add(choiceElement());
		} else
			error("'(', 'if', identifier, 'when', label set or range expected");

		while (!inChoice && current.kind == Symbol.BITWISE_OR
				&& s.choices != null) {
			/*
			 * if (s.choices == null) error("'|' not allowed here");
			 */
			next_symbol();
			s.choices.add(choiceElement());
		}

		return s;
	}

	private void stateRef(StateExpr s) {
		current_is(Symbol.UPPERIDENT, "process identifier expected");
		s.name = current;
		next_symbol();
		while (current.kind == Symbol.SEMICOLON
				|| current.kind == Symbol.LROUND) {
			s.addSeqProcessRef(new SeqProcessRef(s.name, actualParameters()));
			// TODO check current.kind (dot? semicolon?)
			next_symbol();
			current_is(Symbol.UPPERIDENT, "process identifier expected");
			s.name = current;
			next_symbol();
		}
		if (current.kind == Symbol.LSQUARE) {
			next_symbol();
			s.expr = new Vector<Stack<Symbol>>(4);
			while (true) {
				final Stack<Symbol> x = new Stack<Symbol>();
				expression(x);
				s.expr.addElement(x);
				if (current.kind == Symbol.RSQUARE) {
					next_symbol();
					if (current.kind != Symbol.LSQUARE)
						break;
				} else if (current.kind != Symbol.COMMA) {
					error("']' or ',' expected");
				}
				next_symbol();
			}
		}
	}

	private ChoiceElement choiceElement() {
		if (current.kind == Symbol.LROUND) {
			next_symbol();
			final ChoiceElement choice = choiceElement();
			current_is(Symbol.RROUND, "')' expected");
			next_symbol();
			return choice;
		}
		Stack<Symbol> guard = null;
		if (current.kind == Symbol.WHEN) {
			next_symbol();
			guard = new Stack<Symbol>();
			expression(guard);
		}
		return unguardedChoiceElement(guard);
	}

	private ChoiceElement unguardedChoiceElement(final Stack<Symbol> guard) {
		if (current.kind == Symbol.LROUND) {
			next_symbol();
			final ChoiceElement choice = unguardedChoiceElement(guard);
			current_is(Symbol.RROUND, "')' expected");
			next_symbol();
			return choice;
		}
		final ChoiceElement choice = new ChoiceElement();
		choice.guard = guard;
		choice.action = labelElement();
		current_is(Symbol.ARROW, "'->' expected");
		next_symbol();
		choice.stateExpr = stateExpr(true);
		return choice;
	}

	// LABELCONSTANT -------------------------------

	private ActionLabels labelConstant() {
		next_symbol();
		final ActionLabels el = labelElement();
		if (el != null) {
			return el;
		} else
			error("label definition expected");
		return null;
	}

	// set selection @(set , expr)
	private void set_select(Stack<Symbol> expr) {
		final Symbol op = current;
		next_symbol();
		current_is(Symbol.LROUND, "'(' expected to start set index selection");
		final Symbol temp = current; // preserve marker
		temp.setAny(labelConstant());
		temp.kind = Symbol.LABELCONST;
		expr.push(temp);
		current_is(Symbol.COMMA, "',' expected before set index expression");
		next_symbol();
		expression(expr);
		current_is(Symbol.RROUND, "')' expected to end set index selection");
		next_symbol();
		expr.push(op);
	}

	// UNARY ---------------------------------
	private void unary(Stack<Symbol> expr) { // +, -, identifier,
		Symbol unary_operator;
		switch (current.kind) {
		case Symbol.PLUS:
			unary_operator = current;
			unary_operator.kind = Symbol.UNARY_PLUS;
			next_symbol();
			break;
		case Symbol.MINUS:
			unary_operator = current;
			unary_operator.kind = Symbol.UNARY_MINUS;
			next_symbol();
			break;
		case Symbol.PLING:
			unary_operator = current;
			next_symbol();
			break;

		default:
			unary_operator = null;
		}
		switch (current.kind) {
		case Symbol.IDENTIFIER:
		case Symbol.UPPERIDENT:
		case Symbol.INT_VALUE:
			expr.push(current);
			next_symbol();
			break;
		case Symbol.LROUND:
			next_symbol();
			expression(expr);
			current_is(Symbol.RROUND, "')' expected to end expression");
			next_symbol();
			break;
		case Symbol.HASH:
			unary_operator = new Symbol(current);
		case Symbol.QUOTE: // this is a labelConstant
			final Symbol temp = current; // preserve marker
			temp.setAny(labelConstant());
			temp.kind = Symbol.LABELCONST;
			expr.push(temp);
			break;
		case Symbol.AT:
			set_select(expr);
			break;
		default:
			error("syntax error in expression");
		}

		if (unary_operator != null)
			expr.push(unary_operator);
	}

	// MULTIPLICATIVE

	private void multiplicative(Stack<Symbol> expr) { // *, /, %
		unary(expr);
		while (current.kind == Symbol.STAR || current.kind == Symbol.DIVIDE
				|| current.kind == Symbol.MODULUS) {
			final Symbol op = current;
			next_symbol();
			unary(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// ADDITIVE

	private void additive(Stack<Symbol> expr) { // +, -
		multiplicative(expr);
		while (current.kind == Symbol.PLUS || current.kind == Symbol.MINUS) {
			final Symbol op = current;
			next_symbol();
			multiplicative(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// SHIFT

	private void shift(Stack<Symbol> expr) { // <<, >>
		additive(expr);
		while (current.kind == Symbol.SHIFT_LEFT
				|| current.kind == Symbol.SHIFT_RIGHT) {
			final Symbol op = current;
			next_symbol();
			additive(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// RELATIONAL

	private void relational(Stack<Symbol> expr) { // <, <=, >, >=
		shift(expr);
		while (current.kind == Symbol.LESS_THAN
				|| current.kind == Symbol.LESS_THAN_EQUAL
				|| current.kind == Symbol.GREATER_THAN
				|| current.kind == Symbol.GREATER_THAN_EQUAL) {
			final Symbol op = current;
			next_symbol();
			shift(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// EQUALITY

	private void equality(Stack<Symbol> expr) { // ==, !=
		relational(expr);
		while (current.kind == Symbol.EQUALS
				|| current.kind == Symbol.NOT_EQUAL) {
			final Symbol op = current;
			next_symbol();
			relational(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// AND

	private void and(Stack<Symbol> expr) { // &
		equality(expr);
		while (current.kind == Symbol.BITWISE_AND) {
			final Symbol op = current;
			next_symbol();
			equality(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// EXCLUSIVE_OR

	private void exclusive_or(Stack<Symbol> expr) { // ^
		and(expr);
		while (current.kind == Symbol.CIRCUMFLEX) {
			final Symbol op = current;
			next_symbol();
			and(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// INCLUSIVE_OR

	private void inclusive_or(Stack<Symbol> expr) { // |
		exclusive_or(expr);
		while (current.kind == Symbol.BITWISE_OR) {
			final Symbol op = current;
			next_symbol();
			exclusive_or(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// LOGICAL_AND

	private void logical_and(Stack<Symbol> expr) { // &&
		inclusive_or(expr);
		while (current.kind == Symbol.AND) {
			final Symbol op = current;
			next_symbol();
			inclusive_or(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// LOGICAL_OR

	private void logical_or(Stack<Symbol> expr) { // ||
		logical_and(expr);
		while (current.kind == Symbol.OR) {
			final Symbol op = current;
			next_symbol();
			logical_and(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// EXPRESSION

	private void expression(Stack<Symbol> expr) {
		logical_or(expr);
	}

	// this is used to avoid a syntax problem
	// when a parallel composition
	// follows a range or constant definition e.g.
	// const N = 3
	// ||S = (P || Q)
	private void simpleExpression(Stack<Symbol> expr) {
		additive(expr);
	}

	// _______________________________________________________________________________________
	// DID / CAN LTL FORMULA
	private void dcltlDefinition() {
		// TODO: implement
		current_is(Symbol.UPPERIDENT, "DCLTL property identifier expected");
		final Symbol name = current;
		String formula = "";
		next_symbol();
		current_is(Symbol.BECOMES, "'=' expected");

		next_symbol_mod();
		while (true) {
			if (current.kind == Symbol.ALWAYS
					|| current.kind == Symbol.EVENTUALLY
					|| current.kind == Symbol.OR || current.kind == Symbol.AND
					|| current.kind == Symbol.PLING
					|| current.kind == Symbol.LROUND
					|| current.kind == Symbol.RROUND
					|| current.kind == Symbol.ARROW
					|| current.kind == Symbol.UNTIL
					|| current.kind == Symbol.WEAKUNTIL
					|| current.kind == Symbol.NEXTTIME) {
				formula += current.toString();
			} else if (current.kind == Symbol.CAN || current.kind == Symbol.DID
					|| current.kind == Symbol.INITIAL
					|| current.kind == Symbol.DEADLOCK
					|| current.kind == Symbol.TRUE
					|| current.kind == Symbol.FALSE) {
				// Proposition
				push_symbol();
				formula += dcltlProposition();
			} else if (current.kind == Symbol.UFAIR
					|| current.kind == Symbol.SFAIR
					|| current.kind == Symbol.WFAIR) {
				// Fairness constraint
				formula += dcltlFairness(current.kind);
			} else
				break;

			next_symbol_mod();
		}
		push_symbol();

		// System.out.println("Parsed formula: " + formula);
		DcltlDefinition.put(name, formula);
	}

	private String dcltlProposition() {
		boolean start = true;
		String proposition = "";

		next_symbol_mod();
		while (true) {
			if (current.kind == Symbol.CAN || current.kind == Symbol.DID) { // "can(a)",
				// "did(a[2])",
				// ...
				proposition += "(";
				String didOrCan = current.toString();

				next_symbol();
				current_is(Symbol.LROUND, "'(' expected");

				// Parse the actions
				HashSet<String> actions = dcltlParseActions();
				for (String action : actions) {
					proposition += "\"" + didOrCan + "(";
					proposition += action;
					proposition += ")\"||";
				}
				proposition = proposition
						.substring(0, proposition.length() - 2); // cut last ||
				next_symbol();
				current_is(Symbol.RROUND, "')' expected");
				proposition += ")";
			} else if (current.kind == Symbol.INITIAL) { // "initial"
				proposition += "__init";
			} else if (current.kind == Symbol.DEADLOCK) { // "deadlock"
				proposition += "__deadlock";
			} else if (current.kind == Symbol.TRUE
					|| current.kind == Symbol.FALSE
					|| current.kind == Symbol.PLING
					|| current.kind == Symbol.LROUND
					|| current.kind == Symbol.RROUND) { // Other atoms,
				// negation, or
				// parentheses
				proposition += current.toString();
			} else if (!start
					&& (current.kind == Symbol.OR || current.kind == Symbol.AND || current.kind == Symbol.ARROW)) { // Logical
				// connectives
				// that
				// cannot
				// be
				// at
				// the
				// start
				// of
				// a
				// Proposition
				proposition += current.toString();
			} else
				break;

			start = false;
			next_symbol_mod();
		}
		push_symbol();

		return proposition;
	}

	// DS - new feature : parametric dcltl formulas
	private HashSet<String> dcltlExpandSet(HashSet<String> preSet,
			HashSet<String> postSet) {
		if (preSet.isEmpty())
			return postSet;

		if (postSet.isEmpty())
			return preSet;

		HashSet<String> expanded = new HashSet<String>();

		for (String post : postSet) {
			for (String pre : preSet) {
				expanded.add(pre + "." + post);
			}
		}

		return expanded;
	}

	private HashSet<String> dcltlExpandList(HashSet<String> whatSet,
			int indexFrom, int indexTo) {
		HashSet<String> expanded = new HashSet<String>();

		for (String what : whatSet) {
			for (int i = indexFrom; i <= indexTo; i++) {
				expanded.add(what + "." + i);
			}
		}

		return expanded;
	}

	private HashSet<String> dcltlParseActions() {
		HashSet<String> actions = new HashSet<String>();

		next_symbol();

		if (current.kind == Symbol.IDENTIFIER
				|| current.kind == Symbol.INT_VALUE) {
			String action = current.toString();
			actions.add(action);
			next_symbol();

			// currently we only support ranges like [3], [1..3], i.e. no FSP
			// ranges or consts
			if (current.kind == Symbol.LSQUARE) {
				int begin = 0;
				int end = 0;
				next_symbol();

				if (current.kind == Symbol.INT_VALUE) {
					begin = current.intValue();
				} else {
					error("Range expected");
				}
				next_symbol();
				if (current.kind != Symbol.RSQUARE) {
					current_is(Symbol.DOT_DOT, ".. expected");
					next_symbol();
					if (current.kind == Symbol.INT_VALUE) {
						end = current.intValue();
					} else {
						error("Range expected");
					}
					next_symbol();
					current_is(Symbol.RSQUARE, "] expected");
					actions = dcltlExpandList(actions, begin, end);
				} else {
					end = begin;
					actions = dcltlExpandList(actions, begin, end);
				}

			} else {
				push_symbol();
			}
		} else if (current.kind == Symbol.LCURLY) {
			while (true) {
				next_symbol();
				current_is(Symbol.IDENTIFIER, "Identifier expected");
				actions.add(current.toString());
				next_symbol();
				if (current.kind == Symbol.RCURLY) {
					break;
				} else {
					current_is(Symbol.COMMA,
							"Comma or closing bracket expected");
				}
			}
		} else {
			push_symbol();
			return actions;
		}

		next_symbol();
		if (current.kind == Symbol.DOT) {
			HashSet<String> rest = dcltlParseActions();
			actions = dcltlExpandSet(actions, rest);
		} else {
			push_symbol();
			return actions;
		}

		return actions;
	}

	private String dcltlAction() {
		String action = "";

		next_symbol();
		current_is(Symbol.IDENTIFIER, "Identifier expected");
		action += current.toString();

		// Optional: Parameters like [1] or .1
		while (true) {
			next_symbol();
			if (current.kind == Symbol.LSQUARE) {
				next_symbol();
				current_is(Symbol.INT_VALUE, "Integer expected");
				action += "." + current.toString();

				next_symbol();
				current_is(Symbol.RSQUARE, "']' expected");
			} else if (current.kind == Symbol.DOT) {
				next_symbol();
				if (current.kind == Symbol.IDENTIFIER) {
					push_symbol();
					action += "." + dcltlAction();
				} else {
					current_is(Symbol.INT_VALUE, "Integer expected");
					action += "." + current.toString();
				}
			} else {
				push_symbol();
				break;
			}
		}

		return action;
	}

	private String dcltlFairness(int kind) {
		final List<String> actions = new ArrayList<String>();

		// Read the set of actions
		next_symbol();
		current_is(Symbol.LCURLY, "'{' expected");
		while (true) {
			actions.add(dcltlAction());
			next_symbol();
			if (current.kind != Symbol.COMMA)
				break;
		}
		current_is(Symbol.RCURLY, "'}' expected");

		// Generate the fairness constraint
		if (actions.size() > 0) {
			final StringBuilder constraint = new StringBuilder("(");

			constraint.append(kind == Symbol.WFAIR ? "<>[](" : "[]<>(");
			for (final String action : actions) {
				constraint.append((kind == Symbol.UFAIR ? "\"did" : "\"can")
						+ "(" + action + ")\" || ");
			}
			constraint.delete(constraint.length() - 4, constraint.length());
			constraint.append(")");

			if (kind != Symbol.UFAIR) { // Strong & weak fairness: right-hand
				// side of the implication
				constraint.append(" -> []<>(");
				for (final String action : actions) {
					constraint.append("\"did(" + action + ")\" || ");
				}
				constraint.delete(constraint.length() - 4, constraint.length());
				constraint.append(")");
			}
			constraint.append(") -> ");

			return constraint.toString();
		} else
			return "";
	}

	// _______________________________________________________________________________________
	// LINEAR TEMPORAL LOGIC ASSERTIONS

	private void assertDefinition(boolean isConstraint) {
		current_is(Symbol.UPPERIDENT, "LTL property identifier expected");
		final Symbol name = current;
		LabelSet ls = null;
		next_symbol();
		final Hashtable<String, Value> initparams = new Hashtable<String, Value>();
		final Vector<String> params = new Vector<String>();
		paramDefns(initparams, params);
		current_is(Symbol.BECOMES, "'=' expected");
		next_symbol_mod();
		final FormulaSyntax f = ltl_unary();
		if (current.kind == Symbol.PLUS) {
			next_symbol();
			ls = labelSet();
		}
		push_symbol();
		if (processes != null && processes.get(name.toString()) != null
				|| composites != null
				&& composites.get(name.toString()) != null) {
			Diagnostics.fatal("name already defined  " + name, name);
		}
		AssertDefinition.put(name, f, ls, initparams, params, isConstraint);
	}

	// do not want X, U, W, can, did, initial, deadlock to be keywords outside
	// of LTL expressions
	private Symbol modify(Symbol s) {
		if (s.kind == Symbol.UPPERIDENT) {
			if (s.toString().equals("X")) {
				final Symbol nx = new Symbol(s);
				nx.kind = Symbol.NEXTTIME;
				return nx;
			} else if (s.toString().equals("U")) {
				final Symbol ut = new Symbol(s);
				ut.kind = Symbol.UNTIL;
				return ut;
			} else if (s.toString().equals("W")) {
				final Symbol wut = new Symbol(s);
				wut.kind = Symbol.WEAKUNTIL;
				return wut;
			}
		}

		if (s.kind == Symbol.IDENTIFIER) {
			if (s.toString().equals("can")) {
				final Symbol wut = new Symbol(s);
				wut.kind = Symbol.CAN;
				return wut;
			} else if (s.toString().equals("did")) {
				final Symbol wut = new Symbol(s);
				wut.kind = Symbol.DID;
				return wut;
			} else if (s.toString().equals("initial")) {
				final Symbol wut = new Symbol(s);
				wut.kind = Symbol.INITIAL;
				return wut;
			} else if (s.toString().equals("deadlock")) {
				final Symbol wut = new Symbol(s);
				wut.kind = Symbol.DEADLOCK;
				return wut;
			} else if (s.toString().equals("true")) {
				final Symbol wut = new Symbol(s);
				wut.kind = Symbol.TRUE;
				return wut;
			} else if (s.toString().equals("false")) {
				final Symbol wut = new Symbol(s);
				wut.kind = Symbol.FALSE;
				return wut;
			} else if (s.toString().equals("ufair")) {
				final Symbol wut = new Symbol(s);
				wut.kind = Symbol.UFAIR;
				return wut;
			} else if (s.toString().equals("sfair")) {
				final Symbol wut = new Symbol(s);
				wut.kind = Symbol.SFAIR;
				return wut;
			} else if (s.toString().equals("wfair")) {
				final Symbol wut = new Symbol(s);
				wut.kind = Symbol.WFAIR;
				return wut;
			}
		}

		return s;
	}

	private void next_symbol_mod() {
		next_symbol();
		current = modify(current);
	}

	// _______________________________________________________________________________________
	// LINEAR TEMPORAL LOGIC EXPRESSION

	private FormulaSyntax ltl_unary() { // !,<>,[]
		final Symbol op = current;
		switch (current.kind) {
		case Symbol.PLING:
		case Symbol.NEXTTIME:
		case Symbol.EVENTUALLY:
		case Symbol.ALWAYS:
			next_symbol_mod();
			return FormulaSyntax.make(null, op, ltl_unary());
		case Symbol.UPPERIDENT:
			next_symbol_mod();
			if (current.kind == Symbol.LSQUARE) {
				final ActionLabels range = forallRanges();
				current = modify(current);
				return FormulaSyntax.make(op, range);
			} else if (current.kind == Symbol.LROUND) {
				final Vector<Stack<Symbol>> actparams = actualParameters();
				return FormulaSyntax.makeW(op, actparams);
			} else {
				return FormulaSyntax.make(op);
			}
		case Symbol.LROUND:
			next_symbol_mod();
			final FormulaSyntax right = ltl_or();
			current_is(Symbol.RROUND, "')' expected to end LTL expression");
			next_symbol_mod();
			return right;
		case Symbol.IDENTIFIER:
		case Symbol.LSQUARE:
		case Symbol.LCURLY:
			final ActionLabels ts = labelElement();
			push_symbol();
			next_symbol_mod();
			return FormulaSyntax.make(ts);
		case Symbol.EXISTS:
			next_symbol_mod();
			ActionLabels ff = forallRanges();
			push_symbol();
			next_symbol_mod();
			return FormulaSyntax.make(new Symbol(Symbol.OR), ff, ltl_unary());
		case Symbol.FORALL:
			next_symbol_mod();
			ff = forallRanges();
			push_symbol();
			next_symbol_mod();
			return FormulaSyntax.make(new Symbol(Symbol.AND), ff, ltl_unary());
		case Symbol.RIGID:
			next_symbol_mod();
			final Stack<Symbol> tmp = new Stack<Symbol>();
			simpleExpression(tmp);
			push_symbol();
			next_symbol_mod();
			return FormulaSyntax.makeE(op, tmp);
		default:
			Diagnostics.fatal("syntax error in LTL expression", current);
		}
		return null;
	}

	// _______________________________________________________________________________________
	// LTL_AND

	private FormulaSyntax ltl_and() { // &
		FormulaSyntax left = ltl_unary();
		while (current.kind == Symbol.AND) {
			final Symbol op = current;
			next_symbol_mod();
			final FormulaSyntax right = ltl_unary();
			left = FormulaSyntax.make(left, op, right);
		}
		return left;
	}

	// _______________________________________________________________________________________
	// LTL_OR

	private FormulaSyntax ltl_or() { // |
		FormulaSyntax left = ltl_binary();
		while (current.kind == Symbol.OR) {
			final Symbol op = current;
			next_symbol_mod();
			final FormulaSyntax right = ltl_binary();
			left = FormulaSyntax.make(left, op, right);
		}
		return left;
	}

	// _______________________________________________________________________________________
	// LTS_BINARY

	private FormulaSyntax ltl_binary() { // until, ->
		FormulaSyntax left = ltl_and();
		if (current.kind == Symbol.UNTIL || current.kind == Symbol.WEAKUNTIL
				|| current.kind == Symbol.ARROW
				|| current.kind == Symbol.EQUIVALENT) {
			final Symbol op = current;
			next_symbol_mod();
			final FormulaSyntax right = ltl_and();
			left = FormulaSyntax.make(left, op, right);
		}
		return left;
	}

	//
	// ___________________________________________________________________________________
	// STATE PREDICATE DEFINITIONS

	private void predicateDefinition() {
		current_is(Symbol.UPPERIDENT, "predicate identifier expected");
		final Symbol name = current;
		ActionLabels range = null;
		next_symbol();
		if (current.kind == Symbol.LSQUARE)
			range = forallRanges();
		current_is(Symbol.BECOMES, "'=' expected");
		next_symbol();
		current_is(Symbol.LESS_THAN, "'<' expected");
		next_symbol();
		final ActionLabels ts = labelElement();
		current_is(Symbol.COMMA, "',' expected");
		next_symbol();
		final ActionLabels fs = labelElement();
		current_is(Symbol.GREATER_THAN, "'>' expected");
		next_symbol();
		if (current.kind == Symbol.INIT) {
			next_symbol();
			final Stack<Symbol> tmp = new Stack<Symbol>();
			simpleExpression(tmp);
			push_symbol();
			PredicateDefinition.put(name, range, ts, fs, tmp);
		} else {
			push_symbol();
			PredicateDefinition.put(name, range, ts, fs, null);
		}
	}

}
