package com.hopstepjump.jumble.umldiagrams.inheritancearc;


import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.repositorybase.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * an inheritance figure
 * @author Andrew McVeigh
 *
 */
public final class InheritanceArcAppearanceFacetImpl implements BasicArcAppearanceFacet
{
  static final String FIGURE_NAME = "inheritance link";
	private Generalization subject;
	private FigureFacet figureFacet;

  public InheritanceArcAppearanceFacetImpl(FigureFacet figureFacet, Generalization subject)
  {
  	this.figureFacet = figureFacet;
  	this.subject = subject;
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

    // make the arrowhead
    ZPolygon poly = new ZPolygon(last);
    poly.add(last.add(new UDimension(-8, -20)));
    poly.add(last.add(new UDimension(8, -20)));
    poly.add(last);
    poly.setPenPaint(Color.black);
    poly.setFillPaint(Color.white);
    UDimension dimension = secondLast.subtract(last);
    ZTransformGroup arrowGroup = new ZTransformGroup(new ZVisualLeaf(poly));
    arrowGroup.rotate(dimension.getRadians() + Math.PI/2, last.getX(), last.getY());

    // add the thin line
    group.addChild(new ZVisualLeaf(mainArc));

    // add the arrow
    arrowGroup.setChildrenFindable(false);
    arrowGroup.setChildrenPickable(false);
    group.addChild(arrowGroup);

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
  	// if we just have the start, then the end hasn't been set yet
    Object startS = start.getFigureFacet().getSubject();
    Object endS = end.getFigureFacet().getSubject();
    
    // the start and end cannot have the same subject
    if (startS == endS)
  	  return false;
    
    // anything to anything else classifier is ok
    return startS instanceof Classifier && startS.getClass() == endS.getClass();
	}

	public Command makeReanchorCommand(AnchorFacet start, AnchorFacet end)
	{
		Classifier newSpecific = (Classifier) start.getFigureFacet().getSubject();
		Classifier newGeneral = (Classifier) end.getFigureFacet().getSubject();

		newSpecific.getGeneralizations().add(subject);
		subject.setGeneral(newGeneral);
		return null;
	}

	public Object getSubject()
	{
		return subject;
	}

	public boolean hasSubjectBeenDeleted()
	{
		return subject.isThisDeleted();
	}

	public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
	{
		// if this is top and the anchors we are attached to are not the same as the ones that the
		// model element is attached to, then delete
		if (pass == ViewUpdatePassEnum.LAST)
		{
			Classifier specific = subject.getSpecific();
			Classifier general = subject.getGeneral();
			Classifier viewSpecific = (Classifier)figureFacet.getLinkingFacet().getAnchor1().getFigureFacet().getSubject();
			Classifier viewGeneral = (Classifier)figureFacet.getLinkingFacet().getAnchor2().getFigureFacet().getSubject();
			
			if (specific != viewSpecific || general != viewGeneral)
				figureFacet.formDeleteTransaction();
		}
	}

  public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
  {
    return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet);
  }

  public Set<String> getPossibleDisplayStyles(AnchorFacet anchor)
  {
    return null;
  }
}
