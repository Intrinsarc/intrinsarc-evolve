package com.hopstepjump.idraw.figurefacilities.selection;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.figurefacilities.containment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.swing.enhanced.*;
import com.hopstepjump.swing.palette.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

/**
 * the selection tool.  works according to the finite state machine described in the uml project.
 * @author andrew
 *
 */
public final class SelectionToolGem implements Gem
{
	private static JMenuItem INVOKE_ITEM = new JMenuItem("Invoke tool");
  private static final int STEADY_STATE                          = 1;
  private static final int MOVING_SELECTION_SUBSTATE             = 2;
  private static final int POSSIBLY_MANIPULATING_SUBSTATE        = 3;
  private static final int ADJUSTING_MANIPULATOR_SUBSTATE        = 4;
  private static final int SELECTING_AREA_SUBSTATE               = 5;
  private static final int PANNING_DIAGRAM_SUBSTATE              = 6;
  private int state = STEADY_STATE;

  private static final int NOT_DRAGGING_SUBSTATE                 = 1;
  private static final int DRAGGING_OVER_FROM_CONTAINER_SUBSTATE = 2;
  private static final int DRAGGING_OVER_NEW_CONTAINER_SUBSTATE  = 3;
  private int draggingState = NOT_DRAGGING_SUBSTATE;
  // vars to cope with container resizing when moving selection from one container to another
  private ContainerFacet fromContainer;
  private ContainerPreviewFacet fromContainerPreview;
  private ResizingFiguresFacet fromContainerResizings;
  private ContainerFacet newContainer;
  private boolean acceptContainer;
  private ContainerFacet acceptingContainer;
  private ResizingFiguresFacet acceptingContainerResizings;
  private GlobalPopupMenuFacet globalPopupMenuFacet;
  private ToolClassificationFacet toolClassifier;
  private boolean mouseIsOutsideCanvas = true;
  private UPoint currentMousePoint = new UPoint(0, 0); // this is an awt point, not a jazz one!
  private boolean panned;

  // move commands will only be generated for moves greater than the following distance
  private static final int SMALLEST_ACCEPTABLE_MOVE_DISTANCE = 2;

  private ZNode mouseNode;
  private boolean active;
  private Point2D sweepStartPoint;
  private UPoint moveStartPoint;
  private UPoint panStartPoint;
  private UBounds selectionBounds;

  private ZNode areaSweepNode;
  private ZRectangle areaSweepBox;
  private DiagramViewFacet diagramView;
  private DiagramFacet diagram;
  private SelectionFacet selection;
  private ZNode movingNode;
  private ZNode manipulatorSource;
  private FigureFacet possibleSelectableToMove;
  private UPoint manipulatorStart;
  private ToolCoordinatorFacet coordinator;
  private boolean movedEnough;
  private MovingFiguresFacet movingFigures;
  private ManipulatorFacet manipulator;
  private ZMouseEvent transmitManipulatorEvent;
  /** a willing container is one which is willing to let any selection event in its container bounds to be considered as a sweep */
  private ContainerFacet willingContainer;
  private SelectionToolFacet toolFacet = new ToolFacetImpl();
  private ZMouseListenerImpl mouseListener = new ZMouseListenerImpl();
  private ManipulatorListenerFacet manipulatorListener = new ManipulatorListenerFacetImpl();
  private SelectionListenerFacet selectionListener = new SelectionListenerFacetImpl();
  private boolean layoutOnly;
  private UPoint keyMovingPoint;
  private boolean diagramReadOnlyMode;
  
  static
  {
  	GlobalPreferences.registerKeyAction("Palette", INVOKE_ITEM, "SPACE", "Invoke a tool from the diagram palette");
  }

  /**
   * constructor
   * @param layoutOnly are we only allowing layout to happen.  i.e. no destructive container moves etc.
   */
  public SelectionToolGem(boolean layoutOnly)
  {
    this.layoutOnly = layoutOnly;
  }
  
  public void connectGlobalPopupMenuFacet(GlobalPopupMenuFacet globalPopupMenuFacet)
  {
  	this.globalPopupMenuFacet = globalPopupMenuFacet;
  }
  
  public void connectToolClassificationFacet(ToolClassificationFacet toolClassifier)
  {
  	this.toolClassifier = toolClassifier;
  }

	public SelectionToolFacet getToolFacet()
	{
		return toolFacet;
	}
  
	private class SelectionListenerFacetImpl implements SelectionListenerFacet
	{
		public void haveSingleSelection()
		{
			// if we have a favoured manipulator with a type of 0, then enter immediately
			Manipulators manips = selection.getFavouredManipulatorsOfSingleSelection();
			ManipulatorFacet favouredKeyFocus = manips.getKeyFocus();
			if (favouredKeyFocus != null)
			{
				if (favouredKeyFocus.getType() == ManipulatorFacet.TYPE0)
					enter_IMMEDIATE_MANIPULATION_SUBSTATE(favouredKeyFocus);
			}
		}
	}

  private class ManipulatorListenerFacetImpl implements ManipulatorListenerFacet
  {
	  /**
	   * this is called by the manipulator when it has finished
	   */
	  public void haveFinished(Command command)
	  {
	    possibly_exit_ADJUSTING_MANIPULATOR_SUBSTATE(command, transmitManipulatorEvent);
	  }
  }

	private class ZMouseListenerImpl implements ZMouseListener, ZMouseMotionListener
	{
	  public void mouseMoved(ZMouseEvent e)
	  {
	    UPoint localPoint =  new UPoint(e.getLocalPoint());
	
	    switch(state)
	    {
	      case ADJUSTING_MANIPULATOR_SUBSTATE:
	        reenter_ADJUSTING_MANIPULATOR_SUBSTATE_via_mousemoved(e, localPoint);
	        break;
	
	      case MOVING_SELECTION_SUBSTATE:
	      case POSSIBLY_MANIPULATING_SUBSTATE:
	      case SELECTING_AREA_SUBSTATE:
	      case PANNING_DIAGRAM_SUBSTATE:
	      case STEADY_STATE:
	        // do nothing
	        break;
	    }
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
	    UPoint localPoint =  new UPoint(e.getLocalPoint());
	
	    switch(state)
	    {
	      case MOVING_SELECTION_SUBSTATE:
	        reenter_MOVING_SELECTION_SUBSTATE(e, localPoint);
	        break;
	      case POSSIBLY_MANIPULATING_SUBSTATE:
	        exit_POSSIBLY_MANIPULATING_SUBSTATE(e, localPoint, false /* released without dragging */);
	        break;
	      case ADJUSTING_MANIPULATOR_SUBSTATE:
	        reenter_ADJUSTING_MANIPULATOR_SUBSTATE_via_mousedragged(e, localPoint);
	        break;
	      case SELECTING_AREA_SUBSTATE:
	        reenter_SELECTING_AREA_SUBSTATE(e, localPoint);
	        break;
	      case PANNING_DIAGRAM_SUBSTATE:
	        reenter_PANNING_DIAGRAM_SUBSTATE(e, localPoint);
	        break;
	
	      case STEADY_STATE:
	        // do nothing
	        break;
	    }
	  }
	
