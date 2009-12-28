package lts.controller;

import lts.controller.util.*;
import ac.ic.doc.commons.relations.*;
import ac.ic.doc.mtstools.model.*;
import controller.*;
import controller.model.*;

public class MTSControllerSynthesisFacade {
	private static MTSControllerSynthesisFacade instance = new MTSControllerSynthesisFacade();

	public static MTSControllerSynthesisFacade getInstance() {
		return instance;
	}

	private ControllerSynthesiser synthesiser = ControllerSynthesiser
			.getInstance();

	private MTSControllerSynthesisFacade() {
	}

	public MTS<Long, String> synthesisePlainStateController(
			MTS<Long, String> mts, ControllerGoal goal) {
		MTS<Pair<Long, Integer>, String> synthesised = synthesiser.synthesise(
				mts, goal);
		return ControllerUtils.rankedToPlain(synthesised);
	}
}
