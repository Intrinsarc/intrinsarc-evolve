package com.hopstepjump.uml2persistence;

import java.io.*;
import java.util.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.ecore.resource.impl.*;
import org.eclipse.emf.ecore.xmi.impl.*;
import org.eclipse.emf.edit.domain.*;
import org.eclipse.emf.edit.provider.*;
import org.eclipse.emf.edit.provider.resource.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

public class SaveAndReadTest
{
  public static void main(String[] args) throws IOException
  {
    new SaveAndReadTest().go();
  }
  
  private void go() throws IOException
  {
    final EditingDomain domain = setUpEditingDomain();

    final UML2Factory factory = UML2Factory.eINSTANCE;
    Package pkg = factory.createPackage();
    pkg.setName("TopLevel");
    Class cls = pkg.createOwnedClass("TopClass", false);
    pkg.setJ_deleted(1);
    
    // replace the class, using a transaction
    Class cls2 = pkg.createOwnedClass("ReplacementTopClass", true);
    
    // add a comment to the package
    Comment pComment = pkg.createOwnedComment();
    pComment.setBody("This is a package");
    
    // add a comment to a class
    Comment comment = cls2.createOwnedComment();
    comment.setBody("The rain in spain falls on the plain");
    
    // create a new class and a generalisation relationship between them
    Class cls3 = pkg.createOwnedClass("SuperClass", false);
    Generalization gen = cls2.createGeneralization();
    gen.setGeneral(cls3);
    gen.setIsSubstitutable(true);
    
    // associate a diagram with the package
    J_DiagramHolder holder = pkg.createJ_diagramHolder();
    J_Diagram diagram = holder.createDiagram();
    diagram.setName("Diagram1");
    J_Figure figure = diagram.createFigures();
    figure.setId("Figure");
    figure.setSubject(pkg);
    
    // persist to a an xml file in xmi format
    Resource resource = createXMIResource("test.uml2");
    resource.getContents().add(pkg);
    resource.save(null);
    
    // now read it in
    readXMIResource("test.uml2");
    
    System.out.println("$$ successfully done");
  }

  /**
   * @return
   */
  private EditingDomain setUpEditingDomain()
  {
    // register the XMI factory
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
    
    // set up the factories and undo/redo infrastructure
    // Create an adapter factory that yields item providers.
    List factories = new ArrayList();
    factories.add(new ResourceItemProviderAdapterFactory());
    factories.add(new ReflectiveItemProviderAdapterFactory());
    ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(factories);
    EditingDomain domain = new AdapterFactoryEditingDomain(adapterFactory, null, new HashMap());
    return domain;
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
  
  /**
   * @return
   */
  private Resource readXMIResource(String fileName)
  {
    EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
    
    // for some bizarre reason, we need to create a dummy instance and set a var
    // before we are able to successfully get a package to register with the resource set...
    UML2Factory.eINSTANCE.createModel().setName("test");
    EPackage ePackage = UML2Factory.eINSTANCE.getEPackage();

    if (!new File(fileName).exists())
      return null;
    
    // create the resource where Foo will live
    ResourceSet resourceSet = new ResourceSetImpl();
    URI fileURI = URI.createFileURI(new File(fileName).getAbsolutePath());
    resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
    return resourceSet.getResource(fileURI, true);
  }
}
