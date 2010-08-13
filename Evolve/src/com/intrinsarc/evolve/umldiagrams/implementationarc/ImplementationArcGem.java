package com.intrinsarc.evolve.umldiagrams.implementationarc;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.intrinsarc.evolve.umldiagrams.classifiernode.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.arcfacilities.arcsupport.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.repositorybase.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @author Andrew
 */
public class ImplementationArcGem implements Gem
{
  static final String FIGURE_NAME = "implementation";
  private BasicArcAppearanceFacet basicArcAppearanceFacet = new BasicArcAppearanceFacetImpl();
	private Implementation subject;
	private FigureFacet figureFacet;

  public ImplementationArcGem(Implementation subject)
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
		 * @see com.intrinsarc.jumble.arcfacilities.arcsupport.BasicArcAppearanceFacet#getFigureName()
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
    	// start must be a port, end must be an interface
      boolean startReadOnly = start.getFigureFacet().isSubjectReadOnlyInDiagramContext(false);
    	boolean startOk =
    		ImplementationCreatorGem.extractClassType(start.getFigureFacet().getSubject()) != null && !startReadOnly;
    	return startOk && end.getFigureFacet().getSubject() instanceof Interface;
		}

		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
			// if this is top and the anchors we are attached to are not the same as the ones that the
			// model element is attached to, then delete
			if (pass == ViewUpdatePassEnum.LAST)
			{
				Classifier type = subject.getRealizingClassifier();
				Classifier iface = subject.getContract();
				final Classifier viewType = 
					ImplementationCreatorGem.extractClassType((figureFacet.getLinkingFacet().getAnchor1().getFigureFacet().getSubject()));
				final Interface viewIface = (Interface) figureFacet.getLinkingFacet().getAnchor2().getFigureFacet().getSubject();
				
				if (type != viewType || iface != viewIface)
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
			Class newType = ImplementationCreatorGem.extractClassType(
					(start.getFigureFacet().getSubject()));
			Interface newIface = (Interface) end.getFigureFacet().getSubject();

			// change the owner
      newType.getImplementations().add(subject);
			subject.setRealizingClassifier(newType);
			subject.setContract(newIface);
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
