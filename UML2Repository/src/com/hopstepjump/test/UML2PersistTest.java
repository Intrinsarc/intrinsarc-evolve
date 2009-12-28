package com.hopstepjump.test;

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
import org.eclipse.uml2.Package;

import com.hopstepjump.notifications.*;
import com.hopstepjump.uml2commands.*;

class Observer implements Adapter
{
  public void notifyChanged(Notification arg)
  {
    System.out.println("  $$$$ got an adaptation... :" + arg);
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

  public static void main(String[] args)
  {
    new UML2PersistTest().go();
  }
  
  private void go()
  {
    pm = GlobalPersistenceManager.getSingleton().getPersistenceManager();
    
    // add the observer
    
    // make a package with a class
    GlobalNotifier.getSingleton().addObserver(new Observer());
    
    final UML2Factory factory = UML2Factory.eINSTANCE;
    Package pkg = factory.createPackage();
    pkg.setName("Foo");
    
    start();
    UML2CreateCommand create = new UML2CreateCommand(pkg, "created", "removed");
    create.execute(false);
    middle();
    create.afterCommit();
    create.unExecute();
    middle();
    create.execute(false);
    end();
    
//    pkg.eAdapters().add(new Observer());
//    pkg.setName("TopLevel");
//    Class cls = factory.createClass();
//    cls.setName("TopClass");
//    pkg.settable_getOwnedMembers().add(cls);
//    pkg.setJ_deleted(true);
//    pm.makePersistent(pkg);
//    middle();
//    
//    // replace the class, using a transaction
//    Class cls2 = factory.createClass();
//    cls2.setName("ReplacementTopClass");
//    pkg.settable_getOwnedMembers().set(0, cls2);
//    
//    // add a comment to the package
//    Comment pComment = factory.createComment();
//    pComment.setBody("This is a package");
//    pkg.settable_getOwnedComments().add(pComment);
//    
//    middle();
//    
//    // add a comment to a class
//    Comment comment = factory.createComment();
//    comment.setBody("The rain in spain falls on the plain");
//    cls2.settable_getOwnedComments().add(comment);
//    
//    // create a new class and a generalisation relationship between them
//    Class cls3 = factory.createClass();
//    cls3.setName("SuperClass");
//    pkg.settable_getOwnedMembers().add(cls3);
//    Generalization gen = factory.createGeneralization();
//    gen.setGeneral(cls3);
//    gen.setIsSubstitutable(true);
//    cls2.settable_getGeneralizations().add(gen);
//    
//    // associate a diagram with the package
//    J_Diagram diagram = factory.createJ_Diagram();
//    diagram.setName("Diagram1");
//    J_Figure figure = factory.createJ_Figure();
//    figure.setId("Figure");
//    diagram.settable_getFigures().add(figure);
//    figure.setSubject(pkg);
//    pkg.setJ_diagram(diagram);
//    pm.makePersistent(diagram);
//    
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
//    end();
    
    // persist to a an xml file in xmi format
//    Resource resource = createXMIResource("test.uml2");
//    resource.getContents().add(pkg);
//    resource.save(null);
    
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
  private Resource createXMIResource(String fileName)
  {
    // create the resource where Foo will live
    ResourceSet resourceSet = new ResourceSetImpl();
    URI fileURI = URI.createFileURI(new File(fileName).getAbsolutePath());
    Resource resource = resourceSet.createResource(fileURI);
    return resource;
  }  
}
