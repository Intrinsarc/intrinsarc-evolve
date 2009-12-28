package com.hopstepjump.wmf.records;

import com.hopstepjump.wmf.utility.*;

/**
 *
 * (c) Andrew McVeigh 14-Jan-03
 *
 */
public class EndMetafileRecord extends WMFAbstractRecord
{
	public EndMetafileRecord()
	{
		super("0000", "EndMetafile", false);
	}

	/**
	 * @see com.hopstepjump.wmf.WMFAbstractRecord#getParameters()
	 */
	public WMFBytes[] getParameters()
	{
		return new WMFBytes[0];
	}

}
