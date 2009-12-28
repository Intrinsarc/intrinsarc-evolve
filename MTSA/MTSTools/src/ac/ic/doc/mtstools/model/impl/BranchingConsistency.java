package ac.ic.doc.mtstools.model.impl;

import static ac.ic.doc.mtstools.model.MTS.TransitionType.*;

import java.util.*;

import org.apache.commons.lang.*;

import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.operations.*;

public class BranchingConsistency extends AbstractConsistencyRelation implements
		Consistency {

	private Set<?> silentActions;

	public BranchingConsistency(Set<?> silentActions) {
		this.setSilentActions(silentActions);
	}

	public Set<?> getSilentActions() {
		return silentActions;
	}

	public void setSilentActions(Set<?> silentActions) {
		this.silentActions = silentActions;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <S1, S2, A> FixedPointRelationConstructor getRelationContructor(
			MTS<S1, A> mtsA, MTS<S2, A> mtsB) {
		Validate.isTrue(mtsA.getActions().equals(mtsB.getActions()));

		return new FixedPointRelationConstructor(new SimulationChain().add(
				new BranchingForwardSimulation(this.getSilentActions(),
						REQUIRED, POSSIBLE)).add(
				new BranchingBackwardSimulation(this.getSilentActions(),
						REQUIRED, POSSIBLE)));
	}

}
