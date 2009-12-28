package ac.ic.doc.mtstools;

import static ac.ic.doc.mtstools.model.MTS.TransitionType.MAYBE;
import static ac.ic.doc.mtstools.model.MTS.TransitionType.REQUIRED;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import lts.CompactState;
import lts.CompositeState;
import ac.ic.doc.commons.relations.Pair;
import ac.ic.doc.mtsa.MTSCompiler;
import ac.ic.doc.mtstools.model.ImplementationNotion;
import ac.ic.doc.mtstools.model.LTS;
import ac.ic.doc.mtstools.model.MTS;
import ac.ic.doc.mtstools.model.Refinement;
import ac.ic.doc.mtstools.model.impl.BranchingByTransformationSemantics;
import ac.ic.doc.mtstools.model.impl.BranchingImplementationNotion;
import ac.ic.doc.mtstools.model.impl.BranchingSemantics;
import ac.ic.doc.mtstools.model.impl.LTSAdapter;
import ac.ic.doc.mtstools.model.impl.WeakSemantics;
import ac.ic.doc.mtstools.util.fsp.AutomataToMTSConverter;

public class MTSChecker {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("fspFile queriesFile");
			return;
		}
		MTSChecker checher = new MTSChecker();
		
		List<Pair<String,String>> queries = parseQueriesFile(args[1]);
		
		checher.run(args[0], queries);
	}
	
	private static List<Pair<String, String>> parseQueriesFile(String queriesFile) throws IOException {
		List<Pair<String, String>> result = new LinkedList<Pair<String, String>>();
		BufferedReader input = new BufferedReader(new FileReader(queriesFile));
		
		while(input.ready()) {
			String line = input.readLine();
			if (line.matches("\\s*%.*") || line.matches("\\s*")) {
				continue;
			}
			String split[] = line.split(" ");
			if (split.length != 2) {
				throw new RuntimeException("Invalid line format: " + line);
			}
			result.add(Pair.create(split[0], split[1]));
		}		
		
		return result;
	}

	private Map<String, ImplementationNotion> implementationNotions = new LinkedHashMap<String, ImplementationNotion>();

	private Map<String, Refinement> refinementNotions = new LinkedHashMap<String, Refinement>();

	public void run(String modelsFile, List<Pair<String,String>> queries) throws IOException {
	
		Map<String,MTS<Long,String>> MTSs = this.getMTSs(modelsFile);

		this.loadSemantics();
		
	
		for(Pair<String, String> query: queries) {
			MTS<Long,String> mts[] = new MTS[2];
			mts[0] = MTSs.get(query.getFirst());
			mts[1] = MTSs.get(query.getSecond());
			
			System.out.print(query.getFirst() + "  - " + query.getSecond());
			if (mts[0] != null && mts[1] != null) {
				this.test(mts[0], mts[1]);			
			} else {
				System.out.println("Error:");
				if (mts[0] == null) {
					System.out.println(query.getFirst() + " could not be found");
				}
				if (mts[1] == null) {
					System.out.println(query.getSecond() + " could not be found");
				}
				System.out.println();
			}
		}
			
	}

	private Map<String, ImplementationNotion> getImplementationNotions() {
		return this.implementationNotions;
	}
	
	private Map<String, MTS<Long, String>> getMTSs(String modelsFile) throws IOException {

		CompositeState state = MTSCompiler.getInstance().compileCompositeState("DEFAULT", new File(modelsFile));
		
		AutomataToMTSConverter converter = AutomataToMTSConverter.getInstance();
		
		Map<String,MTS<Long,String>> MTSs = new HashMap<String,MTS<Long,String>>();
		for(CompactState automata:(Vector<CompactState>)state.getMachines()) {
			MTS<Long,String> mts = converter.convert(automata);
			MTSs.put(automata.name,mts);
		}
		
		return MTSs;
	}
	
	private Map<String, Refinement> getRefinementNotions() {
		return this.refinementNotions;
	}
	
	private <S> boolean isLTS(MTS<S, ?> mts) {
		for(S state:  mts.getStates()) {
			if (!mts.getTransitions(state, MAYBE).isEmpty()) {
				return false;
			}
		}
		return true;
	}


	private void loadSemantics() {
		Set<String> invisibleActions = Collections.singleton("_tau");
		
		ImplementationNotion branching = new BranchingImplementationNotion(invisibleActions);
		BranchingSemantics naiveBranching = new BranchingSemantics(invisibleActions);
		WeakSemantics weak = new WeakSemantics(invisibleActions);
		WeakSemantics strong = new WeakSemantics(Collections.EMPTY_SET);
		//Refinement branchingRefinement = new BranchingRefinement(invisibleActions);
		Refinement expansionRefinement = new BranchingByTransformationSemantics(invisibleActions);
		
		this.getImplementationNotions().put("Strong    ", strong);
		this.getImplementationNotions().put("Branching ", branching);
		this.getImplementationNotions().put("Weak      ", weak);

		this.getRefinementNotions().put("Strong           ", strong);
		this.getRefinementNotions().put("Naive Branching  ", naiveBranching);
		this.getRefinementNotions().put("Expansion        ", expansionRefinement);
		//this.getRefinementNotions().put("Branching        ", branchingRefinement);
		this.getRefinementNotions().put("Weak             ", weak);		
	}

	private <A,S> void test(MTS<A,S> mts1, MTS<A,S> mts2) {
		if (this.isLTS(mts2)) {
			System.out.println(" checking for implementation");
			LTS<A,S> lts = new LTSAdapter<A,S>(mts2, REQUIRED);
			for(Map.Entry<String, ImplementationNotion> notion: this.getImplementationNotions().entrySet()) {
				long start = System.nanoTime();
				
				System.out.print(notion.getKey() + ": " + notion.getValue().isAnImplementation(mts1,lts));
				System.out.println(" \ttime: " + (System.nanoTime() - start) / 1000000 + " (mseg)");
			}		
		} else {		
			System.out.println(" checking for refinement");
			for(Map.Entry<String, Refinement> notion: this.getRefinementNotions().entrySet()) {
				long start = System.nanoTime();
				System.out.print(notion.getKey() + ": " + notion.getValue().isARefinement(mts1,mts2));
				System.out.println(" \ttime: " + (System.nanoTime() - start) / 1000000 + " (mseg)");
			}		
		}
		System.out.println();
	}


}
