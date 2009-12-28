package com.hopstepjump.backbone.nodes.simple.internal;

import com.hopstepjump.backbone.nodes.simple.*;

public class BBSimpleConnectorEnd implements Comparable<BBSimpleConnectorEnd>
{
	private BBSimpleConnector connector;
	private int side;
	private BBSimplePart part;
	private BBSimplePort port;
	private Integer index;
	private String originalIndex;
	private boolean takeNext;
	
	public BBSimpleConnectorEnd(BBSimpleConnector connector, int side, BBSimplePart part, BBSimplePort port, Integer index, String originalIndex, boolean takeNext)
	{
		this.connector = connector;
		this.side = side;
		this.part = part;
		this.port = port;
		this.index = index;
		this.originalIndex = originalIndex;
		this.takeNext = takeNext;
	}

	public BBSimplePart getPart()
	{
		return part;
	}
	
	public BBSimplePort getPort()
	{
		return port;
	}

	public Integer getIndex()
	{
		return index;
	}

	public boolean isTakeNext()
	{
		return takeNext;
	}

	public String getOriginalIndex()
	{
		return originalIndex;
	}

	public BBSimpleConnector getConnector()
	{
		return connector;
	}

	public int getSide()
	{
		return side;
	}

	public int compareTo(BBSimpleConnectorEnd o)
	{
		if (index != null)
		{
			if (o.index == null)
				return -1;
			else
				return index.compareTo(o.index);
		}
		
		String a = originalIndex;
		String b = o.originalIndex;
		if (b == null)
			return -1;
		if (a == null)
			return 1;
		return a.compareTo(b);
	}
}
