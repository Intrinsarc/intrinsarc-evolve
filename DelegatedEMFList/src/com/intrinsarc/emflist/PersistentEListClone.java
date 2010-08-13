package com.intrinsarc.emflist;

import org.eclipse.emf.common.notify.impl.DelegatingNotifyingListImpl;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.DelegatingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.DelegatingEcoreEList;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * User: mathiasf Made by Gentleware Copied and forked to remove final field
 * Date: 05.01.2006
 */
public abstract class PersistentEListClone extends DelegatingNotifyingListImpl implements InternalEList.Unsettable, EStructuralFeature.Setting
{
  public static abstract class Unsettable extends DelegatingEcoreEList
  {
    protected boolean isSet;

    public Unsettable(InternalEObject owner)
    {
      super(owner);
    }

    protected void didChange()
    {
      isSet = true;
    }

    public boolean isSet()
    {
      return isSet;
    }

    public void unset()
    {
      super.unset();
      if (isNotificationRequired())
      {
        boolean oldIsSet = isSet;
        isSet = false;
        owner.eNotify(createNotification(Notification.UNSET, oldIsSet, false));
      }
      else
      {
        isSet = false;
      }
    }
  }

  protected boolean canContainNull()
  {
    EClassifier eClassifier = getFeatureType();
    if (eClassifier instanceof EDataType)
    {
      if (eClassifier instanceof EEnum)
      {
        return false;
      }
      else
      {
        return !eClassifier.getInstanceClass().isPrimitive();
      }
    }
    else
    {
      return false;
    }
  }

  protected boolean isUnique()
  {
    return getEStructuralFeature().isUnique();
  }

  protected boolean hasInverse()
  {
    EStructuralFeature eStructuralFeature = getEStructuralFeature();
    if (eStructuralFeature instanceof EReference)
    {
      EReference eReference = (EReference) eStructuralFeature;
      return eReference.isContainment() || ((EReference) eStructuralFeature).getEOpposite() != null;
    }
    else
    {
      return false;
    }
  }

  protected Object validate(int index, Object object)
  {
    super.validate(index, object);
    if (object != null && !getFeatureType().isInstance(object))
    {
      throw new ArrayStoreException();
    }
    return object;
  }

  public Object getNotifier()
  {
    return getOwner();
  }

  public Object getFeature()
  {
    return getEStructuralFeature();
  }

  public int getFeatureID()
  {
    return getEStructuralFeature().getFeatureID();
  }

  public EStructuralFeature getEStructuralFeature()
  {
    return getOwner().eClass().getEStructuralFeature(getFeatureID());
  }

  protected abstract InternalEObject getOwner();

  protected EClassifier getFeatureType()
  {
    return getEStructuralFeature().getEType();
  }

  protected EReference getInverseEReference()
  {
    return ((EReference) getEStructuralFeature()).getEOpposite();
  }

  protected int getInverseFeatureID()
  {
    return getInverseEReference().getFeatureID();
  }

  protected Class getInverseFeatureClass()
  {
    return ((EClass) getInverseEReference().getEType()).getInstanceClass();
  }

  protected boolean hasManyInverse()
  {
    EStructuralFeature eStructuralFeature = getEStructuralFeature();
    if (eStructuralFeature instanceof EReference)
    {
      EReference eReference = (EReference) eStructuralFeature;
      EReference oppositeEReference = eReference.getEOpposite();
      return oppositeEReference != null && oppositeEReference.isMany();
    }
    else
    {
      return false;
    }
  }

  protected boolean hasNavigableInverse()
  {
    EStructuralFeature eStructuralFeature = getEStructuralFeature();
    if (eStructuralFeature instanceof EReference)
    {
      EReference eReference = (EReference) eStructuralFeature;
      EReference oppositeEReference = eReference.getEOpposite();
      return oppositeEReference != null;
    }
    else
    {
      return false;
    }
  }

  protected boolean isEObject()
  {
    return getFeatureType() instanceof EClass;
  }

  protected boolean isContainment()
  {
    EStructuralFeature eStructuralFeature = getEStructuralFeature();
    if (eStructuralFeature instanceof EReference)
    {
      EReference eReference = (EReference) eStructuralFeature;
      return eReference.isContainment();
    }
    else
    {
      return false;
    }
  }

