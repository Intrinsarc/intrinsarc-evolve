package com.intrinsarc.idraw.arcfacilities.adjust;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.arcfacilities.previewsupport.*;
import com.intrinsarc.idraw.diagramsupport.moveandresize.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.nodefacilities.linking.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;


public final class ArcAdjustManipulatorGem implements Gem
{ 
	private static final int STEADY_STATE                   = 1;
  private static final int WAITING_SUBSTATE               = 2;
  private static final int MOVING_NEW_POINT_SUBSTATE      = 3;
  private static final int MOVING_EXISTING_POINT_SUBSTATE = 4;
  private static final int MOVING_FREE_NODE_SUBSTATE      = 5;
  private static final int MOVING_OVER_LINKABLE_SUBSTATE  = 6;

  private int state = STEADY_STATE;

  private DiagramViewFacet diagramView;
  private String type;
  private int index;
  private boolean retargetingNode1;
  private ZGroup group;
	private boolean curved;

  private LinkingFacet toAdjust;
  private ActualArcPoints actualPoints;
  private ActualArcPoints savedActualPoints;
  private boolean showHandles;
  private boolean firstSelected;
  private boolean layoutOnly;

  private ResizingFiguresFacet resizings;
	private ManipulatorFacetImpl manipulatorFacet = new ManipulatorFacetImpl();
	private ToolCoordinatorFacet coordinator;

  public ArcAdjustManipulatorGem(
  		ToolCoordinatorFacet coordinator,
      LinkingFacet toAdjust,
      DiagramViewFacet diagramView,
      CalculatedArcPoints calculatedPoints,
      boolean curved,
      boolean firstSelected)
  {
  	DiagramFacet diagram = diagramView.getDiagram();
  	this.coordinator = coordinator;
    actualPoints = new ActualArcPoints(diagram, calculatedPoints);
    actualPoints.setNode1Preview(actualPoints.getNode1().getFigureFacet().getSinglePreview(diagram).getAnchorPreviewFacet());
    actualPoints.setNode2Preview(actualPoints.getNode2().getFigureFacet().getSinglePreview(diagram).getAnchorPreviewFacet());
    savedActualPoints = new ActualArcPoints(diagram, calculatedPoints);
    savedActualPoints.setNode1Preview(actualPoints.getNode1().getFigureFacet().getSinglePreview(diagram).getAnchorPreviewFacet());
    savedActualPoints.setNode2Preview(actualPoints.getNode2().getFigureFacet().getSinglePreview(diagram).getAnchorPreviewFacet());
    this.toAdjust = toAdjust;
    this.diagramView = diagramView;
    this.curved = curved;
    showHandles = true;
    this.firstSelected = firstSelected;
  }

	private class ManipulatorFacetImpl implements ManipulatorFacet
	{
	  private ZGroup diagramLayer;
	  private ManipulatorListenerFacet listener;

	  /** returns the type of the manipulator.  Choose TYPE1, as we want this to be manipulable, even if the item is not yet selected. */
	  public int getType()
	  {
	    return TYPE1;
	  }
	

		/** invoked to see if we want to enter on this key
		 * @param keyPressed the key that was pressed
		 */
		public boolean wantToEnterViaKeyPress(char keyPressed)
		{
			return false;
		}

	  /** invoked on key entry */
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
	    // this is the way that this manipulator is always entered
	    this.listener = listener;
      enter_WAITING_SUBSTATE(source, point);
	  }
	
