package com.hopstepjump.backbone.nodes.simple;

import static com.hopstepjump.backbone.nodes.simple.ValuePatterns.*;

import java.util.*;
import java.util.regex.*;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.backbone.nodes.simple.internal.*;

public class BBSimpleParameter extends BBSimpleObject
{
	private String literal;
	private BBSimpleAttribute attribute;
	private Object resolvedLiteral;
	private boolean isDefault;
	private boolean resolved;
	
	@Override
	public Map<String, List<? extends BBSimpleObject>> getChildren(boolean top)
	{
		Map<String, List<? extends BBSimpleObject>> children = new LinkedHashMap<String, List<? extends BBSimpleObject>>();
		List<BBSimpleObject> t = new ArrayList<BBSimpleObject>();
		t.add(attribute);
		children.put("attribute", t);
		
		return children;
	}

	@Override
	public String getTreeDescription()
	{
		return
			"Parameter" + (literal != null ? ", literal = " + literal : "");
	}
	
	public BBSimpleParameter(String expression)
	{
		this.literal = expression;
	}
	
	public BBSimpleParameter(BBSimpleAttribute attribute)
	{
		this.attribute = attribute;
	}

	public String getLiteral()
	{
		return literal;
	}

	public BBSimpleAttribute getAttribute()
	{
		return attribute;
	}

	public void remapAttribute(Map<BBSimpleAttribute, BBSimpleAttribute> redundant)
	{
		if (attribute != null && redundant.containsKey(attribute))
			attribute = redundant.get(attribute);
	}

	public void addAllDependencies(Set<BBSimpleAttribute> all)
	{
		if (attribute != null)
			all.add(attribute);
	}
	
	public void resolveImplementation(BBSimpleElementRegistry registry, BBSimpleAttribute attr) throws BBImplementationInstantiationException
	{
		if (resolved)
			return;		
		resolved = true;

		if (attribute != null)
			attribute.resolveImplementation(registry);
		else
		if (literal.equals(DEFAULT_PATTERN))
		{
			isDefault = true;
		}
		else
			resolvedLiteral = decodeParameter(literal, attr);
	}
	

	private Object decodeParameter(String parameter, BBSimpleAttribute attr) throws BBImplementationInstantiationException
	{
		Matcher m = OR_PATTERN.matcher(parameter);
		if (!m.matches())
			throw new BBImplementationInstantiationException("Cannot interpret " + parameter + " as a literal for attribute " + attr, attr.getOwner());
			
			int number = 0;
			String full = "";
			for (int lp = 0; lp < NUM_PATTERNS; lp++)
			{
				String match = m.group(lp+2);
				if (match != null)
				{
					full = match;
					number = lp;
					break;
				}
			}
			
			switch (number)
			{
			case 0:				
				return translateString(full);
			case 1:
				return new Float(full);
			case 2:
				return new Double(full);
			case 3:
				return new Character(translateString(full).charAt(0));
			case 4:
				return new Integer(full);
			case 5:
				return new Boolean(full);
			case 6:
				return null;
			default:
				throw new BBImplementationInstantiationException("Cannot interpret " + parameter + " as a literal for attribute " + attr, attr.getOwner());
			}
	}
	
  private static String translateString(String str)
	{
  	return str.replace("\\\\", "\\").replace("\\n", "\n").replace("\\t", "\t").replace("\\\"", "\"");
	}
  
  public Class<?> getValueClass()
  {
  	if (isDefault)
  		return null;
		if (resolvedLiteral != null)
			return resolvedLiteral.getClass();
		return attribute.getType().getImplementationClass();
  }
  
  public boolean isDefault()
  {
  	return isDefault;
  }
  
  public Object resolveValue(BBSimpleInstantiatedFactory context) throws BBRuntimeException
  {
  	if (literal != null)
  		return resolvedLiteral;
  	else
  		return context.resolveAttributeValue(attribute).get();
  }

	public Object getResolvedLiteral()
	{
		return resolvedLiteral;
	}

	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public String getRawName()
	{
		return null;
	}
}
