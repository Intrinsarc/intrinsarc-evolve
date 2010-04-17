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
	private Property subject;
	private FigureFacet figureFacet;
	private String type;

  public RequirementsFeatureLinkGem(Property subject)
  {
  	this.subject = subject;
  	type = subject.getDefault();
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
	    if (type.equals("0") || type.equals("1"))
	    {
		    ZEllipse ell = new ZEllipse(x - offset, y - offset, offset * 2, offset * 2);
	    	ell.setFillPaint(type.equals("0") ? Color.BLACK : Color.WHITE);
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
			properties.add(new PersistentProperty("type", type, "0"));
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
				type = subject.getDefault();

				Class main = (Class) subject.getOwner();
				Class dependsOn = (Class) subject.undeleted_getType();
				final Class viewMain = 
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
    	Set<String> styles = new HashSet<String>();
    	styles.add("requirements");
    	return styles;
    }

		public void acceptPersistentProperties(PersistentFigure pfig)
		{
			type = pfig.getProperties().retrieve("type", "0").asString();
		}
  }
}
