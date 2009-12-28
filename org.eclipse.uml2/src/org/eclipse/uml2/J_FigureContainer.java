

/**
 * <copyright>
 * </copyright>
 *
 * $Id: J_FigureContainer.java,v 1.1 2009-03-04 23:06:47 andrew Exp $
 */
package org.eclipse.uml2;

import java.util.*;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JFigure Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.J_FigureContainer#getFigures <em>Figures</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_FigureContainer#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getJ_FigureContainer()
 * @model
 * @generated
 */
public interface J_FigureContainer extends Element{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

  /**
   * Returns the value of the '<em><b>Figures</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.uml2.J_Figure}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Figures</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Figures</em>' containment reference list.
   * @see org.eclipse.uml2.UML2Package#getJ_FigureContainer_Figures()
   * @model type="org.eclipse.uml2.J_Figure" containment="true"
   * @generated NOT
   */
  ArrayList getFigures();


	/**
	 * Creates a {@link org.eclipse.uml2.J_Figure} and appends it to the '<em><b>Figures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.J_Figure}.
	 * @see #getFigures()
	 * @generated
	 */
  J_Figure createFigures();


  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  ArrayList settable_getFigures();
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  ArrayList undeleted_getFigures();


  /**
   * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.uml2.J_Property}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Properties</em>' containment reference list.
   * @see org.eclipse.uml2.UML2Package#getJ_FigureContainer_Properties()
   * @model type="org.eclipse.uml2.J_Property" containment="true"
   * @generated NOT
   */
  ArrayList getProperties();


	/**
	 * Creates a {@link org.eclipse.uml2.J_Property} and appends it to the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.J_Property}.
	 * @see #getProperties()
	 * @generated
	 */
  J_Property createProperties();


  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  ArrayList settable_getProperties();
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  ArrayList undeleted_getProperties();


} // J_FigureContainer
