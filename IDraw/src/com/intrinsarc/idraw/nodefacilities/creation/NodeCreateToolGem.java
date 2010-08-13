package com.intrinsarc.idraw.nodefacilities.creation;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.diagramsupport.*;
import com.intrinsarc.idraw.diagramsupport.moveandresize.*;
import com.intrinsarc.idraw.figurefacilities.containment.*;
import com.intrinsarc.idraw.figurefacilities.selection.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;


public final class NodeCreateToolGem implements Gem
{
	// 2 different states
  private static final int STEADY_STATE    = 1;
  private static final int MOVING_STATE    = 2;
  private int state = STEADY_STATE;

  private DiagramViewFacet diagramView;
  private DiagramFacet diagram;
  private ZNode mouseNode;
  private boolean active;
  private NodeCreateFacet factory;
  private ToolCoordinatorFacet coordinator;
  private ContainerFacet intoContainer;
  private ContainerFacet acceptingContainer;
  private ResizingFiguresFacet acceptingResizings;
  private UPoint creationPoint;
  private boolean nodeAcceptsContainer;
  private ToolFacet toolFacet = new ToolFacetImpl();
  private ZMouseListenerImpl mouseListener = new ZMouseListenerImpl();
  private FigureFacet previewable = null;
  private UDimension previewableFullDimension;

  public NodeCreateToolGem(NodeCreateFacet factory)
  {
    this.factory = factory;
  }
  
  public ToolFacet getToolFacet()
  {
  	return toolFacet;
  }
  
  private class ZMouseListenerImpl implements ZMouseListener, ZMouseMotionListener
  {
	 	/**Invoked when a mouse button has been pressed on a component.*/
	  public void mouseReleased(ZMouseEvent e)
	  {
	    if (state == MOVING_STATE)
	      enter_CREATE_NODE_STATE(e);
	  }
	
	  /**
	   * Invoked when a mouse button is pressed on a node and then
	   * dragged.  Mouse drag events will continue to be delivered to
	   * the node where the first originated until the mouse button is
	   * released (regardless of whether the mouse position is within the
	   * bounds of the node).
	   */
	  public void mouseDragged(ZMouseEvent e)
	  {
	    switch (state)
	    {
	      case STEADY_STATE:
	        break;
	      case MOVING_STATE:
	        reenter_MOVING_STATE(e);
	        break;
	    }
	  }
		
	  public void mousePressed(ZMouseEvent e)
	  {
	  	if (e.getButton() == MouseEvent.BUTTON3)
	  		enter_STEADY_STATE();
			else
	  	if (e.getButton() == MouseEvent.BUTTON1)
		    switch (state)
		    {
		      case STEADY_STATE:
		        enter_MOVING_STATE(e);
		        break;
		      case MOVING_STATE:
		        reenter_MOVING_STATE(e);
		        break;
		    }
	  }

	  public void mouseClicked(ZMouseEvent e){}
	  public void mouseEntered(ZMouseEvent e){}
	  public void mouseExited(ZMouseEvent e) {}
	  public void mouseMoved(ZMouseEvent e)  {}
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
	      mouseNode = newMouseNode;
	      active = true;
	      coordinator = newCoordinator;
	      enter_STEADY_STATE();
	
				diagramView.turnSelectionLayerOff();
	      mouseNode.addMouseListener(mouseListener);
	      mouseNode.addMouseMotionListener(mouseListener);
	    }
	  }
	
	  public void deactivate()
	  {
	    if (active)
	    {
	      // turn off this handler, and all registered handlers.
	      active = false;
	      diagramView.turnSweepLayerOff();
	      mouseNode.removeMouseListener(mouseListener);
	      mouseNode.removeMouseMotionListener(mouseListener);
	      
	      // if we still have a creation object, then we must remove it
	      removeCreationObject();
	      diagramView.turnSelectionLayerOn();
	    }
	  }
	  
	  public void keyTyped(KeyEvent event)  {}
    public void keyPressed(KeyEvent event) {}
    public void keyReleased(KeyEvent event) {}

    public boolean wantsKey(KeyEvent event)
    {
      return false;
    }

		public ArcAcceptanceFacet getPossibleArcAcceptance()
		{
			return null; // this is not an arc creator
		}
	}

