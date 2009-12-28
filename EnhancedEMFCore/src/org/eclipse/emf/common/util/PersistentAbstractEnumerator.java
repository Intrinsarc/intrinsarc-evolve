/**
 * <copyright>
 *
 * Copyright (c) 2002-2004 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: PersistentAbstractEnumerator.java,v 1.1 2009-03-04 23:05:33 andrew Exp $
 */
package org.eclipse.emf.common.util;


/**
 * An extensible enumerator implementation.
 */
public class PersistentAbstractEnumerator implements Enumerator
{
  /**
   * The name of the enumerator.
   */
  private String name;

  /**
   * The <code>int</code> value of the enumerator.
   */
  private int value;

  /**
   * required for objectdb
   */
  public PersistentAbstractEnumerator()
  {
  }
    
  /**
   * Creates an initialized instance.
   * @param value the <code>int</code> value of the enumerator.
   * @param name the name of the enumerator.
   */
  public PersistentAbstractEnumerator(int value, String name)
  {
    this.name = name;
    this.value = value;
  }

  /**
   * Returns the name of the enumerator.
   * @return the name.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Returns the <code>int</code> value of the enumerator.
   * @return the value.
   */
  public int getValue()
  {
    return value;
  }

  /**
   * Returns the name of the enumerator.
   * @return the name.
   */
  public String toString()
  {
    return name;
  }
  
  public boolean equals(Object other)
  {
    if (other.getClass() != this.getClass())
      return false;
    PersistentAbstractEnumerator enumOther = (PersistentAbstractEnumerator) other;
    return enumOther.name.equals(name) && enumOther.value == value;
  }
}
