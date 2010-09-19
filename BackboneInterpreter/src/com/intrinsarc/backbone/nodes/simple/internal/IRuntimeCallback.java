package com.intrinsarc.backbone.nodes.simple.internal;

import com.intrinsarc.backbone.exceptions.*;

public interface IRuntimeCallback
{
	public void beforeConnections(BBSimpleInstantiatedFactory factory) throws BBRuntimeException;
}