//////////////////////////////////////////////////////////////////
/// state machine ////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////


	private boolean nodeAcceptsContainer(ContainerFacet container, ContainedFacet containable)
	{
		return containable.acceptsContainer(container);
	}

  private void enter_CREATE_NODE_STATE(ZMouseEvent e)
  {
    // draw graphics on screen, and calculate possible container information
    drawCrossHairsAndPossibleContainerInformation(e);
    removeCreationObject();
    
    // if we have an intocontainer, and accepting is null, don't proceed
    if (nodeAcceptsContainer)
    {
	    // make the figure
	    FigureReference reference = diagram.makeNewFigureReference();

	    PersistentProperties properties = new PersistentProperties();
	    // pass the properties from the indicated factory to the one retrieved via the registry
	    factory.initialiseExtraProperties(properties);
	    
	    String name = factory.getFigureName();
	    coordinator.startTransaction("created " + name, "removed " + name);
	    if (acceptingContainer == null)
	    	NodeCreateFigureTransaction.create(diagram, null, reference, null, factory, creationPoint, properties, null);
	    else
	    	NodeCreateFigureTransaction.create(diagram, null, reference, acceptingContainer.getFigureFacet().getFigureReference(), factory, creationPoint, properties, null);

    	if (acceptingResizings != null && acceptingContainer != null)
			{
		    // we need to make a composite command with:
		    // 1) the create figure transaction
		    // 2) the container resizing transaction
		    // 3) the containment transaction
	      acceptingResizings.end();
	      ContainerAddTransaction.add(acceptingContainer, new FigureReference[]{reference});
			}
		
      coordinator.commitTransaction();
      diagramView.getSelection().clearAllSelection();

      // highlight the created figure
      // NOTE: this may not be created if a readonly exception was thrown
  	  diagramView.addFigureToSelectionViaId(reference.getId());
    }

  	enter_STEADY_STATE();
    coordinator.toolFinished(e, false);
  }

  private void enter_STEADY_STATE()
  {
    state = STEADY_STATE;
    creationPoint = null;
    previewable = null;
    diagramView.turnSweepLayerOff();
  }

  private void enter_MOVING_STATE(ZMouseEvent e)
  {
    state = MOVING_STATE;

    // reset vars
    intoContainer = null;
    acceptingContainer = null;
    nodeAcceptsContainer = false;
    acceptingResizings = null;

		previewable = makePreviewableFigure();
    
    // draw the crosshairs, preview for created node, and container info on screen
    drawCrossHairsAndPossibleContainerInformation(e);
  }

  private void drawCrossHairsAndPossibleContainerInformation(ZMouseEvent e)
  {
    // make a simple preview of the intended shape
    UPoint griddedPoint = Grid.roundToGrid(new UPoint(e.getLocalPoint()));
    
    // move the preview with a moving figure mechanism
    MovingFiguresGem movingGem = new MovingFiguresGem(diagram, new UPoint(0, 0));
    MovingFiguresFacet movingFacet = movingGem.getMovingFiguresFacet();
    movingFacet.indicateMovingFigures(Arrays.asList(new FigureFacet[]{previewable}));
    movingFacet.start(previewable);
    movingFacet.move(griddedPoint);
    PreviewFacet preview = movingFacet.getCachedPreview(previewable);
    // now we are moved into place, set the full bounds correctly
    preview.setFullBounds(new UBounds(griddedPoint, previewableFullDimension), false);

    // if we are over a container, then highlight and indicate resizings
    ContainerFacet mouseContainer = getContainerUnderMouse(griddedPoint);

		// see if the nodes object to their new container
		ContainedFacet containable = previewable.getContainedFacet();
	  nodeAcceptsContainer = nodeAcceptsContainer(acceptingContainer, containable);
    if (intoContainer != null && acceptingContainer == null)
    	nodeAcceptsContainer = false;

    if (mouseContainer != intoContainer) // may be null, or may be another container
    {
      // if mouse container is set, we have a potential container to drop this element into
      if (mouseContainer != null && !mouseContainer.getFigureFacet().isSubjectReadOnlyInDiagramContext(false))
      {
        intoContainer = mouseContainer;
				acceptingContainer = null;
				if (intoContainer != null)
					acceptingContainer = intoContainer.getAcceptingSubcontainer(new ContainedFacet[]{containable});
				
		    nodeAcceptsContainer = nodeAcceptsContainer(acceptingContainer, containable);
        if (intoContainer != null && nodeAcceptsContainer && acceptingContainer != null)
        {
	        acceptingResizings = new ResizingFiguresGem(null, diagram).getResizingFiguresFacet();
	        acceptingResizings.markForResizing(acceptingContainer.getFigureFacet());
        }
        else
        	acceptingResizings = null;
      }
      else
      {
        intoContainer = null;
        acceptingContainer = null;
        acceptingResizings = null;
        nodeAcceptsContainer = nodeAcceptsContainer(acceptingContainer, containable);
      }
    }
    
    ZGroup group = new ZGroup();
    if (intoContainer != null)
    {
      if (acceptingResizings != null)
      {
	      acceptingResizings.resizeToAddContainables(new ContainedPreviewFacet[]{preview.getContainedPreviewFacet()}, griddedPoint);
	      group.addChild(acceptingResizings.formView());
      }

      ContainerPreviewFacet intoPreview = intoContainer.getFigureFacet().getSinglePreview(diagram).getContainerPreviewFacet();
      group.addChild(intoPreview.formContainerHighlight(
          intoContainer != null &&
          nodeAcceptsContainer &&
          acceptingResizings != null));
    }
    // get the creation point
		creationPoint = preview.getTopLeftPoint();

		group.addChild(new CrossHair(creationPoint).formView());
    group.addChild(preview.formView());
    
    // form a view which covers the entire set of moving previews
    // -- may be bigger than just the thing moving e.g. in the case of a port with linked text
    // add the focus outline
    ZRectangle focusRect = new ZRectangle(preview.getFullBoundsForContainment());
    focusRect.setFillPaint(ScreenProperties.getPreviewFillColor());
    focusRect.setPenPaint(ScreenProperties.getCrossHairColor());
    focusRect.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
    group.addChild(new ZVisualLeaf(focusRect));

    diagramView.turnSweepLayerOn(group);
  }

  private ContainerFacet getContainerUnderMouse(UPoint point)
  {
    FigureFacet figure = getFigureUnderMouse(point);
    if (figure == null)
    	return null;
    
    ContainerFacet mouseContainer = null;
    ContainerFacet possible = figure.getContainerFacet();
    if (possible != null)
      mouseContainer = possible;

    // if we cannot find a figure under the mouse, or the item we are creating cannot
    // be contained, return null
    if (mouseContainer == null || previewable.getContainedFacet() == null)
      return null;
    
    return SelectionToolGem.getPossibleContainer(
        mouseContainer.getFigureFacet(),
        new ContainedFacet[]{previewable.getContainedFacet()});
  }

  private FigureFacet getFigureUnderMouse(UPoint point)
  {
    // get the figure under the mouse
    ZNode[] nodes = new ZNode[1];
    // don't use the gridded point, as we want the figure directly under the mouse
    return diagramView.getFigureIgnoringManipulators(point, nodes);
  }

  private void reenter_MOVING_STATE(ZMouseEvent e)
  {
    drawCrossHairsAndPossibleContainerInformation(e);
  }

  /**
   * create an object on a temporary diagram 
   */
  private FigureFacet makePreviewableFigure()
  {
    // make a temporary diagram that looks like the one we are going to place the figure on
    // and then make a temporary figure on it, in preview mode
    BasicDiagramGem temporaryDiagramGem = new BasicDiagramGem(diagram.getDiagramReference(), false, null, true);
    DiagramFacet temporaryDiagram = temporaryDiagramGem.getDiagramFacet();
    FigureReference creationReference = temporaryDiagram.makeNewFigureReference();
    factory.createFigure(null, temporaryDiagram, creationReference.getId(), UPoint.ZERO, null);
    
    // get the full bounds when we have resized
    FigureFacet temporaryFigure = temporaryDiagram.retrieveFigure(creationReference.getId());
    previewableFullDimension =
      temporaryFigure.getRecalculatedFullBoundsForDiagramResize(false).getDimension();
    
    // get the temporary figure
    return temporaryFigure;
  }
  
  private void removeCreationObject()
  {
    previewable = null;
    previewableFullDimension = null;
  }  
}