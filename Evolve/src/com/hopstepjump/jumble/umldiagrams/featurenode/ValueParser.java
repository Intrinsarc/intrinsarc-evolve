package com.hopstepjump.jumble.umldiagrams.featurenode;

import java.util.*;
import java.util.regex.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.base.*;
import static com.hopstepjump.backbone.nodes.simple.ValuePatterns.*;

public class ValueParser
{	
	public static List<ValueSpecification> decodeParameters(String parameters, Package pkg, Classifier classifier)
	{
		if (parameters.trim().equals(DEFAULT_PATTERN))
		{
			List<ValueSpecification> values = new ArrayList<ValueSpecification>();
			Expression v = UML2Factory.eINSTANCE.createExpression();
			v.setBody(DEFAULT_PATTERN);
			values.add(v);
			return values;
		}
		
		IDeltaEngine engine = GlobalDeltaEngine.engine;
		DEStratum perspective = engine.locateObject(pkg).asStratum();
		DEElement element = engine.locateObject(classifier).asElement();
		
		List<ValueSpecification> values = new ArrayList<ValueSpecification>();
		parameters = skip(parameters, "(");
		for (;;)
		{
			Matcher m = OR_PATTERN.matcher(parameters);
			if (!m.matches())
				break;
	
			int number = 0;
			String full = "";
			for (int lp = 0; lp < NUM_PATTERNS; lp++)
			{
				String match = m.group(lp+2);
				if (match != null)
				{
					parameters = parameters.substring(m.group(1).length());
					number = lp;
					full = match;
				}
			}
			
			if (number != 7)
			{
				Expression v = UML2Factory.eINSTANCE.createExpression();
				String translate = translateString(full);
				if (number == 0)
					translate = "\"" + translate + "\"";
				v.setBody(translate);
				values.add(v);
			}
			else
			{
				// we must find the resolved variable in order to proceed
				Property found = null;
				for (DeltaPair pair : element.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getConstituents(perspective))
				{
					if (pair.getConstituent().getName().equals(full))
					{
						found = (Property) pair.getOriginal().getRepositoryObject();
						break;
					}
				}
				if (found != null)
				{
					PropertyValueSpecification v = UML2Factory.eINSTANCE.createPropertyValueSpecification();
					v.setProperty(found);
					values.add(v);
				}
			}
			
			parameters = skip(parameters, ",");
		}
		return values;
	}
	
  private static String translateString(String str)
	{
  	return str.replace("\\\\", "\\").replace("\\n", "\n").replace("\\t", "\t").replace("\\\"", "\"");
	}

	private static String skip(String str, String skipString)
	{
  	String s = str.trim();
  	if (s.startsWith(skipString))
  		s = s.substring(skipString.length());
  	return s;
	}
}
