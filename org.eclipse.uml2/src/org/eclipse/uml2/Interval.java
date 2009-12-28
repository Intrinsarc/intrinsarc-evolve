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
 * $Id: Interval.java,v 1.1 2009-03-04 23:06:48 andrew Exp $
 */
package org.eclipse.uml2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interval</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An Interval defines the range between two value specifications. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.Interval#getMins <em>Min</em>}</li>
 *   <li>{@link org.eclipse.uml2.Interval#getMaxes <em>Max</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getInterval()
 * @model
 * @generated
 */
public interface Interval extends ValueSpecification{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Min</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.ValueSpecification}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Refers to the ValueSpecification denoting the minimum value of the range.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Min</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getInterval_Min()
	 * @model type="org.eclipse.uml2.ValueSpecification"
	 * @generated
	 */
	EList getMins();


	/**
	 * Retrieves the {@link org.eclipse.uml2.ValueSpecification} with the specified '<em><b>Name</b></em>' from the '<em><b>Min</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.ValueSpecification} to retrieve.
	 * @return The {@link org.eclipse.uml2.ValueSpecification} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getMins()
	 * @generated
	 */
    ValueSpecification getMin(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getMins();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getMins();


	/**
	 * Returns the value of the '<em><b>Max</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.ValueSpecification}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Refers to the ValueSpecification denoting the maximum value of the range.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getInterval_Max()
	 * @model type="org.eclipse.uml2.ValueSpecification"
	 * @generated
	 */
	EList getMaxes();


	/**
	 * Retrieves the {@link org.eclipse.uml2.ValueSpecification} with the specified '<em><b>Name</b></em>' from the '<em><b>Max</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.ValueSpecification} to retrieve.
	 * @return The {@link org.eclipse.uml2.ValueSpecification} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getMaxes()
	 * @generated
	 */
    ValueSpecification getMax(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getMaxes();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getMaxes();


} // Interval
