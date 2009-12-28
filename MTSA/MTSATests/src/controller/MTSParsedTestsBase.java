package controller;

import java.util.Collections;

import junit.framework.TestCase;
import lts.CompositeState;
import ui.StandardOutput;
import ac.ic.doc.mtsa.MTSCompiler;
import ac.ic.doc.mtstools.model.MTS;
import ac.ic.doc.mtstools.model.MTS.TransitionType;
import ac.ic.doc.mtstools.model.impl.WeakSimulationSemantics;
import ac.ic.doc.mtstools.util.fsp.AutomataToMTSConverter;
import controller.model.ControllerGoal;
import controller.model.gr.GRControllerGoal;
import dispatcher.TransitionSystemDispatcher;

public abstract class MTSParsedTestsBase extends TestCase {

	protected abstract void setControllableActions(ControllerGoal goal);

	protected abstract void setAssumeAndGuarantee(ControllerGoal goal);

	protected abstract String getInputModel();

	protected MTS<Long, String> synthesiseInput(String modelName) throws Exception {
		
		CompositeState c = MTSCompiler.getInstance().compileCompositeState(getInputModel(),
				modelName);
		MTS<Long, String> mts = MTSCompiler.getInstance().compileMTS(getInputModel(), modelName);

		c.goal = this.buildGoal();
		TransitionSystemDispatcher.synthesiseController(c, new StandardOutput());

		MTS<Long, String> synMTS = AutomataToMTSConverter.getInstance().convert(c.getComposition());

		System.out.println(synMTS);

		for (Long state : synMTS.getStates()) {
			assertNotNull(synMTS.getTransitions(state, TransitionType.REQUIRED));
		}
		
		WeakSimulationSemantics weakSimulationSemantics = new WeakSimulationSemantics(Collections
				.emptySet());

		boolean refinement = TransitionSystemDispatcher.isRefinement(mts, " original ", synMTS,
				" synthesised ", weakSimulationSemantics, new StandardOutput());

		assertTrue(refinement);
		
		for (Long state : synMTS.getStates()) {
			assertTrue(synMTS.getTransitions(state, TransitionType.REQUIRED).size()>0);
		}
		
		return synMTS;
	}

	protected ControllerGoal buildGoal() {
		ControllerGoal goal = new GRControllerGoal();
		this.setControllableActions(goal);
		this.setAssumeAndGuarantee(goal);
		return goal;
	}

}
