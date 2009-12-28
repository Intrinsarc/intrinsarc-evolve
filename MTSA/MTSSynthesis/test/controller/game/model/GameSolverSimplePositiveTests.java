package controller.game.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ac.ic.doc.commons.relations.Pair;
import controller.game.gr.GRGameSolver;
import controller.game.gr.GRRankSystem;

public class GameSolverSimplePositiveTests extends GameSolverBaseTestCase {
	
//	public void testTwoGuarantees() throws Exception {
//		long state1 = 1L;
//		long state2 = 2L;
//		long state3 = 3L;
//		long state4 = 4L;
//		long state5 = 5L;
//
//		Set<Long> states = new HashSet<Long>();	states.add(state1);	states.add(state2); states.add(state3);
//		states.add(state4); states.add(state5);
//
//		Assumptions<Long> assumptions =  new Assumptions<Long>();
//		Assume<Long> assume = new Assume<Long>();
//		assume.addState(state1);
//		assume.addState(state2);
//		assume.addState(state5);
//		assumptions.addAssume(assume);
//		
//		Guarantees<Long> guarantees = new Guarantees<Long>();
//		guarantees.addGuarantee(buildGuarantee(new Long[]{state1, state3}));
//		guarantees.addGuarantee(buildGuarantee(state5));
//		
//		Set<Long> faults = new HashSet<Long>();
//		Game<Long> game = new Game<Long>(states);
//		game.addControllableSuccessor(state1, state2);
//		game.addControllableSuccessor(state2, state1);
//		game.addControllableSuccessor(state3, state4);
//		game.addControllableSuccessor(state4, state3);
//		game.addControllableSuccessor(state2, state5);
//		game.addControllableSuccessor(state5, state2);
//		
//		game.addUncontrollableSuccessor(state1, state3);
//		game.addUncontrollableSuccessor(state3, state1);
//		game.addUncontrollableSuccessor(state2, state4);
//		game.addUncontrollableSuccessor(state4, state2);
//				
//		game.setAssumptions(assumptions);
//		game.setGuarantees(guarantees);
//		
//		this.fillPredecessors(game);
//		
//		GRRankSystem<Long> rankSystem = new GRRankSystem<Long>(states, guarantees, assumptions,faults);		
//		
//		GameSolver<Long, Pair<Long, Integer>> solver = new GRGameSolver<Long>(game, rankSystem);
//		Map<Pair<Long, Integer>, Set<Pair<Long, Integer>>> strategy = solver.buildStrategy();
//
//		System.out.println(rankSystem);
//		System.out.println(strategy);
//		System.out.println(solver.getWinningStates());
//	}


