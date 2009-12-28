package controller.game.model;

public interface Rank extends Comparable<Rank> {

	public abstract void init(RankContext context);

	public abstract void increase();

	public abstract void reset();

	public abstract boolean isInfinity();

	public abstract void set(Rank rank);
}