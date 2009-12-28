package ac.ic.doc.mtstools.model.impl;

import java.util.*;

import org.apache.commons.collections15.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;

public class SimulationPredicate<S1, S2, A> implements Predicate<Pair<S1, S2>> {

	private Set<Pair<S1, S2>> relation;
	private Simulation simulation;
	private MTS<S1, A> mts1;
	private MTS<S2, A> mts2;

	public SimulationPredicate(MTS<S1, A> mts1, MTS<S2, A> mts2,
			Set<Pair<S1, S2>> relation, Simulation simulation) {
		this.mts1 = mts1;
		this.mts2 = mts2;
		this.relation = relation;
		this.simulation = simulation;
	}

	public boolean evaluate(Pair<S1, S2> pair) {
		return this.simulation.simulate(this.mts1, pair.getFirst(), this.mts2,
				pair.getSecond(), this.relation);
	}

}
