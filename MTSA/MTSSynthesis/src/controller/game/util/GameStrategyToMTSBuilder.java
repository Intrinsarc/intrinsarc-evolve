package controller.game.util;

import java.util.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.MTS.*;
import ac.ic.doc.mtstools.model.impl.*;

public class GameStrategyToMTSBuilder {
	private static GameStrategyToMTSBuilder instance = new GameStrategyToMTSBuilder();

	public static GameStrategyToMTSBuilder getInstance() {
		return instance;
	}

	private GameStrategyToMTSBuilder() {
	}

	public <State, Action> MTS<Pair<State, Integer>, Action> buildMTSFrom(
			MTS<State, Action> mts,
			Map<Pair<State, Integer>, Set<Pair<State, Integer>>> strategy) {
		Pair<State, Integer> initialState = Pair.create(mts.getInitialState(),
				1);
		MTS<Pair<State, Integer>, Action> result = new MTSImpl<Pair<State, Integer>, Action>(
				initialState);
		result.addStates(strategy.keySet());
		result.addActions(mts.getActions());

		for (Pair<State, Integer> strategyState : strategy.keySet()) {
			for (Pair<Action, State> transition : mts.getTransitions(
					strategyState.getFirst(), TransitionType.REQUIRED)) {

				State to = transition.getSecond();

				Pair<State, Integer> rankedState = this.getRankedState(to,
						strategy.get(strategyState));
				if (rankedState != null) {
					if (!result.getStates().contains(rankedState)) {
						throw new RuntimeException(
								"The strategy built has dead end states. \n State "
										+ rankedState + " is a deadlock state.");
					}
					result.addTransition(strategyState, transition.getFirst(),
							rankedState, TransitionType.REQUIRED);
				}
			}
		}

		// StringBuffer sb = new StringBuffer();
		// for (Pair<State, Integer> state: result.getStates()) {
		// if (result.getTransitions(state, TransitionType.REQUIRED).size()<=0)
		// {
		// sb.append("\n"+state);
		// }
		// }
		// String out = sb.toString();
		// if (!out.isEmpty()) throw new RuntimeException("Deadlock states: " +
		// out);
		// if(result.removeUnreachableStates())
		// System.err.println("Controller has unreachable states.");

		return result;
	}

	public <State> Pair<State, Integer> getRankedState(State state,
			Set<Pair<State, Integer>> set) {
		for (Pair<State, Integer> pair : set) {
			if (pair.getFirst().equals(state)) {
				return pair;
			}
		}
		return null;
	}
}
