package com.hopstepjump.jumble.umldiagrams.slotnode;

import org.eclipse.uml2.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.featurenode.*;
import com.hopstepjump.repositorybase.*;

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
	   
	  public Object createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
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
	    return new FigureReference(diagram, figureId);
	  }
    
	  public void unCreateFigure(Object memento)
	  {
	    FigureReference figureReference = (FigureReference) memento;
	    DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(figureReference.getDiagramReference());
	    FigureFacet figure = GlobalDiagramRegistry.registry.retrieveFigure(figureReference);
	    diagram.remove(figure);
	  }
	  
		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
			return "Slot";
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
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

    public Object createNewSubject(Object previouslyCreated, DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
  
      // possibly resurrect
      if (previouslyCreated != null)
      {
        repository.decrementPersistentDelete((Element) previouslyCreated);
        return previouslyCreated;
      }
  
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

    public void uncreateNewSubject(Object previouslyCreated)
    {
      GlobalSubjectRepository.repository.incrementPersistentDelete((Element) previouslyCreated);
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

