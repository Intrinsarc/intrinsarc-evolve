package com.intrinsarc.idraw.diagramsupport;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.animation.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

final class PanningSizes
{
	UDimension canvasDim;
	UDimension actualObjectDim;
	UDimension maxDim;
	UBounds viewPort;
};

public final class BasicDiagramViewGem implements Gem
{
  public static final boolean USE_LOW_QUALITY_FOR_TRANSITIONS = false;
  public static final long TRANSITION_TIME_MSECS = 300;
	private ZLayerGroup parentLayer; // top layer
	private ZLayerGroup pickLayer;
	// used for selection and to group the object layer
	private ZGroup objectLayer; // contains the linkingLayer and layer
	private ZGroup layer; // layer to add figures to
	private ZGroup globalLayer; // linking elements alway go here
	private ZGroup adornerLayer; // adornments always go here
	private ZGroup debugLayer;
	private ZTransformGroup backdropLayer;
	// a layer for displaying elements for debug purposes
	private ZCamera camera;
	private ZNode cameraNode;
	private ZCamera pickCamera;
	private ZNode pickCameraNode;
	private ToolFacet currentTool;
	private BasicSelectionFacetImpl selection;
	private DiagramFacet diagramFacet;
	private ToolCoordinatorFacet coordinator;
	private UDimension scale;
	private ZCanvas canvas;
	private double panX;
	private double panY;
	private BasicDiagramScrollerFacet scrollerFacet;

	private Map<FigureFacet, ZNode> views = new HashMap<FigureFacet, ZNode>();
	private Map<FigureFacet, ZNode> adorns = new HashMap<FigureFacet, ZNode>();
	private List<Map<FigureFacet, Integer>> previouslyAdorned = new ArrayList<Map<FigureFacet, Integer>>();
	private Map<String, FigureFacet> figures = new HashMap<String, FigureFacet>(); 		 /* id -> figure (doesn't include hidden figures) */
	private HashMap<FigureFacet, ContainerFacet> containers = new HashMap<FigureFacet, ContainerFacet>();
	/* contained figure -> container figure */
	private DiagramViewFacet diagramViewFacet = new DiagramViewFacetImpl();
	private BasicDiagramViewFacet basicFacet = new BasicDiagramViewFacetImpl();
	private DiagramListenerFacet listenerFacet = new DiagramListenerFacetImpl();
  private Object modificationsLock = new Object();
  private Runnable runAfterModifications;
  private DiagramViewContextFacet diagramViewContext;
  private boolean isUsingTools;
  private Set<KeyListener> keyListeners = new HashSet<KeyListener>();
  private UPoint cursorPoint = new UPoint(0, 0);
  private boolean ignoringTransitions;
  private UDimension originalScale;
  private List<DiagramFigureAdornerFacet> adorners;

