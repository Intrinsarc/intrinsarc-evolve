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
  public void setText(String text, Object listSelection, boolean unsuppress);
  
  public FigureFacet getFigureFacet();
}