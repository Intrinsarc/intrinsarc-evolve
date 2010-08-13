package test;

import com.intrinsarc.backbone.runtime.api.*;


public class Logger
{
// start generated code
	private Attribute<String> prefix;
	public Attribute<String> getPrefix() { return prefix; }
	public void setPrefix(Attribute<String> prefix) { this.prefix = prefix;}
	private test.ILogger logger_ILoggerProvided = new ILoggerLoggerImpl();
	public test.ILogger getLogger_ILogger(Class<?> required) { return logger_ILoggerProvided; }
// end generated code

	private class ILoggerLoggerImpl implements test.ILogger
	{
		public void log(String message)
		{
			System.out.println("$$ " + prefix + ": " + message);
		}
	}
}
