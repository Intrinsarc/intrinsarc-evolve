/*
 * Created on May 3, 2004 by Andrew McVeigh
 */
package com.intrinsarc.uml2test;

import java.io.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.ecore.resource.impl.*;
import org.eclipse.emf.ecore.xmi.impl.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;
import org.eclipse.uml2.internal.util.*;

/**
 * @author Andrew
 */


public class UML2Test
{
  public static void main(String args[]) throws IOException
  {
    new UML2Test();
  }
  
  public UML2Test() throws IOException
  {
    ResourceSet resourceSet = new ResourceSetImpl();
    setUpStandaloneUML2Loader();
    
    // make a class and save
    UML2FactoryImpl factory = new UML2FactoryImpl();
    Package pkg = factory.createPackage();
    pkg.setName("MyPackage");
    Class cls = factory.createClass();
    cls.setName("HelloWorld");
    cls.setIsAbstract(true);
    pkg.settable_getOwnedMembers().add(cls);

    
    // persist to a an xml file in xmi format
    Resource resource = createXMIResource("helloworld.uml2");
    resource.getContents().add(pkg);
    resource.save(null);
    System.out.println("Successfully saved");
  }

//  private Car createCar(final PersistenceManager pm, final EmftestFactoryImpl factory, String carName)
//  {
//    pm.currentTransaction().begin();
//    final Car car = factory.createCar();
//    car.setName(carName);
//    return car;
//  }
  
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
  
  private void setUpStandaloneUML2Loader()
  {
    URIConverter.URI_MAP.put(URI.createURI("pathmap:/UML2_LIBRARIES/"),
        URI.createFileURI("C:/eclipse/3.0M8/plugins/org.eclipse.uml2.resources_1.0.0/libraries/"));
    URIConverter.URI_MAP.put(URI.createURI("pathmap:/UML2_METAMODELS/"),
        URI.createFileURI("C:/eclipse/3.0M8/plugins/org.eclipse.uml2.resources_1.0.0/metamodels/"));
    URIConverter.URI_MAP.put(URI.createURI("pathmap:/UML2_PROFILES/"),
        URI.createFileURI("C:/eclipse/3.0M8/plugins/org.eclipse.uml2.resources_1.0.0/profiles/"));
    
    EPackage.Registry.INSTANCE.put("http://www.eclipse.org/uml2/1.0.0/UML", UML2Package.eINSTANCE);
    EPackage.Registry.INSTANCE.put("http:///org/eclipse/uml2.ecore", UML2Package.eINSTANCE); 
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("uml2", new UML2ResourceFactoryImpl()); 
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl()); 
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
  }
}
