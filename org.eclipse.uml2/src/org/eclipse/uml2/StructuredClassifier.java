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
 * $Id: StructuredClassifier.java,v 1.1 2009-03-04 23:06:47 andrew Exp $
 */
package org.eclipse.uml2;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Structured Classifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A structured classifier is an abstract metaclass that represents any classifier whose behavior can be fully or partly described by the collaboration of owned or referenced instances. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getOwnedAttributes <em>Owned Attribute</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getParts <em>Part</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getRoles <em>Role</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getOwnedConnectors <em>Owned Connector</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getDeltaDeletedAttributes <em>Delta Deleted Attributes</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getDeltaReplacedAttributes <em>Delta Replaced Attributes</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getDeltaDeletedPorts <em>Delta Deleted Ports</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getDeltaReplacedPorts <em>Delta Replaced Ports</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getDeltaDeletedConnectors <em>Delta Deleted Connectors</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getDeltaReplacedConnectors <em>Delta Replaced Connectors</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getDeltaDeletedOperations <em>Delta Deleted Operations</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getDeltaReplacedOperations <em>Delta Replaced Operations</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getDeltaDeletedTraces <em>Delta Deleted Traces</em>}</li>
 *   <li>{@link org.eclipse.uml2.StructuredClassifier#getDeltaReplacedTraces <em>Delta Replaced Traces</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier()
 * @model abstract="true"
 * @generated
 */
public interface StructuredClassifier extends Classifier{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Owned Attribute</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Attribute</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * References the properties owned by the classifier. (Subsets StructuredClassifier.role, Classifier. attribute,and Namespace.ownedMember)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owned Attribute</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_OwnedAttribute()
	 * @model type="org.eclipse.uml2.Property" containment="true"
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
	 * Returns the value of the '<em><b>Part</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Part</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * References the properties specifying instances that the classifier owns by composition. This association is derived, selecting those owned properties where isComposite is true.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Part</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_Part()
	 * @model type="org.eclipse.uml2.Property" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList getParts();


	/**
	 * Retrieves the {@link org.eclipse.uml2.Property} with the specified '<em><b>Name</b></em>' from the '<em><b>Part</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Property} to retrieve.
	 * @return The {@link org.eclipse.uml2.Property} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getParts()
	 * @generated
	 */
    Property getPart(String name);




	/**
	 * Returns the value of the '<em><b>Role</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.ConnectableElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * References the roles that instances may play in this classifier. (Abstract union; subsets Classifier.feature.)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Role</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_Role()
	 * @model type="org.eclipse.uml2.ConnectableElement" transient="true" changeable="false" derived="true" ordered="false"
	 * @generated
	 */
	EList getRoles();


	/**
	 * Retrieves the {@link org.eclipse.uml2.ConnectableElement} with the specified '<em><b>Name</b></em>' from the '<em><b>Role</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.ConnectableElement} to retrieve.
	 * @return The {@link org.eclipse.uml2.ConnectableElement} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getRoles()
	 * @generated
	 */
    ConnectableElement getRole(String name);




	/**
	 * Returns the value of the '<em><b>Owned Connector</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Connector}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Connector</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * References the connectors owned by the classifier. (Subsets Classifier.feature and Namespace.ownedMember)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owned Connector</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_OwnedConnector()
	 * @model type="org.eclipse.uml2.Connector" containment="true" ordered="false"
	 * @generated
	 */
	EList getOwnedConnectors();


	/**
	 * Retrieves the {@link org.eclipse.uml2.Connector} with the specified '<em><b>Name</b></em>' from the '<em><b>Owned Connector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Connector} to retrieve.
	 * @return The {@link org.eclipse.uml2.Connector} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOwnedConnectors()
	 * @generated
	 */
    Connector getOwnedConnector(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getOwnedConnectors();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getOwnedConnectors();


	/**
	 * Returns the value of the '<em><b>Delta Deleted Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.DeltaDeletedAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delta Deleted Attributes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta Deleted Attributes</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_DeltaDeletedAttributes()
	 * @model type="org.eclipse.uml2.DeltaDeletedAttribute" containment="true"
	 * @generated
	 */
	EList getDeltaDeletedAttributes();


	/**
	 * Creates a {@link org.eclipse.uml2.DeltaDeletedAttribute} and appends it to the '<em><b>Delta Deleted Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.DeltaDeletedAttribute}.
	 * @see #getDeltaDeletedAttributes()
	 * @generated
	 */
	DeltaDeletedAttribute createDeltaDeletedAttributes();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getDeltaDeletedAttributes();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getDeltaDeletedAttributes();


	/**
	 * Returns the value of the '<em><b>Delta Replaced Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.DeltaReplacedAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delta Replaced Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta Replaced Attributes</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_DeltaReplacedAttributes()
	 * @model type="org.eclipse.uml2.DeltaReplacedAttribute" containment="true"
	 * @generated
	 */
	EList getDeltaReplacedAttributes();


	/**
	 * Creates a {@link org.eclipse.uml2.DeltaReplacedAttribute} and appends it to the '<em><b>Delta Replaced Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.DeltaReplacedAttribute}.
	 * @see #getDeltaReplacedAttributes()
	 * @generated
	 */
	DeltaReplacedAttribute createDeltaReplacedAttributes();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getDeltaReplacedAttributes();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getDeltaReplacedAttributes();


	/**
	 * Returns the value of the '<em><b>Delta Deleted Ports</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.DeltaDeletedPort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delta Deleted Ports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta Deleted Ports</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_DeltaDeletedPorts()
	 * @model type="org.eclipse.uml2.DeltaDeletedPort" containment="true"
	 * @generated
	 */
	EList getDeltaDeletedPorts();


	/**
	 * Creates a {@link org.eclipse.uml2.DeltaDeletedPort} and appends it to the '<em><b>Delta Deleted Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.DeltaDeletedPort}.
	 * @see #getDeltaDeletedPorts()
	 * @generated
	 */
	DeltaDeletedPort createDeltaDeletedPorts();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getDeltaDeletedPorts();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getDeltaDeletedPorts();


	/**
	 * Returns the value of the '<em><b>Delta Replaced Ports</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.DeltaReplacedPort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delta Replaced Ports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta Replaced Ports</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_DeltaReplacedPorts()
	 * @model type="org.eclipse.uml2.DeltaReplacedPort" containment="true"
	 * @generated
	 */
	EList getDeltaReplacedPorts();


	/**
	 * Creates a {@link org.eclipse.uml2.DeltaReplacedPort} and appends it to the '<em><b>Delta Replaced Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.DeltaReplacedPort}.
	 * @see #getDeltaReplacedPorts()
	 * @generated
	 */
	DeltaReplacedPort createDeltaReplacedPorts();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getDeltaReplacedPorts();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getDeltaReplacedPorts();


	/**
	 * Returns the value of the '<em><b>Delta Deleted Connectors</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.DeltaDeletedConnector}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delta Deleted Connectors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta Deleted Connectors</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_DeltaDeletedConnectors()
	 * @model type="org.eclipse.uml2.DeltaDeletedConnector" containment="true"
	 * @generated
	 */
	EList getDeltaDeletedConnectors();


	/**
	 * Creates a {@link org.eclipse.uml2.DeltaDeletedConnector} and appends it to the '<em><b>Delta Deleted Connectors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.DeltaDeletedConnector}.
	 * @see #getDeltaDeletedConnectors()
	 * @generated
	 */
	DeltaDeletedConnector createDeltaDeletedConnectors();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getDeltaDeletedConnectors();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getDeltaDeletedConnectors();


	/**
	 * Returns the value of the '<em><b>Delta Replaced Connectors</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.DeltaReplacedConnector}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delta Replaced Connectors</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta Replaced Connectors</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_DeltaReplacedConnectors()
	 * @model type="org.eclipse.uml2.DeltaReplacedConnector" containment="true"
	 * @generated
	 */
	EList getDeltaReplacedConnectors();


	/**
	 * Creates a {@link org.eclipse.uml2.DeltaReplacedConnector} and appends it to the '<em><b>Delta Replaced Connectors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.DeltaReplacedConnector}.
	 * @see #getDeltaReplacedConnectors()
	 * @generated
	 */
	DeltaReplacedConnector createDeltaReplacedConnectors();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getDeltaReplacedConnectors();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getDeltaReplacedConnectors();


	/**
	 * Returns the value of the '<em><b>Delta Deleted Operations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.DeltaDeletedOperation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delta Deleted Operations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta Deleted Operations</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_DeltaDeletedOperations()
	 * @model type="org.eclipse.uml2.DeltaDeletedOperation" containment="true"
	 * @generated
	 */
	EList getDeltaDeletedOperations();


	/**
	 * Creates a {@link org.eclipse.uml2.DeltaDeletedOperation} and appends it to the '<em><b>Delta Deleted Operations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.DeltaDeletedOperation}.
	 * @see #getDeltaDeletedOperations()
	 * @generated
	 */
	DeltaDeletedOperation createDeltaDeletedOperations();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getDeltaDeletedOperations();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getDeltaDeletedOperations();


	/**
	 * Returns the value of the '<em><b>Delta Replaced Operations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.DeltaReplacedOperation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delta Replaced Operations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta Replaced Operations</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_DeltaReplacedOperations()
	 * @model type="org.eclipse.uml2.DeltaReplacedOperation" containment="true"
	 * @generated
	 */
	EList getDeltaReplacedOperations();


	/**
	 * Creates a {@link org.eclipse.uml2.DeltaReplacedOperation} and appends it to the '<em><b>Delta Replaced Operations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.DeltaReplacedOperation}.
	 * @see #getDeltaReplacedOperations()
	 * @generated
	 */
	DeltaReplacedOperation createDeltaReplacedOperations();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getDeltaReplacedOperations();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getDeltaReplacedOperations();


	/**
	 * Returns the value of the '<em><b>Delta Deleted Traces</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.DeltaDeletedTrace}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delta Deleted Traces</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta Deleted Traces</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_DeltaDeletedTraces()
	 * @model type="org.eclipse.uml2.DeltaDeletedTrace" containment="true"
	 * @generated
	 */
	EList getDeltaDeletedTraces();


	/**
	 * Creates a {@link org.eclipse.uml2.DeltaDeletedTrace} and appends it to the '<em><b>Delta Deleted Traces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.DeltaDeletedTrace}.
	 * @see #getDeltaDeletedTraces()
	 * @generated
	 */
	DeltaDeletedTrace createDeltaDeletedTraces();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getDeltaDeletedTraces();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getDeltaDeletedTraces();


	/**
	 * Returns the value of the '<em><b>Delta Replaced Traces</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.DeltaReplacedTrace}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delta Replaced Traces</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta Replaced Traces</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getStructuredClassifier_DeltaReplacedTraces()
	 * @model type="org.eclipse.uml2.DeltaReplacedTrace" containment="true"
	 * @generated
	 */
	EList getDeltaReplacedTraces();


	/**
	 * Creates a {@link org.eclipse.uml2.DeltaReplacedTrace} and appends it to the '<em><b>Delta Replaced Traces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.DeltaReplacedTrace}.
	 * @see #getDeltaReplacedTraces()
	 * @generated
	 */
	DeltaReplacedTrace createDeltaReplacedTraces();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getDeltaReplacedTraces();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getDeltaReplacedTraces();


    /**
     * Creates a {@link org.eclipse.uml2.Connector} and appends it to the '<em><b>Owned Connector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.Connector} to create.
	 * @return The new {@link org.eclipse.uml2.Connector}.
	 * @see #getOwnedConnectors()
	 * @generated NOT
	 * @deprecated Use #createOwnedConnector() instead.
     */
    Connector createOwnedConnector(EClass eClass);

	/**
	 * Creates a {@link org.eclipse.uml2.Connector} and appends it to the '<em><b>Owned Connector</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.Connector}.
	 * @see #getOwnedConnectors()
	 * @generated
	 */
    Connector createOwnedConnector();

} // StructuredClassifier
