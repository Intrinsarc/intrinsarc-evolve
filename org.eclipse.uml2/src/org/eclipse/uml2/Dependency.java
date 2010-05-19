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
 * $Id: Dependency.java,v 1.1 2009-03-04 23:06:46 andrew Exp $
 */
package org.eclipse.uml2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A dependency is a relationship that signifies that a single or a set of model elements requires other model elements for their specification or implementation. This means that the complete semantics of the depending elements is either semantically or structurally dependent on the definition of the supplier element(s). 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.Dependency#getClients <em>Client</em>}</li>
 *   <li>{@link org.eclipse.uml2.Dependency#getSuppliers <em>Supplier</em>}</li>
 *   <li>{@link org.eclipse.uml2.Dependency#getDependencyTarget <em>Dependency Target</em>}</li>
 *   <li>{@link org.eclipse.uml2.Dependency#isResemblance <em>Resemblance</em>}</li>
 *   <li>{@link org.eclipse.uml2.Dependency#isReplacement <em>Replacement</em>}</li>
 *   <li>{@link org.eclipse.uml2.Dependency#isTrace <em>Trace</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getDependency()
 * @model
 * @generated
 */
public interface Dependency extends PackageableElement, DirectedRelationship{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Client</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.NamedElement}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.NamedElement#getClientDependencies <em>Client Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Client</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element that is affected by the supplier element. In some cases (such as a Trace Abstraction) the direction is unimportant and serves only to distinguish the two elements.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Client</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getDependency_Client()
	 * @see org.eclipse.uml2.NamedElement#getClientDependencies
	 * @model type="org.eclipse.uml2.NamedElement" opposite="clientDependency" required="true" ordered="false"
	 * @generated
	 */
	EList getClients();


	/**
	 * Retrieves the {@link org.eclipse.uml2.NamedElement} with the specified '<em><b>Name</b></em>' from the '<em><b>Client</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.NamedElement} to retrieve.
	 * @return The {@link org.eclipse.uml2.NamedElement} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getClients()
	 * @generated
	 */
    NamedElement getClient(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getClients();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getClients();


	/**
	 * Returns the value of the '<em><b>Supplier</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.NamedElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Supplier</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Designates the element that is unaffected by a change. In a two-way relationship (such as some Refinement Abstractions) this would be the more general element. In an undirected situation, such as a Trace Abstraction, the choice of client and supplier is not relevant.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Supplier</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getDependency_Supplier()
	 * @model type="org.eclipse.uml2.NamedElement" required="true" ordered="false"
	 * @generated
	 */
	EList getSuppliers();


	/**
	 * Retrieves the {@link org.eclipse.uml2.NamedElement} with the specified '<em><b>Name</b></em>' from the '<em><b>Supplier</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.NamedElement} to retrieve.
	 * @return The {@link org.eclipse.uml2.NamedElement} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSuppliers()
	 * @generated
	 */
    NamedElement getSupplier(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getSuppliers();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getSuppliers();


	/**
	 * Returns the value of the '<em><b>Dependency Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dependency Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependency Target</em>' reference.
	 * @see #setDependencyTarget(NamedElement)
	 * @see org.eclipse.uml2.UML2Package#getDependency_DependencyTarget()
	 * @model
	 * @generated
	 */
	NamedElement getDependencyTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Dependency#getDependencyTarget <em>Dependency Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dependency Target</em>' reference.
	 * @see #getDependencyTarget()
	 * @generated
	 */
	void setDependencyTarget(NamedElement value);




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NamedElement undeleted_getDependencyTarget();

	/**
	 * Returns the value of the '<em><b>Resemblance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resemblance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resemblance</em>' attribute.
	 * @see #setResemblance(boolean)
	 * @see org.eclipse.uml2.UML2Package#getDependency_Resemblance()
	 * @model dataType="org.eclipse.uml2.Boolean"
	 * @generated
	 */
	boolean isResemblance();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Dependency#isResemblance <em>Resemblance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resemblance</em>' attribute.
	 * @see #isResemblance()
	 * @generated
	 */
	void setResemblance(boolean value);





	/**
	 * Returns the value of the '<em><b>Replacement</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Replacement</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Replacement</em>' attribute.
	 * @see #setReplacement(boolean)
	 * @see org.eclipse.uml2.UML2Package#getDependency_Replacement()
	 * @model dataType="org.eclipse.uml2.Boolean"
	 * @generated
	 */
	boolean isReplacement();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Dependency#isReplacement <em>Replacement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Replacement</em>' attribute.
	 * @see #isReplacement()
	 * @generated
	 */
	void setReplacement(boolean value);





	/**
	 * Returns the value of the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trace</em>' attribute.
	 * @see #setTrace(boolean)
	 * @see org.eclipse.uml2.UML2Package#getDependency_Trace()
	 * @model dataType="org.eclipse.uml2.Boolean"
	 * @generated
	 */
	boolean isTrace();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Dependency#isTrace <em>Trace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trace</em>' attribute.
	 * @see #isTrace()
	 * @generated
	 */
	void setTrace(boolean value);





} // Dependency
