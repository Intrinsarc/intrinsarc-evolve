/*
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - initial API and implementation
 *
 * $Id: InstanceValue.java,v 1.1 2009-03-04 23:06:45 andrew Exp $
 */
package org.eclipse.uml2;


import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Instance Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An instance value specifies the value modeled by an instance specification. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.InstanceValue#getInstance <em>Instance</em>}</li>
 *   <li>{@link org.eclipse.uml2.InstanceValue#getOwnedAnonymousInstanceValue <em>Owned Anonymous Instance Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getInstanceValue()
 * @model
 * @generated
 */
public interface InstanceValue extends ValueSpecification{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The instance that is the specified value.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Instance</em>' reference.
	 * @see #setInstance(InstanceSpecification)
	 * @see org.eclipse.uml2.UML2Package#getInstanceValue_Instance()
	 * @model required="true"
	 * @generated
	 */
	InstanceSpecification getInstance();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.InstanceValue#getInstance <em>Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance</em>' reference.
	 * @see #getInstance()
	 * @generated
	 */
	void setInstance(InstanceSpecification value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  InstanceSpecification undeleted_getInstance();

	/**
	 * Returns the value of the '<em><b>Owned Anonymous Instance Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Anonymous Instance Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Anonymous Instance Value</em>' containment reference.
	 * @see #setOwnedAnonymousInstanceValue(InstanceSpecification)
	 * @see org.eclipse.uml2.UML2Package#getInstanceValue_OwnedAnonymousInstanceValue()
	 * @model containment="true"
	 * @generated
	 */
	InstanceSpecification getOwnedAnonymousInstanceValue();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.InstanceValue#getOwnedAnonymousInstanceValue <em>Owned Anonymous Instance Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owned Anonymous Instance Value</em>' containment reference.
	 * @see #getOwnedAnonymousInstanceValue()
	 * @generated
	 */
	void setOwnedAnonymousInstanceValue(InstanceSpecification value);


	/**
	 * Creates a {@link org.eclipse.uml2.InstanceSpecification} and sets the '<em><b>Owned Anonymous Instance Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.InstanceSpecification} to create.
	 * @return The new {@link org.eclipse.uml2.InstanceSpecification}.
	 * @see #getOwnedAnonymousInstanceValue()
	 * @generated
	 */
	InstanceSpecification createOwnedAnonymousInstanceValue(EClass eClass);

	/**
	 * Creates a {@link org.eclipse.uml2.EnumerationLiteral} and sets the '<em><b>Owned Anonymous Instance Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.EnumerationLiteral}.
	 * @see #getOwnedAnonymousInstanceValue()
	 * @generated
	 */
	InstanceSpecification createOwnedAnonymousInstanceValue();



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InstanceSpecification undeleted_getOwnedAnonymousInstanceValue();

} // InstanceValue
