package com.hopstepjump.uml2commands;

import java.util.*;

import org.eclipse.uml2.*;

import com.hopstepjump.idraw.foundation.*;

/**
 * the delete command assumes that the objects have been persisted in a separate transaction
 * @author Andrew
 */
public class UML2DeleteCommand extends AbstractCommand
{
  private List<Element> elements = new ArrayList<Element>();
  
  public UML2DeleteCommand(List<Element> elements, String execute, String unExecute)
  {
    super(execute, unExecute);
    this.elements = elements;
  }  
  
  public UML2DeleteCommand(Element element, String execute, String unExecute)
  {
    super(execute, unExecute);
    elements = new ArrayList<Element>();
    elements.add(element);
  }  
  
  public void execute(boolean isTop)
  {
    // executing increments the deleted flag, definitely killing the object
    DeleteHelper.bumpDeleted(elements, 1);
  }

  public void unExecute()
  {
    // undoing decrements the deleted count, possibly restoring an object to life
    DeleteHelper.bumpDeleted(elements, -1);
  }
  
  public void afterCommit()
  {
  }
}