	  /**invoked on button release entry*/
	  public void enterViaButtonRelease(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	  {
	    // not applicable for this manipulator
	  }
	
	  /**invoked when a key has been pressed*/
	  public void keyPressed(char keyPressed)
	  {
	  }
	
	  public void mousePressed(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	    // not applicable -- entry is always via button press, and finished on release
	  }
	
	  /** invoked when a mouse button has been dragged on this figure */
	  public void mouseDragged(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  	// we only want to show the handles if this is curved
	  	showHandles = true;
	    switch (state)
	    {
	      case WAITING_SUBSTATE:
	        // do this here so we don't do anything dramatic until the user has moved the mouse enough
	        // -- this prevents the situation when the user selects the line only as feeling like they are always moving it
	        // is this a point or a segment?
	        exit_WAITING_SUBSTATE(over, point);
	        break;
	      case MOVING_EXISTING_POINT_SUBSTATE:
	        reenter_MOVING_EXISTING_POINT_SUBSTATE(point);
	        break;
	      case MOVING_NEW_POINT_SUBSTATE:
	        reenter_MOVING_NEW_POINT_SUBSTATE(point);
	        break;
	      case MOVING_FREE_NODE_SUBSTATE:
          reenter_MOVING_FREE_NODE_SUBSTATE(over, point);
	        break;
	      case MOVING_OVER_LINKABLE_SUBSTATE:
          reenter_MOVING_OVER_LINKABLE_SUBSTATE(over, point);
	        break;
	    }
	
	    // regenerate on the diagram view
	    cleanUp();
	    addToView(diagramLayer, null);
	  }
	
	  public void mouseMoved(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  }
	
	  /**Invoked when a mouse button has been released on this figure*/
	  public void mouseReleased(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	    actualPoints.removeKinks();
	    switch (state)
	    {
	      case WAITING_SUBSTATE:
	        // simply go back to the steady state
	        break;
	      case MOVING_EXISTING_POINT_SUBSTATE:
	        // make a modification command
	      	coordinator.startTransaction("moved existing link point", "reverted existing link point move");
	        resizings.end();
	        break;
	      case MOVING_NEW_POINT_SUBSTATE:
	        // make a modification command
	      	coordinator.startTransaction("moved new link point", "reverted new link point move");
	        resizings.end();
	        break;
	      case MOVING_OVER_LINKABLE_SUBSTATE:
	        // make a modification command but only if this is a valid target
	      	AnchorFacet node1 = actualPoints.getNode1();
	      	AnchorFacet node2 = actualPoints.getNode2();
	      	if (toAdjust.acceptsAnchors(node1, node2))
	      	{
		      	coordinator.startTransaction("moved existing link point", "reverted existing link point move");
	      		resizings.end();
	      		toAdjust.makeReanchorAction(node1, node2);
	      	}
	      	else
	      	{
	      		actualPoints = new ActualArcPoints(savedActualPoints);
		        diagramView.turnSweepLayerOff();
	      	}
	        break;
	      case MOVING_FREE_NODE_SUBSTATE:
	        // get the saved set of points back.  No command, as this is effectively a cancel operation
	        actualPoints = new ActualArcPoints(savedActualPoints);
	        diagramView.turnSweepLayerOff();
	        break;
	    }
	
	    // regenerate on the diagram view
	    cleanUp();
	    resizings = null;
	    state = STEADY_STATE;
	    showHandles = true;
	
	    listener.haveFinished();
	  }
	
	  public void addToView(ZGroup diagramLayer, ZCanvas canvas)
	  {
	    int offset = 8;
	    this.diagramLayer = diagramLayer;
	    group = new ZGroup();
	
	    if (resizings != null)
	    {
	      setPointsOfPreview();
	      group.addChild(resizings.formView());
	    }
	
	    // make the thick line
	    CalculatedArcPoints calculated = actualPoints.calculateAllPoints();
	    List<UPoint> points = calculated.getAllPoints();
	
			// don't draw anything if we aren't in the steady state
			if (state == STEADY_STATE)
			{
				ZShape thickLine = CalculatedArcPoints.makeConnection(points, curved);
				thickLine.setPenWidth(7);
				thickLine.setPenPaint(ScreenProperties.getTransparentColor());
				
				ZVisualLeaf thickLeaf = new ZVisualLeaf(thickLine); 
				setNodeProperties(thickLeaf, "segment", 0);

				group.addChild(thickLeaf);
				thickLeaf.lower();
			}					
			
	    // make the handles
	    if (showHandles)
			{
		    Iterator<UPoint> iter = points.iterator();
		    for (int index = 0; iter.hasNext(); index++)
		    {
		      UPoint point = iter.next();
		
		      UBounds handleBounds = new UBounds(point, new UDimension(offset, offset)).addToPoint(new UDimension(-offset/2, -offset/2));
		      ZEllipse ell = new ZEllipse(handleBounds.getX(), handleBounds.getY(), handleBounds.getWidth(), handleBounds.getHeight());
		      if (firstSelected)
		        ell.setFillPaint(ScreenProperties.getFirstSelectedHighlightColor());
		      else
		        ell.setFillPaint(ScreenProperties.getHighlightColor());
		      ell.setPenPaint(Color.black);
		      ZNode node = new ZVisualLeaf(ell);
		      setNodeProperties(node, "point", index);
		      group.addChild(node);
		    }
			}
	
	    group.setChildrenFindable(false);
	    group.setChildrenPickable(true);
	
	    diagramLayer.addChild(group);
	  }
	
    public void cleanUp()
	  {
	    if (group != null)
	    {
	      diagramLayer.removeChild(group);
	      group = null;
	    }
	  }


    public void setLayoutOnly()
    {
      layoutOnly = true;
    }
	}

