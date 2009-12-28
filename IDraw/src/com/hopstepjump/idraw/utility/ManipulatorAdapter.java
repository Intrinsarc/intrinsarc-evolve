/*
 * Created on Jan 11, 2004 by Andrew McVeigh
 */
package com.hopstepjump.idraw.utility;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;

/**
 * @author Andrew
 */
public abstract class ManipulatorAdapter implements ManipulatorFacet
{
	public void enterImmediately(ManipulatorListenerFacet listener)
	{
	}

	public void enterViaButtonPress(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	{
	}

	public void enterViaButtonRelease(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	{
	}

	public void enterViaKeyPress(ManipulatorListenerFacet listener, char keyPressed)
	{
	}

	public int getType()
	{
		return TYPE0;
	}

	public void keyPressed(char keyPressed)
	{
	}

	public void mouseDragged(FigureFacet over, UPoint point, ZMouseEvent event)
	{
	}

	public void mouseMoved(FigureFacet over, UPoint point, ZMouseEvent event)
	{
	}

	public void mousePressed(FigureFacet over, UPoint point, ZMouseEvent event)
	{
	}

	public void mouseReleased(FigureFacet over, UPoint point, ZMouseEvent event)
	{
	}

	public boolean wantToEnterViaKeyPress(char keyPressed)
	{
		return false;
	}

}
