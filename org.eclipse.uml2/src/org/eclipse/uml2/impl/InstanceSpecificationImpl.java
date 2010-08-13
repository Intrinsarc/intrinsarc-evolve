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
 * $Id: InstanceSpecificationImpl.java,v 1.1 2009-03-04 23:06:36 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Classifier;
import org.eclipse.uml2.Dependency;
import org.eclipse.uml2.DeployedArtifact;
import org.eclipse.uml2.Deployment;
import org.eclipse.uml2.DeploymentTarget;
import org.eclipse.uml2.InstanceSpecification;
import org.eclipse.uml2.PackageableElement;
import org.eclipse.uml2.PortRemap;
import org.eclipse.uml2.Slot;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.ValueSpecification;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.SubsetEObjectContainmentWithInverseEList;
import org.eclipse.uml2.common.util.SupersetEObjectWithInverseResolvingEList;

import org.eclipse.uml2.internal.operation.DeploymentTargetOperations;
import org.eclipse.uml2.internal.operation.InstanceSpecificationOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Instance Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.InstanceSpecificationImpl#getClientDependencies <em>Client Dependency</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InstanceSpecificationImpl#getDeployments <em>Deployment</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InstanceSpecificationImpl#getDeployedElements <em>Deployed Element</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InstanceSpecificationImpl#getSlots <em>Slot</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InstanceSpecificationImpl#getClassifiers <em>Classifier</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InstanceSpecificationImpl#getSpecification <em>Specification</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InstanceSpecificationImpl#getPortRemaps <em>Port Remap</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstanceSpecificationImpl extends PackageableElementImpl implements InstanceSpecification {
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
	 * The cached value of the '{@link #getSlots() <em>Slot</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlots()
	 * @generated
	 * @ordered
	 */
	protected EList slot = null;

	/**
	 * The cached value of the '{@link #getClassifiers() <em>Classifier</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassifiers()
	 * @generated
	 * @ordered
	 */
	protected EList classifier = null;

	/**
	 * The cached value of the '{@link #getSpecification() <em>Specification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecification()
	 * @generated
	 * @ordered
	 */
	protected ValueSpecification specification = null;

	/**
	 * The cached value of the '{@link #getPortRemaps() <em>Port Remap</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getPortRemaps()
	 * @generated
	 * @ordered
	 */
  protected EList portRemap = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstanceSpecificationImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (InstanceSpecificationImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getInstanceSpecification();
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
			 		deployment = new com.intrinsarc.emflist.PersistentEList(Deployment.class, this, UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT, new int[] {UML2Package.INSTANCE_SPECIFICATION__CLIENT_DEPENDENCY}, UML2Package.DEPLOYMENT__LOCATION);
			 		return deployment;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Deployment.class, this, UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT, new int[] {UML2Package.INSTANCE_SPECIFICATION__CLIENT_DEPENDENCY}, UML2Package.DEPLOYMENT__LOCATION);
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
			deployment = new com.intrinsarc.emflist.PersistentEList(Deployment.class, this, UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT, new int[] {UML2Package.INSTANCE_SPECIFICATION__CLIENT_DEPENDENCY}, UML2Package.DEPLOYMENT__LOCATION);
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT, null, newDeployment));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT, null, newDeployment));
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
	public EList getSlots() {
		if (slot == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		slot = new com.intrinsarc.emflist.PersistentEList(Slot.class, this, UML2Package.INSTANCE_SPECIFICATION__SLOT, UML2Package.SLOT__OWNING_INSTANCE);
			 		return slot;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Slot.class, this, UML2Package.INSTANCE_SPECIFICATION__SLOT, UML2Package.SLOT__OWNING_INSTANCE);
		}      
		return slot;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getSlots() {
		if (slot == null) {
			slot = new com.intrinsarc.emflist.PersistentEList(Slot.class, this, UML2Package.INSTANCE_SPECIFICATION__SLOT, UML2Package.SLOT__OWNING_INSTANCE);
		}
		return slot;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getSlots() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (slot != null) {
			for (Object object : slot) {
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
	 * @deprecated Use #createSlot() instead.
	 */
	public Slot createSlot(EClass eClass) {
		Slot newSlot = (Slot) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INSTANCE_SPECIFICATION__SLOT, null, newSlot));
		}
		getSlots().add(newSlot);
		return newSlot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Slot createSlot() {
		Slot newSlot = UML2Factory.eINSTANCE.createSlot();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INSTANCE_SPECIFICATION__SLOT, null, newSlot));
		}
		settable_getSlots().add(newSlot);
		return newSlot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getClassifiers() {
		if (classifier == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		classifier = new com.intrinsarc.emflist.PersistentEList(Classifier.class, this, UML2Package.INSTANCE_SPECIFICATION__CLASSIFIER);
			 		return classifier;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Classifier.class, this, UML2Package.INSTANCE_SPECIFICATION__CLASSIFIER);
		}      
		return classifier;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getClassifiers() {
		if (classifier == null) {
			classifier = new com.intrinsarc.emflist.PersistentEList(Classifier.class, this, UML2Package.INSTANCE_SPECIFICATION__CLASSIFIER);
		}
		return classifier;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getClassifiers() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (classifier != null) {
			for (Object object : classifier) {
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
    public Classifier getClassifier(String name) {
		for (Iterator i = getClassifiers().iterator(); i.hasNext(); ) {
			Classifier classifier = (Classifier) i.next();
			if (name.equals(classifier.getName())) {
				return classifier;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueSpecification getSpecification() {
		return specification;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public ValueSpecification undeleted_getSpecification() {
		ValueSpecification temp = getSpecification();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSpecification(ValueSpecification newSpecification, NotificationChain msgs) {
		ValueSpecification oldSpecification = specification;
		specification = newSpecification;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.INSTANCE_SPECIFICATION__SPECIFICATION, oldSpecification, newSpecification);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpecification(ValueSpecification newSpecification) {
		if (newSpecification != specification) {
			NotificationChain msgs = null;
			if (specification != null)
				msgs = ((InternalEObject)specification).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.INSTANCE_SPECIFICATION__SPECIFICATION, null, msgs);
			if (newSpecification != null)
				msgs = ((InternalEObject)newSpecification).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UML2Package.INSTANCE_SPECIFICATION__SPECIFICATION, null, msgs);
			msgs = basicSetSpecification(newSpecification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.INSTANCE_SPECIFICATION__SPECIFICATION, newSpecification, newSpecification));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueSpecification createSpecification(EClass eClass) {
		ValueSpecification newSpecification = (ValueSpecification) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INSTANCE_SPECIFICATION__SPECIFICATION, null, newSpecification));
		}
		setSpecification(newSpecification);
		return newSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList getPortRemaps() {
		if (portRemap == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		portRemap = new com.intrinsarc.emflist.PersistentEList(PortRemap.class, this, UML2Package.INSTANCE_SPECIFICATION__PORT_REMAP);
			 		return portRemap;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(PortRemap.class, this, UML2Package.INSTANCE_SPECIFICATION__PORT_REMAP);
		}      
		return portRemap;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getPortRemaps() {
		if (portRemap == null) {
			portRemap = new com.intrinsarc.emflist.PersistentEList(PortRemap.class, this, UML2Package.INSTANCE_SPECIFICATION__PORT_REMAP);
		}
		return portRemap;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getPortRemaps() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (portRemap != null) {
			for (Object object : portRemap) {
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
	public PortRemap createPortRemap(EClass eClass) {
		PortRemap newPortRemap = (PortRemap) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INSTANCE_SPECIFICATION__PORT_REMAP, null, newPortRemap));
		}
		settable_getPortRemaps().add(newPortRemap);
		return newPortRemap;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public PortRemap createPortRemap() {
		PortRemap newPortRemap = UML2Factory.eINSTANCE.createPortRemap();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INSTANCE_SPECIFICATION__PORT_REMAP, null, newPortRemap));
		}
		settable_getPortRemaps().add(newPortRemap);
		return newPortRemap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSlotsAreDefined(DiagnosticChain diagnostics, Map context) {
		return InstanceSpecificationOperations.validateSlotsAreDefined(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNoDuplicateSlots(DiagnosticChain diagnostics, Map context) {
		return InstanceSpecificationOperations.validateNoDuplicateSlots(this, diagnostics, context);
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
			 		clientDependency = new com.intrinsarc.emflist.PersistentEList(Dependency.class, this, UML2Package.INSTANCE_SPECIFICATION__CLIENT_DEPENDENCY, new int[] {UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT}, UML2Package.DEPENDENCY__CLIENT);
			 		return clientDependency;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Dependency.class, this, UML2Package.INSTANCE_SPECIFICATION__CLIENT_DEPENDENCY, new int[] {UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT}, UML2Package.DEPENDENCY__CLIENT);
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
			clientDependency = new com.intrinsarc.emflist.PersistentEList(Dependency.class, this, UML2Package.INSTANCE_SPECIFICATION__CLIENT_DEPENDENCY, new int[] {UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT}, UML2Package.DEPENDENCY__CLIENT);
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
				case UML2Package.INSTANCE_SPECIFICATION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.INSTANCE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.INSTANCE_SPECIFICATION__OWNING_PARAMETER, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT:
					return ((InternalEList)getDeployments()).basicAdd(otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__SLOT:
					return ((InternalEList)getSlots()).basicAdd(otherEnd, msgs);
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
				case UML2Package.INSTANCE_SPECIFICATION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.INSTANCE_SPECIFICATION__OWNING_PARAMETER, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT:
					return ((InternalEList)getDeployments()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__SLOT:
					return ((InternalEList)getSlots()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__SPECIFICATION:
					return basicSetSpecification(null, msgs);
				case UML2Package.INSTANCE_SPECIFICATION__PORT_REMAP:
					return ((InternalEList)getPortRemaps()).basicRemove(otherEnd, msgs);
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
				case UML2Package.INSTANCE_SPECIFICATION__OWNING_PARAMETER:
					return eContainer.eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__OWNED_PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
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
			case UML2Package.INSTANCE_SPECIFICATION__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.INSTANCE_SPECIFICATION__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.INSTANCE_SPECIFICATION__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.INSTANCE_SPECIFICATION__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.INSTANCE_SPECIFICATION__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.INSTANCE_SPECIFICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.INSTANCE_SPECIFICATION__UUID:
				return getUuid();
			case UML2Package.INSTANCE_SPECIFICATION__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.INSTANCE_SPECIFICATION__NAME:
				return getName();
			case UML2Package.INSTANCE_SPECIFICATION__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.INSTANCE_SPECIFICATION__VISIBILITY:
				return getVisibility();
			case UML2Package.INSTANCE_SPECIFICATION__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.INSTANCE_SPECIFICATION__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.INSTANCE_SPECIFICATION__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.INSTANCE_SPECIFICATION__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.INSTANCE_SPECIFICATION__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.INSTANCE_SPECIFICATION__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.INSTANCE_SPECIFICATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT:
				return getDeployments();
			case UML2Package.INSTANCE_SPECIFICATION__DEPLOYED_ELEMENT:
				return getDeployedElements();
			case UML2Package.INSTANCE_SPECIFICATION__SLOT:
				return getSlots();
			case UML2Package.INSTANCE_SPECIFICATION__CLASSIFIER:
				return getClassifiers();
			case UML2Package.INSTANCE_SPECIFICATION__SPECIFICATION:
				return getSpecification();
			case UML2Package.INSTANCE_SPECIFICATION__PORT_REMAP:
				return getPortRemaps();
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
			case UML2Package.INSTANCE_SPECIFICATION__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.INSTANCE_SPECIFICATION__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__NAME:
				setName((String)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT:
				getDeployments().clear();
				getDeployments().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__SLOT:
				getSlots().clear();
				getSlots().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__CLASSIFIER:
				getClassifiers().clear();
				getClassifiers().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__SPECIFICATION:
				setSpecification((ValueSpecification)newValue);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__PORT_REMAP:
				getPortRemaps().clear();
				getPortRemaps().addAll((Collection)newValue);
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
			case UML2Package.INSTANCE_SPECIFICATION__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.INSTANCE_SPECIFICATION__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.INSTANCE_SPECIFICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.INSTANCE_SPECIFICATION__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.INSTANCE_SPECIFICATION__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.INSTANCE_SPECIFICATION__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.INSTANCE_SPECIFICATION__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.INSTANCE_SPECIFICATION__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT:
				getDeployments().clear();
				return;
			case UML2Package.INSTANCE_SPECIFICATION__SLOT:
				getSlots().clear();
				return;
			case UML2Package.INSTANCE_SPECIFICATION__CLASSIFIER:
				getClassifiers().clear();
				return;
			case UML2Package.INSTANCE_SPECIFICATION__SPECIFICATION:
				setSpecification((ValueSpecification)null);
				return;
			case UML2Package.INSTANCE_SPECIFICATION__PORT_REMAP:
				getPortRemaps().clear();
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
			case UML2Package.INSTANCE_SPECIFICATION__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__OWNER:
				return basicGetOwner() != null;
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.INSTANCE_SPECIFICATION__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.INSTANCE_SPECIFICATION__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.INSTANCE_SPECIFICATION__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.INSTANCE_SPECIFICATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.INSTANCE_SPECIFICATION__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.INSTANCE_SPECIFICATION__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.INSTANCE_SPECIFICATION__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.INSTANCE_SPECIFICATION__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.INSTANCE_SPECIFICATION__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.INSTANCE_SPECIFICATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT:
				return deployment != null && !deployment.isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__DEPLOYED_ELEMENT:
				return !getDeployedElements().isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__SLOT:
				return slot != null && !slot.isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__CLASSIFIER:
				return classifier != null && !classifier.isEmpty();
			case UML2Package.INSTANCE_SPECIFICATION__SPECIFICATION:
				return specification != null;
			case UML2Package.INSTANCE_SPECIFICATION__PORT_REMAP:
				return portRemap != null && !portRemap.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.INSTANCE_SPECIFICATION__VISIBILITY:
				return false;
			case UML2Package.INSTANCE_SPECIFICATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
		}
		return eIsSetGen(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class baseClass) {
		if (baseClass == DeploymentTarget.class) {
			switch (derivedFeatureID) {
				case UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT: return UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT;
				case UML2Package.INSTANCE_SPECIFICATION__DEPLOYED_ELEMENT: return UML2Package.DEPLOYMENT_TARGET__DEPLOYED_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == DeployedArtifact.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class baseClass) {
		if (baseClass == DeploymentTarget.class) {
			switch (baseFeatureID) {
				case UML2Package.DEPLOYMENT_TARGET__DEPLOYMENT: return UML2Package.INSTANCE_SPECIFICATION__DEPLOYMENT;
				case UML2Package.DEPLOYMENT_TARGET__DEPLOYED_ELEMENT: return UML2Package.INSTANCE_SPECIFICATION__DEPLOYED_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == DeployedArtifact.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		if (eIsSet(UML2Package.eINSTANCE.getInstanceSpecification_Slot())) {
			ownedElement.addAll(getSlots());
		}
		if (eIsSet(UML2Package.eINSTANCE.getInstanceSpecification_Specification())) {
			ownedElement.add(getSpecification());
		}
		return ownedElement;
	}


} //InstanceSpecificationImpl
