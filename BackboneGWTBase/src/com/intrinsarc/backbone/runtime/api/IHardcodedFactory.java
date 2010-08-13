package com.intrinsarc.backbone.runtime.api;

import com.intrinsarc.backbone.runtime.api.*;

public interface IHardcodedFactory
{
	public void destroy();
	public void childDestroyed(IHardcodedFactory child);
}