	protected Guarantee<Long> buildGuarantee(Long[] longs) {
		Guarantee<Long> result = new Guarantee<Long>();
		for (int i = 0; i < longs.length; i++) {
			result.addState(longs[i]);
		}
		return result;
	}


//	public void testSimpleGame3() throws Exception {
//		long state1 = 1L;
//		long state2 = 2L;
//
//		Set<Long> states = new HashSet<Long>();	states.add(state1);	states.add(state2);
//
//		Guarantees<Long> guarantees = buildSingleStateGuarantee(state2);
//		Assumptions<Long> assumptions = new Assumptions<Long>();
//		Assume<Long> assume = new Assume<Long>();
//		assume.addState(state1);
//		assume.addState(state2);
//		assumptions.addAssume(assume);
//
//		Set<Long> faults = new HashSet<Long>();
//		
//		Game<Long> game = new Game<Long>(states);
//		game.addControllableSuccessor(state1, state2);
//		game.addUncontrollableSuccessor(state2, state1);
//		game.setAssumptions(assumptions);
//		game.setGuarantees(guarantees);
//		
//		GRRankSystem<Long> rankSystem = new GRRankSystem<Long>(states, guarantees, assumptions,faults);		
//		
//		GameSolver<Long, Pair<Long, Integer>> solver = new GRGameSolver<Long>(game, rankSystem);
//		Map<Pair<Long, Integer>, Set<Pair<Long, Integer>>> strategy = solver.buildStrategy();
//		
//		assertEquals(states, solver.getWinningStates());
//		
//		assertEquals(strategy.get(Pair.create(state1,1)), Collections.singleton(Pair.create(state2,1)));
//		assertEquals(strategy.get(Pair.create(state2,1)), Collections.singleton(Pair.create(state1,1)));
//	}
//	
//	/*
//	 * CASE 1: This is a very simple case, with 3 states: state1 -> state2 -> state3
//	 * state 2 is uncontrollable. Assumptions: assume1: states (play with), 
//	 * Guarantees: gurantee1=state3. With this set up, works fine.
//	 * It works as well if we add state3 to the assume (one assume the two states). 
//	 * 
//	 * CASE 2: Just play adding different combinations of states to the assume
//	 * you'll see that there's a strange behaviour. 
//	 * 
//	 */
//	public void testSimpleGame2() throws Exception {
//		long state1 = 1L;
//		long state2 = 2L;
//		long state3 = 3L;
//
//		Set<Long> states = new HashSet<Long>();	states.add(state1);	states.add(state2); states.add(state3);
//
//		Assumptions<Long> assumptions =  new Assumptions<Long>();
//		Assume<Long> assume = new Assume<Long>();
//		assume.addState(state1);
//		//if we add more than one state to this assumption the test fails.
//		assume.addState(state2);
////		assume.addState(state3);
//		assumptions.addAssume(assume);
////		Guarantees<Long> guarantees = buildSingleStateGuarantee(state3);
//		Guarantees<Long> guarantees = new Guarantees<Long>();
//		Guarantee<Long> guarantee = buildGuarantee(state1);
//		guarantee.addState(state3);
//		guarantees.addGuarantee(guarantee);
//
//		Set<Long> faults = new HashSet<Long>();
//		
//		Game<Long> game = new Game<Long>(states);
//		game.addControllableSuccessor(state1, state2);
//		game.addControllableSuccessor(state3, state1);
//		
//		game.addUncontrollableSuccessor(state2, state3);
//
//		game.setAssumptions(assumptions);
//		game.setGuarantees(guarantees);
//		
//		GRRankSystem<Long> rankSystem = new GRRankSystem<Long>(states, guarantees, assumptions,faults);		
//		
//		GameSolver<Long, Pair<Long, Integer>> solver = new GRGameSolver<Long>(game, rankSystem);
//		Map<Pair<Long, Integer>, Set<Pair<Long, Integer>>> strategy = solver.buildStrategy();
//	
//		System.out.println(rankSystem);
//		System.out.println(strategy);
//		System.out.println(solver.getWinningStates());
//		
//		assertEquals(states, solver.getWinningStates());
//		
//		assertEquals(Collections.singleton(Pair.create(state2,1)), strategy.get(Pair.create(state1,1)));
//		assertEquals(Collections.singleton(Pair.create(state3,1)), strategy.get(Pair.create(state2,1)));
//		assertEquals(Collections.singleton(Pair.create(state1,1)), strategy.get(Pair.create(state3,1)));
//	}

