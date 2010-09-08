package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.awt.*;
import java.awt.Component;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

public abstract class FieldPopupManipulator implements Gem
{
	private static final ImageIcon ICON = IconLoader.loadIcon("implementation.png");

	protected ToolCoordinatorFacet coordinator;
	protected DiagramViewFacet diagramView;
	protected FigureFacet figure;
	protected boolean readOnly;
	private ManipulatorFacetImpl manipulatorFacet = new ManipulatorFacetImpl();
	private ManipulatorListenerFacet manipListener;
	
	public abstract void setUpPopup();
	public abstract void removePopup();
	public abstract void setTextAndFinish();
	
	public FieldPopupManipulator(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, FigureFacet figure)
	{
		this.coordinator = coordinator;
		this.diagramView = diagramView;
		this.figure = figure;
		readOnly = GlobalSubjectRepository.repository.isReadOnly(getElement());
	}

	public ManipulatorFacet getManipulatorFacet()
	{
		return manipulatorFacet;
	}

  protected NamedElement getElement()
  {
  	return (NamedElement) figure.getSubject();
  }
  
	private class ManipulatorFacetImpl implements ManipulatorFacet
	{
		private ZGroup diagramLayer;
		private ZGroup group;
				
		/** the resizing manipulator for nodes is not direct.  The item must be selected first */
	  public int getType()
	  {
	    return TYPE2;
	  }
	
	  public void addToView(ZGroup diagramLayer, ZCanvas canvas)
	  {
	  	this.diagramLayer = diagramLayer;
	  	
	  	UPoint pt = figure.getFullBounds().getPoint().subtract(new UDimension(16, 16));
	  	group = new ZGroup();
	  	
	  	ZTransformGroup transform = new ZTransformGroup();
	  	ZImage img = new ZImage(ICON.getImage());
	  	ZVisualLeaf ileaf = new ZVisualLeaf(img);
	  	transform.setTranslation(pt.getX(), pt.getY());
	  	transform.addChild(ileaf);
	  	group.addChild(transform);
	  	
  		// add a clear cover to pick up mouse clicks
  		ZRectangle rect = new ZRectangle(new UBounds(pt, new UDimension(16, 16)));
  		rect.setPenPaint(null);
  		rect.setFillPaint(new Color(0, 0, 0, 0));
	    ZVisualLeaf leaf = new ZVisualLeaf(rect);
	    leaf.putClientProperty("manipulator", this);
  		group.addChild(leaf);
	  	
	    group.setChildrenFindable(false);
	    group.setChildrenPickable(true);
	    
	  	diagramLayer.addChild(group);
	  }
	  
	  public void cleanUp()
	  {
	  	diagramLayer.removeChild(group);
	  	removePopup();
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
	  	manipListener = listener;
	  	setUpPopup();
	  }

		/**invoked on button release entry*/
	  public void enterViaButtonRelease(ManipulatorListenerFacet listener, ZNode source, UPoint point, int modifiers)
	  {
	  }
	
	  /** invoked when a key has been pressed */
	  public void keyPressed(char keyPressed)
	  {
	  }
	
	  /**Invoked when a mouse button has been pressed on this figure */
	  public void mousePressed(FigureFacet over, UPoint point, ZMouseEvent event)
	  {
	  	if (readOnly)
	  		finishManipulator();
	  	else
	  		setTextAndFinish();
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

    public void setLayoutOnly()
    {
    }
	}
	
  protected UPoint findInsidePoint(DiagramViewFacet diagramView, JPanel panel)
	{
  	ZCanvas canvas = diagramView.getCanvas();
  	Component top = getTop(canvas);
  	UBounds topBounds = new UBounds(top.getBounds());
  	
  	UPoint pt = figure.getFullBounds().getTopLeftPoint();
  	pt = pt.subtract(diagramView.getCurrentPan());
  	Point offset = diagramView.getCanvas().getLocationOnScreen();
  	pt = pt.add(new UDimension(5 + offset.x, offset.y));
  	ZSwing swing = new ZSwing(canvas, panel);
  	UBounds bounds = new UBounds(pt, new UBounds(swing.getBounds()).getDimension());
  	
  	// adjust for right and bottom only
  	int bringLeft = bounds.getTopRightPoint().getIntX() - topBounds.getTopRightPoint().getIntX() +20;
  	
  	return pt.subtract(
  			new UDimension(bringLeft > 0 ? bringLeft : 0, 0));
	}
  
	private Component getTop(Component comp)
  {
  	for(;;)
  	{
  		if (comp.getParent() == null)
  			return comp;
  		comp = comp.getParent();
  	}
  }
	
	protected void finishManipulator()
	{
		removePopup();
		manipListener.haveFinished();
	}
}
