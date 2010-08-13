package com.intrinsarc.idraw.foundation.persistence;

import java.util.*;

/**
 *
 * (c) Andrew McVeigh 24-Jan-03
 *
 */
public class PersistentFigure
{
	private Object subject;
	private String id;
	private String recreator;
	private String anchor1Id;
	private String anchor2Id;
	private String containerId;
	private List<String> contentIds = new ArrayList<String>();
	private List<String> linkIds = new ArrayList<String>();
	private PersistentProperties properties;
	
	public PersistentFigure(String id, String recreator)
	{
		this.id = id;
		this.recreator = recreator;
		properties = new PersistentProperties();
	}
  
	public PersistentFigure(String id, String recreator, Object subject, PersistentProperties properties)
	{
		this.id = id;
		this.recreator = recreator;
		this.subject = subject;
		this.properties = properties;
	}
  
	public PersistentFigure(PersistentFigure figure)
	{
		// copy over all the parts, and clone the properties
		subject = figure.subject;
		id = figure.id;
		recreator = figure.recreator;
		anchor1Id = figure.anchor1Id;
		anchor2Id = figure.anchor2Id;
		containerId = figure.containerId;
		contentIds.addAll(figure.contentIds);
		linkIds.addAll(figure.linkIds);
		properties = new PersistentProperties(figure.properties);
	}

	public boolean equals(Object obj)
	{
  	if (!(obj instanceof PersistentFigure))
  		return false;
  	PersistentFigure p = (PersistentFigure) obj;
  	return
  		equals(id, p.id) &&
  		equals(recreator, p.recreator) &&
  		equals(anchor1Id, p.anchor1Id) &&
  		equals(anchor2Id, p.anchor2Id) &&
  		contentIds.equals(p.contentIds) &&
  		linkIds.equals(p.linkIds) &&
  		properties.equals(p.properties);
	}

	private boolean equals(String a, String b)
	{
		if (a == null || b == null)
			return a == b;
		return a.equals(b);
	}

	@Override
	public int hashCode()
	{
		return id.hashCode();
	}

	/**
	 * Returns the anchor1Id.
	 * @return String
	 */
	public String getAnchor1Id()
	{
		return anchor1Id;
	}

	/**
	 * Returns the anchor2Id.
	 * @return String
	 */
	public String getAnchor2Id()
	{
		return anchor2Id;
	}

	/**
	 * Returns the id.
	 * @return String
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Returns the recreator.
	 * @return String
	 */
	public String getRecreator()
	{
		return recreator;
	}

	/**
	 * Sets the anchor1Id.
	 * @param anchor1Id The anchor1Id to set
	 */
	public void setAnchor1Id(String anchor1Id)
	{
		this.anchor1Id = anchor1Id;
	}

	/**
	 * Sets the anchor2Id.
	 * @param anchor2Id The anchor2Id to set
	 */
	public void setAnchor2Id(String anchor2Id)
	{
		this.anchor2Id = anchor2Id;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * Sets the recreator.
	 * @param recreator The recreator to set
	 */
	public void setRecreator(String recreator)
	{
		this.recreator = recreator;
	}
	
	public PersistentProperties getProperties()
	{
		return properties;
	}

	public List<String> getContentIds()
	{
		return contentIds;
	}
	
	public List<String> getLinkIds()
	{
		return linkIds;
	}
	/**
	 * Returns the containerId.
	 * @return String
	 */
	public String getContainerId()
	{
		return containerId;
	}

	/**
	 * Sets the containerId.
	 * @param containerId The containerId to set
	 */
	public void setContainerId(String containerId)
	{
		this.containerId = containerId;
	}

  public Object getSubject()
  {
    return subject;
  }

  public void setSubject(Object subject)
  {
    this.subject = subject;
  }

	public void setProperties(PersistentProperties properties)
	{
		this.properties = properties;
	}
}
