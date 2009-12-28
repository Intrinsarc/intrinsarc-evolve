package controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import lts.CompactState;
import lts.EmptyLTSOuput;
import ac.ic.doc.mtstools.model.MTS;
import ac.ic.doc.mtstools.model.impl.MTSImpl;
import ac.ic.doc.mtstools.util.fsp.MTSToAutomataConverter;
import ar.dc.uba.model.condition.Fluent;
import ar.dc.uba.model.condition.FluentImpl;
import ar.dc.uba.model.condition.FluentPropositionalVariable;
import controller.game.util.MTSToGRGameBuilder;
import controller.model.ControllerGoal;
import controller.model.gr.GRControllerGoal;
import dispatcher.TransitionSystemDispatcher;

public class MTSInconsistentTests extends TestCase {
	public void testSingleLoop() throws Exception {
		Long state0 = 0L;
		MTS<Long, String> mts = new MTSImpl<Long, String>(state0);
		mts.addAction("a");
		mts.addAction("b");
		mts.addRequired(state0, "a", state0);
		
		ControllerGoal goal = new GRControllerGoal();
		goal.addAllControllableActions(Collections.singleton("a"));
		
		Fluent fluentA = new FluentImpl("fluentA", 
				ControllerTestsUtils.buildSymbolSetFrom(new String[]{"a"}),
				ControllerTestsUtils.buildSymbolSetFrom(new String[]{"b"}), 
				false);

		Set<Fluent> fluents = new HashSet<Fluent>();
		fluents.add(fluentA);

		goal.addAllFluents(fluents);
		
		goal.addAssume(new FluentPropositionalVariable(fluentA));
		goal.addGuarantee(new FluentPropositionalVariable(fluentA));

		try {
			MTSToGRGameBuilder.getInstance().buildGameFrom(mts, goal);
			fail();

		} catch (IllegalArgumentException e) {
			//success!!
		}
		
		CompactState convert = MTSToAutomataConverter.getInstance().convert(mts, "dipi");
		convert.setGoal(goal);
		CompactState synthesiseController;
		try {
			synthesiseController = TransitionSystemDispatcher.synthesiseController(convert, new EmptyLTSOuput());
			fail();
		} catch (IllegalArgumentException e) {
			//success
		}
	}
}
