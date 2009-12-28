package lts.controller;

import java.util.*;

import lts.*;

public class ControllerGoalDefinition {
	private Symbol name;
	private Symbol controllableActionSet;
	private Set<Symbol> safetyDefinitions;
	private Set<Symbol> assumeDefinitions;
	private Set<Symbol> guaranteeDefinitions;
	private Set<Symbol> faultsDefinitions;

	private static Hashtable<String, ControllerGoalDefinition> definitions = new Hashtable<String, ControllerGoalDefinition>();

	public final static void init() {
		definitions = new Hashtable<String, ControllerGoalDefinition>();
	}

	public ControllerGoalDefinition(Symbol current) {
		this.name = current;
		this.safetyDefinitions = new HashSet<Symbol>();
		this.assumeDefinitions = new HashSet<Symbol>();
		this.guaranteeDefinitions = new HashSet<Symbol>();
		this.faultsDefinitions = new HashSet<Symbol>();
	}

	public ControllerGoalDefinition(String name) {
		this(new Symbol(Symbol.UPPERIDENT, name));
	}

	public static void put(ControllerGoalDefinition goal) {
		if (definitions.put(goal.getNameString(), goal) != null) {
			Diagnostics.fatal("duplicate Goal definition: " + goal.getName(),
					goal.getName());
		}
	}

	public static ControllerGoalDefinition getDefinition(Symbol definitionName) {
		ControllerGoalDefinition definition = definitions.get(definitionName
				.getName());
		if (definition == null) {
			throw new IllegalArgumentException(
					"Controller Goal definition not found: " + definitionName);
		}
		return definition;
	}

	public Set<Symbol> getGuaranteeDefinitions() {
		return guaranteeDefinitions;
	}

	public Set<Symbol> getFaultsDefinitions() {
		return faultsDefinitions;
	}

	public Set<Symbol> getSafetyDefinitions() {
		return safetyDefinitions;
	}

	public boolean addGuaranteeDefinition(Symbol guaranteeDefinition) {
		if (!this.guaranteeDefinitions.add(guaranteeDefinition)) {
			Diagnostics.fatal("Duplicate Guarantee : "
					+ guaranteeDefinition.getName(), guaranteeDefinition
					.getName());
			return false;
		} else {
			return true;
		}
	}

	public boolean addFaultDefinition(Symbol faultDefinition) {
		if (!this.faultsDefinitions.add(faultDefinition)) {
			Diagnostics.fatal("Duplicate Fault : " + faultDefinition.getName(),
					faultDefinition.getName());
			return false;
		} else {
			return true;
		}
	}

	public boolean addAssumeDefinition(Symbol assumeDefinition) {
		if (!this.assumeDefinitions.add(assumeDefinition)) {
			Diagnostics.fatal("Duplicate Assumption : "
					+ assumeDefinition.getName(), assumeDefinition.getName());
			return false;
		} else {
			return true;
		}
	}

	public boolean addSafetyDefinition(Symbol safetyDefinition) {
		if (!this.safetyDefinitions.add(safetyDefinition)) {
			Diagnostics.fatal("duplicate Safety requirement: "
					+ safetyDefinition.getName(), safetyDefinition.getName());
			return false;
		} else {
			return true;
		}
	}

	public Set<Symbol> getAssumeDefinitions() {
		return assumeDefinitions;
	}

	public Symbol getName() {
		return this.name;
	}

	public String getNameString() {
		return name.getName();
	}

	public void setSafetyDefinitions(Set<Symbol> safetyDefinitions) {
		this.safetyDefinitions = safetyDefinitions;
	}

	public void setAssumeDefinitions(Set<Symbol> assumeDefinitions) {
		this.assumeDefinitions = assumeDefinitions;
	}

	public void setGuaranteeDefinitions(Set<Symbol> guaranteeDefinitions) {
		this.guaranteeDefinitions = guaranteeDefinitions;
	}

	public Symbol getControllableActionSet() {
		return controllableActionSet;
	}

	public void setControllableActionSet(Symbol controllable) {
		this.controllableActionSet = controllable;
	}

	public void setFaultsDefinitions(Set<Symbol> faultsDefinitions) {
		this.faultsDefinitions = faultsDefinitions;
	}

}
