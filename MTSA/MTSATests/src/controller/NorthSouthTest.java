package controller;

import java.util.HashSet;
import java.util.Set;

import ar.dc.uba.model.condition.Fluent;
import ar.dc.uba.model.condition.FluentImpl;
import ar.dc.uba.model.condition.FluentPropositionalVariable;
import controller.model.ControllerGoal;

public class NorthSouthTest extends MTSParsedTestsBase {

	public void testNorthSouthCaseStudy() throws Exception {
		synthesiseInput("NorthSouth");
	}

	protected void setControllableActions(ControllerGoal goal) {
		goal.addAllControllableActions(ControllerTestsUtils.buildStringsSetFrom(new String[] {
				"mn", "ms" }));
	}

	protected void setAssumeAndGuarantee(ControllerGoal goal) {
		Fluent fluentNorthFar = new FluentImpl("northFar", ControllerTestsUtils
				.buildSymbolSetFrom(new String[] { "northFar" }), ControllerTestsUtils
				.buildSymbolSetFrom(new String[] { "northClose", "southClose", "southFar" }), false);
		Fluent fluentSouthFar = new FluentImpl("southFar", ControllerTestsUtils
				.buildSymbolSetFrom(new String[] { "southFar" }), ControllerTestsUtils
				.buildSymbolSetFrom(new String[] { "northClose", "southClose", "northFar" }), false);
		Fluent fluentOpenDoor = new FluentImpl("openDoor", ControllerTestsUtils
				.buildSymbolSetFrom(new String[] { "do" }), ControllerTestsUtils
				.buildSymbolSetFrom(new String[] { "dc" }), true);

		Set<Fluent> fluents = new HashSet<Fluent>();
		fluents.add(fluentNorthFar);
		fluents.add(fluentSouthFar);
		fluents.add(fluentOpenDoor);

		goal.addAllFluents(fluents);

		goal.addAssume(new FluentPropositionalVariable(fluentOpenDoor));
		goal.addGuarantee(new FluentPropositionalVariable(fluentNorthFar));
		goal.addGuarantee(new FluentPropositionalVariable(fluentSouthFar));
	}

	protected String getInputModel() {
		return "TopFreeM = Q0,Q0	= (northFar -> Q1),Q1	= (ms -> Q2	  |ms -> Q39),Q2	= (dc -> Q3),Q3	= (northClose -> Q4),Q4	= (mn -> Q5	  |mn -> Q38),Q5	= (northFar -> Q6),Q6	= (ms -> Q7	  |ms -> Q8),Q7	= (dc -> Q3),Q8	= (do -> Q9),Q9	= (northClose -> Q10),Q10	= (mn -> Q11	  |mn -> Q12	  |ms -> Q14	  |ms -> Q36),Q11	= (dc -> Q5),Q12	= (do -> Q13),Q13	= (northFar -> Q1),Q14	= (dc -> Q15),Q15	= (southClose -> Q16),Q16	= (ms -> Q17	  |ms -> Q35),Q17	= (dc -> Q18),Q18	= (southFar -> Q19),Q19	= (mn -> Q20	  |ms -> Q34),Q20	= (do -> Q21),Q21	= (southClose -> Q22),Q22	= (mn -> Q23	  |mn -> Q25	  |ms -> Q27	  |ms -> Q28),Q23	= (dc -> Q24),Q24	= (northClose -> Q4),Q25	= (do -> Q26),Q26	= (northClose -> Q10),Q27	= (dc -> Q18),Q28	= (do -> Q29),Q29	= (southFar -> Q30),Q30	= (mn -> Q31	  |mn -> Q33),Q31	= (dc -> Q32),Q32	= (southClose -> Q16),Q33	= (do -> Q21),Q34	= (dc -> Q32),Q35	= (do -> Q29),Q36	= (do -> Q37),Q37	= (southClose -> Q22),Q38	= (do -> Q13),Q39	= (do -> Q9).";
	}

}
