package com.hopstepjump.jdoschema;

import org.eclipse.emf.ecore.*;

public class ByteCodeEnhancerSwitcher extends Switcher
{
  public Object caseEClass(EClass cls)
  {
    out.println("    com.objectdb.Enhancer.enhance(\"org.eclipse.uml2.impl." + cls.getName() + "Impl\");");

    return new Object();
  }
}