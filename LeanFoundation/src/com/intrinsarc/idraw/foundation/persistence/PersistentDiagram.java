package com.intrinsarc.idraw.foundation.persistence;

import java.io.*;
import java.util.*;

/**
 *
 * (c) Andrew McVeigh 24-Jan-03
 *
 */
public class PersistentDiagram implements Serializable
{
  private Object linkedObject;
	private Object diagramId;
	private String name;
	private int lastFigureId;
	private PersistentProperties properties = new PersistentProperties();
	private List<PersistentFigure> figures = new ArrayList<PersistentFigure>();
	
	public PersistentDiagram()
	{
	}
	
	public PersistentDiagram(Object linkedObject, Object diagramId, String name, int lastFigureId)
	{
    this.linkedObject = linkedObject;
		this.diagramId = diagramId;
		this.name = name;
		this.lastFigureId = lastFigureId;
	}
	
  
  public Object getLinkedObject()
  {
    return linkedObject;
  }
  
  public void setLinkedObject(Object linkedObject)
  {
    this.linkedObject = linkedObject;
  }
  
	/**
	 * Returns the diagramId.
	 * @return String
	 */
	public Object getDiagramId()
	{
		return diagramId;
	}

	/**
	 * Sets the diagramId.
	 * @param diagramId The diagramId to set
	 */
	public void setDiagramId(String diagramId)
	{
		this.diagramId = diagramId;
	}

	/**
	 * Returns the lastFigureId.
	 * @return int
	 */
	public int getLastFigureId()
	{
		return lastFigureId;
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the lastFigureId.
	 * @param lastFigureId The lastFigureId to set
	 */
	public void setLastFigureId(int lastFigureId)
	{
		this.lastFigureId = lastFigureId;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Returns the properties.
	 * @return PersistentProperties
	 */
	public PersistentProperties getProperties()
	{
		return properties;
	}

	public void addFigure(PersistentFigure figure)
	{
		figures.add(figure);
	}
	
	public void addFigures(Collection<PersistentFigure> newFigures)
	{
		for (PersistentFigure figure : newFigures)
			figures.add(figure);
	}

	public void addFigures(PersistentFigure[] newFigures)
	{
		for (int lp = 0; lp < newFigures.length; lp++)
			figures.add(newFigures[lp]);
	}
	
	public Collection<PersistentFigure> getFigures()
	{
		return figures;
	}
	
	/**
	 * Sets the properties.
	 * @param properties The properties to set
	 */
	public void setProperties(PersistentProperties properties)
	{
		this.properties = properties;
	}

}
