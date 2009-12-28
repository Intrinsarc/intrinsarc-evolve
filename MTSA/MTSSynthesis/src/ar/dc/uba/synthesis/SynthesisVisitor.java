package ar.dc.uba.synthesis;

import ac.ic.doc.mtstools.model.*;
import ar.dc.uba.model.language.*;
import ar.dc.uba.model.lsc.*;
import ar.dc.uba.model.structure.*;

/**
 * Synthesises a MTS from a triggered scenario
 * 
 * @author gsibay
 * 
 */
public class SynthesisVisitor {

	private static SynthesisVisitor instance;

	public static SynthesisVisitor getInstance() {
		if (instance == null) {
			instance = new SynthesisVisitor();
		}
		return instance;
	}

	/**
	 * Synthesis from an eTS
	 * 
	 * @param eTS
	 * @return
	 */
	public MTS<SynthesizedState, Symbol> visitETriggeredScenario(
			ExistentialTriggeredScenario eTS) {
		return new MTSFromETriggeredScenarioSynthesiser().synthesise(eTS);
	}

	/**
	 * Synthesis from a uTS
	 * 
	 * @param uTS
	 * @return
	 */
	public MTS<SynthesizedState, Symbol> visitUTriggeredScenario(
			UniversalTriggeredScenario uTS) {
		return new MTSFromUTriggeredScenarioSynthesiser().synthesise(uTS);
	}
}
