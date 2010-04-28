

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import java.util.Iterator;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.uml2.RequirementsFeature;
import org.eclipse.uml2.RequirementsFeatureLink;
import org.eclipse.uml2.RequirementsLinkKind;
import org.eclipse.uml2.UML2Package;

import org.eclipse.uml2.common.util.CacheAdapter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Requirements Feature Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.RequirementsFeatureLinkImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.RequirementsFeatureLinkImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RequirementsFeatureLinkImpl extends ElementImpl implements RequirementsFeatureLink {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final RequirementsLinkKind KIND_EDEFAULT = RequirementsLinkKind.MANDATORY_LITERAL;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected RequirementsLinkKind kind = KIND_EDEFAULT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected RequirementsFeature type = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequirementsFeatureLinkImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getRequirementsFeatureLink();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequirementsLinkKind getKind() {
		return kind;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKind(RequirementsLinkKind newKind) {
		RequirementsLinkKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.REQUIREMENTS_FEATURE_LINK__KIND, oldKind, kind));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequirementsFeature getType() {
		if (type != null && type.eIsProxy()) {
			RequirementsFeature oldType = type;
			type = (RequirementsFeature)eResolveProxy((InternalEObject)type);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.REQUIREMENTS_FEATURE_LINK__TYPE, oldType, type));
			}
		}
		return type;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequirementsFeature undeleted_getType() {
		RequirementsFeature temp = getType();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequirementsFeature basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(RequirementsFeature newType) {
		RequirementsFeature oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.REQUIREMENTS_FEATURE_LINK__TYPE, oldType, type));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.REQUIREMENTS_FEATURE_LINK__EANNOTATIONS:
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
				case UML2Package.REQUIREMENTS_FEATURE_LINK__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE_LINK__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE_LINK__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
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
			case UML2Package.REQUIREMENTS_FEATURE_LINK__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.REQUIREMENTS_FEATURE_LINK__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__UUID:
				return getUuid();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__KIND:
				return getKind();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__TYPE:
				if (resolve) return getType();
				return basicGetType();
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
			case UML2Package.REQUIREMENTS_FEATURE_LINK__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__KIND:
				setKind((RequirementsLinkKind)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__TYPE:
				setType((RequirementsFeature)newValue);
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
			case UML2Package.REQUIREMENTS_FEATURE_LINK__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__TYPE:
				setType((RequirementsFeature)null);
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
			case UML2Package.REQUIREMENTS_FEATURE_LINK__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__OWNER:
				return basicGetOwner() != null;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.REQUIREMENTS_FEATURE_LINK__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE_LINK__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.REQUIREMENTS_FEATURE_LINK__KIND:
				return kind != KIND_EDEFAULT;
			case UML2Package.REQUIREMENTS_FEATURE_LINK__TYPE:
				return type != null;
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
		result.append(" (kind: "); //$NON-NLS-1$
		result.append(kind);
		result.append(')');
		return result.toString();
	}


} //RequirementsFeatureLinkImpl
