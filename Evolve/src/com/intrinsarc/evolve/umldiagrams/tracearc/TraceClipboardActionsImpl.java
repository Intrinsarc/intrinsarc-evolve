package com.intrinsarc.evolve.umldiagrams.tracearc;

import java.awt.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;


public class TraceClipboardActionsImpl implements ClipboardActionsFacet
{
	private static final ImageIcon ERROR_ICON = IconLoader.loadIcon("error.png");
	private static final ImageIcon DELTA_ICON = IconLoader.loadIcon("delta.png");
	private FigureFacet figureFacet;
	
	public void connectFigureFacet(FigureFacet figure)
	{
		figureFacet = figure;
	}
	
	private Dependency getSubject()
	{
		return (Dependency) figureFacet.getSubject();
	}
	
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
    if (getSubject().getOwner() instanceof DeltaReplacedConstituent)
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
    
    GlobalSubjectRepository.repository.incrementPersistentDelete(getSubject().getOwner());            
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
    StructuredClassifier cls = (StructuredClassifier) owner.getSubject();
  	DeltaDeletedConstituent delete = cls.createDeltaDeletedTraces();
    delete.setDeleted(getSubject());
  }

	private boolean atHome()
	{
	  // are we at home? -- defined by whether the actual owner is in its home stratum
		Classifier cls = getOwner(getSubject());
		Package home = GlobalSubjectRepository.repository.findOwningStratum(cls);
	  Package visualHome = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), getOwnerFigure().getContainerFacet());
	  
	  return home == visualHome;
	}
	
	private boolean visualOwnerAtHome()
	{
	  // are we at home? -- defined by whether the actual owner is in its home stratum
		NamedElement req = getVisualOwner(figureFacet);
		Package home = GlobalSubjectRepository.repository.findOwningStratum(req);
	  Package visualHome = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), getOwnerFigure().getContainerFacet());
	  
	  return home == visualHome;
	}
	
	private FigureFacet getOwnerFigure()
	{
		return figureFacet.getLinkingFacet().getAnchor1().getFigureFacet();
	}
	
	private Classifier getOwner(Dependency subject)
	{
		if (subject.getOwner() instanceof DeltaReplacedTrace)
			return (Classifier) ((DeltaReplacedTrace) subject.getOwner()).getOwner();
		return (Classifier) subject.getOwner();
	}
	
  private NamedElement getVisualOwner(FigureFacet figure)
  {
  	FigureFacet owner = figure.getLinkingFacet().getAnchor1().getFigureFacet();
  	return (NamedElement) owner.getSubject();
  }
}

