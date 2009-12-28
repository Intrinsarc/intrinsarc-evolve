package ac.ic.doc.mtstools.model.impl;

import java.util.*;

import org.apache.commons.collections.*;
import org.apache.commons.lang.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;

public class AlphabetSemantics implements RefinementByRelation {

	private Set<?> silentActions;
	private SemanticType underlyingSemantics;

	public AlphabetSemantics(Set<?> silentActions,
			SemanticType underlyingSemantics) {
		this.setSilentActions(silentActions);
		this.setUnderlyingSemantics(underlyingSemantics);
	}

	@Override
	public <S1, S2, A> BinaryRelation<S1, S2> getLargestRelation(MTS<S1, A> m,
			MTS<S2, A> n) {
		Validate.isTrue(n.getActions().containsAll(m.getActions()));

		return this.getAlphabetRefinement(m.getActions(), n.getActions())
				.getLargestRelation(m, n);
	}

	@Override
	public <S1, S2, A> boolean isAValidRelation(MTS<S1, A> m, MTS<S2, A> n,
			BinaryRelation<S1, S2> relation) {
		Validate.isTrue(n.getActions().containsAll(m.getActions()));

		return this.getAlphabetRefinement(m.getActions(), n.getActions())
				.isAValidRelation(m, n, relation);
	}

	@Override
	public <S1, S2, A> boolean isARefinement(MTS<S1, A> m, MTS<S2, A> n) {
		Validate.isTrue(n.getActions().containsAll(
				CollectionUtils.subtract(m.getActions(), this
						.getSilentActions())));

		return this.getAlphabetRefinement(m.getActions(), n.getActions())
				.isARefinement(m, n);
	}

	private void setSilentActions(Set<?> silentActions) {
		this.silentActions = silentActions;
	}

	private Set<?> getSilentActions() {
		return silentActions;
	}

	private void setUnderlyingSemantics(SemanticType underlyingSemantics) {
		this.underlyingSemantics = underlyingSemantics;
	}

	private SemanticType getUnderlyingSemantics() {
		return underlyingSemantics;
	}

	private <A> RefinementByRelation getAlphabetRefinement(Set<A> alphabet,
			Set<A> extendedAlphabet) {
		Validate.isTrue(extendedAlphabet.containsAll(alphabet));

		Set<Object> extendedSilent = new HashSet<Object>(extendedAlphabet);
		extendedSilent.removeAll(alphabet);
		extendedSilent.addAll(this.getSilentActions());

		return this.getUnderlyingSemantics().getRefinement(extendedSilent);
	}
}
