

/**
 * <copyright>
 * </copyright>
 *
 * $Id: PropertyValueSpecificationImpl.java,v 1.1 2009-03-04 23:06:39 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Property;
import org.eclipse.uml2.PropertyValueSpecification;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.Type;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Value Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.PropertyValueSpecificationImpl#isAliased <em>Aliased</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PropertyValueSpecificationImpl#getProperty <em>Property</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyValueSpecificationImpl extends ValueSpecificationImpl implements PropertyValueSpecification {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #isAliased() <em>Aliased</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAliased()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALIASED_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isAliased() <em>Aliased</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAliased()
	 * @generated
	 * @ordered
	 */
	protected static final int ALIASED_EFLAG = 1 << 8;

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected Property property = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyValueSpecificationImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (PropertyValueSpecificationImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getPropertyValueSpecification();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAliased()
	{
		return (eFlags & ALIASED_EFLAG) != 0;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAliased(boolean newAliased)
	{

		boolean oldAliased = (eFlags & ALIASED_EFLAG) != 0;
		if (newAliased) eFlags |= ALIASED_EFLAG; else eFlags &= ~ALIASED_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PROPERTY_VALUE_SPECIFICATION__ALIASED, oldAliased, newAliased));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property getProperty()
	{
		if (property != null && property.eIsProxy())
		{
			Property oldProperty = property;
			property = (Property)eResolveProxy((InternalEObject)property);
			if (property != oldProperty)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.PROPERTY_VALUE_SPECIFICATION__PROPERTY, oldProperty, property));
			}
		}
		return property;
	}

	
	



	/**
	 * return a version of j_deleted, which considers all of the parents also
	 * @generated NOT
	 */
	@Override
	public boolean isThisDeleted() {
		return super.isThisDeleted() || getProperty() != null && getProperty().isThisDeleted();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property undeleted_getProperty()
	{
		Property temp = getProperty();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property basicGetProperty()
	{
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperty(Property newProperty)
	{

		Property oldProperty = property;
		property = newProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PROPERTY_VALUE_SPECIFICATION__PROPERTY, oldProperty, property));

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
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNING_PARAMETER, msgs);
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
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNING_PARAMETER, msgs);
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
				case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNING_PARAMETER:
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
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__UUID:
				return getUuid();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__NAME:
				return getName();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__VISIBILITY:
				return getVisibility();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__ALIASED:
				return isAliased() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__PROPERTY:
				if (resolve) return getProperty();
				return basicGetProperty();
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
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__NAME:
				setName((String)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__TYPE:
				setType((Type)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__ALIASED:
				setAliased(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__PROPERTY:
				setProperty((Property)newValue);
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
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__TYPE:
				setType((Type)null);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__ALIASED:
				setAliased(ALIASED_EDEFAULT);
				return;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__PROPERTY:
				setProperty((Property)null);
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
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNER:
				return basicGetOwner() != null;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__TYPE:
				return type != null;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__ALIASED:
				return ((eFlags & ALIASED_EFLAG) != 0) != ALIASED_EDEFAULT;
			case UML2Package.PROPERTY_VALUE_SPECIFICATION__PROPERTY:
				return property != null;
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
		result.append(" (aliased: "); //$NON-NLS-1$
		result.append((eFlags & ALIASED_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}


} //PropertyValueSpecificationImpl
