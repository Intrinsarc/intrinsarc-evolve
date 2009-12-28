/*
 * FileAccess.java
 * Created on February 22, 2001, 11:14 AM
 */

package org.freehep.application.services;
import java.io.*;

/**
 * Encapsulates access to a File in a way which will work with unsigned java web
 * start apps. This interface can also be used by applications.
 * 
 * @author tonyj
 * @version $Id: FileAccess.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */
public interface FileAccess
{
  /**
   * Returns whether the file can be read.
   */
  boolean canRead() throws IOException;
  /**
   * Returns whether the file can be written to.
   */
  boolean canWrite() throws IOException;
  /**
   * Gets an InputStream from the file.
   */
  InputStream getInputStream() throws IOException;
  /**
   * Gets an OutputStream to the file.
   */
  OutputStream getOutputStream(boolean append) throws IOException;
  /**
   * Gets the file name as a string
   */
  String getName() throws IOException;
  /**
   * Gets the length of the file
   */
  long getLength() throws IOException;
  /**
   * Gets the maximum length of this file. If the length is not limited returns
   * a very large number
   */
  long getMaxLength() throws IOException;
  /**
   * Sets the maximum file length for the file.
   */
  long setMaxLength(long length) throws IOException;
  /**
   * Applications that need direct access to the underlying File can call this
   * method, but it breaks the JNLP encapsualtion and will throw a
   * SecurityException if used from an unsigned JNLP application.
   */
  File getFile() throws IOException, SecurityException;
}
