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
 * $Id: InterruptibleActivityRegionImpl.java,v 1.1 2009-03-04 23:06:39 andrew Exp $
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
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.uml2.Activity;
import org.eclipse.uml2.ActivityEdge;
import org.eclipse.uml2.ActivityNode;
import org.eclipse.uml2.InterruptibleActivityRegion;
import org.eclipse.uml2.UML2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interruptible Activity Region</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.InterruptibleActivityRegionImpl#getInterruptingEdges <em>Interrupting Edge</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InterruptibleActivityRegionImpl#getContainedNodes <em>Contained Node</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InterruptibleActivityRegionImpl extends ActivityGroupImpl implements InterruptibleActivityRegion {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getInterruptingEdges() <em>Interrupting Edge</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterruptingEdges()
	 * @generated
	 * @ordered
	 */
	protected EList interruptingEdge = null;

	/**
	 * The cached value of the '{@link #getContainedNodes() <em>Contained Node</em>}' reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getContainedNodes()
	 * @generated
	 * @ordered
	 */
    protected EList containedNode = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InterruptibleActivityRegionImpl() {
		super();
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (InterruptibleActivityRegionImpl.class.equals(getClass()) && org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public Activity undeleted_getActivity()
  {
    Activity temp = getActivityGroup_activity();
    if (temp != null && temp.getJ_deleted() > 0)
        return null;
    return temp;
  }

  
  
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getInterruptibleActivityRegion();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getInterruptingEdges() {
		if (interruptingEdge == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		interruptingEdge = new com.intrinsarc.emflist.PersistentEList(ActivityEdge.class, this, UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__INTERRUPTING_EDGE, UML2Package.ACTIVITY_EDGE__INTERRUPTS);
			 		return interruptingEdge;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(ActivityEdge.class, this, UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__INTERRUPTING_EDGE, UML2Package.ACTIVITY_EDGE__INTERRUPTS);
		}      
		return interruptingEdge;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getInterruptingEdges() {
		if (interruptingEdge == null) {
			interruptingEdge = new com.intrinsarc.emflist.PersistentEList(ActivityEdge.class, this, UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__INTERRUPTING_EDGE, UML2Package.ACTIVITY_EDGE__INTERRUPTS);
		}
		return interruptingEdge;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getInterruptingEdges() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (interruptingEdge != null) {
			for (Object object : interruptingEdge) {
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
    public ActivityEdge getInterruptingEdge(String name) {
		for (Iterator i = getInterruptingEdges().iterator(); i.hasNext(); ) {
			ActivityEdge interruptingEdge = (ActivityEdge) i.next();
			if (name.equals(interruptingEdge.getName())) {
				return interruptingEdge;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getContainedNodes() {
		if (containedNode == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		containedNode = new com.intrinsarc.emflist.PersistentEList(ActivityNode.class, this, UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__CONTAINED_NODE, UML2Package.ACTIVITY_NODE__IN_INTERRUPTIBLE_REGION);
			 		return containedNode;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(ActivityNode.class, this, UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__CONTAINED_NODE, UML2Package.ACTIVITY_NODE__IN_INTERRUPTIBLE_REGION);
		}      
		return containedNode;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getContainedNodes() {
		if (containedNode == null) {
			containedNode = new com.intrinsarc.emflist.PersistentEList(ActivityNode.class, this, UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__CONTAINED_NODE, UML2Package.ACTIVITY_NODE__IN_INTERRUPTIBLE_REGION);
		}
		return containedNode;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getContainedNodes() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (containedNode != null) {
			for (Object object : containedNode) {
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
    public ActivityNode getContainedNode(String name) {
		for (Iterator i = getContainedNodes().iterator(); i.hasNext(); ) {
			ActivityNode containedNode = (ActivityNode) i.next();
			if (name.equals(containedNode.getName())) {
				return containedNode;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__INTERRUPTING_EDGE:
					return ((InternalEList)getInterruptingEdges()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__CONTAINED_NODE:
					return ((InternalEList)getContainedNodes()).basicAdd(otherEnd, msgs);
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
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__ACTIVITY_GROUP_ACTIVITY:
				if (eContainer != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__ACTIVITY_GROUP_ACTIVITY, msgs);
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
				case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__INTERRUPTING_EDGE:
					return ((InternalEList)getInterruptingEdges()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__CONTAINED_NODE:
					return ((InternalEList)getContainedNodes()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	public NotificationChain eDynamicInverseRemove(InternalEObject otherEnd, int featureID, Class inverseClass, NotificationChain msgs) {
		switch (eDerivedStructuralFeatureID(featureID, inverseClass)) {
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__ACTIVITY_GROUP_ACTIVITY:
				return eBasicSetContainer(null, UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__ACTIVITY_GROUP_ACTIVITY, msgs);
			default :
				return super.eDynamicInverseRemove(otherEnd, featureID, inverseClass, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs) {
		if (eContainerFeatureID >= 0) {
			switch (eContainerFeatureID) {
				case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__ACTIVITY_GROUP_ACTIVITY:
					return eContainer.eInverseRemove(this, UML2Package.ACTIVITY__GROUP, Activity.class, msgs);
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
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__UUID:
				return getUuid();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__SUPER_GROUP:
				if (resolve) return getSuperGroup();
				return basicGetSuperGroup();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__ACTIVITY_GROUP_ACTIVITY:
				return getActivityGroup_activity();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__INTERRUPTING_EDGE:
				return getInterruptingEdges();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__CONTAINED_NODE:
				return getContainedNodes();
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
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__ACTIVITY_GROUP_ACTIVITY:
				setActivityGroup_activity((Activity)newValue);
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__INTERRUPTING_EDGE:
				getInterruptingEdges().clear();
				getInterruptingEdges().addAll((Collection)newValue);
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__CONTAINED_NODE:
				getContainedNodes().clear();
				getContainedNodes().addAll((Collection)newValue);
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
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__ACTIVITY_GROUP_ACTIVITY:
				setActivityGroup_activity((Activity)null);
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__INTERRUPTING_EDGE:
				getInterruptingEdges().clear();
				return;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__CONTAINED_NODE:
				getContainedNodes().clear();
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
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__OWNER:
				return basicGetOwner() != null;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__SUPER_GROUP:
				return basicGetSuperGroup() != null;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__ACTIVITY_GROUP_ACTIVITY:
				return getActivityGroup_activity() != null;
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__INTERRUPTING_EDGE:
				return interruptingEdge != null && !interruptingEdge.isEmpty();
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION__CONTAINED_NODE:
				return containedNode != null && !containedNode.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}


} //InterruptibleActivityRegionImpl
