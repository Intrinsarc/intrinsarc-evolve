package controller.game.model;

import java.util.*;

import org.apache.commons.collections15.*;

//TODO class to remove
public class Guarantee<State> {
	private Set<State> states;

	public Guarantee() {
		this.states = new HashSet<State>();
	}

	public boolean addState(State state) {
		return this.states.add(state);
	}

	public boolean contains(State state) {
		return this.states.contains(state);
	}

	public Set<State> getStateSet() {
		return SetUtils.unmodifiableSet(this.states);
	}

	public boolean isEmpty() {
		return this.states.isEmpty();
	}

	@Override
	public String toString() {
		return this.states.toString();
	}
}
