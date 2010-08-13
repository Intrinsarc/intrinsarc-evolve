package com.intrinsarc.backbone;

import com.intrinsarc.backbone.runtime.api.*;

public class Runner
{
	private IRun port_IRunProvided;
	
	public void run(String args[])
	{
		port_IRunProvided.run(args);
	}
}
