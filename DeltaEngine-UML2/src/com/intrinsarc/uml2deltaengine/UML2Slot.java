package com.intrinsarc.uml2deltaengine;

import java.util.*;

import org.eclipse.uml2.*;

import com.intrinsarc.backbone.nodes.*;
import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.repositorybase.*;

public class UML2Slot extends DESlot
{
	private Slot subject;
	private DEAttribute attribute;
	private DEAttribute alias;
	private List<DEParameter> value;
	private List<DEAppliedStereotype> appliedStereotypes;

	public UML2Slot(Slot subject)
	{
		this.subject = subject;
		IDeltaEngine engine = GlobalDeltaEngine.engine;
		
		StructuralFeature feature = subject.undeleted_getDefiningFeature();
		if (feature != null)
			attribute = engine.locateObject(feature).asConstituent().asAttribute();
		ArrayList values = subject.undeleted_getValues();
		
		if (!values.isEmpty())
		{
			ValueSpecification val = (ValueSpecification) values.get(0);
			if (val instanceof PropertyValueSpecification && ((PropertyValueSpecification) val).isAliased())
			{
				PropertyValueSpecification pval = (PropertyValueSpecification) val;
				Property prop = pval.undeleted_getProperty();
				if (prop != null)
					alias = engine.locateObject(prop).asConstituent().asAttribute();
			}
			else
			{
		  	value = new ArrayList<DEParameter>();
		  	for (Object spec : subject.undeleted_getValues())
		  	{
		  		if (spec instanceof Expression)
		  		{
	    			String body = ((Expression) spec).getBody();
	    			if (body.startsWith(">"))
	    			{
	    				body = StereotypeUtilities.extractStringProperty(subject, CommonRepositoryFunctions.ACTUAL_SLOT_VALUE);
	    				if (body == null)
	    				{
	    					// set the value to > so we can alert the error checker
	    					value.clear();
	    					BBParameter param = new BBParameter(">");
	    					value.add(param);
	    					break;
	    				}
	    				value.add(new UML2Parameter(body));
	    			}
	    			else
	    				value.add(new UML2Parameter(body));
		  		}
		  		else
		  		if (spec instanceof PropertyValueSpecification)
		  		{
		  			Property prop = ((PropertyValueSpecification) spec).getProperty();
		  			value.add(new UML2Parameter(GlobalDeltaEngine.engine.locateObject(prop).asConstituent().asAttribute()));
		  		}
		  	}
	  	}
		}
	  appliedStereotypes = UML2Component.extractStereotypes(subject);
	}
	
	@Override
	public DEAttribute getAttribute()
	{
		return attribute;
	}

	@Override
	public DEAttribute getEnvironmentAlias()
	{
		return alias;
	}

	@Override
	public List<DEParameter> getValue()
	{
		return value;
	}

	@Override
	public boolean isAliased()
	{
		return alias != null;
	}
	
	@Override
	public DESlot asSlot()
	{
		return this;
	}

	@Override
	public String getName()
	{
		return "";
	}

	@Override
	public DEObject getParent()
	{
    // look up to see if we can find a stratum parent
    Element parent = subject.getOwner();
    if (parent == null)
      return null;
    
    return getEngine().locateObject(parent);
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
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		return appliedStereotypes;
	}
}
