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

public class PartClipboardActionsGem
{
  private static final ImageIcon ERROR_ICON = IconLoader.loadIcon("error.png");
  private static final ImageIcon DELTA_ICON = IconLoader.loadIcon("delta.png");

  private FigureFacet figureFacet;
  private ClipboardActionsFacet clipboardCommandsFacet = new ClipboardActionsFacetImpl();
  
  public PartClipboardActionsGem()
  {
  }
  
  public void connectFigureFacet(FigureFacet figureFacet)
  {
    this.figureFacet = figureFacet;
  }
  
  public ClipboardActionsFacet getClipboardCommandsFacet()
  {
    return clipboardCommandsFacet;
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
      final String uuid = getOriginalSubjectAsPart(getSubject()).getUuid();      
      getSimpleContainerCompartment().addDeleted(uuid);
    }

    private SimpleContainerFacet getSimpleContainerCompartment()
    {
      FigureFacet parent = figureFacet.getContainedFacet().getContainer().getFigureFacet();
      return (SimpleContainerFacet)
        parent.getDynamicFacet(SimpleContainerFacet.class);
    }

    public boolean hasSpecificKillAction()
    {
      return isOutOfPlace() || !atHome();
    }

    public void makeSpecificKillAction(ToolCoordinatorFacet coordinator)
    {
      // be defensive
      if (figureFacet.getContainedFacet().getContainer() == null)
        return;
      
      // only allow changes in the home stratum
      if (!atHome())
      {
        coordinator.displayPopup(ERROR_ICON, "Delta error",
            new JLabel("Must be in home stratum to delete subjects!", DELTA_ICON, JLabel.RIGHT),
            ScreenProperties.getUndoPopupColor(),
            Color.black,
            3000);
        return;
      }

      // if this is a replace, kill the replace delta
      Element subject = getSubject();
      if (subject.getOwner() instanceof DeltaReplacedConstituent && subject.getOwner().getOwner() == extractVisualClassifier())
        generateReplaceDeltaKill(coordinator);
      else
        generateDeleteDelta(coordinator);
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
    final Classifier owner = extractVisualClassifier();

    // generate a delete delta
    coordinator.displayPopup(null, null,
        new JLabel("Delta delete", DELTA_ICON, JLabel.LEADING),
        ScreenProperties.getUndoPopupColor(),
        Color.black,
        1500);
    
    // add this to the classifier as a delete delta
    final Element feature = FeatureNodeGem.getOriginalSubject(getSubject());
    
    addBadConnectorDeletes(coordinator);
    
    // possibly resurrect
    DeltaDeletedConstituent delete = null;
    if (owner instanceof ClassImpl)
    {
      delete = ((Class) owner).createDeltaDeletedAttributes();
      delete.setDeleted(feature);
    }
    else
    if (owner instanceof InterfaceImpl)
    {
      delete = ((Interface) owner).createDeltaDeletedAttributes();
      delete.setDeleted(feature);
    }
  }

  private void addBadConnectorDeletes(ToolCoordinatorFacet coordinator)
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
          addDeleteCommand(cls, pair);
    }
  }

  private void addDeleteCommand(final Class cls, final DeltaPair pair)
  {
    // work out the appropriate object to delta delete or actually delete
    final Connector connector = (Connector) pair.getConstituent().getRepositoryObject();
    
    // if this connector exists in this classifier, remove it
    final SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
    if (connector.getOwner() == cls)
    {
      repository.incrementPersistentDelete(connector);
    }
    else
    // if this is a replace and we own it, delete the replace and add a delta delete
    if (connector.getOwner() instanceof DeltaReplacedConnector && connector.getOwner().getOwner() == cls)
    {      
    	DeltaDeletedConnector del = cls.createDeltaDeletedConnectors();
      del.setDeleted((Connector) pair.getOriginal().getRepositoryObject());
    }
    else
    // just generate a delete delta
    {      
      	DeltaDeletedConnector del = cls.createDeltaDeletedConnectors();
        del.setDeleted((Connector) pair.getOriginal().getRepositoryObject());
    }
  };
}
