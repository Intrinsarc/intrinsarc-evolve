

/**
 * <copyright>
 * </copyright>
 *
 * $Id: DeltaReplacedOperationImpl.java,v 1.1 2009-03-04 23:06:40 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.DeltaReplacedOperation;
import org.eclipse.uml2.Element;
import org.eclipse.uml2.UML2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Delta Replaced Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class DeltaReplacedOperationImpl extends DeltaReplacedConstituentImpl implements DeltaReplacedOperation {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeltaReplacedOperationImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (DeltaReplacedOperationImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getDeltaReplacedOperation();
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
				case UML2Package.DELTA_REPLACED_OPERATION__EANNOTATIONS:
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
				case UML2Package.DELTA_REPLACED_OPERATION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.DELTA_REPLACED_OPERATION__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.DELTA_REPLACED_OPERATION__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.DELTA_REPLACED_OPERATION__REPLACEMENT:
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.DELTA_REPLACED_OPERATION__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.DELTA_REPLACED_OPERATION__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.DELTA_REPLACED_OPERATION__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.DELTA_REPLACED_OPERATION__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.DELTA_REPLACED_OPERATION__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.DELTA_REPLACED_OPERATION__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.DELTA_REPLACED_OPERATION__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.DELTA_REPLACED_OPERATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.DELTA_REPLACED_OPERATION__UUID:
				return getUuid();
			case UML2Package.DELTA_REPLACED_OPERATION__REPLACED:
				if (resolve) return getReplaced();
				return basicGetReplaced();
			case UML2Package.DELTA_REPLACED_OPERATION__REPLACEMENT:
				return getReplacement();
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
			case UML2Package.DELTA_REPLACED_OPERATION__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__REPLACED:
				setReplaced((Element)newValue);
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__REPLACEMENT:
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
	public void eUnset(EStructuralFeature eFeature)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.DELTA_REPLACED_OPERATION__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__REPLACED:
				setReplaced((Element)null);
				return;
			case UML2Package.DELTA_REPLACED_OPERATION__REPLACEMENT:
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
	public boolean eIsSet(EStructuralFeature eFeature)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.DELTA_REPLACED_OPERATION__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.DELTA_REPLACED_OPERATION__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.DELTA_REPLACED_OPERATION__OWNER:
				return basicGetOwner() != null;
			case UML2Package.DELTA_REPLACED_OPERATION__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.DELTA_REPLACED_OPERATION__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.DELTA_REPLACED_OPERATION__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.DELTA_REPLACED_OPERATION__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.DELTA_REPLACED_OPERATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.DELTA_REPLACED_OPERATION__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.DELTA_REPLACED_OPERATION__REPLACED:
				return replaced != null;
			case UML2Package.DELTA_REPLACED_OPERATION__REPLACEMENT:
				return replacement != null;
		}
		return eDynamicIsSet(eFeature);
	}


} //DeltaReplacedOperationImpl
