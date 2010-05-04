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
import com.hopstepjump.repositorybase.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class RequirementsFeatureLinkGem
{
  private BasicArcAppearanceFacet basicArcAppearanceFacet = new BasicArcAppearanceFacetImpl();
	private RequirementsFeatureLink subject;
	private FigureFacet figureFacet;
	private int kind;

  public RequirementsFeatureLinkGem(RequirementsFeatureLink subject)
  {
  	this.subject = subject;
  	kind = subject.getKind().getValue();
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
	    // make the thin and thick lines
	    ZGroup group = new ZGroup();

	    // add the thin line
	    group.addChild(new ZVisualLeaf(mainArc));

	    double x = (int) last.getX();
	    double y = (int) last.getY();
	    double offset = 5;
	    if (kind == RequirementsLinkKind.MANDATORY || kind == RequirementsLinkKind.OPTIONAL)
	    {
		    ZEllipse ell = new ZEllipse(x - offset, y - offset, offset * 2, offset * 2);
	    	ell.setFillPaint(kind == RequirementsLinkKind.MANDATORY ? Color.BLACK : Color.WHITE);
	    	group.addChild(new ZVisualLeaf(ell));
	    }

	    group.setChildrenFindable(false);
	    group.setChildrenPickable(true);
	    return group;
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
			properties.add(new PersistentProperty("kind", kind, 0));
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
				kind = subject.getKind().getValue();

				RequirementsFeature main = (RequirementsFeature) subject.getOwner();
				RequirementsFeature dependsOn = (RequirementsFeature) subject.undeleted_getType();
				final RequirementsFeature viewMain = 
					(RequirementsFeature) figureFacet.getLinkingFacet().getAnchor1().getFigureFacet().getSubject();
				RequirementsFeature viewDependsOn = (RequirementsFeature) figureFacet.getLinkingFacet().getAnchor2().getFigureFacet().getSubject();
				
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
			RequirementsFeature newMain = (RequirementsFeature)
					start.getFigureFacet().getSubject();
			RequirementsFeature newDependsOn = (RequirementsFeature) end.getFigureFacet().getSubject();

			// change the owner and target
			newMain.settable_getSubfeatures().add(subject);
			subject.setType(newDependsOn);
		}

    public boolean isSubjectReadOnlyInDiagramContext(boolean kill)
    {
      return GlobalSubjectRepository.repository.isContainerContextReadOnly(figureFacet);
    }

    public Set<String> getPossibleDisplayStyles(AnchorFacet anchor)
    {
    	Set<String> styles = new HashSet<String>();
    	styles.add("requirements");
    	return styles;
    }

		public void acceptPersistentProperties(PersistentFigure pfig)
		{
			kind = pfig.getProperties().retrieve("kind", 0).asInteger();
		}
  }
}
