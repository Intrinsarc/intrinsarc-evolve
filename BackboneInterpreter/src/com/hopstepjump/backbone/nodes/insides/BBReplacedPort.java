package com.hopstepjump.backbone.nodes.insides;

import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("ReplacedPort")
public class BBReplacedPort extends BBReplacedConstituent
{
	public BBReplacedPort() {}
	
	public BBReplacedPort(String uuid, DEConstituent replacing)
	{
		super(uuid, replacing);
	}
}
