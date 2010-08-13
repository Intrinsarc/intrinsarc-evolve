package com.intrinsarc.idraw.arcfacilities.arcsupport;

import java.util.*;

import javax.swing.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 *
 * (c) Andrew McVeigh 28-Jul-02
 *
 */
public interface BasicArcAppearanceFacet extends Facet
{
	/**
	 * persistence
	 */
	public void addToPersistentProperties(PersistentProperties properties);
	public void acceptPersistentProperties(PersistentFigure pfig);

	/** form the appearance possibly using the main arc
	 * 
	 * @param mainArc
	 * @param second
	 * @param secondLast
	 * @param last
	 * @param calculated
	 * @param curved TODO
	 * @param first
	 * @return
	 */
	public ZNode formAppearance(
		ZShape mainArc,
		UPoint start,
		UPoint second,
		UPoint secondLast,
		UPoint last,
		CalculatedArcPoints calculated, boolean curved);
		
	public String getFigureName();

  public Set<String> getPossibleDisplayStyles(AnchorFacet anchor);
  
  
  void addToContextMenu(
      JPopupMenu menu,
      DiagramViewFacet diagramView,
      ToolCoordinatorFacet coordinator);

	public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end);
	public void makeReanchorAction(AnchorFacet start, AnchorFacet end);

	public Object getSubject();

	public boolean hasSubjectBeenDeleted();

	public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass);

  public boolean isSubjectReadOnlyInDiagramContext(boolean kill);
}
