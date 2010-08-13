package com.intrinsarc.evolve.umldiagrams.packagenode;

import java.awt.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.evolve.umldiagrams.basicnamespacenode.*;
import com.intrinsarc.evolve.umldiagrams.colors.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.repositorybase.*;

/**
 *
 * (c) Andrew McVeigh 27-Aug-02
 *
 */
public class ModelCreatorGem implements Gem
{
  public static final String NAME = "Model";
  private static final String FIGURE_NAME = "model";
  private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
  private boolean autoSized;
  
  public ModelCreatorGem(boolean autoSized)
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
      Model pkg = (Model) owner.createChildPackages(UML2Package.eINSTANCE.getModel());
      return pkg;
    }

    public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
    {
      NamedElement owner = (NamedElement) ((subject == null) ? null : ((Package) subject).getOwner());
      
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, autoSized, false);
      BasicNamespaceNodeGem packageGem = new BasicNamespaceNodeGem(
      		(Package) subject, owner == null ? "" : owner.getName(), diagram, figureId, FIGURE_NAME, BaseColors.getColorPreference(BaseColors.MODEL_COLOR), false);
      basicGem.connectBasicNodeAppearanceFacet(packageGem.getBasicNodeAppearanceFacet());
      basicGem.connectBasicNodeContainerFacet(packageGem.getBasicNodeContainerFacet());
      packageGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      ModelMiniAppearanceGem mini = new ModelMiniAppearanceGem();
      packageGem.connectBasicNamespaceMiniAppearanceFacet(mini.getBasicNamespaceMiniAppearanceFacet());
      mini.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      PackageAppearanceGem appearanceGem = new PackageAppearanceGem();
      packageGem.connectFeaturelessClassifierAppearanceFacet(appearanceGem.getPackageAppearanceFacet());
      appearanceGem.connectFeaturelessClassifierNodeFacet(packageGem.getBasicNamespaceNodeFacet());
  
      diagram.add(basicGem.getBasicNodeFigureFacet());
    }
    
    /**
     * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreator#getFullName()
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
      ModelMiniAppearanceGem mini = new ModelMiniAppearanceGem();
      packageGem.connectBasicNamespaceMiniAppearanceFacet(mini.getBasicNamespaceMiniAppearanceFacet());
      mini.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      PackageAppearanceGem appearanceGem = new PackageAppearanceGem();
      packageGem.connectFeaturelessClassifierAppearanceFacet(appearanceGem.getPackageAppearanceFacet());
      appearanceGem.connectFeaturelessClassifierNodeFacet(packageGem.getBasicNamespaceNodeFacet());
  
      return basicGem.getBasicNodeFigureFacet();
    }
    
		public void initialiseExtraProperties(PersistentProperties properties)
		{
		}
  }
}