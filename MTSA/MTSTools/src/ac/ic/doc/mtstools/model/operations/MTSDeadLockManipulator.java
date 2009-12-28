package ac.ic.doc.mtstools.model.operations;

import ac.ic.doc.mtstools.model.*;

public interface MTSDeadLockManipulator<State, Action> {

	public abstract boolean getTransitionsToDeadlock(MTS<State, Action> mts,
			MTSTrace<Action, State> traceToDeadlock);

	public abstract void deleteTransitionsToDeadlock(
			MTS<State, Action> mtsWithoutDeadlock);

	public int getDeadlockStatus(MTS<State, Action> mts);
}