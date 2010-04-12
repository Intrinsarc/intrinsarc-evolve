package com.hopstepjump.idraw.foundation;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

public interface PaletteManagerFacet extends Facet
{
  void attachTo(DiagramViewFacet diagramView, ZCanvas newCanvas, ZNode newMouseNode);
  void activateLatestSelectionTool();
  UPoint getLastMouseLocation();
  void reestablishCurrentTool();
  void toolFinished(ZMouseEvent e, boolean stopMultiTool);
  DiagramViewFacet getCurrentDiagramView();
  JComponent getPaletteComponent();
  void refreshEnabled();
	void setFocus(String focus);
	String getFocus();
}