	public BasicDiagramViewGem(
		DiagramFacet diagram,
		List<DiagramFigureAdornerFacet> adorners,
		ZCanvas canvas,
		UDimension scale,
		Color backgroundColor,
		boolean isUsingTools)
	{
		this.diagramFacet = diagram;
		this.adorners = adorners;
		this.canvas = canvas;
		this.scale = scale;
		this.originalScale = scale;
		this.isUsingTools = isUsingTools;
		
		// set up the camera and the canvas according to Jazz rules		
		// get details about the canvas
		ZLayerGroup canvasLayer = canvas.getLayer();

		// make a portal
		ZRoot root = new ZRoot();
		parentLayer = new ZLayerGroup();
		root.addChild(parentLayer);
		camera = new ZCamera(parentLayer, canvas.getDrawingSurface());
		camera.setFillColor(null);
		cameraNode = new ZVisualLeaf(camera);
		
		cameraNode.addMouseMotionListener(new ZMouseMotionListener()
		{
			public void mouseDragged(ZMouseEvent e)
			{
				cursorPoint = new UPoint(e.getLocalPoint());
			}

	    public void mouseMoved(ZMouseEvent e)
	    {
				cursorPoint = new UPoint(e.getLocalPoint());
	    }
		});
    
//    camera.setFillColor(new Color(250, 250, 220)); // can set a background color if desired
		camera.setFillColor(null);
		camera.setBounds(0, 0, 100000, 100000); // i.e. infinite
		canvasLayer.addChild(cameraNode);

		// set the rendering and interaction properties
		AffineTransform result = camera.getViewTransform();
		result.scale(scale.getWidth(), scale.getHeight());
//		result.shear(-0.7, 0);
//    result.scale(scale.getWidth() * 1.5, scale.getHeight());
		camera.setViewTransform(result);

		canvas.setNavEventHandlersActive(false);

		// use antialiasing for graphics, but not for text
		setHighQuality(true);
		canvas.setCursor(null);
		///////////////////////////////////////////////

		// set the cursor
		diagramViewFacet.resetCursor();

		// make the pick layer
		pickLayer = new ZLayerGroup();
		createObjectLayers();
		selection = new BasicSelectionFacetImpl(diagramViewFacet, pickLayer, parentLayer, canvas);
		debugLayer = new ZGroup();
		parentLayer.addChild(pickLayer);
		parentLayer.addChild(debugLayer);
		pickLayer.lower();

		// load all of the figures up
		syncWithDiagram(true);
		
		pickCamera = new ZCamera();
		pickCamera.addLayer(pickLayer);
		pickCameraNode = new ZVisualLeaf(pickCamera);
		parentLayer.addChild(pickCameraNode);

		// add a listener to the canvas to pick up resizes
		canvas.addComponentListener(new ComponentListener()
		{
			public void componentResized(ComponentEvent e)
			{
				diagramViewFacet.pan(0, 0);
			}
			public void componentMoved(ComponentEvent e)
			{
				diagramViewFacet.pan(0, 0);
			}
			public void componentShown(ComponentEvent e)
			{
				diagramViewFacet.pan(0, 0);
			}
			public void componentHidden(ComponentEvent e)
			{
				diagramViewFacet.pan(0, 0);
			}
		});

		diagramViewFacet.pan(0, 0); // reset the scrollbars
		canvas.setBackground(backgroundColor);
	}
	
	/**
   * @param b
   */
  private void setHighQuality(boolean highQuality)
  {
    if (highQuality)
      canvas.getDrawingSurface().setRenderQuality(ZDrawingSurface.RENDER_QUALITY_MEDIUM);
    else
      canvas.getDrawingSurface().setRenderQuality(ZDrawingSurface.RENDER_QUALITY_LOW);
  }

  /**
   * 
   */
  private void createObjectLayers()
  {
    pickLayer.removeChild(objectLayer);
    objectLayer = new ZGroup();
		layer = new ZGroup();
		globalLayer = new ZGroup();
		adornerLayer = new ZGroup();
		objectLayer.addChild(layer);
		objectLayer.addChild(globalLayer); // links need to be above nodes etc
		objectLayer.addChild(adornerLayer); // adorners need to be above all
		pickLayer.addChild(objectLayer);
		objectLayer.lower();
		
    pickLayer.removeChild(backdropLayer);
		backdropLayer = new ZTransformGroup();
		pickLayer.addChild(backdropLayer);
		backdropLayer.lower();
  }

  private void syncWithDiagram(boolean determineAdornments)
	{
		// clear out any graphical views, and any maps etc
		layer.removeAllChildren();
		globalLayer.removeAllChildren();
		adornerLayer.removeAllChildren();
		debugLayer.removeAllChildren();
		selection.clearAllSelection();
		selection.turnSelectionLayerOff();
		selection.turnSweepLayerOff();
		views = new HashMap<FigureFacet, ZNode>();
		adorns = new HashMap<FigureFacet, ZNode>();
		figures = new HashMap<String, FigureFacet>();
		containers = new HashMap<FigureFacet, ContainerFacet>();
    
		// load the full set of figures
		for (FigureFacet figure : diagramFacet.getFigures())
			figures.put(figure.getId(), figure);

    // work out what we should adorn
		if (determineAdornments)
		{
		  previouslyAdorned.clear();
		  if (adorners != null)
		  {
  		  for (DiagramFigureAdornerFacet adorner : adorners)
    		  previouslyAdorned.add(
    		      adorner.determineAdornments(diagramFacet, new HashSet<FigureFacet>(figures.values())));
		  }
		}
    
		for (FigureFacet figure : figures.values())
		{
			// if this is contained, and has a parent, don't do it
			ContainedFacet contained = figure.getContainedFacet();
			if (contained != null && contained.getContainer() != null)
				continue;

			addFigureAndChildrenToView(figure);
		}
		
		// make sure the scroll bars are correct
		adjustScrollBars();
	}
	
