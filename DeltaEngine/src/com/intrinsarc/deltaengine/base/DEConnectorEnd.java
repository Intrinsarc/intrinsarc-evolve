package com.intrinsarc.deltaengine.base;


public class DEConnectorEnd implements Comparable<DEConnectorEnd>
{
	private DEConnector connector;
	private int side;
	private DEPart part;
	private DEPort port;
	private Integer index;
	private String originalIndex;
	private boolean takeNext;
	
	public DEConnectorEnd(DEConnector connector, int side, DEPart part, DEPort port, Integer index, String originalIndex, boolean takeNext)
	{
		this.connector = connector;
		this.side = side;
		this.part = part;
		this.port = port;
		this.index = index;
		this.originalIndex = originalIndex;
		this.takeNext = takeNext;
	}

	public DEPart getPart()
	{
		return part;
	}
	
	public DEPort getPort()
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

	public DEConnector getConnector()
	{
		return connector;
	}

	public int getSide()
	{
		return side;
	}

	public int compareTo(DEConnectorEnd o)
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

	public boolean equals(Object obj)
	{
		if (!(obj instanceof DEConnectorEnd))
			return false;
		DEConnectorEnd o = (DEConnectorEnd) obj;
		return
			connector == o.connector &&
			side == o.side &&
			part == o.part &&
			port == o.port &&
			equals(index, o.index) &&
			equals(originalIndex, o.originalIndex) &&
			takeNext == o.takeNext;  
	}

	private boolean equals(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
			return true;
		if (o1 == null || o2 == null)
			return false;
		return o1.equals(o2);
	}

	public int hashCode()
	{
		return port.hashCode();
	}
	
	
}
