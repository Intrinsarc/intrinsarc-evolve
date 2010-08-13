package com.intrinsarc.idraw.arcfacilities.creation;

import java.awt.event.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.arcfacilities.creationbase.*;
import com.intrinsarc.idraw.arcfacilities.previewsupport.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.linking.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;


public final class ArcCreateToolGem implements Gem
{
  // state enums
  private static final int STEADY_STATE                  = 1;
  private static final int CONSTRUCTING_SEGMENT_SUBSTATE = 2;
  // state vars
  private int state = STEADY_STATE;
  // needed for management of tool
  private boolean active;
  private DiagramViewFacet diagramView;
  private DiagramFacet diagram;
  private SelectionFacet selection;
  private ZNode mouseNode;
  private ToolCoordinatorFacet coordinator;
  // state for the current arc in construction
  private UPoint startPoint;
  private ArcCreateFacet factory;
  private BasicArcPreviewFacet previewFigure;
  private FigureFacet mouseLinker;
  private PreviewFacet mouseLinkerPreview;
  private AnchorFacet initialLinkable;
  private boolean overLinkableNode;
  private ToolFacet toolFacet = new ToolFacetImpl();
  private ZMouseListenerImpl mouseListener = new ZMouseListenerImpl();

  public ArcCreateToolGem(ArcCreateFacet factory)
  {
    this.factory = factory;
    this.previewFigure = null;
  }
  
  public ToolFacet getToolFacet()
  {
  	return toolFacet;
  }
  
  private class ZMouseListenerImpl implements ZMouseListener, ZMouseMotionListener
  {
	  public void mousePressed(ZMouseEvent e)
	  {
	    if (state == STEADY_STATE)
	    {
	      // get the arcable figure under this one
	      FigureFacet figure = getFigure(new UPoint(e.getLocalPoint()));
	      
	      if (figure != null &&
	          figure.getAnchorFacet() != null &&
	          factory.acceptsAnchors(figure.getAnchorFacet(), null))
	        enter_CONSTRUCTING_ARC_STATE(e, figure.getAnchorFacet());
	      else
	        coordinator.toolFinished(e, false);
	
	      selection.clearAllSelection();
	    }
	  }
	
	  public void mouseDragged(ZMouseEvent e)
	  {
	    if (state == CONSTRUCTING_SEGMENT_SUBSTATE)
	      reenter_CONSTRUCTING_SEGMENT_SUBSTATE(e);
	  }
	
	  public void mouseReleased(ZMouseEvent e)
	  {
	    if (state == CONSTRUCTING_SEGMENT_SUBSTATE)
	    {
	      FigureFacet figure = getFigure(new UPoint(e.getLocalPoint()));
	      if (figure != null  && figure.getAnchorFacet() != null && factory.acceptsAnchors(initialLinkable, figure.getAnchorFacet()))
	        enter_MAKE_ARC_FIGURE_SUBSTATE(e, figure.getAnchorFacet());
	      else
	        enter_START_NEW_SEGMENT_SUBSTATE(e);
	    }
	  }
	
	  public void mouseMoved(ZMouseEvent e)
	  {
	    if (state == STEADY_STATE)
	      reenter_STEADY_STATE(e);
	    else
	    if (state == CONSTRUCTING_SEGMENT_SUBSTATE)
	      reenter_CONSTRUCTING_SEGMENT_SUBSTATE(e);
	  }
	
	  public void mouseClicked(ZMouseEvent e){}
	  public void mouseEntered(ZMouseEvent e){}
	  public void mouseExited(ZMouseEvent e){}
  }
  
  private class ToolFacetImpl implements ToolFacet
  {
	  public boolean isActive()
	  {
	    return active;
	  }
	
