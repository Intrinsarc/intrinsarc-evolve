package com.hopstepjump.uml2commands;

import java.util.*;

import javax.jdo.*;

public class GlobalPersistenceManager
{
  private static boolean CLIENT_SERVER = true;
  public static GlobalPersistenceManager singleton = new GlobalPersistenceManager();
  private boolean notUsingObjectdb;
  private PersistenceManager pm;
  
  private GlobalPersistenceManager()
  {
    notUsingObjectdb = "true".equals(System.getProperty("NotUsingObjectdb")); 
    if (!notUsingObjectdb)
      setUpPersistenceManager();
      
  }
  
  public static GlobalPersistenceManager getSingleton()
  {
    return singleton;
  }
  
  public PersistenceManager getPersistenceManager()
  {
    return pm;
  }
  
  public boolean notUsingObjectdb()
  {
    return notUsingObjectdb;
  }
  
  private void setUpPersistenceManager()
  {
    // Obtain a database connection:
    Properties properties = new Properties();
    properties.setProperty("javax.jdo.PersistenceManagerFactoryClass", "com.objectdb.jdo.PMF");
    properties.setProperty("javax.jdo.option.RestoreValues", "true");
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
    pm = pmf.getPersistenceManager();
  }
}
