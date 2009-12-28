package controller.game.util;

import java.util.*;

import controller.game.model.*;

public class GameValidationHelper {
	public static <State> boolean areValidAssumptions(Game<State> game,
			Assumptions<State> assumptions) {
		for (int i = 1; i <= assumptions.getSize(); ++i) {
			Assume<State> assume = assumptions.getAssume(i);
			if (!game.getStates().containsAll(assume.getStateSet())) {
				return false;
			}
		}
		return true;
	}

	public static <State> boolean areValidGuarantees(Game<State> game,
			Guarantees<State> guarantees) {
		for (int i = 1; i <= guarantees.getSize(); ++i) {
			Guarantee<State> guarantee = guarantees.getGuarantee(i);
			if (!game.getStates().containsAll(guarantee.getStateSet())) {
				return false;
			}
		}
		return true;
	}

	public static <State> boolean areValid(Game<State> game, Set<State> stateSet) {

		if (!game.getStates().containsAll(stateSet)) {
			return false;
		}
		return true;
	}

}
