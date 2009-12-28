package ar.dc.uba.synthesis;

import java.util.*;

import org.apache.commons.collections15.*;

import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.model.MTS.*;
import ar.dc.uba.model.condition.*;
import ar.dc.uba.model.language.*;
import ar.dc.uba.model.structure.*;

/**
 * Synthesis algorithm. From Universal triggered scenario to MTS
 * 
 * @author gsibay
 * 
 */
public class MTSFromETriggeredScenarioSynthesiser extends
		MTSFromTriggeredScenarioSynthesiser {

	@Override
	protected SynthesizedState createInitialState(
			ZetaFunction initialZetaFunction) {
		// Initial state's word is the operational representation of the empty
		// word epsilon
		Symbol firstSymbol = new CanonicalSymbol(this.getSynthesiserHelper()
				.getConditionsHolding(initialZetaFunction));
		Word initialWord = new Word(Collections.singletonList(firstSymbol));

		Set<Word> requiredPaths = new HashSet<Word>(); // no obligations
		if (this.satisfiesPrechart(initialWord)) {
			// Every word in the Mainchart is an obligation
			requiredPaths = this.getLm().getWords();
		}

		return new SynthesizedState(initialWord,
				new ObligationsFromExistential(requiredPaths),
				initialZetaFunction);
	}

	@Override
	protected Set<SynthesizedState> processState(
			MTS<SynthesizedState, Symbol> mts,
			SynthesizedState anUnprocessedState) {
		Set<SynthesizedState> newStates = new HashSet<SynthesizedState>();

		// Improved version: Add tau transition first
		// Adds tau transitions (if its the case)
		if (!anUnprocessedState.getObligations().isEmpty()) { // There are
																// obligations
																// in current
																// unprocessed
																// state
			// Add a tau transition
			newStates.addAll(this.addTransitions(mts, anUnprocessedState,
					Alphabet.TAU));
		}

		for (Symbol symbol : this.getRestrictedSymbols()) {
			// adds the transitions for the unprocessed state and new states may
			// require to be processed
			newStates.addAll(this.addTransitions(mts, anUnprocessedState,
					symbol));
		}
		return newStates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ar.dc.uba.synthesis.MTSFromLSCSynthesizer#addTransitions(ac.ic.doc.mtstools
	 * .model.MTS, ar.dc.uba.model.structure.SynthesizedState,
	 * ar.dc.uba.model.language.Symbol)
	 */
	@Override
	protected Collection<SynthesizedState> addTransitions(
			MTS<SynthesizedState, Symbol> mts, SynthesizedState state, Symbol t) {
		Collection<SynthesizedState> newStates = new HashSet<SynthesizedState>();

		Word significativeSuffix = state.getSignificativeSuffix();

		// initialize the next zeta function as a copy of the previous one
		ZetaFunction nextZetaFunction = state.getZetaFunction().getCopy();

		// next state�s significative suffix, and update the zetaFunction
		Word nextSignificativeSuffix = this.next(significativeSuffix, t,
				nextZetaFunction);

		Set<Word> newRequiredPaths = new HashSet<Word>(); // No new obligations
		if (this.satisfiesPrechart(nextSignificativeSuffix)) {
			// Every word in the Mainchart is an obligation
			newRequiredPaths = this.getLm().getWords();
		}

		// Add the new transitions

		SynthesizedState nextState;

		ObligationsFromExistential obligations = ((ObligationsFromExistential) state
				.getObligations());
		// Each requiredPath starting with t must be satisfied from this state
		Set<Word> requiredPathsStartingWithCurrentSymbol = this.follows(
				obligations.getRequiredPaths(), t);
		for (Word requiredPath : requiredPathsStartingWithCurrentSymbol) {

			Set<Word> inheritedRequiredPath;
			if (requiredPath.equals(Word.EMPTY_WORD)) { // End of a required
														// path.
				// There is no obligation�s propagation
				inheritedRequiredPath = SetUtils.EMPTY_SET;
			} else {
				inheritedRequiredPath = Collections.singleton(requiredPath);
			}

			// The next state�s mandatory paths is the union of
			// newMandatoryPaths and oldMandatoryPaths
			Set<Word> nextStateObligations = new HashSet<Word>(CollectionUtils
					.union(newRequiredPaths, inheritedRequiredPath));

			// Add as required transition
			nextState = new SynthesizedState(nextSignificativeSuffix,
					new ObligationsFromExistential(nextStateObligations),
					nextZetaFunction.getCopy());
			this.addIfNotPresent(nextState, mts, newStates);
			mts.addTransition(state, t, nextState, TransitionType.REQUIRED);
		}

		if (!this.firsts(obligations.getRequiredPaths()).contains(t)) {

			// IMPROVED VERSION:
			// If tau and then t can be done to an equal state, do not add it as
			// a transition.
			// Add as maybe transition only if
			nextState = new SynthesizedState(nextSignificativeSuffix,
					new ObligationsFromExistential(newRequiredPaths),
					nextZetaFunction.getCopy());
			BinaryRelation<Symbol, SynthesizedState> transitionsFromState = mts
					.getTransitions(state, TransitionType.MAYBE);
			Iterator<SynthesizedState> statesAfterTauIt = transitionsFromState
					.getImage(Alphabet.TAU).iterator();
			boolean isRedundantTransition = false;
			while (statesAfterTauIt.hasNext() && !isRedundantTransition) {
				SynthesizedState stateAfterTau = statesAfterTauIt.next();
				if (mts.getTransitions(stateAfterTau, TransitionType.MAYBE)
						.getImage(t).contains(nextState)) {
					isRedundantTransition = true;
				}
			}

			if (!isRedundantTransition) {
				this.addIfNotPresent(nextState, mts, newStates);
				// mts.addTransition(state, t, nextState, TransitionType.MAYBE);
				mts.addPossible(state, t, nextState);
			}
		}

		return newStates;
	}

	@Override
	protected boolean hasTauTransitions() {
		return true;
	}
}
