package com.hopstepjump.uml2commands;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

import com.hopstepjump.idraw.foundation.*;

public class UML2RemoveCommand extends AbstractCommand
{
  private Element parent;
  private EStructuralFeature listField;
  private Element child;
  
  public UML2RemoveCommand(Element parent, EStructuralFeature listField, Element child, String execute, String unExecute)
  {
    super(execute, unExecute);
    this.parent = parent;
    this.listField = listField;
    this.child = child;
  }  
  
  public void execute(boolean isTop)
  {
    EList list = (EList) parent.eGet(listField);
    if (!list.remove(child))
      throw new IllegalStateException("Cannot find child " + child + " in listfield " + listField + " of parent " + parent);
  }

  public void unExecute()
  {
    EList list = (EList) parent.eGet(listField);
    list.add(child);
  }
}