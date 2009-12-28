package ac.ic.doc.mtstools.facade;

import java.util.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.MTS.*;
import ac.ic.doc.mtstools.model.impl.*;
import ac.ic.doc.mtstools.model.operations.*;
import ac.ic.doc.mtstools.model.operations.impl.*;
import ac.ic.doc.mtstools.utils.*;

/**
 * This class it's the entry point of every functionality developed in MTSA
 * Core.
 * 
 */
public class MTSAFacade {

	/**
	 * Returns the optimistic version of the model <code>mts</code>.
	 * 
	 */
	public static MTS<Long, String> getOptimisticModel(MTS<Long, String> mts) {
		MTS<Long, String> optimistic = getMTSByTransitionType(mts,
				TransitionType.POSSIBLE);
		deleteTransitionsToDeadlock(optimistic);
		return optimistic;
	}

	/**
	 * Returns the pessimistic version of the model <code>mts</code>.
	 */
	public static MTS<Long, String> getPesimisticModel(MTS<Long, String> mts) {
		MTS<Long, String> pessimistic = getMTSByTransitionType(mts,
				TransitionType.REQUIRED);
		deleteTransitionsToDeadlock(pessimistic);
		return pessimistic;
	}

	/**
	 * Removes every trace to deadlock.
	 * 
	 */
	public static <State, Action> void deleteTransitionsToDeadlock(
			MTS<State, Action> mtsWithoutDeadlock) {
		MTSDeadLockManipulator<State, Action> deadlockManipulator = new MTSDeadLockManipulatorImpl<State, Action>();
		deadlockManipulator.deleteTransitionsToDeadlock(mtsWithoutDeadlock);
	}

	/**
	 * Returns an MTS only with transitions of <code>transitionType</code> type.
	 * 
	 */
	private static MTS<Long, String> getMTSByTransitionType(
			MTS<Long, String> mts, TransitionType transitionType) {
		MTSImpl<Long, String> result = new MTSImpl<Long, String>(mts
				.getInitialState());
		for (String action : mts.getActions()) {
			result.addAction(action);
		}

		for (Long state : mts.getStates()) {
			result.addState(state);
			for (Pair<String, Long> transition : mts.getTransitions(state,
					transitionType)) {
				result.addState(transition.getSecond());
				result.addTransition(state, transition.getFirst(), transition
						.getSecond(), TransitionType.REQUIRED);
			}
		}
		return result;
	}

	/**
	 * Fills the <code>trace</code> parameter with a trace to deadlock and
	 * returns 1 if every implementation have a deadlock state, 2 if every
	 * implementation is deadlock free, and 3 otherwise.
	 * 
	 */
	public static <Action, State> int getTraceToDeadlock(
			MTS<State, Action> mts, MTSTrace<Action, State> trace) {
		return new MTSDeadLockManipulatorImpl<State, Action>()
				.getDeadlockStatus(mts);
	}

	/**
	 * Apply the closure model hiding the <code>silentActions</code>.
	 * 
	 * @return
	 */
	public static <State, Action> void applyClosure(MTS<State, Action> mts,
			Set<Action> silentActions) {
		MTSClosure closureBuilder = new MTSClosureBuilder();
		closureBuilder.applyMTSClosure(mts, silentActions);
	}

	public static <State, Action> void hide(MTS<State, Action> mts,
			Action internalAction, Set<Action> silentActions) {
		MTSUtils.hide(mts, silentActions, internalAction);
	}

	public static <State, Action> void removeSilentTransitions(
			MTS<State, Action> plant, Action toHide) {
		MTSUtils.removeSilentTransitions(plant, toHide);
	}
}
