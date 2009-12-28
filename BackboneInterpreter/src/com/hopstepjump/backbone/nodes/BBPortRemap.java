package com.hopstepjump.backbone.nodes;

import java.io.*;

import com.hopstepjump.backbone.nodes.converters.BBXStreamConverters.*;
import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("remap")
public class BBPortRemap implements Serializable
{
	private String uuid;
	@XStreamConverter(PortReferenceConverter.class)
	private DEPort port[] = new DEPort[1];
	
	public BBPortRemap(String uuid, DEPort port)
	{
		this.uuid = uuid;
		this.port[0] = port;
	}

	public String getUuid()
	{
		return uuid;
	}
	
	public DEPort getPort()
	{
		return port[0];
	}
}
