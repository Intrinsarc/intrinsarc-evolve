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
 * $Id: Region.java,v 1.1 2009-03-04 23:06:46 andrew Exp $
 */
package org.eclipse.uml2;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Region</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.Region#getSubvertices <em>Subvertex</em>}</li>
 *   <li>{@link org.eclipse.uml2.Region#getTransitions <em>Transition</em>}</li>
 *   <li>{@link org.eclipse.uml2.Region#getStateMachine <em>State Machine</em>}</li>
 *   <li>{@link org.eclipse.uml2.Region#getState <em>State</em>}</li>
 *   <li>{@link org.eclipse.uml2.Region#getExtendedRegion <em>Extended Region</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getRegion()
 * @model
 * @generated
 */
public interface Region extends Namespace, RedefinableElement{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Subvertex</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Vertex}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Vertex#getContainer <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subvertex</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subvertex</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getRegion_Subvertex()
	 * @see org.eclipse.uml2.Vertex#getContainer
	 * @model type="org.eclipse.uml2.Vertex" opposite="container" containment="true" ordered="false"
	 * @generated
	 */
	EList getSubvertices();


	/**
	 * Retrieves the {@link org.eclipse.uml2.Vertex} with the specified '<em><b>Name</b></em>' from the '<em><b>Subvertex</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Vertex} to retrieve.
	 * @return The {@link org.eclipse.uml2.Vertex} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSubvertices()
	 * @generated
	 */
    Vertex getSubvertex(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getSubvertices();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getSubvertices();


	/**
	 * Creates a {@link org.eclipse.uml2.Vertex} and appends it to the '<em><b>Subvertex</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.Vertex} to create.
	 * @return The new {@link org.eclipse.uml2.Vertex}.
	 * @see #getSubvertices()
	 * @generated
	 */
    Vertex createSubvertex(EClass eClass);

	/**
	 * Returns the value of the '<em><b>Transition</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Transition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Transition#getContainer <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transition</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getRegion_Transition()
	 * @see org.eclipse.uml2.Transition#getContainer
	 * @model type="org.eclipse.uml2.Transition" opposite="container" containment="true" ordered="false"
	 * @generated
	 */
	EList getTransitions();


	/**
	 * Retrieves the {@link org.eclipse.uml2.Transition} with the specified '<em><b>Name</b></em>' from the '<em><b>Transition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Transition} to retrieve.
	 * @return The {@link org.eclipse.uml2.Transition} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getTransitions()
	 * @generated
	 */
    Transition getTransition(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getTransitions();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getTransitions();


	/**
	 * Creates a {@link org.eclipse.uml2.Transition} and appends it to the '<em><b>Transition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.Transition} to create.
	 * @return The new {@link org.eclipse.uml2.Transition}.
	 * @see #getTransitions()
	 * @generated
	 */
    Transition createTransition(EClass eClass);

	/**
	 * Creates a {@link org.eclipse.uml2.Transition} and appends it to the '<em><b>Transition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.Transition}.
	 * @see #getTransitions()
	 * @generated
	 */
    Transition createTransition();

	/**
	 * Returns the value of the '<em><b>State Machine</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.StateMachine#getRegions <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Machine</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Machine</em>' container reference.
	 * @see #setStateMachine(StateMachine)
	 * @see org.eclipse.uml2.UML2Package#getRegion_StateMachine()
	 * @see org.eclipse.uml2.StateMachine#getRegions
	 * @model opposite="region"
	 * @generated
	 */
	StateMachine getStateMachine();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Region#getStateMachine <em>State Machine</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Machine</em>' container reference.
	 * @see #getStateMachine()
	 * @generated
	 */
	void setStateMachine(StateMachine value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  StateMachine undeleted_getStateMachine();

	/**
	 * Returns the value of the '<em><b>State</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.State#getRegions <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' container reference.
	 * @see #setState(State)
	 * @see org.eclipse.uml2.UML2Package#getRegion_State()
	 * @see org.eclipse.uml2.State#getRegions
	 * @model opposite="region"
	 * @generated
	 */
	State getState();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Region#getState <em>State</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' container reference.
	 * @see #getState()
	 * @generated
	 */
	void setState(State value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  State undeleted_getState();

	/**
	 * Returns the value of the '<em><b>Extended Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extended Region</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended Region</em>' reference.
	 * @see #setExtendedRegion(Region)
	 * @see org.eclipse.uml2.UML2Package#getRegion_ExtendedRegion()
	 * @model
	 * @generated
	 */
	Region getExtendedRegion();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Region#getExtendedRegion <em>Extended Region</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Region</em>' reference.
	 * @see #getExtendedRegion()
	 * @generated
	 */
	void setExtendedRegion(Region value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  Region undeleted_getExtendedRegion();

	/**
	 * Returns the value of the '<em><b>Redefined Element</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.RedefinableElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * redefinable element that is being redefined by this element. This is a derived union.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Redefined Element</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getRedefinableElement_RedefinedElement()
	 * @model type="org.eclipse.uml2.RedefinableElement" transient="true" changeable="false" derived="true" ordered="false"
	 * @generated
	 */
	EList getRedefinedElements();


	/**
	 * Retrieves the {@link org.eclipse.uml2.RedefinableElement} with the specified '<em><b>Name</b></em>' from the '<em><b>Redefined Element</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.RedefinableElement} to retrieve.
	 * @return The {@link org.eclipse.uml2.RedefinableElement} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getRedefinedElements()
	 * @generated
	 */
    RedefinableElement getRedefinedElement(String name);




} // Region
