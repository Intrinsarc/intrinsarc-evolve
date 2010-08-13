package com.intrinsarc.backbone.nodes.insides;

import com.intrinsarc.backbone.nodes.lazy.*;
import com.intrinsarc.backbone.parserbase.*;
import com.intrinsarc.deltaengine.base.*;

public class BBReplacedAttribute extends BBReplacedConstituent
{
	public BBReplacedAttribute() {}
	
	public BBReplacedAttribute(UuidReference reference, DEConstituent replacement)
	{
		super(reference.getUuid(), replacement);
	}
}
