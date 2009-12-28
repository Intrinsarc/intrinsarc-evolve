package com.hopstepjump.jumble.umldiagrams.base;

import org.eclipse.uml2.*;

public class SetTextPayload
{
  private Element subject;
  private Object memento;

  public SetTextPayload(Element subject, Object memento)
  {
    this.subject = subject;
    this.memento = memento;
  }
  
  public Element getSubject()
  {
    return subject;
  }
  
  public Object getMemento()
  {
    return memento;
  }
}
