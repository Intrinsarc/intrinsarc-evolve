

/**
 * <copyright>
 * </copyright>
 *
 * $Id: J_DiagramImpl.java,v 1.1 2009-03-04 23:06:39 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import java.util.Map;

import java.util.Iterator;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.J_Diagram;
import org.eclipse.uml2.J_Figure;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;

import org.eclipse.uml2.common.util.CacheAdapter;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JDiagram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.J_DiagramImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.J_DiagramImpl#getLastFigureId <em>Last Figure Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class J_DiagramImpl extends J_FigureContainerImpl implements J_Diagram {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastFigureId() <em>Last Figure Id</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getLastFigureId()
	 * @generated
	 * @ordered
	 */
  protected static final int LAST_FIGURE_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLastFigureId() <em>Last Figure Id</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getLastFigureId()
	 * @generated
	 * @ordered
	 */
  protected int lastFigureId = LAST_FIGURE_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected J_DiagramImpl() {
		super();
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (J_DiagramImpl.class.equals(getClass()) && org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getJ_Diagram();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		newName = newName == null ? NAME_EDEFAULT : newName;
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.JDIAGRAM__NAME, oldName, name));

	}


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public int getLastFigureId() {
		return lastFigureId;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setLastFigureId(int newLastFigureId) {
		int oldLastFigureId = lastFigureId;
		lastFigureId = newLastFigureId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.JDIAGRAM__LAST_FIGURE_ID, oldLastFigureId, lastFigureId));

	}


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.JDIAGRAM__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
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
				case UML2Package.JDIAGRAM__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.JDIAGRAM__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.JDIAGRAM__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.JDIAGRAM__FIGURES:
					return ((InternalEList)getFigures()).basicRemove(otherEnd, msgs);
				case UML2Package.JDIAGRAM__PROPERTIES:
					return ((InternalEList)getProperties()).basicRemove(otherEnd, msgs);
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.JDIAGRAM__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.JDIAGRAM__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.JDIAGRAM__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.JDIAGRAM__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.JDIAGRAM__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.JDIAGRAM__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.JDIAGRAM__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.JDIAGRAM__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.JDIAGRAM__UUID:
				return getUuid();
			case UML2Package.JDIAGRAM__FIGURES:
				return getFigures();
			case UML2Package.JDIAGRAM__PROPERTIES:
				return getProperties();
			case UML2Package.JDIAGRAM__NAME:
				return getName();
			case UML2Package.JDIAGRAM__LAST_FIGURE_ID:
				return new Integer(getLastFigureId());
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
			case UML2Package.JDIAGRAM__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.JDIAGRAM__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.JDIAGRAM__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.JDIAGRAM__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.JDIAGRAM__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.JDIAGRAM__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.JDIAGRAM__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.JDIAGRAM__FIGURES:
				getFigures().clear();
				getFigures().addAll((Collection)newValue);
				return;
			case UML2Package.JDIAGRAM__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection)newValue);
				return;
			case UML2Package.JDIAGRAM__NAME:
				setName((String)newValue);
				return;
			case UML2Package.JDIAGRAM__LAST_FIGURE_ID:
				setLastFigureId(((Integer)newValue).intValue());
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
			case UML2Package.JDIAGRAM__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.JDIAGRAM__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.JDIAGRAM__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.JDIAGRAM__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.JDIAGRAM__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.JDIAGRAM__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.JDIAGRAM__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.JDIAGRAM__FIGURES:
				getFigures().clear();
				return;
			case UML2Package.JDIAGRAM__PROPERTIES:
				getProperties().clear();
				return;
			case UML2Package.JDIAGRAM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.JDIAGRAM__LAST_FIGURE_ID:
				setLastFigureId(LAST_FIGURE_ID_EDEFAULT);
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
			case UML2Package.JDIAGRAM__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.JDIAGRAM__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.JDIAGRAM__OWNER:
				return basicGetOwner() != null;
			case UML2Package.JDIAGRAM__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.JDIAGRAM__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.JDIAGRAM__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.JDIAGRAM__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.JDIAGRAM__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.JDIAGRAM__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.JDIAGRAM__FIGURES:
				return figures != null && !figures.isEmpty();
			case UML2Package.JDIAGRAM__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case UML2Package.JDIAGRAM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.JDIAGRAM__LAST_FIGURE_ID:
				return lastFigureId != LAST_FIGURE_ID_EDEFAULT;
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", lastFigureId: "); //$NON-NLS-1$
		result.append(lastFigureId);
		result.append(')');
		return result.toString();
	}


} //J_DiagramImpl
