/*
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - initial API and implementation
 *
 * $Id: SlotImpl.java,v 1.2 2009-04-22 10:01:15 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMFOptions;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.uml2.Element;
import org.eclipse.uml2.InstanceSpecification;
import org.eclipse.uml2.Slot;
import org.eclipse.uml2.StructuralFeature;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.ValueSpecification;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Slot</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.SlotImpl#getOwningInstance <em>Owning Instance</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.SlotImpl#getValues <em>Value</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.SlotImpl#getDefiningFeature <em>Defining Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SlotImpl extends ElementImpl implements Slot {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getValues() <em>Value</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList value = null;

	/**
	 * The cached value of the '{@link #getDefiningFeature() <em>Defining Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefiningFeature()
	 * @generated
	 * @ordered
	 */
	protected StructuralFeature definingFeature = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SlotImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (SlotImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getSlot();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstanceSpecification getOwningInstance()
	{
		if (eContainerFeatureID != UML2Package.SLOT__OWNING_INSTANCE) return null;
		return (InstanceSpecification)eContainer;
	}

	
	



  /**
   * return a version of j_deleted, which considers all of the parents also
   * @generated NOT
   */
  public boolean isThisDeleted()
  {
    // is this itself deleted?
    boolean deleted = getJ_deleted() > 0;
    if (deleted)
      return true;
    
    // is it's parent (or recursively upwards) deleted
    Element owner = getOwner();
    if (owner != null && owner.isThisDeleted())
      return true;
    
    // be very defensive
    // a slot with no associated type or attribute cannot exist
    if (getOwner() == null || getOwner().getOwner() == null || getOwner().getOwner().getOwner() == null || undeleted_getDefiningFeature() == null || undeleted_getDefiningFeature().getOwner() == null)
      return true;
    
    return
      !EMFOptions.doesAttributeExistForSlot(
        getOwner().getOwner().getOwner(),
        undeleted_getDefiningFeature().getOwner(),
        undeleted_getDefiningFeature().getUuid());
  }
  



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public InstanceSpecification undeleted_getOwningInstance()
	{
		InstanceSpecification temp = getOwningInstance();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwningInstance(InstanceSpecification newOwningInstance)
	{

		if (newOwningInstance != eContainer || (eContainerFeatureID != UML2Package.SLOT__OWNING_INSTANCE && newOwningInstance != null))
		{
			if (EcoreUtil.isAncestor(this, newOwningInstance))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwningInstance != null)
				msgs = ((InternalEObject)newOwningInstance).eInverseAdd(this, UML2Package.INSTANCE_SPECIFICATION__SLOT, InstanceSpecification.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newOwningInstance, UML2Package.SLOT__OWNING_INSTANCE, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.SLOT__OWNING_INSTANCE, newOwningInstance, newOwningInstance));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getValues()
	{
		if (value == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		value = new com.intrinsarc.emflist.PersistentEList(ValueSpecification.class, this, UML2Package.SLOT__VALUE);
			 		return value;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(ValueSpecification.class, this, UML2Package.SLOT__VALUE);
		}      
		return value;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getValues()
	{
		if (value == null)
		{
			
		
			value = new com.intrinsarc.emflist.PersistentEList(ValueSpecification.class, this, UML2Package.SLOT__VALUE);
		}
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getValues()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (value != null)
		{
			for (Object object : value)
			{
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public ValueSpecification getValue(String name) {
		for (Iterator i = getValues().iterator(); i.hasNext(); ) {
			ValueSpecification value = (ValueSpecification) i.next();
			if (name.equals(value.getName())) {
				return value;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueSpecification createValue(EClass eClass) {
		ValueSpecification newValue = (ValueSpecification) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.SLOT__VALUE, null, newValue));
		}
		settable_getValues().add(newValue);
		return newValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructuralFeature getDefiningFeature()
	{
		if (definingFeature != null && definingFeature.eIsProxy())
		{
			StructuralFeature oldDefiningFeature = definingFeature;
			definingFeature = (StructuralFeature)eResolveProxy((InternalEObject)definingFeature);
			if (definingFeature != oldDefiningFeature)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.SLOT__DEFINING_FEATURE, oldDefiningFeature, definingFeature));
			}
		}
		return definingFeature;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public StructuralFeature undeleted_getDefiningFeature()
	{
		StructuralFeature temp = getDefiningFeature();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructuralFeature basicGetDefiningFeature()
	{
		return definingFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefiningFeature(StructuralFeature newDefiningFeature)
	{

		StructuralFeature oldDefiningFeature = definingFeature;
		definingFeature = newDefiningFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.SLOT__DEFINING_FEATURE, oldDefiningFeature, definingFeature));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs)
	{
		if (featureID >= 0)
		{
			switch (eDerivedStructuralFeatureID(featureID, baseClass))
			{
				case UML2Package.SLOT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.SLOT__OWNING_INSTANCE:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.SLOT__OWNING_INSTANCE, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs)
	{
		if (featureID >= 0)
		{
			switch (eDerivedStructuralFeatureID(featureID, baseClass))
			{
				case UML2Package.SLOT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.SLOT__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.SLOT__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.SLOT__OWNING_INSTANCE:
					return eBasicSetContainer(null, UML2Package.SLOT__OWNING_INSTANCE, msgs);
				case UML2Package.SLOT__VALUE:
					return ((InternalEList)getValues()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element basicGetOwner()
	{
		InstanceSpecification owningInstance = getOwningInstance();			
		if (owningInstance != null) {
			return owningInstance;
		}
		return super.basicGetOwner();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedElementsHelper(EList ownedElement)
	{
		super.getOwnedElementsHelper(ownedElement);
		if (eIsSet(UML2Package.eINSTANCE.getSlot_Value())) {
			ownedElement.addAll(getValues());
		}
		return ownedElement;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs)
	{
		if (eContainerFeatureID >= 0)
		{
			switch (eContainerFeatureID)
			{
				case UML2Package.SLOT__OWNING_INSTANCE:
					return eContainer.eInverseRemove(this, UML2Package.INSTANCE_SPECIFICATION__SLOT, InstanceSpecification.class, msgs);
				default:
					return eDynamicBasicRemoveFromContainer(msgs);
			}
		}
		return eContainer.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - eContainerFeatureID, null, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.SLOT__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.SLOT__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.SLOT__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.SLOT__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.SLOT__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.SLOT__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.SLOT__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.SLOT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.SLOT__UUID:
				return getUuid();
			case UML2Package.SLOT__OWNING_INSTANCE:
				return getOwningInstance();
			case UML2Package.SLOT__VALUE:
				return getValues();
			case UML2Package.SLOT__DEFINING_FEATURE:
				if (resolve) return getDefiningFeature();
				return basicGetDefiningFeature();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.SLOT__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.SLOT__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.SLOT__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.SLOT__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.SLOT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.SLOT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.SLOT__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.SLOT__OWNING_INSTANCE:
				setOwningInstance((InstanceSpecification)newValue);
				return;
			case UML2Package.SLOT__VALUE:
				getValues().clear();
				getValues().addAll((Collection)newValue);
				return;
			case UML2Package.SLOT__DEFINING_FEATURE:
				setDefiningFeature((StructuralFeature)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.SLOT__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.SLOT__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.SLOT__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.SLOT__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.SLOT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.SLOT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.SLOT__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.SLOT__OWNING_INSTANCE:
				setOwningInstance((InstanceSpecification)null);
				return;
			case UML2Package.SLOT__VALUE:
				getValues().clear();
				return;
			case UML2Package.SLOT__DEFINING_FEATURE:
				setDefiningFeature((StructuralFeature)null);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.SLOT__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.SLOT__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.SLOT__OWNER:
				return basicGetOwner() != null;
			case UML2Package.SLOT__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.SLOT__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.SLOT__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.SLOT__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.SLOT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.SLOT__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.SLOT__OWNING_INSTANCE:
				return getOwningInstance() != null;
			case UML2Package.SLOT__VALUE:
				return value != null && !value.isEmpty();
			case UML2Package.SLOT__DEFINING_FEATURE:
				return definingFeature != null;
		}
		return eDynamicIsSet(eFeature);
	}


} //SlotImpl
