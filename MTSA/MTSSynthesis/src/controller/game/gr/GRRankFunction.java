package controller.game.gr;

import java.util.*;

import controller.game.model.*;

public class GRRankFunction<State> {

	private Map<State, GRRank> function; // This is a ranking function. It maps
											// every state to a Rank
	private RankContext context;

	public GRRankFunction(Set<State> allStates, int height, int width) {
		this.context = new GRRankContext(height, width);
		this.function = new HashMap<State, GRRank>();
		for (State state : allStates) {
			GRRank initialRank = new GRRank(this.context);
			function.put(state, initialRank);
		}
	}

	public void increase(State state) {
		this.function.get(state).increase();
	}

	public boolean isInfinity(State state) {
		return this.function.get(state).isInfinity();
	}

	public void updateRank(State state, Rank rank) {
		this.function.get(state).set(rank);
	}

	public GRRank getRank(State state) {
		return this.function.get(state);
	}

	public RankContext getContext() {
		return this.context;
	}

	/*
	 * This should actually be used only by RankSystem and not be public
	 */
	protected GRRank getMinimum(Set<State> states) {
		GRRank minimum = new GRRank(this.context);
		minimum.setToInfinity();

		for (State state : states) {
			Rank rank = this.getRank(state);
			if (rank.compareTo(minimum) < 0)
				minimum.set(rank);
		}
		return minimum;
	}

	/*
	 * This should actually be used only by RankSystem and not be public
	 */
	protected GRRank getMaximum(Set<State> states) {
		GRRank maximum = new GRRank(this.context);

		for (State state : states) {
			Rank rank = this.getRank(state);
			if (rank.compareTo(maximum) > 0)
				maximum.set(rank);
		}
		return maximum;
	}

	@Override
	public String toString() {
		return "Context: " + this.context.toString() + "\n" + "Rank Function: "
				+ this.function.toString();
	}
}
