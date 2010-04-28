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
 * $Id: UML2Factory.java,v 1.2 2009-04-22 10:01:09 andrew Exp $
 */
package org.eclipse.uml2;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.uml2.UML2Package
 * @generated
 */
public interface UML2Factory extends EFactory{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UML2Factory eINSTANCE = new org.eclipse.uml2.impl.UML2FactoryImpl();

	/**
	 * Returns a new object of class '<em>Opaque Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Opaque Expression</em>'.
	 * @generated
	 */
	OpaqueExpression createOpaqueExpression();

	/**
	 * Returns a new object of class '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expression</em>'.
	 * @generated
	 */
	Expression createExpression();

	/**
	 * Returns a new object of class '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comment</em>'.
	 * @generated
	 */
	Comment createComment();

	/**
	 * Returns a new object of class '<em>Class</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Class</em>'.
	 * @generated
	 */
	org.eclipse.uml2.Class createClass();

	/**
	 * Returns a new object of class '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property</em>'.
	 * @generated
	 */
	Property createProperty();

	/**
	 * Returns a new object of class '<em>Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation</em>'.
	 * @generated
	 */
	Operation createOperation();

	/**
	 * Returns a new object of class '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter</em>'.
	 * @generated
	 */
	Parameter createParameter();

	/**
	 * Returns a new object of class '<em>Package</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Package</em>'.
	 * @generated
	 */
	org.eclipse.uml2.Package createPackage();

	/**
	 * Returns a new object of class '<em>Enumeration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enumeration</em>'.
	 * @generated
	 */
	Enumeration createEnumeration();

	/**
	 * Returns a new object of class '<em>Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Data Type</em>'.
	 * @generated
	 */
	DataType createDataType();

	/**
	 * Returns a new object of class '<em>Enumeration Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enumeration Literal</em>'.
	 * @generated
	 */
	EnumerationLiteral createEnumerationLiteral();

	/**
	 * Returns a new object of class '<em>Primitive Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Primitive Type</em>'.
	 * @generated
	 */
	PrimitiveType createPrimitiveType();

	/**
	 * Returns a new object of class '<em>Literal Boolean</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Literal Boolean</em>'.
	 * @generated
	 */
	LiteralBoolean createLiteralBoolean();

	/**
	 * Returns a new object of class '<em>Literal String</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Literal String</em>'.
	 * @generated
	 */
	LiteralString createLiteralString();

	/**
	 * Returns a new object of class '<em>Literal Null</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Literal Null</em>'.
	 * @generated
	 */
	LiteralNull createLiteralNull();

	/**
	 * Returns a new object of class '<em>Literal Integer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Literal Integer</em>'.
	 * @generated
	 */
	LiteralInteger createLiteralInteger();

	/**
	 * Returns a new object of class '<em>Literal Unlimited Natural</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Literal Unlimited Natural</em>'.
	 * @generated
	 */
	LiteralUnlimitedNatural createLiteralUnlimitedNatural();

	/**
	 * Returns a new object of class '<em>Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constraint</em>'.
	 * @generated
	 */
	Constraint createConstraint();

	/**
	 * Returns a new object of class '<em>Instance Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Instance Specification</em>'.
	 * @generated
	 */
	InstanceSpecification createInstanceSpecification();

	/**
	 * Returns a new object of class '<em>Slot</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Slot</em>'.
	 * @generated
	 */
	Slot createSlot();

	/**
	 * Returns a new object of class '<em>Instance Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Instance Value</em>'.
	 * @generated
	 */
	InstanceValue createInstanceValue();

	/**
	 * Returns a new object of class '<em>Generalization</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generalization</em>'.
	 * @generated
	 */
	Generalization createGeneralization();

	/**
	 * Returns a new object of class '<em>Element Import</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Element Import</em>'.
	 * @generated
	 */
	ElementImport createElementImport();

	/**
	 * Returns a new object of class '<em>Package Import</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Package Import</em>'.
	 * @generated
	 */
	PackageImport createPackageImport();

	/**
	 * Returns a new object of class '<em>Association</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Association</em>'.
	 * @generated
	 */
	Association createAssociation();

	/**
	 * Returns a new object of class '<em>Package Merge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Package Merge</em>'.
	 * @generated
	 */
	PackageMerge createPackageMerge();