	public DiagramViewFacet getDiagramViewFacet()
	{
		return diagramViewFacet;
	}
	
	public BasicDiagramViewFacet getBasicDiagramViewFacet()
	{
		return basicFacet;
	}
	
	public DiagramListenerFacet getDiagramListenerFacet()
	{
		return listenerFacet;
	}
	
	public void connectBasicDiagramScrollerFacet(BasicDiagramScrollerFacet scrollerFacet)
	{
		this.scrollerFacet = scrollerFacet;
	}
	
	public void connectDiagramViewContextFacet(DiagramViewContextFacet diagramViewContext)
	{
		this.diagramViewContext = diagramViewContext;
	}
	
	private class DiagramListenerFacetImpl implements DiagramListenerFacet
	{
		public synchronized void haveModifications(final DiagramChange[] changes)
		{
      int length = changes.length;
      if (length == 0)
      	return;

      int lp = 0;
      final Map<FigureFacet, Integer> modifiedAdorned = new HashMap<FigureFacet, Integer>();
      if (adorners != null)
        for (DiagramFigureAdornerFacet adorner : adorners)
        {
          // determine any adornment changes
          final Map<FigureFacet, Integer> nextAdorned;
          nextAdorned = adorner.determineAdornments(diagramFacet, new HashSet<FigureFacet>(figures.values()));
          
          Set<FigureFacet> intersection = new HashSet<FigureFacet>(nextAdorned.keySet());
          intersection.retainAll(previouslyAdorned.get(lp).keySet());
          modifiedAdorned.putAll(nextAdorned);
          if (previouslyAdorned.get(lp) != null)
            modifiedAdorned.putAll(previouslyAdorned.get(lp));
          for (FigureFacet figure : intersection)
          	modifiedAdorned.remove(figure);
          previouslyAdorned.set(lp, nextAdorned);
          lp++;
        }
				
			final Runnable modificationProcessor = new Runnable()
			{
				public synchronized void run()
				{
					for (int lp = 0; lp < changes.length; lp++)
					{
						DiagramChange change = changes[lp];
						FigureFacet figure = null;
						if (change.getFigureId() != null)
							figure = change.getFigure();
						
						// handle the figure, without disturbing the map
						switch (change.getModificationType())
						{
							case ADD :
								addFigureToDiagram(figure);
                modifiedAdorned.remove(figure);
								break;
							case REMOVE :
								removeFigureFromDiagram(figure, false);
								previouslyAdorned.remove(figure);
								modifiedAdorned.remove(figure);
								break;
							case MODIFY :
								adjustFigureInDiagram(figure);
                modifiedAdorned.remove(figure);
								break;
							case RESYNC:
								syncWithDiagram(false);
								modifiedAdorned.clear();
								break;
						}
					}
					
					// process any modifications to adornment status
					for (FigureFacet modified : modifiedAdorned.keySet())
					{
						if (figures.containsKey(modified))
							adjustFigureInDiagram(modified);
					}
					
					// nudge the pan in case the moved figure means that the diagram now fits on the screen
					// also adjusts the scrollbars
					diagramViewFacet.pan(0, 0);
					
					synchronized (modificationsLock)
					{
						if (runAfterModifications != null)
							runAfterModifications.run();
						runAfterModifications = null;
					}
				}
			};

			if (SwingUtilities.isEventDispatchThread())
				modificationProcessor.run();
			else
			{
				SwingUtilities.invokeLater(modificationProcessor);
			}
		}
		
		/**
		 * @see com.intrinsarc.idraw.foundation.DiagramListenerFacet#refreshViewAttributes()
		 */
		public void refreshViewAttributes()
		{
			if (diagramViewContext != null)
				diagramViewContext.refreshViewAttributes();
		}
	}

	private class BasicDiagramViewFacetImpl implements BasicDiagramViewFacet
	{
		public UDimension getDimensionsOfView()
		{
			return makePanningSizes().maxDim;
		}
	
		public UBounds getViewPort()
		{
			return makePanningSizes().viewPort;
		}
		
		/**
		 * @see com.intrinsarc.idraw.diagramsupport.BasicDiagramViewFacet#getDiagramViewFacet()
		 */
		public DiagramViewFacet getDiagramViewFacet()
		{
			return diagramViewFacet;
		}

