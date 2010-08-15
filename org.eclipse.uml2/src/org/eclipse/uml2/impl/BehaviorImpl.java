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
 * $Id: BehaviorImpl.java,v 1.1 2009-03-04 23:06:35 andrew Exp $
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
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Behavior;
import org.eclipse.uml2.BehavioralFeature;
import org.eclipse.uml2.BehavioredClassifier;
import org.eclipse.uml2.CollaborationOccurrence;
import org.eclipse.uml2.ComponentKind;
import org.eclipse.uml2.Constraint;
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.Parameter;
import org.eclipse.uml2.ParameterSet;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.SubsetEObjectEList;
import org.eclipse.uml2.common.util.SupersetEObjectContainmentWithInverseEList;
import org.eclipse.uml2.internal.operation.BehaviorOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Behavior</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.BehaviorImpl#getOwnedRules <em>Owned Rule</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.BehaviorImpl#isReentrant <em>Is Reentrant</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.BehaviorImpl#getContext <em>Context</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.BehaviorImpl#getRedefinedBehaviors <em>Redefined Behavior</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.BehaviorImpl#getSpecification <em>Specification</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.BehaviorImpl#getParameters <em>Parameter</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.BehaviorImpl#getFormalParameters <em>Formal Parameter</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.BehaviorImpl#getReturnResults <em>Return Result</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.BehaviorImpl#getPreconditions <em>Precondition</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.BehaviorImpl#getPostconditions <em>Postcondition</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.BehaviorImpl#getOwnedParameterSets <em>Owned Parameter Set</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class BehaviorImpl extends ClassImpl implements Behavior {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #isReentrant() <em>Is Reentrant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReentrant()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_REENTRANT_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isReentrant() <em>Is Reentrant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReentrant()
	 * @generated
	 * @ordered
	 */
	protected static final int IS_REENTRANT_EFLAG = 1 << 12;

	/**
	 * The cached value of the '{@link #getRedefinedBehaviors() <em>Redefined Behavior</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRedefinedBehaviors()
	 * @generated
	 * @ordered
	 */
	protected EList redefinedBehavior = null;

	/**
	 * The cached value of the '{@link #getSpecification() <em>Specification</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecification()
	 * @generated
	 * @ordered
	 */
	protected BehavioralFeature specification = null;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameter</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList parameter = null;

	/**
	 * The cached value of the '{@link #getPreconditions() <em>Precondition</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreconditions()
	 * @generated
	 * @ordered
	 */
	protected EList precondition = null;

	/**
	 * The cached value of the '{@link #getPostconditions() <em>Postcondition</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostconditions()
	 * @generated
	 * @ordered
	 */
	protected EList postcondition = null;

	/**
	 * The cached value of the '{@link #getOwnedParameterSets() <em>Owned Parameter Set</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedParameterSets()
	 * @generated
	 * @ordered
	 */
	protected EList ownedParameterSet = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BehaviorImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (BehaviorImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getBehavior();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReentrant()
	{
		return (eFlags & IS_REENTRANT_EFLAG) != 0;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsReentrant(boolean newIsReentrant)
	{

		boolean oldIsReentrant = (eFlags & IS_REENTRANT_EFLAG) != 0;
		if (newIsReentrant) eFlags |= IS_REENTRANT_EFLAG; else eFlags &= ~IS_REENTRANT_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.BEHAVIOR__IS_REENTRANT, oldIsReentrant, newIsReentrant));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BehavioredClassifier getContext()
	{
		if (eContainerFeatureID != UML2Package.BEHAVIOR__CONTEXT) return null;
		return (BehavioredClassifier)eContainer;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public BehavioredClassifier undeleted_getContext()
	{
		BehavioredClassifier temp = getContext();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContext(BehavioredClassifier newContext)
	{

		if (newContext != eContainer || (eContainerFeatureID != UML2Package.BEHAVIOR__CONTEXT && newContext != null))
		{
			if (EcoreUtil.isAncestor(this, newContext))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newContext != null)
				msgs = ((InternalEObject)newContext).eInverseAdd(this, UML2Package.BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR, BehavioredClassifier.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newContext, UML2Package.BEHAVIOR__CONTEXT, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.BEHAVIOR__CONTEXT, newContext, newContext));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getRedefinedBehaviors()
	{
		if (redefinedBehavior == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		redefinedBehavior = new com.intrinsarc.emflist.PersistentEList(Behavior.class, this, UML2Package.BEHAVIOR__REDEFINED_BEHAVIOR);
			 		return redefinedBehavior;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Behavior.class, this, UML2Package.BEHAVIOR__REDEFINED_BEHAVIOR);
		}      
		return redefinedBehavior;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getRedefinedBehaviors()
	{
		if (redefinedBehavior == null)
		{
			
		
			redefinedBehavior = new com.intrinsarc.emflist.PersistentEList(Behavior.class, this, UML2Package.BEHAVIOR__REDEFINED_BEHAVIOR);
		}
		return redefinedBehavior;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getRedefinedBehaviors()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (redefinedBehavior != null)
		{
			for (Object object : redefinedBehavior)
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
    public Behavior getRedefinedBehavior(String name) {
		for (Iterator i = getRedefinedBehaviors().iterator(); i.hasNext(); ) {
			Behavior redefinedBehavior = (Behavior) i.next();
			if (name.equals(redefinedBehavior.getName())) {
				return redefinedBehavior;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BehavioralFeature getSpecification()
	{
		if (specification != null && specification.eIsProxy())
		{
			BehavioralFeature oldSpecification = specification;
			specification = (BehavioralFeature)eResolveProxy((InternalEObject)specification);
			if (specification != oldSpecification)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.BEHAVIOR__SPECIFICATION, oldSpecification, specification));
			}
		}
		return specification;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public BehavioralFeature undeleted_getSpecification()
	{
		BehavioralFeature temp = getSpecification();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BehavioralFeature basicGetSpecification()
	{
		return specification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSpecification(BehavioralFeature newSpecification, NotificationChain msgs)
	{

		BehavioralFeature oldSpecification = specification;
		specification = newSpecification;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.BEHAVIOR__SPECIFICATION, oldSpecification, newSpecification);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpecification(BehavioralFeature newSpecification)
	{

		if (newSpecification != specification)
		{
			NotificationChain msgs = null;
			if (specification != null)
				msgs = ((InternalEObject)specification).eInverseRemove(this, UML2Package.BEHAVIORAL_FEATURE__METHOD, BehavioralFeature.class, msgs);
			if (newSpecification != null)
				msgs = ((InternalEObject)newSpecification).eInverseAdd(this, UML2Package.BEHAVIORAL_FEATURE__METHOD, BehavioralFeature.class, msgs);
			msgs = basicSetSpecification(newSpecification, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.BEHAVIOR__SPECIFICATION, newSpecification, newSpecification));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getParameters()
	{
		if (parameter == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		parameter = new com.intrinsarc.emflist.PersistentEList(Parameter.class, this, UML2Package.BEHAVIOR__PARAMETER);
			 		return parameter;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Parameter.class, this, UML2Package.BEHAVIOR__PARAMETER);
		}      
		return parameter;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getParameters()
	{
		if (parameter == null)
		{
			
		
			parameter = new com.intrinsarc.emflist.PersistentEList(Parameter.class, this, UML2Package.BEHAVIOR__PARAMETER);
		}
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getParameters()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (parameter != null)
		{
			for (Object object : parameter)
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
    public Parameter getParameter(String name) {
		for (Iterator i = getParameters().iterator(); i.hasNext(); ) {
			Parameter parameter = (Parameter) i.next();
			if (name.equals(parameter.getName())) {
				return parameter;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createParameter() instead.
	 */
	public Parameter createParameter(EClass eClass) {
		Parameter newParameter = (Parameter) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.BEHAVIOR__PARAMETER, null, newParameter));
		}
		getParameters().add(newParameter);
		return newParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter createParameter() {
		Parameter newParameter = UML2Factory.eINSTANCE.createParameter();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.BEHAVIOR__PARAMETER, null, newParameter));
		}
		settable_getParameters().add(newParameter);
		return newParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getFormalParameters() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			EList result = (EList) cache.get(eResource(), this,
				UML2Package.eINSTANCE.getBehavior_FormalParameter());

			if (result == null) {
				EList formalParameters = BehaviorOperations
					.getFormalParameters(this);
				cache.put(eResource(), this, UML2Package.eINSTANCE
					.getBehavior_FormalParameter(),
					result = new EcoreEList.UnmodifiableEList(this,
						UML2Package.eINSTANCE.getBehavior_FormalParameter(),
						formalParameters.size(), formalParameters.toArray()));
			}

			return result;
		}

		EList formalParameters = BehaviorOperations.getFormalParameters(this);
		return new EcoreEList.UnmodifiableEList(this, UML2Package.eINSTANCE
			.getBehavior_FormalParameter(), formalParameters.size(),
			formalParameters.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public Parameter getFormalParameter(String name) {
		for (Iterator i = getFormalParameters().iterator(); i.hasNext(); ) {
			Parameter formalParameter = (Parameter) i.next();
			if (name.equals(formalParameter.getName())) {
				return formalParameter;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getReturnResults() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			EList result = (EList) cache.get(eResource(), this,
				UML2Package.eINSTANCE.getBehavior_ReturnResult());

			if (result == null) {
				EList returnResults = BehaviorOperations.getReturnResults(this);
				cache.put(eResource(), this, UML2Package.eINSTANCE
					.getBehavior_ReturnResult(),
					result = new EcoreEList.UnmodifiableEList(this,
						UML2Package.eINSTANCE.getBehavior_ReturnResult(),
						returnResults.size(), returnResults.toArray()));
			}

			return result;
		}

		EList returnResults = BehaviorOperations.getReturnResults(this);
		return new EcoreEList.UnmodifiableEList(this, UML2Package.eINSTANCE
			.getBehavior_ReturnResult(), returnResults.size(), returnResults
			.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public Parameter getReturnResult(String name) {
		for (Iterator i = getReturnResults().iterator(); i.hasNext(); ) {
			Parameter returnResult = (Parameter) i.next();
			if (name.equals(returnResult.getName())) {
				return returnResult;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPreconditions()
	{
		if (precondition == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		precondition = new com.intrinsarc.emflist.PersistentEList(Constraint.class, this, UML2Package.BEHAVIOR__PRECONDITION, new int[] {UML2Package.BEHAVIOR__OWNED_RULE});
			 		return precondition;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Constraint.class, this, UML2Package.BEHAVIOR__PRECONDITION, new int[] {UML2Package.BEHAVIOR__OWNED_RULE});
		}      
		return precondition;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getPreconditions()
	{
		if (precondition == null)
		{
			
		
			precondition = new com.intrinsarc.emflist.PersistentEList(Constraint.class, this, UML2Package.BEHAVIOR__PRECONDITION, new int[] {UML2Package.BEHAVIOR__OWNED_RULE});
		}
		return precondition;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getPreconditions()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (precondition != null)
		{
			for (Object object : precondition)
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
    public Constraint getPrecondition(String name) {
		for (Iterator i = getPreconditions().iterator(); i.hasNext(); ) {
			Constraint precondition = (Constraint) i.next();
			if (name.equals(precondition.getName())) {
				return precondition;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPostconditions()
	{
		if (postcondition == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		postcondition = new com.intrinsarc.emflist.PersistentEList(Constraint.class, this, UML2Package.BEHAVIOR__POSTCONDITION, new int[] {UML2Package.BEHAVIOR__OWNED_RULE});
			 		return postcondition;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Constraint.class, this, UML2Package.BEHAVIOR__POSTCONDITION, new int[] {UML2Package.BEHAVIOR__OWNED_RULE});
		}      
		return postcondition;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getPostconditions()
	{
		if (postcondition == null)
		{
			
		
			postcondition = new com.intrinsarc.emflist.PersistentEList(Constraint.class, this, UML2Package.BEHAVIOR__POSTCONDITION, new int[] {UML2Package.BEHAVIOR__OWNED_RULE});
		}
		return postcondition;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getPostconditions()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (postcondition != null)
		{
			for (Object object : postcondition)
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
    public Constraint getPostcondition(String name) {
		for (Iterator i = getPostconditions().iterator(); i.hasNext(); ) {
			Constraint postcondition = (Constraint) i.next();
			if (name.equals(postcondition.getName())) {
				return postcondition;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedParameterSets()
	{
		if (ownedParameterSet == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		ownedParameterSet = new com.intrinsarc.emflist.PersistentEList(ParameterSet.class, this, UML2Package.BEHAVIOR__OWNED_PARAMETER_SET);
			 		return ownedParameterSet;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(ParameterSet.class, this, UML2Package.BEHAVIOR__OWNED_PARAMETER_SET);
		}      
		return ownedParameterSet;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedParameterSets()
	{
		if (ownedParameterSet == null)
		{
			
		
			ownedParameterSet = new com.intrinsarc.emflist.PersistentEList(ParameterSet.class, this, UML2Package.BEHAVIOR__OWNED_PARAMETER_SET);
		}
		return ownedParameterSet;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedParameterSets()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedParameterSet != null)
		{
			for (Object object : ownedParameterSet)
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
    public ParameterSet getOwnedParameterSet(String name) {
		for (Iterator i = getOwnedParameterSets().iterator(); i.hasNext(); ) {
			ParameterSet ownedParameterSet = (ParameterSet) i.next();
			if (name.equals(ownedParameterSet.getName())) {
				return ownedParameterSet;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createOwnedParameterSet() instead.
	 */
	public ParameterSet createOwnedParameterSet(EClass eClass) {
		ParameterSet newOwnedParameterSet = (ParameterSet) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.BEHAVIOR__OWNED_PARAMETER_SET, null, newOwnedParameterSet));
		}
		getOwnedParameterSets().add(newOwnedParameterSet);
		return newOwnedParameterSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterSet createOwnedParameterSet() {
		ParameterSet newOwnedParameterSet = UML2Factory.eINSTANCE.createParameterSet();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.BEHAVIOR__OWNED_PARAMETER_SET, null, newOwnedParameterSet));
		}
		settable_getOwnedParameterSets().add(newOwnedParameterSet);
		return newOwnedParameterSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedRules()
	{
		if (ownedRule == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		ownedRule = new com.intrinsarc.emflist.PersistentEList(Constraint.class, this, UML2Package.BEHAVIOR__OWNED_RULE, new int[] {UML2Package.BEHAVIOR__PRECONDITION, UML2Package.BEHAVIOR__POSTCONDITION}, UML2Package.CONSTRAINT__NAMESPACE);
			 		return ownedRule;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Constraint.class, this, UML2Package.BEHAVIOR__OWNED_RULE, new int[] {UML2Package.BEHAVIOR__PRECONDITION, UML2Package.BEHAVIOR__POSTCONDITION}, UML2Package.CONSTRAINT__NAMESPACE);
		}      
		return ownedRule;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedRules()
	{
		if (ownedRule == null)
		{
			
		
			ownedRule = new com.intrinsarc.emflist.PersistentEList(Constraint.class, this, UML2Package.BEHAVIOR__OWNED_RULE, new int[] {UML2Package.BEHAVIOR__PRECONDITION, UML2Package.BEHAVIOR__POSTCONDITION}, UML2Package.CONSTRAINT__NAMESPACE);
		}
		return ownedRule;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedRules()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedRule != null)
		{
			for (Object object : ownedRule)
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
				case UML2Package.BEHAVIOR__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.BEHAVIOR__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.BEHAVIOR__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.BEHAVIOR__OWNING_PARAMETER, msgs);
				case UML2Package.BEHAVIOR__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__USE_CASE:
					return ((InternalEList)getUseCases()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_BEHAVIOR:
					return ((InternalEList)getOwnedBehaviors()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__IMPLEMENTATION:
					return ((InternalEList)getImplementations()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_OPERATION:
					return ((InternalEList)getOwnedOperations()).basicAdd(otherEnd, msgs);
				case UML2Package.BEHAVIOR__CONTEXT:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.BEHAVIOR__CONTEXT, msgs);
				case UML2Package.BEHAVIOR__SPECIFICATION:
					if (specification != null)
						msgs = ((InternalEObject)specification).eInverseRemove(this, UML2Package.BEHAVIORAL_FEATURE__METHOD, BehavioralFeature.class, msgs);
					return basicSetSpecification((BehavioralFeature)otherEnd, msgs);
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
			case UML2Package.BEHAVIOR__OWNED_STATE_MACHINE:
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs)
	{
		if (featureID >= 0)
		{
			switch (eDerivedStructuralFeatureID(featureID, baseClass))
			{
				case UML2Package.BEHAVIOR__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.BEHAVIOR__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.BEHAVIOR__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.BEHAVIOR__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.BEHAVIOR__OWNING_PARAMETER, msgs);
				case UML2Package.BEHAVIOR__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_USE_CASE:
					return ((InternalEList)getOwnedUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__USE_CASE:
					return ((InternalEList)getUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OCCURRENCE:
					return ((InternalEList)getOccurrences()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_BEHAVIOR:
					return ((InternalEList)getOwnedBehaviors()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__IMPLEMENTATION:
					return ((InternalEList)getImplementations()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_TRIGGER:
					return ((InternalEList)getOwnedTriggers()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_STATE_MACHINE:
					return ((InternalEList)getOwnedStateMachines()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_ATTRIBUTE:
					return ((InternalEList)getOwnedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_CONNECTOR:
					return ((InternalEList)getOwnedConnectors()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__DELTA_DELETED_ATTRIBUTES:
					return ((InternalEList)getDeltaDeletedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__DELTA_REPLACED_ATTRIBUTES:
					return ((InternalEList)getDeltaReplacedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__DELTA_DELETED_PORTS:
					return ((InternalEList)getDeltaDeletedPorts()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__DELTA_REPLACED_PORTS:
					return ((InternalEList)getDeltaReplacedPorts()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__DELTA_DELETED_CONNECTORS:
					return ((InternalEList)getDeltaDeletedConnectors()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__DELTA_REPLACED_CONNECTORS:
					return ((InternalEList)getDeltaReplacedConnectors()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__DELTA_DELETED_OPERATIONS:
					return ((InternalEList)getDeltaDeletedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__DELTA_REPLACED_OPERATIONS:
					return ((InternalEList)getDeltaReplacedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__DELTA_DELETED_TRACES:
					return ((InternalEList)getDeltaDeletedTraces()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__DELTA_REPLACED_TRACES:
					return ((InternalEList)getDeltaReplacedTraces()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_PORT:
					return ((InternalEList)getOwnedPorts()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_OPERATION:
					return ((InternalEList)getOwnedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__NESTED_CLASSIFIER:
					return ((InternalEList)getNestedClassifiers()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_RECEPTION:
					return ((InternalEList)getOwnedReceptions()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__CONTEXT:
					return eBasicSetContainer(null, UML2Package.BEHAVIOR__CONTEXT, msgs);
				case UML2Package.BEHAVIOR__SPECIFICATION:
					return basicSetSpecification(null, msgs);
				case UML2Package.BEHAVIOR__PARAMETER:
					return ((InternalEList)getParameters()).basicRemove(otherEnd, msgs);
				case UML2Package.BEHAVIOR__OWNED_PARAMETER_SET:
					return ((InternalEList)getOwnedParameterSets()).basicRemove(otherEnd, msgs);
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
				case UML2Package.BEHAVIOR__OWNING_PARAMETER:
					return eContainer.eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__OWNED_PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
				case UML2Package.BEHAVIOR__CONTEXT:
					return eContainer.eInverseRemove(this, UML2Package.BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR, BehavioredClassifier.class, msgs);
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
			case UML2Package.BEHAVIOR__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.BEHAVIOR__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.BEHAVIOR__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.BEHAVIOR__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.BEHAVIOR__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.BEHAVIOR__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.BEHAVIOR__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.BEHAVIOR__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.BEHAVIOR__UUID:
				return getUuid();
			case UML2Package.BEHAVIOR__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.BEHAVIOR__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.BEHAVIOR__NAME:
				return getName();
			case UML2Package.BEHAVIOR__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.BEHAVIOR__VISIBILITY:
				return getVisibility();
			case UML2Package.BEHAVIOR__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.BEHAVIOR__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.BEHAVIOR__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.BEHAVIOR__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.BEHAVIOR__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.BEHAVIOR__MEMBER:
				return getMembers();
			case UML2Package.BEHAVIOR__OWNED_RULE:
				return getOwnedRules();
			case UML2Package.BEHAVIOR__IMPORTED_MEMBER:
				return getImportedMembers();
			case UML2Package.BEHAVIOR__ELEMENT_IMPORT:
				return getElementImports();
			case UML2Package.BEHAVIOR__PACKAGE_IMPORT:
				return getPackageImports();
			case UML2Package.BEHAVIOR__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.BEHAVIOR__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.BEHAVIOR__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.BEHAVIOR__PACKAGE:
				if (resolve) return getPackage();
				return basicGetPackage();
			case UML2Package.BEHAVIOR__IS_RETIRED:
				return isRetired() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.BEHAVIOR__REDEFINITION_CONTEXT:
				return getRedefinitionContexts();
			case UML2Package.BEHAVIOR__IS_LEAF:
				return isLeaf() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.BEHAVIOR__FEATURE:
				return getFeatures();
			case UML2Package.BEHAVIOR__IS_ABSTRACT:
				return isAbstract() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.BEHAVIOR__INHERITED_MEMBER:
				return getInheritedMembers();
			case UML2Package.BEHAVIOR__GENERAL:
				return getGenerals();
			case UML2Package.BEHAVIOR__GENERALIZATION:
				return getGeneralizations();
			case UML2Package.BEHAVIOR__ATTRIBUTE:
				return getAttributes();
			case UML2Package.BEHAVIOR__REDEFINED_CLASSIFIER:
				return getRedefinedClassifiers();
			case UML2Package.BEHAVIOR__SUBSTITUTION:
				return getSubstitutions();
			case UML2Package.BEHAVIOR__POWERTYPE_EXTENT:
				return getPowertypeExtents();
			case UML2Package.BEHAVIOR__OWNED_USE_CASE:
				return getOwnedUseCases();
			case UML2Package.BEHAVIOR__USE_CASE:
				return getUseCases();
			case UML2Package.BEHAVIOR__REPRESENTATION:
				return getRepresentation();
			case UML2Package.BEHAVIOR__OCCURRENCE:
				return getOccurrences();
			case UML2Package.BEHAVIOR__OWNED_BEHAVIOR:
				return getOwnedBehaviors();
			case UML2Package.BEHAVIOR__CLASSIFIER_BEHAVIOR:
				return getClassifierBehavior();
			case UML2Package.BEHAVIOR__IMPLEMENTATION:
				return getImplementations();
			case UML2Package.BEHAVIOR__OWNED_TRIGGER:
				return getOwnedTriggers();
			case UML2Package.BEHAVIOR__OWNED_STATE_MACHINE:
				return getOwnedStateMachines();
			case UML2Package.BEHAVIOR__OWNED_ATTRIBUTE:
				return getOwnedAttributes();
			case UML2Package.BEHAVIOR__PART:
				return getParts();
			case UML2Package.BEHAVIOR__ROLE:
				return getRoles();
			case UML2Package.BEHAVIOR__OWNED_CONNECTOR:
				return getOwnedConnectors();
			case UML2Package.BEHAVIOR__DELTA_DELETED_ATTRIBUTES:
				return getDeltaDeletedAttributes();
			case UML2Package.BEHAVIOR__DELTA_REPLACED_ATTRIBUTES:
				return getDeltaReplacedAttributes();
			case UML2Package.BEHAVIOR__DELTA_DELETED_PORTS:
				return getDeltaDeletedPorts();
			case UML2Package.BEHAVIOR__DELTA_REPLACED_PORTS:
				return getDeltaReplacedPorts();
			case UML2Package.BEHAVIOR__DELTA_DELETED_CONNECTORS:
				return getDeltaDeletedConnectors();
			case UML2Package.BEHAVIOR__DELTA_REPLACED_CONNECTORS:
				return getDeltaReplacedConnectors();
			case UML2Package.BEHAVIOR__DELTA_DELETED_OPERATIONS:
				return getDeltaDeletedOperations();
			case UML2Package.BEHAVIOR__DELTA_REPLACED_OPERATIONS:
				return getDeltaReplacedOperations();
			case UML2Package.BEHAVIOR__DELTA_DELETED_TRACES:
				return getDeltaDeletedTraces();
			case UML2Package.BEHAVIOR__DELTA_REPLACED_TRACES:
				return getDeltaReplacedTraces();
			case UML2Package.BEHAVIOR__OWNED_PORT:
				return getOwnedPorts();
			case UML2Package.BEHAVIOR__OWNED_OPERATION:
				return getOwnedOperations();
			case UML2Package.BEHAVIOR__SUPER_CLASS:
				return getSuperClasses();
			case UML2Package.BEHAVIOR__EXTENSION:
				return getExtensions();
			case UML2Package.BEHAVIOR__NESTED_CLASSIFIER:
				return getNestedClassifiers();
			case UML2Package.BEHAVIOR__IS_ACTIVE:
				return isActive() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.BEHAVIOR__OWNED_RECEPTION:
				return getOwnedReceptions();
			case UML2Package.BEHAVIOR__COMPONENT_KIND:
				return getComponentKind();
			case UML2Package.BEHAVIOR__IS_REENTRANT:
				return isReentrant() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.BEHAVIOR__CONTEXT:
				return getContext();
			case UML2Package.BEHAVIOR__REDEFINED_BEHAVIOR:
				return getRedefinedBehaviors();
			case UML2Package.BEHAVIOR__SPECIFICATION:
				if (resolve) return getSpecification();
				return basicGetSpecification();
			case UML2Package.BEHAVIOR__PARAMETER:
				return getParameters();
			case UML2Package.BEHAVIOR__FORMAL_PARAMETER:
				return getFormalParameters();
			case UML2Package.BEHAVIOR__RETURN_RESULT:
				return getReturnResults();
			case UML2Package.BEHAVIOR__PRECONDITION:
				return getPreconditions();
			case UML2Package.BEHAVIOR__POSTCONDITION:
				return getPostconditions();
			case UML2Package.BEHAVIOR__OWNED_PARAMETER_SET:
				return getOwnedParameterSets();
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
			case UML2Package.BEHAVIOR__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.BEHAVIOR__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.BEHAVIOR__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.BEHAVIOR__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.BEHAVIOR__NAME:
				setName((String)newValue);
				return;
			case UML2Package.BEHAVIOR__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.BEHAVIOR__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_RULE:
				getOwnedRules().clear();
				getOwnedRules().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__ELEMENT_IMPORT:
				getElementImports().clear();
				getElementImports().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__PACKAGE_IMPORT:
				getPackageImports().clear();
				getPackageImports().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.BEHAVIOR__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.BEHAVIOR__IS_RETIRED:
				setIsRetired(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.BEHAVIOR__IS_LEAF:
				setIsLeaf(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.BEHAVIOR__IS_ABSTRACT:
				setIsAbstract(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.BEHAVIOR__GENERALIZATION:
				getGeneralizations().clear();
				getGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				getRedefinedClassifiers().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__SUBSTITUTION:
				getSubstitutions().clear();
				getSubstitutions().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				getPowertypeExtents().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				getOwnedUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__USE_CASE:
				getUseCases().clear();
				getUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)newValue);
				return;
			case UML2Package.BEHAVIOR__OCCURRENCE:
				getOccurrences().clear();
				getOccurrences().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_BEHAVIOR:
				getOwnedBehaviors().clear();
				getOwnedBehaviors().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__CLASSIFIER_BEHAVIOR:
				setClassifierBehavior((Behavior)newValue);
				return;
			case UML2Package.BEHAVIOR__IMPLEMENTATION:
				getImplementations().clear();
				getImplementations().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_TRIGGER:
				getOwnedTriggers().clear();
				getOwnedTriggers().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_STATE_MACHINE:
				getOwnedStateMachines().clear();
				getOwnedStateMachines().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_ATTRIBUTE:
				getOwnedAttributes().clear();
				getOwnedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_CONNECTOR:
				getOwnedConnectors().clear();
				getOwnedConnectors().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__DELTA_DELETED_ATTRIBUTES:
				getDeltaDeletedAttributes().clear();
				getDeltaDeletedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__DELTA_REPLACED_ATTRIBUTES:
				getDeltaReplacedAttributes().clear();
				getDeltaReplacedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__DELTA_DELETED_PORTS:
				getDeltaDeletedPorts().clear();
				getDeltaDeletedPorts().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__DELTA_REPLACED_PORTS:
				getDeltaReplacedPorts().clear();
				getDeltaReplacedPorts().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__DELTA_DELETED_CONNECTORS:
				getDeltaDeletedConnectors().clear();
				getDeltaDeletedConnectors().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__DELTA_REPLACED_CONNECTORS:
				getDeltaReplacedConnectors().clear();
				getDeltaReplacedConnectors().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__DELTA_DELETED_OPERATIONS:
				getDeltaDeletedOperations().clear();
				getDeltaDeletedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__DELTA_REPLACED_OPERATIONS:
				getDeltaReplacedOperations().clear();
				getDeltaReplacedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__DELTA_DELETED_TRACES:
				getDeltaDeletedTraces().clear();
				getDeltaDeletedTraces().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__DELTA_REPLACED_TRACES:
				getDeltaReplacedTraces().clear();
				getDeltaReplacedTraces().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_PORT:
				getOwnedPorts().clear();
				getOwnedPorts().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_OPERATION:
				getOwnedOperations().clear();
				getOwnedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__NESTED_CLASSIFIER:
				getNestedClassifiers().clear();
				getNestedClassifiers().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__IS_ACTIVE:
				setIsActive(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.BEHAVIOR__OWNED_RECEPTION:
				getOwnedReceptions().clear();
				getOwnedReceptions().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__COMPONENT_KIND:
				setComponentKind((ComponentKind)newValue);
				return;
			case UML2Package.BEHAVIOR__IS_REENTRANT:
				setIsReentrant(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.BEHAVIOR__CONTEXT:
				setContext((BehavioredClassifier)newValue);
				return;
			case UML2Package.BEHAVIOR__REDEFINED_BEHAVIOR:
				getRedefinedBehaviors().clear();
				getRedefinedBehaviors().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__SPECIFICATION:
				setSpecification((BehavioralFeature)newValue);
				return;
			case UML2Package.BEHAVIOR__PARAMETER:
				getParameters().clear();
				getParameters().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__PRECONDITION:
				getPreconditions().clear();
				getPreconditions().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__POSTCONDITION:
				getPostconditions().clear();
				getPostconditions().addAll((Collection)newValue);
				return;
			case UML2Package.BEHAVIOR__OWNED_PARAMETER_SET:
				getOwnedParameterSets().clear();
				getOwnedParameterSets().addAll((Collection)newValue);
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
			case UML2Package.BEHAVIOR__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.BEHAVIOR__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.BEHAVIOR__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.BEHAVIOR__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.BEHAVIOR__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.BEHAVIOR__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.BEHAVIOR__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.BEHAVIOR__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.BEHAVIOR__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.BEHAVIOR__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.BEHAVIOR__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.BEHAVIOR__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.BEHAVIOR__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.BEHAVIOR__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.BEHAVIOR__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.BEHAVIOR__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.BEHAVIOR__OWNED_RULE:
				getOwnedRules().clear();
				return;
			case UML2Package.BEHAVIOR__ELEMENT_IMPORT:
				getElementImports().clear();
				return;
			case UML2Package.BEHAVIOR__PACKAGE_IMPORT:
				getPackageImports().clear();
				return;
			case UML2Package.BEHAVIOR__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.BEHAVIOR__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.BEHAVIOR__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.BEHAVIOR__IS_RETIRED:
				setIsRetired(IS_RETIRED_EDEFAULT);
				return;
			case UML2Package.BEHAVIOR__IS_LEAF:
				setIsLeaf(IS_LEAF_EDEFAULT);
				return;
			case UML2Package.BEHAVIOR__IS_ABSTRACT:
				setIsAbstract(IS_ABSTRACT_EDEFAULT);
				return;
			case UML2Package.BEHAVIOR__GENERALIZATION:
				getGeneralizations().clear();
				return;
			case UML2Package.BEHAVIOR__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				return;
			case UML2Package.BEHAVIOR__SUBSTITUTION:
				getSubstitutions().clear();
				return;
			case UML2Package.BEHAVIOR__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				return;
			case UML2Package.BEHAVIOR__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				return;
			case UML2Package.BEHAVIOR__USE_CASE:
				getUseCases().clear();
				return;
			case UML2Package.BEHAVIOR__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)null);
				return;
			case UML2Package.BEHAVIOR__OCCURRENCE:
				getOccurrences().clear();
				return;
			case UML2Package.BEHAVIOR__OWNED_BEHAVIOR:
				getOwnedBehaviors().clear();
				return;
			case UML2Package.BEHAVIOR__CLASSIFIER_BEHAVIOR:
				setClassifierBehavior((Behavior)null);
				return;
			case UML2Package.BEHAVIOR__IMPLEMENTATION:
				getImplementations().clear();
				return;
			case UML2Package.BEHAVIOR__OWNED_TRIGGER:
				getOwnedTriggers().clear();
				return;
			case UML2Package.BEHAVIOR__OWNED_STATE_MACHINE:
				getOwnedStateMachines().clear();
				return;
			case UML2Package.BEHAVIOR__OWNED_ATTRIBUTE:
				getOwnedAttributes().clear();
				return;
			case UML2Package.BEHAVIOR__OWNED_CONNECTOR:
				getOwnedConnectors().clear();
				return;
			case UML2Package.BEHAVIOR__DELTA_DELETED_ATTRIBUTES:
				getDeltaDeletedAttributes().clear();
				return;
			case UML2Package.BEHAVIOR__DELTA_REPLACED_ATTRIBUTES:
				getDeltaReplacedAttributes().clear();
				return;
			case UML2Package.BEHAVIOR__DELTA_DELETED_PORTS:
				getDeltaDeletedPorts().clear();
				return;
			case UML2Package.BEHAVIOR__DELTA_REPLACED_PORTS:
				getDeltaReplacedPorts().clear();
				return;
			case UML2Package.BEHAVIOR__DELTA_DELETED_CONNECTORS:
				getDeltaDeletedConnectors().clear();
				return;
			case UML2Package.BEHAVIOR__DELTA_REPLACED_CONNECTORS:
				getDeltaReplacedConnectors().clear();
				return;
			case UML2Package.BEHAVIOR__DELTA_DELETED_OPERATIONS:
				getDeltaDeletedOperations().clear();
				return;
			case UML2Package.BEHAVIOR__DELTA_REPLACED_OPERATIONS:
				getDeltaReplacedOperations().clear();
				return;
			case UML2Package.BEHAVIOR__DELTA_DELETED_TRACES:
				getDeltaDeletedTraces().clear();
				return;
			case UML2Package.BEHAVIOR__DELTA_REPLACED_TRACES:
				getDeltaReplacedTraces().clear();
				return;
			case UML2Package.BEHAVIOR__OWNED_PORT:
				getOwnedPorts().clear();
				return;
			case UML2Package.BEHAVIOR__OWNED_OPERATION:
				getOwnedOperations().clear();
				return;
			case UML2Package.BEHAVIOR__NESTED_CLASSIFIER:
				getNestedClassifiers().clear();
				return;
			case UML2Package.BEHAVIOR__IS_ACTIVE:
				setIsActive(IS_ACTIVE_EDEFAULT);
				return;
			case UML2Package.BEHAVIOR__OWNED_RECEPTION:
				getOwnedReceptions().clear();
				return;
			case UML2Package.BEHAVIOR__COMPONENT_KIND:
				setComponentKind(COMPONENT_KIND_EDEFAULT);
				return;
			case UML2Package.BEHAVIOR__IS_REENTRANT:
				setIsReentrant(IS_REENTRANT_EDEFAULT);
				return;
			case UML2Package.BEHAVIOR__CONTEXT:
				setContext((BehavioredClassifier)null);
				return;
			case UML2Package.BEHAVIOR__REDEFINED_BEHAVIOR:
				getRedefinedBehaviors().clear();
				return;
			case UML2Package.BEHAVIOR__SPECIFICATION:
				setSpecification((BehavioralFeature)null);
				return;
			case UML2Package.BEHAVIOR__PARAMETER:
				getParameters().clear();
				return;
			case UML2Package.BEHAVIOR__PRECONDITION:
				getPreconditions().clear();
				return;
			case UML2Package.BEHAVIOR__POSTCONDITION:
				getPostconditions().clear();
				return;
			case UML2Package.BEHAVIOR__OWNED_PARAMETER_SET:
				getOwnedParameterSets().clear();
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
			case UML2Package.BEHAVIOR__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.BEHAVIOR__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.BEHAVIOR__OWNER:
				return basicGetOwner() != null;
			case UML2Package.BEHAVIOR__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.BEHAVIOR__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.BEHAVIOR__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.BEHAVIOR__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.BEHAVIOR__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.BEHAVIOR__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.BEHAVIOR__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.BEHAVIOR__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.BEHAVIOR__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.BEHAVIOR__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.BEHAVIOR__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.BEHAVIOR__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.BEHAVIOR__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.BEHAVIOR__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.BEHAVIOR__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.BEHAVIOR__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.BEHAVIOR__MEMBER:
				return !getMembers().isEmpty();
			case UML2Package.BEHAVIOR__OWNED_RULE:
				return ownedRule != null && !ownedRule.isEmpty();
			case UML2Package.BEHAVIOR__IMPORTED_MEMBER:
				return !getImportedMembers().isEmpty();
			case UML2Package.BEHAVIOR__ELEMENT_IMPORT:
				return elementImport != null && !elementImport.isEmpty();
			case UML2Package.BEHAVIOR__PACKAGE_IMPORT:
				return packageImport != null && !packageImport.isEmpty();
			case UML2Package.BEHAVIOR__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.BEHAVIOR__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.BEHAVIOR__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.BEHAVIOR__PACKAGE:
				return basicGetPackage() != null;
			case UML2Package.BEHAVIOR__IS_RETIRED:
				return ((eFlags & IS_RETIRED_EFLAG) != 0) != IS_RETIRED_EDEFAULT;
			case UML2Package.BEHAVIOR__REDEFINITION_CONTEXT:
				return !getRedefinitionContexts().isEmpty();
			case UML2Package.BEHAVIOR__IS_LEAF:
				return ((eFlags & IS_LEAF_EFLAG) != 0) != IS_LEAF_EDEFAULT;
			case UML2Package.BEHAVIOR__FEATURE:
				return !getFeatures().isEmpty();
			case UML2Package.BEHAVIOR__IS_ABSTRACT:
				return isAbstract() != IS_ABSTRACT_EDEFAULT;
			case UML2Package.BEHAVIOR__INHERITED_MEMBER:
				return !getInheritedMembers().isEmpty();
			case UML2Package.BEHAVIOR__GENERAL:
				return !getGenerals().isEmpty();
			case UML2Package.BEHAVIOR__GENERALIZATION:
				return generalization != null && !generalization.isEmpty();
			case UML2Package.BEHAVIOR__ATTRIBUTE:
				return !getAttributes().isEmpty();
			case UML2Package.BEHAVIOR__REDEFINED_CLASSIFIER:
				return redefinedClassifier != null && !redefinedClassifier.isEmpty();
			case UML2Package.BEHAVIOR__SUBSTITUTION:
				return substitution != null && !substitution.isEmpty();
			case UML2Package.BEHAVIOR__POWERTYPE_EXTENT:
				return powertypeExtent != null && !powertypeExtent.isEmpty();
			case UML2Package.BEHAVIOR__OWNED_USE_CASE:
				return ownedUseCase != null && !ownedUseCase.isEmpty();
			case UML2Package.BEHAVIOR__USE_CASE:
				return useCase != null && !useCase.isEmpty();
			case UML2Package.BEHAVIOR__REPRESENTATION:
				return representation != null;
			case UML2Package.BEHAVIOR__OCCURRENCE:
				return occurrence != null && !occurrence.isEmpty();
			case UML2Package.BEHAVIOR__OWNED_BEHAVIOR:
				return !getOwnedBehaviors().isEmpty();
			case UML2Package.BEHAVIOR__CLASSIFIER_BEHAVIOR:
				return classifierBehavior != null;
			case UML2Package.BEHAVIOR__IMPLEMENTATION:
				return implementation != null && !implementation.isEmpty();
			case UML2Package.BEHAVIOR__OWNED_TRIGGER:
				return ownedTrigger != null && !ownedTrigger.isEmpty();
			case UML2Package.BEHAVIOR__OWNED_STATE_MACHINE:
				return !getOwnedStateMachines().isEmpty();
			case UML2Package.BEHAVIOR__OWNED_ATTRIBUTE:
				return !getOwnedAttributes().isEmpty();
			case UML2Package.BEHAVIOR__PART:
				return !getParts().isEmpty();
			case UML2Package.BEHAVIOR__ROLE:
				return !getRoles().isEmpty();
			case UML2Package.BEHAVIOR__OWNED_CONNECTOR:
				return ownedConnector != null && !ownedConnector.isEmpty();
			case UML2Package.BEHAVIOR__DELTA_DELETED_ATTRIBUTES:
				return deltaDeletedAttributes != null && !deltaDeletedAttributes.isEmpty();
			case UML2Package.BEHAVIOR__DELTA_REPLACED_ATTRIBUTES:
				return deltaReplacedAttributes != null && !deltaReplacedAttributes.isEmpty();
			case UML2Package.BEHAVIOR__DELTA_DELETED_PORTS:
				return deltaDeletedPorts != null && !deltaDeletedPorts.isEmpty();
			case UML2Package.BEHAVIOR__DELTA_REPLACED_PORTS:
				return deltaReplacedPorts != null && !deltaReplacedPorts.isEmpty();
			case UML2Package.BEHAVIOR__DELTA_DELETED_CONNECTORS:
				return deltaDeletedConnectors != null && !deltaDeletedConnectors.isEmpty();
			case UML2Package.BEHAVIOR__DELTA_REPLACED_CONNECTORS:
				return deltaReplacedConnectors != null && !deltaReplacedConnectors.isEmpty();
			case UML2Package.BEHAVIOR__DELTA_DELETED_OPERATIONS:
				return deltaDeletedOperations != null && !deltaDeletedOperations.isEmpty();
			case UML2Package.BEHAVIOR__DELTA_REPLACED_OPERATIONS:
				return deltaReplacedOperations != null && !deltaReplacedOperations.isEmpty();
			case UML2Package.BEHAVIOR__DELTA_DELETED_TRACES:
				return deltaDeletedTraces != null && !deltaDeletedTraces.isEmpty();
			case UML2Package.BEHAVIOR__DELTA_REPLACED_TRACES:
				return deltaReplacedTraces != null && !deltaReplacedTraces.isEmpty();
			case UML2Package.BEHAVIOR__OWNED_PORT:
				return ownedPort != null && !ownedPort.isEmpty();
			case UML2Package.BEHAVIOR__OWNED_OPERATION:
				return ownedOperation != null && !ownedOperation.isEmpty();
			case UML2Package.BEHAVIOR__SUPER_CLASS:
				return !getSuperClasses().isEmpty();
			case UML2Package.BEHAVIOR__EXTENSION:
				return !getExtensions().isEmpty();
			case UML2Package.BEHAVIOR__NESTED_CLASSIFIER:
				return nestedClassifier != null && !nestedClassifier.isEmpty();
			case UML2Package.BEHAVIOR__IS_ACTIVE:
				return ((eFlags & IS_ACTIVE_EFLAG) != 0) != IS_ACTIVE_EDEFAULT;
			case UML2Package.BEHAVIOR__OWNED_RECEPTION:
				return ownedReception != null && !ownedReception.isEmpty();
			case UML2Package.BEHAVIOR__COMPONENT_KIND:
				return componentKind != COMPONENT_KIND_EDEFAULT;
			case UML2Package.BEHAVIOR__IS_REENTRANT:
				return ((eFlags & IS_REENTRANT_EFLAG) != 0) != IS_REENTRANT_EDEFAULT;
			case UML2Package.BEHAVIOR__CONTEXT:
				return getContext() != null;
			case UML2Package.BEHAVIOR__REDEFINED_BEHAVIOR:
				return redefinedBehavior != null && !redefinedBehavior.isEmpty();
			case UML2Package.BEHAVIOR__SPECIFICATION:
				return specification != null;
			case UML2Package.BEHAVIOR__PARAMETER:
				return parameter != null && !parameter.isEmpty();
			case UML2Package.BEHAVIOR__FORMAL_PARAMETER:
				return !getFormalParameters().isEmpty();
			case UML2Package.BEHAVIOR__RETURN_RESULT:
				return !getReturnResults().isEmpty();
			case UML2Package.BEHAVIOR__PRECONDITION:
				return precondition != null && !precondition.isEmpty();
			case UML2Package.BEHAVIOR__POSTCONDITION:
				return postcondition != null && !postcondition.isEmpty();
			case UML2Package.BEHAVIOR__OWNED_PARAMETER_SET:
				return ownedParameterSet != null && !ownedParameterSet.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.BEHAVIOR__VISIBILITY:
				return false;
			case UML2Package.BEHAVIOR__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.BEHAVIOR__OWNED_BEHAVIOR:
				return ownedBehavior != null && !ownedBehavior.isEmpty();
			case UML2Package.BEHAVIOR__OWNED_STATE_MACHINE:
				return ownedStateMachine != null && !ownedStateMachine.isEmpty();
			case UML2Package.BEHAVIOR__OWNED_ATTRIBUTE:
				return ownedAttribute != null && !ownedAttribute.isEmpty();
		}
		return eIsSetGen(eFeature);
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
		result.append(" (isReentrant: "); //$NON-NLS-1$
		result.append((eFlags & IS_REENTRANT_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getRedefinedElementsHelper(EList redefinedElement)
	{
		super.getRedefinedElementsHelper(redefinedElement);
		if (eIsSet(UML2Package.eINSTANCE.getBehavior_RedefinedBehavior())) {
			for (Iterator i = ((InternalEList) getRedefinedBehaviors()).basicIterator(); i.hasNext(); ) {
				redefinedElement.add(i.next());
			}
		}
		return redefinedElement;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedMembersHelper(EList ownedMember)
	{
		super.getOwnedMembersHelper(ownedMember);
		if (eIsSet(UML2Package.eINSTANCE.getBehavior_Parameter())) {
			ownedMember.addAll(getParameters());
		}
		return ownedMember;
	}


} //BehaviorImpl
