package com.hopstepjump.notifications;

import java.util.*;

import org.eclipse.emf.common.notify.*;

public class GlobalNotifier implements Adapter
{
  private static GlobalNotifier globalNotifier = new GlobalNotifier();
  private Set<Adapter> observers = new HashSet<Adapter>();

  public static GlobalNotifier getSingleton()
  {
    return globalNotifier;
  }
  
  private GlobalNotifier()
  {
  }
  
  public void addObserver(Adapter observer)
  {
    observers.add(observer);
  }
  
  public void removeObserver(Adapter observer)
  {
    observers.remove(observer);
  }
  
  public void notifyChanged(Notification notification)
  {
    for (Adapter observer : observers)
      observer.notifyChanged(notification);
  }

  public Notifier getTarget()
  {
    return null;
  }

  public void setTarget(Notifier notifier)
  {
  }

  public boolean isAdapterForType(Object arg0)
  {
    return false;
  }
}
