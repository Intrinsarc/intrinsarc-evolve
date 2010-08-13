package com.intrinsarc.deltaengine.base;


public class DELink
{
	private DELinkEnd a;
	private DELinkEnd b;
	
	public DELink(DELinkEnd a, DELinkEnd b)
	{
		this.a = a;
		this.b = b;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof DELink))
			return false;
		DELink other = (DELink) obj;
		return a.equals(other.a) && b.equals(other.b) || a.equals(other.b) && b.equals(other.a);
	}

	@Override
	public int hashCode()
	{
		return a.hashCode() ^ b.hashCode();
	}
}
