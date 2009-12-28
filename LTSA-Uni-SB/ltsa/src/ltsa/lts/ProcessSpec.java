package ltsa.lts;

import java.io.*;
import java.util.*;

public class ProcessSpec extends Declaration {
	Symbol name;
	Hashtable<String, Value> constants;
	Hashtable<String, Value> init_constants = new Hashtable<String, Value>();
	Vector<String> parameters = new Vector<String>(2);
	Vector<StateDefn> stateDefns = new Vector<StateDefn>(4);
	LabelSet alphaAdditions;
	LabelSet alphaHidden;
	Vector<RelabelDefn> alphaRelabel;
	final boolean isProperty;
	final boolean isMinimal;
	final boolean isDeterministic;
	boolean exposeNotHide = false;

	File importFile = null; // used if the process is imported from a .aut file

	public ProcessSpec() {
		this(-1);
	}

	public ProcessSpec(int kind) {
		isProperty = kind == Symbol.PROPERTY;
		isMinimal = kind == Symbol.MINIMAL;
		isDeterministic = kind == Symbol.DETERMINISTIC;
	}

	public boolean imported() {
		return importFile != null;
	}

	@SuppressWarnings("unchecked")
	public String getname() {
		constants = (Hashtable<String, Value>) init_constants.clone();
		final StateDefn s = stateDefns.firstElement();
		name = s.name;
		if (s.range != null)
			Diagnostics.fatal("process name cannot be indexed", name);
		return s.name.toString();
	}

	@Override
	public void explicitStates(StateMachine m) {
		final Enumeration<StateDefn> e = stateDefns.elements();
		while (e.hasMoreElements()) {
			final Declaration d = e.nextElement();
			d.explicitStates(m);
		}
	}

	public void addAlphabet(StateMachine m) {
		if (alphaAdditions != null) {
			final Vector<String> a = alphaAdditions.getActions(constants);
			final Enumeration<String> e = a.elements();
			while (e.hasMoreElements()) {
				final String s = e.nextElement();
				if (!m.alphabet.containsKey(s))
					m.alphabet.put(s, m.eventLabel.label());
			}
		}
	}

	public void hideAlphabet(StateMachine m) {
		if (alphaHidden == null)
			return;
		m.hidden = alphaHidden.getActions(constants);
	}

	public void relabelAlphabet(StateMachine m) {
		if (alphaRelabel == null)
			return;
		m.relabels = new Relation<String, String>();
		final Enumeration<RelabelDefn> e = alphaRelabel.elements();
		while (e.hasMoreElements()) {
			final RelabelDefn r = e.nextElement();
			r.makeRelabels(constants, m.relabels);
		}
	}

	@Override
	public void crunch(StateMachine m) {
		final Enumeration<StateDefn> e = stateDefns.elements();
		while (e.hasMoreElements()) {
			final Declaration d = e.nextElement();
			d.crunch(m);
		}
	}

	@Override
	public void transition(StateMachine m) {
		final Enumeration<StateDefn> e = stateDefns.elements();
		while (e.hasMoreElements()) {
			final Declaration d = e.nextElement();
			d.transition(m);
		}
	}

	public void doParams(Vector<Value> actuals) {
		final Enumeration<Value> a = actuals.elements();
		final Enumeration<String> f = parameters.elements();
		while (a.hasMoreElements() && f.hasMoreElements())
			constants.put(f.nextElement(), a.nextElement());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ProcessSpec clone() {
		final ProcessSpec p = (ProcessSpec) super.clone();
		p.constants = (Hashtable<String, Value>) constants.clone();
		p.stateDefns = new Vector<StateDefn>(stateDefns.size());
		for (final StateDefn e : stateDefns) {
			p.stateDefns.addElement(e.clone());
		}
		return p;
	}

}
