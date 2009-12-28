// Copyright 2001, FreeHEP.
package org.freehep.util.io;


/**
 * Constants for the ASCII85 encoding.
 * 
 * @author Mark Donszelmann
 * @version $Id: ASCII85.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public interface ASCII85
{

  public final static int MAX_CHARS_PER_LINE = 80;

  public static long a85p1 = 85;
  public static long a85p2 = a85p1 * a85p1;
  public static long a85p3 = a85p2 * a85p1;
  public static long a85p4 = a85p3 * a85p1;

}
