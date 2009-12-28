package com.hopstepjump.jdoschema;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;

public class JDOMetaDataSwitcher extends Switcher
{
  public Object caseEClass(EClass cls)
  {
    out.print("\t<class name=\"org.eclipse.uml2.impl." + cls.getName() + "Impl\"");
    
    // is this special?
    boolean special = cls.getName().startsWith("J_");

    EList superClasses = cls.getESuperTypes();
    EClass superClass = null;
    if (superClasses.size() > 0)
    {
      superClass = (EClass) superClasses.get(0);
      String superClassName = superClass.getName();
      if (superClassName == null)
        superClassName = "EModelElement";
      
      out.println(" persistence-capable-superclass=\"org.eclipse.uml2.impl." + superClassName + "Impl\" >");
    }
    else
      out.println(" >");
    
    // handle templateable parameter differently
    if (cls.getName().equals("TemplateParameter"))
      out.println("\t\t<field name=\"default_\" persistence-modifier=\"none\" />");
    
    // handle links etc
    EList links = cls.getEAllStructuralFeatures();
    for (Object baseStruct : links)
    {
      EStructuralFeature struct = (EStructuralFeature) baseStruct;
      EReference ref = null;
      if (struct instanceof EReference)
        ref = (EReference) struct;
      
      // if this is also contained in the superclass' list, don't bother
      if (superClass != null)
      {
        if (superClass.getEAllStructuralFeatures().contains(struct))
          continue;
      }
      
      if (!struct.isDerived())
      {
        if (struct.getUpperBound() == -1)
        {          
          out.print("\t\t<field name=\"" + struct.getName() + "\" persistence-modifier=\"persistent\" ");
          if (special)
          {
            out.println("default-fetch-group=\"true\" embedded=\"true\" >");
            out.println("\t\t  <collection embedded-element=\"true\" />");
            out.println("\t\t</field>");
          }
          else
            out.println(" />");
        }
        else
        {
          String name = struct.getName();
          if (struct.getName().startsWith("binaryData"))
          {
            out.print("\t\t<field name=\"" + struct.getName() + "\" persistence-modifier=\"persistent\" ");
            out.println("embedded=\"true\" />");            
          }
          else
          if (struct.getEType().getName().equals("Boolean"))
          {
            // these literally aren't there -- flags are used instead
          }
          else
          if (ref != null && ref.isContainer())
          {
            // these are an alias to the container reference
          }
          else
          if (name.contains("_") && !name.startsWith("j_"))
          {
            // ignore this, as it doesn't seem to be generated
          }
          else
          if (struct.getEType().getName().endsWith("Kind"))
          {
            out.print("\t\t<field name=\"" + struct.getName() + "\" persistence-modifier=\"persistent\" ");
            out.println("embedded=\"true\" />");
          }
          else
          // handle the type field of Operation, which causes a problem for some unknown reason
          if (cls.getName().equals("Operation") && struct.getName().equals("type"))
              ;
          else
          {
            out.print("\t\t<field name=\"" + struct.getName() + "\" persistence-modifier=\"persistent\" ");
            if (special)
              out.print("default-fetch-group=\"true\"");
            out.println(" />");
          }
        }
      }
    }
        
    out.println("\t</class>");

    return new Object();
  }
}