	  /**Invoked when a mouse button has been released on a component.*/
	  public void mouseReleased(ZMouseEvent e)
	  {
	    UPoint localPoint =  new UPoint(e.getLocalPoint());
	
	    switch(state)
	    {
	      case MOVING_SELECTION_SUBSTATE:
	        exit_MOVING_SELECTION_SUBSTATE(e, localPoint);
	        break;
	      case ADJUSTING_MANIPULATOR_SUBSTATE:
	        reenter_ADJUSTING_MANIPULATOR_SUBSTATE_via_mousereleased(e, localPoint);
	        break;
	      case SELECTING_AREA_SUBSTATE:
	        exit_SELECTING_AREA_SUBSTATE(e, localPoint);
	        break;
	      case PANNING_DIAGRAM_SUBSTATE:
	        exit_PANNING_DIAGRAM_SUBSTATE(e, localPoint);
	        break;
	      case POSSIBLY_MANIPULATING_SUBSTATE:
	        exit_POSSIBLY_MANIPULATING_SUBSTATE(e, localPoint, true);
	        break;
	
	      case STEADY_STATE:
	        // do nothing
	        break;
	    }
	  }
	
	  /**Invoked when a mouse button has been pressed on a component.*/
	  public void mousePressed(ZMouseEvent e)
	  {
	    UPoint localPoint = new UPoint(e.getLocalPoint());
	    
	    switch(state)
	    {
	      case STEADY_STATE:
	        exit_STEADY_STATE(e, localPoint);
	        break;
	
	      case ADJUSTING_MANIPULATOR_SUBSTATE:
	        reenter_ADJUSTING_MANIPULATOR_SUBSTATE_via_mousepressed(e, localPoint);
	        break;
	
	      case MOVING_SELECTION_SUBSTATE:
	      case POSSIBLY_MANIPULATING_SUBSTATE:
	      case SELECTING_AREA_SUBSTATE:
	      case PANNING_DIAGRAM_SUBSTATE:
	        // do nothing
	        break;
	    }
	  }
	
	  public void mouseEntered(MouseEvent e)
	  {
	  	mouseIsOutsideCanvas = false;
	  }
	
	  public void mouseExited(final MouseEvent e)
	  {
	  	// if we are in a dragging state and we have room, then we should scroll
	    switch(state)
	    {
	      case STEADY_STATE:
	      case PANNING_DIAGRAM_SUBSTATE:
	        break;
	
	      case ADJUSTING_MANIPULATOR_SUBSTATE:
	      case MOVING_SELECTION_SUBSTATE:
	      case POSSIBLY_MANIPULATING_SUBSTATE:
	      case SELECTING_AREA_SUBSTATE:
	      	if (!mouseIsOutsideCanvas)
	      	{
		      	mouseIsOutsideCanvas = true;
		      	new Thread(new Runnable()
		      	{
		      		public void run()
		      		{
				      	try
				      	{
				      		while (mouseIsOutsideCanvas)
				      		{
						      	Thread.sleep(20);
						      	
						      	// see if the mouse is above or below the canvas
						      	double xIncrement = 0;
						      	double yIncrement = 0;
						      	UBounds canvasBounds = new UBounds(diagramView.getCanvas().getBounds());
						      	if (currentMousePoint.getY() < canvasBounds.getTopLeftPoint().getY())
						      		yIncrement = 4;
						      	if (currentMousePoint.getY() > canvasBounds.getBottomRightPoint().getY())
						      		yIncrement = -4;
						      	if (currentMousePoint.getX() < canvasBounds.getBottomLeftPoint().getX())
						      		xIncrement = 4;
						      	if (currentMousePoint.getX() > canvasBounds.getBottomRightPoint().getX())
						      		xIncrement = -4;
		
						      	diagramView.pan(xIncrement, yIncrement);
										MouseEvent me = new MouseEvent((Component) e.getSource(), e.getID(), e.getWhen(), e.getModifiers(), (int) currentMousePoint.getX(), (int) currentMousePoint.getY(), e.getClickCount(), e.isPopupTrigger());
						      	diagramView.getCanvas().dispatchEvent(me);
//						      	diagramView.finishedModifications();
				      		}
				      	}
				      	catch (InterruptedException ex)
				      	{
				      	}
		      		}
		      	}).start();
	      	}
		      break;
	    }
	  }
	
	  public void mouseClicked(ZMouseEvent e){}
	  public void mouseEntered(ZMouseEvent e){}
	  public void mouseExited(ZMouseEvent e){}
	}

	private class ToolFacetImpl implements SelectionToolFacet
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
	      diagramReadOnlyMode = diagram.isReadOnly();
	      selection = diagramView.getSelection();
	      mouseNode = newMouseNode;
	      active = true;
	      coordinator = newCoordinator;
	
