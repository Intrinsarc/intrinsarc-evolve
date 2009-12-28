package ac.ic.doc.mtstools.utils;

import java.util.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.MTS.*;
import ac.ic.doc.mtstools.model.impl.*;

/**
 * This class consists exclusively of static methods that operate on or return
 * MTS and LTS.
 */
public class MTSUtils {

	/**
	 * Returns true if <code>mts</code> has tau transitions
	 * 
	 */
	public static <State, Action> boolean hasTauTransitions(
			MTS<State, Action> mts) {
		for (State state : mts.getStates()) {
			for (Pair<Action, State> transition : mts.getTransitions(state,
					TransitionType.POSSIBLE)) {
				if (transition.getFirst().equals(MTSConstants.TAU)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns true if <code>mts</code> has no outgoing transitions from state
	 * <code>stateFrom</code> with <code>transitionType</code> type.
	 */
	public static <State, Action> boolean stateWithoutOutgoingChoices(
			MTS<State, Action> mts, State stateFrom,
			TransitionType transitionType) {
		return mts.getTransitions(stateFrom, transitionType).size() <= 1;
	}

	/**
	 */
	public static <State, Action> void hide(MTS<State, Action> mts,
			Set<Action> silentActions, Action internalAction) {
		for (Action action : silentActions) {
			for (State from : mts.getStates()) {
				for (State to : mts.getTransitions(from,
						TransitionType.REQUIRED).getImage(action)) {
					mts.removeRequired(from, action, to);
					mts.addRequired(from, internalAction, to);
				}
			}
		}
	}

	public static <State, Action> void removeSilentTransitions(
			MTS<State, Action> plant, Action toHide) {
		Set<MTSTransition<Action, State>> toDelete = new HashSet<MTSTransition<Action, State>>();

		for (State from : plant.getStates()) {
			// las acciones del ambiente son required (por ahora)
			Set<State> statesTo = plant.getTransitions(from,
					TransitionType.REQUIRED).getImage(toHide);
			for (State stateTo : statesTo) {
				toDelete.add(MTSTransition.createMTSEventState(from, toHide,
						stateTo));
			}
		}
		for (MTSTransition<Action, State> transition : toDelete) {
			plant.removeRequired(transition.getStateFrom(), transition
					.getEvent(), transition.getStateTo());
		}

	}

}
