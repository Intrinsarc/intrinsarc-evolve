package com.intrinsarc.evolve.umldiagrams.containmentarc;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.arcfacilities.arcsupport.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public final class ContainmentArcAppearanceFacetImpl implements BasicArcAppearanceFacet
{
	static final String FIGURE_NAME = "containment link";

	public ContainmentArcAppearanceFacetImpl()
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

		// make the possible composite symbol
		ZEllipse circle =
			new ZEllipse(
				start.getX() - 10,
				start.getY() - 20,
				20,
				20);
		circle.setPenPaint(Color.black);
		circle.setFillPaint(Color.white);
		ZPolygon cross = new ZPolygon(start);
		cross.add(start.add(new UDimension(0, -20)));
		cross.add(start.add(new UDimension(0, -10)));
		cross.add(start.add(new UDimension(-10, -10)));
		cross.add(start.add(new UDimension(10, -10)));
		cross.setClosed(false);
		cross.setPenPaint(Color.black);
		cross.setFillPaint(null);
		UDimension crossDimension = second.subtract(start);
		ZTransformGroup crossGroup =
			new ZTransformGroup(new ZVisualLeaf(circle));
		crossGroup.addChild(new ZVisualLeaf(cross));
		crossGroup.rotate(
			crossDimension.getRadians() + Math.PI / 2,
			start.getX(),
			start.getY());

		// add the thin line
		group.addChild(new ZVisualLeaf(mainArc));

		crossGroup.setChildrenFindable(false);
		crossGroup.setChildrenPickable(false);
		group.addChild(crossGroup);

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
