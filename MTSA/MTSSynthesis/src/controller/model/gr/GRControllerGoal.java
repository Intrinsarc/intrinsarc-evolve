package controller.model.gr;

import java.util.*;

import org.apache.commons.collections15.*;

import ar.dc.uba.model.condition.*;
import controller.model.*;

public class GRControllerGoal implements ControllerGoal {
	private Set<Formula> faults;
	private Set<Formula> assumptions;
	private Set<Formula> guarantees;
	private Set<Fluent> fluents;
	private Set<String> controllableActions;

	public GRControllerGoal() {
		this.faults = new HashSet<Formula>();
		this.assumptions = new HashSet<Formula>();
		this.guarantees = new HashSet<Formula>();
		this.fluents = new HashSet<Fluent>();
		this.controllableActions = new HashSet<String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.model.gr.ControllerGoal#addAssume(ar.dc.uba.model.condition
	 * .Formula)
	 */
	public boolean addAssume(Formula assume) {
		return this.assumptions.add(assume);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.model.gr.ControllerGoal#addGuarantee(ar.dc.uba.model.condition
	 * .Formula)
	 */
	public boolean addGuarantee(Formula guarantee) {
		return this.guarantees.add(guarantee);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.model.gr.ControllerGoal#addAllFluents(java.util.Set)
	 */
	public boolean addAllFluents(Set<Fluent> involvedFluents) {
		return this.fluents.addAll(involvedFluents);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.model.gr.ControllerGoal#getFluents()
	 */
	public Set<Fluent> getFluents() {
		return SetUtils.unmodifiableSet(this.fluents);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.model.gr.ControllerGoal#getAssumptions()
	 */
	public Set<Formula> getAssumptions() {
		return assumptions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.model.gr.ControllerGoal#getGuarantees()
	 */
	public Set<Formula> getGuarantees() {
		return guarantees;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.model.gr.ControllerGoal#getControllableActions()
	 */
	public Set<String> getControllableActions() {
		return controllableActions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.model.gr.ControllerGoal#addAllControllableActions(java.util
	 * .Set)
	 */
	public void addAllControllableActions(Set<String> controllableActions) {
		this.controllableActions.addAll(controllableActions);
	}

	@Override
	public void addFault(Formula faultFormula) {
		this.faults.add(faultFormula);
	}

	@Override
	public Set<Formula> getFaults() {
		return this.faults;
	}

}
