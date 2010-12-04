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
 * $Id: ProfileApplicationImpl.java,v 1.1 2009-03-04 23:06:36 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.Profile;
import org.eclipse.uml2.ProfileApplication;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.SubsetEObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Profile Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.ProfileApplicationImpl#getImportedPackage <em>Imported Package</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ProfileApplicationImpl#getImportedProfile <em>Imported Profile</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProfileApplicationImpl extends PackageImportImpl implements ProfileApplication {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getImportedProfile() <em>Imported Profile</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportedProfile()
	 * @generated
	 * @ordered
	 */
	protected Profile importedProfile = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProfileApplicationImpl() {
		super();
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (ProfileApplicationImpl.class.equals(getClass()) && org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getProfileApplication();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.uml2.Package getImportedPackage() {
		if (importedPackage != null && importedPackage.eIsProxy()) {
			org.eclipse.uml2.Package oldImportedPackage = importedPackage;
			importedPackage = (org.eclipse.uml2.Package)eResolveProxy((InternalEObject)importedPackage);
			if (importedPackage != oldImportedPackage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.PROFILE_APPLICATION__IMPORTED_PACKAGE, oldImportedPackage, importedPackage));
			}
		}
		return importedPackage;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public org.eclipse.uml2.Package undeleted_getImportedPackage() {
		org.eclipse.uml2.Package temp = getImportedPackage();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.uml2.Package basicGetImportedPackage() {
		return importedPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Profile getImportedProfile() {
		if (importedProfile != null && importedProfile.eIsProxy()) {
			Profile oldImportedProfile = importedProfile;
			importedProfile = (Profile)eResolveProxy((InternalEObject)importedProfile);
			if (importedProfile != oldImportedProfile) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.PROFILE_APPLICATION__IMPORTED_PROFILE, oldImportedProfile, importedProfile));
			}
		}
		return importedProfile;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Profile undeleted_getImportedProfile() {
		Profile temp = getImportedProfile();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Profile basicGetImportedProfile() {
		return importedProfile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportedProfile(Profile newImportedProfile) {
		Profile oldImportedProfile = importedProfile;
		importedProfile = newImportedProfile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PROFILE_APPLICATION__IMPORTED_PROFILE, oldImportedProfile, importedProfile));

		if (newImportedProfile != null || oldImportedProfile == importedPackage) {
			setImportedPackage(newImportedProfile);
		}
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportedPackage(org.eclipse.uml2.Package newImportedPackage) {
		org.eclipse.uml2.Package oldImportedPackage = importedPackage;
		importedPackage = newImportedPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PROFILE_APPLICATION__IMPORTED_PACKAGE, oldImportedPackage, importedPackage));

		if (importedProfile != null && importedProfile != newImportedPackage) {
			setImportedProfile(null);
		}
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.PROFILE_APPLICATION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.PROFILE_APPLICATION__IMPORTING_NAMESPACE:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.PROFILE_APPLICATION__IMPORTING_NAMESPACE, msgs);
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
				case UML2Package.PROFILE_APPLICATION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.PROFILE_APPLICATION__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.PROFILE_APPLICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.PROFILE_APPLICATION__IMPORTING_NAMESPACE:
					return eBasicSetContainer(null, UML2Package.PROFILE_APPLICATION__IMPORTING_NAMESPACE, msgs);
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
	public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs) {
		if (eContainerFeatureID >= 0) {
			switch (eContainerFeatureID) {
				case UML2Package.PROFILE_APPLICATION__IMPORTING_NAMESPACE:
					return eContainer.eInverseRemove(this, UML2Package.NAMESPACE__PACKAGE_IMPORT, Namespace.class, msgs);
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.PROFILE_APPLICATION__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.PROFILE_APPLICATION__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.PROFILE_APPLICATION__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.PROFILE_APPLICATION__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.PROFILE_APPLICATION__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.PROFILE_APPLICATION__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.PROFILE_APPLICATION__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.PROFILE_APPLICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.PROFILE_APPLICATION__UUID:
				return getUuid();
			case UML2Package.PROFILE_APPLICATION__RELATED_ELEMENT:
				return getRelatedElements();
			case UML2Package.PROFILE_APPLICATION__SOURCE:
				return getSources();
			case UML2Package.PROFILE_APPLICATION__TARGET:
				return getTargets();
			case UML2Package.PROFILE_APPLICATION__VISIBILITY:
				return getVisibility();
			case UML2Package.PROFILE_APPLICATION__IMPORTED_PACKAGE:
				if (resolve) return getImportedPackage();
				return basicGetImportedPackage();
			case UML2Package.PROFILE_APPLICATION__IMPORTING_NAMESPACE:
				return getImportingNamespace();
			case UML2Package.PROFILE_APPLICATION__IMPORTED_PROFILE:
				if (resolve) return getImportedProfile();
				return basicGetImportedProfile();
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
			case UML2Package.PROFILE_APPLICATION__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.PROFILE_APPLICATION__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.PROFILE_APPLICATION__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.PROFILE_APPLICATION__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.PROFILE_APPLICATION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.PROFILE_APPLICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.PROFILE_APPLICATION__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.PROFILE_APPLICATION__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.PROFILE_APPLICATION__IMPORTED_PACKAGE:
				setImportedPackage((org.eclipse.uml2.Package)newValue);
				return;
			case UML2Package.PROFILE_APPLICATION__IMPORTING_NAMESPACE:
				setImportingNamespace((Namespace)newValue);
				return;
			case UML2Package.PROFILE_APPLICATION__IMPORTED_PROFILE:
				setImportedProfile((Profile)newValue);
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
			case UML2Package.PROFILE_APPLICATION__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.PROFILE_APPLICATION__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.PROFILE_APPLICATION__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.PROFILE_APPLICATION__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.PROFILE_APPLICATION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.PROFILE_APPLICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.PROFILE_APPLICATION__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.PROFILE_APPLICATION__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.PROFILE_APPLICATION__IMPORTED_PACKAGE:
				setImportedPackage((org.eclipse.uml2.Package)null);
				return;
			case UML2Package.PROFILE_APPLICATION__IMPORTING_NAMESPACE:
				setImportingNamespace((Namespace)null);
				return;
			case UML2Package.PROFILE_APPLICATION__IMPORTED_PROFILE:
				setImportedProfile((Profile)null);
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
			case UML2Package.PROFILE_APPLICATION__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.PROFILE_APPLICATION__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.PROFILE_APPLICATION__OWNER:
				return basicGetOwner() != null;
			case UML2Package.PROFILE_APPLICATION__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.PROFILE_APPLICATION__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.PROFILE_APPLICATION__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.PROFILE_APPLICATION__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.PROFILE_APPLICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.PROFILE_APPLICATION__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.PROFILE_APPLICATION__RELATED_ELEMENT:
				return !getRelatedElements().isEmpty();
			case UML2Package.PROFILE_APPLICATION__SOURCE:
				return !getSources().isEmpty();
			case UML2Package.PROFILE_APPLICATION__TARGET:
				return !getTargets().isEmpty();
			case UML2Package.PROFILE_APPLICATION__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.PROFILE_APPLICATION__IMPORTED_PACKAGE:
				return importedPackage != null;
			case UML2Package.PROFILE_APPLICATION__IMPORTING_NAMESPACE:
				return getImportingNamespace() != null;
			case UML2Package.PROFILE_APPLICATION__IMPORTED_PROFILE:
				return importedProfile != null;
		}
		return eDynamicIsSet(eFeature);
	}


} //ProfileApplicationImpl
