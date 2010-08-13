package com.intrinsarc.emflist;

import java.util.*;

import org.eclipse.emf.ecore.*;

public class UnmodifiableEList extends PersistentEListClone
{
  protected List list = Collections.EMPTY_LIST;
  protected InternalEObject owner;
  protected int featureID;

  public UnmodifiableEList(final Class dummy, final InternalEObject owner, final int featureID)
  {
    super();
    this.owner = owner;
    this.featureID = featureID;
  }

  public UnmodifiableEList(final Class dummy, final InternalEObject owner, final int featureID, final int dummy2)
  {
    super();
    this.owner = owner;
    this.featureID = featureID;
  }

  public UnmodifiableEList(final InternalEObject owner, final int featureID)
  {
    super();
    this.owner = owner;
    this.featureID = featureID;
  }

  // added in haste
  public UnmodifiableEList(final Class dummy, final InternalEObject owner, final int featureID, int[] is, int activity_node__activity)
  {
    super();
    this.owner = owner;
    this.featureID = featureID;
  }
  
  // added in haste
  public UnmodifiableEList(final Class dummy, final InternalEObject owner, final int featureID, int[] is)
  {
    super();
    this.owner = owner;
    this.featureID = featureID;
  }
  
  protected List delegateList()
  {
    return list;
  }

  public int getFeatureID()
  {
    return featureID;
  }


  protected InternalEObject getOwner()
  {
    return owner;
  }  
}
