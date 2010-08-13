package com.intrinsarc.backbone.nodes.simple.internal;

import com.intrinsarc.backbone.nodes.simple.*;

class CachedConnectorEnd
{
	private BBSimpleConnector conn;
	private int side;
	
	public CachedConnectorEnd(BBSimpleConnector conn, int side)
	{
		this.conn = conn;
		this.side = side;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof CachedConnectorEnd))
			return false;
		CachedConnectorEnd other = (CachedConnectorEnd) obj;
		return conn == other.conn && side == other.side;
	}

	@Override
	public int hashCode()
	{
		return conn.hashCode() ^ side;
	}
}