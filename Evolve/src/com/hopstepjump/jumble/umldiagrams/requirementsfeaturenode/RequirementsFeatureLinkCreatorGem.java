package com.hopstepjump.jumble.umldiagrams.requirementsfeaturenode;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.hopstepjump.idraw.arcfacilities.arcsupport.*;
import com.hopstepjump.idraw.arcfacilities.creationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

public class RequirementsFeatureLinkCreatorGem
{
  public static final String NAME = "feature link";
  private ArcCreateFacet arcCreateFacet = new ArcCreateFacetImpl();
  private RequirementsLinkKind kind;

  public RequirementsFeatureLinkCreatorGem(RequirementsLinkKind kind)
  {
  	this.kind = kind;
  }
  
  public ArcCreateFacet getArcCreateFacet()
  {
    return arcCreateFacet;
  }
  
  private class ArcCreateFacetImpl implements ArcCreateFacet
  {
    public String getFigureName()
    {
      return NAME;
    }
  
    public void create(Object subject, DiagramFacet diagram, String figureId, ReferenceCalculatedArcPoints referencePoints, PersistentProperties properties)
    {
      // instantiate to use conventional facets
      BasicArcGem gem = new BasicArcGem(this, diagram, figureId, new CalculatedArcPoints(referencePoints));
      RequirementsFeatureLinkGem providedGem = new RequirementsFeatureLinkGem((RequirementsFeatureLink) subject);
      providedGem.connectFigureFacet(gem.getFigureFacet());
      gem.connectBasicArcAppearanceFacet(providedGem.getBasicArcAppearanceFacet());
      diagram.add(gem.getFigureFacet());
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
      RequirementsFeatureLinkGem requiredGem = new RequirementsFeatureLinkGem((RequirementsFeatureLink) figure.getSubject());
      requiredGem.connectFigureFacet(gem.getFigureFacet());
      gem.connectBasicArcAppearanceFacet(requiredGem.getBasicArcAppearanceFacet());

      return gem.getFigureFacet();
    }

    public void initialiseExtraProperties(PersistentProperties properties)
    {
      properties.add(new PersistentProperty("kind", kind.getValue(), 0));
    }
    
    public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
    {
    	// start must be a port, end must be an interface
      boolean startReadOnly = start.getFigureFacet().isSubjectReadOnlyInDiagramContext(false);
    	Element elem = (Element) start.getFigureFacet().getSubject();
    	boolean startOk = elem instanceof RequirementsFeature && !startReadOnly;
    	if (end == null)
    		return startOk;
    	return startOk && end.getFigureFacet().getSubject() instanceof RequirementsFeature;
    }
    
    public Object createNewSubject(DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties)
    {
    	// add to the list of provided interfaces, if it isn't there already
    	CalculatedArcPoints points = new CalculatedArcPoints(calculatedPoints);
    	RequirementsFeature main = (RequirementsFeature) points.getNode1().getFigureFacet().getSubject();
    	RequirementsFeature dependOn = (RequirementsFeature) points.getNode2().getFigureFacet().getSubject();

    	RequirementsFeatureLink prop = main.createSubfeatures();
    	prop.setKind(kind);
    	prop.setType(dependOn);
    	
    	return prop;
    }

		public void aboutToMakeTransaction(ToolCoordinatorFacet coordinator)
		{
		}
  }
}
