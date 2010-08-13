package com.intrinsarc.backbone.nodes.insides;

import com.intrinsarc.backbone.nodes.lazy.*;
import com.intrinsarc.backbone.parserbase.*;
import com.intrinsarc.deltaengine.base.*;

public class BBReplacedPart extends BBReplacedConstituent
{
	public BBReplacedPart() {}
	
	public BBReplacedPart(UuidReference reference, DEConstituent replacement)
	{
		super(reference.getUuid(), replacement);
	}
}
