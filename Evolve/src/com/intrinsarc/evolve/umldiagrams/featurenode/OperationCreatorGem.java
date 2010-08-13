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

public final class OperationCreatorGem implements Gem
{
  public static final int FEATURE_TYPE = 1;
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	
  public OperationCreatorGem()
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
	    return OperationFeatureTypeFacetImpl.FIGURE_NAME;
	  }
	
	  public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, false);
	  	FeatureNodeGem featureGem = new FeatureNodeGem((Feature) subject);
	  	
	  	// wire the facets together
			featureGem.connectFeatureTypeFacet(
          new OperationFeatureTypeFacetImpl(
              basicGem.getBasicNodeFigureFacet(),
              featureGem.getTextableFacet()));
			basicGem.connectBasicNodeAppearanceFacet(featureGem.getBasicNodeAppearanceFacet());
	    basicGem.connectClipboardCommandsFacet(featureGem.getClipboardCommandsFacet());
			featureGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
	
	    diagram.add(basicGem.getBasicNodeFigureFacet());
	  }

	  /**
		 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
		 */
		public String getRecreatorName()
		{
	    return "Operation";
		}

		/**
		 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
		 */
		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
	  	FeatureNodeGem featureGem = new FeatureNodeGem(figure);
	  	
	  	// wire the facets together
			featureGem.connectFeatureTypeFacet(
          new OperationFeatureTypeFacetImpl(
              basicGem.getBasicNodeFigureFacet(),
              featureGem.getTextableFacet()));
			basicGem.connectBasicNodeAppearanceFacet(featureGem.getBasicNodeAppearanceFacet());
			featureGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
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
      
      Operation operation = null;
      // this is a pain -- caused by poor commonality of operation ownership in the metamodel
      if (owner instanceof Interface)
        operation = ((Interface) owner).createOwnedOperation();
      else
      if (owner instanceof Class)
        operation = ((Class) owner).createOwnedOperation();
      else
      if (owner instanceof PrimitiveType)
      	operation = ((PrimitiveType) owner).createOwnedOperation();
      
      operation.setName("operation");
      operation.setVisibility(VisibilityKind.PUBLIC_LITERAL);
      return operation;
    }

    public void initialiseExtraProperties(PersistentProperties properties)
		{
		}
	}
}
