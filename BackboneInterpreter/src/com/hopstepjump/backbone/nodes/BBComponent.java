package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.net.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.converters.BBXStreamConverters.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("Component")
public class BBComponent extends DEComponent implements INode, Serializable
{
  private transient DEObject parent;
  @XStreamAlias("name")
  @XStreamAsAttribute
  private String rawName;
  private String uuid = BBUidGenerator.newUuid(getClass());
  @XStreamAsAttribute
  private String kind;
  private Boolean retired;
  @XStreamAlias("abstract")
  private Boolean isAbstract;
  
  @XStreamAlias("substitutes")
  @XStreamConverter(ComponentReferencesConverter.class)
  private List<DEElement> substitutes;
  @XStreamConverter(ComponentReferencesConverter.class)
  private List<DEElement> resembles;
  
  // the stereotypes
  @XStreamAlias("appliedStereotypes")
  private List<DEAppliedStereotype> replacedAppliedStereotypes;
  
  // the attributes
  @XStreamConverter(StringReferencesConverter.class)
  private List<String> deletedAttributes;
  private List<BBReplacedAttribute> replacedAttributes;
  private List<BBAttribute> addedAttributes;
  
  // the ports
  @XStreamConverter(StringReferencesConverter.class)
  private List<String> deletedPorts;
  private List<BBReplacedPort> replacedPorts;
  private List<BBPort> addedPorts;
  
  // the parts
  @XStreamConverter(StringReferencesConverter.class)
  private List<String> deletedParts;
  private List<BBReplacedPart> replacedParts;
  private List<BBPart> addedParts;
  
  // the connectors
  @XStreamConverter(StringReferencesConverter.class)
  private List<String> deletedConnectors;
  private List<BBReplacedConnector> replacedConnectors;
  private List<BBConnector> addedConnectors;
  
  // the connectors
  @XStreamConverter(StringReferencesConverter.class)
  private List<String> deletedPortLinks;
  private List<BBReplacedConnector> replacedPortLinks;
  private List<BBConnector> addedPortLinks;
	
  // for going the other way
  private transient List<DEElement> substituters;
  private transient Set<DEElement> resemblers;
  
  // the deltas for the difference calculations
  private transient boolean initialiseDeltas;
  private transient Deltas attributes;
  private transient Deltas parts;
  private transient Deltas ports;
  private transient Deltas connectors;
  private transient Deltas portLinks;
  private transient Deltas appliedStereotypes;
  
  // other cached variables
  private transient Set<String> replacedUuids;
  
	public BBComponent() {}
	
  public BBComponent(String uuid)
  {
  	this.uuid = uuid;
  	this.rawName = uuid;
  	kind = ComponentKindEnum.NORMAL.name();
  	readResolve();
  }
  
