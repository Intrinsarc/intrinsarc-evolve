package lts.chart;

import lts.*;
import lts.chart.util.*;
import ar.dc.uba.model.lsc.*;

/**
 * @author gsibay
 * 
 */
public class ExistentialTriggeredScenarioDefinition extends
		TriggeredScenarioDefinition {

	public static void put(TriggeredScenarioDefinition chart) {
		throw new UnsupportedOperationException(
				"Use the superclass to hold the definitions");
	}

	public ExistentialTriggeredScenarioDefinition(Symbol symbol) {
		super(symbol);
	}

	public ExistentialTriggeredScenarioDefinition(String name) {
		super(name);
	}

	@Override
	public TriggeredScenario adapt(
			LocationNamingStrategyImpl locationNamingStrategyImpl)
			throws TriggeredScenarioTransformationException {
		return TriggeredScenarioDefinitionToTriggeredScenario.getInstance()
				.transformExistentialTriggeredScenario(this,
						new LocationNamingStrategyImpl());
	}

}
