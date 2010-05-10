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
 * $Id: DeploymentTargetImpl.java,v 1.1 2009-03-04 23:06:35 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;
import java.util.Iterator;

import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Dependency;
import org.eclipse.uml2.Deployment;
import org.eclipse.uml2.DeploymentTarget;
import org.eclipse.uml2.PackageableElement;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.SubsetEObjectContainmentWithInverseEList;
import org.eclipse.uml2.common.util.SupersetEObjectWithInverseResolvingEList;

import org.eclipse.uml2.internal.operation.DeploymentTargetOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Deployment Target</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.DeploymentTargetImpl#getClientDependencies <em>Client Dependency</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DeploymentTargetImpl#getDeployments <em>Deployment</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DeploymentTargetImpl#getDeployedElements <em>Deployed Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DeploymentTargetImpl extends NamedElementImpl implements DeploymentTarget {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getDeployments() <em>Deployment</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeployments()
	 * @generated
	 * @ordered
	 */
	protected EList deployment = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeploymentTargetImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (DeploymentTargetImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getDeploymentTarget();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeployments() {
		if (deployment == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		deployment = new com.hopstepjump.emflist.PersistentEList(Deployment.class, this, UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT, new int[] {UML2Package.DEPLOYMENT_TARGET__CLIENT_DEPENDENCY}, UML2Package.DEPLOYMENT__LOCATION);
			 		return deployment;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(Deployment.class, this, UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT, new int[] {UML2Package.DEPLOYMENT_TARGET__CLIENT_DEPENDENCY}, UML2Package.DEPLOYMENT__LOCATION);
		}      
		return deployment;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getDeployments() {
		if (deployment == null) {
			deployment = new com.hopstepjump.emflist.PersistentEList(Deployment.class, this, UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT, new int[] {UML2Package.DEPLOYMENT_TARGET__CLIENT_DEPENDENCY}, UML2Package.DEPLOYMENT__LOCATION);
		}
		return deployment;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getDeployments() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deployment != null) {
			for (Object object : deployment) {
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
    public Deployment getDeployment(String name) {
		for (Iterator i = getDeployments().iterator(); i.hasNext(); ) {
			Deployment deployment = (Deployment) i.next();
			if (name.equals(deployment.getName())) {
				return deployment;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createDeployment() instead.
	 */
	public Deployment createDeployment(EClass eClass) {
		Deployment newDeployment = (Deployment) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT, null, newDeployment));
		}
		getDeployments().add(newDeployment);
		return newDeployment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Deployment createDeployment() {
		Deployment newDeployment = UML2Factory.eINSTANCE.createDeployment();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT, null, newDeployment));
		}
		settable_getDeployments().add(newDeployment);
		return newDeployment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getDeployedElements() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			EList result = (EList) cache.get(this, UML2Package.eINSTANCE
				.getDeploymentTarget_DeployedElement());

			if (result == null) {
				EList deployedElements = DeploymentTargetOperations
					.getDeployedElements(this);
				cache.put(this, UML2Package.eINSTANCE
					.getDeploymentTarget_DeployedElement(),
					result = new EcoreEList.UnmodifiableEList(this,
						UML2Package.eINSTANCE
							.getDeploymentTarget_DeployedElement(),
						deployedElements.size(), deployedElements.toArray()));
			}

			return result;
		}

		EList deployedElements = DeploymentTargetOperations
			.getDeployedElements(this);
		return new EcoreEList.UnmodifiableEList(this, UML2Package.eINSTANCE
			.getDeploymentTarget_DeployedElement(), deployedElements.size(),
			deployedElements.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public PackageableElement getDeployedElement(String name) {
		for (Iterator i = getDeployedElements().iterator(); i.hasNext(); ) {
			PackageableElement deployedElement = (PackageableElement) i.next();
			if (name.equals(deployedElement.getName())) {
				return deployedElement;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getClientDependencies() {
		if (clientDependency == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		clientDependency = new com.hopstepjump.emflist.PersistentEList(Dependency.class, this, UML2Package.DEPLOYMENT_TARGET__CLIENT_DEPENDENCY, new int[] {UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT}, UML2Package.DEPENDENCY__CLIENT);
			 		return clientDependency;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(Dependency.class, this, UML2Package.DEPLOYMENT_TARGET__CLIENT_DEPENDENCY, new int[] {UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT}, UML2Package.DEPENDENCY__CLIENT);
		}      
		return clientDependency;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getClientDependencies() {
		if (clientDependency == null) {
			clientDependency = new com.hopstepjump.emflist.PersistentEList(Dependency.class, this, UML2Package.DEPLOYMENT_TARGET__CLIENT_DEPENDENCY, new int[] {UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT}, UML2Package.DEPENDENCY__CLIENT);
		}
		return clientDependency;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getClientDependencies() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (clientDependency != null) {
			for (Object object : clientDependency) {
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
				case UML2Package.DEPLOYMENT_TARGET__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.DEPLOYMENT_TARGET__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.DEPLOYMENT_TARGET__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.DEPLOYMENT_TARGET__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.DEPLOYMENT_TARGET__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT:
					return ((InternalEList)getDeployments()).basicAdd(otherEnd, msgs);
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
				case UML2Package.DEPLOYMENT_TARGET__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT_TARGET__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT_TARGET__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT_TARGET__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT_TARGET__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.DEPLOYMENT_TARGET__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT_TARGET__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.DEPLOYMENT_TARGET__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT:
					return ((InternalEList)getDeployments()).basicRemove(otherEnd, msgs);
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
			case UML2Package.DEPLOYMENT_TARGET__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.DEPLOYMENT_TARGET__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.DEPLOYMENT_TARGET__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.DEPLOYMENT_TARGET__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.DEPLOYMENT_TARGET__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.DEPLOYMENT_TARGET__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.DEPLOYMENT_TARGET__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.DEPLOYMENT_TARGET__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.DEPLOYMENT_TARGET__UUID:
				return getUuid();
			case UML2Package.DEPLOYMENT_TARGET__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.DEPLOYMENT_TARGET__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.DEPLOYMENT_TARGET__NAME:
				return getName();
			case UML2Package.DEPLOYMENT_TARGET__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.DEPLOYMENT_TARGET__VISIBILITY:
				return getVisibility();
			case UML2Package.DEPLOYMENT_TARGET__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.DEPLOYMENT_TARGET__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.DEPLOYMENT_TARGET__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.DEPLOYMENT_TARGET__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.DEPLOYMENT_TARGET__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT:
				return getDeployments();
			case UML2Package.DEPLOYMENT_TARGET__DEPLOYED_ELEMENT:
				return getDeployedElements();
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
			case UML2Package.DEPLOYMENT_TARGET__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.DEPLOYMENT_TARGET__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__NAME:
				setName((String)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT:
				getDeployments().clear();
				getDeployments().addAll((Collection)newValue);
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
			case UML2Package.DEPLOYMENT_TARGET__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.DEPLOYMENT_TARGET__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.DEPLOYMENT_TARGET__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT_TARGET__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT_TARGET__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.DEPLOYMENT_TARGET__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.DEPLOYMENT_TARGET__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT_TARGET__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.DEPLOYMENT_TARGET__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.DEPLOYMENT_TARGET__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT_TARGET__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT_TARGET__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.DEPLOYMENT_TARGET__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.DEPLOYMENT_TARGET__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.DEPLOYMENT_TARGET__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.DEPLOYMENT_TARGET__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT:
				getDeployments().clear();
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
			case UML2Package.DEPLOYMENT_TARGET__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.DEPLOYMENT_TARGET__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.DEPLOYMENT_TARGET__OWNER:
				return basicGetOwner() != null;
			case UML2Package.DEPLOYMENT_TARGET__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.DEPLOYMENT_TARGET__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.DEPLOYMENT_TARGET__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.DEPLOYMENT_TARGET__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.DEPLOYMENT_TARGET__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.DEPLOYMENT_TARGET__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.DEPLOYMENT_TARGET__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.DEPLOYMENT_TARGET__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.DEPLOYMENT_TARGET__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.DEPLOYMENT_TARGET__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.DEPLOYMENT_TARGET__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.DEPLOYMENT_TARGET__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.DEPLOYMENT_TARGET__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.DEPLOYMENT_TARGET__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.DEPLOYMENT_TARGET__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.DEPLOYMENT_TARGET__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT:
				return deployment != null && !deployment.isEmpty();
			case UML2Package.DEPLOYMENT_TARGET__DEPLOYED_ELEMENT:
				return !getDeployedElements().isEmpty();
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
		if (eIsSet(UML2Package.eINSTANCE.getDeploymentTarget_Deployment())) {
			ownedElement.addAll(getDeployments());
		}
		return ownedElement;
	}


} //DeploymentTargetImpl
