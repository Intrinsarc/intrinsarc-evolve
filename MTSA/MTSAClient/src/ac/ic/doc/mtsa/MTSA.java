package ac.ic.doc.mtsa;

import java.util.*;

import ac.ic.doc.mtstools.facade.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.impl.*;
import ac.ic.doc.mtstools.model.operations.impl.*;

public class MTSA {
	public MTS<Long, String> compile(String sourceString, String modelName)
			throws Exception {

		return MTSCompiler.getInstance().compileMTS(sourceString, modelName);
	}

	// refinement
	public <State, Action> boolean isRefinement(MTS<State, Action> refined,
			MTS<State, Action> refines, Set<Action> silentActions) {
		return new WeakSemantics(silentActions).isARefinement(refined, refines);
	}

	// merge
	public <State, Action> MTS<Object, Action> merge(MTS<State, Action> mtsA,
			MTS<State, Action> mtsB, Set<Action> silentActions) {
		return new WeakAlphabetMergeBuilder(silentActions).merge(mtsA, mtsB);
	}

	// optimistic
	/**
	 * Returns the optimistic version of the model <code>mts</code>.
	 * 
	 */
	public static MTS<Long, String> getOptimisticModel(MTS<Long, String> mts) {
		return MTSAFacade.getOptimisticModel(mts);
	}

	// pessimistic
	/**
	 * Returns the pessimistic version of the model <code>mts</code>.
	 */
	public static MTS<Long, String> getPesimisticModel(MTS<Long, String> mts) {
		return MTSAFacade.getPesimisticModel(mts);
	}
}
