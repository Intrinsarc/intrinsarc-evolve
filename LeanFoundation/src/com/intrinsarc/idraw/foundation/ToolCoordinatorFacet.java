package com.intrinsarc.idraw.foundation;

import java.awt.*;

import javax.swing.*;

import com.intrinsarc.easydock.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.environment.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;


/**
 * ToolCoordinator coordinates the activies of a number of tools, and allow them to emit commands.
 */

public interface ToolCoordinatorFacet extends PopupMakerFacet, TransactionManagerFacet
{	
	void commitTransaction(boolean fullyCommitCurrentDiagramInForeground);
	boolean inTransaction();
	
  public void attachTo(DiagramViewFacet diagramView, ZCanvas canvas, ZNode mouseNode);
  public DiagramViewFacet getCurrentDiagramView();
  
  public void attachToFrame(JFrame frame, PaletteManagerFacet palette, IEasyDock dock);
	public Cursor displayWaitCursor();
	public Cursor displayCursorForAWhile(int cursor, int msecs);
	public void restoreCursor(Cursor oldCursor);
  public void reestablishCurrentTool();
	public void blockInput();
	public void restoreInput();
  public UPoint getLastMouseLocation();

  public void toolFinished(ZMouseEvent event, boolean stopMultiTool);

  public int invokeYesNoCancelDialog(String title, Object message);
  public int invokeErrorDialog(String title, Object message);
  public int invokeAsDialog(ImageIcon icon, String title, JComponent contents, JComponent buttons[], int defaultButton, Runnable runAfterShown);
  public void changedDiagram(DiagramFacet view);
  public IEasyDock getDock();
	public int getFrameXPreference(Preference xPreference);
	public int getFrameYPreference(Preference yPreference);
	public int getIntegerPreference(Preference preference);
	public void dismissCurrentDialog();
	public void invokeLater(Runnable runnable);
} 