package controller.game.model;

import java.util.*;

public interface GameSolver<T, S> {

	public abstract void solveGame();

	// I'm not sure if this should be public.
	public abstract Set<T> getWinningStates();

	public abstract boolean isWinning(T state);

	public abstract Map<S, Set<S>> buildStrategy();

}