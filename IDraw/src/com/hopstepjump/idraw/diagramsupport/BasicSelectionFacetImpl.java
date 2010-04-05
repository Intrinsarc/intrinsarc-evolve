package com.hopstepjump.idraw.diagramsupport;

import java.util.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;


final class BasicSelectionFacetImpl implements SelectionFacet
{
  private static final int NONE_SELECTED_STATE = 1;
  private static final int ONE_SELECTED_STATE = 2;
  private static final int N_SELECTED_STATE  = 3;

  private int state = NONE_SELECTED_STATE;

  private DiagramViewFacet diagramView;
  private ZLayerGroup selectionLayer = new ZLayerGroup();
  private ZLayerGroup sweepLayer = new ZLayerGroup();
  private Map<FigureFacet, Manipulators> selectedFigures = new HashMap<FigureFacet, Manipulators>();
  private Manipulators favouredManipulators;
  private FigureFacet favouredSelectable;
  private ZCanvas canvas;
  private Set<SelectionListenerFacet> listeners = new HashSet<SelectionListenerFacet>();
  private FigureFacet firstSelected;
	private ToolCoordinatorFacet coordinator;

  public BasicSelectionFacetImpl(DiagramViewFacet diagramView, ZGroup selectingGroup, ZGroup marqueeGroup, ZCanvas canvas)
  {
    this.diagramView = diagramView;
    selectionLayer.editor().getSpatialIndexGroup();
    selectingGroup.addChild(selectionLayer);
    selectingGroup.addChild(sweepLayer);
    this.canvas = canvas;
    turnSelectionLayerOn();
  }
    
	/**
	 * @see com.hopstepjump.idraw.foundation.SelectionFacet#addToSelection(FigureFacet, boolean)
	 */
	public void addToSelection(
		FigureFacet oneSelectable,
		boolean allowTYPE0Manipulator)
	{
		if (oneSelectable != null)
		{
			FigureFacet selectable = oneSelectable.getActualFigureForSelection();
			
			// must be on diagram or we can't select even if we want to
			if (!diagramView.getDiagram().contains(selectable))
				return;
			
	    if (selectable != null && !isSelected(selectable))
	    {
	      switch (state)
	      {
	        case NONE_SELECTED_STATE:
	          transition_from_NONE_SELECTED_STATE_to_ONE_SELECTED_STATE(selectable, allowTYPE0Manipulator);
	          break;
	        case ONE_SELECTED_STATE:
	          transition_from_ONE_SELECTED_STATE_to_N_SELECTED_STATE(selectable);
	          break;
	        case N_SELECTED_STATE:
	          transition_from_N_SELECTED_STATE_to_N_SELECTED_STATE_added(selectable);
	          break;
	      }
	    }
		}
	}

  public void addToSelection(FigureFacet selectable)
  {
  	addToSelection(selectable, false);
	}
	
  public void removeFromSelection(FigureFacet oneSelectable)
  {
		//FigureFacet selectable = oneSelectable.getActualFigureForSelection();
		
		// only remove the figure if it was the one that was selectable
		// this rule only really works for when the child is not selectable
		// but the child is being asked to be removed from view
		// -- this check prevents the parent from being removed unnecessarily
    if (isSelected(oneSelectable))
    {
      switch (state)
      {
        case NONE_SELECTED_STATE:
          // nothing to do -- can't occur actually :-)
          break;
        case ONE_SELECTED_STATE:
          transition_from_ONE_SELECTED_STATE_to_NONE_SELECTED_STATE();
          break;
        case N_SELECTED_STATE:
          int newSize = selectedFigures.size() - 1;
          if (newSize == 0)
            transition_from_N_SELECTED_STATE_to_NONE_SELECTED_STATE();
          else
          if (newSize == 1)
            transition_from_N_SELECTED_STATE_to_ONE_SELECTED_STATE(oneSelectable);
          else
            transition_from_N_SELECTED_STATE_to_N_SELECTED_STATE_removed(oneSelectable);
          break;
      }
    }
  }
	
  public void clearAllSelection()
  {
      switch (state)
      {
        case NONE_SELECTED_STATE:
          // nothing to do
          break;
        case ONE_SELECTED_STATE:
          transition_from_ONE_SELECTED_STATE_to_NONE_SELECTED_STATE();
          break;
        case N_SELECTED_STATE:
          transition_from_N_SELECTED_STATE_to_NONE_SELECTED_STATE();
          break;
      }
  }

  public void addToSelection(FigureFacet[] selectables)
  {
    for (int lp = 0; lp < selectables.length; lp++)
      addToSelection(selectables[lp]);
  }


  public Set<FigureFacet> getSelectedFigures()
  {
    return new HashSet<FigureFacet>(selectedFigures.keySet());
  }
	
  public Manipulators getFavouredManipulatorsOfSingleSelection()
  {
    if (state == ONE_SELECTED_STATE)
      return favouredManipulators;
    return null;
  }