		/**
		 * @see com.intrinsarc.idraw.diagramsupport.BasicDiagramViewFacet#requestFocus()
		 */
		public void requestFocus()
		{
			canvas.requestFocus();
		}
	}

	private class DiagramViewFacetImpl implements DiagramViewFacet
	{ 
		public UDimension getCurrentPan()
		{
			return new UDimension(panX, panY);
		}
		
		public void pan(double xDifference, double yDifference)
		{
			panX -= xDifference;
			panY -= yDifference;
	
			// don't go to a -ve coordinate
			if (panX < 0)
			{
				xDifference += panX;
				panX = 0;
			}
			if (panY < 0)
			{
				yDifference += panY;
				panY = 0;
			}
	
			// don't allow panning greater than the diagram contains
			PanningSizes sizes = makePanningSizes();
	
			double maxPanX = sizes.maxDim.getWidth() - sizes.canvasDim.getWidth();
			if (panX > maxPanX)
			{
				double diff = maxPanX - panX;
				panX = maxPanX;
				xDifference -= diff;
			}
			double maxPanY = sizes.maxDim.getHeight() - sizes.canvasDim.getHeight();
			if (panY > maxPanY)
			{
				double diff = maxPanY - panY;
				panY = maxPanY;
				yDifference -= diff;
			}
	
			AffineTransform transform = camera.getViewTransform();
			transform.translate(xDifference, yDifference);
			camera.setViewTransform(transform);
			
			// make sure the backdrop is in the correct place
			backdropLayer.setTranslation(panX, panY);
	
			// make sure that the scroll bars show where we are in relation to the current view
			adjustScrollBars();
		}

		public UDimension getScale()
		{
			return scale;
		}
	
		public void setCursor(Cursor cursor)
		{
			canvas.setCursor(cursor);
		}
	
		public void resetCursor()
		{
			canvas.setCursor(null);
      canvas.requestFocus();
		}
	
		public void moveManipulatorToNewLayer(ManipulatorFacet manipulator)
		{
			selection.moveManipulatorToNewLayer(manipulator);
		}
	
		public void moveManipulatorToSelectionLayer(ManipulatorFacet manipulator)
		{
			selection.moveManipulatorToSelectionLayer(manipulator);
		}
	
		/**
		 * @see com.giroway.jumble.foundation.interfaces.DiagramView#getCanvas()
		 */
		public ZCanvas getCanvas()
		{
			return canvas;
		}
	
		public void addKeyListenerJustOnce(KeyListener keyListener)
		{
			if (!keyListeners.contains(keyListener))
			{
				keyListeners.add(keyListener);
				canvas.addKeyListener(keyListener);
			}
		}
	
		/**get the figure under the mouse.  Will be selectable or moveable.*/
		public ManipulatorFacet getManipulatorUnderPoint(UPoint e, ZNode[] nodes)
		{
			ZSceneGraphPath path = new ZSceneGraphPath();
			ZBounds rect = new UBounds(e, new UDimension(0.1, 0.1));
			pickLayer.pick(rect, path);
			ZNode node = path.getNode();
			if (node != null)
			{
				Object under = node.getClientProperty("manipulator");
				if (under != null)
				{
					if (nodes != null)
						nodes[0] = node;
					// reasonable cast (cannot get client properties back except as objects)
					return (ManipulatorFacet) under;
				}
			}
	
			return null;
		}
	
		/**get the figure under the mouse.  Will be selectable or moveable.*/
		public FigureFacet getFigureUsingEventPoint(ZMouseEvent e, ZNode[] nodes)
		{
			ZNode node = e.getNode();
			if (node != null)
			{
				FigureFacet figure = (FigureFacet) node.getClientProperty("figure");
				if (figure != null)
				{
					if (nodes != null)
						nodes[0] = node;
					return figure;
				}
			}
	
			return null;
		}
	
		/** get the figure under the mouse, ignoring the manipulator (selection) layer. */
		public FigureFacet getFigureIgnoringManipulators(UPoint e, ZNode[] nodes)
		{
			ZSceneGraphPath path = new ZSceneGraphPath();
			if (e == null)
				return null;
			ZBounds rect = new UBounds(e, new UDimension(0.1, 0.1));
			objectLayer.pick(rect, path);
			ZNode node = path.getNode();
			if (node != null)
			{
				FigureFacet figure = (FigureFacet) node.getClientProperty("figure");
				if (figure != null)
				{
					if (nodes != null)
						nodes[0] = node;
	
					return figure;
				}
			}
	
			return null;
		}
	
