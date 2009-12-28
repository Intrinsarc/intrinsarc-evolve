package com.hopstepjump.repositorybase;

import java.util.*;

import org.eclipse.emf.ecore.*;

public class UMLSubclassFinder
{
  private EClass type;

  public UMLSubclassFinder(EClass type)
  {
    this.type = type;
  }

  public List<EClass> findSubClasses()
  {
    Set<EClass> subclasses = new HashSet<EClass>();
    
    // iterate over the entire UML2 package
    EPackage parent = type.getEPackage();
    for (Object obj : parent.eContents())
    {
      if (obj instanceof EClass)
      {
        EClass possible = (EClass) obj;
        if (!possible.isAbstract() && isSubclassOfType(possible))
        subclasses.add(possible);
      }
    }
    List<EClass> results = new ArrayList<EClass>(subclasses);
    Collections.sort(results, new Comparator<EClass>()
        {
          public int compare(EClass e1, EClass e2)
          {
            return e1.getName().compareTo(e2.getName());
          }
        });
    
    return results;
  }

  private boolean isSubclassOfType(EClass possible)
  {
    if (possible == type)
      return true;
    for (Object obj : possible.getESuperTypes())
    {
      EClass superClass = (EClass) obj;
      boolean match = isSubclassOfType(superClass);
      if (match)
        return true;
    }
    return false;
  }

}
