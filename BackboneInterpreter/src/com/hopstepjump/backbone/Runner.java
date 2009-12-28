package com.hopstepjump.backbone;

import com.hopstepjump.backbone.runtime.api.*;

public class Runner
{
	private IRun port_IRunProvided;
	
	public void run(String args[])
	{
		port_IRunProvided.run(args);
	}
}
