// Copyright 2002, FreeHEP.
package org.freehep.util;

import java.util.*;

/**
 * Unsynchronized version of a Stack.
 * 
 * @author Mark Donszelmann
 */
public class FastStack extends ArrayList
{

  public FastStack()
  {
    this(10);
  }

  public FastStack(int initialCapacity)
  {
    super(initialCapacity);
  }

  public Object push(Object item)
  {
    add(item);
    return item;
  }

  public Object pop()
  {
    Object obj = peek();
    int len = size();

    remove(len - 1);

    return obj;
  }

  public Object peek()
  {
    int len = size();
    if (len == 0)
      throw new EmptyStackException();
    return get(len - 1);
  }
}
