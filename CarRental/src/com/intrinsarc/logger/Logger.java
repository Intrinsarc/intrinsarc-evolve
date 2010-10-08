package com.intrinsarc.logger;

import com.hopstepjump.backbone.runtime.api.*;

public class Logger
{
// start generated code
// attributes
// required ports
// provided ports
	private ILoggerLoggerImpl logger_ILoggerProvided = new ILoggerLoggerImpl();
// setters and getters
	public com.intrinsarc.logger.ILogger getLogger_ILogger(Class<?> required) { return logger_ILoggerProvided; }
// end generated code


	private class ILoggerLoggerImpl implements com.intrinsarc.logger.ILogger
	{
		public void log(String msg)
		{
			System.out.println("Logger: " + msg);
		}
	}
}
