// Copyright 2002, FreeHEP.
package org.freehep.util.io;

import java.io.*;

/**
 * The PromptInputStream reads from an inputstream until it reads any prompt for
 * which a listener is added. The listener is informed that the prompt is found.
 * The route which contains the prompt is supplied as a parameter to the
 * listener. Returning from the prompt listener without reading the route to its
 * end will allow the main stream to read it.
 * 
 * The implementation of this class is based on the RoutedInputStream.
 * 
 * @author Mark Donszelmann
 * @version $Id: PromptInputStream.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class PromptInputStream extends RoutedInputStream
{

  public PromptInputStream(InputStream input)
  {
    super(input);
  }

  public void addPromptListener(String prompt, PromptListener listener)
  {
    final PromptListener promptListener = listener;
    addRoute(prompt, prompt, new RouteListener()
    {
      public void routeFound(RoutedInputStream.Route input) throws IOException
      {
        promptListener.promptFound(input);
      }
    });
  }
}
