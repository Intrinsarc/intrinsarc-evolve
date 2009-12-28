package controller;

import java.util.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;
import controller.game.gr.*;
import controller.game.model.*;
import controller.game.util.*;
import controller.model.*;

public class ControllerSynthesiser {

	private static ControllerSynthesiser instance = new ControllerSynthesiser();

	public static ControllerSynthesiser getInstance() {
		return instance;
	}

	private ControllerSynthesiser() {
	}

	public <State, Action> MTS<Pair<State, Integer>, Action> synthesise(
			MTS<State, Action> mts, ControllerGoal goal) {

		Game<State> game = MTSToGRGameBuilder.getInstance().buildGameFrom(mts,
				goal);
		GRRankSystem<State> system = new GRRankSystem<State>(game.getStates(),
				game.getGuarantees(), game.getAssumptions(), game.getFaults());

		GameSolver<State, Pair<State, Integer>> solver = new GRGameSolver<State>(
				game, system);
		solver.solveGame();
		if (solver.isWinning(mts.getInitialState())) {
			Map<Pair<State, Integer>, Set<Pair<State, Integer>>> strategy = solver
					.buildStrategy();
			return GameStrategyToMTSBuilder.getInstance().buildMTSFrom(mts,
					strategy);
		} else {
			return null;
		}
	}
}
