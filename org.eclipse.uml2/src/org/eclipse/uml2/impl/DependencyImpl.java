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
 * $Id: DependencyImpl.java,v 1.2 2009-04-22 10:38:33 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Dependency;
import org.eclipse.uml2.DirectedRelationship;
import org.eclipse.uml2.Element;
import org.eclipse.uml2.NamedElement;
import org.eclipse.uml2.Relationship;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.UnionEObjectEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.DependencyImpl#getClients <em>Client</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DependencyImpl#getSuppliers <em>Supplier</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DependencyImpl#getDependencyTarget <em>Dependency Target</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DependencyImpl#isResemblance <em>Resemblance</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DependencyImpl#isReplacement <em>Replacement</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DependencyImpl#isTrace <em>Trace</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DependencyImpl extends PackageableElementImpl implements Dependency {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getClients() <em>Client</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClients()
	 * @generated
	 * @ordered
	 */
	protected EList client = null;

	/**
	 * The cached value of the '{@link #getSuppliers() <em>Supplier</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuppliers()
	 * @generated
	 * @ordered
	 */
	protected EList supplier = null;

	/**
	 * The cached value of the '{@link #getDependencyTarget() <em>Dependency Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencyTarget()
	 * @generated NOT
	 * @ordered
	 */
	protected ElementImpl dependencyTarget = null;

	/**
	 * The default value of the '{@link #isResemblance() <em>Resemblance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isResemblance()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RESEMBLANCE_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isResemblance() <em>Resemblance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isResemblance()
	 * @generated
	 * @ordered
	 */
	protected static final int RESEMBLANCE_EFLAG = 1 << 8;

	/**
	 * The default value of the '{@link #isReplacement() <em>Replacement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReplacement()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REPLACEMENT_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isReplacement() <em>Replacement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReplacement()
	 * @generated
	 * @ordered
	 */
	protected static final int REPLACEMENT_EFLAG = 1 << 9;

	/**
	 * The default value of the '{@link #isTrace() <em>Trace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTrace()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TRACE_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isTrace() <em>Trace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTrace()
	 * @generated
	 * @ordered
	 */
	protected static final int TRACE_EFLAG = 1 << 10;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DependencyImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (DependencyImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getDependency();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getRelatedElements()
	{
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			EList relatedElement = (EList) cache.get(eResource(), this, UML2Package.eINSTANCE.getRelationship_RelatedElement());
			if (relatedElement == null) {
				EList union = getRelatedElementsHelper(new UniqueEList());
				cache.put(eResource(), this, UML2Package.eINSTANCE.getRelationship_RelatedElement(), relatedElement = new UnionEObjectEList(this, UML2Package.eINSTANCE.getRelationship_RelatedElement(), union.size(), union.toArray()));
			}
			return relatedElement;
		}
		EList union = getRelatedElementsHelper(new UniqueEList());
		return new UnionEObjectEList(this, UML2Package.eINSTANCE.getRelationship_RelatedElement(), union.size(), union.toArray());
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected EList getSourcesHelper(EList source) {
		if (eIsSet(UML2Package.eINSTANCE.getDependency_Client())) {
			for (Iterator i = ((InternalEList) getClients()).basicIterator(); i.hasNext(); ) {
				source.add(i.next());
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getSources()
	{
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			EList source = (EList) cache.get(eResource(), this, UML2Package.eINSTANCE.getDirectedRelationship_Source());
			if (source == null) {
				EList union = getSourcesHelper(new UniqueEList());
				cache.put(eResource(), this, UML2Package.eINSTANCE.getDirectedRelationship_Source(), source = new UnionEObjectEList(this, UML2Package.eINSTANCE.getDirectedRelationship_Source(), union.size(), union.toArray()));
			}
			return source;
		}
		EList union = getSourcesHelper(new UniqueEList());
		return new UnionEObjectEList(this, UML2Package.eINSTANCE.getDirectedRelationship_Source(), union.size(), union.toArray());
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected EList getTargetsHelper(EList target) {
		if (eIsSet(UML2Package.eINSTANCE.getDependency_Supplier())) {
			for (Iterator i = ((InternalEList) getSuppliers()).basicIterator(); i.hasNext(); ) {
				target.add(i.next());
			}
		}
		// amcv: add the dependency target also...
		if (getDependencyTarget() != null)
			target.add(getDependencyTarget());
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTargets()
	{
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			EList target = (EList) cache.get(eResource(), this, UML2Package.eINSTANCE.getDirectedRelationship_Target());
			if (target == null) {
				EList union = getTargetsHelper(new UniqueEList());
				cache.put(eResource(), this, UML2Package.eINSTANCE.getDirectedRelationship_Target(), target = new UnionEObjectEList(this, UML2Package.eINSTANCE.getDirectedRelationship_Target(), union.size(), union.toArray()));
			}
			return target;
		}
		EList union = getTargetsHelper(new UniqueEList());
		return new UnionEObjectEList(this, UML2Package.eINSTANCE.getDirectedRelationship_Target(), union.size(), union.toArray());
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
			 		client = new com.intrinsarc.emflist.PersistentEList(NamedElement.class, this, UML2Package.DEPENDENCY__CLIENT, UML2Package.NAMED_ELEMENT__CLIENT_DEPENDENCY);
			 		return client;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(NamedElement.class, this, UML2Package.DEPENDENCY__CLIENT, UML2Package.NAMED_ELEMENT__CLIENT_DEPENDENCY);
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
			
		
			client = new com.intrinsarc.emflist.PersistentEList(NamedElement.class, this, UML2Package.DEPENDENCY__CLIENT, UML2Package.NAMED_ELEMENT__CLIENT_DEPENDENCY);
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
    public NamedElement getClient(String name) {
		for (Iterator i = getClients().iterator(); i.hasNext(); ) {
			NamedElement client = (NamedElement) i.next();
			if (name.equals(client.getName())) {
				return client;
			}
		}
		return null;
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
			 		supplier = new com.intrinsarc.emflist.PersistentEList(NamedElement.class, this, UML2Package.DEPENDENCY__SUPPLIER);
			 		return supplier;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(NamedElement.class, this, UML2Package.DEPENDENCY__SUPPLIER);
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
			
		
			supplier = new com.intrinsarc.emflist.PersistentEList(NamedElement.class, this, UML2Package.DEPENDENCY__SUPPLIER);
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
    public NamedElement getSupplier(String name) {
		for (Iterator i = getSuppliers().iterator(); i.hasNext(); ) {
			NamedElement supplier = (NamedElement) i.next();
			if (name.equals(supplier.getName())) {
				return supplier;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public NamedElement getDependencyTarget() {
		if (dependencyTarget != null && dependencyTarget.eIsProxy()) {
			NamedElement oldDependencyTarget = (NamedElement) dependencyTarget;
			dependencyTarget = (ElementImpl) eResolveProxy((InternalEObject)dependencyTarget);
			if (dependencyTarget != oldDependencyTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.DEPENDENCY__DEPENDENCY_TARGET, oldDependencyTarget, dependencyTarget));
			}
		}
		return (NamedElement) dependencyTarget;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedElement undeleted_getDependencyTarget()
	{
		NamedElement temp = getDependencyTarget();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public NamedElement basicGetDependencyTarget() {
		return (NamedElement) dependencyTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setDependencyTarget(NamedElement newDependencyTarget) {
		// save away important other relationships, but omit straight dependencies and implementations
		if (isReplacement() || isResemblance() || isTrace() || getClass() != DependencyImpl.class && getClass() != ImplementationImpl.class)
		{
			if (newDependencyTarget != dependencyTarget && dependencyTarget instanceof NamedElement)
				((NamedElement) dependencyTarget).settable_getReverseDependencies().remove(this);
			if (newDependencyTarget != null && newDependencyTarget.settable_getReverseDependencies().indexOf(this) == -1)
				newDependencyTarget.settable_getReverseDependencies().add(this);
		}

		NamedElement oldDependencyTarget = (NamedElement) dependencyTarget;
		dependencyTarget = (ElementImpl) newDependencyTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.DEPENDENCY__DEPENDENCY_TARGET, oldDependencyTarget, dependencyTarget));			
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isResemblance()
	{
		return (eFlags & RESEMBLANCE_EFLAG) != 0;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResemblance(boolean newResemblance)
	{

		boolean oldResemblance = (eFlags & RESEMBLANCE_EFLAG) != 0;
		if (newResemblance) eFlags |= RESEMBLANCE_EFLAG; else eFlags &= ~RESEMBLANCE_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.DEPENDENCY__RESEMBLANCE, oldResemblance, newResemblance));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReplacement()
	{
		return (eFlags & REPLACEMENT_EFLAG) != 0;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReplacement(boolean newReplacement)
	{

		boolean oldReplacement = (eFlags & REPLACEMENT_EFLAG) != 0;
		if (newReplacement) eFlags |= REPLACEMENT_EFLAG; else eFlags &= ~REPLACEMENT_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.DEPENDENCY__REPLACEMENT, oldReplacement, newReplacement));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTrace()
	{
		return (eFlags & TRACE_EFLAG) != 0;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrace(boolean newTrace)
	{

		boolean oldTrace = (eFlags & TRACE_EFLAG) != 0;
		if (newTrace) eFlags |= TRACE_EFLAG; else eFlags &= ~TRACE_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.DEPENDENCY__TRACE, oldTrace, newTrace));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 *
	public void setDependencyTarget(NamedElement newDependencyTarget) {
		NamedElement oldDependencyTarget = (NamedElement) dependencyTarget;
		dependencyTarget = (ElementImpl) newDependencyTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.DEPENDENCY__DEPENDENCY_TARGET, oldDependencyTarget, dependencyTarget));
	} */


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.DEPENDENCY__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.DEPENDENCY__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.DEPENDENCY__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.DEPENDENCY__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.DEPENDENCY__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.DEPENDENCY__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.DEPENDENCY__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.DEPENDENCY__OWNING_PARAMETER, msgs);
				case UML2Package.DEPENDENCY__CLIENT:
					return ((InternalEList)getClients()).basicAdd(otherEnd, msgs);
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
				case UML2Package.DEPENDENCY__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPENDENCY__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPENDENCY__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPENDENCY__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPENDENCY__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.DEPENDENCY__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPENDENCY__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.DEPENDENCY__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.DEPENDENCY__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.DEPENDENCY__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.DEPENDENCY__OWNING_PARAMETER, msgs);
				case UML2Package.DEPENDENCY__CLIENT:
					return ((InternalEList)getClients()).basicRemove(otherEnd, msgs);
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
				case UML2Package.DEPENDENCY__OWNING_PARAMETER:
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.DEPENDENCY__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.DEPENDENCY__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.DEPENDENCY__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.DEPENDENCY__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.DEPENDENCY__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.DEPENDENCY__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.DEPENDENCY__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.DEPENDENCY__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.DEPENDENCY__UUID:
				return getUuid();
			case UML2Package.DEPENDENCY__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.DEPENDENCY__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.DEPENDENCY__NAME:
				return getName();
			case UML2Package.DEPENDENCY__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.DEPENDENCY__VISIBILITY:
				return getVisibility();
			case UML2Package.DEPENDENCY__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.DEPENDENCY__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.DEPENDENCY__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.DEPENDENCY__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.DEPENDENCY__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.DEPENDENCY__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.DEPENDENCY__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.DEPENDENCY__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.DEPENDENCY__RELATED_ELEMENT:
				return getRelatedElements();
			case UML2Package.DEPENDENCY__SOURCE:
				return getSources();
			case UML2Package.DEPENDENCY__TARGET:
				return getTargets();
			case UML2Package.DEPENDENCY__CLIENT:
				return getClients();
			case UML2Package.DEPENDENCY__SUPPLIER:
				return getSuppliers();
			case UML2Package.DEPENDENCY__DEPENDENCY_TARGET:
				if (resolve) return getDependencyTarget();
				return basicGetDependencyTarget();
			case UML2Package.DEPENDENCY__RESEMBLANCE:
				return isResemblance() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.DEPENDENCY__REPLACEMENT:
				return isReplacement() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.DEPENDENCY__TRACE:
				return isTrace() ? Boolean.TRUE : Boolean.FALSE;
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
			case UML2Package.DEPENDENCY__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.DEPENDENCY__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.DEPENDENCY__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.DEPENDENCY__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.DEPENDENCY__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.DEPENDENCY__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.DEPENDENCY__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.DEPENDENCY__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.DEPENDENCY__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.DEPENDENCY__NAME:
				setName((String)newValue);
				return;
			case UML2Package.DEPENDENCY__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.DEPENDENCY__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.DEPENDENCY__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.DEPENDENCY__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.DEPENDENCY__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.DEPENDENCY__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.DEPENDENCY__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.DEPENDENCY__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.DEPENDENCY__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.DEPENDENCY__CLIENT:
				getClients().clear();
				getClients().addAll((Collection)newValue);
				return;
			case UML2Package.DEPENDENCY__SUPPLIER:
				getSuppliers().clear();
				getSuppliers().addAll((Collection)newValue);
				return;
			case UML2Package.DEPENDENCY__DEPENDENCY_TARGET:
				setDependencyTarget((NamedElement)newValue);
				return;
			case UML2Package.DEPENDENCY__RESEMBLANCE:
				setResemblance(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.DEPENDENCY__REPLACEMENT:
				setReplacement(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.DEPENDENCY__TRACE:
				setTrace(((Boolean)newValue).booleanValue());
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
			case UML2Package.DEPENDENCY__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.DEPENDENCY__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.DEPENDENCY__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.DEPENDENCY__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.DEPENDENCY__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.DEPENDENCY__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.DEPENDENCY__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.DEPENDENCY__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.DEPENDENCY__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.DEPENDENCY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.DEPENDENCY__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.DEPENDENCY__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.DEPENDENCY__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.DEPENDENCY__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.DEPENDENCY__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.DEPENDENCY__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.DEPENDENCY__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.DEPENDENCY__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.DEPENDENCY__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.DEPENDENCY__CLIENT:
				getClients().clear();
				return;
			case UML2Package.DEPENDENCY__SUPPLIER:
				getSuppliers().clear();
				return;
			case UML2Package.DEPENDENCY__DEPENDENCY_TARGET:
				setDependencyTarget((NamedElement)null);
				return;
			case UML2Package.DEPENDENCY__RESEMBLANCE:
				setResemblance(RESEMBLANCE_EDEFAULT);
				return;
			case UML2Package.DEPENDENCY__REPLACEMENT:
				setReplacement(REPLACEMENT_EDEFAULT);
				return;
			case UML2Package.DEPENDENCY__TRACE:
				setTrace(TRACE_EDEFAULT);
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
			case UML2Package.DEPENDENCY__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.DEPENDENCY__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.DEPENDENCY__OWNER:
				return basicGetOwner() != null;
			case UML2Package.DEPENDENCY__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.DEPENDENCY__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.DEPENDENCY__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.DEPENDENCY__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.DEPENDENCY__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.DEPENDENCY__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.DEPENDENCY__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.DEPENDENCY__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.DEPENDENCY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.DEPENDENCY__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.DEPENDENCY__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.DEPENDENCY__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.DEPENDENCY__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.DEPENDENCY__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.DEPENDENCY__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.DEPENDENCY__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.DEPENDENCY__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.DEPENDENCY__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.DEPENDENCY__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.DEPENDENCY__RELATED_ELEMENT:
				return !getRelatedElements().isEmpty();
			case UML2Package.DEPENDENCY__SOURCE:
				return !getSources().isEmpty();
			case UML2Package.DEPENDENCY__TARGET:
				return !getTargets().isEmpty();
			case UML2Package.DEPENDENCY__CLIENT:
				return client != null && !client.isEmpty();
			case UML2Package.DEPENDENCY__SUPPLIER:
				return supplier != null && !supplier.isEmpty();
			case UML2Package.DEPENDENCY__DEPENDENCY_TARGET:
				return dependencyTarget != null;
			case UML2Package.DEPENDENCY__RESEMBLANCE:
				return ((eFlags & RESEMBLANCE_EFLAG) != 0) != RESEMBLANCE_EDEFAULT;
			case UML2Package.DEPENDENCY__REPLACEMENT:
				return ((eFlags & REPLACEMENT_EFLAG) != 0) != REPLACEMENT_EDEFAULT;
			case UML2Package.DEPENDENCY__TRACE:
				return ((eFlags & TRACE_EFLAG) != 0) != TRACE_EDEFAULT;
		}
		return eDynamicIsSet(eFeature);
	}

	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.DEPENDENCY__VISIBILITY:
				return false;
			case UML2Package.DEPENDENCY__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
		}
		return eIsSetGen(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class baseClass)
	{
		if (baseClass == Relationship.class)
		{
			switch (derivedFeatureID)
			{
				case UML2Package.DEPENDENCY__RELATED_ELEMENT: return UML2Package.RELATIONSHIP__RELATED_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == DirectedRelationship.class)
		{
			switch (derivedFeatureID)
			{
				case UML2Package.DEPENDENCY__SOURCE: return UML2Package.DIRECTED_RELATIONSHIP__SOURCE;
				case UML2Package.DEPENDENCY__TARGET: return UML2Package.DIRECTED_RELATIONSHIP__TARGET;
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
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class baseClass)
	{
		if (baseClass == Relationship.class)
		{
			switch (baseFeatureID)
			{
				case UML2Package.RELATIONSHIP__RELATED_ELEMENT: return UML2Package.DEPENDENCY__RELATED_ELEMENT;
				default: return -1;
			}
		}
		if (baseClass == DirectedRelationship.class)
		{
			switch (baseFeatureID)
			{
				case UML2Package.DIRECTED_RELATIONSHIP__SOURCE: return UML2Package.DEPENDENCY__SOURCE;
				case UML2Package.DIRECTED_RELATIONSHIP__TARGET: return UML2Package.DEPENDENCY__TARGET;
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
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (resemblance: "); //$NON-NLS-1$
		result.append((eFlags & RESEMBLANCE_EFLAG) != 0);
		result.append(", replacement: "); //$NON-NLS-1$
		result.append((eFlags & REPLACEMENT_EFLAG) != 0);
		result.append(", trace: "); //$NON-NLS-1$
		result.append((eFlags & TRACE_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getRelatedElementsHelper(EList relatedElement)
	{
		EList source = getSources();
		if (!source.isEmpty()) {
			for (Iterator i = ((InternalEList) source).basicIterator(); i.hasNext(); ) {
				relatedElement.add(i.next());
			}
		}
		EList target = getTargets();
		if (!target.isEmpty()) {
			for (Iterator i = ((InternalEList) target).basicIterator(); i.hasNext(); ) {
				relatedElement.add(i.next());
			}
		}
		return relatedElement;
	}

} //DependencyImpl
