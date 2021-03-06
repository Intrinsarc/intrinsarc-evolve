package com.intrinsarc.idraw.nodefacilities.nodesupport;

import java.util.*;

import javax.swing.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;

import edu.umd.cs.jazz.*;

/**
 *
 * (c) Andrew McVeigh 27-Jul-02
 *
 */
public interface BasicNodeAppearanceFacet extends Facet
{
	/**
	 * persistence
	 */
  public void addToPersistentProperties(PersistentProperties properties);

	public ZNode formView();
	public UBounds getFullBoundsForContainment();
	public UBounds getRecalculatedFullBounds(boolean diagramResize);
  public String getFigureName();
  public UBounds getAutoSizedBounds(boolean autoSized);
  public PreviewFacet makeNodePreviewFigure(PreviewCacheFacet previews, DiagramFacet diagram, UPoint start, boolean isFocus);
  public Manipulators getSelectionManipulators(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, boolean favoured, boolean firstSelected, boolean allowTYPE0Manipulators);
	public UDimension getCreationExtent();
	public FigureFacet getActualFigureForSelection();
	public boolean acceptsContainer(ContainerFacet container);
	public Set<String> getDisplayStyles(boolean anchorIsTarget);
	public ToolFigureClassification getToolClassification(UPoint point, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator);
	
	/** right press and middle press */ 
	public JPopupMenu makeContextMenu(DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator);
	public void middleButtonPressed(ToolCoordinatorFacet coordinator);

	/** view update management */
	public Object getSubject();
	public boolean hasSubjectBeenDeleted();
	public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass);
  public void produceEffect(ToolCoordinatorFacet coordinator, String effect, Object[] parameters);

  /** form a command after a visual move has taken place */
  public void performPostContainerDropTransaction();
	public boolean canMoveContainers();
	public boolean isSubjectReadOnlyInDiagramContext(boolean kill);

	public void acceptPersistentFigure(PersistentFigure pfig);

}
