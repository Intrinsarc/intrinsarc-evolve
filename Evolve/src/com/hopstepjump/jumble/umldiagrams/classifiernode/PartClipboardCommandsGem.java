package com.hopstepjump.jumble.umldiagrams.classifiernode;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.featurenode.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;

public class PartClipboardCommandsGem
{
  private static final ImageIcon INFO_ICON = IconLoader.loadIcon("information.png");
  private static final ImageIcon ERROR_ICON = IconLoader.loadIcon("error.png");
  private static final ImageIcon DELTA_ICON = IconLoader.loadIcon("delta.png");

  private FigureFacet figureFacet;
  private ClipboardCommandsFacet clipboardCommandsFacet = new ClipboardCommandsFacetImpl();
  
  public PartClipboardCommandsGem()
  {
  }
  
  public void connectFigureFacet(FigureFacet figureFacet)
  {
    this.figureFacet = figureFacet;
  }
  
  public ClipboardCommandsFacet getClipboardCommandsFacet()
  {
    return clipboardCommandsFacet;
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
    
    public Command makePostDeleteCommand()
    {
      // important to use the reference rather than the figure, which gets recreated...
      final FigureReference reference = figureFacet.getFigureReference();
      final String uuid = getOriginalSubjectAsPart(getSubject()).getUuid();
      
      return new AbstractCommand()
      {
        public void execute(boolean isTop)
        {
          getSimpleContainerCompartment().addDeleted(uuid);
        }

        private SimpleContainerFacet getSimpleContainerCompartment()
        {
          FigureFacet figure = GlobalDiagramRegistry.registry.retrieveFigure(reference);
          FigureFacet parent = figure.getContainedFacet().getContainer().getFigureFacet();
          return (SimpleContainerFacet)
            parent.getDynamicFacet(SimpleContainerFacet.class);
        }

        public void unExecute()
        {
          getSimpleContainerCompartment().removeDeleted(uuid);
        } 
      };
    }

    public boolean hasSpecificKillCommand()
    {
      return isOutOfPlace() || !atHome();
    }

    public Command makeSpecificKillCommand(ToolCoordinatorFacet coordinator)
    {
      // be defensive
      if (figureFacet.getContainedFacet().getContainer() == null)
        return null;
      
      // only allow changes in the home stratum
      if (!atHome())
      {
        coordinator.displayPopup(ERROR_ICON, "Delta error",
            new JLabel("Must be in home stratum to delete subjects!", DELTA_ICON, JLabel.RIGHT),
            ScreenProperties.getUndoPopupColor(),
            Color.black,
            3000);
        return null;
      }

      // if this is a replace, kill the replace delta
      Element subject = getSubject();
      if (subject.getOwner() instanceof DeltaReplacedConstituent && subject.getOwner().getOwner() == extractVisualClassifier())
        return generateReplaceDeltaKill(coordinator);
      else
        return generateDeleteDelta(coordinator);
    }
  }
  
  private Element getSubject()
  {
    return (Element) figureFacet.getSubject();
  }
  
  private boolean atHome()
  {
    // are we at home?
    Package home = GlobalSubjectRepository.repository.findOwningStratum(extractVisualClassifier());
    Package visualHome = GlobalSubjectRepository.repository.findVisuallyOwningStratum(figureFacet.getDiagram(), figureFacet.getContainedFacet().getContainer());
    
    return home == visualHome;
  }

  private Classifier extractVisualClassifier()
  {
    return ClassifierConstituentHelper.extractVisualClassifierFromConstituent(figureFacet);
  }
  
  /** return the replaced feature is this is a replacement, otherwise just return the feature */
  private Property getOriginalSubjectAsPart(Object subject)
  {
    Property part = (Property) subject;
    if (part.getOwner() instanceof DeltaReplacedConstituent)
      return (Property)((DeltaReplacedConstituent) part.getOwner()).getReplaced();
    return part;
  }    

  /** returns true if the element is out of place */
  private boolean isOutOfPlace()
  {
    return extractVisualClassifier() != getSubject().getOwner();
  }
  

  private Command generateReplaceDeltaKill(ToolCoordinatorFacet coordinator)
  {
    // generate a delete delta for the replace
    coordinator.displayPopup(null, null,
        new JLabel("Removed replace delta", DELTA_ICON, JLabel.LEADING),
        ScreenProperties.getUndoPopupColor(),
        Color.black,
        1500);
    
    return new AbstractCommand("Removed replace delta", "Restored replace delta")
    {
      public void execute(boolean isTop)
      {
        GlobalSubjectRepository.repository.incrementPersistentDelete(getSubject().getOwner());            
      }

      public void unExecute()
      {
        GlobalSubjectRepository.repository.decrementPersistentDelete(getSubject().getOwner());
      } 
    };
  }

