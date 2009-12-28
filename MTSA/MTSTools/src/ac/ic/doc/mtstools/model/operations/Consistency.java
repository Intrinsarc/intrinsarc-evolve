package ac.ic.doc.mtstools.model.operations;

import java.util.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;

public interface Consistency {
	public <S1, S2, A> boolean areConsistent(MTS<S1, A> mtsA, MTS<S2, A> mtsB);

	public <S1, S2, A> Set<Pair<S1, S2>> getConsistecyRelation(MTS<S1, A> mtsA,
			MTS<S2, A> mtsB);
}
