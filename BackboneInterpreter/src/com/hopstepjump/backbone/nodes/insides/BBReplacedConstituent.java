package com.hopstepjump.backbone.nodes.insides;

import java.io.*;

import com.hopstepjump.deltaengine.base.*;

public class BBReplacedConstituent implements Serializable
{
	private String uuid;
	private DEConstituent replacement;

	public BBReplacedConstituent() {}
	
	public BBReplacedConstituent(String uuid, DEConstituent replacement)
	{
		this.uuid = uuid;
		this.replacement = replacement;
	}

	public DEConstituent getReplacement()
	{
		return replacement;
	}

	public String getUuid()
	{
		return uuid;
	}
}