	  public void activate(DiagramViewFacet newDiagramView, ZNode newMouseNode, ToolCoordinatorFacet newCoordinator)
	  {
	    if (!active)
	    {
	      diagramView = newDiagramView;
	      diagram = diagramView.getDiagram();
	      selection = diagramView.getSelection();
	      mouseNode = newMouseNode;
	      active = true;
	      coordinator = newCoordinator;
	      BasicNodeGem basicGem = new BasicNodeGem(null, diagram, diagram.makeNewFigureReference().getId(), new UPoint(0,0), true, false);
				BasicAnchorGem basicLinkingGem = new BasicAnchorGem();
				basicGem.connectBasicNodeAppearanceFacet(basicLinkingGem.getBasicNodeAppearanceFacet());
				basicLinkingGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
	      mouseLinker = basicGem.getBasicNodeFigureFacet();
	      mouseLinkerPreview = mouseLinker.getSinglePreview(diagram);
	      mouseNode.addMouseListener(mouseListener);
	      mouseNode.addMouseMotionListener(mouseListener);
	      diagramView.turnSelectionLayerOff();
	      state = STEADY_STATE;
	    }
	  }
	
	  public void deactivate()
	  {
	    if (active)
	    {
	      // turn off this handler, and all registered handlers.
	      active = false;
	      mouseNode.removeMouseListener(mouseListener);
	      mouseNode.removeMouseMotionListener(mouseListener);
	      diagramView.turnSelectionLayerOn();
	    }
	  }
	
	  public void keyTyped(KeyEvent event) {}
    public void keyPressed(KeyEvent event) {}
    public void keyReleased(KeyEvent event) {}

    public boolean wantsKey(KeyEvent event)
    {
      return false;
    }

		public ArcAcceptanceFacet getPossibleArcAcceptance()
		{
			return factory;
		}
  }


/////////////////////////////////////////////////////////////////////////////////
//// mouse functions
/////////////////////////////////////////////////////////////////////////////////

	private FigureFacet getFigure(UPoint point)
	{
  	FigureFacet figure = diagramView.getFigureIgnoringManipulators(point, null);
  	if (figure == null)
  		return figure;
  	return figure.getActualFigureForSelection();
	}

////////////////////////////////////////////////////////////////////////////////
///// start of state machine calls
////////////////////////////////////////////////////////////////////////////////

  private void enter_CONSTRUCTING_ARC_STATE(ZMouseEvent e, AnchorFacet arcable)
  {
    overLinkableNode = false;

    initialLinkable = arcable;
    startPoint = Grid.roundToGrid(new UPoint(e.getLocalPoint()));
    mouseLinkerPreview.setFullBounds(new UBounds(startPoint, new UDimension(0,0)), false);

    // see if both nodes are also selected
    ActualArcPoints newActuals = new ActualArcPoints(diagram, arcable, mouseLinker.getAnchorFacet(), startPoint);
    newActuals.setNode1Preview(arcable.getFigureFacet().getSinglePreview(diagram).getAnchorPreviewFacet());
    newActuals.setNode2Preview(mouseLinkerPreview.getAnchorPreviewFacet());
		// use a rectilinear preview to set up the arc
		BasicArcPreviewGem gem = new BasicArcPreviewGem(diagram, null, newActuals, new UPoint(0, 0), false, false);
    previewFigure = gem.getBasicArcPreviewFacet();
    state = CONSTRUCTING_SEGMENT_SUBSTATE;
  }

  private void reenter_STEADY_STATE(ZMouseEvent e)
  {
    // highlight a possible linkable under the mouse
    UPoint mousePoint = Grid.roundToGrid(new UPoint(e.getLocalPoint()));
    FigureFacet overNow = getFigure(mousePoint);

    // the virtual point is always taken to be the middle
    if (overNow != null && overNow.getAnchorFacet() != null)
    {
      // ask the acceptance facet if this is ok
      if (factory.acceptsAnchors(overNow.getAnchorFacet(), null))
      {
        AnchorFacet linkable = overNow.getAnchorFacet();
        AnchorPreviewFacet preview = linkable.getFigureFacet().getSinglePreview(diagram).getAnchorPreviewFacet();
        diagramView.turnSweepLayerOn(preview.formAnchorHighlight(true));
      }
      else
      {
        AnchorFacet linkable = overNow.getAnchorFacet();
        AnchorPreviewFacet preview = linkable.getFigureFacet().getSinglePreview(diagram).getAnchorPreviewFacet();
        diagramView.turnSweepLayerOn(preview.formAnchorHighlight(false));
      }
    }
    else
      diagramView.turnSweepLayerOff();
  }

