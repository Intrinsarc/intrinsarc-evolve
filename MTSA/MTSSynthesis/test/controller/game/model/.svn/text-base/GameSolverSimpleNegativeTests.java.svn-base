package controller.game.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ac.ic.doc.commons.relations.Pair;
import controller.game.gr.GRGameSolver;
import controller.game.gr.GRRankSystem;

public class GameSolverSimpleNegativeTests extends GameSolverBaseTestCase {
	
	/*
	 * The state 2 (the only one in the only assumption) is the
	 * uncontrollable predecessor of state 3 (the only one in 
	 * the only guarantee). We start with 2 but after that we 
	 * add 1 and so on, but we never adds 5 (the other 
	 * uncontrollable successor of 2), this implies 
	 * that 5 never reaches the infinity rank and therefore
	 * 2 is never infinite. Hence the algorithm finds a 
	 * winning strategy. 
	 */
	public void testNoStrategyForGame1() throws Exception {
		long state1 = 1L;
		long state2 = 2L;
		long state3 = 3L;
		long state4 = 4L;
		long state5 = 5L;
		long state6 = 6L;

		Set<Long> states = new HashSet<Long>();	states.add(state1);	states.add(state2); states.add(state3);
		states.add(state4);	states.add(state5); states.add(state6);

		Assumptions<Long> assumptions =  new Assumptions<Long>();
		Assume<Long> assume = new Assume<Long>();
		assume.addState(state2);
		assumptions.addAssume(assume);
		Guarantees<Long> guarantees = buildSingleStateGuarantee(state3);

		Set<Long> faults = new HashSet<Long>();
		
		Game<Long> game = new Game<Long>(states);
		game.addControllableSuccessor(state1, state2);
		game.addControllableSuccessor(state3, state4);
		game.addControllableSuccessor(state6, state2);
		
		game.addUncontrollableSuccessor(state2, state3);
		game.addUncontrollableSuccessor(state2, state5);
		game.addUncontrollableSuccessor(state5, state6);
		game.addUncontrollableSuccessor(state4, state1);
		
		game.setAssumptions(assumptions);
		game.setGuarantees(guarantees);
		game.setFaults(faults);
		this.fillPredecessors(game);
		
		GRRankSystem<Long> rankSystem = new GRRankSystem<Long>(states, guarantees, assumptions, faults);		
		
		GRGameSolver<Long> solver = new GRGameSolver<Long>(game, rankSystem);
		Map<Pair<Long, Integer>, Set<Pair<Long, Integer>>> strategy = solver.buildStrategy();
	
		System.out.println(rankSystem);
		System.out.println(strategy);
		
		assertTrue(strategy.isEmpty());
	}
	/*
	 * This case sounds good. Has a deadlock state 
	 * and doesn't find a strategy
	 */
	public void testDeadlockForGame() throws Exception {
		long state1 = 1L;
		long state2 = 2L;
		long state3 = 3L;
		long state4 = 4L;
		long state5 = 5L;

		Set<Long> states = new HashSet<Long>();	states.add(state1);	states.add(state2); states.add(state3);
		states.add(state4);	states.add(state5); 

		Assumptions<Long> assumptions =  new Assumptions<Long>();
		Assume<Long> assume = new Assume<Long>();
		assume.addState(state1);
		assumptions.addAssume(assume);
		Guarantees<Long> guarantees = buildSingleStateGuarantee(state3);
		
		Set<Long> faults = new HashSet<Long>();
		
		Game<Long> game = new Game<Long>(states);
		game.addControllableSuccessor(state1, state2);
		game.addControllableSuccessor(state3, state4);
		
		game.addUncontrollableSuccessor(state2, state3);
		game.addUncontrollableSuccessor(state2, state5);
		game.addUncontrollableSuccessor(state4, state1);
		
		game.setAssumptions(assumptions);
		game.setGuarantees(guarantees);
		this.fillPredecessors(game);
		
		GRRankSystem<Long> rankSystem = new GRRankSystem<Long>(states, guarantees, assumptions,faults);		
		
		GRGameSolver<Long> solver = new GRGameSolver<Long>(game, rankSystem);
		Map<Pair<Long, Integer>, Set<Pair<Long, Integer>>> strategy = solver.buildStrategy();
	
		System.out.println(rankSystem);
		System.out.println(strategy);
		
		assertTrue(strategy.isEmpty());
	}
}
