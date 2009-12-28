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
 * $Id: Comment.java,v 1.1 2009-03-04 23:06:48 andrew Exp $
 */
package org.eclipse.uml2;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Comment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A comment gives the ability to attach various remarks to elements. A comment carries no semantic force, but may contain information that is useful to a modeler. A comment can be owned by any element. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.Comment#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.uml2.Comment#getAnnotatedElements <em>Annotated Element</em>}</li>
 *   <li>{@link org.eclipse.uml2.Comment#getBodyExpression <em>Body Expression</em>}</li>
 *   <li>{@link org.eclipse.uml2.Comment#getBinaryData <em>Binary Data</em>}</li>
 *   <li>{@link org.eclipse.uml2.Comment#getBinaryFormat <em>Binary Format</em>}</li>
 *   <li>{@link org.eclipse.uml2.Comment#getBinaryCount <em>Binary Count</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getComment()
 * @model
 * @generated
 */
public interface Comment extends TemplateableElement{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Body</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies a string that is the comment.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Body</em>' attribute.
	 * @see #setBody(String)
	 * @see org.eclipse.uml2.UML2Package#getComment_Body()
	 * @model default="" dataType="org.eclipse.uml2.String"
	 *        extendedMetaData="kind='element'"
	 * @generated
	 */
	String getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Comment#getBody <em>Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' attribute.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(String value);





	/**
	 * Returns the value of the '<em><b>Annotated Element</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Element}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Annotated Element</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * References the Element(s) being commented.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Annotated Element</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getComment_AnnotatedElement()
	 * @model type="org.eclipse.uml2.Element" ordered="false"
	 * @generated
	 */
	EList getAnnotatedElements();



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getAnnotatedElements();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getAnnotatedElements();


	/**
	 * Returns the value of the '<em><b>Body Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body Expression</em>' containment reference.
	 * @see #setBodyExpression(StringExpression)
	 * @see org.eclipse.uml2.UML2Package#getComment_BodyExpression()
	 * @model containment="true"
	 * @generated
	 */
	StringExpression getBodyExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Comment#getBodyExpression <em>Body Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body Expression</em>' containment reference.
	 * @see #getBodyExpression()
	 * @generated
	 */
	void setBodyExpression(StringExpression value);


    /**
     * Creates a {@link org.eclipse.uml2.StringExpression} and sets the '<em><b>Body Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.StringExpression} to create.
	 * @return The new {@link org.eclipse.uml2.StringExpression}.
	 * @see #getBodyExpression()
	 * @generated NOT
	 * @deprecated Use #createBodyExpression() instead.
     */
    StringExpression createBodyExpression(EClass eClass);

	/**
	 * Creates a {@link org.eclipse.uml2.StringExpression} and sets the '<em><b>Body Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.StringExpression}.
	 * @see #getBodyExpression()
	 * @generated
	 */
    StringExpression createBodyExpression();



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  StringExpression undeleted_getBodyExpression();

	/**
	 * Returns the value of the '<em><b>Binary Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Binary Data</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Binary Data</em>' attribute.
	 * @see #setBinaryData(byte[])
	 * @see org.eclipse.uml2.UML2Package#getComment_BinaryData()
	 * @model
	 * @generated
	 */
  byte[] getBinaryData();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Comment#getBinaryData <em>Binary Data</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binary Data</em>' attribute.
	 * @see #getBinaryData()
	 * @generated
	 */
  void setBinaryData(byte[] value);





	/**
	 * Returns the value of the '<em><b>Binary Format</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Binary Format</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Binary Format</em>' attribute.
	 * @see #setBinaryFormat(String)
	 * @see org.eclipse.uml2.UML2Package#getComment_BinaryFormat()
	 * @model default="" dataType="org.eclipse.uml2.String"
	 * @generated
	 */
  String getBinaryFormat();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Comment#getBinaryFormat <em>Binary Format</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binary Format</em>' attribute.
	 * @see #getBinaryFormat()
	 * @generated
	 */
  void setBinaryFormat(String value);





	/**
	 * Returns the value of the '<em><b>Binary Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Binary Count</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Binary Count</em>' attribute.
	 * @see #setBinaryCount(int)
	 * @see org.eclipse.uml2.UML2Package#getComment_BinaryCount()
	 * @model dataType="org.eclipse.uml2.Integer"
	 * @generated
	 */
  int getBinaryCount();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Comment#getBinaryCount <em>Binary Count</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binary Count</em>' attribute.
	 * @see #getBinaryCount()
	 * @generated
	 */
  void setBinaryCount(int value);





} // Comment
