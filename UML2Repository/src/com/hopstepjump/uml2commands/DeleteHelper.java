package com.hopstepjump.uml2commands;

import java.util.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

public class DeleteHelper
{
  public static void bumpDeleted(List<Element> elements, int increment)
  {
    // for each element, recurse down, looking at all contained objects
    for (Element element : elements)
    {
      bumpDeleted(element, increment);
    }
  }

  public static void bumpDeleted(Element element, int increment)
  {
    element.setJ_deleted(element.getJ_deleted() + increment);
    for (TreeIterator iter = element.eClass().eAllContents(); iter.hasNext();)
    {
      Object obj = iter.next();
      if (obj instanceof EReference)
      {
        EReference reference = (EReference) obj;
        
        if (reference.isContainment())
        {
          Object value = element.eGet(reference);
          if (value instanceof EList)
          {
            for (Object inner : (EList) value)
            {
              bumpDeleted((Element) inner, increment);
            }
          }
          else
            if (value != null)
              bumpDeleted((Element) value, increment);
        }        
      }
    }
  }
}
