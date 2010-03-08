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
 * $Id: DeploymentImpl.java,v 1.1 2009-03-04 23:06:36 andrew Exp $
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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.DeployedArtifact;
import org.eclipse.uml2.Deployment;
import org.eclipse.uml2.DeploymentSpecification;
import org.eclipse.uml2.DeploymentTarget;
import org.eclipse.uml2.NamedElement;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.SubsetEObjectResolvingEList;
import org.eclipse.uml2.common.util.SupersetEObjectResolvingEList;
import org.eclipse.uml2.common.util.SupersetEObjectWithInverseResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Deployment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.DeploymentImpl#getSuppliers <em>Supplier</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DeploymentImpl#getClients <em>Client</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DeploymentImpl#getDeployedArtifacts <em>Deployed Artifact</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DeploymentImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DeploymentImpl#getConfigurations <em>Configuration</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeploymentImpl extends DependencyImpl implements Deployment {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getDeployedArtifacts() <em>Deployed Artifact</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeployedArtifacts()
	 * @generated
	 * @ordered
	 */
	protected EList deployedArtifact = null;

	/**
	 * The cached value of the '{@link #getConfigurations() <em>Configuration</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurations()
	 * @generated
	 * @ordered
	 */
	protected EList configuration = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeploymentImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (DeploymentImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getDeployment();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeployedArtifacts()
	{
		if (deployedArtifact == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		deployedArtifact = new com.hopstepjump.emflist.PersistentEList(DeployedArtifact.class, this, UML2Package.DEPLOYMENT__DEPLOYED_ARTIFACT, new int[] {UML2Package.DEPLOYMENT__SUPPLIER});
			 		return deployedArtifact;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(DeployedArtifact.class, this, UML2Package.DEPLOYMENT__DEPLOYED_ARTIFACT, new int[] {UML2Package.DEPLOYMENT__SUPPLIER});
		}      
		return deployedArtifact;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getDeployedArtifacts()
	{
		if (deployedArtifact == null)
		{
			
		
			deployedArtifact = new com.hopstepjump.emflist.PersistentEList(DeployedArtifact.class, this, UML2Package.DEPLOYMENT__DEPLOYED_ARTIFACT, new int[] {UML2Package.DEPLOYMENT__SUPPLIER});
		}
		return deployedArtifact;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getDeployedArtifacts()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deployedArtifact != null)
		{
			for (Object object : deployedArtifact)
			{
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
    public DeployedArtifact getDeployedArtifact(String name) {
		for (Iterator i = getDeployedArtifacts().iterator(); i.hasNext(); ) {
			DeployedArtifact deployedArtifact = (DeployedArtifact) i.next();
			if (name.equals(deployedArtifact.getName())) {
				return deployedArtifact;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentTarget getLocation()
	{
		if (eContainerFeatureID != UML2Package.DEPLOYMENT__LOCATION) return null;
		return (DeploymentTarget)eContainer;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public DeploymentTarget undeleted_getLocation()
	{
		DeploymentTarget temp = getLocation();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(DeploymentTarget newLocation)
	{

		if (newLocation != null && !getClients().contains(newLocation)) {
			getClients().add(newLocation);
		}
		if (newLocation != eContainer || (eContainerFeatureID != UML2Package.DEPLOYMENT__LOCATION && newLocation != null))
		{
			if (EcoreUtil.isAncestor(this, newLocation))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newLocation != null)
				msgs = ((InternalEObject)newLocation).eInverseAdd(this, UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT, DeploymentTarget.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newLocation, UML2Package.DEPLOYMENT__LOCATION, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.DEPLOYMENT__LOCATION, newLocation, newLocation));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getConfigurations()
	{
		if (configuration == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		configuration = new com.hopstepjump.emflist.PersistentEList(DeploymentSpecification.class, this, UML2Package.DEPLOYMENT__CONFIGURATION);
			 		return configuration;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(DeploymentSpecification.class, this, UML2Package.DEPLOYMENT__CONFIGURATION);
		}      
		return configuration;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getConfigurations()
	{
		if (configuration == null)
		{
			
		
			configuration = new com.hopstepjump.emflist.PersistentEList(DeploymentSpecification.class, this, UML2Package.DEPLOYMENT__CONFIGURATION);
		}
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getConfigurations()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (configuration != null)
		{
			for (Object object : configuration)
			{
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
    public DeploymentSpecification getConfiguration(String name) {
		for (Iterator i = getConfigurations().iterator(); i.hasNext(); ) {
			DeploymentSpecification configuration = (DeploymentSpecification) i.next();
			if (name.equals(configuration.getName())) {
				return configuration;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createConfiguration() instead.
	 */
	public DeploymentSpecification createConfiguration(EClass eClass) {
		DeploymentSpecification newConfiguration = (DeploymentSpecification) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.DEPLOYMENT__CONFIGURATION, null, newConfiguration));
		}
		getConfigurations().add(newConfiguration);
		return newConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentSpecification createConfiguration() {
		DeploymentSpecification newConfiguration = UML2Factory.eINSTANCE.createDeploymentSpecification();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.DEPLOYMENT__CONFIGURATION, null, newConfiguration));
		}
		settable_getConfigurations().add(newConfiguration);
		return newConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getSuppliers()
	{
		if (supplier == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		supplier = new com.hopstepjump.emflist.PersistentEList(NamedElement.class, this, UML2Package.DEPLOYMENT__SUPPLIER, new int[] {UML2Package.DEPLOYMENT__DEPLOYED_ARTIFACT});
			 		return supplier;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(NamedElement.class, this, UML2Package.DEPLOYMENT__SUPPLIER, new int[] {UML2Package.DEPLOYMENT__DEPLOYED_ARTIFACT});
		}      
		return supplier;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getSuppliers()
	{
		if (supplier == null)
		{
			
		
			supplier = new com.hopstepjump.emflist.PersistentEList(NamedElement.class, this, UML2Package.DEPLOYMENT__SUPPLIER, new int[] {UML2Package.DEPLOYMENT__DEPLOYED_ARTIFACT});
		}
		return supplier;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getSuppliers()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (supplier != null)
		{
			for (Object object : supplier)
			{
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
	public EList getClients()
	{
		if (client == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		client = new com.hopstepjump.emflist.PersistentEList(NamedElement.class, this, UML2Package.DEPLOYMENT__CLIENT, new int[] {UML2Package.DEPLOYMENT__LOCATION}, UML2Package.NAMED_ELEMENT__CLIENT_DEPENDENCY);
			 		return client;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(NamedElement.class, this, UML2Package.DEPLOYMENT__CLIENT, new int[] {UML2Package.DEPLOYMENT__LOCATION}, UML2Package.NAMED_ELEMENT__CLIENT_DEPENDENCY);
		}      
		return client;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getClients()
	{
		if (client == null)
		{
			
		
			client = new com.hopstepjump.emflist.PersistentEList(NamedElement.class, this, UML2Package.DEPLOYMENT__CLIENT, new int[] {UML2Package.DEPLOYMENT__LOCATION}, UML2Package.NAMED_ELEMENT__CLIENT_DEPENDENCY);
		}
		return client;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getClients()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (client != null)
		{
			for (Object object : client)
			{
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
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs)
	{
		if (featureID >= 0)
		{
			switch (eDerivedStructuralFeatureID(featureID, baseClass))
			{
				case UML2Package.DEPLOYMENT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.DEPLOYMENT__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.DEPLOYMENT__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.DEPLOYMENT__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.DEPLOYMENT__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.DEPLOYMENT__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.DEPLOYMENT__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.DEPLOYMENT__OWNING_PARAMETER, msgs);
				case UML2Package.DEPLOYMENT__CLIENT:
					return ((InternalEList)getClients()).basicAdd(otherEnd, msgs);
				case UML2Package.DEPLOYMENT__LOCATION:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.DEPLOYMENT__LOCATION, msgs);
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
				case UML2Package.DEPLOYMENT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.DEPLOYMENT__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.DEPLOYMENT__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.DEPLOYMENT__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.DEPLOYMENT__OWNING_PARAMETER, msgs);
				case UML2Package.DEPLOYMENT__CLIENT:
					return ((InternalEList)getClients()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPLOYMENT__LOCATION:
					return eBasicSetContainer(null, UML2Package.DEPLOYMENT__LOCATION, msgs);
				case UML2Package.DEPLOYMENT__CONFIGURATION:
					return ((InternalEList)getConfigurations()).basicRemove(otherEnd, msgs);
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
				case UML2Package.DEPLOYMENT__OWNING_PARAMETER:
					return eContainer.eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__OWNED_PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
				case UML2Package.DEPLOYMENT__LOCATION:
					return eContainer.eInverseRemove(this, UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT, DeploymentTarget.class, msgs);
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
			case UML2Package.DEPLOYMENT__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.DEPLOYMENT__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.DEPLOYMENT__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.DEPLOYMENT__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.DEPLOYMENT__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.DEPLOYMENT__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.DEPLOYMENT__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.DEPLOYMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.DEPLOYMENT__UUID:
				return getUuid();
			case UML2Package.DEPLOYMENT__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.DEPLOYMENT__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.DEPLOYMENT__NAME:
				return getName();
			case UML2Package.DEPLOYMENT__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.DEPLOYMENT__VISIBILITY:
				return getVisibility();
			case UML2Package.DEPLOYMENT__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.DEPLOYMENT__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.DEPLOYMENT__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.DEPLOYMENT__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.DEPLOYMENT__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.DEPLOYMENT__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.DEPLOYMENT__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.DEPLOYMENT__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.DEPLOYMENT__RELATED_ELEMENT:
				return getRelatedElements();
			case UML2Package.DEPLOYMENT__SOURCE:
				return getSources();
			case UML2Package.DEPLOYMENT__TARGET:
				return getTargets();
			case UML2Package.DEPLOYMENT__CLIENT:
				return getClients();
			case UML2Package.DEPLOYMENT__SUPPLIER:
				return getSuppliers();
			case UML2Package.DEPLOYMENT__DEPENDENCY_TARGET:
				if (resolve) return getDependencyTarget();
				return basicGetDependencyTarget();
			case UML2Package.DEPLOYMENT__RESEMBLANCE:
				return isResemblance() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.DEPLOYMENT__REPLACEMENT:
				return isReplacement() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.DEPLOYMENT__DEPLOYED_ARTIFACT:
				return getDeployedArtifacts();
			case UML2Package.DEPLOYMENT__LOCATION:
				return getLocation();
			case UML2Package.DEPLOYMENT__CONFIGURATION:
				return getConfigurations();
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
			case UML2Package.DEPLOYMENT__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.DEPLOYMENT__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.DEPLOYMENT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.DEPLOYMENT__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.DEPLOYMENT__NAME:
				setName((String)newValue);
				return;
			case UML2Package.DEPLOYMENT__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.DEPLOYMENT__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.DEPLOYMENT__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.DEPLOYMENT__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.DEPLOYMENT__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.DEPLOYMENT__CLIENT:
				getClients().clear();
				getClients().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT__SUPPLIER:
				getSuppliers().clear();
				getSuppliers().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT__DEPENDENCY_TARGET:
				setDependencyTarget((NamedElement)newValue);
				return;
			case UML2Package.DEPLOYMENT__RESEMBLANCE:
				setResemblance(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.DEPLOYMENT__REPLACEMENT:
				setReplacement(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.DEPLOYMENT__DEPLOYED_ARTIFACT:
				getDeployedArtifacts().clear();
				getDeployedArtifacts().addAll((Collection)newValue);
				return;
			case UML2Package.DEPLOYMENT__LOCATION:
				setLocation((DeploymentTarget)newValue);
				return;
			case UML2Package.DEPLOYMENT__CONFIGURATION:
				getConfigurations().clear();
				getConfigurations().addAll((Collection)newValue);
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
			case UML2Package.DEPLOYMENT__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.DEPLOYMENT__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.DEPLOYMENT__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.DEPLOYMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.DEPLOYMENT__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.DEPLOYMENT__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.DEPLOYMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.DEPLOYMENT__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.DEPLOYMENT__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.DEPLOYMENT__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.DEPLOYMENT__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.DEPLOYMENT__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.DEPLOYMENT__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.DEPLOYMENT__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT__CLIENT:
				getClients().clear();
				return;
			case UML2Package.DEPLOYMENT__SUPPLIER:
				getSuppliers().clear();
				return;
			case UML2Package.DEPLOYMENT__DEPENDENCY_TARGET:
				setDependencyTarget((NamedElement)null);
				return;
			case UML2Package.DEPLOYMENT__RESEMBLANCE:
				setResemblance(RESEMBLANCE_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT__REPLACEMENT:
				setReplacement(REPLACEMENT_EDEFAULT);
				return;
			case UML2Package.DEPLOYMENT__DEPLOYED_ARTIFACT:
				getDeployedArtifacts().clear();
				return;
			case UML2Package.DEPLOYMENT__LOCATION:
				setLocation((DeploymentTarget)null);
				return;
			case UML2Package.DEPLOYMENT__CONFIGURATION:
				getConfigurations().clear();
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSetGen(EStructuralFeature eFeature)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.DEPLOYMENT__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.DEPLOYMENT__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.DEPLOYMENT__OWNER:
				return basicGetOwner() != null;
			case UML2Package.DEPLOYMENT__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.DEPLOYMENT__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.DEPLOYMENT__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.DEPLOYMENT__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.DEPLOYMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.DEPLOYMENT__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.DEPLOYMENT__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.DEPLOYMENT__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.DEPLOYMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.DEPLOYMENT__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.DEPLOYMENT__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.DEPLOYMENT__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.DEPLOYMENT__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.DEPLOYMENT__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.DEPLOYMENT__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.DEPLOYMENT__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.DEPLOYMENT__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.DEPLOYMENT__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.DEPLOYMENT__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.DEPLOYMENT__RELATED_ELEMENT:
				return !getRelatedElements().isEmpty();
			case UML2Package.DEPLOYMENT__SOURCE:
				return !getSources().isEmpty();
			case UML2Package.DEPLOYMENT__TARGET:
				return !getTargets().isEmpty();
			case UML2Package.DEPLOYMENT__CLIENT:
				return client != null && !client.isEmpty();
			case UML2Package.DEPLOYMENT__SUPPLIER:
				return supplier != null && !supplier.isEmpty();
			case UML2Package.DEPLOYMENT__DEPENDENCY_TARGET:
				return dependencyTarget != null;
			case UML2Package.DEPLOYMENT__RESEMBLANCE:
				return ((eFlags & RESEMBLANCE_EFLAG) != 0) != RESEMBLANCE_EDEFAULT;
			case UML2Package.DEPLOYMENT__REPLACEMENT:
				return ((eFlags & REPLACEMENT_EFLAG) != 0) != REPLACEMENT_EDEFAULT;
			case UML2Package.DEPLOYMENT__DEPLOYED_ARTIFACT:
				return deployedArtifact != null && !deployedArtifact.isEmpty();
			case UML2Package.DEPLOYMENT__LOCATION:
				return getLocation() != null;
			case UML2Package.DEPLOYMENT__CONFIGURATION:
				return configuration != null && !configuration.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}


	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.DEPLOYMENT__VISIBILITY:
				return false;
			case UML2Package.DEPLOYMENT__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
		}
		return eIsSetGen(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getTargetsHelper(EList target)
	{
		super.getTargetsHelper(target);
		if (eIsSet(UML2Package.eINSTANCE.getDeployment_DeployedArtifact())) {
			for (Iterator i = ((InternalEList) getDeployedArtifacts()).basicIterator(); i.hasNext(); ) {
				target.add(i.next());
			}
		}
		return target;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getSourcesHelper(EList source)
	{
		super.getSourcesHelper(source);
		DeploymentTarget location = getLocation();
		if (location != null) {
			source.add(location);
		}
		return source;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedElementsHelper(EList ownedElement)
	{
		super.getOwnedElementsHelper(ownedElement);
		if (eIsSet(UML2Package.eINSTANCE.getDeployment_Configuration())) {
			ownedElement.addAll(getConfigurations());
		}
		return ownedElement;
	}


} //DeploymentImpl
