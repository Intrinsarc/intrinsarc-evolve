package com.intrinsarc.uml2deltaengine;

import java.util.*;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.repositorybase.*;

public class UML2AppliedStereotype extends DEAppliedStereotype
{
	private Element parent;
	private DEComponent stereotype;
	private Map<DEAttribute, String> properties;
	
	public UML2AppliedStereotype(Stereotype stereo, Element element)
	{
		this.parent = element;
		IDeltaEngine engine = GlobalDeltaEngine.engine;
		stereotype = engine.locateObject(stereo).asComponent();
		Collection<DeltaPair> pairs = StereotypeUtilities.findAllStereotypeProperties(element, stereotype).values();
		List<Property> props = new ArrayList<Property>();
		for (DeltaPair pair : pairs)
		  props.add((Property) pair.getOriginal().getRepositoryObject());
		
    for (Object obj : element.undeleted_getAppliedBasicStereotypeValues())
    {
      AppliedBasicStereotypeValue value = (AppliedBasicStereotypeValue) obj;
      System.out.println("$$ value = " + value + ", is deleted = " + value.isThisDeleted() + ", property = " + value.getProperty());
      if (!value.isThisDeleted() && !value.getProperty().isThisDeleted())
      {
      	Property prop = value.getProperty();
        if (props.contains(prop))
        {
          DEAttribute attr = engine.locateObject(prop).asConstituent().asAttribute();
          Object val = value.getValue();
        	if (properties == null)
        		properties = new HashMap<DEAttribute, String>();
          if (val instanceof Expression)
            properties.put(attr, ((Expression) val).getBody());
          else
          if (val instanceof LiteralBoolean)
            properties.put(attr, "" + ((LiteralBoolean) val).isValue());
        }
      }
    }
	}
	
	public Map<DEAttribute, String> getProperties()
	{
		return properties;
	}

	@Override
	public String getName()
	{
		return "";
	}

	@Override
	public DEObject getParent()
	{
		return GlobalDeltaEngine.engine.locateObject(parent);
	}

	@Override
	public DEComponent getStereotype()
	{
		return stereotype;
	}
}
