package com.hopstepjump.jumble.umldiagrams.inheritancearc;

import org.eclipse.uml2.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.arcfacilities.creationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.repositorybase.*;

public final class InheritanceCreatorGem implements Gem
{
	public static final String NAME = "Inheritance";
	private ArcCreateFacet arcCreateFacet = new ArcCreateFacetImpl();

  public InheritanceCreatorGem()
  {
  }

	public ArcCreateFacet getArcCreateFacet()
	{
		return arcCreateFacet;
	}
	
	private class ArcCreateFacetImpl implements ArcCreateFacet
	{
	  public String getFigureName()
	  {
	    return InheritanceArcAppearanceFacetImpl.FIGURE_NAME;
	  }
	
	  public Object create(Object subject, DiagramFacet diagram, String figureId, ReferenceCalculatedArcPoints referencePoints, PersistentProperties properties)
	  {
	  	// instantiate to use conventional facets
	  	BasicArcGem gem = new BasicArcGem(this, diagram, figureId, new CalculatedArcPoints(referencePoints));
	    gem.connectBasicArcAppearanceFacet(new InheritanceArcAppearanceFacetImpl(gem.getFigureFacet(), (Generalization) subject));
	    														
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
	    gem.connectBasicArcAppearanceFacet(new InheritanceArcAppearanceFacetImpl(gem.getFigureFacet(), (Generalization) figure.getSubject()));
	    return gem.getFigureFacet();
		}

    public void initialiseExtraProperties(PersistentProperties properties)
    {
    }

    public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
    {
    	// if we just have the start, then the end hasn't been set yet
      Object startS = start.getFigureFacet().getSubject();
      Object endS = end == null ? null : end.getFigureFacet().getSubject();
      if (end == null)
        return startS instanceof Classifier;
      
      // the start and end cannot have the same subject
      if (startS == endS)
    	  return false;

      // anything to anything else classifier is ok
      return startS instanceof Classifier && startS.getClass() == endS.getClass();
    }
    
    public Object createNewSubject(Object previouslyCreated, DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

      // possibly resurrect
      if (previouslyCreated != null)
      {
        repository.decrementPersistentDelete((Element) previouslyCreated);
        return previouslyCreated;
      }

      // resolve to figures
      CalculatedArcPoints points = new CalculatedArcPoints(calculatedPoints);
      AnchorFacet anchor1 = points.getNode1();
      AnchorFacet anchor2 = points.getNode2();
      
      Classifier subject1 = (Classifier) anchor1.getFigureFacet().getSubject();
      Classifier subject2 = (Classifier) anchor2.getFigureFacet().getSubject();
      
      // subject1 should own the generalisation arc, and it should point to subject2
      Generalization gen = UML2Factory.eINSTANCE.createGeneralization();
      // containment is always the specific side of the generalisation
      subject1.getGeneralizations().add(gen);
      gen.setGeneral(subject2);
      
      return gen;
    }

    public void uncreateNewSubject(Object previouslyCreated)
    {
      GlobalSubjectRepository.repository.incrementPersistentDelete((Element) previouslyCreated);
    }

		public void aboutToMakeCommand(ToolCoordinatorFacet coordinator)
		{
		}
		
		public Object extractRawSubject(Object previouslyCreated)
		{
			return previouslyCreated;
		}
	} 
}