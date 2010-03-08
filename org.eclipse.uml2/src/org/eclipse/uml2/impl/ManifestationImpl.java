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
 * $Id: ManifestationImpl.java,v 1.1 2009-03-04 23:06:43 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Manifestation;
import org.eclipse.uml2.NamedElement;
import org.eclipse.uml2.OpaqueExpression;
import org.eclipse.uml2.PackageableElement;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.SupersetEObjectWithInverseResolvingEList;

import org.eclipse.uml2.common.util.SubsetEObjectResolvingEList;

import org.eclipse.uml2.common.util.SupersetEObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Manifestation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.ManifestationImpl#getSuppliers <em>Supplier</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ManifestationImpl#getUtilizedElement <em>Utilized Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ManifestationImpl extends AbstractionImpl implements Manifestation {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getUtilizedElement() <em>Utilized Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUtilizedElement()
	 * @generated
	 * @ordered
	 */
	protected PackageableElement utilizedElement = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManifestationImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getManifestation();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PackageableElement getUtilizedElement()
	{
		if (utilizedElement != null && utilizedElement.eIsProxy())
		{
			PackageableElement oldUtilizedElement = utilizedElement;
			utilizedElement = (PackageableElement)eResolveProxy((InternalEObject)utilizedElement);
			if (utilizedElement != oldUtilizedElement)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.MANIFESTATION__UTILIZED_ELEMENT, oldUtilizedElement, utilizedElement));
			}
		}
		return utilizedElement;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public PackageableElement undeleted_getUtilizedElement()
	{
		PackageableElement temp = getUtilizedElement();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PackageableElement basicGetUtilizedElement()
	{
		return utilizedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUtilizedElement(PackageableElement newUtilizedElement)
	{

		if (newUtilizedElement != null && !getSuppliers().contains(newUtilizedElement)) {
			getSuppliers().add(newUtilizedElement);
		}
		PackageableElement oldUtilizedElement = utilizedElement;
		utilizedElement = newUtilizedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.MANIFESTATION__UTILIZED_ELEMENT, oldUtilizedElement, utilizedElement));

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
			 		supplier = new com.hopstepjump.emflist.PersistentEList(NamedElement.class, this, UML2Package.MANIFESTATION__SUPPLIER, new int[] {UML2Package.MANIFESTATION__UTILIZED_ELEMENT});
			 		return supplier;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(NamedElement.class, this, UML2Package.MANIFESTATION__SUPPLIER, new int[] {UML2Package.MANIFESTATION__UTILIZED_ELEMENT});
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
			
		
			supplier = new com.hopstepjump.emflist.PersistentEList(NamedElement.class, this, UML2Package.MANIFESTATION__SUPPLIER, new int[] {UML2Package.MANIFESTATION__UTILIZED_ELEMENT});
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
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs)
	{
		if (featureID >= 0)
		{
			switch (eDerivedStructuralFeatureID(featureID, baseClass))
			{
				case UML2Package.MANIFESTATION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.MANIFESTATION__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.MANIFESTATION__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.MANIFESTATION__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.MANIFESTATION__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.MANIFESTATION__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.MANIFESTATION__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.MANIFESTATION__OWNING_PARAMETER, msgs);
				case UML2Package.MANIFESTATION__CLIENT:
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
				case UML2Package.MANIFESTATION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.MANIFESTATION__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.MANIFESTATION__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.MANIFESTATION__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.MANIFESTATION__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.MANIFESTATION__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.MANIFESTATION__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.MANIFESTATION__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.MANIFESTATION__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.MANIFESTATION__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.MANIFESTATION__OWNING_PARAMETER, msgs);
				case UML2Package.MANIFESTATION__CLIENT:
					return ((InternalEList)getClients()).basicRemove(otherEnd, msgs);
				case UML2Package.MANIFESTATION__MAPPING:
					return basicSetMapping(null, msgs);
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
				case UML2Package.MANIFESTATION__OWNING_PARAMETER:
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
			case UML2Package.MANIFESTATION__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.MANIFESTATION__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.MANIFESTATION__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.MANIFESTATION__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.MANIFESTATION__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.MANIFESTATION__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.MANIFESTATION__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.MANIFESTATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.MANIFESTATION__UUID:
				return getUuid();
			case UML2Package.MANIFESTATION__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.MANIFESTATION__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.MANIFESTATION__NAME:
				return getName();
			case UML2Package.MANIFESTATION__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.MANIFESTATION__VISIBILITY:
				return getVisibility();
			case UML2Package.MANIFESTATION__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.MANIFESTATION__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.MANIFESTATION__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.MANIFESTATION__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.MANIFESTATION__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.MANIFESTATION__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.MANIFESTATION__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.MANIFESTATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.MANIFESTATION__RELATED_ELEMENT:
				return getRelatedElements();
			case UML2Package.MANIFESTATION__SOURCE:
				return getSources();
			case UML2Package.MANIFESTATION__TARGET:
				return getTargets();
			case UML2Package.MANIFESTATION__CLIENT:
				return getClients();
			case UML2Package.MANIFESTATION__SUPPLIER:
				return getSuppliers();
			case UML2Package.MANIFESTATION__DEPENDENCY_TARGET:
				if (resolve) return getDependencyTarget();
				return basicGetDependencyTarget();
			case UML2Package.MANIFESTATION__RESEMBLANCE:
				return isResemblance() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.MANIFESTATION__REPLACEMENT:
				return isReplacement() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.MANIFESTATION__MAPPING:
				return getMapping();
			case UML2Package.MANIFESTATION__UTILIZED_ELEMENT:
				if (resolve) return getUtilizedElement();
				return basicGetUtilizedElement();
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
			case UML2Package.MANIFESTATION__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.MANIFESTATION__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.MANIFESTATION__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.MANIFESTATION__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.MANIFESTATION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.MANIFESTATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.MANIFESTATION__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.MANIFESTATION__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.MANIFESTATION__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.MANIFESTATION__NAME:
				setName((String)newValue);
				return;
			case UML2Package.MANIFESTATION__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.MANIFESTATION__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.MANIFESTATION__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.MANIFESTATION__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.MANIFESTATION__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.MANIFESTATION__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.MANIFESTATION__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.MANIFESTATION__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.MANIFESTATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.MANIFESTATION__CLIENT:
				getClients().clear();
				getClients().addAll((Collection)newValue);
				return;
			case UML2Package.MANIFESTATION__SUPPLIER:
				getSuppliers().clear();
				getSuppliers().addAll((Collection)newValue);
				return;
			case UML2Package.MANIFESTATION__DEPENDENCY_TARGET:
				setDependencyTarget((NamedElement)newValue);
				return;
			case UML2Package.MANIFESTATION__RESEMBLANCE:
				setResemblance(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.MANIFESTATION__REPLACEMENT:
				setReplacement(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.MANIFESTATION__MAPPING:
				setMapping((OpaqueExpression)newValue);
				return;
			case UML2Package.MANIFESTATION__UTILIZED_ELEMENT:
				setUtilizedElement((PackageableElement)newValue);
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
			case UML2Package.MANIFESTATION__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.MANIFESTATION__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.MANIFESTATION__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.MANIFESTATION__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.MANIFESTATION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.MANIFESTATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.MANIFESTATION__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.MANIFESTATION__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.MANIFESTATION__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.MANIFESTATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.MANIFESTATION__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.MANIFESTATION__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.MANIFESTATION__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.MANIFESTATION__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.MANIFESTATION__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.MANIFESTATION__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.MANIFESTATION__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.MANIFESTATION__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.MANIFESTATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.MANIFESTATION__CLIENT:
				getClients().clear();
				return;
			case UML2Package.MANIFESTATION__SUPPLIER:
				getSuppliers().clear();
				return;
			case UML2Package.MANIFESTATION__DEPENDENCY_TARGET:
				setDependencyTarget((NamedElement)null);
				return;
			case UML2Package.MANIFESTATION__RESEMBLANCE:
				setResemblance(RESEMBLANCE_EDEFAULT);
				return;
			case UML2Package.MANIFESTATION__REPLACEMENT:
				setReplacement(REPLACEMENT_EDEFAULT);
				return;
			case UML2Package.MANIFESTATION__MAPPING:
				setMapping((OpaqueExpression)null);
				return;
			case UML2Package.MANIFESTATION__UTILIZED_ELEMENT:
				setUtilizedElement((PackageableElement)null);
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
			case UML2Package.MANIFESTATION__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.MANIFESTATION__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.MANIFESTATION__OWNER:
				return basicGetOwner() != null;
			case UML2Package.MANIFESTATION__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.MANIFESTATION__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.MANIFESTATION__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.MANIFESTATION__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.MANIFESTATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.MANIFESTATION__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.MANIFESTATION__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.MANIFESTATION__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.MANIFESTATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.MANIFESTATION__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.MANIFESTATION__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.MANIFESTATION__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.MANIFESTATION__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.MANIFESTATION__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.MANIFESTATION__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.MANIFESTATION__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.MANIFESTATION__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.MANIFESTATION__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.MANIFESTATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.MANIFESTATION__RELATED_ELEMENT:
				return !getRelatedElements().isEmpty();
			case UML2Package.MANIFESTATION__SOURCE:
				return !getSources().isEmpty();
			case UML2Package.MANIFESTATION__TARGET:
				return !getTargets().isEmpty();
			case UML2Package.MANIFESTATION__CLIENT:
				return client != null && !client.isEmpty();
			case UML2Package.MANIFESTATION__SUPPLIER:
				return supplier != null && !supplier.isEmpty();
			case UML2Package.MANIFESTATION__DEPENDENCY_TARGET:
				return dependencyTarget != null;
			case UML2Package.MANIFESTATION__RESEMBLANCE:
				return ((eFlags & RESEMBLANCE_EFLAG) != 0) != RESEMBLANCE_EDEFAULT;
			case UML2Package.MANIFESTATION__REPLACEMENT:
				return ((eFlags & REPLACEMENT_EFLAG) != 0) != REPLACEMENT_EDEFAULT;
			case UML2Package.MANIFESTATION__MAPPING:
				return mapping != null;
			case UML2Package.MANIFESTATION__UTILIZED_ELEMENT:
				return utilizedElement != null;
		}
		return eDynamicIsSet(eFeature);
	}


	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.MANIFESTATION__VISIBILITY:
				return false;
			case UML2Package.MANIFESTATION__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
		}
		return eIsSetGen(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getTargetsHelper(EList target)
	{
		super.getTargetsHelper(target);
		if (eIsSet(UML2Package.eINSTANCE.getManifestation_UtilizedElement())) {
			target.add(getUtilizedElement());
		}
		return target;
	}


} //ManifestationImpl
