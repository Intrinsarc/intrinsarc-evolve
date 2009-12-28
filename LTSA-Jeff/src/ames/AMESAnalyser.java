package ames;

import java.util.*;

import lts.*;
import lts.ltl.*;

import com.hopstepjump.backbone.runtime.api.*;

public class AMESAnalyser
{
// start generated code
// attributes
	private Attribute<java.lang.Integer> stateCount;
	private Attribute<java.lang.Boolean> checkDeadlock;
	private Attribute<java.lang.Boolean> multiCE;
// required ports
	private ames.IExtendedAnalyser analyser;
// provided ports
	private IAnalyserMainImpl main_IAnalyserProvided = new IAnalyserMainImpl();
// setters and getters
	public Attribute<java.lang.Integer> getStateCount() { return stateCount; }
	public void setStateCount(Attribute<java.lang.Integer> stateCount) { this.stateCount = stateCount;}
	public void setRawStateCount(java.lang.Integer stateCount) { this.stateCount.set(stateCount);}
	public Attribute<java.lang.Boolean> getCheckDeadlock() { return checkDeadlock; }
	public void setCheckDeadlock(Attribute<java.lang.Boolean> checkDeadlock) { this.checkDeadlock = checkDeadlock;}
	public void setRawCheckDeadlock(java.lang.Boolean checkDeadlock) { this.checkDeadlock.set(checkDeadlock);}
	public Attribute<java.lang.Boolean> getMultiCE() { return multiCE; }
	public void setMultiCE(Attribute<java.lang.Boolean> multiCE) { this.multiCE = multiCE;}
	public void setRawMultiCE(java.lang.Boolean multiCE) { this.multiCE.set(multiCE);}
	public void setAnalyser_IExtendedAnalyser(ames.IExtendedAnalyser analyser) { this.analyser = analyser; }
	public lts.IAnalyser getMain_IAnalyser(Class<?> required) { return main_IAnalyserProvided; }
// end generated code
	
	private LTSOutput output;

	// >>> AMES: Multiple counterexamples
	List<List<String>> traces;

	public List<List<String>> getErrorTraces() {
		return traces;
	}

	private class IAnalyserMainImpl implements lts.IAnalyser
	{
		// >>> AMES: Deadlock Insensitive Analysis, multiple counterexamples
		public void analyse()
		{
			output.outln("Analysing...");
			System.gc(); // garbage collect before start
			long start = System.currentTimeMillis();
			int ret = newState_analyse(analyser.getStateCodec().zero(), null);
			long finish = System.currentTimeMillis();
			if (ret == ExtendedAnalyser.DEADLOCK)
			{
				output.outln("Trace to DEADLOCK:");
				analyser.printTracePath();
			}
			else if (ret == ExtendedAnalyser.ERROR)
			{
				output.outln("Trace to property violation in " + analyser.getErrorStateMachine().name + ":");
				analyser.printTracePath();
			}
			else
			{
				output.outln("No " + (checkDeadlock.get() ? "deadlocks/" : "") + "errors");
			}
			output.outln("Analysed in: " + (finish - start) + "ms");
		}

		@Override
		public void activate(CompositeState current, LTSOutput outputImpl, boolean ignoreAsterisk)
		{
			output = outputImpl;
			analyser.activate(current, outputImpl, ignoreAsterisk);
		}

		@Override
		public void analyse(FluentTrace tracer)
		{
			analyser.analyse(tracer);
		}

		@Override
		public CompactState composeNoHide()
		{
			return analyser.composeNoHide();
		}

		@Override
		public IAnimator getAnimator()
		{
			return analyser.getAnimator();
		}

		@Override
		public List getErrorTrace()
		{
			return analyser.getErrorTrace();
		}
	}
	
	// >>> AMES: Deadlock Insensitive Analysis, multiple counterexamples
	private int newState_analyse(byte[] fromState, byte[] target)
	{
		traces = new LinkedList<List<String>>();

		boolean error = false; // dimitra
		stateCount.set(0);
		int nTrans = 0; // number of transitions
		MyHashQueue hh = new MyHashQueue(100001);
		analyser.makePossiblePartialOrderReduction(hh);
		hh.addPut(fromState, 0, null);
		Integer dummy = new Integer(-1);
		Runtime r = Runtime.getRuntime();
		MyHashQueueEntry qe = null;
		while (!hh.empty()) {
			qe = hh.peek();
			fromState = qe.key;
			int[] state = analyser.getStateCodec().decode(fromState);
			stateCount.set(stateCount.get() + 1);
			if (stateCount.get() % 10000 == 0) {
				output.out("Depth " + hh.depth(qe) + " ");
				analyser.outStatistics(nTrans);
			}
			// determine eligible transitions
			List transitions = analyser.eligibleTransitions(state);
			hh.pop();
			if (transitions == null) {
				if (checkDeadlock.get() && !analyser.isEND(state)) {
					output.out("Depth " + hh.depth(qe) + " ");
					analyser.outStatistics(nTrans);
					analyser.setTrace(hh, qe);
					return ExtendedAnalyser.DEADLOCK;
				}
			} else {
				Iterator e = transitions.iterator();
				while (e.hasNext()) {
					int[] next = (int[]) e.next();
					byte[] code = analyser.getStateCodec().encode(next);
					nTrans++;
					if (code == null || StateCodec.equals(code, target)) {
						output.out("Depth " + hh.depth(qe) + " ");
						analyser.outStatistics(nTrans);
						if (code == null) {
							int i = 0;
							while (next[i] >= 0)
								i++;
							analyser.setErrorMachine(i);
						}
						LinkedList trace = analyser.setTrace(hh, qe);
						trace.add(analyser.getActionName(next[analyser.getNmach()])); // last action to
						// ERROR
						if (code == null && multiCE.get()) {
							error = true;

							traces.add(trace);

							output.outln("One counterexample is:");
							analyser.printTracePath();
						} else if (code == null)
							return ExtendedAnalyser.ERROR;
						else
							return ExtendedAnalyser.FOUND;
					} else if (!hh.containsKey(code)) {
						hh.addPut(code, next[analyser.getNmach()], qe);
					}
				}
			}
		}
		if (error)
			return ExtendedAnalyser.ERROR;
		output.out("Depth " + hh.depth(qe) + " ");
		analyser.outStatistics(nTrans);
		return ExtendedAnalyser.SUCCESS;
	}
}
