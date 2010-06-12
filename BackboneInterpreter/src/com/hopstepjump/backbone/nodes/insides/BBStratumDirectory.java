package com.hopstepjump.backbone.nodes.insides;


public class BBStratumDirectory
{
	private String path;
	private String stratumName;
	
	public BBStratumDirectory() {}
	
	public BBStratumDirectory(String path, String stratumName)
	{
		this.path = path;
		this.stratumName = stratumName;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public void setPath(String path)
	{
		this.path = path;
	}

	public String getStratumName()
	{
		return stratumName;
	}

	public void setStratumName(String stratumName)
	{
		this.stratumName = stratumName;
	}
}
