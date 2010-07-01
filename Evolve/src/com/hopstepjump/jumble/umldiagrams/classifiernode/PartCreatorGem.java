package com.hopstepjump.jumble.umldiagrams.classifiernode;

import java.awt.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.colors.*;
import com.hopstepjump.repositorybase.*;

public class PartCreatorGem
{
  public String name;
  private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
  private boolean suppressAttributes = false;
  private boolean suppressOperations = true;
  private boolean displayOnlyIcon = false;
  private Preference fillColorPreference;
  private boolean statePart;
  
	public PartCreatorGem(boolean statePart)
  {
		this.statePart = statePart;
		if (statePart)
		{
			this.fillColorPreference = BaseColors.STATE_PART_COLOR;
			name = "State part";
		}
		else
		{
			this.fillColorPreference = BaseColors.PART_COLOR;
			name = "Part";
		}
  }

  public NodeCreateFacet getNodeCreateFacet()
  {
    return nodeCreateFacet;
  }

  private class NodeCreateFacetImpl implements NodeCreateFacet
  {
    public String getFigureName()
    {
      return "part";
    }
  
    public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, false);
      PersistentProperties actualProperties = new PersistentProperties();
      initialiseExtraProperties(actualProperties);
      ClassifierNodeGem classifierGem =
        new ClassifierNodeGem(
            diagram,
            getFillColor(),
      			new PersistentFigure(figureId, null, subject, actualProperties),
            true);
      basicGem.connectBasicNodeAppearanceFacet(classifierGem.getBasicNodeAppearanceFacet());
      basicGem.connectBasicNodeContainerFacet(classifierGem.getBasicNodeContainerFacet());
      
      // connect up the clipboard facet
      PartClipboardActionsGem clip = new PartClipboardActionsGem();
      clip.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      basicGem.connectClipboardCommandsFacet(clip.getClipboardCommandsFacet());
      
      classifierGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

      // add on the component appearance
      PartMiniAppearanceGem combinedGem = new PartMiniAppearanceGem();
      classifierGem.connectClassifierMiniAppearanceFacet(combinedGem.getClassifierMiniAppearanceFacet());
      combinedGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

      ComponentMiniAppearanceGem componentGem = new ComponentMiniAppearanceGem();
      componentGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      combinedGem.connectComponentMiniAppearanceFacet(componentGem.getClassifierMiniAppearanceFacet());
        
      InterfaceMiniAppearanceGem interfaceGem = new InterfaceMiniAppearanceGem();
      interfaceGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      combinedGem.connectInterfaceMiniAppearanceFacet(interfaceGem.getClassifierMiniAppearanceFacet());
        
      diagram.add(basicGem.getBasicNodeFigureFacet());
    }
  
    /**
     * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
     */
    public String getRecreatorName()
    {
      return name;
    }

    public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
      ClassifierNodeGem classifierGem =
        new ClassifierNodeGem(
        		getFillColor(),
        		true,
        		figure);
      basicGem.connectBasicNodeAppearanceFacet(classifierGem.getBasicNodeAppearanceFacet());
      basicGem.connectBasicNodeContainerFacet(classifierGem.getBasicNodeContainerFacet());
      classifierGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

      // connect up the clipboard facet
      PartClipboardActionsGem clip = new PartClipboardActionsGem();
      clip.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      basicGem.connectClipboardCommandsFacet(clip.getClipboardCommandsFacet());
      
      // add on the component appearance
      PartMiniAppearanceGem combinedGem = new PartMiniAppearanceGem();
      classifierGem.connectClassifierMiniAppearanceFacet(combinedGem.getClassifierMiniAppearanceFacet());
      combinedGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());

      ComponentMiniAppearanceGem componentGem = new ComponentMiniAppearanceGem();
      componentGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      combinedGem.connectComponentMiniAppearanceFacet(componentGem.getClassifierMiniAppearanceFacet());
        
      InterfaceMiniAppearanceGem interfaceGem = new InterfaceMiniAppearanceGem();
      interfaceGem.connectFigureFacet(basicGem.getBasicNodeFigureFacet());
      combinedGem.connectInterfaceMiniAppearanceFacet(interfaceGem.getClassifierMiniAppearanceFacet());

      return basicGem.getBasicNodeFigureFacet();
    }

    public Object createNewSubject(DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

      // see if we can find the class owner
      Element possibleOwner = repository.findOwningElement(
      		containingReference,
      		new EClass[]{
      				UML2Package.eINSTANCE.getClass_(),
      				UML2Package.eINSTANCE.getProperty()});
      Class owner = null;
      if (possibleOwner instanceof Class)
        owner = (Class) possibleOwner;
      if (possibleOwner instanceof Property)
        owner = (Class) ((Property) possibleOwner).getType();
      
      // create the property with the appropriate instance specification
      Property part = owner.createOwnedAttribute();
      InstanceValue instanceValue = (InstanceValue) part.createDefaultValue(UML2Package.eINSTANCE.getInstanceValue());
      InstanceSpecification instanceSpecification = instanceValue.createOwnedAnonymousInstanceValue();
      instanceValue.setInstance(instanceSpecification);
      
      // possibly set the name
      String name = properties.retrieve("name", (String) null).asString();
      if (name != null)
        part.setName(name);
      
      // possibly set the type
      if (relatedSubject instanceof Type)
        part.setType((Type) relatedSubject);
      
      // parts have composite aggregation kind
      part.setAggregation(AggregationKind.COMPOSITE_LITERAL);
      
    	// should we set a stereotype?
    	String stereoName = properties.retrieve(">stereotype", (String) null).asString();
    	if (stereoName != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getProperty(), stereoName);
        if (stereo != null)
          part.getAppliedBasicStereotypes().add(stereo);
      }
      
      return part;
    }
    
		public void initialiseExtraProperties(PersistentProperties properties)
		{
      properties.addIfNotThere(new PersistentProperty("supA", suppressAttributes, false));
      properties.addIfNotThere(new PersistentProperty("supO", suppressOperations, false));
      properties.addIfNotThere(new PersistentProperty("icon", displayOnlyIcon, false));
			properties.addIfNotThere(new PersistentProperty("auto", false, true));
      properties.addIfNotThere(new PersistentProperty("fill", getFillColor(), Color.WHITE));
      if (statePart)
        properties.addIfNotThere(new PersistentProperty(">stereotype", "state-part"));
		}		
  }
  
  public void setFillColorPreference(Preference fillColorPreference)
	{
		this.fillColorPreference = fillColorPreference;
	}

	private Color getFillColor()
	{
		if (fillColorPreference == null)
			return BaseColors.getColorPreference(BaseColors.PART_COLOR);
		return BaseColors.getColorPreference(fillColorPreference);
	}
}