		public FigureFacet[] findInArea(UBounds area, Class<?> markerInterface)
		{
			// get the objects to move
			ZFindFilter filter =
				new ZMagBoundsFindFilter(new ZBounds(area), camera.getMagnification());
			ArrayList nodes = camera.findNodes(filter);
	
			Iterator iter = nodes.iterator();
			List<FigureFacet> figures = new ArrayList<FigureFacet>();
			while (iter.hasNext())
			{
				ZNode node = (ZNode) iter.next();
				FigureFacet figure = fromNodeToFigure(node);
				if (figure != null)
					if (markerInterface == null
						|| markerInterface.isAssignableFrom(figure.getClass()))
							figures.add(figure);
			}
	
			return figures.toArray(new FigureFacet[0]);
		}
	
	
		public SelectionFacet getSelection()
		{
			return selection;
		}
	
		public void selectAllFigures()
		{
			Iterator iter = views.keySet().iterator();

			int number = 0;
			while (iter.hasNext())
			{
				FigureFacet figure = (FigureFacet) iter.next();
				selection.addToSelection(figure.getActualFigureForSelection());
				number++;
			}
		}
	
		public void setCurrentTool(
			ToolFacet newCurrentTool,
			ToolCoordinatorFacet newCoordinator)
		{
			// turn the sweep layer off, as many tools leave debris here...
			turnSweepLayerOff();
	
			if (currentTool != null)
				currentTool.deactivate();
			currentTool = newCurrentTool;
			if (currentTool != null)
				currentTool.activate(this, cameraNode, newCoordinator);
			coordinator = newCoordinator;
			selection.connectToolCoordinatorFacet(coordinator);
		}
	
		public void turnSelectionLayerOff()
		{
			selection.turnSelectionLayerOff();
		}
	
		public void turnSelectionLayerOn()
		{
			selection.turnSelectionLayerOn();
		}
	
		public void turnSweepLayerOff()
		{
			selection.turnSweepLayerOff();
		}
	
		public void turnSweepLayerOn(ZNode node)
		{
			selection.turnSweepLayerOn(node);
		}
	
		public void turnDebugLayerOff()
		{
			debugLayer.removeAllChildren();
		}
	
		public void turnDebugLayerOn(ZNode node)
		{
			turnDebugLayerOff();
			debugLayer.addChild(node);
		}
	
		public DiagramFacet getDiagram()
		{
			return diagramFacet;
		}
		
		/**
		 * @see com.intrinsarc.idraw.foundation.DiagramFacet#makeContextMenu(DiagramViewFacet, ToolCoordinatorFacet, boolean)
		 */
		public JPopupMenu makeContextMenu(ToolCoordinatorFacet coordinator)
		{
			if (diagramViewContext != null)
			{
				return diagramViewContext.makeContextMenu(coordinator);
			}
			return null;
		}
		
		public SmartMenuContributorFacet getSmartMenuContributorFacet()
		{
			if (diagramViewContext != null)
			{
				return diagramViewContext.getSmartMenuContributorFacet();
			}
			return null;
		}
		
		/**
		 * @see com.intrinsarc.idraw.foundation.DiagramFacet#makeMiddleButtonCommand()
		 */
		public void middleButtonPressed()
		{
			if (diagramViewContext != null)
				diagramViewContext.middleButtonPressed();
		}
		
		/**
		 * @see com.intrinsarc.idraw.foundation.DiagramViewFacet#isUsingTools()
		 */
		public boolean isUsingTools()
		{
			return isUsingTools;
		}

		/**
		 * @see com.intrinsarc.idraw.foundation.DiagramViewFacet#getDrawBounds()
		 */
		public UBounds getDrawnBounds()
		{
			return new UBounds(objectLayer.getBounds());
		}

		/**
		 * @see com.intrinsarc.idraw.foundation.DiagramViewFacet#getCursorPoint()
		 */
		public UPoint getCursorPoint()
		{
			return cursorPoint;
		}

