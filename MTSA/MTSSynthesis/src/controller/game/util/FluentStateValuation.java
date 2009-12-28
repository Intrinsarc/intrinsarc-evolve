package controller.game.util;

import java.util.*;

import ar.dc.uba.model.condition.*;

public class FluentStateValuation<State> implements Valuation {

	private Map<State, Set<Fluent>> statesFromFluents = new HashMap<State, Set<Fluent>>();
	private State actualState;

	public FluentStateValuation(Set<State> states) {
		for (State state : states) {
			this.statesFromFluents.put(state, new HashSet<Fluent>());
		}
	}

	public void setActualState(State newActualState) {
		this.actualState = newActualState;
	}

	public boolean addHoldingFluent(State state, Fluent fluent) {
		return this.statesFromFluents.get(state).add(fluent);
	}

	public boolean isTrue(State state, Fluent fluent) {
		return this.statesFromFluents.get(state).contains(fluent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ar.dc.uba.model.condition.Valuation#getValuation(ar.dc.uba.model.condition
	 * .PropositionalVariable)
	 */
	@Override
	public boolean getValuation(PropositionalVariable variable) {
		for (Fluent fluent : this.statesFromFluents.get(this.actualState)) {
			if (fluent.getName().equals(variable.getName())) {
				return true;
			}
		}
		return false;
	}
}
