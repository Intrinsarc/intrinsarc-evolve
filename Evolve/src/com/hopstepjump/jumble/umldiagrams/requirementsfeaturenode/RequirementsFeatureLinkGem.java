package com.hopstepjump.jumble.umldiagrams.requirementsfeaturenode;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.jumble.umldiagrams.classifiernode.*;
import com.hopstepjump.repositorybase.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class RequirementsFeatureLinkGem
{
  private BasicArcAppearanceFacet basicArcAppearanceFacet = new BasicArcAppearanceFacetImpl();
	private Property subject;
	private FigureFacet figureFacet;

  public RequirementsFeatureLinkGem(Property subject)
  {
  	this.subject = subject;
  }
  
  public void connectFigureFacet(FigureFacet figureFacet)
  {
  	this.figureFacet = figureFacet;
  }
  
  public BasicArcAppearanceFacet getBasicArcAppearanceFacet()
  {
    return basicArcAppearanceFacet;
  }

  class BasicArcAppearanceFacetImpl implements BasicArcAppearanceFacet
  {
		public ZNode formAppearance(
			ZShape mainArc,
			UPoint start,
			UPoint second,
			UPoint secondLast,
			UPoint last,
			CalculatedArcPoints calculated, boolean curved)
	  {
	  	// see if we want a direct style, or with arrows
		  Set<String> styles = figureFacet.getLinkingFacet().getAnchor2().getDisplayStyles(true);
		  return formRealisationAppearance(
          mainArc,
          secondLast,
          last,
          styles != null && styles.contains(InterfaceCreatorGem.LINK_STYLE_DIRECT));
	  }
	  
		/**
		 * @see com.hopstepjump.jumble.arcfacilities.arcsupport.BasicArcAppearanceFacet#getFigureName()
		 */
		public String getFigureName()
		{
			return RequirementsFeatureLinkCreatorGem.NAME;
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
    	// start must be a port, end must be an interface
      boolean startReadOnly = start.getFigureFacet().isSubjectReadOnlyInDiagramContext(false);
    	boolean startOk =
    		start.getFigureFacet().getSubject() instanceof Class && !startReadOnly;
    	return startOk && end.getFigureFacet().getSubject() instanceof Class;
		}

		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
			// if this is top and the anchors we are attached to are not the same as the ones that the
			// model element is attached to, then delete
			if (pass == ViewUpdatePassEnum.LAST)
			{
				Class main = (Class) subject.getOwner();
				Class dependsOn = (Class) subject.undeleted_getType();
				final Class viewMain= 
					(Class) figureFacet.getLinkingFacet().getAnchor1().getFigureFacet().getSubject();
				final Class viewDependsOn = (Class) figureFacet.getLinkingFacet().getAnchor2().getFigureFacet().getSubject();
				
				if (main != viewMain || dependsOn != viewDependsOn)
					figureFacet.formDeleteTransaction();
			}
		}

		public Object getSubject()
		{
			return subject;
		}

		public boolean hasSubjectBeenDeleted()
		{
			return subject.isThisDeleted();
		}

		public void makeReanchorAction(AnchorFacet start, AnchorFacet end)
		{
			Class newMain = (Class)
					start.getFigureFacet().getSubject();
			Class newDependsOn = (Class) end.getFigureFacet().getSubject();

			// change the owner and target
			newMain.settable_getOwnedAttributes().add(subject);
			subject.setType(newDependsOn);
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet);
    }

    public Set<String> getPossibleDisplayStyles(AnchorFacet anchor)
    {
     if (anchor == figureFacet.getLinkingFacet().getAnchor1())
       return null;

     HashSet<String> styles = new HashSet<String>();
     styles.add(InterfaceCreatorGem.LINK_STYLE_DIRECT);
     return styles;
    }

		public void acceptPersistentProperties(PersistentFigure pfig)
		{
		}
  }
  
  /**
   * @param mainArc
   * @param secondLast
   * @param last
   * @param directStyle
   * @return
   */
  private static ZNode formRealisationAppearance(ZShape mainArc, UPoint secondLast, UPoint last, boolean directStyle)
  {
    // make the thin and thick lines
    ZGroup group = new ZGroup();

    if (!directStyle)
	    mainArc.setStroke(
	    	new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{3,5}, 0));

    // add the thin line
    group.addChild(new ZVisualLeaf(mainArc));

    // make the arrowhead
		if (!directStyle)
		{
	    ZPolygon poly = new ZPolygon(last);
	    poly.add(last.add(new UDimension(-8, -20)));
	    poly.add(last.add(new UDimension(8, -20)));
	    poly.add(last);
	    poly.setPenPaint(Color.black);
	    poly.setFillPaint(Color.white);
	    UDimension dimension = secondLast.subtract(last);
	    ZTransformGroup arrowGroup = new ZTransformGroup(new ZVisualLeaf(poly));
	    arrowGroup.rotate(dimension.getRadians() + Math.PI/2, last.getX(), last.getY());

	    // add the arrow
	    arrowGroup.setChildrenFindable(false);
	    arrowGroup.setChildrenPickable(false);
	    group.addChild(arrowGroup);
		}


    group.setChildrenFindable(false);
    group.setChildrenPickable(true);
    return group;
  }
}
