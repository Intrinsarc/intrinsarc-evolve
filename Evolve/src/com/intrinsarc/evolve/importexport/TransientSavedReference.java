package com.intrinsarc.evolve.importexport;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

public class TransientSavedReference
{
  private Element from;
  private EReference referred;
  private Element to;

  public TransientSavedReference(Element from, EReference referred, Element to)
  {
    this.from = from;
    this.referred = referred;
    this.to = to;
  }

  public Element getFrom()
  {
    return from;
  }

  public EReference getReferred()
  {
    return referred;
  }

  public Element getTo()
  {
    return to;
  }  
}


