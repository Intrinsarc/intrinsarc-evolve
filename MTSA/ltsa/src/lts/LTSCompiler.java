package lts;

import java.io.*;
import java.util.*;

import lts.chart.*;
import lts.chart.util.*;
import lts.controller.*;
import lts.ltl.*;
import lts.util.*;

public class LTSCompiler {

	private Lex lex;
	private LTSOutput output;
	private String currentDirectory;
	private Symbol current;

	static Hashtable processes;
	static Hashtable compiled;
	static Hashtable composites;
	private int compositionType = -1;

	public LTSCompiler(LTSInput input, LTSOutput output, String currentDirectory) {
		lex = new Lex(input);
		this.output = output;
		this.currentDirectory = currentDirectory;
		Diagnostics.init(output);
		SeqProcessRef.output = output;
		StateMachine.output = output;
		Expression.constants = new Hashtable();
		Range.ranges = new Hashtable();
		LabelSet.constants = new Hashtable();
		ProgressDefinition.definitions = new Hashtable();
		MenuDefinition.definitions = new Hashtable();
		PredicateDefinition.init();
		AssertDefinition.init();
		TriggeredScenarioDefinition.init();
		ControllerDefinition.init();
		ControllerGoalDefinition.init();
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
		processes = new Hashtable(); // processes
		composites = new Hashtable(); // composites
		compiled = new Hashtable(); // compiled
		doparse(composites, processes, compiled);
		ProgressDefinition.compile();
		MenuDefinition.compile();
		PredicateDefinition.compileAll();
		AssertDefinition.compileAll(output);
		CompositionExpression ce = (CompositionExpression) composites.get(name);
		if (ce == null && composites.size() > 0) {
			Enumeration e = composites.elements();
			ce = (CompositionExpression) e.nextElement();
		}
		if (ce != null) {
			CompositeState compose = ce.compose(null);
			return compose;
		} else {
			try {
				// There is no composite expression. All scenarios are
				// synthesised
				this.addAllToCompiled(TriggeredScenarioDefinition
						.synthesiseAll(output));
			} catch (TriggeredScenarioTransformationException e) {
				throw new RuntimeException(e);
			}

			compileProcesses(processes, compiled);
			return noCompositionExpression(compiled);
		}
	}

	/**
	 * Add all the CompactState in compiledToBeAdded to the compiled table with
	 * the name of the CompactState as key.
	 * 
	 * @param compiledToBeAdded
	 */
	private void addAllToCompiled(Collection<CompactState> compiledToBeAdded) {
		for (CompactState compactState : compiledToBeAdded) {
			compiled.put(compactState.getName(), compactState);
		}
	}

	// put compiled definitions in Hashtable compiled
	private void compileProcesses(Hashtable h, Hashtable compiled) {
		Enumeration e = h.elements();
		while (e.hasMoreElements()) {
			ProcessSpec p = (ProcessSpec) e.nextElement();
			if (!p.imported()) {
				StateMachine one = new StateMachine(p);
				CompactState c = one.makeCompactState();
				output.outln("Compiled: " + c.name);
				compiled.put(c.name, c);
			} else {
				CompactState c = new AutCompactState(p.name, p.importFile);
				output.outln("Imported: " + c.name);
				compiled.put(c.name, c);
			}
		}
		AssertDefinition.compileConstraints(output, compiled);
	}

	public void parse(Hashtable composites, Hashtable processes) {
		doparse(composites, processes, null);
	}