	/**
	 * Returns a new object of class '<em>Stereotype</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Stereotype</em>'.
	 * @generated
	 */
	Stereotype createStereotype();

	/**
	 * Returns a new object of class '<em>Profile</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Profile</em>'.
	 * @generated
	 */
	Profile createProfile();

	/**
	 * Returns a new object of class '<em>Profile Application</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Profile Application</em>'.
	 * @generated
	 */
	ProfileApplication createProfileApplication();

	/**
	 * Returns a new object of class '<em>Extension</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extension</em>'.
	 * @generated
	 */
	Extension createExtension();

	/**
	 * Returns a new object of class '<em>Extension End</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extension End</em>'.
	 * @generated
	 */
	ExtensionEnd createExtensionEnd();

	/**
	 * Returns a new object of class '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model</em>'.
	 * @generated
	 */
	Model createModel();

	/**
	 * Returns a new object of class '<em>Information Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Information Item</em>'.
	 * @generated
	 */
	InformationItem createInformationItem();

	/**
	 * Returns a new object of class '<em>Information Flow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Information Flow</em>'.
	 * @generated
	 */
	InformationFlow createInformationFlow();

	/**
	 * Returns a new object of class '<em>Association Class</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Association Class</em>'.
	 * @generated
	 */
	AssociationClass createAssociationClass();

	/**
	 * Returns a new object of class '<em>Permission</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Permission</em>'.
	 * @generated
	 */
	Permission createPermission();

	/**
	 * Returns a new object of class '<em>Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dependency</em>'.
	 * @generated
	 */
	Dependency createDependency();

	/**
	 * Returns a new object of class '<em>Usage</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Usage</em>'.
	 * @generated
	 */
	Usage createUsage();

	/**
	 * Returns a new object of class '<em>Abstraction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abstraction</em>'.
	 * @generated
	 */
	Abstraction createAbstraction();

	/**
	 * Returns a new object of class '<em>Realization</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Realization</em>'.
	 * @generated
	 */
	Realization createRealization();

	/**
	 * Returns a new object of class '<em>Substitution</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Substitution</em>'.
	 * @generated
	 */
	Substitution createSubstitution();

	/**
	 * Returns a new object of class '<em>Activity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Activity</em>'.
	 * @generated
	 */
	Activity createActivity();

	/**
	 * Returns a new object of class '<em>Generalization Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generalization Set</em>'.
	 * @generated
	 */
	GeneralizationSet createGeneralizationSet();

	/**
	 * Returns a new object of class '<em>Artifact</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Artifact</em>'.
	 * @generated
	 */
	Artifact createArtifact();

	/**
	 * Returns a new object of class '<em>Manifestation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Manifestation</em>'.
	 * @generated
	 */
	Manifestation createManifestation();

	/**
	 * Returns a new object of class '<em>Control Flow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Control Flow</em>'.
	 * @generated
	 */
	ControlFlow createControlFlow();

	/**
	 * Returns a new object of class '<em>Object Flow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Object Flow</em>'.
	 * @generated
	 */
	ObjectFlow createObjectFlow();

	/**
	 * Returns a new object of class '<em>Initial Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Initial Node</em>'.
	 * @generated
	 */
	InitialNode createInitialNode();

	/**
	 * Returns a new object of class '<em>Activity Final Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Activity Final Node</em>'.
	 * @generated
	 */
	ActivityFinalNode createActivityFinalNode();

	/**
	 * Returns a new object of class '<em>Decision Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Decision Node</em>'.
	 * @generated
	 */
	DecisionNode createDecisionNode();

	/**
	 * Returns a new object of class '<em>Merge Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Merge Node</em>'.
	 * @generated
	 */
	MergeNode createMergeNode();

	/**
	 * Returns a new object of class '<em>Output Pin</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Output Pin</em>'.
	 * @generated
	 */
	OutputPin createOutputPin();

	/**
	 * Returns a new object of class '<em>Input Pin</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Input Pin</em>'.
	 * @generated
	 */
	InputPin createInputPin();

	/**
	 * Returns a new object of class '<em>Activity Parameter Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Activity Parameter Node</em>'.
	 * @generated
	 */
	ActivityParameterNode createActivityParameterNode();

