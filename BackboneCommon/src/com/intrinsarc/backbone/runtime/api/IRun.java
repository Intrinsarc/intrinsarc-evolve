package com.intrinsarc.backbone.runtime.api;

public interface IRun
{
	/** the main method -- if this returns true, the components are destroyed().
	 *  returning false allows swing programs etc to continue on after run() has exited, without the
	 *  configuration being destroyed. */
	public boolean run(String args[]);
}
