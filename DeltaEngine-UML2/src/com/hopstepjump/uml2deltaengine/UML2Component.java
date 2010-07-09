package com.hopstepjump.uml2deltaengine;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.repositorybase.*;

public class UML2Component extends DEComponent
{
  private org.eclipse.uml2.Class subject;
  private Set<String> replacedUuids;

  /** the deltas for the difference calculations */
  private boolean initialiseDeltas;
  private Deltas attributes;
  private Deltas operations;
  private Deltas parts;
  private Deltas ports;
  private Deltas connectors;
  private Deltas portLinks;
  private Deltas traces;
  private Deltas appliedStereotypes;

  public UML2Component(org.eclipse.uml2.Class subject)
  {
    super();
    this.subject = subject;
  }
  
  public static List<DEAppliedStereotype> extractStereotypes(Element element)
	{
  	List<DEAppliedStereotype> applied = new ArrayList<DEAppliedStereotype>();
  	if (element.isThisDeleted())
  		return applied;
  	for (Object obj : element.undeleted_getAppliedBasicStereotypes())
  		applied.add(new UML2AppliedStereotype((Stereotype) obj, element));
  	return applied;
	}
  
  @Override
  public List<DEElement> getRawSubstitutes()
  {
    // filter owned dependencies
  	List<DEElement> substitutes = new ArrayList<DEElement>();
    for (Object obj : subject.undeleted_getOwnedAnonymousDependencies())
    {
      Dependency dep = (Dependency) obj;
      if (dep.isReplacement())
        substitutes.add((DEElement) getEngine().locateObject(dep.getDependencyTarget()));
    }
    return substitutes;
  }

  @Override
  public List<DEElement> getRawResembles()
  {
    // filter owned dependencies
    List<DEElement> resemblances = new ArrayList<DEElement>();
    for (Object obj : subject.undeleted_getOwnedAnonymousDependencies())
    {
      Dependency dep = (Dependency) obj;
      if (dep.isResemblance())
      {
        DEElement elem = getEngine().locateObject(dep.getDependencyTarget()).asElement();
        if (elem != null && elem.asComponent() != null)
          resemblances.add(elem);
      }
    }
    return resemblances;
  }
  
  @Override
  public String getRawName()
  {
    return subject.getName();
  }

  @Override
  public DEObject getParent()
  {
    // look up to see if we can find a stratum parent
    Element parent = subject.getOwner();
    while (parent != null && UML2DeltaEngine.isRawPackage(parent))
    	parent = parent.getOwner();
    if (parent == null)
      return null;
    
    return getEngine().locateObject(parent);
  }

  @Override
  public Object getRepositoryObject()
  {
    return subject;
  }

  public String getUuid()
  {
    return subject.getUuid();
  }

  @Override
  public DEElement asElement()
  {
    return this;
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
      case DELTA_OPERATION:
        return operations;
      case DELTA_PORT:
        return ports;
      case DELTA_CONNECTOR:
        return connectors;
      case DELTA_PORT_LINK:
        return portLinks;
      case DELTA_TRACE:
        return traces;
    }
    
