/*
 * ApplicationListener.java
 *
 * Created on April 5, 2001, 12:15 PM
 */

package org.freehep.application;
import java.util.*;

/**
 * Listen for ApplicationEvents
 * 
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: ApplicationListener.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public interface ApplicationListener extends EventListener
{
  void initializationComplete(ApplicationEvent e);
  void aboutToExit(ApplicationEvent e);
}
