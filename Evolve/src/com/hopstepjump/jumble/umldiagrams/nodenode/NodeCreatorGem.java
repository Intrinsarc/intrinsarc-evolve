package com.hopstepjump.jumble.umldiagrams.nodenode;

import java.awt.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.basicnamespacenode.*;
import com.hopstepjump.repositorybase.*;

/**
 *
 * (c) Andrew McVeigh 29-Aug-02
 *
 */
public class NodeCreatorGem
{
	public static final String NAME = "Node";
	private static final String FIGURE_NAME = "node";
	private NodeCreateFacet nodeCreateFacet = new NodeCreateFacetImpl();
	
  public NodeCreatorGem(boolean autoSized)
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
	    return FIGURE_NAME;
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

      // no nested classes currently supported
      Package owner = (Package) diagram.getLinkedObject();
      
      // see if we can find a better owner, based on the containing figure
      if (containingReference != null)
      {
        FigureFacet container = GlobalDiagramRegistry.registry.retrieveFigure(containingReference);
        owner = (Package) repository.findVisuallyOwningNamespace(diagram, container.getContainerFacet());
      }
      
      // get the package associated with this diagram, and add the new package to it
      return owner.createOwnedMember(UML2Package.eINSTANCE.getNode());
    }

    public void createFigure(Object subject, DiagramFacet diagram, String figureId, UPoint location, PersistentProperties properties)
	  {
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figureId, location, false, false);
	  	BasicNamespaceNodeGem componentGem = new BasicNamespaceNodeGem((Node) subject, "", diagram, figureId, FIGURE_NAME, new Color(250, 255, 240), false);
			basicGem.connectBasicNodeAppearanceFacet(componentGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(componentGem.getBasicNodeContainerFacet());
			componentGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			componentGem.connectFeaturelessClassifierAppearanceFacet(new NodeAppearanceGem().getPackageAppearanceFacet());
	
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
		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
	  	BasicNodeGem basicGem = new BasicNodeGem(getRecreatorName(), diagram, figure, false);
	  	BasicNamespaceNodeGem componentGem = new BasicNamespaceNodeGem(FIGURE_NAME, figure.getSubject(), figure.getProperties());
			basicGem.connectBasicNodeAppearanceFacet(componentGem.getBasicNodeAppearanceFacet());
			basicGem.connectBasicNodeContainerFacet(componentGem.getBasicNodeContainerFacet());
			componentGem.connectBasicNodeFigureFacet(basicGem.getBasicNodeFigureFacet());
			componentGem.connectFeaturelessClassifierAppearanceFacet(new NodeAppearanceGem().getPackageAppearanceFacet());
			return basicGem.getBasicNodeFigureFacet();
		}

		public void initialiseExtraProperties(PersistentProperties properties)
		{
		}
	}
}