		public void addFigureToSelectionViaId(String id)
		{
			FigureFacet figure = figures.get(id);
			if (figure == null)
				return;
			selection.addToSelection(figure, true);
		}

    public synchronized void transitionToDiagram(DiagramFacet diagram, UBounds openRegionHint)
    {
      // if we are ignoring transitions for now, just return
      if (ignoringTransitions)
        return;
      
      // we are now in a transition, so indicate that we must ingore other transitions
      String transitionType = GlobalPreferences.preferences.getRawPreference(
          new Preference("Appearance", "Diagram transition effect", new PersistentProperty("none"))).asString();
      if (!transitionType.equals("none"))
        ignoringTransitions = true;
      
      diagramFacet = diagram;
      ZGroup oldObjectLayer = objectLayer;
      createObjectLayers();
      
      // sync up so we have all the new figures 
      syncWithDiagram(true);
      
      if (transitionType.equals("zoom"))
        handleZoomTransition(openRegionHint, oldObjectLayer);
       
    }
    
		public void setScalingBounds(UBounds fullScreen)
		{
			scale = fullScreen.getDimension();
			UBounds canvasBounds = new UBounds(canvas.getBounds());
			AffineTransform result = camera.getViewTransform();
			result.scale(canvasBounds.getWidth() / scale.getWidth(), canvasBounds.getHeight() / scale.getHeight());
			camera.setViewTransform(result);
		}

		public void setOriginalScaling()
		{
			scale = originalScale;
			AffineTransform result = camera.getViewTransform();
			result.scale(scale.getWidth(), scale.getHeight());
			camera.setViewTransform(result);

		}

    public void setBackdrop(ZNode backdrop)
    {
      backdropLayer.removeAllChildren();
      if (backdrop != null)
        backdropLayer.addChild(backdrop);
    }

    public void setBackgroundColor(Color background)
    {
      canvas.setBackground(background);
    }

		public List<DiagramFigureAdornerFacet> getAdorners()
		{
			return adorners;
		}
	}

	private void addFigureAndChildrenToView(FigureFacet figure)
	{
		// if this is a container, add contained
		ContainerFacet container = figure.getContainerFacet();
		if (container != null)
		{
			addFigureToDiagram(figure);
			Iterator iter = container.getContents();
			while (iter.hasNext())
			{
				// necessary cast
				FigureFacet child = (FigureFacet) iter.next();
				addFigureAndChildrenToView(child);
			}
		}
		else
			addFigureToDiagram(figure);
	}

	private FigureFacet fromNodeToFigure(ZNode node)
	{
		return (FigureFacet) node.getClientProperty("figure");
	}

	private void addFigureToDiagram(FigureFacet figure)
	{
		if (figure == null)
			return;

		// don't bother, if this is not showing
		if (!figure.isShowing())
			return;

		// if there already, don't bother
		if (views.containsKey(figure))
			return;

		// get a possible container
		ContainerFacet container = null;

		ContainedFacet contained = figure.getContainedFacet();
		if (contained != null)
		{
			container = contained.getContainer();
			containers.put(figure, container);
		}

		// (1) figure was added
		// store container details

		if (figure.getContainerFacet() != null)
		{
			ZGroup containerGroup = new ZGroup();
			
			containerGroup.addChild(figure.formView());
			
			// no need to lower as it must be empty
			views.put(figure, containerGroup);
			figures.put(figure.getId(), figure);
			ZGroup layer = getLayerFor(figure, container);
			if (layer != null)
				layer.addChild(containerGroup);
		}
		else
		{
			ZNode view = figure.formView();
			views.put(figure, view);
			figures.put(figure.getId(), figure);
			ZGroup layer = getLayerFor(figure, container);
			// some child adds will be omitted if they occur before their containers
			if (layer != null)
				getLayerFor(figure, container).addChild(view);
		}
		
		// possibly adorn
		adorn(figure);
	}

	private ZNode makeAdornments(FigureFacet figure)
  {
	  // clipboard and others have no adorners
	  if (adorners == null)
      return null;
    
	  int lp = 0;
	  boolean someAdorned = false;
    ZGroup group = new ZGroup();
	  for (DiagramFigureAdornerFacet adorner : adorners)
	  {
  	  // if the adorner is set, ask it to possible adorn the figure's view
	  	Map<FigureFacet, Integer> adornments = previouslyAdorned.get(lp);
	  	Integer style = adornments.get(figure);
  	  if (style != null)
  	  {
  	    group.addChild(adorner.adornFigure(figure, style));
  	    someAdorned = true;
  	  }
  	  lp++;
	  }
	  
	  if (someAdorned)
	    return group;
	  return null;
  }

