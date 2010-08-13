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
 * $Id: StateInvariantImpl.java,v 1.1 2009-03-04 23:06:35 andrew Exp $
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

//import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Constraint;
import org.eclipse.uml2.Interaction;
import org.eclipse.uml2.InteractionOperand;
import org.eclipse.uml2.Lifeline;
import org.eclipse.uml2.StateInvariant;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Invariant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.StateInvariantImpl#getInvariant <em>Invariant</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StateInvariantImpl#getCovereds <em>Covered</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateInvariantImpl extends InteractionFragmentImpl implements StateInvariant {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getInvariant() <em>Invariant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvariant()
	 * @generated
	 * @ordered
	 */
	protected Constraint invariant = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateInvariantImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (StateInvariantImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getStateInvariant();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Constraint getInvariant() {
		return invariant;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Constraint undeleted_getInvariant() {
		Constraint temp = getInvariant();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInvariant(Constraint newInvariant, NotificationChain msgs) {
		Constraint oldInvariant = invariant;
		invariant = newInvariant;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.STATE_INVARIANT__INVARIANT, oldInvariant, newInvariant);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInvariant(Constraint newInvariant) {
		if (newInvariant != invariant) {
			NotificationChain msgs = null;
			if (invariant != null)
				msgs = ((InternalEObject)invariant).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.STATE_INVARIANT__INVARIANT, null, msgs);
			if (newInvariant != null)
				msgs = ((InternalEObject)newInvariant).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UML2Package.STATE_INVARIANT__INVARIANT, null, msgs);
			msgs = basicSetInvariant(newInvariant, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.STATE_INVARIANT__INVARIANT, newInvariant, newInvariant));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Constraint createInvariant(EClass eClass) {
		Constraint newInvariant = (Constraint) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.STATE_INVARIANT__INVARIANT, null, newInvariant));
		}
		setInvariant(newInvariant);
		return newInvariant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Constraint createInvariant() {
		Constraint newInvariant = UML2Factory.eINSTANCE.createConstraint();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.STATE_INVARIANT__INVARIANT, null, newInvariant));
		}
		setInvariant(newInvariant);
		return newInvariant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getCovereds() {

		if (null == covered) {
			covered =
				new EObjectWithInverseResolvingEList.ManyInverse(Lifeline.class, this, UML2Package.STATE_INVARIANT__COVERED, UML2Package.LIFELINE__COVERED_BY);
		}

		return covered;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getCovereds() {
		if (covered == null) {
			covered = new com.intrinsarc.emflist.PersistentEList(Lifeline.class, this, UML2Package.STATE_INVARIANT__COVERED);
		}
		return covered;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getCovereds() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (covered != null) {
			for (Object object : covered) {
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
    public Lifeline getCovered(String name) {
		for (Iterator i = getCovereds().iterator(); i.hasNext(); ) {
			Lifeline covered = (Lifeline) i.next();
			if (name.equals(covered.getName())) {
				return covered;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.STATE_INVARIANT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.STATE_INVARIANT__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__COVERED:
					return ((InternalEList)getCovereds()).basicAdd(otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__ENCLOSING_INTERACTION:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.STATE_INVARIANT__ENCLOSING_INTERACTION, msgs);
				case UML2Package.STATE_INVARIANT__ENCLOSING_OPERAND:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.STATE_INVARIANT__ENCLOSING_OPERAND, msgs);
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
				case UML2Package.STATE_INVARIANT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.STATE_INVARIANT__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.STATE_INVARIANT__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__COVERED:
					return ((InternalEList)getCovereds()).basicRemove(otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__GENERAL_ORDERING:
					return ((InternalEList)getGeneralOrderings()).basicRemove(otherEnd, msgs);
				case UML2Package.STATE_INVARIANT__ENCLOSING_INTERACTION:
					return eBasicSetContainer(null, UML2Package.STATE_INVARIANT__ENCLOSING_INTERACTION, msgs);
				case UML2Package.STATE_INVARIANT__ENCLOSING_OPERAND:
					return eBasicSetContainer(null, UML2Package.STATE_INVARIANT__ENCLOSING_OPERAND, msgs);
				case UML2Package.STATE_INVARIANT__INVARIANT:
					return basicSetInvariant(null, msgs);
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
				case UML2Package.STATE_INVARIANT__ENCLOSING_INTERACTION:
					return eContainer.eInverseRemove(this, UML2Package.INTERACTION__FRAGMENT, Interaction.class, msgs);
				case UML2Package.STATE_INVARIANT__ENCLOSING_OPERAND:
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
			case UML2Package.STATE_INVARIANT__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.STATE_INVARIANT__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.STATE_INVARIANT__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.STATE_INVARIANT__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.STATE_INVARIANT__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.STATE_INVARIANT__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.STATE_INVARIANT__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.STATE_INVARIANT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.STATE_INVARIANT__UUID:
				return getUuid();
			case UML2Package.STATE_INVARIANT__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.STATE_INVARIANT__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.STATE_INVARIANT__NAME:
				return getName();
			case UML2Package.STATE_INVARIANT__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.STATE_INVARIANT__VISIBILITY:
				return getVisibility();
			case UML2Package.STATE_INVARIANT__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.STATE_INVARIANT__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.STATE_INVARIANT__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.STATE_INVARIANT__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.STATE_INVARIANT__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.STATE_INVARIANT__COVERED:
				return getCovereds();
			case UML2Package.STATE_INVARIANT__GENERAL_ORDERING:
				return getGeneralOrderings();
			case UML2Package.STATE_INVARIANT__ENCLOSING_INTERACTION:
				return getEnclosingInteraction();
			case UML2Package.STATE_INVARIANT__ENCLOSING_OPERAND:
				return getEnclosingOperand();
			case UML2Package.STATE_INVARIANT__INVARIANT:
				return getInvariant();
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
			case UML2Package.STATE_INVARIANT__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.STATE_INVARIANT__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.STATE_INVARIANT__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.STATE_INVARIANT__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.STATE_INVARIANT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.STATE_INVARIANT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.STATE_INVARIANT__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.STATE_INVARIANT__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.STATE_INVARIANT__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.STATE_INVARIANT__NAME:
				setName((String)newValue);
				return;
			case UML2Package.STATE_INVARIANT__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.STATE_INVARIANT__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.STATE_INVARIANT__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.STATE_INVARIANT__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.STATE_INVARIANT__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.STATE_INVARIANT__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.STATE_INVARIANT__COVERED:
				getCovereds().clear();
				getCovereds().addAll((Collection)newValue);
				return;
			case UML2Package.STATE_INVARIANT__GENERAL_ORDERING:
				getGeneralOrderings().clear();
				getGeneralOrderings().addAll((Collection)newValue);
				return;
			case UML2Package.STATE_INVARIANT__ENCLOSING_INTERACTION:
				setEnclosingInteraction((Interaction)newValue);
				return;
			case UML2Package.STATE_INVARIANT__ENCLOSING_OPERAND:
				setEnclosingOperand((InteractionOperand)newValue);
				return;
			case UML2Package.STATE_INVARIANT__INVARIANT:
				setInvariant((Constraint)newValue);
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
			case UML2Package.STATE_INVARIANT__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.STATE_INVARIANT__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.STATE_INVARIANT__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.STATE_INVARIANT__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.STATE_INVARIANT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.STATE_INVARIANT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.STATE_INVARIANT__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.STATE_INVARIANT__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.STATE_INVARIANT__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.STATE_INVARIANT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.STATE_INVARIANT__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.STATE_INVARIANT__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.STATE_INVARIANT__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.STATE_INVARIANT__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.STATE_INVARIANT__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.STATE_INVARIANT__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.STATE_INVARIANT__COVERED:
				getCovereds().clear();
				return;
			case UML2Package.STATE_INVARIANT__GENERAL_ORDERING:
				getGeneralOrderings().clear();
				return;
			case UML2Package.STATE_INVARIANT__ENCLOSING_INTERACTION:
				setEnclosingInteraction((Interaction)null);
				return;
			case UML2Package.STATE_INVARIANT__ENCLOSING_OPERAND:
				setEnclosingOperand((InteractionOperand)null);
				return;
			case UML2Package.STATE_INVARIANT__INVARIANT:
				setInvariant((Constraint)null);
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
			case UML2Package.STATE_INVARIANT__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.STATE_INVARIANT__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.STATE_INVARIANT__OWNER:
				return basicGetOwner() != null;
			case UML2Package.STATE_INVARIANT__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.STATE_INVARIANT__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.STATE_INVARIANT__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.STATE_INVARIANT__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.STATE_INVARIANT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.STATE_INVARIANT__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.STATE_INVARIANT__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.STATE_INVARIANT__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.STATE_INVARIANT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.STATE_INVARIANT__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.STATE_INVARIANT__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.STATE_INVARIANT__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.STATE_INVARIANT__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.STATE_INVARIANT__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.STATE_INVARIANT__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.STATE_INVARIANT__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.STATE_INVARIANT__COVERED:
				return !getCovereds().isEmpty();
			case UML2Package.STATE_INVARIANT__GENERAL_ORDERING:
				return generalOrdering != null && !generalOrdering.isEmpty();
			case UML2Package.STATE_INVARIANT__ENCLOSING_INTERACTION:
				return getEnclosingInteraction() != null;
			case UML2Package.STATE_INVARIANT__ENCLOSING_OPERAND:
				return getEnclosingOperand() != null;
			case UML2Package.STATE_INVARIANT__INVARIANT:
				return invariant != null;
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
		if (eIsSet(UML2Package.eINSTANCE.getStateInvariant_Invariant())) {
			ownedElement.add(getInvariant());
		}
		return ownedElement;
	}


} //StateInvariantImpl
