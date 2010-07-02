package hardcoded;

public class LTSARunner
{
	public static void main(String args[])
	{
		new LTSAFactory().initialize(null).getRun().run(args);
	}
}
