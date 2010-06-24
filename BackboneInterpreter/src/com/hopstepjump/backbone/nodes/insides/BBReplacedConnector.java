package com.hopstepjump.backbone.nodes.insides;

import com.hopstepjump.backbone.nodes.lazy.*;
import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class BBReplacedConnector extends BBReplacedConstituent
{
	public BBReplacedConnector() {}
	
	public BBReplacedConnector(UuidReference reference, DEConstituent replacement)
	{
		super(reference.getUuid(), replacement);
	}
}
