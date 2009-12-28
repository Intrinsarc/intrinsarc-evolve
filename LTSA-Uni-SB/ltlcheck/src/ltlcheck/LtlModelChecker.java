package ltlcheck;

import gov.nasa.ltl.graph.Graph;
import gov.nasa.ltl.trans.LTL2Buchi;
import gov.nasa.ltl.trans.ParseErrorException;

public class LtlModelChecker
{
	private final static boolean debug = true;

	public static Counterexample check(Graph ts, String formula) throws ParseErrorException
	{
		long starttime;
		
		System.out.println("Formula: " + formula);
		// Generate Did/Can Expanded Graph
		if(debug) System.out.print("Expanding graph... ");
		starttime = System.nanoTime();
		final Graph dcts = DidCanTranslator.translate(ts);
		if(debug) System.out.println("done (" + ((System.nanoTime() - starttime) / 1000000000l) + " s)");

		// Remove deadlock
		if(debug) System.out.print("Massaging deadlock states... ");
		starttime = System.nanoTime();
		GraphTransformations.removeDeadlock(dcts);
		if(debug) System.out.println("done (" + ((System.nanoTime() - starttime) / 1000000000l) + " s)");

		// Generate Buchi Automata for negated LTL formula
		if(debug) System.out.print("Generating Buchi automaton (NASA)... ");
		starttime = System.nanoTime();
		Graph ba = null;
		ba = LTL2Buchi.translate("! (" + formula + ")");
		if(debug) System.out.println("done (" + ((System.nanoTime() - starttime) / 1000000000l) + " s)");
		if(debug) System.out.print("Parsing transition labels... ");
		starttime = System.nanoTime();
		GraphActionParser.parseTransitions(ba);
		if(debug) System.out.println("done (" + ((System.nanoTime() - starttime) / 1000000000l) + " s)");

		// Generate Product Automata of Did/Can Expanded Graph and Buchi Automata of LTL formula
		if(debug) System.out.print("Generating product automaton... ");
		starttime = System.nanoTime();
		final GeneralGraph pa = ProductTranslator.translate(dcts, ba);
		if(debug) System.out.println("done (" + ((System.nanoTime() - starttime) / 1000000000l) + " s)");

		// Check Property via reachable cycle detection
		if(debug) System.out.print("Checking property... ");
		starttime = System.nanoTime();
		final PersistenceChecker pc = new PersistenceChecker(pa);
		pc.run();
		if(debug) System.out.println("done (" + ((System.nanoTime() - starttime) / 1000000000l) + " s)\n");

		return pc.getCounterexample();
	}
}
