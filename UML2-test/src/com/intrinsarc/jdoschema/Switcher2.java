package com.intrinsarc.jdoschema;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.util.*;

public class Switcher2 extends EcoreSwitch
{
  public Object caseEClass(EClass cls)
  {
    System.out.println("    com.objectdb.Enhancer.enhance(\"org.eclipse.uml2.impl." + cls.getName() + "Impl\");");

    return new Object();
  }
}