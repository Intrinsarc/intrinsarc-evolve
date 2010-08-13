package com.intrinsarc.emflist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.InternalEObject;

/**
 * User: mathiasf Made by Gentleware Date: 03.01.2006
 */
public class PersistentEList extends PersistentEListClone
{
  protected List list = new ArrayList();
  protected InternalEObject owner;
  protected int featureID;

  public PersistentEList(ArrayList list, final Class dummy, final InternalEObject owner, final int featureID)
  {
    super();
    this.list = list;  // allows us to alias to the list...
    this.owner = owner;
    this.featureID = featureID;
  }

  public PersistentEList(final Class dummy, final InternalEObject owner, final int featureID)
  {
    super();
    this.owner = owner;
    this.featureID = featureID;
  }

  public PersistentEList(final Class dummy, final InternalEObject owner, final int featureID, final int dummy2)
  {
    super();
    this.owner = owner;
    this.featureID = featureID;
  }

  public PersistentEList(final InternalEObject owner, final int featureID)
  {
    super();
    this.owner = owner;
    this.featureID = featureID;
  }

  // added in haste
  public PersistentEList(final Class dummy, final InternalEObject owner, final int featureID, int[] is, int activity_node__activity)
  {
    super();
    this.owner = owner;
    this.featureID = featureID;
  }
  
  // added in haste
  public PersistentEList(final Class dummy, final InternalEObject owner, final int featureID, int[] is)
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
