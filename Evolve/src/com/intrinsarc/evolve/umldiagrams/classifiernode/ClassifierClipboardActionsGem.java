package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.awt.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

public class ClassifierClipboardActionsGem
{
  private static final ImageIcon DELTA_ICON = IconLoader.loadIcon("delta.png");

	private boolean iface;
	private boolean stereotype;
  private FigureFacet figureFacet;
  private ClipboardActionsFacet clipboardCommandsFacet = new ClipboardActionsFacetImpl();
  
  public ClassifierClipboardActionsGem(boolean iface, boolean stereotype)
  {
  	this.iface = iface;
  	this.stereotype = stereotype;
  }
  
  public void connectFigureFacet(FigureFacet figureFacet)
  {
    this.figureFacet = figureFacet;
  }
  
  public ClipboardActionsFacet getClipboardCommandsFacet()
  {
    return clipboardCommandsFacet;
  }
  
  public static void makeEvolveAction(
			final Package owner,
			final Classifier original,
			final FigureFacet existing,
			final boolean iface,
			final boolean stereotype,
			final boolean retired)
	{
		Classifier evolution;					
		if (iface)
			evolution = (Classifier) owner.createOwnedMember(UML2Package.eINSTANCE.getInterface());
		else
		if (stereotype)
			evolution = (Classifier) owner.createOwnedMember(UML2Package.eINSTANCE.getStereotype());
		else
		{
			evolution = (Classifier) owner.createOwnedMember(UML2Package.eINSTANCE.getClass_());
			((Class) evolution).setComponentKind(((Class) original).getComponentKind());
		}

		Dependency dep = evolution.createOwnedAnonymousDependencies();
		dep.setResemblance(true);
		dep.setReplacement(true);
    dep.settable_getClients().add(evolution);
    dep.setDependencyTarget(original);
    
    // should we retire this?
    if (retired)
    	evolution.setIsRetired(true);
    
    SwitchSubjectTransaction.switchSubject(existing, evolution);
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
      return isOutOfPlace() && isVisibleHere();
    }

    public void makeSpecificKillAction(ToolCoordinatorFacet coordinator)
    {
      coordinator.displayPopup(null, null,
          new JLabel("Element retired", DELTA_ICON, JLabel.LEADING),
          ScreenProperties.getUndoPopupColor(),
          Color.black,
          1500);
      
      // make the evolution here
      final Package owner =
      	GlobalSubjectRepository.repository.findVisuallyOwningStratum(
      			figureFacet.getDiagram(),
      			figureFacet.getContainerFacet());
      
			ClassifierClipboardActionsGem.makeEvolveAction(
					owner,
					(Classifier) figureFacet.getSubject(),
					figureFacet,
					iface,
					stereotype,
					true);
    }

    private boolean isVisibleHere()
    {
    	DEElement me = GlobalDeltaEngine.engine.locateObject(figureFacet.getSubject()).asElement();
    	DEStratum current = GlobalDeltaEngine.engine.locateObject(figureFacet.getDiagram().getLinkedObject()).asStratum();
    	return current.getCanSeePlusMe().contains(me.getHomeStratum());
    }
    
    private boolean isOutOfPlace()
    {
      // are we at home?
    	DEElement me = GlobalDeltaEngine.engine.locateObject(figureFacet.getSubject()).asElement();
    	DEStratum current = GlobalDeltaEngine.engine.locateObject(figureFacet.getDiagram().getLinkedObject()).asStratum();    	
      return current != me.getHomeStratum();
    }
  }
}
