package ac.ic.doc.mtstools.model.impl;

import java.util.*;

import org.apache.commons.lang.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;

public class MTSImpl<State, Action> extends
		AbstractTransitionSystem<State, Action> implements MTS<State, Action> {

	private EnumMap<TransitionType, Map<State, BinaryRelation<Action, State>>> trasitionsByType;

	public MTSImpl(State initialState) {
		super(initialState);
	}

	public boolean addPossible(State from, Action label, State to) {
		this.validateNewTransition(from, label, to);
		boolean added = this.getTransitionsForInternalUpdate(from,
				TransitionType.POSSIBLE).addPair(label, to);
		if (added) {
			this.getTransitionsForInternalUpdate(from, TransitionType.MAYBE)
					.addPair(label, to);
		}
		return added;
	}

	public boolean addRequired(State from, Action label, State to) {
		this.validateNewTransition(from, label, to);
		this.getTransitionsForInternalUpdate(from, TransitionType.POSSIBLE)
				.addPair(label, to);
		boolean added = this.getTransitionsForInternalUpdate(from,
				TransitionType.REQUIRED).addPair(label, to);
		if (added) {
			this.getTransitionsForInternalUpdate(from, TransitionType.MAYBE)
					.removePair(label, to);
		}
		return added;
	}

	public boolean addState(State state) {
		if (super.addState(state)) {
			for (TransitionType type : TransitionType.values()) {
				this.getTrasitionsByType().get(type).put(state,
						this.newRelation());
			}
			return true;
		}
		return false;
	}

	public boolean addTransition(State from, Action label, State to,
			TransitionType type) {
		this.validateNewTransition(from, label, to);
		return type.addTransition(this, from, label, to);
	}

	public BinaryRelation<Action, State> getTransitions(State state,
			TransitionType type) {
		return this.getTransitions(type).get(state);
	}

	protected BinaryRelation<Action, State> getTransitionsForInternalUpdate(
			State state, TransitionType type) {
		return this.getTransitions(state, type);
	}

	public Map<State, BinaryRelation<Action, State>> getTransitions(
			TransitionType type) {
		return this.getTrasitionsByType().get(type);
	}

	protected EnumMap<TransitionType, Map<State, BinaryRelation<Action, State>>> getTrasitionsByType() {
		if (this.trasitionsByType == null) {
			this
					.setTrasitionsByType(new EnumMap<TransitionType, Map<State, BinaryRelation<Action, State>>>(
							TransitionType.class));
			for (TransitionType type : TransitionType.values()) {
				this.trasitionsByType.put(type,
						new HashMap<State, BinaryRelation<Action, State>>());
			}
		}
		return this.trasitionsByType;
	}

	protected void setTrasitionsByType(
			EnumMap<TransitionType, Map<State, BinaryRelation<Action, State>>> trasitionsByType) {
		this.trasitionsByType = trasitionsByType;
	}

	private void validateExistingTransition(State from, Action label, State to,
			TransitionType possible) {
		Validate.isTrue(getTransitions(from, possible).contains(
				Pair.create(label, to)));
	}

	/**
	 * Elimina la transicion si existe y ademas elimina los posibles estados que
	 * quedaran huerfanos luego de borrar la transicion.
	 * 
	 */
	public boolean removeTransition(State from, Action label, State to,
			TransitionType type) {
		this.validateNewTransition(from, label, to);
		this.validateExistingTransition(from, label, to, type);
		return type.removeTransition(this, from, label, to);
	}

	public boolean removePossible(State from, Action label, State to) {
		boolean removed = this.getTransitionsForInternalUpdate(from,
				TransitionType.POSSIBLE).removePair(label, to);
		if (removed) {
			this.getTransitionsForInternalUpdate(from, TransitionType.MAYBE)
					.removePair(label, to);
			this.getTransitionsForInternalUpdate(from, TransitionType.REQUIRED)
					.removePair(label, to);
		}
		return removed;
	}

	public boolean removeRequired(State from, Action label, State to) {
		boolean removed = this.getTransitionsForInternalUpdate(from,
				TransitionType.REQUIRED).removePair(label, to);
		if (removed) {
			removed &= this.getTransitionsForInternalUpdate(from,
					TransitionType.POSSIBLE).removePair(label, to);
		}
		return removed;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("States: ").append(this.getStates()).append("\r\n");
		sb.append("Actions: ").append(this.getActions()).append("\r\n");
		sb.append("Required Transitions: ").append(
				this.getTransitions(TransitionType.REQUIRED)).append("\r\n");
		sb.append("Maybe Transitions: ").append(
				this.getTransitions(TransitionType.MAYBE)).append("\r\n");
		return sb.toString();
	}

	protected BinaryRelation<Action, State> getTransitionsFrom(State state) {
		return getTransitions(state, TransitionType.POSSIBLE);
	}

	public void removeAction(Action action) {
		if (!hasTransitionOn(action)) {
			getInternalActions().remove(action);
		}

	}

	private boolean hasTransitionOn(Action action) {
		for (State state : getStates()) {
			if (!getTransitionsFrom(state).getImage(action).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	// TODO Dipi, refactorizar
	// public Set<State> getReachableStatesBy(State state, TransitionType
	// transitionType) {
	// Set<State> reachableStates = new
	// HashSet<State>((int)(this.getStates().size()/.75f + 1),0.75f);
	// Queue<State> toProcess = new LinkedList<State>();
	// toProcess.offer(state);
	// reachableStates.add(state);
	// while(!toProcess.isEmpty()) {
	// for (Pair<Action, State> transition : getTransitions(toProcess.poll(),
	// transitionType)) {
	// if (!reachableStates.contains(transition.getSecond())) {
	// toProcess.offer(transition.getSecond());
	// reachableStates.add(transition.getSecond());
	// }
	// }
	// }
	// return reachableStates;
	// }
	//
	// @Override
	// protected Collection<State> getReachableStatesBy(State state) {
	// return getReachableStatesBy(state, TransitionType.POSSIBLE);
	// }

	@Override
	protected void removeTransitions(Collection<State> unreachableStates) {
		for (Map<State, BinaryRelation<Action, State>> transitions : this.trasitionsByType
				.values()) {
			this.removeTransitions(transitions, unreachableStates);
		}
	}
}