  public List<DEAppliedStereotype> settable_getReplacedAppliedStereotypes()
	{
  	if (replacedAppliedStereotypes == null)
  		replacedAppliedStereotypes = new ArrayList<DEAppliedStereotype>();
  	return replacedAppliedStereotypes;
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
    informAboutParent(replacedAttributes);
    informAboutParent(addedAttributes);
    informAboutParent(replacedPorts);
    informAboutParent(addedPorts);
    informAboutParent(replacedParts);
    informAboutParent(addedParts);
    informAboutParent(replacedConnectors);
    informAboutParent(addedConnectors);
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
	    	if (node instanceof INode)
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
  
	public void setComponentKind(ComponentKindEnum componentKind)
	{
		this.kind = componentKind.name();		
	}
	
	public ComponentKindEnum getComponentKind()
	{
		return ComponentKindEnum.valueOf(kind);
	}
	
  ///////////////////////// the attributes ////////////////////////  
  public List<String> settable_getDeletedAttributes()
  {
    if (deletedAttributes == null)
      deletedAttributes = new ArrayList<String>();
    return deletedAttributes;
  }

  public List<BBReplacedAttribute> settable_getReplacedAttributes()
  {
    if (replacedAttributes == null)
      replacedAttributes = new ArrayList<BBReplacedAttribute>();
    return replacedAttributes;
  }

  public List<BBAttribute> settable_getAddedAttributes()
  {
    if (addedAttributes == null)
      addedAttributes = new ArrayList<BBAttribute>();
    return addedAttributes;
  }
  
  ///////////////////////// the ports ////////////////////////  
  public List<String> settable_getDeletedPorts()
  {
    if (deletedPorts == null)
      deletedPorts = new ArrayList<String>();
    return deletedPorts;
  }

  public List<BBReplacedPort> settable_getReplacedPorts()
  {
    if (replacedPorts == null)
      replacedPorts = new ArrayList<BBReplacedPort>();
    return replacedPorts;
  }

  public List<BBPort> settable_getAddedPorts()
  {
    if (addedPorts == null)
      addedPorts = new ArrayList<BBPort>();
    return addedPorts;
  }

  ///////////////////////// the parts ////////////////////////  
  public List<String> settable_getDeletedParts()
  {
    if (deletedParts == null)
      deletedParts = new ArrayList<String>();
    return deletedParts;
  }

  public List<BBReplacedPart> settable_getReplacedParts()
  {
    if (replacedParts == null)
      replacedParts = new ArrayList<BBReplacedPart>();
    return replacedParts;
  }

  public List<BBPart> settable_getAddedParts()
  {
    if (addedParts == null)
      addedParts = new ArrayList<BBPart>();
    return addedParts;
  }

  ///////////////////////// the connectors ////////////////////////  
  public List<String> settable_getDeletedConnectors()
  {
    if (deletedConnectors == null)
      deletedConnectors = new ArrayList<String>();
    return deletedConnectors;
  }

  public List<BBReplacedConnector> settable_getReplacedConnectors()
  {
    if (replacedConnectors == null)
      replacedConnectors = new ArrayList<BBReplacedConnector>();
    return replacedConnectors;
  }

  public List<BBConnector> settable_getAddedConnectors()
  {
    if (addedConnectors == null)
      addedConnectors = new ArrayList<BBConnector>();
    return addedConnectors;
  }

  ///////////////////////// the port links ////////////////////////  
  public List<String> settable_getDeletedPortLinks()
  {
    if (deletedPortLinks == null)
      deletedPortLinks = new ArrayList<String>();
    return deletedPortLinks;
  }

  public List<BBReplacedConnector> settable_getReplacedPortLinks()
  {
    if (replacedPortLinks == null)
      replacedPortLinks = new ArrayList<BBReplacedConnector>();
    return replacedPortLinks;
  }

  public List<BBConnector> settable_getAddedPortLinks()
  {
    if (addedPortLinks == null)
      addedPortLinks = new ArrayList<BBConnector>();
    return addedPortLinks;
  }

  //////////////////////////////////////////////////////
  
  public List<DEElement> settable_getSubstitutes()
  {
    if (substitutes == null)
      substitutes = new ArrayList<DEElement>();
    return substitutes;
  }

  public List<DEElement> settable_getRawResembles()
  {
    if (resembles == null)
      resembles = new ArrayList<DEElement>();
    return resembles;
  }
  
  ////////////////////// contract functions ///////////////////////
  
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
	public List<DEElement> getRawSubstitutes()
	{
    if (substitutes == null)
      return new ArrayList<DEElement>();
    return substitutes;
	}

	@Override
	public List<? extends DEElement> getRawResembles()
	{
    if (resembles == null)
      return new ArrayList<DEElement>();
    return resembles;
	}

  @Override
  public boolean canBeDecomposed(DEStratum perspective)
  {
    return
    	getComponentKind() != ComponentKindEnum.PRIMITIVE &&
    	!isLeaf(perspective) &&
    	!isPlaceholder(perspective);
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
      case DELTA_PART:
        return parts;
      case DELTA_ATTRIBUTE:
        return attributes;
      case DELTA_PORT:
        return ports;
      case DELTA_CONNECTOR:
        return connectors;
      case DELTA_PORT_LINK:
      	return portLinks;
    }
    
    return super.getDeltas(type);
  }  

