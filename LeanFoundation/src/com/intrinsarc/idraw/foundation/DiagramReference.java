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

public class DiagramReference implements Serializable
{
  private Object id;

  public DiagramReference(Object id)
  {
    this.id = id;
  }

  public Object getId()
  {
    return id;
  }

  public String toString()
  {
    return "DiagramReference(" + id + ")";
  }

  public int hashCode()
  {
    return id.hashCode();
  }

  public boolean equals(Object other)
  {
    if (!(other instanceof DiagramReference))
      return false;
    return id.equals(((DiagramReference) other).id);
  }
}