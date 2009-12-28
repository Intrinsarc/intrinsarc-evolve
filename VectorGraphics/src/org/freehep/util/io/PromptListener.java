// Copyright 2002, FreeHEP.
package org.freehep.util.io;

import java.io.*;

/**
 * Listener to inform that Prompt of the PromptInputStream has been found.
 * 
 * @author Mark Donszelmann
 * @version $Id: PromptListener.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public interface PromptListener
{

  /**
   * Prompt was found, and can now be read.
   */
  public void promptFound(RoutedInputStream.Route route) throws IOException;
}
