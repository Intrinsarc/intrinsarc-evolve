package lts.chart;

import java.util.*;

import lts.*;
import lts.chart.util.*;
import ac.ic.doc.mtstools.model.*;
import ac.ic.doc.mtstools.util.fsp.*;
import ar.dc.uba.model.lsc.*;
import ar.dc.uba.model.structure.*;

/**
 * @author gsibay
 * 
 */
public abstract class TriggeredScenarioDefinition {

	private Symbol name;
	private Set<String> instances;
	private BasicChartDefinition prechart;
	private BasicChartDefinition mainchart;
	private Set<Interaction> restricted;
	private Set<ConditionDefinition> conditionDefinitions;

	private static Hashtable<String, TriggeredScenarioDefinition> definitions = new Hashtable<String, TriggeredScenarioDefinition>();

	public final static void init() {
		definitions = new Hashtable<String, TriggeredScenarioDefinition>();
	}

	public TriggeredScenarioDefinition(Symbol n) {
		this.name = n;
		this.conditionDefinitions = new HashSet<ConditionDefinition>();
	}

	public TriggeredScenarioDefinition(String name) {
		this(new Symbol(Symbol.UPPERIDENT, name));
	}

	public static void put(TriggeredScenarioDefinition chart)
			throws DuplicatedTriggeredScenarioDefinitionException {
		if (definitions.put(chart.getName().toString(), chart) != null) {
			throw new DuplicatedTriggeredScenarioDefinitionException(chart
					.getName());
		}
	}

	/**
	 * Transforms this triggered scenario to a triggered scenario that can be an
	 * input for the synthesis algorithm
	 * 
	 * @param locationNamingStrategyImpl
	 * @param definition
	 * @return
	 * @throws TriggeredScenarioTransformationException
	 */
	public abstract TriggeredScenario adapt(
			LocationNamingStrategyImpl locationNamingStrategyImpl)
			throws TriggeredScenarioTransformationException;

	/**
	 * Returns the definition with that name. An IllegalArgumentException is
	 * thrown if not found.
	 * 
	 * @param definitionName
	 * @return
	 */
	public static TriggeredScenarioDefinition getDefinition(
			Symbol definitionName) {
		TriggeredScenarioDefinition definition = definitions.get(definitionName
				.toString());
		if (definition == null) {
			throw new IllegalArgumentException(
					"Triggered scenario definition not found: "
							+ definitionName.toString());
		}
		return definition;
	}

	public void addConditionDefinition(ConditionDefinition conditionDefinition) {
		if (this.conditionDefinitions.contains(conditionDefinition)) {
			Diagnostics.fatal("duplicate Condition definition: "
					+ conditionDefinition.getName(), conditionDefinition
					.getName());
		} else {
			this.conditionDefinitions.add(conditionDefinition);
		}
	}

	public Set<ConditionDefinition> getConditionDefinitions() {
		return this.conditionDefinitions;
	}

	public ConditionDefinition getConditionDefinition(String conconditionId) {
		ConditionDefinition searchedCondition = null;
		Iterator<ConditionDefinition> definitionsIt = this.conditionDefinitions
				.iterator();
		while (searchedCondition == null && definitionsIt.hasNext()) {
			ConditionDefinition currentCondition = definitionsIt.next();
			if (conconditionId.equals(currentCondition.getName())) {
				searchedCondition = currentCondition;
			}
		}
		return searchedCondition;
	}

	public boolean hasCondition(String conditionName) {
		boolean found = false;
		for (ConditionDefinition conditionDefinition : this.conditionDefinitions) {
			found = found
					|| conditionDefinition.getName().equals(conditionName);
		}
		return found;
	}

	public static boolean contains(Symbol n) {
		// if (definitions==null) return false;
		return definitions.containsKey(n.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ic.doc.ltsa.common.iface.IChartDefinition#synthesiseAll(ic.doc.ltsa.common
	 * .iface.LTSOutput)
	 */
	public static Collection<CompactState> synthesiseAll(LTSOutput output)
			throws TriggeredScenarioTransformationException {
		Collection<CompactState> compactStateCollection = new LinkedList<CompactState>();
		Enumeration<TriggeredScenarioDefinition> lscDefinitions = definitions
				.elements();
		while (lscDefinitions.hasMoreElements()) {
			TriggeredScenarioDefinition definition = (TriggeredScenarioDefinition) lscDefinitions
					.nextElement();
			compactStateCollection.add(definition.synthesise(output));
		}
		return compactStateCollection;
	}

	public CompactState synthesise(LTSOutput output)
			throws TriggeredScenarioTransformationException {
		TriggeredScenario triggeredScenario = this
				.adapt(new LocationNamingStrategyImpl());
		output.outln("Synthesising from triggered scenario: ");
		output.outln(this.getName().toString());
		output.outln(triggeredScenario.toString());

		// Synthesise the MTS from the triggered scenario
		MTS<SynthesizedState, ar.dc.uba.model.language.Symbol> synthesisedMTS = MTSSynthesiserFacade
				.getInstance().synthesise(triggeredScenario);

		// First convert the MTS to a <Long, String> MTS.
		// That is the states are long and the transitions are strings
		GenericMTSToLongStringMTSConverter toLongStringMTSConverter = new GenericMTSToLongStringMTSConverter();

		// Use the converter from MTS<Long,String> to CompactState
		return MTSToAutomataConverter.getInstance().convert(
				toLongStringMTSConverter.convert(synthesisedMTS),
				this.getName().toString());
	}

	public Set<String> getInstances() {
		return instances;
	}

	public void setInstances(Set<String> instances) {
		this.instances = instances;
	}

	public BasicChartDefinition getPrechart() {
		return prechart;
	}

	public void setPrechart(BasicChartDefinition prechart) {
		this.prechart = prechart;
	}

	public BasicChartDefinition getMainchart() {
		return mainchart;
	}

	public void setMainchart(BasicChartDefinition mainchart) {
		this.mainchart = mainchart;
	}

	public Symbol getName() {
		return this.name;
	}

	public Set<Interaction> getRestricted() {
		return restricted;
	}

	public void setRestricted(Set<Interaction> restricted) {
		this.restricted = restricted;
	}
}
