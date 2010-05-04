package com.hopstepjump.repository;

import java.util.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.repositorybase.*;

public class UMLNodeText
{
  public static String getNodeText(Element element)
  {
  	// is this a part?
  	InstanceSpecification instance = UMLTypes.extractInstanceOfPart(element);
  	if (instance != null)
  	{
  		Package current = GlobalSubjectRepository.repository.findOwningStratum(element);
  		EList classifiers = instance.getClassifiers();
  		String name = ((NamedElement) element).getName();
  		if (classifiers.isEmpty())
  			return getName(element);
  		Classifier cls = (Classifier) classifiers.get(0);
  		DEComponent comp = GlobalDeltaEngine.engine.locateObject(cls).asComponent();
  		return name + " : " + comp.getName(GlobalDeltaEngine.engine.locateObject(current).asStratum());
  	}
  	
  	// is this a slot?
  	if (element instanceof Slot)
  	{
  		Slot slot = (Slot) element;  		
  		Package current = GlobalSubjectRepository.repository.findOwningStratum(slot);
    	Property part = (Property) slot.getOwner().getOwner().getOwner();
    	Classifier cls = (Classifier) getPossibleDeltaSubject(part).getOwner();
    	return getSlotText(current, cls, slot);
  	}
  	else
    // if this is inheritance, format accordingly
    if (element instanceof Generalization)
    {
    	Generalization gen = (Generalization) element;
    	return getName(gen.getGeneral());
    }
    else
    if (element instanceof Port)
    {
    	return makeNameFromPort((Port) element);
    }
    else
    if (element instanceof Property)
    {
  		Package current = GlobalSubjectRepository.repository.findOwningStratum(element);
    	return makeNameFromAttribute(current, (Classifier) getPossibleDeltaSubject(element).getOwner(), (Property) element);
    }
    else
    if (element instanceof Operation)
    {
      return makeNameFromOperation((Operation) element, false);
    }
    else
    if (element instanceof Parameter)
    {
      return makeNameFromParameter((Parameter) element, true, false);
    }
    else
    if (element instanceof Implementation)
    {
      Implementation impl = (Implementation) element;
      return getName(impl.getContract()); 
    }
    else
    if (element instanceof Dependency)
    {
      Dependency dep = (Dependency) element;
      return
        getName(dep.getDependencyTarget()); 
    }
    else
    if (element instanceof Expression)
    {
    	Expression exp = (Expression) element;
    	return exp.getBody();
    }
    else
    if (element instanceof LiteralInteger)
    {
    	LiteralInteger value = (LiteralInteger) element;
    	return "" + value.getValue();
    }
    else
    if (element instanceof LiteralUnlimitedNatural)
    {
    	LiteralUnlimitedNatural value = (LiteralUnlimitedNatural) element;
    	return "" + value.getValue();
    }
    else
    if (element instanceof RequirementsFeatureLink)
    {
    	RequirementsFeatureLink sub = (RequirementsFeatureLink) element;
    	return "(" + getNodeText(sub.undeleted_getType()) + ")";
    }
    else
    if (element instanceof DeltaDeletedConstituent)
    {
      Element deleted = ((DeltaDeletedConstituent) element).getDeleted();
      return getNodeText(deleted) + " (from " + removeFirst(GlobalSubjectRepository.repository.getFullyQualifiedName(deleted.getOwner(), "::")) + ")";
    }
    else
    if (element instanceof DeltaReplacedConstituent)
    {
      Element replaced = ((DeltaReplacedConstituent) element).getReplaced();
      return getNodeText(replaced) + " (from " + removeFirst(GlobalSubjectRepository.repository.getFullyQualifiedName(replaced.getOwner(), "::")) + ")";
    }
      
    return getName(element);
  }
  
  public static Element getPossibleDeltaSubject(Object subject)
  {
    Element element = (Element) subject;
    if (element.getOwner() instanceof DeltaReplacedConstituent)
      return element.getOwner();
    return element;
  }
  
