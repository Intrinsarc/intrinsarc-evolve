package com.hopstepjump.backbone.runtime.api;

import java.util.*;

public interface ICreate
{
	public Object create(Map<String, Object> values);
	public void destroy(Object memento);
}
