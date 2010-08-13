package com.intrinsarc.backbone.nodes.insides;

import com.intrinsarc.backbone.nodes.lazy.*;
import com.intrinsarc.backbone.parserbase.*;
import com.intrinsarc.deltaengine.base.*;

public class BBReplacedConnector extends BBReplacedConstituent
{
	public BBReplacedConnector() {}
	
	public BBReplacedConnector(UuidReference reference, DEConstituent replacement)
	{
		super(reference.getUuid(), replacement);
	}
}
