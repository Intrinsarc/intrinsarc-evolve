package ac.ic.doc.mtstools.model.operations;

import java.util.*;

import ac.ic.doc.mtstools.model.*;

public interface MTSClosure {
	public <State, Action> void applyMTSClosure(MTS<State, Action> mts,
			Set<Action> silentActions);
}
