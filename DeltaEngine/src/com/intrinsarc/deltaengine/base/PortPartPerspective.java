package com.intrinsarc.deltaengine.base;

public class PortPartPerspective
{
	private DEStratum perspective;
	private DEPort port;
	private DEPart part;
	
	public PortPartPerspective(DEStratum perspective, DEPort port)
	{
		this.perspective = perspective;
		this.port = port;
	}
	
	public PortPartPerspective(DEStratum perspective, DEPort port, DEPart part)
	{
		this.perspective = perspective;
		this.port = port;
		this.part = part;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof PortPartPerspective))
			return false;
		PortPartPerspective other = (PortPartPerspective) obj;
		return perspective == other.perspective && port == other.port && part == other.part;
	}

	@Override
	public int hashCode()
	{
		// perspective may be null (for when it doesn't matter) so don't use this
		if (port == null)
			return 0; // pathological, but can happen
		return port.hashCode() ^ (perspective != null ? perspective.hashCode() : 0) ^ (part != null ? part.hashCode() : 0);
	}

	@Override
	public String toString()
	{
		if (part == null)
			return "PortPartPerspective(" + perspective + ", " + port + ")";		
		return "PortPartPerspective(" + perspective + ", " + port + ", " + part + ")";
	}

	public DEStratum getPerspective()
	{
		return perspective;
	}

	public DEPort getPort()
	{
		return port;
	}

	public DEPart getPart()
	{
		return part;
	}
}


