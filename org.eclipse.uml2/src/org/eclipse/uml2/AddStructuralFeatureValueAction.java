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
 * $Id: AddStructuralFeatureValueAction.java,v 1.1 2009-03-04 23:06:48 andrew Exp $
 */
package org.eclipse.uml2;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Add Structural Feature Value Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Structural Features are potentially multi-valued and ordered, so the action supports specification of insertion points for new values. It also supports the removal of existing values of the structural feature before the new value is added. The object to access is specified dynamically, by referring to an input pin on which the object will be placed at runtime. The type of the value of this pin is the classifier that owns the specified structural feature, and the value�s multiplicity is 1..1. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.AddStructuralFeatureValueAction#isReplaceAll <em>Is Replace All</em>}</li>
 *   <li>{@link org.eclipse.uml2.AddStructuralFeatureValueAction#getInsertAt <em>Insert At</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getAddStructuralFeatureValueAction()
 * @model
 * @generated
 */
public interface AddStructuralFeatureValueAction extends WriteStructuralFeatureAction{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Is Replace All</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Replace All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies whether existing values of the structural feature of the object should be removed before adding the new value.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Is Replace All</em>' attribute.
	 * @see #setIsReplaceAll(boolean)
	 * @see org.eclipse.uml2.UML2Package#getAddStructuralFeatureValueAction_IsReplaceAll()
	 * @model default="false" dataType="org.eclipse.uml2.Boolean"
	 * @generated
	 */
	boolean isReplaceAll();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.AddStructuralFeatureValueAction#isReplaceAll <em>Is Replace All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Replace All</em>' attribute.
	 * @see #isReplaceAll()
	 * @generated
	 */
	void setIsReplaceAll(boolean value);





	/**
	 * Returns the value of the '<em><b>Insert At</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Insert At</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * (Specialized from Action:input) Gives the position at which to insert a new value or move an existing value in ordered structural features. The type of the pin is UnlimitedNatural, but the value cannot be zero. This pin is omitted for unordered structural features.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Insert At</em>' containment reference.
	 * @see #setInsertAt(InputPin)
	 * @see org.eclipse.uml2.UML2Package#getAddStructuralFeatureValueAction_InsertAt()
	 * @model containment="true"
	 * @generated
	 */
	InputPin getInsertAt();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.AddStructuralFeatureValueAction#getInsertAt <em>Insert At</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Insert At</em>' containment reference.
	 * @see #getInsertAt()
	 * @generated
	 */
	void setInsertAt(InputPin value);


	/**
	 * Creates a {@link org.eclipse.uml2.InputPin} and sets the '<em><b>Insert At</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.InputPin} to create.
	 * @return The new {@link org.eclipse.uml2.InputPin}.
	 * @see #getInsertAt()
	 * @generated
	 */
    InputPin createInsertAt(EClass eClass);

	/**
	 * Creates a {@link org.eclipse.uml2.InputPin} and sets the '<em><b>Insert At</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.InputPin}.
	 * @see #getInsertAt()
	 * @generated
	 */
    InputPin createInsertAt();



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  InputPin undeleted_getInsertAt();

} // AddStructuralFeatureValueAction
