package com.intrinsarc.jdoschema;

import java.io.*;

import org.eclipse.emf.ecore.util.*;

public class Switcher extends EcoreSwitch
{
  protected PrintStream out;
  
  public void setStream(PrintStream out)
  {
    this.out = out;
  }
}
