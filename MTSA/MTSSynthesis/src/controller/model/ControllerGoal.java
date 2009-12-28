package controller.model;

import java.util.*;

import ar.dc.uba.model.condition.*;

/**
 * DIPI: Needs check. This interface it's mixed. it allows the user to work with
 * asumes and guarantees while he's working with fluents and formulas.
 * 
 * @author dipi
 * 
 */
// DIPI: rethinks needed. This interface may be similar to game stuf. Faults and
// assume will not be present for every controller.
public interface ControllerGoal {

	public abstract boolean addAssume(Formula assume);

	public abstract boolean addGuarantee(Formula guarantee);

	public abstract boolean addAllFluents(Set<Fluent> involvedFluents);

	public abstract Set<Fluent> getFluents();

	public abstract Set<Formula> getFaults();

	public abstract Set<Formula> getAssumptions();

	public abstract Set<Formula> getGuarantees();

	public abstract Set<String> getControllableActions();

	public abstract void addAllControllableActions(
			Set<String> controllableActions);

	public abstract void addFault(Formula adaptFormulaAndCreateFluents);

}