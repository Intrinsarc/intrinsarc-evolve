package com.hopstepjump.backbone.nodes.insides;

import java.io.*;
import java.util.*;

import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

public class BBReplacedConstituent implements Serializable
{
	@XStreamAlias("original")
	private String uuid;
	@XStreamImplicit
	private List<DEConstituent> with = new ArrayList<DEConstituent>();

	public BBReplacedConstituent() {}
	
	public BBReplacedConstituent(String uuid, DEConstituent replacing)
	{
		this.uuid = uuid;
		with.add(replacing);
	}

	public DEConstituent getReplacement()
	{
		return with.get(0);
	}

	public String getUuid()
	{
		return uuid;
	}
}