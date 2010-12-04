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
 * $Id: ImplementationImpl.java,v 1.1 2009-03-04 23:06:42 andrew Exp $
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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.BehavioredClassifier;
import org.eclipse.uml2.Classifier;
import org.eclipse.uml2.Component;
import org.eclipse.uml2.Implementation;
import org.eclipse.uml2.Interface;
import org.eclipse.uml2.NamedElement;
import org.eclipse.uml2.OpaqueExpression;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.SubsetEObjectResolvingEList;

import org.eclipse.uml2.common.util.SupersetEObjectResolvingEList;
import org.eclipse.uml2.common.util.SupersetEObjectWithInverseResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Implementation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.ImplementationImpl#getSuppliers <em>Supplier</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ImplementationImpl#getClients <em>Client</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ImplementationImpl#getContract <em>Contract</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ImplementationImpl#getImplementingClassifier <em>Implementing Classifier</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ImplementationImpl extends RealizationImpl implements Implementation {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getContract() <em>Contract</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContract()
	 * @generated
	 * @ordered
	 */
	protected Interface contract = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImplementationImpl() {
		super();
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (ImplementationImpl.class.equals(getClass()) && org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getImplementation();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interface getContract() {
		if (contract != null && contract.eIsProxy()) {
			Interface oldContract = contract;
			contract = (Interface)eResolveProxy((InternalEObject)contract);
			if (contract != oldContract) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.IMPLEMENTATION__CONTRACT, oldContract, contract));
			}
		}
		return contract;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Interface undeleted_getContract() {
		Interface temp = getContract();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interface basicGetContract() {
		return contract;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setContract(Interface newContract) {
		if (newContract != null && !getSuppliers().contains(newContract)) {
			getSuppliers().add(newContract);
		}
		Interface oldContract = contract;
		contract = newContract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.IMPLEMENTATION__CONTRACT, oldContract, contract));
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BehavioredClassifier getImplementingClassifier() {
		if (eContainerFeatureID != UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER) return null;
		return (BehavioredClassifier)eContainer;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public BehavioredClassifier undeleted_getImplementingClassifier() {
		BehavioredClassifier temp = getImplementingClassifier();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementingClassifier(BehavioredClassifier newImplementingClassifier) {
		if (newImplementingClassifier != null && !getClients().contains(newImplementingClassifier)) {
			getClients().add(newImplementingClassifier);
		}
		if (newImplementingClassifier != eContainer || (eContainerFeatureID != UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER && newImplementingClassifier != null)) {
			if (EcoreUtil.isAncestor(this, newImplementingClassifier))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newImplementingClassifier != null)
				msgs = ((InternalEObject)newImplementingClassifier).eInverseAdd(this, UML2Package.BEHAVIORED_CLASSIFIER__IMPLEMENTATION, BehavioredClassifier.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newImplementingClassifier, UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER, newImplementingClassifier, newImplementingClassifier));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getSuppliers() {
		if (supplier == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		supplier = new com.intrinsarc.emflist.PersistentEList(NamedElement.class, this, UML2Package.IMPLEMENTATION__SUPPLIER, new int[] {UML2Package.IMPLEMENTATION__REALIZING_CLASSIFIER, UML2Package.IMPLEMENTATION__CONTRACT});
			 		return supplier;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(NamedElement.class, this, UML2Package.IMPLEMENTATION__SUPPLIER, new int[] {UML2Package.IMPLEMENTATION__REALIZING_CLASSIFIER, UML2Package.IMPLEMENTATION__CONTRACT});
		}      
		return supplier;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getSuppliers() {
		if (supplier == null) {
			supplier = new com.intrinsarc.emflist.PersistentEList(NamedElement.class, this, UML2Package.IMPLEMENTATION__SUPPLIER, new int[] {UML2Package.IMPLEMENTATION__REALIZING_CLASSIFIER, UML2Package.IMPLEMENTATION__CONTRACT});
		}
		return supplier;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getSuppliers() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (supplier != null) {
			for (Object object : supplier) {
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
	public EList getClients() {
		if (client == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		client = new com.intrinsarc.emflist.PersistentEList(NamedElement.class, this, UML2Package.IMPLEMENTATION__CLIENT, new int[] {UML2Package.IMPLEMENTATION__ABSTRACTION, UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER}, UML2Package.NAMED_ELEMENT__CLIENT_DEPENDENCY);
			 		return client;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(NamedElement.class, this, UML2Package.IMPLEMENTATION__CLIENT, new int[] {UML2Package.IMPLEMENTATION__ABSTRACTION, UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER}, UML2Package.NAMED_ELEMENT__CLIENT_DEPENDENCY);
		}      
		return client;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getClients() {
		if (client == null) {
			client = new com.intrinsarc.emflist.PersistentEList(NamedElement.class, this, UML2Package.IMPLEMENTATION__CLIENT, new int[] {UML2Package.IMPLEMENTATION__ABSTRACTION, UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER}, UML2Package.NAMED_ELEMENT__CLIENT_DEPENDENCY);
		}
		return client;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getClients() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (client != null) {
			for (Object object : client) {
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
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.IMPLEMENTATION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.IMPLEMENTATION__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.IMPLEMENTATION__OWNING_PARAMETER, msgs);
				case UML2Package.IMPLEMENTATION__CLIENT:
					return ((InternalEList)getClients()).basicAdd(otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__ABSTRACTION:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.IMPLEMENTATION__ABSTRACTION, msgs);
				case UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER, msgs);
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
				case UML2Package.IMPLEMENTATION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.IMPLEMENTATION__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.IMPLEMENTATION__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.IMPLEMENTATION__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.IMPLEMENTATION__OWNING_PARAMETER, msgs);
				case UML2Package.IMPLEMENTATION__CLIENT:
					return ((InternalEList)getClients()).basicRemove(otherEnd, msgs);
				case UML2Package.IMPLEMENTATION__MAPPING:
					return basicSetMapping(null, msgs);
				case UML2Package.IMPLEMENTATION__ABSTRACTION:
					return eBasicSetContainer(null, UML2Package.IMPLEMENTATION__ABSTRACTION, msgs);
				case UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER:
					return eBasicSetContainer(null, UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER, msgs);
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
				case UML2Package.IMPLEMENTATION__OWNING_PARAMETER:
					return eContainer.eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__OWNED_PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
				case UML2Package.IMPLEMENTATION__ABSTRACTION:
					return eContainer.eInverseRemove(this, UML2Package.COMPONENT__REALIZATION, Component.class, msgs);
				case UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER:
					return eContainer.eInverseRemove(this, UML2Package.BEHAVIORED_CLASSIFIER__IMPLEMENTATION, BehavioredClassifier.class, msgs);
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
			case UML2Package.IMPLEMENTATION__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.IMPLEMENTATION__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.IMPLEMENTATION__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.IMPLEMENTATION__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.IMPLEMENTATION__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.IMPLEMENTATION__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.IMPLEMENTATION__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.IMPLEMENTATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.IMPLEMENTATION__UUID:
				return getUuid();
			case UML2Package.IMPLEMENTATION__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.IMPLEMENTATION__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.IMPLEMENTATION__NAME:
				return getName();
			case UML2Package.IMPLEMENTATION__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.IMPLEMENTATION__VISIBILITY:
				return getVisibility();
			case UML2Package.IMPLEMENTATION__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.IMPLEMENTATION__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.IMPLEMENTATION__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.IMPLEMENTATION__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.IMPLEMENTATION__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.IMPLEMENTATION__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.IMPLEMENTATION__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.IMPLEMENTATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.IMPLEMENTATION__RELATED_ELEMENT:
				return getRelatedElements();
			case UML2Package.IMPLEMENTATION__SOURCE:
				return getSources();
			case UML2Package.IMPLEMENTATION__TARGET:
				return getTargets();
			case UML2Package.IMPLEMENTATION__CLIENT:
				return getClients();
			case UML2Package.IMPLEMENTATION__SUPPLIER:
				return getSuppliers();
			case UML2Package.IMPLEMENTATION__DEPENDENCY_TARGET:
				if (resolve) return getDependencyTarget();
				return basicGetDependencyTarget();
			case UML2Package.IMPLEMENTATION__RESEMBLANCE:
				return isResemblance() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.IMPLEMENTATION__REPLACEMENT:
				return isReplacement() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.IMPLEMENTATION__TRACE:
				return isTrace() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.IMPLEMENTATION__MAPPING:
				return getMapping();
			case UML2Package.IMPLEMENTATION__ABSTRACTION:
				return getAbstraction();
			case UML2Package.IMPLEMENTATION__REALIZING_CLASSIFIER:
				if (resolve) return getRealizingClassifier();
				return basicGetRealizingClassifier();
			case UML2Package.IMPLEMENTATION__CONTRACT:
				if (resolve) return getContract();
				return basicGetContract();
			case UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER:
				return getImplementingClassifier();
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
			case UML2Package.IMPLEMENTATION__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.IMPLEMENTATION__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.IMPLEMENTATION__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.IMPLEMENTATION__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.IMPLEMENTATION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.IMPLEMENTATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.IMPLEMENTATION__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.IMPLEMENTATION__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.IMPLEMENTATION__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.IMPLEMENTATION__NAME:
				setName((String)newValue);
				return;
			case UML2Package.IMPLEMENTATION__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.IMPLEMENTATION__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.IMPLEMENTATION__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.IMPLEMENTATION__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.IMPLEMENTATION__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.IMPLEMENTATION__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.IMPLEMENTATION__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.IMPLEMENTATION__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.IMPLEMENTATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.IMPLEMENTATION__CLIENT:
				getClients().clear();
				getClients().addAll((Collection)newValue);
				return;
			case UML2Package.IMPLEMENTATION__SUPPLIER:
				getSuppliers().clear();
				getSuppliers().addAll((Collection)newValue);
				return;
			case UML2Package.IMPLEMENTATION__DEPENDENCY_TARGET:
				setDependencyTarget((NamedElement)newValue);
				return;
			case UML2Package.IMPLEMENTATION__RESEMBLANCE:
				setResemblance(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.IMPLEMENTATION__REPLACEMENT:
				setReplacement(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.IMPLEMENTATION__TRACE:
				setTrace(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.IMPLEMENTATION__MAPPING:
				setMapping((OpaqueExpression)newValue);
				return;
			case UML2Package.IMPLEMENTATION__ABSTRACTION:
				setAbstraction((Component)newValue);
				return;
			case UML2Package.IMPLEMENTATION__REALIZING_CLASSIFIER:
				setRealizingClassifier((Classifier)newValue);
				return;
			case UML2Package.IMPLEMENTATION__CONTRACT:
				setContract((Interface)newValue);
				return;
			case UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER:
				setImplementingClassifier((BehavioredClassifier)newValue);
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
			case UML2Package.IMPLEMENTATION__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.IMPLEMENTATION__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.IMPLEMENTATION__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.IMPLEMENTATION__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.IMPLEMENTATION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.IMPLEMENTATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.IMPLEMENTATION__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.IMPLEMENTATION__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.IMPLEMENTATION__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.IMPLEMENTATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.IMPLEMENTATION__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.IMPLEMENTATION__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.IMPLEMENTATION__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.IMPLEMENTATION__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.IMPLEMENTATION__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.IMPLEMENTATION__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.IMPLEMENTATION__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.IMPLEMENTATION__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.IMPLEMENTATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.IMPLEMENTATION__CLIENT:
				getClients().clear();
				return;
			case UML2Package.IMPLEMENTATION__SUPPLIER:
				getSuppliers().clear();
				return;
			case UML2Package.IMPLEMENTATION__DEPENDENCY_TARGET:
				setDependencyTarget((NamedElement)null);
				return;
			case UML2Package.IMPLEMENTATION__RESEMBLANCE:
				setResemblance(RESEMBLANCE_EDEFAULT);
				return;
			case UML2Package.IMPLEMENTATION__REPLACEMENT:
				setReplacement(REPLACEMENT_EDEFAULT);
				return;
			case UML2Package.IMPLEMENTATION__TRACE:
				setTrace(TRACE_EDEFAULT);
				return;
			case UML2Package.IMPLEMENTATION__MAPPING:
				setMapping((OpaqueExpression)null);
				return;
			case UML2Package.IMPLEMENTATION__ABSTRACTION:
				setAbstraction((Component)null);
				return;
			case UML2Package.IMPLEMENTATION__REALIZING_CLASSIFIER:
				setRealizingClassifier((Classifier)null);
				return;
			case UML2Package.IMPLEMENTATION__CONTRACT:
				setContract((Interface)null);
				return;
			case UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER:
				setImplementingClassifier((BehavioredClassifier)null);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSetGen(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.IMPLEMENTATION__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.IMPLEMENTATION__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.IMPLEMENTATION__OWNER:
				return basicGetOwner() != null;
			case UML2Package.IMPLEMENTATION__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.IMPLEMENTATION__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.IMPLEMENTATION__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.IMPLEMENTATION__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.IMPLEMENTATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.IMPLEMENTATION__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.IMPLEMENTATION__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.IMPLEMENTATION__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.IMPLEMENTATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.IMPLEMENTATION__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.IMPLEMENTATION__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.IMPLEMENTATION__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.IMPLEMENTATION__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.IMPLEMENTATION__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.IMPLEMENTATION__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.IMPLEMENTATION__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.IMPLEMENTATION__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.IMPLEMENTATION__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.IMPLEMENTATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.IMPLEMENTATION__RELATED_ELEMENT:
				return !getRelatedElements().isEmpty();
			case UML2Package.IMPLEMENTATION__SOURCE:
				return !getSources().isEmpty();
			case UML2Package.IMPLEMENTATION__TARGET:
				return !getTargets().isEmpty();
			case UML2Package.IMPLEMENTATION__CLIENT:
				return client != null && !client.isEmpty();
			case UML2Package.IMPLEMENTATION__SUPPLIER:
				return supplier != null && !supplier.isEmpty();
			case UML2Package.IMPLEMENTATION__DEPENDENCY_TARGET:
				return dependencyTarget != null;
			case UML2Package.IMPLEMENTATION__RESEMBLANCE:
				return ((eFlags & RESEMBLANCE_EFLAG) != 0) != RESEMBLANCE_EDEFAULT;
			case UML2Package.IMPLEMENTATION__REPLACEMENT:
				return ((eFlags & REPLACEMENT_EFLAG) != 0) != REPLACEMENT_EDEFAULT;
			case UML2Package.IMPLEMENTATION__TRACE:
				return ((eFlags & TRACE_EFLAG) != 0) != TRACE_EDEFAULT;
			case UML2Package.IMPLEMENTATION__MAPPING:
				return mapping != null;
			case UML2Package.IMPLEMENTATION__ABSTRACTION:
				return getAbstraction() != null;
			case UML2Package.IMPLEMENTATION__REALIZING_CLASSIFIER:
				return realizingClassifier != null;
			case UML2Package.IMPLEMENTATION__CONTRACT:
				return contract != null;
			case UML2Package.IMPLEMENTATION__IMPLEMENTING_CLASSIFIER:
				return getImplementingClassifier() != null;
		}
		return eDynamicIsSet(eFeature);
	}


	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.IMPLEMENTATION__VISIBILITY:
				return false;
			case UML2Package.IMPLEMENTATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
		}
		return eIsSetGen(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getTargetsHelper(EList target) {
		super.getTargetsHelper(target);
		if (eIsSet(UML2Package.eINSTANCE.getImplementation_Contract())) {
			target.add(getContract());
		}
		return target;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getSourcesHelper(EList source) {
		super.getSourcesHelper(source);
		BehavioredClassifier implementingClassifier = getImplementingClassifier();
		if (implementingClassifier != null) {
			source.add(implementingClassifier);
		}
		return source;
	}


} //ImplementationImpl