	/**
	 * Returns a new object of class '<em>Value Pin</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Value Pin</em>'.
	 * @generated
	 */
	ValuePin createValuePin();

	/**
	 * Returns a new object of class '<em>Interface</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interface</em>'.
	 * @generated
	 */
	Interface createInterface();

	/**
	 * Returns a new object of class '<em>Implementation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Implementation</em>'.
	 * @generated
	 */
	Implementation createImplementation();

	/**
	 * Returns a new object of class '<em>Actor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Actor</em>'.
	 * @generated
	 */
	Actor createActor();

	/**
	 * Returns a new object of class '<em>Extend</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extend</em>'.
	 * @generated
	 */
	Extend createExtend();

	/**
	 * Returns a new object of class '<em>Use Case</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Use Case</em>'.
	 * @generated
	 */
	UseCase createUseCase();

	/**
	 * Returns a new object of class '<em>Extension Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extension Point</em>'.
	 * @generated
	 */
	ExtensionPoint createExtensionPoint();

	/**
	 * Returns a new object of class '<em>Include</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Include</em>'.
	 * @generated
	 */
	Include createInclude();

	/**
	 * Returns a new object of class '<em>Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action</em>'.
	 * @generated
	 */
	Action createAction();

	/**
	 * Returns a new object of class '<em>Call Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Call Trigger</em>'.
	 * @generated
	 */
	CallTrigger createCallTrigger();

	/**
	 * Returns a new object of class '<em>Change Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Change Trigger</em>'.
	 * @generated
	 */
	ChangeTrigger createChangeTrigger();

	/**
	 * Returns a new object of class '<em>Reception</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reception</em>'.
	 * @generated
	 */
	Reception createReception();

	/**
	 * Returns a new object of class '<em>Signal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Signal</em>'.
	 * @generated
	 */
	Signal createSignal();

	/**
	 * Returns a new object of class '<em>Signal Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Signal Trigger</em>'.
	 * @generated
	 */
	SignalTrigger createSignalTrigger();

	/**
	 * Returns a new object of class '<em>Time Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Trigger</em>'.
	 * @generated
	 */
	TimeTrigger createTimeTrigger();

	/**
	 * Returns a new object of class '<em>Any Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Any Trigger</em>'.
	 * @generated
	 */
	AnyTrigger createAnyTrigger();

	/**
	 * Returns a new object of class '<em>Connector End</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connector End</em>'.
	 * @generated
	 */
	ConnectorEnd createConnectorEnd();

	/**
	 * Returns a new object of class '<em>Connector</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connector</em>'.
	 * @generated
	 */
	Connector createConnector();

	/**
	 * Returns a new object of class '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable</em>'.
	 * @generated
	 */
	Variable createVariable();

	/**
	 * Returns a new object of class '<em>Structured Activity Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Structured Activity Node</em>'.
	 * @generated
	 */
	StructuredActivityNode createStructuredActivityNode();

	/**
	 * Returns a new object of class '<em>Conditional Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Conditional Node</em>'.
	 * @generated
	 */
	ConditionalNode createConditionalNode();

	/**
	 * Returns a new object of class '<em>Clause</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Clause</em>'.
	 * @generated
	 */
	Clause createClause();

	/**
	 * Returns a new object of class '<em>Loop Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Loop Node</em>'.
	 * @generated
	 */
	LoopNode createLoopNode();

	/**
	 * Returns a new object of class '<em>State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Machine</em>'.
	 * @generated
	 */
	StateMachine createStateMachine();

	/**
	 * Returns a new object of class '<em>Region</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Region</em>'.
	 * @generated
	 */
	Region createRegion();

	/**
	 * Returns a new object of class '<em>Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pseudostate</em>'.
	 * @generated
	 */
	Pseudostate createPseudostate();

	/**
	 * Returns a new object of class '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State</em>'.
	 * @generated
	 */
	State createState();

	/**
	 * Returns a new object of class '<em>Connection Point Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connection Point Reference</em>'.
	 * @generated
	 */
	ConnectionPointReference createConnectionPointReference();

	/**
	 * Returns a new object of class '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transition</em>'.
	 * @generated
	 */
	Transition createTransition();

	/**
	 * Returns a new object of class '<em>Final State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Final State</em>'.
	 * @generated
	 */
	FinalState createFinalState();

