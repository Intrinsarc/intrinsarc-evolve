package ac.ic.doc.mtstools.model.impl;

import static ac.ic.doc.mtstools.model.MTS.TransitionType.*;

import java.util.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;

public class WeakForwardSimulation<A> extends AbstractForwardSimulation<A> {

	private Set<A> silentActions;

	public WeakForwardSimulation(Set<A> silentActions) {
		this(silentActions, REQUIRED, REQUIRED);
	}

	public WeakForwardSimulation(Set<A> silentActions,
			MTS.TransitionType actualTransitionType,
			MTS.TransitionType simulatorTransitionType) {
		super(actualTransitionType, simulatorTransitionType);
		this.silentActions = silentActions;
	}

	@Override
	protected <S> Iterator<List<Pair<A, S>>> getPaths(MTS<S, A> mts, S state,
			A label) {
		return ClousurePathBuilder.getInstance().getPathsIterator(mts, state,
				label, this.getSimulatorTransitionType(), this.silentActions);
	}

	@Override
	protected <S1, S2> boolean simulate(S1 s1, Pair<A, S1> transition, S2 s2,
			List<Pair<A, S2>> path, Set<Pair<S1, S2>> relation) {
		Pair<A, S2> lastTransition = path.get(path.size() - 1);

		return relation.contains(Pair.create(transition.getSecond(),
				lastTransition.getSecond()));
	}

}
