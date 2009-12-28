package org.freehep.application.studio;

import java.util.*;

/**
 * 
 * @author tonyj
 */
public interface StudioListener extends EventListener
{
  void handleEvent(EventObject event);
}
