

/**
 * <copyright>
 * </copyright>
 *
 * $Id: J_DiagramHolderImpl.java,v 1.1 2009-03-04 23:06:39 andrew Exp $
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

import org.eclipse.uml2.J_Diagram;
import org.eclipse.uml2.J_DiagramHolder;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JDiagram Holder</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.J_DiagramHolderImpl#getDiagram <em>Diagram</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.J_DiagramHolderImpl#getSaveTime <em>Save Time</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.J_DiagramHolderImpl#getSavedBy <em>Saved By</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class J_DiagramHolderImpl extends ElementImpl implements J_DiagramHolder {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getDiagram() <em>Diagram</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiagram()
	 * @generated
	 * @ordered
	 */
	protected J_Diagram diagram = null;

	/**
	 * The default value of the '{@link #getSaveTime() <em>Save Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSaveTime()
	 * @generated
	 * @ordered
	 */
	protected static final String SAVE_TIME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSaveTime() <em>Save Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSaveTime()
	 * @generated
	 * @ordered
	 */
	protected String saveTime = SAVE_TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSavedBy() <em>Saved By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSavedBy()
	 * @generated
	 * @ordered
	 */
	protected static final String SAVED_BY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSavedBy() <em>Saved By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSavedBy()
	 * @generated
	 * @ordered
	 */
	protected String savedBy = SAVED_BY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected J_DiagramHolderImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (J_DiagramHolderImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getJ_DiagramHolder();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public J_Diagram getDiagram() {
		return diagram;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public J_Diagram undeleted_getDiagram() {
		J_Diagram temp = getDiagram();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDiagram(J_Diagram newDiagram, NotificationChain msgs) {
		J_Diagram oldDiagram = diagram;
		diagram = newDiagram;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.JDIAGRAM_HOLDER__DIAGRAM, oldDiagram, newDiagram);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDiagram(J_Diagram newDiagram) {
		if (newDiagram != diagram) {
			NotificationChain msgs = null;
			if (diagram != null)
				msgs = ((InternalEObject)diagram).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.JDIAGRAM_HOLDER__DIAGRAM, null, msgs);
			if (newDiagram != null)
				msgs = ((InternalEObject)newDiagram).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UML2Package.JDIAGRAM_HOLDER__DIAGRAM, null, msgs);
			msgs = basicSetDiagram(newDiagram, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.JDIAGRAM_HOLDER__DIAGRAM, newDiagram, newDiagram));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public J_Diagram createDiagram(EClass eClass) {
		J_Diagram newDiagram = (J_Diagram) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.JDIAGRAM_HOLDER__DIAGRAM, null, newDiagram));
		}
		setDiagram(newDiagram);
		return newDiagram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public J_Diagram createDiagram() {
		J_Diagram newDiagram = UML2Factory.eINSTANCE.createJ_Diagram();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.JDIAGRAM_HOLDER__DIAGRAM, null, newDiagram));
		}
		setDiagram(newDiagram);
		return newDiagram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSaveTime() {
		return saveTime;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSaveTime(String newSaveTime) {
		newSaveTime = newSaveTime == null ? SAVE_TIME_EDEFAULT : newSaveTime;
		String oldSaveTime = saveTime;
		saveTime = newSaveTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.JDIAGRAM_HOLDER__SAVE_TIME, oldSaveTime, saveTime));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSavedBy() {
		return savedBy;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSavedBy(String newSavedBy) {
		newSavedBy = newSavedBy == null ? SAVED_BY_EDEFAULT : newSavedBy;
		String oldSavedBy = savedBy;
		savedBy = newSavedBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.JDIAGRAM_HOLDER__SAVED_BY, oldSavedBy, savedBy));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.JDIAGRAM_HOLDER__EANNOTATIONS:
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
				case UML2Package.JDIAGRAM_HOLDER__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.JDIAGRAM_HOLDER__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.JDIAGRAM_HOLDER__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.JDIAGRAM_HOLDER__DIAGRAM:
					return basicSetDiagram(null, msgs);
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
			case UML2Package.JDIAGRAM_HOLDER__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.JDIAGRAM_HOLDER__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.JDIAGRAM_HOLDER__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.JDIAGRAM_HOLDER__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.JDIAGRAM_HOLDER__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.JDIAGRAM_HOLDER__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.JDIAGRAM_HOLDER__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.JDIAGRAM_HOLDER__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.JDIAGRAM_HOLDER__UUID:
				return getUuid();
			case UML2Package.JDIAGRAM_HOLDER__DIAGRAM:
				return getDiagram();
			case UML2Package.JDIAGRAM_HOLDER__SAVE_TIME:
				return getSaveTime();
			case UML2Package.JDIAGRAM_HOLDER__SAVED_BY:
				return getSavedBy();
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
			case UML2Package.JDIAGRAM_HOLDER__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.JDIAGRAM_HOLDER__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.JDIAGRAM_HOLDER__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.JDIAGRAM_HOLDER__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.JDIAGRAM_HOLDER__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.JDIAGRAM_HOLDER__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.JDIAGRAM_HOLDER__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.JDIAGRAM_HOLDER__DIAGRAM:
				setDiagram((J_Diagram)newValue);
				return;
			case UML2Package.JDIAGRAM_HOLDER__SAVE_TIME:
				setSaveTime((String)newValue);
				return;
			case UML2Package.JDIAGRAM_HOLDER__SAVED_BY:
				setSavedBy((String)newValue);
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
			case UML2Package.JDIAGRAM_HOLDER__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.JDIAGRAM_HOLDER__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.JDIAGRAM_HOLDER__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.JDIAGRAM_HOLDER__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.JDIAGRAM_HOLDER__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.JDIAGRAM_HOLDER__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.JDIAGRAM_HOLDER__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.JDIAGRAM_HOLDER__DIAGRAM:
				setDiagram((J_Diagram)null);
				return;
			case UML2Package.JDIAGRAM_HOLDER__SAVE_TIME:
				setSaveTime(SAVE_TIME_EDEFAULT);
				return;
			case UML2Package.JDIAGRAM_HOLDER__SAVED_BY:
				setSavedBy(SAVED_BY_EDEFAULT);
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
			case UML2Package.JDIAGRAM_HOLDER__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.JDIAGRAM_HOLDER__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.JDIAGRAM_HOLDER__OWNER:
				return basicGetOwner() != null;
			case UML2Package.JDIAGRAM_HOLDER__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.JDIAGRAM_HOLDER__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.JDIAGRAM_HOLDER__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.JDIAGRAM_HOLDER__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.JDIAGRAM_HOLDER__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.JDIAGRAM_HOLDER__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.JDIAGRAM_HOLDER__DIAGRAM:
				return diagram != null;
			case UML2Package.JDIAGRAM_HOLDER__SAVE_TIME:
				return SAVE_TIME_EDEFAULT == null ? saveTime != null : !SAVE_TIME_EDEFAULT.equals(saveTime);
			case UML2Package.JDIAGRAM_HOLDER__SAVED_BY:
				return SAVED_BY_EDEFAULT == null ? savedBy != null : !SAVED_BY_EDEFAULT.equals(savedBy);
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
		result.append(" (saveTime: "); //$NON-NLS-1$
		result.append(saveTime);
		result.append(", savedBy: "); //$NON-NLS-1$
		result.append(savedBy);
		result.append(')');
		return result.toString();
	}


} //J_DiagramHolderImpl
