package ac.ic.doc.mtstools.model.impl;

import java.util.*;

import ac.ic.doc.mtstools.model.MTS.*;

public class WeakBisimulationSemantics extends BaseSemanticsByRelation {

	public WeakBisimulationSemantics(Set<?> silentActions) {
		super(new FixedPointRelationConstructor(new SimulationChain().add(
				new WeakForwardSimulation(silentActions)).add(
				new WeakBackwardSimulation(silentActions,
						TransitionType.REQUIRED, TransitionType.REQUIRED))),
				silentActions);
	}

}
