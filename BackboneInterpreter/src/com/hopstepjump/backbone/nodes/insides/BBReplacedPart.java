package com.hopstepjump.backbone.nodes.insides;

import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("ReplacedPart")
public class BBReplacedPart extends BBReplacedConstituent
{
	public BBReplacedPart() {}
	
	public BBReplacedPart(String uuid, DEConstituent replacing)
	{
		super(uuid, replacing);
	}
}