  public FigureFacet getSingleSelection()
  {
    if (state == ONE_SELECTED_STATE)
      return favouredSelectable;
    return null;
  }

  public boolean isSelected(FigureFacet selectable)
  {
    if (selectable == null)
      return false;
    return selectedFigures.containsKey(selectable);
  }
	
  public UBounds getSelectionBounds()
  {
    Iterator iter = selectedFigures.keySet().iterator();
    UBounds bounds = null;
    while (iter.hasNext())
    {
      ZNode node = ((FigureFacet) iter.next()).formView();

      if (bounds == null)
        bounds = new UBounds(node.getBounds());
      else
      	bounds = bounds.union(node.getBounds());
    }
    if (bounds == null)
      return new UBounds(new UPoint(0,0), new UDimension(0,0));
    return bounds;
  }
	
	public void addSelectionListener(SelectionListenerFacet listener)
	{
		listeners.add(listener);
	}

	public void removeSelectionListener(SelectionListenerFacet listener)
	{
		listeners.remove(listener);
	}

  /**
   * start of public methods which can invoke the state machine
   */

  public void adjusted(FigureFacet selectable)
  {
    // redo the selection figure, but don't disturb the list
    if (isSelected(selectable))
    {
      switch (state)
      {
        case NONE_SELECTED_STATE:
          break;
        case ONE_SELECTED_STATE:
          adjust_ONE_SELECTED_STATE(selectable);
          break;
        case N_SELECTED_STATE:
          adjust_N_SELECTED_STATE(selectable);
          break;
      }
    }
  }

  /**
   * end of public methods which can invoke state machine
   */

  /**
   * start of state machine
   *
   */

  private void transition_from_NONE_SELECTED_STATE_to_ONE_SELECTED_STATE(FigureFacet selectable, boolean allowTYPE0Manipulators)
  {
    // this is favoured
    favouredSelectable = selectable;
    firstSelected = selectable;
    favouredManipulators = selectable.getSelectionManipulators(
        coordinator, diagramView, true, true, allowTYPE0Manipulators);
    selectedFigures.put(selectable, favouredManipulators);
    favouredManipulators.addToView(selectionLayer, canvas);
    state = ONE_SELECTED_STATE;
    tellListenersAboutSingleSelection();
  }

  private void transition_from_ONE_SELECTED_STATE_to_N_SELECTED_STATE(FigureFacet figure)
  {
    // clear out the initial favoured, and add it back as unfavoured
    favouredManipulators.cleanUp();
    Manipulators unfavoured = favouredSelectable.getSelectionManipulators(
        coordinator, diagramView, false, true, false);
	  selectedFigures.put(favouredSelectable, unfavoured);
	  unfavoured.addToView(selectionLayer, canvas);
    favouredSelectable = null;
    favouredManipulators = null;

    // add the unfavoured manipulators for the new selection
    Manipulators newUnfavoured = figure.getSelectionManipulators(coordinator, diagramView, false, false, false);
    newUnfavoured.addToView(selectionLayer, canvas);
    selectedFigures.put(figure, newUnfavoured);
    state = N_SELECTED_STATE;
  }

  private void transition_from_N_SELECTED_STATE_to_N_SELECTED_STATE_added(FigureFacet figure)
  {
    // add the unfavoured manipulators for the new selection
    Manipulators newUnfavoured = figure.getSelectionManipulators(coordinator, diagramView, false, false, false);
    newUnfavoured.addToView(selectionLayer, canvas);
    selectedFigures.put(figure, newUnfavoured);
  }

  private void transition_from_N_SELECTED_STATE_to_ONE_SELECTED_STATE(FigureFacet figure)
  {
    Manipulators old = selectedFigures.get(figure);
    old.cleanUp();
    selectedFigures.remove(figure);

    // redo the one left, with favoured status
    favouredSelectable = selectedFigures.keySet().toArray(new FigureFacet[0])[0];
    firstSelected = favouredSelectable;
    Manipulators oldManip = selectedFigures.get(favouredSelectable);
    oldManip.cleanUp();
    favouredManipulators = favouredSelectable.getSelectionManipulators(coordinator, diagramView, true, true, false);
    selectedFigures.put(favouredSelectable, favouredManipulators);
    favouredManipulators.addToView(selectionLayer, canvas);
    state = ONE_SELECTED_STATE;
    tellListenersAboutSingleSelection();
  }

  private void transition_from_N_SELECTED_STATE_to_N_SELECTED_STATE_removed(FigureFacet figure)
  {
    Manipulators oldManip = selectedFigures.get(figure);
    oldManip.cleanUp();
    Manipulators previous = selectedFigures.get(figure);
    selectedFigures.remove(figure);
    
    // pick a new firstSelected, if the deselected figure was the first
    if (figure == firstSelected)
    {
      firstSelected = (FigureFacet) selectedFigures.keySet().toArray()[0];
      previous.cleanUp();
      
      Manipulators next = firstSelected.getSelectionManipulators(coordinator, diagramView, false, true, false);
      next.addToView(selectionLayer, canvas);
      selectedFigures.put(firstSelected, next);      
    }
  }

