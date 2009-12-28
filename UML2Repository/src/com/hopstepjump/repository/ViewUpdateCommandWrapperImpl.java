package com.hopstepjump.repository;

import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 06-Nov-02
 *
 */
public class ViewUpdateCommandWrapperImpl implements CommandWrapperFacet
{
	/**
	 * @see com.hopstepjump.idraw.diagramsupport.CommandWrapperFacet#wrapCommand(Command)
	 */
	public Command wrapCommand(Command command)
	{
		return new ViewUpdateWrapperCommand(command);
	}
}
