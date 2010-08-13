package com.intrinsarc.uml2deltaengine;

import java.util.*;

import org.eclipse.uml2.*;

import com.intrinsarc.backbone.nodes.*;
import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.repositorybase.*;

public class UML2Attribute extends DEAttribute
{
  private Property subject;
	private List<DEAppliedStereotype> appliedStereotypes;

  public UML2Attribute(Property subject)
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
  public DEPort asPort()
  {
    return null;
  }

  @Override
  public DEConnector asConnector()
  {
    return null;
  }
  
  public DEElement getType()
  {
  	// extract the type from type element we point to
  	if (subject.undeleted_getType() == null)
  		return null;
  	return GlobalDeltaEngine.engine.locateObject(subject.undeleted_getType()).asElement();
  }

  public List<DEParameter> getDefaultValue()
  {
  	List<DEParameter> params = new ArrayList<DEParameter>();
  	for (Object spec : subject.undeleted_getDefaultValues())
  	{
  		if (spec instanceof Expression)
  		{
  			String body = ((Expression) spec).getBody();
  			if (body.startsWith(">"))
  			{
  				body = StereotypeUtilities.extractStringProperty(subject, CommonRepositoryFunctions.ACTUAL_VALUE);
  				if (body == null)
  				{
  					params.clear();
  					BBParameter param = new BBParameter(">");
  					params.add(param);
  					return params;
  				}
  				params.add(new UML2Parameter(body));
  			}
  			else
  				params.add(new UML2Parameter(body));
  		}
  		else
  		if (spec instanceof PropertyValueSpecification)
  		{
  			Property prop = ((PropertyValueSpecification) spec).getProperty();
  			params.add(new UML2Parameter(GlobalDeltaEngine.engine.locateObject(prop).asConstituent().asAttribute()));
  		}
  	}
  	return params;
  }

	@Override
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		return appliedStereotypes;
	}

	@Override
	public boolean isReadOnly()
	{
		return subject.getReadWrite().equals(PropertyAccessKind.READ_ONLY_LITERAL);
	}

	@Override
	public boolean isWriteOnly()
	{
		return subject.getReadWrite().equals(PropertyAccessKind.WRITE_ONLY_LITERAL);
	}

	@Override
	public boolean isSuppressGeneration()
	{
		return
		  StereotypeUtilities.extractBooleanProperty(subject, CommonRepositoryFunctions.SUPPRESS_GENERATION) ||
		  StereotypeUtilities.extractBooleanProperty(subject, CommonRepositoryFunctions.SUPPRESS_GENERATION_PORT);
	}
}
