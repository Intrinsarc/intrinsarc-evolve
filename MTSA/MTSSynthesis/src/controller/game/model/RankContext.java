package controller.game.model;

public interface RankContext {

	public abstract boolean inRange(int value, int assume);

	public abstract int getHeight();

	public abstract int getWidth();

}