  public static String removeFirst(String fullyQualifiedName)
  {
    return fullyQualifiedName.substring(fullyQualifiedName.indexOf("::") + 2);
  }
  
  public static String getName(NamedElement classifier)
  {
  	if (classifier == null)
  		return "";
  	
  	// is this substituted?
  	if (UMLTypes.isClassifierSubstituting(classifier))
  		return extractSubstitutedClassifierName(classifier);
  	
  	return classifier.getName();
  }
  
  public static String getName(Element element)
  {
  	if (element instanceof Classifier || element instanceof RequirementsFeature)
  	{
  		return getName((NamedElement) element);
  	}
    if (element instanceof NamedElement)
    {
      String name = ((NamedElement) element).getName();
      return (name == null || name.length() == 0) ? " " : name;
    }
    
    return " ";
  }
  
  public static String makeNameFromOperation(Operation op, boolean shortForm)
  {
  	if (op == null)
  		return "test()";
    String name = op.getName() + "(";
    
    // handle the body parameters
    if (!shortForm)
    {
      boolean addedAlready = false;
      for (Object paramObj : op.undeleted_getReturnResults())
      {
        Parameter param = (Parameter) paramObj;
        
        if (!param.getDirection().equals(ParameterDirectionKind.RETURN_LITERAL))
        {
          if (addedAlready)
            name += ", ";
          addedAlready = true;
          name += makeNameFromParameter(param, true, true);
        }
      }
    }
    else
    if (!op.undeleted_getReturnResults().isEmpty())
      name += "...";
    
    name += ")";
    
    // handle the return parameter
    for (Object paramObj : op.undeleted_getReturnResults())
    {
      Parameter param = (Parameter) paramObj;
      
      if (param.getDirection().equals(ParameterDirectionKind.RETURN_LITERAL))
        name += makeNameFromParameter(param, false, false);
    }
    
    return name;
  }
  
  public static String makeNameFromParameter(Parameter param, boolean includeName, boolean inouts)
  {
    String name = "";
        
    if (inouts)
    {
      if (param.getDirection().equals(ParameterDirectionKind.OUT_LITERAL))
        name += "out ";
      if (param.getDirection().equals(ParameterDirectionKind.INOUT_LITERAL))
        name += "inout ";
    }

    if (includeName)
      name += param.getName();
    
    if (param.undeleted_getType() != null)
      name += ": " + param.undeleted_getType().getName();

    if (param.getDefaultValue() != null)
      name += " = " + param.getDefault();
    
    return name;
  }

  public static String makeNameFromPort(Port port)
  {
		ValueSpecification lower = port.getLowerValue();
		ValueSpecification upper = port.getUpperValue();
		
		if (lower != null || upper != null)
		{
			String name = port.getName() + " [";
			if (lower != null)
				name += lower.stringValue() + "..";
			if (upper != null)
      {
        if (port.getUpper() == -1)
          name += "*]";
        else
          name += upper.stringValue() + "]";
      }
			return name;  
		}
		else
			return port.getName();

  }
  
  public static String makeNameFromAttribute(Package current, Classifier cls, Property property)
  {
    Type type = property.undeleted_getType();
    String name = property.getName();
    
    // add a possible multiplicity
    if (property.getUpperValue() != null)
    {
      if (property.getLowerValue() != null)
        name += "[" + property.lowerBound() + "..";
      name += ((property.upperBound() == -1) ? "*" : ("" + property.upperBound())) + "] "; 
    }
    
    if (type != null)
      name += " : " + type.getName();
    String readWrite = "";
    if (property.getReadWrite().equals(PropertyAccessKind.READ_ONLY_LITERAL))
    	readWrite = " {readonly}";
    else
      if (property.getReadWrite().equals(PropertyAccessKind.WRITE_ONLY_LITERAL))
      	readWrite = " {writeonly}";    	

    return name + makeValueText(current, cls, property.undeleted_getDefaultValues()) + readWrite;
  }