  private void removeFigureFromDiagram(FigureFacet figure, boolean leaveInCache)
	{
		// (3) figure was removed
		// get possible old container
		ContainerFacet container = containers.get(figure);
		ZNode view = views.get(figure);
		ZGroup layer = getLayerFor(figure, container);
		if (layer != null)
			layer.removeChild(view);
		unadorn(figure);
		
		selection.removeFromSelection(figure);
		containers.remove(figure);
		views.remove(figure);
		figures.remove(figure.getId());
	}

	private void adjustFigureInDiagram(FigureFacet figure)
	{
		if (figure.hasSubjectBeenDeleted())
			return;
		
		// (2) figure was adjusted somehow
		ZNode oldView = views.get(figure);
		
		// possibly remove the adorner
		unadorn(figure);

		// if the figure is hidden, it will be in the diagram, but not in the view
		// don't bother adjusting in this case
		if (oldView == null)
		{
			// possibly, this is a show operation
			showFigureInView(figure);
			return;
		}

		// if the old view is not null, and the figure is hidden, adjust accordingly
		if (!figure.isShowing())
		{
			hideFigureFromView(figure);
			return;
		}

		ZNode newView = figure.formView();

		// handle a container by removing the 1st view from the group and adding back again
		if (figure.getContainerFacet() != null)
		{
			ZGroup oldGroup = (ZGroup) oldView;
			// remove the 1st element, and readd
			oldGroup.removeChild(0);
			oldGroup.addChild(newView);
			oldGroup.lower(newView); // take it to start position again
			newView = oldView;
			// reuse so we don't have to regenerate all the children, which may be ok...
		}
		
		// adorn?
		adorn(figure);
		
		// get the old container, and the new one
		ContainerFacet oldContainer = containers.get(figure);
		ContainerFacet newContainer = null;

		ContainedFacet contained = figure.getContainedFacet();
		if (contained != null)
			newContainer = contained.getContainer();

		containers.remove(figure);
		containers.put(figure, newContainer); // may be null
		ZGroup layer = getLayerFor(figure, oldContainer);
		if (layer != null)
			layer.removeChild(oldView);
		layer = getLayerFor(figure, newContainer);
		if (layer != null )
			layer.addChild(newView); // goes to the top
		
		views.remove(figure); // remove the "old" one, which may now be out of date
		figures.remove(figure.getId());
		views.put(figure, newView);
		figures.put(figure.getId(), figure);

		// handle the selection
		selection.adjusted(figure);
	}

	private void adorn(FigureFacet figure)
	{
		ZNode adorner = makeAdornments(figure);
		if (adorner != null)
		{
			adornerLayer.addChild(adorner);
			adorns.put(figure, adorner);
		}
	}
	
	private void unadorn(FigureFacet figure)
	{
		ZNode adorner = adorns.get(figure);
		if (adorner != null)
		{
			adornerLayer.removeChild(adorner);
			adorns.remove(figure);
		}
	}

	private void showFigureInView(FigureFacet figure)
	{
		addFigureToDiagram(figure);
	}
	
	private void hideFigureFromView(FigureFacet figure)
	{
		removeFigureFromDiagram(figure, true);
	}

	private ZGroup getLayerFor(FigureFacet figure, ContainerFacet container)
	{
		if (figure.useGlobalLayer())
			return globalLayer;
		if (container == null)
			return layer;

		// otherwise, get the zgroup by looking up the container
		ZGroup containerGroup = (ZGroup) views.get(container.getFigureFacet());
		if (containerGroup == null)
			return null;
		return containerGroup;
	}

