package com.hopstepjump.backbone.nodes.converters;

import com.hopstepjump.backbone.exceptions.*;

public interface LazyResolver
{
	void resolveLazyReferences() throws BBNodeNotFoundException;
}
