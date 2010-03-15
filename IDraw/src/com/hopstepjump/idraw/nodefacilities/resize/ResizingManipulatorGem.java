package com.hopstepjump.idraw.nodefacilities.resize;

import java.awt.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;
import com.hopstepjump.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;


public final class ResizingManipulatorGem implements Gem
{
	private static final Color HANDLE_PEN_COLOUR = Color.black;

  private static final int STEADY_STATE                = 1;
  private static final int DRAGGING_SUBSTATE           = 2;

  private int state = STEADY_STATE;
  private UPoint startPoint;
  private boolean resizeFromCentre;
  private UBounds resizeBounds;
  private int corner;
  private DiagramViewFacet diagramView;
  /** borderOn is set once a drag has occurred.  If set, a box will be drawn around the resize bounds. */
  private boolean borderOn;
  private ResizingFiguresFacet resizer;

  public static final int NO_CORNER           = 0;
  public static final int TOP_LEFT_CORNER     = 1;
  public static final int BOTTOM_RIGHT_CORNER = 2;
  public static final int TOP_RIGHT_CORNER    = 3;
  public static final int BOTTOM_LEFT_CORNER  = 4;

	private ToolCoordinatorFacet coordinator;
  private FigureFacet toResize;
  private UBounds originalBounds;
  private ZGroup group;
  private ResizeVetterFacet vetter;
  private ManipulatorFacetImpl manipulatorFacet = new ManipulatorFacetImpl();
  private ManipulatorListenerFacet listener;
  private ZGroup diagramLayer;
	private ZCanvas canvas;
	private boolean firstSelected;


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
	    if (state == STEADY_STATE)
	    {
	      ResizingManipulatorGem.this.listener = listener;
	      exit_STEADY_STATE(source, point, modifiers);
	    }
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
	    if (state == DRAGGING_SUBSTATE)
	      reenter_DRAGGING_SUBSTATE(point);
	  }
	
	  public void mouseMoved(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  }
	
	  /**Invoked when a mouse button has been released on this figure */
	  public void mouseReleased(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	    if (state == DRAGGING_SUBSTATE)
	      enter_RESIZED_STATE(diagramView.getDiagram(), point);
	  }
	
	  public void addToView(ZGroup newDiagramLayer, ZCanvas newCanvas)
	  {
	    // make up handles for the selection of this class
	    diagramLayer = newDiagramLayer;
	    canvas = newCanvas;
	    group = new ZGroup();
	    ZNode node;

	    if (state != DRAGGING_SUBSTATE)
	    {
	      node = createHandle(diagramView, resizeBounds.getTopLeftPoint(), firstSelected);
	      setNodeProperties(node, "top-left");
	      group.addChild(node);
	
	      node = createHandle(diagramView, resizeBounds.getBottomRightPoint(), firstSelected);
	      setNodeProperties(node, "bottom-right");
	      group.addChild(node);
	
	      node = createHandle(diagramView, resizeBounds.getTopRightPoint(), firstSelected);
	      setNodeProperties(node, "top-right");
	      group.addChild(node);
	
	      node = createHandle(diagramView, resizeBounds.getBottomLeftPoint(), firstSelected);
	      setNodeProperties(node, "bottom-left");
	      group.addChild(node);
	    }
	    
	    if (borderOn)
	    {
	  		// if the resizeBounds have altered, then draw a line around the shape
	      UPoint cross = null;
        UBounds constrainedBounds = resizer.getCachedPreview(toResize).getFullBounds();
	  		switch (corner)
	      {
	        case TOP_LEFT_CORNER:
	          cross = constrainedBounds.getTopLeftPoint();
	          break;
	        case BOTTOM_RIGHT_CORNER:
	          cross = constrainedBounds.getBottomRightPoint();
	          break;
	        case TOP_RIGHT_CORNER:
	          cross = constrainedBounds.getTopRightPoint();
	          break;
	        case BOTTOM_LEFT_CORNER:
	          cross = constrainedBounds.getBottomLeftPoint();
	          break;
	      }
	  		group.addChild(new CrossHair(cross).formView());
	  		
	  		group.addChild(resizer.formView());
	  		
	  		// add a grey cover over the resizing part
	  		ZRectangle rect = new ZRectangle(constrainedBounds);
	  		rect.setPenPaint(null);
	  		rect.setFillPaint(ScreenProperties.getPreviewFillColor());
	  		group.addChild(new ZVisualLeaf(rect));
	    }
	
	    group.setChildrenFindable(false);
	    group.setChildrenPickable(true);
	
	    diagramLayer.addChild(group);
	  }
	  
	  private void setNodeProperties(ZNode node, String corner)
	  {
	    node.putClientProperty("manipulator", this);
	    node.putClientProperty("corner", corner);
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

  public ResizingManipulatorGem(
  		ToolCoordinatorFacet coordinator,
      FigureFacet toResize,
      DiagramViewFacet diagramView,
      UBounds originalBounds,
      ResizeVetterFacet vetter,
      boolean firstSelected)
  {
  	this.coordinator = coordinator;
    this.toResize = toResize;
    this.originalBounds = originalBounds;
    this.diagramView = diagramView;
    this.vetter = vetter;
    this.firstSelected = firstSelected;
    enter_STEADY_STATE();
  }


  /**
   *
   * resizing manipulator state machine
   *
   */

  private void enter_STEADY_STATE()
  {
    // must set any display vars here, as formView can be called anytime after this
    resizeBounds = originalBounds;
    borderOn = false;
    resizer = null;
    state = STEADY_STATE;
  }

  private void exit_STEADY_STATE(ZNode source, UPoint point, int modifiers)
  {
    // set up the vars now, and classify here
    // if shift is pressed, resize from the centre
    resizeFromCentre = false;
    if ((modifiers & ZMouseEvent.SHIFT_MASK) != 0)
      resizeFromCentre = true;

    String cornerString = (String) source.getClientProperty("corner");
    corner = NO_CORNER;
    if (cornerString.equals("top-left"))
    {
      corner = TOP_LEFT_CORNER;
      diagramView.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
    }
    else
    if (cornerString.equals("bottom-right"))
    {
      corner = BOTTOM_RIGHT_CORNER;
      diagramView.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
    }
    else
    if (cornerString.equals("top-right"))
    {
      corner = TOP_RIGHT_CORNER;
      diagramView.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
    }
    else
    if (cornerString.equals("bottom-left"))
    {
      corner = BOTTOM_LEFT_CORNER;
      diagramView.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
    }

    startPoint = point;

    // we are now dragging the mouse, if we are over a corner
    if (corner != NO_CORNER)
    {
      state = DRAGGING_SUBSTATE;
	    vetter.startResizeVet();
    }
  }

  private void reenter_DRAGGING_SUBSTATE(UPoint point)
  {
    UBounds oldBounds = resizeBounds;
    UDimension difference = new UPoint(point).subtract(startPoint);

    // get the proposed extent, and ask it to be vetted
    UDimension extent = resizeBounds.getDimension();
    UDimension addition = difference;
    if (resizeFromCentre)
      addition = addition.multiply(2);  // double difference

    //////////////////////////
    // work out the new extent
    //////////////////////////
    switch (corner)
    {
      case TOP_LEFT_CORNER:
        extent = extent.add(difference.negative());
        break;
      case BOTTOM_RIGHT_CORNER:
        extent = extent.add(difference);
        break;
      case TOP_RIGHT_CORNER:
        extent = extent.add(new UDimension(difference.getWidth(), -difference.getHeight()));
        break;
      case BOTTOM_LEFT_CORNER:
        extent = extent.add(new UDimension(-difference.getWidth(), difference.getHeight()));
        break;
    }
    startPoint = point;
    
    // set up the bounds now
    UPoint topLeftPt = resizeBounds.getPoint();

    // ask a vetter
    extent = vetter.vetResizedExtent(new UBounds(topLeftPt, extent));

    /////////////////////////////
    // work out the resize bounds
    /////////////////////////////
    if (resizeFromCentre)
    {
      resizeBounds = formCentrePreservingBounds(oldBounds, extent);
    }
    else
    {
      switch (corner)
      {
        case TOP_LEFT_CORNER:
          topLeftPt = oldBounds.getBottomRightPoint().add(extent.negative());
          break;
        case BOTTOM_RIGHT_CORNER:
          // topleft stays where it was
          break;
        case TOP_RIGHT_CORNER:
          topLeftPt = oldBounds.getBottomLeftPoint().add(new UDimension(0, -extent.getHeight()));
          break;
        case BOTTOM_LEFT_CORNER:
          topLeftPt = oldBounds.getTopRightPoint().add(new UDimension(-extent.getWidth(), 0));
          break;
      }
      resizeBounds = new UBounds(topLeftPt, extent);
    }

    ///////////////////////////////////////////////
    // set the start point to be one of the corners
    ///////////////////////////////////////////////
    switch (corner)
    {
      case TOP_LEFT_CORNER:
        startPoint = resizeBounds.getTopLeftPoint();
        break;
      case BOTTOM_RIGHT_CORNER:
        startPoint = resizeBounds.getBottomRightPoint();
        break;
      case TOP_RIGHT_CORNER:
        startPoint = resizeBounds.getTopRightPoint();
        break;
      case BOTTOM_LEFT_CORNER:
        startPoint = resizeBounds.getBottomLeftPoint();
        break;
    }

    // ask a possible vetter
    UBounds screenBounds = resizeBounds;
    screenBounds = vetter.vetResizedBounds(diagramView, corner, resizeBounds, resizeFromCentre);

    // create the resizing figures now, if we haven't already
    boolean wantBorder = borderOn || !resizeBounds.equals(oldBounds);
    if (wantBorder)
    {
      if (!borderOn && wantBorder)
      {
        resizer = new ResizingFiguresGem(null, diagramView.getDiagram()).getResizingFiguresFacet();
        resizer.markForResizing(toResize);
      }
      borderOn = true;
      
      resizer.setFocusBounds(screenBounds);
    }

    // regenerate on the diagram view
    manipulatorFacet.cleanUp();
    manipulatorFacet.addToView(diagramLayer, canvas);
  }


  public static UBounds formCentrePreservingBounds(UBounds oldBounds, UDimension extent)
  {
    // preserve the centre, but make symmetrical and gridded
    UPoint oldCentre = oldBounds.getMiddlePoint();

    // make width and height an integer of gridded size * 2
    UDimension grid = Grid.getGridSpace().multiply(2);

    double oldWidth = oldBounds.getWidth();
    double gridWidth = grid.getWidth();
    double width = oldWidth + Math.ceil((extent.getWidth() - oldWidth) / gridWidth) * gridWidth;

    double oldHeight = oldBounds.getHeight();
    double gridHeight = grid.getHeight();
    double height = oldHeight + Math.ceil((extent.getHeight() - oldHeight) / gridHeight) * gridHeight;

    UPoint topLeft = new UPoint(oldCentre.getX() - width / 2, oldCentre.getY() - height / 2);
    return new UBounds(topLeft, new UDimension(width, height));
  }

  public static UBounds formCentrePreservingBoundsExactly(UBounds oldBounds, UDimension extent)
  {
    // preserve the centre, but make symmetrical and gridded
    UPoint oldCentre = oldBounds.getMiddlePoint();

    // make width and height an integer of gridded size * 2
    UDimension grid = Grid.getGridSpace().multiply(2);

    double oldWidth = oldBounds.getWidth();
    double gridWidth = grid.getWidth();
    double width = oldWidth + Math.round((extent.getWidth() - oldWidth) / gridWidth) * gridWidth;

    double oldHeight = oldBounds.getHeight();
    double gridHeight = grid.getHeight();
    double height = oldHeight + Math.round((extent.getHeight() - oldHeight) / gridHeight) * gridHeight;

    UPoint topLeft = new UPoint(oldCentre.getX() - width / 2, oldCentre.getY() - height / 2);
    return new UBounds(topLeft, extent);
  }

  private void enter_RESIZED_STATE(DiagramFacet diagram, UPoint point)
  {
    // jump back to the steady state
    manipulatorFacet.cleanUp();

    if (borderOn)
    {
      listener.haveFinished();
    	coordinator.startTransaction("resizing " + toResize.getFigureName(), "undoing resize of " + toResize.getFigureName());
	    resizer.end();
	    coordinator.commitTransaction();
    	enter_STEADY_STATE();  // need to do this so that addToView() will display handles again
    }
    else
    {
    	enter_STEADY_STATE();  // need to do this so that addToView() will display handles again
      listener.haveFinished();
    }
  }

  /**
   *
   *
   * state machine finished
   *
   *
   */
  
	public ManipulatorFacet getManipulatorFacet()
	{
		return manipulatorFacet;
	}

  public static ZVisualLeaf createHandle(DiagramViewFacet diagramView, UPoint handleCentre, boolean firstSelected)
  {
    int offset = 4;
    int handleDiameter = 8;
    double scaleX = diagramView.getScale().getWidth();
    double scaleY = diagramView.getScale().getHeight();

    UBounds handleBounds = new UBounds(handleCentre, new UDimension(handleDiameter/scaleX, handleDiameter/scaleY)).addToPoint(new UDimension(-offset/scaleX, -offset/scaleY));
    ZEllipse ell = new ZEllipse(handleBounds.getX(), handleBounds.getY(), handleBounds.getWidth(), handleBounds.getHeight());
    if (firstSelected)
      ell.setFillPaint(ScreenProperties.getFirstSelectedHighlightColor());
    else
      ell.setFillPaint(ScreenProperties.getHighlightColor());
    ell.setPenPaint(HANDLE_PEN_COLOUR);
    
    return new ZVisualLeaf(ell);
  }
}
