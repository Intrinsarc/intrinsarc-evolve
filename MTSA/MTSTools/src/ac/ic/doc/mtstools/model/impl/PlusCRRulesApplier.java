package ac.ic.doc.mtstools.model.impl;

import java.util.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.MTS.*;

public class PlusCRRulesApplier extends PlusOperatorApplier {

	public TransitionType applyCompositionRules(
			Pair<Long, TransitionType> transitionOnActualAction,
			Pair<Vector<Long>, TransitionType> acumulatedState) {
		TransitionType compositeTransitionType;
		if (acumulatedState.getSecond() == TransitionType.MAYBE
				&& transitionOnActualAction.getSecond() == TransitionType.MAYBE) {
			compositeTransitionType = TransitionType.MAYBE;
		} else {
			compositeTransitionType = TransitionType.REQUIRED;
		}
		return compositeTransitionType;
	}

	public TransitionType applyCompositionRules(TransitionType transitionType) {
		return TransitionType.REQUIRED;
	}

}
