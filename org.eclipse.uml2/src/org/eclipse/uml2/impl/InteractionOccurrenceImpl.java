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
 * $Id: InteractionOccurrenceImpl.java,v 1.1 2009-03-04 23:06:36 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;
import java.util.Iterator;

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

import org.eclipse.uml2.Gate;
import org.eclipse.uml2.InputPin;
import org.eclipse.uml2.Interaction;
import org.eclipse.uml2.InteractionOccurrence;
import org.eclipse.uml2.InteractionOperand;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interaction Occurrence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.InteractionOccurrenceImpl#getRefersTo <em>Refers To</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InteractionOccurrenceImpl#getActualGates <em>Actual Gate</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InteractionOccurrenceImpl#getArguments <em>Argument</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InteractionOccurrenceImpl extends InteractionFragmentImpl implements InteractionOccurrence {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getRefersTo() <em>Refers To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefersTo()
	 * @generated
	 * @ordered
	 */
	protected Interaction refersTo = null;

	/**
	 * The cached value of the '{@link #getActualGates() <em>Actual Gate</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActualGates()
	 * @generated
	 * @ordered
	 */
	protected EList actualGate = null;

	/**
	 * The cached value of the '{@link #getArguments() <em>Argument</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArguments()
	 * @generated
	 * @ordered
	 */
	protected EList argument = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InteractionOccurrenceImpl() {
		super();
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (InteractionOccurrenceImpl.class.equals(getClass()) && org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getInteractionOccurrence();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interaction getRefersTo() {
		if (refersTo != null && refersTo.eIsProxy()) {
			Interaction oldRefersTo = refersTo;
			refersTo = (Interaction)eResolveProxy((InternalEObject)refersTo);
			if (refersTo != oldRefersTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.INTERACTION_OCCURRENCE__REFERS_TO, oldRefersTo, refersTo));
			}
		}
		return refersTo;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Interaction undeleted_getRefersTo() {
		Interaction temp = getRefersTo();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interaction basicGetRefersTo() {
		return refersTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRefersTo(Interaction newRefersTo) {
		Interaction oldRefersTo = refersTo;
		refersTo = newRefersTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.INTERACTION_OCCURRENCE__REFERS_TO, oldRefersTo, refersTo));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getActualGates() {
		if (actualGate == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		actualGate = new com.intrinsarc.emflist.PersistentEList(Gate.class, this, UML2Package.INTERACTION_OCCURRENCE__ACTUAL_GATE);
			 		return actualGate;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Gate.class, this, UML2Package.INTERACTION_OCCURRENCE__ACTUAL_GATE);
		}      
		return actualGate;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getActualGates() {
		if (actualGate == null) {
			actualGate = new com.intrinsarc.emflist.PersistentEList(Gate.class, this, UML2Package.INTERACTION_OCCURRENCE__ACTUAL_GATE);
		}
		return actualGate;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getActualGates() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (actualGate != null) {
			for (Object object : actualGate) {
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
    public Gate getActualGate(String name) {
		for (Iterator i = getActualGates().iterator(); i.hasNext(); ) {
			Gate actualGate = (Gate) i.next();
			if (name.equals(actualGate.getName())) {
				return actualGate;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createActualGate() instead.
	 */
	public Gate createActualGate(EClass eClass) {
		Gate newActualGate = (Gate) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERACTION_OCCURRENCE__ACTUAL_GATE, null, newActualGate));
		}
		getActualGates().add(newActualGate);
		return newActualGate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Gate createActualGate() {
		Gate newActualGate = UML2Factory.eINSTANCE.createGate();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERACTION_OCCURRENCE__ACTUAL_GATE, null, newActualGate));
		}
		settable_getActualGates().add(newActualGate);
		return newActualGate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getArguments() {
		if (argument == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		argument = new com.intrinsarc.emflist.PersistentEList(InputPin.class, this, UML2Package.INTERACTION_OCCURRENCE__ARGUMENT);
			 		return argument;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(InputPin.class, this, UML2Package.INTERACTION_OCCURRENCE__ARGUMENT);
		}      
		return argument;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getArguments() {
		if (argument == null) {
			argument = new com.intrinsarc.emflist.PersistentEList(InputPin.class, this, UML2Package.INTERACTION_OCCURRENCE__ARGUMENT);
		}
		return argument;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getArguments() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (argument != null) {
			for (Object object : argument) {
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
    public InputPin getArgument(String name) {
		for (Iterator i = getArguments().iterator(); i.hasNext(); ) {
			InputPin argument = (InputPin) i.next();
			if (name.equals(argument.getName())) {
				return argument;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputPin createArgument(EClass eClass) {
		InputPin newArgument = (InputPin) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERACTION_OCCURRENCE__ARGUMENT, null, newArgument));
		}
		settable_getArguments().add(newArgument);
		return newArgument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputPin createArgument() {
		InputPin newArgument = UML2Factory.eINSTANCE.createInputPin();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERACTION_OCCURRENCE__ARGUMENT, null, newArgument));
		}
		settable_getArguments().add(newArgument);
		return newArgument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.INTERACTION_OCCURRENCE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.INTERACTION_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__COVERED:
					return ((InternalEList)getCovereds()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_INTERACTION:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_INTERACTION, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_OPERAND:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_OPERAND, msgs);
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
				case UML2Package.INTERACTION_OCCURRENCE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__COVERED:
					return ((InternalEList)getCovereds()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__GENERAL_ORDERING:
					return ((InternalEList)getGeneralOrderings()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_INTERACTION:
					return eBasicSetContainer(null, UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_INTERACTION, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_OPERAND:
					return eBasicSetContainer(null, UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_OPERAND, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__ACTUAL_GATE:
					return ((InternalEList)getActualGates()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__ARGUMENT:
					return ((InternalEList)getArguments()).basicRemove(otherEnd, msgs);
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
				case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_INTERACTION:
					return eContainer.eInverseRemove(this, UML2Package.INTERACTION__FRAGMENT, Interaction.class, msgs);
				case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_OPERAND:
					return eContainer.eInverseRemove(this, UML2Package.INTERACTION_OPERAND__FRAGMENT, InteractionOperand.class, msgs);
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
			case UML2Package.INTERACTION_OCCURRENCE__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.INTERACTION_OCCURRENCE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.INTERACTION_OCCURRENCE__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.INTERACTION_OCCURRENCE__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.INTERACTION_OCCURRENCE__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.INTERACTION_OCCURRENCE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.INTERACTION_OCCURRENCE__UUID:
				return getUuid();
			case UML2Package.INTERACTION_OCCURRENCE__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.INTERACTION_OCCURRENCE__NAME:
				return getName();
			case UML2Package.INTERACTION_OCCURRENCE__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.INTERACTION_OCCURRENCE__VISIBILITY:
				return getVisibility();
			case UML2Package.INTERACTION_OCCURRENCE__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.INTERACTION_OCCURRENCE__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.INTERACTION_OCCURRENCE__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.INTERACTION_OCCURRENCE__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.INTERACTION_OCCURRENCE__COVERED:
				return getCovereds();
			case UML2Package.INTERACTION_OCCURRENCE__GENERAL_ORDERING:
				return getGeneralOrderings();
			case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_INTERACTION:
				return getEnclosingInteraction();
			case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_OPERAND:
				return getEnclosingOperand();
			case UML2Package.INTERACTION_OCCURRENCE__REFERS_TO:
				if (resolve) return getRefersTo();
				return basicGetRefersTo();
			case UML2Package.INTERACTION_OCCURRENCE__ACTUAL_GATE:
				return getActualGates();
			case UML2Package.INTERACTION_OCCURRENCE__ARGUMENT:
				return getArguments();
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
			case UML2Package.INTERACTION_OCCURRENCE__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.INTERACTION_OCCURRENCE__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__NAME:
				setName((String)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__COVERED:
				getCovereds().clear();
				getCovereds().addAll((Collection)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__GENERAL_ORDERING:
				getGeneralOrderings().clear();
				getGeneralOrderings().addAll((Collection)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_INTERACTION:
				setEnclosingInteraction((Interaction)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_OPERAND:
				setEnclosingOperand((InteractionOperand)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__REFERS_TO:
				setRefersTo((Interaction)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__ACTUAL_GATE:
				getActualGates().clear();
				getActualGates().addAll((Collection)newValue);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__ARGUMENT:
				getArguments().clear();
				getArguments().addAll((Collection)newValue);
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
			case UML2Package.INTERACTION_OCCURRENCE__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.INTERACTION_OCCURRENCE__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.INTERACTION_OCCURRENCE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.INTERACTION_OCCURRENCE__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.INTERACTION_OCCURRENCE__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.INTERACTION_OCCURRENCE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.INTERACTION_OCCURRENCE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.INTERACTION_OCCURRENCE__COVERED:
				getCovereds().clear();
				return;
			case UML2Package.INTERACTION_OCCURRENCE__GENERAL_ORDERING:
				getGeneralOrderings().clear();
				return;
			case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_INTERACTION:
				setEnclosingInteraction((Interaction)null);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_OPERAND:
				setEnclosingOperand((InteractionOperand)null);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__REFERS_TO:
				setRefersTo((Interaction)null);
				return;
			case UML2Package.INTERACTION_OCCURRENCE__ACTUAL_GATE:
				getActualGates().clear();
				return;
			case UML2Package.INTERACTION_OCCURRENCE__ARGUMENT:
				getArguments().clear();
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
			case UML2Package.INTERACTION_OCCURRENCE__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__OWNER:
				return basicGetOwner() != null;
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.INTERACTION_OCCURRENCE__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.INTERACTION_OCCURRENCE__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.INTERACTION_OCCURRENCE__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.INTERACTION_OCCURRENCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.INTERACTION_OCCURRENCE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.INTERACTION_OCCURRENCE__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.INTERACTION_OCCURRENCE__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.INTERACTION_OCCURRENCE__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__COVERED:
				return covered != null && !covered.isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__GENERAL_ORDERING:
				return generalOrdering != null && !generalOrdering.isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_INTERACTION:
				return getEnclosingInteraction() != null;
			case UML2Package.INTERACTION_OCCURRENCE__ENCLOSING_OPERAND:
				return getEnclosingOperand() != null;
			case UML2Package.INTERACTION_OCCURRENCE__REFERS_TO:
				return refersTo != null;
			case UML2Package.INTERACTION_OCCURRENCE__ACTUAL_GATE:
				return actualGate != null && !actualGate.isEmpty();
			case UML2Package.INTERACTION_OCCURRENCE__ARGUMENT:
				return argument != null && !argument.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedElementsHelper(EList ownedElement) {
		super.getOwnedElementsHelper(ownedElement);
		if (eIsSet(UML2Package.eINSTANCE.getInteractionOccurrence_ActualGate())) {
			ownedElement.addAll(getActualGates());
		}
		return ownedElement;
	}


} //InteractionOccurrenceImpl
