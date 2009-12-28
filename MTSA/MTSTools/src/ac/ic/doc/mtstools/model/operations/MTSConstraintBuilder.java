package ac.ic.doc.mtstools.model.operations;

import java.util.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.MTS.*;
import ac.ic.doc.mtstools.model.impl.*;

public class MTSConstraintBuilder {

	public <State, Action> void makeConstrainedModel(MTS<State, Action> mts) {

		Set<MTSTransition<Action, State>> toMakePossible = new HashSet<MTSTransition<Action, State>>();
		for (State state : mts.getStates()) {
			BinaryRelation<Action, State> transitions = mts.getTransitions(
					state, TransitionType.REQUIRED);
			if (transitions.size() > 1) {
				for (Pair<Action, State> transition : transitions) {
					Action action = transition.getFirst();
					assert (!MTSConstants.ASTERIX.equals(action));
					toMakePossible.add(MTSTransition.createMTSEventState(state,
							action, transition.getSecond()));
				}
			}
		}
		for (MTSTransition<Action, State> tr : toMakePossible) {
			mts.removeRequired(tr.getStateFrom(), tr.getEvent(), tr
					.getStateTo());
			mts.addPossible(tr.getStateFrom(), tr.getEvent(), tr.getStateTo());
		}
	}

}
