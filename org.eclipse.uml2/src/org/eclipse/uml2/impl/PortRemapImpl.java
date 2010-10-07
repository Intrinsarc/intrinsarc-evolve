

/**
 * <copyright>
 * </copyright>
 *
 * $Id: PortRemapImpl.java,v 1.2 2009-04-22 10:01:14 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMFOptions;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.uml2.Classifier;
import org.eclipse.uml2.DeltaReplacedConstituent;
import org.eclipse.uml2.Element;
import org.eclipse.uml2.Port;
import org.eclipse.uml2.PortRemap;
import org.eclipse.uml2.Property;
import org.eclipse.uml2.UML2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Port Remap</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.PortRemapImpl#getOriginalPort <em>Original Port</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PortRemapImpl#getNewPort <em>New Port</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PortRemapImpl extends ElementImpl implements PortRemap
{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getOriginalPort() <em>Original Port</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getOriginalPort()
	 * @generated
	 * @ordered
	 */
  protected Port originalPort = null;

	/**
	 * The cached value of the '{@link #getNewPort() <em>New Port</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getNewPort()
	 * @generated
	 * @ordered
	 */
  protected Port newPort = null;

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected PortRemapImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (PortRemapImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getPortRemap();
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Port getOriginalPort() {
		if (originalPort != null && originalPort.eIsProxy()) {
			Port oldOriginalPort = originalPort;
			originalPort = (Port)eResolveProxy((InternalEObject)originalPort);
			if (originalPort != oldOriginalPort) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.PORT_REMAP__ORIGINAL_PORT, oldOriginalPort, originalPort));
			}
		}
		return originalPort;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Port undeleted_getOriginalPort() {
		Port temp = getOriginalPort();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Port basicGetOriginalPort() {
		return originalPort;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setOriginalPort(Port newOriginalPort) {
		Port oldOriginalPort = originalPort;
		originalPort = newOriginalPort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PORT_REMAP__ORIGINAL_PORT, oldOriginalPort, originalPort));

	}


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Port getNewPort() {
		if (newPort != null && newPort.eIsProxy()) {
			Port oldNewPort = newPort;
			newPort = (Port)eResolveProxy((InternalEObject)newPort);
			if (newPort != oldNewPort) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.PORT_REMAP__NEW_PORT, oldNewPort, newPort));
			}
		}
		return newPort;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Port undeleted_getNewPort() {
		Port temp = getNewPort();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Port basicGetNewPort() {
		return newPort;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setNewPort(Port newNewPort) {
		Port oldNewPort = newPort;
		newPort = newNewPort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PORT_REMAP__NEW_PORT, oldNewPort, newPort));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.PORT_REMAP__EANNOTATIONS:
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
				case UML2Package.PORT_REMAP__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.PORT_REMAP__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.PORT_REMAP__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
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
			case UML2Package.PORT_REMAP__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.PORT_REMAP__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.PORT_REMAP__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.PORT_REMAP__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.PORT_REMAP__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.PORT_REMAP__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.PORT_REMAP__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.PORT_REMAP__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.PORT_REMAP__UUID:
				return getUuid();
			case UML2Package.PORT_REMAP__ORIGINAL_PORT:
				if (resolve) return getOriginalPort();
				return basicGetOriginalPort();
			case UML2Package.PORT_REMAP__NEW_PORT:
				if (resolve) return getNewPort();
				return basicGetNewPort();
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
			case UML2Package.PORT_REMAP__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.PORT_REMAP__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.PORT_REMAP__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.PORT_REMAP__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.PORT_REMAP__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.PORT_REMAP__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.PORT_REMAP__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.PORT_REMAP__ORIGINAL_PORT:
				setOriginalPort((Port)newValue);
				return;
			case UML2Package.PORT_REMAP__NEW_PORT:
				setNewPort((Port)newValue);
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
			case UML2Package.PORT_REMAP__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.PORT_REMAP__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.PORT_REMAP__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.PORT_REMAP__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.PORT_REMAP__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.PORT_REMAP__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.PORT_REMAP__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.PORT_REMAP__ORIGINAL_PORT:
				setOriginalPort((Port)null);
				return;
			case UML2Package.PORT_REMAP__NEW_PORT:
				setNewPort((Port)null);
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
			case UML2Package.PORT_REMAP__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.PORT_REMAP__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.PORT_REMAP__OWNER:
				return basicGetOwner() != null;
			case UML2Package.PORT_REMAP__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.PORT_REMAP__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.PORT_REMAP__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.PORT_REMAP__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.PORT_REMAP__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.PORT_REMAP__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.PORT_REMAP__ORIGINAL_PORT:
				return originalPort != null;
			case UML2Package.PORT_REMAP__NEW_PORT:
				return newPort != null;
		}
		return eDynamicIsSet(eFeature);
	}


  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */

  @Override
  public boolean isThisDeleted()
  {
    if (super.isThisDeleted())
      return true;
    
    // if this is not a replaced part, then there should be no remaps
    if (!(extractPart().getOwner() instanceof DeltaReplacedConstituent))
      return true;
    
    // if either original or new are deleted, we are deleted to
    if (originalPort == null || originalPort.getJ_deleted() > 0)
      return true;
    if (newPort == null || newPort.getJ_deleted() > 0)
      return true;
    
    // probabl should check that we actually have both ports available in the new part
    return false;
  }

  private Property extractPart()
  {
    return (Property) getOwner().getOwner().getOwner();
  }
  
  private Classifier extractClassifier(Port port)
  {
    Element owner = port.getOwner();
    if (owner instanceof DeltaReplacedConstituent)
      owner = owner.getOwner();
    return (Classifier) owner;
  }
} //PortRemapImpl