	/**
	 * Returns a new object of class '<em>Expansion Region</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expansion Region</em>'.
	 * @generated
	 */
	ExpansionRegion createExpansionRegion();

	/**
	 * Returns a new object of class '<em>Exception Handler</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exception Handler</em>'.
	 * @generated
	 */
	ExceptionHandler createExceptionHandler();

	/**
	 * Returns a new object of class '<em>Port</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Port</em>'.
	 * @generated
	 */
	Port createPort();

	/**
	 * Returns a new object of class '<em>Create Object Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Create Object Action</em>'.
	 * @generated
	 */
	CreateObjectAction createCreateObjectAction();

	/**
	 * Returns a new object of class '<em>Destroy Object Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Destroy Object Action</em>'.
	 * @generated
	 */
	DestroyObjectAction createDestroyObjectAction();

	/**
	 * Returns a new object of class '<em>Test Identity Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Identity Action</em>'.
	 * @generated
	 */
	TestIdentityAction createTestIdentityAction();

	/**
	 * Returns a new object of class '<em>Read Self Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Read Self Action</em>'.
	 * @generated
	 */
	ReadSelfAction createReadSelfAction();

	/**
	 * Returns a new object of class '<em>Read Structural Feature Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Read Structural Feature Action</em>'.
	 * @generated
	 */
	ReadStructuralFeatureAction createReadStructuralFeatureAction();

	/**
	 * Returns a new object of class '<em>Clear Structural Feature Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Clear Structural Feature Action</em>'.
	 * @generated
	 */
	ClearStructuralFeatureAction createClearStructuralFeatureAction();

	/**
	 * Returns a new object of class '<em>Remove Structural Feature Value Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remove Structural Feature Value Action</em>'.
	 * @generated
	 */
	RemoveStructuralFeatureValueAction createRemoveStructuralFeatureValueAction();

	/**
	 * Returns a new object of class '<em>Add Structural Feature Value Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Add Structural Feature Value Action</em>'.
	 * @generated
	 */
	AddStructuralFeatureValueAction createAddStructuralFeatureValueAction();

	/**
	 * Returns a new object of class '<em>Link End Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Link End Data</em>'.
	 * @generated
	 */
	LinkEndData createLinkEndData();

	/**
	 * Returns a new object of class '<em>Read Link Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Read Link Action</em>'.
	 * @generated
	 */
	ReadLinkAction createReadLinkAction();

	/**
	 * Returns a new object of class '<em>Link End Creation Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Link End Creation Data</em>'.
	 * @generated
	 */
	LinkEndCreationData createLinkEndCreationData();

	/**
	 * Returns a new object of class '<em>Create Link Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Create Link Action</em>'.
	 * @generated
	 */
	CreateLinkAction createCreateLinkAction();

	/**
	 * Returns a new object of class '<em>Destroy Link Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Destroy Link Action</em>'.
	 * @generated
	 */
	DestroyLinkAction createDestroyLinkAction();

	/**
	 * Returns a new object of class '<em>Clear Association Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Clear Association Action</em>'.
	 * @generated
	 */
	ClearAssociationAction createClearAssociationAction();

	/**
	 * Returns a new object of class '<em>Read Variable Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Read Variable Action</em>'.
	 * @generated
	 */
	ReadVariableAction createReadVariableAction();

	/**
	 * Returns a new object of class '<em>Clear Variable Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Clear Variable Action</em>'.
	 * @generated
	 */
	ClearVariableAction createClearVariableAction();

	/**
	 * Returns a new object of class '<em>Add Variable Value Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Add Variable Value Action</em>'.
	 * @generated
	 */
	AddVariableValueAction createAddVariableValueAction();

	/**
	 * Returns a new object of class '<em>Remove Variable Value Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remove Variable Value Action</em>'.
	 * @generated
	 */
	RemoveVariableValueAction createRemoveVariableValueAction();

	/**
	 * Returns a new object of class '<em>Apply Function Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Apply Function Action</em>'.
	 * @generated
	 */
	ApplyFunctionAction createApplyFunctionAction();

	/**
	 * Returns a new object of class '<em>Primitive Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Primitive Function</em>'.
	 * @generated
	 */
	PrimitiveFunction createPrimitiveFunction();

	/**
	 * Returns a new object of class '<em>Send Signal Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Send Signal Action</em>'.
	 * @generated
	 */
	SendSignalAction createSendSignalAction();

