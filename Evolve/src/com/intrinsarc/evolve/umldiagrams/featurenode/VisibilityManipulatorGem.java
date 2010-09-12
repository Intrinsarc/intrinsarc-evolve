/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.intrinsarc.evolve.umldiagrams.featurenode;

import org.eclipse.uml2.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

/**
 * @version 	1.0
 * @author
 */
public final class VisibilityManipulatorGem implements Gem
{
	private UPoint point;
	private double height;
	private VisibilityKind accessType;
	private int featureType;
	private ManipulatorFacetImpl manipulatorFacet = new ManipulatorFacetImpl();
	private boolean firstSelected;

	public VisibilityManipulatorGem(
	    VisibilityKind accessType,
	    UPoint point,
	    double height,
	    int featureType,
	    boolean firstSelected)
	{
		this.point = point;
		this.height = height;
		this.accessType = accessType;
		this.featureType = featureType;
		this.firstSelected = firstSelected;
	}

	public ManipulatorFacet getManipulatorFacet()
	{
		return manipulatorFacet;
	}

	private class ManipulatorFacetImpl implements ManipulatorFacet
	{
		private ZGroup diagramLayer;
		private ZNode hilight;
		
		
		/** the resizing manipulator for nodes is not direct.  The item must be selected first */
	  public int getType()
	  {
	    return TYPE2;
	  }
	
	  public void addToView(ZGroup diagramLayer, ZCanvas canvas)
	  {
	  	this.diagramLayer = diagramLayer;
	  	hilight = FeatureNodeGem.makeIcon(
	  	    accessType,
	  	    true,
	  	    point,
	  	    height,
	  	    firstSelected ? ScreenProperties.getFirstSelectedHighlightColor() : ScreenProperties.getHighlightColor(),
	  	    featureType);
	  	diagramLayer.addChild(hilight);
	  }
	  
	  public void cleanUp()
	  {
	  	diagramLayer.removeChild(hilight);
	  }
	
		/** invoked to see if we want to enter on this key
		 * @param keyPressed the key that was pressed
		 */
		public boolean wantToEnterViaKeyPress(char keyPressed)
		{
			return false;
		}

	  /**invoked on key entry*/
	  public void enterViaKeyPress(ManipulatorListenerFacet listener, char keyPressed)
	  {
	    // not applicable
	  }
	
	  /** invoked only on type 0 manipulators */
	  public void enterImmediately(ManipulatorListenerFacet listener)
	  {
	    // not applicable
	  }
	  
	  /**invoked on button press entry*/
	  public void enterViaButtonPress(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	  {
	  }
	
	  /**invoked on button release entry*/
	  public void enterViaButtonRelease(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	  {
	  }
	
	  /** invoked when a key has been pressed */
	  public void keyPressed(char keyPressed)
	  {
	  }
	
	  /**Invoked when a mouse button has been pressed on this figure */
	  public void mousePressed(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  }
	
	  /** invoked when a mouse button has been dragged on this figure */
	  public void mouseDragged(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  }
	
	  public void mouseMoved(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  }
	
	  /**Invoked when a mouse button has been released on this figure */
	  public void mouseReleased(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  }

    public void setLayoutOnly()
    {
    }

		public boolean handlesReadonly()
		{
			return false;
		}
	}
}
