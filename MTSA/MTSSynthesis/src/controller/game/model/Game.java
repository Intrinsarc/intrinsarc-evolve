package controller.game.model;

import java.util.*;

import org.apache.commons.lang.*;

import controller.game.util.*;

public class Game<State> {

	private Set<State> faults;
	private Guarantees<State> guarantees;
	private Assumptions<State> assumptions;
	private Map<State, Set<State>> predecessors;
	private Map<State, Set<State>> uncontrollableSuccessors;
	private Map<State, Set<State>> controllableSuccessors;
	private Set<State> uncontrollable;
	private Set<State> states;

	public Game(Set<State> states) {
		this.states = states;
		this.predecessors = new HashMap<State, Set<State>>();
		this.uncontrollable = new HashSet<State>();
		this.uncontrollableSuccessors = new HashMap<State, Set<State>>();
		this.controllableSuccessors = new HashMap<State, Set<State>>();
		this.faults = new HashSet<State>();
		this.basicInitialisation();
	}

	private void basicInitialisation() {
		for (State state : this.states) {
			this.predecessors.put(state, new HashSet<State>());
			this.uncontrollableSuccessors.put(state, new HashSet<State>());
			this.controllableSuccessors.put(state, new HashSet<State>());
		}
	}

	public void setAssumptions(Assumptions<State> assumptions) {
		Validate.isTrue(GameValidationHelper.areValidAssumptions(this,
				assumptions),
				"Every state in assumptions set must be in game's state set.");
		if (assumptions.getSize() == 0) {
			Assume<State> trueAssume = new Assume<State>();
			for (State state : this.getStates()) {
				trueAssume.addState(state);
			}
			assumptions.addAssume(trueAssume);
		}

		this.assumptions = assumptions;
	}

	// DIPI: Change Needed. Guarantees and assumptions should be parameters to
	// the constructor.
	public void setGuarantees(Guarantees<State> guarantees) {
		Validate.isTrue(GameValidationHelper.areValidGuarantees(this,
				guarantees),
				"Every state in guarantees set must be in game's state set.");
		this.guarantees = guarantees;
	}

	public void setFaults(Set<State> faults) {
		Validate.isTrue(GameValidationHelper.areValid(this, faults),
				"Every state in faults set must be in game's state set.");
		this.faults = faults;
	}

	public Guarantee<State> getGuarantee(int guaranteeId) {
		return this.guarantees.getGuarantee(guaranteeId);
	}

	public Assume<State> getAssumption(int assumeId) {
		return this.assumptions.getAssume(assumeId);
	}

	public int getGuaranteesQuantity() {
		return this.guarantees.size();
	}

	public int getAssumptionsQuantity() {
		return this.assumptions.getSize();
	}

	public boolean isUncontrollable(State state) {
		return this.uncontrollable.contains(state);
	}

	public Set<State> getUncontrollableSuccessors(State state) {
		return this.uncontrollableSuccessors.get(state);
	}

	public Set<State> getControllableSuccessors(State state) {
		return this.controllableSuccessors.get(state);
	}

	public Set<State> getPredecessors(State state) {
		return this.predecessors.get(state);
	}

	public Set<State> getStates() {
		return this.states;
	}

	public Guarantees<State> guarantees() {
		return this.guarantees;
	}

	public Guarantees<State> getGuarantees() {
		return this.guarantees;
	}

	public Assumptions<State> getAssumptions() {
		return this.assumptions;
	}

	public void addControllableSuccessor(State state1, State state2) {
		this.validateNewState(state1);
		if (!this.controllableSuccessors.containsKey(state1)) {
			this.controllableSuccessors.put(state1, new HashSet<State>());
		}
		this.controllableSuccessors.get(state1).add(state2);
	}

	private void validateNewState(State state1) {
		Validate.isTrue(this.states.contains(state1), "State:" + state1
				+ "it is not part of the game.");
	}

	public void addPredecessor(State predeccessor, State successor) {
		Validate.isTrue(this.states.contains(predeccessor));
		Validate.isTrue(this.states.contains(successor));
		this.predecessors.get(successor).add(predeccessor);
	}

	public void addUncontrollableSuccessor(State predecessor, State successor) {
		if (!this.uncontrollableSuccessors.containsKey(predecessor)) {
			this.uncontrollableSuccessors
					.put(predecessor, new HashSet<State>());
		}
		this.uncontrollableSuccessors.get(predecessor).add(successor);
		this.uncontrollable.add(predecessor);
		this.addPredecessor(predecessor, successor);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer().append("Game:")
				.append("States: [").append(this.getStates()).append("]")
				.append("\n").append("Uncontrollable States: ").append(
						this.uncontrollable).append("\n").append(
						"Transitions: \n");
		for (State state : this.getStates()) {
			for (State succ : this.getControllableSuccessors(state)) {
				sb.append(" c[").append(state).append("->").append(succ)
						.append("]");
			}
			for (State succ : this.getUncontrollableSuccessors(state)) {
				sb.append(" u[").append(state).append("->").append(succ)
						.append("]");
			}
		}
		sb.append("\n").append("Assumptions: ").append(this.getAssumptions())
				.append("\n").append("Guarantees: ").append(
						this.getGuarantees());
		return sb.toString();
	}

	public Set<State> getFaults() {
		return faults;
	}
}
