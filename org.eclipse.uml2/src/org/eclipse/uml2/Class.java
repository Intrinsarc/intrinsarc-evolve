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
 * $Id: Class.java,v 1.1 2009-03-04 23:06:48 andrew Exp $
 */
package org.eclipse.uml2;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Class is a kind of classifier whose features are attributes and operations. Attributes of a class are represented by instances of Property that are owned by the class. Some of these attributes may represent the navigable ends of binary associations. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.Class#getOwnedOperations <em>Owned Operation</em>}</li>
 *   <li>{@link org.eclipse.uml2.Class#getSuperClasses <em>Super Class</em>}</li>
 *   <li>{@link org.eclipse.uml2.Class#getExtensions <em>Extension</em>}</li>
 *   <li>{@link org.eclipse.uml2.Class#getNestedClassifiers <em>Nested Classifier</em>}</li>
 *   <li>{@link org.eclipse.uml2.Class#isActive <em>Is Active</em>}</li>
 *   <li>{@link org.eclipse.uml2.Class#getOwnedReceptions <em>Owned Reception</em>}</li>
 *   <li>{@link org.eclipse.uml2.Class#getComponentKind <em>Component Kind</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getClass_()
 * @model
 * @generated
 */
public interface Class extends BehavioredClassifier, EncapsulatedClassifier{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Is Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Active</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Determines whether an object specified by this class is active or not. If true, then the owning class is referred to as an active class. If false, then such a class is referred to as a passive class.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Is Active</em>' attribute.
	 * @see #setIsActive(boolean)
	 * @see org.eclipse.uml2.UML2Package#getClass_IsActive()
	 * @model dataType="org.eclipse.uml2.Boolean"
	 * @generated
	 */
	boolean isActive();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Class#isActive <em>Is Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Active</em>' attribute.
	 * @see #isActive()
	 * @generated
	 */
	void setIsActive(boolean value);





	/**
	 * Returns the value of the '<em><b>Owned Operation</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Operation}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Operation#getClass_ <em>Class </em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Operation</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The operations owned by the class. The association is ordered. Subsets Classifier::feature and Namespace::ownedMember.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owned Operation</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getClass_OwnedOperation()
	 * @see org.eclipse.uml2.Operation#getClass_
	 * @model type="org.eclipse.uml2.Operation" opposite="class_" containment="true"
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

	/**
	 * Returns the value of the '<em><b>Super Class</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Class}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Class</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This gives the superclasses of a class. It redefines Classifier::general. This is derived.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Super Class</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getClass_SuperClass()
	 * @model type="org.eclipse.uml2.Class" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList getSuperClasses();


	/**
	 * Retrieves the {@link org.eclipse.uml2.Class} with the specified '<em><b>Name</b></em>' from the '<em><b>Super Class</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Class} to retrieve.
	 * @return The {@link org.eclipse.uml2.Class} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getSuperClasses()
	 * @generated
	 */
    Class getSuperClass(String name);




	/**
	 * Returns the value of the '<em><b>Extension</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Extension}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Extension#getMetaclass <em>Metaclass</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getClass_Extension()
	 * @see org.eclipse.uml2.Extension#getMetaclass
	 * @model type="org.eclipse.uml2.Extension" opposite="metaclass" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList getExtensions();


	/**
	 * Retrieves the {@link org.eclipse.uml2.Extension} with the specified '<em><b>Name</b></em>' from the '<em><b>Extension</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Extension} to retrieve.
	 * @return The {@link org.eclipse.uml2.Extension} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getExtensions()
	 * @generated
	 */
    Extension getExtension(String name);




	/**
	 * Returns the value of the '<em><b>Nested Classifier</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Classifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nested Classifier</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * References all the Classifiers that are defined (nested) within the Class. Subsets Element:: ownedMember.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Nested Classifier</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getClass_NestedClassifier()
	 * @model type="org.eclipse.uml2.Classifier" containment="true"
	 * @generated
	 */
	EList getNestedClassifiers();


	/**
	 * Retrieves the {@link org.eclipse.uml2.Classifier} with the specified '<em><b>Name</b></em>' from the '<em><b>Nested Classifier</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Classifier} to retrieve.
	 * @return The {@link org.eclipse.uml2.Classifier} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getNestedClassifiers()
	 * @generated
	 */
    Classifier getNestedClassifier(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getNestedClassifiers();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getNestedClassifiers();


	/**
	 * Creates a {@link org.eclipse.uml2.Classifier} and appends it to the '<em><b>Nested Classifier</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.Classifier} to create.
	 * @return The new {@link org.eclipse.uml2.Classifier}.
	 * @see #getNestedClassifiers()
	 * @generated
	 */
    Classifier createNestedClassifier(EClass eClass);

	/**
	 * Returns the value of the '<em><b>Owned Reception</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Reception}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Reception</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Receptions that objects of this class are willing to accept. (Specializes Namespace.owned- Member and Classifier.feature.)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owned Reception</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getClass_OwnedReception()
	 * @model type="org.eclipse.uml2.Reception" containment="true" ordered="false"
	 * @generated
	 */
	EList getOwnedReceptions();


	/**
	 * Retrieves the {@link org.eclipse.uml2.Reception} with the specified '<em><b>Name</b></em>' from the '<em><b>Owned Reception</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Reception} to retrieve.
	 * @return The {@link org.eclipse.uml2.Reception} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOwnedReceptions()
	 * @generated
	 */
    Reception getOwnedReception(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getOwnedReceptions();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getOwnedReceptions();


	/**
	 * Returns the value of the '<em><b>Component Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.uml2.ComponentKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Kind</em>' attribute.
	 * @see org.eclipse.uml2.ComponentKind
	 * @see #setComponentKind(ComponentKind)
	 * @see org.eclipse.uml2.UML2Package#getClass_ComponentKind()
	 * @model
	 * @generated
	 */
	ComponentKind getComponentKind();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Class#getComponentKind <em>Component Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Kind</em>' attribute.
	 * @see org.eclipse.uml2.ComponentKind
	 * @see #getComponentKind()
	 * @generated
	 */
	void setComponentKind(ComponentKind value);





    /**
     * Creates a {@link org.eclipse.uml2.Reception} and appends it to the '<em><b>Owned Reception</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.Reception} to create.
	 * @return The new {@link org.eclipse.uml2.Reception}.
	 * @see #getOwnedReceptions()
	 * @generated NOT
	 * @deprecated Use #createOwnedReception() instead.
     */
    Reception createOwnedReception(EClass eClass);

	/**
	 * Creates a {@link org.eclipse.uml2.Reception} and appends it to the '<em><b>Owned Reception</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.Reception}.
	 * @see #getOwnedReceptions()
	 * @generated
	 */
    Reception createOwnedReception();

	// <!-- begin-custom-operations -->

	/**
	 * Determines whether this class is a metaclass.
	 * 
	 * @return <code>true</code> if this class is stereotyped as a metaclass;
	 *         <code>false</code> otherwise.
	 */
	boolean isMetaclass();

	/**
	 * Creates a property with the specified name, type, lower bound, and upper
	 * bound as an owned attribute of this class.
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
	 * names, and parameter types as an owned operation of this class.
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

} // Class