  private Command generateDeleteDelta(ToolCoordinatorFacet coordinator)
  {
    final Classifier owner = extractVisualClassifier();

    // generate a delete delta
    coordinator.displayPopup(null, null,
        new JLabel("Delta delete", DELTA_ICON, JLabel.LEADING),
        ScreenProperties.getUndoPopupColor(),
        Color.black,
        1500);
    
    // add this to the classifier as a delete delta
    final Element feature = FeatureNodeGem.getOriginalSubject(getSubject());
    
    CompositeCommand cmd = new CompositeCommand("Added delete delta", "Removed delete delta");
    addBadConnectorDeletes(coordinator, cmd);
    cmd.addCommand(
      new AbstractCommand()
      {
        private DeltaDeletedConstituent delete;
        
        public void execute(boolean isTop)
        {
          SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
          
          // possibly resurrect
          if (delete != null)
          {
            repository.decrementPersistentDelete(delete);
          }
          else
          {
            if (owner instanceof ClassImpl)
              delete = ((Class) owner).createDeltaDeletedAttributes();
            else
            if (owner instanceof InterfaceImpl)
              delete = ((Interface) owner).createDeltaDeletedAttributes();
            delete.setDeleted(feature);
          }
        }

        public void unExecute()
        {
          GlobalSubjectRepository.repository.incrementPersistentDelete(delete);
        }
      });
    
    return cmd;
  }

  private void addBadConnectorDeletes(ToolCoordinatorFacet coordinator, CompositeCommand cmd)
  {
    // find any connectors in this stratum that connect to this part, and delete them
    IDeltaEngine engine = GlobalDeltaEngine.engine;
    Class cls = (Class) extractVisualClassifier();
    DEComponent comp = engine.locateObject(cls).asComponent();
    IDeltas deltas = comp.getDeltas(ConstituentTypeEnum.DELTA_CONNECTOR);
    DEStratum perspective = comp.getHomeStratum();
    Set<DeltaPair> pairs = deltas.getConstituents(perspective, true);
    DEPart part = engine.locateObject(figureFacet.getSubject()).asConstituent().asPart();
    
    for (DeltaPair pair : pairs)
    {
      // does this link to the part?
      DEConnector conn = pair.getConstituent().asConnector(); 
      for (int lp = 0; lp < 2; lp++)
        if (conn.getPart(perspective, comp, lp) == part)
          addDeleteCommand(cls, pair, cmd);
    }
  }

  private void addDeleteCommand(final Class cls, final DeltaPair pair, CompositeCommand cmd)
  {
    // work out the appropriate object to delta delete or actually delete
    final Connector connector = (Connector) pair.getConstituent().getRepositoryObject();
    
    // if this connector exists in this classifier, remove it
    final SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
    if (connector.getOwner() == cls)
    {
      cmd.addCommand(new AbstractCommand("", "")
      {
        public void execute(boolean isTop)
        {
          repository.incrementPersistentDelete(connector);
        }

        public void unExecute()
        {
          repository.decrementPersistentDelete(connector);
        }
      });
    }
    else
    // if this is a replace and we own it, delete the replace and add a delta delete
    if (connector.getOwner() instanceof DeltaReplacedConnector && connector.getOwner().getOwner() == cls)
    {      
      cmd.addCommand(new AbstractCommand("", "")
      {
      	private DeltaDeletedConnector del;
      	public void execute(boolean isTop)
        {
      		if (del == null)
      		{
            del = cls.createDeltaDeletedConnectors();
            del.setDeleted((Connector) pair.getOriginal().getRepositoryObject());
      		}
      		else
      		{
	          repository.incrementPersistentDelete(connector.getOwner());
	          repository.decrementPersistentDelete(del);
      		}
        }

        public void unExecute()
        {
          repository.decrementPersistentDelete(connector.getOwner());
          repository.incrementPersistentDelete(del);
        }
      });
    }
    else
    // just generate a delete delta
    {      
      cmd.addCommand(new AbstractCommand("", "")
      {
      	private DeltaDeletedConnector del;
        public void execute(boolean isTop)
        {
        	if (del == null)
        	{
            del = cls.createDeltaDeletedConnectors();
            del.setDeleted((Connector) pair.getOriginal().getRepositoryObject());
        	}
        	else
        		repository.decrementPersistentDelete(del);
        }

        public void unExecute()
        {
          repository.incrementPersistentDelete(del);
        }
      });      
    }
  };
}
