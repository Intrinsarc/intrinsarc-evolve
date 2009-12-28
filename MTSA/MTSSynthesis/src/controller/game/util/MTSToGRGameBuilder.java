package controller.game.util;

import java.util.*;

import org.apache.commons.lang.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.MTS.*;
import ar.dc.uba.model.condition.*;
import ar.dc.uba.model.language.*;
import controller.game.model.*;
import controller.model.*;

/**
 * Builds a game.
 * 
 * @author dipi
 * 
 */
public class MTSToGRGameBuilder {

	private static MTSToGRGameBuilder instance = new MTSToGRGameBuilder();

	public static <State, Action> MTSToGRGameBuilder getInstance() {
		return instance;
	}

	private MTSToGRGameBuilder() {
	}

	/**
	 * Builds a GRGame from an MTS. The MTS is assumed to be an LTS
	 * representation (i.e. only required transitions).
	 * 
	 */
	public <State, Action> Game<State> buildGameFrom(MTS<State, Action> mts,
			ControllerGoal goal) {
		this.validateActions(mts, goal);
		Game<State> game = new Game<State>(mts.getStates());
		FluentStateValuation<State> valuation = FluentUtils.getInstance()
				.buildValuation(mts, goal.getFluents());
		game.setFaults(this.formulaToStateSet(mts.getStates(),
				goal.getFaults(), valuation));
		game.setAssumptions(this.formulasToAssumptions(mts.getStates(), goal
				.getAssumptions(), valuation));
		game.setGuarantees(this.formulasToGuarantees(mts.getStates(), goal
				.getGuarantees(), valuation));
		this.initialiseSuccessors(mts, goal, game);

		return game;
	}

	// TODO: I am handling only the first fault formula.
	// Otherwise, it is not clear how to handle a collection?
	private <State, Action> Set<State> formulaToStateSet(Set<State> states,
			Set<Formula> formulas, FluentStateValuation<State> valuation) {

		Set<State> faults = new HashSet<State>();
		for (Formula formula : formulas) {
			for (State state : states) {
				valuation.setActualState(state);
				if (formula.evaluate(valuation)) {
					faults.add(state);
				}
			}
			Validate.isTrue(!faults.isEmpty(),
					"There is no state satisfying formula:" + formula);
			return faults;
		}
		return faults;
	}

	private <State, Action> void validateActions(MTS<State, Action> mts,
			ControllerGoal goal) {
		Set<Action> actions = mts.getActions();
		Validate
				.isTrue(actions.containsAll(goal.getControllableActions()),
						"\n Goal controllable actions does not belong to the mts action set.");
		for (Fluent fluent : goal.getFluents()) {
			this.validateFluentSymbols(fluent, actions, fluent
					.getInitiatingActions());
			this.validateFluentSymbols(fluent, actions, fluent
					.getTerminatingActions());
		}
	}

	private <Action> void validateFluentSymbols(Fluent fluent,
			Set<Action> actions, Set<Symbol> initiatingActions) {
		for (Symbol symbol : initiatingActions) {
			Validate
					.isTrue(
							actions.contains(symbol.toString()),
							"\n Every action in "
									+ fluent
									+ " must be included in model action set. \n Action: "
									+ symbol.toString()
									+ " does not belong to actions set.");
		}
	}

	private <State, Action> void initialiseSuccessors(MTS<State, Action> mts,
			ControllerGoal goal, Game<State> game) {
		Set<String> controllableActions = goal.getControllableActions();

		for (State from : mts.getStates()) {
			for (Pair<Action, State> tr : mts.getTransitions(from,
					TransitionType.REQUIRED)) {
				State to = tr.getSecond();
				if (!controllableActions.contains(tr.getFirst())) {
					game.addUncontrollableSuccessor(from, to);
				} else {
					game.addControllableSuccessor(from, to);
				}
			}
			if (!game.isUncontrollable(from)) {
				for (State state : game.getControllableSuccessors(from)) {
					game.addPredecessor(from, state);
				}
			}
		}
	}

	private <State, Action> Assumptions<State> formulasToAssumptions(
			Set<State> states, Set<Formula> formulas,
			FluentStateValuation<State> valuation) {

		Assumptions<State> assumptions = new Assumptions<State>();
		for (Formula formula : formulas) {
			Assume<State> assume = new Assume<State>();
			for (State state : states) {
				valuation.setActualState(state);
				if (formula.evaluate(valuation)) {
					assume.addState(state);
				}
			}
			Validate.isTrue(!assume.isEmpty(),
					"There is no state satisfying formula:" + formula);
			assumptions.addAssume(assume);
		}

		return assumptions;
	}

	private <State, Action> Guarantees<State> formulasToGuarantees(
			Set<State> states, Set<Formula> formulas,
			FluentStateValuation<State> valuation) {

		Guarantees<State> guarantees = new Guarantees<State>();
		for (Formula formula : formulas) {
			Guarantee<State> guarantee = new Guarantee<State>();
			for (State state : states) {
				valuation.setActualState(state);
				if (formula.evaluate(valuation)) {
					guarantee.addState(state);
				}
			}
			Validate.isTrue(!guarantee.isEmpty(),
					"There is no state satisfying formula:" + formula);
			guarantees.addGuarantee(guarantee);
		}

		return guarantees;
	}
}