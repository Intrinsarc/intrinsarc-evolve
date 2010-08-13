package com.intrinsarc.multiuser;

import java.util.*;

import javax.jdo.*;

import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

public class UML2User2 extends UML2MultiUserBase
{
  public static void main(String[] args)
  {
    new UML2User2().go();
  }

  private void go()
  {
    pm = setUpPersistenceManager();
    final UML2FactoryImpl factory = new UML2FactoryImpl();

    waitForEnter("change name of packages Foo to Bar");
    
    long difference = setPackageNames("Foo", "Bar");
    System.out.println("$$ done in " + difference + "ms");

    waitForEnter("change name of packages Bar to FooBar");
    
    difference = setPackageNames("Bar", "FooBar");
    System.out.println("$$ done in " + difference + "ms");
  }

  private long setPackageNames(String oldName, String newName)
  {
    Extent pkgExtent = pm.getExtent(PackageImpl.class, false);
    String filter = "name == \"" + oldName + "\"";
    Query query = pm.newQuery(pkgExtent, filter);
    Collection pkgs = (Collection) query.execute();
    System.out.println("$$ changing " + pkgs.size() + " names to " + newName);
    long start = System.currentTimeMillis();
    start();
    for (Object pkg : pkgs)
    {
      Package realPkg = (Package) pkg;
      realPkg.setName(newName);
    }
    end();
    long end = System.currentTimeMillis();
    return end - start;
  }
}