  protected boolean hasProxies()
  {
    EStructuralFeature eStructuralFeature = getEStructuralFeature();
    if (eStructuralFeature instanceof EReference)
    {
      EReference eReference = (EReference) eStructuralFeature;
      return eReference.isResolveProxies();
    }
    else
    {
      return false;
    }
  }

  protected boolean hasInstanceClass()
  {
    return getFeatureType().getInstanceClass() != null;
  }

  protected Object resolve(int index, Object object)
  {
    if (isEObject() && hasProxies())
    {
      EObject resolved = resolveProxy((EObject) object);
      if (resolved != object)
      {
        Object oldObject = delegateGet(index);
        delegateSet(index, validate(index, resolved));
        didSet(index, resolved, oldObject);

        if (isNotificationRequired())
        {
          getOwner().eNotify(createNotification(Notification.RESOLVE, object, resolved, index, false));
        }

        return resolved;
      }
    }
    return object;
  }

  protected EObject resolveProxy(EObject eObject)
  {
    return eObject.eIsProxy() ? getOwner().eResolveProxy((InternalEObject) eObject) : eObject;
  }

  public Object[] toArray()
  {
    if (hasProxies())
    {
      for (int i = size() - 1; i >= 0; --i)
      {
        get(i);
      }
    }
    return super.toArray();
  }

  public Object[] toArray(Object array[])
  {
    if (hasProxies())
    {
      for (int i = size() - 1; i >= 0; --i)
      {
        get(i);
      }
    }
    return super.toArray(array);
  }

  protected NotificationImpl createNotification(int eventType, Object oldObject, Object newObject, int index,
      boolean wasSet)
  {
    return new ENotificationImpl(getOwner(), eventType, getFeatureID(), oldObject, newObject, index, wasSet);
  }

  protected NotificationImpl createNotification(int eventType, boolean oldValue, boolean newValue)
  {
    return new ENotificationImpl(getOwner(), eventType, getFeatureID(), oldValue, newValue);
  }

  /*
   * Javadoc copied from base class.
   */
  protected void dispatchNotification(Notification notification)
  {
    getOwner().eNotify(notification);
  }

  public Object basicGet(int index)
  {
    return super.basicGet(index);
  }

  public List basicList()
  {
    return super.basicList();
  }

  protected boolean isNotificationRequired()
  {
    return getOwner().eNotificationRequired();
  }

  public NotificationChain inverseAdd(Object object, NotificationChain notifications)
  {
    InternalEObject internalEObject = (InternalEObject) object;
    if (hasNavigableInverse())
    {
      if (!hasInstanceClass())
      {
        return internalEObject.eInverseAdd(getOwner(), internalEObject.eClass().getFeatureID(getInverseEReference()),
            null, notifications);
      }
      else
      {
        return internalEObject.eInverseAdd(getOwner(), getInverseFeatureID(), getInverseFeatureClass(), notifications);
      }
    }
    else
    {
      return internalEObject.eInverseAdd(getOwner(), InternalEObject.EOPPOSITE_FEATURE_BASE - getFeatureID(), null,
          notifications);
    }
  }

  public NotificationChain inverseRemove(Object object, NotificationChain notifications)
  {
    InternalEObject internalEObject = (InternalEObject) object;
    if (hasNavigableInverse())
    {
      if (!hasInstanceClass())
      {
        return internalEObject.eInverseRemove(getOwner(),
            internalEObject.eClass().getFeatureID(getInverseEReference()), null, notifications);
      }
      else
      {
        return internalEObject.eInverseRemove(getOwner(), getInverseFeatureID(), getInverseFeatureClass(),
            notifications);
      }
    }
    else
    {
      return internalEObject.eInverseRemove(getOwner(), InternalEObject.EOPPOSITE_FEATURE_BASE - getFeatureID(), null,
          notifications);
    }
  }

