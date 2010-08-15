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
 * $Id: ProtocolConformanceImpl.java,v 1.1 2009-03-04 23:06:39 andrew Exp $
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

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Element;
import org.eclipse.uml2.ProtocolConformance;
import org.eclipse.uml2.ProtocolStateMachine;
import org.eclipse.uml2.UML2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Protocol Conformance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.ProtocolConformanceImpl#getSpecificMachine <em>Specific Machine</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ProtocolConformanceImpl#getGeneralMachine <em>General Machine</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProtocolConformanceImpl extends DirectedRelationshipImpl implements ProtocolConformance {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getGeneralMachine() <em>General Machine</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGeneralMachine()
	 * @generated
	 * @ordered
	 */
	protected ProtocolStateMachine generalMachine = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProtocolConformanceImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (ProtocolConformanceImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getProtocolConformance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolStateMachine getSpecificMachine()
	{
		if (eContainerFeatureID != UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE) return null;
		return (ProtocolStateMachine)eContainer;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public ProtocolStateMachine undeleted_getSpecificMachine()
	{
		ProtocolStateMachine temp = getSpecificMachine();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpecificMachine(ProtocolStateMachine newSpecificMachine)
	{

		if (newSpecificMachine != eContainer || (eContainerFeatureID != UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE && newSpecificMachine != null))
		{
			if (EcoreUtil.isAncestor(this, newSpecificMachine))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSpecificMachine != null)
				msgs = ((InternalEObject)newSpecificMachine).eInverseAdd(this, UML2Package.PROTOCOL_STATE_MACHINE__CONFORMANCE, ProtocolStateMachine.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newSpecificMachine, UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE, newSpecificMachine, newSpecificMachine));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolStateMachine getGeneralMachine()
	{
		if (generalMachine != null && generalMachine.eIsProxy())
		{
			ProtocolStateMachine oldGeneralMachine = generalMachine;
			generalMachine = (ProtocolStateMachine)eResolveProxy((InternalEObject)generalMachine);
			if (generalMachine != oldGeneralMachine)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.PROTOCOL_CONFORMANCE__GENERAL_MACHINE, oldGeneralMachine, generalMachine));
			}
		}
		return generalMachine;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public ProtocolStateMachine undeleted_getGeneralMachine()
	{
		ProtocolStateMachine temp = getGeneralMachine();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolStateMachine basicGetGeneralMachine()
	{
		return generalMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGeneralMachine(ProtocolStateMachine newGeneralMachine)
	{

		ProtocolStateMachine oldGeneralMachine = generalMachine;
		generalMachine = newGeneralMachine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PROTOCOL_CONFORMANCE__GENERAL_MACHINE, oldGeneralMachine, generalMachine));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element basicGetOwner()
	{
		ProtocolStateMachine specificMachine = getSpecificMachine();			
		if (specificMachine != null) {
			return specificMachine;
		}
		return super.basicGetOwner();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getTargetsHelper(EList target)
	{
		super.getTargetsHelper(target);
		if (eIsSet(UML2Package.eINSTANCE.getProtocolConformance_GeneralMachine())) {
			target.add(getGeneralMachine());
		}
		return target;
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
				case UML2Package.PROTOCOL_CONFORMANCE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE, msgs);
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
				case UML2Package.PROTOCOL_CONFORMANCE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.PROTOCOL_CONFORMANCE__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.PROTOCOL_CONFORMANCE__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE:
					return eBasicSetContainer(null, UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE, msgs);
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
	public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs)
	{
		if (eContainerFeatureID >= 0)
		{
			switch (eContainerFeatureID)
			{
				case UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE:
					return eContainer.eInverseRemove(this, UML2Package.PROTOCOL_STATE_MACHINE__CONFORMANCE, ProtocolStateMachine.class, msgs);
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
			case UML2Package.PROTOCOL_CONFORMANCE__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.PROTOCOL_CONFORMANCE__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.PROTOCOL_CONFORMANCE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.PROTOCOL_CONFORMANCE__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.PROTOCOL_CONFORMANCE__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.PROTOCOL_CONFORMANCE__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.PROTOCOL_CONFORMANCE__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.PROTOCOL_CONFORMANCE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.PROTOCOL_CONFORMANCE__UUID:
				return getUuid();
			case UML2Package.PROTOCOL_CONFORMANCE__RELATED_ELEMENT:
				return getRelatedElements();
			case UML2Package.PROTOCOL_CONFORMANCE__SOURCE:
				return getSources();
			case UML2Package.PROTOCOL_CONFORMANCE__TARGET:
				return getTargets();
			case UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE:
				return getSpecificMachine();
			case UML2Package.PROTOCOL_CONFORMANCE__GENERAL_MACHINE:
				if (resolve) return getGeneralMachine();
				return basicGetGeneralMachine();
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
			case UML2Package.PROTOCOL_CONFORMANCE__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE:
				setSpecificMachine((ProtocolStateMachine)newValue);
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__GENERAL_MACHINE:
				setGeneralMachine((ProtocolStateMachine)newValue);
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
			case UML2Package.PROTOCOL_CONFORMANCE__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE:
				setSpecificMachine((ProtocolStateMachine)null);
				return;
			case UML2Package.PROTOCOL_CONFORMANCE__GENERAL_MACHINE:
				setGeneralMachine((ProtocolStateMachine)null);
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
			case UML2Package.PROTOCOL_CONFORMANCE__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.PROTOCOL_CONFORMANCE__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.PROTOCOL_CONFORMANCE__OWNER:
				return basicGetOwner() != null;
			case UML2Package.PROTOCOL_CONFORMANCE__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.PROTOCOL_CONFORMANCE__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.PROTOCOL_CONFORMANCE__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.PROTOCOL_CONFORMANCE__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.PROTOCOL_CONFORMANCE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.PROTOCOL_CONFORMANCE__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.PROTOCOL_CONFORMANCE__RELATED_ELEMENT:
				return !getRelatedElements().isEmpty();
			case UML2Package.PROTOCOL_CONFORMANCE__SOURCE:
				return !getSources().isEmpty();
			case UML2Package.PROTOCOL_CONFORMANCE__TARGET:
				return !getTargets().isEmpty();
			case UML2Package.PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE:
				return getSpecificMachine() != null;
			case UML2Package.PROTOCOL_CONFORMANCE__GENERAL_MACHINE:
				return generalMachine != null;
		}
		return eDynamicIsSet(eFeature);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getSourcesHelper(EList source)
	{
		super.getSourcesHelper(source);
		ProtocolStateMachine specificMachine = getSpecificMachine();
		if (specificMachine != null) {
			source.add(specificMachine);
		}
		return source;
	}


} //ProtocolConformanceImpl
