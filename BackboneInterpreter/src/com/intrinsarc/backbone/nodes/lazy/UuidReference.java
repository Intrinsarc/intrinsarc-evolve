package com.intrinsarc.backbone.nodes.lazy;

public class UuidReference
{
	private String uuid;
	private String name;
	private String file;
	private int line;
	private int pos;
	
	public UuidReference()
	{
	}
	
	public UuidReference(String uuid)
	{
		this.uuid = uuid;
		this.name = uuid;
	}
	
	public void setUUID(String uuid)
	{
		this.uuid = uuid;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setFile(String file)
	{
		this.file = file;
	}

	public void setLine(int line)
	{
		this.line = line;
	}

	public void setPos(int pos)
	{
		this.pos = pos;
	}

	public String getUuid()
	{
		return uuid;
	}

	public String getName()
	{
		return name;
	}

	public String getFile()
	{
		return file;
	}

	public int getLine()
	{
		return line;
	}

	public int getPos()
	{
		return pos;
	}
	
	public String toString()
	{
		return "uuidreference(uuid=" + uuid + ", name = " + name + ", file = " + file + ", line = " + line + ", position = " + pos;
	}
}
