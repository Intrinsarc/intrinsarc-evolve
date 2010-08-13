package com.intrinsarc.multiuser;

import java.io.*;
import java.util.*;

import javax.jdo.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.ecore.resource.impl.*;
import org.eclipse.emf.ecore.xmi.impl.*;
import org.eclipse.emf.edit.domain.*;
import org.eclipse.emf.edit.provider.*;
import org.eclipse.emf.edit.provider.resource.*;


public class UML2MultiUserBase
{
  protected final static boolean CLIENT_SERVER = true;
  protected PersistenceManager pm;

  protected void end()
  {
    pm.currentTransaction().commit();
  }

  protected void middle()
  {
    end();
    start();
  }

  protected void start()
  {
    pm.currentTransaction().begin();
  }

  /**
   * @return
   */
  protected EditingDomain setUpEditingDomain()
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
  protected PersistenceManager setUpPersistenceManager()
  {
    // Obtain a database connection:
    Properties properties = new Properties();
    properties.setProperty("javax.jdo.option.Optimistic", "true");
    properties.setProperty("javax.jdo.option.RetainValues", "true");
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
  protected Resource createXMIResource(String fileName)
  {
    // create the resource where Foo will live
    ResourceSet resourceSet = new ResourceSetImpl();
    URI fileURI = URI.createFileURI(new File(fileName).getAbsolutePath());
    Resource resource = resourceSet.createResource(fileURI);
    return resource;
  }  
  
  protected String waitForEnter(String message)
  {
    System.out.println("$$ press enter to " + message);
    try
    {
      return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }
    catch (IOException e)
    {
    }
    return null;
  }
}
