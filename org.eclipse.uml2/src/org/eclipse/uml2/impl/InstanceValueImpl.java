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
 * $Id: InstanceValueImpl.java,v 1.1 2009-03-04 23:06:44 andrew Exp $
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
import org.eclipse.uml2.EnumerationLiteral;
import org.eclipse.uml2.InstanceSpecification;
import org.eclipse.uml2.InstanceValue;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.Type;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.ValueSpecification;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Instance Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.InstanceValueImpl#getInstance <em>Instance</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InstanceValueImpl#getOwnedAnonymousInstanceValue <em>Owned Anonymous Instance Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstanceValueImpl extends ValueSpecificationImpl implements InstanceValue {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getInstance() <em>Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstance()
	 * @generated
	 * @ordered
	 */
	protected InstanceSpecification instance = null;

	/**
	 * The cached value of the '{@link #getOwnedAnonymousInstanceValue() <em>Owned Anonymous Instance Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedAnonymousInstanceValue()
	 * @generated
	 * @ordered
	 */
	protected InstanceSpecification ownedAnonymousInstanceValue = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstanceValueImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (InstanceValueImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getInstanceValue();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstanceSpecification getInstance()
	{
		if (instance != null && instance.eIsProxy())
		{
			InstanceSpecification oldInstance = instance;
			instance = (InstanceSpecification)eResolveProxy((InternalEObject)instance);
			if (instance != oldInstance)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.INSTANCE_VALUE__INSTANCE, oldInstance, instance));
			}
		}
		return instance;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public InstanceSpecification undeleted_getInstance()
	{
		InstanceSpecification temp = getInstance();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstanceSpecification basicGetInstance()
	{
		return instance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstance(InstanceSpecification newInstance)
	{

		InstanceSpecification oldInstance = instance;
		instance = newInstance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.INSTANCE_VALUE__INSTANCE, oldInstance, instance));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstanceSpecification getOwnedAnonymousInstanceValue()
	{
		return ownedAnonymousInstanceValue;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstanceSpecification undeleted_getOwnedAnonymousInstanceValue()
	{
		InstanceSpecification temp = getOwnedAnonymousInstanceValue();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwnedAnonymousInstanceValue(InstanceSpecification newOwnedAnonymousInstanceValue, NotificationChain msgs)
	{

		InstanceSpecification oldOwnedAnonymousInstanceValue = ownedAnonymousInstanceValue;
		ownedAnonymousInstanceValue = newOwnedAnonymousInstanceValue;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_INSTANCE_VALUE, oldOwnedAnonymousInstanceValue, newOwnedAnonymousInstanceValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnedAnonymousInstanceValue(InstanceSpecification newOwnedAnonymousInstanceValue)
	{

		if (newOwnedAnonymousInstanceValue != ownedAnonymousInstanceValue)
		{
			NotificationChain msgs = null;
			if (ownedAnonymousInstanceValue != null)
				msgs = ((InternalEObject)ownedAnonymousInstanceValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_INSTANCE_VALUE, null, msgs);
			if (newOwnedAnonymousInstanceValue != null)
				msgs = ((InternalEObject)newOwnedAnonymousInstanceValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_INSTANCE_VALUE, null, msgs);
			msgs = basicSetOwnedAnonymousInstanceValue(newOwnedAnonymousInstanceValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_INSTANCE_VALUE, newOwnedAnonymousInstanceValue, newOwnedAnonymousInstanceValue));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstanceSpecification createOwnedAnonymousInstanceValue(EClass eClass) {
		InstanceSpecification newOwnedAnonymousInstanceValue = (InstanceSpecification) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_INSTANCE_VALUE, null, newOwnedAnonymousInstanceValue));
		}
		setOwnedAnonymousInstanceValue(newOwnedAnonymousInstanceValue);
		return newOwnedAnonymousInstanceValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InstanceSpecification createOwnedAnonymousInstanceValue() {
		InstanceSpecification newOwnedAnonymousInstanceValue = UML2Factory.eINSTANCE.createInstanceSpecification();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_INSTANCE_VALUE, null, newOwnedAnonymousInstanceValue));
		}
		setOwnedAnonymousInstanceValue(newOwnedAnonymousInstanceValue);
		return newOwnedAnonymousInstanceValue;
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
				case UML2Package.INSTANCE_VALUE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.INSTANCE_VALUE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.INSTANCE_VALUE__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.INSTANCE_VALUE__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.INSTANCE_VALUE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.INSTANCE_VALUE__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.INSTANCE_VALUE__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.INSTANCE_VALUE__OWNING_PARAMETER, msgs);
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
				case UML2Package.INSTANCE_VALUE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_VALUE__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_VALUE__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_VALUE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_VALUE__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.INSTANCE_VALUE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_VALUE__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.INSTANCE_VALUE__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.INSTANCE_VALUE__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.INSTANCE_VALUE__OWNING_PARAMETER, msgs);
				case UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_INSTANCE_VALUE:
					return basicSetOwnedAnonymousInstanceValue(null, msgs);
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
				case UML2Package.INSTANCE_VALUE__OWNING_PARAMETER:
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
			case UML2Package.INSTANCE_VALUE__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.INSTANCE_VALUE__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.INSTANCE_VALUE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.INSTANCE_VALUE__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.INSTANCE_VALUE__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.INSTANCE_VALUE__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.INSTANCE_VALUE__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.INSTANCE_VALUE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.INSTANCE_VALUE__UUID:
				return getUuid();
			case UML2Package.INSTANCE_VALUE__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.INSTANCE_VALUE__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.INSTANCE_VALUE__NAME:
				return getName();
			case UML2Package.INSTANCE_VALUE__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.INSTANCE_VALUE__VISIBILITY:
				return getVisibility();
			case UML2Package.INSTANCE_VALUE__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.INSTANCE_VALUE__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.INSTANCE_VALUE__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.INSTANCE_VALUE__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.INSTANCE_VALUE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case UML2Package.INSTANCE_VALUE__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.INSTANCE_VALUE__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.INSTANCE_VALUE__INSTANCE:
				if (resolve) return getInstance();
				return basicGetInstance();
			case UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_INSTANCE_VALUE:
				return getOwnedAnonymousInstanceValue();
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
			case UML2Package.INSTANCE_VALUE__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.INSTANCE_VALUE__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__NAME:
				setName((String)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__TYPE:
				setType((Type)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__INSTANCE:
				setInstance((InstanceSpecification)newValue);
				return;
			case UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_INSTANCE_VALUE:
				setOwnedAnonymousInstanceValue((InstanceSpecification)newValue);
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
			case UML2Package.INSTANCE_VALUE__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.INSTANCE_VALUE__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.INSTANCE_VALUE__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.INSTANCE_VALUE__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.INSTANCE_VALUE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.INSTANCE_VALUE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.INSTANCE_VALUE__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.INSTANCE_VALUE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.INSTANCE_VALUE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.INSTANCE_VALUE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.INSTANCE_VALUE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.INSTANCE_VALUE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.INSTANCE_VALUE__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.INSTANCE_VALUE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.INSTANCE_VALUE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.INSTANCE_VALUE__TYPE:
				setType((Type)null);
				return;
			case UML2Package.INSTANCE_VALUE__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.INSTANCE_VALUE__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.INSTANCE_VALUE__INSTANCE:
				setInstance((InstanceSpecification)null);
				return;
			case UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_INSTANCE_VALUE:
				setOwnedAnonymousInstanceValue((InstanceSpecification)null);
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
			case UML2Package.INSTANCE_VALUE__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.INSTANCE_VALUE__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.INSTANCE_VALUE__OWNER:
				return basicGetOwner() != null;
			case UML2Package.INSTANCE_VALUE__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.INSTANCE_VALUE__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.INSTANCE_VALUE__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.INSTANCE_VALUE__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.INSTANCE_VALUE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.INSTANCE_VALUE__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.INSTANCE_VALUE__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.INSTANCE_VALUE__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.INSTANCE_VALUE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.INSTANCE_VALUE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.INSTANCE_VALUE__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.INSTANCE_VALUE__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.INSTANCE_VALUE__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.INSTANCE_VALUE__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.INSTANCE_VALUE__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.INSTANCE_VALUE__TYPE:
				return type != null;
			case UML2Package.INSTANCE_VALUE__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.INSTANCE_VALUE__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.INSTANCE_VALUE__INSTANCE:
				return instance != null;
			case UML2Package.INSTANCE_VALUE__OWNED_ANONYMOUS_INSTANCE_VALUE:
				return ownedAnonymousInstanceValue != null;
		}
		return eDynamicIsSet(eFeature);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.ValueSpecification#stringValue()
	 */
	public String stringValue() {
		InstanceSpecification instance = getInstance();

		if (EnumerationLiteral.class.isInstance(instance)) {
			return ((EnumerationLiteral) instance).getName();
		} else if (null != instance) {
			ValueSpecification specification = instance.getSpecification();

			if (null != specification) {
				return specification.stringValue();
			}
		}

		return super.stringValue();
	}

} // InstanceValueImpl
