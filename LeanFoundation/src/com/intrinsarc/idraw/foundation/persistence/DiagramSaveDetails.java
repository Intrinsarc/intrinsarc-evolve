package com.intrinsarc.idraw.foundation.persistence;


public class DiagramSaveDetails
{
	private String savedBy;
	private String saveTime;
	
	public DiagramSaveDetails(String savedBy, String saveTime)
	{
		this.savedBy = savedBy;
		this.saveTime = saveTime;
	}

	@Override
	public int hashCode()
	{
		return savedBy.hashCode() ^ saveTime.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof DiagramSaveDetails))
			return false;
		DiagramSaveDetails other = (DiagramSaveDetails) obj;
		return savedBy.equals(other.savedBy) && saveTime.equals(other.saveTime);
	}

	public boolean isEmpty()
	{
		return savedBy.isEmpty() && saveTime.isEmpty();
	}
	
	public String getSavedBy()
	{
		return savedBy;
	}

	public void setSavedBy(String savedBy)
	{
		this.savedBy = savedBy;
	}

	public String getSaveTime()
	{
		return saveTime;
	}

	public void setSaveTime(String saveTime)
	{
		this.saveTime = saveTime;
	}
}
