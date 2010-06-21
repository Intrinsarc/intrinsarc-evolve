package com.hopstepjump.backbone.nodes.insides;

import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class BBReplacedAttribute extends BBReplacedConstituent
{
	public BBReplacedAttribute() {}
	
	public BBReplacedAttribute(UUIDReference reference, DEConstituent replacement)
	{
		super(reference.getUUID(), replacement);
	}
}
