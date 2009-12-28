package controller.game.gr;

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
public class GRGameSolver<State> implements
		GameSolver<State, Pair<State, Integer>> {
	private Game<State> game;
	private GRRankSystem<State> rankSystem;
	private boolean gameSolved;

	public GRGameSolver(Game<State> game, GRRankSystem<State> rankSystem) {
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
			Rank rank = this.rankSystem.getRank(state, guaranteeId);

			// If current rank is already infinity, it obviously should
			// not be increased.
			if (rank.isInfinity()) {
				continue;
			}

			// What is the best possible ranking that s could have according to
			// it's successors?
			Rank bestRank = this.best(state, guaranteeId);

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
					this.rankSystem.set(state, i, GRRank
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
			State state, int guaranteeId, Rank bestRank) {

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
		Rank best = this.best(pred, guaranteeId);
		Rank rank = this.rankSystem.getRank(pred, guaranteeId);
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
					GRRank infinity = GRRank.getInfinityFor(this.rankSystem
							.getContext(guaranteeId));
					this.rankSystem.set(state, guaranteeId, infinity);
				}
				Rank infinity = this.rankSystem.getRank(state, 1);
				this.addPredecessorsTo(pending, state, 1, infinity);
			}
		}

		Assume<State> firstAssumption = this.game.getAssumption(1);
		for (State state : this.game.getStates()) {
			for (int i = 1; i <= this.game.getGuaranteesQuantity(); i++) {
				if (!this.game.getGuarantee(i).contains(state)
						&& !this.game.getFaults().contains(state)
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

	public boolean isBetterThan(State state, State succ, int guaranteeId,
			int nextGuaranteeId, boolean mayIncrease) {
		Rank succRank = this.rankSystem.getRank(succ, nextGuaranteeId);
		GRRank stateRank = this.rankSystem.getRank(state, guaranteeId);
		int actualStateVSsuccessor = stateRank.compareTo(succRank);

		boolean isInAssumption = this.game.getAssumption(stateRank.getAssume())
				.contains(state);
		return ((mayIncrease && !succRank.isInfinity()) || (!mayIncrease
				&& (actualStateVSsuccessor > 0) || (!succRank.isInfinity()
				&& actualStateVSsuccessor == 0 && !isInAssumption)));
	}

	private int getNextGuarantee(int guaranteeId, State state) {
		if (this.game.getGuarantee(guaranteeId).contains(state)
				|| this.game.getFaults().contains(state)) {
			return this.increaseGuarantee(guaranteeId);
		} else {
			return guaranteeId;
		}
	}

	private int getNextGuaranteeStrategy(int guaranteeId, State state) {
		if (this.game.getGuarantee(guaranteeId).contains(state)) {
			return this.increaseGuarantee(guaranteeId);
		} else {
			return guaranteeId;
		}
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

				int nextGuaranteeId = this.getNextGuaranteeStrategy(
						guaranteeId, state);
				boolean rankMayIncrease = this.game.getGuarantee(guaranteeId)
						.contains(state)
						|| this.game.getFaults().contains(state);
				Set<Pair<State, Integer>> successors = new HashSet<Pair<State, Integer>>();
				if (this.game.isUncontrollable(state)) {
					for (State succ : this.game
							.getUncontrollableSuccessors(state)) {
						Validate
								.isTrue(
										this.isBetterThan(state, succ,
												guaranteeId, nextGuaranteeId,
												rankMayIncrease),
										"State: "
												+ succ
												+ " must have a better rank than state: "
												+ state);
						Pair<State, Integer> target = Pair.create(succ,
								nextGuaranteeId);
						successors.add(target);
					}

					for (State succ : this.game
							.getControllableSuccessors(state)) {
						if (this.isBetterThan(state, succ, guaranteeId,
								nextGuaranteeId, rankMayIncrease)) {
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
						if (this.isBetterThan(state, succ, guaranteeId,
								nextGuaranteeId, rankMayIncrease)) {
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
	private Rank best(State state, int guaranteeId) {
		// Different ranks have different infinity values.

		// DIPI: why? is this an optimisatokion? Could just work with
		// infinity=allstates.size?
		// Nir
		// Yes. This is an optimization. Here I left the KISS design pattern and
		// decided that I am smart ;)
		// In general allstates.size is an upper bound on the number possible
		// ranks.
		// The actual bound is smaller. It is computed in GRRankSystem and you
		// wrote that you
		// don't understand it there.
		// What creates increases in the rank? States that are in the
		// assumption.
		// When is a rank not evidence of a cycle? When it is caused by a very
		// long chain of states in the assumption that are
		// not in the guarantee.
		// What is the maximal number of increases that could still be winning?
		// The number of states in the largest assumption.
		// A number smaller than this can still be saved by a visit to the
		// guarantee (there are cycles on the
		// smaller assumptions but on the largest assumption there is no cycle
		// and this cycle will be winning for
		// the system).
		// A number larger than this means that all assumptions are visited in
		// cycles and there is a cycle
		// that visits all the assumptions and not the guarantee.

		// We want to return a GRRank that has the infinity related to
		// guaranteeId, so
		// returnRank has the infinity value related to guaranteeId
		GRRank bestRank = this.getBestFromSuccessors(state, guaranteeId);

		if (this.game.getGuarantee(guaranteeId).contains(state)
				|| this.game.getFaults().contains(state)) {
			RankContext initialGuaranteeContext = this.rankSystem
					.getContext(guaranteeId);

			// In this case, the infinity value of bestRank and returnRank may
			// be different.
			// We set returnRank to the desired return value and return it
			// (either (0,1) or infinity)
			if (bestRank.isInfinity()) {
				// for this guaranteeId the rank is infinity
				return GRRank.getInfinityFor(initialGuaranteeContext);
			} else {
				// for this guaranteeId the rank is "zero"
				return new GRRank(initialGuaranteeContext);
			}
		} else if (this.game.getAssumption(bestRank.getAssume())
				.contains(state)) {
			bestRank.increase();
		}

		return bestRank;
	}

	private GRRank getBestFromSuccessors(State state, int guaranteeId) {
		GRRank bestRank;
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
