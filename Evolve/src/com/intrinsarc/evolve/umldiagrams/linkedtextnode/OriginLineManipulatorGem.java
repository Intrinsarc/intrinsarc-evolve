package com.intrinsarc.evolve.umldiagrams.linkedtextnode;

import java.awt.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;


public class OriginLineManipulatorGem implements Gem
{
	private ManipulatorFacet manipulatorFacet = new ManipulatorFacetImpl();
	private ManipulatorFacet decoratedManipulatorFacet;
  private FigureFacet originFigure;
  private ZGroup diagramLayer;
  private ZVisualLeaf lineLeaf;
  private UPoint majorPoint;


	private class ManipulatorFacetImpl implements ManipulatorFacet
	{
	  /**
	   * manipulator interface methods
	   *
	   */
	  /** the resizing manipulator for nodes is not direct.  The item must be selected first */
	  public int getType()
	  {
	    return decoratedManipulatorFacet.getType();
	  }
	
		/** invoked to see if we want to enter on this key
		 * @param keyPressed the key that was pressed
		 */
		public boolean wantToEnterViaKeyPress(char keyPressed)
		{
			return decoratedManipulatorFacet.wantToEnterViaKeyPress(keyPressed);
		}
		
	  /**invoked on key entry*/
	  public void enterViaKeyPress(ManipulatorListenerFacet listener, char keyPressed)
	  {
	    decoratedManipulatorFacet.enterViaKeyPress(listener, keyPressed);
	  }
	
	  /** invoked only on type 0 manipulators */
	  public void enterImmediately(ManipulatorListenerFacet listener)
	  {
	    decoratedManipulatorFacet.enterImmediately(listener);
	  }
	  
	  /**invoked on button press entry*/
	  public void enterViaButtonPress(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	  {
	    decoratedManipulatorFacet.enterViaButtonPress(listener, source, point, modifiers);
	  }
	
	  /**invoked on button release entry*/
	  public void enterViaButtonRelease(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	  {
	    decoratedManipulatorFacet.enterViaButtonRelease(listener, source, point, modifiers);
	  }
	
	  /** invoked when a key has been pressed */
	  public void keyPressed(char keyPressed)
	  {
	    decoratedManipulatorFacet.keyPressed(keyPressed);
	  }
	
	  /**Invoked when a mouse button has been pressed on this figure */
	  public void mousePressed(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	    decoratedManipulatorFacet.mousePressed(over, point, null);
	  }
	
	  /** invoked when a mouse button has been dragged on this figure */
	  public void mouseDragged(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	    decoratedManipulatorFacet.mouseDragged(over, point, null);
	  }
	
	  public void mouseMoved(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	    decoratedManipulatorFacet.mouseMoved(over, point, null);
	  }
	
	  /**Invoked when a mouse button has been released on this figure */
	  public void mouseReleased(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  	decoratedManipulatorFacet.mouseReleased(over, point, null);
	  }
	
	  public void addToView(ZGroup newDiagramLayer, ZCanvas newCanvas)
	  {
      diagramLayer = newDiagramLayer;

      ZLine line = new ZLine(
          originFigure.getFullBounds().getMiddlePoint(),
          majorPoint);
	    line.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,10}, 0));
      lineLeaf = new ZVisualLeaf(line);
      diagramLayer.addChild(lineLeaf);

      decoratedManipulatorFacet.addToView(newDiagramLayer, newCanvas);
	  }

    public void cleanUp()
	  {
      decoratedManipulatorFacet.cleanUp();
      if (lineLeaf != null)
        diagramLayer.removeChild(lineLeaf);
      lineLeaf = null;
      diagramLayer = null;
	  }

    public void setLayoutOnly()
    {
    }
	}

  public OriginLineManipulatorGem(FigureFacet originFigure, UPoint majorPoint)
  {
    this.originFigure = originFigure;
    this.majorPoint = majorPoint;
  }

  
	public ManipulatorFacet getManipulatorFacet()
	{
		return manipulatorFacet;
	}
	
	public void setDecoratedManipulatorFacet(ManipulatorFacet decoratedManipulatorFacet)
	{
	  this.decoratedManipulatorFacet = decoratedManipulatorFacet;
	}
}
