package com.intrinsarc.evolve.umldiagrams.featurenode;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;
import com.intrinsarc.repositorybase.*;

/**
 * @author Andrew
 */
public final class AttributeCreatorGem implements Gem
{
  public static final int FEATURE_TYPE = 0;
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	private String stereotype;

  public AttributeCreatorGem()
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
	    return AttributeFeatureTypeFacetImpl.FIGURE_NAME;
	  }
	   
	  public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, false);
	  	FeatureNodeGem featureGem = new FeatureNodeGem((Property) subject);
	  	
	  	// connect the facets together
			featureGem.connectFeatureTypeFacet(
        new AttributeFeatureTypeFacetImpl(
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
			return "Attribute";
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
          new AttributeFeatureTypeFacetImpl(
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
  
      Classifier owner = (Classifier) repository.findOwningElement(
          containingReference, UML2Package.eINSTANCE.getClassifier());
      if (owner == null)
        return null;
      
      Property attr = null;
      if (owner instanceof Interface)
      {
        attr = ((Interface) owner).createOwnedAttribute();
        attr.setVisibility(VisibilityKind.PUBLIC_LITERAL);
      }
      else
      if (owner instanceof Class)
      {
      	Class cls = (Class) owner;
        attr = cls.createOwnedAttribute();
        if (cls.getComponentKind().equals(ComponentKind.NONE_LITERAL))
        	attr.setVisibility(VisibilityKind.PRIVATE_LITERAL);
        else
        	attr.setVisibility(VisibilityKind.PUBLIC_LITERAL);        	
      }

      attr.setName("attribute");
      attr.setType(repository.findPrimitiveType("type"));
      
      // should we set a stereotype?
      String stereoName = properties.retrieve(">stereotype", (String) null).asString();
      if (stereoName != null)
      {
        Stereotype stereo = GlobalSubjectRepository.repository.findStereotype(
            UML2Package.eINSTANCE.getProperty(), stereoName);
        if (stereo != null)
          attr.getAppliedBasicStereotypes().add(stereo);
      }

      return attr;
    }

		public void initialiseExtraProperties(PersistentProperties properties)
		{
      properties.addIfNotThere(new PersistentProperty(">stereotype", stereotype));
		}
  }
	
	public void setStereotype(String stereo)
	{
		this.stereotype = stereo;
	}
}
