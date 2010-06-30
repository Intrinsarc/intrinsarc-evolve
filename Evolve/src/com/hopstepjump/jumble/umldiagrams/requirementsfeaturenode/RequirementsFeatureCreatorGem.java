package com.hopstepjump.jumble.umldiagrams.requirementsfeaturenode;

import java.awt.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.colors.*;
import com.hopstepjump.repositorybase.*;

public final class RequirementsFeatureCreatorGem implements Gem
{
	public static final String NAME = "feature";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	private boolean displayOnlyIcon;
	private boolean showStereotype = true;
	private boolean autoSized = true;
	private String stereotype;
  private String stereotype2;
  private String resemblanceUUID;
  private Preference fillColorPreference;
	
	public RequirementsFeatureCreatorGem()
	{
	}

	public NodeCreateFacet getNodeCreateFacet()
	{
		return nodeCreateFacet;
	}

	private class NodeCreateFacetImpl implements NodeCreateFacet
	{
		public String getFigureName()
		{
			return NAME;
		}
	
    public Object createNewSubject(DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

      // nested classes live in the nested classifier link...
      Namespace owner = (Namespace) diagram.getLinkedObject();
      
      // see if we can find a better owner, based on the containing figure
      if (containingReference != null)
      {
        FigureFacet container = GlobalDiagramRegistry.registry.retrieveFigure(containingReference);
        owner = repository.findVisuallyOwningPackage(diagram, container.getContainerFacet());
      }
      
      // get the package associated with this diagram, and add the new feature to it
       RequirementsFeature req = (RequirementsFeature) ((Package) owner).createOwnedMember(UML2Package.eINSTANCE.getRequirementsFeature());
      
    	// should we set a stereotype?
    	String stereoName = properties.retrieve(">stereotype", (String) null).asString();
    	if (stereoName != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getClass_(), stereoName);
        if (stereo != null)
          req.getAppliedBasicStereotypes().add(stereo);
      }
      
      // should we set a second stereotype?
      String stereoName2 = properties.retrieve(">stereotype2", (String) null).asString();
      if (stereoName2 != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getClass_(), stereoName2);
        if (stereo != null)
          req.getAppliedBasicStereotypes().add(stereo);
      }
      
    	// possibly set up a resemblance relationship
      PersistentProperty res = properties.retrieve(">resemblanceUUID", (String) null);
      if (res.asString() != null)
      {
      	NamedElement superElement = (NamedElement) GlobalSubjectRepository.repository.findNamedElementByUUID(res.asString());
      	Dependency dep = req.createOwnedAnonymousDependencies();
        dep.settable_getClients().add(req);
        dep.setDependencyTarget(superElement);
        dep.setResemblance(true);
      }
       
      return req;
    }

    public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
		{
			BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, false);
			PersistentProperties actualProperties = new PersistentProperties(properties);
			initialiseExtraProperties(actualProperties);
			RequirementsFeatureNodeGem classifierGem =
				new RequirementsFeatureNodeGem(
            diagram,
            getFillColor(),
      			new PersistentFigure(figureId, null, subject, actualProperties));
			basicGem.connectBasicNodeAppearanceFacet(classifierGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(classifierGem.getBasicNodeContainerFacet());
			RequirementsFeatureClipboardActionsGem clip = new RequirementsFeatureClipboardActionsGem();
			clip.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectClipboardCommandsFacet(clip.getClipboardCommandsFacet());
			classifierGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			
      // add on the component appearance
      RequirementsFeatureMiniAppearanceGem appearanceGem = new RequirementsFeatureMiniAppearanceGem();
			classifierGem.connectClassifierMiniAppearanceFacet(appearanceGem.getRequirementsFeatureMiniAppearanceFacet());
      appearanceGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
				
			diagram.add(basicGem.getBasicNodeFigureFacet());
		}
    
		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
			return NAME;
		}

		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
			BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
			RequirementsFeatureNodeGem classifierGem =
				new RequirementsFeatureNodeGem(getFillColor(), figure);
			basicGem.connectBasicNodeAppearanceFacet(classifierGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(classifierGem.getBasicNodeContainerFacet());
			RequirementsFeatureClipboardActionsGem clip = new RequirementsFeatureClipboardActionsGem();
			clip.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectClipboardCommandsFacet(clip.getClipboardCommandsFacet());
			classifierGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

      // add on the component appearance
      RequirementsFeatureMiniAppearanceGem appearanceGem = new RequirementsFeatureMiniAppearanceGem();
      classifierGem.connectClassifierMiniAppearanceFacet(appearanceGem.getRequirementsFeatureMiniAppearanceFacet());
      appearanceGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
        
			return basicGem.getBasicNodeFigureFacet();
		}
		
		public void initialiseExtraProperties(PersistentProperties properties)
		{
			properties.addIfNotThere(new PersistentProperty("auto", autoSized, true));
			properties.addIfNotThere(new PersistentProperty("icon", displayOnlyIcon, false));
      properties.addIfNotThere(new PersistentProperty("fill", getFillColor(), Color.WHITE));
      properties.addIfNotThere(new PersistentProperty("showStereo", showStereotype, true));
      properties.addIfNotThere(new PersistentProperty(">stereotype", stereotype));
      properties.addIfNotThere(new PersistentProperty(">stereotype2", stereotype2));
      properties.addIfNotThere(new PersistentProperty(">resemblanceUUID", resemblanceUUID));
		}
	}

	public void setDisplayOnlyIcon(boolean displayOnlyIcon)
	{
		this.displayOnlyIcon = displayOnlyIcon;
	}

	public void setShowStereotype(boolean showStereotype)
	{
		this.showStereotype = showStereotype;
	}

	public void setAutoSized(boolean autoSized)
	{
		this.autoSized = autoSized;
	}

	public void setStereotype(String stereotypeName)
	{
		this.stereotype = stereotypeName;
	}
  
  public void setFillColorPreference(Preference pref)
  {
    this.fillColorPreference = pref;
  }

  public void setStereotype2(String stereotype2)
  {
    this.stereotype2 = stereotype2;
  }

	public void setResemblance(String uuid)
	{
		this.resemblanceUUID = uuid;
	}
	
	private Color getFillColor()
	{
		if (fillColorPreference == null)
			return BaseColors.getColorPreference(BaseColors.REQUIREMENTS_FEATURE_COLOR);
		return BaseColors.getColorPreference(fillColorPreference);
	}
}