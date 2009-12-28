package com.hopstepjump.wmf.records;

import com.hopstepjump.wmf.utility.*;

/**
 *
 * (c) Andrew McVeigh 20-Jan-03
 *
 */
public class DeleteObjectRecord extends WMFAbstractRecord
{
	private WMFRecord objectToDelete;
	private int objectNumberToDelete;
	
	public DeleteObjectRecord()
	{
		super("01f0", "DeleteObject", false);
	}

	public DeleteObjectRecord(WMFRecord objectToDelete)
	{
		this();
		this.objectToDelete = objectToDelete;
	}

	public DeleteObjectRecord(int objectNumberToDelete)
	{
		this();
		this.objectNumberToDelete = objectNumberToDelete;
	}

	/*
	 * @see WMFRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		if (objectToDelete != null)
			return new WMFBytes[]{new WMFWord(objectToDelete.getObjectNumber())};
		else
			return new WMFBytes[]{new WMFWord(objectNumberToDelete)};
	}
}