	private PanningSizes makePanningSizes()
	{
		UDimension canvasDim = new UBounds(canvas.getBounds()).getDimension();
		canvasDim =
			new UDimension(
				canvasDim.getWidth() / scale.getWidth(),
				canvasDim.getHeight() / scale.getHeight());
		UDimension actualObjectDim =
			new UBounds(objectLayer.getBounds())
				.getBottomRightPoint()
				.subtract(
				new UPoint(0, 0));
		UDimension maxDim = actualObjectDim.maxOfEach(canvasDim);
		if (actualObjectDim.getWidth() > canvasDim.getWidth())
			maxDim = maxDim.add(new UDimension(100, 0));
		if (actualObjectDim.getHeight() > canvasDim.getHeight())
			maxDim = maxDim.add(new UDimension(0, 100));

		PanningSizes sizes = new PanningSizes();
		sizes.canvasDim = canvasDim;
		sizes.actualObjectDim = actualObjectDim;
		sizes.maxDim = maxDim;
		sizes.viewPort = new UBounds(new UPoint(panX, panY), canvasDim);
		return sizes;
	}

	private void adjustScrollBars()
	{
		if (scrollerFacet != null)
		{
			PanningSizes sizes = makePanningSizes();
			scrollerFacet.adjustScroll(
				sizes.actualObjectDim,
				sizes.canvasDim,
				sizes.viewPort,
				sizes.maxDim);
		}
	}
	
  private void handleZoomTransition(UBounds openRegionHint, ZGroup oldObjectLayer)
  {
    // remove the new object layer in order to scale and transform
    pickLayer.removeChild(objectLayer);
    
    // if we are scaling into a package, then make the current scale up,
    // and the new one scale up also, from the open region hint area
    UBounds canvasBounds = new UBounds(canvas.getBounds());      
    LayerAnimator oldScaling = null;
    LayerAnimator newScaling = null;
    
    // if we have an hint about which region this was opened from, fly into the region,
    // otherwise, fly out and do it to the centre
    if (openRegionHint != null)
    {
      oldScaling =
        new LayerAnimator(
            oldObjectLayer,
            openRegionHint,
            openRegionHint,
            canvasBounds,
            true,
            false);
      UBounds objectBounds = new UBounds(
          UPoint.ZERO,
          new UBounds(objectLayer.getBounds()).getBottomRightPoint());
      newScaling =
        new LayerAnimator(
            objectLayer,
            objectBounds,
            openRegionHint,
            objectBounds,
            false,
            false);
    }
    else
    {
      // scale the current screen into a small space...
      UDimension smallDimension = new UDimension(16, 16);
      UBounds smallSpace =
        new UBounds(
            canvasBounds.getMiddlePoint().subtract(smallDimension),
            canvasBounds.getMiddlePoint().add(smallDimension));
      UDimension smallDimension2 = new UDimension(100, 100);
      UBounds smallSpace2 =
        new UBounds(
            canvasBounds.getMiddlePoint().subtract(smallDimension2),
            canvasBounds.getMiddlePoint().add(smallDimension2));
      
      oldScaling =
        new LayerAnimator(
            oldObjectLayer,
            canvasBounds,
            canvasBounds,
            smallSpace,
            true,
            true);
      newScaling =
        new LayerAnimator(
            objectLayer,
            smallSpace2,
            canvasBounds,
            smallSpace2,
            false,
            true);        
    }

    // animate the layer
    animateLayer(
        oldScaling,
        newScaling,          
        TRANSITION_TIME_MSECS /* length of animation in msecs */);
  }

  private void animateLayer(
      final LayerAnimator oldScaling,
      final LayerAnimator newScaling,
      long msecsLength)
  {
    final ZAnimation taskPerformer = new ZAnimation()
    {
      protected void animationStarted()
      {
        super.animationStarted();
        if (USE_LOW_QUALITY_FOR_TRANSITIONS)
          setHighQuality(false);
      }

      protected void animationStopped()
      {
        super.animationStopped();

        // restore
        if (USE_LOW_QUALITY_FOR_TRANSITIONS)
          setHighQuality(true);
        coordinator.restoreInput();
        oldScaling.remove(pickLayer);
        newScaling.cement(pickLayer);
        ignoringTransitions = false;
      }
      
      protected void animateFrameForTime(long aTime)
      {
        super.animateFrameForTime(aTime);

        float ratio = getAlpha().value(aTime);
        oldScaling.moveToCycle(ratio);
        newScaling.moveToCycle(ratio);
      }
    };
    
    // add to the screen
    oldScaling.add(pickLayer);
    newScaling.add(pickLayer);

    // schedule the animation
    ZAlpha alpha = new ZAlpha(
        1,
        msecsLength);
    taskPerformer.setAlpha(alpha);
    taskPerformer.play();
  }
}