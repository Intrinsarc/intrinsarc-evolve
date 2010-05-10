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
 * $Id: GeneralOrderingImpl.java,v 1.1 2009-03-04 23:06:39 andrew Exp $
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
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.uml2.EventOccurrence;
import org.eclipse.uml2.GeneralOrdering;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>General Ordering</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.GeneralOrderingImpl#getBefore <em>Before</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.GeneralOrderingImpl#getAfter <em>After</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GeneralOrderingImpl extends NamedElementImpl implements GeneralOrdering {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getBefore() <em>Before</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBefore()
	 * @generated
	 * @ordered
	 */
	protected EventOccurrence before = null;

	/**
	 * The cached value of the '{@link #getAfter() <em>After</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAfter()
	 * @generated
	 * @ordered
	 */
	protected EventOccurrence after = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GeneralOrderingImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (GeneralOrderingImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getGeneralOrdering();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventOccurrence getBefore() {
		if (before != null && before.eIsProxy()) {
			EventOccurrence oldBefore = before;
			before = (EventOccurrence)eResolveProxy((InternalEObject)before);
			if (before != oldBefore) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.GENERAL_ORDERING__BEFORE, oldBefore, before));
			}
		}
		return before;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EventOccurrence undeleted_getBefore() {
		EventOccurrence temp = getBefore();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventOccurrence basicGetBefore() {
		return before;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBefore(EventOccurrence newBefore, NotificationChain msgs) {
		EventOccurrence oldBefore = before;
		before = newBefore;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.GENERAL_ORDERING__BEFORE, oldBefore, newBefore);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBefore(EventOccurrence newBefore) {
		if (newBefore != before) {
			NotificationChain msgs = null;
			if (before != null)
				msgs = ((InternalEObject)before).eInverseRemove(this, UML2Package.EVENT_OCCURRENCE__TO_AFTER, EventOccurrence.class, msgs);
			if (newBefore != null)
				msgs = ((InternalEObject)newBefore).eInverseAdd(this, UML2Package.EVENT_OCCURRENCE__TO_AFTER, EventOccurrence.class, msgs);
			msgs = basicSetBefore(newBefore, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.GENERAL_ORDERING__BEFORE, newBefore, newBefore));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventOccurrence getAfter() {
		if (after != null && after.eIsProxy()) {
			EventOccurrence oldAfter = after;
			after = (EventOccurrence)eResolveProxy((InternalEObject)after);
			if (after != oldAfter) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.GENERAL_ORDERING__AFTER, oldAfter, after));
			}
		}
		return after;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EventOccurrence undeleted_getAfter() {
		EventOccurrence temp = getAfter();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventOccurrence basicGetAfter() {
		return after;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAfter(EventOccurrence newAfter, NotificationChain msgs) {
		EventOccurrence oldAfter = after;
		after = newAfter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.GENERAL_ORDERING__AFTER, oldAfter, newAfter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAfter(EventOccurrence newAfter) {
		if (newAfter != after) {
			NotificationChain msgs = null;
			if (after != null)
				msgs = ((InternalEObject)after).eInverseRemove(this, UML2Package.EVENT_OCCURRENCE__TO_BEFORE, EventOccurrence.class, msgs);
			if (newAfter != null)
				msgs = ((InternalEObject)newAfter).eInverseAdd(this, UML2Package.EVENT_OCCURRENCE__TO_BEFORE, EventOccurrence.class, msgs);
			msgs = basicSetAfter(newAfter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.GENERAL_ORDERING__AFTER, newAfter, newAfter));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.GENERAL_ORDERING__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.GENERAL_ORDERING__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.GENERAL_ORDERING__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.GENERAL_ORDERING__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.GENERAL_ORDERING__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.GENERAL_ORDERING__BEFORE:
					if (before != null)
						msgs = ((InternalEObject)before).eInverseRemove(this, UML2Package.EVENT_OCCURRENCE__TO_AFTER, EventOccurrence.class, msgs);
					return basicSetBefore((EventOccurrence)otherEnd, msgs);
				case UML2Package.GENERAL_ORDERING__AFTER:
					if (after != null)
						msgs = ((InternalEObject)after).eInverseRemove(this, UML2Package.EVENT_OCCURRENCE__TO_BEFORE, EventOccurrence.class, msgs);
					return basicSetAfter((EventOccurrence)otherEnd, msgs);
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
				case UML2Package.GENERAL_ORDERING__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.GENERAL_ORDERING__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.GENERAL_ORDERING__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.GENERAL_ORDERING__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.GENERAL_ORDERING__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.GENERAL_ORDERING__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.GENERAL_ORDERING__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.GENERAL_ORDERING__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.GENERAL_ORDERING__BEFORE:
					return basicSetBefore(null, msgs);
				case UML2Package.GENERAL_ORDERING__AFTER:
					return basicSetAfter(null, msgs);
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
			case UML2Package.GENERAL_ORDERING__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.GENERAL_ORDERING__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.GENERAL_ORDERING__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.GENERAL_ORDERING__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.GENERAL_ORDERING__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.GENERAL_ORDERING__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.GENERAL_ORDERING__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.GENERAL_ORDERING__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.GENERAL_ORDERING__UUID:
				return getUuid();
			case UML2Package.GENERAL_ORDERING__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.GENERAL_ORDERING__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.GENERAL_ORDERING__NAME:
				return getName();
			case UML2Package.GENERAL_ORDERING__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.GENERAL_ORDERING__VISIBILITY:
				return getVisibility();
			case UML2Package.GENERAL_ORDERING__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.GENERAL_ORDERING__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.GENERAL_ORDERING__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.GENERAL_ORDERING__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.GENERAL_ORDERING__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.GENERAL_ORDERING__BEFORE:
				if (resolve) return getBefore();
				return basicGetBefore();
			case UML2Package.GENERAL_ORDERING__AFTER:
				if (resolve) return getAfter();
				return basicGetAfter();
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
			case UML2Package.GENERAL_ORDERING__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.GENERAL_ORDERING__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__NAME:
				setName((String)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__BEFORE:
				setBefore((EventOccurrence)newValue);
				return;
			case UML2Package.GENERAL_ORDERING__AFTER:
				setAfter((EventOccurrence)newValue);
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
			case UML2Package.GENERAL_ORDERING__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.GENERAL_ORDERING__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.GENERAL_ORDERING__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.GENERAL_ORDERING__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.GENERAL_ORDERING__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.GENERAL_ORDERING__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.GENERAL_ORDERING__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.GENERAL_ORDERING__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.GENERAL_ORDERING__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.GENERAL_ORDERING__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.GENERAL_ORDERING__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.GENERAL_ORDERING__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.GENERAL_ORDERING__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.GENERAL_ORDERING__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.GENERAL_ORDERING__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.GENERAL_ORDERING__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.GENERAL_ORDERING__BEFORE:
				setBefore((EventOccurrence)null);
				return;
			case UML2Package.GENERAL_ORDERING__AFTER:
				setAfter((EventOccurrence)null);
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
			case UML2Package.GENERAL_ORDERING__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.GENERAL_ORDERING__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.GENERAL_ORDERING__OWNER:
				return basicGetOwner() != null;
			case UML2Package.GENERAL_ORDERING__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.GENERAL_ORDERING__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.GENERAL_ORDERING__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.GENERAL_ORDERING__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.GENERAL_ORDERING__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.GENERAL_ORDERING__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.GENERAL_ORDERING__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.GENERAL_ORDERING__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.GENERAL_ORDERING__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.GENERAL_ORDERING__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.GENERAL_ORDERING__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.GENERAL_ORDERING__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.GENERAL_ORDERING__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.GENERAL_ORDERING__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.GENERAL_ORDERING__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.GENERAL_ORDERING__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.GENERAL_ORDERING__BEFORE:
				return before != null;
			case UML2Package.GENERAL_ORDERING__AFTER:
				return after != null;
		}
		return eDynamicIsSet(eFeature);
	}


} //GeneralOrderingImpl
