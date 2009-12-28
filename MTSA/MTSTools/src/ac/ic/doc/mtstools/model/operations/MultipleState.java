package ac.ic.doc.mtstools.model.operations;

import java.util.*;

public class MultipleState implements Iterable<Integer> {
	private BitSet internalState;
	private BitSet internalOutgoingMaybes;

	public MultipleState() {
		internalState = new BitSet();
		internalOutgoingMaybes = new BitSet();
	}

	public MultipleState(int initialState) {
		this();
		this.addState(initialState);
	}

	public void addState(Integer stateId) {
		internalState.set(stateId.intValue());
	}

	public void markAsMaybe(Integer stateId) {
		internalOutgoingMaybes.set(stateId);
	}

	public boolean hasMaybeMark(Integer stateId) {
		return internalOutgoingMaybes.get(stateId);
	}

	public boolean hasState(Integer state) {
		return internalState.get(state.intValue());
	}

	public int width() {
		return internalState.size();
	}

	/**
	 * @return the internalState
	 */
	protected BitSet getInternalState() {
		return internalState;
	}

	/**
	 * @return the internalOutgoingMaybes
	 */
	protected BitSet getInternalOutgoingMaybes() {
		return internalOutgoingMaybes;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		try {
			MultipleState ms = (MultipleState) obj;
			return ms.getInternalState().equals(this.getInternalState());
			// &&
			// ms.getInternalOutgoingMaybes().equals(this.getInternalOutgoingMaybes());
		} catch (RuntimeException e) {
			return false;
		}

	}

	@Override
	public int hashCode() {
		return internalState.hashCode();
	}

	// saque el state por el problema del long
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			private int currentIndex = 0;

			public boolean hasNext() {
				currentIndex = internalState.nextSetBit(currentIndex);
				return -1 != currentIndex;
			}

			public Integer next() {
				return Integer.valueOf(currentIndex++);
			}

			public void remove() {
				throw new UnsupportedOperationException("Can't remove items");
			}
		};
	}
}
