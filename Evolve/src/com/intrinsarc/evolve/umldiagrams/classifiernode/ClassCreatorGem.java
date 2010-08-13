package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.awt.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.umldiagrams.colors.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.repositorybase.*;

public final class ClassCreatorGem implements Gem
{
	public static final String NAME = "Class";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	private boolean suppressAttributes;
	private boolean suppressOperations;
	private boolean displayOnlyIcon;
	private boolean showStereotype = true;
	private boolean autoSized = true;
	private boolean suppressContents;
	private boolean placeholder;
	private boolean factory;
	private ComponentKindEnum kind = ComponentKindEnum.NONE;
	private String stereotype;
  private String stereotype2;
  private String resemblanceUUID;
  private Preference fillColorPreference;
	
	public ClassCreatorGem()
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
			return "class";
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
        owner = repository.findVisuallyOwningNamespace(diagram, container.getContainerFacet());
      }
      
      Class cls = null;
      // get the package associated with this diagram, and add the new package to it
      if (owner instanceof Class)
      {
      	cls = (Class) ((Class) owner).createNestedClassifier(UML2Package.eINSTANCE.getClass_());
      }
      else
        cls = (Class) ((Package) owner).createOwnedMember(UML2Package.eINSTANCE.getClass_());
      
    	// should we set a stereotype?
    	String stereoName = properties.retrieve(">stereotype", (String) null).asString();
    	if (stereoName != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getClass_(), stereoName);
        if (stereo != null)
          cls.getAppliedBasicStereotypes().add(stereo);
      }
      
      // should we set a second stereotype?
      String stereoName2 = properties.retrieve(">stereotype2", (String) null).asString();
      if (stereoName2 != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getClass_(), stereoName2);
        if (stereo != null)
          cls.getAppliedBasicStereotypes().add(stereo);
      }
      
      // is this a placeholder
      PersistentProperty place = properties.retrieve(">placeholder", false);
      if (place.asBoolean())
      	StereotypeUtilities.setBooleanRawStereotypeAttributeTransaction(cls, CommonRepositoryFunctions.PLACEHOLDER, true);
      
      // is this a factory
      PersistentProperty fact = properties.retrieve(">factory", false);
      if (fact.asBoolean())
      	StereotypeUtilities.setBooleanRawStereotypeAttributeTransaction(cls, CommonRepositoryFunctions.FACTORY, true);
      
      // set the kind
      PersistentProperty k = properties.retrieve(">kind", 0);
    	cls.setComponentKind(ComponentKind.get(k.asInteger()));
    	
    	// possibly set up a resemblance relationship
      PersistentProperty res = properties.retrieve(">resemblanceUUID", (String) null);
      if (res.asString() != null)
      {
      	NamedElement superElement = (NamedElement) GlobalSubjectRepository.repository.findNamedElementByUUID(res.asString());
      	Dependency dep = cls.createOwnedAnonymousDependencies();
        dep.settable_getClients().add(cls);
        dep.setDependencyTarget(superElement);
        dep.setResemblance(true);
      }
       
      return cls;
    }

    public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
		{
			BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, false);
			PersistentProperties actualProperties = new PersistentProperties(properties);
			initialiseExtraProperties(actualProperties);
			ClassifierNodeGem classifierGem =
				new ClassifierNodeGem(
            diagram,
            getFillColor(),
      			new PersistentFigure(figureId, null, subject, actualProperties),
            false);
			basicGem.connectBasicNodeAppearanceFacet(classifierGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(classifierGem.getBasicNodeContainerFacet());
			ClassifierClipboardActionsGem clip = new ClassifierClipboardActionsGem(false, false);
			clip.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectClipboardCommandsFacet(clip.getClipboardCommandsFacet());
			classifierGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			
      // add on the component appearance
      ComponentMiniAppearanceGem appearanceGem = new ComponentMiniAppearanceGem();
			classifierGem.connectClassifierMiniAppearanceFacet(appearanceGem.getClassifierMiniAppearanceFacet());
      appearanceGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
				
			diagram.add(basicGem.getBasicNodeFigureFacet());
		}
    
		/**
		 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
			return NAME;
		}

		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
			BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
			ClassifierNodeGem classifierGem =
				new ClassifierNodeGem(getFillColor(), false, figure);
			basicGem.connectBasicNodeAppearanceFacet(classifierGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(classifierGem.getBasicNodeContainerFacet());
			ClassifierClipboardActionsGem clip = new ClassifierClipboardActionsGem(false, false);
			clip.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectClipboardCommandsFacet(clip.getClipboardCommandsFacet());
			classifierGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

      // add on the component appearance
      ComponentMiniAppearanceGem appearanceGem = new ComponentMiniAppearanceGem();
      classifierGem.connectClassifierMiniAppearanceFacet(appearanceGem.getClassifierMiniAppearanceFacet());
      appearanceGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
        
			return basicGem.getBasicNodeFigureFacet();
		}
		
		public void initialiseExtraProperties(PersistentProperties properties)
		{
			properties.addIfNotThere(new PersistentProperty("supA", suppressAttributes, false));
			properties.addIfNotThere(new PersistentProperty("supC", suppressContents, false));
			properties.addIfNotThere(new PersistentProperty("supO", suppressOperations, false));
			properties.addIfNotThere(new PersistentProperty("auto", autoSized, true));
			properties.addIfNotThere(new PersistentProperty("icon", displayOnlyIcon, false));
      properties.addIfNotThere(new PersistentProperty("fill", getFillColor(), Color.WHITE));
      properties.addIfNotThere(new PersistentProperty("showStereo", showStereotype, true));
      properties.addIfNotThere(new PersistentProperty(">stereotype", stereotype));
      properties.addIfNotThere(new PersistentProperty(">stereotype2", stereotype2));
      properties.addIfNotThere(new PersistentProperty(">placeholder", placeholder, false));
      properties.addIfNotThere(new PersistentProperty(">factory", factory, false));
      properties.addIfNotThere(new PersistentProperty(">kind", kind.ordinal(), 0));
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

	public void setSuppressAttributes(boolean suppressAttributes)
	{
		this.suppressAttributes = suppressAttributes;
	}

	public void setSuppressOperations(boolean suppressOperations)
	{
		this.suppressOperations = suppressOperations;
	}

	public void setAutoSized(boolean autoSized)
	{
		this.autoSized = autoSized;
	}

	public void setStereotype(String stereotypeName)
	{
		this.stereotype = stereotypeName;
	}
  
  public void setFillColorPrefence(Preference pref)
  {
    this.fillColorPreference = pref;
  }

  public void setStereotype2(String stereotype2)
  {
    this.stereotype2 = stereotype2;
  }

	public void setSuppressContents(boolean suppress)
	{
		this.suppressContents = suppress;
	}

	public void setPlaceholder(boolean placeholder)
	{
		this.placeholder = placeholder;
	}
	
	public void setFactory(boolean factory)
	{
		this.factory = factory;
	}
	
	public void setComponentKind(ComponentKindEnum kind)
	{
		this.kind = kind;
	}

	public void setResemblance(String uuid)
	{
		this.resemblanceUUID = uuid;
	}
	
	private Color getFillColor()
	{
		if (fillColorPreference == null)
			return BaseColors.getColorPreference(BaseColors.COMPONENT_COLOR);
		return BaseColors.getColorPreference(fillColorPreference);
	}
}