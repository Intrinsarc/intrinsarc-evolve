package com.intrinsarc.evolve.umldiagrams.slotnode;

import org.eclipse.uml2.*;

import com.intrinsarc.evolve.umldiagrams.featurenode.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.repositorybase.*;

/**
 * @author andrew
 */
public class SlotCreatorGem implements Gem
{
  public static final int FEATURE_TYPE = 2;
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
  private String stereotype;

  public SlotCreatorGem()
  {
  }

  public SlotCreatorGem(String stereotype)
  {
  	this.stereotype = stereotype;
  }

	public NodeCreateFacet getNodeCreateFacet()
	{
		return nodeCreateFacet;
	}

	private class NodeCreateFacetImpl implements NodeCreateFacet
	{
	  public String getFigureName()
	  {
	    return SlotFeatureTypeFacetImpl.FIGURE_NAME;
	  }
	   
	  public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, false);
	  	FeatureNodeGem featureGem = new FeatureNodeGem((Slot) subject);
	  	
      PersistentProperties actualProperties = new PersistentProperties(properties);
      initialiseExtraProperties(actualProperties);
      
	  	// connect the facets together
			featureGem.connectFeatureTypeFacet(
        new SlotFeatureTypeFacetImpl(
            basicGem.getBasicNodeFigureFacet(),
            featureGem.getTextableFacet()));
			featureGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectBasicNodeAppearanceFacet(featureGem.getBasicNodeAppearanceFacet());
	    basicGem.connectClipboardCommandsFacet(featureGem.getClipboardCommandsFacet());
	    diagram.add(basicGem.getBasicNodeFigureFacet());
	  }
    
		/**
		 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
			return "Slot";
		}

		/**
		 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
		 */
		public FigureFacet createFigure(
			DiagramFacet diagram, PersistentFigure figure)
		{
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
	  	FeatureNodeGem featureGem = new FeatureNodeGem(figure);
	  	// connect the facets together
			featureGem.connectFeatureTypeFacet(
          new SlotFeatureTypeFacetImpl(
              basicGem.getBasicNodeFigureFacet(),
              featureGem.getTextableFacet()));
			featureGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			basicGem.connectBasicNodeAppearanceFacet(featureGem.getBasicNodeAppearanceFacet());
      basicGem.connectClipboardCommandsFacet(featureGem.getClipboardCommandsFacet());
			
			return basicGem.getBasicNodeFigureFacet();
		}

    public Object createNewSubject(DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

      // find the instance specification of the part
      Property part = (Property) repository.findOwningElement(
          containingReference, UML2Package.eINSTANCE.getProperty());
      Property attribute = (Property) relatedSubject;
      InstanceSpecification instance = UMLTypes.extractInstanceOfPart(part);
      
      // create the slot
      Slot slot = instance.createSlot();
      slot.setDefiningFeature(attribute);
      Expression expression = (Expression) slot.createValue(UML2Package.eINSTANCE.getExpression());
      expression.setBody("value");

      // should we set a stereotype?
      String stereoName = properties.retrieve(">stereotype", (String) null).asString();
      if (stereoName != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getSlot(), stereoName);
        if (stereo != null)
          slot.getAppliedBasicStereotypes().add(stereo);
      }
      
      return slot;
    }
    
		public void initialiseExtraProperties(PersistentProperties properties)
		{
      properties.addIfNotThere(new PersistentProperty(">stereotype", stereotype));
		}
  }
  
  public void setStereotype(String stereotypeName)
  {
    this.stereotype = stereotypeName;
  }
}

