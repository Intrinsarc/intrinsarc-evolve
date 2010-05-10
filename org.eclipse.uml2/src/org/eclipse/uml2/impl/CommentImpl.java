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
 * $Id: CommentImpl.java,v 1.1 2009-03-04 23:06:36 andrew Exp $
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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Comment;
import org.eclipse.uml2.Element;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.CommentImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.CommentImpl#getAnnotatedElements <em>Annotated Element</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.CommentImpl#getBodyExpression <em>Body Expression</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.CommentImpl#getBinaryData <em>Binary Data</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.CommentImpl#getBinaryFormat <em>Binary Format</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.CommentImpl#getBinaryCount <em>Binary Count</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CommentImpl extends TemplateableElementImpl implements Comment {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getBody() <em>Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected static final String BODY_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected String body = BODY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAnnotatedElements() <em>Annotated Element</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotatedElements()
	 * @generated
	 * @ordered
	 */
	protected EList annotatedElement = null;

	/**
	 * The cached value of the '{@link #getBodyExpression() <em>Body Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBodyExpression()
	 * @generated
	 * @ordered
	 */
	protected StringExpression bodyExpression = null;

	/**
	 * The default value of the '{@link #getBinaryData() <em>Binary Data</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getBinaryData()
	 * @generated
	 * @ordered
	 */
  protected static final byte[] BINARY_DATA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBinaryData() <em>Binary Data</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getBinaryData()
	 * @generated
	 * @ordered
	 */
  protected byte[] binaryData = BINARY_DATA_EDEFAULT;

	/**
	 * The default value of the '{@link #getBinaryFormat() <em>Binary Format</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getBinaryFormat()
	 * @generated
	 * @ordered
	 */
  protected static final String BINARY_FORMAT_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getBinaryFormat() <em>Binary Format</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getBinaryFormat()
	 * @generated
	 * @ordered
	 */
  protected String binaryFormat = BINARY_FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getBinaryCount() <em>Binary Count</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getBinaryCount()
	 * @generated
	 * @ordered
	 */
  protected static final int BINARY_COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getBinaryCount() <em>Binary Count</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getBinaryCount()
	 * @generated
	 * @ordered
	 */
  protected int binaryCount = BINARY_COUNT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommentImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (CommentImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getComment();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBody() {
		return body;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBody(String newBody) {
		newBody = newBody == null ? BODY_EDEFAULT : newBody;
		String oldBody = body;
		body = newBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.COMMENT__BODY, oldBody, body));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAnnotatedElements() {
		if (annotatedElement == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		annotatedElement = new com.hopstepjump.emflist.PersistentEList(Element.class, this, UML2Package.COMMENT__ANNOTATED_ELEMENT);
			 		return annotatedElement;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(Element.class, this, UML2Package.COMMENT__ANNOTATED_ELEMENT);
		}      
		return annotatedElement;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getAnnotatedElements() {
		if (annotatedElement == null) {
			annotatedElement = new com.hopstepjump.emflist.PersistentEList(Element.class, this, UML2Package.COMMENT__ANNOTATED_ELEMENT);
		}
		return annotatedElement;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getAnnotatedElements() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (annotatedElement != null) {
			for (Object object : annotatedElement) {
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
	public StringExpression getBodyExpression() {
		return bodyExpression;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public StringExpression undeleted_getBodyExpression() {
		StringExpression temp = getBodyExpression();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBodyExpression(StringExpression newBodyExpression, NotificationChain msgs) {
		StringExpression oldBodyExpression = bodyExpression;
		bodyExpression = newBodyExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.COMMENT__BODY_EXPRESSION, oldBodyExpression, newBodyExpression);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBodyExpression(StringExpression newBodyExpression) {
		if (newBodyExpression != bodyExpression) {
			NotificationChain msgs = null;
			if (bodyExpression != null)
				msgs = ((InternalEObject)bodyExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.COMMENT__BODY_EXPRESSION, null, msgs);
			if (newBodyExpression != null)
				msgs = ((InternalEObject)newBodyExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UML2Package.COMMENT__BODY_EXPRESSION, null, msgs);
			msgs = basicSetBodyExpression(newBodyExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.COMMENT__BODY_EXPRESSION, newBodyExpression, newBodyExpression));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createBodyExpression() instead.
	 */
	public StringExpression createBodyExpression(EClass eClass) {
		StringExpression newBodyExpression = (StringExpression) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.COMMENT__BODY_EXPRESSION, null, newBodyExpression));
		}
		setBodyExpression(newBodyExpression);
		return newBodyExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringExpression createBodyExpression() {
		StringExpression newBodyExpression = UML2Factory.eINSTANCE.createStringExpression();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.COMMENT__BODY_EXPRESSION, null, newBodyExpression));
		}
		setBodyExpression(newBodyExpression);
		return newBodyExpression;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public byte[] getBinaryData() {
		return binaryData;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setBinaryData(byte[] newBinaryData) {
		byte[] oldBinaryData = binaryData;
		binaryData = newBinaryData;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.COMMENT__BINARY_DATA, oldBinaryData, binaryData));

	}


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getBinaryFormat() {
		return binaryFormat;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setBinaryFormat(String newBinaryFormat) {
		newBinaryFormat = newBinaryFormat == null ? BINARY_FORMAT_EDEFAULT : newBinaryFormat;
		String oldBinaryFormat = binaryFormat;
		binaryFormat = newBinaryFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.COMMENT__BINARY_FORMAT, oldBinaryFormat, binaryFormat));

	}


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public int getBinaryCount() {
		return binaryCount;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setBinaryCount(int newBinaryCount) {
		int oldBinaryCount = binaryCount;
		binaryCount = newBinaryCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.COMMENT__BINARY_COUNT, oldBinaryCount, binaryCount));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.COMMENT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMENT__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.COMMENT__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.COMMENT__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
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
				case UML2Package.COMMENT__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMENT__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMENT__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.COMMENT__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.COMMENT__BODY_EXPRESSION:
					return basicSetBodyExpression(null, msgs);
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
			case UML2Package.COMMENT__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.COMMENT__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.COMMENT__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.COMMENT__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.COMMENT__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.COMMENT__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.COMMENT__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.COMMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.COMMENT__UUID:
				return getUuid();
			case UML2Package.COMMENT__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.COMMENT__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.COMMENT__BODY:
				return getBody();
			case UML2Package.COMMENT__ANNOTATED_ELEMENT:
				return getAnnotatedElements();
			case UML2Package.COMMENT__BODY_EXPRESSION:
				return getBodyExpression();
			case UML2Package.COMMENT__BINARY_DATA:
				return getBinaryData();
			case UML2Package.COMMENT__BINARY_FORMAT:
				return getBinaryFormat();
			case UML2Package.COMMENT__BINARY_COUNT:
				return new Integer(getBinaryCount());
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
			case UML2Package.COMMENT__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.COMMENT__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.COMMENT__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.COMMENT__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.COMMENT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.COMMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.COMMENT__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.COMMENT__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.COMMENT__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.COMMENT__BODY:
				setBody((String)newValue);
				return;
			case UML2Package.COMMENT__ANNOTATED_ELEMENT:
				getAnnotatedElements().clear();
				getAnnotatedElements().addAll((Collection)newValue);
				return;
			case UML2Package.COMMENT__BODY_EXPRESSION:
				setBodyExpression((StringExpression)newValue);
				return;
			case UML2Package.COMMENT__BINARY_DATA:
				setBinaryData((byte[])newValue);
				return;
			case UML2Package.COMMENT__BINARY_FORMAT:
				setBinaryFormat((String)newValue);
				return;
			case UML2Package.COMMENT__BINARY_COUNT:
				setBinaryCount(((Integer)newValue).intValue());
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
			case UML2Package.COMMENT__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.COMMENT__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.COMMENT__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.COMMENT__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.COMMENT__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.COMMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.COMMENT__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.COMMENT__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.COMMENT__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.COMMENT__BODY:
				setBody(BODY_EDEFAULT);
				return;
			case UML2Package.COMMENT__ANNOTATED_ELEMENT:
				getAnnotatedElements().clear();
				return;
			case UML2Package.COMMENT__BODY_EXPRESSION:
				setBodyExpression((StringExpression)null);
				return;
			case UML2Package.COMMENT__BINARY_DATA:
				setBinaryData(BINARY_DATA_EDEFAULT);
				return;
			case UML2Package.COMMENT__BINARY_FORMAT:
				setBinaryFormat(BINARY_FORMAT_EDEFAULT);
				return;
			case UML2Package.COMMENT__BINARY_COUNT:
				setBinaryCount(BINARY_COUNT_EDEFAULT);
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
			case UML2Package.COMMENT__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.COMMENT__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.COMMENT__OWNER:
				return basicGetOwner() != null;
			case UML2Package.COMMENT__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.COMMENT__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.COMMENT__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.COMMENT__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.COMMENT__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.COMMENT__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.COMMENT__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.COMMENT__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.COMMENT__BODY:
				return BODY_EDEFAULT == null ? body != null : !BODY_EDEFAULT.equals(body);
			case UML2Package.COMMENT__ANNOTATED_ELEMENT:
				return annotatedElement != null && !annotatedElement.isEmpty();
			case UML2Package.COMMENT__BODY_EXPRESSION:
				return bodyExpression != null;
			case UML2Package.COMMENT__BINARY_DATA:
				return BINARY_DATA_EDEFAULT == null ? binaryData != null : !BINARY_DATA_EDEFAULT.equals(binaryData);
			case UML2Package.COMMENT__BINARY_FORMAT:
				return BINARY_FORMAT_EDEFAULT == null ? binaryFormat != null : !BINARY_FORMAT_EDEFAULT.equals(binaryFormat);
			case UML2Package.COMMENT__BINARY_COUNT:
				return binaryCount != BINARY_COUNT_EDEFAULT;
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
		result.append(" (body: "); //$NON-NLS-1$
		result.append(body);
		result.append(", binaryData: "); //$NON-NLS-1$
		result.append(binaryData);
		result.append(", binaryFormat: "); //$NON-NLS-1$
		result.append(binaryFormat);
		result.append(", binaryCount: "); //$NON-NLS-1$
		result.append(binaryCount);
		result.append(')');
		return result.toString();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedElementsHelper(EList ownedElement) {
		super.getOwnedElementsHelper(ownedElement);
		if (eIsSet(UML2Package.eINSTANCE.getComment_BodyExpression())) {
			ownedElement.add(getBodyExpression());
		}
		return ownedElement;
	}


} //CommentImpl
