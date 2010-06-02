package com.hopstepjump.jumble.alloy;

import java.util.*;

public class Stratum implements Comparable<Stratum>
{
  private List<Stratum> dependentOn = new ArrayList<Stratum>();
  private String name;
  private String number;
  private org.eclipse.uml2.Package pkg;
  
  public Stratum(String name, String number)
  {
    this.name = name;
    this.number = number;
  }
  
  public void addDependency(Stratum lower)
  {
    dependentOn.add(lower);
  }

  public int compareTo(Stratum other)
  {
    // if we can find this in our dependencies, then it is lower
    if (inDependencies(other))
      return -1;
    if (other.inDependencies(this))
      return 1;
    return 0;
  }

  private boolean inDependencies(Stratum other)
  {
    if (dependentOn.contains(other))
      return true;
    for (Stratum s : dependentOn)
    {
      if (s.inDependencies(other))
        return true;
    }
    return false;
  }

  public String getFullName()
  {
    return name + number;
  }
  
  public String getNumber()
  {
    return number;
  }
  
  public int getActualNumber()
  {
    return new Integer(number);
  }

  public org.eclipse.uml2.Package getPackage()
  {
    return pkg;
  }

  public void setPackage(org.eclipse.uml2.Package pkg)
  {
    this.pkg = pkg;
  }
}

