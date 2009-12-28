package com.hopstepjump.jumble.management;

import java.lang.management.*;
import java.util.*;

import javax.management.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.jumble.packageview.actions.*;
import com.hopstepjump.jumble.packageview.base.*;
import com.hopstepjump.repositorybase.*;

public class GUIManager implements GUIManagerMBean
{
  public GUIManager()
  {
  }
  
  public void register()
  {
    try
    {
      MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
      // register the mbean
      java.lang.Class cls = this.getClass();
      ObjectName name = new ObjectName(cls.getPackage().getName() + ":type=" + cls.getSimpleName()); 
      mbs.registerMBean(this, name);
    }
    catch (JMException ex)
    {
      System.err.println("Got an exception when registering the mbean for ApplicationWindow");
      ex.printStackTrace();
    }
  }

  public void openClipboard(boolean maximise, boolean closeable)
  {
    GlobalPackageViewRegistry.activeRegistry.openClipboard(PackageViewRegistryGem.CLIPBOARD_TYPE, maximise, closeable, null, null);
  }

  public void openDiagram(String packageName, boolean maximise, boolean closeable)
  {
    // translate the path to a package
    Collection<NamedElement> elements = GlobalSubjectRepository.repository.findElements(packageName, PackageImpl.class, true); 
    if (elements.size() == 0)
      throw new IllegalArgumentException("Cannot find package " + packageName);
    if (elements.size() > 1)
      throw new IllegalArgumentException("Found more than 1 package " + packageName);
    
    GlobalPackageViewRegistry.activeRegistry.open(
        (Package) elements.iterator().next(), closeable, false, null, null, true);
  }
}
