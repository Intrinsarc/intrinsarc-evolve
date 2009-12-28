/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.wmf.records;

import com.hopstepjump.wmf.*;
import com.hopstepjump.wmf.utility.*;

/**
 * @version 	1.0
 * @author
 */
public class MoveToRecord extends WMFAbstractRecord
{
	private int x;
	private int y;
	
	public MoveToRecord()
	{
		super("0214", "MoveTo", false);
	}

	public MoveToRecord(int x, int y)
	{
		this();
		this.x = x;
		this.y = y;
	}

	/*
	 * @see WMFAbstractRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		return new WMFBytes[]{new WMFWord(WMFFormat.scale(y)), new WMFWord(WMFFormat.scale(x))};
	}

}