	/**
	 * Returns a new object of class '<em>Broadcast Signal Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Broadcast Signal Action</em>'.
	 * @generated
	 */
	BroadcastSignalAction createBroadcastSignalAction();

	/**
	 * Returns a new object of class '<em>Send Object Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Send Object Action</em>'.
	 * @generated
	 */
	SendObjectAction createSendObjectAction();

	/**
	 * Returns a new object of class '<em>Call Operation Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Call Operation Action</em>'.
	 * @generated
	 */
	CallOperationAction createCallOperationAction();

	/**
	 * Returns a new object of class '<em>Call Behavior Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Call Behavior Action</em>'.
	 * @generated
	 */
	CallBehaviorAction createCallBehaviorAction();

	/**
	 * Returns a new object of class '<em>Fork Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Fork Node</em>'.
	 * @generated
	 */
	ForkNode createForkNode();

	/**
	 * Returns a new object of class '<em>Join Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Join Node</em>'.
	 * @generated
	 */
	JoinNode createJoinNode();

	/**
	 * Returns a new object of class '<em>Flow Final Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Flow Final Node</em>'.
	 * @generated
	 */
	FlowFinalNode createFlowFinalNode();

	/**
	 * Returns a new object of class '<em>Central Buffer Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Central Buffer Node</em>'.
	 * @generated
	 */
	CentralBufferNode createCentralBufferNode();

	/**
	 * Returns a new object of class '<em>Activity Partition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Activity Partition</em>'.
	 * @generated
	 */
	ActivityPartition createActivityPartition();

	/**
	 * Returns a new object of class '<em>Template Signature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Template Signature</em>'.
	 * @generated
	 */
	TemplateSignature createTemplateSignature();

	/**
	 * Returns a new object of class '<em>Template Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Template Parameter</em>'.
	 * @generated
	 */
	TemplateParameter createTemplateParameter();

	/**
	 * Returns a new object of class '<em>String Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Expression</em>'.
	 * @generated
	 */
	StringExpression createStringExpression();

	/**
	 * Returns a new object of class '<em>Template Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Template Binding</em>'.
	 * @generated
	 */
	TemplateBinding createTemplateBinding();

	/**
	 * Returns a new object of class '<em>Template Parameter Substitution</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Template Parameter Substitution</em>'.
	 * @generated
	 */
	TemplateParameterSubstitution createTemplateParameterSubstitution();

	/**
	 * Returns a new object of class '<em>Collaboration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Collaboration</em>'.
	 * @generated
	 */
	Collaboration createCollaboration();

	/**
	 * Returns a new object of class '<em>Operation Template Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation Template Parameter</em>'.
	 * @generated
	 */
	OperationTemplateParameter createOperationTemplateParameter();

	/**
	 * Returns a new object of class '<em>Classifier Template Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Classifier Template Parameter</em>'.
	 * @generated
	 */
	ClassifierTemplateParameter createClassifierTemplateParameter();

	/**
	 * Returns a new object of class '<em>Redefinable Template Signature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Redefinable Template Signature</em>'.
	 * @generated
	 */
	RedefinableTemplateSignature createRedefinableTemplateSignature();

	/**
	 * Returns a new object of class '<em>Connectable Element Template Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connectable Element Template Parameter</em>'.
	 * @generated
	 */
	ConnectableElementTemplateParameter createConnectableElementTemplateParameter();

	/**
	 * Returns a new object of class '<em>Interaction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interaction</em>'.
	 * @generated
	 */
	Interaction createInteraction();

	/**
	 * Returns a new object of class '<em>Lifeline</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Lifeline</em>'.
	 * @generated
	 */
	Lifeline createLifeline();

	/**
	 * Returns a new object of class '<em>Message</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Message</em>'.
	 * @generated
	 */
	Message createMessage();

	/**
	 * Returns a new object of class '<em>General Ordering</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>General Ordering</em>'.
	 * @generated
	 */
	GeneralOrdering createGeneralOrdering();

	/**
	 * Returns a new object of class '<em>Event Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Occurrence</em>'.
	 * @generated
	 */
	EventOccurrence createEventOccurrence();

	/**
	 * Returns a new object of class '<em>Execution Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Execution Occurrence</em>'.
	 * @generated
	 */
	ExecutionOccurrence createExecutionOccurrence();

