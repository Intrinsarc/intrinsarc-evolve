package com.intrinsarc.evolve.umldiagrams.messagearc;

import com.intrinsarc.evolve.umldiagrams.connectorarc.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.arcfacilities.arcsupport.*;
import com.intrinsarc.idraw.arcfacilities.creationbase.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;

/**
 * @author andrew
 */
public class MessageCreatorGem implements Gem
{
  public static final String NAME = "Message";
	private ArcCreateFacet arcCreateFacet = new ArcCreateFacetImpl();
  private int type;
	
  public MessageCreatorGem()
  {
    this.type = MessageArcAppearanceGem.CALL_TYPE;
  }
  
	public MessageCreatorGem(int type)
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
	    return getActualFigureName(type);
	  }
	
	  public void create(Object subject, DiagramFacet diagram, String figureId, ReferenceCalculatedArcPoints referencePoints, PersistentProperties properties)
	  {
	  	// instantiate to use conventional facets
	  	BasicArcGem gem = new BasicArcGem(this, diagram, figureId, new CalculatedArcPoints(referencePoints));
	  	OldConnectorArcAppearanceGem connectorGem = new OldConnectorArcAppearanceGem(properties); 
	    gem.connectBasicArcAppearanceFacet(connectorGem.getBasicArcAppearanceFacet());
	    gem.connectContainerFacet(connectorGem.getContainerFacet());
	    gem.connectAdvancedArcFacet(connectorGem.getAdvancedArcFacet());
	    connectorGem.connectFigureFacet(gem.getFigureFacet(), properties);
      
      properties.addIfNotThere(new PersistentProperty("type", type, MessageArcAppearanceGem.CALL_TYPE));
      MessageArcAppearanceGem delegatingAppearanceGem = new MessageArcAppearanceGem(
      		new PersistentFigure(figureId, null, subject, properties));
      connectorGem.connectDelegatingBasicArcAppearanceFacet(
          delegatingAppearanceGem.getBasicArcAppearanceFacet());	    																					 
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
	  	OldConnectorArcAppearanceGem connectorGem = new OldConnectorArcAppearanceGem(figure.getProperties()); 
	    gem.connectBasicArcAppearanceFacet(connectorGem.getBasicArcAppearanceFacet());
	    gem.connectContainerFacet(connectorGem.getContainerFacet());
	    gem.connectAdvancedArcFacet(connectorGem.getAdvancedArcFacet());
      connectorGem.connectFigureFacet(gem.getFigureFacet(), figure.getProperties());

      MessageArcAppearanceGem delegatingAppearanceGem = new MessageArcAppearanceGem(figure);
      connectorGem.connectDelegatingBasicArcAppearanceFacet(
          delegatingAppearanceGem.getBasicArcAppearanceFacet());
	  	
	  	return gem.getFigureFacet();
		}

    public void initialiseExtraProperties(PersistentProperties properties)
    {
      properties.add(
          new PersistentProperty("type", type, MessageArcAppearanceGem.CALL_TYPE));
    }
    
    public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end)
    {
      return true;
    }
    
    public Object createNewSubject(DiagramFacet diagram, ReferenceCalculatedArcPoints calculatedPoints, PersistentProperties properties)
    {
    	return null;
    }

		public void aboutToMakeTransaction(ToolCoordinatorFacet coordinator)
		{
		}
	}
  

  public static String getActualFigureName(int type)
  {
    switch (type)
    {
      case MessageArcAppearanceGem.CALL_TYPE:
        return "synchronous message";
      case MessageArcAppearanceGem.RETURN_TYPE:
        return "return message";
      default:
        return "asynchronous message";
    }
  }
}
