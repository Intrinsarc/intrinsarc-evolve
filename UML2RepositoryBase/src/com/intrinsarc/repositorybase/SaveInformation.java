package com.intrinsarc.repositorybase;

import java.io.*;

/**
 *
 * (c) Andrew McVeigh 24-Sep-02
 *
 */
public class SaveInformation implements Serializable
{
	private int diagramsToSave;
	private boolean repositoryToSave;
	
	public SaveInformation(boolean repositoryToSave, int diagramsToSave)
	{
		this.repositoryToSave = repositoryToSave;
		this.diagramsToSave = diagramsToSave;
	}

	public void setDiagramsToSave(int diagramsToSave)
	{
		this.diagramsToSave = diagramsToSave;
	}

	public int getDiagramsToSave()
	{
		return diagramsToSave;
	}

	public void setRepositoryToSave(boolean repositoryToSave)
	{
		this.repositoryToSave = repositoryToSave;
	}

	public boolean getRepositoryToSave()
	{
		return repositoryToSave;
	}
	
	public boolean projectNeedsSaving()
	{
		return repositoryToSave || diagramsToSave > 0;
	}
}
