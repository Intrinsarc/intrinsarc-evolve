package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

public class BBRequirementsFeature extends DEInterface implements INode, Serializable
{
  private transient DEObject parent;
  @XStreamAlias("name")
  @XStreamAsAttribute
  private String rawName;
  private String uuid = BBUidGenerator.newUuid(getClass());  
  private List<DEElement> substitutes;
  private List<DEElement> resembles;
  private Boolean retired;
  
  // the stereotypes
  @XStreamAlias("appliedStereotypes")
  private List<DEAppliedStereotype> replacedAppliedStereotypes;
  
  // the constituents  
  private List<String> deletedSubfeatures;
  private List<BBReplacedRequirementsFeatureLink> replacedSubfeatures;
  private List<DERequirementsFeatureLink> addedSubfeatures;

	// for going the other way
  private transient List<DEElement> substituters;
  private transient Set<DEElement> resemblers;
  
  // the deltas for the difference calculations
  private transient boolean initialiseDeltas;
  private transient Deltas subfeatures;
  private transient Deltas appliedStereotypes;
  
  // other cached variables
  private transient Set<String> replacedUuids;
  
  public BBRequirementsFeature() {}
  
  public BBRequirementsFeature(String uuid)
  {
  	this.uuid = uuid;
  	this.rawName = uuid;
  	readResolve();
  }

  protected Object readResolve()
  {
  	super.readResolve();
  	GlobalNodeRegistry.registry.addNode(this);
  	
    substituters = new ArrayList<DEElement>();
    resemblers = new HashSet<DEElement>();

  	return this;
  }
  
  public void setParent(DEObject parent)
  {
    this.parent = parent;
    
    // tell all constituents
    informAboutParent(replacedSubfeatures);
    informAboutParent(addedSubfeatures);
    informAboutParent(replacedAppliedStereotypes);
    
  	// tell anything we resemble or substitute about ourselves
  	if (resembles != null)
  		for (DEElement r : resembles)
  			r.getPossibleImmediateSubElements().add(this);
  	if (substitutes != null)
  		for (DEElement r : substitutes)
  			r.getSubstituters().add(this);
  }

  private void informAboutParent(List<? extends BBReplacedConstituent> constituents)
	{
  	if (constituents != null)
  	{
	  	Set<DEObject> objects = new HashSet<DEObject>();    
	    for (BBReplacedConstituent replaced : constituents)
	    	objects.add(replaced.getReplacement());
	    informAboutParent(objects);
  	}  	
	}
  
	private void informAboutParent(Collection<? extends DEObject> nodes)
  {
		if (nodes != null)
		{
	    for (DEObject node : nodes)
	    	if (nodes  instanceof INode)
		  		((INode) node).setParent(this);
		}
  }

  public void setUuid(String uuid)
  {
    this.uuid = uuid;
  }

  public void setRawName(String rawName)
  {
    this.rawName = rawName;
  }
  
  public List<String> settable_getDeletedSubfeatures()
  {
    if (deletedSubfeatures == null)
      deletedSubfeatures = new ArrayList<String>();
    return deletedSubfeatures;
  }

  public List<BBReplacedRequirementsFeatureLink> settable_getReplacedSubfeatures()
  {
    if (replacedSubfeatures == null)
      replacedSubfeatures = new ArrayList<BBReplacedRequirementsFeatureLink>();
    return replacedSubfeatures;
  }

  public List<DERequirementsFeatureLink> settable_getAddedSubfeatures()
  {
    if (addedSubfeatures == null)
      addedSubfeatures = new ArrayList<DERequirementsFeatureLink>();
    return addedSubfeatures;
  }

  public List<DEElement> settable_getRawResembles()
  {
    if (resembles == null)
      resembles = new ArrayList<DEElement>();
    return resembles;
  }
  
  public List<DEElement> settable_getRawSubstitutes()
  {
    if (substitutes == null)
      substitutes = new ArrayList<DEElement>();
    return substitutes;
  }

  ///////////////////////////////// contract functions ///////////////////////


	@Override
	public Set<DEElement> getPossibleImmediateSubElements()
	{
		return resemblers;
	}

	@Override
	public List<DEElement> getSubstituters()
	{
		return substituters;
	}
	
	@Override
	public List<? extends DEElement> getRawResembles()
	{
    if (resembles == null)
      return new ArrayList<DEElement>();
    return resembles;
	}
	
  @Override
  public String getRawName()
  {
    return rawName;
  }
  
  @Override
  public IDeltas getDeltas(ConstituentTypeEnum type)
  {
    if (!initialiseDeltas)
    {
      initialiseDeltas();
      initialiseDeltas = true;
    }
    
    switch (type)
    {
	  	case DELTA_APPLIED_STEREOTYPE:
	  		return appliedStereotypes;
      case DELTA_REQUIREMENT_FEATURE_LINK:
        return subfeatures;
    }
    
    return super.getDeltas(type);
  } 

  @Override
  public List<DEElement> getRawSubstitutes()
  {
    List<DEElement> raw = new ArrayList<DEElement>();
    if (substitutes != null)
      raw.addAll(substitutes);
    return raw;
  }

  @Override
  public DEElement asElement()
  {
    return this;
  }

  @Override
  public DEObject getParent()
  {
    return parent;
  }

  @Override
  public Object getRepositoryObject()
  {
    return this;
  }

  @Override
  public String getUuid()
  {
    return uuid;
  }
	
	///////////////////////////////////////////////////
	///////////////// complex delta handling //////////
	
  private void initialiseDeltas()
  {
    // handle operations
    subfeatures = BBComponent.createDeltas(
        this,
        ConstituentTypeEnum.DELTA_REQUIREMENT_FEATURE_LINK,
        addedSubfeatures,
        deletedSubfeatures,
        replacedSubfeatures);
    
    // handle stereotypes
    Set<DeltaPair> pairs = new HashSet<DeltaPair>();
    if (replacedAppliedStereotypes != null)
	    for (DEAppliedStereotype app : replacedAppliedStereotypes)
	    	pairs.add(new DeltaPair(app.getUuid(), app));
    appliedStereotypes = new Deltas(
    		this,
    		new HashSet<DeltaPair>(),
    		new HashSet<String>(),
    		pairs,
    		ConstituentTypeEnum.DELTA_APPLIED_STEREOTYPE);
  }

  @Override
  public Set<String> getReplaceUuidsOnly()
  {
    if (replacedUuids == null)
    {
      replacedUuids = new LinkedHashSet<String>();
      BBComponent.addReplacedUuids(replacedUuids, replacedSubfeatures);
    }
    
    return replacedUuids;
  }

  public List<DEAppliedStereotype> settable_getReplacedAppliedStereotypes()
	{
  	if (replacedAppliedStereotypes == null)
  		replacedAppliedStereotypes = new ArrayList<DEAppliedStereotype>();
  	return replacedAppliedStereotypes;
	}

  public void setRawRetired(boolean retired)
  {
  	this.retired = retired ? true : null;
  }
  
	@Override
	public boolean isRawRetired()
	{
		return retired != null ? retired : false;
	}

	@Override
	public boolean isRawAbstract()
	{
		// features are never abstract
		return false;
	}
}
