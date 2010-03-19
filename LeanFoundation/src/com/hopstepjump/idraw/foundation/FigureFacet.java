package com.hopstepjump.idraw.foundation;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.persistence.*;

import edu.umd.cs.jazz.*;


/**
 * See FigureValidator for the rules about which facets a given type needs to support, and which
 * facets it cannot support.
 */

public interface FigureFacet extends MainFacet
{
	public static final int NODE_TYPE       = 0;
	public static final int CONTAINER_TYPE  = 1;
	public static final int ARC_TYPE        = 2;
	
	/**
	 * persistence
	 */
  public PersistentFigure makePersistentFigure();

  public ZNode formView();
  public boolean useGlobalLayer();
  
 	public Command updateViewAfterSubjectChanged(boolean isTop, ViewUpdatePassEnum pass);
 	public Object getSubject();
 	public boolean hasSubjectBeenDeleted();
	public void aboutToAdjust();
  public void adjusted();

  /**
   * identity management
   */
  
  public String getId();
  public FigureReference getFigureReference();
  public DiagramFacet getDiagram();
  public String getFigureName();
  public int getType();
  
	/**
	 * showing and hiding
	 */
	
  public void setShowing(boolean showing);
  public boolean isShowing();
  /** the full bounds of the shape, that handles will usually be shown around */
  public UBounds getFullBounds();
  /** the full bounds of the figure, including all children figures desired */
  public UBounds getFullBoundsForContainment();
  /**
   * get the recalculated bounds for diagram resize purposes, or for resizing
   * in the node creation tool
   */
  public UBounds getRecalculatedFullBoundsForDiagramResize(boolean diagramResize);

  /**
   * selection methods
   * @param coordinator TODO
   */
  public Manipulators getSelectionManipulators(
      ToolCoordinatorFacet coordinator,
      DiagramViewFacet diagramView,
      boolean favoured,
      boolean firstSelected, boolean allowTYPE0Manipulators);
  public FigureFacet getActualFigureForSelection();

  /** each figure can define a popup menu */
  public JPopupMenu makeContextMenu(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator);
  public Command middleButtonPressed(ToolCoordinatorFacet coordinator);
  public void produceEffect(ToolCoordinatorFacet coordinator, String effect, Object[] parameters);
  
  public void cleanUp();
  
  /**
   * figure previewing
   */
  
  public void addPreviewToCache(DiagramFacet diagram, PreviewCacheFacet figuresToPreview, UPoint start, boolean addMyself);
  public PreviewFacet getSinglePreview(DiagramFacet diagram);
  
  /**
   * ports that can be supported: note the support regime:
   * NODE_TYPE:      supports getContainedFacet() and getAnchorFacet() only
   * CONTAINER_TYPE: supports getContainerFacet(), getContainedFacet() and getAnchorFacet() only
   * ARC_TYPE:       supports getLinkFacet() and getAnchorFacet() only
   */
  
  /** nodes and containers can in turn be contained */
  public ContainedFacet getContainedFacet();
  /** containers alone support containment */
  public ContainerFacet getContainerFacet();
  /** arcs support the ability to be the target of a link.  This is optional for containers and nodes */
  public AnchorFacet getAnchorFacet();
  /** arcs alone support linking */
  public LinkingFacet getLinkingFacet();
  
  /** support for the clipboard is optional */
  public ClipboardFacet getClipboardFacet();
  public ClipboardCommandsFacet getClipboardCommandsFacet();
  
  /** form a command to delete this figure from the diagram */
  public Command formDeleteCommand();
  public boolean isSubjectReadOnlyInDiagramContext(boolean kill);
  public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator);

	public void acceptPersistentFigure(PersistentFigure pfig);
}
