package ac.ic.doc.mtstools.model.impl;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lts.CompactState;
import lts.CompositeState;
import lts.LTSOutput;
import ac.ic.doc.mtstools.model.MTS;
import ac.ic.doc.mtstools.test.util.MTSTestBase;
import ac.ic.doc.mtstools.test.util.TestLTSOuput;
import ac.ic.doc.mtstools.util.fsp.AutomataToMTSConverter;

public class MTSAvsLTSATest extends MTSTestBase {
	

	public void testUnLTSenMTSAyEnLTSA() throws Exception {
		String sourceString = "PHIL = (sitdown->right.get->left.get->eat->left.put->right.put->arise->PHIL).\n " +
				"FORK = (get -> put -> FORK).\n " +
				"||DINERS(N=4)=forall [i:0..N-1](phil[i]:PHIL||{phil[i].left,phil[((i-1)+N)%N].right}::FORK).\n" +
				"menu RUN = {phil[0..7].{sitdown,eat}}";

		LTSOutput output = new TestLTSOuput();

		CompositeState compositeByMTSA = ltsaTestUtils.buildAutomataFromSource(sourceString);
		CompositeState compositeByLTSA = ltsaTestUtils.buildAutomataFromSource(sourceString);
		
		
		compositeByLTSA.compose(new TestLTSOuput());
		
		
		MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();

		System.gc();
		long initialMemoryUsed = memorymbean.getHeapMemoryUsage().getUsed();
		compositeByLTSA.compose(output);
		long initialLTSATime = System.currentTimeMillis();
		long memoriaLTSA = memorymbean.getHeapMemoryUsage().getUsed() - initialMemoryUsed;
		System.out.println(memoriaLTSA);
		long tiempoLTSA = (System.currentTimeMillis() - initialLTSATime);
				
		System.gc();
		initialMemoryUsed = memorymbean.getHeapMemoryUsage().getUsed();
		long initialMTSATime = System.currentTimeMillis();
		new MTSMultipleComposer(new CompositionRuleApplier()).compose(buildMTSCollection(compositeByMTSA));
		long tiempoMTSA = (System.currentTimeMillis() - initialMTSATime);
		System.out.println("Compuesto en : " + tiempoMTSA + "ms.");
		long memoriaMTSA = memorymbean.getHeapMemoryUsage().getUsed() - initialMemoryUsed;
		System.out.println(memoriaMTSA);
		
		System.out.println("MTSA consumio : " + memoriaLTSA/ (1024 * 1024));
		System.out.println("MTSA consumio : " + memoriaMTSA/ (1024 * 1024));
		System.out.println("Diferencia de memoria : " + (Math.abs(memoriaMTSA - memoriaLTSA)/ (1024 * 1024)) + "Mb.");
		System.out.println("Diferencia de tiempo : " + Math.abs(tiempoMTSA - tiempoLTSA));

	}

	private List<MTS<Long, String>> buildMTSCollection(CompositeState compositeByMTSA) {
		List<MTS<Long, String>> result = new ArrayList<MTS<Long,String>>();
		for (Iterator<CompactState> it = compositeByMTSA.getMachines().iterator(); it.hasNext();) {
			CompactState compactState = it.next();
			result.add(AutomataToMTSConverter.getInstance().convert((CompactState) compactState));
		}
		return result;
	}
	
}
