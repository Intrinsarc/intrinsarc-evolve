package lts;

import java.io.*;
import java.util.*;

class ProcessSpec extends Declaration {
	Symbol name;
	Hashtable constants;
	Hashtable init_constants = new Hashtable();
	Vector parameters = new Vector();
	Vector stateDefns = new Vector();
	LabelSet alphaAdditions;
	LabelSet alphaHidden;
	Vector alphaRelabel;
	boolean isProperty = false;
	boolean isMinimal = false;
	boolean isDeterministic = false;
	boolean exposeNotHide = false;

	File importFile = null; // used if the process is imported from a .aut file

	public boolean imported() {
		return importFile != null;
	}

	public String getname() {
		constants = (Hashtable) init_constants.clone();
		StateDefn s = (StateDefn) stateDefns.firstElement();
		name = s.name;
		if (s.range != null)
			Diagnostics.fatal("process name cannot be indexed", name);
		return s.name.toString();
	}

	public void explicitStates(StateMachine m) {
		Enumeration e = stateDefns.elements();
		while (e.hasMoreElements()) {
			Declaration d = (Declaration) e.nextElement();
			d.explicitStates(m);
		}
	}

	public void addAlphabet(StateMachine m) {
		if (alphaAdditions != null) {
			Vector a = alphaAdditions.getActions(constants);
			Enumeration e = a.elements();
			while (e.hasMoreElements()) {
				String s = (String) e.nextElement();
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
		m.relabels = new Relation();
		Enumeration e = alphaRelabel.elements();
		while (e.hasMoreElements()) {
			RelabelDefn r = (RelabelDefn) e.nextElement();
			r.makeRelabels(constants, m.relabels);
		}
	}

	public void crunch(StateMachine m) {
		Enumeration e = stateDefns.elements();
		while (e.hasMoreElements()) {
			Declaration d = (Declaration) e.nextElement();
			d.crunch(m);
		}
	}

	public void transition(StateMachine m) {
		Enumeration e = stateDefns.elements();
		while (e.hasMoreElements()) {
			Declaration d = (Declaration) e.nextElement();
			d.transition(m);
		}
	}

	public void doParams(Vector actuals) {
		Enumeration a = actuals.elements();
		Enumeration f = parameters.elements();
		while (a.hasMoreElements() && f.hasMoreElements())
			constants.put(f.nextElement(), a.nextElement());
	}

	public ProcessSpec myclone() {
		ProcessSpec p = new ProcessSpec();
		p.name = name;
		p.constants = (Hashtable) constants.clone();
		p.init_constants = init_constants;
		p.parameters = parameters;
		Enumeration e = stateDefns.elements();
		while (e.hasMoreElements())
			p.stateDefns.addElement(((StateDefn) e.nextElement()).myclone());
		p.alphaAdditions = alphaAdditions;
		p.alphaHidden = alphaHidden;
		p.alphaRelabel = alphaRelabel;
		p.isProperty = isProperty;
		p.isMinimal = isMinimal;
		p.isDeterministic = isDeterministic;
		p.exposeNotHide = exposeNotHide;
		p.importFile = importFile;
		return p;
	}

}

