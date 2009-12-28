package com.hopstepjump.uml2commands;

import java.util.*;

import javax.jdo.*;

import org.eclipse.uml2.*;

import com.hopstepjump.idraw.foundation.*;

public class UML2CreateCommand extends AbstractCommand
{
  private List<Element> elements = new ArrayList<Element>();
  private boolean initialCommitOk;
  
  public UML2CreateCommand(List<Element> elements, String execute, String unExecute)
  {
    super(execute, unExecute);
    this.elements = elements;
  }  
  
  public UML2CreateCommand(Element element, String execute, String unExecute)
  {
    super(execute, unExecute);
    elements = new ArrayList<Element>();
    elements.add(element);
  }  
  
  public void execute(boolean isTop)
  {
    if (!initialCommitOk)
    {
      // register each object as persistent
      PersistenceManager pm = GlobalPersistenceManager.getSingleton().getPersistenceManager();
      
      for (Element element : elements)
        pm.makePersistent(element);
    }
    else
      DeleteHelper.bumpDeleted(elements, -1);
  }
  
  public void unExecute()
  {
    // if we haven't committed can ignore, as rollback handles making non-persistent
    DeleteHelper.bumpDeleted(elements, 1);
  }
  
  public void afterCommit()
  {
    // once we have stored objects in the database, there's no deleting them
    // instead, we just toggle the deleted flag...
    initialCommitOk = true;
  }
}