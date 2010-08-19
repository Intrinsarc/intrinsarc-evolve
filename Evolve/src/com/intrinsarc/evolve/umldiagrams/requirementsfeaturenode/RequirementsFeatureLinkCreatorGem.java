package com.intrinsarc.evolve.umldiagrams.requirementsfeaturenode;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.arcfacilities.arcsupport.*;
import com.intrinsarc.idraw.arcfacilities.creationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;

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
      RequirementsFeatureLinkGem req = new RequirementsFeatureLinkGem((RequirementsFeatureLink) subject);
      req.connectFigureFacet(gem.getFigureFacet());
      gem.connectBasicArcAppearanceFacet(req.getBasicArcAppearanceFacet());
	    gem.connectClipboardActionsFacet(req.getClipboardCommandsFacet());
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
      RequirementsFeatureLinkGem req = new RequirementsFeatureLinkGem((RequirementsFeatureLink) figure.getSubject());
      req.connectFigureFacet(gem.getFigureFacet());
	    gem.connectClipboardActionsFacet(req.getClipboardCommandsFacet());
      gem.connectBasicArcAppearanceFacet(req.getBasicArcAppearanceFacet());

      return gem.getFigureFacet();
    }

    public void initialiseExtraProperties(PersistentProperties properties)
    {
      properties.add(new PersistentProperty("kind", kind.getValue(), 0));
    }
    
    public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
    {
    	return acceptsOneOrBothAnchors(start, end);
    }
    
    public Object createNewSubject(DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties)
    {
    	// add to the list of provided interfaces, if it isn't there already
    	CalculatedArcPoints points = new CalculatedArcPoints(calculatedPoints);
    	RequirementsFeature main = (RequirementsFeature) points.getNode1().getFigureFacet().getSubject();
    	RequirementsFeature dependOn = (RequirementsFeature) points.getNode2().getFigureFacet().getSubject();

    	RequirementsFeatureLink prop = main.createSubfeatures();
    	prop.setKind(kind);
    	prop.setType(getRealTarget(dependOn));
    	
    	return prop;
    }

		public void aboutToMakeTransaction(ToolCoordinatorFacet coordinator)
		{
		}
  }
  
  public static boolean acceptsOneOrBothAnchors(AnchorFacet start, AnchorFacet end)
  {
  	// start must be a port, end must be an interface
    boolean startReadOnly = start.getFigureFacet().isSubjectReadOnlyInDiagramContext(false);
  	Element elem = (Element) start.getFigureFacet().getSubject();
  	boolean startOk = elem instanceof RequirementsFeature && !startReadOnly;
  	if (end == null)
  		return startOk;
  	return startOk && end.getFigureFacet().getSubject() instanceof RequirementsFeature;
  }
  
  public static RequirementsFeature getRealTarget(NamedElement element)
  {
    // change the owner, but make sure we take the original
    DEElement elem = GlobalDeltaEngine.engine.locateObject(element).asElement().getReplacesOrSelf().iterator().next();
    return (RequirementsFeature) elem.getRepositoryObject();  	
  }
}
