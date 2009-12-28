package com.hopstepjump.backbone.nodes.insides;

import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("ReplacedOperation")
public class BBReplacedOperation extends BBReplacedConstituent
{
	public BBReplacedOperation() {}
	
	public BBReplacedOperation(String uuid, DEConstituent replacing)
	{
		super(uuid, replacing);
	}
}

