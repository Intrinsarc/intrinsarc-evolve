package com.intrinsarc.repositorybase;

import java.io.*;
import java.util.*;

import com.intrinsarc.idraw.foundation.persistence.*;

/**
 *
 * (c) Andrew McVeigh 24-Sep-02
 *
 */
public class SaveInformation implements Serializable
{
	private int diagramsToSave;
	private boolean repositoryToSave;
	private List<DiagramSaveDetails> diagramsInConflict;
	
	public SaveInformation(boolean repositoryToSave, int diagramsToSave, List<DiagramSaveDetails> diagramsInConflict)
	{
		this.repositoryToSave = repositoryToSave;
		this.diagramsToSave = diagramsToSave;
		this.diagramsInConflict = diagramsInConflict;
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
	
	public List<DiagramSaveDetails> getDiagramsInConflict()
	{
		return diagramsInConflict;
	}
}
