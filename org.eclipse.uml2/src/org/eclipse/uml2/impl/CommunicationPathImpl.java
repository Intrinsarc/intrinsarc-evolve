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
 * $Id: CommunicationPathImpl.java,v 1.1 2009-03-04 23:06:34 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.uml2.CollaborationOccurrence;
import org.eclipse.uml2.CommunicationPath;
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Communication Path</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class CommunicationPathImpl extends AssociationImpl implements CommunicationPath {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommunicationPathImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (CommunicationPathImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getCommunicationPath();
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
				case UML2Package.COMMUNICATION_PATH__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.COMMUNICATION_PATH__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.COMMUNICATION_PATH__OWNING_PARAMETER, msgs);
				case UML2Package.COMMUNICATION_PATH__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__USE_CASE:
					return ((InternalEList)getUseCases()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__OWNED_END:
					return ((InternalEList)getOwnedEnds()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__MEMBER_END:
					return ((InternalEList)getMemberEnds()).basicAdd(otherEnd, msgs);
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
				case UML2Package.COMMUNICATION_PATH__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.COMMUNICATION_PATH__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.COMMUNICATION_PATH__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.COMMUNICATION_PATH__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.COMMUNICATION_PATH__OWNING_PARAMETER, msgs);
				case UML2Package.COMMUNICATION_PATH__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__OWNED_USE_CASE:
					return ((InternalEList)getOwnedUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__USE_CASE:
					return ((InternalEList)getUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__OCCURRENCE:
					return ((InternalEList)getOccurrences()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__OWNED_END:
					return ((InternalEList)getOwnedEnds()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMUNICATION_PATH__MEMBER_END:
					return ((InternalEList)getMemberEnds()).basicRemove(otherEnd, msgs);
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
				case UML2Package.COMMUNICATION_PATH__OWNING_PARAMETER:
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
			case UML2Package.COMMUNICATION_PATH__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.COMMUNICATION_PATH__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.COMMUNICATION_PATH__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.COMMUNICATION_PATH__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.COMMUNICATION_PATH__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.COMMUNICATION_PATH__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.COMMUNICATION_PATH__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.COMMUNICATION_PATH__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.COMMUNICATION_PATH__UUID:
				return getUuid();
			case UML2Package.COMMUNICATION_PATH__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.COMMUNICATION_PATH__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.COMMUNICATION_PATH__NAME:
				return getName();
			case UML2Package.COMMUNICATION_PATH__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.COMMUNICATION_PATH__VISIBILITY:
				return getVisibility();
			case UML2Package.COMMUNICATION_PATH__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.COMMUNICATION_PATH__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.COMMUNICATION_PATH__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.COMMUNICATION_PATH__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.COMMUNICATION_PATH__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.COMMUNICATION_PATH__MEMBER:
				return getMembers();
			case UML2Package.COMMUNICATION_PATH__OWNED_RULE:
				return getOwnedRules();
			case UML2Package.COMMUNICATION_PATH__IMPORTED_MEMBER:
				return getImportedMembers();
			case UML2Package.COMMUNICATION_PATH__ELEMENT_IMPORT:
				return getElementImports();
			case UML2Package.COMMUNICATION_PATH__PACKAGE_IMPORT:
				return getPackageImports();
			case UML2Package.COMMUNICATION_PATH__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.COMMUNICATION_PATH__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.COMMUNICATION_PATH__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.COMMUNICATION_PATH__PACKAGE:
				if (resolve) return getPackage();
				return basicGetPackage();
			case UML2Package.COMMUNICATION_PATH__IS_RETIRED:
				return isRetired() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.COMMUNICATION_PATH__REDEFINITION_CONTEXT:
				return getRedefinitionContexts();
			case UML2Package.COMMUNICATION_PATH__IS_LEAF:
				return isLeaf() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.COMMUNICATION_PATH__FEATURE:
				return getFeatures();
			case UML2Package.COMMUNICATION_PATH__IS_ABSTRACT:
				return isAbstract() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.COMMUNICATION_PATH__INHERITED_MEMBER:
				return getInheritedMembers();
			case UML2Package.COMMUNICATION_PATH__GENERAL:
				return getGenerals();
			case UML2Package.COMMUNICATION_PATH__GENERALIZATION:
				return getGeneralizations();
			case UML2Package.COMMUNICATION_PATH__ATTRIBUTE:
				return getAttributes();
			case UML2Package.COMMUNICATION_PATH__REDEFINED_CLASSIFIER:
				return getRedefinedClassifiers();
			case UML2Package.COMMUNICATION_PATH__SUBSTITUTION:
				return getSubstitutions();
			case UML2Package.COMMUNICATION_PATH__POWERTYPE_EXTENT:
				return getPowertypeExtents();
			case UML2Package.COMMUNICATION_PATH__OWNED_USE_CASE:
				return getOwnedUseCases();
			case UML2Package.COMMUNICATION_PATH__USE_CASE:
				return getUseCases();
			case UML2Package.COMMUNICATION_PATH__REPRESENTATION:
				return getRepresentation();
			case UML2Package.COMMUNICATION_PATH__OCCURRENCE:
				return getOccurrences();
			case UML2Package.COMMUNICATION_PATH__RELATED_ELEMENT:
				return getRelatedElements();
			case UML2Package.COMMUNICATION_PATH__IS_DERIVED:
				return isDerived() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.COMMUNICATION_PATH__OWNED_END:
				return getOwnedEnds();
			case UML2Package.COMMUNICATION_PATH__END_TYPE:
				return getEndTypes();
			case UML2Package.COMMUNICATION_PATH__MEMBER_END:
				return getMemberEnds();
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
			case UML2Package.COMMUNICATION_PATH__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.COMMUNICATION_PATH__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__NAME:
				setName((String)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__OWNED_RULE:
				getOwnedRules().clear();
				getOwnedRules().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__ELEMENT_IMPORT:
				getElementImports().clear();
				getElementImports().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__PACKAGE_IMPORT:
				getPackageImports().clear();
				getPackageImports().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__IS_RETIRED:
				setIsRetired(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.COMMUNICATION_PATH__IS_LEAF:
				setIsLeaf(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.COMMUNICATION_PATH__IS_ABSTRACT:
				setIsAbstract(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.COMMUNICATION_PATH__GENERALIZATION:
				getGeneralizations().clear();
				getGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				getRedefinedClassifiers().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__SUBSTITUTION:
				getSubstitutions().clear();
				getSubstitutions().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				getPowertypeExtents().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				getOwnedUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__USE_CASE:
				getUseCases().clear();
				getUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__OCCURRENCE:
				getOccurrences().clear();
				getOccurrences().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__IS_DERIVED:
				setIsDerived(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.COMMUNICATION_PATH__OWNED_END:
				getOwnedEnds().clear();
				getOwnedEnds().addAll((Collection)newValue);
				return;
			case UML2Package.COMMUNICATION_PATH__MEMBER_END:
				getMemberEnds().clear();
				getMemberEnds().addAll((Collection)newValue);
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
			case UML2Package.COMMUNICATION_PATH__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.COMMUNICATION_PATH__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.COMMUNICATION_PATH__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.COMMUNICATION_PATH__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.COMMUNICATION_PATH__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.COMMUNICATION_PATH__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.COMMUNICATION_PATH__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.COMMUNICATION_PATH__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__OWNED_RULE:
				getOwnedRules().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__ELEMENT_IMPORT:
				getElementImports().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__PACKAGE_IMPORT:
				getPackageImports().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.COMMUNICATION_PATH__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.COMMUNICATION_PATH__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.COMMUNICATION_PATH__IS_RETIRED:
				setIsRetired(IS_RETIRED_EDEFAULT);
				return;
			case UML2Package.COMMUNICATION_PATH__IS_LEAF:
				setIsLeaf(IS_LEAF_EDEFAULT);
				return;
			case UML2Package.COMMUNICATION_PATH__IS_ABSTRACT:
				setIsAbstract(IS_ABSTRACT_EDEFAULT);
				return;
			case UML2Package.COMMUNICATION_PATH__GENERALIZATION:
				getGeneralizations().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__SUBSTITUTION:
				getSubstitutions().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__USE_CASE:
				getUseCases().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)null);
				return;
			case UML2Package.COMMUNICATION_PATH__OCCURRENCE:
				getOccurrences().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__IS_DERIVED:
				setIsDerived(IS_DERIVED_EDEFAULT);
				return;
			case UML2Package.COMMUNICATION_PATH__OWNED_END:
				getOwnedEnds().clear();
				return;
			case UML2Package.COMMUNICATION_PATH__MEMBER_END:
				getMemberEnds().clear();
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
			case UML2Package.COMMUNICATION_PATH__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.COMMUNICATION_PATH__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.COMMUNICATION_PATH__OWNER:
				return basicGetOwner() != null;
			case UML2Package.COMMUNICATION_PATH__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.COMMUNICATION_PATH__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.COMMUNICATION_PATH__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.COMMUNICATION_PATH__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.COMMUNICATION_PATH__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.COMMUNICATION_PATH__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.COMMUNICATION_PATH__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.COMMUNICATION_PATH__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.COMMUNICATION_PATH__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.COMMUNICATION_PATH__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.COMMUNICATION_PATH__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.COMMUNICATION_PATH__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.COMMUNICATION_PATH__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.COMMUNICATION_PATH__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.COMMUNICATION_PATH__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.COMMUNICATION_PATH__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.COMMUNICATION_PATH__MEMBER:
				return !getMembers().isEmpty();
			case UML2Package.COMMUNICATION_PATH__OWNED_RULE:
				return ownedRule != null && !ownedRule.isEmpty();
			case UML2Package.COMMUNICATION_PATH__IMPORTED_MEMBER:
				return !getImportedMembers().isEmpty();
			case UML2Package.COMMUNICATION_PATH__ELEMENT_IMPORT:
				return elementImport != null && !elementImport.isEmpty();
			case UML2Package.COMMUNICATION_PATH__PACKAGE_IMPORT:
				return packageImport != null && !packageImport.isEmpty();
			case UML2Package.COMMUNICATION_PATH__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.COMMUNICATION_PATH__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.COMMUNICATION_PATH__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.COMMUNICATION_PATH__PACKAGE:
				return basicGetPackage() != null;
			case UML2Package.COMMUNICATION_PATH__IS_RETIRED:
				return ((eFlags & IS_RETIRED_EFLAG) != 0) != IS_RETIRED_EDEFAULT;
			case UML2Package.COMMUNICATION_PATH__REDEFINITION_CONTEXT:
				return !getRedefinitionContexts().isEmpty();
			case UML2Package.COMMUNICATION_PATH__IS_LEAF:
				return ((eFlags & IS_LEAF_EFLAG) != 0) != IS_LEAF_EDEFAULT;
			case UML2Package.COMMUNICATION_PATH__FEATURE:
				return !getFeatures().isEmpty();
			case UML2Package.COMMUNICATION_PATH__IS_ABSTRACT:
				return ((eFlags & IS_ABSTRACT_EFLAG) != 0) != IS_ABSTRACT_EDEFAULT;
			case UML2Package.COMMUNICATION_PATH__INHERITED_MEMBER:
				return !getInheritedMembers().isEmpty();
			case UML2Package.COMMUNICATION_PATH__GENERAL:
				return !getGenerals().isEmpty();
			case UML2Package.COMMUNICATION_PATH__GENERALIZATION:
				return generalization != null && !generalization.isEmpty();
			case UML2Package.COMMUNICATION_PATH__ATTRIBUTE:
				return !getAttributes().isEmpty();
			case UML2Package.COMMUNICATION_PATH__REDEFINED_CLASSIFIER:
				return redefinedClassifier != null && !redefinedClassifier.isEmpty();
			case UML2Package.COMMUNICATION_PATH__SUBSTITUTION:
				return substitution != null && !substitution.isEmpty();
			case UML2Package.COMMUNICATION_PATH__POWERTYPE_EXTENT:
				return powertypeExtent != null && !powertypeExtent.isEmpty();
			case UML2Package.COMMUNICATION_PATH__OWNED_USE_CASE:
				return ownedUseCase != null && !ownedUseCase.isEmpty();
			case UML2Package.COMMUNICATION_PATH__USE_CASE:
				return useCase != null && !useCase.isEmpty();
			case UML2Package.COMMUNICATION_PATH__REPRESENTATION:
				return representation != null;
			case UML2Package.COMMUNICATION_PATH__OCCURRENCE:
				return occurrence != null && !occurrence.isEmpty();
			case UML2Package.COMMUNICATION_PATH__RELATED_ELEMENT:
				return !getRelatedElements().isEmpty();
			case UML2Package.COMMUNICATION_PATH__IS_DERIVED:
				return ((eFlags & IS_DERIVED_EFLAG) != 0) != IS_DERIVED_EDEFAULT;
			case UML2Package.COMMUNICATION_PATH__OWNED_END:
				return ownedEnd != null && !ownedEnd.isEmpty();
			case UML2Package.COMMUNICATION_PATH__END_TYPE:
				return !getEndTypes().isEmpty();
			case UML2Package.COMMUNICATION_PATH__MEMBER_END:
				return memberEnd != null && !memberEnd.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}


	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.COMMUNICATION_PATH__VISIBILITY:
				return false;
			case UML2Package.COMMUNICATION_PATH__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
		}
		return eIsSetGen(eFeature);
	}

} //CommunicationPathImpl
