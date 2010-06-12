package com.hopstepjump.backbone.nodes;

import java.io.*;

import com.hopstepjump.deltaengine.base.*;

public class BBPortRemap implements Serializable
{
	private String uuid;
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
