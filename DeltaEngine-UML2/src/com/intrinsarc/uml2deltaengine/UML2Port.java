package com.intrinsarc.uml2deltaengine;

import java.util.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.repositorybase.*;

public class UML2Port extends DEPort
{
  private Port subject;
	private List<DEAppliedStereotype> appliedStereotypes;

  public UML2Port(Port subject)
  {
    super();
    this.subject = subject;
    appliedStereotypes = UML2Component.extractStereotypes(subject);
  }

  @Override
  public String getName()
  {
    return subject.getName();
  }

  @Override
  public DEObject getParent()
  {
    // if this is a replacement, go a bit higher to get the owner
    if (subject.getOwner() instanceof DeltaReplacedConstituent)
      return getEngine().locateObject(subject.getOwner().getOwner());
    if (subject.getOwner() == null)
    	return null;
    return getEngine().locateObject(subject.getOwner());
  }

  @Override
  public Object getRepositoryObject()
  {
    return subject;
  }

  @Override
  public String getUuid()
  {
    return subject.getUuid();
  }

  @Override
  public Set<DEInterface> getSetProvidedInterfaces()
  {
    // this is what is implemented
    Set<DEInterface> provided = new LinkedHashSet<DEInterface>();
    
    Type type =  subject.undeleted_getType();
    if (type == null || !(type instanceof Class))
      return provided;
    Class portType = (Class) type;
    
    for (Object obj : portType.undeleted_getImplementations())
    {
      Implementation impl = (Implementation) obj;
      Interface contract = impl.undeleted_getContract();
      if (contract != null)
        provided.add(getEngine().locateObject(contract).asInterface());
    }
    return provided;
  }

  
  @Override
  public Set<DEInterface> getSetRequiredInterfaces()
  {
    // this is what is implemented
    Set<DEInterface> required = new LinkedHashSet<DEInterface>();
    
    Type type =  subject.undeleted_getType();
    if (type == null || !(type instanceof Class))
      return required;
    Class portType = (Class) type;
    
    for (Object obj : portType.undeleted_getOwnedAnonymousDependencies())
    {
      Dependency dep = (Dependency) obj;
      NamedElement elem = dep.getDependencyTarget();
      if (elem instanceof Interface)
        required.add(getEngine().locateObject(elem).asInterface());
    }
    return required;
  }

  
  @Override
  public int getUpperBound()
  {
    return subject.getUpper();
  }

  @Override
  public int getLowerBound()
  {
    return subject.getLower();
  }

  @Override
  public DEPort asPort()
  {
    return this;
  }

  @Override
  public DEConnector asConnector()
  {
    return null;
  }

	@Override
	public boolean isSuppressGeneration()
	{
		return StereotypeUtilities.extractBooleanProperty(subject, CommonRepositoryFunctions.SUPPRESS_GENERATION_PORT);
	}
	
	@Override
	public boolean isBeanMain()
	{
		return StereotypeUtilities.extractBooleanProperty(subject, CommonRepositoryFunctions.PORT_BEAN_MAIN);
	}
	
	@Override
	public boolean isBeanNoName()
	{
		return StereotypeUtilities.extractBooleanProperty(subject, CommonRepositoryFunctions.PORT_BEAN_NO_NAME);
	}
	
	@Override
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		return appliedStereotypes;
	}

	@Override
	public PortKindEnum getPortKind()
	{
		PortKind kind = subject.getKind();
		if (kind.equals(PortKind.NORMAL_LITERAL))
			return PortKindEnum.NORMAL;
		if (kind.equals(PortKind.CREATE_LITERAL))
			return PortKindEnum.CREATE;
		if (kind.equals(PortKind.HYPERPORT_START_LITERAL))
			return PortKindEnum.HYPERPORT_START;
		if (kind.equals(PortKind.HYPERPORT_END_LITERAL))
			return PortKindEnum.HYPERPORT_END;
		return PortKindEnum.AUTOCONNECT;
	}

	@Override
	public boolean isOrdered()
	{
		return subject.isOrdered();
	}
}
