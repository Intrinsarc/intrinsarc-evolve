

/**
 * <copyright>
 * </copyright>
 *
 * $Id: AppliedBasicStereotypeValueImpl.java,v 1.1 2009-03-04 23:06:44 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.AppliedBasicStereotypeValue;
import org.eclipse.uml2.Property;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.ValueSpecification;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Applied Basic Stereotype Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.AppliedBasicStereotypeValueImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.AppliedBasicStereotypeValueImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AppliedBasicStereotypeValueImpl extends ElementImpl implements AppliedBasicStereotypeValue {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected Property property = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected ValueSpecification value = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AppliedBasicStereotypeValueImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (AppliedBasicStereotypeValueImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getAppliedBasicStereotypeValue();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property getProperty()
	{
		if (property != null && property.eIsProxy())
		{
			Property oldProperty = property;
			property = (Property)eResolveProxy((InternalEObject)property);
			if (property != oldProperty)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__PROPERTY, oldProperty, property));
			}
		}
		return property;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property undeleted_getProperty()
	{
		Property temp = getProperty();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property basicGetProperty()
	{
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperty(Property newProperty)
	{

		Property oldProperty = property;
		property = newProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__PROPERTY, oldProperty, property));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueSpecification getValue()
	{
		return value;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueSpecification undeleted_getValue()
	{
		ValueSpecification temp = getValue();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValue(ValueSpecification newValue, NotificationChain msgs)
	{

		ValueSpecification oldValue = value;
		value = newValue;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__VALUE, oldValue, newValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(ValueSpecification newValue)
	{

		if (newValue != value)
		{
			NotificationChain msgs = null;
			if (value != null)
				msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__VALUE, null, msgs);
			if (newValue != null)
				msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__VALUE, null, msgs);
			msgs = basicSetValue(newValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__VALUE, newValue, newValue));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueSpecification createValue(EClass eClass) {
		ValueSpecification newValue = (ValueSpecification) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__VALUE, null, newValue));
		}
		setValue(newValue);
		return newValue;
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
				case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
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
				case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__VALUE:
					return basicSetValue(null, msgs);
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__UUID:
				return getUuid();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__PROPERTY:
				if (resolve) return getProperty();
				return basicGetProperty();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__VALUE:
				return getValue();
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
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__PROPERTY:
				setProperty((Property)newValue);
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__VALUE:
				setValue((ValueSpecification)newValue);
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
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__PROPERTY:
				setProperty((Property)null);
				return;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__VALUE:
				setValue((ValueSpecification)null);
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
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__OWNER:
				return basicGetOwner() != null;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__PROPERTY:
				return property != null;
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE__VALUE:
				return value != null;
		}
		return eDynamicIsSet(eFeature);
	}


} //AppliedBasicStereotypeValueImpl
