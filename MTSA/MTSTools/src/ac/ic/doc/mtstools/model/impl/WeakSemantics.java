package ac.ic.doc.mtstools.model.impl;

import java.util.*;

import ac.ic.doc.mtstools.model.*;

public class WeakSemantics extends BaseSemanticsByRelation implements
		Refinement, ImplementationNotion {

	public WeakSemantics(Set<?> silentActions) {
		super(new FixedPointRelationConstructor(new SimulationChain().add(
				new WeakForwardSimulation(silentActions)).add(
				new WeakBackwardSimulation(silentActions))), silentActions);
			
	}

}