	/**
	 * Returns a new object of class '<em>State Invariant</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Invariant</em>'.
	 * @generated
	 */
	StateInvariant createStateInvariant();

	/**
	 * Returns a new object of class '<em>Stop</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Stop</em>'.
	 * @generated
	 */
	Stop createStop();

	/**
	 * Returns a new object of class '<em>Collaboration Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Collaboration Occurrence</em>'.
	 * @generated
	 */
	CollaborationOccurrence createCollaborationOccurrence();

	/**
	 * Returns a new object of class '<em>Data Store Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Data Store Node</em>'.
	 * @generated
	 */
	DataStoreNode createDataStoreNode();

	/**
	 * Returns a new object of class '<em>Interruptible Activity Region</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interruptible Activity Region</em>'.
	 * @generated
	 */
	InterruptibleActivityRegion createInterruptibleActivityRegion();

	/**
	 * Returns a new object of class '<em>Parameter Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter Set</em>'.
	 * @generated
	 */
	ParameterSet createParameterSet();

	/**
	 * Returns a new object of class '<em>Read Extent Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Read Extent Action</em>'.
	 * @generated
	 */
	ReadExtentAction createReadExtentAction();

	/**
	 * Returns a new object of class '<em>Reclassify Object Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reclassify Object Action</em>'.
	 * @generated
	 */
	ReclassifyObjectAction createReclassifyObjectAction();

	/**
	 * Returns a new object of class '<em>Read Is Classified Object Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Read Is Classified Object Action</em>'.
	 * @generated
	 */
	ReadIsClassifiedObjectAction createReadIsClassifiedObjectAction();

	/**
	 * Returns a new object of class '<em>Start Owned Behavior Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Start Owned Behavior Action</em>'.
	 * @generated
	 */
	StartOwnedBehaviorAction createStartOwnedBehaviorAction();

	/**
	 * Returns a new object of class '<em>Qualifier Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Qualifier Value</em>'.
	 * @generated
	 */
	QualifierValue createQualifierValue();

	/**
	 * Returns a new object of class '<em>Read Link Object End Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Read Link Object End Action</em>'.
	 * @generated
	 */
	ReadLinkObjectEndAction createReadLinkObjectEndAction();

	/**
	 * Returns a new object of class '<em>Read Link Object End Qualifier Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Read Link Object End Qualifier Action</em>'.
	 * @generated
	 */
	ReadLinkObjectEndQualifierAction createReadLinkObjectEndQualifierAction();

	/**
	 * Returns a new object of class '<em>Create Link Object Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Create Link Object Action</em>'.
	 * @generated
	 */
	CreateLinkObjectAction createCreateLinkObjectAction();

	/**
	 * Returns a new object of class '<em>Accept Event Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Accept Event Action</em>'.
	 * @generated
	 */
	AcceptEventAction createAcceptEventAction();

	/**
	 * Returns a new object of class '<em>Accept Call Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Accept Call Action</em>'.
	 * @generated
	 */
	AcceptCallAction createAcceptCallAction();

	/**
	 * Returns a new object of class '<em>Reply Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Reply Action</em>'.
	 * @generated
	 */
	ReplyAction createReplyAction();

	/**
	 * Returns a new object of class '<em>Raise Exception Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Raise Exception Action</em>'.
	 * @generated
	 */
	RaiseExceptionAction createRaiseExceptionAction();

	/**
	 * Returns a new object of class '<em>Time Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Expression</em>'.
	 * @generated
	 */
	TimeExpression createTimeExpression();

	/**
	 * Returns a new object of class '<em>Duration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Duration</em>'.
	 * @generated
	 */
	Duration createDuration();

	/**
	 * Returns a new object of class '<em>Time Observation Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Observation Action</em>'.
	 * @generated
	 */
	TimeObservationAction createTimeObservationAction();

	/**
	 * Returns a new object of class '<em>Duration Interval</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Duration Interval</em>'.
	 * @generated
	 */
	DurationInterval createDurationInterval();

	/**
	 * Returns a new object of class '<em>Interval</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interval</em>'.
	 * @generated
	 */
	Interval createInterval();

	/**
	 * Returns a new object of class '<em>Time Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Constraint</em>'.
	 * @generated
	 */
	TimeConstraint createTimeConstraint();

