package com.intrinsarc.idraw.foundation;

import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class FigureReference implements Serializable
{
  private DiagramReference diagramReference;
  private String id;

  public FigureReference(DiagramReference diagramReference, String id)
  {
    this.diagramReference = diagramReference;
    this.id = id;
  }

  public FigureReference(DiagramFacet diagramFacet, String id)
  {
    this.diagramReference = diagramFacet.getDiagramReference();
    this.id = id;
  }

  public DiagramReference getDiagramReference()
  {
    return diagramReference;
  }

  public String getId()
  {
    return id;
  }

  public String toString()
  {
    return "FigureReference(" + diagramReference + ", " + id + ")";
  }

  public int hashCode()
  {
    return diagramReference.hashCode() ^ id.hashCode();
  }

  public boolean equals(Object other)
  {
    if (!(other instanceof FigureReference))
      return false;
      
    FigureReference otherReference = (FigureReference) other;
    return id.equals(otherReference.id) && diagramReference.equals(otherReference.diagramReference);
  }
}