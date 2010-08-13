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
 * $Id: LinkEndCreationDataImpl.java,v 1.1 2009-03-04 23:06:39 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

//import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.InputPin;
import org.eclipse.uml2.LinkEndCreationData;
import org.eclipse.uml2.Property;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.QualifierValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link End Creation Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.LinkEndCreationDataImpl#isReplaceAll <em>Is Replace All</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.LinkEndCreationDataImpl#getInsertAt <em>Insert At</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.LinkEndCreationDataImpl#getQualifiers <em>Qualifier</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LinkEndCreationDataImpl extends LinkEndDataImpl implements LinkEndCreationData {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #isReplaceAll() <em>Is Replace All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReplaceAll()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_REPLACE_ALL_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isReplaceAll() <em>Is Replace All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReplaceAll()
	 * @generated
	 * @ordered
	 */
	protected static final int IS_REPLACE_ALL_EFLAG = 1 << 8;

	/**
	 * The cached value of the '{@link #getInsertAt() <em>Insert At</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsertAt()
	 * @generated
	 * @ordered
	 */
	protected InputPin insertAt = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkEndCreationDataImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (LinkEndCreationDataImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getLinkEndCreationData();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReplaceAll() {
		return (eFlags & IS_REPLACE_ALL_EFLAG) != 0;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsReplaceAll(boolean newIsReplaceAll) {
		boolean oldIsReplaceAll = (eFlags & IS_REPLACE_ALL_EFLAG) != 0;
		if (newIsReplaceAll) eFlags |= IS_REPLACE_ALL_EFLAG; else eFlags &= ~IS_REPLACE_ALL_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.LINK_END_CREATION_DATA__IS_REPLACE_ALL, oldIsReplaceAll, newIsReplaceAll));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputPin getInsertAt() {
		if (insertAt != null && insertAt.eIsProxy()) {
			InputPin oldInsertAt = insertAt;
			insertAt = (InputPin)eResolveProxy((InternalEObject)insertAt);
			if (insertAt != oldInsertAt) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.LINK_END_CREATION_DATA__INSERT_AT, oldInsertAt, insertAt));
			}
		}
		return insertAt;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public InputPin undeleted_getInsertAt() {
		InputPin temp = getInsertAt();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputPin basicGetInsertAt() {
		return insertAt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInsertAt(InputPin newInsertAt) {
		InputPin oldInsertAt = insertAt;
		insertAt = newInsertAt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.LINK_END_CREATION_DATA__INSERT_AT, oldInsertAt, insertAt));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getQualifiers() {

		if (null == qualifier) {
			qualifier = new EObjectContainmentEList(QualifierValue.class, this, UML2Package.LINK_END_CREATION_DATA__QUALIFIER);
		}

		return qualifier;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getQualifiers() {
		if (qualifier == null) {
			qualifier = new com.intrinsarc.emflist.PersistentEList(QualifierValue.class, this, UML2Package.LINK_END_CREATION_DATA__QUALIFIER);
		}
		return qualifier;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getQualifiers() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (qualifier != null) {
			for (Object object : qualifier) {
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
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.LINK_END_CREATION_DATA__EANNOTATIONS:
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.LINK_END_CREATION_DATA__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.LINK_END_CREATION_DATA__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.LINK_END_CREATION_DATA__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.LINK_END_CREATION_DATA__QUALIFIER:
					return ((InternalEList)getQualifiers()).basicRemove(otherEnd, msgs);
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.LINK_END_CREATION_DATA__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.LINK_END_CREATION_DATA__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.LINK_END_CREATION_DATA__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.LINK_END_CREATION_DATA__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.LINK_END_CREATION_DATA__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.LINK_END_CREATION_DATA__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.LINK_END_CREATION_DATA__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.LINK_END_CREATION_DATA__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.LINK_END_CREATION_DATA__UUID:
				return getUuid();
			case UML2Package.LINK_END_CREATION_DATA__VALUE:
				if (resolve) return getValue();
				return basicGetValue();
			case UML2Package.LINK_END_CREATION_DATA__END:
				if (resolve) return getEnd();
				return basicGetEnd();
			case UML2Package.LINK_END_CREATION_DATA__QUALIFIER:
				return getQualifiers();
			case UML2Package.LINK_END_CREATION_DATA__IS_REPLACE_ALL:
				return isReplaceAll() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.LINK_END_CREATION_DATA__INSERT_AT:
				if (resolve) return getInsertAt();
				return basicGetInsertAt();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.LINK_END_CREATION_DATA__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.LINK_END_CREATION_DATA__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.LINK_END_CREATION_DATA__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.LINK_END_CREATION_DATA__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.LINK_END_CREATION_DATA__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.LINK_END_CREATION_DATA__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.LINK_END_CREATION_DATA__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.LINK_END_CREATION_DATA__VALUE:
				setValue((InputPin)newValue);
				return;
			case UML2Package.LINK_END_CREATION_DATA__END:
				setEnd((Property)newValue);
				return;
			case UML2Package.LINK_END_CREATION_DATA__QUALIFIER:
				getQualifiers().clear();
				getQualifiers().addAll((Collection)newValue);
				return;
			case UML2Package.LINK_END_CREATION_DATA__IS_REPLACE_ALL:
				setIsReplaceAll(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.LINK_END_CREATION_DATA__INSERT_AT:
				setInsertAt((InputPin)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.LINK_END_CREATION_DATA__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.LINK_END_CREATION_DATA__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.LINK_END_CREATION_DATA__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.LINK_END_CREATION_DATA__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.LINK_END_CREATION_DATA__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.LINK_END_CREATION_DATA__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.LINK_END_CREATION_DATA__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.LINK_END_CREATION_DATA__VALUE:
				setValue((InputPin)null);
				return;
			case UML2Package.LINK_END_CREATION_DATA__END:
				setEnd((Property)null);
				return;
			case UML2Package.LINK_END_CREATION_DATA__QUALIFIER:
				getQualifiers().clear();
				return;
			case UML2Package.LINK_END_CREATION_DATA__IS_REPLACE_ALL:
				setIsReplaceAll(IS_REPLACE_ALL_EDEFAULT);
				return;
			case UML2Package.LINK_END_CREATION_DATA__INSERT_AT:
				setInsertAt((InputPin)null);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.LINK_END_CREATION_DATA__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.LINK_END_CREATION_DATA__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.LINK_END_CREATION_DATA__OWNER:
				return basicGetOwner() != null;
			case UML2Package.LINK_END_CREATION_DATA__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.LINK_END_CREATION_DATA__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.LINK_END_CREATION_DATA__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.LINK_END_CREATION_DATA__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.LINK_END_CREATION_DATA__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.LINK_END_CREATION_DATA__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.LINK_END_CREATION_DATA__VALUE:
				return value != null;
			case UML2Package.LINK_END_CREATION_DATA__END:
				return end != null;
			case UML2Package.LINK_END_CREATION_DATA__QUALIFIER:
				return !getQualifiers().isEmpty();
			case UML2Package.LINK_END_CREATION_DATA__IS_REPLACE_ALL:
				return ((eFlags & IS_REPLACE_ALL_EFLAG) != 0) != IS_REPLACE_ALL_EDEFAULT;
			case UML2Package.LINK_END_CREATION_DATA__INSERT_AT:
				return insertAt != null;
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (isReplaceAll: "); //$NON-NLS-1$
		result.append((eFlags & IS_REPLACE_ALL_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}


} //LinkEndCreationDataImpl