  /**
   * Resolve to compare objects but do not modify list
   */
  public boolean contains(Object object)
  {
    if (isEObject())
    {
      int size = size();
      if (size > 4)
      {
        if (isContainment())
        {
          if (!(object instanceof EObject))
            return false;
          InternalEObject eObject = (InternalEObject) object;
          return eObject.eContainer() == getOwner()
              && (hasNavigableInverse()
                  ? eObject.eContainerFeatureID() == getInverseFeatureID()
                  : InternalEObject.EOPPOSITE_FEATURE_BASE - eObject.eContainerFeatureID() == getFeatureID());
        }
        // We can also optimize single valued reverse.
        //
        else
          if (hasNavigableInverse() && !hasManyInverse())
          {
            return object instanceof EObject && ((EObject) object).eGet(getInverseEReference()) == getOwner();
          }
      }

      boolean result = super.contains(object);
      if (hasProxies() && !result)
      {
        for (int i = 0; i < size; ++i)
        {
          EObject eObject = resolveProxy((EObject) delegateGet(i));
          if (eObject == object)
          {
            return true;
          }
        }
      }
      return result;
    }
    else
    {
      return super.contains(object);
    }
  }

  public int indexOf(Object object)
  {
    int index = super.indexOf(object);
    if (index >= 0)
      return index;

    // EATM This might be better written as a single loop for the EObject case?
    //
    if (isEObject())
    {
      for (int i = 0, size = size(); i < size; ++i)
      {
        EObject eObject = resolveProxy((EObject) delegateGet(i));
        if (eObject == object)
        {
          return i;
        }
      }
    }

    return -1;
  }

  public int lastIndexOf(Object object)
  {
    int result = super.lastIndexOf(object);
    if (isEObject() && result == -1)
    {
      for (int i = size() - 1; i >= 0; --i)
      {
        EObject eObject = resolveProxy((EObject) delegateGet(i));
        if (eObject == object)
        {
          return i;
        }
      }
    }

    return result;
  }

  public Iterator basicIterator()
  {
    return super.basicIterator();
  }

  public ListIterator basicListIterator()
  {
    return super.basicListIterator();
  }

  public ListIterator basicListIterator(int index)
  {
    return super.basicListIterator(index);
  }

  public EObject getEObject()
  {
    return getOwner();
  }

  public Object get(boolean resolve)
  {
    return this;
  }

  public void set(Object newValue)
  {
    clear();
    addAll((List) newValue);
  }

  public boolean isSet()
  {
    return !isEmpty();
  }

  public void unset()
  {
    clear();
  }

  public static class UnmodifiableEList extends DelegatingEList.UnmodifiableEList
      implements
        InternalEList.Unsettable,
        EStructuralFeature.Setting
  {
    protected final InternalEObject owner;
    protected final EStructuralFeature eStructuralFeature;

    public UnmodifiableEList(InternalEObject owner, EStructuralFeature eStructuralFeature, List underlyingList)
    {
      super(underlyingList);
      this.owner = owner;
      this.eStructuralFeature = eStructuralFeature;
    }

    public Object basicGet(int index)
    {
      return super.basicGet(index);
    }

    public List basicList()
    {
      return super.basicList();
    }

    public Iterator basicIterator()
    {
      return super.basicIterator();
    }

    public ListIterator basicListIterator()
    {
      return super.basicListIterator();
    }

    public ListIterator basicListIterator(int index)
    {
      return super.basicListIterator(index);
    }

    public EObject getEObject()
    {
      return owner;
    }

    public EStructuralFeature getEStructuralFeature()
    {
      return eStructuralFeature;
    }

    public Object get(boolean resolve)
    {
      return this;
    }

    public void set(Object newValue)
    {
      throw new UnsupportedOperationException();
    }

    public boolean isSet()
    {
      return !isEmpty();
    }

    public void unset()
    {
      throw new UnsupportedOperationException();
    }

    public NotificationChain basicRemove(Object object, NotificationChain notifications)
    {
      throw new UnsupportedOperationException();
    }

    public NotificationChain basicAdd(Object object, NotificationChain notifications)
    {
      throw new UnsupportedOperationException();
    }
  }

