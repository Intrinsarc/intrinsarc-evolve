package com.hopstepjump.backbone.nodes.insides;

import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("ReplacedConnector")
public class BBReplacedConnector extends BBReplacedConstituent
{
	public BBReplacedConnector() {}
	
	public BBReplacedConnector(String uuid, DEConstituent replacing)
	{
		super(uuid, replacing);
	}
}
