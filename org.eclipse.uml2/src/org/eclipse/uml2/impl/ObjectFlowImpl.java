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
 * $Id: ObjectFlowImpl.java,v 1.1 2009-03-04 23:06:37 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Activity;
import org.eclipse.uml2.ActivityNode;
import org.eclipse.uml2.Behavior;
import org.eclipse.uml2.InterruptibleActivityRegion;
import org.eclipse.uml2.ObjectFlow;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.StructuredActivityNode;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.ValueSpecification;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Object Flow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.ObjectFlowImpl#isMulticast <em>Is Multicast</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ObjectFlowImpl#isMultireceive <em>Is Multireceive</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ObjectFlowImpl#getTransformation <em>Transformation</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ObjectFlowImpl#getSelection <em>Selection</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ObjectFlowImpl extends ActivityEdgeImpl implements ObjectFlow {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #isMulticast() <em>Is Multicast</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isMulticast()
	 * @generated
	 * @ordered
	 */
    protected static final boolean IS_MULTICAST_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isMulticast() <em>Is Multicast</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMulticast()
	 * @generated
	 * @ordered
	 */
	protected static final int IS_MULTICAST_EFLAG = 1 << 9;

	/**
	 * The default value of the '{@link #isMultireceive() <em>Is Multireceive</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isMultireceive()
	 * @generated
	 * @ordered
	 */
    protected static final boolean IS_MULTIRECEIVE_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isMultireceive() <em>Is Multireceive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultireceive()
	 * @generated
	 * @ordered
	 */
	protected static final int IS_MULTIRECEIVE_EFLAG = 1 << 10;

	/**
	 * The cached value of the '{@link #getTransformation() <em>Transformation</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getTransformation()
	 * @generated
	 * @ordered
	 */
    protected Behavior transformation = null;

	/**
	 * The cached value of the '{@link #getSelection() <em>Selection</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSelection()
	 * @generated
	 * @ordered
	 */
    protected Behavior selection = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected ObjectFlowImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (ObjectFlowImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getObjectFlow();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isMulticast()
	{
		return (eFlags & IS_MULTICAST_EFLAG) != 0;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setIsMulticast(boolean newIsMulticast)
	{

		boolean oldIsMulticast = (eFlags & IS_MULTICAST_EFLAG) != 0;
		if (newIsMulticast) eFlags |= IS_MULTICAST_EFLAG; else eFlags &= ~IS_MULTICAST_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.OBJECT_FLOW__IS_MULTICAST, oldIsMulticast, newIsMulticast));

	}


	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isMultireceive()
	{
		return (eFlags & IS_MULTIRECEIVE_EFLAG) != 0;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setIsMultireceive(boolean newIsMultireceive)
	{

		boolean oldIsMultireceive = (eFlags & IS_MULTIRECEIVE_EFLAG) != 0;
		if (newIsMultireceive) eFlags |= IS_MULTIRECEIVE_EFLAG; else eFlags &= ~IS_MULTIRECEIVE_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.OBJECT_FLOW__IS_MULTIRECEIVE, oldIsMultireceive, newIsMultireceive));

	}


	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Behavior getTransformation()
	{
		if (transformation != null && transformation.eIsProxy())
		{
			Behavior oldTransformation = transformation;
			transformation = (Behavior)eResolveProxy((InternalEObject)transformation);
			if (transformation != oldTransformation)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.OBJECT_FLOW__TRANSFORMATION, oldTransformation, transformation));
			}
		}
		return transformation;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Behavior undeleted_getTransformation()
	{
		Behavior temp = getTransformation();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Behavior basicGetTransformation()
	{
		return transformation;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setTransformation(Behavior newTransformation)
	{

		Behavior oldTransformation = transformation;
		transformation = newTransformation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.OBJECT_FLOW__TRANSFORMATION, oldTransformation, transformation));

	}


	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Behavior getSelection()
	{
		if (selection != null && selection.eIsProxy())
		{
			Behavior oldSelection = selection;
			selection = (Behavior)eResolveProxy((InternalEObject)selection);
			if (selection != oldSelection)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.OBJECT_FLOW__SELECTION, oldSelection, selection));
			}
		}
		return selection;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Behavior undeleted_getSelection()
	{
		Behavior temp = getSelection();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Behavior basicGetSelection()
	{
		return selection;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setSelection(Behavior newSelection)
	{

		Behavior oldSelection = selection;
		selection = newSelection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.OBJECT_FLOW__SELECTION, oldSelection, selection));

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
				case UML2Package.OBJECT_FLOW__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.OBJECT_FLOW__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__ACTIVITY:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.OBJECT_FLOW__ACTIVITY, msgs);
				case UML2Package.OBJECT_FLOW__SOURCE:
					if (source != null)
						msgs = ((InternalEObject)source).eInverseRemove(this, UML2Package.ACTIVITY_NODE__OUTGOING, ActivityNode.class, msgs);
					return basicSetSource((ActivityNode)otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__TARGET:
					if (target != null)
						msgs = ((InternalEObject)target).eInverseRemove(this, UML2Package.ACTIVITY_NODE__INCOMING, ActivityNode.class, msgs);
					return basicSetTarget((ActivityNode)otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__IN_STRUCTURED_NODE:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.OBJECT_FLOW__IN_STRUCTURED_NODE, msgs);
				case UML2Package.OBJECT_FLOW__IN_PARTITION:
					return ((InternalEList)getInPartitions()).basicAdd(otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__INTERRUPTS:
					if (interrupts != null)
						msgs = ((InternalEObject)interrupts).eInverseRemove(this, UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__INTERRUPTING_EDGE, InterruptibleActivityRegion.class, msgs);
					return basicSetInterrupts((InterruptibleActivityRegion)otherEnd, msgs);
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
				case UML2Package.OBJECT_FLOW__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.OBJECT_FLOW__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.OBJECT_FLOW__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__ACTIVITY:
					return eBasicSetContainer(null, UML2Package.OBJECT_FLOW__ACTIVITY, msgs);
				case UML2Package.OBJECT_FLOW__SOURCE:
					return basicSetSource(null, msgs);
				case UML2Package.OBJECT_FLOW__TARGET:
					return basicSetTarget(null, msgs);
				case UML2Package.OBJECT_FLOW__GUARD:
					return basicSetGuard(null, msgs);
				case UML2Package.OBJECT_FLOW__IN_STRUCTURED_NODE:
					return eBasicSetContainer(null, UML2Package.OBJECT_FLOW__IN_STRUCTURED_NODE, msgs);
				case UML2Package.OBJECT_FLOW__IN_PARTITION:
					return ((InternalEList)getInPartitions()).basicRemove(otherEnd, msgs);
				case UML2Package.OBJECT_FLOW__WEIGHT:
					return basicSetWeight(null, msgs);
				case UML2Package.OBJECT_FLOW__INTERRUPTS:
					return basicSetInterrupts(null, msgs);
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
				case UML2Package.OBJECT_FLOW__ACTIVITY:
					return eContainer.eInverseRemove(this, UML2Package.ACTIVITY__EDGE, Activity.class, msgs);
				case UML2Package.OBJECT_FLOW__IN_STRUCTURED_NODE:
					return eContainer.eInverseRemove(this, UML2Package.STRUCTURED_ACTIVITY_NODE__CONTAINED_EDGE, StructuredActivityNode.class, msgs);
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
			case UML2Package.OBJECT_FLOW__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.OBJECT_FLOW__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.OBJECT_FLOW__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.OBJECT_FLOW__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.OBJECT_FLOW__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.OBJECT_FLOW__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.OBJECT_FLOW__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.OBJECT_FLOW__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.OBJECT_FLOW__UUID:
				return getUuid();
			case UML2Package.OBJECT_FLOW__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.OBJECT_FLOW__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.OBJECT_FLOW__NAME:
				return getName();
			case UML2Package.OBJECT_FLOW__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.OBJECT_FLOW__VISIBILITY:
				return getVisibility();
			case UML2Package.OBJECT_FLOW__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.OBJECT_FLOW__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.OBJECT_FLOW__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.OBJECT_FLOW__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.OBJECT_FLOW__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.OBJECT_FLOW__REDEFINITION_CONTEXT:
				return getRedefinitionContexts();
			case UML2Package.OBJECT_FLOW__IS_LEAF:
				return isLeaf() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.OBJECT_FLOW__ACTIVITY:
				return getActivity();
			case UML2Package.OBJECT_FLOW__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case UML2Package.OBJECT_FLOW__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case UML2Package.OBJECT_FLOW__IN_GROUP:
				return getInGroups();
			case UML2Package.OBJECT_FLOW__GUARD:
				return getGuard();
			case UML2Package.OBJECT_FLOW__REDEFINED_ELEMENT:
				return getRedefinedElements();
			case UML2Package.OBJECT_FLOW__IN_STRUCTURED_NODE:
				return getInStructuredNode();
			case UML2Package.OBJECT_FLOW__IN_PARTITION:
				return getInPartitions();
			case UML2Package.OBJECT_FLOW__WEIGHT:
				return getWeight();
			case UML2Package.OBJECT_FLOW__INTERRUPTS:
				if (resolve) return getInterrupts();
				return basicGetInterrupts();
			case UML2Package.OBJECT_FLOW__IS_MULTICAST:
				return isMulticast() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.OBJECT_FLOW__IS_MULTIRECEIVE:
				return isMultireceive() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.OBJECT_FLOW__TRANSFORMATION:
				if (resolve) return getTransformation();
				return basicGetTransformation();
			case UML2Package.OBJECT_FLOW__SELECTION:
				if (resolve) return getSelection();
				return basicGetSelection();
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
			case UML2Package.OBJECT_FLOW__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.OBJECT_FLOW__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.OBJECT_FLOW__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.OBJECT_FLOW__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.OBJECT_FLOW__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.OBJECT_FLOW__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.OBJECT_FLOW__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.OBJECT_FLOW__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.OBJECT_FLOW__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.OBJECT_FLOW__NAME:
				setName((String)newValue);
				return;
			case UML2Package.OBJECT_FLOW__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.OBJECT_FLOW__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.OBJECT_FLOW__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.OBJECT_FLOW__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.OBJECT_FLOW__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.OBJECT_FLOW__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.OBJECT_FLOW__IS_LEAF:
				setIsLeaf(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.OBJECT_FLOW__ACTIVITY:
				setActivity((Activity)newValue);
				return;
			case UML2Package.OBJECT_FLOW__SOURCE:
				setSource((ActivityNode)newValue);
				return;
			case UML2Package.OBJECT_FLOW__TARGET:
				setTarget((ActivityNode)newValue);
				return;
			case UML2Package.OBJECT_FLOW__GUARD:
				setGuard((ValueSpecification)newValue);
				return;
			case UML2Package.OBJECT_FLOW__REDEFINED_ELEMENT:
				getRedefinedElements().clear();
				getRedefinedElements().addAll((Collection)newValue);
				return;
			case UML2Package.OBJECT_FLOW__IN_STRUCTURED_NODE:
				setInStructuredNode((StructuredActivityNode)newValue);
				return;
			case UML2Package.OBJECT_FLOW__IN_PARTITION:
				getInPartitions().clear();
				getInPartitions().addAll((Collection)newValue);
				return;
			case UML2Package.OBJECT_FLOW__WEIGHT:
				setWeight((ValueSpecification)newValue);
				return;
			case UML2Package.OBJECT_FLOW__INTERRUPTS:
				setInterrupts((InterruptibleActivityRegion)newValue);
				return;
			case UML2Package.OBJECT_FLOW__IS_MULTICAST:
				setIsMulticast(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.OBJECT_FLOW__IS_MULTIRECEIVE:
				setIsMultireceive(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.OBJECT_FLOW__TRANSFORMATION:
				setTransformation((Behavior)newValue);
				return;
			case UML2Package.OBJECT_FLOW__SELECTION:
				setSelection((Behavior)newValue);
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
			case UML2Package.OBJECT_FLOW__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.OBJECT_FLOW__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.OBJECT_FLOW__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.OBJECT_FLOW__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.OBJECT_FLOW__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.OBJECT_FLOW__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.OBJECT_FLOW__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.OBJECT_FLOW__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.OBJECT_FLOW__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.OBJECT_FLOW__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.OBJECT_FLOW__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.OBJECT_FLOW__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.OBJECT_FLOW__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.OBJECT_FLOW__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.OBJECT_FLOW__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.OBJECT_FLOW__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.OBJECT_FLOW__IS_LEAF:
				setIsLeaf(IS_LEAF_EDEFAULT);
				return;
			case UML2Package.OBJECT_FLOW__ACTIVITY:
				setActivity((Activity)null);
				return;
			case UML2Package.OBJECT_FLOW__SOURCE:
				setSource((ActivityNode)null);
				return;
			case UML2Package.OBJECT_FLOW__TARGET:
				setTarget((ActivityNode)null);
				return;
			case UML2Package.OBJECT_FLOW__GUARD:
				setGuard((ValueSpecification)null);
				return;
			case UML2Package.OBJECT_FLOW__REDEFINED_ELEMENT:
				getRedefinedElements().clear();
				return;
			case UML2Package.OBJECT_FLOW__IN_STRUCTURED_NODE:
				setInStructuredNode((StructuredActivityNode)null);
				return;
			case UML2Package.OBJECT_FLOW__IN_PARTITION:
				getInPartitions().clear();
				return;
			case UML2Package.OBJECT_FLOW__WEIGHT:
				setWeight((ValueSpecification)null);
				return;
			case UML2Package.OBJECT_FLOW__INTERRUPTS:
				setInterrupts((InterruptibleActivityRegion)null);
				return;
			case UML2Package.OBJECT_FLOW__IS_MULTICAST:
				setIsMulticast(IS_MULTICAST_EDEFAULT);
				return;
			case UML2Package.OBJECT_FLOW__IS_MULTIRECEIVE:
				setIsMultireceive(IS_MULTIRECEIVE_EDEFAULT);
				return;
			case UML2Package.OBJECT_FLOW__TRANSFORMATION:
				setTransformation((Behavior)null);
				return;
			case UML2Package.OBJECT_FLOW__SELECTION:
				setSelection((Behavior)null);
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
			case UML2Package.OBJECT_FLOW__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.OBJECT_FLOW__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.OBJECT_FLOW__OWNER:
				return basicGetOwner() != null;
			case UML2Package.OBJECT_FLOW__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.OBJECT_FLOW__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.OBJECT_FLOW__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.OBJECT_FLOW__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.OBJECT_FLOW__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.OBJECT_FLOW__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.OBJECT_FLOW__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.OBJECT_FLOW__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.OBJECT_FLOW__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.OBJECT_FLOW__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.OBJECT_FLOW__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.OBJECT_FLOW__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.OBJECT_FLOW__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.OBJECT_FLOW__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.OBJECT_FLOW__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.OBJECT_FLOW__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.OBJECT_FLOW__REDEFINITION_CONTEXT:
				return !getRedefinitionContexts().isEmpty();
			case UML2Package.OBJECT_FLOW__IS_LEAF:
				return ((eFlags & IS_LEAF_EFLAG) != 0) != IS_LEAF_EDEFAULT;
			case UML2Package.OBJECT_FLOW__ACTIVITY:
				return getActivity() != null;
			case UML2Package.OBJECT_FLOW__SOURCE:
				return source != null;
			case UML2Package.OBJECT_FLOW__TARGET:
				return target != null;
			case UML2Package.OBJECT_FLOW__IN_GROUP:
				return !getInGroups().isEmpty();
			case UML2Package.OBJECT_FLOW__GUARD:
				return guard != null;
			case UML2Package.OBJECT_FLOW__REDEFINED_ELEMENT:
				return redefinedElement != null && !redefinedElement.isEmpty();
			case UML2Package.OBJECT_FLOW__IN_STRUCTURED_NODE:
				return getInStructuredNode() != null;
			case UML2Package.OBJECT_FLOW__IN_PARTITION:
				return inPartition != null && !inPartition.isEmpty();
			case UML2Package.OBJECT_FLOW__WEIGHT:
				return weight != null;
			case UML2Package.OBJECT_FLOW__INTERRUPTS:
				return interrupts != null;
			case UML2Package.OBJECT_FLOW__IS_MULTICAST:
				return ((eFlags & IS_MULTICAST_EFLAG) != 0) != IS_MULTICAST_EDEFAULT;
			case UML2Package.OBJECT_FLOW__IS_MULTIRECEIVE:
				return ((eFlags & IS_MULTIRECEIVE_EFLAG) != 0) != IS_MULTIRECEIVE_EDEFAULT;
			case UML2Package.OBJECT_FLOW__TRANSFORMATION:
				return transformation != null;
			case UML2Package.OBJECT_FLOW__SELECTION:
				return selection != null;
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
		result.append(" (isMulticast: "); //$NON-NLS-1$
		result.append((eFlags & IS_MULTICAST_EFLAG) != 0);
		result.append(", isMultireceive: "); //$NON-NLS-1$
		result.append((eFlags & IS_MULTIRECEIVE_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}


} //ObjectFlowImpl
