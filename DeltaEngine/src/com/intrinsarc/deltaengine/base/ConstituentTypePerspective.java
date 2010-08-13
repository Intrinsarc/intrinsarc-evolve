package com.intrinsarc.deltaengine.base;

public class ConstituentTypePerspective
{
	private DEStratum perspective;
	private ConstituentTypeEnum type;
	
	public ConstituentTypePerspective(DEStratum perspective, ConstituentTypeEnum type)
	{
		this.perspective = perspective;
		this.type = type;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ConstituentTypePerspective))
			return false;
		ConstituentTypePerspective other = (ConstituentTypePerspective) obj;
		return perspective == other.perspective && type == other.type;
	}

	@Override
	public int hashCode()
	{
		return perspective.hashCode() ^ type.hashCode();
	}
}
