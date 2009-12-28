package com.hopstepjump.idraw.nodefacilities.creation;

import java.awt.*;
import java.awt.event.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.selection.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.linking.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;

public class ZoomToolGem implements Gem
{
  // needed for management of tool
  private boolean active;
  private DiagramViewFacet diagramView;
  private DiagramFacet diagram;
  private ToolCoordinatorFacet coordinator;
  // state for the current arc in construction
  private UPoint startPoint;
  private ZNode mouseNode;
  private UPoint start;
  private UPoint end;
  private double scale = 1;
  private UDimension scaleDimensions;


  private ToolFacet toolFacet = new ToolFacetImpl();
  private ZMouseListenerImpl mouseListener = new ZMouseListenerImpl();

  public ZoomToolGem()
  {
  }
  
  public ToolFacet getToolFacet()
  {
  	return toolFacet;
  }
  
  private class ZMouseListenerImpl implements ZMouseListener, ZMouseMotionListener
  {
	  public void mousePressed(ZMouseEvent e)
	  {
	  	scaleDimensions = new UBounds(diagramView.getCanvas().getBounds()).getDimension();
	  	scale = scaleDimensions.getHeight() / scaleDimensions.getWidth();
	  	start = new UPoint(e.getLocalPoint());
	  	end = start;
	  	if ((e.getModifiers() & MouseEvent.BUTTON2_MASK) != 0)
	  		diagramView.turnSweepLayerOn(makeZoomText("Resetting view to 100%"));
	  }
	
	  private ZNode makeZoomText(String detail)
		{
  		// display the amount that we will scale out by
  		ZText text = new ZText(detail);
  		text.setFont(new Font("Arial", Font.PLAIN, 24));
  		text.setTranslation(20, 20);
  		text.setPenColor(Color.DARK_GRAY);
  		return new ZVisualLeaf(text);
		}

		public void mouseDragged(ZMouseEvent e)
	  {
	  	// if this is a left button, zoom in
			UPoint point = fixToScale(new UPoint(e.getLocalPoint()));
			
	  	if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0)
	  	{
	  		end = point;
	  		ZGroup group = new ZGroup(SelectionToolGem.constructSweepBox(new UBounds(start, end)));
	  		double zoom = Math.abs(start.getX() - end.getX()) / scaleDimensions.getWidth();
	  		group.addChild(makeZoomText("Zooming in: " + (int) (zoom * 100)  + "%"));
	  		diagramView.turnSweepLayerOn(group);
	  	}
	  	else
	  	if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0)
	  	{
//	  		double zoom = Math.abs(start.getX() - end.getX()) / scaleDimensions.getWidth();
	  		diagramView.turnSweepLayerOn(makeZoomText("Zooming out: " + 10 + "%"));	  		
	  	}
	  }
	
	  private UPoint fixToScale(UPoint point)
		{
	  	// use the x axis
	  	double x = point.getX();
	  	double y = x * scale;
	  	return new UPoint(x, y);
		}

		public void mouseReleased(ZMouseEvent e)
	  {
	  	diagramView.turnSweepLayerOff();
	  	coordinator.toolFinished(e, false);
	  	
	  	// this is either a zoom in, a zoom out or a 100% restore view
	  	if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0)
	  	{
	  		diagramView.setScalingBounds(new UBounds(start, end));
	  	}
	  	else
	    if ((e.getModifiers() & MouseEvent.BUTTON3_MASK )!= 0)
	    {
	    	System.out.println("$$ zoom out");
	    }
	    else
	    	diagramView.setOriginalScaling();
	  }
	
	  public void mouseMoved(ZMouseEvent e)
	  {
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
	      mouseNode = newMouseNode;
	      active = true;
	      coordinator = newCoordinator;
	      BasicNodeGem basicGem = new BasicNodeGem(null, diagram, diagram.makeNewFigureReference().getId(), new UPoint(0,0), true, false);
				BasicAnchorGem basicLinkingGem = new BasicAnchorGem();
				basicGem.connectBasicNodeAppearanceFacet(basicLinkingGem.getBasicNodeAppearanceFacet());
				basicLinkingGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
	      mouseNode.addMouseListener(mouseListener);
	      mouseNode.addMouseMotionListener(mouseListener);
	      diagramView.turnSelectionLayerOff();
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
			return null;
		}
  }

////////////////////////////////////////////////////////////////////////////////
///// start of state machine calls
////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////
///// end of state machine calls
////////////////////////////////////////////////////////////////////////////////
}