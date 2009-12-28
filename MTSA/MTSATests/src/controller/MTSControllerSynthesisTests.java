package controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;
import lts.controller.MTSControllerSynthesisFacade;
import ui.StandardOutput;
import ac.ic.doc.commons.relations.Pair;
import ac.ic.doc.mtstools.model.MTS;
import ac.ic.doc.mtstools.model.MTSConstants;
import ac.ic.doc.mtstools.model.impl.MTSImpl;
import ac.ic.doc.mtstools.model.impl.WeakSemantics;
import ar.dc.uba.model.condition.Fluent;
import ar.dc.uba.model.condition.FluentImpl;
import ar.dc.uba.model.condition.FluentPropositionalVariable;
import controller.game.gr.GRGameSolver;
import controller.game.gr.GRRankSystem;
import controller.game.model.Game;
import controller.game.model.GameSolver;
import controller.game.util.MTSToGRGameBuilder;
import controller.model.ControllerGoal;
import controller.model.gr.GRControllerGoal;
import dispatcher.TransitionSystemDispatcher;

public class MTSControllerSynthesisTests extends TestCase {
	/*
	 * Case: From a simple MTS build the correct representing game.
	 */
	public void testSimpleMTStoGame() throws Exception {
		Long state0 = 0L;
		Long state1 = 1L;
		Long state2 = 2L;

		HashSet<String> controllableActions = new HashSet<String>();
		controllableActions.add("a");

		MTS<Long, String> mts = new MTSImpl<Long, String>(state0);
		mts.addAction("a");
		mts.addAction("b");
		mts.addAction("c");
		mts.addState(state1);
		mts.addState(state2);
		mts.addRequired(state0, "a", state1);
		mts.addRequired(state1, "b", state2);
		mts.addRequired(state2, "c", state0);
		
		ControllerGoal goal = new GRControllerGoal();
		goal.addAllControllableActions(controllableActions);

		Fluent fluentA = new FluentImpl("fluentA", 
				ControllerTestsUtils.buildSymbolSetFrom(new String[]{"a"}),
				ControllerTestsUtils.buildSymbolSetFrom(new String[]{"b", "c"}), 
				false);
		Fluent fluentB = new FluentImpl("fluentB", 
				ControllerTestsUtils.buildSymbolSetFrom(new String[]{"b"}),
				ControllerTestsUtils.buildSymbolSetFrom(new String[]{"a", "c"}), 
				false);
		Fluent fluentC = new FluentImpl("fluentC", 
				ControllerTestsUtils.buildSymbolSetFrom(new String[]{"c"}),
				ControllerTestsUtils.buildSymbolSetFrom(new String[]{"a", "b"}), 
				true);
		
		Set<Fluent> fluents = new HashSet<Fluent>();
		fluents.add(fluentA);
		fluents.add(fluentB);
		fluents.add(fluentC);
		
		goal.addAllFluents(fluents);
		
		goal.addAssume(new FluentPropositionalVariable(fluentC));
		goal.addGuarantee(new FluentPropositionalVariable(fluentB));
		
		MTSToGRGameBuilder builder = MTSToGRGameBuilder.getInstance();
		Game<Long> game = builder.buildGameFrom(mts, goal);
		
		GRRankSystem<Long> rankSystem = new GRRankSystem<Long>(game.getStates(), game.getGuarantees(), game.getAssumptions(), Collections.EMPTY_SET);		
		
		GameSolver<Long, Pair<Long, Integer>> solver = new GRGameSolver<Long>(game, rankSystem);
		Map<Pair<Long, Integer>, Set<Pair<Long, Integer>>> strategy = solver.buildStrategy();

		assertEquals(game.getStates(), solver.getWinningStates());
		
		assertEquals(Collections.singleton(Pair.create(state1,1)), strategy.get(Pair.create(state0,1)));
		assertEquals(Collections.singleton(Pair.create(state2,1)), strategy.get(Pair.create(state1,1)));
		assertEquals(Collections.singleton(Pair.create(state0,1)), strategy.get(Pair.create(state2,1)));
		
		MTS<Long, String> synMTS = MTSControllerSynthesisFacade.getInstance().synthesisePlainStateController(mts, goal);

		WeakSemantics weakSemantics = new WeakSemantics(Collections.singleton(MTSConstants.TAU));

		boolean refinement = TransitionSystemDispatcher.isRefinement(
				synMTS, " synthesised ", mts, " original " , weakSemantics, 
				new StandardOutput());
		
		assertTrue(refinement);
	}
}
