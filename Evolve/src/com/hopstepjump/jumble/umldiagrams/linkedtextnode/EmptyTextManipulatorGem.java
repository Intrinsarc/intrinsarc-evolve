package com.hopstepjump.jumble.umldiagrams.linkedtextnode;

import java.awt.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

public class EmptyTextManipulatorGem implements Gem
{
	private static final Color PEN_COLOR = Color.GRAY;
	private ManipulatorFacet manipulatorFacet = new ManipulatorFacetImpl();
	private ManipulatorFacet decoratedManipulatorFacet;
	private FigureFacet originFigure;
  private ZGroup diagramLayer;
  private ZGroup group;
  private UPoint majorPoint;
  private String fieldName;


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
	  	cleanUp();
	    decoratedManipulatorFacet.enterViaKeyPress(listener, keyPressed);
	  }
	
	  /** invoked only on type 0 manipulators */
	  public void enterImmediately(ManipulatorListenerFacet listener)
	  {
	  	cleanUp();
	    decoratedManipulatorFacet.enterImmediately(listener);
	  }
	  
	  /**invoked on button press entry*/
	  public void enterViaButtonPress(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	  {
	  	cleanUp();
	    decoratedManipulatorFacet.enterViaButtonPress(listener, source, point, modifiers);
	  }
	
	  /**invoked on button release entry*/
	  public void enterViaButtonRelease(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	  {
	  	cleanUp();
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

    	group = new ZGroup();
    	ZText text = new ZText(fieldName);
    	text.setTranslation(majorPoint.getX(), majorPoint.getY());
    	text.setPenColor(PEN_COLOR);
    	group.addChild(new ZVisualLeaf(text));
      // add a transparent box over this so we can pick up correct mouse events
      ZRectangle rect = new ZRectangle(text.getBounds());
      rect.setFillPaint(ScreenProperties.getTransparentColor());
      rect.setPenPaint(null);
      ZVisualLeaf transparentCover = new ZVisualLeaf(rect);
      group.addChild(transparentCover);
      diagramLayer.addChild(group);
      group.setChildrenPickable(false);
      group.setChildrenFindable(false);
      group.putClientProperty("manipulator", this);
      
      decoratedManipulatorFacet.addToView(newDiagramLayer, newCanvas);
	  }

    public void cleanUp()
	  {
      decoratedManipulatorFacet.cleanUp();
      if (group != null)
        diagramLayer.removeChild(group);
      group = null;
      diagramLayer = null;
	  }

    public void setLayoutOnly()
    {
    }
	}
  
	public EmptyTextManipulatorGem(DiagramViewFacet diagramView, FigureFacet originFigure, UPoint majorPoint, String fieldName)
	{
		this.majorPoint = majorPoint;
		this.fieldName = fieldName;
		this.originFigure = originFigure;
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
