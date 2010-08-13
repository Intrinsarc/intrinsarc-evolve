

/**
 * <copyright>
 * </copyright>
 *
 * $Id: DeltaReplacedConstituentImpl.java,v 1.1 2009-03-04 23:06:39 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import java.util.*;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.DeltaReplacedConstituent;
import org.eclipse.uml2.Element;
import org.eclipse.uml2.UML2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delta Replaced Constituent</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.DeltaReplacedConstituentImpl#getReplaced <em>Replaced</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DeltaReplacedConstituentImpl#getReplacement <em>Replacement</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeltaReplacedConstituentImpl extends ElementImpl implements DeltaReplacedConstituent {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getReplaced() <em>Replaced</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReplaced()
	 * @generated
	 * @ordered
	 */
	protected Element replaced = null;

	/**
	 * The cached value of the '{@link #getReplacement() <em>Replacement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReplacement()
	 * @generated
	 * @ordered
	 */
	protected Element replacement = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeltaReplacedConstituentImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (DeltaReplacedConstituentImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getDeltaReplacedConstituent();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element getReplaced() {
		if (replaced != null && replaced.eIsProxy()) {
			Element oldReplaced = replaced;
			replaced = (Element)eResolveProxy((InternalEObject)replaced);
			if (replaced != oldReplaced) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACED, oldReplaced, replaced));
			}
		}
		return replaced;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element undeleted_getReplaced() {
		Element temp = getReplaced();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element basicGetReplaced() {
		return replaced;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReplaced(Element newReplaced) {
		Element oldReplaced = replaced;
		replaced = newReplaced;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACED, oldReplaced, replaced));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element getReplacement() {
		return replacement;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element undeleted_getReplacement() {
		Element temp = getReplacement();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReplacement(Element newReplacement, NotificationChain msgs) {
		Element oldReplacement = replacement;
		replacement = newReplacement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACEMENT, oldReplacement, newReplacement);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReplacement(Element newReplacement) {
		if (newReplacement != replacement) {
			NotificationChain msgs = null;
			if (replacement != null)
				msgs = ((InternalEObject)replacement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACEMENT, null, msgs);
			if (newReplacement != null)
				msgs = ((InternalEObject)newReplacement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACEMENT, null, msgs);
			msgs = basicSetReplacement(newReplacement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACEMENT, newReplacement, newReplacement));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element createReplacement(EClass eClass) {
		Element newReplacement = (Element) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACEMENT, null, newReplacement));
		}
		setReplacement(newReplacement);
		return newReplacement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.DELTA_REPLACED_CONSTITUENT__EANNOTATIONS:
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
				case UML2Package.DELTA_REPLACED_CONSTITUENT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.DELTA_REPLACED_CONSTITUENT__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.DELTA_REPLACED_CONSTITUENT__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACEMENT:
					return basicSetReplacement(null, msgs);
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
			case UML2Package.DELTA_REPLACED_CONSTITUENT__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.DELTA_REPLACED_CONSTITUENT__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__UUID:
				return getUuid();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACED:
				if (resolve) return getReplaced();
				return basicGetReplaced();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACEMENT:
				return getReplacement();
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
			case UML2Package.DELTA_REPLACED_CONSTITUENT__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACED:
				setReplaced((Element)newValue);
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACEMENT:
				setReplacement((Element)newValue);
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
			case UML2Package.DELTA_REPLACED_CONSTITUENT__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACED:
				setReplaced((Element)null);
				return;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACEMENT:
				setReplacement((Element)null);
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
			case UML2Package.DELTA_REPLACED_CONSTITUENT__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__OWNER:
				return basicGetOwner() != null;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.DELTA_REPLACED_CONSTITUENT__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.DELTA_REPLACED_CONSTITUENT__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACED:
				return replaced != null;
			case UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACEMENT:
				return replacement != null;
		}
		return eDynamicIsSet(eFeature);
	}


	  /**
	   * <!-- begin-user-doc -->
	   * <!-- end-user-doc -->
	   * @generated NOT
	   */

	  @Override
	  public boolean isThisDeleted()
	  {
	    if (super.isThisDeleted())
	      return true;
	    
	    // if we don't have 2 ends that are valid, this is deleted
	    if (replaced == null || replaced.getJ_deleted() > 0)
	    	return true;
	    if (replacement == null || replacement.getJ_deleted() > 0)
	    	return true;
      
      return !EMFOptions.doesAddExistElsewhere(replaced.getUuid(), getOwner(), getOwner().getOwner());
	  }

} //DeltaReplacedConstituentImpl
