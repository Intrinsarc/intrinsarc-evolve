package com.hopstepjump.jumble.umldiagrams.associationarc;

import org.eclipse.uml2.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.arcfacilities.creationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.jumble.umldiagrams.connectorarc.*;
import com.hopstepjump.jumble.umldiagrams.dependencyarc.*;

/**
 * @author andrew
 */
public class AssociationCreatorGem implements Gem
{
  public static final String NAME = "Association";
	private ArcCreateFacet arcCreateFacet = new ArcCreateFacetImpl();
  private int type;
  private boolean unidirectional;

	
  public AssociationCreatorGem()
  {
    this.type = AssociationArcAppearanceGem.ASSOCIATION_TYPE;
  }
  
  public AssociationCreatorGem(boolean unidirectional)
  {
    this.type = AssociationArcAppearanceGem.ASSOCIATION_TYPE;
    this.unidirectional = unidirectional;
  }
  
	public AssociationCreatorGem(int type)
	{
    this.type = type;
	}
	
	public ArcCreateFacet getArcCreateFacet()
	{
		return arcCreateFacet;
	}
	
	private class ArcCreateFacetImpl implements ArcCreateFacet
	{
	  public String getFigureName()
	  {
	    return "association";
	  }
	
	  public Object create(Object subject, DiagramFacet diagram, String figureId, ReferenceCalculatedArcPoints referencePoints, PersistentProperties properties)
	  {
	  	// instantiate to use conventional facets
	  	BasicArcGem gem = new BasicArcGem(this, diagram, figureId, new CalculatedArcPoints(referencePoints));
	  	OldConnectorArcAppearanceGem connectorGem = new OldConnectorArcAppearanceGem(properties); 
	    gem.connectBasicArcAppearanceFacet(connectorGem.getBasicArcAppearanceFacet());
	    gem.connectContainerFacet(connectorGem.getContainerFacet());
	    gem.connectAdvancedArcFacet(connectorGem.getAdvancedArcFacet());
	    connectorGem.connectFigureFacet(gem.getFigureFacet(), properties);
      
      properties.addIfNotThere(new PersistentProperty("type", type, AssociationArcAppearanceGem.ASSOCIATION_TYPE));
      properties.addIfNotThere(new PersistentProperty("uni", unidirectional, false));
      AssociationArcAppearanceGem delegatingAppearanceGem = new AssociationArcAppearanceGem(properties);
      delegatingAppearanceGem.connectFigureFacet(gem.getFigureFacet());
      connectorGem.connectDelegatingBasicArcAppearanceFacet(
          delegatingAppearanceGem.getBasicArcAppearanceFacet());
	    																					 
	    diagram.add(gem.getFigureFacet());
	    return new FigureReference(diagram, figureId);
	  }
	
	  public void unCreate(Object memento)
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
			return NAME;
		}
	
		/**
		 * @see com.hopstepjump.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
		 */
		public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
		{
	  	// instantiate to use conventional facets
	  	BasicArcGem gem = new BasicArcGem(this, diagram, figure);
	  	OldConnectorArcAppearanceGem connectorGem = new OldConnectorArcAppearanceGem(figure.getProperties()); 
	    gem.connectBasicArcAppearanceFacet(connectorGem.getBasicArcAppearanceFacet());
	    gem.connectContainerFacet(connectorGem.getContainerFacet());
	    gem.connectAdvancedArcFacet(connectorGem.getAdvancedArcFacet());
      connectorGem.connectFigureFacet(gem.getFigureFacet(), figure.getProperties());

      AssociationArcAppearanceGem delegatingAppearanceGem = new AssociationArcAppearanceGem(figure.getProperties());
      delegatingAppearanceGem.connectFigureFacet(gem.getFigureFacet());
      connectorGem.connectDelegatingBasicArcAppearanceFacet(
          delegatingAppearanceGem.getBasicArcAppearanceFacet());
	  	
	  	return gem.getFigureFacet();
		}

    public void initialiseExtraProperties(PersistentProperties properties)
    {
      properties.add(new PersistentProperty("type", type, AssociationArcAppearanceGem.ASSOCIATION_TYPE));
      properties.add(new PersistentProperty("uni", unidirectional, false));
    }

    public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
    {
      return acceptsOneOrBothAnchors(start, end);
    }
    
    public Object createNewSubject(Object previouslyCreated, DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties)
    {
    	return null;
    }

    public void uncreateNewSubject(Object previouslyCreated)
    {
    }

		public void aboutToMakeCommand(ToolCoordinatorFacet coordinator)
		{
		}

		public Object extractRawSubject(Object previouslyCreated)
		{
			return previouslyCreated;
		}
	}
  

  public static String getFigureName(int type)
  {
    switch (type)
    {
      case AssociationArcAppearanceGem.AGGREGATION_TYPE:
        return "aggregation link";
      case AssociationArcAppearanceGem.COMPOSITION_TYPE:
        return "composition link";
      default:
        return "association link";
    }    
  }

  public static boolean acceptsOneOrBothAnchors(AnchorFacet start, AnchorFacet end)
  {
    // end must be something sensible, but can be circular
    boolean startReadOnly = start.getFigureFacet().isSubjectReadOnlyInDiagramContext(false);
    boolean startOk = DependencyCreatorGem.extractDependentClient(start.getFigureFacet().getSubject()) != null && !startReadOnly;
    if (end == null)
      return startOk;
    return
      startOk &&
      end.getFigureFacet().getSubject() instanceof NamedElement;
  }
}
