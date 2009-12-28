package com.hopstepjump.deltaengine.base;

public class DELinkEnd
{
	private DEPort port;
	private DEPart part;
	
	public DELinkEnd(DEPort port)
	{
		this.port = port;
	}
	
	public DELinkEnd(DEPort port, DEPart part)
	{
		this.port = port;
		this.part = part;
	}
	
	public DEPort getPort()
	{
		return port;
	}
	
	public DEPart getPart()
	{
		return part;
	}
		
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof DELinkEnd))
			return false;
		DELinkEnd other = (DELinkEnd) obj;
		return port == other.port && part == other.part;
	}

	@Override
	public int hashCode()
	{
		if (part == null)
			return port.hashCode();
		if (port == null)
			return 0;
		return port.hashCode() ^ part.hashCode();
	}

	@Override
	public String toString()
	{
		if (part == null)
			return "DEComponentLinkEnd(" + port + ")";
		return "DEPartLinkEnd(" + port + ", " + part + ")";
	}

	/** a component link end has no part -- it links to a port */
	public boolean isComponentLinkEnd()
	{
		return part == null;
	}
}
