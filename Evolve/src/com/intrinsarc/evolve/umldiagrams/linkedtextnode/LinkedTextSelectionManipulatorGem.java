package com.intrinsarc.evolve.umldiagrams.linkedtextnode;

import java.awt.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;


public class LinkedTextSelectionManipulatorGem implements Gem
{
	private ManipulatorFacet manipulatorFacet = new ManipulatorFacetImpl();
  private UBounds figureBounds;
  private ZGroup diagramLayer;
  private ZGroup group;
  private UDimension offset;

	private class ManipulatorFacetImpl implements ManipulatorFacet
	{
	  /**
	   * manipulator interface methods
	   *
	   */
	  /** the resizing manipulator for nodes is not direct.  The item must be selected first */
	  public int getType()
	  {
	    return TYPE2;
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
	    // not applicable for this manipulator.  Only necessary for complex text entry
	  }
	
	  /** invoked when a key has been pressed */
	  public void keyPressed(char keyPressed)
	  {
	    // ignore, could put kb shortcuts later
	  }
	
	  /**Invoked when a mouse button has been pressed on this figure */
	  public void mousePressed(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	    // ignore, as this is only used for entry other than via button press, and the state cycle for this manipulator ends with
	    // release anyway
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
	
	  public void addToView(ZGroup newDiagramLayer, ZCanvas newCanvas)
	  {
	    diagramLayer = newDiagramLayer;
	    UBounds bounds = figureBounds.addToPoint(offset.negative()).addToExtent(offset.multiply(2));
	    group = new ZGroup();
	    ZRoundedRectangle rect = new ZRoundedRectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), 6, 4);
	    rect.setPenPaint(Color.BLACK);
	    rect.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
	    rect.setFillPaint(null);
	    group.addChild(new ZVisualLeaf(rect));
	    diagramLayer.addChild(group);
	  }

    public void cleanUp()
	  {
	  	if (group != null)
		    diagramLayer.removeChild(group);
			group = null;
	  }

    public void setLayoutOnly()
    {
    }
	}

  public LinkedTextSelectionManipulatorGem(UBounds figureBounds, UDimension offset)
  {
    this.figureBounds = figureBounds;
    this.offset = offset;
  }

  
	public ManipulatorFacet getManipulatorFacet()
	{
		return manipulatorFacet;
	}
}
