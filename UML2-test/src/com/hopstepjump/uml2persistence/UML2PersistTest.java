package com.hopstepjump.uml2persistence;

import java.io.*;
import java.util.*;

import javax.jdo.*;

import org.eclipse.emf.common.notify.*;
import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.ecore.resource.impl.*;
import org.eclipse.emf.ecore.xmi.impl.*;
import org.eclipse.emf.edit.domain.*;
import org.eclipse.emf.edit.provider.*;
import org.eclipse.emf.edit.provider.resource.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Class;
import org.eclipse.uml2.Package;

import com.hopstepjump.notifications.*;

class Observer implements Adapter
{
  public void notifyChanged(Notification arg)
  {
//    System.out.println("  $$$$ got an adaptation... :" + arg);
  }

  public Notifier getTarget()
  {
    return null;
  }

  public void setTarget(Notifier arg0)
  {
  }

  public boolean isAdapterForType(Object arg0)
  {
    return false;
  }
  
}

public class UML2PersistTest
{
  private final static boolean CLIENT_SERVER = true;
  private PersistenceManager pm;

  public static void main(String[] args) throws IOException
  {
    new UML2PersistTest().go();
  }
  
  private void go() throws IOException
  {
    pm = setUpPersistenceManager();
    final EditingDomain domain = setUpEditingDomain();

    // add the observer
    
    // make a package with a class
    GlobalNotifier.getSingleton().addObserver(new Observer());
    EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
    start();
    final UML2Factory factory = UML2Factory.eINSTANCE;
    Package pkg = factory.createPackage();
//    pkg.eAdapters().add(new Observer());
    pkg.setName("TopLevel");
    Class cls = pkg.createOwnedClass("TopClass", false);
    pkg.setJ_deleted(1);
    pm.makePersistent(pkg);
    middle();
    
    // replace the class, using a transaction
    Class cls2 = pkg.createOwnedClass("ReplacementTopClass", true);
    
    // add a comment to the package
    Comment pComment = pkg.createOwnedComment();
    pComment.setBody("This is a package");
    
    middle();
    
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
    
//    Association assoc = factory.createAssociation();
//    assoc.setName("association1");
//    Property prop1 = factory.createProperty();
//    prop1.setType(cls3);
//    Property prop2 = factory.createProperty();
//    prop2.setType(cls2);
//    assoc.getMemberEnds().add(prop1);
//    assoc.getMemberEnds().add(prop2);
//    cls2.getOwnedConnectors().add(assoc);
    
    
//    // make lots of classes to see the size of the db
//    System.out.println("$$ started making lots of classes");
//    long start = System.currentTimeMillis();
//    for (int lp = 0; lp < 200; lp++)
//    {
//      middle();
//      Package npkg = factory.createPackage();
//      npkg.setName("TopLevel" + lp);
//      pm.makePersistent(npkg);
//      
//      for (int lp2 = 0; lp2 < 200; lp2++)
//      {
//        // replace the class, using a transaction
//        Class ncls = factory.createClass();
//        ncls.setName("TopClass" + (lp * 200 + lp2));
//        npkg.settable_getOwnedMembers().add(ncls);        
//      }
//    }
//    long end = System.currentTimeMillis();
//    System.out.println("$$ took " + (end - start) + "ms to creat 40000 classes");
    
    // create a stereotype, and associate it with an eclass
    Profile profile = (Profile) pkg.createChildPackages(UML2Package.eINSTANCE.getProfile());
    Stereotype ffactory = profile.createOwnedStereotype("Factory", false);
    ffactory.setExtendsMetaModelElement("uml2.Class");
    Property fattr = ffactory.createOwnedAttribute();
    fattr.setName("attr");
    
    Class fcls = pkg.createOwnedClass("MyFactory", false);
    System.out.println("$$ fcls' eclass name = " + fcls.eClass().getName());
    fcls.getAppliedBasicStereotypes().add(ffactory);
    
    // make some values
    AppliedBasicStereotypeValue value = fcls.createAppliedBasicStereotypeValues();
    value.setProperty(fattr);
    LiteralInteger lint = (LiteralInteger) value.createValue(UML2Package.eINSTANCE.getLiteralInteger());
    lint.setValue(10);
    end();

    // persist to a an xml file in xmi format
    Resource resource = createXMIResource("test.uml2");
    resource.getContents().add(pkg);
    resource.save(null);
    

    System.out.println("$$ successfully done");
  }


  private void end()
  {
    pm.currentTransaction().commit();
  }

  private void middle()
  {
    end();
    start();
  }

  private void start()
  {
    pm.currentTransaction().begin();
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
  private PersistenceManager setUpPersistenceManager()
  {
    // Obtain a database connection:
    Properties properties = new Properties();
    properties.setProperty("javax.jdo.PersistenceManagerFactoryClass", "com.objectdb.jdo.PMF");
    if (CLIENT_SERVER)
    {
      properties.setProperty("javax.jdo.option.ConnectionURL", "objectdb://localhost/uml2.odb");
      properties.setProperty("javax.jdo.option.ConnectionUserName", "admin");
      properties.setProperty("javax.jdo.option.ConnectionPassword", "admin");
    }
    else
    {
      properties.setProperty("javax.jdo.option.ConnectionURL", "uml2.odb");
    }

    PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(properties, JDOHelper.class.getClassLoader());
    PersistenceManager pm = pmf.getPersistenceManager();
    return pm;
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
