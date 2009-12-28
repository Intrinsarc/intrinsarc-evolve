package com.hopstepjump.idraw.diagramsupport;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;


/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class BasicDiagramScrollerGem implements Gem
{
	private JScrollBar xScrollBar;
	private JScrollBar yScrollBar;
	private JComponent overviewArea;
	private BasicDiagramViewFacet basicDiagramViewFacet;
	private DiagramViewFacet littleView;
  private UPoint lastPoint;
  private double scale;
  private Popup popup;
  private BasicDiagramScrollerFacet scrollerFacet = new BasicDiagramScrollerFacetImpl();
  private Color backgroundColor;
  
  private class BasicDiagramScrollerFacetImpl implements BasicDiagramScrollerFacet
  {
		public void adjustScroll(UDimension objectDimensions, UDimension canvasDimensions, UBounds viewPort, UDimension maxDimensions)
		{
			// if the	objects are greater than the canvas, we need a scrollbar
			boolean needScrollers = false;
	
			if (objectDimensions.getWidth() > canvasDimensions.getWidth() ||
					objectDimensions.getHeight() > canvasDimensions.getHeight())
				needScrollers = true;
	
			// turn the x scroller on or off
			if (needScrollers != xScrollBar.isVisible())
			{
				xScrollBar.setVisible(needScrollers);
				yScrollBar.setVisible(needScrollers);
				overviewArea.setVisible(needScrollers);
			}
				
			// adjust the position, extent and range of the x scroller
			if (needScrollers)
				xScrollBar.setValues((int) viewPort.getTopLeftPoint().getX(), (int) viewPort.getDimension().getWidth(),
														 0, (int) maxDimensions.getWidth());
	
			// adjust the position, extent and range of the y scroller
			if (needScrollers)
				yScrollBar.setValues((int) viewPort.getTopLeftPoint().getY(), (int) viewPort.getDimension().getHeight(),
														 0, (int) maxDimensions.getHeight());
		}
  }
	
	public BasicDiagramScrollerGem(JScrollBar xScrollBar, JScrollBar yScrollBar, JComponent overviewArea, Color backgroundColor)
	{
		this.xScrollBar = xScrollBar;
		this.yScrollBar = yScrollBar;
		this.overviewArea = overviewArea;
		this.backgroundColor = backgroundColor;
	}
	
	public void connectBasicDiagramViewFacet(BasicDiagramViewFacet basicDiagramViewFacet)
	{
		this.basicDiagramViewFacet = basicDiagramViewFacet;
		addListenersToScrollBars();
	}
	
	public BasicDiagramScrollerFacet getBasicDiagramScrollerFacet()
	{
		return scrollerFacet;
	}
	
	private void addListenersToScrollBars()
	{
    // add the listeners to the scroll bars
    xScrollBar.addAdjustmentListener(new AdjustmentListener()
    {
    	public void adjustmentValueChanged(AdjustmentEvent e)
    	{
    		double xPos = e.getValue();
    		basicDiagramViewFacet.getDiagramViewFacet().pan(basicDiagramViewFacet.getViewPort().getTopLeftPoint().getX() - xPos, 0);
    	}
    });
    yScrollBar.addAdjustmentListener(new AdjustmentListener()
    {
    	public void adjustmentValueChanged(AdjustmentEvent e)
    	{
    		double yPos = e.getValue();
    		basicDiagramViewFacet.getDiagramViewFacet().pan(0, basicDiagramViewFacet.getViewPort().getTopLeftPoint().getY() - yPos);
    	}
    });
    
    overviewArea.addMouseListener(new MouseAdapter()
    {
    	static final double MAX_SIZE_IN_ANY_DIMENSION = 220;
    	
    	public void mousePressed(MouseEvent e)
    	{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
	    		lastPoint = new UPoint(e.getPoint());
	    		
	    		UDimension viewDim = basicDiagramViewFacet.getDimensionsOfView();
	    		if (viewDim.getWidth() > viewDim.getHeight())
	    		{
	    			// scale so that x is 200, and y adjusts
	    			scale = MAX_SIZE_IN_ANY_DIMENSION / viewDim.getWidth();
	    		}
	    		else
	    			scale = MAX_SIZE_IN_ANY_DIMENSION / viewDim.getHeight();
	
	    		littleView = makeLittleDiagramView(scale);
	    		ZCanvas littleCanvas = littleView.getCanvas();
	    		UBounds viewPort = basicDiagramViewFacet.getViewPort();
	    		
	    		littleView.turnSweepLayerOn(
	    		    makeLittleRectangle(viewPort));

	    		AbstractBorder border = new LineBorder(Color.black);
	    		littleCanvas.setBorder(border);

					double xSize = viewDim.getWidth() * scale;
					double ySize = viewDim.getHeight() * scale;
	    		littleCanvas.setPreferredSize(new Dimension((int) xSize, (int) ySize));
	    		
	    		// work out how much to offset by to place the rectangle at the cursor
	    		// can't use this, as we don't seem to have cursor warping in java
	//    		double xOffset = viewPort.getMiddlePoint().getX() * scale / 2;
	//    		double yOffset = viewPort.getMiddlePoint().getY() * scale / 2;
	    		
	    		// if the popup is set, then remove it
	    		if (popup != null)
	    			destroyPopup();

					overviewArea.requestFocus();
	    		popup = PopupFactory.getSharedInstance().getPopup(overviewArea, littleCanvas, (int) (overviewArea.getLocationOnScreen().getX() - xSize + 10), (int) (overviewArea.getLocationOnScreen().getY() - ySize + 10));
	    		popup.show();
				}
    	}
    	
			private void destroyPopup()
			{
    		popup.hide();
    		basicDiagramViewFacet.requestFocus();
    		popup = null;
    		littleView = null;
			}
			
    	public void mouseReleased(MouseEvent e)
    	{
    		if (e.getButton() == MouseEvent.BUTTON1)
					destroyPopup();
    	}
    });
    overviewArea.addMouseMotionListener(new MouseMotionListener()
    {	
    	public void mouseDragged(MouseEvent e)
    	{
    		basicDiagramViewFacet.getDiagramViewFacet().pan((lastPoint.getX() - e.getPoint().getX()) / scale, (lastPoint.getY() - e.getPoint().getY()) / scale);
    		lastPoint = new UPoint(e.getPoint());
    		littleView.turnSweepLayerOn(makeLittleRectangle(
    		    basicDiagramViewFacet.getViewPort()));
    	}
    	
    	public void mouseMoved(MouseEvent e)
    	{
    	}
    });
	}

	/**
   * @param viewPort
   * @return
   */
  private ZNode makeLittleRectangle(UBounds viewPort)
  {
		ZRectangle rect = new ZRectangle(viewPort);
		rect.setPenPaint(Color.BLACK);
		rect.setPenWidth(2);
		rect.setFillPaint(null);
		
		ZGroup group = new ZGroup();
		group.addChild(new ZVisualLeaf(rect));
		
		return group;
  }

  private DiagramViewFacet makeLittleDiagramView(double scaling)
	{
    // diagram view
    BasicDiagramViewGem littleDiagramView = new BasicDiagramViewGem(basicDiagramViewFacet.getDiagramViewFacet().getDiagram(), null, new ZCanvas(),
    																																new UDimension(scaling, scaling), backgroundColor, false);    
    return littleDiagramView.getDiagramViewFacet();
	}
}
