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
 * $Id: Realization.java,v 1.1 2009-03-04 23:06:48 andrew Exp $
 */
package org.eclipse.uml2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Realization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * In the metamodel, a Realization is a subtype of Dependencies::Realization. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.Realization#getAbstraction <em>Abstraction</em>}</li>
 *   <li>{@link org.eclipse.uml2.Realization#getRealizingClassifier <em>Realizing Classifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getRealization()
 * @model
 * @generated
 */
public interface Realization extends Abstraction{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Abstraction</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Component#getRealizations <em>Realization</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstraction</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstraction</em>' container reference.
	 * @see #setAbstraction(Component)
	 * @see org.eclipse.uml2.UML2Package#getRealization_Abstraction()
	 * @see org.eclipse.uml2.Component#getRealizations
	 * @model opposite="realization"
	 * @generated
	 */
	Component getAbstraction();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Realization#getAbstraction <em>Abstraction</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstraction</em>' container reference.
	 * @see #getAbstraction()
	 * @generated
	 */
	void setAbstraction(Component value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  Component undeleted_getAbstraction();

	/**
	 * Returns the value of the '<em><b>Realizing Classifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Realizing Classifier</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Realizing Classifier</em>' reference.
	 * @see #setRealizingClassifier(Classifier)
	 * @see org.eclipse.uml2.UML2Package#getRealization_RealizingClassifier()
	 * @model required="true"
	 * @generated
	 */
	Classifier getRealizingClassifier();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Realization#getRealizingClassifier <em>Realizing Classifier</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Realizing Classifier</em>' reference.
	 * @see #getRealizingClassifier()
	 * @generated
	 */
	void setRealizingClassifier(Classifier value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  Classifier undeleted_getRealizingClassifier();

} // Realization
