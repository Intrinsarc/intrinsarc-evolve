package com.hopstepjump.repositorybase;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.deltaengine.base.*;

public class UMLTypes
{

  public static boolean isPart(Element element)
  {
    return extractInstanceOfPart(element) != null;
  }

  public static InstanceSpecification extractInstanceOfPart(Element element)
  {
  	if (element instanceof Property)
  	{
  		Property property = (Property) element;
  		if (property.undeleted_getDefaultValue() instanceof InstanceValue)
  		{
    		InstanceValue value = (InstanceValue) property.undeleted_getDefaultValue();
  			return value.undeleted_getInstance();
  		}
  	}
  	return null;  	
  }

  /**
   * find the classifier that this is substituting
   * @return null if no substitutions
   */
  public static NamedElement extractSubstitutedClassifier(NamedElement subject)
  {
    // do we have any substitution
  	if (subject == null || subject.undeleted_getClientDependencies() == null)
  		return null;
    for (Object obj : subject.undeleted_getClientDependencies())
    {
      Dependency dep = (Dependency) obj;
      if (dep.isReplacement())
      {
        NamedElement target = dep.undeleted_getDependencyTarget();
        if (target != null && target instanceof NamedElement)
          return (NamedElement) target;
      }
    }
    
    return null;
  }

  /**
   * @return true if this classifier is substituting for another 
   */
  public static boolean isClassifierSubstituting(NamedElement subject)
  {
  	// do we have any substitutions
  	for (Object obj : subject.undeleted_getClientDependencies())
  	{
  		Dependency dep = (Dependency) obj;
  		if (dep.isReplacement())
  		{
  		  NamedElement target = dep.undeleted_getDependencyTarget();
  		  if (target != null)
  				return true;
  		}
  	}
  	
  	return false;
  }

  public static boolean isStratum(Element element)
  {
    return
    	element instanceof Package &&
    	StereotypeUtilities.isRawStereotypeApplied(element, CommonRepositoryFunctions.STRATUM);
  }

  public static boolean isRelaxedStratum(Element element)
  {
    return
      isStratum(element) &&
      StereotypeUtilities.extractBooleanProperty(element, CommonRepositoryFunctions.RELAXED);
  }

  public static boolean isStrictStratum(Element element)
  {
    return
      isStratum(element) &&
      !StereotypeUtilities.extractBooleanProperty(element, CommonRepositoryFunctions.RELAXED);
  }


	public static boolean isDestructiveStratum(Element element)
	{
    return
    	isStratum(element) &&
    	StereotypeUtilities.extractBooleanProperty(element, CommonRepositoryFunctions.DESTRUCTIVE);
	}
	
  public static boolean isStratumPackage(Object obj)
  {
    if (obj.getClass() != PackageImpl.class)
      return false;
    return StereotypeUtilities.isRawStereotypeApplied((Element) obj, CommonRepositoryFunctions.STRATUM);
  }

  public static boolean containsParts(Class obj)
	{
  	DEComponent elem = GlobalDeltaEngine.engine.locateObject(obj).asComponent();
  	return !elem.getDeltas(ConstituentTypeEnum.DELTA_PART).getConstituents(elem.getHomeStratum()).isEmpty();
	}

  public static Package findDefinitiveOwningStratum(Element element)
  {
    // travel upwards until we find a stratum, or complain bitterly
    for (Element owner = element; owner != null; owner = owner.getOwner())
    {
      if (isStratumPackage(owner))
        return (Package) owner;
    }
    return null;
  }
}