  private void setNodeProperties(ZNode node, String type, int index)
  {
    node.putClientProperty("manipulator", manipulatorFacet);
    node.putClientProperty("type", type);
    node.putClientProperty("index", new Integer(index));
  }

  /**
   * start of state machine
   *
   */

  private void enter_ADJUSTING_POINT_STATE(UPoint point)
  {
    // move the point specified by the index
    state = MOVING_EXISTING_POINT_SUBSTATE;
    reenter_MOVING_EXISTING_POINT_SUBSTATE(point);
  }

  private void reenter_MOVING_EXISTING_POINT_SUBSTATE(UPoint point)
  {
    // move the point specified by the index
    // note that index is 1 greater than the internal points array
    actualPoints.replaceInternalPoint(index-1, point);
    diagramView.turnSweepLayerOn(new CrossHair(point).formView());
  }

  private void reenter_MOVING_NEW_POINT_SUBSTATE(UPoint point)
  {
    // move the point specified by the index
    // note that index is 1 greater than the internal points array
    actualPoints.replaceInternalPoint(index, point);
    diagramView.turnSweepLayerOn(new CrossHair(point).formView());
  }

  private void enter_BREAKING_SEGMENT_STATE(UPoint point)
  {
    state = MOVING_NEW_POINT_SUBSTATE;
    // add a point
    actualPoints.addInternalPoint(index, point);
  }

  private void enter_WAITING_SUBSTATE(ZNode source, UPoint point)
  {
    type = (String) source.getClientProperty("type");
    index = ((Integer) source.getClientProperty("index")).intValue();
    state = WAITING_SUBSTATE;
    
    // if the type is "segment", find the line that the point is closest to
    // works for curved and rectilinear lines
    if (type.equals("segment"))
    {
	    List<UPoint> points = actualPoints.calculateAllPoints().getAllPoints();
	    Iterator<UPoint> iter = points.iterator();
	    UPoint start = iter.next();
	    double minimumDistanceToLine = 1e7;
	    index = 0;
	    int loop = 0;
	    for (; iter.hasNext(); loop++)
	    {
	    	UPoint next = iter.next();
	    	Line2D line = new Line2D.Double(start, next);
	    	
	    	double distanceToLine = line.ptLineDist(point);
	    	if (distanceToLine < minimumDistanceToLine)
	    	{
	    		minimumDistanceToLine = distanceToLine;
	    		index = loop;
	    	}
	    	// the 2nd point of this line is the start point for the next line	    	
	    	start = next;
	    }
    }
  }

  private void exit_WAITING_SUBSTATE(FigureFacet over, UPoint point)
  {
    // form the resizing figures
    resizings = new ResizingFiguresGem(null, diagramView.getDiagram()).getResizingFiguresFacet();

    // get the figure back again, and set the actual points
    resizings.markForResizing(toAdjust.getFigureFacet());
    resizings.forceDisplay();
    setPointsOfPreview();

    if (type.equals("point"))
    {
      // if this is an end point, we are retargeting
      if (index == 0 || index == actualPoints.getInternalPoints().size() + 2 - 1)
      {
        // don't allow retargeting of endpoints in layout only mode
        if (!layoutOnly && !toAdjust.getFigureFacet().isSubjectReadOnlyInDiagramContext(false))
          enter_RETARGETING_ARC_END_STATE(over, point);
      }
      else
        enter_ADJUSTING_POINT_STATE(point);
    }
    else
    if (type.equals("segment"))
      enter_BREAKING_SEGMENT_STATE(point);

    // place cross hairs
    if (state != STEADY_STATE && state != WAITING_SUBSTATE)
      diagramView.turnSweepLayerOn(new CrossHair(point).formView());
  }

  /**
   * 
   */
  private void setPointsOfPreview()
  {
    PreviewFacet preview = resizings.getCachedPreviewOrMakeOne(toAdjust.getFigureFacet());
    
    // get the BasicArcPreviewFacet dynamic preview
    BasicArcPreviewFacet basicPreviewFacet = preview.getDynamicFacet(BasicArcPreviewFacet.class);
    basicPreviewFacet.setActualPoints(actualPoints);
  }

