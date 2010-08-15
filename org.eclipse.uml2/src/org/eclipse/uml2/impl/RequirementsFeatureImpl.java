

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.DeltaDeletedRequirementsFeatureLink;
import org.eclipse.uml2.DeltaReplacedRequirementsFeatureLink;
import org.eclipse.uml2.RequirementsFeature;
import org.eclipse.uml2.RequirementsFeatureLink;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Requirements Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.RequirementsFeatureImpl#getSubfeatures <em>Subfeatures</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.RequirementsFeatureImpl#getDeltaReplacedSubfeatures <em>Delta Replaced Subfeatures</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.RequirementsFeatureImpl#getDeltaDeletedSubfeatures <em>Delta Deleted Subfeatures</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RequirementsFeatureImpl extends TypeImpl implements RequirementsFeature {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getSubfeatures() <em>Subfeatures</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubfeatures()
	 * @generated
	 * @ordered
	 */
	protected EList subfeatures = null;

	/**
	 * The cached value of the '{@link #getDeltaReplacedSubfeatures() <em>Delta Replaced Subfeatures</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltaReplacedSubfeatures()
	 * @generated
	 * @ordered
	 */
	protected EList deltaReplacedSubfeatures = null;

	/**
	 * The cached value of the '{@link #getDeltaDeletedSubfeatures() <em>Delta Deleted Subfeatures</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltaDeletedSubfeatures()
	 * @generated
	 * @ordered
	 */
	protected EList deltaDeletedSubfeatures = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequirementsFeatureImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (RequirementsFeatureImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getRequirementsFeature();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getSubfeatures()
	{
		if (subfeatures == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		subfeatures = new com.intrinsarc.emflist.PersistentEList(RequirementsFeatureLink.class, this, UML2Package.REQUIREMENTS_FEATURE__SUBFEATURES);
			 		return subfeatures;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(RequirementsFeatureLink.class, this, UML2Package.REQUIREMENTS_FEATURE__SUBFEATURES);
		}      
		return subfeatures;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getSubfeatures()
	{
		if (subfeatures == null)
		{
			
		
			subfeatures = new com.intrinsarc.emflist.PersistentEList(RequirementsFeatureLink.class, this, UML2Package.REQUIREMENTS_FEATURE__SUBFEATURES);
		}
		return subfeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getSubfeatures()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (subfeatures != null)
		{
			for (Object object : subfeatures)
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
	public RequirementsFeatureLink createSubfeatures(EClass eClass) {
		RequirementsFeatureLink newSubfeatures = (RequirementsFeatureLink) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.REQUIREMENTS_FEATURE__SUBFEATURES, null, newSubfeatures));
		}
		settable_getSubfeatures().add(newSubfeatures);
		return newSubfeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequirementsFeatureLink createSubfeatures() {
		RequirementsFeatureLink newSubfeatures = UML2Factory.eINSTANCE.createRequirementsFeatureLink();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.REQUIREMENTS_FEATURE__SUBFEATURES, null, newSubfeatures));
		}
		settable_getSubfeatures().add(newSubfeatures);
		return newSubfeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaReplacedSubfeatures()
	{
		if (deltaReplacedSubfeatures == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		deltaReplacedSubfeatures = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedRequirementsFeatureLink.class, this, UML2Package.REQUIREMENTS_FEATURE__DELTA_REPLACED_SUBFEATURES);
			 		return deltaReplacedSubfeatures;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaReplacedRequirementsFeatureLink.class, this, UML2Package.REQUIREMENTS_FEATURE__DELTA_REPLACED_SUBFEATURES);
		}      
		return deltaReplacedSubfeatures;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaReplacedSubfeatures()
	{
		if (deltaReplacedSubfeatures == null)
		{
			
		
			deltaReplacedSubfeatures = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedRequirementsFeatureLink.class, this, UML2Package.REQUIREMENTS_FEATURE__DELTA_REPLACED_SUBFEATURES);
		}
		return deltaReplacedSubfeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaReplacedSubfeatures()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaReplacedSubfeatures != null)
		{
			for (Object object : deltaReplacedSubfeatures)
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
	public DeltaReplacedRequirementsFeatureLink createDeltaReplacedSubfeatures(EClass eClass) {
		DeltaReplacedRequirementsFeatureLink newDeltaReplacedSubfeatures = (DeltaReplacedRequirementsFeatureLink) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.REQUIREMENTS_FEATURE__DELTA_REPLACED_SUBFEATURES, null, newDeltaReplacedSubfeatures));
		}
		settable_getDeltaReplacedSubfeatures().add(newDeltaReplacedSubfeatures);
		return newDeltaReplacedSubfeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaReplacedRequirementsFeatureLink createDeltaReplacedSubfeatures() {
		DeltaReplacedRequirementsFeatureLink newDeltaReplacedSubfeatures = UML2Factory.eINSTANCE.createDeltaReplacedRequirementsFeatureLink();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.REQUIREMENTS_FEATURE__DELTA_REPLACED_SUBFEATURES, null, newDeltaReplacedSubfeatures));
		}
		settable_getDeltaReplacedSubfeatures().add(newDeltaReplacedSubfeatures);
		return newDeltaReplacedSubfeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaDeletedSubfeatures()
	{
		if (deltaDeletedSubfeatures == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		deltaDeletedSubfeatures = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedRequirementsFeatureLink.class, this, UML2Package.REQUIREMENTS_FEATURE__DELTA_DELETED_SUBFEATURES);
			 		return deltaDeletedSubfeatures;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaDeletedRequirementsFeatureLink.class, this, UML2Package.REQUIREMENTS_FEATURE__DELTA_DELETED_SUBFEATURES);
		}      
		return deltaDeletedSubfeatures;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaDeletedSubfeatures()
	{
		if (deltaDeletedSubfeatures == null)
		{
			
		
			deltaDeletedSubfeatures = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedRequirementsFeatureLink.class, this, UML2Package.REQUIREMENTS_FEATURE__DELTA_DELETED_SUBFEATURES);
		}
		return deltaDeletedSubfeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaDeletedSubfeatures()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaDeletedSubfeatures != null)
		{
			for (Object object : deltaDeletedSubfeatures)
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
	public DeltaDeletedRequirementsFeatureLink createDeltaDeletedSubfeatures(EClass eClass) {
		DeltaDeletedRequirementsFeatureLink newDeltaDeletedSubfeatures = (DeltaDeletedRequirementsFeatureLink) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.REQUIREMENTS_FEATURE__DELTA_DELETED_SUBFEATURES, null, newDeltaDeletedSubfeatures));
		}
		settable_getDeltaDeletedSubfeatures().add(newDeltaDeletedSubfeatures);
		return newDeltaDeletedSubfeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaDeletedRequirementsFeatureLink createDeltaDeletedSubfeatures() {
		DeltaDeletedRequirementsFeatureLink newDeltaDeletedSubfeatures = UML2Factory.eINSTANCE.createDeltaDeletedRequirementsFeatureLink();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.REQUIREMENTS_FEATURE__DELTA_DELETED_SUBFEATURES, null, newDeltaDeletedSubfeatures));
		}
		settable_getDeltaDeletedSubfeatures().add(newDeltaDeletedSubfeatures);
		return newDeltaDeletedSubfeatures;
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
				case UML2Package.REQUIREMENTS_FEATURE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.REQUIREMENTS_FEATURE__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.REQUIREMENTS_FEATURE__OWNING_PARAMETER, msgs);
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
				case UML2Package.REQUIREMENTS_FEATURE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.REQUIREMENTS_FEATURE__OWNING_PARAMETER, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__SUBFEATURES:
					return ((InternalEList)getSubfeatures()).basicRemove(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__DELTA_REPLACED_SUBFEATURES:
					return ((InternalEList)getDeltaReplacedSubfeatures()).basicRemove(otherEnd, msgs);
				case UML2Package.REQUIREMENTS_FEATURE__DELTA_DELETED_SUBFEATURES:
					return ((InternalEList)getDeltaDeletedSubfeatures()).basicRemove(otherEnd, msgs);
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
				case UML2Package.REQUIREMENTS_FEATURE__OWNING_PARAMETER:
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
			case UML2Package.REQUIREMENTS_FEATURE__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.REQUIREMENTS_FEATURE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.REQUIREMENTS_FEATURE__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.REQUIREMENTS_FEATURE__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.REQUIREMENTS_FEATURE__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.REQUIREMENTS_FEATURE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.REQUIREMENTS_FEATURE__UUID:
				return getUuid();
			case UML2Package.REQUIREMENTS_FEATURE__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.REQUIREMENTS_FEATURE__NAME:
				return getName();
			case UML2Package.REQUIREMENTS_FEATURE__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.REQUIREMENTS_FEATURE__VISIBILITY:
				return getVisibility();
			case UML2Package.REQUIREMENTS_FEATURE__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.REQUIREMENTS_FEATURE__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.REQUIREMENTS_FEATURE__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.REQUIREMENTS_FEATURE__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.REQUIREMENTS_FEATURE__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.REQUIREMENTS_FEATURE__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.REQUIREMENTS_FEATURE__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.REQUIREMENTS_FEATURE__PACKAGE:
				if (resolve) return getPackage();
				return basicGetPackage();
			case UML2Package.REQUIREMENTS_FEATURE__IS_RETIRED:
				return isRetired() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.REQUIREMENTS_FEATURE__SUBFEATURES:
				return getSubfeatures();
			case UML2Package.REQUIREMENTS_FEATURE__DELTA_REPLACED_SUBFEATURES:
				return getDeltaReplacedSubfeatures();
			case UML2Package.REQUIREMENTS_FEATURE__DELTA_DELETED_SUBFEATURES:
				return getDeltaDeletedSubfeatures();
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
			case UML2Package.REQUIREMENTS_FEATURE__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.REQUIREMENTS_FEATURE__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__NAME:
				setName((String)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__IS_RETIRED:
				setIsRetired(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.REQUIREMENTS_FEATURE__SUBFEATURES:
				getSubfeatures().clear();
				getSubfeatures().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__DELTA_REPLACED_SUBFEATURES:
				getDeltaReplacedSubfeatures().clear();
				getDeltaReplacedSubfeatures().addAll((Collection)newValue);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__DELTA_DELETED_SUBFEATURES:
				getDeltaDeletedSubfeatures().clear();
				getDeltaDeletedSubfeatures().addAll((Collection)newValue);
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
			case UML2Package.REQUIREMENTS_FEATURE__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__IS_RETIRED:
				setIsRetired(IS_RETIRED_EDEFAULT);
				return;
			case UML2Package.REQUIREMENTS_FEATURE__SUBFEATURES:
				getSubfeatures().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE__DELTA_REPLACED_SUBFEATURES:
				getDeltaReplacedSubfeatures().clear();
				return;
			case UML2Package.REQUIREMENTS_FEATURE__DELTA_DELETED_SUBFEATURES:
				getDeltaDeletedSubfeatures().clear();
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
			case UML2Package.REQUIREMENTS_FEATURE__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE__OWNER:
				return basicGetOwner() != null;
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.REQUIREMENTS_FEATURE__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.REQUIREMENTS_FEATURE__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.REQUIREMENTS_FEATURE__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.REQUIREMENTS_FEATURE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.REQUIREMENTS_FEATURE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.REQUIREMENTS_FEATURE__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.REQUIREMENTS_FEATURE__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.REQUIREMENTS_FEATURE__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.REQUIREMENTS_FEATURE__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.REQUIREMENTS_FEATURE__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.REQUIREMENTS_FEATURE__PACKAGE:
				return basicGetPackage() != null;
			case UML2Package.REQUIREMENTS_FEATURE__IS_RETIRED:
				return ((eFlags & IS_RETIRED_EFLAG) != 0) != IS_RETIRED_EDEFAULT;
			case UML2Package.REQUIREMENTS_FEATURE__SUBFEATURES:
				return subfeatures != null && !subfeatures.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE__DELTA_REPLACED_SUBFEATURES:
				return deltaReplacedSubfeatures != null && !deltaReplacedSubfeatures.isEmpty();
			case UML2Package.REQUIREMENTS_FEATURE__DELTA_DELETED_SUBFEATURES:
				return deltaDeletedSubfeatures != null && !deltaDeletedSubfeatures.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}


} //RequirementsFeatureImpl
