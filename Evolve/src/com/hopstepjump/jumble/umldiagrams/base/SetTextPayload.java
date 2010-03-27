package com.hopstepjump.jumble.umldiagrams.base;

import org.eclipse.uml2.*;

public class SetTextPayload
{
  private Element subject;

  public SetTextPayload(Element subject)
  {
    this.subject = subject;
  }
  
  public Element getSubject()
  {
    return subject;
  }
}
