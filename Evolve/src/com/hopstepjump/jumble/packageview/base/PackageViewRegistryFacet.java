package com.hopstepjump.jumble.packageview.base;

import java.util.*;

import org.eclipse.uml2.Package;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.guibase.*;



/**
 *
 * (c) Andrew McVeigh 12-Sep-02
 *
 */
public interface PackageViewRegistryFacet extends Facet
{
	/** open editor will look for a reusable editor of the same type as specified, or create another if there are none */
	public void open(Package pkg, boolean closeable, boolean openNewTab, UBounds openRegionHint, Package possibleFixedPerspective, boolean addToStack);
	public void openClipboard(String name, boolean explicitlyMaximize, boolean closeable, List<DiagramFigureAdornerFacet> adorners, Package possibleFixedPerspective);
	public void clone(ReusableDiagramViewFacet view);
	public void haveClosed(ReusableDiagramViewFacet view);
	public void haveFocus(ReusableDiagramViewFacet view);
  
	/** used by package figure to see if the diagram is empty -- should probably be somewhere else */
	public boolean isDiagramEmpty(Package pkg);
	
	/** remove all resources, such as diagram listeners */
	public void aboutToClose();
	
	/** reconnect to the tool coordinator -- needed when window/browser focus changes */
	public void reattachToCoordinator();
	
	public KeyInterpreterFacet getClipboardKeyInterpreter();
	public KeyInterpreterFacet getDiagramKeyInterpreter();
	public DiagramFacet getClipboardDiagram(String name);
  public void reset();
  
  public List<DiagramFacet> getViewedDiagrams();
	public ReusableDiagramViewFacet getFocussedView();
}
