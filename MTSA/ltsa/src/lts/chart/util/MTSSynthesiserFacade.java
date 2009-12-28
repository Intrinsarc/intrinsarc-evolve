package lts.chart.util;

import ac.ic.doc.mtstools.model.*;
import ar.dc.uba.model.language.*;
import ar.dc.uba.model.lsc.*;
import ar.dc.uba.model.structure.*;
import ar.dc.uba.synthesis.*;

/**
 * Encapsulates the call to the synthesis algorithm.
 * 
 * @author gsibay
 * 
 */
public class MTSSynthesiserFacade {

	static private MTSSynthesiserFacade instance = new MTSSynthesiserFacade();

	static public MTSSynthesiserFacade getInstance() {
		return instance;
	}

	private MTSSynthesiserFacade() {
	}

	public MTS<SynthesizedState, Symbol> synthesise(
			TriggeredScenario triggeredScenario) {
		return triggeredScenario.acceptSynthesisVisitor(SynthesisVisitor
				.getInstance());
	}
}