	private void doparse(Hashtable composites, Hashtable processes,
			Hashtable compiled) {
		next_symbol();
		try {
			while (current.kind != Symbol.EOFSYM) {
				if (current.kind == Symbol.CONSTANT) {
					next_symbol();
					constantDefinition(Expression.constants);
				} else if (current.kind == Symbol.RANGE) {
					next_symbol();
					rangeDefinition();
				} else if (current.kind == Symbol.SET) {
					next_symbol();
					setDefinition();
				} else if (current.kind == Symbol.PROGRESS) {
					next_symbol();
					progressDefinition();
				} else if (current.kind == Symbol.MENU) {
					next_symbol();
					menuDefinition();
				} else if (current.kind == Symbol.ANIMATION) {
					next_symbol();
					animationDefinition();
				} else if (current.kind == Symbol.ASSERT) {
					next_symbol();
					assertDefinition(false, false);
				} else if (current.kind == Symbol.CONSTRAINT) {
					next_symbol();
					assertDefinition(true, false);
				} else if (current.kind == Symbol.LTLPROPERTY) {
					next_symbol();
					assertDefinition(true, true);
				} else if (current.kind == Symbol.PREDICATE) {
					next_symbol();
					predicateDefinition();
				} else if (current.kind == Symbol.GOAL) {
					next_symbol();

					current_is(Symbol.UPPERIDENT, "goal identifier expected");

					this.validateUniqueProcessName(current);
					ControllerGoalDefinition goal = new ControllerGoalDefinition(
							current);
					next_symbol();

					this.goalDefinition(goal);

				} else if (current.kind == Symbol.IMPORT) {
					next_symbol();
					ProcessSpec p = importDefinition();
					if (processes.put(p.name.toString(), p) != null) {
						Diagnostics.fatal("duplicate process definition: "
								+ p.name, p.name);
					}
				} else if (current.kind == Symbol.E_TRIGGERED_SCENARIO) {
					next_symbol();
					// Check the syntax
					current_is(Symbol.UPPERIDENT, "chart identifier expected");

					this.validateUniqueProcessName(current);

					// create the existential triggeredScenario with the given
					// identifier
					TriggeredScenarioDefinition eTSDefinition = new ExistentialTriggeredScenarioDefinition(
							current);

					next_symbol();
					this.triggeredScenarioDefinition(eTSDefinition);
				} else if (current.kind == Symbol.U_TRIGGERED_SCENARIO) {
					next_symbol();
					// Check the syntax
					current_is(Symbol.UPPERIDENT, "chart identifier expected");

					this.validateUniqueProcessName(current);

					// create the universal triggered Scenario with the given
					// identifier
					TriggeredScenarioDefinition uTSDefinition = new UniversalTriggeredScenarioDefinition(
							current);

					next_symbol();
					this.triggeredScenarioDefinition(uTSDefinition);
				} else if (current.kind == Symbol.DETERMINISTIC
						|| current.kind == Symbol.MINIMAL
						|| current.kind == Symbol.PROPERTY
						|| current.kind == Symbol.COMPOSE
						|| current.kind == Symbol.OPTIMISTIC
						|| current.kind == Symbol.PESSIMISTIC
						|| LTSUtils.isCompositionExpression(current)
						|| current.kind == Symbol.CLOUSURE
						|| current.kind == Symbol.ABSTRACT
						|| current.kind == Symbol.CONTROLLER) {
					boolean makeDet = false;
					boolean makeMin = false;
					boolean makeProp = false;
					boolean makeComp = false;
					boolean makeOptimistic = false;
					boolean makePessimistic = false;
					boolean makeClousure = false;
					boolean makeAbstract = false;
					boolean makeController = false;

					if (current.kind == Symbol.CLOUSURE) {
						makeClousure = true;
						next_symbol();
					}
					if (current.kind == Symbol.ABSTRACT) {
						makeAbstract = true;
						next_symbol();
					}
					if (current.kind == Symbol.DETERMINISTIC) {
						makeDet = true;
						next_symbol();
					}
					if (current.kind == Symbol.MINIMAL) {
						makeMin = true;
						next_symbol();
					}
					if (current.kind == Symbol.COMPOSE) {
						makeComp = true;
						next_symbol();
					}
					if (current.kind == Symbol.PROPERTY) {
						makeProp = true;
						next_symbol();
					}
					if (current.kind == Symbol.OPTIMISTIC) {
						makeOptimistic = true;
						next_symbol();
					}
					if (current.kind == Symbol.PESSIMISTIC) {
						makePessimistic = true;
						next_symbol();
					}
					if (current.kind == Symbol.CONTROLLER) {
						makeController = true;
						next_symbol();
					}
					if (current.kind != Symbol.OR
							&& current.kind != Symbol.PLUS_CA
							&& current.kind != Symbol.PLUS_CR
							&& current.kind != Symbol.MERGE) {
						ProcessSpec p = stateDefns();
						if (processes.put(p.name.toString(), p) != null) {
							Diagnostics.fatal("duplicate process definition: "
									+ p.name, p.name);
						}
						p.isProperty = makeProp;
						p.isMinimal = makeMin;
						p.isDeterministic = makeDet;
						p.isOptimistic = makeOptimistic;
						p.isPessimistic = makePessimistic;
						p.isClousure = makeClousure;
						p.isAbstract = makeAbstract;
						p.isController = makeController;

					} else if (LTSUtils.isCompositionExpression(current)) {
						CompositionExpression c = composition();
						c.composites = composites;
						c.processes = processes;
						c.compiledProcesses = compiled;
						c.output = output;
						c.makeDeterministic = makeDet;
						c.makeProperty = makeProp;
						c.makeMinimal = makeMin;
						c.makeDeterministic = makeDet;
						c.makeCompose = makeComp;
						c.makeOptimistic = makeOptimistic;
						c.makePessimistic = makePessimistic;
						c.makeClousure = makeClousure;
						c.makeAbstract = makeAbstract;
						c.makeController = makeController;
						c.compositionType = compositionType;
						compositionType = -1;

						if (composites.put(c.name.toString(), c) != null) {
							Diagnostics
									.fatal("duplicate composite definition: "
											+ c.name, c.name);
						}
					}
				} else {
					ProcessSpec p = stateDefns();
					if (processes.put(p.name.toString(), p) != null) {
						Diagnostics.fatal("duplicate process definition: "
								+ p.name, p.name);
					}
				}

				next_symbol();
			}
		} catch (DuplicatedTriggeredScenarioDefinitionException e) {
			Diagnostics.fatal("duplicate Chart definition: " + e.getName());
		}
	}

	private CompositeState noCompositionExpression(Hashtable h) {
		Vector v = new Vector(16);
		Enumeration e = h.elements();
		while (e.hasMoreElements()) {
			v.addElement(e.nextElement());
		}
		return new CompositeState(v);
	}

	private CompositionExpression composition() {
		current_is(Symbol.OR, "|| expected");
		next_symbol();
		CompositionExpression c = new CompositionExpression();
		current_is(Symbol.UPPERIDENT, "process identifier expected");
		c.name = current;
		next_symbol();
		paramDefns(c.init_constants, c.parameters);
		current_is(Symbol.BECOMES, "= expected");
		next_symbol();
		c.body = compositebody();
		c.priorityActions = priorityDefn(c);
		this.priorizeMaybeActions(c.priorityActions);
		if (current.kind == Symbol.BACKSLASH || current.kind == Symbol.AT) {
			c.exposeNotHide = (current.kind == Symbol.AT);
			next_symbol();
			c.alphaHidden = labelSet();
			Vector addLabels = new Vector();
			LabelSet alphaHidden = c.alphaHidden;
			this.hideMaybeActions(alphaHidden, addLabels);
		}

		// Controller Synthesis
		if (Symbol.SINE == current.kind) {
			parseControllerGoal(c);
		}

		current_is(Symbol.DOT, "dot expected");
		return c;
	}

	private CompositeBody compositebody() {
		CompositeBody b = new CompositeBody();
		if (current.kind == Symbol.IF) {
			next_symbol();
			b.boolexpr = new Stack();
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
				ActionLabels el = labelElement();
				if (current.kind == Symbol.COLON_COLON) {
					b.accessSet = el;
					next_symbol();
					if (isLabel()) {
						b.prefix = labelElement();
						current_is(Symbol.COLON, " : expected");
						next_symbol();
					}
				} else if (current.kind == Symbol.COLON) {
					b.prefix = el;
					next_symbol();
				} else
					error(" : or :: expected");
			}
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
		ActionLabels head = range();
		ActionLabels next = head;
		while (current.kind == Symbol.LSQUARE) {
			ActionLabels t = range();
			next.addFollower(t);
			next = t;
		}
		return head;
	}

	private Vector processRefs() {
		Vector procRefs = new Vector();
		current_is(Symbol.LROUND, "( expected");
		next_symbol();
		if (current.kind != Symbol.RROUND) {
			procRefs.addElement(compositebody());
			while (LTSUtils.isCompositionExpression(current)) {
				next_symbol();
				procRefs.addElement(compositebody());
			}
			current_is(Symbol.RROUND, ") expected");
		}
		next_symbol();
		return procRefs;
	}