  public static abstract class Generic extends DelegatingEcoreEList
  {
    public static final int IS_SET = EcoreEList.Generic.IS_SET;
    public static final int IS_UNSETTABLE = EcoreEList.Generic.IS_UNSETTABLE;
    public static final int HAS_INSTANCE_CLASS = EcoreEList.Generic.HAS_INSTANCE_CLASS;
    public static final int HAS_NAVIGABLE_INVERSE = EcoreEList.Generic.HAS_NAVIGABLE_INVERSE;
    public static final int HAS_MANY_INVERSE = EcoreEList.Generic.HAS_MANY_INVERSE;
    public static final int IS_CONTAINMENT = EcoreEList.Generic.IS_CONTAINMENT;
    public static final int IS_CONTAINER = EcoreEList.Generic.IS_CONTAINER;
    public static final int IS_UNIQUE = EcoreEList.Generic.IS_UNIQUE;
    public static final int IS_PRIMITIVE = EcoreEList.Generic.IS_PRIMITIVE;
    public static final int IS_ENUM = EcoreEList.Generic.IS_ENUM;
    public static final int IS_EOBJECT = EcoreEList.Generic.IS_EOBJECT;
    public static final int HAS_PROXIES = EcoreEList.Generic.HAS_PROXIES;

    public static int kind(EStructuralFeature eStructuralFeature)
    {
      return EcoreEList.Generic.kind(eStructuralFeature);
    }

    protected int kind;

    public Generic(int kind, InternalEObject owner)
    {
      super(owner);
      this.kind = kind;
    }

    protected boolean useEquals()
    {
      // We can use == for EObjects and EnumLiterals.
      //
      return (kind & (IS_EOBJECT | IS_ENUM)) == 0;
    }

    protected boolean canContainNull()
    {
      return (kind & (IS_EOBJECT | IS_PRIMITIVE | IS_ENUM)) == 0;
    }

    protected boolean isUnique()
    {
      return (kind & IS_UNIQUE) != 0;
    }

    protected boolean hasInverse()
    {
      return (kind & (HAS_NAVIGABLE_INVERSE | IS_CONTAINMENT)) != 0;
    }

    protected boolean hasManyInverse()
    {
      return (kind & HAS_MANY_INVERSE) != 0;
    }

    protected boolean hasNavigableInverse()
    {
      return (kind & HAS_NAVIGABLE_INVERSE) != 0;
    }

    protected boolean isEObject()
    {
      return (kind & IS_EOBJECT) != 0;
    }

    protected boolean isContainment()
    {
      return (kind & IS_CONTAINMENT) != 0;
    }

    protected boolean hasProxies()
    {
      return (kind & HAS_PROXIES) != 0;
    }

    protected boolean hasInstanceClass()
    {
      return (kind & HAS_INSTANCE_CLASS) != 0;
    }

    protected boolean isContainer()
    {
      return (kind & IS_CONTAINER) != 0;
    }

    protected boolean isUnsettable()
    {
      return (kind & IS_UNSETTABLE) != 0;
    }

    public boolean isSet()
    {
      return isUnsettable() ? (kind & IS_SET) != 0 : !isEmpty();
    }

    public void unset()
    {
      super.unset();
      if (isUnsettable())
      {
        if (isNotificationRequired())
        {
          boolean oldIsSet = (kind & IS_SET) != 0;
          kind &= ~IS_SET;
          owner.eNotify(createNotification(Notification.UNSET, oldIsSet, false));
        }
        else
        {
          kind &= ~IS_SET;
        }
      }
    }

    protected void didChange()
    {
      kind |= IS_SET;
    }
  }

  public static abstract class Dynamic extends Generic
  {
    protected EStructuralFeature eStructuralFeature;

    public Dynamic(InternalEObject owner, EStructuralFeature eStructuralFeature)
    {
      super(kind(eStructuralFeature), owner);
      this.eStructuralFeature = eStructuralFeature;
    }

    public Dynamic(int kind, InternalEObject owner, EStructuralFeature eStructuralFeature)
    {
      super(kind, owner);
      this.eStructuralFeature = eStructuralFeature;
    }

    public EStructuralFeature getEStructuralFeature()
    {
      return eStructuralFeature;
    }
  }

}
