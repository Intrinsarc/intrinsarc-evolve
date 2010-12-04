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
 * $Id: TemplateParameterSubstitutionImpl.java,v 1.1 2009-03-04 23:06:41 andrew Exp $
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
import org.eclipse.uml2.ParameterableElement;
import org.eclipse.uml2.TemplateBinding;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateParameterSubstitution;
import org.eclipse.uml2.UML2Package;

import org.eclipse.uml2.common.util.SubsetEObjectContainmentEList;
import org.eclipse.uml2.common.util.SupersetEObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Template Parameter Substitution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.TemplateParameterSubstitutionImpl#getActuals <em>Actual</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TemplateParameterSubstitutionImpl#getFormal <em>Formal</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TemplateParameterSubstitutionImpl#getTemplateBinding <em>Template Binding</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TemplateParameterSubstitutionImpl#getOwnedActuals <em>Owned Actual</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TemplateParameterSubstitutionImpl extends ElementImpl implements TemplateParameterSubstitution {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getActuals() <em>Actual</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActuals()
	 * @generated
	 * @ordered
	 */
	protected EList actual = null;

	/**
	 * The cached value of the '{@link #getFormal() <em>Formal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormal()
	 * @generated
	 * @ordered
	 */
	protected TemplateParameter formal = null;

	/**
	 * The cached value of the '{@link #getOwnedActuals() <em>Owned Actual</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedActuals()
	 * @generated
	 * @ordered
	 */
	protected EList ownedActual = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TemplateParameterSubstitutionImpl() {
		super();
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (TemplateParameterSubstitutionImpl.class.equals(getClass()) && org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getTemplateParameterSubstitution();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateParameter getFormal() {
		if (formal != null && formal.eIsProxy()) {
			TemplateParameter oldFormal = formal;
			formal = (TemplateParameter)eResolveProxy((InternalEObject)formal);
			if (formal != oldFormal) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__FORMAL, oldFormal, formal));
			}
		}
		return formal;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public TemplateParameter undeleted_getFormal() {
		TemplateParameter temp = getFormal();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateParameter basicGetFormal() {
		return formal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFormal(TemplateParameter newFormal) {
		TemplateParameter oldFormal = formal;
		formal = newFormal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__FORMAL, oldFormal, formal));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateBinding getTemplateBinding() {
		if (eContainerFeatureID != UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING) return null;
		return (TemplateBinding)eContainer;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public TemplateBinding undeleted_getTemplateBinding() {
		TemplateBinding temp = getTemplateBinding();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemplateBinding(TemplateBinding newTemplateBinding) {
		if (newTemplateBinding != eContainer || (eContainerFeatureID != UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING && newTemplateBinding != null)) {
			if (EcoreUtil.isAncestor(this, newTemplateBinding))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newTemplateBinding != null)
				msgs = ((InternalEObject)newTemplateBinding).eInverseAdd(this, UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION, TemplateBinding.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newTemplateBinding, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING, newTemplateBinding, newTemplateBinding));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getActuals() {
		if (actual == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		actual = new com.intrinsarc.emflist.PersistentEList(ParameterableElement.class, this, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__ACTUAL, new int[] {UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL});
			 		return actual;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(ParameterableElement.class, this, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__ACTUAL, new int[] {UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL});
		}      
		return actual;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getActuals() {
		if (actual == null) {
			actual = new com.intrinsarc.emflist.PersistentEList(ParameterableElement.class, this, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__ACTUAL, new int[] {UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL});
		}
		return actual;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getActuals() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (actual != null) {
			for (Object object : actual) {
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
	public EList getOwnedActuals() {
		if (ownedActual == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		ownedActual = new com.intrinsarc.emflist.PersistentEList(ParameterableElement.class, this, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL, new int[] {UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__ACTUAL});
			 		return ownedActual;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(ParameterableElement.class, this, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL, new int[] {UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__ACTUAL});
		}      
		return ownedActual;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedActuals() {
		if (ownedActual == null) {
			ownedActual = new com.intrinsarc.emflist.PersistentEList(ParameterableElement.class, this, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL, new int[] {UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__ACTUAL});
		}
		return ownedActual;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedActuals() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedActual != null) {
			for (Object object : ownedActual) {
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
	public ParameterableElement createOwnedActual(EClass eClass) {
		ParameterableElement newOwnedActual = (ParameterableElement) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL, null, newOwnedActual));
		}
		settable_getOwnedActuals().add(newOwnedActual);
		return newOwnedActual;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element basicGetOwner() {
		TemplateBinding templateBinding = getTemplateBinding();			
		if (templateBinding != null) {
			return templateBinding;
		}
		return super.basicGetOwner();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedElementsHelper(EList ownedElement) {
		super.getOwnedElementsHelper(ownedElement);
		if (eIsSet(UML2Package.eINSTANCE.getTemplateParameterSubstitution_OwnedActual())) {
			ownedElement.addAll(getOwnedActuals());
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
				case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING, msgs);
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
				case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING:
					return eBasicSetContainer(null, UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING, msgs);
				case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL:
					return ((InternalEList)getOwnedActuals()).basicRemove(otherEnd, msgs);
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
				case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING:
					return eContainer.eInverseRemove(this, UML2Package.TEMPLATE_BINDING__PARAMETER_SUBSTITUTION, TemplateBinding.class, msgs);
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
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__UUID:
				return getUuid();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__FORMAL:
				if (resolve) return getFormal();
				return basicGetFormal();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING:
				return getTemplateBinding();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__ACTUAL:
				return getActuals();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL:
				return getOwnedActuals();
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
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__FORMAL:
				setFormal((TemplateParameter)newValue);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING:
				setTemplateBinding((TemplateBinding)newValue);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__ACTUAL:
				getActuals().clear();
				getActuals().addAll((Collection)newValue);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL:
				getOwnedActuals().clear();
				getOwnedActuals().addAll((Collection)newValue);
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
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__FORMAL:
				setFormal((TemplateParameter)null);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING:
				setTemplateBinding((TemplateBinding)null);
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__ACTUAL:
				getActuals().clear();
				return;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL:
				getOwnedActuals().clear();
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
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNER:
				return basicGetOwner() != null;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__FORMAL:
				return formal != null;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING:
				return getTemplateBinding() != null;
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__ACTUAL:
				return actual != null && !actual.isEmpty();
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL:
				return ownedActual != null && !ownedActual.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}


} //TemplateParameterSubstitutionImpl
