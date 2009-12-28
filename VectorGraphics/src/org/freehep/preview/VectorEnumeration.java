// Copyright 2000, CERN, Geneva, Switzerland and SLAC, Stanford, U.S.A.
package org.freehep.preview;

import java.util.*;

/**
 * Re-usable enumeration for java.util.Vector
 * 
 * @author Mark Donszelmann
 * @version $Id: VectorEnumeration.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */

public class VectorEnumeration implements Enumeration
{

  private Vector vector;
  private int index;

  public VectorEnumeration()
  {
    this(null);
  }

  public VectorEnumeration(Vector vector)
  {
    this.vector = vector;
    this.index = 0;
  }

  public void reset(Vector vector)
  {
    this.vector = vector;
    this.index = 0;
  }

  public boolean hasMoreElements()
  {
    if (vector != null)
    {
      return index < vector.size();
    }
    return false;
  }

  public Object nextElement()
  {
    if (vector != null)
    {
      Object object = vector.elementAt(index);
      index++;
      return object;
    }
    throw new NoSuchElementException();
  }
}
