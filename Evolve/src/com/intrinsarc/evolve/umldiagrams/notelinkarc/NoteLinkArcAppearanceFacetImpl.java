package com.intrinsarc.evolve.umldiagrams.notelinkarc;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.arcfacilities.arcsupport.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;


public final class NoteLinkArcAppearanceFacetImpl implements BasicArcAppearanceFacet
{
  final static String figureName = "note link";

  public NoteLinkArcAppearanceFacetImpl()
  {
  }

	public ZNode formAppearance(
		ZShape mainArc,
		UPoint start,
		UPoint second,
		UPoint secondLast,
		UPoint last,
		CalculatedArcPoints calculated, boolean curved)
  {
    mainArc.setStroke(
    	new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{3,5}, 0));

		return new ZVisualLeaf(mainArc);
  }
  
	/**
	 * @see com.intrinsarc.jumble.arcfacilities.arcsupport.BasicArcAppearanceFacet#getFigureName()
	 */
	public String getFigureName()
	{
		return figureName;
	}

	/**
	 * @see com.intrinsarc.idraw.arcfacilities.arcsupport.BasicArcAppearanceFacet#addToPersistentProperties(PersistentProperties)
	 */
	public void addToPersistentProperties(PersistentProperties properties)
	{
	}

  public void addToContextMenu(JPopupMenu menu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
  {
  }

	public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
	{
		return true;
	}

	public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
	{
	}

	public Object getSubject()
	{
		return null;
	}

	public boolean hasSubjectBeenDeleted()
	{
		return false;
	}

	public void makeReanchorAction(AnchorFacet start, AnchorFacet end)
	{
	}

  public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
  {
    return false;
  }

  public Set<String> getPossibleDisplayStyles(AnchorFacet anchor)
  {
    return null;
  }

	public void acceptPersistentProperties(PersistentFigure pfig)
	{
	}
}