  private void transition_from_ONE_SELECTED_STATE_to_NONE_SELECTED_STATE()
  {
    favouredManipulators.cleanUp();
    selectedFigures = new HashMap<FigureFacet, Manipulators>();
    selectionLayer.removeAllChildren();  // just in case
    state = NONE_SELECTED_STATE;
    firstSelected = null;
    favouredSelectable = null;
    favouredManipulators = null;
  }

  private void transition_from_N_SELECTED_STATE_to_NONE_SELECTED_STATE()
  {
    Iterator iter = selectedFigures.values().iterator();
    while (iter.hasNext())
    {
      Manipulators manips = (Manipulators) iter.next();
      manips.cleanUp();
    }
    selectedFigures = new HashMap<FigureFacet, Manipulators>();
    selectionLayer.removeAllChildren();  // just in case
    state = NONE_SELECTED_STATE;
    firstSelected = null;
    favouredSelectable = null;
    favouredManipulators = null;
  }

  private void adjust_ONE_SELECTED_STATE(FigureFacet selectable)
  {
    // this is favoured
    favouredSelectable = selectable;  // need to replace with the new one, as the old may be out of date
    firstSelected = selectable;
    favouredManipulators.cleanUp();
    favouredManipulators = favouredSelectable.getSelectionManipulators(coordinator, diagramView, true, true, false);
    favouredManipulators.addToView(selectionLayer, canvas);
    selectedFigures.remove(favouredSelectable);  // need to remove, so that it can be replaced by the more up to date figure
    selectedFigures.put(favouredSelectable, favouredManipulators);
  }

  private void adjust_N_SELECTED_STATE(FigureFacet figure)
  {
    // this is not favoured
    Manipulators manips = selectedFigures.get(figure);
    manips.cleanUp();

    Manipulators newManips = figure.getSelectionManipulators(
        coordinator, diagramView, false, figure == firstSelected, false);
    newManips.addToView(selectionLayer, canvas);
		selectedFigures.remove(figure);  // need to remove and replace with the new figure, which will be more up to date
    selectedFigures.put(figure, newManips);
  }

  /**
   * end of state machine
   *
   */

  public void turnSweepLayerOn(ZNode sweepNode)
  {
    sweepLayer.removeAllChildren();
    sweepLayer.addChild(sweepNode);
  }

  public void turnSweepLayerOff()
  {
    sweepLayer.removeAllChildren();
  }

  public void turnSelectionLayerOn()
  {
    selectionLayer.editor().removeInvisibleGroup();
    selectionLayer.setChildrenPickable(true);
  }

  public void turnSelectionLayerOff()
  {
    selectionLayer.editor().getInvisibleGroup();
    selectionLayer.setChildrenPickable(false);
  }

  void moveManipulatorToNewLayer(ManipulatorFacet manipulator)
  {
    // it is currently on the selection layer
    manipulator.cleanUp();
    turnSweepLayerOff();
    manipulator.addToView(sweepLayer, canvas);
  }

  void moveManipulatorToSelectionLayer(ManipulatorFacet manipulator)
  {
    // it is currently on the selection layer
    manipulator.cleanUp();
    turnSelectionLayerOn();
    turnSweepLayerOff();
    manipulator.addToView(selectionLayer, canvas);
  }
  
	private void tellListenersAboutSingleSelection()
	{
		Iterator iter = listeners.iterator();
		while (iter.hasNext())
		{
			((SelectionListenerFacet) iter.next()).haveSingleSelection();
		}
	}

	/**
	 * @see com.hopstepjump.idraw.foundation.FigureChosenFacet#isChosen(FigureFacet)
	 */
	public boolean isChosen(FigureFacet figure)
	{
		return isSelected(figure);
	}

  public FigureFacet getFirstSelectedFigure()
  {
    return firstSelected;
  }


  public Set<FigureFacet> getTopLevelSelectedFigures()
  {
    Set<FigureFacet> figures = getSelectedFigures();
    Set<FigureFacet> top = new HashSet<FigureFacet>();
    
    // only include things which are truly top
    for (FigureFacet figure : figures)
      if (!containedByAnother(figures, figure))
        top.add(figure);
    return top;
  }


  private boolean containedByAnother(Set<FigureFacet> figures, FigureFacet figure)
  {
    FigureFacet parent = figure;
    while (parent != null)
    {
      if (parent != figure && figures.contains(parent))
        return true;
      ContainedFacet contained = parent.getContainedFacet();
      if (contained == null)
        break;
      ContainerFacet container = contained.getContainer();
      if (container == null)
        break;
      parent = container.getFigureFacet();
    }
    return false;
  }
  
  public void connectToolCoordinatorFacet(ToolCoordinatorFacet coordinator)
  {
  	this.coordinator = coordinator;
  }
}
