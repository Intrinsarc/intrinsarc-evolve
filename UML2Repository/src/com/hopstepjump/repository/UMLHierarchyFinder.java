package com.hopstepjump.repository;

import java.util.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

public class UMLHierarchyFinder
{
  private EClass elementClass;
  
  public UMLHierarchyFinder(Element element)
  {
    this.elementClass = element.eClass();
  }
  
  public UMLHierarchyFinder(EClass ecls)
  {
    elementClass = ecls;
  }

  private void sort(ArrayList<EClass> sorted)
  {
    Collections.sort(sorted,
        new Comparator<EClass>()
        {
          public int compare(EClass e1, EClass e2)
          {
            Set<EClass> h1 = findHierarchy(e1);
            if (h1.contains(e2))
              return 1;
            if (e1 == e2)
              return 0;
            
            // if neither are in the superset of the other, order by name
            Set<EClass> h2 = findHierarchy(e2);
            if (!h2.contains(e1))
            {
              // always prefer anything starting with Name :-)
              if (e1.getName().startsWith("Name"))
                return -1;
              if (e2.getName().startsWith("Name"))
                return 1;
              return e1.getName().compareTo(e2.getName());
            }
            
            // otherwise, we have the opposite order
            return -1;
          }
        });
  }

  public List<EClass> findSortedHierarchy()
  {
    Set<EClass> found = findHierarchy(elementClass);
    
    ArrayList<EClass> sorted = new ArrayList<EClass>(found);
    sort(sorted);
    return sorted;
  }

  private Set<EClass> findHierarchy(EClass cls)
  {
    Set<EClass> found = new HashSet<EClass>();
    expandOut(cls, found);
    return found;
  }

  private void expandOut(EClass cls, Set<EClass> found)
  {
    found.add(cls);
    // look for any superclasses
    for (Object object : cls.getEAllSuperTypes())
    {
      EClass superType = (EClass) object;
      expandOut(superType, found);
    }
  }
}
