package com.intrinsarc.deltaengine.base;

public class ErrorDescription
{
  private String description;
	private boolean diagramOnly;
  
  public ErrorDescription(String description)
  {
    this.description = description;
  }
  
  public ErrorDescription(String description, boolean diagramOnly)
  {
    this.description = description;
    this.diagramOnly = diagramOnly;
  }
  
  public String toString()
  {
    return description;
  }

	public boolean isDiagramOnly()
	{
		return diagramOnly;
	}
}
