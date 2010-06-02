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
 * $Id: ExecutableNodeImpl.java,v 1.1 2009-03-04 23:06:43 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Activity;
import org.eclipse.uml2.ExceptionHandler;
import org.eclipse.uml2.ExecutableNode;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.StructuredActivityNode;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Executable Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.ExecutableNodeImpl#getHandlers <em>Handler</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ExecutableNodeImpl extends ActivityNodeImpl implements ExecutableNode {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getHandlers() <em>Handler</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHandlers()
	 * @generated
	 * @ordered
	 */
	protected EList handler = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExecutableNodeImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (ExecutableNodeImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getExecutableNode();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getHandlers() {
		if (handler == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		handler = new com.hopstepjump.emflist.PersistentEList(ExceptionHandler.class, this, UML2Package.EXECUTABLE_NODE__HANDLER, UML2Package.EXCEPTION_HANDLER__PROTECTED_NODE);
			 		return handler;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(ExceptionHandler.class, this, UML2Package.EXECUTABLE_NODE__HANDLER, UML2Package.EXCEPTION_HANDLER__PROTECTED_NODE);
		}      
		return handler;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getHandlers() {
		if (handler == null) {
			handler = new com.hopstepjump.emflist.PersistentEList(ExceptionHandler.class, this, UML2Package.EXECUTABLE_NODE__HANDLER, UML2Package.EXCEPTION_HANDLER__PROTECTED_NODE);
		}
		return handler;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getHandlers() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (handler != null) {
			for (Object object : handler) {
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
	 * @deprecated Use #createHandler() instead.
	 */
	public ExceptionHandler createHandler(EClass eClass) {
		ExceptionHandler newHandler = (ExceptionHandler) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.EXECUTABLE_NODE__HANDLER, null, newHandler));
		}
		getHandlers().add(newHandler);
		return newHandler;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExceptionHandler createHandler() {
		ExceptionHandler newHandler = UML2Factory.eINSTANCE.createExceptionHandler();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.EXECUTABLE_NODE__HANDLER, null, newHandler));
		}
		settable_getHandlers().add(newHandler);
		return newHandler;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.EXECUTABLE_NODE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.EXECUTABLE_NODE__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__OUTGOING:
					return ((InternalEList)getOutgoings()).basicAdd(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__INCOMING:
					return ((InternalEList)getIncomings()).basicAdd(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__ACTIVITY:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.EXECUTABLE_NODE__ACTIVITY, msgs);
				case UML2Package.EXECUTABLE_NODE__IN_STRUCTURED_NODE:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.EXECUTABLE_NODE__IN_STRUCTURED_NODE, msgs);
				case UML2Package.EXECUTABLE_NODE__IN_PARTITION:
					return ((InternalEList)getInPartitions()).basicAdd(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__IN_INTERRUPTIBLE_REGION:
					return ((InternalEList)getInInterruptibleRegions()).basicAdd(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__HANDLER:
					return ((InternalEList)getHandlers()).basicAdd(otherEnd, msgs);
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
				case UML2Package.EXECUTABLE_NODE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.EXECUTABLE_NODE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.EXECUTABLE_NODE__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__OUTGOING:
					return ((InternalEList)getOutgoings()).basicRemove(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__INCOMING:
					return ((InternalEList)getIncomings()).basicRemove(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__ACTIVITY:
					return eBasicSetContainer(null, UML2Package.EXECUTABLE_NODE__ACTIVITY, msgs);
				case UML2Package.EXECUTABLE_NODE__IN_STRUCTURED_NODE:
					return eBasicSetContainer(null, UML2Package.EXECUTABLE_NODE__IN_STRUCTURED_NODE, msgs);
				case UML2Package.EXECUTABLE_NODE__IN_PARTITION:
					return ((InternalEList)getInPartitions()).basicRemove(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__IN_INTERRUPTIBLE_REGION:
					return ((InternalEList)getInInterruptibleRegions()).basicRemove(otherEnd, msgs);
				case UML2Package.EXECUTABLE_NODE__HANDLER:
					return ((InternalEList)getHandlers()).basicRemove(otherEnd, msgs);
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
				case UML2Package.EXECUTABLE_NODE__ACTIVITY:
					return eContainer.eInverseRemove(this, UML2Package.ACTIVITY__NODE, Activity.class, msgs);
				case UML2Package.EXECUTABLE_NODE__IN_STRUCTURED_NODE:
					return eContainer.eInverseRemove(this, UML2Package.STRUCTURED_ACTIVITY_NODE__CONTAINED_NODE, StructuredActivityNode.class, msgs);
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
			case UML2Package.EXECUTABLE_NODE__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.EXECUTABLE_NODE__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.EXECUTABLE_NODE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.EXECUTABLE_NODE__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.EXECUTABLE_NODE__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.EXECUTABLE_NODE__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.EXECUTABLE_NODE__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.EXECUTABLE_NODE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.EXECUTABLE_NODE__UUID:
				return getUuid();
			case UML2Package.EXECUTABLE_NODE__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.EXECUTABLE_NODE__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.EXECUTABLE_NODE__NAME:
				return getName();
			case UML2Package.EXECUTABLE_NODE__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.EXECUTABLE_NODE__VISIBILITY:
				return getVisibility();
			case UML2Package.EXECUTABLE_NODE__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.EXECUTABLE_NODE__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.EXECUTABLE_NODE__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.EXECUTABLE_NODE__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.EXECUTABLE_NODE__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.EXECUTABLE_NODE__REDEFINITION_CONTEXT:
				return getRedefinitionContexts();
			case UML2Package.EXECUTABLE_NODE__IS_LEAF:
				return isLeaf() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.EXECUTABLE_NODE__OUTGOING:
				return getOutgoings();
			case UML2Package.EXECUTABLE_NODE__INCOMING:
				return getIncomings();
			case UML2Package.EXECUTABLE_NODE__IN_GROUP:
				return getInGroups();
			case UML2Package.EXECUTABLE_NODE__ACTIVITY:
				return getActivity();
			case UML2Package.EXECUTABLE_NODE__REDEFINED_ELEMENT:
				return getRedefinedElements();
			case UML2Package.EXECUTABLE_NODE__IN_STRUCTURED_NODE:
				return getInStructuredNode();
			case UML2Package.EXECUTABLE_NODE__IN_PARTITION:
				return getInPartitions();
			case UML2Package.EXECUTABLE_NODE__IN_INTERRUPTIBLE_REGION:
				return getInInterruptibleRegions();
			case UML2Package.EXECUTABLE_NODE__HANDLER:
				return getHandlers();
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
			case UML2Package.EXECUTABLE_NODE__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.EXECUTABLE_NODE__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__NAME:
				setName((String)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__IS_LEAF:
				setIsLeaf(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.EXECUTABLE_NODE__OUTGOING:
				getOutgoings().clear();
				getOutgoings().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__INCOMING:
				getIncomings().clear();
				getIncomings().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__ACTIVITY:
				setActivity((Activity)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__REDEFINED_ELEMENT:
				getRedefinedElements().clear();
				getRedefinedElements().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__IN_STRUCTURED_NODE:
				setInStructuredNode((StructuredActivityNode)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__IN_PARTITION:
				getInPartitions().clear();
				getInPartitions().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__IN_INTERRUPTIBLE_REGION:
				getInInterruptibleRegions().clear();
				getInInterruptibleRegions().addAll((Collection)newValue);
				return;
			case UML2Package.EXECUTABLE_NODE__HANDLER:
				getHandlers().clear();
				getHandlers().addAll((Collection)newValue);
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
			case UML2Package.EXECUTABLE_NODE__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.EXECUTABLE_NODE__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.EXECUTABLE_NODE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.EXECUTABLE_NODE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.EXECUTABLE_NODE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.EXECUTABLE_NODE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.EXECUTABLE_NODE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.EXECUTABLE_NODE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__IS_LEAF:
				setIsLeaf(IS_LEAF_EDEFAULT);
				return;
			case UML2Package.EXECUTABLE_NODE__OUTGOING:
				getOutgoings().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__INCOMING:
				getIncomings().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__ACTIVITY:
				setActivity((Activity)null);
				return;
			case UML2Package.EXECUTABLE_NODE__REDEFINED_ELEMENT:
				getRedefinedElements().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__IN_STRUCTURED_NODE:
				setInStructuredNode((StructuredActivityNode)null);
				return;
			case UML2Package.EXECUTABLE_NODE__IN_PARTITION:
				getInPartitions().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__IN_INTERRUPTIBLE_REGION:
				getInInterruptibleRegions().clear();
				return;
			case UML2Package.EXECUTABLE_NODE__HANDLER:
				getHandlers().clear();
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
			case UML2Package.EXECUTABLE_NODE__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.EXECUTABLE_NODE__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.EXECUTABLE_NODE__OWNER:
				return basicGetOwner() != null;
			case UML2Package.EXECUTABLE_NODE__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.EXECUTABLE_NODE__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.EXECUTABLE_NODE__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.EXECUTABLE_NODE__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.EXECUTABLE_NODE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.EXECUTABLE_NODE__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.EXECUTABLE_NODE__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.EXECUTABLE_NODE__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.EXECUTABLE_NODE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.EXECUTABLE_NODE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.EXECUTABLE_NODE__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.EXECUTABLE_NODE__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.EXECUTABLE_NODE__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.EXECUTABLE_NODE__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.EXECUTABLE_NODE__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.EXECUTABLE_NODE__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.EXECUTABLE_NODE__REDEFINITION_CONTEXT:
				return !getRedefinitionContexts().isEmpty();
			case UML2Package.EXECUTABLE_NODE__IS_LEAF:
				return ((eFlags & IS_LEAF_EFLAG) != 0) != IS_LEAF_EDEFAULT;
			case UML2Package.EXECUTABLE_NODE__OUTGOING:
				return outgoing != null && !outgoing.isEmpty();
			case UML2Package.EXECUTABLE_NODE__INCOMING:
				return incoming != null && !incoming.isEmpty();
			case UML2Package.EXECUTABLE_NODE__IN_GROUP:
				return !getInGroups().isEmpty();
			case UML2Package.EXECUTABLE_NODE__ACTIVITY:
				return getActivity() != null;
			case UML2Package.EXECUTABLE_NODE__REDEFINED_ELEMENT:
				return redefinedElement != null && !redefinedElement.isEmpty();
			case UML2Package.EXECUTABLE_NODE__IN_STRUCTURED_NODE:
				return getInStructuredNode() != null;
			case UML2Package.EXECUTABLE_NODE__IN_PARTITION:
				return inPartition != null && !inPartition.isEmpty();
			case UML2Package.EXECUTABLE_NODE__IN_INTERRUPTIBLE_REGION:
				return inInterruptibleRegion != null && !inInterruptibleRegion.isEmpty();
			case UML2Package.EXECUTABLE_NODE__HANDLER:
				return handler != null && !handler.isEmpty();
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
		if (eIsSet(UML2Package.eINSTANCE.getExecutableNode_Handler())) {
			ownedElement.addAll(getHandlers());
		}
		return ownedElement;
	}


} //ExecutableNodeImpl
