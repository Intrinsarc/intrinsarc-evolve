package com.hopstepjump.jumble.umldiagrams.stereotypenode;

import java.awt.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.basicnamespacenode.*;
import com.hopstepjump.jumble.umldiagrams.packagenode.*;
import com.hopstepjump.repositorybase.*;

public class ProfileCreatorGem implements Gem
{
  public static final String NAME = "Profile";
  private static final Color FILL_COLOR =  new Color(252, 255, 206);
  private static final String FIGURE_NAME = "profile";
  private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
  private boolean autoSized;
  
  public ProfileCreatorGem(boolean autoSized)
  {
    this.autoSized = autoSized;
  }

  public NodeCreateFacet getNodeCreateFacet()
  {
    return nodeCreateFacet;
  }
  
  private class NodeCreateFacetImpl implements NodeCreateFacet
  {
    public String getFigureName()
    {
      return FIGURE_NAME;
    }
  
    public Object createNewSubject(DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

      // no nested classes currently supported
      Package owner = (Package) diagram.getLinkedObject();
      
      // see if we can find a better owner, based on the containing figure
      if (containingReference != null)
      {
        FigureFacet container = GlobalDiagramRegistry.registry.retrieveFigure(containingReference);
        owner = (Package) repository.findVisuallyOwningNamespace(diagram, container.getContainerFacet());
      }
      
      // get the package associated with this diagram, and add the new package to it
      Profile pkg = (Profile) owner.createChildPackages(UML2Package.eINSTANCE.getProfile());
      return pkg;
    }

    public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
    {
      NamedElement owner = (NamedElement) ((subject == null) ? null : ((Package) subject).getOwner());
      
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, autoSized, false);
      BasicNamespaceNodeGem packageGem = new BasicNamespaceNodeGem((Package) subject, owner == null ? "" : owner.getName(), diagram, figureId, FIGURE_NAME, FILL_COLOR, false);
      basicGem.connectBasicNodeAppearanceFacet(packageGem.getBasicNodeAppearanceFacet());
      basicGem.connectBasicNodeContainerFacet(packageGem.getBasicNodeContainerFacet());
      packageGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      ProfileMiniAppearanceGem miniGem = new ProfileMiniAppearanceGem();
      packageGem.connectBasicNamespaceMiniAppearanceFacet(miniGem.getBasicNamespaceMiniAppearanceFacet());
      miniGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      PackageAppearanceGem appearanceGem = new PackageAppearanceGem();
      packageGem.connectFeaturelessClassifierAppearanceFacet(appearanceGem.getPackageAppearanceFacet());
      appearanceGem.connectFeaturelessClassifierNodeFacet(packageGem.getBasicNamespaceNodeFacet());
  
      diagram.add(basicGem.getBasicNodeFigureFacet());
    }
    
    /**
     * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreator#getFullName()
     */
    public String getRecreatorName()
    {
      return NAME;
    }

    public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
      BasicNamespaceNodeGem packageGem = new BasicNamespaceNodeGem(FIGURE_NAME, figure);
      basicGem.connectBasicNodeAppearanceFacet(packageGem.getBasicNodeAppearanceFacet());
      basicGem.connectBasicNodeContainerFacet(packageGem.getBasicNodeContainerFacet());
      packageGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      PackageAppearanceGem appearanceGem = new PackageAppearanceGem();
      ProfileMiniAppearanceGem miniGem = new ProfileMiniAppearanceGem();
      packageGem.connectBasicNamespaceMiniAppearanceFacet(miniGem.getBasicNamespaceMiniAppearanceFacet());
      miniGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      packageGem.connectFeaturelessClassifierAppearanceFacet(appearanceGem.getPackageAppearanceFacet());
      appearanceGem.connectFeaturelessClassifierNodeFacet(packageGem.getBasicNamespaceNodeFacet());
  
      return basicGem.getBasicNodeFigureFacet();
    }
    
		public void initialiseExtraProperties(PersistentProperties properties)
		{
		}
  }
}
