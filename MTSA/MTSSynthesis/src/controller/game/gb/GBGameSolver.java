package controller.game.gb;

import java.util.*;

import org.apache.commons.lang.*;

import ac.ic.doc.commons.relations.*;
import controller.game.model.*;

/**
 * For the computations of successors and predecessors: A state s is
 * uncontrollable if it has some uncontrollable action enabled (at least one) A
 * state s is controllable if ALL the enabled actions are controllable Consider
 * a transition s --a--> t t is a successor of s if s is uncontrollable and a is
 * uncontrollable or if s is controllable (and then obviously a is controllable)
 * s is a predecessor of t if s is uncontrollable and a is uncontrollable or if
 * s is controllable (and then obviously a is controllable)
 */
public class GBGameSolver<State> implements
		GameSolver<State, Pair<State, Integer>> {
	private Game<State> game;
	private GBRankSystem<State> rankSystem;
	private boolean gameSolved;

	public GBGameSolver(Game<State> game, GBRankSystem<State> rankSystem) {
		this.game = game;
		this.rankSystem = rankSystem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.GameSolver#solveGame()
	 */
	public void solveGame() {

		Queue<Pair<State, Integer>> pending = new LinkedList<Pair<State, Integer>>();

		this.initialise(pending);

		// Handle the pending states
		while (!pending.isEmpty()) {

			Pair<State, Integer> pair = pending.poll();
			State state = pair.getFirst();
			int guaranteeId = pair.getSecond();

			// The current rank of the state s
			GBRank rank = this.rankSystem.getRank(state, guaranteeId);

			// If current rank is already infinity, it obviously should
			// not be increased.
			if (rank.isInfinity()) {
				continue;
			}

			// What is the best possible ranking that s could have according to
			// it's successors?
			GBRank bestRank = this.best(state, guaranteeId);

			// The existing ranking is already higher or equal then nothing
			// needs to be
			// done. Go to the next state in the set of pending
			if (bestRank.compareTo(rank) <= 0) {
				continue;
			}

			// set the new ranking of the state to the computed best value
			// If the new value is infinity it can be set for all rankings.
			if (bestRank.isInfinity()) {
				for (int i = 1; i <= this.game.getGuaranteesQuantity(); i++) {
					this.rankSystem.set(state, i, GBRank
							.getInfinityFor(this.rankSystem.getContext(i)));
				}
			} else {
				this.rankSystem.set(state, guaranteeId, bestRank);
			}

			addPredecessorsTo(pending, state, guaranteeId, bestRank);
		}
		this.gameSolved = true;
	}

	/*
	 * Handle predecessors. There is a more efficient implementation of this
	 * part by storing the best values and not recomputing them again and again
	 * whenever we meet a state. This is currently ignored.
	 */
	private void addPredecessorsTo(Collection<Pair<State, Integer>> pending,
			State state, int guaranteeId, GBRank bestRank) {

		Set<State> predecessors = this.game.getPredecessors(state);
		for (State pred : predecessors) {

			if (bestRank.isInfinity()) {
				if (this.game.isUncontrollable(pred)) {
					pending.add(Pair.create(pred, 1));
				} else {
					for (int i = 1; i <= this.game.getGuaranteesQuantity(); i++) {
						if (this.needsToBeUpdated(i, pred)) {
							addIfNotIn(pending, pred, i);
						}
					}
				}
			} else if (this.needsToBeUpdated(guaranteeId, pred)) {
				addIfNotIn(pending, pred, guaranteeId);

			}
		}
	}

	private void addIfNotIn(Collection<Pair<State, Integer>> pending,
			State state, int guaranteeId) {
		Pair<State, Integer> newRankedState = Pair.create(state, guaranteeId);
		if (!pending.contains(newRankedState)) {
			pending.add(newRankedState);
		}
	}

	private boolean needsToBeUpdated(int guaranteeId, State pred) {
		GBRank best = this.best(pred, guaranteeId);
		GBRank rank = this.rankSystem.getRank(pred, guaranteeId);
		return best.compareTo(rank) > 0;
	}

	// Increase the rank of dead ends to infinity.
	// Add their contoled predecessors to pending.
	// Add states that are not in guarantee and in assumption[1] to pending
	private void initialise(Collection<Pair<State, Integer>> pending) {
		// Set the rank of every state that is a dead end to infinity (i.e
		// safety goals included)
		// Add it's predecessors (if necessary) to the pending states
		for (State state : this.game.getStates()) {
			if (this.game.getControllableSuccessors(state).isEmpty()
					&& this.game.getUncontrollableSuccessors(state).isEmpty()) {
				for (int guaranteeId = 1; guaranteeId <= this.game
						.getGuaranteesQuantity(); guaranteeId++) {
					GBRank infinity = GBRank.getInfinityFor(this.rankSystem
							.getContext(guaranteeId));
					this.rankSystem.set(state, guaranteeId, infinity);
				}
				GBRank infinity = this.rankSystem.getRank(state, 1);
				this.addPredecessorsTo(pending, state, 1, infinity);
			}
		}

		Assume<State> firstAssumption = this.game.getAssumption(1);
		for (State state : this.game.getStates()) {
			for (int i = 1; i <= this.game.getGuaranteesQuantity(); i++) {
				if (!this.game.getGuarantee(i).contains(state)
						&& firstAssumption.contains(state)) {
					pending.add(Pair.create(state, i));
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.GameSolver#getWinningStates()
	 */
	public Set<State> getWinningStates() {
		Set<State> winning = new HashSet<State>();
		if (!gameSolved) {
			this.solveGame();
		}
		for (State state : this.game.getStates()) {
			boolean infinity = this.rankSystem.getRank(state, 1).isInfinity();
			if (!infinity) {
				winning.add(state);
			}
		}
		return winning;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.GameSolver#isWinning(State)
	 */
	public boolean isWinning(State state) {
		if (!gameSolved) {
			this.solveGame();
		}
		return !this.rankSystem.getRank(state, 1).isInfinity();
	}

	public boolean isBetterThan(State state, State succ, int guaranteeId) {
		Guarantee<State> guarantee = this.game.getGuarantee(guaranteeId);
		boolean stateInGuarantee = guarantee.contains(state);

		GBRank succRank = this.rankSystem.getRank(succ, this.getNextGuarantee(
				guaranteeId, state));
		GBRank stateRank = this.rankSystem.getRank(state, guaranteeId);
		int actualStateVSsuccessor = stateRank.compareTo(succRank);

		return ((stateInGuarantee && !succRank.isInfinity()) || (!stateInGuarantee && actualStateVSsuccessor > 0));
	}

	private int getNextGuarantee(int guaranteeId, State state) {
		if (this.game.getGuarantee(guaranteeId).contains(state)) {
			return this.increaseGuarantee(guaranteeId);
		} else
			return guaranteeId;
	}

	private int increaseGuarantee(int guaranteeId) {
		return (guaranteeId % this.game.getGuaranteesQuantity()) + 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.GameSolver#buildStrategy()
	 */
	public Map<Pair<State, Integer>, Set<Pair<State, Integer>>> buildStrategy() {
		if (!gameSolved) {
			this.solveGame();
		}

		Map<Pair<State, Integer>, Set<Pair<State, Integer>>> result = new HashMap<Pair<State, Integer>, Set<Pair<State, Integer>>>();

		Set<State> winningStates = this.getWinningStates();
		for (State state : winningStates) {
			for (int guaranteeId = 1; guaranteeId <= this.game
					.getGuaranteesQuantity(); guaranteeId++) {
				Pair<State, Integer> source = Pair.create(state, guaranteeId);

				int nextGuaranteeId = this.getNextGuarantee(guaranteeId, state);

				Set<Pair<State, Integer>> successors = new HashSet<Pair<State, Integer>>();
				if (this.game.isUncontrollable(state)) {
					for (State succ : this.game
							.getUncontrollableSuccessors(state)) {
						Validate.isTrue(this.isBetterThan(state, succ,
								guaranteeId), "State: " + succ
								+ " must have a better rank than state: "
								+ state);
						Pair<State, Integer> target = Pair.create(succ,
								nextGuaranteeId);
						successors.add(target);
					}

					for (State succ : this.game
							.getControllableSuccessors(state)) {
						if (this.isBetterThan(state, succ, guaranteeId)) {
							Pair<State, Integer> target = Pair.create(succ,
									nextGuaranteeId);
							successors.add(target);
							Validate.isTrue(this.isWinning(state), "is state: "
									+ state + " winning? 5");
							Validate.isTrue(this.isWinning(succ), "state: "
									+ succ + " it's not winning. 2");
						}
					}
				} else { // Controllable State
					boolean atLeastOne = false;
					for (State succ : this.game
							.getControllableSuccessors(state)) {
						if (this.isBetterThan(state, succ, guaranteeId)) {
							Pair<State, Integer> target = Pair.create(succ,
									nextGuaranteeId);
							successors.add(target);
							Validate.isTrue(this.isWinning(succ), "state: "
									+ succ + " it's not winning. 3");
							atLeastOne = true;
						}
					}
					Validate.isTrue(atLeastOne, "\n State:" + source
							+ " must have at least one successor.");
				}
				Validate.notEmpty(successors, "\n State:" + source
						+ " should have at least one successor.");
				result.put(source, successors);
			}
		}

		return result;
	}

	/**
	 * (Main ...) What is the best possible value that the rank of a state can
	 * be?
	 */
	private GBRank best(State state, int guaranteeId) {

		GBRank bestRank = this.getBestFromSuccessors(state, guaranteeId);

		if (this.game.getGuarantee(guaranteeId).contains(state)) {
			GBRankContext initialGuaranteeContext = this.rankSystem
					.getContext(guaranteeId);

			// In this case, the infinity value of bestRank and returnRank may
			// be different.
			// We set returnRank to the desired return value and return it
			// (either (0,1) or infinity)
			if (bestRank.isInfinity()) {
				// for this guaranteeId the rank is infinity
				return GBRank.getInfinityFor(initialGuaranteeContext);
			} else {
				// for this guaranteeId the rank is "zero"
				return new GBRank(initialGuaranteeContext);
			}
		} else {
			bestRank.increase();
		}

		return bestRank;
	}

	private GBRank getBestFromSuccessors(State state, int guaranteeId) {
		GBRank bestRank;
		// if state belongs to guarantee i then the best rank for it
		// should based on the rank for the next guarantee
		// DIPI: Don't Understand. If nextGuarantee is the same as guaranteeId,
		// why we compute the min/max
		// of the successors for the same guarantee? the state it's already in
		// the guarantee.

		// Nir
		// If the state is in the guarantee, then next guarantee should be
		// what's written below.
		// If the state is not in the guarantee, then the next guarantee should
		// be the same guaranteeId.
		// In addition,
		// In an uncontrollable state the best we can expect is worst, so we are
		// going to get the max.
		// In a controllable state, the best we can expect is our choice, so we
		// are going to get the min.

		int nextGuarantee = this.getNextGuarantee(guaranteeId, state);
		if (game.isUncontrollable(state)) {
			// there is no assumption about the environment
			bestRank = this.rankSystem.getMaximum(nextGuarantee, this.game
					.getUncontrollableSuccessors(state));
		} else {
			bestRank = this.rankSystem.getMinimum(nextGuarantee, this.game
					.getControllableSuccessors(state));
		}
		return bestRank;
	}

}
