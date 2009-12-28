package ac.ic.doc.mtstools.model.operations.impl;

import java.util.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.MTS.*;
import ac.ic.doc.mtstools.model.operations.*;

public class MTSClosureBuilder implements MTSClosure {

	public <State, Action> void applyMTSClosure(MTS<State, Action> mts,
			Set<Action> silentActions) {
		if (!silentActions.isEmpty()) {
			for (State state : mts.getStates()) {
				for (Action action : mts.getActions()) {
					TransitionType transitionType = TransitionType.REQUIRED;
					if (!silentActions.contains(action)) {

						Iterator<List<Pair<Action, State>>> iterator = getIterator(
								mts, state, action, transitionType,
								silentActions);
						while (iterator.hasNext()) {
							List<Pair<Action, State>> transitions = iterator
									.next();
							Pair<Action, State> transition = transitions.get(1);
							State stateTo = transition.getSecond();
							mts.addRequired(state, action, stateTo);
						}

						transitionType = TransitionType.MAYBE;
						iterator = getIterator(mts, state, action,
								transitionType, silentActions);
						while (iterator.hasNext()) {
							List<Pair<Action, State>> transitions = iterator
									.next();
							Pair<Action, State> transition = transitions.get(1);
							State stateTo = transition.getSecond();
							mts.addPossible(state, action, stateTo);
						}
					} else {
						Set<Action> emptySilentActions = Collections.EMPTY_SET;

						Iterator<List<Pair<Action, State>>> iterator = getIterator(
								mts, state, action, transitionType,
								emptySilentActions);
						while (iterator.hasNext()) {
							List<Pair<Action, State>> transitions = iterator
									.next();
							Pair<Action, State> transition = transitions.get(1);
							State stateTo = transition.getSecond();
							mts.addRequired(state, action, stateTo);
						}

						transitionType = TransitionType.MAYBE;

						iterator = getIterator(mts, state, action,
								transitionType, emptySilentActions);
						while (iterator.hasNext()) {
							List<Pair<Action, State>> transitions = iterator
									.next();
							Pair<Action, State> transition = transitions.get(1);
							State stateTo = transition.getSecond();
							mts.addPossible(state, action, stateTo);
						}
					}
				}
			}
		}
	}

	protected <State, Action> Iterator<List<Pair<Action, State>>> getIterator(
			MTS<State, Action> mts, State state, Action action,
			TransitionType transitionType, Set<Action> emptySilentActions) {
		// return getClosurePathBuilder().getPathsIterator(mts, state, action,
		// transitionType, emptySilentActions);
		return getClosurePathBuilder().getProjectionIterator(mts, state,
				action, transitionType, emptySilentActions);

	}

	protected ProjectionBuilder getClosurePathBuilder() {
		// return ClousurePathBuilder.getInstance();
		return ProjectionBuilder.getInstance();
	}
}
