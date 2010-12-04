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
 * $Id: ElementImpl.java,v 1.1 2009-03-04 23:06:35 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.lang.reflect.Method;

import java.util.Collection;
//import java.util.Iterator;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

//import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EModelElementImpl;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.AppliedBasicStereotypeValue;
import org.eclipse.uml2.Comment;
import org.eclipse.uml2.Element;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;

import org.eclipse.uml2.*;

import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.UnionEObjectEList;

import org.eclipse.uml2.Model;
import org.eclipse.uml2.Stereotype;

import org.eclipse.uml2.internal.operation.ElementOperations;
import org.eclipse.uml2.internal.operation.StereotypeOperations;

import com.intrinsarc.notifications.*;

import java.lang.Class;
import java.util.Iterator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.ElementImpl#getOwnedComments <em>Owned Comment</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ElementImpl#getJ_deleted <em>Jdeleted</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ElementImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ElementImpl#getAppliedBasicStereotypes <em>Applied Basic Stereotypes</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ElementImpl#getAppliedBasicStereotypeValues <em>Applied Basic Stereotype Values</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ElementImpl#getUuid <em>Uuid</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ElementImpl extends EModelElementImpl implements Element {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getOwnedComments() <em>Owned Comment</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedComments()
	 * @generated
	 * @ordered
	 */
	protected EList ownedComment = null;

	/**
	 * The default value of the '{@link #getJ_deleted() <em>Jdeleted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJ_deleted()
	 * @generated
	 * @ordered
	 */
	protected static final int JDELETED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getJ_deleted() <em>Jdeleted</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getJ_deleted()
	 * @generated
	 * @ordered
	 */
  protected int j_deleted = JDELETED_EDEFAULT;

	/**
	 * The default value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected static final String DOCUMENTATION_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected String documentation = DOCUMENTATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAppliedBasicStereotypes() <em>Applied Basic Stereotypes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppliedBasicStereotypes()
	 * @generated
	 * @ordered
	 */
	protected EList appliedBasicStereotypes = null;

	/**
	 * The cached value of the '{@link #getAppliedBasicStereotypeValues() <em>Applied Basic Stereotype Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppliedBasicStereotypeValues()
	 * @generated
	 * @ordered
	 */
	protected EList appliedBasicStereotypeValues = null;

	/**
	 * The default value of the '{@link #getUuid() <em>Uuid</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getUuid()
	 * @generated
	 * @ordered
	 */
  protected static final String UUID_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getUuid() <em>Uuid</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getUuid()
	 * @generated
	 * @ordered
	 */
  protected String uuid = UUID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated NOT
     */
	protected ElementImpl() {
        super();
        
        // allocate a UUID
        if (GlobalUUIDGenerator.GlOBAL_UUID_GENERATOR != null)
          uuid = GlobalUUIDGenerator.GlOBAL_UUID_GENERATOR.generateUUID(this);
        
        CacheAdapter.INSTANCE.adapt(this);
        eAdapters().add(GlobalNotifier.getSingleton());
    }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getElement();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedElements() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			EList ownedElement = (EList) cache.get(eResource(), this, UML2Package.eINSTANCE.getElement_OwnedElement());
			if (ownedElement == null) {
				EList union = getOwnedElementsHelper(new UniqueEList());
				cache.put(eResource(), this, UML2Package.eINSTANCE.getElement_OwnedElement(), ownedElement = new UnionEObjectEList(this, UML2Package.eINSTANCE.getElement_OwnedElement(), union.size(), union.toArray()));
			}
			return ownedElement;
		}
		EList union = getOwnedElementsHelper(new UniqueEList());
		return new UnionEObjectEList(this, UML2Package.eINSTANCE.getElement_OwnedElement(), union.size(), union.toArray());
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element getOwner() {
		Element owner = basicGetOwner();
		return owner == null ? null : (Element)eResolveProxy((InternalEObject)owner);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Element basicGetOwner() {
		return eContainer instanceof Element ? (Element) eContainer : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedComments() {
		if (ownedComment == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		ownedComment = new com.intrinsarc.emflist.PersistentEList(Comment.class, this, UML2Package.ELEMENT__OWNED_COMMENT);
			 		return ownedComment;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Comment.class, this, UML2Package.ELEMENT__OWNED_COMMENT);
		}      
		return ownedComment;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedComments() {
		if (ownedComment == null) {
			ownedComment = new com.intrinsarc.emflist.PersistentEList(Comment.class, this, UML2Package.ELEMENT__OWNED_COMMENT);
		}
		return ownedComment;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedComments() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedComment != null) {
			for (Object object : ownedComment) {
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
	 * @generated NOT
	 * @deprecated Use #createOwnedComment() instead.
	 */
	public Comment createOwnedComment(EClass eClass) {
		Comment newOwnedComment = (Comment) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.ELEMENT__OWNED_COMMENT, null, newOwnedComment));
		}
		getOwnedComments().add(newOwnedComment);
		return newOwnedComment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Comment createOwnedComment() {
		Comment newOwnedComment = UML2Factory.eINSTANCE.createComment();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.ELEMENT__OWNED_COMMENT, null, newOwnedComment));
		}
		settable_getOwnedComments().add(newOwnedComment);
		return newOwnedComment;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public int getJ_deleted() {
		return j_deleted;
	}

	
	




	/**
	 * return a version of j_deleted, which considers all of the parents also
	 * @generated NOT
	 */
	public boolean isThisDeleted()
	{
		// is this itself deleted?
		boolean deleted = getJ_deleted() > 0;
		if (deleted)
			return true;
		
		// is it's parent (or recursively upwards) deleted
		Element owner = getOwner();
		if (owner != null && owner.isThisDeleted())
			return true;

		// if this is a directed relationship, ask each element in turn if they are deleted
		if (this instanceof DirectedRelationship)
		{
			DirectedRelationship d = (DirectedRelationship) this;
			if (isDeleted(d.getSources()) || isDeleted(d.getTargets(), d.getSources()))
				return true;
		}
		
		// if we are here, we didn't get deleted
		return false;
	}


	/** see if all of the targets, that aren't also sources, are deleted
	 * 
	 * @param targets
	 * @param sources
	 * @return
	 */
	private boolean isDeleted(EList targets, EList sources)
	{
		for (Object obj : targets)
		{
			Element element = (Element) obj;
			if (!sources.contains(obj) && !element.isThisDeleted())
				return false;
		}
		return true;
	}

	/**
	 * this checks the list of targets to see if any are deleted
	 * @param targets
	 * @return
	 */
	private boolean isDeleted(EList targets)
	{
		for (Object obj : targets)
		{
			Element element = (Element) obj;
			if (!element.isThisDeleted())
				return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setJ_deleted(int newJ_deleted) {
		int oldJ_deleted = j_deleted;
		j_deleted = newJ_deleted;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.ELEMENT__JDELETED, oldJ_deleted, j_deleted));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDocumentation() {
		return documentation;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentation(String newDocumentation) {
		newDocumentation = newDocumentation == null ? DOCUMENTATION_EDEFAULT : newDocumentation;
		String oldDocumentation = documentation;
		documentation = newDocumentation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.ELEMENT__DOCUMENTATION, oldDocumentation, documentation));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAppliedBasicStereotypes() {
		if (appliedBasicStereotypes == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		appliedBasicStereotypes = new com.intrinsarc.emflist.PersistentEList(Stereotype.class, this, UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPES);
			 		return appliedBasicStereotypes;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Stereotype.class, this, UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPES);
		}      
		return appliedBasicStereotypes;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getAppliedBasicStereotypes() {
		if (appliedBasicStereotypes == null) {
			appliedBasicStereotypes = new com.intrinsarc.emflist.PersistentEList(Stereotype.class, this, UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPES);
		}
		return appliedBasicStereotypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getAppliedBasicStereotypes() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (appliedBasicStereotypes != null) {
			for (Object object : appliedBasicStereotypes) {
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
	public Stereotype getAppliedBasicStereotypes(String name) {
		for (Iterator i = getAppliedBasicStereotypes().iterator(); i.hasNext(); ) {
			Stereotype appliedBasicStereotypes = (Stereotype) i.next();
			if (name.equals(appliedBasicStereotypes.getName())) {
				return appliedBasicStereotypes;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAppliedBasicStereotypeValues() {
		if (appliedBasicStereotypeValues == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		appliedBasicStereotypeValues = new com.intrinsarc.emflist.PersistentEList(AppliedBasicStereotypeValue.class, this, UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES);
			 		return appliedBasicStereotypeValues;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(AppliedBasicStereotypeValue.class, this, UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES);
		}      
		return appliedBasicStereotypeValues;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getAppliedBasicStereotypeValues() {
		if (appliedBasicStereotypeValues == null) {
			appliedBasicStereotypeValues = new com.intrinsarc.emflist.PersistentEList(AppliedBasicStereotypeValue.class, this, UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES);
		}
		return appliedBasicStereotypeValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getAppliedBasicStereotypeValues() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (appliedBasicStereotypeValues != null) {
			for (Object object : appliedBasicStereotypeValues) {
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
	public AppliedBasicStereotypeValue createAppliedBasicStereotypeValues(EClass eClass) {
		AppliedBasicStereotypeValue newAppliedBasicStereotypeValues = (AppliedBasicStereotypeValue) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES, null, newAppliedBasicStereotypeValues));
		}
		settable_getAppliedBasicStereotypeValues().add(newAppliedBasicStereotypeValues);
		return newAppliedBasicStereotypeValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AppliedBasicStereotypeValue createAppliedBasicStereotypeValues() {
		AppliedBasicStereotypeValue newAppliedBasicStereotypeValues = UML2Factory.eINSTANCE.createAppliedBasicStereotypeValue();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES, null, newAppliedBasicStereotypeValues));
		}
		settable_getAppliedBasicStereotypeValues().add(newAppliedBasicStereotypeValues);
		return newAppliedBasicStereotypeValues;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getUuid() {
		return uuid;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setUuid(String newUuid) {
		newUuid = newUuid == null ? UUID_EDEFAULT : newUuid;
		String oldUuid = uuid;
		uuid = newUuid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.ELEMENT__UUID, oldUuid, uuid));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNotOwnSelf(DiagnosticChain diagnostics, Map context) {
		return ElementOperations.validateNotOwnSelf(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateHasOwner(DiagnosticChain diagnostics, Map context) {
		return ElementOperations.validateHasOwner(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Set allOwnedElements() {
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Set result = (Set) cache.get(eResource(), this, UML2Package.eINSTANCE.getElement().getEOperations().get(2));
			if (result == null) {
				cache.put(eResource(), this, UML2Package.eINSTANCE.getElement().getEOperations().get(2), result = ElementOperations.allOwnedElements(this));
			}
			return result;
		}
		return ElementOperations.allOwnedElements(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean mustBeOwned() {
		return ElementOperations.mustBeOwned(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.ELEMENT__EANNOTATIONS:
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
				case UML2Package.ELEMENT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.ELEMENT__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
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
			case UML2Package.ELEMENT__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.ELEMENT__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.ELEMENT__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.ELEMENT__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.ELEMENT__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.ELEMENT__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.ELEMENT__UUID:
				return getUuid();
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
			case UML2Package.ELEMENT__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.ELEMENT__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.ELEMENT__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.ELEMENT__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.ELEMENT__UUID:
				setUuid((String)newValue);
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
			case UML2Package.ELEMENT__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.ELEMENT__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.ELEMENT__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.ELEMENT__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.ELEMENT__UUID:
				setUuid(UUID_EDEFAULT);
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
			case UML2Package.ELEMENT__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.ELEMENT__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.ELEMENT__OWNER:
				return basicGetOwner() != null;
			case UML2Package.ELEMENT__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.ELEMENT__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.ELEMENT__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.ELEMENT__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
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
		result.append(" (j_deleted: "); //$NON-NLS-1$
		result.append(j_deleted);
		result.append(", documentation: "); //$NON-NLS-1$
		result.append(documentation);
		result.append(", uuid: "); //$NON-NLS-1$
		result.append(uuid);
		result.append(')');
		return result.toString();
	}


	/**
	 * Retrieves the cache adapter for this '<em><b>Element</b></em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The cache adapter for this '<em><b>Element</b></em>'.
	 * @generated NOT
	 */
	protected CacheAdapter getCacheAdapter() {
		return CacheAdapter.INSTANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedElementsHelper(EList ownedElement) {
		if (eIsSet(UML2Package.eINSTANCE.getElement_OwnedComment())) {
			ownedElement.addAll(getOwnedComments());
		}
		return ownedElement;
	}

	// <!-- begin-custom-operations -->

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.ecore.InternalEObject#eSetResource(org.eclipse.emf.ecore.resource.Resource.Internal,
	 *      org.eclipse.emf.common.notify.NotificationChain)
	 */
	public NotificationChain eSetResource(Resource.Internal resource,
			NotificationChain notifications) {
		CacheAdapter.INSTANCE.adapt(resource);

		return super.eSetResource(resource, notifications);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#createEAnnotation(String)
	 */
	public EAnnotation createEAnnotation(String source) {
		EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();

		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0,
				EcorePackage.EMODEL_ELEMENT__EANNOTATIONS, null, eAnnotation));
		}

		eAnnotation.setSource(source);
		eAnnotation.setEModelElement(this);

		return eAnnotation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#apply(org.eclipse.uml2.Stereotype)
	 */
	public void apply(Stereotype stereotype) {
		StereotypeOperations.apply(stereotype, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#getApplicableStereotype(java.lang.String)
	 */
	public Stereotype getApplicableStereotype(String qualifiedStereotypeName) {
		return StereotypeOperations.getApplicableStereotype(this,
			qualifiedStereotypeName);
	}

	private static Method GET_APPLICABLE_STEREOTYPES = null;

	static {
		try {
			GET_APPLICABLE_STEREOTYPES = ElementImpl.class.getMethod(
				"getApplicableStereotypes", null); //$NON-NLS-1$
		} catch (Exception e) {
			// ignore
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#getApplicableStereotypes()
	 */
	public Set getApplicableStereotypes() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			Set result = (Set) cache.get(this, GET_APPLICABLE_STEREOTYPES);

			if (result == null) {
				cache.put(this, GET_APPLICABLE_STEREOTYPES,
					result = Collections.unmodifiableSet(StereotypeOperations
						.getApplicableStereotypes(this)));
			}

			return result;
		}

		return Collections.unmodifiableSet(StereotypeOperations
			.getApplicableStereotypes(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#getAppliedStereotype(java.lang.String)
	 */
	public Stereotype getAppliedStereotype(String qualifiedStereotypeName) {
		return StereotypeOperations.getAppliedStereotype(this,
			qualifiedStereotypeName);
	}

	private static Method GET_APPLIED_STEREOTYPES = null;

	static {
		try {
			GET_APPLIED_STEREOTYPES = ElementImpl.class.getMethod(
				"getAppliedStereotypes", null); //$NON-NLS-1$
		} catch (Exception e) {
			// ignore
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#getAppliedStereotypes()
	 */
	public Set getAppliedStereotypes() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			Set result = (Set) cache.get(this, GET_APPLIED_STEREOTYPES);

			if (result == null) {
				cache.put(this, GET_APPLIED_STEREOTYPES, result = Collections
					.unmodifiableSet(StereotypeOperations
						.getAppliedStereotypes(this)));
			}

			return result;
		}

		return Collections.unmodifiableSet(StereotypeOperations
			.getAppliedStereotypes(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#getModel()
	 */
	public Model getModel() {
		return ElementOperations.getModel(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#getNearestPackage()
	 */
	public org.eclipse.uml2.Package getNearestPackage() {
		return ElementOperations.getNearestPackage(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#getValue(org.eclipse.uml2.Stereotype,
	 *      java.lang.String)
	 */
	public Object getValue(Stereotype stereotype, String propertyName) {
		return StereotypeOperations.getValue(stereotype, this, propertyName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#isApplied(org.eclipse.uml2.Stereotype)
	 */
	public boolean isApplied(Stereotype stereotype) {
		return StereotypeOperations.isApplied(stereotype, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#isRequired(org.eclipse.uml2.Stereotype)
	 */
	public boolean isRequired(Stereotype stereotype) {
		return StereotypeOperations.isRequired(stereotype, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#setValue(org.eclipse.uml2.Stereotype,
	 *      java.lang.String, java.lang.Object)
	 */
	public void setValue(Stereotype stereotype, String propertyName,
			Object value) {
		StereotypeOperations.setValue(stereotype, this, propertyName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#hasValue(org.eclipse.uml2.Stereotype,
	 *      java.lang.String)
	 */
	public boolean hasValue(Stereotype stereotype, String propertyName) {
		return StereotypeOperations.hasValue(stereotype, this, propertyName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#unapply(org.eclipse.uml2.Stereotype)
	 */
	public void unapply(Stereotype stereotype) {
		StereotypeOperations.unapply(stereotype, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#destroy()
	 */
	public void destroy() {
		ElementOperations.destroy(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#getAppliedVersion(org.eclipse.uml2.Stereotype)
	 */
	public String getAppliedVersion(Stereotype stereotype) {
		return StereotypeOperations.getAppliedVersion(stereotype, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#addKeyword(java.lang.String)
	 */
	public void addKeyword(String keyword) {
		ElementOperations.addKeyword(this, keyword);
	}

	private static Method GET_KEYWORDS = null;

	static {
		try {
			GET_KEYWORDS = ElementImpl.class.getMethod("getKeywords", null); //$NON-NLS-1$
		} catch (Exception e) {
			// ignore
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#getKeywords()
	 */
	public Set getKeywords() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			Set result = (Set) cache.get(eResource(), this, GET_KEYWORDS);

			if (result == null) {
				cache.put(eResource(), this, GET_KEYWORDS, result = Collections
					.unmodifiableSet(ElementOperations.getKeywords(this)));
			}

			return result;
		}

		return Collections.unmodifiableSet(ElementOperations.getKeywords(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#hasKeyword(java.lang.String)
	 */
	public boolean hasKeyword(String keyword) {
		return ElementOperations.hasKeyword(this, keyword);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Element#removeKeyword(java.lang.String)
	 */
	public void removeKeyword(String keyword) {
		ElementOperations.removeKeyword(this, keyword);
	}
	
	// <!-- end-custom-operations -->

} //ElementImpl
