package com.hopstepjump.backbone.nodes.insides;

import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("ReplacedAttribute")
public class BBReplacedAttribute extends BBReplacedConstituent
{
	public BBReplacedAttribute() {}
	
	public BBReplacedAttribute(String uuid, DEConstituent replacing)
	{
		super(uuid, replacing);
	}
}