	/**
	 * Returns a new object of class '<em>Interval Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interval Constraint</em>'.
	 * @generated
	 */
	IntervalConstraint createIntervalConstraint();

	/**
	 * Returns a new object of class '<em>Time Interval</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Interval</em>'.
	 * @generated
	 */
	TimeInterval createTimeInterval();

	/**
	 * Returns a new object of class '<em>Duration Observation Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Duration Observation Action</em>'.
	 * @generated
	 */
	DurationObservationAction createDurationObservationAction();

	/**
	 * Returns a new object of class '<em>Duration Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Duration Constraint</em>'.
	 * @generated
	 */
	DurationConstraint createDurationConstraint();

	/**
	 * Returns a new object of class '<em>Protocol Conformance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Protocol Conformance</em>'.
	 * @generated
	 */
	ProtocolConformance createProtocolConformance();

	/**
	 * Returns a new object of class '<em>Protocol State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Protocol State Machine</em>'.
	 * @generated
	 */
	ProtocolStateMachine createProtocolStateMachine();

	/**
	 * Returns a new object of class '<em>Protocol Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Protocol Transition</em>'.
	 * @generated
	 */
	ProtocolTransition createProtocolTransition();

	/**
	 * Returns a new object of class '<em>Interaction Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interaction Occurrence</em>'.
	 * @generated
	 */
	InteractionOccurrence createInteractionOccurrence();

	/**
	 * Returns a new object of class '<em>Gate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Gate</em>'.
	 * @generated
	 */
	Gate createGate();

	/**
	 * Returns a new object of class '<em>Part Decomposition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Part Decomposition</em>'.
	 * @generated
	 */
	PartDecomposition createPartDecomposition();

	/**
	 * Returns a new object of class '<em>Interaction Operand</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interaction Operand</em>'.
	 * @generated
	 */
	InteractionOperand createInteractionOperand();

	/**
	 * Returns a new object of class '<em>Interaction Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interaction Constraint</em>'.
	 * @generated
	 */
	InteractionConstraint createInteractionConstraint();

	/**
	 * Returns a new object of class '<em>Combined Fragment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Combined Fragment</em>'.
	 * @generated
	 */
	CombinedFragment createCombinedFragment();

	/**
	 * Returns a new object of class '<em>Continuation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Continuation</em>'.
	 * @generated
	 */
	Continuation createContinuation();

	/**
	 * Returns a new object of class '<em>Expansion Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expansion Node</em>'.
	 * @generated
	 */
	ExpansionNode createExpansionNode();

	/**
	 * Returns a new object of class '<em>Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Component</em>'.
	 * @generated
	 */
	Component createComponent();

	/**
	 * Returns a new object of class '<em>Deployment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deployment</em>'.
	 * @generated
	 */
	Deployment createDeployment();

	/**
	 * Returns a new object of class '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node</em>'.
	 * @generated
	 */
	Node createNode();

	/**
	 * Returns a new object of class '<em>Device</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Device</em>'.
	 * @generated
	 */
	Device createDevice();

	/**
	 * Returns a new object of class '<em>Execution Environment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Execution Environment</em>'.
	 * @generated
	 */
	ExecutionEnvironment createExecutionEnvironment();

	/**
	 * Returns a new object of class '<em>Communication Path</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Communication Path</em>'.
	 * @generated
	 */
	CommunicationPath createCommunicationPath();

	/**
	 * Returns a new object of class '<em>Deployment Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deployment Specification</em>'.
	 * @generated
	 */
	DeploymentSpecification createDeploymentSpecification();

	/**
	 * Returns a new object of class '<em>JFigure Container</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JFigure Container</em>'.
	 * @generated
	 */
  J_FigureContainer createJ_FigureContainer();

	/**
	 * Returns a new object of class '<em>JFigure</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JFigure</em>'.
	 * @generated
	 */
	J_Figure createJ_Figure();

	/**
	 * Returns a new object of class '<em>JProperty</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JProperty</em>'.
	 * @generated
	 */
	J_Property createJ_Property();

	/**
	 * Returns a new object of class '<em>JDiagram</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JDiagram</em>'.
	 * @generated
	 */
	J_Diagram createJ_Diagram();

