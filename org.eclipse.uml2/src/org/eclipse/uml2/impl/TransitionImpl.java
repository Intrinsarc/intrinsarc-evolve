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
 * $Id: TransitionImpl.java,v 1.1 2009-03-04 23:06:36 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;
import java.util.Collections;
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

import org.eclipse.uml2.Activity;
import org.eclipse.uml2.Classifier;
import org.eclipse.uml2.Constraint;
import org.eclipse.uml2.Element;
import org.eclipse.uml2.Region;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.Transition;
import org.eclipse.uml2.TransitionKind;
import org.eclipse.uml2.Trigger;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.Vertex;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.CacheAdapter;

import org.eclipse.uml2.internal.operation.StateMachineOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.TransitionImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TransitionImpl#getContainer <em>Container</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TransitionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TransitionImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TransitionImpl#getRedefinedTransition <em>Redefined Transition</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TransitionImpl#getTriggers <em>Trigger</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TransitionImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TransitionImpl#getEffect <em>Effect</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.TransitionImpl#getRedefinitionContexts <em>Redefinition Context</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionImpl extends RedefinableElementImpl implements Transition {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final TransitionKind KIND_EDEFAULT = TransitionKind.INTERNAL_LITERAL;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected TransitionKind kind = KIND_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected Vertex source = null;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Vertex target = null;

	/**
	 * The cached value of the '{@link #getRedefinedTransition() <em>Redefined Transition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRedefinedTransition()
	 * @generated
	 * @ordered
	 */
	protected Transition redefinedTransition = null;

	/**
	 * The cached value of the '{@link #getTriggers() <em>Trigger</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTriggers()
	 * @generated
	 * @ordered
	 */
	protected EList trigger = null;

	/**
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected Constraint guard = null;

	/**
	 * The cached value of the '{@link #getEffect() <em>Effect</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEffect()
	 * @generated
	 * @ordered
	 */
	protected Activity effect = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransitionImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (TransitionImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getTransition();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransitionKind getKind()
	{
		return kind;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKind(TransitionKind newKind)
	{

		TransitionKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.TRANSITION__KIND, oldKind, kind));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Region getContainer()
	{
		if (eContainerFeatureID != UML2Package.TRANSITION__CONTAINER) return null;
		return (Region)eContainer;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Region undeleted_getContainer()
	{
		Region temp = getContainer();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainer(Region newContainer)
	{

		if (newContainer != eContainer || (eContainerFeatureID != UML2Package.TRANSITION__CONTAINER && newContainer != null))
		{
			if (EcoreUtil.isAncestor(this, newContainer))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newContainer != null)
				msgs = ((InternalEObject)newContainer).eInverseAdd(this, UML2Package.REGION__TRANSITION, Region.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newContainer, UML2Package.TRANSITION__CONTAINER, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.TRANSITION__CONTAINER, newContainer, newContainer));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vertex getSource()
	{
		if (source != null && source.eIsProxy())
		{
			Vertex oldSource = source;
			source = (Vertex)eResolveProxy((InternalEObject)source);
			if (source != oldSource)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.TRANSITION__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Vertex undeleted_getSource()
	{
		Vertex temp = getSource();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vertex basicGetSource()
	{
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(Vertex newSource, NotificationChain msgs)
	{

		Vertex oldSource = source;
		source = newSource;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.TRANSITION__SOURCE, oldSource, newSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(Vertex newSource)
	{

		if (newSource != source)
		{
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject)source).eInverseRemove(this, UML2Package.VERTEX__OUTGOING, Vertex.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, UML2Package.VERTEX__OUTGOING, Vertex.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.TRANSITION__SOURCE, newSource, newSource));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vertex getTarget()
	{
		if (target != null && target.eIsProxy())
		{
			Vertex oldTarget = target;
			target = (Vertex)eResolveProxy((InternalEObject)target);
			if (target != oldTarget)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.TRANSITION__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Vertex undeleted_getTarget()
	{
		Vertex temp = getTarget();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Vertex basicGetTarget()
	{
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(Vertex newTarget, NotificationChain msgs)
	{

		Vertex oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.TRANSITION__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Vertex newTarget)
	{

		if (newTarget != target)
		{
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, UML2Package.VERTEX__INCOMING, Vertex.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, UML2Package.VERTEX__INCOMING, Vertex.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.TRANSITION__TARGET, newTarget, newTarget));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition getRedefinedTransition()
	{
		if (redefinedTransition != null && redefinedTransition.eIsProxy())
		{
			Transition oldRedefinedTransition = redefinedTransition;
			redefinedTransition = (Transition)eResolveProxy((InternalEObject)redefinedTransition);
			if (redefinedTransition != oldRedefinedTransition)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.TRANSITION__REDEFINED_TRANSITION, oldRedefinedTransition, redefinedTransition));
			}
		}
		return redefinedTransition;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Transition undeleted_getRedefinedTransition()
	{
		Transition temp = getRedefinedTransition();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition basicGetRedefinedTransition()
	{
		return redefinedTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRedefinedTransition(Transition newRedefinedTransition)
	{

		Transition oldRedefinedTransition = redefinedTransition;
		redefinedTransition = newRedefinedTransition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.TRANSITION__REDEFINED_TRANSITION, oldRedefinedTransition, redefinedTransition));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getTriggers()
	{
		if (trigger == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		trigger = new com.intrinsarc.emflist.PersistentEList(Trigger.class, this, UML2Package.TRANSITION__TRIGGER);
			 		return trigger;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Trigger.class, this, UML2Package.TRANSITION__TRIGGER);
		}      
		return trigger;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getTriggers()
	{
		if (trigger == null)
		{
			
		
			trigger = new com.intrinsarc.emflist.PersistentEList(Trigger.class, this, UML2Package.TRANSITION__TRIGGER);
		}
		return trigger;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getTriggers()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (trigger != null)
		{
			for (Object object : trigger)
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
    public Trigger getTrigger(String name) {
		for (Iterator i = getTriggers().iterator(); i.hasNext(); ) {
			Trigger trigger = (Trigger) i.next();
			if (name.equals(trigger.getName())) {
				return trigger;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Constraint getGuard()
	{
		return guard;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Constraint undeleted_getGuard()
	{
		Constraint temp = getGuard();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGuard(Constraint newGuard, NotificationChain msgs)
	{

		Constraint oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.TRANSITION__GUARD, oldGuard, newGuard);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGuard(Constraint newGuard)
	{

		if (newGuard != guard)
		{
			NotificationChain msgs = null;
			if (guard != null)
				msgs = ((InternalEObject)guard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.TRANSITION__GUARD, null, msgs);
			if (newGuard != null)
				msgs = ((InternalEObject)newGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UML2Package.TRANSITION__GUARD, null, msgs);
			msgs = basicSetGuard(newGuard, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.TRANSITION__GUARD, newGuard, newGuard));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Constraint createGuard(EClass eClass) {
		Constraint newGuard = (Constraint) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.TRANSITION__GUARD, null, newGuard));
		}
		setGuard(newGuard);
		return newGuard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Constraint createGuard() {
		Constraint newGuard = UML2Factory.eINSTANCE.createConstraint();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.TRANSITION__GUARD, null, newGuard));
		}
		setGuard(newGuard);
		return newGuard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Activity getEffect()
	{
		return effect;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Activity undeleted_getEffect()
	{
		Activity temp = getEffect();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEffect(Activity newEffect, NotificationChain msgs)
	{

		Activity oldEffect = effect;
		effect = newEffect;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.TRANSITION__EFFECT, oldEffect, newEffect);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEffect(Activity newEffect)
	{

		if (newEffect != effect)
		{
			NotificationChain msgs = null;
			if (effect != null)
				msgs = ((InternalEObject)effect).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.TRANSITION__EFFECT, null, msgs);
			if (newEffect != null)
				msgs = ((InternalEObject)newEffect).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UML2Package.TRANSITION__EFFECT, null, msgs);
			msgs = basicSetEffect(newEffect, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.TRANSITION__EFFECT, newEffect, newEffect));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createEffect() instead.
	 */
	public Activity createEffect(EClass eClass) {
		Activity newEffect = (Activity) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.TRANSITION__EFFECT, null, newEffect));
		}
		setEffect(newEffect);
		return newEffect;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Activity createEffect() {
		Activity newEffect = UML2Factory.eINSTANCE.createActivity();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.TRANSITION__EFFECT, null, newEffect));
		}
		setEffect(newEffect);
		return newEffect;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getRedefinitionContexts() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			EList result = (EList) cache.get(this, UML2Package.eINSTANCE
				.getRedefinableElement_RedefinitionContext());

			if (result == null) {
				EList redefinitionContexts = StateMachineOperations
					.getRedefinitionContexts(this);
				cache.put(this, UML2Package.eINSTANCE
					.getRedefinableElement_RedefinitionContext(),
					result = new EcoreEList.UnmodifiableEList(this,
						UML2Package.eINSTANCE
							.getRedefinableElement_RedefinitionContext(),
						redefinitionContexts.size(), redefinitionContexts
							.toArray()));
			}

			return result;
		}

		EList redefinitionContexts = StateMachineOperations
			.getRedefinitionContexts(this);
		return new EcoreEList.UnmodifiableEList(this, UML2Package.eINSTANCE
			.getRedefinableElement_RedefinitionContext(), redefinitionContexts
			.size(), redefinitionContexts.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public Classifier getRedefinitionContext(String name) {
		for (Iterator i = getRedefinitionContexts().iterator(); i.hasNext(); ) {
			Classifier redefinitionContext = (Classifier) i.next();
			if (name.equals(redefinitionContext.getName())) {
				return redefinitionContext;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element basicGetOwner()
	{
		Region container = getContainer();			
		if (container != null) {
			return container;
		}
		return super.basicGetOwner();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedElementsHelper(EList ownedElement)
	{
		super.getOwnedElementsHelper(ownedElement);
		if (eIsSet(UML2Package.eINSTANCE.getTransition_Guard())) {
			ownedElement.add(getGuard());
		}
		if (eIsSet(UML2Package.eINSTANCE.getTransition_Effect())) {
			ownedElement.add(getEffect());
		}
		return ownedElement;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getRedefinedElements()
	{
		return new EcoreEList.UnmodifiableEList(this, null, 0, Collections.EMPTY_LIST.toArray());
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
				case UML2Package.TRANSITION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.TRANSITION__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.TRANSITION__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.TRANSITION__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.TRANSITION__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.TRANSITION__CONTAINER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.TRANSITION__CONTAINER, msgs);
				case UML2Package.TRANSITION__SOURCE:
					if (source != null)
						msgs = ((InternalEObject)source).eInverseRemove(this, UML2Package.VERTEX__OUTGOING, Vertex.class, msgs);
					return basicSetSource((Vertex)otherEnd, msgs);
				case UML2Package.TRANSITION__TARGET:
					if (target != null)
						msgs = ((InternalEObject)target).eInverseRemove(this, UML2Package.VERTEX__INCOMING, Vertex.class, msgs);
					return basicSetTarget((Vertex)otherEnd, msgs);
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
				case UML2Package.TRANSITION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.TRANSITION__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.TRANSITION__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.TRANSITION__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.TRANSITION__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.TRANSITION__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.TRANSITION__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.TRANSITION__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.TRANSITION__CONTAINER:
					return eBasicSetContainer(null, UML2Package.TRANSITION__CONTAINER, msgs);
				case UML2Package.TRANSITION__SOURCE:
					return basicSetSource(null, msgs);
				case UML2Package.TRANSITION__TARGET:
					return basicSetTarget(null, msgs);
				case UML2Package.TRANSITION__GUARD:
					return basicSetGuard(null, msgs);
				case UML2Package.TRANSITION__EFFECT:
					return basicSetEffect(null, msgs);
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
				case UML2Package.TRANSITION__CONTAINER:
					return eContainer.eInverseRemove(this, UML2Package.REGION__TRANSITION, Region.class, msgs);
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
			case UML2Package.TRANSITION__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.TRANSITION__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.TRANSITION__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.TRANSITION__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.TRANSITION__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.TRANSITION__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.TRANSITION__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.TRANSITION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.TRANSITION__UUID:
				return getUuid();
			case UML2Package.TRANSITION__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.TRANSITION__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.TRANSITION__NAME:
				return getName();
			case UML2Package.TRANSITION__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.TRANSITION__VISIBILITY:
				return getVisibility();
			case UML2Package.TRANSITION__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.TRANSITION__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.TRANSITION__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.TRANSITION__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.TRANSITION__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.TRANSITION__REDEFINITION_CONTEXT:
				return getRedefinitionContexts();
			case UML2Package.TRANSITION__IS_LEAF:
				return isLeaf() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.TRANSITION__KIND:
				return getKind();
			case UML2Package.TRANSITION__CONTAINER:
				return getContainer();
			case UML2Package.TRANSITION__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case UML2Package.TRANSITION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case UML2Package.TRANSITION__REDEFINED_TRANSITION:
				if (resolve) return getRedefinedTransition();
				return basicGetRedefinedTransition();
			case UML2Package.TRANSITION__TRIGGER:
				return getTriggers();
			case UML2Package.TRANSITION__GUARD:
				return getGuard();
			case UML2Package.TRANSITION__EFFECT:
				return getEffect();
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
			case UML2Package.TRANSITION__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.TRANSITION__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.TRANSITION__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.TRANSITION__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.TRANSITION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.TRANSITION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.TRANSITION__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.TRANSITION__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.TRANSITION__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.TRANSITION__NAME:
				setName((String)newValue);
				return;
			case UML2Package.TRANSITION__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.TRANSITION__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.TRANSITION__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.TRANSITION__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.TRANSITION__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.TRANSITION__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.TRANSITION__IS_LEAF:
				setIsLeaf(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.TRANSITION__KIND:
				setKind((TransitionKind)newValue);
				return;
			case UML2Package.TRANSITION__CONTAINER:
				setContainer((Region)newValue);
				return;
			case UML2Package.TRANSITION__SOURCE:
				setSource((Vertex)newValue);
				return;
			case UML2Package.TRANSITION__TARGET:
				setTarget((Vertex)newValue);
				return;
			case UML2Package.TRANSITION__REDEFINED_TRANSITION:
				setRedefinedTransition((Transition)newValue);
				return;
			case UML2Package.TRANSITION__TRIGGER:
				getTriggers().clear();
				getTriggers().addAll((Collection)newValue);
				return;
			case UML2Package.TRANSITION__GUARD:
				setGuard((Constraint)newValue);
				return;
			case UML2Package.TRANSITION__EFFECT:
				setEffect((Activity)newValue);
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
			case UML2Package.TRANSITION__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.TRANSITION__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.TRANSITION__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.TRANSITION__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.TRANSITION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.TRANSITION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.TRANSITION__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.TRANSITION__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.TRANSITION__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.TRANSITION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.TRANSITION__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.TRANSITION__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.TRANSITION__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.TRANSITION__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.TRANSITION__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.TRANSITION__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.TRANSITION__IS_LEAF:
				setIsLeaf(IS_LEAF_EDEFAULT);
				return;
			case UML2Package.TRANSITION__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case UML2Package.TRANSITION__CONTAINER:
				setContainer((Region)null);
				return;
			case UML2Package.TRANSITION__SOURCE:
				setSource((Vertex)null);
				return;
			case UML2Package.TRANSITION__TARGET:
				setTarget((Vertex)null);
				return;
			case UML2Package.TRANSITION__REDEFINED_TRANSITION:
				setRedefinedTransition((Transition)null);
				return;
			case UML2Package.TRANSITION__TRIGGER:
				getTriggers().clear();
				return;
			case UML2Package.TRANSITION__GUARD:
				setGuard((Constraint)null);
				return;
			case UML2Package.TRANSITION__EFFECT:
				setEffect((Activity)null);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.TRANSITION__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.TRANSITION__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.TRANSITION__OWNER:
				return basicGetOwner() != null;
			case UML2Package.TRANSITION__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.TRANSITION__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.TRANSITION__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.TRANSITION__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.TRANSITION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.TRANSITION__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.TRANSITION__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.TRANSITION__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.TRANSITION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.TRANSITION__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.TRANSITION__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.TRANSITION__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.TRANSITION__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.TRANSITION__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.TRANSITION__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.TRANSITION__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.TRANSITION__REDEFINITION_CONTEXT:
				return !getRedefinitionContexts().isEmpty();
			case UML2Package.TRANSITION__IS_LEAF:
				return ((eFlags & IS_LEAF_EFLAG) != 0) != IS_LEAF_EDEFAULT;
			case UML2Package.TRANSITION__KIND:
				return kind != KIND_EDEFAULT;
			case UML2Package.TRANSITION__CONTAINER:
				return getContainer() != null;
			case UML2Package.TRANSITION__SOURCE:
				return source != null;
			case UML2Package.TRANSITION__TARGET:
				return target != null;
			case UML2Package.TRANSITION__REDEFINED_TRANSITION:
				return redefinedTransition != null;
			case UML2Package.TRANSITION__TRIGGER:
				return trigger != null && !trigger.isEmpty();
			case UML2Package.TRANSITION__GUARD:
				return guard != null;
			case UML2Package.TRANSITION__EFFECT:
				return effect != null;
		}
		return eDynamicIsSet(eFeature);
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
		result.append(" (kind: "); //$NON-NLS-1$
		result.append(kind);
		result.append(')');
		return result.toString();
	}


} //TransitionImpl
