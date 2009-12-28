package run;

import hardcoded_state.*;

public class TestState
{
	public static void main(String[] args)
	{
		new TrafficSimulatorFactory().initialize(null).getRun().run(args);
	}
}
