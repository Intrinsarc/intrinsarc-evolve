package com.intrinsarc.jdoschema;

import java.io.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.ecore.resource.impl.*;
import org.eclipse.emf.ecore.xmi.impl.*;

public class Generator
{
  public Generator()
  {
    // register the XMI factory
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().
      put("*", new XMIResourceFactoryImpl());
      
  }
  
  public void generate(Switcher switcher, PrintStream out) throws IOException
  {
    switcher.setStream(out);
    Resource resource = createXMIResource("../org.eclipse.uml2/model/UML2.ecore");
    resource.load(null);
    EPackage pkg = (EPackage) resource.getContents().get(0);
    System.out.println("$$ found " + pkg.getEClassifiers().size() + " classifiers");
    for (Object classifier : pkg.getEClassifiers())
      switcher.doSwitch((EObject) classifier);
  }


  /**
   * @return
   */
  private Resource createXMIResource(String fileName)
  {
    // create the resource where Foo will live
    ResourceSet resourceSet = new ResourceSetImpl();
    URI fileURI = URI.createFileURI(new File(fileName).getAbsolutePath());
    Resource resource = resourceSet.createResource(fileURI);
    return resource;
  }  
}
