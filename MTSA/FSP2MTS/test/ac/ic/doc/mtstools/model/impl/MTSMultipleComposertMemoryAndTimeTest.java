package ac.ic.doc.mtstools.model.impl;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import lts.CompositeState;
import lts.LTSOutput;
import ac.ic.doc.mtstools.test.util.MTSTestBase;
import ac.ic.doc.mtstools.test.util.TestLTSOuput;
import dispatcher.TransitionSystemDispatcher;

public class MTSMultipleComposertMemoryAndTimeTest extends MTSTestBase {

	public void testComponeMTSA() throws Exception {
		String sourceString = "PHIL = (sitdown?->right.get->left.get->eat->left.put->right.put->arise->PHIL).\n FORK = (get -> put -> FORK).\n ||DINERS(N=5)=forall [i:0..N-1](phil[i]:PHIL||{phil[i].left,phil[((i-1)+N)%N].right}::FORK).menu RUN = {phil[0..5].{sitdown,eat}}";
		LTSOutput output = new TestLTSOuput();

		CompositeState compositeOriginal = ltsaTestUtils.buildAutomataFromSource(sourceString);
		System.gc();
		MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
		long initialMemoryUsed = memorymbean.getHeapMemoryUsage().getUsed();
		TransitionSystemDispatcher.paralellComposition(compositeOriginal, output);
		MemoryMXBean memorymbean2 = ManagementFactory.getMemoryMXBean();
		long finalMemoryUsed = memorymbean2.getHeapMemoryUsage().getUsed();
		System.out.println("Memoria usada : " + ((finalMemoryUsed - initialMemoryUsed)/ (1024 * 1024)) + "Mb.");
		
	}

//	public void testComponeLTSA() throws Exception {
//		String sourceString = "PHIL = (sitdown->right.get->left.get->eat->left.put->right.put->arise->PHIL).\n FORK = (get -> put -> FORK).\n ||DINERS(N=5)=forall [i:0..N-1](phil[i]:PHIL||{phil[i].left,phil[((i-1)+N)%N].right}::FORK).menu RUN = {phil[0..5].{sitdown,eat}}";
//		LTSOutput output = new TestLTSOuput();
//
//		ICompositeState compositeOriginal = ltsaTestUtils.buildAutomataFromSource(sourceString);
//		TransitionSystemDispatcher.compose(compositeOriginal, output);
//		
//	}
	public static void main(String[] args) throws Exception {
		MTSMultipleComposertMemoryAndTimeTest multipleComposertMemoryAndTimeTest = new MTSMultipleComposertMemoryAndTimeTest();
		multipleComposertMemoryAndTimeTest.setUp();
		multipleComposertMemoryAndTimeTest.testComponeMTSA();
	}
}