  private void reenter_CONSTRUCTING_SEGMENT_SUBSTATE(ZMouseEvent e)
  {
    // get a possible arcable node under the mouse
    UPoint mousePoint = Grid.roundToGrid(new UPoint(e.getLocalPoint()));
    FigureFacet overNow = getFigure(mousePoint);

    // the virtual point is always taken to be the middle
    if (overNow == null || overNow.getAnchorFacet() == null)
    {
      mouseLinkerPreview.setFullBounds(new UBounds(mousePoint, new UDimension(0,0)), false);
      previewFigure.setNode2(mouseLinker.getAnchorFacet());
      previewFigure.setNode2Preview(mouseLinkerPreview.getAnchorPreviewFacet());
      previewFigure.formBetterVirtualPoint(Grid.roundToGrid(mousePoint));
      overLinkableNode = false;
    }
    else
    {
      // we have a node2 now
      previewFigure.formBetterVirtualPoint(Grid.roundToGrid(mousePoint));
      previewFigure.setNode2(overNow.getAnchorFacet());
      previewFigure.setNode2Preview(overNow.getSinglePreview(diagram).getAnchorPreviewFacet());
      overLinkableNode = true;
    }

    ZGroup group = new ZGroup();
    if (overLinkableNode)
    {
      group.addChild(previewFigure.getPreview2().formAnchorHighlight(factory.acceptsAnchors(initialLinkable, overNow.getAnchorFacet())));
      group.addChild(new CrossHair(previewFigure.getCalculatedPoints().getEndPoint()).formView());
    }
    else
      group.addChild(new CrossHair(mousePoint).formView());
    group.addChild(previewFigure.getPreviewFacet().formView());
    diagramView.turnSweepLayerOn(group);
  }

  private void enter_MAKE_ARC_FIGURE_SUBSTATE(ZMouseEvent e, AnchorFacet arcable)
  {
    // constuct an inheritance figure
    diagramView.turnSweepLayerOff();
    previewFigure.setNode2(arcable);
    previewFigure.setNode2Preview(arcable.getFigureFacet().getSinglePreview(diagram).getAnchorPreviewFacet());

    // get the created figure via its reference
    FigureReference reference = diagram.makeNewFigureReference();

	  // make the arc
    PersistentProperties properties = new PersistentProperties();
    factory.initialiseExtraProperties(properties);
    factory.aboutToMakeTransaction(coordinator);
    coordinator.startTransaction("created " + factory.getFigureName(), "removed " + factory.getFigureName());
    ArcCreateFigureTransaction.create(
    		diagram,
      	null,
        reference,
        factory,
        previewFigure.getReferenceCalculatedPoints(diagram),
        properties);
	  coordinator.commitTransaction();
	  diagramView.getSelection().clearAllSelection();	
	  diagramView.addFigureToSelectionViaId(reference.getId());

    state = STEADY_STATE;

    coordinator.toolFinished(e, false);
  }

  private void enter_START_NEW_SEGMENT_SUBSTATE(ZMouseEvent e)
  {
    // make a new point
    UPoint griddedPoint = Grid.roundToGrid(new UPoint(e.getLocalPoint()));
    previewFigure.addInternalPoint(griddedPoint);
    reenter_CONSTRUCTING_SEGMENT_SUBSTATE(e);
  }

////////////////////////////////////////////////////////////////////////////////
///// end of state machine calls
////////////////////////////////////////////////////////////////////////////////
}