package com.intrinsarc.multiuser;

import java.lang.reflect.*;
import java.util.*;

import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

public class UML2User1 extends UML2MultiUserBase
{
  public static void main(String[] args)
  {
    new UML2User1().go();
  }

  private void go()
  {
    pm = setUpPersistenceManager();
    final UML2FactoryImpl factory = new UML2FactoryImpl();

    // make class with a name of "Foo"
    int size = 10000;
    Package pkgs[] = new Package[size];
    start();
    for (int lp = 0; lp < size; lp++)
    {
      Package internalPkg = factory.createPackage();
      internalPkg.setName("Foo");
      pm.makePersistent(internalPkg);
      pkgs[lp] = internalPkg;
    }
    end();
    
    
    System.out.println("$$ created " + size + " packages named Foo and committed");

    
    while (true)
    {
      String value = waitForEnter("look at last package name again (1) = evict all, (2) = keep all, (3) = exit");
      if (value.startsWith("3"))
        break;
      checkAllPackages(pkgs, value.startsWith("1"));
    }
  }

  private void checkAllPackages(Package pkgs[], boolean evictAll)
  {
    if (evictAll)
    {
      long start = System.currentTimeMillis();
      pm.evictAll();
      long end = System.currentTimeMillis();
      System.out.println("$$ evict all took " + (end - start));
    }
    
    long start = System.currentTimeMillis();
    Set<String> names = new HashSet<String>();
    for (int lp = 0; lp < pkgs.length; lp++)
      names.add(pkgs[lp].getName());
    long end = System.currentTimeMillis();
    for (String name : names)
      System.out.println("$$ found name " + name);
    System.out.println("$$ took " + (end - start) + "ms with " + (evictAll ? "eviction" : "no eviction"));
    System.out.println("$$ this translates to " + (int)((double) pkgs.length / ((end - start) / 1000.0)) + " reads per second");
  }
}