	/**
	 * Returns a new object of class '<em>JDiagram Holder</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JDiagram Holder</em>'.
	 * @generated
	 */
	J_DiagramHolder createJ_DiagramHolder();

	/**
	 * Returns a new object of class '<em>Applied Basic Stereotype Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Applied Basic Stereotype Value</em>'.
	 * @generated
	 */
	AppliedBasicStereotypeValue createAppliedBasicStereotypeValue();

	/**
	 * Returns a new object of class '<em>Property Value Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Value Specification</em>'.
	 * @generated
	 */
	PropertyValueSpecification createPropertyValueSpecification();

	/**
	 * Returns a new object of class '<em>Delta Replaced Constituent</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Replaced Constituent</em>'.
	 * @generated
	 */
	DeltaReplacedConstituent createDeltaReplacedConstituent();

	/**
	 * Returns a new object of class '<em>Delta Deleted Constituent</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Deleted Constituent</em>'.
	 * @generated
	 */
	DeltaDeletedConstituent createDeltaDeletedConstituent();

	/**
	 * Returns a new object of class '<em>Delta Replaced Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Replaced Attribute</em>'.
	 * @generated
	 */
	DeltaReplacedAttribute createDeltaReplacedAttribute();

	/**
	 * Returns a new object of class '<em>Delta Deleted Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Deleted Attribute</em>'.
	 * @generated
	 */
	DeltaDeletedAttribute createDeltaDeletedAttribute();

	/**
	 * Returns a new object of class '<em>Delta Replaced Port</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Replaced Port</em>'.
	 * @generated
	 */
	DeltaReplacedPort createDeltaReplacedPort();

	/**
	 * Returns a new object of class '<em>Delta Deleted Port</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Deleted Port</em>'.
	 * @generated
	 */
	DeltaDeletedPort createDeltaDeletedPort();

	/**
	 * Returns a new object of class '<em>Delta Replaced Connector</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Replaced Connector</em>'.
	 * @generated
	 */
	DeltaReplacedConnector createDeltaReplacedConnector();

	/**
	 * Returns a new object of class '<em>Delta Deleted Connector</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Deleted Connector</em>'.
	 * @generated
	 */
	DeltaDeletedConnector createDeltaDeletedConnector();

	/**
	 * Returns a new object of class '<em>Delta Replaced Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Replaced Operation</em>'.
	 * @generated
	 */
	DeltaReplacedOperation createDeltaReplacedOperation();

	/**
	 * Returns a new object of class '<em>Delta Deleted Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Deleted Operation</em>'.
	 * @generated
	 */
	DeltaDeletedOperation createDeltaDeletedOperation();

	/**
	 * Returns a new object of class '<em>Port Remap</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Port Remap</em>'.
	 * @generated
	 */
  PortRemap createPortRemap();

	/**
	 * Returns a new object of class '<em>Saved Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Saved Reference</em>'.
	 * @generated
	 */
	SavedReference createSavedReference();

	/**
	 * Returns a new object of class '<em>Requirements Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Requirements Feature</em>'.
	 * @generated
	 */
	RequirementsFeature createRequirementsFeature();

	/**
	 * Returns a new object of class '<em>Requirements Feature Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Requirements Feature Link</em>'.
	 * @generated
	 */
	RequirementsFeatureLink createRequirementsFeatureLink();

	/**
	 * Returns a new object of class '<em>Delta Replaced Requirements Feature Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Replaced Requirements Feature Link</em>'.
	 * @generated
	 */
	DeltaReplacedRequirementsFeatureLink createDeltaReplacedRequirementsFeatureLink();

	/**
	 * Returns a new object of class '<em>Delta Deleted Requirements Feature Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Deleted Requirements Feature Link</em>'.
	 * @generated
	 */
	DeltaDeletedRequirementsFeatureLink createDeltaDeletedRequirementsFeatureLink();

	/**
	 * Returns a new object of class '<em>Delta Deleted Trace</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Deleted Trace</em>'.
	 * @generated
	 */
	DeltaDeletedTrace createDeltaDeletedTrace();

	/**
	 * Returns a new object of class '<em>Delta Replaced Trace</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Delta Replaced Trace</em>'.
	 * @generated
	 */
	DeltaReplacedTrace createDeltaReplacedTrace();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UML2Package getUML2Package();

} //UML2Factory
