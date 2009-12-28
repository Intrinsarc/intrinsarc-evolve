package com.hopstepjump.idraw.figurefacilities.textmanipulationbase;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;


public interface TextableFacet extends Facet
{
	public UBounds getTextBounds(String text);
  public UBounds vetTextResizedExtent(String text);
  
  public JList formSelectionList(String textSoFar);
  public Object setText(String text, Object listSelection, boolean unsuppress, Object oldMemento);
  public void unSetText(Object memento);
  
  public FigureFacet getFigureFacet();
}