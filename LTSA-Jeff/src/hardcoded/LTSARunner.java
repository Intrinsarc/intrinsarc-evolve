package hardcoded;

public class LTSARunner
{
	public static void main(String args[])
	{
		new LTSAFactory().initialize(null, null).getRun().run(args);
	}
}
