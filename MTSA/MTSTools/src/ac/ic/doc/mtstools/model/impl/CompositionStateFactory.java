package ac.ic.doc.mtstools.model.impl;

import java.util.*;

import ac.ic.doc.mtstools.model.*;

public class CompositionStateFactory {
	private long nextStateId;
	private Map<List<Long>, CompositionState> vectorToState;

	public CompositionStateFactory() {
		vectorToState = new HashMap<List<Long>, CompositionState>();
	}

	/**
	 * @return the nextStateId
	 */
	protected long getNextStateId() {
		return nextStateId++;
	}

	public CompositionState getNextStateFor(List<Long> states) {
		if (vectorToState.containsKey(states)) {
			return vectorToState.get(states);
		} else {
			CompositionState result = null;
			if (states.contains(MTSConstants.ERROR_STATE)) {
				result = new CompositionState(MTSConstants.ERROR_STATE, states);
			} else {
				result = new CompositionState(nextStateId++, states);
			}
			vectorToState.put(states, result);
			return result;
		}
	}

}
