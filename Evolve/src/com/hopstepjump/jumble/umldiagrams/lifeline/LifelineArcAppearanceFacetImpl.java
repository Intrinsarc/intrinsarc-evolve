package com.hopstepjump.jumble.umldiagrams.lifeline;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public final class LifelineArcAppearanceFacetImpl implements BasicArcAppearanceFacet
{
  static final String FIGURE_NAME = "lifeline";

  public LifelineArcAppearanceFacetImpl()
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
    // make the thin and thick lines
    ZGroup group = new ZGroup();

    // add the thin line
    mainArc.setStroke(
        new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{4,4}, 0));
    mainArc.setPenPaint(Color.MAGENTA);
    group.addChild(new ZVisualLeaf(mainArc));

    group.setChildrenFindable(false);
    group.setChildrenPickable(true);
    return group;
  }
  
	/**
	 * @see com.hopstepjump.jumble.arcfacilities.arcsupport.BasicArcAppearanceFacet#getFigureName()
	 */
	public String getFigureName()
	{
		return FIGURE_NAME;
	}

	/**
	 * @see com.hopstepjump.idraw.arcfacilities.arcsupport.BasicArcAppearanceFacet#addToPersistentProperties(PersistentProperties)
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

	public Command makeReanchorCommand(AnchorFacet start, AnchorFacet end)
	{
		return null;
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
