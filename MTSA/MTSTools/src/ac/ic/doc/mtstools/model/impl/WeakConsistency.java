package ac.ic.doc.mtstools.model.impl;

import static ac.ic.doc.mtstools.model.MTS.TransitionType.*;

import java.util.*;

import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.operations.*;

/**
 * This class implements a weak consistency relation, which characterises
 * consistency between models under weak semantics. If the silent actions sets
 * is empty then it characterises strong consistency.
 * 
 */
public class WeakConsistency extends AbstractConsistencyRelation implements
		Consistency {

	private Set<?> silentActions;

	public WeakConsistency(Set<?> silentActions) {
		this.setSilentActions(silentActions);
	}

	public Set<?> getSilentActions() {
		return silentActions;
	}

	public void setSilentActions(Set<?> silentActions) {
		this.silentActions = silentActions;
	}

	@SuppressWarnings("unchecked")
	protected <S1, S2, A> FixedPointRelationConstructor getRelationContructor(
			MTS<S1, A> mtsA, MTS<S2, A> mtsB) {
		return new FixedPointRelationConstructor(new SimulationChain().add(
				new WeakForwardSimulation(this.getSilentActions(), REQUIRED,
						POSSIBLE)).add(
				new WeakBackwardSimulation(this.getSilentActions(), REQUIRED,
						POSSIBLE)));
	}

}
