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
 * $Id: LinkEndDataImpl.java,v 1.1 2009-03-04 23:06:36 andrew Exp $
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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.uml2.InputPin;
import org.eclipse.uml2.LinkEndData;
import org.eclipse.uml2.Property;
import org.eclipse.uml2.QualifierValue;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link End Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.LinkEndDataImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.LinkEndDataImpl#getEnd <em>End</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.LinkEndDataImpl#getQualifiers <em>Qualifier</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LinkEndDataImpl extends ElementImpl implements LinkEndData {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected InputPin value = null;

	/**
	 * The cached value of the '{@link #getEnd() <em>End</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnd()
	 * @generated
	 * @ordered
	 */
	protected Property end = null;

	/**
	 * The cached value of the '{@link #getQualifiers() <em>Qualifier</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiers()
	 * @generated
	 * @ordered
	 */
	protected EList qualifier = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkEndDataImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (LinkEndDataImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getLinkEndData();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputPin getValue() {
		if (value != null && value.eIsProxy()) {
			InputPin oldValue = value;
			value = (InputPin)eResolveProxy((InternalEObject)value);
			if (value != oldValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.LINK_END_DATA__VALUE, oldValue, value));
			}
		}
		return value;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public InputPin undeleted_getValue() {
		InputPin temp = getValue();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputPin basicGetValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(InputPin newValue) {
		InputPin oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.LINK_END_DATA__VALUE, oldValue, value));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property getEnd() {
		if (end != null && end.eIsProxy()) {
			Property oldEnd = end;
			end = (Property)eResolveProxy((InternalEObject)end);
			if (end != oldEnd) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.LINK_END_DATA__END, oldEnd, end));
			}
		}
		return end;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Property undeleted_getEnd() {
		Property temp = getEnd();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property basicGetEnd() {
		return end;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnd(Property newEnd) {
		Property oldEnd = end;
		end = newEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.LINK_END_DATA__END, oldEnd, end));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getQualifiers() {
		if (qualifier == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		qualifier = new com.hopstepjump.emflist.PersistentEList(QualifierValue.class, this, UML2Package.LINK_END_DATA__QUALIFIER);
			 		return qualifier;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(QualifierValue.class, this, UML2Package.LINK_END_DATA__QUALIFIER);
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
			qualifier = new com.hopstepjump.emflist.PersistentEList(QualifierValue.class, this, UML2Package.LINK_END_DATA__QUALIFIER);
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
	 * @generated NOT
	 * @deprecated Use #createQualifier() instead.
	 */
	public QualifierValue createQualifier(EClass eClass) {
		QualifierValue newQualifier = (QualifierValue) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.LINK_END_DATA__QUALIFIER, null, newQualifier));
		}
		getQualifiers().add(newQualifier);
		return newQualifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QualifierValue createQualifier() {
		QualifierValue newQualifier = UML2Factory.eINSTANCE.createQualifierValue();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.LINK_END_DATA__QUALIFIER, null, newQualifier));
		}
		settable_getQualifiers().add(newQualifier);
		return newQualifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.LINK_END_DATA__EANNOTATIONS:
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
				case UML2Package.LINK_END_DATA__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.LINK_END_DATA__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.LINK_END_DATA__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.LINK_END_DATA__QUALIFIER:
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
			case UML2Package.LINK_END_DATA__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.LINK_END_DATA__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.LINK_END_DATA__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.LINK_END_DATA__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.LINK_END_DATA__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.LINK_END_DATA__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.LINK_END_DATA__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.LINK_END_DATA__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.LINK_END_DATA__UUID:
				return getUuid();
			case UML2Package.LINK_END_DATA__VALUE:
				if (resolve) return getValue();
				return basicGetValue();
			case UML2Package.LINK_END_DATA__END:
				if (resolve) return getEnd();
				return basicGetEnd();
			case UML2Package.LINK_END_DATA__QUALIFIER:
				return getQualifiers();
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
			case UML2Package.LINK_END_DATA__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.LINK_END_DATA__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.LINK_END_DATA__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.LINK_END_DATA__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.LINK_END_DATA__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.LINK_END_DATA__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.LINK_END_DATA__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.LINK_END_DATA__VALUE:
				setValue((InputPin)newValue);
				return;
			case UML2Package.LINK_END_DATA__END:
				setEnd((Property)newValue);
				return;
			case UML2Package.LINK_END_DATA__QUALIFIER:
				getQualifiers().clear();
				getQualifiers().addAll((Collection)newValue);
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
			case UML2Package.LINK_END_DATA__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.LINK_END_DATA__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.LINK_END_DATA__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.LINK_END_DATA__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.LINK_END_DATA__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.LINK_END_DATA__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.LINK_END_DATA__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.LINK_END_DATA__VALUE:
				setValue((InputPin)null);
				return;
			case UML2Package.LINK_END_DATA__END:
				setEnd((Property)null);
				return;
			case UML2Package.LINK_END_DATA__QUALIFIER:
				getQualifiers().clear();
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
			case UML2Package.LINK_END_DATA__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.LINK_END_DATA__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.LINK_END_DATA__OWNER:
				return basicGetOwner() != null;
			case UML2Package.LINK_END_DATA__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.LINK_END_DATA__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.LINK_END_DATA__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.LINK_END_DATA__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.LINK_END_DATA__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.LINK_END_DATA__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.LINK_END_DATA__VALUE:
				return value != null;
			case UML2Package.LINK_END_DATA__END:
				return end != null;
			case UML2Package.LINK_END_DATA__QUALIFIER:
				return qualifier != null && !qualifier.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}


} //LinkEndDataImpl