	/*
	 * CASE 1: This case has a couple of variations. The simplest one is the one that's 
	 * runnable at the moment, which is basically 3 states in a "cycle" 
	 * of the form: state1 -> state2 -> state3 -> state1
	 * with states 1 and 3 controllable and state 2 uncontrollable. 
	 * Assumptions: 1= state1, Guarantees: 1= state3
	 * Using this configuration we get exactly what we wanted 
	 * a strategy such that it's the game. There's no choice for the players.
	 * 
	 *  CASE 2: If we delete the comment lines related to state4 (except for state4 -> state4 .. cycle)
	 *  we get the same game but with a dead end in state4 and again we get a correct strategy.
	 *  
	 *  CASE 3: If we add every state (from 1 to 4) to the assume of the game, it breaks the algorithm. 
	 *  Independently of the algorithm. It's odd, because if we have a dead end as part of an assume
	 *  then there should be a strategy because there is no way to ensure that always eventually assume 
	 *  holds. Am I correct?
	 *  
	 *  Note: I delete the "atLeastOne" check, to see what is the "output". 
	 */
	public void testSimpleGame21() throws Exception {
		long state1 = 1L;
		long state2 = 2L;
		long state3 = 3L;
		long state4 = 4L;

		Set<Long> states = new HashSet<Long>();	states.add(state1);	states.add(state2); states.add(state3);
		states.add(state4);

		Assumptions<Long> assumptions =  new Assumptions<Long>();
		Assume<Long> assume = new Assume<Long>();
		assume.addState(state1);
		assume.addState(state2);
		assume.addState(state3);
		assume.addState(state4);
		
		assumptions.addAssume(assume);
		Guarantees<Long> guarantees = buildSingleStateGuarantee(state3);

		Set<Long> faults = new HashSet<Long>();
		
		Game<Long> game = new Game<Long>(states);
		game.addControllableSuccessor(state1, state2);
		game.addControllableSuccessor(state3, state1);
		game.addControllableSuccessor(state3, state4);
		
		game.addUncontrollableSuccessor(state2, state3);
//		game.addUncontrollableSuccessor(state4, state4);
				
		game.setAssumptions(assumptions);
		game.setGuarantees(guarantees);
		
		GRRankSystem<Long> rankSystem = new GRRankSystem<Long>(states, guarantees, assumptions,faults);		
		
		GameSolver<Long, Pair<Long, Integer>> solver = new GRGameSolver<Long>(game, rankSystem);
		Map<Pair<Long, Integer>, Set<Pair<Long, Integer>>> strategy = solver.buildStrategy();

		System.out.println(rankSystem);
		System.out.println(strategy);
		System.out.println(solver.getWinningStates());
		
		states.remove(state4);
		
		assertEquals(states, solver.getWinningStates());
		
		assertEquals(Collections.singleton(Pair.create(state2,1)), strategy.get(Pair.create(state1,1)));
		assertEquals(Collections.singleton(Pair.create(state3,1)), strategy.get(Pair.create(state2,1)));
		assertEquals(Collections.singleton(Pair.create(state1,1)), strategy.get(Pair.create(state3,1)));
	}
	

//	/*
//	 * This is a very small game. Two states: 
//	 * state 1 -> state2 -> state1 ....
//	 * state1 controllable
//	 * state2 uncontrollable
//	 * 
//	 * In this case I needed to change the infinite 
//	 * value from context.height to context.height+1.
//	 * which I think it's not wrong. 
//	 * Assumptions, just one = state1
//	 * Guarantees, just one = state2
//	 *  
//	 */
//	public void testSimpleGame1() throws Exception {
//		long state1 = 1L;
//		long state2 = 2L;
//
//		Set<Long> states = new HashSet<Long>();	states.add(state1);	states.add(state2);
//
//		Assumptions<Long> assumptions = buildSingleStateAssumption(state1);
//		Guarantees<Long> guarantees = buildSingleStateGuarantee(state2);
//		Set<Long> faults = new HashSet<Long>();
//		
//		//DIPI RankSystem and Game sees the same things, 
//		//I should check the design for this. 
//		Game<Long> game = new Game<Long>(states);
//		game.addControllableSuccessor(state1, state2);
//		game.addUncontrollableSuccessor(state2, state1);
//		
//		game.setAssumptions(assumptions);
//		game.setGuarantees(guarantees);
//		
//		GRRankSystem<Long> rankSystem = new GRRankSystem<Long>(states, guarantees, assumptions,faults);		
//		
//		GameSolver<Long, Pair<Long, Integer>> solver = new GRGameSolver<Long>(game, rankSystem);
//		Map<Pair<Long, Integer>, Set<Pair<Long, Integer>>> strategy = solver.buildStrategy();
//		
//		assertEquals(states, solver.getWinningStates());
//		
//		assertEquals(strategy.get(Pair.create(state1,1)), Collections.singleton(Pair.create(state2,1)));
//		assertEquals(strategy.get(Pair.create(state2,1)), Collections.singleton(Pair.create(state1,1)));
//		
//	}
}
