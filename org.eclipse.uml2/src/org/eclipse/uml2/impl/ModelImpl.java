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
 * $Id: ModelImpl.java,v 1.2 2009-04-22 10:01:13 andrew Exp $
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
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.uml2.Element;
import org.eclipse.uml2.J_DiagramHolder;
import org.eclipse.uml2.J_Diagram;
import org.eclipse.uml2.Model;
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;
import org.eclipse.uml2.internal.operation.ModelOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.ModelImpl#getViewpoint <em>Viewpoint</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelImpl extends PackageImpl implements Model {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getViewpoint() <em>Viewpoint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewpoint()
	 * @generated
	 * @ordered
	 */
	protected static final String VIEWPOINT_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getViewpoint() <em>Viewpoint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewpoint()
	 * @generated
	 * @ordered
	 */
	protected String viewpoint = VIEWPOINT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getModel();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getViewpoint() {
		return viewpoint;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setViewpoint(String newViewpoint) {
		newViewpoint = newViewpoint == null ? VIEWPOINT_EDEFAULT : newViewpoint;
		String oldViewpoint = viewpoint;
		viewpoint = newViewpoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.MODEL__VIEWPOINT, oldViewpoint, viewpoint));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.MODEL__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.MODEL__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.MODEL__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.MODEL__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.MODEL__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.MODEL__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicAdd(otherEnd, msgs);
				case UML2Package.MODEL__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicAdd(otherEnd, msgs);
				case UML2Package.MODEL__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicAdd(otherEnd, msgs);
				case UML2Package.MODEL__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.MODEL__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.MODEL__OWNING_PARAMETER, msgs);
				case UML2Package.MODEL__PACKAGE_MERGE:
					return ((InternalEList)getPackageMerges()).basicAdd(otherEnd, msgs);
				case UML2Package.MODEL__CHILD_PACKAGES:
					return ((InternalEList)getChildPackages()).basicAdd(otherEnd, msgs);
				case UML2Package.MODEL__PARENT_PACKAGE:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.MODEL__PARENT_PACKAGE, msgs);
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
				case UML2Package.MODEL__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.MODEL__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.MODEL__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.MODEL__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.MODEL__OWNING_PARAMETER, msgs);
				case UML2Package.MODEL__OWNED_MEMBER:
					return ((InternalEList)getOwnedMembers()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__PACKAGE_MERGE:
					return ((InternalEList)getPackageMerges()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__PACKAGE_EXTENSION:
					return ((InternalEList)getPackageExtensions()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__JDIAGRAM_HOLDER:
					return basicSetJ_diagramHolder(null, msgs);
				case UML2Package.MODEL__CHILD_PACKAGES:
					return ((InternalEList)getChildPackages()).basicRemove(otherEnd, msgs);
				case UML2Package.MODEL__PARENT_PACKAGE:
					return eBasicSetContainer(null, UML2Package.MODEL__PARENT_PACKAGE, msgs);
				case UML2Package.MODEL__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS:
					return ((InternalEList)getAnonymousDeletedImportPlaceholders()).basicRemove(otherEnd, msgs);
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
				case UML2Package.MODEL__OWNING_PARAMETER:
					return eContainer.eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__OWNED_PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
				case UML2Package.MODEL__PARENT_PACKAGE:
					return eContainer.eInverseRemove(this, UML2Package.PACKAGE__CHILD_PACKAGES, org.eclipse.uml2.Package.class, msgs);
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
			case UML2Package.MODEL__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.MODEL__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.MODEL__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.MODEL__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.MODEL__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.MODEL__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.MODEL__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.MODEL__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.MODEL__UUID:
				return getUuid();
			case UML2Package.MODEL__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.MODEL__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.MODEL__NAME:
				return getName();
			case UML2Package.MODEL__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.MODEL__VISIBILITY:
				return getVisibility();
			case UML2Package.MODEL__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.MODEL__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.MODEL__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.MODEL__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.MODEL__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.MODEL__MEMBER:
				return getMembers();
			case UML2Package.MODEL__OWNED_RULE:
				return getOwnedRules();
			case UML2Package.MODEL__IMPORTED_MEMBER:
				return getImportedMembers();
			case UML2Package.MODEL__ELEMENT_IMPORT:
				return getElementImports();
			case UML2Package.MODEL__PACKAGE_IMPORT:
				return getPackageImports();
			case UML2Package.MODEL__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.MODEL__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.MODEL__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.MODEL__NESTED_PACKAGE:
				return getNestedPackages();
			case UML2Package.MODEL__NESTING_PACKAGE:
				if (resolve) return getNestingPackage();
				return basicGetNestingPackage();
			case UML2Package.MODEL__OWNED_TYPE:
				return getOwnedTypes();
			case UML2Package.MODEL__OWNED_MEMBER:
				return getOwnedMembers();
			case UML2Package.MODEL__PACKAGE_MERGE:
				return getPackageMerges();
			case UML2Package.MODEL__APPLIED_PROFILE:
				return getAppliedProfiles();
			case UML2Package.MODEL__PACKAGE_EXTENSION:
				return getPackageExtensions();
			case UML2Package.MODEL__JDIAGRAM_HOLDER:
				return getJ_diagramHolder();
			case UML2Package.MODEL__CHILD_PACKAGES:
				return getChildPackages();
			case UML2Package.MODEL__PARENT_PACKAGE:
				return getParentPackage();
			case UML2Package.MODEL__READ_ONLY:
				return isReadOnly() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.MODEL__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS:
				return getAnonymousDeletedImportPlaceholders();
			case UML2Package.MODEL__VIEWPOINT:
				return getViewpoint();
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
			case UML2Package.MODEL__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.MODEL__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.MODEL__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.MODEL__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.MODEL__NAME:
				setName((String)newValue);
				return;
			case UML2Package.MODEL__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.MODEL__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.MODEL__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__OWNED_RULE:
				getOwnedRules().clear();
				getOwnedRules().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__ELEMENT_IMPORT:
				getElementImports().clear();
				getElementImports().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__PACKAGE_IMPORT:
				getPackageImports().clear();
				getPackageImports().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.MODEL__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.MODEL__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.MODEL__OWNED_MEMBER:
				getOwnedMembers().clear();
				getOwnedMembers().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__PACKAGE_MERGE:
				getPackageMerges().clear();
				getPackageMerges().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__APPLIED_PROFILE:
				getAppliedProfiles().clear();
				getAppliedProfiles().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__PACKAGE_EXTENSION:
				getPackageExtensions().clear();
				getPackageExtensions().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__JDIAGRAM_HOLDER:
				setJ_diagramHolder((J_DiagramHolder)newValue);
				return;
			case UML2Package.MODEL__CHILD_PACKAGES:
				getChildPackages().clear();
				getChildPackages().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__PARENT_PACKAGE:
				setParentPackage((org.eclipse.uml2.Package)newValue);
				return;
			case UML2Package.MODEL__READ_ONLY:
				setReadOnly(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.MODEL__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS:
				getAnonymousDeletedImportPlaceholders().clear();
				getAnonymousDeletedImportPlaceholders().addAll((Collection)newValue);
				return;
			case UML2Package.MODEL__VIEWPOINT:
				setViewpoint((String)newValue);
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
			case UML2Package.MODEL__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.MODEL__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.MODEL__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.MODEL__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.MODEL__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.MODEL__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.MODEL__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.MODEL__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.MODEL__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.MODEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.MODEL__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.MODEL__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.MODEL__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.MODEL__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.MODEL__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.MODEL__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.MODEL__OWNED_RULE:
				getOwnedRules().clear();
				return;
			case UML2Package.MODEL__ELEMENT_IMPORT:
				getElementImports().clear();
				return;
			case UML2Package.MODEL__PACKAGE_IMPORT:
				getPackageImports().clear();
				return;
			case UML2Package.MODEL__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.MODEL__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.MODEL__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.MODEL__OWNED_MEMBER:
				getOwnedMembers().clear();
				return;
			case UML2Package.MODEL__PACKAGE_MERGE:
				getPackageMerges().clear();
				return;
			case UML2Package.MODEL__APPLIED_PROFILE:
				getAppliedProfiles().clear();
				return;
			case UML2Package.MODEL__PACKAGE_EXTENSION:
				getPackageExtensions().clear();
				return;
			case UML2Package.MODEL__JDIAGRAM_HOLDER:
				setJ_diagramHolder((J_DiagramHolder)null);
				return;
			case UML2Package.MODEL__CHILD_PACKAGES:
				getChildPackages().clear();
				return;
			case UML2Package.MODEL__PARENT_PACKAGE:
				setParentPackage((org.eclipse.uml2.Package)null);
				return;
			case UML2Package.MODEL__READ_ONLY:
				setReadOnly(READ_ONLY_EDEFAULT);
				return;
			case UML2Package.MODEL__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS:
				getAnonymousDeletedImportPlaceholders().clear();
				return;
			case UML2Package.MODEL__VIEWPOINT:
				setViewpoint(VIEWPOINT_EDEFAULT);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSetGen(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.MODEL__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.MODEL__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.MODEL__OWNER:
				return basicGetOwner() != null;
			case UML2Package.MODEL__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.MODEL__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.MODEL__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.MODEL__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.MODEL__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.MODEL__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.MODEL__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.MODEL__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.MODEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.MODEL__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.MODEL__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.MODEL__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.MODEL__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.MODEL__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.MODEL__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.MODEL__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.MODEL__MEMBER:
				return !getMembers().isEmpty();
			case UML2Package.MODEL__OWNED_RULE:
				return ownedRule != null && !ownedRule.isEmpty();
			case UML2Package.MODEL__IMPORTED_MEMBER:
				return !getImportedMembers().isEmpty();
			case UML2Package.MODEL__ELEMENT_IMPORT:
				return elementImport != null && !elementImport.isEmpty();
			case UML2Package.MODEL__PACKAGE_IMPORT:
				return packageImport != null && !packageImport.isEmpty();
			case UML2Package.MODEL__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.MODEL__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.MODEL__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.MODEL__NESTED_PACKAGE:
				return !getNestedPackages().isEmpty();
			case UML2Package.MODEL__NESTING_PACKAGE:
				return basicGetNestingPackage() != null;
			case UML2Package.MODEL__OWNED_TYPE:
				return !getOwnedTypes().isEmpty();
			case UML2Package.MODEL__OWNED_MEMBER:
				return ownedMember != null && !ownedMember.isEmpty();
			case UML2Package.MODEL__PACKAGE_MERGE:
				return packageMerge != null && !packageMerge.isEmpty();
			case UML2Package.MODEL__APPLIED_PROFILE:
				return appliedProfile != null && !appliedProfile.isEmpty();
			case UML2Package.MODEL__PACKAGE_EXTENSION:
				return packageExtension != null && !packageExtension.isEmpty();
			case UML2Package.MODEL__JDIAGRAM_HOLDER:
				return j_diagramHolder != null;
			case UML2Package.MODEL__CHILD_PACKAGES:
				return childPackages != null && !childPackages.isEmpty();
			case UML2Package.MODEL__PARENT_PACKAGE:
				return getParentPackage() != null;
			case UML2Package.MODEL__READ_ONLY:
				return ((eFlags & READ_ONLY_EFLAG) != 0) != READ_ONLY_EDEFAULT;
			case UML2Package.MODEL__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS:
				return anonymousDeletedImportPlaceholders != null && !anonymousDeletedImportPlaceholders.isEmpty();
			case UML2Package.MODEL__VIEWPOINT:
				return VIEWPOINT_EDEFAULT == null ? viewpoint != null : !VIEWPOINT_EDEFAULT.equals(viewpoint);
		}
		return eDynamicIsSet(eFeature);
	}

	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.MODEL__VISIBILITY:
				return false;
			case UML2Package.MODEL__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
		}
		return eIsSetGen(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (viewpoint: "); //$NON-NLS-1$
		result.append(viewpoint);
		result.append(')');
		return result.toString();
	}


	// <!-- begin-custom-operations -->

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Model#isLibrary()
	 */
	public boolean isLibrary() {
		return ModelOperations.isLibrary(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Model#isMetamodel()
	 */
	public boolean isMetamodel() {
		return ModelOperations.isMetamodel(this);
	}

	// <!-- end-custom-operations -->

} //ModelImpl
