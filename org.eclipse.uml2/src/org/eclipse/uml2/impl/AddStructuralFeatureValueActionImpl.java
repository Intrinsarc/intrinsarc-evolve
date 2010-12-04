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
 * $Id: AddStructuralFeatureValueActionImpl.java,v 1.1 2009-03-04 23:06:40 andrew Exp $
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
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Activity;
import org.eclipse.uml2.AddStructuralFeatureValueAction;
import org.eclipse.uml2.InputPin;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.StructuralFeature;
import org.eclipse.uml2.StructuredActivityNode;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Add Structural Feature Value Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.AddStructuralFeatureValueActionImpl#isReplaceAll <em>Is Replace All</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.AddStructuralFeatureValueActionImpl#getInsertAt <em>Insert At</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AddStructuralFeatureValueActionImpl extends WriteStructuralFeatureActionImpl implements AddStructuralFeatureValueAction {
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
	protected static final int IS_REPLACE_ALL_EFLAG = 1 << 9;

	/**
	 * The cached value of the '{@link #getInsertAt() <em>Insert At</em>}' containment reference.
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
	protected AddStructuralFeatureValueActionImpl() {
		super();
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (AddStructuralFeatureValueActionImpl.class.equals(getClass()) && org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getAddStructuralFeatureValueAction();
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
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IS_REPLACE_ALL, oldIsReplaceAll, newIsReplaceAll));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputPin getInsertAt() {
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
	public NotificationChain basicSetInsertAt(InputPin newInsertAt, NotificationChain msgs) {
		InputPin oldInsertAt = insertAt;
		insertAt = newInsertAt;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INSERT_AT, oldInsertAt, newInsertAt);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInsertAt(InputPin newInsertAt) {
		if (newInsertAt != insertAt) {
			NotificationChain msgs = null;
			if (insertAt != null)
				msgs = ((InternalEObject)insertAt).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INSERT_AT, null, msgs);
			if (newInsertAt != null)
				msgs = ((InternalEObject)newInsertAt).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INSERT_AT, null, msgs);
			msgs = basicSetInsertAt(newInsertAt, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INSERT_AT, newInsertAt, newInsertAt));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputPin createInsertAt(EClass eClass) {
		InputPin newInsertAt = (InputPin) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INSERT_AT, null, newInsertAt));
		}
		setInsertAt(newInsertAt);
		return newInsertAt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputPin createInsertAt() {
		InputPin newInsertAt = UML2Factory.eINSTANCE.createInputPin();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INSERT_AT, null, newInsertAt));
		}
		setInsertAt(newInsertAt);
		return newInsertAt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OUTGOING:
					return ((InternalEList)getOutgoings()).basicAdd(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INCOMING:
					return ((InternalEList)getIncomings()).basicAdd(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__ACTIVITY:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__ACTIVITY, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_STRUCTURED_NODE:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_STRUCTURED_NODE, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_PARTITION:
					return ((InternalEList)getInPartitions()).basicAdd(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_INTERRUPTIBLE_REGION:
					return ((InternalEList)getInInterruptibleRegions()).basicAdd(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__HANDLER:
					return ((InternalEList)getHandlers()).basicAdd(otherEnd, msgs);
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
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OUTGOING:
					return ((InternalEList)getOutgoings()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INCOMING:
					return ((InternalEList)getIncomings()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__ACTIVITY:
					return eBasicSetContainer(null, UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__ACTIVITY, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_STRUCTURED_NODE:
					return eBasicSetContainer(null, UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_STRUCTURED_NODE, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_PARTITION:
					return ((InternalEList)getInPartitions()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_INTERRUPTIBLE_REGION:
					return ((InternalEList)getInInterruptibleRegions()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__HANDLER:
					return ((InternalEList)getHandlers()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__LOCAL_PRECONDITION:
					return ((InternalEList)getLocalPreconditions()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__LOCAL_POSTCONDITION:
					return ((InternalEList)getLocalPostconditions()).basicRemove(otherEnd, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OBJECT:
					return basicSetObject(null, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__VALUE:
					return basicSetValue(null, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INSERT_AT:
					return basicSetInsertAt(null, msgs);
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
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__ACTIVITY:
					return eContainer.eInverseRemove(this, UML2Package.ACTIVITY__NODE, Activity.class, msgs);
				case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_STRUCTURED_NODE:
					return eContainer.eInverseRemove(this, UML2Package.STRUCTURED_ACTIVITY_NODE__CONTAINED_NODE, StructuredActivityNode.class, msgs);
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
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__UUID:
				return getUuid();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__NAME:
				return getName();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__VISIBILITY:
				return getVisibility();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REDEFINITION_CONTEXT:
				return getRedefinitionContexts();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IS_LEAF:
				return isLeaf() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OUTGOING:
				return getOutgoings();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INCOMING:
				return getIncomings();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_GROUP:
				return getInGroups();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__ACTIVITY:
				return getActivity();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REDEFINED_ELEMENT:
				return getRedefinedElements();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_STRUCTURED_NODE:
				return getInStructuredNode();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_PARTITION:
				return getInPartitions();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_INTERRUPTIBLE_REGION:
				return getInInterruptibleRegions();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__HANDLER:
				return getHandlers();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__EFFECT:
				return getEffect();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OUTPUT:
				return getOutputs();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INPUT:
				return getInputs();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__CONTEXT:
				if (resolve) return getContext();
				return basicGetContext();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__LOCAL_PRECONDITION:
				return getLocalPreconditions();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__LOCAL_POSTCONDITION:
				return getLocalPostconditions();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__STRUCTURAL_FEATURE:
				if (resolve) return getStructuralFeature();
				return basicGetStructuralFeature();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OBJECT:
				return getObject();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__VALUE:
				return getValue();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IS_REPLACE_ALL:
				return isReplaceAll() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INSERT_AT:
				return getInsertAt();
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
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__NAME:
				setName((String)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IS_LEAF:
				setIsLeaf(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OUTGOING:
				getOutgoings().clear();
				getOutgoings().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INCOMING:
				getIncomings().clear();
				getIncomings().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__ACTIVITY:
				setActivity((Activity)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REDEFINED_ELEMENT:
				getRedefinedElements().clear();
				getRedefinedElements().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_STRUCTURED_NODE:
				setInStructuredNode((StructuredActivityNode)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_PARTITION:
				getInPartitions().clear();
				getInPartitions().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_INTERRUPTIBLE_REGION:
				getInInterruptibleRegions().clear();
				getInInterruptibleRegions().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__HANDLER:
				getHandlers().clear();
				getHandlers().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__EFFECT:
				setEffect((String)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__LOCAL_PRECONDITION:
				getLocalPreconditions().clear();
				getLocalPreconditions().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__LOCAL_POSTCONDITION:
				getLocalPostconditions().clear();
				getLocalPostconditions().addAll((Collection)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__STRUCTURAL_FEATURE:
				setStructuralFeature((StructuralFeature)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OBJECT:
				setObject((InputPin)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__VALUE:
				setValue((InputPin)newValue);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IS_REPLACE_ALL:
				setIsReplaceAll(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INSERT_AT:
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
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IS_LEAF:
				setIsLeaf(IS_LEAF_EDEFAULT);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OUTGOING:
				getOutgoings().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INCOMING:
				getIncomings().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__ACTIVITY:
				setActivity((Activity)null);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REDEFINED_ELEMENT:
				getRedefinedElements().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_STRUCTURED_NODE:
				setInStructuredNode((StructuredActivityNode)null);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_PARTITION:
				getInPartitions().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_INTERRUPTIBLE_REGION:
				getInInterruptibleRegions().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__HANDLER:
				getHandlers().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__EFFECT:
				setEffect(EFFECT_EDEFAULT);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__LOCAL_PRECONDITION:
				getLocalPreconditions().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__LOCAL_POSTCONDITION:
				getLocalPostconditions().clear();
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__STRUCTURAL_FEATURE:
				setStructuralFeature((StructuralFeature)null);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OBJECT:
				setObject((InputPin)null);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__VALUE:
				setValue((InputPin)null);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IS_REPLACE_ALL:
				setIsReplaceAll(IS_REPLACE_ALL_EDEFAULT);
				return;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INSERT_AT:
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
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNER:
				return basicGetOwner() != null;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REDEFINITION_CONTEXT:
				return !getRedefinitionContexts().isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IS_LEAF:
				return ((eFlags & IS_LEAF_EFLAG) != 0) != IS_LEAF_EDEFAULT;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OUTGOING:
				return outgoing != null && !outgoing.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INCOMING:
				return incoming != null && !incoming.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_GROUP:
				return !getInGroups().isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__ACTIVITY:
				return getActivity() != null;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__REDEFINED_ELEMENT:
				return redefinedElement != null && !redefinedElement.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_STRUCTURED_NODE:
				return getInStructuredNode() != null;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_PARTITION:
				return inPartition != null && !inPartition.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IN_INTERRUPTIBLE_REGION:
				return inInterruptibleRegion != null && !inInterruptibleRegion.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__HANDLER:
				return handler != null && !handler.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__EFFECT:
				return EFFECT_EDEFAULT == null ? effect != null : !EFFECT_EDEFAULT.equals(effect);
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OUTPUT:
				return !getOutputs().isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INPUT:
				return !getInputs().isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__CONTEXT:
				return basicGetContext() != null;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__LOCAL_PRECONDITION:
				return localPrecondition != null && !localPrecondition.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__LOCAL_POSTCONDITION:
				return localPostcondition != null && !localPostcondition.isEmpty();
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__STRUCTURAL_FEATURE:
				return structuralFeature != null;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__OBJECT:
				return object != null;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__VALUE:
				return value != null;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IS_REPLACE_ALL:
				return ((eFlags & IS_REPLACE_ALL_EFLAG) != 0) != IS_REPLACE_ALL_EDEFAULT;
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INSERT_AT:
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


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getInputsHelper(EList input) {
		super.getInputsHelper(input);
		if (eIsSet(UML2Package.eINSTANCE.getAddStructuralFeatureValueAction_InsertAt())) {
			input.add(getInsertAt());
		}
		return input;
	}


} //AddStructuralFeatureValueActionImpl
