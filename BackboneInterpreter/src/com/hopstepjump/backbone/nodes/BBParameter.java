package com.hopstepjump.backbone.nodes;

import com.hopstepjump.backbone.nodes.lazy.*;
import com.hopstepjump.deltaengine.base.*;

public class BBParameter extends DEParameter
{
	private String literal;
	private LazyObject<DEAttribute> attribute;

	public BBParameter(String literal)
	{
		this.literal = literal;
	}

	public BBParameter(UuidReference reference)
	{
		this.attribute = new LazyObject<DEAttribute>(DEAttribute.class, reference);
	}

	@Override
	public DEAttribute getAttribute()
	{
		if (attribute == null)
			return null;
		return attribute.getObject();
	}

	@Override
	public String getLiteral()
	{
		return literal;
	}

	@Override
	public void resolveLazyReferences()
	{
		if (attribute != null)
			attribute.resolve();
	}
}
