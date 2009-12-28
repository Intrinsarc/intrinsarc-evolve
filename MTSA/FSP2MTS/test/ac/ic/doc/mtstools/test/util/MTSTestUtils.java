package ac.ic.doc.mtstools.test.util;

import java.util.Collections;
import java.util.Set;

import junit.framework.TestCase;
import lts.CompactState;
import lts.CompositeState;
import lts.LTSOutput;
import ac.ic.doc.mtstools.model.MTS;
import ac.ic.doc.mtstools.model.MTS.TransitionType;
import ac.ic.doc.mtstools.model.impl.LTSAdapter;
import ac.ic.doc.mtstools.model.impl.WeakSemantics;
import ac.ic.doc.mtstools.util.fsp.AutomataToMTSConverter;
import dispatcher.TransitionSystemDispatcher;

public class MTSTestUtils extends TestCase {
	protected LTSATestUtils ltsaTestUtils;
	protected LTSOutput output;
	
	public MTSTestUtils() {
		this.ltsaTestUtils = new LTSATestUtils();
		this.output = new TestLTSOuput();
	}
	
	public void areEquivalent(MTS<Long, String> originalMTS, MTS<Long, String> finalMTS) {
		Set<String> invisibleActions = Collections.singleton("tau");
		WeakSemantics weak = new WeakSemantics(invisibleActions);
		WeakSemantics strong = new WeakSemantics(Collections.EMPTY_SET);
		
		assertTrue(weak.isARefinement(originalMTS, finalMTS));
		assertTrue(weak.isARefinement(finalMTS, originalMTS));

		assertTrue(strong.isARefinement(originalMTS, finalMTS));
		assertTrue(strong.isARefinement(finalMTS, originalMTS));
	}

	public void assertMinimisation(String sourceString) throws Exception {
		CompositeState composite = buildCompositeState(sourceString, output);
		MTS<Long, String> originalMTS = AutomataToMTSConverter.getInstance().convert((CompactState) composite.getComposition());
		
		TransitionSystemDispatcher.minimise(composite, output);
		MTS<Long, String> finalMTS = AutomataToMTSConverter.getInstance().convert((CompactState) composite.getComposition());

		assertFalse(finalMTS.equals(originalMTS));
		areEquivalent(originalMTS, finalMTS);
		assertTrue(originalMTS.getStates().size()>=finalMTS.getStates().size());
	}

	public CompositeState buildCompositeState(String sourceString, LTSOutput ltsOutput)
			throws Exception {
		CompositeState composite = ltsaTestUtils.buildAutomataFromSource(sourceString);
		TransitionSystemDispatcher.paralellComposition(composite, ltsOutput);
		return composite;
	}
	
	public void assertComposition(String sourceString, TestLTSOuput testLTSOuput) throws Exception {

		MTS<Long, String> originalMTS = buildMTSFrom(sourceString, testLTSOuput);

		CompositeState compositeA = ltsaTestUtils.buildAutomataFromSource(sourceString);
		TransitionSystemDispatcher.makePessimisticModel(compositeA, output);

		CompositeState compositeB = ltsaTestUtils.buildAutomataFromSource(sourceString);
		TransitionSystemDispatcher.paralellComposition(compositeB, output);
		TransitionSystemDispatcher.makePessimisticModel(compositeB, output);

		MTS<Long, String> mtsA = AutomataToMTSConverter.getInstance().convert((CompactState) compositeA.getComposition());
		MTS<Long, String> mtsB = AutomataToMTSConverter.getInstance().convert((CompactState) compositeB.getComposition());

		Set<String> invisibleActions = Collections.singleton("tau");
		WeakSemantics weak = new WeakSemantics(invisibleActions);
		WeakSemantics strong = new WeakSemantics(Collections.EMPTY_SET);

		assertTrue(weak.isAnImplementation(originalMTS, new LTSAdapter<Long, String>(mtsA, TransitionType.REQUIRED)));
		assertTrue(weak.isAnImplementation(originalMTS, new LTSAdapter<Long, String>(mtsB, TransitionType.REQUIRED)));

		assertTrue(weak.isARefinement(originalMTS, mtsA));
		assertTrue(weak.isARefinement(originalMTS, mtsB));

		assertTrue(strong.isARefinement(originalMTS, mtsA));
		assertTrue(strong.isARefinement(originalMTS, mtsB));
	}

	public MTS<Long, String> buildMTSFrom(String sourceString, LTSOutput ltsOuput) throws Exception {
		CompositeState compositeOriginal = buildCompositeState(sourceString, ltsOuput);
		MTS<Long, String> originalMTS = AutomataToMTSConverter.getInstance().convert((CompactState) compositeOriginal.getComposition());
		return originalMTS;
	}

}