	      mouseNode.addMouseListener(mouseListener);
	      mouseNode.addMouseMotionListener(mouseListener);
	     	selection.addSelectionListener(selectionListener);
	
	
	/*
	 // autoscrolling logic...
	      diagramView.getCanvas().addMouseListener(new MouseAdapter()
	      {
	      	public void mouseEntered(MouseEvent e)
	      	{
	      		SelectionTool.this.mouseEntered(e);
	      	};
	      	
	      	public void mouseExited(MouseEvent e)
	      	{
	      		SelectionTool.this.mouseExited(e);
	      	};
	      });
	      diagramView.getCanvas().addMouseMotionListener(new MouseMotionListener()
	      {
	      	public void mouseMoved(MouseEvent e)
	      	{
	      		currentMousePoint = new UPoint(e.getX(), e.getY());
	      	}
	      	public void mouseDragged(MouseEvent e)
	      	{
	      		currentMousePoint = new UPoint(e.getX(), e.getY());
	      	}
	      });*/
	      enter_STEADY_STATE();
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
	      selection.removeSelectionListener(selectionListener);
	      enter_STEADY_STATE();
	    }
	  }
	  
	  public boolean wantsKey(KeyEvent event)
	  {
	    // if nothing is selected, we don't care
	    return selection.getFirstSelectedFigure() != null;
	  }

    public void keyPressed(KeyEvent event)
    {
	    // removed "pressed " before comparing
    	KeyStroke stroke = KeyStroke.getKeyStrokeForEvent(event);
      if (stroke.equals(INVOKE_ITEM.getAccelerator()))
      {
      	// get the element under the mouse
      	final UPoint pt = diagramView.getCursorPoint();
      	FigureFacet under = getFigureUnderMouse(pt);
      	boolean readOnly = under == null ? false : under.isSubjectReadOnlyInDiagramContext(false);
      	ToolFigureClassification screen = under != null ? under.getToolClassification(pt, diagramView, coordinator) : null;
      	if (under == null)
      	{
      		screen = new ToolFigureClassification("top", null);
      		readOnly = diagram.isReadOnly();
      	}

      	if (screen != null)
      	{
      		final ZCanvas canvas = diagramView.getCanvas();
	      	JPopupMenu menu = new JPopupMenu();
	      	// add the elements
	      	String category = null;
	      	Set<String> already = new HashSet<String>();
	      	for (final ToolClassification tool : toolClassifier.getToolClassifications())
	      	{
	      		if (screen != null && screen.matches(tool))
	      		{
	      			// don't include if we have this already
	      			final IRichPaletteEntry rich = (IRichPaletteEntry) tool.getUserObject();
	      			if (already.contains(rich.getName()))
	      					continue;
	      			already.add(rich.getName());
	      			
	      			// do we add a separator?
	      			if (category != null && !category.equals(tool.getCategory()))
	      				menu.addSeparator();
	    				category = tool.getCategory();
	      			
	      			boolean contextReadOnly = readOnly;
	      			if (!tool.isNode() && under != null)
	      			{
	      				// ask the arc facet if we accet the item as the start
	      				AnchorFacet anchor = under.getAnchorFacet();
	      				if (anchor == null)
	      					contextReadOnly = true;
	      				else
	      				{
	      					// yuck!
	      					ArcAcceptanceFacet acceptance =
	      						((ToolFacet) ((IRichPaletteEntry) tool.getUserObject()).getUserObject()).getPossibleArcAcceptance();
	      					if (acceptance == null)
	      						contextReadOnly = true;
	      					else
	      					contextReadOnly = !acceptance.acceptsAnchors(anchor, null);
	      				}
	      			}
	      			final JMenuItem item = new JMenuItem((contextReadOnly ? "<html><body text='gray'>" : "") + rich.getName(), rich.getIcon());
	      			menu.add(item);
	      			item.setEnabled(!contextReadOnly);
	      			item.addActionListener(new ActionListener()
	      			{
								public void actionPerformed(ActionEvent e)
								{
									rich.select();
									
									// is this multi-tool?
									if ((e.getModifiers() & ActionEvent.SHIFT_MASK) != 0)
										toolClassifier.setMultiTool(true);

									// click on and off to simulate a mouse press
									int buttonMask = MouseEvent.BUTTON1_MASK;
									
									// create a new point to reflect the possible viewport of the view
									UPoint adjusted = pt.subtract(diagramView.getCurrentPan());
									
							    sendMouseEvent(adjusted, MouseEvent.MOUSE_PRESSED, buttonMask);
							    // only release and click if this is an actual node
							    if (tool.isNode())
							    {
								    sendMouseEvent(adjusted, MouseEvent.MOUSE_RELEASED, buttonMask);
								    sendMouseEvent(adjusted, MouseEvent.MOUSE_CLICKED, buttonMask);
							    }
								}
	      			});
	      		}
	      	}
	      	
	      	// add anything extra that the tool figure holds
	      	if (screen.getMenuItems() != null)
	      	{
	      		Utilities.addSeparator(menu);
	      		for (JMenuItem item : screen.getMenuItems())
	      		{
	      			menu.add(item);
	      			item.setEnabled(item.isEnabled() && !readOnly);
	      		}
	      	}
	      	
	      	// work out the height of the menu
	      	ZSwing s = new ZSwing(diagramView.getCanvas(), menu);
	      	double height = s.getBounds().height;
	      	
	      	if (menu.getComponentCount() != 0)
	      	{
	      		ZGroup group = new ZGroup();
	          group.addChild(new CrossHair(pt).formView());
	          diagramView.turnSweepLayerOn(group);
	          UDimension pan = diagramView.getCurrentPan();
	      		menu.show(canvas, pt.getIntX() + 5 - pan.getIntWidth(), pt.getIntY() - (int)(height / 2) - pan.getIntHeight());
	      	}
      	}
      	return;
      }
      
      // not for readonly
      if (diagramReadOnlyMode)
        return;      

      // if this is an arrow key, then drag
      int code = event.getKeyCode();          
      if (code  == KeyEvent.VK_RIGHT || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN)
      {
        double x = Grid.getGridSpace().getWidth();
        double y = Grid.getGridSpace().getHeight();           
        
        UDimension offset;
        switch (code)
        {
          case KeyEvent.VK_RIGHT:
            offset = new UDimension(x, 0);
            break;
          case KeyEvent.VK_LEFT:
            offset = new UDimension(-x, 0);
            break;
          case KeyEvent.VK_UP:
            offset = new UDimension(0, -y);
            break;
          case KeyEvent.VK_DOWN:
            default:
            offset = new UDimension(0, y);
            break;
        }
        
        // get the selected figure
        FigureFacet first = diagramView.getSelection().getFirstSelectedFigure();
        if (first != null)
        {
          if (state != MOVING_SELECTION_SUBSTATE)
          {
            UPoint start = first.getFullBounds().getPoint();
            enter_MOVING_SELECTION_SUBSTATE(null, start, first);
            keyMovingPoint = start;
          }
          keyMovingPoint = keyMovingPoint.add(offset);
          reenter_MOVING_SELECTION_SUBSTATE(null, keyMovingPoint);
        }
      }
    }
    
    private void sendMouseEvent(UPoint point, int event, int buttonMask)
    {
    	ZCanvas canvas = diagramView.getCanvas();
      canvas.dispatchEvent(new MouseEvent(
          canvas,
          event,
          0,
          buttonMask,
          point.getIntX(),
          point.getIntY(),
          1,
          false));
    }
    
    public void keyReleased(KeyEvent event)
    {
      // not for readonly
      if (diagramReadOnlyMode)
        return;
      
      int code = event.getKeyCode();
      if (state == MOVING_SELECTION_SUBSTATE)
        if (code == KeyEvent.VK_SHIFT || code  == KeyEvent.VK_RIGHT |
            code == KeyEvent.VK_LEFT || code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN)
        {
          // make sure shift is off
          if ((event.getModifiers() & Event.SHIFT_MASK) == 0)
            exit_MOVING_SELECTION_SUBSTATE(null, keyMovingPoint);
        }
    }
    
	  /**Invoked when a key is pressed */
	  public void keyTyped(KeyEvent event)
	  {
      // not for readonly
      if (diagramReadOnlyMode)
        return;

	    char keyPress = event.getKeyChar();
    	KeyStroke stroke = KeyStroke.getKeyStrokeForEvent(event);
      if (!stroke.equals(INVOKE_ITEM.getAccelerator()))
		    switch(state)
		    {
		      case STEADY_STATE:
		        {
	  	        // get the manipulators from a possible single selection
	  	        // if so, then enter the manipulator via a key press
	  	        Manipulators manips = selection.getFavouredManipulatorsOfSingleSelection();
	  	        if (manips != null && manips.getKeyFocus() != null &&
	  	            !selection.getSingleSelection().isSubjectReadOnlyInDiagramContext(false))
	  	        {
	  	          enter_KEY_ENTRY_SUBSTATE(manips.getKeyFocus(), keyPress);
	  	        }
		        }
		        break;
		
		      case MOVING_SELECTION_SUBSTATE:
		        break;
		      case ADJUSTING_MANIPULATOR_SUBSTATE:
		        break;
		      case SELECTING_AREA_SUBSTATE:
		        break;
		      case PANNING_DIAGRAM_SUBSTATE:
		        break;
		      case POSSIBLY_MANIPULATING_SUBSTATE:
		        break;
		    }
	  }

    public void setReadOnlyMode(boolean newReadOnlyMode)
    {
      diagramReadOnlyMode = newReadOnlyMode;
    }

		public ArcAcceptanceFacet getPossibleArcAcceptance()
		{
			return null;
		}
	}


