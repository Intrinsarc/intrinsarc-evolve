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
 * $Id: DataType.java,v 1.1 2009-03-04 23:06:45 andrew Exp $
 */
package org.eclipse.uml2;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * DataType defines a kind of classifier in which operations are all pure functions (i.e., they can return data values but they cannot change data values, because they have no identity). For example, an �add� operation on a number with another number as an argument yields a third number as a result; the target and argument are unchanged. A DataType may also contain attributes to support the modeling of structured data types. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.DataType#getOwnedAttributes <em>Owned Attribute</em>}</li>
 *   <li>{@link org.eclipse.uml2.DataType#getOwnedOperations <em>Owned Operation</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getDataType()
 * @model
 * @generated
 */
public interface DataType extends Classifier{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Owned Attribute</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Property}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Property#getDatatype <em>Datatype</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Attribute</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The Attributes owned by the DataType. Subsets Classifier::attribute and Element:: ownedMember.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owned Attribute</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getDataType_OwnedAttribute()
	 * @see org.eclipse.uml2.Property#getDatatype
	 * @model type="org.eclipse.uml2.Property" opposite="datatype" containment="true"
	 * @generated
	 */
	EList getOwnedAttributes();


	/**
	 * Retrieves the {@link org.eclipse.uml2.Property} with the specified '<em><b>Name</b></em>' from the '<em><b>Owned Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Property} to retrieve.
	 * @return The {@link org.eclipse.uml2.Property} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOwnedAttributes()
	 * @generated
	 */
    Property getOwnedAttribute(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getOwnedAttributes();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getOwnedAttributes();


	/**
	 * Creates a {@link org.eclipse.uml2.Property} and appends it to the '<em><b>Owned Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.Property} to create.
	 * @return The new {@link org.eclipse.uml2.Property}.
	 * @see #getOwnedAttributes()
	 * @generated
	 */
    Property createOwnedAttribute(EClass eClass);

	/**
	 * Creates a {@link org.eclipse.uml2.Property} and appends it to the '<em><b>Owned Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.Property}.
	 * @see #getOwnedAttributes()
	 * @generated
	 */
    Property createOwnedAttribute();

	/**
	 * Returns the value of the '<em><b>Owned Operation</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Operation}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Operation#getDatatype <em>Datatype</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Operation</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The Operations owned by the DataType. Subsets Classifier::feature and Element:: ownedMember.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owned Operation</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getDataType_OwnedOperation()
	 * @see org.eclipse.uml2.Operation#getDatatype
	 * @model type="org.eclipse.uml2.Operation" opposite="datatype" containment="true"
	 * @generated
	 */
	EList getOwnedOperations();


	/**
	 * Retrieves the {@link org.eclipse.uml2.Operation} with the specified '<em><b>Name</b></em>' from the '<em><b>Owned Operation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Operation} to retrieve.
	 * @return The {@link org.eclipse.uml2.Operation} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOwnedOperations()
	 * @generated
	 */
    Operation getOwnedOperation(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getOwnedOperations();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getOwnedOperations();


    /**
     * Creates a {@link org.eclipse.uml2.Operation} and appends it to the '<em><b>Owned Operation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.Operation} to create.
	 * @return The new {@link org.eclipse.uml2.Operation}.
	 * @see #getOwnedOperations()
	 * @generated NOT
	 * @deprecated Use #createOwnedOperation() instead.
     */
    Operation createOwnedOperation(EClass eClass);

	/**
	 * Creates a {@link org.eclipse.uml2.Operation} and appends it to the '<em><b>Owned Operation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.Operation}.
	 * @see #getOwnedOperations()
	 * @generated
	 */
    Operation createOwnedOperation();

	// <!-- begin-custom-operations -->

	/**
	 * Creates a property with the specified name, type, lower bound, and upper
	 * bound as an owned attribute of this data type.
	 * 
	 * @param name
	 *            The name for the owned attribute.
	 * @param type
	 *            The type for the owned attribute.
	 * @param lowerBound
	 *            The lower bound for the owned attribute.
	 * @param upperBound
	 *            The upper bound for the owned attribute.
	 * @return The new property.
	 * @exception IllegalArgumentException
	 *                If either of the bounds is invalid.
	 */
	Property createOwnedAttribute(String name, Type type, int lowerBound,
			int upperBound);

	/**
	 * Creates an operation with the specified name, return type, parameter
	 * names, and parameter types as an owned operation of this data type.
	 * 
	 * @param name
	 *            The name for the owned operation.
	 * @param returnType
	 *            The return type for the owned operation, or <code>null</code>.
	 * @param parameterNames
	 *            The names of the owned operation's parameters, or
	 *            <code>null</code>.
	 * @param parameterTypes
	 *            The types of the owned operation's parameters, or
	 *            <code>null</code>.
	 * @return The new operation.
	 * @exception IllegalArgumentException
	 *                If the number of parameter names does not match the number
	 *                of parameter types.
	 */
	Operation createOwnedOperation(String name, Type returnType,
			String[] parameterNames, Type[] parameterTypes);

	// <!-- end-custom-operations -->

} // DataType
