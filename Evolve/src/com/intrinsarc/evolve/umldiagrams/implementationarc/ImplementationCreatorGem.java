package com.intrinsarc.evolve.umldiagrams.implementationarc;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.arcfacilities.arcsupport.*;
import com.intrinsarc.idraw.arcfacilities.creationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.repositorybase.*;

/**
 * @author Andrew
 */
public class ImplementationCreatorGem implements Gem
{
  public static final String NAME = "implementation";
  private ArcCreateFacet arcCreateFacet = new ArcCreateFacetImpl();

  public ImplementationCreatorGem()
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
      return ImplementationArcGem.FIGURE_NAME;
    }
  
    public void create(Object subject, DiagramFacet diagram, String figureId, ReferenceCalculatedArcPoints referencePoints, PersistentProperties properties)
    {
      // instantiate to use conventional facets
      BasicArcGem gem = new BasicArcGem(this, diagram, figureId, new CalculatedArcPoints(referencePoints));
      ImplementationArcGem providedGem = new ImplementationArcGem((Implementation) subject);
      providedGem.connectFigureFacet(gem.getFigureFacet());
      gem.connectBasicArcAppearanceFacet(providedGem.getBasicArcAppearanceFacet());
      diagram.add(gem.getFigureFacet());
    }
  
    /**
     * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#getFullName()
     */
    public String getRecreatorName()
    {
      return NAME;
    }

    /**
     * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorFacet#persistence_createFromProperties(FigureReference, PersistentProperties)
     */
    public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
    {
      // instantiate to use conventional facets
      BasicArcGem gem = new BasicArcGem(this, diagram, figure);
      ImplementationArcGem requiredGem = new ImplementationArcGem((Implementation) figure.getSubject());
      requiredGem.connectFigureFacet(gem.getFigureFacet());
      gem.connectBasicArcAppearanceFacet(requiredGem.getBasicArcAppearanceFacet());

      return gem.getFigureFacet();
    }

    public void initialiseExtraProperties(PersistentProperties properties)
    {
    }
    
    public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
    {
    	// start must be a port, end must be an interface
      boolean startReadOnly = start.getFigureFacet().isSubjectReadOnlyInDiagramContext(false);
    	Element elem = (Element) start.getFigureFacet().getSubject();
    	boolean startOk = extractClassType(elem) != null && !startReadOnly;
    	if (end == null)
    		return startOk;
    	return startOk && end.getFigureFacet().getSubject() instanceof Interface;
    }
    
    public Object createNewSubject(DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties)
    {
      SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

    	// add to the list of provided interfaces, if it isn't there already
    	CalculatedArcPoints points = new CalculatedArcPoints(calculatedPoints);
    	Class type = extractClassType(points.getNode1().getFigureFacet().getSubject());
    	Interface iface = (Interface) points.getNode2().getFigureFacet().getSubject();

    	// make an implementation and store it in the type
    	Implementation implementation = type.createImplementation();
    	implementation.setRealizingClassifier(type);
    	implementation.setContract(iface);
    	
    	return implementation;
    }

		public void aboutToMakeTransaction(ToolCoordinatorFacet coordinator)
		{
		}
  }
  
  public static Class extractClassType(Object subject)
	{
  	// if this is an instance of a class, then use this directly
  	if (subject instanceof Class)
  		return (Class) subject;
  	// if this is a typed element, and the type is a class, use this
  	if (subject instanceof TypedElement)
  	{
  		TypedElement typed = (TypedElement) subject;
  		Type type = typed.getType();
  		if (type instanceof Class)
  		{
  			return (Class) type; 
  		}
  	}
  	
  	// can't attach
  	return null;
	}
}
