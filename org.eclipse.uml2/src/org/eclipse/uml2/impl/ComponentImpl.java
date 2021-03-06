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
 * $Id: ComponentImpl.java,v 1.1 2009-03-04 23:06:42 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Behavior;
import org.eclipse.uml2.CollaborationOccurrence;
import org.eclipse.uml2.Component;
import org.eclipse.uml2.ComponentKind;
import org.eclipse.uml2.Dependency;
import org.eclipse.uml2.Enumeration;
import org.eclipse.uml2.Interface;
import org.eclipse.uml2.NamedElement;
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.PackageableElement;
import org.eclipse.uml2.PrimitiveType;
import org.eclipse.uml2.Realization;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.SubsetEObjectContainmentWithInverseEList;
import org.eclipse.uml2.common.util.SupersetEObjectWithInverseResolvingEList;

import org.eclipse.uml2.internal.operation.ComponentOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.ComponentImpl#getClientDependencies <em>Client Dependency</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ComponentImpl#isIndirectlyInstantiated <em>Is Indirectly Instantiated</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ComponentImpl#getRequireds <em>Required</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ComponentImpl#getProvideds <em>Provided</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ComponentImpl#getRealizations <em>Realization</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ComponentImpl#getOwnedMembers <em>Owned Member</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentImpl extends ClassImpl implements Component {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #isIndirectlyInstantiated() <em>Is Indirectly Instantiated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIndirectlyInstantiated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_INDIRECTLY_INSTANTIATED_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isIndirectlyInstantiated() <em>Is Indirectly Instantiated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIndirectlyInstantiated()
	 * @generated
	 * @ordered
	 */
	protected static final int IS_INDIRECTLY_INSTANTIATED_EFLAG = 1 << 12;

	/**
	 * The cached value of the '{@link #getRealizations() <em>Realization</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRealizations()
	 * @generated
	 * @ordered
	 */
	protected EList realization = null;

	/**
	 * The cached value of the '{@link #getOwnedMembers() <em>Owned Member</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedMembers()
	 * @generated
	 * @ordered
	 */
	protected EList ownedMember = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentImpl() {
		super();
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (ComponentImpl.class.equals(getClass()) && org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getComponent();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIndirectlyInstantiated() {
		return (eFlags & IS_INDIRECTLY_INSTANTIATED_EFLAG) != 0;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsIndirectlyInstantiated(boolean newIsIndirectlyInstantiated) {
		boolean oldIsIndirectlyInstantiated = (eFlags & IS_INDIRECTLY_INSTANTIATED_EFLAG) != 0;
		if (newIsIndirectlyInstantiated) eFlags |= IS_INDIRECTLY_INSTANTIATED_EFLAG; else eFlags &= ~IS_INDIRECTLY_INSTANTIATED_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.COMPONENT__IS_INDIRECTLY_INSTANTIATED, oldIsIndirectlyInstantiated, newIsIndirectlyInstantiated));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getRequireds() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			EList result = (EList) cache.get(this, UML2Package.eINSTANCE
				.getComponent_Required());

			if (result == null) {
				EList requireds = ComponentOperations.getRequireds(this);
				cache.put(this, UML2Package.eINSTANCE.getComponent_Required(),
					result = new EcoreEList.UnmodifiableEList(this,
						UML2Package.eINSTANCE.getComponent_Required(),
						requireds.size(), requireds.toArray()));
			}

			return result;
		}

		EList requireds = ComponentOperations.getRequireds(this);
		return new EcoreEList.UnmodifiableEList(this, UML2Package.eINSTANCE
			.getComponent_Required(), requireds.size(), requireds.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interface getRequired(String name) {
		for (Iterator i = getRequireds().iterator(); i.hasNext(); ) {
			Interface required = (Interface) i.next();
			if (name.equals(required.getName())) {
				return required;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getProvideds() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			EList result = (EList) cache.get(this, UML2Package.eINSTANCE
				.getComponent_Provided());

			if (result == null) {
				EList provideds = ComponentOperations.getProvideds(this);
				cache.put(this, UML2Package.eINSTANCE.getComponent_Provided(),
					result = new EcoreEList.UnmodifiableEList(this,
						UML2Package.eINSTANCE.getComponent_Provided(),
						provideds.size(), provideds.toArray()));
			}

			return result;
		}

		EList provideds = ComponentOperations.getProvideds(this);
		return new EcoreEList.UnmodifiableEList(this, UML2Package.eINSTANCE
			.getComponent_Provided(), provideds.size(), provideds.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interface getProvided(String name) {
		for (Iterator i = getProvideds().iterator(); i.hasNext(); ) {
			Interface provided = (Interface) i.next();
			if (name.equals(provided.getName())) {
				return provided;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getRealizations() {
		if (realization == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		realization = new com.intrinsarc.emflist.PersistentEList(Realization.class, this, UML2Package.COMPONENT__REALIZATION, new int[] {UML2Package.COMPONENT__CLIENT_DEPENDENCY}, UML2Package.REALIZATION__ABSTRACTION);
			 		return realization;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Realization.class, this, UML2Package.COMPONENT__REALIZATION, new int[] {UML2Package.COMPONENT__CLIENT_DEPENDENCY}, UML2Package.REALIZATION__ABSTRACTION);
		}      
		return realization;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getRealizations() {
		if (realization == null) {
			realization = new com.intrinsarc.emflist.PersistentEList(Realization.class, this, UML2Package.COMPONENT__REALIZATION, new int[] {UML2Package.COMPONENT__CLIENT_DEPENDENCY}, UML2Package.REALIZATION__ABSTRACTION);
		}
		return realization;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getRealizations() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (realization != null) {
			for (Object object : realization) {
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
	public Realization getRealization(String name) {
		for (Iterator i = getRealizations().iterator(); i.hasNext(); ) {
			Realization realization = (Realization) i.next();
			if (name.equals(realization.getName())) {
				return realization;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Realization createRealization(EClass eClass) {
		Realization newRealization = (Realization) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.COMPONENT__REALIZATION, null, newRealization));
		}
		settable_getRealizations().add(newRealization);
		return newRealization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Realization createRealization() {
		Realization newRealization = UML2Factory.eINSTANCE.createRealization();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.COMPONENT__REALIZATION, null, newRealization));
		}
		settable_getRealizations().add(newRealization);
		return newRealization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedMembers() {
		if (ownedMember == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		ownedMember = new com.intrinsarc.emflist.PersistentEList(PackageableElement.class, this, UML2Package.COMPONENT__OWNED_MEMBER);
			 		return ownedMember;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(PackageableElement.class, this, UML2Package.COMPONENT__OWNED_MEMBER);
		}      
		return ownedMember;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedMembers() {
		if (ownedMember == null) {
			ownedMember = new com.intrinsarc.emflist.PersistentEList(PackageableElement.class, this, UML2Package.COMPONENT__OWNED_MEMBER);
		}
		return ownedMember;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedMembers() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedMember != null) {
			for (Object object : ownedMember) {
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
	public NamedElement getOwnedMember(String name) {
		for (Iterator i = getOwnedMembers().iterator(); i.hasNext(); ) {
			PackageableElement ownedMember = (PackageableElement) i.next();
			if (name.equals(ownedMember.getName())) {
				return ownedMember;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PackageableElement createOwnedMember(EClass eClass) {
		PackageableElement newOwnedMember = (PackageableElement) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.COMPONENT__OWNED_MEMBER, null, newOwnedMember));
		}
		getOwnedMembers().add(newOwnedMember);
		return newOwnedMember;
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
			 		clientDependency = new com.intrinsarc.emflist.PersistentEList(Dependency.class, this, UML2Package.COMPONENT__CLIENT_DEPENDENCY, new int[] {UML2Package.COMPONENT__SUBSTITUTION, UML2Package.COMPONENT__IMPLEMENTATION, UML2Package.COMPONENT__REALIZATION}, UML2Package.DEPENDENCY__CLIENT);
			 		return clientDependency;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Dependency.class, this, UML2Package.COMPONENT__CLIENT_DEPENDENCY, new int[] {UML2Package.COMPONENT__SUBSTITUTION, UML2Package.COMPONENT__IMPLEMENTATION, UML2Package.COMPONENT__REALIZATION}, UML2Package.DEPENDENCY__CLIENT);
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
			clientDependency = new com.intrinsarc.emflist.PersistentEList(Dependency.class, this, UML2Package.COMPONENT__CLIENT_DEPENDENCY, new int[] {UML2Package.COMPONENT__SUBSTITUTION, UML2Package.COMPONENT__IMPLEMENTATION, UML2Package.COMPONENT__REALIZATION}, UML2Package.DEPENDENCY__CLIENT);
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
				case UML2Package.COMPONENT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.COMPONENT__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.COMPONENT__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.COMPONENT__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.COMPONENT__OWNING_PARAMETER, msgs);
				case UML2Package.COMPONENT__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__USE_CASE:
					return ((InternalEList)getUseCases()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_BEHAVIOR:
					return ((InternalEList)getOwnedBehaviors()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__IMPLEMENTATION:
					return ((InternalEList)getImplementations()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_OPERATION:
					return ((InternalEList)getOwnedOperations()).basicAdd(otherEnd, msgs);
				case UML2Package.COMPONENT__REALIZATION:
					return ((InternalEList)getRealizations()).basicAdd(otherEnd, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	public NotificationChain eDynamicInverseAdd(InternalEObject otherEnd, int featureID, Class inverseClass, NotificationChain msgs) {
		switch (eDerivedStructuralFeatureID(featureID, inverseClass)) {
			case UML2Package.COMPONENT__OWNED_STATE_MACHINE:
				return ((InternalEList)getOwnedStateMachines()).basicAdd(otherEnd, msgs);
			default :
				return super.eDynamicInverseAdd(otherEnd, featureID, inverseClass, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.COMPONENT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.COMPONENT__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.COMPONENT__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.COMPONENT__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.COMPONENT__OWNING_PARAMETER, msgs);
				case UML2Package.COMPONENT__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_USE_CASE:
					return ((InternalEList)getOwnedUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__USE_CASE:
					return ((InternalEList)getUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OCCURRENCE:
					return ((InternalEList)getOccurrences()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_BEHAVIOR:
					return ((InternalEList)getOwnedBehaviors()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__IMPLEMENTATION:
					return ((InternalEList)getImplementations()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_TRIGGER:
					return ((InternalEList)getOwnedTriggers()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_STATE_MACHINE:
					return ((InternalEList)getOwnedStateMachines()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_ATTRIBUTE:
					return ((InternalEList)getOwnedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_CONNECTOR:
					return ((InternalEList)getOwnedConnectors()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__DELTA_DELETED_ATTRIBUTES:
					return ((InternalEList)getDeltaDeletedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__DELTA_REPLACED_ATTRIBUTES:
					return ((InternalEList)getDeltaReplacedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__DELTA_DELETED_PORTS:
					return ((InternalEList)getDeltaDeletedPorts()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__DELTA_REPLACED_PORTS:
					return ((InternalEList)getDeltaReplacedPorts()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__DELTA_DELETED_CONNECTORS:
					return ((InternalEList)getDeltaDeletedConnectors()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__DELTA_REPLACED_CONNECTORS:
					return ((InternalEList)getDeltaReplacedConnectors()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__DELTA_DELETED_OPERATIONS:
					return ((InternalEList)getDeltaDeletedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__DELTA_REPLACED_OPERATIONS:
					return ((InternalEList)getDeltaReplacedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__DELTA_DELETED_TRACES:
					return ((InternalEList)getDeltaDeletedTraces()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__DELTA_REPLACED_TRACES:
					return ((InternalEList)getDeltaReplacedTraces()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_PORT:
					return ((InternalEList)getOwnedPorts()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_OPERATION:
					return ((InternalEList)getOwnedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__NESTED_CLASSIFIER:
					return ((InternalEList)getNestedClassifiers()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_RECEPTION:
					return ((InternalEList)getOwnedReceptions()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__REALIZATION:
					return ((InternalEList)getRealizations()).basicRemove(otherEnd, msgs);
				case UML2Package.COMPONENT__OWNED_MEMBER:
					return ((InternalEList)getOwnedMembers()).basicRemove(otherEnd, msgs);
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
				case UML2Package.COMPONENT__OWNING_PARAMETER:
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
			case UML2Package.COMPONENT__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.COMPONENT__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.COMPONENT__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.COMPONENT__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.COMPONENT__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.COMPONENT__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.COMPONENT__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.COMPONENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.COMPONENT__UUID:
				return getUuid();
			case UML2Package.COMPONENT__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.COMPONENT__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.COMPONENT__NAME:
				return getName();
			case UML2Package.COMPONENT__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.COMPONENT__VISIBILITY:
				return getVisibility();
			case UML2Package.COMPONENT__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.COMPONENT__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.COMPONENT__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.COMPONENT__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.COMPONENT__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.COMPONENT__MEMBER:
				return getMembers();
			case UML2Package.COMPONENT__OWNED_RULE:
				return getOwnedRules();
			case UML2Package.COMPONENT__IMPORTED_MEMBER:
				return getImportedMembers();
			case UML2Package.COMPONENT__ELEMENT_IMPORT:
				return getElementImports();
			case UML2Package.COMPONENT__PACKAGE_IMPORT:
				return getPackageImports();
			case UML2Package.COMPONENT__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.COMPONENT__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.COMPONENT__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.COMPONENT__PACKAGE:
				if (resolve) return getPackage();
				return basicGetPackage();
			case UML2Package.COMPONENT__IS_RETIRED:
				return isRetired() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.COMPONENT__REDEFINITION_CONTEXT:
				return getRedefinitionContexts();
			case UML2Package.COMPONENT__IS_LEAF:
				return isLeaf() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.COMPONENT__FEATURE:
				return getFeatures();
			case UML2Package.COMPONENT__IS_ABSTRACT:
				return isAbstract() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.COMPONENT__INHERITED_MEMBER:
				return getInheritedMembers();
			case UML2Package.COMPONENT__GENERAL:
				return getGenerals();
			case UML2Package.COMPONENT__GENERALIZATION:
				return getGeneralizations();
			case UML2Package.COMPONENT__ATTRIBUTE:
				return getAttributes();
			case UML2Package.COMPONENT__REDEFINED_CLASSIFIER:
				return getRedefinedClassifiers();
			case UML2Package.COMPONENT__SUBSTITUTION:
				return getSubstitutions();
			case UML2Package.COMPONENT__POWERTYPE_EXTENT:
				return getPowertypeExtents();
			case UML2Package.COMPONENT__OWNED_USE_CASE:
				return getOwnedUseCases();
			case UML2Package.COMPONENT__USE_CASE:
				return getUseCases();
			case UML2Package.COMPONENT__REPRESENTATION:
				return getRepresentation();
			case UML2Package.COMPONENT__OCCURRENCE:
				return getOccurrences();
			case UML2Package.COMPONENT__OWNED_BEHAVIOR:
				return getOwnedBehaviors();
			case UML2Package.COMPONENT__CLASSIFIER_BEHAVIOR:
				return getClassifierBehavior();
			case UML2Package.COMPONENT__IMPLEMENTATION:
				return getImplementations();
			case UML2Package.COMPONENT__OWNED_TRIGGER:
				return getOwnedTriggers();
			case UML2Package.COMPONENT__OWNED_STATE_MACHINE:
				return getOwnedStateMachines();
			case UML2Package.COMPONENT__OWNED_ATTRIBUTE:
				return getOwnedAttributes();
			case UML2Package.COMPONENT__PART:
				return getParts();
			case UML2Package.COMPONENT__ROLE:
				return getRoles();
			case UML2Package.COMPONENT__OWNED_CONNECTOR:
				return getOwnedConnectors();
			case UML2Package.COMPONENT__DELTA_DELETED_ATTRIBUTES:
				return getDeltaDeletedAttributes();
			case UML2Package.COMPONENT__DELTA_REPLACED_ATTRIBUTES:
				return getDeltaReplacedAttributes();
			case UML2Package.COMPONENT__DELTA_DELETED_PORTS:
				return getDeltaDeletedPorts();
			case UML2Package.COMPONENT__DELTA_REPLACED_PORTS:
				return getDeltaReplacedPorts();
			case UML2Package.COMPONENT__DELTA_DELETED_CONNECTORS:
				return getDeltaDeletedConnectors();
			case UML2Package.COMPONENT__DELTA_REPLACED_CONNECTORS:
				return getDeltaReplacedConnectors();
			case UML2Package.COMPONENT__DELTA_DELETED_OPERATIONS:
				return getDeltaDeletedOperations();
			case UML2Package.COMPONENT__DELTA_REPLACED_OPERATIONS:
				return getDeltaReplacedOperations();
			case UML2Package.COMPONENT__DELTA_DELETED_TRACES:
				return getDeltaDeletedTraces();
			case UML2Package.COMPONENT__DELTA_REPLACED_TRACES:
				return getDeltaReplacedTraces();
			case UML2Package.COMPONENT__OWNED_PORT:
				return getOwnedPorts();
			case UML2Package.COMPONENT__OWNED_OPERATION:
				return getOwnedOperations();
			case UML2Package.COMPONENT__SUPER_CLASS:
				return getSuperClasses();
			case UML2Package.COMPONENT__EXTENSION:
				return getExtensions();
			case UML2Package.COMPONENT__NESTED_CLASSIFIER:
				return getNestedClassifiers();
			case UML2Package.COMPONENT__IS_ACTIVE:
				return isActive() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.COMPONENT__OWNED_RECEPTION:
				return getOwnedReceptions();
			case UML2Package.COMPONENT__COMPONENT_KIND:
				return getComponentKind();
			case UML2Package.COMPONENT__IS_INDIRECTLY_INSTANTIATED:
				return isIndirectlyInstantiated() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.COMPONENT__REQUIRED:
				return getRequireds();
			case UML2Package.COMPONENT__PROVIDED:
				return getProvideds();
			case UML2Package.COMPONENT__REALIZATION:
				return getRealizations();
			case UML2Package.COMPONENT__OWNED_MEMBER:
				return getOwnedMembers();
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
			case UML2Package.COMPONENT__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.COMPONENT__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.COMPONENT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.COMPONENT__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.COMPONENT__NAME:
				setName((String)newValue);
				return;
			case UML2Package.COMPONENT__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.COMPONENT__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_RULE:
				getOwnedRules().clear();
				getOwnedRules().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__ELEMENT_IMPORT:
				getElementImports().clear();
				getElementImports().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__PACKAGE_IMPORT:
				getPackageImports().clear();
				getPackageImports().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.COMPONENT__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.COMPONENT__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.COMPONENT__IS_RETIRED:
				setIsRetired(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.COMPONENT__IS_LEAF:
				setIsLeaf(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.COMPONENT__IS_ABSTRACT:
				setIsAbstract(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.COMPONENT__GENERALIZATION:
				getGeneralizations().clear();
				getGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				getRedefinedClassifiers().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__SUBSTITUTION:
				getSubstitutions().clear();
				getSubstitutions().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				getPowertypeExtents().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				getOwnedUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__USE_CASE:
				getUseCases().clear();
				getUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)newValue);
				return;
			case UML2Package.COMPONENT__OCCURRENCE:
				getOccurrences().clear();
				getOccurrences().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_BEHAVIOR:
				getOwnedBehaviors().clear();
				getOwnedBehaviors().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__CLASSIFIER_BEHAVIOR:
				setClassifierBehavior((Behavior)newValue);
				return;
			case UML2Package.COMPONENT__IMPLEMENTATION:
				getImplementations().clear();
				getImplementations().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_TRIGGER:
				getOwnedTriggers().clear();
				getOwnedTriggers().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_STATE_MACHINE:
				getOwnedStateMachines().clear();
				getOwnedStateMachines().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_ATTRIBUTE:
				getOwnedAttributes().clear();
				getOwnedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_CONNECTOR:
				getOwnedConnectors().clear();
				getOwnedConnectors().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__DELTA_DELETED_ATTRIBUTES:
				getDeltaDeletedAttributes().clear();
				getDeltaDeletedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__DELTA_REPLACED_ATTRIBUTES:
				getDeltaReplacedAttributes().clear();
				getDeltaReplacedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__DELTA_DELETED_PORTS:
				getDeltaDeletedPorts().clear();
				getDeltaDeletedPorts().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__DELTA_REPLACED_PORTS:
				getDeltaReplacedPorts().clear();
				getDeltaReplacedPorts().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__DELTA_DELETED_CONNECTORS:
				getDeltaDeletedConnectors().clear();
				getDeltaDeletedConnectors().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__DELTA_REPLACED_CONNECTORS:
				getDeltaReplacedConnectors().clear();
				getDeltaReplacedConnectors().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__DELTA_DELETED_OPERATIONS:
				getDeltaDeletedOperations().clear();
				getDeltaDeletedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__DELTA_REPLACED_OPERATIONS:
				getDeltaReplacedOperations().clear();
				getDeltaReplacedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__DELTA_DELETED_TRACES:
				getDeltaDeletedTraces().clear();
				getDeltaDeletedTraces().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__DELTA_REPLACED_TRACES:
				getDeltaReplacedTraces().clear();
				getDeltaReplacedTraces().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_PORT:
				getOwnedPorts().clear();
				getOwnedPorts().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_OPERATION:
				getOwnedOperations().clear();
				getOwnedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__NESTED_CLASSIFIER:
				getNestedClassifiers().clear();
				getNestedClassifiers().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__IS_ACTIVE:
				setIsActive(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.COMPONENT__OWNED_RECEPTION:
				getOwnedReceptions().clear();
				getOwnedReceptions().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__COMPONENT_KIND:
				setComponentKind((ComponentKind)newValue);
				return;
			case UML2Package.COMPONENT__IS_INDIRECTLY_INSTANTIATED:
				setIsIndirectlyInstantiated(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.COMPONENT__REALIZATION:
				getRealizations().clear();
				getRealizations().addAll((Collection)newValue);
				return;
			case UML2Package.COMPONENT__OWNED_MEMBER:
				getOwnedMembers().clear();
				getOwnedMembers().addAll((Collection)newValue);
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
			case UML2Package.COMPONENT__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.COMPONENT__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.COMPONENT__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.COMPONENT__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.COMPONENT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.COMPONENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.COMPONENT__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.COMPONENT__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.COMPONENT__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.COMPONENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.COMPONENT__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.COMPONENT__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.COMPONENT__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.COMPONENT__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.COMPONENT__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.COMPONENT__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.COMPONENT__OWNED_RULE:
				getOwnedRules().clear();
				return;
			case UML2Package.COMPONENT__ELEMENT_IMPORT:
				getElementImports().clear();
				return;
			case UML2Package.COMPONENT__PACKAGE_IMPORT:
				getPackageImports().clear();
				return;
			case UML2Package.COMPONENT__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.COMPONENT__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.COMPONENT__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.COMPONENT__IS_RETIRED:
				setIsRetired(IS_RETIRED_EDEFAULT);
				return;
			case UML2Package.COMPONENT__IS_LEAF:
				setIsLeaf(IS_LEAF_EDEFAULT);
				return;
			case UML2Package.COMPONENT__IS_ABSTRACT:
				setIsAbstract(IS_ABSTRACT_EDEFAULT);
				return;
			case UML2Package.COMPONENT__GENERALIZATION:
				getGeneralizations().clear();
				return;
			case UML2Package.COMPONENT__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				return;
			case UML2Package.COMPONENT__SUBSTITUTION:
				getSubstitutions().clear();
				return;
			case UML2Package.COMPONENT__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				return;
			case UML2Package.COMPONENT__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				return;
			case UML2Package.COMPONENT__USE_CASE:
				getUseCases().clear();
				return;
			case UML2Package.COMPONENT__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)null);
				return;
			case UML2Package.COMPONENT__OCCURRENCE:
				getOccurrences().clear();
				return;
			case UML2Package.COMPONENT__OWNED_BEHAVIOR:
				getOwnedBehaviors().clear();
				return;
			case UML2Package.COMPONENT__CLASSIFIER_BEHAVIOR:
				setClassifierBehavior((Behavior)null);
				return;
			case UML2Package.COMPONENT__IMPLEMENTATION:
				getImplementations().clear();
				return;
			case UML2Package.COMPONENT__OWNED_TRIGGER:
				getOwnedTriggers().clear();
				return;
			case UML2Package.COMPONENT__OWNED_STATE_MACHINE:
				getOwnedStateMachines().clear();
				return;
			case UML2Package.COMPONENT__OWNED_ATTRIBUTE:
				getOwnedAttributes().clear();
				return;
			case UML2Package.COMPONENT__OWNED_CONNECTOR:
				getOwnedConnectors().clear();
				return;
			case UML2Package.COMPONENT__DELTA_DELETED_ATTRIBUTES:
				getDeltaDeletedAttributes().clear();
				return;
			case UML2Package.COMPONENT__DELTA_REPLACED_ATTRIBUTES:
				getDeltaReplacedAttributes().clear();
				return;
			case UML2Package.COMPONENT__DELTA_DELETED_PORTS:
				getDeltaDeletedPorts().clear();
				return;
			case UML2Package.COMPONENT__DELTA_REPLACED_PORTS:
				getDeltaReplacedPorts().clear();
				return;
			case UML2Package.COMPONENT__DELTA_DELETED_CONNECTORS:
				getDeltaDeletedConnectors().clear();
				return;
			case UML2Package.COMPONENT__DELTA_REPLACED_CONNECTORS:
				getDeltaReplacedConnectors().clear();
				return;
			case UML2Package.COMPONENT__DELTA_DELETED_OPERATIONS:
				getDeltaDeletedOperations().clear();
				return;
			case UML2Package.COMPONENT__DELTA_REPLACED_OPERATIONS:
				getDeltaReplacedOperations().clear();
				return;
			case UML2Package.COMPONENT__DELTA_DELETED_TRACES:
				getDeltaDeletedTraces().clear();
				return;
			case UML2Package.COMPONENT__DELTA_REPLACED_TRACES:
				getDeltaReplacedTraces().clear();
				return;
			case UML2Package.COMPONENT__OWNED_PORT:
				getOwnedPorts().clear();
				return;
			case UML2Package.COMPONENT__OWNED_OPERATION:
				getOwnedOperations().clear();
				return;
			case UML2Package.COMPONENT__NESTED_CLASSIFIER:
				getNestedClassifiers().clear();
				return;
			case UML2Package.COMPONENT__IS_ACTIVE:
				setIsActive(IS_ACTIVE_EDEFAULT);
				return;
			case UML2Package.COMPONENT__OWNED_RECEPTION:
				getOwnedReceptions().clear();
				return;
			case UML2Package.COMPONENT__COMPONENT_KIND:
				setComponentKind(COMPONENT_KIND_EDEFAULT);
				return;
			case UML2Package.COMPONENT__IS_INDIRECTLY_INSTANTIATED:
				setIsIndirectlyInstantiated(IS_INDIRECTLY_INSTANTIATED_EDEFAULT);
				return;
			case UML2Package.COMPONENT__REALIZATION:
				getRealizations().clear();
				return;
			case UML2Package.COMPONENT__OWNED_MEMBER:
				getOwnedMembers().clear();
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
			case UML2Package.COMPONENT__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.COMPONENT__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.COMPONENT__OWNER:
				return basicGetOwner() != null;
			case UML2Package.COMPONENT__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.COMPONENT__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.COMPONENT__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.COMPONENT__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.COMPONENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.COMPONENT__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.COMPONENT__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.COMPONENT__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.COMPONENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.COMPONENT__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.COMPONENT__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.COMPONENT__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.COMPONENT__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.COMPONENT__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.COMPONENT__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.COMPONENT__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.COMPONENT__MEMBER:
				return !getMembers().isEmpty();
			case UML2Package.COMPONENT__OWNED_RULE:
				return ownedRule != null && !ownedRule.isEmpty();
			case UML2Package.COMPONENT__IMPORTED_MEMBER:
				return !getImportedMembers().isEmpty();
			case UML2Package.COMPONENT__ELEMENT_IMPORT:
				return elementImport != null && !elementImport.isEmpty();
			case UML2Package.COMPONENT__PACKAGE_IMPORT:
				return packageImport != null && !packageImport.isEmpty();
			case UML2Package.COMPONENT__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.COMPONENT__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.COMPONENT__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.COMPONENT__PACKAGE:
				return basicGetPackage() != null;
			case UML2Package.COMPONENT__IS_RETIRED:
				return ((eFlags & IS_RETIRED_EFLAG) != 0) != IS_RETIRED_EDEFAULT;
			case UML2Package.COMPONENT__REDEFINITION_CONTEXT:
				return !getRedefinitionContexts().isEmpty();
			case UML2Package.COMPONENT__IS_LEAF:
				return ((eFlags & IS_LEAF_EFLAG) != 0) != IS_LEAF_EDEFAULT;
			case UML2Package.COMPONENT__FEATURE:
				return !getFeatures().isEmpty();
			case UML2Package.COMPONENT__IS_ABSTRACT:
				return isAbstract() != IS_ABSTRACT_EDEFAULT;
			case UML2Package.COMPONENT__INHERITED_MEMBER:
				return !getInheritedMembers().isEmpty();
			case UML2Package.COMPONENT__GENERAL:
				return !getGenerals().isEmpty();
			case UML2Package.COMPONENT__GENERALIZATION:
				return generalization != null && !generalization.isEmpty();
			case UML2Package.COMPONENT__ATTRIBUTE:
				return !getAttributes().isEmpty();
			case UML2Package.COMPONENT__REDEFINED_CLASSIFIER:
				return redefinedClassifier != null && !redefinedClassifier.isEmpty();
			case UML2Package.COMPONENT__SUBSTITUTION:
				return substitution != null && !substitution.isEmpty();
			case UML2Package.COMPONENT__POWERTYPE_EXTENT:
				return powertypeExtent != null && !powertypeExtent.isEmpty();
			case UML2Package.COMPONENT__OWNED_USE_CASE:
				return ownedUseCase != null && !ownedUseCase.isEmpty();
			case UML2Package.COMPONENT__USE_CASE:
				return useCase != null && !useCase.isEmpty();
			case UML2Package.COMPONENT__REPRESENTATION:
				return representation != null;
			case UML2Package.COMPONENT__OCCURRENCE:
				return occurrence != null && !occurrence.isEmpty();
			case UML2Package.COMPONENT__OWNED_BEHAVIOR:
				return !getOwnedBehaviors().isEmpty();
			case UML2Package.COMPONENT__CLASSIFIER_BEHAVIOR:
				return classifierBehavior != null;
			case UML2Package.COMPONENT__IMPLEMENTATION:
				return implementation != null && !implementation.isEmpty();
			case UML2Package.COMPONENT__OWNED_TRIGGER:
				return ownedTrigger != null && !ownedTrigger.isEmpty();
			case UML2Package.COMPONENT__OWNED_STATE_MACHINE:
				return !getOwnedStateMachines().isEmpty();
			case UML2Package.COMPONENT__OWNED_ATTRIBUTE:
				return !getOwnedAttributes().isEmpty();
			case UML2Package.COMPONENT__PART:
				return !getParts().isEmpty();
			case UML2Package.COMPONENT__ROLE:
				return !getRoles().isEmpty();
			case UML2Package.COMPONENT__OWNED_CONNECTOR:
				return ownedConnector != null && !ownedConnector.isEmpty();
			case UML2Package.COMPONENT__DELTA_DELETED_ATTRIBUTES:
				return deltaDeletedAttributes != null && !deltaDeletedAttributes.isEmpty();
			case UML2Package.COMPONENT__DELTA_REPLACED_ATTRIBUTES:
				return deltaReplacedAttributes != null && !deltaReplacedAttributes.isEmpty();
			case UML2Package.COMPONENT__DELTA_DELETED_PORTS:
				return deltaDeletedPorts != null && !deltaDeletedPorts.isEmpty();
			case UML2Package.COMPONENT__DELTA_REPLACED_PORTS:
				return deltaReplacedPorts != null && !deltaReplacedPorts.isEmpty();
			case UML2Package.COMPONENT__DELTA_DELETED_CONNECTORS:
				return deltaDeletedConnectors != null && !deltaDeletedConnectors.isEmpty();
			case UML2Package.COMPONENT__DELTA_REPLACED_CONNECTORS:
				return deltaReplacedConnectors != null && !deltaReplacedConnectors.isEmpty();
			case UML2Package.COMPONENT__DELTA_DELETED_OPERATIONS:
				return deltaDeletedOperations != null && !deltaDeletedOperations.isEmpty();
			case UML2Package.COMPONENT__DELTA_REPLACED_OPERATIONS:
				return deltaReplacedOperations != null && !deltaReplacedOperations.isEmpty();
			case UML2Package.COMPONENT__DELTA_DELETED_TRACES:
				return deltaDeletedTraces != null && !deltaDeletedTraces.isEmpty();
			case UML2Package.COMPONENT__DELTA_REPLACED_TRACES:
				return deltaReplacedTraces != null && !deltaReplacedTraces.isEmpty();
			case UML2Package.COMPONENT__OWNED_PORT:
				return ownedPort != null && !ownedPort.isEmpty();
			case UML2Package.COMPONENT__OWNED_OPERATION:
				return ownedOperation != null && !ownedOperation.isEmpty();
			case UML2Package.COMPONENT__SUPER_CLASS:
				return !getSuperClasses().isEmpty();
			case UML2Package.COMPONENT__EXTENSION:
				return !getExtensions().isEmpty();
			case UML2Package.COMPONENT__NESTED_CLASSIFIER:
				return nestedClassifier != null && !nestedClassifier.isEmpty();
			case UML2Package.COMPONENT__IS_ACTIVE:
				return ((eFlags & IS_ACTIVE_EFLAG) != 0) != IS_ACTIVE_EDEFAULT;
			case UML2Package.COMPONENT__OWNED_RECEPTION:
				return ownedReception != null && !ownedReception.isEmpty();
			case UML2Package.COMPONENT__COMPONENT_KIND:
				return componentKind != COMPONENT_KIND_EDEFAULT;
			case UML2Package.COMPONENT__IS_INDIRECTLY_INSTANTIATED:
				return ((eFlags & IS_INDIRECTLY_INSTANTIATED_EFLAG) != 0) != IS_INDIRECTLY_INSTANTIATED_EDEFAULT;
			case UML2Package.COMPONENT__REQUIRED:
				return !getRequireds().isEmpty();
			case UML2Package.COMPONENT__PROVIDED:
				return !getProvideds().isEmpty();
			case UML2Package.COMPONENT__REALIZATION:
				return realization != null && !realization.isEmpty();
			case UML2Package.COMPONENT__OWNED_MEMBER:
				return ownedMember != null && !ownedMember.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.COMPONENT__VISIBILITY:
				return false;
			case UML2Package.COMPONENT__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.COMPONENT__OWNED_BEHAVIOR:
				return ownedBehavior != null && !ownedBehavior.isEmpty();
			case UML2Package.COMPONENT__OWNED_STATE_MACHINE:
				return ownedStateMachine != null && !ownedStateMachine.isEmpty();
			case UML2Package.COMPONENT__OWNED_ATTRIBUTE:
				return ownedAttribute != null && !ownedAttribute.isEmpty();
		}
		return eIsSetGen(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (isIndirectlyInstantiated: "); //$NON-NLS-1$
		result.append((eFlags & IS_INDIRECTLY_INSTANTIATED_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected EList getOwnedElementsHelper(EList ownedElement) {
		super.getOwnedElementsHelper(ownedElement);
		if (eIsSet(UML2Package.eINSTANCE.getComponent_Realization())) {
			ownedElement.addAll(getRealizations());
		}
		EList ownedMember = super.getOwnedMembers();
		if (!ownedMember.isEmpty()) {
			for (Iterator i = ((InternalEList) ownedMember).basicIterator(); i.hasNext(); ) {
				ownedElement.add(i.next());
			}
		}
		return ownedElement;
	}

	// <!-- begin-custom-operations -->

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.impl.NamespaceImpl#getMembersHelper(org.eclipse.emf.common.util.EList)
	 */
	protected EList getMembersHelper(EList member) {
		super.getMembersHelper(member);
		EList ownedMember = super.getOwnedMembers();
		if (!ownedMember.isEmpty()) {
			for (Iterator i = ((InternalEList) ownedMember).basicIterator(); i.hasNext();) {
				member.add(i.next());
			}
		}
		return member;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Namespace#getNamesOfMember(org.eclipse.uml2.NamedElement)
	 */
	public Set getNamesOfMember(NamedElement element) {
		return ComponentOperations.getNamesOfMember(this, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Namespace#importMembers(java.util.Set)
	 */
	public Set importMembers(Set imps) {
		return ComponentOperations.importMembers(this, imps);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Classifier#inherit(java.util.Set)
	 */
	public Set inherit(Set inhs) {
		return ComponentOperations.inherit(this, inhs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Component#createOwnedClass(java.lang.String,
	 *      boolean)
	 */
	public org.eclipse.uml2.Class createOwnedClass(String name,
			boolean isAbstract) {
		return ComponentOperations.createOwnedClass(this, name, isAbstract);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Component#createOwnedEnumeraton(java.lang.String)
	 */
	public Enumeration createOwnedEnumeraton(String name) {
		return ComponentOperations.createOwnedEnumeration(this, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Component#createOwnedPackage(java.lang.String)
	 */
	public org.eclipse.uml2.Package createOwnedPackage(String name) {
		return ComponentOperations.createOwnedPackage(this, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Component#createOwnedPrimitiveType(java.lang.String)
	 */
	public PrimitiveType createOwnedPrimitiveType(String name) {
		return ComponentOperations.createOwnedPrimitiveType(this, name);
	}

	// <!-- end-custom-operations -->

} //ComponentImpl
