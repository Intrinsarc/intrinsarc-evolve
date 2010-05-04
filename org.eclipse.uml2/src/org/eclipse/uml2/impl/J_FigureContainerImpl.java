

/**
 * <copyright>
 * </copyright>
 *
 * $Id: J_FigureContainerImpl.java,v 1.1 2009-03-04 23:06:39 andrew Exp $
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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.*;
import org.eclipse.uml2.J_Figure;
import org.eclipse.uml2.J_FigureContainer;
import org.eclipse.uml2.J_Property;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;

import java.util.*;

import org.eclipse.emf.common.notify.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.*;
import org.eclipse.emf.ecore.util.*;
import org.eclipse.uml2.*;
import java.lang.Class;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JFigure Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.J_FigureContainerImpl#getFigures <em>Figures</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.J_FigureContainerImpl#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class J_FigureContainerImpl extends ElementImpl implements J_FigureContainer
{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

  /**
   * The cached value of the '{@link #getFigures() <em>Figures</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFigures()
   * @generated NOT
   * @ordered
   */
  protected ArrayList figures = null;

  /**
   * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProperties()
   * @generated NOT
   * @ordered
   */
  protected ArrayList properties = null;

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected J_FigureContainerImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (J_FigureContainerImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getJ_FigureContainer();
	}

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public ArrayList getFigures()
  {
    if (figures == null)
    {
      if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
      {
          // create the list lazily...
          figures = new ArrayList();
          return figures;
      }
      
      return new ArrayList();
    }      
    return figures;
  }

  
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public ArrayList settable_getFigures()
  {
    if (figures == null)
    {
      
    
      figures = new ArrayList();
    }
    return figures;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public ArrayList undeleted_getFigures()
  {
    
    ArrayList temp = new ArrayList();

    if (figures != null)
    {
      for (Object object : figures)
      {
        org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
        if (element.isThisDeleted())
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
	public J_Figure createFigures(EClass eClass) {
		J_Figure newFigures = (J_Figure) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.JFIGURE_CONTAINER__FIGURES, null, newFigures));
		}
		settable_getFigures().add(newFigures);
		return newFigures;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public J_Figure createFigures() {
		J_Figure newFigures = UML2Factory.eINSTANCE.createJ_Figure();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.JFIGURE_CONTAINER__FIGURES, null, newFigures));
		}
		settable_getFigures().add(newFigures);
		return newFigures;
	}

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public ArrayList getProperties()
  {
    if (properties == null)
    {
      if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
      {
          // create the list lazily...
          properties = new ArrayList();
          return properties;
      }
      
      return new ArrayList();
    }      
    return properties;
  }

  
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public ArrayList settable_getProperties()
  {
    if (properties == null)
    {
      
    
      properties = new ArrayList();
    }
    return properties;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public ArrayList undeleted_getProperties()
  {
    
    ArrayList temp = new ArrayList();

    if (properties != null)
    {
      for (Object object : properties)
      {
        org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
        if (element.getJ_deleted() <= 0)
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
	public J_Property createProperties(EClass eClass) {
		J_Property newProperties = (J_Property) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.JFIGURE_CONTAINER__PROPERTIES, null, newProperties));
		}
		settable_getProperties().add(newProperties);
		return newProperties;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public J_Property createProperties() {
		J_Property newProperties = UML2Factory.eINSTANCE.createJ_Property();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.JFIGURE_CONTAINER__PROPERTIES, null, newProperties));
		}
		settable_getProperties().add(newProperties);
		return newProperties;
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
				case UML2Package.JFIGURE_CONTAINER__EANNOTATIONS:
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs)
	{
		if (featureID >= 0)
		{
			switch (eDerivedStructuralFeatureID(featureID, baseClass))
			{
				case UML2Package.JFIGURE_CONTAINER__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.JFIGURE_CONTAINER__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.JFIGURE_CONTAINER__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.JFIGURE_CONTAINER__FIGURES:
					return ((InternalEList)getFigures()).basicRemove(otherEnd, msgs);
				case UML2Package.JFIGURE_CONTAINER__PROPERTIES:
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
  public Object eGet(EStructuralFeature eFeature, boolean resolve)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.JFIGURE_CONTAINER__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.JFIGURE_CONTAINER__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.JFIGURE_CONTAINER__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.JFIGURE_CONTAINER__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.JFIGURE_CONTAINER__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.JFIGURE_CONTAINER__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.JFIGURE_CONTAINER__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.JFIGURE_CONTAINER__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.JFIGURE_CONTAINER__UUID:
				return getUuid();
			case UML2Package.JFIGURE_CONTAINER__FIGURES:
				return getFigures();
			case UML2Package.JFIGURE_CONTAINER__PROPERTIES:
				return getProperties();
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
			case UML2Package.JFIGURE_CONTAINER__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.JFIGURE_CONTAINER__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.JFIGURE_CONTAINER__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.JFIGURE_CONTAINER__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.JFIGURE_CONTAINER__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.JFIGURE_CONTAINER__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.JFIGURE_CONTAINER__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.JFIGURE_CONTAINER__FIGURES:
				getFigures().clear();
				getFigures().addAll((Collection)newValue);
				return;
			case UML2Package.JFIGURE_CONTAINER__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection)newValue);
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
			case UML2Package.JFIGURE_CONTAINER__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.JFIGURE_CONTAINER__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.JFIGURE_CONTAINER__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.JFIGURE_CONTAINER__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.JFIGURE_CONTAINER__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.JFIGURE_CONTAINER__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.JFIGURE_CONTAINER__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.JFIGURE_CONTAINER__FIGURES:
				getFigures().clear();
				return;
			case UML2Package.JFIGURE_CONTAINER__PROPERTIES:
				getProperties().clear();
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
			case UML2Package.JFIGURE_CONTAINER__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.JFIGURE_CONTAINER__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.JFIGURE_CONTAINER__OWNER:
				return basicGetOwner() != null;
			case UML2Package.JFIGURE_CONTAINER__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.JFIGURE_CONTAINER__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.JFIGURE_CONTAINER__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.JFIGURE_CONTAINER__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.JFIGURE_CONTAINER__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.JFIGURE_CONTAINER__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.JFIGURE_CONTAINER__FIGURES:
				return figures != null && !figures.isEmpty();
			case UML2Package.JFIGURE_CONTAINER__PROPERTIES:
				return properties != null && !properties.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}


} //J_FigureContainerImpl
