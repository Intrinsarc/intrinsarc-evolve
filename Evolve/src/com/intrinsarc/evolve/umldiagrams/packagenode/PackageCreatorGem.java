package com.intrinsarc.evolve.umldiagrams.packagenode;

import java.awt.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.evolve.umldiagrams.basicnamespacenode.*;
import com.intrinsarc.evolve.umldiagrams.colors.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.repositorybase.*;


public final class PackageCreatorGem implements Gem
{
	public static final String NAME = "Package";
	private Preference fillColorPreference;
	private static final String FIGURE_NAME = "package";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	private boolean autoSized;
  private boolean relaxed;
  private String stereotype;
  private boolean displayOnlyIcon;
	
  public PackageCreatorGem(boolean autoSized)
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

      // find a possible nested package
      Package owner = (Package) diagram.getLinkedObject();
      
      // see if we can find a better owner, based on the containing figure
      if (containingReference != null)
      {
        FigureFacet container = GlobalDiagramRegistry.registry.retrieveFigure(containingReference);
        Package possibleOwner = (Package) repository.findOwningElement(
            container.getContainerFacet().getFigureFacet().getFigureReference(), UML2Package.eINSTANCE.getPackage());
        if (possibleOwner != null)
          owner = possibleOwner;
      }
      
      // get the package associated with this diagram, and add the new package to it
      Package pkg = owner.createChildPackages(UML2Package.eINSTANCE.getPackage());
      
      // should we set a stereotype?
      String stereoName = properties.retrieve(">stereotype", "").asString();
      if (stereoName != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getPackage(), stereoName);
        if (stereo != null)
          pkg.getAppliedBasicStereotypes().add(stereo);
      }

      // should we set relaxed on a possible stratum?
      boolean relaxed = properties.retrieve(">relaxed", false).asBoolean();
      if (relaxed)
      {
        StereotypeUtilities.setBooleanRawStereotypeAttributeTransaction(
            pkg, CommonRepositoryFunctions.RELAXED, relaxed);
      }

      return pkg;
    }

    public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
      NamedElement owner = (NamedElement) ((subject == null) ? null : ((Package) subject).getOwner());
      PersistentProperties actualProperties = new PersistentProperties(properties);
      initialiseExtraProperties(actualProperties);
      
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, actualProperties.retrieve(">autoSized", false).asBoolean(), false);
	  	BasicNamespaceNodeGem packageGem = new BasicNamespaceNodeGem(
          (Package) subject,
          owner == null ? "" : owner.getName(),
              diagram,
              figureId,
              FIGURE_NAME,
              actualProperties.retrieve(">fillColor").asColor(),
              actualProperties.retrieve(">displayOnlyIcon").asBoolean());
			basicGem.connectBasicNodeAppearanceFacet(packageGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(packageGem.getBasicNodeContainerFacet());
			packageGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			PackageAppearanceGem appearanceGem = new PackageAppearanceGem();
			packageGem.connectFeaturelessClassifierAppearanceFacet(appearanceGem.getPackageAppearanceFacet());
			appearanceGem.connectFeaturelessClassifierNodeFacet(packageGem.getBasicNamespaceNodeFacet());

      PackageMiniAppearanceGem miniGem = new PackageMiniAppearanceGem();
      miniGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
			packageGem.connectBasicNamespaceMiniAppearanceFacet(miniGem.getBasicNamespaceMiniAppearanceFacet());

	
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
			PackageAppearanceGem appearanceGem = new PackageAppearanceGem();
			packageGem.connectFeaturelessClassifierAppearanceFacet(appearanceGem.getPackageAppearanceFacet());
			appearanceGem.connectFeaturelessClassifierNodeFacet(packageGem.getBasicNamespaceNodeFacet());
      
      PackageMiniAppearanceGem miniGem = new PackageMiniAppearanceGem();
      miniGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      packageGem.connectBasicNamespaceMiniAppearanceFacet(miniGem.getBasicNamespaceMiniAppearanceFacet());
	
	    return basicGem.getBasicNodeFigureFacet();
		}
		
		public void initialiseExtraProperties(PersistentProperties properties)
		{
      properties.addIfNotThere(new PersistentProperty(">stereotype", stereotype));
      properties.addIfNotThere(new PersistentProperty(">displayOnlyIcon", displayOnlyIcon, false));
      properties.addIfNotThere(new PersistentProperty(">fillColor", getFillColor(), null));
      properties.addIfNotThere(new PersistentProperty(">relaxed", relaxed, false));
      properties.addIfNotThere(new PersistentProperty(">autoSized", autoSized, false));
		}
	}

  public void setStereotype(String stereotypeName)
  {
    this.stereotype = stereotypeName;
  }

  public void setFillColorPreference(Preference pref)
  {
    this.fillColorPreference = pref;
  }

  public void setRelaxed(boolean relaxed)
  {
    this.relaxed = relaxed;
  }

  public void setDisplayOnlyIcon(boolean displayOnlyIcon)
  {
    this.displayOnlyIcon = displayOnlyIcon;
  }
  	
	private Color getFillColor()
	{
		if (fillColorPreference == null)
			return BaseColors.getColorPreference(BaseColors.PACKAGE_COLOR);
		return BaseColors.getColorPreference(fillColorPreference);
	}
}