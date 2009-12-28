package statetest;


public class Logger
{
// start generated code
	private statetest.ILogger log_ILoggerProvided = new ILoggerLogImpl();
	public statetest.ILogger getLog_ILogger(Class<?> required) { return log_ILoggerProvided; }
// end generated code

	private class ILoggerLogImpl implements statetest.ILogger
	{
		public void log(String message)
		{
			System.out.println("LOGGER: " + message);
		}
	}
}