//////////////////////////////////////////////////////////////////
/// state machine ////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////


  private FigureFacet getFigureUnderMouse(UPoint point)
  {
    FigureFacet figure = getRealFigureUnderMouse(point);
    if (figure == null)
	  	return null;
	  return figure.getActualFigureForSelection();
  }

  private FigureFacet getRealFigureUnderMouse(UPoint point)
  {
    // get the figure under the mouse
    ZNode[] nodes = new ZNode[1];
    return diagramView.getFigureIgnoringManipulators(point, nodes);
  }

  private ResizingFiguresFacet makeResizingsForContainer(ContainerFacet overContainer, PreviewCacheFacet nextInChain)
  {
    ResizingFiguresFacet resizings = new ResizingFiguresGem(nextInChain, diagram).getResizingFiguresFacet();
    resizings.markForResizing(overContainer.getFigureFacet());
    return resizings;
  }

  private ContainedFacet[] getContained(MovingFiguresFacet movingFigures)
  {
    // we are adding the nodes to this container
    Iterator iter = movingFigures.getSelectedPreviewFigures();
    List<FigureFacet> containable = new ArrayList<FigureFacet>();
    while (iter.hasNext())
    {
      PreviewFacet preview = (PreviewFacet) iter.next();
      if (preview.getContainedPreviewFacet() != null)
        containable.add(preview.isPreviewFor());
    }
    return containable.toArray(new ContainedFacet[0]);
  }

  private ContainedFacet[] getTopLevelContained(MovingFiguresFacet movingFigures)
  {
    // we are adding the nodes to this container
    Iterator iter = movingFigures.getTopLevelFigures();
    ArrayList<ContainedFacet> containable = new ArrayList<ContainedFacet>();
    while (iter.hasNext())
    {
      FigureFacet figure = (FigureFacet) iter.next();
      ContainedFacet contained = figure.getContainedFacet();
      if (contained != null)  // links aren't containable
	      containable.add(contained);
    }
    return containable.toArray(new ContainedFacet[0]);
  }

  private ContainedPreviewFacet[] getContainedPreviews(MovingFiguresFacet movingFigures)
  {
    // we are adding the nodes to this container
    Iterator iter = movingFigures.getSelectedPreviewFigures();
    ArrayList<ContainedPreviewFacet> containable = new ArrayList<ContainedPreviewFacet>();
    while (iter.hasNext())
    {
      PreviewFacet preview = (PreviewFacet) iter.next();
      if (preview.getContainedPreviewFacet() != null)
        containable.add(preview.getContainedPreviewFacet());
    }
    return containable.toArray(new ContainedPreviewFacet[0]);
  }

  private FigureReference[] getContainableReferences(DiagramFacet diagram, MovingFiguresFacet movingFigures)
  {
    List<FigureReference> containableReferences = new ArrayList<FigureReference>();
    Iterator iter = movingFigures.getTopLevelFigures();
    while (iter.hasNext())
    {
      FigureFacet figure = (FigureFacet) iter.next();
      if (figure.getContainedFacet() != null)
        containableReferences.add(figure.getFigureReference());
    }
    return containableReferences.toArray(new FigureReference[0]);
  }

  ////////////////////////////////////////////
  // start of state machine calls
  ////////////////////////////////////////////

  private void enter_STEADY_STATE()
  {
    state = STEADY_STATE;
    diagramView.resetCursor();
    diagramView.turnSweepLayerOff();
    diagramView.turnSelectionLayerOn();
  }
  
  private void exit_STEADY_STATE(ZMouseEvent e, UPoint localPoint)
  {
    // get the figure under the mouse
    ZNode[] nodes = new ZNode[1];
    // don't use the gridded point, as we want the figure directly under the mouse
    ZNode[] ignoringNodes = new ZNode[1];
    FigureFacet overIgnoringManipulators = diagramView.getFigureIgnoringManipulators(localPoint, ignoringNodes);

		// if the right button has been pressed, bring up a possible menu
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
      enter_CONTEXT_MENU_STATE(e, localPoint);
		else
    // if the button is middle, pan regardless of the underlying figure
    if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0 || ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0 && e.getClickCount() == 2))
    {
      enter_PANNING_STATE(e, localPoint);
    }
    else
    {
      // get a possibly manipulator
      int manipType = ManipulatorFacet.TYPE1;
      ManipulatorFacet manip = diagramView.getManipulatorUnderPoint(localPoint, nodes);
      if (manip != null)
        manipType = manip.getType();
      
      // try for a manipulator first
      if (manip != null && (manipType == 1 || manipType == 2))
      {
        // not for readonly
        if (!diagramReadOnlyMode)
          enter_BUTTON_PRESS_ENTRY_SUBSTATE(e, localPoint, nodes[0], manip);
      }
      else
      if (manip != null && manipType == 3 && !e.isShiftDown())
      {
        // not for readonly
        if (!diagramReadOnlyMode)
          enter_POSSIBLY_MANIPULATING_SUBSTATE(e, localPoint, nodes[0], manip, overIgnoringManipulators);
      }
      else
      {
        // otherwise, if we have no figure, we are sweeping -- ignore any manipulators after this point
        ContainerFacet willingContainer = getWillingContainer(localPoint, overIgnoringManipulators);
        if (overIgnoringManipulators == null || willingContainer != null)
          enter_SELECTION_SWEEP_STATE(e, localPoint, willingContainer);
        else
        {
          enter_SELECTION_MOVE_STATE(e, localPoint, overIgnoringManipulators);
        }
      }
    }
  }
  
  private void enter_CONTEXT_MENU_STATE(ZMouseEvent e, UPoint localPoint)
  {
		// get the figure under the cursor and ask it for a popup menu
		FigureFacet figure = getFigureUnderMouse(localPoint);

  	// bring up a menu
		JPopupMenu popup = null;
  	if (figure != null)
  	{
			popup = figure.makeContextMenu(diagramView, coordinator);
			// see if a the global menu editor wants to add or modify this
      if (popup == null)
        popup = new JPopupMenu();
			if (globalPopupMenuFacet != null)
				globalPopupMenuFacet.addToContextMenu(popup, diagramView, coordinator, figure);
  	}
		else
			popup = diagramView.makeContextMenu(coordinator);
			
		if (popup != null && popup.getComponentCount() != 0)
		{
	  	popup.setLabel("Context menu");
	  	popup.show(diagramView.getCanvas(), e.getX(), e.getY());
		}
  	
  	// not really needed
  	enter_STEADY_STATE();
  }
  
  // return the container directly, if figure is a container, and it is willing to act as a backdrop
  private ContainerFacet getWillingContainer(UPoint point, FigureFacet figure)
  {
    if (figure == null)
      return null;
    
    ContainerFacet container = figure.getContainerFacet();
    if (container != null && container.isWillingToActAsBackdrop() && container.insideContainer(point))
        return container;

    return null;  // no willing container found
  }
  
  private void enter_SELECTION_SWEEP_STATE(ZMouseEvent e, UPoint localPoint, ContainerFacet willingContainer)
  {
    if (!e.isShiftDown())
      selection.clearAllSelection();

    // take note of the first point
    sweepStartPoint = localPoint;

    diagramView.turnSweepLayerOff();
    this.willingContainer = willingContainer;
    state = SELECTING_AREA_SUBSTATE;
  }

  /**
   * make a sweep box for selecting parts of the display
   * @param box
   * @return
   */
  public static ZNode constructSweepBox(Rectangle2D box)
  {
    ZRectangle sweep = new ZRectangle(box);
    sweep.setPenPaint(Color.black);
    sweep.setFillPaint(ScreenProperties.getPreviewFillColor());
    sweep.setStroke(ScreenProperties.getDashedStroke());
    return new ZVisualLeaf(sweep);
  }

  private void enter_PANNING_STATE(ZMouseEvent e, UPoint localPoint)
  {
    panned = false;
    state = PANNING_DIAGRAM_SUBSTATE;
    diagramView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    panStartPoint = localPoint;
  }

  private void reenter_PANNING_DIAGRAM_SUBSTATE(ZMouseEvent e, UPoint localPoint)
  {
    UDimension difference = localPoint.subtract(panStartPoint);
    if (difference.distance() > 2)
      panned = true;
    diagramView.pan(difference.getWidth(), difference.getHeight());
  }

  private void enter_SELECTION_MOVE_STATE(ZMouseEvent e, UPoint localPoint, FigureFacet over)
  {
    // if shifted, simply toggle selection
    if (e.isShiftDown())
    {
      FigureFacet selectable = over.getActualFigureForSelection();
      if (selection.isSelected(selectable))
      {
        selection.removeFromSelection(selectable);
        // remove all children also
        removeChildrenFromSelection(selectable);
      }
      else
        selection.addToSelection(selectable);
      enter_STEADY_STATE();
    }
    else
    if (!e.isShiftDown())
    {
	    FigureFacet selectable = over.getActualFigureForSelection();
	
	    // if the object is not selected, make it the only selection
	    if (!selection.isSelected(selectable))
	      selection.clearAllSelection();
	
	    selection.addToSelection(selectable);
	
	    // get the possible new figure under the mouse
	    // -- if it is a manipulator, then use this instead
      ZNode[] nodes = new ZNode[1];

      // don't use the gridded point, as we want the manipulator directly under the mouse
      ManipulatorFacet manip = diagramView.getManipulatorUnderPoint(localPoint, nodes);
      if (!diagramReadOnlyMode)
        if (manip != null && manip.getType() == ManipulatorFacet.TYPE1)
          enter_BUTTON_PRESS_ENTRY_SUBSTATE(e, localPoint, nodes[0], manip);
        else
          enter_MOVING_SELECTION_SUBSTATE(e, localPoint, selectable);
    }
  }

	private void removeChildrenFromSelection(FigureFacet figure)
	{
		selection.removeFromSelection(figure);

		if (figure.getContainerFacet() != null)
    	for (Iterator iter = figure.getContainerFacet().getContents(); iter.hasNext();)
    		removeChildrenFromSelection((FigureFacet) iter.next());
    		
    if (figure.getAnchorFacet() != null)
    {
    	for (Iterator iter = figure.getAnchorFacet().getLinks(); iter.hasNext();)
    		removeChildrenFromSelection(((LinkingFacet) iter.next()).getFigureFacet());
    }
	}

  private void enter_IMMEDIATE_MANIPULATION_SUBSTATE(ManipulatorFacet manipulator)
  {
    this.manipulator = manipulator;
    manipulatorStart = new UPoint(0,0);  // irrelevant

    diagramView.moveManipulatorToNewLayer(manipulator);
    enter_ADJUSTING_MANIPULATOR_SUBSTATE();
    manipulator.enterImmediately(manipulatorListener);
  }

  private void enter_BUTTON_PRESS_ENTRY_SUBSTATE(ZMouseEvent e, UPoint localPoint, ZNode source, ManipulatorFacet manipulator)
  {
    UPoint griddedPoint = Grid.roundToGrid(localPoint);

    this.manipulator = manipulator;
    manipulatorStart = griddedPoint;

    diagramView.moveManipulatorToNewLayer(manipulator);
    enter_ADJUSTING_MANIPULATOR_SUBSTATE();
    manipulator.enterViaButtonPress(manipulatorListener, source, griddedPoint, e.getModifiers());
  }

  private void enter_KEY_ENTRY_SUBSTATE(ManipulatorFacet manipulator, char keyPress)
  {
		if (manipulator.wantToEnterViaKeyPress(keyPress))
		{
			this.manipulator = manipulator;
			manipulatorStart = new UPoint(0, 0);
	    diagramView.moveManipulatorToNewLayer(manipulator);
      enter_ADJUSTING_MANIPULATOR_SUBSTATE();
			manipulator.enterViaKeyPress(manipulatorListener, keyPress);
		}
  }

  private void enter_POSSIBLY_MANIPULATING_SUBSTATE(ZMouseEvent e, UPoint localPoint, ZNode source, ManipulatorFacet manipulator, FigureFacet underManipulator)
  {
    UPoint griddedPoint = Grid.roundToGrid(localPoint);
    
    this.manipulator = manipulator;
    manipulatorSource = source;
    manipulatorStart = griddedPoint;
    this.possibleSelectableToMove = null;
    this.possibleSelectableToMove = underManipulator;
    state = POSSIBLY_MANIPULATING_SUBSTATE;
  }

  private void exit_POSSIBLY_MANIPULATING_SUBSTATE(ZMouseEvent e, UPoint localPoint, boolean releasedWithoutDragging)
  {
    if (releasedWithoutDragging)
    {
      diagramView.moveManipulatorToNewLayer(manipulator);
      enter_ADJUSTING_MANIPULATOR_SUBSTATE();
      manipulator.enterViaButtonRelease(manipulatorListener, manipulatorSource, manipulatorStart, e.getModifiers());
    }
    else
    if (manipulatorStart.distance(localPoint) > SMALLEST_ACCEPTABLE_MOVE_DISTANCE * 2)
    {
      if (possibleSelectableToMove == null)
        enter_STEADY_STATE();
      else
        // are now moving the selections
        enter_MOVING_SELECTION_SUBSTATE_including_drag(e, localPoint, manipulatorStart, possibleSelectableToMove);
    }
  }

  private void reenter_ADJUSTING_MANIPULATOR_SUBSTATE_via_mousepressed(ZMouseEvent e, UPoint localPoint)
  {
    transmitManipulatorEvent = e;
    UPoint griddedPoint = Grid.roundToGrid(localPoint);
    manipulator.mousePressed(getFigureUnderMouse(localPoint), griddedPoint, e);
  }

  private void reenter_ADJUSTING_MANIPULATOR_SUBSTATE_via_mousemoved(ZMouseEvent e, UPoint localPoint)
  {
    transmitManipulatorEvent = null;
    final UPoint griddedPoint = Grid.roundToGrid(localPoint);

    // if we haven't moved enough to justify an adjustment, then don't bother
    if (!movedEnough && griddedPoint.distance(manipulatorStart) < SMALLEST_ACCEPTABLE_MOVE_DISTANCE)
      return;
    movedEnough = true;

		manipulator.mouseMoved(getFigureUnderMouse(griddedPoint), griddedPoint, e);
  }

  private void reenter_ADJUSTING_MANIPULATOR_SUBSTATE_via_mousedragged(ZMouseEvent e, UPoint localPoint)
  {
    transmitManipulatorEvent = null;
    UPoint griddedPoint = Grid.roundToGrid(localPoint);

    // if we haven't moved enough to justify an adjustment, then don't bother
    if (!movedEnough && griddedPoint.distance(manipulatorStart) < SMALLEST_ACCEPTABLE_MOVE_DISTANCE)
      return;
    movedEnough = true;

    manipulator.mouseDragged(getFigureUnderMouse(griddedPoint), griddedPoint, e);
  }

  private void reenter_ADJUSTING_MANIPULATOR_SUBSTATE_via_mousereleased(ZMouseEvent e, UPoint localPoint)
  {
    transmitManipulatorEvent = null;
    UPoint griddedPoint = Grid.roundToGrid(localPoint);

    manipulator.mouseReleased(getFigureUnderMouse(griddedPoint), griddedPoint, e);
  }

  private void enter_ADJUSTING_MANIPULATOR_SUBSTATE()
  {
    state = ADJUSTING_MANIPULATOR_SUBSTATE;
    movedEnough = false;
    transmitManipulatorEvent = null;
    
    // if we are in layout mode, tell the manipulator(s)
    if (layoutOnly)
      manipulator.setLayoutOnly();
  }

  private void possibly_exit_ADJUSTING_MANIPULATOR_SUBSTATE(Command manipulatorCommand, ZMouseEvent relevantManipulatorEvent)
  {
    // move the manipulator back to the selection layer
    diagramView.moveManipulatorToSelectionLayer(manipulator);

    // display the manipulator node first
    coordinator.executeCommandAndUpdateViews(manipulatorCommand);

    coordinator.toolFinished(relevantManipulatorEvent, true);
    enter_STEADY_STATE();

    // if this was due to a mouse press, we need to generate a mouse press
    if (relevantManipulatorEvent != null)
      exit_STEADY_STATE(relevantManipulatorEvent, new UPoint(relevantManipulatorEvent.getLocalPoint()));
  }

  private void enter_MOVING_SELECTION_SUBSTATE_including_drag(ZMouseEvent e, UPoint localPoint, UPoint moveStartPoint, FigureFacet underManipulator)
  {
    enter_SELECTION_MOVE_STATE(e, moveStartPoint, underManipulator);
    // if the manipulator was over a selectable figure, the state should now be MOVING_SELECTION_SUBSTATE, otherwise it is STEADY_STATE
    // if we are moving a selection, then drag it as well
    if (state == MOVING_SELECTION_SUBSTATE)
      reenter_MOVING_SELECTION_SUBSTATE(e, localPoint);
  }

  private void enter_MOVING_SELECTION_SUBSTATE(ZMouseEvent e, UPoint localPoint, FigureFacet draggedFigure)
  {
    // clear out the selection
    moveStartPoint = Grid.roundToGrid(localPoint);
    state = MOVING_SELECTION_SUBSTATE;

    // behave like rose
    diagramView.turnSelectionLayerOff();

    // change the cursor
    diagramView.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

    // get the moving figures, and save them away
    movedEnough = false;

    // reset the dragging variables
    fromContainer = null;
    newContainer = null;
    acceptingContainer = null;
    fromContainerPreview = null;
    fromContainerResizings = null;
    acceptContainer = true;  // this must accept the container, or it wouldn't be there :-)
    acceptingContainerResizings = null;
    keyMovingPoint = null;

    movingFigures = new MovingFiguresGem(diagram, moveStartPoint).getMovingFiguresFacet();
    // need to set up the boundary conditions
    boolean allMovingFromSameContainer = movingFigures.indicateMovingFigures(selection.getSelectedFigures());
    if (allMovingFromSameContainer)
    {
    	ContainerFacet movingFromContainer = movingFigures.getContainerAllAreMovingFrom();
    	if (movingFromContainer == null)
    		fromContainer = null;
      else
				fromContainer = getPossibleContainer(movingFromContainer.getFigureFacet(), getTopLevelContained(movingFigures));
      movingFigures.start(draggedFigure);
      draggingState = DRAGGING_OVER_FROM_CONTAINER_SUBSTATE;
    }
    else
      enter_STEADY_STATE();
  }
  
  private void exit_MOVING_SELECTION_SUBSTATE(ZMouseEvent e, UPoint localPoint)
  {
    UPoint finalPoint = Grid.roundToGrid(localPoint);

    // only generate a move command if the distance is reasonable
    if (movedEnough)
    {
      movingFigures.move(finalPoint);
      reenter_DRAGGING_SUBSTATE(finalPoint);

      CompositeCommand comp = new CompositeCommand("", "");
      String description = "moved figures";
      String undoDescription = "moved figures back";
      switch (draggingState)
      {
        case DRAGGING_OVER_FROM_CONTAINER_SUBSTATE:
          // don't need to generate a container type command
          break;
        case DRAGGING_OVER_NEW_CONTAINER_SUBSTATE:
        	// if the container won't accept, then cancel the command
        	if (!acceptContainer)
        	{
					  enter_STEADY_STATE();
						return;		
        	}

          // generate a remove from the old container and an add to the new container
          if (fromContainer != null) // might be the top level
          {
          	ContainerFacet actualFromContainer = movingFigures.getContainerAllAreMovingFrom();
            comp.addCommand(new ContainerRemoveCommand(actualFromContainer.getFigureFacet().getFigureReference(), getContainableReferences(diagram, movingFigures), "moved out of old container", "moved back into old container"));
            description = "moved figures from " + fromContainer.getFigureFacet().getFigureName();
            undoDescription = "moved figures back into " + fromContainer.getFigureFacet().getFigureName();
          }
          if (acceptingContainer != null)  // might be the top level
          {
            comp.addCommand(new ContainerAddCommand(acceptingContainer.getFigureFacet().getFigureReference(), getContainableReferences(diagram, movingFigures), "moved into new container", "moved out of new container"));
            if (fromContainer != null)
            {
              description = "moved figures from " + fromContainer.getFigureFacet().getFigureName() + " into " + newContainer.getFigureFacet().getFigureName();
              undoDescription = "moved figures back into " + fromContainer.getFigureFacet().getFigureName() + " from " + newContainer.getFigureFacet().getFigureName();
            }
            else
            {
              description = "moved figures into " + newContainer.getFigureFacet().getFigureName();
              undoDescription = "moved figures from " + newContainer.getFigureFacet().getFigureName();
            }
            
            // add locate commands to ensure that they relocate correctly
            // ask each containable for a move command
            for (Iterator iter = movingFigures.getTopLevelFigures(); iter.hasNext();)
            {
              FigureFacet top = (FigureFacet) iter.next();
              ContainedFacet contained = top.getContainedFacet();
              if (contained != null)
                comp.addCommand(contained.getPostContainerDropCommand());
            }
          }
          break;
      }

      if (acceptingContainerResizings != null)
      {
        comp.addCommand(acceptingContainerResizings.end("resized new container", "restored size of new container"));
      }
      else
      if (fromContainerResizings != null)
        comp.addCommand(fromContainerResizings.end("resized old container", "restored size of old container"));
      else
        comp.addCommand(movingFigures.end("moved figures", "moved figures back"));
      comp.setDescriptions(description, undoDescription);
      coordinator.executeCommandAndUpdateViews(comp);
    }

    // turn the selection back on and remove the sweepbox
    enter_STEADY_STATE();
  }

  private void exit_PANNING_DIAGRAM_SUBSTATE(ZMouseEvent e, UPoint localPoint)
  {

    if (!panned)
    {
      Cursor oldCursor = coordinator.displayWaitCursor();
      try
      {
        ZNode[] ignoringNodes = new ZNode[1];
        FigureFacet overIgnoringManipulators = diagramView.getFigureIgnoringManipulators(localPoint, ignoringNodes);

        // ask the figure for a middle button command, which can then be executed
        // -- make sure we are over a figure
      
        FigureFacet panningLocalFigure =
          overIgnoringManipulators != null ? overIgnoringManipulators.getActualFigureForSelection() : null;

        if (panningLocalFigure != null)
        {
          Command cmd = panningLocalFigure.middleButtonPressed(coordinator);
          if (cmd != null)
            coordinator.executeCommandAndUpdateViews(cmd);
        }
        else
          // form the command on the diagram
          diagramView.middleButtonPressed();
      }
      finally
      {
        coordinator.restoreCursor(oldCursor);
      }
    }

    enter_STEADY_STATE();
  }

  private void reenter_MOVING_SELECTION_SUBSTATE(ZMouseEvent e, UPoint localPoint)
  {
    UPoint griddedPoint = Grid.roundToGrid(localPoint);

    // if we haven't moved enough to justify an adjustment, then don't bother
    if (!movedEnough && griddedPoint.distance(moveStartPoint) < SMALLEST_ACCEPTABLE_MOVE_DISTANCE)
      return;
    movedEnough = true;

    // move the figures
    movingFigures.move(griddedPoint);
    reenter_DRAGGING_SUBSTATE(griddedPoint);

    // add the views
    ZGroup group = new ZGroup();
    if (acceptingContainerResizings != null)
      group.addChild(acceptingContainerResizings.formView());
    if (fromContainerResizings != null)
      group.addChild(fromContainerResizings.formView());
    // add a mark to indicate the target
    if (draggingState == DRAGGING_OVER_FROM_CONTAINER_SUBSTATE)
    {
      ContainerFacet actualMovingFrom = movingFigures.getContainerAllAreMovingFrom();
      
      // don't show the container accepting highlight if there is no chance of moving
      boolean showPreview =
        actualMovingFrom == null || movingFigures.contentsCanMoveContainers();

      if (fromContainerPreview != null && showPreview)
      {
        ZNode containerHighlight = fromContainerPreview.formContainerHighlight(true);
        if (containerHighlight != null)
          group.addChild(containerHighlight);
      }
    }
    else
    {
      if (newContainer != null)
      {
      	ContainerPreviewFacet preview = newContainer.getFigureFacet().getSinglePreview(diagram).getContainerPreviewFacet();
        group.addChild(preview.formContainerHighlight(acceptingContainer != null && acceptContainer));
      }
    }

    // finally! add the moving figures
    group.addChild(movingFigures.formView());
   diagramView.turnSweepLayerOn(group);
  }

  private void reenter_DRAGGING_SUBSTATE(UPoint griddedPoint)
  {
    // get the container that we are currently over, treating the fromContainer specially
    FigureFacet figure = getRealFigureUnderMouse(griddedPoint);
    ContainerFacet mouseContainer = null;
    
    ContainerFacet possible = SelectionToolGem.getPossibleContainer(figure, getTopLevelContained(movingFigures));
    if (possible != null)
    {
      if (!movingFigures.nodeIsInFocusOfPreview(possible.getFigureFacet()) && possible.insideContainer(griddedPoint))
        mouseContainer = possible;
    }
    // maybe it hasn't moved from the container?
    if (mouseContainer == null && fromContainer != null && fromContainer.insideContainer(griddedPoint))
      mouseContainer = fromContainer;
    
    // if we are in layout mode, we cannot move from the old container
    if (layoutOnly)
      mouseContainer = fromContainer;

    switch (draggingState)
    {
      case DRAGGING_OVER_FROM_CONTAINER_SUBSTATE:
        // make a resizings for the from container
      	ContainerFacet actualFromContainer = movingFigures.getContainerAllAreMovingFrom();
        if (fromContainerResizings == null && fromContainer != null)
        {
        	fromContainerResizings = makeResizingsForContainer(actualFromContainer, movingFigures);
          fromContainerPreview = fromContainerResizings.getCachedPreview(fromContainer.getFigureFacet()).getContainerPreviewFacet();
        }

        if (fromContainer != mouseContainer &&
            (actualFromContainer == null || movingFigures.contentsCanMoveContainers()))
        {
          // remove the previews from
          draggingState = DRAGGING_OVER_NEW_CONTAINER_SUBSTATE;
          newContainer = mouseContainer; // may be null
          ContainedFacet[] topLevelContained = getTopLevelContained(movingFigures);
          acceptingContainer = null;
          if (newContainer != null)
          {
	          acceptingContainer = newContainer.getAcceptingSubcontainer(topLevelContained);
          }
          
          // see if we can find an accepting container
          acceptContainer = containablesAcceptContainer(acceptingContainer, topLevelContained);
          if (newContainer != null && acceptingContainer == null)
          	acceptContainer = false;
          	
          acceptingContainerResizings = null;
          if (acceptContainer && acceptingContainer != null)
          {
            PreviewCacheFacet cache = movingFigures;
            if (fromContainerResizings != null)
              cache = fromContainerResizings;
            acceptingContainerResizings = makeResizingsForContainer(acceptingContainer, cache);
            // ask it to add the previews
	         	acceptingContainerResizings.resizeToAddContainables(getContainedPreviews(movingFigures), griddedPoint);
          }

          // ask the from container to resize to remove the containables
          if (fromContainer != null)
            fromContainerResizings.resizeToRemoveContainables(getContainedPreviews(movingFigures), griddedPoint);
        }
        else if (fromContainerResizings != null)
        {
					// we know that the from container accepts the containables, as they just came from there...
	        fromContainerResizings.adjustForExistingContainables(getContainedPreviews(movingFigures), griddedPoint);
        }
        break;

      case DRAGGING_OVER_NEW_CONTAINER_SUBSTATE:
        // we are over a new container. if the mouseContainer isn't the same, then transition to exited_new_container and back possibly
        if (mouseContainer == newContainer)
        {
          // may be null still
          if (acceptingContainerResizings != null)
            acceptingContainerResizings.adjustForExistingContainables(getContainedPreviews(movingFigures), griddedPoint);
        }
        else
        if (mouseContainer == fromContainer)
        {
          // discard the to container, and jump back to dragging over state
          if (acceptingContainerResizings != null)
          {
            acceptingContainerResizings.restoreSizeForContainables();
            acceptingContainerResizings.disconnect(); // we are throwing this away
          }
          if (fromContainerResizings != null)
					{
            fromContainerResizings.adjustForExistingContainables(getContainedPreviews(movingFigures), griddedPoint);
					}

          // reset the state back
          newContainer = null;
          acceptingContainer = null;
          acceptContainer = true;  // it is back where it came from, so it must accept it
          acceptingContainerResizings = null;
          draggingState = DRAGGING_OVER_FROM_CONTAINER_SUBSTATE;
        }
        else
        if (mouseContainer != null)
        {
          // discard the to container, and jump back to dragging over state
          if (acceptingContainerResizings != null)
          {
            acceptingContainerResizings.restoreSizeForContainables();
            acceptingContainerResizings.disconnect(); // we are throwing this away
          }

          // reset the state back
          draggingState = DRAGGING_OVER_NEW_CONTAINER_SUBSTATE;

          // get the new resizings for this container
          newContainer = mouseContainer;
          ContainedFacet[] topLevelContained = getTopLevelContained(movingFigures);
          acceptingContainer = newContainer.getAcceptingSubcontainer(topLevelContained);
          
          // see if we can find an accepting container
          acceptingContainerResizings = null;
          acceptContainer = SelectionToolGem.containablesAcceptContainer(acceptingContainer, topLevelContained);
          if (acceptingContainer == null)
          	acceptContainer = false;

          // ask it to add the previews
          if (acceptContainer)
					{
	          PreviewCacheFacet cache = movingFigures;
	          if (fromContainerResizings != null)
	            cache = fromContainerResizings;
	          acceptingContainerResizings = makeResizingsForContainer(acceptingContainer, cache);
	          acceptingContainerResizings.resizeToAddContainables(getContainedPreviews(movingFigures), griddedPoint);
					}
        }
        else
        {
          // have a new container that is null
          // discard the to container, and jump back to dragging over state
					// accepting container may be null because the container may not have accepted the dragged item
					if (acceptingContainerResizings != null)
					{
	          acceptingContainerResizings.restoreSizeForContainables();
	          acceptingContainerResizings.disconnect(); // we are throwing this away
					}

          // reset the state back
          newContainer = null;
			    acceptingContainer = null;
          acceptContainer = SelectionToolGem.containablesAcceptContainer(newContainer, getTopLevelContained(movingFigures));
          acceptingContainerResizings = null;
          draggingState = DRAGGING_OVER_NEW_CONTAINER_SUBSTATE;
        }
        break;
    }
  }


  private void reenter_SELECTING_AREA_SUBSTATE(ZMouseEvent e, UPoint localPoint)
  {
    diagramView.turnSweepLayerOn(constructSweepBox(new UBounds(sweepStartPoint, localPoint)));
  }

  private void exit_SELECTING_AREA_SUBSTATE(ZMouseEvent e, UPoint localPoint)
  {
    // find the nodes swept out by the area
    diagramView.turnSweepLayerOff();

    UBounds sweepBounds = new UBounds(sweepStartPoint, localPoint);
    FigureFacet[] selected = diagramView.findInArea(sweepBounds, FigureFacet.class);

    // add this to the selection
    for (int lp = 0; lp < selected.length; lp++)
    {
      // don't add this if it is the willing container, and it doesn't transgress the full bounds
      FigureFacet single = selected[lp].getActualFigureForSelection();
   
      boolean add = true;
      // don't add this if the selection doesn't interset, or contain the boundary of a node
      if (single.getContainedFacet() != null && single.getFullBounds().contains(sweepBounds))
				add = false;
				
			if (add)
      	selection.addToSelection(single);
    }
    enter_STEADY_STATE();
  }

  /**
   * @param possible
   * @param adjusting
   * @return
   */
  public static boolean containerAndContainedAreHappy(ContainerFacet possible, ContainedFacet[] adjusting)
  {
    ContainerFacet subContainer = possible.getAcceptingSubcontainer(adjusting);
    if (subContainer == null)
      return false;
    return containablesAcceptContainer(subContainer, adjusting);
  }

  public static boolean containablesAcceptContainer(ContainerFacet container, ContainedFacet[] containable)
  {
    if (container != null && container.getFigureFacet().isSubjectReadOnlyInDiagramContext(false))
      return false;
    
  	for (int lp = 0; lp < containable.length; lp++)
  	{
  		if (!containable[lp].acceptsContainer(container))
  			return false;
  	}
  
  	// if we got here, all is fine
  	return true;
  }

  /**
   * if the figure is a container, this will be returned.
   * If the figure is a containable, then the container containing it will be returned.
   * 
   *  NOTE: this is complex.
   *  Logic: Move up to find a container that can directly accept items, and offers a subcontainer
   *         that the adjusting elements are all happy with.  Figures can control which container highlights
   *         for a given containable that moves by making sure that the one that is wanted has the directlyAcceptsItem
   *         as true.
   *         
   */
  public static ContainerFacet getPossibleContainer(FigureFacet figure, ContainedFacet[] adjusting)
  {
  	if (figure == null)
  		return null;
  	
  	ContainerFacet possible = figure.getContainerFacet();
  	
    if (possible == null)
    {
  		// if we haven't found a container yet, and the element is containable, then try back through the containers
  		// of this item
  		ContainedFacet contained = figure.getContainedFacet();
  		if (contained != null)
  			possible = contained.getContainer();
    }
  
    // make sure that we get a container that accepts items
    ContainerFacet lastContainer = possible;
    while (possible != null)
    {
      if (possible.directlyAcceptsItems() && SelectionToolGem.containerAndContainedAreHappy(possible, adjusting))
        return possible;
      lastContainer = possible;
      ContainedFacet currentContained = possible.getContainedFacet();
      if (currentContained == null)
        possible = null;
      else
        possible = currentContained.getContainer();
    }
    
    // return the last container, even though it won't be appropriate, as we don't want
    // elements to be overlaid on incompatible elements
  	return lastContainer;
  }

  /////////////////////////////////////////
  // end of state machine
  /////////////////////////////////////////
}