package com.intrinsarc.evolve.umldiagrams.requirementsfeaturenode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.umldiagrams.tracearc.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.arcfacilities.arcsupport.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;
import com.intrinsarc.swing.enhanced.*;

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
    }

    public boolean hasSpecificKillAction()
    {
      return !atHome();
    }

    public void makeSpecificKillAction(ToolCoordinatorFacet coordinator)
    {
      // only allow changes in the home stratum
      if (!visualOwnerAtHome())
      {
        coordinator.displayPopup(ERROR_ICON, "Delta error",
            new JLabel("Must be in home stratum to delete subjects!", DELTA_ICON, JLabel.LEFT),
            ScreenProperties.getUndoPopupColor(),
            Color.black,
            3000);
        return;
      }

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
      delete.setDeleted(subject);
    }
  }
  
  private boolean atHome()
  {
    // are we at home? -- defined by whether the actual owner is in its home stratum
  	RequirementsFeature req = getOwner(subject);
  	Package home = GlobalSubjectRepository.repository.findOwningStratum(req);
    Package visualHome = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), getOwnerFigure().getContainerFacet());
    
    return home == visualHome;
  }

  private boolean visualOwnerAtHome()
  {
    // are we at home? -- defined by whether the actual owner is in its home stratum
  	RequirementsFeature req = getVisualOwner(figureFacet);
  	Package home = GlobalSubjectRepository.repository.findOwningStratum(req);
    Package visualHome = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), getOwnerFigure().getContainerFacet());
    
    return home == visualHome;
  }
  
  private FigureFacet getOwnerFigure()
  {
  	return figureFacet.getLinkingFacet().getAnchor1().getFigureFacet();
  }
  
  private RequirementsFeature getOwner(RequirementsFeatureLink subject)
  {
  	if (subject.getOwner() instanceof DeltaReplacedRequirementsFeatureLink)
  		return (RequirementsFeature) ((DeltaReplacedRequirementsFeatureLink) subject.getOwner()).getOwner();
  	return (RequirementsFeature) subject.getOwner();
  }
  
  private RequirementsFeature getVisualOwner(FigureFacet figure)
  {
  	FigureFacet owner = figure.getLinkingFacet().getAnchor1().getFigureFacet();
  	return (RequirementsFeature) owner.getSubject();
  }
  
  private DERequirementsFeature getVisualTarget()
  {
  	FigureFacet target = figureFacet.getLinkingFacet().getAnchor2().getFigureFacet();
  	DERequirementsFeature de = GlobalDeltaEngine.engine.locateObject(target.getSubject()).asRequirementsFeature();
  	return de.getReplacesOrSelf().iterator().next().asRequirementsFeature();
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
		 * @see com.intrinsarc.jumble.arcfacilities.arcsupport.BasicArcAppearanceFacet#getFigureName()
		 */
		public String getFigureName()
		{
			return RequirementsFeatureLinkCreatorGem.NAME;
		}
	
		/**
		 * @see com.intrinsarc.idraw.arcfacilities.arcsupport.BasicArcAppearanceFacet#addToPersistentProperties(PersistentProperties)
		 */
		public void addToPersistentProperties(PersistentProperties properties)
		{
			properties.add(new PersistentProperty("kind", kind, 0));
		}
	
    public void addToContextMenu(JPopupMenu menu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
    {
      // only add a replace if this is not visually at home
    	if (getVisualOwner(figureFacet) != getOwner(subject))
      {
        JMenuItem replaceItem = getReplaceItem(diagramView, coordinator);
        menu.add(replaceItem);
        Utilities.addSeparator(menu);
      }
    }
    
    private JMenuItem getReplaceItem(final DiagramViewFacet diagramView, final ToolCoordinatorFacet coordinator)
    {
      // for adding operations
      JMenuItem replace = new JMenuItem("Replace", new NullIcon());
      replace.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
        	RequirementsFeatureLink replace = (RequirementsFeatureLink) figureFacet.getSubject();
          coordinator.startTransaction("replaced subfeature", "removed replaced subfeature");
          RequirementsFeatureLink replacement = createDeltaReplacedLink(getVisualOwner(figureFacet), replace);
          
          // change the subject so we don't lose the graphical view
          RequirementsFeatureLinkCreatorGem gem = new RequirementsFeatureLinkCreatorGem(subject.getKind());
          PersistentFigure pfig = figureFacet.makePersistentFigure();
          pfig.setSubject(replacement);
          DiagramFacet diagram = figureFacet.getDiagram();
          FigureReference reference = diagram.makeNewFigureReference();
          gem.getArcCreateFacet().create(
          		replacement,
          		diagram,
          		reference.getId(),
          		figureFacet.getLinkingFacet().getCalculated().getReferenceCalculatedArcPoints(diagram),
          		figureFacet.makePersistentFigure().getProperties());
          coordinator.commitTransaction(true);
          
          // select the replaced figure
          diagramView.getSelection().clearAllSelection();
          diagramView.getSelection().addToSelection(diagram.retrieveFigure(reference.getId()));
        }
      });

      return replace;
    }
    
    private RequirementsFeatureLink createDeltaReplacedLink(RequirementsFeature owner, RequirementsFeatureLink replace)
    { 
    	DeltaReplacedRequirementsFeatureLink replacement = owner.createDeltaReplacedSubfeatures();
      replacement.setReplaced(replace);
      RequirementsFeatureLink next = (RequirementsFeatureLink) replacement.createReplacement(UML2Package.eINSTANCE.getRequirementsFeatureLink());
      next.setKind(replace.getKind());
      next.setType(replace.getType());
      return next;
    }
    
		public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
		{
      // ensure we are even authorised...
			if (getOwner(subject) != getVisualOwner(figureFacet))
				return false;
			
      return start.getFigureFacet() == figureFacet.getLinkingFacet().getAnchor1().getFigureFacet() && RequirementsFeatureLinkCreatorGem.acceptsOneOrBothAnchors(start, end);
		}

		public void updateViewAfterSubjectChanged(ViewUpdatePassEnum pass)
		{
			// if this is top and the anchors we are attached to are not the same as the ones that the
			// model element is attached to, then delete
			if (pass == ViewUpdatePassEnum.LAST)
			{
				kind = subject.getKind().getValue();

				// is this in the correct place?
				// first, ensure that the element is included
				IDeltaEngine engine = GlobalDeltaEngine.engine;
				DERequirementsFeature dereq = engine.locateObject(getOwner(subject)).asRequirementsFeature();
	      Package visualHome = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), getOwnerFigure().getContainerFacet());
	      DEStratum perspective = engine.locateObject(visualHome).asStratum();
	      
	      boolean found = false;
	      DERequirementsFeature visualTarget = getVisualTarget();
	      for (DeltaPair pair : dereq.getDeltas(ConstituentTypeEnum.DELTA_REQUIREMENT_FEATURE_LINK).getConstituents(perspective))
	      {
	      	if (pair.getConstituent().getUuid().equals(subject.getUuid()))
	      	{
	      		// ensure this links to the correct element
	      		if (pair.getConstituent().asRequirementsFeatureLink().getSubfeature().getReplacesOrSelf().iterator().next() == visualTarget)
	      			found = true;
	      		break;
	      	}
	      }
				
	      if (!found)
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
			RequirementsFeature newDependsOn = (RequirementsFeature) end.getFigureFacet().getSubject();
			subject.setType(RequirementsFeatureLinkCreatorGem.getRealTarget(newDependsOn));
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