	private Vector relabelDefns() {
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

	private Vector relabelSet() {
		current_is(Symbol.LCURLY, "{ expected");
		next_symbol();
		Vector v = new Vector();
		relabelBoth(v, relabelDefn());
		while (current.kind == Symbol.COMMA) {
			next_symbol();
			relabelBoth(v, relabelDefn());
		}
		current_is(Symbol.RCURLY, "} expected");
		next_symbol();
		return v;
	}

	private void relabelBoth(Vector v, RelabelDefn relabelDefn) {
		v.addElement(relabelDefn);
		this.relabelMTS(v, relabelDefn);
	}

	private RelabelDefn relabelDefn() {
		RelabelDefn r = new RelabelDefn();
		if (current.kind == Symbol.FORALL) {
			next_symbol();
			r.range = forallRanges();
			r.defns = relabelSet();
		} else {
			r.newlabel = labelElement();
			current_is(Symbol.DIVIDE, "/ expected");
			next_symbol();
			r.oldlabel = labelElement();
		}
		return r;
	}

	private ProcessRef processRef() {
		ProcessRef p = new ProcessRef();
		current_is(Symbol.UPPERIDENT, "process identifier expected");
		p.name = current;
		next_symbol();
		p.actualParams = actualParameters();
		if (current.kind != Symbol.RROUND) {
			compositionType = current.kind;
		}
		return p;
	}

	private Vector actualParameters() {
		if (current.kind != Symbol.LROUND)
			return null;
		Vector v = new Vector();
		next_symbol();
		Stack stk = new Stack();
		expression(stk);
		v.addElement(stk);
		while (current.kind == Symbol.COMMA) {
			next_symbol();
			stk = new Stack();
			expression(stk);
			v.addElement(stk);
		}
		current_is(Symbol.RROUND, ") - expected");
		next_symbol();
		return v;
	}

	private ProcessSpec stateDefns() {
		ProcessSpec p = new ProcessSpec();
		current_is(Symbol.UPPERIDENT, "process identifier expected");
		Symbol temp = current;
		next_symbol();
		paramDefns(p.init_constants, p.parameters);
		push_symbol();
		current = temp;
		p.stateDefns.addElement(stateDefn());
		while (current.kind == Symbol.COMMA) {
			next_symbol();
			p.stateDefns.addElement(stateDefn());
		}
		if (current.kind == Symbol.PLUS) {
			next_symbol();
			p.alphaAdditions = labelSet();
			// this.addMaybesToAlphabet(p);
		}
		p.alphaRelabel = relabelDefns();
		if (current.kind == Symbol.BACKSLASH || current.kind == Symbol.AT) {
			p.exposeNotHide = (current.kind == Symbol.AT);
			next_symbol();
			p.alphaHidden = labelSet();
			Vector addLabels = new Vector();
			LabelSet alphaHidden = p.alphaHidden;
			this.hideMaybeActions(alphaHidden, addLabels);
		}

		if (Symbol.SINE == current.kind) {
			parseControllerGoal(p);
		}

		p.getname();
		current_is(Symbol.DOT, "dot expected");
		return p;
	}

	private void parseControllerGoal(ProcessSpec p) {
		// tengo que parsear un goal por ahora uno solo,
		// si adimitiera mas estaria aceptando el otro tipo de goals!
		// que pasa con esta idea si cambia el tipo de goals-->cambiaran los
		// geals nada mas :D
		next_symbol();
		current_is(Symbol.LCURLY, "{ expected");
		next_symbol();
		current_is(Symbol.UPPERIDENT, "goal identifier expected");
		p.goal = current;
		next_symbol();
		current_is(Symbol.RCURLY, "} expected");
		next_symbol();
	}

	private void parseControllerGoal(CompositionExpression c) {
		// tengo que parsear un goal por ahora uno solo,
		// si adimitiera mas estaria aceptando el otro tipo de goals!
		// que pasa con esta idea si cambia el tipo de goals-->cambiaran los
		// geals nada mas :D
		next_symbol();
		current_is(Symbol.LCURLY, "{ expected");
		next_symbol();
		current_is(Symbol.UPPERIDENT, "goal identifier expected");
		c.goal = current;
		next_symbol();
		current_is(Symbol.RCURLY, "} expected");
		next_symbol();
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
		ProcessSpec p = new ProcessSpec();
		p.name = current;
		next_symbol();
		current_is(Symbol.BECOMES, "= expected");
		next_symbol();
		current_is(Symbol.STRING_VALUE, " - imported file name expected");
		p.importFile = new File(currentDirectory, current.toString());
		return p;
	}

	private void animationDefinition() {
		current_is(Symbol.UPPERIDENT, "animation identifier expected");
		MenuDefinition m = new MenuDefinition();
		m.name = current;
		next_symbol();
		current_is(Symbol.BECOMES, "= expected");
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
			current_is(Symbol.LCURLY, "{ expected");
			next_symbol();
			current_is(Symbol.UPPERIDENT, "animation name expected");
			Symbol name = current;
			next_symbol();
			m.addAnimationPart(name, relabelDefns());
			while (LTSUtils.isOrSymbol(current)) {
				next_symbol();
				current_is(Symbol.UPPERIDENT, "animation name expected");
				name = current;
				next_symbol();
				m.addAnimationPart(name, relabelDefns());
			}
			current_is(Symbol.RCURLY, "} expected");
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
		MenuDefinition m = new MenuDefinition();
		m.name = current;
		next_symbol();
		current_is(Symbol.BECOMES, "= expected");
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
		ProgressDefinition p = new ProgressDefinition();
		p.name = current;
		next_symbol();
		if (current.kind == Symbol.LSQUARE)
			p.range = forallRanges();
		current_is(Symbol.BECOMES, "= expected");
		next_symbol();
		if (current.kind == Symbol.IF) {
			next_symbol();
			p.pactions = labelElement();
			current_is(Symbol.THEN, "then expected");
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
		Symbol temp = current;
		next_symbol();
		current_is(Symbol.BECOMES, "= expected");
		next_symbol();
		LabelSet ls = new LabelSet(temp, setValue());
		push_symbol();
	}

	private LabelSet labelSet() {
		if (current.kind == Symbol.LCURLY)
			return new LabelSet(setValue());
		else if (current.kind == Symbol.UPPERIDENT) {
			LabelSet ls = (LabelSet) LabelSet.constants.get(current.toString());
			if (ls == null)
				error("set definition not found for: " + current);
			next_symbol();
			return ls;
		} else {
			error("{ or set identifier expected");
			return null;
		}
	}

	private Vector setValue() {
		current_is(Symbol.LCURLY, "{ expected");
		next_symbol();
		Vector v = new Vector();
		v.addElement(labelElement());
		while (current.kind == Symbol.COMMA) {
			next_symbol();
			v.addElement(labelElement());
		}
		current_is(Symbol.RCURLY, "} expected");
		next_symbol();
		return v;
	}

	private ActionLabels labelElement() {
		if (current.kind != Symbol.IDENTIFIER && !isLabelSet()
				&& current.kind != Symbol.LSQUARE)
			error("identifier, label set or range expected");
		ActionLabels e = null;
		if (current.kind == Symbol.IDENTIFIER) {
			String toString = current.toString();
			if ("tau".equals(toString) || "tau?".equals(toString))
				error("'tau' cannot be used as an action label");
			e = new ActionName(current);
			next_symbol();
		} else if (isLabelSet()) {
			LabelSet left = labelSet();
			if (current.kind == Symbol.BACKSLASH) {
				next_symbol();
				LabelSet right = labelSet();
				e = new ActionSetExpr(left, right);
			} else {
				e = new ActionSet(left);
			}
		} else if (current.kind == Symbol.LSQUARE)
			e = range();
		if (current.kind == Symbol.DOT || current.kind == Symbol.LSQUARE) {
			if (current.kind == Symbol.DOT)
				next_symbol();
			if (e != null)
				e.addFollower(labelElement());
		}
		return e;
	}

	private void constantDefinition(Hashtable p) {
		current_is(Symbol.UPPERIDENT,
				"constant, upper case identifier expected");
		Symbol name = current;
		next_symbol();
		current_is(Symbol.BECOMES, "= expected");
		next_symbol();
		Stack tmp = new Stack();
		simpleExpression(tmp);
		push_symbol();
		if (p.put(name.toString(), Expression.getValue(tmp, null, null)) != null) {
			Diagnostics.fatal("duplicate constant definition: " + name, name);
		}
	}

	private void paramDefns(Hashtable p, Vector parameters) {
		if (current.kind == Symbol.LROUND) {
			next_symbol();
			parameterDefinition(p, parameters);
			while (current.kind == Symbol.COMMA) {
				next_symbol();
				parameterDefinition(p, parameters);
			}
			current_is(Symbol.RROUND, ") expected");
			next_symbol();
		}
	}

	private void parameterDefinition(Hashtable p, Vector parameters) {
		current_is(Symbol.UPPERIDENT,
				"parameter, upper case identifier expected");
		Symbol name = current;
		next_symbol();
		current_is(Symbol.BECOMES, "= expected");
		next_symbol();
		Stack tmp = new Stack();
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
		StateDefn s = new StateDefn();
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
		current_is(Symbol.BECOMES, "= expected");
		next_symbol();
		s.stateExpr = stateExpr();
		return s;
	}

	private Stack getEvaluatedExpression() {
		Stack tmp = new Stack();
		simpleExpression(tmp);
		int v = Expression.evaluate(tmp, null, null);
		tmp = new Stack();
		tmp.push(new Symbol(Symbol.INT_VALUE, v));
		return tmp;
	}

	private void rangeDefinition() {
		current_is(Symbol.UPPERIDENT,
				"range name, upper case identifier expected");
		Symbol name = current;
		next_symbol();
		current_is(Symbol.BECOMES, "= expected");
		next_symbol();
		Range r = new Range();
		r.low = getEvaluatedExpression();
		current_is(Symbol.DOT_DOT, "..  expected");
		next_symbol();
		r.high = getEvaluatedExpression();
		if (Range.ranges.put(name.toString(), r) != null) {
			Diagnostics.fatal("duplicate range definition: " + name, name);
		}
		push_symbol();
	}

	private ActionLabels range() { // this is a mess.. needs to be rewritten
		if (current.kind == Symbol.LSQUARE) {
			next_symbol();
			ActionLabels r;
			Stack low = null;
			Stack high = null;
			if (current.kind != Symbol.IDENTIFIER) {
				if (isLabelSet()) {
					r = new ActionSet(labelSet());
				} else if (current.kind == Symbol.UPPERIDENT
						&& Range.ranges.containsKey(current.toString())) {
					r = new ActionRange((Range) Range.ranges.get(current
							.toString()));
					next_symbol();
				} else {
					low = new Stack();
					expression(low);
					r = new ActionExpr(low);
				}
				if (current.kind == Symbol.DOT_DOT) {
					next_symbol();
					high = new Stack();
					expression(high);
					r = new ActionRange(low, high);
				}
			} else {
				Symbol varname = current;
				next_symbol();
				if (current.kind == Symbol.COLON) {
					next_symbol();
					if (isLabelSet()) {
						r = new ActionVarSet(varname, labelSet());
					} else if (current.kind == Symbol.UPPERIDENT
							&& Range.ranges.containsKey(current.toString())) {
						r = new ActionVarRange(varname, (Range) Range.ranges
								.get(current.toString()));
						next_symbol();
					} else {
						low = new Stack();
						expression(low);
						current_is(Symbol.DOT_DOT, "..  expected");
						next_symbol();
						high = new Stack();
						expression(high);
						r = new ActionVarRange(varname, low, high);
					}
				} else {
					push_symbol();
					current = varname;
					low = new Stack();
					expression(low);
					if (current.kind == Symbol.DOT_DOT) {
						next_symbol();
						high = new Stack();
						expression(high);
						r = new ActionRange(low, high);
					} else
						r = new ActionExpr(low);
				}
			}
			current_is(Symbol.RSQUARE, "] expected");
			next_symbol();
			return r;
		} else
			return null;
	}

	private StateExpr stateExpr() {
		StateExpr s = new StateExpr();
		if (current.kind == Symbol.UPPERIDENT)
			stateRef(s);
		else if (current.kind == Symbol.IF) {
			next_symbol();
			s.boolexpr = new Stack();
			expression(s.boolexpr);
			current_is(Symbol.THEN, "keyword then expected");
			next_symbol();
			s.thenpart = stateExpr();
			if (current.kind == Symbol.ELSE) {
				next_symbol();
				s.elsepart = stateExpr();
			} else {
				Symbol stop = new Symbol(Symbol.UPPERIDENT, "STOP");
				StateExpr se = new StateExpr();
				se.name = stop;
				s.elsepart = se;
			}
		} else if (current.kind == Symbol.LROUND) {
			next_symbol();
			choiceExpr(s);
			current_is(Symbol.RROUND, ") expected");
			next_symbol();
		} else
			error(" (, if or process identifier expected");

		return s;
	}

	private void stateRef(StateExpr s) {
		current_is(Symbol.UPPERIDENT, "process identifier expected");
		s.name = current;
		next_symbol();
		while (current.kind == Symbol.SEMICOLON
				|| current.kind == Symbol.LROUND) {
			s.addSeqProcessRef(new SeqProcessRef(s.name, actualParameters()));
			next_symbol();
			current_is(Symbol.UPPERIDENT, "process identifier expected");
			s.name = current;
			next_symbol();
		}
		if (current.kind == Symbol.LSQUARE) {
			s.expr = new Vector();
			while (current.kind == Symbol.LSQUARE) {
				next_symbol();
				Stack x = new Stack();
				expression(x);
				s.expr.addElement(x);
				current_is(Symbol.RSQUARE, "] expected");
				next_symbol();
			}
		}
	}

	private void choiceExpr(StateExpr s) {
		s.choices = new Vector();
		s.choices.addElement(choiceElement());
		while (current.kind == Symbol.BITWISE_OR) {
			next_symbol();
			s.choices.addElement(choiceElement());
		}
	}

	private ChoiceElement choiceElement() {
		ChoiceElement first = new ChoiceElement();
		if (current.kind == Symbol.WHEN) {
			next_symbol();
			first.guard = new Stack();
			expression(first.guard);
		}
		first.action = labelElement();
		current_is(Symbol.ARROW, "-> expected");
		ChoiceElement next = first;
		ChoiceElement last = first;
		next_symbol();
		while (current.kind == Symbol.IDENTIFIER
				|| current.kind == Symbol.LSQUARE || isLabelSet()) {
			StateExpr ex = new StateExpr();
			next = new ChoiceElement();
			next.action = labelElement();
			ex.choices = new Vector();
			ex.choices.addElement(next);
			last.stateExpr = ex;
			last = next;
			current_is(Symbol.ARROW, "-> expected");
			next_symbol();
		}
		next.stateExpr = stateExpr();
		return first;
	}

	private Symbol event() {
		current_is(Symbol.IDENTIFIER, "event identifier expected");
		Symbol e = current;
		next_symbol();
		return e;
	}

	// LABELCONSTANT -------------------------------

	private ActionLabels labelConstant() {
		next_symbol();
		ActionLabels el = labelElement();
		if (el != null) {
			return el;
		} else
			error("label definition expected");
		return null;
	}

	// set selection @(set , expr)
	private void set_select(Stack expr) {
		Symbol op = current;
		next_symbol();
		current_is(Symbol.LROUND, "( expected to start set index selection");
		Symbol temp = current; // preserve marker
		temp.setAny(labelConstant());
		temp.kind = Symbol.LABELCONST;
		expr.push(temp);
		current_is(Symbol.COMMA, ", expected before set index expression");
		next_symbol();
		expression(expr);
		current_is(Symbol.RROUND, ") expected to end set index selection");
		next_symbol();
		expr.push(op);
	}

	// UNARY ---------------------------------
	private void unary(Stack expr) { // +, -, identifier,
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
			current_is(Symbol.RROUND, ") expected to end expression");
			next_symbol();
			break;
		case Symbol.HASH:
			unary_operator = new Symbol(current);
		case Symbol.QUOTE: // this is a labelConstant
			Symbol temp = current; // preserve marker
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

	private void multiplicative(Stack expr) { // *, /, %
		unary(expr);
		while (current.kind == Symbol.STAR || current.kind == Symbol.DIVIDE
				|| current.kind == Symbol.MODULUS) {
			Symbol op = current;
			next_symbol();
			unary(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// ADDITIVE

	private void additive(Stack expr) { // +, -
		multiplicative(expr);
		while (current.kind == Symbol.PLUS || current.kind == Symbol.MINUS) {
			Symbol op = current;
			next_symbol();
			multiplicative(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// SHIFT

	private void shift(Stack expr) { // <<, >>
		additive(expr);
		while (current.kind == Symbol.SHIFT_LEFT
				|| current.kind == Symbol.SHIFT_RIGHT) {
			Symbol op = current;
			next_symbol();
			additive(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// RELATIONAL

	private void relational(Stack expr) { // <, <=, >, >=
		shift(expr);
		while (current.kind == Symbol.LESS_THAN
				|| current.kind == Symbol.LESS_THAN_EQUAL
				|| current.kind == Symbol.GREATER_THAN
				|| current.kind == Symbol.GREATER_THAN_EQUAL) {
			Symbol op = current;
			next_symbol();
			shift(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// EQUALITY

	private void equality(Stack expr) { // ==, !=
		relational(expr);
		while (current.kind == Symbol.EQUALS
				|| current.kind == Symbol.NOT_EQUAL) {
			Symbol op = current;
			next_symbol();
			relational(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// AND

	private void and(Stack expr) { // &
		equality(expr);
		while (current.kind == Symbol.BITWISE_AND) {
			Symbol op = current;
			next_symbol();
			equality(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// EXCLUSIVE_OR

	private void exclusive_or(Stack expr) { // ^
		and(expr);
		while (current.kind == Symbol.CIRCUMFLEX) {
			Symbol op = current;
			next_symbol();
			and(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// INCLUSIVE_OR

	private void inclusive_or(Stack expr) { // |
		exclusive_or(expr);
		while (current.kind == Symbol.BITWISE_OR) {
			Symbol op = current;
			next_symbol();
			exclusive_or(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// LOGICAL_AND

	private void logical_and(Stack expr) { // &&
		inclusive_or(expr);
		while (current.kind == Symbol.AND) {
			Symbol op = current;
			next_symbol();
			inclusive_or(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// LOGICAL_OR

	private void logical_or(Stack expr) { // ||
		logical_and(expr);
		while (current.kind == Symbol.OR) {
			Symbol op = current;
			next_symbol();
			logical_and(expr);
			expr.push(op);
		}
	}

	// _______________________________________________________________________________________
	// EXPRESSION

	private void expression(Stack expr) {
		logical_or(expr);
	}

	// this is used to avoid a syntax problem
	// when a parallel composition
	// follows a range or constant definition e.g.
	// const N = 3
	// ||S = (P || Q)
	private void simpleExpression(Stack expr) {
		additive(expr);
	}

	// _______________________________________________________________________________________
	// LINEAR TEMPORAL LOGIC ASSERTIONS

	private void assertDefinition(boolean isConstraint, boolean isProperty) {
		Hashtable initparams = new Hashtable();
		Vector params = new Vector();
		LabelSet ls = null;

		current_is(Symbol.UPPERIDENT, "LTL property identifier expected");
		Symbol name = current;
		next_symbol();
		paramDefns(initparams, params);
		current_is(Symbol.BECOMES, "= expected");
		next_symbol_mod();

		FormulaSyntax formula = ltl_unary();

		if (current.kind == Symbol.PLUS) {
			next_symbol();
			ls = labelSet();
		}
		push_symbol();
		this.validateUniqueProcessName(name);

		AssertDefinition.put(name, formula, ls, initparams, params,
				isConstraint, isProperty);

		// Negation of the formula
		Symbol notName = new Symbol(name);
		notName.setString(AssertDefinition.NOT_DEF + notName.getName());
		Symbol s = new Symbol(Symbol.PLING);
		FormulaSyntax notF = FormulaSyntax.make(null, s, formula);

		this.validateUniqueProcessName(notName);
		AssertDefinition.put(notName, notF, ls, initparams, params,
				isConstraint, isProperty);
	}

	/**
	 * Validates that there is no process or composite process with name
	 * designated by <code>processName</code> If so it reports the fatal error
	 * 
	 */
	private void validateUniqueProcessName(Symbol processName) {
		if (processes != null && processes.get(processName.toString()) != null
				|| composites != null
				&& composites.get(processName.toString()) != null) {
			Diagnostics.fatal(
					"name already defined  " + processName.toString(),
					processName);
		}
	}

	// do not want X and U to be keywords outside of LTL expressions
	private Symbol modify(Symbol s) {
		if (s.kind != Symbol.UPPERIDENT)
			return s;
		if (s.toString().equals("X")) {
			Symbol nx = new Symbol(s);
			nx.kind = Symbol.NEXTTIME;
			return nx;
		}
		if (s.toString().equals("U")) {
			Symbol ut = new Symbol(s);
			ut.kind = Symbol.UNTIL;
			return ut;
		}
		if (s.toString().equals("W")) {
			Symbol wut = new Symbol(s);
			wut.kind = Symbol.WEAKUNTIL;
			return wut;
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
		Symbol op = current;
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
				ActionLabels range = forallRanges();
				current = modify(current);
				return FormulaSyntax.make(op, range);
			} else if (current.kind == Symbol.LROUND) {
				Vector actparams = actualParameters();
				return FormulaSyntax.make(op, actparams);
			} else {
				return FormulaSyntax.make(op);
			}
		case Symbol.LROUND:
			next_symbol_mod();
			FormulaSyntax right = ltl_or();
			current_is(Symbol.RROUND, ") expected to end LTL expression");
			next_symbol_mod();
			return right;
		case Symbol.IDENTIFIER:
		case Symbol.LSQUARE:
		case Symbol.LCURLY:
			ActionLabels ts = labelElement();
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
			Stack tmp = new Stack();
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
			Symbol op = current;
			next_symbol_mod();
			FormulaSyntax right = ltl_unary();
			left = FormulaSyntax.make(left, op, right);
		}
		return left;
	}

	// _______________________________________________________________________________________
	// LTL_OR

	private FormulaSyntax ltl_or() { // |
		FormulaSyntax left = ltl_binary();
		while (LTSUtils.isOrSymbol(current)) {
			Symbol op = current;
			next_symbol_mod();
			FormulaSyntax right = ltl_binary();
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
			Symbol op = current;
			next_symbol_mod();
			FormulaSyntax right = ltl_and();
			left = FormulaSyntax.make(left, op, right);
		}
		return left;
	}

	//
	// ___________________________________________________________________________________
	// STATE PREDICATE DEFINITIONS

	private void predicateDefinition() {
		current_is(Symbol.UPPERIDENT, "predicate identifier expected");
		Symbol name = current;
		ActionLabels range = null;
		next_symbol();
		if (current.kind == Symbol.LSQUARE)
			range = forallRanges();
		current_is(Symbol.BECOMES, "= expected");
		next_symbol();
		current_is(Symbol.LESS_THAN, "< expected");
		next_symbol();
		ActionLabels ts = labelElement();
		current_is(Symbol.COMMA, ", expected");
		next_symbol();
		ActionLabels fs = labelElement();
		current_is(Symbol.GREATER_THAN, "> expected");
		next_symbol();
		if (current.kind == Symbol.INIT) {
			next_symbol();
			Stack tmp = new Stack();
			simpleExpression(tmp);
			push_symbol();
			PredicateDefinition.put(name, range, ts, fs, tmp);
		} else {
			push_symbol();
			PredicateDefinition.put(name, range, ts, fs, null);
		}
	}

	// *******************************************************************************************************/
	// MTS operations
	// *******************************************************************************************************/

	/*
	 * This method extends the set of labels to be relabeled with the maybe or
	 * the required labels.
	 */
	private void relabelMTS(Vector relabels, RelabelDefn relabelDefn) {
		if (relabelDefn.oldlabel instanceof ActionName) {
			ActionName oldLabel = (ActionName) relabelDefn.oldlabel;
			String oldName = oldLabel.name.getName();

			RelabelDefn relabelMaybe = new RelabelDefn();
			ActionName newLabel = (ActionName) relabelDefn.newlabel;
			String newName = newLabel.name.getName();

			Symbol newLabelMaybeSymbol = new Symbol(newLabel.name, MTSUtils
					.getMaybeAction(newName));
			Symbol oldLabelMaybeSymbol = new Symbol(newLabel.name, MTSUtils
					.getMaybeAction(oldName));

			relabelMaybe.oldlabel = new ActionName(oldLabelMaybeSymbol);
			relabelMaybe.newlabel = new ActionName(newLabelMaybeSymbol);

			relabels.addElement(relabelMaybe);
		} else if (relabelDefn.oldlabel instanceof ActionSet) {
			ActionSet oldActionSet = (ActionSet) relabelDefn.oldlabel;

			for (Iterator it = oldActionSet.getActions(null, null).iterator(); it
					.hasNext();) {
				String oldName = (String) it.next();

				RelabelDefn relabelMaybe = new RelabelDefn();
				ActionName newLabel = (ActionName) relabelDefn.newlabel;
				String newName = newLabel.name.getName();

				Symbol newLabelMaybeSymbol = new Symbol(newLabel.name, MTSUtils
						.getMaybeAction(newName));
				Symbol oldLabelMaybeSymbol = new Symbol(newLabel.name, MTSUtils
						.getMaybeAction(oldName));

				relabelMaybe.oldlabel = new ActionName(oldLabelMaybeSymbol);
				relabelMaybe.newlabel = new ActionName(newLabelMaybeSymbol);

				relabels.addElement(relabelMaybe);
			}
		}
	}

	private void addMaybesToAlphabet(ProcessSpec p) {
		Vector addLabels = new Vector();
		if (p.alphaAdditions != null) {
			this.processMTSActions(addLabels, p.alphaAdditions.labels);
		}
	}

	private void hideMaybes(ProcessSpec p) {
		Vector addLabels = new Vector();
		LabelSet alphaHidden = p.alphaHidden;
		hideMaybeActions(alphaHidden, addLabels);
	}

	private void hideMaybeActions(LabelSet alphaHidden, Vector addLabels) {
		if (alphaHidden != null) {
			this.processMTSActions(addLabels, alphaHidden.labels);
		}
	}

	private void priorizeMaybeActions(LabelSet priorityActions) {
		// Only ActionName expected.
		Vector addLabels = new Vector();
		if (priorityActions != null) {
			this.processMTSActions(addLabels, priorityActions.labels);
		}
	}

	private void processMTSActions(Vector addLabels, Vector allLabels) {
		for (Iterator it = allLabels.iterator(); it.hasNext();) {
			ActionLabels action = (ActionLabels) it.next();
			if (action instanceof ActionSet) {
				this.processActionSet((ActionSet) action, addLabels);
			} else if (action instanceof ActionName) {
				addLabels.add(processActionName((ActionName) action));
			} else {
				throw new RuntimeException(
						"It wasn't ActionName nor ActionSet, was an instance of class: "
								+ action.getClass());
			}
		}
		allLabels.addAll(addLabels);
	}

	/**
	 * Recorre las actions en <code>actionLabels</code> y en el caso de q la
	 * action sea maybe agrega a <code>addLabels</code> la action no maybe y
	 * viceversa.
	 * 
	 * @param actionLabels
	 * @param addLabels
	 */
	private void processActionSet(ActionSet actionLabels, Vector addLabels) {
		ActionSet actionSet = (ActionSet) actionLabels;
		for (Iterator it = actionSet.getActions(null, null).iterator(); it
				.hasNext();) {
			String actionLabel = (String) it.next();
			actionLabel = getOpositeActionLabel(actionLabel);
			addLabels.add(new ActionName(new Symbol(Symbol.STRING_VALUE,
					actionLabel)));
		}
	}

	// TODO pasarlo a MTSUtils
	/**
	 * Devuelve el label string opuesto a <code>actionLabel</code>
	 * 
	 * @param actionLabel
	 * @return
	 */
	public String getOpositeActionLabel(String actionLabel) {
		if (MTSUtils.isMaybe(actionLabel)) {
			actionLabel = MTSUtils.getAction(actionLabel);
		} else {
			actionLabel = MTSUtils.getMaybeAction(actionLabel);
		}
		return actionLabel;
	}

	/**
	 * Devuelve la action opuesta a <code>actionName</code>
	 */
	public ActionName processActionName(ActionName actionName) {
		return new ActionName(new Symbol(actionName.name,
				getOpositeActionLabel(actionName.name.getName())));
	}

	/*
	 * Not used yet
	 */
	private void controllerDefinition(ControllerDefinition controllerDefinition) {
		current_is(Symbol.BECOMES, "= expected after controller identifier");
		next_symbol();

		current_is(Symbol.LCURLY, "{ expected");
		next_symbol();

		current_is(Symbol.UPPERIDENT, "goal identifier expected");
		controllerDefinition.setProcess(current);
		next_symbol();

		current_is(Symbol.COMMA, ", expected after goal identifier");
		next_symbol();

		current_is(Symbol.UPPERIDENT, "goal identifier expected");
		controllerDefinition.setGoal(current);
		next_symbol();

		current_is(Symbol.RCURLY, "} expected");
		next_symbol();

		ControllerDefinition.put(controllerDefinition);

		push_symbol();
	}

	private void goalDefinition(ControllerGoalDefinition goal) {
		current_is(Symbol.BECOMES, "= expected after goal identifier");
		next_symbol();

		current_is(Symbol.LCURLY, "{ expected");
		next_symbol();

		if (current.kind == Symbol.SAFETY) {
			next_symbol();
			goal.setSafetyDefinitions(this.controllerSubGoal());
		}

		if (current.kind == Symbol.FAULT) {
			next_symbol();
			goal.setFaultsDefinitions(this.controllerSubGoal());
		}

		if (current.kind == Symbol.ASSUME) {
			next_symbol();
			goal.setAssumeDefinitions(this.controllerSubGoal());
		}

		if (current.kind == Symbol.GUARANTEE) {
			next_symbol();
			goal.setGuaranteeDefinitions(this.controllerSubGoal());
		}

		this.controllableActionSet(goal);

		ControllerGoalDefinition.put(goal);

		current_is(Symbol.RCURLY, "} expected");
		// push_symbol();
	}

	private void controllableActionSet(ControllerGoalDefinition goal) {
		current_is(Symbol.CONTROLLABLE, "controllable action set expected");
		next_symbol();

		current_is(Symbol.BECOMES, "= expected for controllable action set");
		next_symbol();

		current_is(Symbol.LCURLY, "{ expected");
		next_symbol();

		current_is(Symbol.UPPERIDENT, "action set identifier expected");
		goal.setControllableActionSet(current);
		next_symbol();

		current_is(Symbol.RCURLY, "} expected");
		next_symbol();
	}

	private Set<Symbol> controllerSubGoal() {
		Set<Symbol> definitions = new HashSet<Symbol>();
		current_is(Symbol.BECOMES, "= expected");
		next_symbol();

		current_is(Symbol.LCURLY, "{ expected");
		next_symbol();
		boolean finish = false;
		while (current.kind == Symbol.UPPERIDENT && !finish) {
			definitions.add(current);
			next_symbol();
			if (current.kind != Symbol.COMMA) {
				finish = true;
				break;
			}
			next_symbol();
		}
		current_is(Symbol.RCURLY, "} expected");
		next_symbol();

		return definitions;
	}

	/**
	 * Parses a Triggered Scenario
	 */
	private void triggeredScenarioDefinition(
			TriggeredScenarioDefinition triggeredScenarioDefinition)
			throws DuplicatedTriggeredScenarioDefinitionException {

		current_is(Symbol.BECOMES, "= expected after chart identifier");
		next_symbol();

		current_is(Symbol.LCURLY, "{ expected");
		next_symbol();

		// Instances must come next
		triggeredScenarioDefinition.setInstances(this.chartInstancesValues());

		// Conditions (if any) comes next
		while (current.kind == Symbol.CONDITION) {
			next_symbol();
			triggeredScenarioDefinition.addConditionDefinition(this
					.conditionDefinition());
		}

		// Prechart must come next
		current_is(Symbol.PRECHART, "prechart expected");
		next_symbol();
		triggeredScenarioDefinition.setPrechart(this.basicChartDefinition(true,
				triggeredScenarioDefinition));

		// Mainchart must come next
		current_is(Symbol.MAINCHART, "mainchart expected");
		next_symbol();
		triggeredScenarioDefinition.setMainchart(this.basicChartDefinition(
				false, triggeredScenarioDefinition));

		triggeredScenarioDefinition.setRestricted(this
				.restrictsDefinition(triggeredScenarioDefinition));

		current_is(Symbol.RCURLY, "} expected");
		next_symbol();

		TriggeredScenarioDefinition.put(triggeredScenarioDefinition);

		push_symbol();
	}

	private ConditionDefinition conditionDefinition() {
		current_is(Symbol.UPPERIDENT, "Identifier expected");
		Symbol name = current;

		next_symbol();
		current_is(Symbol.BECOMES, "= expected");

		next_symbol_mod();
		FormulaSyntax formula = ltl_unary();

		if (!formula.isPropositionalLogic()) {
			error("Condition must be a Fluent Propositional Logic formula");
			return null;
		}
		ConditionDefinition conditionDefinition = new ConditionDefinition(name
				.getName(), formula);

		return conditionDefinition;
	}

	private Set<Interaction> restrictsDefinition(
			TriggeredScenarioDefinition tsDefinition) {
		Set<Interaction> result = new HashSet<Interaction>();

		if (current.kind == Symbol.RESTRICTS) {
			next_symbol();

			current_is(Symbol.LCURLY, "{ expected");
			next_symbol();

			while (current.kind != Symbol.RCURLY) {
				try {
					result
							.add((Interaction) locationValue(false,
									tsDefinition));
				} catch (ClassCastException e) {
					error("Restrictions can only be Interactions");
					return null;
				}
			}
			next_symbol();
		}
		return result;
	}

	private BasicChartDefinition basicChartDefinition(
			boolean isPrechartDefinition,
			TriggeredScenarioDefinition tsDefinition) {
		current_is(Symbol.LCURLY, "{ expected");
		next_symbol();
		BasicChartDefinition chartDefinition = new BasicChartDefinition();
		chartDefinition.addLocation(locationValue(isPrechartDefinition,
				tsDefinition));

		while (current.kind != Symbol.RCURLY) {
			chartDefinition.addLocation(locationValue(isPrechartDefinition,
					tsDefinition));
		}
		next_symbol();

		return chartDefinition;
	}

	private Location locationValue(boolean isPrechartDefinition,
			TriggeredScenarioDefinition tsDefinition) {
		// A location can be a Condition or an Interaction
		// Interactions are of the form: UPPERIDENT -> IDENT -> UPPERIDENT
		// Conditions are like: UPPERIDENT [UPPERIDENT UPPERIDENT...]. The first
		// ident is the name of the condition and then
		// comes the set of instances that the condition synchronises.
		Symbol previous = this.identifier();

		if (current.kind == Symbol.ARROW) {
			// Location is an Interaction
			next_symbol();

			// previous symbol is the interaction's source
			String source = previous.getName();
			String message = this.event().getName();

			current_is(Symbol.ARROW, "-> expected");
			next_symbol();

			String target = this.identifier().getName();

			return new Interaction(source, message, target);
		} else if (current.kind == Symbol.LSQUARE) {
			// Conditions can only be placed in the Prechart
			if (!isPrechartDefinition) {
				error("Conditions can only be placed in the Prechart");
				return null;
			} else {
				// Location is a Condition
				next_symbol();

				// previous symbol is the condition's identifier
				if (!tsDefinition.hasCondition(previous.getName())) {
					// Condition must be defined previously in the
					// TriggeredScenario.
					error("Condition not defined: " + previous.getName());
					return null;
				} else {
					String conditionName = previous.getName();

					// get the instances synchronising with this condition
					Set<String> instances = new HashSet<String>();

					// at least there must be an instance
					instances.add(this.identifier().getName());
					while (current.kind != Symbol.RSQUARE) {
						instances.add(this.identifier().getName());
					}
					next_symbol();

					return new ConditionLocation(conditionName, instances);
				}
			}
		} else {
			error("-> or [ expected");
			return null;
		}
	}

	private NamedFPLFormula guaranteeDefinition() {
		return namedFPLFormula();
	}

	private NamedFPLFormula assumeDefinition() {
		return namedFPLFormula();
	}

	private NamedFPLFormula namedFPLFormula() {
		current_is(Symbol.UPPERIDENT, "Identifier expected");
		Symbol name = current;

		next_symbol();
		current_is(Symbol.BECOMES, "= expected");

		// should we allow parametrized FLTL formulas?
		next_symbol_mod();
		FormulaSyntax formula = ltl_unary();

		if (!formula.isPropositionalLogic()) {
			error(name + " must be a Fluent Propositional Logic formula");
			return null;
		}

		return new NamedFPLFormula(name.getName(), formula);
	}

	/**
	 * Checks that next symbol is an identifier and gets it.
	 */
	private Symbol identifier() {
		current_is(Symbol.UPPERIDENT, "identifier expected");
		Symbol identifier = current;
		next_symbol();
		return identifier;
	}

	private Set<String> chartInstancesValues() {
		current_is(Symbol.INSTANCES, "instances expected");
		next_symbol();

		current_is(Symbol.LCURLY, "{ expected");
		next_symbol();
		Set<String> instances = new HashSet<String>();
		while (current.kind == Symbol.UPPERIDENT) {
			instances.add(current.toString());
			next_symbol();
		}
		current_is(Symbol.RCURLY, "} expected");
		next_symbol();
		return instances;
	}

}