  @Override
  public String getRawName()
  {
    return rawName;
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
    // handle attributes
    attributes = createDeltas(
        this,
        ConstituentTypeEnum.DELTA_ATTRIBUTE,
        addedAttributes,
        deletedAttributes,
        replacedAttributes);
    
    // handle parts
    parts = createDeltas(
        this,
        ConstituentTypeEnum.DELTA_PART,
        addedParts,
        deletedParts,
        replacedParts);
        
    // handle ports
    ports = createDeltas(
        this,
        ConstituentTypeEnum.DELTA_PORT,
        addedPorts,
        deletedPorts,
        replacedPorts);
    
    // handle connectors
    connectors = createDeltas(
        this,
        ConstituentTypeEnum.DELTA_CONNECTOR,
        addedConnectors,
        deletedConnectors,
        replacedConnectors);
    
    // handle port links
    portLinks = createDeltas(
        this,
        ConstituentTypeEnum.DELTA_PORT_LINK,
        addedPortLinks,
        deletedPortLinks,
        replacedPortLinks);
    
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

  /**
   * code to allocate a UUID for factories and placeholders
   */
	private static String hostName;
	static
	{
		try
		{
			hostName = java.net.InetAddress.getLocalHost().getHostName();
		}
		catch (UnknownHostException e)
		{
			hostName = ""; 
		}
	}
  private String makeNewUUID()
	{
		return hostName + "-" + UUID.randomUUID();
	}

	@Override
  public Set<String> getReplaceUuidsOnly()
  {
    if (replacedUuids == null)
    {
      replacedUuids = new LinkedHashSet<String>();
      addReplacedUuids(replacedUuids, replacedAttributes);
      addReplacedUuids(replacedUuids, replacedParts);
      addReplacedUuids(replacedUuids, replacedPorts);
      addReplacedUuids(replacedUuids, replacedConnectors);
      addReplacedUuids(replacedUuids, replacedPortLinks);
    }
    
    return replacedUuids;
  }
  
  //////////////////////////////////////////// static helper functions
  public static void addReplacedUuids(Set<String> replacedUuids, List<? extends BBReplacedConstituent> replaces)
  {
  	if (replaces != null)
	    for (BBReplacedConstituent replace : replaces)
	      replacedUuids.add(replace.getUuid());
  }

  /**
   * record the deltas, as a set of pairs for adds, a set of uuids for deletes and a set of pairs for replaces
   */
  public static Deltas createDeltas(
      DEElement deElement,
      ConstituentTypeEnum type,
      List<? extends DEConstituent> elements,
      List<String> deltaDeletedConstituents,
      List<? extends BBReplacedConstituent> deltaReplacedElements)
  {
    IDeltaEngine engine = GlobalDeltaEngine.engine;
    Set<DeltaPair> adds = new LinkedHashSet<DeltaPair>();
    Set<String> deletes = new LinkedHashSet<String>();
    Set<DeltaPair> replaces = new LinkedHashSet<DeltaPair>();

    if (elements != null)
	    for (DEConstituent element : elements)
	      adds.add(new DeltaPair(element.getUuid(), engine.locateObject(element).asConstituent()));

    if (deltaDeletedConstituents != null)
    	deletes.addAll(deltaDeletedConstituents);
    	
    if (deltaReplacedElements != null)
	    for (BBReplacedConstituent replace : deltaReplacedElements)
	    {
	      replaces.add(
	          new DeltaPair(
	              replace.getUuid(),
	              engine.locateObject(replace.getReplacement()).asConstituent()));
	    }
    
    return new Deltas(deElement, adds, deletes, replaces, type);
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

  public void setRawAbstract(boolean isAbstract)
  {
  	this.isAbstract = isAbstract ? true : null;
  }
  
	@Override
	public boolean isRawAbstract()
	{
		return isAbstract != null ? isAbstract: false;
	}
}
