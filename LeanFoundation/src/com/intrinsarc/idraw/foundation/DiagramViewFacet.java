package com.intrinsarc.idraw.foundation;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;



/**
 * this a view of a diagram, which has a selection.  It makes sure that anything that is adjusted on
 * the diagram is reflected in the view on the screen.
 */

public interface DiagramViewFacet extends Facet
{
	/** does this want to actually use tools? */
	public boolean isUsingTools();
	
	/** allow another object to register for key events.  only 1 is allowed */
	public void addKeyListenerJustOnce(KeyListener listener);

  /**get hold of the selection*/
  public SelectionFacet getSelection();
  public void addFigureToSelectionViaId(String id);

  /** a view can have a scale */
  public UDimension getScale();
  public void setScalingBounds(UBounds bounds);
	public void setOriginalScaling();
  
  /** we can get the actual draw bounds of the figures on the page */
  public UBounds getDrawnBounds();

  /**select everything -- an optimisation*/
  public void selectAllFigures( );

  /**get hold of the current diagram */
  public DiagramFacet getDiagram();
  public void transitionToDiagram(DiagramFacet diagram, UBounds openRegionHint);

  /** allow the cursor to be set */
  public void setCursor(Cursor cursor);
  public UPoint getCursorPoint();
  public void resetCursor();
  
  /**is this really needed here?*/
  public void setCurrentTool(ToolFacet newCurrentTool, ToolCoordinatorFacet coordinator );

  /** pan the diagram */
  void pan(double xDifference, double yDifference);
  UDimension getCurrentPan();

  /**support for the interactive nature of tools -- finding nodes under the mouse or in an area*/
  public ManipulatorFacet getManipulatorUnderPoint(UPoint e, ZNode[] nodes);
  public FigureFacet getFigureIgnoringManipulators(UPoint e, ZNode[] nodes);
  public FigureFacet getFigureUsingEventPoint(ZMouseEvent e, ZNode[] nodes);
  public FigureFacet[] findInArea(UBounds area, Class<?> markerInterface );


  /**support for the interactive nature of tools -- redrawing the screen, or the selection/sweep layer*/
  public void turnSweepLayerOn(ZNode sweepNode );
  public void turnSweepLayerOff( );
  public void turnDebugLayerOn(ZNode sweepNode );
  public void turnDebugLayerOff( );
  public void turnSelectionLayerOn( );
  public void turnSelectionLayerOff( );
  public void setBackdrop(ZNode backdrop);
  public void setOtherBackdrop(ZNode backdrop);
  public void moveManipulatorToNewLayer(ManipulatorFacet manipulator);
  public void moveManipulatorToSelectionLayer(ManipulatorFacet manipulator);
  public void setBackgroundColor(Color background);
  
  /** hack -- should encapsulate better -- used for popups */
  public ZCanvas getCanvas();
  
  /** context sensitive menus and actions */
  public JPopupMenu makeContextMenu(ToolCoordinatorFacet coordinator);
  public void middleButtonPressed();
  public SmartMenuContributorFacet getSmartMenuContributorFacet();
  public List<DiagramFigureAdornerFacet> getAdorners();
}