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
 * $Id: DataTypeImpl.java,v 1.1 2009-03-04 23:06:38 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.CollaborationOccurrence;
import org.eclipse.uml2.DataType;
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.Operation;
import org.eclipse.uml2.Property;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.Type;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.internal.operation.DataTypeOperations;
import org.eclipse.uml2.internal.operation.TypeOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.DataTypeImpl#getOwnedAttributes <em>Owned Attribute</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.DataTypeImpl#getOwnedOperations <em>Owned Operation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DataTypeImpl extends ClassifierImpl implements DataType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getOwnedAttributes() <em>Owned Attribute</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList ownedAttribute = null;

	/**
	 * The cached value of the '{@link #getOwnedOperations() <em>Owned Operation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedOperations()
	 * @generated
	 * @ordered
	 */
	protected EList ownedOperation = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataTypeImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (DataTypeImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getDataType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedAttributes() {
		if (ownedAttribute == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		ownedAttribute = new com.hopstepjump.emflist.PersistentEList(Property.class, this, UML2Package.DATA_TYPE__OWNED_ATTRIBUTE, UML2Package.PROPERTY__DATATYPE);
			 		return ownedAttribute;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(Property.class, this, UML2Package.DATA_TYPE__OWNED_ATTRIBUTE, UML2Package.PROPERTY__DATATYPE);
		}      
		return ownedAttribute;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedAttributes() {
		if (ownedAttribute == null) {
			ownedAttribute = new com.hopstepjump.emflist.PersistentEList(Property.class, this, UML2Package.DATA_TYPE__OWNED_ATTRIBUTE, UML2Package.PROPERTY__DATATYPE);
		}
		return ownedAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedAttributes() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedAttribute != null) {
			for (Object object : ownedAttribute) {
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
    public Property getOwnedAttribute(String name) {
		for (Iterator i = getOwnedAttributes().iterator(); i.hasNext(); ) {
			Property ownedAttribute = (Property) i.next();
			if (name.equals(ownedAttribute.getName())) {
				return ownedAttribute;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property createOwnedAttribute(EClass eClass) {
		Property newOwnedAttribute = (Property) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.DATA_TYPE__OWNED_ATTRIBUTE, null, newOwnedAttribute));
		}
		settable_getOwnedAttributes().add(newOwnedAttribute);
		return newOwnedAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property createOwnedAttribute() {
		Property newOwnedAttribute = UML2Factory.eINSTANCE.createProperty();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.DATA_TYPE__OWNED_ATTRIBUTE, null, newOwnedAttribute));
		}
		settable_getOwnedAttributes().add(newOwnedAttribute);
		return newOwnedAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedOperations() {
		if (ownedOperation == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		ownedOperation = new com.hopstepjump.emflist.PersistentEList(Operation.class, this, UML2Package.DATA_TYPE__OWNED_OPERATION, UML2Package.OPERATION__DATATYPE);
			 		return ownedOperation;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(Operation.class, this, UML2Package.DATA_TYPE__OWNED_OPERATION, UML2Package.OPERATION__DATATYPE);
		}      
		return ownedOperation;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedOperations() {
		if (ownedOperation == null) {
			ownedOperation = new com.hopstepjump.emflist.PersistentEList(Operation.class, this, UML2Package.DATA_TYPE__OWNED_OPERATION, UML2Package.OPERATION__DATATYPE);
		}
		return ownedOperation;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedOperations() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedOperation != null) {
			for (Object object : ownedOperation) {
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
    public Operation getOwnedOperation(String name) {
		for (Iterator i = getOwnedOperations().iterator(); i.hasNext(); ) {
			Operation ownedOperation = (Operation) i.next();
			if (name.equals(ownedOperation.getName())) {
				return ownedOperation;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createOwnedOperation() instead.
	 */
	public Operation createOwnedOperation(EClass eClass) {
		Operation newOwnedOperation = (Operation) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.DATA_TYPE__OWNED_OPERATION, null, newOwnedOperation));
		}
		getOwnedOperations().add(newOwnedOperation);
		return newOwnedOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operation createOwnedOperation() {
		Operation newOwnedOperation = UML2Factory.eINSTANCE.createOperation();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.DATA_TYPE__OWNED_OPERATION, null, newOwnedOperation));
		}
		settable_getOwnedOperations().add(newOwnedOperation);
		return newOwnedOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Set inherit(Set inhs) {
		return DataTypeOperations.inherit(this, inhs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.DATA_TYPE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.DATA_TYPE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.DATA_TYPE__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.DATA_TYPE__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.DATA_TYPE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.DATA_TYPE__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicAdd(otherEnd, msgs);
				case UML2Package.DATA_TYPE__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicAdd(otherEnd, msgs);
				case UML2Package.DATA_TYPE__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicAdd(otherEnd, msgs);
				case UML2Package.DATA_TYPE__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.DATA_TYPE__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.DATA_TYPE__OWNING_PARAMETER, msgs);
				case UML2Package.DATA_TYPE__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicAdd(otherEnd, msgs);
				case UML2Package.DATA_TYPE__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicAdd(otherEnd, msgs);
				case UML2Package.DATA_TYPE__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicAdd(otherEnd, msgs);
				case UML2Package.DATA_TYPE__USE_CASE:
					return ((InternalEList)getUseCases()).basicAdd(otherEnd, msgs);
				case UML2Package.DATA_TYPE__OWNED_ATTRIBUTE:
					return ((InternalEList)getOwnedAttributes()).basicAdd(otherEnd, msgs);
				case UML2Package.DATA_TYPE__OWNED_OPERATION:
					return ((InternalEList)getOwnedOperations()).basicAdd(otherEnd, msgs);
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
				case UML2Package.DATA_TYPE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.DATA_TYPE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.DATA_TYPE__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.DATA_TYPE__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.DATA_TYPE__OWNING_PARAMETER, msgs);
				case UML2Package.DATA_TYPE__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__OWNED_USE_CASE:
					return ((InternalEList)getOwnedUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__USE_CASE:
					return ((InternalEList)getUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__OCCURRENCE:
					return ((InternalEList)getOccurrences()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__OWNED_ATTRIBUTE:
					return ((InternalEList)getOwnedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.DATA_TYPE__OWNED_OPERATION:
					return ((InternalEList)getOwnedOperations()).basicRemove(otherEnd, msgs);
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
				case UML2Package.DATA_TYPE__OWNING_PARAMETER:
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.DATA_TYPE__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.DATA_TYPE__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.DATA_TYPE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.DATA_TYPE__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.DATA_TYPE__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.DATA_TYPE__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.DATA_TYPE__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.DATA_TYPE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.DATA_TYPE__UUID:
				return getUuid();
			case UML2Package.DATA_TYPE__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.DATA_TYPE__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.DATA_TYPE__NAME:
				return getName();
			case UML2Package.DATA_TYPE__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.DATA_TYPE__VISIBILITY:
				return getVisibility();
			case UML2Package.DATA_TYPE__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.DATA_TYPE__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.DATA_TYPE__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.DATA_TYPE__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.DATA_TYPE__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.DATA_TYPE__MEMBER:
				return getMembers();
			case UML2Package.DATA_TYPE__OWNED_RULE:
				return getOwnedRules();
			case UML2Package.DATA_TYPE__IMPORTED_MEMBER:
				return getImportedMembers();
			case UML2Package.DATA_TYPE__ELEMENT_IMPORT:
				return getElementImports();
			case UML2Package.DATA_TYPE__PACKAGE_IMPORT:
				return getPackageImports();
			case UML2Package.DATA_TYPE__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.DATA_TYPE__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.DATA_TYPE__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.DATA_TYPE__PACKAGE:
				if (resolve) return getPackage();
				return basicGetPackage();
			case UML2Package.DATA_TYPE__IS_RETIRED:
				return isRetired() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.DATA_TYPE__REDEFINITION_CONTEXT:
				return getRedefinitionContexts();
			case UML2Package.DATA_TYPE__IS_LEAF:
				return isLeaf() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.DATA_TYPE__FEATURE:
				return getFeatures();
			case UML2Package.DATA_TYPE__IS_ABSTRACT:
				return isAbstract() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.DATA_TYPE__INHERITED_MEMBER:
				return getInheritedMembers();
			case UML2Package.DATA_TYPE__GENERAL:
				return getGenerals();
			case UML2Package.DATA_TYPE__GENERALIZATION:
				return getGeneralizations();
			case UML2Package.DATA_TYPE__ATTRIBUTE:
				return getAttributes();
			case UML2Package.DATA_TYPE__REDEFINED_CLASSIFIER:
				return getRedefinedClassifiers();
			case UML2Package.DATA_TYPE__SUBSTITUTION:
				return getSubstitutions();
			case UML2Package.DATA_TYPE__POWERTYPE_EXTENT:
				return getPowertypeExtents();
			case UML2Package.DATA_TYPE__OWNED_USE_CASE:
				return getOwnedUseCases();
			case UML2Package.DATA_TYPE__USE_CASE:
				return getUseCases();
			case UML2Package.DATA_TYPE__REPRESENTATION:
				return getRepresentation();
			case UML2Package.DATA_TYPE__OCCURRENCE:
				return getOccurrences();
			case UML2Package.DATA_TYPE__OWNED_ATTRIBUTE:
				return getOwnedAttributes();
			case UML2Package.DATA_TYPE__OWNED_OPERATION:
				return getOwnedOperations();
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
			case UML2Package.DATA_TYPE__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.DATA_TYPE__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.DATA_TYPE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.DATA_TYPE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.DATA_TYPE__NAME:
				setName((String)newValue);
				return;
			case UML2Package.DATA_TYPE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.DATA_TYPE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.DATA_TYPE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__OWNED_RULE:
				getOwnedRules().clear();
				getOwnedRules().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__ELEMENT_IMPORT:
				getElementImports().clear();
				getElementImports().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__PACKAGE_IMPORT:
				getPackageImports().clear();
				getPackageImports().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.DATA_TYPE__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.DATA_TYPE__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.DATA_TYPE__IS_RETIRED:
				setIsRetired(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.DATA_TYPE__IS_LEAF:
				setIsLeaf(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.DATA_TYPE__IS_ABSTRACT:
				setIsAbstract(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.DATA_TYPE__GENERALIZATION:
				getGeneralizations().clear();
				getGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				getRedefinedClassifiers().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__SUBSTITUTION:
				getSubstitutions().clear();
				getSubstitutions().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				getPowertypeExtents().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				getOwnedUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__USE_CASE:
				getUseCases().clear();
				getUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)newValue);
				return;
			case UML2Package.DATA_TYPE__OCCURRENCE:
				getOccurrences().clear();
				getOccurrences().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__OWNED_ATTRIBUTE:
				getOwnedAttributes().clear();
				getOwnedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.DATA_TYPE__OWNED_OPERATION:
				getOwnedOperations().clear();
				getOwnedOperations().addAll((Collection)newValue);
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
			case UML2Package.DATA_TYPE__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.DATA_TYPE__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.DATA_TYPE__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.DATA_TYPE__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.DATA_TYPE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.DATA_TYPE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.DATA_TYPE__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.DATA_TYPE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.DATA_TYPE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.DATA_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.DATA_TYPE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.DATA_TYPE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.DATA_TYPE__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.DATA_TYPE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.DATA_TYPE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.DATA_TYPE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.DATA_TYPE__OWNED_RULE:
				getOwnedRules().clear();
				return;
			case UML2Package.DATA_TYPE__ELEMENT_IMPORT:
				getElementImports().clear();
				return;
			case UML2Package.DATA_TYPE__PACKAGE_IMPORT:
				getPackageImports().clear();
				return;
			case UML2Package.DATA_TYPE__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.DATA_TYPE__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.DATA_TYPE__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.DATA_TYPE__IS_RETIRED:
				setIsRetired(IS_RETIRED_EDEFAULT);
				return;
			case UML2Package.DATA_TYPE__IS_LEAF:
				setIsLeaf(IS_LEAF_EDEFAULT);
				return;
			case UML2Package.DATA_TYPE__IS_ABSTRACT:
				setIsAbstract(IS_ABSTRACT_EDEFAULT);
				return;
			case UML2Package.DATA_TYPE__GENERALIZATION:
				getGeneralizations().clear();
				return;
			case UML2Package.DATA_TYPE__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				return;
			case UML2Package.DATA_TYPE__SUBSTITUTION:
				getSubstitutions().clear();
				return;
			case UML2Package.DATA_TYPE__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				return;
			case UML2Package.DATA_TYPE__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				return;
			case UML2Package.DATA_TYPE__USE_CASE:
				getUseCases().clear();
				return;
			case UML2Package.DATA_TYPE__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)null);
				return;
			case UML2Package.DATA_TYPE__OCCURRENCE:
				getOccurrences().clear();
				return;
			case UML2Package.DATA_TYPE__OWNED_ATTRIBUTE:
				getOwnedAttributes().clear();
				return;
			case UML2Package.DATA_TYPE__OWNED_OPERATION:
				getOwnedOperations().clear();
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
			case UML2Package.DATA_TYPE__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.DATA_TYPE__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.DATA_TYPE__OWNER:
				return basicGetOwner() != null;
			case UML2Package.DATA_TYPE__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.DATA_TYPE__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.DATA_TYPE__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.DATA_TYPE__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.DATA_TYPE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.DATA_TYPE__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.DATA_TYPE__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.DATA_TYPE__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.DATA_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.DATA_TYPE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.DATA_TYPE__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.DATA_TYPE__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.DATA_TYPE__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.DATA_TYPE__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.DATA_TYPE__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.DATA_TYPE__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.DATA_TYPE__MEMBER:
				return !getMembers().isEmpty();
			case UML2Package.DATA_TYPE__OWNED_RULE:
				return ownedRule != null && !ownedRule.isEmpty();
			case UML2Package.DATA_TYPE__IMPORTED_MEMBER:
				return !getImportedMembers().isEmpty();
			case UML2Package.DATA_TYPE__ELEMENT_IMPORT:
				return elementImport != null && !elementImport.isEmpty();
			case UML2Package.DATA_TYPE__PACKAGE_IMPORT:
				return packageImport != null && !packageImport.isEmpty();
			case UML2Package.DATA_TYPE__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.DATA_TYPE__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.DATA_TYPE__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.DATA_TYPE__PACKAGE:
				return basicGetPackage() != null;
			case UML2Package.DATA_TYPE__IS_RETIRED:
				return ((eFlags & IS_RETIRED_EFLAG) != 0) != IS_RETIRED_EDEFAULT;
			case UML2Package.DATA_TYPE__REDEFINITION_CONTEXT:
				return !getRedefinitionContexts().isEmpty();
			case UML2Package.DATA_TYPE__IS_LEAF:
				return ((eFlags & IS_LEAF_EFLAG) != 0) != IS_LEAF_EDEFAULT;
			case UML2Package.DATA_TYPE__FEATURE:
				return !getFeatures().isEmpty();
			case UML2Package.DATA_TYPE__IS_ABSTRACT:
				return ((eFlags & IS_ABSTRACT_EFLAG) != 0) != IS_ABSTRACT_EDEFAULT;
			case UML2Package.DATA_TYPE__INHERITED_MEMBER:
				return !getInheritedMembers().isEmpty();
			case UML2Package.DATA_TYPE__GENERAL:
				return !getGenerals().isEmpty();
			case UML2Package.DATA_TYPE__GENERALIZATION:
				return generalization != null && !generalization.isEmpty();
			case UML2Package.DATA_TYPE__ATTRIBUTE:
				return !getAttributes().isEmpty();
			case UML2Package.DATA_TYPE__REDEFINED_CLASSIFIER:
				return redefinedClassifier != null && !redefinedClassifier.isEmpty();
			case UML2Package.DATA_TYPE__SUBSTITUTION:
				return substitution != null && !substitution.isEmpty();
			case UML2Package.DATA_TYPE__POWERTYPE_EXTENT:
				return powertypeExtent != null && !powertypeExtent.isEmpty();
			case UML2Package.DATA_TYPE__OWNED_USE_CASE:
				return ownedUseCase != null && !ownedUseCase.isEmpty();
			case UML2Package.DATA_TYPE__USE_CASE:
				return useCase != null && !useCase.isEmpty();
			case UML2Package.DATA_TYPE__REPRESENTATION:
				return representation != null;
			case UML2Package.DATA_TYPE__OCCURRENCE:
				return occurrence != null && !occurrence.isEmpty();
			case UML2Package.DATA_TYPE__OWNED_ATTRIBUTE:
				return ownedAttribute != null && !ownedAttribute.isEmpty();
			case UML2Package.DATA_TYPE__OWNED_OPERATION:
				return ownedOperation != null && !ownedOperation.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}


	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.DATA_TYPE__VISIBILITY:
				return false;
			case UML2Package.DATA_TYPE__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
		}
		return eIsSetGen(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedMembersHelper(EList ownedMember) {
		super.getOwnedMembersHelper(ownedMember);
		if (eIsSet(UML2Package.eINSTANCE.getDataType_OwnedAttribute())) {
			ownedMember.addAll(getOwnedAttributes());
		}
		if (eIsSet(UML2Package.eINSTANCE.getDataType_OwnedOperation())) {
			ownedMember.addAll(getOwnedOperations());
		}
		return ownedMember;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getAttributesHelper(EList attribute) {
		super.getAttributesHelper(attribute);
		if (eIsSet(UML2Package.eINSTANCE.getDataType_OwnedAttribute())) {
			attribute.addAll(getOwnedAttributes());
		}
		return attribute;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getFeaturesHelper(EList feature) {
		super.getFeaturesHelper(feature);
		if (eIsSet(UML2Package.eINSTANCE.getDataType_OwnedOperation())) {
			feature.addAll(getOwnedOperations());
		}
		return feature;
	}


	// <!-- begin-custom-operations -->

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.DataType#createOwnedAttribute(java.lang.String,
	 *      org.eclipse.uml2.Type, int, int)
	 */
	public Property createOwnedAttribute(String name, Type type,
			int lowerBound, int upperBound) {
		return TypeOperations.createOwnedAttribute(this, name, type,
			lowerBound, upperBound);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.DataType#createOwnedOperation(java.lang.String,
	 *      org.eclipse.uml2.Type, java.lang.String[], org.eclipse.uml2.Type[])
	 */
	public Operation createOwnedOperation(String name, Type returnType,
			String[] parameterNames, Type[] parameterTypes) {
		return TypeOperations.createOwnedOperation(this, name, returnType,
			parameterNames, parameterTypes);
	}

	// <!-- end-custom-operations -->

} //DataTypeImpl
