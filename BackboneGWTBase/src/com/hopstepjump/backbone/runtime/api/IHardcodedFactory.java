package com.hopstepjump.backbone.runtime.api;

public interface IHardcodedFactory
{
	public void destroy();
	public void childDestroyed(IHardcodedFactory child);
}
