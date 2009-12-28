package ac.ic.doc.mtstools.util.fsp;

import static ac.ic.doc.mtstools.model.MTS.TransitionType.*;

import java.util.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.MTS.*;
import ac.ic.doc.mtstools.model.impl.*;

/**
 * This class converts a generic MTS into a MTS<Long,String>. It was created in
 * order to adapt generic MTS into MTS<Long,String> since some MTSA functions
 * handle only this kind of MTSs. The actions are transformed using the
 * toString() method, and the states are numbered from 0 starting from the
 * initial state.
 * 
 * @author fdario
 * 
 */
public class GenericMTSToLongStringMTSConverter {

	public <S, A> MTS<Long, String> convert(MTS<S, A> mts) {
		Map<S, Long> stateMapping = this.createStateMapping(mts);

		MTS<Long, String> result = new MTSImpl<Long, String>(stateMapping
				.get(mts.getInitialState()));
		result.addStates(stateMapping.values());
		for (A action : mts.getActions()) {
			result.addAction(action.toString());
		}

		for (TransitionType type : TransitionType.values()) {
			if (MAYBE.equals(type))
				continue;
			for (S state : mts.getStates()) {
				for (Pair<A, S> transition : mts.getTransitions(state, type)) {
					result.addTransition(stateMapping.get(state), transition
							.getFirst().toString(), stateMapping.get(transition
							.getSecond()), type);
				}
			}

		}

		return result;
	}

	private <S> Map<S, Long> createStateMapping(MTS<S, ?> mts) {
		Map<S, Long> result = new HashMap<S, Long>();
		Long nextValue = 0L;
		result.put(mts.getInitialState(), nextValue++);
		for (S state : mts.getStates()) {
			if (!result.containsKey(state)) {
				result.put(state, nextValue++);
			}
		}
		return result;
	}

}
