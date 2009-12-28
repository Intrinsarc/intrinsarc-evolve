package com.hopstepjump.uml2commands;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

import com.hopstepjump.idraw.foundation.*;

public class UML2SetCommand extends AbstractCommand
{
  private Element subject;
  private EStructuralFeature field;
  private Object value;
  private Object oldValue;
  
  public UML2SetCommand(Element subject, EStructuralFeature field, Object value, String execute, String unExecute)
  {
    super(execute, unExecute);
    this.subject = subject;
    this.field = field;
    this.value = value;
  }  
  
  public void execute(boolean isTop)
  {
    oldValue = subject.eGet(field);
    subject.eSet(field, value);
  }

  public void unExecute()
  {
    subject.eSet(field, oldValue);
  }
}