package com.hopstepjump.backbone.nodes;

import java.io.*;

import com.hopstepjump.backbone.nodes.lazy.*;
import com.hopstepjump.deltaengine.base.*;

public class BBPortRemap implements Serializable
{
	private String uuid;
	private LazyObject<DEPort> port = new LazyObject<DEPort>(DEPort.class);
	
	public BBPortRemap(String uuid, DEPort port)
	{
		this.uuid = uuid;
		this.port.setObject(port);
	}

	public BBPortRemap(String uuid, UuidReference reference)
	{
		this.uuid = uuid;
		port.setReference(reference);
	}

	public String getUuid()
	{
		return uuid;
	}
	
	public DEPort getPort()
	{
		return port.getObject();
	}

	public void resolveLazyReferences()
	{
		port.resolve();
	}
}
