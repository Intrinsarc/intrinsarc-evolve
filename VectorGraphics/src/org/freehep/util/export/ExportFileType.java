// Copyright 2003, FreeHEP.
package org.freehep.util.export;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import org.freehep.swing.*;

/**
 * Objects which extend this class provide enough functionality to provide an
 * output file type for the ExportDialog.
 * 
 * @author Charles Loomis
 * @author Mark Donszelmann
 * @version $Id: ExportFileType.java,v 1.1 2009-03-04 22:46:59 andrew Exp $
 */
public abstract class ExportFileType implements Comparable
{

  private static ClassLoader loader;

  /**
   * Returns a short description of the format
   */
  public abstract String getDescription();

  /**
   * Returns an array of possible extensions for the format, the first of which
   * is the preferred extension
   */
  public abstract String[] getExtensions();

  /**
   * Return the MIME-type(s) for this format, the first of which is the
   * preferred MIME type.
   */
  public abstract String[] getMIMETypes();

  /**
   * Writes the given component out in the format of this file type.
   */
  public abstract void exportToFile(OutputStream os, Component printTarget, Component parent, Properties properties,
      String creator) throws java.io.IOException;

  /**
   * Writes the given component out in the format of this file type.
   */
  public abstract void exportToFile(File file, Component printTarget, Component parent, Properties properties,
      String creator) throws java.io.IOException;

  /**
   * Writes the all given components out in the format of this file type.
   */
  public abstract void exportToFile(OutputStream os, Component[] printTarget, Component parent, Properties properties,
      String creator) throws java.io.IOException;

  /**
   * Writes the all given components out in the format of this file type.
   */
  public abstract void exportToFile(File file, Component[] printTarget, Component parent, Properties properties,
      String creator) throws java.io.IOException;


  /**
   * Compares to other exportfiletype in alphabetical order on the description
   * string.
   */
  public int compareTo(Object o)
  {
    ExportFileType type = (ExportFileType) o;
    return getDescription().compareTo(type.getDescription());
  }

  /**
   * Returns true if this format has extra options.
   */
  public boolean hasOptionPanel()
  {
    return false;
  }

  /**
   * Returns a panel which allows to user to set options associated with this
   * particular output format.
   */
  public JPanel createOptionPanel(Properties options)
  {
    return null;
  }

  /**
   * Sets any changed options from the optionPanel to the properties object.
   * 
   * @return true if any options were set.
   */
  public boolean applyChangedOptions(JPanel optionPanel, Properties options)
  {
    return false;
  }

  /**
   * Returns a file filter which selects only those files which correspond to a
   * particular file type.
   */
  public FileFilter getFileFilter()
  {
    return new ExtensionFileFilter(getExtensions(), getDescription());
  }

  /**
   * Gives the accessory the chance to change the output filename. In
   * particular, to change the extension to match the output file format.
   */
  public File adjustFilename(File file, Properties properties)
  {
    return adjustExtension(file, getExtensions()[0], getExtensions());
  }

  /**
   * This method returns true if the given file has an extension which can be
   * handled by this file type.
   */
  public boolean fileHasValidExtension(File file)
  {
    return checkExtension(file, getExtensions());
  }

  /**
   * Returns true if this ExportFileType can handle multipage output. The
   * default implementation return false;
   */
  public boolean isMultipageCapable()
  {
    return false;
  }

  /**
   * Sets the classloader to be used for loading ExportFileTypes
   */
  public static void setClassLoader(ClassLoader loader)
  {
    ExportFileType.loader = loader;
  }

  /**
   * Return all registered ExportFileTypes
   */
  public static List getExportFileTypes()
  {
    return getExportFileTypes(null);
  }

  /**
   * Return all registered ExportFileTypes for a certain format. Format may be
   * null, in which case all ExportFileTypes are returned.
   */
  public static List getExportFileTypes(String format)
  {
    return ExportFileTypeRegistry.getDefaultInstance(loader).get(format);
  }

  /**
   * A utility function that checks a file's extension.
   */
  public static boolean checkExtension(File file, String[] acceptableExtensions)
  {

    // Default is that the check failed.
    boolean match = false;

    if (file != null && acceptableExtensions != null)
    {

      // Get the extension.
      String originalName = file.getName();
      String extension = "";
      int dotIndex = originalName.lastIndexOf('.');
      if (dotIndex > 0 && dotIndex < (originalName.length() - 1))
      {
        extension = originalName.substring(dotIndex + 1);
      }
      else
      {
        extension = "";
      }

      // Check to see if the extension is one of the acceptable
      // extensions.
      for (int i = 0; i < acceptableExtensions.length; i++)
      {
        if (acceptableExtensions[i].equals(extension))
        {
          match = true;
        }
      }
    }
    return match;
  }

  /**
   * Change the extension of a file if it is not of the appropriate type.
   */
  public static File adjustExtension(File file, String preferredExtension, String[] acceptableExtensions)
  {

    File returnValue = file;

    if (file != null)
    {

      // Get first the filename without the extension and the
      // extension itself.
      String originalParent = file.getParent();
      String originalName = file.getName();
      String mainName = "";
      String extension = "";

      if (originalParent != null)
      {
        mainName = originalParent + File.separator;
      }

      int dotIndex = originalName.lastIndexOf('.');
      if (dotIndex > 0 && dotIndex < (originalName.length() - 1))
      {
        mainName += originalName.substring(0, dotIndex);
        extension = originalName.substring(dotIndex + 1);
      }
      else
      {
        mainName += originalName;
        extension = "";
      }

      // Check to see if the extension is one of the acceptable
      // extensions.
      boolean match = false;
      if (acceptableExtensions != null)
      {
        for (int i = 0; i < acceptableExtensions.length; i++)
        {
          if (acceptableExtensions[i].equals(extension))
          {
            match = true;
          }
        }
      }

      // If the extension isn't an acceptable one, then change
      // the extension to the preferred one.
      if (!match)
      {
        String newName = mainName + "." + preferredExtension;
        File newFile = new File(newName);
        returnValue = newFile;
      }
    }
    return returnValue;
  }
}
