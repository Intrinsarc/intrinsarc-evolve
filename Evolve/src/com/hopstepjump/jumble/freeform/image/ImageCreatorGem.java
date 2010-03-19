package com.hopstepjump.jumble.freeform.image;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.repositorybase.*;

/**
 * an image node creator
 * @author amcveigh
 */
public final class ImageCreatorGem implements Gem
{
  public static final String NAME = "Image";
  private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
  
  public ImageCreatorGem()
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
      return ImageNodeGem.FIGURE_NAME;
    }
   
    public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, true, true);
      ImageNodeGem measureBoxGem = new ImageNodeGem(subject);
      basicGem.connectBasicNodeAppearanceFacet(measureBoxGem.getBasicNodeAppearanceFacet());
      measureBoxGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      
      diagram.add(basicGem.getBasicNodeFigureFacet());
    }
    
    /**
     * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
     */
    public String getRecreatorName()
    {
      return NAME;
    }

    /**
     * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
     */
    public FigureFacet createFigure(
      DiagramFacet diagram, PersistentFigure figure)
    {
      BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, true);
      ImageNodeGem measureBoxGem = new ImageNodeGem(figure);
      basicGem.connectBasicNodeAppearanceFacet(measureBoxGem.getBasicNodeAppearanceFacet());
      measureBoxGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
      return basicGem.getBasicNodeFigureFacet();
    }

    public Object createNewSubject(DiagramFacet diagram, FigureReference containingReference, Object relatedSubject, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

      // no nested classes currently supported
      Namespace owner = (Package) diagram.getLinkedObject();
      
      // see if we can find a better owner, based on the containing figure
      if (containingReference != null)
      {
        FigureFacet container = GlobalDiagramRegistry.registry.retrieveFigure(containingReference);
        owner = repository.findVisuallyOwningNamespace(diagram, container.getContainerFacet());
      }
      
      // get the package associated with this diagram, and add the new package to it
      Comment comment = owner.createOwnedComment();

      return comment;
    }

    public void initialiseExtraProperties(PersistentProperties properties)
    {
    }
  }
}
