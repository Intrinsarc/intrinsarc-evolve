package com.hopstepjump.jumble.umldiagrams.featurenode;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class AddFeatureCommand extends AbstractCommand
{
	private FigureReference addable;
	private FigureReference featureReference;
	private String factoryName;
	private Object memento;
	private PersistentProperties properties;
  private Object useSubject;
	private Object relatedSubject;
	private UPoint location;

	public AddFeatureCommand(FigureReference addable, FigureReference featureReference, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject, String executeDescription, String unExecuteDescription, UPoint location)
	{
		super(executeDescription, unExecuteDescription);
		this.addable = addable;
		this.featureReference = featureReference;
		this.factoryName = factory.getRecreatorName();
		this.properties = properties;
		this.useSubject = useSubject;
		this.relatedSubject = relatedSubject;
		this.location = location;
	}

	public void execute(boolean isTop)
	{
		memento = getAddable().addFeature(
		    memento,
		    featureReference,
		    retrieveFactory(),
		    properties,
		    useSubject,
		    relatedSubject,
		    location);
	}

	public void unExecute()
	{
		getAddable().unAddFeature(memento);
	}
  
	private NodeCreateFacet retrieveFactory()
	{
		return (NodeCreateFacet) PersistentFigureRecreatorRegistry.registry.retrieveRecreator(factoryName);
	}

	private FeatureAddFacet getAddable()
	{
		return (FeatureAddFacet) GlobalDiagramRegistry.registry.retrieveFigure(addable).getDynamicFacet(FeatureAddFacet.class);
	}
}
