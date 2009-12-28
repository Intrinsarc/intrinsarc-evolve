package controller.game.model;

import junit.framework.TestCase;

public class GameSolverBaseTestCase extends TestCase {

	public void testMock() throws Exception {}
	
	protected void fillPredecessors(Game<Long> game) {
		for (Long from : game.getStates()) {
			if (!game.isUncontrollable(from)) {
				for (Long state : game.getControllableSuccessors(from)) {
					game.addPredecessor(from, state);
				}
			}
		}

	}
	
	protected Assumptions<Long> buildSingleStateAssumption(long state1) {
		Assumptions<Long> assumptions = new Assumptions<Long>();
		addAssumptionFor(state1, assumptions);
		return assumptions;
	}

	private void addAssumptionFor(long state1, Assumptions<Long> assumptions) {
		Assume<Long> assume = new Assume<Long>();
		assume.addState(state1);
		assumptions.addAssume(assume);
	}

	protected Guarantees<Long> buildSingleStateGuarantee(long state2) {
		Guarantees<Long> guarantees = new Guarantees<Long>();
		guarantees.addGuarantee(buildGuarantee(state2));
		return guarantees;
	}

	protected Guarantee<Long> buildGuarantee(long state) {
		Guarantee<Long> guarantee = new Guarantee<Long>();
		guarantee.addState(state);
		return guarantee;
	}

}