  private void enter_RETARGETING_ARC_END_STATE(FigureFacet over, UPoint point)
  {
    // work out which node this is
    retargetingNode1 = (index == 0);
    reenter_MOVING_FREE_NODE_SUBSTATE(over, point);
  }

  private void reenter_MOVING_FREE_NODE_SUBSTATE(FigureFacet over, UPoint point)
  {
    if (over != null && over.getAnchorFacet() != null && over != toAdjust.getFigureFacet() &&
        (!over.isSubjectReadOnlyInDiagramContext(false) || !retargetingNode1 || toAdjust.getFigureFacet().isSubjectReadOnlyInDiagramContext(false)))
    {
      reenter_MOVING_OVER_LINKABLE_SUBSTATE(over, point);
      return;
    }

    // we need to construct a free node
    state = MOVING_FREE_NODE_SUBSTATE;
    
    DiagramFacet diagram = diagramView.getDiagram();
	  BasicNodeGem basicGem = new BasicNodeGem(null, diagram, diagram.makeNewFigureReference().getId(), new UPoint(0,0), true, false);
		BasicAnchorGem basicLinkingGem = new BasicAnchorGem();
		basicGem.connectBasicNodeAppearanceFacet(basicLinkingGem.getBasicNodeAppearanceFacet());
		basicLinkingGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
	  FigureFacet mouseLinker = basicGem.getBasicNodeFigureFacet();
	  PreviewFacet mouseLinkerPreview = mouseLinker.getSinglePreview(diagramView.getDiagram());

    mouseLinkerPreview.setFullBounds(new UBounds(point, new UDimension(0,0)), false);
    if (retargetingNode1)
    {
      actualPoints.setNode1(mouseLinker.getAnchorFacet());
      actualPoints.setNode1Preview(mouseLinkerPreview.getAnchorPreviewFacet());
      actualPoints.setVirtualPoint(actualPoints.calculateBetterVirtualPoint(point, actualPoints.getPreview2()));
    }
    else
    {
      actualPoints.setNode2(mouseLinker.getAnchorFacet());
      actualPoints.setNode2Preview(mouseLinkerPreview.getAnchorPreviewFacet());
      actualPoints.setVirtualPoint(actualPoints.calculateBetterVirtualPoint(point, actualPoints.getPreview1()));
    }

    diagramView.turnSweepLayerOn(new CrossHair(point).formView());
  }

  private void reenter_MOVING_OVER_LINKABLE_SUBSTATE(FigureFacet over, UPoint point)
  {
    if (over == null || over.getAnchorFacet() == null || over == toAdjust.getFigureFacet())
    {
      reenter_MOVING_FREE_NODE_SUBSTATE(over, point);
      return;
    }

    // we have a node to attach to
    AnchorFacet linkable = over.getAnchorFacet();
    state = MOVING_OVER_LINKABLE_SUBSTATE;
    if (retargetingNode1)
    {    	
      actualPoints.setNode1(linkable);
      actualPoints.setNode1Preview(linkable.getFigureFacet().getSinglePreview(diagramView.getDiagram()).getAnchorPreviewFacet());
      actualPoints.setVirtualPoint(actualPoints.calculateBetterVirtualPoint(point, actualPoints.getPreview2()));

      ZGroup group = new ZGroup();
    	boolean ok = toAdjust.acceptsAnchors(linkable, toAdjust.getAnchor2());
    	group.addChild(actualPoints.getPreview1().formAnchorHighlight(ok));
      group.addChild(new CrossHair(actualPoints.calculateAllPoints().getStartPoint()).formView());
      diagramView.turnSweepLayerOn(group);
    }
    else
    {
      actualPoints.setNode2(linkable);
      actualPoints.setNode2Preview(linkable.getFigureFacet().getSinglePreview(diagramView.getDiagram()).getAnchorPreviewFacet());
      actualPoints.setVirtualPoint(actualPoints.calculateBetterVirtualPoint(point, actualPoints.getPreview1()));
      ZGroup group = new ZGroup();

    	boolean ok = toAdjust.acceptsAnchors(toAdjust.getAnchor1(), linkable);
      group.addChild(actualPoints.getPreview2().formAnchorHighlight(ok));
      group.addChild(new CrossHair(actualPoints.calculateAllPoints().getEndPoint()).formView());
      diagramView.turnSweepLayerOn(group);
    }
  }

  /**
   * end of state machine
   *
   */

	public ManipulatorFacet getManipulatorFacet()
	{
		return manipulatorFacet;
	}
}