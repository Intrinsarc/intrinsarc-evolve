package com.intrinsarc.idraw.diagramsupport;


public class DiagramRecreationException extends Exception
{
  public DiagramRecreationException(String message)
  {
    super(message);
  }

  public DiagramRecreationException(Exception ex)
  {
    super(ex);
  }
}
