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
 * $Id: StructuralFeature.java,v 1.1 2009-03-04 23:06:44 andrew Exp $
 */
package org.eclipse.uml2;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Structural Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A structural feature is a typed feature of a classifier that specify the structure of instances of the classifier. Structural feature is an abstract metaclass. By specializing multiplicity element, it supports a multiplicity that specifies valid cardinalities for the set of values associated with an instantiation of the structural feature. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.StructuralFeature#getReadWrite <em>Read Write</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getStructuralFeature()
 * @model abstract="true"
 * @generated
 */
public interface StructuralFeature extends Feature, TypedElement, MultiplicityElement{
	
	/**
	 * @generated NOT
	 */
	boolean isReadOnly();
	/**
	 * @generated NOT
	 */
	void setIsReadOnly(boolean readonly);
	

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Read Write</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.uml2.PropertyAccessKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Read Write</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read Write</em>' attribute.
	 * @see org.eclipse.uml2.PropertyAccessKind
	 * @see #setReadWrite(PropertyAccessKind)
	 * @see org.eclipse.uml2.UML2Package#getStructuralFeature_ReadWrite()
	 * @model
	 * @generated
	 */
	PropertyAccessKind getReadWrite();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.StructuralFeature#getReadWrite <em>Read Write</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Write</em>' attribute.
	 * @see org.eclipse.uml2.PropertyAccessKind
	 * @see #getReadWrite()
	 * @generated
	 */
	void setReadWrite(PropertyAccessKind value);





} // StructuralFeature
