package ac.ic.doc.mtstools.model.impl;

import java.util.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.MTS.*;

public interface TransitionRulesApplier {

	public abstract TransitionType applyCompositionRules(
			Pair<Long, TransitionType> transitionOnActualAction,
			Pair<Vector<Long>, TransitionType> acumulatedState);

	public abstract TransitionType applyCompositionRules(
			TransitionType transitionType);

	public abstract <State, Action> boolean composableModels(
			List<MTS<State, Action>> mtss);

	public abstract <State, Action> boolean composableStates(
			List<MTS<State, Action>> mtss, CompositionState state);

	public abstract <State, Action> void cleanUp(MTS<State, Action> mts);
}