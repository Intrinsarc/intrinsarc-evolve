// Copyright 2003, FreeHEP.
package org.freehep.util.export;

import java.util.*;

import javax.imageio.spi.*;

import org.freehep.util.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: ExportFileTypeRegistry.java,v 1.1 2006-07-03 13:33:31 amcveigh
 *          Exp $
 */
class ExportFileTypeRegistry
{

  private static ExportFileTypeRegistry registry;
  private ServiceRegistry service;
  private static ClassLoader loader;

  private static final Collection categories = new ArrayList(2);
  static
  {
    categories.add(ExportFileType.class);
    categories.add(RegisterableService.class);
  }

  private ExportFileTypeRegistry()
  {
    service = new ServiceRegistry(categories.iterator());
    addStandardExportFileTypes();
    addApplicationClasspathExportFileTypes();
  }

  static ExportFileTypeRegistry getDefaultInstance(ClassLoader loader)
  {
    if (loader != null)
    {
      if (ExportFileTypeRegistry.loader != null)
        throw new RuntimeException(ExportFileTypeRegistry.class.getName()
            + ": Different classloader was already used in getDefaultInstance");
      ExportFileTypeRegistry.loader = loader;
    }

    if (registry == null)
    {
      registry = new ExportFileTypeRegistry();
    }
    return registry;
  }

  /**
   * Returns a list of all registered ExportFileTypes in the order in which they
   * are found in jar files on the classpath.
   */
  public List get()
  {
    return get(null);
  }

  /**
   * Returns a list of all registered ExportFileTypes in the order in which they
   * are found in jar files on the classpath. The list is for a particular
   * format, or all if format is null.
   */
  public List get(String format)
  {
    List export = new ArrayList();
    Iterator iterator = service.getServiceProviders(ExportFileType.class, true);
    while (iterator.hasNext())
    {
      ExportFileType type = (ExportFileType) iterator.next();
      if (format == null)
      {
        export.add(type);
      }
      else
      {
        String[] ext = type.getExtensions();
        for (int i = 0; i < ext.length; i++)
        {
          if (ext[i].equals(format))
          {
            export.add(type);
            break;
          }
        }
      }
    }
    return export;
  }

  private void addStandardExportFileTypes()
  {
    // hardwired standard
    // service.registerServiceProvider(new PDFExportFileType());
  }

  private void addApplicationClasspathExportFileTypes()
  {
    /*
     * ClassLoader classLoader = (loader != null) ? loader :
     * Thread.currentThread().getContextClassLoader();
     * 
     * Iterator iterator = categories.iterator(); while (iterator.hasNext()) {
     * Class category = (Class)iterator.next(); Iterator providers =
     * Service.providers(category, classLoader).iterator(); Object previous =
     * null; while (providers.hasNext()) { Object current = providers.next();
     * service.registerServiceProvider(current); if (previous != null) {
     * service.setOrdering(category, previous, current); } previous = current; } }
     */
  }

  public static void main(String[] args)
  {
    ExportFileTypeRegistry r = ExportFileTypeRegistry.getDefaultInstance(null);
    System.out.println("All ExportFileTypes");
    Iterator providers = r.get().iterator();
    while (providers.hasNext())
    {
      System.out.println("   " + providers.next());
    }
    System.out.println("PDF ExportFileTypes");
    providers = r.get("pdf").iterator();
    while (providers.hasNext())
    {
      System.out.println("   " + providers.next());
    }
  }
}
