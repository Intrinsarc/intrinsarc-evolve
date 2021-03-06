package ac.ic.doc.mtstools.model.impl;

import java.util.Collections;

import ac.ic.doc.commons.relations.Pair;
import ac.ic.doc.mtstools.model.MTS;
import ac.ic.doc.mtstools.model.Refinement;
import ac.ic.doc.mtstools.test.util.MTSTestBase;

public class PlusCROperatorTest extends MTSTestBase{
	

	protected Refinement refinement;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.refinement = new WeakSemantics(Collections.EMPTY_SET);
	}
	
	public void testCR() throws Exception {
		String sourceString = "A_0 = ( x -> A_0_1 | x -> A_0_2), A_0_1 = ( a? -> A_0_1 | b? -> A_0_1), A_0_2 = ( c? -> A_0_2 | d? -> A_0_2).\r\n";
		MTS<Long, String> mtsA = testUtils.buildMTSFrom(sourceString, ltsOutput);
		
		sourceString = "B_0 = ( x -> B_0_1 | x -> B_0_2), B_0_1 = ( a? -> B_0_1 | d? -> B_0_1), B_0_2 = ( c? -> B_0_2 | b? -> B_0_2).\r\n";
		MTS<Long, String> mtsB = testUtils.buildMTSFrom(sourceString, ltsOutput);
		
		StrongPlusCROperator CRoperator = new StrongPlusCROperator();
		
		MTS<Pair<Long,Long>, String> cr = CRoperator.compose(mtsA, mtsB);
		
		assertTrue(refinement.isARefinement(mtsA, cr));
		assertTrue(refinement.isARefinement(mtsB, cr));
		
		WeakAlphabetPlusCROperator weakCRoperator = new WeakAlphabetPlusCROperator();
		
		cr = weakCRoperator.compose(mtsA, mtsB);
		
		assertTrue(refinement.isARefinement(mtsA, cr));
		assertTrue(refinement.isARefinement(mtsB, cr));
		
		
	}

}
