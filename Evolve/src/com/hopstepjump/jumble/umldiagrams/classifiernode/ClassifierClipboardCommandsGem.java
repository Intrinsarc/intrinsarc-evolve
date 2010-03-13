package com.hopstepjump.jumble.umldiagrams.classifiernode;

import java.awt.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;

public class ClassifierClipboardCommandsGem
{
  private static final ImageIcon INFO_ICON = IconLoader.loadIcon("information.png");
  private static final ImageIcon ERROR_ICON = IconLoader.loadIcon("error.png");
  private static final ImageIcon DELTA_ICON = IconLoader.loadIcon("delta.png");

	private boolean iface;
	private boolean stereotype;
  private FigureFacet figureFacet;
  private ClipboardCommandsFacet clipboardCommandsFacet = new ClipboardCommandsFacetImpl();
  
  public ClassifierClipboardCommandsGem(boolean iface, boolean stereotype)
  {
  	this.iface = iface;
  	this.stereotype = stereotype;
  }
  
  public void connectFigureFacet(FigureFacet figureFacet)
  {
    this.figureFacet = figureFacet;
  }
  
  public ClipboardCommandsFacet getClipboardCommandsFacet()
  {
    return clipboardCommandsFacet;
  }
  
  public static void addToEvolutionCommand(
			final Package owner,
			final Classifier original,
			CompositeCommand cmd,
			final FigureReference existing,
			final boolean iface,
			final boolean stereotype,
			final boolean retired)
	{
		cmd.addCommand(new AbstractCommand()
		{
			private Command switchSubject;
			private boolean runAlready;
			private Classifier evolution;					
			
			public void execute(boolean isTop)
			{
				if (!runAlready)
				{
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
		      
		      switchSubject = new SwitchSubjectCommand(existing, evolution, "", "");
		      switchSubject.execute(true);
		      runAlready = true;
				}
				else
				{
					GlobalSubjectRepository.repository.decrementPersistentDelete(evolution);								
					switchSubject.execute(true);
				}						
			}
	
			public void unExecute()
			{
				switchSubject.unExecute();
				GlobalSubjectRepository.repository.incrementPersistentDelete(evolution);
			}
		});
	}

	private class ClipboardCommandsFacetImpl implements ClipboardCommandsFacet
  {
    public boolean hasSpecificDeleteCommand()
    {
      return false;
    }

    public Command makeSpecificDeleteCommand()
    {
      return null;
    }
    
    public Command performPostDeleteTransaction()
    {
    	return null;
    }

    public boolean hasSpecificKillCommand()
    {
      return isOutOfPlace() && isVisibleHere();
    }

    public Command makeSpecificKillCommand(ToolCoordinatorFacet coordinator)
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
      
			CompositeCommand cmd = new CompositeCommand("Retire component", "Remove retirement");
			ClassifierClipboardCommandsGem.addToEvolutionCommand(
					owner,
					(Classifier) figureFacet.getSubject(),
					cmd,
					figureFacet.getFigureReference(),
					iface,
					stereotype,
					true);
      
      return cmd;
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
