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
 * $Id: TypedElementImpl.java,v 1.1 2009-03-04 23:06:36 andrew Exp $
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
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.Type;
import org.eclipse.uml2.TypedElement;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Typed Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.TypedElementImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class TypedElementImpl extends NamedElementImpl implements TypedElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected Type type = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypedElementImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (TypedElementImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getTypedElement();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type getType()
	{
		if (type != null && type.eIsProxy())
		{
			Type oldType = type;
			type = (Type)eResolveProxy((InternalEObject)type);
			if (type != oldType)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.TYPED_ELEMENT__TYPE, oldType, type));
			}
		}
		return type;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Type undeleted_getType()
	{
		Type temp = getType();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type basicGetType()
	{
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(Type newType)
	{

		Type oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.TYPED_ELEMENT__TYPE, oldType, type));

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
				case UML2Package.TYPED_ELEMENT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.TYPED_ELEMENT__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.TYPED_ELEMENT__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.TYPED_ELEMENT__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.TYPED_ELEMENT__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
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
				case UML2Package.TYPED_ELEMENT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.TYPED_ELEMENT__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.TYPED_ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.TYPED_ELEMENT__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.TYPED_ELEMENT__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.TYPED_ELEMENT__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.TYPED_ELEMENT__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.TYPED_ELEMENT__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.TYPED_ELEMENT__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.TYPED_ELEMENT__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.TYPED_ELEMENT__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.TYPED_ELEMENT__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.TYPED_ELEMENT__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.TYPED_ELEMENT__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.TYPED_ELEMENT__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.TYPED_ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.TYPED_ELEMENT__UUID:
				return getUuid();
			case UML2Package.TYPED_ELEMENT__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.TYPED_ELEMENT__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.TYPED_ELEMENT__NAME:
				return getName();
			case UML2Package.TYPED_ELEMENT__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.TYPED_ELEMENT__VISIBILITY:
				return getVisibility();
			case UML2Package.TYPED_ELEMENT__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.TYPED_ELEMENT__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.TYPED_ELEMENT__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.TYPED_ELEMENT__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.TYPED_ELEMENT__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.TYPED_ELEMENT__TYPE:
				if (resolve) return getType();
				return basicGetType();
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
			case UML2Package.TYPED_ELEMENT__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.TYPED_ELEMENT__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.TYPED_ELEMENT__TYPE:
				setType((Type)newValue);
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
			case UML2Package.TYPED_ELEMENT__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.TYPED_ELEMENT__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.TYPED_ELEMENT__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.TYPED_ELEMENT__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.TYPED_ELEMENT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.TYPED_ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.TYPED_ELEMENT__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.TYPED_ELEMENT__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.TYPED_ELEMENT__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.TYPED_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.TYPED_ELEMENT__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.TYPED_ELEMENT__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.TYPED_ELEMENT__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.TYPED_ELEMENT__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.TYPED_ELEMENT__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.TYPED_ELEMENT__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.TYPED_ELEMENT__TYPE:
				setType((Type)null);
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
			case UML2Package.TYPED_ELEMENT__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.TYPED_ELEMENT__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.TYPED_ELEMENT__OWNER:
				return basicGetOwner() != null;
			case UML2Package.TYPED_ELEMENT__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.TYPED_ELEMENT__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.TYPED_ELEMENT__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.TYPED_ELEMENT__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.TYPED_ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.TYPED_ELEMENT__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.TYPED_ELEMENT__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.TYPED_ELEMENT__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.TYPED_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.TYPED_ELEMENT__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.TYPED_ELEMENT__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.TYPED_ELEMENT__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.TYPED_ELEMENT__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.TYPED_ELEMENT__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.TYPED_ELEMENT__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.TYPED_ELEMENT__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.TYPED_ELEMENT__TYPE:
				return type != null;
		}
		return eDynamicIsSet(eFeature);
	}


} //TypedElementImpl