	private static String makeValueText(Package current, Classifier cls, List<ValueSpecification> values)
	{
	int size = values.size();
	String name = "";
		if (!values.isEmpty())
		{
			if (size > 1)
				name += " = (";
			else
				name += " = ";
			int lp = 0;
			for (Object value : values)
			{
				if (value instanceof Expression)
					name += ((Expression) value).getBody();    		
		    else
		    if (value instanceof PropertyValueSpecification)
		      name += getAttributeName(current, cls, ((PropertyValueSpecification) value).getProperty());
		    else
		    	name += "??";
				if (++lp != size)
					name += ", ";
			}
			if (size > 1)
				name += ")";
		}
		return name;
	}

  public static String getSlotText(Package current, Classifier cls, Slot slot)
  {
  	// get the part
  	Property part = (Property) slot.getOwner().getOwner().getOwner();
  	
    String name = getSlotName(current, slot);
    ArrayList values = slot.undeleted_getValues();
    
    ValueSpecification expression = null;
    if (values != null && !values.isEmpty())
      expression = (ValueSpecification) values.get(0);
    
    if (expression instanceof PropertyValueSpecification)	
    {
      PropertyValueSpecification spec = (PropertyValueSpecification) expression;
      if (spec.undeleted_getProperty() != null && spec.isAliased())
      {
      	String propertyName = spec.getProperty() == null ? "??" : getAttributeName(current, (Classifier) getPossibleDeltaSubject(part).getOwner(), spec.getProperty());
      	return name + " (" + propertyName + ")";
      }
    }

    // make up the parameter list
    return name + makeValueText(current, cls, values);
  }

  private static String getAttributeName(Package current, Classifier type, Property property)
	{
    for (DeltaPair pair : getPossibleAttributes(current, type))
    {
      if (pair.getOriginal().getRepositoryObject() == property)
        return pair.getConstituent().getName();
    }
    return "??"; 
	}

	public static String getSlotName(Package current, Slot slot)
  {
    if (slot == null || slot.undeleted_getDefiningFeature() == null)
      return "";

    Property feature = (Property) slot.undeleted_getDefiningFeature();
    Class partType = (Class) ((Property) slot.getOwner().getOwner().getOwner()).getType();
    return getAttributeName(current, partType, feature);
  }
  
  public static Set<DeltaPair> getPossibleAttributes(Package current, Classifier partType)
  {
    if (partType == null)
      return new HashSet<DeltaPair>();

    // get the set of properties of the type, from this visual perspective
    IDeltaEngine engine = GlobalDeltaEngine.engine;
    DEComponent type = engine.locateObject(partType).asComponent();
    DEStratum perspective = engine.locateObject(current).asStratum();
    IDeltas deltas = type.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE);
    return deltas.getConstituents(perspective, true);
  }
  
  
  /**
   * extract the name by possibly using the primed version of a substituted classifier
   * @return null if no substitutions
   */
  public static String extractSubstitutedClassifierName(NamedElement subject)
  {
    Package stratum = GlobalSubjectRepository.repository.findOwningStratum(subject);
    DEStratum perspective = GlobalDeltaEngine.engine.locateObject(stratum).asStratum();
    
    return extractSubstitutedClassifierName(perspective, subject);
  }

  /**
   * extract the name by possibly using the primed version of a substituted classifier
   * @return null if no substitutions
   */
  public static String extractSubstitutedClassifierName(DEStratum perspective, NamedElement subject)
  {
    String newName = subject.getName();
    
    // do we have any substitutions
    for (Object obj : subject.undeleted_getClientDependencies())
    {
      Dependency dep = (Dependency) obj;
      if (dep.isReplacement())
      {
        NamedElement target = dep.undeleted_getDependencyTarget();
        if (target != null)
        {
          DEElement substituted = GlobalDeltaEngine.engine.locateObject(target).asElement();
          
          if (newName == null || newName.length() == 0)
            return substituted.getName() + "`";
          else
            return newName + "` (was " + substituted.getFullyQualifiedName() + ")";
        }
      }
    }
    
    return null;
  }
}
