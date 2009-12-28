package com.hopstepjump.idraw.foundation;

import java.util.*;

import com.hopstepjump.geometry.*;


/**
 * contains all the elements that are highlighted, and of greater interest.
 */

public interface SelectionFacet extends ChosenFiguresFacet
{
  public void addToSelection(FigureFacet figure);
  public void addToSelection(FigureFacet figure, boolean allowTYPE0Manipulator);
  public void addToSelection(FigureFacet[] figures);
  public Manipulators getFavouredManipulatorsOfSingleSelection();
  public FigureFacet getSingleSelection();
  public void removeFromSelection(FigureFacet figure);
  public void clearAllSelection();
  public boolean isSelected(FigureFacet figure);
  public UBounds getSelectionBounds();
	public Set<FigureFacet> getSelectedFigures();
  public Set<FigureFacet> getTopLevelSelectedFigures();
	public FigureFacet getFirstSelectedFigure();
  
  public void addSelectionListener(SelectionListenerFacet listener);
  public void removeSelectionListener(SelectionListenerFacet listener);
}
