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
 * $Id: TemplateBindingImpl.java,v 1.1 2009-03-04 23:06:34 andrew Exp $
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

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Element;
import org.eclipse.uml2.TemplateBinding;
import org.eclipse.uml2.TemplateParameterSubstitution;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.TemplateableElement;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Template Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.TemplateBindingImpl#getBoundElement <em>Bound Element</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TemplateBindingImpl#getSignature <em>Signature</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TemplateBindingImpl#getParameterSubstitutions <em>Parameter Substitution</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TemplateBindingImpl extends DirectedRelationshipImpl implements TemplateBinding {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getSignature() <em>Signature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected TemplateSignature signature = null;

	/**
	 * The cached value of the '{@link #getParameterSubstitutions() <em>Parameter Substitution</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterSubstitutions()
	 * @generated
	 * @ordered
	 */
	protected EList parameterSubstitution = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TemplateBindingImpl() {
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
		return UML2Package.eINSTANCE.getTemplateBinding();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateableElement getBoundElement() {
		if (eContainerFeatureID != UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT) return null;
		return (TemplateableElement)eContainer;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public TemplateableElement undeleted_getBoundElement() {
		TemplateableElement temp = getBoundElement();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBoundElement(TemplateableElement newBoundElement) {
		if (newBoundElement != eContainer || (eContainerFeatureID != UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT && newBoundElement != null)) {
			if (EcoreUtil.isAncestor(this, newBoundElement))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newBoundElement != null)
				msgs = ((InternalEObject)newBoundElement).eInverseAdd(this, UML2Package.TEMPLATEABLE_ELEMENT__TEMPLATE_BINDING, TemplateableElement.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newBoundElement, UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT, newBoundElement, newBoundElement));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateSignature getSignature() {
		if (signature != null && signature.eIsProxy()) {
			TemplateSignature oldSignature = signature;
			signature = (TemplateSignature)eResolveProxy((InternalEObject)signature);
			if (signature != oldSignature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.TEMPLATE_BINDING__SIGNATURE, oldSignature, signature));
			}
		}
		return signature;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public TemplateSignature undeleted_getSignature() {
		TemplateSignature temp = getSignature();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateSignature basicGetSignature() {
		return signature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSignature(TemplateSignature newSignature) {
		TemplateSignature oldSignature = signature;
		signature = newSignature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.TEMPLATE_BINDING__SIGNATURE, oldSignature, signature));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getParameterSubstitutions() {
		if (parameterSubstitution == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		parameterSubstitution = new com.hopstepjump.emflist.PersistentEList(TemplateParameterSubstitution.class, this, UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING);
			 		return parameterSubstitution;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(TemplateParameterSubstitution.class, this, UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING);
		}      
		return parameterSubstitution;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getParameterSubstitutions() {
		if (parameterSubstitution == null) {
			parameterSubstitution = new com.hopstepjump.emflist.PersistentEList(TemplateParameterSubstitution.class, this, UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING);
		}
		return parameterSubstitution;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getParameterSubstitutions() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (parameterSubstitution != null) {
			for (Object object : parameterSubstitution) {
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
	 * @deprecated Use #createParameterSubstitution() instead.
	 */
	public TemplateParameterSubstitution createParameterSubstitution(EClass eClass) {
		TemplateParameterSubstitution newParameterSubstitution = (TemplateParameterSubstitution) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION, null, newParameterSubstitution));
		}
		getParameterSubstitutions().add(newParameterSubstitution);
		return newParameterSubstitution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateParameterSubstitution createParameterSubstitution() {
		TemplateParameterSubstitution newParameterSubstitution = UML2Factory.eINSTANCE.createTemplateParameterSubstitution();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION, null, newParameterSubstitution));
		}
		settable_getParameterSubstitutions().add(newParameterSubstitution);
		return newParameterSubstitution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element basicGetOwner() {
		TemplateableElement boundElement = getBoundElement();			
		if (boundElement != null) {
			return boundElement;
		}
		return super.basicGetOwner();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getSourcesHelper(EList source) {
		super.getSourcesHelper(source);
		TemplateableElement boundElement = getBoundElement();
		if (boundElement != null) {
			source.add(boundElement);
		}
		return source;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getTargetsHelper(EList target) {
		super.getTargetsHelper(target);
		if (eIsSet(UML2Package.eINSTANCE.getTemplateBinding_Signature())) {
			target.add(getSignature());
		}
		return target;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedElementsHelper(EList ownedElement) {
		super.getOwnedElementsHelper(ownedElement);
		if (eIsSet(UML2Package.eINSTANCE.getTemplateBinding_ParameterSubstitution())) {
			ownedElement.addAll(getParameterSubstitutions());
		}
		return ownedElement;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.TEMPLATE_BINDING__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT, msgs);
				case UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION:
					return ((InternalEList)getParameterSubstitutions()).basicAdd(otherEnd, msgs);
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
				case UML2Package.TEMPLATE_BINDING__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.TEMPLATE_BINDING__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.TEMPLATE_BINDING__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT:
					return eBasicSetContainer(null, UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT, msgs);
				case UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION:
					return ((InternalEList)getParameterSubstitutions()).basicRemove(otherEnd, msgs);
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
				case UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT:
					return eContainer.eInverseRemove(this, UML2Package.TEMPLATEABLE_ELEMENT__TEMPLATE_BINDING, TemplateableElement.class, msgs);
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
			case UML2Package.TEMPLATE_BINDING__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.TEMPLATE_BINDING__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.TEMPLATE_BINDING__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.TEMPLATE_BINDING__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.TEMPLATE_BINDING__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.TEMPLATE_BINDING__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.TEMPLATE_BINDING__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.TEMPLATE_BINDING__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.TEMPLATE_BINDING__UUID:
				return getUuid();
			case UML2Package.TEMPLATE_BINDING__RELATED_ELEMENT:
				return getRelatedElements();
			case UML2Package.TEMPLATE_BINDING__SOURCE:
				return getSources();
			case UML2Package.TEMPLATE_BINDING__TARGET:
				return getTargets();
			case UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT:
				return getBoundElement();
			case UML2Package.TEMPLATE_BINDING__SIGNATURE:
				if (resolve) return getSignature();
				return basicGetSignature();
			case UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION:
				return getParameterSubstitutions();
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
			case UML2Package.TEMPLATE_BINDING__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.TEMPLATE_BINDING__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.TEMPLATE_BINDING__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.TEMPLATE_BINDING__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.TEMPLATE_BINDING__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.TEMPLATE_BINDING__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.TEMPLATE_BINDING__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT:
				setBoundElement((TemplateableElement)newValue);
				return;
			case UML2Package.TEMPLATE_BINDING__SIGNATURE:
				setSignature((TemplateSignature)newValue);
				return;
			case UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION:
				getParameterSubstitutions().clear();
				getParameterSubstitutions().addAll((Collection)newValue);
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
			case UML2Package.TEMPLATE_BINDING__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.TEMPLATE_BINDING__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.TEMPLATE_BINDING__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.TEMPLATE_BINDING__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.TEMPLATE_BINDING__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.TEMPLATE_BINDING__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.TEMPLATE_BINDING__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT:
				setBoundElement((TemplateableElement)null);
				return;
			case UML2Package.TEMPLATE_BINDING__SIGNATURE:
				setSignature((TemplateSignature)null);
				return;
			case UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION:
				getParameterSubstitutions().clear();
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
			case UML2Package.TEMPLATE_BINDING__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.TEMPLATE_BINDING__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.TEMPLATE_BINDING__OWNER:
				return basicGetOwner() != null;
			case UML2Package.TEMPLATE_BINDING__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.TEMPLATE_BINDING__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.TEMPLATE_BINDING__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.TEMPLATE_BINDING__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.TEMPLATE_BINDING__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.TEMPLATE_BINDING__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.TEMPLATE_BINDING__RELATED_ELEMENT:
				return !getRelatedElements().isEmpty();
			case UML2Package.TEMPLATE_BINDING__SOURCE:
				return !getSources().isEmpty();
			case UML2Package.TEMPLATE_BINDING__TARGET:
				return !getTargets().isEmpty();
			case UML2Package.TEMPLATE_BINDING__BOUND_ELEMENT:
				return getBoundElement() != null;
			case UML2Package.TEMPLATE_BINDING__SIGNATURE:
				return signature != null;
			case UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION:
				return parameterSubstitution != null && !parameterSubstitution.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}


} //TemplateBindingImpl
