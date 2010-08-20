package com.intrinsarc.backbone.runtime.api;

import java.util.*;

public interface ICreate
{
	public Object create(Map<String, Object> suppliedParameters);
	public void destroy(Object memento);
}
