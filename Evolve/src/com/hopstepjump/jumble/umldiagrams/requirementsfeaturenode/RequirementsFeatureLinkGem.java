package com.hopstepjump.jumble.umldiagrams.requirementsfeaturenode;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.featurenode.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public class RequirementsFeatureLinkGem
{
  private static final ImageIcon ERROR_ICON = IconLoader.loadIcon("error.png");
  private static final ImageIcon DELTA_ICON = IconLoader.loadIcon("delta.png");

  private BasicArcAppearanceFacet basicArcAppearanceFacet = new BasicArcAppearanceFacetImpl();
	private RequirementsFeatureLink subject;
	private FigureFacet figureFacet;
	private int kind;
  private ClipboardActionsFacet clipboardCommandsFacet = new ClipboardActionsFacetImpl();

  public RequirementsFeatureLinkGem(RequirementsFeatureLink subject)
  {
  	this.subject = subject;
  	kind = subject.getKind().getValue();
  }
  
  public void connectFigureFacet(FigureFacet figureFacet)
  {
  	this.figureFacet = figureFacet;
  }
  
	public ClipboardActionsFacet getClipboardCommandsFacet()
  {
    return clipboardCommandsFacet;
  }
  
  public BasicArcAppearanceFacet getBasicArcAppearanceFacet()
  {
    return basicArcAppearanceFacet;
  }

  
  private class ClipboardActionsFacetImpl implements ClipboardActionsFacet
  {
    public boolean hasSpecificDeleteAction()
    {
      return false;
    }

    public void makeSpecificDeleteAction()
    {
    }
    
    public void performPostDeleteAction()
    {
      // important to use the reference rather than the figure, which gets recreated...
      String uuid = FeatureNodeGem.getOriginalSubject(subject).getUuid();      
      getSimpleDeletedUuidsFacet().addDeleted(uuid);
    }

	  private SimpleDeletedUuidsFacet getSimpleDeletedUuidsFacet()
	  {
	    // follow one anchor up until we find the classifier, and then look for the simple deleted uuids facet 
	    FigureFacet clsFigure = figureFacet.getLinkingFacet().getAnchor1().getFigureFacet();
	    return (SimpleDeletedUuidsFacet)
	      clsFigure.getDynamicFacet(SimpleDeletedUuidsFacet.class);
	  }

    public boolean hasSpecificKillAction()
    {
      return isOutOfPlace() || !atHome();
    }

    /** returns true if the element is out of place */
    private boolean isOutOfPlace()
    {
      return
      	figureFacet.getLinkingFacet().getAnchor1().getFigureFacet().getSubject() != getOwner() ||
      	figureFacet.getLinkingFacet().getAnchor1().getFigureFacet().getSubject() != subject.getType();
    }

    public void makeSpecificKillAction(ToolCoordinatorFacet coordinator)
    {
      // only allow changes in the home stratum
//      if (!atHome())
//      {
//        coordinator.displayPopup(ERROR_ICON, "Delta error",
//            new JLabel("Must be in home stratum to delete subjects!", DELTA_ICON, JLabel.LEFT),
//            ScreenProperties.getUndoPopupColor(),
//            Color.black,
//            3000);
//        return;
//      }

      // if this is a replace, kill the replace delta
      if (subject.getOwner() instanceof DeltaReplacedConstituent)
        generateReplaceDeltaKill(coordinator);
      else
        generateDeleteDelta(coordinator);
    }

    private void generateReplaceDeltaKill(ToolCoordinatorFacet coordinator)
    {
      // generate a delete delta for the replace
      coordinator.displayPopup(null, null,
          new JLabel("Removed replace delta", DELTA_ICON, JLabel.LEADING),
          ScreenProperties.getUndoPopupColor(),
          Color.black,
          1500);
      
      GlobalSubjectRepository.repository.incrementPersistentDelete(subject.getOwner());            
    }

    private void generateDeleteDelta(ToolCoordinatorFacet coordinator)
    {
      // generate a delete delta
      coordinator.displayPopup(null, null,
          new JLabel("Delta delete", DELTA_ICON, JLabel.LEADING),
          ScreenProperties.getUndoPopupColor(),
          Color.black,
          1500);
      
      FigureFacet owner = figureFacet.getLinkingFacet().getAnchor1().getFigureFacet();
      RequirementsFeature req = (RequirementsFeature) owner.getSubject();
    	DeltaDeletedConstituent delete = req.createDeltaDeletedSubfeatures();
      delete.setDeleted(req);
    }

    private boolean atHome()
    {
      // are we at home?
    	RequirementsFeature req = getOwner();
      Package home = GlobalSubjectRepository.repository.findOwningStratum(req);
      FigureFacet owner = figureFacet.getLinkingFacet().getAnchor1().getFigureFacet();
      Package visualHome = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), owner.getContainerFacet());
      
      return home == visualHome;
    }
  }
  
  private RequirementsFeature getOwner()
  {
  	return (RequirementsFeature) FeatureNodeGem.getOriginalSubject(figureFacet.getSubject()).getOwner();
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

				DERequirementsFeature main = getOriginal(getOwner());
				DERequirementsFeature dependsOn = getOriginal((RequirementsFeature) subject.undeleted_getType());
				DERequirementsFeature viewMain = 
					getOriginal((RequirementsFeature) figureFacet.getLinkingFacet().getAnchor1().getFigureFacet().getSubject());
				DERequirementsFeature viewDependsOn = getOriginal((RequirementsFeature) figureFacet.getLinkingFacet().getAnchor2().getFigureFacet().getSubject());
				
				if (main != viewMain || dependsOn != viewDependsOn)
					figureFacet.formDeleteTransaction();
			}
		}
		
		private DERequirementsFeature getOriginal(RequirementsFeature f)
		{
			DERequirementsFeature req = GlobalDeltaEngine.engine.locateObject(f).asRequirementsFeature();
			if (req.isSubstitution())
			{
				DERequirementsFeature de = req.getSubstitutes().iterator().next().asRequirementsFeature(); 
				if (de != null)
					return de;
			}
			return req;
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