    return super.getDeltas(type);
  }  

  @SuppressWarnings("unchecked")
  private void initialiseDeltas()
  {
  	// handle attributes
    attributes = createDeltas(
        this,
        ConstituentTypeEnum.DELTA_ATTRIBUTE,
        subject.undeleted_getOwnedAttributes(),
        subject.undeleted_getDeltaDeletedAttributes(),
        subject.undeleted_getDeltaReplacedAttributes(),
        new DeltaElementAcceptor()
        {
          public boolean accept(Element element)
          {
            return UMLTypes.extractInstanceOfPart(element) == null;
          }
        });
    // handle parts
    parts = createDeltas(
        this,
        ConstituentTypeEnum.DELTA_PART,
        subject.undeleted_getOwnedAttributes(),
        subject.undeleted_getDeltaDeletedAttributes(),
        subject.undeleted_getDeltaReplacedAttributes(),
        new DeltaElementAcceptor()
        {
          public boolean accept(Element element)
          {
            return UMLTypes.extractInstanceOfPart(element) != null;
          }
        });
    // handle operations
    operations = createDeltas(
        this,
        ConstituentTypeEnum.DELTA_OPERATION,
        subject.undeleted_getOwnedOperations(),
        subject.undeleted_getDeltaDeletedOperations(),
        subject.undeleted_getDeltaReplacedOperations(),
        null);
    // handle ports
    ports = createDeltas(
        this,
        ConstituentTypeEnum.DELTA_PORT,
        subject.undeleted_getOwnedPorts(),
        subject.undeleted_getDeltaDeletedPorts(),
        subject.undeleted_getDeltaReplacedPorts(),
        null);
    // handle connectors
    connectors = createDeltas(
        this,
        ConstituentTypeEnum.DELTA_CONNECTOR,
        subject.undeleted_getOwnedConnectors(),
        subject.undeleted_getDeltaDeletedConnectors(),
        subject.undeleted_getDeltaReplacedConnectors(),
        new DeltaElementAcceptor()
        {
          public boolean accept(Element element)
          {
            return !((Connector) element).getKind().equals(ConnectorKind.PORT_LINK_LITERAL);
          }
        });
    // handle port links
    portLinks = createDeltas(
        this,
        ConstituentTypeEnum.DELTA_PORT_LINK,
        subject.undeleted_getOwnedConnectors(),
        subject.undeleted_getDeltaDeletedConnectors(),
        subject.undeleted_getDeltaReplacedConnectors(),
        new DeltaElementAcceptor()
        {
          public boolean accept(Element element)
          {
            return ((Connector) element).getKind().equals(ConnectorKind.PORT_LINK_LITERAL);
          }
        });
    
    // handle traces
    traces = createDeltas(
        this,
        ConstituentTypeEnum.DELTA_TRACE,
        subject.undeleted_getOwnedAnonymousDependencies(),
        subject.undeleted_getDeltaDeletedTraces(),
        subject.undeleted_getDeltaReplacedTraces(),
        new DeltaElementAcceptor()
        {
          public boolean accept(Element element)
          {
            return ((Dependency) element).isTrace();
          }
        });
    
    // handle stereotypes
  	Set<DeltaPair> stereos = new LinkedHashSet<DeltaPair>();
  	for (DEAppliedStereotype appl : getReplacedAppliedStereotypes())
  		stereos.add(new DeltaPair(appl.getUuid(), appl));
  	appliedStereotypes = new Deltas(
    		this,
    		new HashSet<DeltaPair>(),
    		new HashSet<String>(),
    		stereos,
    		ConstituentTypeEnum.DELTA_APPLIED_STEREOTYPE);
  }

	@SuppressWarnings("unchecked")
  @Override
  public Set<String> getReplaceUuidsOnly()
  {
    if (replacedUuids == null)
    {
      replacedUuids = new LinkedHashSet<String>();
      addReplacedUuids(replacedUuids, subject.undeleted_getDeltaReplacedAttributes());
      addReplacedUuids(replacedUuids, subject.undeleted_getDeltaReplacedOperations());
      addReplacedUuids(replacedUuids, subject.undeleted_getDeltaReplacedPorts());
      addReplacedUuids(replacedUuids, subject.undeleted_getDeltaReplacedConnectors());
      addReplacedUuids(replacedUuids, subject.undeleted_getDeltaReplacedTraces());
    }
    
    return replacedUuids;
  }
  
  //////////////////////////////////////////// static helper functions
  public static void addReplacedUuids(Set<String> replacedUuids, ArrayList<? extends DeltaReplacedConstituent> replaces)
  {
    for (DeltaReplacedConstituent replace : replaces)
      replacedUuids.add(replace.getReplaced().getUuid());
  }

  /**
   * record the deltas, as a set of pairs for adds, a set of uuids for deletes and a set of pairs for replaces
   */
  public static Deltas createDeltas(
      DEElement deElement,
      ConstituentTypeEnum type,
      List<? extends Element> elements,
      List<? extends DeltaDeletedConstituent> deltaDeletedElements,
      List<? extends DeltaReplacedConstituent> deltaReplacedElements,
      DeltaElementAcceptor deltaElementAcceptor)
  {
    IDeltaEngine engine = GlobalDeltaEngine.engine;
    Set<DeltaPair> adds = new LinkedHashSet<DeltaPair>();
    Set<String> deletes = new LinkedHashSet<String>();
    Set<DeltaPair> replaces = new LinkedHashSet<DeltaPair>();

    for (Element element : elements)
    {
      if (deltaElementAcceptor == null || deltaElementAcceptor.accept(element))
        adds.add(new DeltaPair(element.getUuid(), engine.locateObject(element).asConstituent()));
    }
    
    for (DeltaDeletedConstituent del : deltaDeletedElements)
    {
      if (deltaElementAcceptor == null || deltaElementAcceptor.accept(del.getDeleted()))
        deletes.add(del.getDeleted().getUuid());
    }
    
    for (DeltaReplacedConstituent replace : deltaReplacedElements)
    {
      
      if (deltaElementAcceptor == null || deltaElementAcceptor.accept(replace.getReplaced()))
        replaces.add(
            new DeltaPair(
                replace.getReplaced().getUuid(),
                engine.locateObject(replace.getReplacement()).asConstituent()));
    }
    
    return new Deltas(deElement, adds, deletes, replaces, type);
  }

	@Override
	public Set<DEElement> getPossibleImmediateSubElements()
	{
		// look in the reverse direction at all resemblances
		Set<DEElement> immediate = new LinkedHashSet<DEElement>();
		for (Object obj : subject.undeleted_getReverseDependencies())
		{
			Dependency dep = (Dependency) obj;
			if (dep.isResemblance())
			{
				// get the owner of the dependency
				if (dep.getOwner() instanceof Class)
				{
					NamedElement real = CommonRepositoryFunctions.translateFromSubstitutingToSubstituted((NamedElement) dep.getOwner());
					if (real instanceof Class)
						immediate.add(getEngine().locateObject(real).asElement());
				}
			}
		}
		return immediate;
	}

	@Override
	public List<DEElement> getSubstituters()
	{
		// look back to find any elements that substitute this
		List<DEElement> substituters = new ArrayList<DEElement>();
		for (Object obj : subject.undeleted_getReverseDependencies())
		{
			Dependency dep = (Dependency) obj;
			if (dep.isReplacement())
			{
				Element elem = dep.getOwner();
				if (elem instanceof Class)
					substituters.add(getEngine().locateObject(elem).asElement());
			}
		}
		return substituters;
	}

	@Override
	public ComponentKindEnum getComponentKind()
	{
		return ComponentKindEnum.values()[subject.getComponentKind().getValue()];
	}

	public List<DEAppliedStereotype> getReplacedAppliedStereotypes()
	{
  	List<DEAppliedStereotype> stereos = new ArrayList<DEAppliedStereotype>();
  	for (Object obj : subject.undeleted_getAppliedBasicStereotypes())
  		stereos.add(new UML2AppliedStereotype((Stereotype) obj, subject));
  	return stereos;
	}

	@Override
	public boolean isRawRetired()
	{
		return subject.isRetired();
	}

	@Override
	public boolean isRawAbstract()
	{
		return subject.isAbstract();
	}
}
