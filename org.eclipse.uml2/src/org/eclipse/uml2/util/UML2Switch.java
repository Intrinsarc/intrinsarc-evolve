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
 * $Id: UML2Switch.java,v 1.2 2009-04-22 10:01:08 andrew Exp $
 */
package org.eclipse.uml2.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.uml2.Abstraction;
import org.eclipse.uml2.AcceptCallAction;
import org.eclipse.uml2.AcceptEventAction;
import org.eclipse.uml2.Action;
import org.eclipse.uml2.Activity;
import org.eclipse.uml2.ActivityEdge;
import org.eclipse.uml2.ActivityFinalNode;
import org.eclipse.uml2.ActivityGroup;
import org.eclipse.uml2.ActivityNode;
import org.eclipse.uml2.ActivityParameterNode;
import org.eclipse.uml2.ActivityPartition;
import org.eclipse.uml2.Actor;
import org.eclipse.uml2.AddStructuralFeatureValueAction;
import org.eclipse.uml2.AddVariableValueAction;
import org.eclipse.uml2.AnyTrigger;
import org.eclipse.uml2.AppliedBasicStereotypeValue;
import org.eclipse.uml2.ApplyFunctionAction;
import org.eclipse.uml2.Artifact;
import org.eclipse.uml2.Association;
import org.eclipse.uml2.AssociationClass;
import org.eclipse.uml2.Behavior;
import org.eclipse.uml2.BehavioralFeature;
import org.eclipse.uml2.BehavioredClassifier;
import org.eclipse.uml2.BroadcastSignalAction;
import org.eclipse.uml2.CallAction;
import org.eclipse.uml2.CallBehaviorAction;
import org.eclipse.uml2.CallOperationAction;
import org.eclipse.uml2.CallTrigger;
import org.eclipse.uml2.CentralBufferNode;
import org.eclipse.uml2.ChangeTrigger;
import org.eclipse.uml2.Classifier;
import org.eclipse.uml2.ClassifierTemplateParameter;
import org.eclipse.uml2.Clause;
import org.eclipse.uml2.ClearAssociationAction;
import org.eclipse.uml2.ClearStructuralFeatureAction;
import org.eclipse.uml2.ClearVariableAction;
import org.eclipse.uml2.Collaboration;
import org.eclipse.uml2.CollaborationOccurrence;
import org.eclipse.uml2.CombinedFragment;
import org.eclipse.uml2.Comment;
import org.eclipse.uml2.CommunicationPath;
import org.eclipse.uml2.Component;
import org.eclipse.uml2.ConditionalNode;
import org.eclipse.uml2.ConnectableElement;
import org.eclipse.uml2.ConnectableElementTemplateParameter;
import org.eclipse.uml2.ConnectionPointReference;
import org.eclipse.uml2.Connector;
import org.eclipse.uml2.ConnectorEnd;
import org.eclipse.uml2.Constraint;
import org.eclipse.uml2.Continuation;
import org.eclipse.uml2.ControlFlow;
import org.eclipse.uml2.ControlNode;
import org.eclipse.uml2.CreateLinkAction;
import org.eclipse.uml2.CreateLinkObjectAction;
import org.eclipse.uml2.CreateObjectAction;
import org.eclipse.uml2.DataStoreNode;
import org.eclipse.uml2.DataType;
import org.eclipse.uml2.DecisionNode;
import org.eclipse.uml2.DeltaDeletedAttribute;
import org.eclipse.uml2.DeltaDeletedConnector;
import org.eclipse.uml2.DeltaDeletedConstituent;
import org.eclipse.uml2.DeltaDeletedOperation;
import org.eclipse.uml2.DeltaDeletedPort;
import org.eclipse.uml2.DeltaDeletedRequirementsFeatureLink;
import org.eclipse.uml2.DeltaDeletedTrace;
import org.eclipse.uml2.DeltaReplacedConstituent;
import org.eclipse.uml2.DeltaReplacedOperation;
import org.eclipse.uml2.DeltaReplacedPort;
import org.eclipse.uml2.DeltaReplacedRequirementsFeatureLink;
import org.eclipse.uml2.DeltaReplacedTrace;
import org.eclipse.uml2.DeltaReplacedAttribute;
import org.eclipse.uml2.DeltaReplacedConnector;
import org.eclipse.uml2.Dependency;
import org.eclipse.uml2.DeployedArtifact;
import org.eclipse.uml2.Deployment;
import org.eclipse.uml2.DeploymentSpecification;
import org.eclipse.uml2.DeploymentTarget;
import org.eclipse.uml2.DestroyLinkAction;
import org.eclipse.uml2.DestroyObjectAction;
import org.eclipse.uml2.Device;
import org.eclipse.uml2.DirectedRelationship;
import org.eclipse.uml2.Duration;
import org.eclipse.uml2.DurationConstraint;
import org.eclipse.uml2.DurationInterval;
import org.eclipse.uml2.DurationObservationAction;
import org.eclipse.uml2.Element;
import org.eclipse.uml2.ElementImport;
import org.eclipse.uml2.EncapsulatedClassifier;
import java.util.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Enumeration;

import org.eclipse.uml2.EnumerationLiteral;
import org.eclipse.uml2.EventOccurrence;
import org.eclipse.uml2.ExceptionHandler;
import org.eclipse.uml2.ExecutableNode;
import org.eclipse.uml2.ExecutionEnvironment;
import org.eclipse.uml2.ExecutionOccurrence;
import org.eclipse.uml2.ExpansionNode;
import org.eclipse.uml2.ExpansionRegion;
import org.eclipse.uml2.Expression;
import org.eclipse.uml2.Extend;
import org.eclipse.uml2.Extension;
import org.eclipse.uml2.ExtensionEnd;
import org.eclipse.uml2.ExtensionPoint;
import org.eclipse.uml2.Feature;
import org.eclipse.uml2.FinalNode;
import org.eclipse.uml2.FinalState;
import org.eclipse.uml2.FlowFinalNode;
import org.eclipse.uml2.ForkNode;
import org.eclipse.uml2.Gate;
import org.eclipse.uml2.GeneralOrdering;
import org.eclipse.uml2.Generalization;
import org.eclipse.uml2.GeneralizationSet;
import org.eclipse.uml2.Implementation;
import org.eclipse.uml2.Include;
import org.eclipse.uml2.InformationFlow;
import org.eclipse.uml2.InformationItem;
import org.eclipse.uml2.InitialNode;
import org.eclipse.uml2.InputPin;
import org.eclipse.uml2.InstanceSpecification;
import org.eclipse.uml2.InstanceValue;
import org.eclipse.uml2.Interaction;
import org.eclipse.uml2.InteractionConstraint;
import org.eclipse.uml2.InteractionFragment;
import org.eclipse.uml2.InteractionOccurrence;
import org.eclipse.uml2.InteractionOperand;
import org.eclipse.uml2.Interface;
import org.eclipse.uml2.InterruptibleActivityRegion;
import org.eclipse.uml2.Interval;
import org.eclipse.uml2.IntervalConstraint;
import org.eclipse.uml2.InvocationAction;
import org.eclipse.uml2.J_Diagram;
import org.eclipse.uml2.J_DiagramHolder;
import org.eclipse.uml2.J_Figure;
import org.eclipse.uml2.J_FigureContainer;
import org.eclipse.uml2.J_Property;
import org.eclipse.uml2.JoinNode;
import org.eclipse.uml2.Lifeline;
import org.eclipse.uml2.LinkAction;
import org.eclipse.uml2.LinkEndCreationData;
import org.eclipse.uml2.LinkEndData;
import org.eclipse.uml2.LiteralBoolean;
import org.eclipse.uml2.LiteralInteger;
import org.eclipse.uml2.LiteralNull;
import org.eclipse.uml2.LiteralSpecification;
import org.eclipse.uml2.LiteralString;
import org.eclipse.uml2.LiteralUnlimitedNatural;
import org.eclipse.uml2.LoopNode;
import org.eclipse.uml2.Manifestation;
import org.eclipse.uml2.MergeNode;
import org.eclipse.uml2.Message;
import org.eclipse.uml2.MessageEnd;
import org.eclipse.uml2.MessageTrigger;
import org.eclipse.uml2.Model;
import org.eclipse.uml2.MultiplicityElement;
import org.eclipse.uml2.NamedElement;
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.Node;
import org.eclipse.uml2.ObjectFlow;
import org.eclipse.uml2.ObjectNode;
import org.eclipse.uml2.OpaqueExpression;
import org.eclipse.uml2.Operation;
import org.eclipse.uml2.OperationTemplateParameter;
import org.eclipse.uml2.OutputPin;
import org.eclipse.uml2.PackageImport;
import org.eclipse.uml2.PackageMerge;
import org.eclipse.uml2.PackageableElement;
import org.eclipse.uml2.Parameter;
import org.eclipse.uml2.ParameterSet;
import org.eclipse.uml2.ParameterableClassifier;
import org.eclipse.uml2.ParameterableElement;
import org.eclipse.uml2.PartDecomposition;
import org.eclipse.uml2.Permission;
import org.eclipse.uml2.Pin;
import org.eclipse.uml2.Port;
import org.eclipse.uml2.PortRemap;
import org.eclipse.uml2.PrimitiveFunction;
import org.eclipse.uml2.PrimitiveType;
import org.eclipse.uml2.Profile;
import org.eclipse.uml2.ProfileApplication;
import org.eclipse.uml2.Property;
import org.eclipse.uml2.PropertyValueSpecification;
import org.eclipse.uml2.ProtocolConformance;
import org.eclipse.uml2.ProtocolStateMachine;
import org.eclipse.uml2.ProtocolTransition;
import org.eclipse.uml2.Pseudostate;
import org.eclipse.uml2.QualifierValue;
import org.eclipse.uml2.RaiseExceptionAction;
import org.eclipse.uml2.ReadExtentAction;
import org.eclipse.uml2.ReadIsClassifiedObjectAction;
import org.eclipse.uml2.ReadLinkAction;
import org.eclipse.uml2.ReadLinkObjectEndAction;
import org.eclipse.uml2.ReadLinkObjectEndQualifierAction;
import org.eclipse.uml2.ReadSelfAction;
import org.eclipse.uml2.ReadStructuralFeatureAction;
import org.eclipse.uml2.ReadVariableAction;
import org.eclipse.uml2.Realization;
import org.eclipse.uml2.Reception;
import org.eclipse.uml2.ReclassifyObjectAction;
import org.eclipse.uml2.RedefinableElement;
import org.eclipse.uml2.RedefinableTemplateSignature;
import org.eclipse.uml2.Region;
import org.eclipse.uml2.Relationship;
import org.eclipse.uml2.RemoveStructuralFeatureValueAction;
import org.eclipse.uml2.RemoveVariableValueAction;
import org.eclipse.uml2.ReplyAction;
import org.eclipse.uml2.RequirementsFeature;
import org.eclipse.uml2.RequirementsFeatureLink;
import org.eclipse.uml2.SavedReference;
import org.eclipse.uml2.SendObjectAction;
import org.eclipse.uml2.SendSignalAction;
import org.eclipse.uml2.Signal;
import org.eclipse.uml2.SignalTrigger;
import org.eclipse.uml2.Slot;
import org.eclipse.uml2.StartOwnedBehaviorAction;
import org.eclipse.uml2.State;
import org.eclipse.uml2.StateInvariant;
import org.eclipse.uml2.StateMachine;
import org.eclipse.uml2.Stereotype;
import org.eclipse.uml2.Stop;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.StructuralFeature;
import org.eclipse.uml2.StructuralFeatureAction;
import org.eclipse.uml2.StructuredActivityNode;
import org.eclipse.uml2.StructuredClassifier;
import org.eclipse.uml2.Substitution;
import org.eclipse.uml2.TemplateBinding;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateParameterSubstitution;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.TemplateableClassifier;
import org.eclipse.uml2.TemplateableElement;
import org.eclipse.uml2.TestIdentityAction;
import org.eclipse.uml2.TimeConstraint;
import org.eclipse.uml2.TimeExpression;
import org.eclipse.uml2.TimeInterval;
import org.eclipse.uml2.TimeObservationAction;
import org.eclipse.uml2.TimeTrigger;
import org.eclipse.uml2.Transition;
import org.eclipse.uml2.Trigger;
import org.eclipse.uml2.Type;
import org.eclipse.uml2.TypedElement;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.Usage;
import org.eclipse.uml2.UseCase;
import org.eclipse.uml2.ValuePin;
import org.eclipse.uml2.ValueSpecification;
import org.eclipse.uml2.Variable;
import org.eclipse.uml2.VariableAction;
import org.eclipse.uml2.Vertex;
import org.eclipse.uml2.WriteLinkAction;
import org.eclipse.uml2.WriteStructuralFeatureAction;
import org.eclipse.uml2.WriteVariableAction;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.uml2.UML2Package
 * @generated
 */
public class UML2Switch {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static UML2Package modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UML2Switch()
	{
		if (modelPackage == null)
		{
			modelPackage = UML2Package.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public Object doSwitch(EObject theEObject)
	{
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(EClass theEClass, EObject theEObject)
	{
		if (theEClass.eContainer() == modelPackage)
		{
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else
		{
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(int classifierID, EObject theEObject)
	{
		switch (classifierID)
		{
			case UML2Package.ELEMENT:
			{
				Element element = (Element)theEObject;
				Object result = caseElement(element);
				if (result == null) result = caseEModelElement(element);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.MULTIPLICITY_ELEMENT:
			{
				MultiplicityElement multiplicityElement = (MultiplicityElement)theEObject;
				Object result = caseMultiplicityElement(multiplicityElement);
				if (result == null) result = caseUML2_Element(multiplicityElement);
				if (result == null) result = caseEModelElement(multiplicityElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.NAMED_ELEMENT:
			{
				NamedElement namedElement = (NamedElement)theEObject;
				Object result = caseNamedElement(namedElement);
				if (result == null) result = caseUML2_TemplateableElement(namedElement);
				if (result == null) result = caseUML2_Element(namedElement);
				if (result == null) result = caseEModelElement(namedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.NAMESPACE:
			{
				Namespace namespace = (Namespace)theEObject;
				Object result = caseNamespace(namespace);
				if (result == null) result = caseUML2_NamedElement(namespace);
				if (result == null) result = caseUML2_TemplateableElement(namespace);
				if (result == null) result = caseUML2_Element(namespace);
				if (result == null) result = caseEModelElement(namespace);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.OPAQUE_EXPRESSION:
			{
				OpaqueExpression opaqueExpression = (OpaqueExpression)theEObject;
				Object result = caseOpaqueExpression(opaqueExpression);
				if (result == null) result = caseUML2_ValueSpecification(opaqueExpression);
				if (result == null) result = caseUML2_TypedElement(opaqueExpression);
				if (result == null) result = caseUML2_ParameterableElement(opaqueExpression);
				if (result == null) result = caseUML2_NamedElement(opaqueExpression);
				if (result == null) result = caseUML2_Element(opaqueExpression);
				if (result == null) result = caseUML2_TemplateableElement(opaqueExpression);
				if (result == null) result = caseEModelElement(opaqueExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.VALUE_SPECIFICATION:
			{
				ValueSpecification valueSpecification = (ValueSpecification)theEObject;
				Object result = caseValueSpecification(valueSpecification);
				if (result == null) result = caseUML2_TypedElement(valueSpecification);
				if (result == null) result = caseUML2_ParameterableElement(valueSpecification);
				if (result == null) result = caseUML2_NamedElement(valueSpecification);
				if (result == null) result = caseUML2_Element(valueSpecification);
				if (result == null) result = caseUML2_TemplateableElement(valueSpecification);
				if (result == null) result = caseEModelElement(valueSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.EXPRESSION:
			{
				Expression expression = (Expression)theEObject;
				Object result = caseExpression(expression);
				if (result == null) result = caseUML2_OpaqueExpression(expression);
				if (result == null) result = caseUML2_ValueSpecification(expression);
				if (result == null) result = caseUML2_TypedElement(expression);
				if (result == null) result = caseUML2_ParameterableElement(expression);
				if (result == null) result = caseUML2_NamedElement(expression);
				if (result == null) result = caseUML2_Element(expression);
				if (result == null) result = caseUML2_TemplateableElement(expression);
				if (result == null) result = caseEModelElement(expression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.COMMENT:
			{
				Comment comment = (Comment)theEObject;
				Object result = caseComment(comment);
				if (result == null) result = caseUML2_TemplateableElement(comment);
				if (result == null) result = caseUML2_Element(comment);
				if (result == null) result = caseEModelElement(comment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DIRECTED_RELATIONSHIP:
			{
				DirectedRelationship directedRelationship = (DirectedRelationship)theEObject;
				Object result = caseDirectedRelationship(directedRelationship);
				if (result == null) result = caseUML2_Relationship(directedRelationship);
				if (result == null) result = caseUML2_Element(directedRelationship);
				if (result == null) result = caseEModelElement(directedRelationship);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.RELATIONSHIP:
			{
				Relationship relationship = (Relationship)theEObject;
				Object result = caseRelationship(relationship);
				if (result == null) result = caseUML2_Element(relationship);
				if (result == null) result = caseEModelElement(relationship);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CLASS:
			{
				org.eclipse.uml2.Class class_ = (org.eclipse.uml2.Class)theEObject;
				Object result = caseClass(class_);
				if (result == null) result = caseUML2_BehavioredClassifier(class_);
				if (result == null) result = caseUML2_EncapsulatedClassifier(class_);
				if (result == null) result = caseUML2_Classifier(class_);
				if (result == null) result = caseUML2_StructuredClassifier(class_);
				if (result == null) result = caseUML2_Namespace(class_);
				if (result == null) result = caseUML2_Type(class_);
				if (result == null) result = caseUML2_RedefinableElement(class_);
				if (result == null) result = caseUML2_NamedElement(class_);
				if (result == null) result = caseUML2_PackageableElement(class_);
				if (result == null) result = caseUML2_TemplateableElement(class_);
				if (result == null) result = caseUML2_ParameterableElement(class_);
				if (result == null) result = caseUML2_Element(class_);
				if (result == null) result = caseEModelElement(class_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TYPE:
			{
				Type type = (Type)theEObject;
				Object result = caseType(type);
				if (result == null) result = caseUML2_PackageableElement(type);
				if (result == null) result = caseUML2_NamedElement(type);
				if (result == null) result = caseUML2_ParameterableElement(type);
				if (result == null) result = caseUML2_TemplateableElement(type);
				if (result == null) result = caseUML2_Element(type);
				if (result == null) result = caseEModelElement(type);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PROPERTY:
			{
				Property property = (Property)theEObject;
				Object result = caseProperty(property);
				if (result == null) result = caseUML2_StructuralFeature(property);
				if (result == null) result = caseUML2_ConnectableElement(property);
				if (result == null) result = caseUML2_DeploymentTarget(property);
				if (result == null) result = caseUML2_Feature(property);
				if (result == null) result = caseUML2_TypedElement(property);
				if (result == null) result = caseUML2_MultiplicityElement(property);
				if (result == null) result = caseUML2_NamedElement(property);
				if (result == null) result = caseUML2_ParameterableElement(property);
				if (result == null) result = caseUML2_RedefinableElement(property);
				if (result == null) result = caseUML2_Element(property);
				if (result == null) result = caseUML2_TemplateableElement(property);
				if (result == null) result = caseEModelElement(property);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.OPERATION:
			{
				Operation operation = (Operation)theEObject;
				Object result = caseOperation(operation);
				if (result == null) result = caseUML2_BehavioralFeature(operation);
				if (result == null) result = caseUML2_TypedElement(operation);
				if (result == null) result = caseUML2_MultiplicityElement(operation);
				if (result == null) result = caseUML2_ParameterableElement(operation);
				if (result == null) result = caseUML2_Namespace(operation);
				if (result == null) result = caseUML2_Feature(operation);
				if (result == null) result = caseUML2_NamedElement(operation);
				if (result == null) result = caseUML2_Element(operation);
				if (result == null) result = caseUML2_RedefinableElement(operation);
				if (result == null) result = caseUML2_TemplateableElement(operation);
				if (result == null) result = caseEModelElement(operation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TYPED_ELEMENT:
			{
				TypedElement typedElement = (TypedElement)theEObject;
				Object result = caseTypedElement(typedElement);
				if (result == null) result = caseUML2_NamedElement(typedElement);
				if (result == null) result = caseUML2_TemplateableElement(typedElement);
				if (result == null) result = caseUML2_Element(typedElement);
				if (result == null) result = caseEModelElement(typedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PARAMETER:
			{
				Parameter parameter = (Parameter)theEObject;
				Object result = caseParameter(parameter);
				if (result == null) result = caseUML2_ConnectableElement(parameter);
				if (result == null) result = caseUML2_TypedElement(parameter);
				if (result == null) result = caseUML2_MultiplicityElement(parameter);
				if (result == null) result = caseUML2_NamedElement(parameter);
				if (result == null) result = caseUML2_ParameterableElement(parameter);
				if (result == null) result = caseUML2_Element(parameter);
				if (result == null) result = caseUML2_TemplateableElement(parameter);
				if (result == null) result = caseEModelElement(parameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PACKAGE:
			{
				org.eclipse.uml2.Package package_ = (org.eclipse.uml2.Package)theEObject;
				Object result = casePackage(package_);
				if (result == null) result = caseUML2_Namespace(package_);
				if (result == null) result = caseUML2_PackageableElement(package_);
				if (result == null) result = caseUML2_NamedElement(package_);
				if (result == null) result = caseUML2_ParameterableElement(package_);
				if (result == null) result = caseUML2_TemplateableElement(package_);
				if (result == null) result = caseUML2_Element(package_);
				if (result == null) result = caseEModelElement(package_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ENUMERATION:
			{
				Enumeration enumeration = (Enumeration)theEObject;
				Object result = caseEnumeration(enumeration);
				if (result == null) result = caseUML2_DataType(enumeration);
				if (result == null) result = caseUML2_Classifier(enumeration);
				if (result == null) result = caseUML2_Namespace(enumeration);
				if (result == null) result = caseUML2_Type(enumeration);
				if (result == null) result = caseUML2_RedefinableElement(enumeration);
				if (result == null) result = caseUML2_NamedElement(enumeration);
				if (result == null) result = caseUML2_PackageableElement(enumeration);
				if (result == null) result = caseUML2_TemplateableElement(enumeration);
				if (result == null) result = caseUML2_ParameterableElement(enumeration);
				if (result == null) result = caseUML2_Element(enumeration);
				if (result == null) result = caseEModelElement(enumeration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DATA_TYPE:
			{
				DataType dataType = (DataType)theEObject;
				Object result = caseDataType(dataType);
				if (result == null) result = caseUML2_Classifier(dataType);
				if (result == null) result = caseUML2_Namespace(dataType);
				if (result == null) result = caseUML2_Type(dataType);
				if (result == null) result = caseUML2_RedefinableElement(dataType);
				if (result == null) result = caseUML2_NamedElement(dataType);
				if (result == null) result = caseUML2_PackageableElement(dataType);
				if (result == null) result = caseUML2_TemplateableElement(dataType);
				if (result == null) result = caseUML2_ParameterableElement(dataType);
				if (result == null) result = caseUML2_Element(dataType);
				if (result == null) result = caseEModelElement(dataType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ENUMERATION_LITERAL:
			{
				EnumerationLiteral enumerationLiteral = (EnumerationLiteral)theEObject;
				Object result = caseEnumerationLiteral(enumerationLiteral);
				if (result == null) result = caseUML2_InstanceSpecification(enumerationLiteral);
				if (result == null) result = caseUML2_PackageableElement(enumerationLiteral);
				if (result == null) result = caseUML2_DeploymentTarget(enumerationLiteral);
				if (result == null) result = caseUML2_DeployedArtifact(enumerationLiteral);
				if (result == null) result = caseUML2_NamedElement(enumerationLiteral);
				if (result == null) result = caseUML2_ParameterableElement(enumerationLiteral);
				if (result == null) result = caseUML2_TemplateableElement(enumerationLiteral);
				if (result == null) result = caseUML2_Element(enumerationLiteral);
				if (result == null) result = caseEModelElement(enumerationLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PRIMITIVE_TYPE:
			{
				PrimitiveType primitiveType = (PrimitiveType)theEObject;
				Object result = casePrimitiveType(primitiveType);
				if (result == null) result = caseUML2_DataType(primitiveType);
				if (result == null) result = caseUML2_Classifier(primitiveType);
				if (result == null) result = caseUML2_Namespace(primitiveType);
				if (result == null) result = caseUML2_Type(primitiveType);
				if (result == null) result = caseUML2_RedefinableElement(primitiveType);
				if (result == null) result = caseUML2_NamedElement(primitiveType);
				if (result == null) result = caseUML2_PackageableElement(primitiveType);
				if (result == null) result = caseUML2_TemplateableElement(primitiveType);
				if (result == null) result = caseUML2_ParameterableElement(primitiveType);
				if (result == null) result = caseUML2_Element(primitiveType);
				if (result == null) result = caseEModelElement(primitiveType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CLASSIFIER:
			{
				Classifier classifier = (Classifier)theEObject;
				Object result = caseClassifier(classifier);
				if (result == null) result = caseUML2_Namespace(classifier);
				if (result == null) result = caseUML2_Type(classifier);
				if (result == null) result = caseUML2_RedefinableElement(classifier);
				if (result == null) result = caseUML2_NamedElement(classifier);
				if (result == null) result = caseUML2_PackageableElement(classifier);
				if (result == null) result = caseUML2_TemplateableElement(classifier);
				if (result == null) result = caseUML2_ParameterableElement(classifier);
				if (result == null) result = caseUML2_Element(classifier);
				if (result == null) result = caseEModelElement(classifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.FEATURE:
			{
				Feature feature = (Feature)theEObject;
				Object result = caseFeature(feature);
				if (result == null) result = caseUML2_RedefinableElement(feature);
				if (result == null) result = caseUML2_NamedElement(feature);
				if (result == null) result = caseUML2_TemplateableElement(feature);
				if (result == null) result = caseUML2_Element(feature);
				if (result == null) result = caseEModelElement(feature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CONSTRAINT:
			{
				Constraint constraint = (Constraint)theEObject;
				Object result = caseConstraint(constraint);
				if (result == null) result = caseUML2_PackageableElement(constraint);
				if (result == null) result = caseUML2_NamedElement(constraint);
				if (result == null) result = caseUML2_ParameterableElement(constraint);
				if (result == null) result = caseUML2_TemplateableElement(constraint);
				if (result == null) result = caseUML2_Element(constraint);
				if (result == null) result = caseEModelElement(constraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.LITERAL_BOOLEAN:
			{
				LiteralBoolean literalBoolean = (LiteralBoolean)theEObject;
				Object result = caseLiteralBoolean(literalBoolean);
				if (result == null) result = caseUML2_LiteralSpecification(literalBoolean);
				if (result == null) result = caseUML2_ValueSpecification(literalBoolean);
				if (result == null) result = caseUML2_TypedElement(literalBoolean);
				if (result == null) result = caseUML2_ParameterableElement(literalBoolean);
				if (result == null) result = caseUML2_NamedElement(literalBoolean);
				if (result == null) result = caseUML2_Element(literalBoolean);
				if (result == null) result = caseUML2_TemplateableElement(literalBoolean);
				if (result == null) result = caseEModelElement(literalBoolean);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.LITERAL_SPECIFICATION:
			{
				LiteralSpecification literalSpecification = (LiteralSpecification)theEObject;
				Object result = caseLiteralSpecification(literalSpecification);
				if (result == null) result = caseUML2_ValueSpecification(literalSpecification);
				if (result == null) result = caseUML2_TypedElement(literalSpecification);
				if (result == null) result = caseUML2_ParameterableElement(literalSpecification);
				if (result == null) result = caseUML2_NamedElement(literalSpecification);
				if (result == null) result = caseUML2_Element(literalSpecification);
				if (result == null) result = caseUML2_TemplateableElement(literalSpecification);
				if (result == null) result = caseEModelElement(literalSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.LITERAL_STRING:
			{
				LiteralString literalString = (LiteralString)theEObject;
				Object result = caseLiteralString(literalString);
				if (result == null) result = caseUML2_LiteralSpecification(literalString);
				if (result == null) result = caseUML2_ValueSpecification(literalString);
				if (result == null) result = caseUML2_TypedElement(literalString);
				if (result == null) result = caseUML2_ParameterableElement(literalString);
				if (result == null) result = caseUML2_NamedElement(literalString);
				if (result == null) result = caseUML2_Element(literalString);
				if (result == null) result = caseUML2_TemplateableElement(literalString);
				if (result == null) result = caseEModelElement(literalString);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.LITERAL_NULL:
			{
				LiteralNull literalNull = (LiteralNull)theEObject;
				Object result = caseLiteralNull(literalNull);
				if (result == null) result = caseUML2_LiteralSpecification(literalNull);
				if (result == null) result = caseUML2_ValueSpecification(literalNull);
				if (result == null) result = caseUML2_TypedElement(literalNull);
				if (result == null) result = caseUML2_ParameterableElement(literalNull);
				if (result == null) result = caseUML2_NamedElement(literalNull);
				if (result == null) result = caseUML2_Element(literalNull);
				if (result == null) result = caseUML2_TemplateableElement(literalNull);
				if (result == null) result = caseEModelElement(literalNull);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.LITERAL_INTEGER:
			{
				LiteralInteger literalInteger = (LiteralInteger)theEObject;
				Object result = caseLiteralInteger(literalInteger);
				if (result == null) result = caseUML2_LiteralSpecification(literalInteger);
				if (result == null) result = caseUML2_ValueSpecification(literalInteger);
				if (result == null) result = caseUML2_TypedElement(literalInteger);
				if (result == null) result = caseUML2_ParameterableElement(literalInteger);
				if (result == null) result = caseUML2_NamedElement(literalInteger);
				if (result == null) result = caseUML2_Element(literalInteger);
				if (result == null) result = caseUML2_TemplateableElement(literalInteger);
				if (result == null) result = caseEModelElement(literalInteger);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.LITERAL_UNLIMITED_NATURAL:
			{
				LiteralUnlimitedNatural literalUnlimitedNatural = (LiteralUnlimitedNatural)theEObject;
				Object result = caseLiteralUnlimitedNatural(literalUnlimitedNatural);
				if (result == null) result = caseUML2_LiteralSpecification(literalUnlimitedNatural);
				if (result == null) result = caseUML2_ValueSpecification(literalUnlimitedNatural);
				if (result == null) result = caseUML2_TypedElement(literalUnlimitedNatural);
				if (result == null) result = caseUML2_ParameterableElement(literalUnlimitedNatural);
				if (result == null) result = caseUML2_NamedElement(literalUnlimitedNatural);
				if (result == null) result = caseUML2_Element(literalUnlimitedNatural);
				if (result == null) result = caseUML2_TemplateableElement(literalUnlimitedNatural);
				if (result == null) result = caseEModelElement(literalUnlimitedNatural);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.BEHAVIORAL_FEATURE:
			{
				BehavioralFeature behavioralFeature = (BehavioralFeature)theEObject;
				Object result = caseBehavioralFeature(behavioralFeature);
				if (result == null) result = caseUML2_Namespace(behavioralFeature);
				if (result == null) result = caseUML2_Feature(behavioralFeature);
				if (result == null) result = caseUML2_NamedElement(behavioralFeature);
				if (result == null) result = caseUML2_RedefinableElement(behavioralFeature);
				if (result == null) result = caseUML2_TemplateableElement(behavioralFeature);
				if (result == null) result = caseUML2_Element(behavioralFeature);
				if (result == null) result = caseEModelElement(behavioralFeature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.STRUCTURAL_FEATURE:
			{
				StructuralFeature structuralFeature = (StructuralFeature)theEObject;
				Object result = caseStructuralFeature(structuralFeature);
				if (result == null) result = caseUML2_Feature(structuralFeature);
				if (result == null) result = caseUML2_TypedElement(structuralFeature);
				if (result == null) result = caseUML2_MultiplicityElement(structuralFeature);
				if (result == null) result = caseUML2_RedefinableElement(structuralFeature);
				if (result == null) result = caseUML2_NamedElement(structuralFeature);
				if (result == null) result = caseUML2_Element(structuralFeature);
				if (result == null) result = caseUML2_TemplateableElement(structuralFeature);
				if (result == null) result = caseEModelElement(structuralFeature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INSTANCE_SPECIFICATION:
			{
				InstanceSpecification instanceSpecification = (InstanceSpecification)theEObject;
				Object result = caseInstanceSpecification(instanceSpecification);
				if (result == null) result = caseUML2_PackageableElement(instanceSpecification);
				if (result == null) result = caseUML2_DeploymentTarget(instanceSpecification);
				if (result == null) result = caseUML2_DeployedArtifact(instanceSpecification);
				if (result == null) result = caseUML2_NamedElement(instanceSpecification);
				if (result == null) result = caseUML2_ParameterableElement(instanceSpecification);
				if (result == null) result = caseUML2_TemplateableElement(instanceSpecification);
				if (result == null) result = caseUML2_Element(instanceSpecification);
				if (result == null) result = caseEModelElement(instanceSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.SLOT:
			{
				Slot slot = (Slot)theEObject;
				Object result = caseSlot(slot);
				if (result == null) result = caseUML2_Element(slot);
				if (result == null) result = caseEModelElement(slot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INSTANCE_VALUE:
			{
				InstanceValue instanceValue = (InstanceValue)theEObject;
				Object result = caseInstanceValue(instanceValue);
				if (result == null) result = caseUML2_ValueSpecification(instanceValue);
				if (result == null) result = caseUML2_TypedElement(instanceValue);
				if (result == null) result = caseUML2_ParameterableElement(instanceValue);
				if (result == null) result = caseUML2_NamedElement(instanceValue);
				if (result == null) result = caseUML2_Element(instanceValue);
				if (result == null) result = caseUML2_TemplateableElement(instanceValue);
				if (result == null) result = caseEModelElement(instanceValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.REDEFINABLE_ELEMENT:
			{
				RedefinableElement redefinableElement = (RedefinableElement)theEObject;
				Object result = caseRedefinableElement(redefinableElement);
				if (result == null) result = caseUML2_NamedElement(redefinableElement);
				if (result == null) result = caseUML2_TemplateableElement(redefinableElement);
				if (result == null) result = caseUML2_Element(redefinableElement);
				if (result == null) result = caseEModelElement(redefinableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.GENERALIZATION:
			{
				Generalization generalization = (Generalization)theEObject;
				Object result = caseGeneralization(generalization);
				if (result == null) result = caseUML2_DirectedRelationship(generalization);
				if (result == null) result = caseUML2_Relationship(generalization);
				if (result == null) result = caseUML2_Element(generalization);
				if (result == null) result = caseEModelElement(generalization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PACKAGEABLE_ELEMENT:
			{
				PackageableElement packageableElement = (PackageableElement)theEObject;
				Object result = casePackageableElement(packageableElement);
				if (result == null) result = caseUML2_NamedElement(packageableElement);
				if (result == null) result = caseUML2_ParameterableElement(packageableElement);
				if (result == null) result = caseUML2_TemplateableElement(packageableElement);
				if (result == null) result = caseUML2_Element(packageableElement);
				if (result == null) result = caseEModelElement(packageableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ELEMENT_IMPORT:
			{
				ElementImport elementImport = (ElementImport)theEObject;
				Object result = caseElementImport(elementImport);
				if (result == null) result = caseUML2_DirectedRelationship(elementImport);
				if (result == null) result = caseUML2_Relationship(elementImport);
				if (result == null) result = caseUML2_Element(elementImport);
				if (result == null) result = caseEModelElement(elementImport);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PACKAGE_IMPORT:
			{
				PackageImport packageImport = (PackageImport)theEObject;
				Object result = casePackageImport(packageImport);
				if (result == null) result = caseUML2_DirectedRelationship(packageImport);
				if (result == null) result = caseUML2_Relationship(packageImport);
				if (result == null) result = caseUML2_Element(packageImport);
				if (result == null) result = caseEModelElement(packageImport);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ASSOCIATION:
			{
				Association association = (Association)theEObject;
				Object result = caseAssociation(association);
				if (result == null) result = caseUML2_Classifier(association);
				if (result == null) result = caseUML2_Relationship(association);
				if (result == null) result = caseUML2_Namespace(association);
				if (result == null) result = caseUML2_Type(association);
				if (result == null) result = caseUML2_RedefinableElement(association);
				if (result == null) result = caseUML2_Element(association);
				if (result == null) result = caseUML2_NamedElement(association);
				if (result == null) result = caseUML2_PackageableElement(association);
				if (result == null) result = caseEModelElement(association);
				if (result == null) result = caseUML2_TemplateableElement(association);
				if (result == null) result = caseUML2_ParameterableElement(association);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PACKAGE_MERGE:
			{
				PackageMerge packageMerge = (PackageMerge)theEObject;
				Object result = casePackageMerge(packageMerge);
				if (result == null) result = caseUML2_DirectedRelationship(packageMerge);
				if (result == null) result = caseUML2_Relationship(packageMerge);
				if (result == null) result = caseUML2_Element(packageMerge);
				if (result == null) result = caseEModelElement(packageMerge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.STEREOTYPE:
			{
				Stereotype stereotype = (Stereotype)theEObject;
				Object result = caseStereotype(stereotype);
				if (result == null) result = caseUML2_Class(stereotype);
				if (result == null) result = caseUML2_BehavioredClassifier(stereotype);
				if (result == null) result = caseUML2_EncapsulatedClassifier(stereotype);
				if (result == null) result = caseUML2_Classifier(stereotype);
				if (result == null) result = caseUML2_StructuredClassifier(stereotype);
				if (result == null) result = caseUML2_Namespace(stereotype);
				if (result == null) result = caseUML2_Type(stereotype);
				if (result == null) result = caseUML2_RedefinableElement(stereotype);
				if (result == null) result = caseUML2_NamedElement(stereotype);
				if (result == null) result = caseUML2_PackageableElement(stereotype);
				if (result == null) result = caseUML2_TemplateableElement(stereotype);
				if (result == null) result = caseUML2_ParameterableElement(stereotype);
				if (result == null) result = caseUML2_Element(stereotype);
				if (result == null) result = caseEModelElement(stereotype);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PROFILE:
			{
				Profile profile = (Profile)theEObject;
				Object result = caseProfile(profile);
				if (result == null) result = caseUML2_Package(profile);
				if (result == null) result = caseUML2_Namespace(profile);
				if (result == null) result = caseUML2_PackageableElement(profile);
				if (result == null) result = caseUML2_NamedElement(profile);
				if (result == null) result = caseUML2_ParameterableElement(profile);
				if (result == null) result = caseUML2_TemplateableElement(profile);
				if (result == null) result = caseUML2_Element(profile);
				if (result == null) result = caseEModelElement(profile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PROFILE_APPLICATION:
			{
				ProfileApplication profileApplication = (ProfileApplication)theEObject;
				Object result = caseProfileApplication(profileApplication);
				if (result == null) result = caseUML2_PackageImport(profileApplication);
				if (result == null) result = caseUML2_DirectedRelationship(profileApplication);
				if (result == null) result = caseUML2_Relationship(profileApplication);
				if (result == null) result = caseUML2_Element(profileApplication);
				if (result == null) result = caseEModelElement(profileApplication);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.EXTENSION:
			{
				Extension extension = (Extension)theEObject;
				Object result = caseExtension(extension);
				if (result == null) result = caseUML2_Association(extension);
				if (result == null) result = caseUML2_Classifier(extension);
				if (result == null) result = caseUML2_Relationship(extension);
				if (result == null) result = caseUML2_Namespace(extension);
				if (result == null) result = caseUML2_Type(extension);
				if (result == null) result = caseUML2_RedefinableElement(extension);
				if (result == null) result = caseUML2_Element(extension);
				if (result == null) result = caseUML2_NamedElement(extension);
				if (result == null) result = caseUML2_PackageableElement(extension);
				if (result == null) result = caseEModelElement(extension);
				if (result == null) result = caseUML2_TemplateableElement(extension);
				if (result == null) result = caseUML2_ParameterableElement(extension);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.EXTENSION_END:
			{
				ExtensionEnd extensionEnd = (ExtensionEnd)theEObject;
				Object result = caseExtensionEnd(extensionEnd);
				if (result == null) result = caseUML2_Property(extensionEnd);
				if (result == null) result = caseUML2_StructuralFeature(extensionEnd);
				if (result == null) result = caseUML2_ConnectableElement(extensionEnd);
				if (result == null) result = caseUML2_DeploymentTarget(extensionEnd);
				if (result == null) result = caseUML2_Feature(extensionEnd);
				if (result == null) result = caseUML2_TypedElement(extensionEnd);
				if (result == null) result = caseUML2_MultiplicityElement(extensionEnd);
				if (result == null) result = caseUML2_NamedElement(extensionEnd);
				if (result == null) result = caseUML2_ParameterableElement(extensionEnd);
				if (result == null) result = caseUML2_RedefinableElement(extensionEnd);
				if (result == null) result = caseUML2_Element(extensionEnd);
				if (result == null) result = caseUML2_TemplateableElement(extensionEnd);
				if (result == null) result = caseEModelElement(extensionEnd);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.BEHAVIOR:
			{
				Behavior behavior = (Behavior)theEObject;
				Object result = caseBehavior(behavior);
				if (result == null) result = caseUML2_Class(behavior);
				if (result == null) result = caseUML2_BehavioredClassifier(behavior);
				if (result == null) result = caseUML2_EncapsulatedClassifier(behavior);
				if (result == null) result = caseUML2_Classifier(behavior);
				if (result == null) result = caseUML2_StructuredClassifier(behavior);
				if (result == null) result = caseUML2_Namespace(behavior);
				if (result == null) result = caseUML2_Type(behavior);
				if (result == null) result = caseUML2_RedefinableElement(behavior);
				if (result == null) result = caseUML2_NamedElement(behavior);
				if (result == null) result = caseUML2_PackageableElement(behavior);
				if (result == null) result = caseUML2_TemplateableElement(behavior);
				if (result == null) result = caseUML2_ParameterableElement(behavior);
				if (result == null) result = caseUML2_Element(behavior);
				if (result == null) result = caseEModelElement(behavior);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.BEHAVIORED_CLASSIFIER:
			{
				BehavioredClassifier behavioredClassifier = (BehavioredClassifier)theEObject;
				Object result = caseBehavioredClassifier(behavioredClassifier);
				if (result == null) result = caseUML2_Classifier(behavioredClassifier);
				if (result == null) result = caseUML2_Namespace(behavioredClassifier);
				if (result == null) result = caseUML2_Type(behavioredClassifier);
				if (result == null) result = caseUML2_RedefinableElement(behavioredClassifier);
				if (result == null) result = caseUML2_NamedElement(behavioredClassifier);
				if (result == null) result = caseUML2_PackageableElement(behavioredClassifier);
				if (result == null) result = caseUML2_TemplateableElement(behavioredClassifier);
				if (result == null) result = caseUML2_ParameterableElement(behavioredClassifier);
				if (result == null) result = caseUML2_Element(behavioredClassifier);
				if (result == null) result = caseEModelElement(behavioredClassifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ACTIVITY:
			{
				Activity activity = (Activity)theEObject;
				Object result = caseActivity(activity);
				if (result == null) result = caseUML2_Behavior(activity);
				if (result == null) result = caseUML2_Class(activity);
				if (result == null) result = caseUML2_BehavioredClassifier(activity);
				if (result == null) result = caseUML2_EncapsulatedClassifier(activity);
				if (result == null) result = caseUML2_Classifier(activity);
				if (result == null) result = caseUML2_StructuredClassifier(activity);
				if (result == null) result = caseUML2_Namespace(activity);
				if (result == null) result = caseUML2_Type(activity);
				if (result == null) result = caseUML2_RedefinableElement(activity);
				if (result == null) result = caseUML2_NamedElement(activity);
				if (result == null) result = caseUML2_PackageableElement(activity);
				if (result == null) result = caseUML2_TemplateableElement(activity);
				if (result == null) result = caseUML2_ParameterableElement(activity);
				if (result == null) result = caseUML2_Element(activity);
				if (result == null) result = caseEModelElement(activity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PERMISSION:
			{
				Permission permission = (Permission)theEObject;
				Object result = casePermission(permission);
				if (result == null) result = caseUML2_Dependency(permission);
				if (result == null) result = caseUML2_PackageableElement(permission);
				if (result == null) result = caseUML2_DirectedRelationship(permission);
				if (result == null) result = caseUML2_NamedElement(permission);
				if (result == null) result = caseUML2_ParameterableElement(permission);
				if (result == null) result = caseUML2_Relationship(permission);
				if (result == null) result = caseUML2_TemplateableElement(permission);
				if (result == null) result = caseUML2_Element(permission);
				if (result == null) result = caseEModelElement(permission);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DEPENDENCY:
			{
				Dependency dependency = (Dependency)theEObject;
				Object result = caseDependency(dependency);
				if (result == null) result = caseUML2_PackageableElement(dependency);
				if (result == null) result = caseUML2_DirectedRelationship(dependency);
				if (result == null) result = caseUML2_NamedElement(dependency);
				if (result == null) result = caseUML2_ParameterableElement(dependency);
				if (result == null) result = caseUML2_Relationship(dependency);
				if (result == null) result = caseUML2_TemplateableElement(dependency);
				if (result == null) result = caseUML2_Element(dependency);
				if (result == null) result = caseEModelElement(dependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.USAGE:
			{
				Usage usage = (Usage)theEObject;
				Object result = caseUsage(usage);
				if (result == null) result = caseUML2_Dependency(usage);
				if (result == null) result = caseUML2_PackageableElement(usage);
				if (result == null) result = caseUML2_DirectedRelationship(usage);
				if (result == null) result = caseUML2_NamedElement(usage);
				if (result == null) result = caseUML2_ParameterableElement(usage);
				if (result == null) result = caseUML2_Relationship(usage);
				if (result == null) result = caseUML2_TemplateableElement(usage);
				if (result == null) result = caseUML2_Element(usage);
				if (result == null) result = caseEModelElement(usage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ABSTRACTION:
			{
				Abstraction abstraction = (Abstraction)theEObject;
				Object result = caseAbstraction(abstraction);
				if (result == null) result = caseUML2_Dependency(abstraction);
				if (result == null) result = caseUML2_PackageableElement(abstraction);
				if (result == null) result = caseUML2_DirectedRelationship(abstraction);
				if (result == null) result = caseUML2_NamedElement(abstraction);
				if (result == null) result = caseUML2_ParameterableElement(abstraction);
				if (result == null) result = caseUML2_Relationship(abstraction);
				if (result == null) result = caseUML2_TemplateableElement(abstraction);
				if (result == null) result = caseUML2_Element(abstraction);
				if (result == null) result = caseEModelElement(abstraction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.REALIZATION:
			{
				Realization realization = (Realization)theEObject;
				Object result = caseRealization(realization);
				if (result == null) result = caseUML2_Abstraction(realization);
				if (result == null) result = caseUML2_Dependency(realization);
				if (result == null) result = caseUML2_PackageableElement(realization);
				if (result == null) result = caseUML2_DirectedRelationship(realization);
				if (result == null) result = caseUML2_NamedElement(realization);
				if (result == null) result = caseUML2_ParameterableElement(realization);
				if (result == null) result = caseUML2_Relationship(realization);
				if (result == null) result = caseUML2_TemplateableElement(realization);
				if (result == null) result = caseUML2_Element(realization);
				if (result == null) result = caseEModelElement(realization);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.SUBSTITUTION:
			{
				Substitution substitution = (Substitution)theEObject;
				Object result = caseSubstitution(substitution);
				if (result == null) result = caseUML2_Realization(substitution);
				if (result == null) result = caseUML2_Abstraction(substitution);
				if (result == null) result = caseUML2_Dependency(substitution);
				if (result == null) result = caseUML2_PackageableElement(substitution);
				if (result == null) result = caseUML2_DirectedRelationship(substitution);
				if (result == null) result = caseUML2_NamedElement(substitution);
				if (result == null) result = caseUML2_ParameterableElement(substitution);
				if (result == null) result = caseUML2_Relationship(substitution);
				if (result == null) result = caseUML2_TemplateableElement(substitution);
				if (result == null) result = caseUML2_Element(substitution);
				if (result == null) result = caseEModelElement(substitution);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.GENERALIZATION_SET:
			{
				GeneralizationSet generalizationSet = (GeneralizationSet)theEObject;
				Object result = caseGeneralizationSet(generalizationSet);
				if (result == null) result = caseUML2_PackageableElement(generalizationSet);
				if (result == null) result = caseUML2_NamedElement(generalizationSet);
				if (result == null) result = caseUML2_ParameterableElement(generalizationSet);
				if (result == null) result = caseUML2_TemplateableElement(generalizationSet);
				if (result == null) result = caseUML2_Element(generalizationSet);
				if (result == null) result = caseEModelElement(generalizationSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ASSOCIATION_CLASS:
			{
				AssociationClass associationClass = (AssociationClass)theEObject;
				Object result = caseAssociationClass(associationClass);
				if (result == null) result = caseUML2_Class(associationClass);
				if (result == null) result = caseUML2_Association(associationClass);
				if (result == null) result = caseUML2_BehavioredClassifier(associationClass);
				if (result == null) result = caseUML2_EncapsulatedClassifier(associationClass);
				if (result == null) result = caseUML2_Classifier(associationClass);
				if (result == null) result = caseUML2_Relationship(associationClass);
				if (result == null) result = caseUML2_StructuredClassifier(associationClass);
				if (result == null) result = caseUML2_Namespace(associationClass);
				if (result == null) result = caseUML2_Type(associationClass);
				if (result == null) result = caseUML2_RedefinableElement(associationClass);
				if (result == null) result = caseUML2_Element(associationClass);
				if (result == null) result = caseUML2_NamedElement(associationClass);
				if (result == null) result = caseUML2_PackageableElement(associationClass);
				if (result == null) result = caseEModelElement(associationClass);
				if (result == null) result = caseUML2_TemplateableElement(associationClass);
				if (result == null) result = caseUML2_ParameterableElement(associationClass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INFORMATION_ITEM:
			{
				InformationItem informationItem = (InformationItem)theEObject;
				Object result = caseInformationItem(informationItem);
				if (result == null) result = caseUML2_Classifier(informationItem);
				if (result == null) result = caseUML2_Namespace(informationItem);
				if (result == null) result = caseUML2_Type(informationItem);
				if (result == null) result = caseUML2_RedefinableElement(informationItem);
				if (result == null) result = caseUML2_NamedElement(informationItem);
				if (result == null) result = caseUML2_PackageableElement(informationItem);
				if (result == null) result = caseUML2_TemplateableElement(informationItem);
				if (result == null) result = caseUML2_ParameterableElement(informationItem);
				if (result == null) result = caseUML2_Element(informationItem);
				if (result == null) result = caseEModelElement(informationItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INFORMATION_FLOW:
			{
				InformationFlow informationFlow = (InformationFlow)theEObject;
				Object result = caseInformationFlow(informationFlow);
				if (result == null) result = caseUML2_PackageableElement(informationFlow);
				if (result == null) result = caseUML2_DirectedRelationship(informationFlow);
				if (result == null) result = caseUML2_NamedElement(informationFlow);
				if (result == null) result = caseUML2_ParameterableElement(informationFlow);
				if (result == null) result = caseUML2_Relationship(informationFlow);
				if (result == null) result = caseUML2_TemplateableElement(informationFlow);
				if (result == null) result = caseUML2_Element(informationFlow);
				if (result == null) result = caseEModelElement(informationFlow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.MODEL:
			{
				Model model = (Model)theEObject;
				Object result = caseModel(model);
				if (result == null) result = caseUML2_Package(model);
				if (result == null) result = caseUML2_Namespace(model);
				if (result == null) result = caseUML2_PackageableElement(model);
				if (result == null) result = caseUML2_NamedElement(model);
				if (result == null) result = caseUML2_ParameterableElement(model);
				if (result == null) result = caseUML2_TemplateableElement(model);
				if (result == null) result = caseUML2_Element(model);
				if (result == null) result = caseEModelElement(model);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CONNECTOR_END:
			{
				ConnectorEnd connectorEnd = (ConnectorEnd)theEObject;
				Object result = caseConnectorEnd(connectorEnd);
				if (result == null) result = caseUML2_MultiplicityElement(connectorEnd);
				if (result == null) result = caseUML2_Element(connectorEnd);
				if (result == null) result = caseEModelElement(connectorEnd);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CONNECTABLE_ELEMENT:
			{
				ConnectableElement connectableElement = (ConnectableElement)theEObject;
				Object result = caseConnectableElement(connectableElement);
				if (result == null) result = caseUML2_NamedElement(connectableElement);
				if (result == null) result = caseUML2_ParameterableElement(connectableElement);
				if (result == null) result = caseUML2_TemplateableElement(connectableElement);
				if (result == null) result = caseUML2_Element(connectableElement);
				if (result == null) result = caseEModelElement(connectableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CONNECTOR:
			{
				Connector connector = (Connector)theEObject;
				Object result = caseConnector(connector);
				if (result == null) result = caseUML2_Feature(connector);
				if (result == null) result = caseUML2_RedefinableElement(connector);
				if (result == null) result = caseUML2_NamedElement(connector);
				if (result == null) result = caseUML2_TemplateableElement(connector);
				if (result == null) result = caseUML2_Element(connector);
				if (result == null) result = caseEModelElement(connector);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.STRUCTURED_CLASSIFIER:
			{
				StructuredClassifier structuredClassifier = (StructuredClassifier)theEObject;
				Object result = caseStructuredClassifier(structuredClassifier);
				if (result == null) result = caseUML2_Classifier(structuredClassifier);
				if (result == null) result = caseUML2_Namespace(structuredClassifier);
				if (result == null) result = caseUML2_Type(structuredClassifier);
				if (result == null) result = caseUML2_RedefinableElement(structuredClassifier);
				if (result == null) result = caseUML2_NamedElement(structuredClassifier);
				if (result == null) result = caseUML2_PackageableElement(structuredClassifier);
				if (result == null) result = caseUML2_TemplateableElement(structuredClassifier);
				if (result == null) result = caseUML2_ParameterableElement(structuredClassifier);
				if (result == null) result = caseUML2_Element(structuredClassifier);
				if (result == null) result = caseEModelElement(structuredClassifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ACTIVITY_EDGE:
			{
				ActivityEdge activityEdge = (ActivityEdge)theEObject;
				Object result = caseActivityEdge(activityEdge);
				if (result == null) result = caseUML2_RedefinableElement(activityEdge);
				if (result == null) result = caseUML2_NamedElement(activityEdge);
				if (result == null) result = caseUML2_TemplateableElement(activityEdge);
				if (result == null) result = caseUML2_Element(activityEdge);
				if (result == null) result = caseEModelElement(activityEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ACTIVITY_GROUP:
			{
				ActivityGroup activityGroup = (ActivityGroup)theEObject;
				Object result = caseActivityGroup(activityGroup);
				if (result == null) result = caseUML2_Element(activityGroup);
				if (result == null) result = caseEModelElement(activityGroup);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ACTIVITY_NODE:
			{
				ActivityNode activityNode = (ActivityNode)theEObject;
				Object result = caseActivityNode(activityNode);
				if (result == null) result = caseUML2_RedefinableElement(activityNode);
				if (result == null) result = caseUML2_NamedElement(activityNode);
				if (result == null) result = caseUML2_TemplateableElement(activityNode);
				if (result == null) result = caseUML2_Element(activityNode);
				if (result == null) result = caseEModelElement(activityNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ACTION:
			{
				Action action = (Action)theEObject;
				Object result = caseAction(action);
				if (result == null) result = caseUML2_ExecutableNode(action);
				if (result == null) result = caseUML2_ActivityNode(action);
				if (result == null) result = caseUML2_RedefinableElement(action);
				if (result == null) result = caseUML2_NamedElement(action);
				if (result == null) result = caseUML2_TemplateableElement(action);
				if (result == null) result = caseUML2_Element(action);
				if (result == null) result = caseEModelElement(action);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.OBJECT_NODE:
			{
				ObjectNode objectNode = (ObjectNode)theEObject;
				Object result = caseObjectNode(objectNode);
				if (result == null) result = caseUML2_ActivityNode(objectNode);
				if (result == null) result = caseUML2_TypedElement(objectNode);
				if (result == null) result = caseUML2_RedefinableElement(objectNode);
				if (result == null) result = caseUML2_NamedElement(objectNode);
				if (result == null) result = caseUML2_TemplateableElement(objectNode);
				if (result == null) result = caseUML2_Element(objectNode);
				if (result == null) result = caseEModelElement(objectNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CONTROL_NODE:
			{
				ControlNode controlNode = (ControlNode)theEObject;
				Object result = caseControlNode(controlNode);
				if (result == null) result = caseUML2_ActivityNode(controlNode);
				if (result == null) result = caseUML2_RedefinableElement(controlNode);
				if (result == null) result = caseUML2_NamedElement(controlNode);
				if (result == null) result = caseUML2_TemplateableElement(controlNode);
				if (result == null) result = caseUML2_Element(controlNode);
				if (result == null) result = caseEModelElement(controlNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CONTROL_FLOW:
			{
				ControlFlow controlFlow = (ControlFlow)theEObject;
				Object result = caseControlFlow(controlFlow);
				if (result == null) result = caseUML2_ActivityEdge(controlFlow);
				if (result == null) result = caseUML2_RedefinableElement(controlFlow);
				if (result == null) result = caseUML2_NamedElement(controlFlow);
				if (result == null) result = caseUML2_TemplateableElement(controlFlow);
				if (result == null) result = caseUML2_Element(controlFlow);
				if (result == null) result = caseEModelElement(controlFlow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.OBJECT_FLOW:
			{
				ObjectFlow objectFlow = (ObjectFlow)theEObject;
				Object result = caseObjectFlow(objectFlow);
				if (result == null) result = caseUML2_ActivityEdge(objectFlow);
				if (result == null) result = caseUML2_RedefinableElement(objectFlow);
				if (result == null) result = caseUML2_NamedElement(objectFlow);
				if (result == null) result = caseUML2_TemplateableElement(objectFlow);
				if (result == null) result = caseUML2_Element(objectFlow);
				if (result == null) result = caseEModelElement(objectFlow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INITIAL_NODE:
			{
				InitialNode initialNode = (InitialNode)theEObject;
				Object result = caseInitialNode(initialNode);
				if (result == null) result = caseUML2_ControlNode(initialNode);
				if (result == null) result = caseUML2_ActivityNode(initialNode);
				if (result == null) result = caseUML2_RedefinableElement(initialNode);
				if (result == null) result = caseUML2_NamedElement(initialNode);
				if (result == null) result = caseUML2_TemplateableElement(initialNode);
				if (result == null) result = caseUML2_Element(initialNode);
				if (result == null) result = caseEModelElement(initialNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.FINAL_NODE:
			{
				FinalNode finalNode = (FinalNode)theEObject;
				Object result = caseFinalNode(finalNode);
				if (result == null) result = caseUML2_ControlNode(finalNode);
				if (result == null) result = caseUML2_ActivityNode(finalNode);
				if (result == null) result = caseUML2_RedefinableElement(finalNode);
				if (result == null) result = caseUML2_NamedElement(finalNode);
				if (result == null) result = caseUML2_TemplateableElement(finalNode);
				if (result == null) result = caseUML2_Element(finalNode);
				if (result == null) result = caseEModelElement(finalNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ACTIVITY_FINAL_NODE:
			{
				ActivityFinalNode activityFinalNode = (ActivityFinalNode)theEObject;
				Object result = caseActivityFinalNode(activityFinalNode);
				if (result == null) result = caseUML2_FinalNode(activityFinalNode);
				if (result == null) result = caseUML2_ControlNode(activityFinalNode);
				if (result == null) result = caseUML2_ActivityNode(activityFinalNode);
				if (result == null) result = caseUML2_RedefinableElement(activityFinalNode);
				if (result == null) result = caseUML2_NamedElement(activityFinalNode);
				if (result == null) result = caseUML2_TemplateableElement(activityFinalNode);
				if (result == null) result = caseUML2_Element(activityFinalNode);
				if (result == null) result = caseEModelElement(activityFinalNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DECISION_NODE:
			{
				DecisionNode decisionNode = (DecisionNode)theEObject;
				Object result = caseDecisionNode(decisionNode);
				if (result == null) result = caseUML2_ControlNode(decisionNode);
				if (result == null) result = caseUML2_ActivityNode(decisionNode);
				if (result == null) result = caseUML2_RedefinableElement(decisionNode);
				if (result == null) result = caseUML2_NamedElement(decisionNode);
				if (result == null) result = caseUML2_TemplateableElement(decisionNode);
				if (result == null) result = caseUML2_Element(decisionNode);
				if (result == null) result = caseEModelElement(decisionNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.MERGE_NODE:
			{
				MergeNode mergeNode = (MergeNode)theEObject;
				Object result = caseMergeNode(mergeNode);
				if (result == null) result = caseUML2_ControlNode(mergeNode);
				if (result == null) result = caseUML2_ActivityNode(mergeNode);
				if (result == null) result = caseUML2_RedefinableElement(mergeNode);
				if (result == null) result = caseUML2_NamedElement(mergeNode);
				if (result == null) result = caseUML2_TemplateableElement(mergeNode);
				if (result == null) result = caseUML2_Element(mergeNode);
				if (result == null) result = caseEModelElement(mergeNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.EXECUTABLE_NODE:
			{
				ExecutableNode executableNode = (ExecutableNode)theEObject;
				Object result = caseExecutableNode(executableNode);
				if (result == null) result = caseUML2_ActivityNode(executableNode);
				if (result == null) result = caseUML2_RedefinableElement(executableNode);
				if (result == null) result = caseUML2_NamedElement(executableNode);
				if (result == null) result = caseUML2_TemplateableElement(executableNode);
				if (result == null) result = caseUML2_Element(executableNode);
				if (result == null) result = caseEModelElement(executableNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.OUTPUT_PIN:
			{
				OutputPin outputPin = (OutputPin)theEObject;
				Object result = caseOutputPin(outputPin);
				if (result == null) result = caseUML2_Pin(outputPin);
				if (result == null) result = caseUML2_ObjectNode(outputPin);
				if (result == null) result = caseUML2_MultiplicityElement(outputPin);
				if (result == null) result = caseUML2_ActivityNode(outputPin);
				if (result == null) result = caseUML2_TypedElement(outputPin);
				if (result == null) result = caseUML2_Element(outputPin);
				if (result == null) result = caseUML2_RedefinableElement(outputPin);
				if (result == null) result = caseUML2_NamedElement(outputPin);
				if (result == null) result = caseEModelElement(outputPin);
				if (result == null) result = caseUML2_TemplateableElement(outputPin);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INPUT_PIN:
			{
				InputPin inputPin = (InputPin)theEObject;
				Object result = caseInputPin(inputPin);
				if (result == null) result = caseUML2_Pin(inputPin);
				if (result == null) result = caseUML2_ObjectNode(inputPin);
				if (result == null) result = caseUML2_MultiplicityElement(inputPin);
				if (result == null) result = caseUML2_ActivityNode(inputPin);
				if (result == null) result = caseUML2_TypedElement(inputPin);
				if (result == null) result = caseUML2_Element(inputPin);
				if (result == null) result = caseUML2_RedefinableElement(inputPin);
				if (result == null) result = caseUML2_NamedElement(inputPin);
				if (result == null) result = caseEModelElement(inputPin);
				if (result == null) result = caseUML2_TemplateableElement(inputPin);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PIN:
			{
				Pin pin = (Pin)theEObject;
				Object result = casePin(pin);
				if (result == null) result = caseUML2_ObjectNode(pin);
				if (result == null) result = caseUML2_MultiplicityElement(pin);
				if (result == null) result = caseUML2_ActivityNode(pin);
				if (result == null) result = caseUML2_TypedElement(pin);
				if (result == null) result = caseUML2_Element(pin);
				if (result == null) result = caseUML2_RedefinableElement(pin);
				if (result == null) result = caseUML2_NamedElement(pin);
				if (result == null) result = caseEModelElement(pin);
				if (result == null) result = caseUML2_TemplateableElement(pin);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ACTIVITY_PARAMETER_NODE:
			{
				ActivityParameterNode activityParameterNode = (ActivityParameterNode)theEObject;
				Object result = caseActivityParameterNode(activityParameterNode);
				if (result == null) result = caseUML2_ObjectNode(activityParameterNode);
				if (result == null) result = caseUML2_ActivityNode(activityParameterNode);
				if (result == null) result = caseUML2_TypedElement(activityParameterNode);
				if (result == null) result = caseUML2_RedefinableElement(activityParameterNode);
				if (result == null) result = caseUML2_NamedElement(activityParameterNode);
				if (result == null) result = caseUML2_TemplateableElement(activityParameterNode);
				if (result == null) result = caseUML2_Element(activityParameterNode);
				if (result == null) result = caseEModelElement(activityParameterNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.VALUE_PIN:
			{
				ValuePin valuePin = (ValuePin)theEObject;
				Object result = caseValuePin(valuePin);
				if (result == null) result = caseUML2_InputPin(valuePin);
				if (result == null) result = caseUML2_Pin(valuePin);
				if (result == null) result = caseUML2_ObjectNode(valuePin);
				if (result == null) result = caseUML2_MultiplicityElement(valuePin);
				if (result == null) result = caseUML2_ActivityNode(valuePin);
				if (result == null) result = caseUML2_TypedElement(valuePin);
				if (result == null) result = caseUML2_Element(valuePin);
				if (result == null) result = caseUML2_RedefinableElement(valuePin);
				if (result == null) result = caseUML2_NamedElement(valuePin);
				if (result == null) result = caseEModelElement(valuePin);
				if (result == null) result = caseUML2_TemplateableElement(valuePin);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INTERFACE:
			{
				Interface interface_ = (Interface)theEObject;
				Object result = caseInterface(interface_);
				if (result == null) result = caseUML2_Classifier(interface_);
				if (result == null) result = caseUML2_Namespace(interface_);
				if (result == null) result = caseUML2_Type(interface_);
				if (result == null) result = caseUML2_RedefinableElement(interface_);
				if (result == null) result = caseUML2_NamedElement(interface_);
				if (result == null) result = caseUML2_PackageableElement(interface_);
				if (result == null) result = caseUML2_TemplateableElement(interface_);
				if (result == null) result = caseUML2_ParameterableElement(interface_);
				if (result == null) result = caseUML2_Element(interface_);
				if (result == null) result = caseEModelElement(interface_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.IMPLEMENTATION:
			{
				Implementation implementation = (Implementation)theEObject;
				Object result = caseImplementation(implementation);
				if (result == null) result = caseUML2_Realization(implementation);
				if (result == null) result = caseUML2_Abstraction(implementation);
				if (result == null) result = caseUML2_Dependency(implementation);
				if (result == null) result = caseUML2_PackageableElement(implementation);
				if (result == null) result = caseUML2_DirectedRelationship(implementation);
				if (result == null) result = caseUML2_NamedElement(implementation);
				if (result == null) result = caseUML2_ParameterableElement(implementation);
				if (result == null) result = caseUML2_Relationship(implementation);
				if (result == null) result = caseUML2_TemplateableElement(implementation);
				if (result == null) result = caseUML2_Element(implementation);
				if (result == null) result = caseEModelElement(implementation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ARTIFACT:
			{
				Artifact artifact = (Artifact)theEObject;
				Object result = caseArtifact(artifact);
				if (result == null) result = caseUML2_Classifier(artifact);
				if (result == null) result = caseUML2_DeployedArtifact(artifact);
				if (result == null) result = caseUML2_Namespace(artifact);
				if (result == null) result = caseUML2_Type(artifact);
				if (result == null) result = caseUML2_RedefinableElement(artifact);
				if (result == null) result = caseUML2_NamedElement(artifact);
				if (result == null) result = caseUML2_PackageableElement(artifact);
				if (result == null) result = caseUML2_TemplateableElement(artifact);
				if (result == null) result = caseUML2_ParameterableElement(artifact);
				if (result == null) result = caseUML2_Element(artifact);
				if (result == null) result = caseEModelElement(artifact);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.MANIFESTATION:
			{
				Manifestation manifestation = (Manifestation)theEObject;
				Object result = caseManifestation(manifestation);
				if (result == null) result = caseUML2_Abstraction(manifestation);
				if (result == null) result = caseUML2_Dependency(manifestation);
				if (result == null) result = caseUML2_PackageableElement(manifestation);
				if (result == null) result = caseUML2_DirectedRelationship(manifestation);
				if (result == null) result = caseUML2_NamedElement(manifestation);
				if (result == null) result = caseUML2_ParameterableElement(manifestation);
				if (result == null) result = caseUML2_Relationship(manifestation);
				if (result == null) result = caseUML2_TemplateableElement(manifestation);
				if (result == null) result = caseUML2_Element(manifestation);
				if (result == null) result = caseEModelElement(manifestation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ACTOR:
			{
				Actor actor = (Actor)theEObject;
				Object result = caseActor(actor);
				if (result == null) result = caseUML2_Classifier(actor);
				if (result == null) result = caseUML2_Namespace(actor);
				if (result == null) result = caseUML2_Type(actor);
				if (result == null) result = caseUML2_RedefinableElement(actor);
				if (result == null) result = caseUML2_NamedElement(actor);
				if (result == null) result = caseUML2_PackageableElement(actor);
				if (result == null) result = caseUML2_TemplateableElement(actor);
				if (result == null) result = caseUML2_ParameterableElement(actor);
				if (result == null) result = caseUML2_Element(actor);
				if (result == null) result = caseEModelElement(actor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.EXTEND:
			{
				Extend extend = (Extend)theEObject;
				Object result = caseExtend(extend);
				if (result == null) result = caseUML2_NamedElement(extend);
				if (result == null) result = caseUML2_DirectedRelationship(extend);
				if (result == null) result = caseUML2_TemplateableElement(extend);
				if (result == null) result = caseUML2_Relationship(extend);
				if (result == null) result = caseUML2_Element(extend);
				if (result == null) result = caseEModelElement(extend);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.USE_CASE:
			{
				UseCase useCase = (UseCase)theEObject;
				Object result = caseUseCase(useCase);
				if (result == null) result = caseUML2_BehavioredClassifier(useCase);
				if (result == null) result = caseUML2_Classifier(useCase);
				if (result == null) result = caseUML2_Namespace(useCase);
				if (result == null) result = caseUML2_Type(useCase);
				if (result == null) result = caseUML2_RedefinableElement(useCase);
				if (result == null) result = caseUML2_NamedElement(useCase);
				if (result == null) result = caseUML2_PackageableElement(useCase);
				if (result == null) result = caseUML2_TemplateableElement(useCase);
				if (result == null) result = caseUML2_ParameterableElement(useCase);
				if (result == null) result = caseUML2_Element(useCase);
				if (result == null) result = caseEModelElement(useCase);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.EXTENSION_POINT:
			{
				ExtensionPoint extensionPoint = (ExtensionPoint)theEObject;
				Object result = caseExtensionPoint(extensionPoint);
				if (result == null) result = caseUML2_RedefinableElement(extensionPoint);
				if (result == null) result = caseUML2_NamedElement(extensionPoint);
				if (result == null) result = caseUML2_TemplateableElement(extensionPoint);
				if (result == null) result = caseUML2_Element(extensionPoint);
				if (result == null) result = caseEModelElement(extensionPoint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INCLUDE:
			{
				Include include = (Include)theEObject;
				Object result = caseInclude(include);
				if (result == null) result = caseUML2_NamedElement(include);
				if (result == null) result = caseUML2_DirectedRelationship(include);
				if (result == null) result = caseUML2_TemplateableElement(include);
				if (result == null) result = caseUML2_Relationship(include);
				if (result == null) result = caseUML2_Element(include);
				if (result == null) result = caseEModelElement(include);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.COLLABORATION_OCCURRENCE:
			{
				CollaborationOccurrence collaborationOccurrence = (CollaborationOccurrence)theEObject;
				Object result = caseCollaborationOccurrence(collaborationOccurrence);
				if (result == null) result = caseUML2_NamedElement(collaborationOccurrence);
				if (result == null) result = caseUML2_TemplateableElement(collaborationOccurrence);
				if (result == null) result = caseUML2_Element(collaborationOccurrence);
				if (result == null) result = caseEModelElement(collaborationOccurrence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.COLLABORATION:
			{
				Collaboration collaboration = (Collaboration)theEObject;
				Object result = caseCollaboration(collaboration);
				if (result == null) result = caseUML2_BehavioredClassifier(collaboration);
				if (result == null) result = caseUML2_StructuredClassifier(collaboration);
				if (result == null) result = caseUML2_Classifier(collaboration);
				if (result == null) result = caseUML2_Namespace(collaboration);
				if (result == null) result = caseUML2_Type(collaboration);
				if (result == null) result = caseUML2_RedefinableElement(collaboration);
				if (result == null) result = caseUML2_NamedElement(collaboration);
				if (result == null) result = caseUML2_PackageableElement(collaboration);
				if (result == null) result = caseUML2_TemplateableElement(collaboration);
				if (result == null) result = caseUML2_ParameterableElement(collaboration);
				if (result == null) result = caseUML2_Element(collaboration);
				if (result == null) result = caseEModelElement(collaboration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PORT:
			{
				Port port = (Port)theEObject;
				Object result = casePort(port);
				if (result == null) result = caseUML2_Property(port);
				if (result == null) result = caseUML2_StructuralFeature(port);
				if (result == null) result = caseUML2_ConnectableElement(port);
				if (result == null) result = caseUML2_DeploymentTarget(port);
				if (result == null) result = caseUML2_Feature(port);
				if (result == null) result = caseUML2_TypedElement(port);
				if (result == null) result = caseUML2_MultiplicityElement(port);
				if (result == null) result = caseUML2_NamedElement(port);
				if (result == null) result = caseUML2_ParameterableElement(port);
				if (result == null) result = caseUML2_RedefinableElement(port);
				if (result == null) result = caseUML2_Element(port);
				if (result == null) result = caseUML2_TemplateableElement(port);
				if (result == null) result = caseEModelElement(port);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ENCAPSULATED_CLASSIFIER:
			{
				EncapsulatedClassifier encapsulatedClassifier = (EncapsulatedClassifier)theEObject;
				Object result = caseEncapsulatedClassifier(encapsulatedClassifier);
				if (result == null) result = caseUML2_StructuredClassifier(encapsulatedClassifier);
				if (result == null) result = caseUML2_Classifier(encapsulatedClassifier);
				if (result == null) result = caseUML2_Namespace(encapsulatedClassifier);
				if (result == null) result = caseUML2_Type(encapsulatedClassifier);
				if (result == null) result = caseUML2_RedefinableElement(encapsulatedClassifier);
				if (result == null) result = caseUML2_NamedElement(encapsulatedClassifier);
				if (result == null) result = caseUML2_PackageableElement(encapsulatedClassifier);
				if (result == null) result = caseUML2_TemplateableElement(encapsulatedClassifier);
				if (result == null) result = caseUML2_ParameterableElement(encapsulatedClassifier);
				if (result == null) result = caseUML2_Element(encapsulatedClassifier);
				if (result == null) result = caseEModelElement(encapsulatedClassifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CALL_TRIGGER:
			{
				CallTrigger callTrigger = (CallTrigger)theEObject;
				Object result = caseCallTrigger(callTrigger);
				if (result == null) result = caseUML2_MessageTrigger(callTrigger);
				if (result == null) result = caseUML2_Trigger(callTrigger);
				if (result == null) result = caseUML2_NamedElement(callTrigger);
				if (result == null) result = caseUML2_TemplateableElement(callTrigger);
				if (result == null) result = caseUML2_Element(callTrigger);
				if (result == null) result = caseEModelElement(callTrigger);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.MESSAGE_TRIGGER:
			{
				MessageTrigger messageTrigger = (MessageTrigger)theEObject;
				Object result = caseMessageTrigger(messageTrigger);
				if (result == null) result = caseUML2_Trigger(messageTrigger);
				if (result == null) result = caseUML2_NamedElement(messageTrigger);
				if (result == null) result = caseUML2_TemplateableElement(messageTrigger);
				if (result == null) result = caseUML2_Element(messageTrigger);
				if (result == null) result = caseEModelElement(messageTrigger);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CHANGE_TRIGGER:
			{
				ChangeTrigger changeTrigger = (ChangeTrigger)theEObject;
				Object result = caseChangeTrigger(changeTrigger);
				if (result == null) result = caseUML2_Trigger(changeTrigger);
				if (result == null) result = caseUML2_NamedElement(changeTrigger);
				if (result == null) result = caseUML2_TemplateableElement(changeTrigger);
				if (result == null) result = caseUML2_Element(changeTrigger);
				if (result == null) result = caseEModelElement(changeTrigger);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TRIGGER:
			{
				Trigger trigger = (Trigger)theEObject;
				Object result = caseTrigger(trigger);
				if (result == null) result = caseUML2_NamedElement(trigger);
				if (result == null) result = caseUML2_TemplateableElement(trigger);
				if (result == null) result = caseUML2_Element(trigger);
				if (result == null) result = caseEModelElement(trigger);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.RECEPTION:
			{
				Reception reception = (Reception)theEObject;
				Object result = caseReception(reception);
				if (result == null) result = caseUML2_BehavioralFeature(reception);
				if (result == null) result = caseUML2_Namespace(reception);
				if (result == null) result = caseUML2_Feature(reception);
				if (result == null) result = caseUML2_NamedElement(reception);
				if (result == null) result = caseUML2_RedefinableElement(reception);
				if (result == null) result = caseUML2_TemplateableElement(reception);
				if (result == null) result = caseUML2_Element(reception);
				if (result == null) result = caseEModelElement(reception);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.SIGNAL:
			{
				Signal signal = (Signal)theEObject;
				Object result = caseSignal(signal);
				if (result == null) result = caseUML2_Classifier(signal);
				if (result == null) result = caseUML2_Namespace(signal);
				if (result == null) result = caseUML2_Type(signal);
				if (result == null) result = caseUML2_RedefinableElement(signal);
				if (result == null) result = caseUML2_NamedElement(signal);
				if (result == null) result = caseUML2_PackageableElement(signal);
				if (result == null) result = caseUML2_TemplateableElement(signal);
				if (result == null) result = caseUML2_ParameterableElement(signal);
				if (result == null) result = caseUML2_Element(signal);
				if (result == null) result = caseEModelElement(signal);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.SIGNAL_TRIGGER:
			{
				SignalTrigger signalTrigger = (SignalTrigger)theEObject;
				Object result = caseSignalTrigger(signalTrigger);
				if (result == null) result = caseUML2_MessageTrigger(signalTrigger);
				if (result == null) result = caseUML2_Trigger(signalTrigger);
				if (result == null) result = caseUML2_NamedElement(signalTrigger);
				if (result == null) result = caseUML2_TemplateableElement(signalTrigger);
				if (result == null) result = caseUML2_Element(signalTrigger);
				if (result == null) result = caseEModelElement(signalTrigger);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TIME_TRIGGER:
			{
				TimeTrigger timeTrigger = (TimeTrigger)theEObject;
				Object result = caseTimeTrigger(timeTrigger);
				if (result == null) result = caseUML2_Trigger(timeTrigger);
				if (result == null) result = caseUML2_NamedElement(timeTrigger);
				if (result == null) result = caseUML2_TemplateableElement(timeTrigger);
				if (result == null) result = caseUML2_Element(timeTrigger);
				if (result == null) result = caseEModelElement(timeTrigger);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ANY_TRIGGER:
			{
				AnyTrigger anyTrigger = (AnyTrigger)theEObject;
				Object result = caseAnyTrigger(anyTrigger);
				if (result == null) result = caseUML2_MessageTrigger(anyTrigger);
				if (result == null) result = caseUML2_Trigger(anyTrigger);
				if (result == null) result = caseUML2_NamedElement(anyTrigger);
				if (result == null) result = caseUML2_TemplateableElement(anyTrigger);
				if (result == null) result = caseUML2_Element(anyTrigger);
				if (result == null) result = caseEModelElement(anyTrigger);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.VARIABLE:
			{
				Variable variable = (Variable)theEObject;
				Object result = caseVariable(variable);
				if (result == null) result = caseUML2_ConnectableElement(variable);
				if (result == null) result = caseUML2_TypedElement(variable);
				if (result == null) result = caseUML2_MultiplicityElement(variable);
				if (result == null) result = caseUML2_NamedElement(variable);
				if (result == null) result = caseUML2_ParameterableElement(variable);
				if (result == null) result = caseUML2_Element(variable);
				if (result == null) result = caseUML2_TemplateableElement(variable);
				if (result == null) result = caseEModelElement(variable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.STRUCTURED_ACTIVITY_NODE:
			{
				StructuredActivityNode structuredActivityNode = (StructuredActivityNode)theEObject;
				Object result = caseStructuredActivityNode(structuredActivityNode);
				if (result == null) result = caseUML2_Action(structuredActivityNode);
				if (result == null) result = caseUML2_Namespace(structuredActivityNode);
				if (result == null) result = caseUML2_ActivityGroup(structuredActivityNode);
				if (result == null) result = caseUML2_ExecutableNode(structuredActivityNode);
				if (result == null) result = caseUML2_NamedElement(structuredActivityNode);
				if (result == null) result = caseUML2_Element(structuredActivityNode);
				if (result == null) result = caseUML2_ActivityNode(structuredActivityNode);
				if (result == null) result = caseUML2_TemplateableElement(structuredActivityNode);
				if (result == null) result = caseEModelElement(structuredActivityNode);
				if (result == null) result = caseUML2_RedefinableElement(structuredActivityNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CONDITIONAL_NODE:
			{
				ConditionalNode conditionalNode = (ConditionalNode)theEObject;
				Object result = caseConditionalNode(conditionalNode);
				if (result == null) result = caseUML2_StructuredActivityNode(conditionalNode);
				if (result == null) result = caseUML2_Action(conditionalNode);
				if (result == null) result = caseUML2_Namespace(conditionalNode);
				if (result == null) result = caseUML2_ActivityGroup(conditionalNode);
				if (result == null) result = caseUML2_ExecutableNode(conditionalNode);
				if (result == null) result = caseUML2_NamedElement(conditionalNode);
				if (result == null) result = caseUML2_Element(conditionalNode);
				if (result == null) result = caseUML2_ActivityNode(conditionalNode);
				if (result == null) result = caseUML2_TemplateableElement(conditionalNode);
				if (result == null) result = caseEModelElement(conditionalNode);
				if (result == null) result = caseUML2_RedefinableElement(conditionalNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CLAUSE:
			{
				Clause clause = (Clause)theEObject;
				Object result = caseClause(clause);
				if (result == null) result = caseUML2_Element(clause);
				if (result == null) result = caseEModelElement(clause);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.LOOP_NODE:
			{
				LoopNode loopNode = (LoopNode)theEObject;
				Object result = caseLoopNode(loopNode);
				if (result == null) result = caseUML2_StructuredActivityNode(loopNode);
				if (result == null) result = caseUML2_Action(loopNode);
				if (result == null) result = caseUML2_Namespace(loopNode);
				if (result == null) result = caseUML2_ActivityGroup(loopNode);
				if (result == null) result = caseUML2_ExecutableNode(loopNode);
				if (result == null) result = caseUML2_NamedElement(loopNode);
				if (result == null) result = caseUML2_Element(loopNode);
				if (result == null) result = caseUML2_ActivityNode(loopNode);
				if (result == null) result = caseUML2_TemplateableElement(loopNode);
				if (result == null) result = caseEModelElement(loopNode);
				if (result == null) result = caseUML2_RedefinableElement(loopNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INTERACTION:
			{
				Interaction interaction = (Interaction)theEObject;
				Object result = caseInteraction(interaction);
				if (result == null) result = caseUML2_Behavior(interaction);
				if (result == null) result = caseUML2_InteractionFragment(interaction);
				if (result == null) result = caseUML2_Class(interaction);
				if (result == null) result = caseUML2_NamedElement(interaction);
				if (result == null) result = caseUML2_BehavioredClassifier(interaction);
				if (result == null) result = caseUML2_EncapsulatedClassifier(interaction);
				if (result == null) result = caseUML2_TemplateableElement(interaction);
				if (result == null) result = caseUML2_Classifier(interaction);
				if (result == null) result = caseUML2_StructuredClassifier(interaction);
				if (result == null) result = caseUML2_Element(interaction);
				if (result == null) result = caseUML2_Namespace(interaction);
				if (result == null) result = caseUML2_Type(interaction);
				if (result == null) result = caseUML2_RedefinableElement(interaction);
				if (result == null) result = caseEModelElement(interaction);
				if (result == null) result = caseUML2_PackageableElement(interaction);
				if (result == null) result = caseUML2_ParameterableElement(interaction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INTERACTION_FRAGMENT:
			{
				InteractionFragment interactionFragment = (InteractionFragment)theEObject;
				Object result = caseInteractionFragment(interactionFragment);
				if (result == null) result = caseUML2_NamedElement(interactionFragment);
				if (result == null) result = caseUML2_TemplateableElement(interactionFragment);
				if (result == null) result = caseUML2_Element(interactionFragment);
				if (result == null) result = caseEModelElement(interactionFragment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.LIFELINE:
			{
				Lifeline lifeline = (Lifeline)theEObject;
				Object result = caseLifeline(lifeline);
				if (result == null) result = caseUML2_NamedElement(lifeline);
				if (result == null) result = caseUML2_TemplateableElement(lifeline);
				if (result == null) result = caseUML2_Element(lifeline);
				if (result == null) result = caseEModelElement(lifeline);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.MESSAGE:
			{
				Message message = (Message)theEObject;
				Object result = caseMessage(message);
				if (result == null) result = caseUML2_NamedElement(message);
				if (result == null) result = caseUML2_TemplateableElement(message);
				if (result == null) result = caseUML2_Element(message);
				if (result == null) result = caseEModelElement(message);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.GENERAL_ORDERING:
			{
				GeneralOrdering generalOrdering = (GeneralOrdering)theEObject;
				Object result = caseGeneralOrdering(generalOrdering);
				if (result == null) result = caseUML2_NamedElement(generalOrdering);
				if (result == null) result = caseUML2_TemplateableElement(generalOrdering);
				if (result == null) result = caseUML2_Element(generalOrdering);
				if (result == null) result = caseEModelElement(generalOrdering);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.MESSAGE_END:
			{
				MessageEnd messageEnd = (MessageEnd)theEObject;
				Object result = caseMessageEnd(messageEnd);
				if (result == null) result = caseUML2_NamedElement(messageEnd);
				if (result == null) result = caseUML2_TemplateableElement(messageEnd);
				if (result == null) result = caseUML2_Element(messageEnd);
				if (result == null) result = caseEModelElement(messageEnd);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.EVENT_OCCURRENCE:
			{
				EventOccurrence eventOccurrence = (EventOccurrence)theEObject;
				Object result = caseEventOccurrence(eventOccurrence);
				if (result == null) result = caseUML2_InteractionFragment(eventOccurrence);
				if (result == null) result = caseUML2_MessageEnd(eventOccurrence);
				if (result == null) result = caseUML2_NamedElement(eventOccurrence);
				if (result == null) result = caseUML2_TemplateableElement(eventOccurrence);
				if (result == null) result = caseUML2_Element(eventOccurrence);
				if (result == null) result = caseEModelElement(eventOccurrence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.EXECUTION_OCCURRENCE:
			{
				ExecutionOccurrence executionOccurrence = (ExecutionOccurrence)theEObject;
				Object result = caseExecutionOccurrence(executionOccurrence);
				if (result == null) result = caseUML2_InteractionFragment(executionOccurrence);
				if (result == null) result = caseUML2_NamedElement(executionOccurrence);
				if (result == null) result = caseUML2_TemplateableElement(executionOccurrence);
				if (result == null) result = caseUML2_Element(executionOccurrence);
				if (result == null) result = caseEModelElement(executionOccurrence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.STATE_INVARIANT:
			{
				StateInvariant stateInvariant = (StateInvariant)theEObject;
				Object result = caseStateInvariant(stateInvariant);
				if (result == null) result = caseUML2_InteractionFragment(stateInvariant);
				if (result == null) result = caseUML2_NamedElement(stateInvariant);
				if (result == null) result = caseUML2_TemplateableElement(stateInvariant);
				if (result == null) result = caseUML2_Element(stateInvariant);
				if (result == null) result = caseEModelElement(stateInvariant);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.STOP:
			{
				Stop stop = (Stop)theEObject;
				Object result = caseStop(stop);
				if (result == null) result = caseUML2_EventOccurrence(stop);
				if (result == null) result = caseUML2_InteractionFragment(stop);
				if (result == null) result = caseUML2_MessageEnd(stop);
				if (result == null) result = caseUML2_NamedElement(stop);
				if (result == null) result = caseUML2_TemplateableElement(stop);
				if (result == null) result = caseUML2_Element(stop);
				if (result == null) result = caseEModelElement(stop);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TEMPLATE_SIGNATURE:
			{
				TemplateSignature templateSignature = (TemplateSignature)theEObject;
				Object result = caseTemplateSignature(templateSignature);
				if (result == null) result = caseUML2_Element(templateSignature);
				if (result == null) result = caseEModelElement(templateSignature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TEMPLATE_PARAMETER:
			{
				TemplateParameter templateParameter = (TemplateParameter)theEObject;
				Object result = caseTemplateParameter(templateParameter);
				if (result == null) result = caseUML2_Element(templateParameter);
				if (result == null) result = caseEModelElement(templateParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TEMPLATEABLE_ELEMENT:
			{
				TemplateableElement templateableElement = (TemplateableElement)theEObject;
				Object result = caseTemplateableElement(templateableElement);
				if (result == null) result = caseUML2_Element(templateableElement);
				if (result == null) result = caseEModelElement(templateableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.STRING_EXPRESSION:
			{
				StringExpression stringExpression = (StringExpression)theEObject;
				Object result = caseStringExpression(stringExpression);
				if (result == null) result = caseUML2_TemplateableElement(stringExpression);
				if (result == null) result = caseUML2_Element(stringExpression);
				if (result == null) result = caseEModelElement(stringExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PARAMETERABLE_ELEMENT:
			{
				ParameterableElement parameterableElement = (ParameterableElement)theEObject;
				Object result = caseParameterableElement(parameterableElement);
				if (result == null) result = caseUML2_Element(parameterableElement);
				if (result == null) result = caseEModelElement(parameterableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TEMPLATE_BINDING:
			{
				TemplateBinding templateBinding = (TemplateBinding)theEObject;
				Object result = caseTemplateBinding(templateBinding);
				if (result == null) result = caseUML2_DirectedRelationship(templateBinding);
				if (result == null) result = caseUML2_Relationship(templateBinding);
				if (result == null) result = caseUML2_Element(templateBinding);
				if (result == null) result = caseEModelElement(templateBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TEMPLATE_PARAMETER_SUBSTITUTION:
			{
				TemplateParameterSubstitution templateParameterSubstitution = (TemplateParameterSubstitution)theEObject;
				Object result = caseTemplateParameterSubstitution(templateParameterSubstitution);
				if (result == null) result = caseUML2_Element(templateParameterSubstitution);
				if (result == null) result = caseEModelElement(templateParameterSubstitution);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.OPERATION_TEMPLATE_PARAMETER:
			{
				OperationTemplateParameter operationTemplateParameter = (OperationTemplateParameter)theEObject;
				Object result = caseOperationTemplateParameter(operationTemplateParameter);
				if (result == null) result = caseUML2_TemplateParameter(operationTemplateParameter);
				if (result == null) result = caseUML2_Element(operationTemplateParameter);
				if (result == null) result = caseEModelElement(operationTemplateParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CLASSIFIER_TEMPLATE_PARAMETER:
			{
				ClassifierTemplateParameter classifierTemplateParameter = (ClassifierTemplateParameter)theEObject;
				Object result = caseClassifierTemplateParameter(classifierTemplateParameter);
				if (result == null) result = caseUML2_TemplateParameter(classifierTemplateParameter);
				if (result == null) result = caseUML2_Element(classifierTemplateParameter);
				if (result == null) result = caseEModelElement(classifierTemplateParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PARAMETERABLE_CLASSIFIER:
			{
				ParameterableClassifier parameterableClassifier = (ParameterableClassifier)theEObject;
				Object result = caseParameterableClassifier(parameterableClassifier);
				if (result == null) result = caseUML2_Classifier(parameterableClassifier);
				if (result == null) result = caseUML2_Namespace(parameterableClassifier);
				if (result == null) result = caseUML2_Type(parameterableClassifier);
				if (result == null) result = caseUML2_RedefinableElement(parameterableClassifier);
				if (result == null) result = caseUML2_NamedElement(parameterableClassifier);
				if (result == null) result = caseUML2_PackageableElement(parameterableClassifier);
				if (result == null) result = caseUML2_TemplateableElement(parameterableClassifier);
				if (result == null) result = caseUML2_ParameterableElement(parameterableClassifier);
				if (result == null) result = caseUML2_Element(parameterableClassifier);
				if (result == null) result = caseEModelElement(parameterableClassifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.REDEFINABLE_TEMPLATE_SIGNATURE:
			{
				RedefinableTemplateSignature redefinableTemplateSignature = (RedefinableTemplateSignature)theEObject;
				Object result = caseRedefinableTemplateSignature(redefinableTemplateSignature);
				if (result == null) result = caseUML2_RedefinableElement(redefinableTemplateSignature);
				if (result == null) result = caseUML2_TemplateSignature(redefinableTemplateSignature);
				if (result == null) result = caseUML2_NamedElement(redefinableTemplateSignature);
				if (result == null) result = caseUML2_Element(redefinableTemplateSignature);
				if (result == null) result = caseUML2_TemplateableElement(redefinableTemplateSignature);
				if (result == null) result = caseEModelElement(redefinableTemplateSignature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TEMPLATEABLE_CLASSIFIER:
			{
				TemplateableClassifier templateableClassifier = (TemplateableClassifier)theEObject;
				Object result = caseTemplateableClassifier(templateableClassifier);
				if (result == null) result = caseUML2_Classifier(templateableClassifier);
				if (result == null) result = caseUML2_Namespace(templateableClassifier);
				if (result == null) result = caseUML2_Type(templateableClassifier);
				if (result == null) result = caseUML2_RedefinableElement(templateableClassifier);
				if (result == null) result = caseUML2_NamedElement(templateableClassifier);
				if (result == null) result = caseUML2_PackageableElement(templateableClassifier);
				if (result == null) result = caseUML2_TemplateableElement(templateableClassifier);
				if (result == null) result = caseUML2_ParameterableElement(templateableClassifier);
				if (result == null) result = caseUML2_Element(templateableClassifier);
				if (result == null) result = caseEModelElement(templateableClassifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CONNECTABLE_ELEMENT_TEMPLATE_PARAMETER:
			{
				ConnectableElementTemplateParameter connectableElementTemplateParameter = (ConnectableElementTemplateParameter)theEObject;
				Object result = caseConnectableElementTemplateParameter(connectableElementTemplateParameter);
				if (result == null) result = caseUML2_TemplateParameter(connectableElementTemplateParameter);
				if (result == null) result = caseUML2_Element(connectableElementTemplateParameter);
				if (result == null) result = caseEModelElement(connectableElementTemplateParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.FORK_NODE:
			{
				ForkNode forkNode = (ForkNode)theEObject;
				Object result = caseForkNode(forkNode);
				if (result == null) result = caseUML2_ControlNode(forkNode);
				if (result == null) result = caseUML2_ActivityNode(forkNode);
				if (result == null) result = caseUML2_RedefinableElement(forkNode);
				if (result == null) result = caseUML2_NamedElement(forkNode);
				if (result == null) result = caseUML2_TemplateableElement(forkNode);
				if (result == null) result = caseUML2_Element(forkNode);
				if (result == null) result = caseEModelElement(forkNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.JOIN_NODE:
			{
				JoinNode joinNode = (JoinNode)theEObject;
				Object result = caseJoinNode(joinNode);
				if (result == null) result = caseUML2_ControlNode(joinNode);
				if (result == null) result = caseUML2_ActivityNode(joinNode);
				if (result == null) result = caseUML2_RedefinableElement(joinNode);
				if (result == null) result = caseUML2_NamedElement(joinNode);
				if (result == null) result = caseUML2_TemplateableElement(joinNode);
				if (result == null) result = caseUML2_Element(joinNode);
				if (result == null) result = caseEModelElement(joinNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.FLOW_FINAL_NODE:
			{
				FlowFinalNode flowFinalNode = (FlowFinalNode)theEObject;
				Object result = caseFlowFinalNode(flowFinalNode);
				if (result == null) result = caseUML2_FinalNode(flowFinalNode);
				if (result == null) result = caseUML2_ControlNode(flowFinalNode);
				if (result == null) result = caseUML2_ActivityNode(flowFinalNode);
				if (result == null) result = caseUML2_RedefinableElement(flowFinalNode);
				if (result == null) result = caseUML2_NamedElement(flowFinalNode);
				if (result == null) result = caseUML2_TemplateableElement(flowFinalNode);
				if (result == null) result = caseUML2_Element(flowFinalNode);
				if (result == null) result = caseEModelElement(flowFinalNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CENTRAL_BUFFER_NODE:
			{
				CentralBufferNode centralBufferNode = (CentralBufferNode)theEObject;
				Object result = caseCentralBufferNode(centralBufferNode);
				if (result == null) result = caseUML2_ObjectNode(centralBufferNode);
				if (result == null) result = caseUML2_ActivityNode(centralBufferNode);
				if (result == null) result = caseUML2_TypedElement(centralBufferNode);
				if (result == null) result = caseUML2_RedefinableElement(centralBufferNode);
				if (result == null) result = caseUML2_NamedElement(centralBufferNode);
				if (result == null) result = caseUML2_TemplateableElement(centralBufferNode);
				if (result == null) result = caseUML2_Element(centralBufferNode);
				if (result == null) result = caseEModelElement(centralBufferNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ACTIVITY_PARTITION:
			{
				ActivityPartition activityPartition = (ActivityPartition)theEObject;
				Object result = caseActivityPartition(activityPartition);
				if (result == null) result = caseUML2_NamedElement(activityPartition);
				if (result == null) result = caseUML2_ActivityGroup(activityPartition);
				if (result == null) result = caseUML2_TemplateableElement(activityPartition);
				if (result == null) result = caseUML2_Element(activityPartition);
				if (result == null) result = caseEModelElement(activityPartition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.EXPANSION_NODE:
			{
				ExpansionNode expansionNode = (ExpansionNode)theEObject;
				Object result = caseExpansionNode(expansionNode);
				if (result == null) result = caseUML2_ObjectNode(expansionNode);
				if (result == null) result = caseUML2_ActivityNode(expansionNode);
				if (result == null) result = caseUML2_TypedElement(expansionNode);
				if (result == null) result = caseUML2_RedefinableElement(expansionNode);
				if (result == null) result = caseUML2_NamedElement(expansionNode);
				if (result == null) result = caseUML2_TemplateableElement(expansionNode);
				if (result == null) result = caseUML2_Element(expansionNode);
				if (result == null) result = caseEModelElement(expansionNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.EXPANSION_REGION:
			{
				ExpansionRegion expansionRegion = (ExpansionRegion)theEObject;
				Object result = caseExpansionRegion(expansionRegion);
				if (result == null) result = caseUML2_StructuredActivityNode(expansionRegion);
				if (result == null) result = caseUML2_Action(expansionRegion);
				if (result == null) result = caseUML2_Namespace(expansionRegion);
				if (result == null) result = caseUML2_ActivityGroup(expansionRegion);
				if (result == null) result = caseUML2_ExecutableNode(expansionRegion);
				if (result == null) result = caseUML2_NamedElement(expansionRegion);
				if (result == null) result = caseUML2_Element(expansionRegion);
				if (result == null) result = caseUML2_ActivityNode(expansionRegion);
				if (result == null) result = caseUML2_TemplateableElement(expansionRegion);
				if (result == null) result = caseEModelElement(expansionRegion);
				if (result == null) result = caseUML2_RedefinableElement(expansionRegion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.EXCEPTION_HANDLER:
			{
				ExceptionHandler exceptionHandler = (ExceptionHandler)theEObject;
				Object result = caseExceptionHandler(exceptionHandler);
				if (result == null) result = caseUML2_Element(exceptionHandler);
				if (result == null) result = caseEModelElement(exceptionHandler);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INTERACTION_OCCURRENCE:
			{
				InteractionOccurrence interactionOccurrence = (InteractionOccurrence)theEObject;
				Object result = caseInteractionOccurrence(interactionOccurrence);
				if (result == null) result = caseUML2_InteractionFragment(interactionOccurrence);
				if (result == null) result = caseUML2_NamedElement(interactionOccurrence);
				if (result == null) result = caseUML2_TemplateableElement(interactionOccurrence);
				if (result == null) result = caseUML2_Element(interactionOccurrence);
				if (result == null) result = caseEModelElement(interactionOccurrence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.GATE:
			{
				Gate gate = (Gate)theEObject;
				Object result = caseGate(gate);
				if (result == null) result = caseUML2_MessageEnd(gate);
				if (result == null) result = caseUML2_NamedElement(gate);
				if (result == null) result = caseUML2_TemplateableElement(gate);
				if (result == null) result = caseUML2_Element(gate);
				if (result == null) result = caseEModelElement(gate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PART_DECOMPOSITION:
			{
				PartDecomposition partDecomposition = (PartDecomposition)theEObject;
				Object result = casePartDecomposition(partDecomposition);
				if (result == null) result = caseUML2_InteractionOccurrence(partDecomposition);
				if (result == null) result = caseUML2_InteractionFragment(partDecomposition);
				if (result == null) result = caseUML2_NamedElement(partDecomposition);
				if (result == null) result = caseUML2_TemplateableElement(partDecomposition);
				if (result == null) result = caseUML2_Element(partDecomposition);
				if (result == null) result = caseEModelElement(partDecomposition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INTERACTION_OPERAND:
			{
				InteractionOperand interactionOperand = (InteractionOperand)theEObject;
				Object result = caseInteractionOperand(interactionOperand);
				if (result == null) result = caseUML2_Namespace(interactionOperand);
				if (result == null) result = caseUML2_InteractionFragment(interactionOperand);
				if (result == null) result = caseUML2_NamedElement(interactionOperand);
				if (result == null) result = caseUML2_TemplateableElement(interactionOperand);
				if (result == null) result = caseUML2_Element(interactionOperand);
				if (result == null) result = caseEModelElement(interactionOperand);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INTERACTION_CONSTRAINT:
			{
				InteractionConstraint interactionConstraint = (InteractionConstraint)theEObject;
				Object result = caseInteractionConstraint(interactionConstraint);
				if (result == null) result = caseUML2_Constraint(interactionConstraint);
				if (result == null) result = caseUML2_PackageableElement(interactionConstraint);
				if (result == null) result = caseUML2_NamedElement(interactionConstraint);
				if (result == null) result = caseUML2_ParameterableElement(interactionConstraint);
				if (result == null) result = caseUML2_TemplateableElement(interactionConstraint);
				if (result == null) result = caseUML2_Element(interactionConstraint);
				if (result == null) result = caseEModelElement(interactionConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.COMBINED_FRAGMENT:
			{
				CombinedFragment combinedFragment = (CombinedFragment)theEObject;
				Object result = caseCombinedFragment(combinedFragment);
				if (result == null) result = caseUML2_InteractionFragment(combinedFragment);
				if (result == null) result = caseUML2_NamedElement(combinedFragment);
				if (result == null) result = caseUML2_TemplateableElement(combinedFragment);
				if (result == null) result = caseUML2_Element(combinedFragment);
				if (result == null) result = caseEModelElement(combinedFragment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CONTINUATION:
			{
				Continuation continuation = (Continuation)theEObject;
				Object result = caseContinuation(continuation);
				if (result == null) result = caseUML2_InteractionFragment(continuation);
				if (result == null) result = caseUML2_NamedElement(continuation);
				if (result == null) result = caseUML2_TemplateableElement(continuation);
				if (result == null) result = caseUML2_Element(continuation);
				if (result == null) result = caseEModelElement(continuation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.STATE_MACHINE:
			{
				StateMachine stateMachine = (StateMachine)theEObject;
				Object result = caseStateMachine(stateMachine);
				if (result == null) result = caseUML2_Behavior(stateMachine);
				if (result == null) result = caseUML2_Class(stateMachine);
				if (result == null) result = caseUML2_BehavioredClassifier(stateMachine);
				if (result == null) result = caseUML2_EncapsulatedClassifier(stateMachine);
				if (result == null) result = caseUML2_Classifier(stateMachine);
				if (result == null) result = caseUML2_StructuredClassifier(stateMachine);
				if (result == null) result = caseUML2_Namespace(stateMachine);
				if (result == null) result = caseUML2_Type(stateMachine);
				if (result == null) result = caseUML2_RedefinableElement(stateMachine);
				if (result == null) result = caseUML2_NamedElement(stateMachine);
				if (result == null) result = caseUML2_PackageableElement(stateMachine);
				if (result == null) result = caseUML2_TemplateableElement(stateMachine);
				if (result == null) result = caseUML2_ParameterableElement(stateMachine);
				if (result == null) result = caseUML2_Element(stateMachine);
				if (result == null) result = caseEModelElement(stateMachine);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.REGION:
			{
				Region region = (Region)theEObject;
				Object result = caseRegion(region);
				if (result == null) result = caseUML2_Namespace(region);
				if (result == null) result = caseUML2_RedefinableElement(region);
				if (result == null) result = caseUML2_NamedElement(region);
				if (result == null) result = caseUML2_TemplateableElement(region);
				if (result == null) result = caseUML2_Element(region);
				if (result == null) result = caseEModelElement(region);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PSEUDOSTATE:
			{
				Pseudostate pseudostate = (Pseudostate)theEObject;
				Object result = casePseudostate(pseudostate);
				if (result == null) result = caseUML2_Vertex(pseudostate);
				if (result == null) result = caseUML2_NamedElement(pseudostate);
				if (result == null) result = caseUML2_TemplateableElement(pseudostate);
				if (result == null) result = caseUML2_Element(pseudostate);
				if (result == null) result = caseEModelElement(pseudostate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.STATE:
			{
				State state = (State)theEObject;
				Object result = caseState(state);
				if (result == null) result = caseUML2_Namespace(state);
				if (result == null) result = caseUML2_RedefinableElement(state);
				if (result == null) result = caseUML2_Vertex(state);
				if (result == null) result = caseUML2_NamedElement(state);
				if (result == null) result = caseUML2_TemplateableElement(state);
				if (result == null) result = caseUML2_Element(state);
				if (result == null) result = caseEModelElement(state);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.VERTEX:
			{
				Vertex vertex = (Vertex)theEObject;
				Object result = caseVertex(vertex);
				if (result == null) result = caseUML2_NamedElement(vertex);
				if (result == null) result = caseUML2_TemplateableElement(vertex);
				if (result == null) result = caseUML2_Element(vertex);
				if (result == null) result = caseEModelElement(vertex);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CONNECTION_POINT_REFERENCE:
			{
				ConnectionPointReference connectionPointReference = (ConnectionPointReference)theEObject;
				Object result = caseConnectionPointReference(connectionPointReference);
				if (result == null) result = caseUML2_Vertex(connectionPointReference);
				if (result == null) result = caseUML2_NamedElement(connectionPointReference);
				if (result == null) result = caseUML2_TemplateableElement(connectionPointReference);
				if (result == null) result = caseUML2_Element(connectionPointReference);
				if (result == null) result = caseEModelElement(connectionPointReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TRANSITION:
			{
				Transition transition = (Transition)theEObject;
				Object result = caseTransition(transition);
				if (result == null) result = caseUML2_RedefinableElement(transition);
				if (result == null) result = caseUML2_NamedElement(transition);
				if (result == null) result = caseUML2_TemplateableElement(transition);
				if (result == null) result = caseUML2_Element(transition);
				if (result == null) result = caseEModelElement(transition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.FINAL_STATE:
			{
				FinalState finalState = (FinalState)theEObject;
				Object result = caseFinalState(finalState);
				if (result == null) result = caseUML2_State(finalState);
				if (result == null) result = caseUML2_Namespace(finalState);
				if (result == null) result = caseUML2_RedefinableElement(finalState);
				if (result == null) result = caseUML2_Vertex(finalState);
				if (result == null) result = caseUML2_NamedElement(finalState);
				if (result == null) result = caseUML2_TemplateableElement(finalState);
				if (result == null) result = caseUML2_Element(finalState);
				if (result == null) result = caseEModelElement(finalState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CREATE_OBJECT_ACTION:
			{
				CreateObjectAction createObjectAction = (CreateObjectAction)theEObject;
				Object result = caseCreateObjectAction(createObjectAction);
				if (result == null) result = caseUML2_Action(createObjectAction);
				if (result == null) result = caseUML2_ExecutableNode(createObjectAction);
				if (result == null) result = caseUML2_ActivityNode(createObjectAction);
				if (result == null) result = caseUML2_RedefinableElement(createObjectAction);
				if (result == null) result = caseUML2_NamedElement(createObjectAction);
				if (result == null) result = caseUML2_TemplateableElement(createObjectAction);
				if (result == null) result = caseUML2_Element(createObjectAction);
				if (result == null) result = caseEModelElement(createObjectAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DESTROY_OBJECT_ACTION:
			{
				DestroyObjectAction destroyObjectAction = (DestroyObjectAction)theEObject;
				Object result = caseDestroyObjectAction(destroyObjectAction);
				if (result == null) result = caseUML2_Action(destroyObjectAction);
				if (result == null) result = caseUML2_ExecutableNode(destroyObjectAction);
				if (result == null) result = caseUML2_ActivityNode(destroyObjectAction);
				if (result == null) result = caseUML2_RedefinableElement(destroyObjectAction);
				if (result == null) result = caseUML2_NamedElement(destroyObjectAction);
				if (result == null) result = caseUML2_TemplateableElement(destroyObjectAction);
				if (result == null) result = caseUML2_Element(destroyObjectAction);
				if (result == null) result = caseEModelElement(destroyObjectAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TEST_IDENTITY_ACTION:
			{
				TestIdentityAction testIdentityAction = (TestIdentityAction)theEObject;
				Object result = caseTestIdentityAction(testIdentityAction);
				if (result == null) result = caseUML2_Action(testIdentityAction);
				if (result == null) result = caseUML2_ExecutableNode(testIdentityAction);
				if (result == null) result = caseUML2_ActivityNode(testIdentityAction);
				if (result == null) result = caseUML2_RedefinableElement(testIdentityAction);
				if (result == null) result = caseUML2_NamedElement(testIdentityAction);
				if (result == null) result = caseUML2_TemplateableElement(testIdentityAction);
				if (result == null) result = caseUML2_Element(testIdentityAction);
				if (result == null) result = caseEModelElement(testIdentityAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.READ_SELF_ACTION:
			{
				ReadSelfAction readSelfAction = (ReadSelfAction)theEObject;
				Object result = caseReadSelfAction(readSelfAction);
				if (result == null) result = caseUML2_Action(readSelfAction);
				if (result == null) result = caseUML2_ExecutableNode(readSelfAction);
				if (result == null) result = caseUML2_ActivityNode(readSelfAction);
				if (result == null) result = caseUML2_RedefinableElement(readSelfAction);
				if (result == null) result = caseUML2_NamedElement(readSelfAction);
				if (result == null) result = caseUML2_TemplateableElement(readSelfAction);
				if (result == null) result = caseUML2_Element(readSelfAction);
				if (result == null) result = caseEModelElement(readSelfAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.STRUCTURAL_FEATURE_ACTION:
			{
				StructuralFeatureAction structuralFeatureAction = (StructuralFeatureAction)theEObject;
				Object result = caseStructuralFeatureAction(structuralFeatureAction);
				if (result == null) result = caseUML2_Action(structuralFeatureAction);
				if (result == null) result = caseUML2_ExecutableNode(structuralFeatureAction);
				if (result == null) result = caseUML2_ActivityNode(structuralFeatureAction);
				if (result == null) result = caseUML2_RedefinableElement(structuralFeatureAction);
				if (result == null) result = caseUML2_NamedElement(structuralFeatureAction);
				if (result == null) result = caseUML2_TemplateableElement(structuralFeatureAction);
				if (result == null) result = caseUML2_Element(structuralFeatureAction);
				if (result == null) result = caseEModelElement(structuralFeatureAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.READ_STRUCTURAL_FEATURE_ACTION:
			{
				ReadStructuralFeatureAction readStructuralFeatureAction = (ReadStructuralFeatureAction)theEObject;
				Object result = caseReadStructuralFeatureAction(readStructuralFeatureAction);
				if (result == null) result = caseUML2_StructuralFeatureAction(readStructuralFeatureAction);
				if (result == null) result = caseUML2_Action(readStructuralFeatureAction);
				if (result == null) result = caseUML2_ExecutableNode(readStructuralFeatureAction);
				if (result == null) result = caseUML2_ActivityNode(readStructuralFeatureAction);
				if (result == null) result = caseUML2_RedefinableElement(readStructuralFeatureAction);
				if (result == null) result = caseUML2_NamedElement(readStructuralFeatureAction);
				if (result == null) result = caseUML2_TemplateableElement(readStructuralFeatureAction);
				if (result == null) result = caseUML2_Element(readStructuralFeatureAction);
				if (result == null) result = caseEModelElement(readStructuralFeatureAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.WRITE_STRUCTURAL_FEATURE_ACTION:
			{
				WriteStructuralFeatureAction writeStructuralFeatureAction = (WriteStructuralFeatureAction)theEObject;
				Object result = caseWriteStructuralFeatureAction(writeStructuralFeatureAction);
				if (result == null) result = caseUML2_StructuralFeatureAction(writeStructuralFeatureAction);
				if (result == null) result = caseUML2_Action(writeStructuralFeatureAction);
				if (result == null) result = caseUML2_ExecutableNode(writeStructuralFeatureAction);
				if (result == null) result = caseUML2_ActivityNode(writeStructuralFeatureAction);
				if (result == null) result = caseUML2_RedefinableElement(writeStructuralFeatureAction);
				if (result == null) result = caseUML2_NamedElement(writeStructuralFeatureAction);
				if (result == null) result = caseUML2_TemplateableElement(writeStructuralFeatureAction);
				if (result == null) result = caseUML2_Element(writeStructuralFeatureAction);
				if (result == null) result = caseEModelElement(writeStructuralFeatureAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CLEAR_STRUCTURAL_FEATURE_ACTION:
			{
				ClearStructuralFeatureAction clearStructuralFeatureAction = (ClearStructuralFeatureAction)theEObject;
				Object result = caseClearStructuralFeatureAction(clearStructuralFeatureAction);
				if (result == null) result = caseUML2_StructuralFeatureAction(clearStructuralFeatureAction);
				if (result == null) result = caseUML2_Action(clearStructuralFeatureAction);
				if (result == null) result = caseUML2_ExecutableNode(clearStructuralFeatureAction);
				if (result == null) result = caseUML2_ActivityNode(clearStructuralFeatureAction);
				if (result == null) result = caseUML2_RedefinableElement(clearStructuralFeatureAction);
				if (result == null) result = caseUML2_NamedElement(clearStructuralFeatureAction);
				if (result == null) result = caseUML2_TemplateableElement(clearStructuralFeatureAction);
				if (result == null) result = caseUML2_Element(clearStructuralFeatureAction);
				if (result == null) result = caseEModelElement(clearStructuralFeatureAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION:
			{
				RemoveStructuralFeatureValueAction removeStructuralFeatureValueAction = (RemoveStructuralFeatureValueAction)theEObject;
				Object result = caseRemoveStructuralFeatureValueAction(removeStructuralFeatureValueAction);
				if (result == null) result = caseUML2_WriteStructuralFeatureAction(removeStructuralFeatureValueAction);
				if (result == null) result = caseUML2_StructuralFeatureAction(removeStructuralFeatureValueAction);
				if (result == null) result = caseUML2_Action(removeStructuralFeatureValueAction);
				if (result == null) result = caseUML2_ExecutableNode(removeStructuralFeatureValueAction);
				if (result == null) result = caseUML2_ActivityNode(removeStructuralFeatureValueAction);
				if (result == null) result = caseUML2_RedefinableElement(removeStructuralFeatureValueAction);
				if (result == null) result = caseUML2_NamedElement(removeStructuralFeatureValueAction);
				if (result == null) result = caseUML2_TemplateableElement(removeStructuralFeatureValueAction);
				if (result == null) result = caseUML2_Element(removeStructuralFeatureValueAction);
				if (result == null) result = caseEModelElement(removeStructuralFeatureValueAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ADD_STRUCTURAL_FEATURE_VALUE_ACTION:
			{
				AddStructuralFeatureValueAction addStructuralFeatureValueAction = (AddStructuralFeatureValueAction)theEObject;
				Object result = caseAddStructuralFeatureValueAction(addStructuralFeatureValueAction);
				if (result == null) result = caseUML2_WriteStructuralFeatureAction(addStructuralFeatureValueAction);
				if (result == null) result = caseUML2_StructuralFeatureAction(addStructuralFeatureValueAction);
				if (result == null) result = caseUML2_Action(addStructuralFeatureValueAction);
				if (result == null) result = caseUML2_ExecutableNode(addStructuralFeatureValueAction);
				if (result == null) result = caseUML2_ActivityNode(addStructuralFeatureValueAction);
				if (result == null) result = caseUML2_RedefinableElement(addStructuralFeatureValueAction);
				if (result == null) result = caseUML2_NamedElement(addStructuralFeatureValueAction);
				if (result == null) result = caseUML2_TemplateableElement(addStructuralFeatureValueAction);
				if (result == null) result = caseUML2_Element(addStructuralFeatureValueAction);
				if (result == null) result = caseEModelElement(addStructuralFeatureValueAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.LINK_ACTION:
			{
				LinkAction linkAction = (LinkAction)theEObject;
				Object result = caseLinkAction(linkAction);
				if (result == null) result = caseUML2_Action(linkAction);
				if (result == null) result = caseUML2_ExecutableNode(linkAction);
				if (result == null) result = caseUML2_ActivityNode(linkAction);
				if (result == null) result = caseUML2_RedefinableElement(linkAction);
				if (result == null) result = caseUML2_NamedElement(linkAction);
				if (result == null) result = caseUML2_TemplateableElement(linkAction);
				if (result == null) result = caseUML2_Element(linkAction);
				if (result == null) result = caseEModelElement(linkAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.LINK_END_DATA:
			{
				LinkEndData linkEndData = (LinkEndData)theEObject;
				Object result = caseLinkEndData(linkEndData);
				if (result == null) result = caseUML2_Element(linkEndData);
				if (result == null) result = caseEModelElement(linkEndData);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.READ_LINK_ACTION:
			{
				ReadLinkAction readLinkAction = (ReadLinkAction)theEObject;
				Object result = caseReadLinkAction(readLinkAction);
				if (result == null) result = caseUML2_LinkAction(readLinkAction);
				if (result == null) result = caseUML2_Action(readLinkAction);
				if (result == null) result = caseUML2_ExecutableNode(readLinkAction);
				if (result == null) result = caseUML2_ActivityNode(readLinkAction);
				if (result == null) result = caseUML2_RedefinableElement(readLinkAction);
				if (result == null) result = caseUML2_NamedElement(readLinkAction);
				if (result == null) result = caseUML2_TemplateableElement(readLinkAction);
				if (result == null) result = caseUML2_Element(readLinkAction);
				if (result == null) result = caseEModelElement(readLinkAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.LINK_END_CREATION_DATA:
			{
				LinkEndCreationData linkEndCreationData = (LinkEndCreationData)theEObject;
				Object result = caseLinkEndCreationData(linkEndCreationData);
				if (result == null) result = caseUML2_LinkEndData(linkEndCreationData);
				if (result == null) result = caseUML2_Element(linkEndCreationData);
				if (result == null) result = caseEModelElement(linkEndCreationData);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CREATE_LINK_ACTION:
			{
				CreateLinkAction createLinkAction = (CreateLinkAction)theEObject;
				Object result = caseCreateLinkAction(createLinkAction);
				if (result == null) result = caseUML2_WriteLinkAction(createLinkAction);
				if (result == null) result = caseUML2_LinkAction(createLinkAction);
				if (result == null) result = caseUML2_Action(createLinkAction);
				if (result == null) result = caseUML2_ExecutableNode(createLinkAction);
				if (result == null) result = caseUML2_ActivityNode(createLinkAction);
				if (result == null) result = caseUML2_RedefinableElement(createLinkAction);
				if (result == null) result = caseUML2_NamedElement(createLinkAction);
				if (result == null) result = caseUML2_TemplateableElement(createLinkAction);
				if (result == null) result = caseUML2_Element(createLinkAction);
				if (result == null) result = caseEModelElement(createLinkAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.WRITE_LINK_ACTION:
			{
				WriteLinkAction writeLinkAction = (WriteLinkAction)theEObject;
				Object result = caseWriteLinkAction(writeLinkAction);
				if (result == null) result = caseUML2_LinkAction(writeLinkAction);
				if (result == null) result = caseUML2_Action(writeLinkAction);
				if (result == null) result = caseUML2_ExecutableNode(writeLinkAction);
				if (result == null) result = caseUML2_ActivityNode(writeLinkAction);
				if (result == null) result = caseUML2_RedefinableElement(writeLinkAction);
				if (result == null) result = caseUML2_NamedElement(writeLinkAction);
				if (result == null) result = caseUML2_TemplateableElement(writeLinkAction);
				if (result == null) result = caseUML2_Element(writeLinkAction);
				if (result == null) result = caseEModelElement(writeLinkAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DESTROY_LINK_ACTION:
			{
				DestroyLinkAction destroyLinkAction = (DestroyLinkAction)theEObject;
				Object result = caseDestroyLinkAction(destroyLinkAction);
				if (result == null) result = caseUML2_WriteLinkAction(destroyLinkAction);
				if (result == null) result = caseUML2_LinkAction(destroyLinkAction);
				if (result == null) result = caseUML2_Action(destroyLinkAction);
				if (result == null) result = caseUML2_ExecutableNode(destroyLinkAction);
				if (result == null) result = caseUML2_ActivityNode(destroyLinkAction);
				if (result == null) result = caseUML2_RedefinableElement(destroyLinkAction);
				if (result == null) result = caseUML2_NamedElement(destroyLinkAction);
				if (result == null) result = caseUML2_TemplateableElement(destroyLinkAction);
				if (result == null) result = caseUML2_Element(destroyLinkAction);
				if (result == null) result = caseEModelElement(destroyLinkAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CLEAR_ASSOCIATION_ACTION:
			{
				ClearAssociationAction clearAssociationAction = (ClearAssociationAction)theEObject;
				Object result = caseClearAssociationAction(clearAssociationAction);
				if (result == null) result = caseUML2_Action(clearAssociationAction);
				if (result == null) result = caseUML2_ExecutableNode(clearAssociationAction);
				if (result == null) result = caseUML2_ActivityNode(clearAssociationAction);
				if (result == null) result = caseUML2_RedefinableElement(clearAssociationAction);
				if (result == null) result = caseUML2_NamedElement(clearAssociationAction);
				if (result == null) result = caseUML2_TemplateableElement(clearAssociationAction);
				if (result == null) result = caseUML2_Element(clearAssociationAction);
				if (result == null) result = caseEModelElement(clearAssociationAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.VARIABLE_ACTION:
			{
				VariableAction variableAction = (VariableAction)theEObject;
				Object result = caseVariableAction(variableAction);
				if (result == null) result = caseUML2_Action(variableAction);
				if (result == null) result = caseUML2_ExecutableNode(variableAction);
				if (result == null) result = caseUML2_ActivityNode(variableAction);
				if (result == null) result = caseUML2_RedefinableElement(variableAction);
				if (result == null) result = caseUML2_NamedElement(variableAction);
				if (result == null) result = caseUML2_TemplateableElement(variableAction);
				if (result == null) result = caseUML2_Element(variableAction);
				if (result == null) result = caseEModelElement(variableAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.READ_VARIABLE_ACTION:
			{
				ReadVariableAction readVariableAction = (ReadVariableAction)theEObject;
				Object result = caseReadVariableAction(readVariableAction);
				if (result == null) result = caseUML2_VariableAction(readVariableAction);
				if (result == null) result = caseUML2_Action(readVariableAction);
				if (result == null) result = caseUML2_ExecutableNode(readVariableAction);
				if (result == null) result = caseUML2_ActivityNode(readVariableAction);
				if (result == null) result = caseUML2_RedefinableElement(readVariableAction);
				if (result == null) result = caseUML2_NamedElement(readVariableAction);
				if (result == null) result = caseUML2_TemplateableElement(readVariableAction);
				if (result == null) result = caseUML2_Element(readVariableAction);
				if (result == null) result = caseEModelElement(readVariableAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.WRITE_VARIABLE_ACTION:
			{
				WriteVariableAction writeVariableAction = (WriteVariableAction)theEObject;
				Object result = caseWriteVariableAction(writeVariableAction);
				if (result == null) result = caseUML2_VariableAction(writeVariableAction);
				if (result == null) result = caseUML2_Action(writeVariableAction);
				if (result == null) result = caseUML2_ExecutableNode(writeVariableAction);
				if (result == null) result = caseUML2_ActivityNode(writeVariableAction);
				if (result == null) result = caseUML2_RedefinableElement(writeVariableAction);
				if (result == null) result = caseUML2_NamedElement(writeVariableAction);
				if (result == null) result = caseUML2_TemplateableElement(writeVariableAction);
				if (result == null) result = caseUML2_Element(writeVariableAction);
				if (result == null) result = caseEModelElement(writeVariableAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CLEAR_VARIABLE_ACTION:
			{
				ClearVariableAction clearVariableAction = (ClearVariableAction)theEObject;
				Object result = caseClearVariableAction(clearVariableAction);
				if (result == null) result = caseUML2_VariableAction(clearVariableAction);
				if (result == null) result = caseUML2_Action(clearVariableAction);
				if (result == null) result = caseUML2_ExecutableNode(clearVariableAction);
				if (result == null) result = caseUML2_ActivityNode(clearVariableAction);
				if (result == null) result = caseUML2_RedefinableElement(clearVariableAction);
				if (result == null) result = caseUML2_NamedElement(clearVariableAction);
				if (result == null) result = caseUML2_TemplateableElement(clearVariableAction);
				if (result == null) result = caseUML2_Element(clearVariableAction);
				if (result == null) result = caseEModelElement(clearVariableAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ADD_VARIABLE_VALUE_ACTION:
			{
				AddVariableValueAction addVariableValueAction = (AddVariableValueAction)theEObject;
				Object result = caseAddVariableValueAction(addVariableValueAction);
				if (result == null) result = caseUML2_WriteVariableAction(addVariableValueAction);
				if (result == null) result = caseUML2_VariableAction(addVariableValueAction);
				if (result == null) result = caseUML2_Action(addVariableValueAction);
				if (result == null) result = caseUML2_ExecutableNode(addVariableValueAction);
				if (result == null) result = caseUML2_ActivityNode(addVariableValueAction);
				if (result == null) result = caseUML2_RedefinableElement(addVariableValueAction);
				if (result == null) result = caseUML2_NamedElement(addVariableValueAction);
				if (result == null) result = caseUML2_TemplateableElement(addVariableValueAction);
				if (result == null) result = caseUML2_Element(addVariableValueAction);
				if (result == null) result = caseEModelElement(addVariableValueAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.REMOVE_VARIABLE_VALUE_ACTION:
			{
				RemoveVariableValueAction removeVariableValueAction = (RemoveVariableValueAction)theEObject;
				Object result = caseRemoveVariableValueAction(removeVariableValueAction);
				if (result == null) result = caseUML2_WriteVariableAction(removeVariableValueAction);
				if (result == null) result = caseUML2_VariableAction(removeVariableValueAction);
				if (result == null) result = caseUML2_Action(removeVariableValueAction);
				if (result == null) result = caseUML2_ExecutableNode(removeVariableValueAction);
				if (result == null) result = caseUML2_ActivityNode(removeVariableValueAction);
				if (result == null) result = caseUML2_RedefinableElement(removeVariableValueAction);
				if (result == null) result = caseUML2_NamedElement(removeVariableValueAction);
				if (result == null) result = caseUML2_TemplateableElement(removeVariableValueAction);
				if (result == null) result = caseUML2_Element(removeVariableValueAction);
				if (result == null) result = caseEModelElement(removeVariableValueAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.APPLY_FUNCTION_ACTION:
			{
				ApplyFunctionAction applyFunctionAction = (ApplyFunctionAction)theEObject;
				Object result = caseApplyFunctionAction(applyFunctionAction);
				if (result == null) result = caseUML2_Action(applyFunctionAction);
				if (result == null) result = caseUML2_ExecutableNode(applyFunctionAction);
				if (result == null) result = caseUML2_ActivityNode(applyFunctionAction);
				if (result == null) result = caseUML2_RedefinableElement(applyFunctionAction);
				if (result == null) result = caseUML2_NamedElement(applyFunctionAction);
				if (result == null) result = caseUML2_TemplateableElement(applyFunctionAction);
				if (result == null) result = caseUML2_Element(applyFunctionAction);
				if (result == null) result = caseEModelElement(applyFunctionAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PRIMITIVE_FUNCTION:
			{
				PrimitiveFunction primitiveFunction = (PrimitiveFunction)theEObject;
				Object result = casePrimitiveFunction(primitiveFunction);
				if (result == null) result = caseUML2_PackageableElement(primitiveFunction);
				if (result == null) result = caseUML2_NamedElement(primitiveFunction);
				if (result == null) result = caseUML2_ParameterableElement(primitiveFunction);
				if (result == null) result = caseUML2_TemplateableElement(primitiveFunction);
				if (result == null) result = caseUML2_Element(primitiveFunction);
				if (result == null) result = caseEModelElement(primitiveFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CALL_ACTION:
			{
				CallAction callAction = (CallAction)theEObject;
				Object result = caseCallAction(callAction);
				if (result == null) result = caseUML2_InvocationAction(callAction);
				if (result == null) result = caseUML2_Action(callAction);
				if (result == null) result = caseUML2_ExecutableNode(callAction);
				if (result == null) result = caseUML2_ActivityNode(callAction);
				if (result == null) result = caseUML2_RedefinableElement(callAction);
				if (result == null) result = caseUML2_NamedElement(callAction);
				if (result == null) result = caseUML2_TemplateableElement(callAction);
				if (result == null) result = caseUML2_Element(callAction);
				if (result == null) result = caseEModelElement(callAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INVOCATION_ACTION:
			{
				InvocationAction invocationAction = (InvocationAction)theEObject;
				Object result = caseInvocationAction(invocationAction);
				if (result == null) result = caseUML2_Action(invocationAction);
				if (result == null) result = caseUML2_ExecutableNode(invocationAction);
				if (result == null) result = caseUML2_ActivityNode(invocationAction);
				if (result == null) result = caseUML2_RedefinableElement(invocationAction);
				if (result == null) result = caseUML2_NamedElement(invocationAction);
				if (result == null) result = caseUML2_TemplateableElement(invocationAction);
				if (result == null) result = caseUML2_Element(invocationAction);
				if (result == null) result = caseEModelElement(invocationAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.SEND_SIGNAL_ACTION:
			{
				SendSignalAction sendSignalAction = (SendSignalAction)theEObject;
				Object result = caseSendSignalAction(sendSignalAction);
				if (result == null) result = caseUML2_InvocationAction(sendSignalAction);
				if (result == null) result = caseUML2_Action(sendSignalAction);
				if (result == null) result = caseUML2_ExecutableNode(sendSignalAction);
				if (result == null) result = caseUML2_ActivityNode(sendSignalAction);
				if (result == null) result = caseUML2_RedefinableElement(sendSignalAction);
				if (result == null) result = caseUML2_NamedElement(sendSignalAction);
				if (result == null) result = caseUML2_TemplateableElement(sendSignalAction);
				if (result == null) result = caseUML2_Element(sendSignalAction);
				if (result == null) result = caseEModelElement(sendSignalAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.BROADCAST_SIGNAL_ACTION:
			{
				BroadcastSignalAction broadcastSignalAction = (BroadcastSignalAction)theEObject;
				Object result = caseBroadcastSignalAction(broadcastSignalAction);
				if (result == null) result = caseUML2_InvocationAction(broadcastSignalAction);
				if (result == null) result = caseUML2_Action(broadcastSignalAction);
				if (result == null) result = caseUML2_ExecutableNode(broadcastSignalAction);
				if (result == null) result = caseUML2_ActivityNode(broadcastSignalAction);
				if (result == null) result = caseUML2_RedefinableElement(broadcastSignalAction);
				if (result == null) result = caseUML2_NamedElement(broadcastSignalAction);
				if (result == null) result = caseUML2_TemplateableElement(broadcastSignalAction);
				if (result == null) result = caseUML2_Element(broadcastSignalAction);
				if (result == null) result = caseEModelElement(broadcastSignalAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.SEND_OBJECT_ACTION:
			{
				SendObjectAction sendObjectAction = (SendObjectAction)theEObject;
				Object result = caseSendObjectAction(sendObjectAction);
				if (result == null) result = caseUML2_InvocationAction(sendObjectAction);
				if (result == null) result = caseUML2_Action(sendObjectAction);
				if (result == null) result = caseUML2_ExecutableNode(sendObjectAction);
				if (result == null) result = caseUML2_ActivityNode(sendObjectAction);
				if (result == null) result = caseUML2_RedefinableElement(sendObjectAction);
				if (result == null) result = caseUML2_NamedElement(sendObjectAction);
				if (result == null) result = caseUML2_TemplateableElement(sendObjectAction);
				if (result == null) result = caseUML2_Element(sendObjectAction);
				if (result == null) result = caseEModelElement(sendObjectAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CALL_OPERATION_ACTION:
			{
				CallOperationAction callOperationAction = (CallOperationAction)theEObject;
				Object result = caseCallOperationAction(callOperationAction);
				if (result == null) result = caseUML2_CallAction(callOperationAction);
				if (result == null) result = caseUML2_InvocationAction(callOperationAction);
				if (result == null) result = caseUML2_Action(callOperationAction);
				if (result == null) result = caseUML2_ExecutableNode(callOperationAction);
				if (result == null) result = caseUML2_ActivityNode(callOperationAction);
				if (result == null) result = caseUML2_RedefinableElement(callOperationAction);
				if (result == null) result = caseUML2_NamedElement(callOperationAction);
				if (result == null) result = caseUML2_TemplateableElement(callOperationAction);
				if (result == null) result = caseUML2_Element(callOperationAction);
				if (result == null) result = caseEModelElement(callOperationAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CALL_BEHAVIOR_ACTION:
			{
				CallBehaviorAction callBehaviorAction = (CallBehaviorAction)theEObject;
				Object result = caseCallBehaviorAction(callBehaviorAction);
				if (result == null) result = caseUML2_CallAction(callBehaviorAction);
				if (result == null) result = caseUML2_InvocationAction(callBehaviorAction);
				if (result == null) result = caseUML2_Action(callBehaviorAction);
				if (result == null) result = caseUML2_ExecutableNode(callBehaviorAction);
				if (result == null) result = caseUML2_ActivityNode(callBehaviorAction);
				if (result == null) result = caseUML2_RedefinableElement(callBehaviorAction);
				if (result == null) result = caseUML2_NamedElement(callBehaviorAction);
				if (result == null) result = caseUML2_TemplateableElement(callBehaviorAction);
				if (result == null) result = caseUML2_Element(callBehaviorAction);
				if (result == null) result = caseEModelElement(callBehaviorAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TIME_EXPRESSION:
			{
				TimeExpression timeExpression = (TimeExpression)theEObject;
				Object result = caseTimeExpression(timeExpression);
				if (result == null) result = caseUML2_ValueSpecification(timeExpression);
				if (result == null) result = caseUML2_TypedElement(timeExpression);
				if (result == null) result = caseUML2_ParameterableElement(timeExpression);
				if (result == null) result = caseUML2_NamedElement(timeExpression);
				if (result == null) result = caseUML2_Element(timeExpression);
				if (result == null) result = caseUML2_TemplateableElement(timeExpression);
				if (result == null) result = caseEModelElement(timeExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DURATION:
			{
				Duration duration = (Duration)theEObject;
				Object result = caseDuration(duration);
				if (result == null) result = caseUML2_ValueSpecification(duration);
				if (result == null) result = caseUML2_TypedElement(duration);
				if (result == null) result = caseUML2_ParameterableElement(duration);
				if (result == null) result = caseUML2_NamedElement(duration);
				if (result == null) result = caseUML2_Element(duration);
				if (result == null) result = caseUML2_TemplateableElement(duration);
				if (result == null) result = caseEModelElement(duration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TIME_OBSERVATION_ACTION:
			{
				TimeObservationAction timeObservationAction = (TimeObservationAction)theEObject;
				Object result = caseTimeObservationAction(timeObservationAction);
				if (result == null) result = caseUML2_WriteStructuralFeatureAction(timeObservationAction);
				if (result == null) result = caseUML2_StructuralFeatureAction(timeObservationAction);
				if (result == null) result = caseUML2_Action(timeObservationAction);
				if (result == null) result = caseUML2_ExecutableNode(timeObservationAction);
				if (result == null) result = caseUML2_ActivityNode(timeObservationAction);
				if (result == null) result = caseUML2_RedefinableElement(timeObservationAction);
				if (result == null) result = caseUML2_NamedElement(timeObservationAction);
				if (result == null) result = caseUML2_TemplateableElement(timeObservationAction);
				if (result == null) result = caseUML2_Element(timeObservationAction);
				if (result == null) result = caseEModelElement(timeObservationAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DURATION_INTERVAL:
			{
				DurationInterval durationInterval = (DurationInterval)theEObject;
				Object result = caseDurationInterval(durationInterval);
				if (result == null) result = caseUML2_Interval(durationInterval);
				if (result == null) result = caseUML2_ValueSpecification(durationInterval);
				if (result == null) result = caseUML2_TypedElement(durationInterval);
				if (result == null) result = caseUML2_ParameterableElement(durationInterval);
				if (result == null) result = caseUML2_NamedElement(durationInterval);
				if (result == null) result = caseUML2_Element(durationInterval);
				if (result == null) result = caseUML2_TemplateableElement(durationInterval);
				if (result == null) result = caseEModelElement(durationInterval);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INTERVAL:
			{
				Interval interval = (Interval)theEObject;
				Object result = caseInterval(interval);
				if (result == null) result = caseUML2_ValueSpecification(interval);
				if (result == null) result = caseUML2_TypedElement(interval);
				if (result == null) result = caseUML2_ParameterableElement(interval);
				if (result == null) result = caseUML2_NamedElement(interval);
				if (result == null) result = caseUML2_Element(interval);
				if (result == null) result = caseUML2_TemplateableElement(interval);
				if (result == null) result = caseEModelElement(interval);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TIME_CONSTRAINT:
			{
				TimeConstraint timeConstraint = (TimeConstraint)theEObject;
				Object result = caseTimeConstraint(timeConstraint);
				if (result == null) result = caseUML2_IntervalConstraint(timeConstraint);
				if (result == null) result = caseUML2_Constraint(timeConstraint);
				if (result == null) result = caseUML2_PackageableElement(timeConstraint);
				if (result == null) result = caseUML2_NamedElement(timeConstraint);
				if (result == null) result = caseUML2_ParameterableElement(timeConstraint);
				if (result == null) result = caseUML2_TemplateableElement(timeConstraint);
				if (result == null) result = caseUML2_Element(timeConstraint);
				if (result == null) result = caseEModelElement(timeConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INTERVAL_CONSTRAINT:
			{
				IntervalConstraint intervalConstraint = (IntervalConstraint)theEObject;
				Object result = caseIntervalConstraint(intervalConstraint);
				if (result == null) result = caseUML2_Constraint(intervalConstraint);
				if (result == null) result = caseUML2_PackageableElement(intervalConstraint);
				if (result == null) result = caseUML2_NamedElement(intervalConstraint);
				if (result == null) result = caseUML2_ParameterableElement(intervalConstraint);
				if (result == null) result = caseUML2_TemplateableElement(intervalConstraint);
				if (result == null) result = caseUML2_Element(intervalConstraint);
				if (result == null) result = caseEModelElement(intervalConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.TIME_INTERVAL:
			{
				TimeInterval timeInterval = (TimeInterval)theEObject;
				Object result = caseTimeInterval(timeInterval);
				if (result == null) result = caseUML2_Interval(timeInterval);
				if (result == null) result = caseUML2_ValueSpecification(timeInterval);
				if (result == null) result = caseUML2_TypedElement(timeInterval);
				if (result == null) result = caseUML2_ParameterableElement(timeInterval);
				if (result == null) result = caseUML2_NamedElement(timeInterval);
				if (result == null) result = caseUML2_Element(timeInterval);
				if (result == null) result = caseUML2_TemplateableElement(timeInterval);
				if (result == null) result = caseEModelElement(timeInterval);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DURATION_OBSERVATION_ACTION:
			{
				DurationObservationAction durationObservationAction = (DurationObservationAction)theEObject;
				Object result = caseDurationObservationAction(durationObservationAction);
				if (result == null) result = caseUML2_WriteStructuralFeatureAction(durationObservationAction);
				if (result == null) result = caseUML2_StructuralFeatureAction(durationObservationAction);
				if (result == null) result = caseUML2_Action(durationObservationAction);
				if (result == null) result = caseUML2_ExecutableNode(durationObservationAction);
				if (result == null) result = caseUML2_ActivityNode(durationObservationAction);
				if (result == null) result = caseUML2_RedefinableElement(durationObservationAction);
				if (result == null) result = caseUML2_NamedElement(durationObservationAction);
				if (result == null) result = caseUML2_TemplateableElement(durationObservationAction);
				if (result == null) result = caseUML2_Element(durationObservationAction);
				if (result == null) result = caseEModelElement(durationObservationAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DURATION_CONSTRAINT:
			{
				DurationConstraint durationConstraint = (DurationConstraint)theEObject;
				Object result = caseDurationConstraint(durationConstraint);
				if (result == null) result = caseUML2_IntervalConstraint(durationConstraint);
				if (result == null) result = caseUML2_Constraint(durationConstraint);
				if (result == null) result = caseUML2_PackageableElement(durationConstraint);
				if (result == null) result = caseUML2_NamedElement(durationConstraint);
				if (result == null) result = caseUML2_ParameterableElement(durationConstraint);
				if (result == null) result = caseUML2_TemplateableElement(durationConstraint);
				if (result == null) result = caseUML2_Element(durationConstraint);
				if (result == null) result = caseEModelElement(durationConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DATA_STORE_NODE:
			{
				DataStoreNode dataStoreNode = (DataStoreNode)theEObject;
				Object result = caseDataStoreNode(dataStoreNode);
				if (result == null) result = caseUML2_CentralBufferNode(dataStoreNode);
				if (result == null) result = caseUML2_ObjectNode(dataStoreNode);
				if (result == null) result = caseUML2_ActivityNode(dataStoreNode);
				if (result == null) result = caseUML2_TypedElement(dataStoreNode);
				if (result == null) result = caseUML2_RedefinableElement(dataStoreNode);
				if (result == null) result = caseUML2_NamedElement(dataStoreNode);
				if (result == null) result = caseUML2_TemplateableElement(dataStoreNode);
				if (result == null) result = caseUML2_Element(dataStoreNode);
				if (result == null) result = caseEModelElement(dataStoreNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.INTERRUPTIBLE_ACTIVITY_REGION:
			{
				InterruptibleActivityRegion interruptibleActivityRegion = (InterruptibleActivityRegion)theEObject;
				Object result = caseInterruptibleActivityRegion(interruptibleActivityRegion);
				if (result == null) result = caseUML2_ActivityGroup(interruptibleActivityRegion);
				if (result == null) result = caseUML2_Element(interruptibleActivityRegion);
				if (result == null) result = caseEModelElement(interruptibleActivityRegion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PARAMETER_SET:
			{
				ParameterSet parameterSet = (ParameterSet)theEObject;
				Object result = caseParameterSet(parameterSet);
				if (result == null) result = caseUML2_NamedElement(parameterSet);
				if (result == null) result = caseUML2_TemplateableElement(parameterSet);
				if (result == null) result = caseUML2_Element(parameterSet);
				if (result == null) result = caseEModelElement(parameterSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.COMPONENT:
			{
				Component component = (Component)theEObject;
				Object result = caseComponent(component);
				if (result == null) result = caseUML2_Class(component);
				if (result == null) result = caseUML2_BehavioredClassifier(component);
				if (result == null) result = caseUML2_EncapsulatedClassifier(component);
				if (result == null) result = caseUML2_Classifier(component);
				if (result == null) result = caseUML2_StructuredClassifier(component);
				if (result == null) result = caseUML2_Namespace(component);
				if (result == null) result = caseUML2_Type(component);
				if (result == null) result = caseUML2_RedefinableElement(component);
				if (result == null) result = caseUML2_NamedElement(component);
				if (result == null) result = caseUML2_PackageableElement(component);
				if (result == null) result = caseUML2_TemplateableElement(component);
				if (result == null) result = caseUML2_ParameterableElement(component);
				if (result == null) result = caseUML2_Element(component);
				if (result == null) result = caseEModelElement(component);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DEPLOYMENT:
			{
				Deployment deployment = (Deployment)theEObject;
				Object result = caseDeployment(deployment);
				if (result == null) result = caseUML2_Dependency(deployment);
				if (result == null) result = caseUML2_PackageableElement(deployment);
				if (result == null) result = caseUML2_DirectedRelationship(deployment);
				if (result == null) result = caseUML2_NamedElement(deployment);
				if (result == null) result = caseUML2_ParameterableElement(deployment);
				if (result == null) result = caseUML2_Relationship(deployment);
				if (result == null) result = caseUML2_TemplateableElement(deployment);
				if (result == null) result = caseUML2_Element(deployment);
				if (result == null) result = caseEModelElement(deployment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DEPLOYED_ARTIFACT:
			{
				DeployedArtifact deployedArtifact = (DeployedArtifact)theEObject;
				Object result = caseDeployedArtifact(deployedArtifact);
				if (result == null) result = caseUML2_NamedElement(deployedArtifact);
				if (result == null) result = caseUML2_TemplateableElement(deployedArtifact);
				if (result == null) result = caseUML2_Element(deployedArtifact);
				if (result == null) result = caseEModelElement(deployedArtifact);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DEPLOYMENT_TARGET:
			{
				DeploymentTarget deploymentTarget = (DeploymentTarget)theEObject;
				Object result = caseDeploymentTarget(deploymentTarget);
				if (result == null) result = caseUML2_NamedElement(deploymentTarget);
				if (result == null) result = caseUML2_TemplateableElement(deploymentTarget);
				if (result == null) result = caseUML2_Element(deploymentTarget);
				if (result == null) result = caseEModelElement(deploymentTarget);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.NODE:
			{
				Node node = (Node)theEObject;
				Object result = caseNode(node);
				if (result == null) result = caseUML2_Class(node);
				if (result == null) result = caseUML2_DeploymentTarget(node);
				if (result == null) result = caseUML2_BehavioredClassifier(node);
				if (result == null) result = caseUML2_EncapsulatedClassifier(node);
				if (result == null) result = caseUML2_NamedElement(node);
				if (result == null) result = caseUML2_Classifier(node);
				if (result == null) result = caseUML2_StructuredClassifier(node);
				if (result == null) result = caseUML2_TemplateableElement(node);
				if (result == null) result = caseUML2_Namespace(node);
				if (result == null) result = caseUML2_Type(node);
				if (result == null) result = caseUML2_RedefinableElement(node);
				if (result == null) result = caseUML2_Element(node);
				if (result == null) result = caseUML2_PackageableElement(node);
				if (result == null) result = caseEModelElement(node);
				if (result == null) result = caseUML2_ParameterableElement(node);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DEVICE:
			{
				Device device = (Device)theEObject;
				Object result = caseDevice(device);
				if (result == null) result = caseUML2_Node(device);
				if (result == null) result = caseUML2_Class(device);
				if (result == null) result = caseUML2_DeploymentTarget(device);
				if (result == null) result = caseUML2_BehavioredClassifier(device);
				if (result == null) result = caseUML2_EncapsulatedClassifier(device);
				if (result == null) result = caseUML2_NamedElement(device);
				if (result == null) result = caseUML2_Classifier(device);
				if (result == null) result = caseUML2_StructuredClassifier(device);
				if (result == null) result = caseUML2_TemplateableElement(device);
				if (result == null) result = caseUML2_Namespace(device);
				if (result == null) result = caseUML2_Type(device);
				if (result == null) result = caseUML2_RedefinableElement(device);
				if (result == null) result = caseUML2_Element(device);
				if (result == null) result = caseUML2_PackageableElement(device);
				if (result == null) result = caseEModelElement(device);
				if (result == null) result = caseUML2_ParameterableElement(device);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.EXECUTION_ENVIRONMENT:
			{
				ExecutionEnvironment executionEnvironment = (ExecutionEnvironment)theEObject;
				Object result = caseExecutionEnvironment(executionEnvironment);
				if (result == null) result = caseUML2_Node(executionEnvironment);
				if (result == null) result = caseUML2_Class(executionEnvironment);
				if (result == null) result = caseUML2_DeploymentTarget(executionEnvironment);
				if (result == null) result = caseUML2_BehavioredClassifier(executionEnvironment);
				if (result == null) result = caseUML2_EncapsulatedClassifier(executionEnvironment);
				if (result == null) result = caseUML2_NamedElement(executionEnvironment);
				if (result == null) result = caseUML2_Classifier(executionEnvironment);
				if (result == null) result = caseUML2_StructuredClassifier(executionEnvironment);
				if (result == null) result = caseUML2_TemplateableElement(executionEnvironment);
				if (result == null) result = caseUML2_Namespace(executionEnvironment);
				if (result == null) result = caseUML2_Type(executionEnvironment);
				if (result == null) result = caseUML2_RedefinableElement(executionEnvironment);
				if (result == null) result = caseUML2_Element(executionEnvironment);
				if (result == null) result = caseUML2_PackageableElement(executionEnvironment);
				if (result == null) result = caseEModelElement(executionEnvironment);
				if (result == null) result = caseUML2_ParameterableElement(executionEnvironment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.COMMUNICATION_PATH:
			{
				CommunicationPath communicationPath = (CommunicationPath)theEObject;
				Object result = caseCommunicationPath(communicationPath);
				if (result == null) result = caseUML2_Association(communicationPath);
				if (result == null) result = caseUML2_Classifier(communicationPath);
				if (result == null) result = caseUML2_Relationship(communicationPath);
				if (result == null) result = caseUML2_Namespace(communicationPath);
				if (result == null) result = caseUML2_Type(communicationPath);
				if (result == null) result = caseUML2_RedefinableElement(communicationPath);
				if (result == null) result = caseUML2_Element(communicationPath);
				if (result == null) result = caseUML2_NamedElement(communicationPath);
				if (result == null) result = caseUML2_PackageableElement(communicationPath);
				if (result == null) result = caseEModelElement(communicationPath);
				if (result == null) result = caseUML2_TemplateableElement(communicationPath);
				if (result == null) result = caseUML2_ParameterableElement(communicationPath);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PROTOCOL_CONFORMANCE:
			{
				ProtocolConformance protocolConformance = (ProtocolConformance)theEObject;
				Object result = caseProtocolConformance(protocolConformance);
				if (result == null) result = caseUML2_DirectedRelationship(protocolConformance);
				if (result == null) result = caseUML2_Relationship(protocolConformance);
				if (result == null) result = caseUML2_Element(protocolConformance);
				if (result == null) result = caseEModelElement(protocolConformance);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PROTOCOL_STATE_MACHINE:
			{
				ProtocolStateMachine protocolStateMachine = (ProtocolStateMachine)theEObject;
				Object result = caseProtocolStateMachine(protocolStateMachine);
				if (result == null) result = caseUML2_StateMachine(protocolStateMachine);
				if (result == null) result = caseUML2_Behavior(protocolStateMachine);
				if (result == null) result = caseUML2_Class(protocolStateMachine);
				if (result == null) result = caseUML2_BehavioredClassifier(protocolStateMachine);
				if (result == null) result = caseUML2_EncapsulatedClassifier(protocolStateMachine);
				if (result == null) result = caseUML2_Classifier(protocolStateMachine);
				if (result == null) result = caseUML2_StructuredClassifier(protocolStateMachine);
				if (result == null) result = caseUML2_Namespace(protocolStateMachine);
				if (result == null) result = caseUML2_Type(protocolStateMachine);
				if (result == null) result = caseUML2_RedefinableElement(protocolStateMachine);
				if (result == null) result = caseUML2_NamedElement(protocolStateMachine);
				if (result == null) result = caseUML2_PackageableElement(protocolStateMachine);
				if (result == null) result = caseUML2_TemplateableElement(protocolStateMachine);
				if (result == null) result = caseUML2_ParameterableElement(protocolStateMachine);
				if (result == null) result = caseUML2_Element(protocolStateMachine);
				if (result == null) result = caseEModelElement(protocolStateMachine);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PROTOCOL_TRANSITION:
			{
				ProtocolTransition protocolTransition = (ProtocolTransition)theEObject;
				Object result = caseProtocolTransition(protocolTransition);
				if (result == null) result = caseUML2_Transition(protocolTransition);
				if (result == null) result = caseUML2_RedefinableElement(protocolTransition);
				if (result == null) result = caseUML2_NamedElement(protocolTransition);
				if (result == null) result = caseUML2_TemplateableElement(protocolTransition);
				if (result == null) result = caseUML2_Element(protocolTransition);
				if (result == null) result = caseEModelElement(protocolTransition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.READ_EXTENT_ACTION:
			{
				ReadExtentAction readExtentAction = (ReadExtentAction)theEObject;
				Object result = caseReadExtentAction(readExtentAction);
				if (result == null) result = caseUML2_Action(readExtentAction);
				if (result == null) result = caseUML2_ExecutableNode(readExtentAction);
				if (result == null) result = caseUML2_ActivityNode(readExtentAction);
				if (result == null) result = caseUML2_RedefinableElement(readExtentAction);
				if (result == null) result = caseUML2_NamedElement(readExtentAction);
				if (result == null) result = caseUML2_TemplateableElement(readExtentAction);
				if (result == null) result = caseUML2_Element(readExtentAction);
				if (result == null) result = caseEModelElement(readExtentAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.RECLASSIFY_OBJECT_ACTION:
			{
				ReclassifyObjectAction reclassifyObjectAction = (ReclassifyObjectAction)theEObject;
				Object result = caseReclassifyObjectAction(reclassifyObjectAction);
				if (result == null) result = caseUML2_Action(reclassifyObjectAction);
				if (result == null) result = caseUML2_ExecutableNode(reclassifyObjectAction);
				if (result == null) result = caseUML2_ActivityNode(reclassifyObjectAction);
				if (result == null) result = caseUML2_RedefinableElement(reclassifyObjectAction);
				if (result == null) result = caseUML2_NamedElement(reclassifyObjectAction);
				if (result == null) result = caseUML2_TemplateableElement(reclassifyObjectAction);
				if (result == null) result = caseUML2_Element(reclassifyObjectAction);
				if (result == null) result = caseEModelElement(reclassifyObjectAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.READ_IS_CLASSIFIED_OBJECT_ACTION:
			{
				ReadIsClassifiedObjectAction readIsClassifiedObjectAction = (ReadIsClassifiedObjectAction)theEObject;
				Object result = caseReadIsClassifiedObjectAction(readIsClassifiedObjectAction);
				if (result == null) result = caseUML2_Action(readIsClassifiedObjectAction);
				if (result == null) result = caseUML2_ExecutableNode(readIsClassifiedObjectAction);
				if (result == null) result = caseUML2_ActivityNode(readIsClassifiedObjectAction);
				if (result == null) result = caseUML2_RedefinableElement(readIsClassifiedObjectAction);
				if (result == null) result = caseUML2_NamedElement(readIsClassifiedObjectAction);
				if (result == null) result = caseUML2_TemplateableElement(readIsClassifiedObjectAction);
				if (result == null) result = caseUML2_Element(readIsClassifiedObjectAction);
				if (result == null) result = caseEModelElement(readIsClassifiedObjectAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.START_OWNED_BEHAVIOR_ACTION:
			{
				StartOwnedBehaviorAction startOwnedBehaviorAction = (StartOwnedBehaviorAction)theEObject;
				Object result = caseStartOwnedBehaviorAction(startOwnedBehaviorAction);
				if (result == null) result = caseUML2_Action(startOwnedBehaviorAction);
				if (result == null) result = caseUML2_ExecutableNode(startOwnedBehaviorAction);
				if (result == null) result = caseUML2_ActivityNode(startOwnedBehaviorAction);
				if (result == null) result = caseUML2_RedefinableElement(startOwnedBehaviorAction);
				if (result == null) result = caseUML2_NamedElement(startOwnedBehaviorAction);
				if (result == null) result = caseUML2_TemplateableElement(startOwnedBehaviorAction);
				if (result == null) result = caseUML2_Element(startOwnedBehaviorAction);
				if (result == null) result = caseEModelElement(startOwnedBehaviorAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.QUALIFIER_VALUE:
			{
				QualifierValue qualifierValue = (QualifierValue)theEObject;
				Object result = caseQualifierValue(qualifierValue);
				if (result == null) result = caseUML2_Element(qualifierValue);
				if (result == null) result = caseEModelElement(qualifierValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.READ_LINK_OBJECT_END_ACTION:
			{
				ReadLinkObjectEndAction readLinkObjectEndAction = (ReadLinkObjectEndAction)theEObject;
				Object result = caseReadLinkObjectEndAction(readLinkObjectEndAction);
				if (result == null) result = caseUML2_Action(readLinkObjectEndAction);
				if (result == null) result = caseUML2_ExecutableNode(readLinkObjectEndAction);
				if (result == null) result = caseUML2_ActivityNode(readLinkObjectEndAction);
				if (result == null) result = caseUML2_RedefinableElement(readLinkObjectEndAction);
				if (result == null) result = caseUML2_NamedElement(readLinkObjectEndAction);
				if (result == null) result = caseUML2_TemplateableElement(readLinkObjectEndAction);
				if (result == null) result = caseUML2_Element(readLinkObjectEndAction);
				if (result == null) result = caseEModelElement(readLinkObjectEndAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.READ_LINK_OBJECT_END_QUALIFIER_ACTION:
			{
				ReadLinkObjectEndQualifierAction readLinkObjectEndQualifierAction = (ReadLinkObjectEndQualifierAction)theEObject;
				Object result = caseReadLinkObjectEndQualifierAction(readLinkObjectEndQualifierAction);
				if (result == null) result = caseUML2_Action(readLinkObjectEndQualifierAction);
				if (result == null) result = caseUML2_ExecutableNode(readLinkObjectEndQualifierAction);
				if (result == null) result = caseUML2_ActivityNode(readLinkObjectEndQualifierAction);
				if (result == null) result = caseUML2_RedefinableElement(readLinkObjectEndQualifierAction);
				if (result == null) result = caseUML2_NamedElement(readLinkObjectEndQualifierAction);
				if (result == null) result = caseUML2_TemplateableElement(readLinkObjectEndQualifierAction);
				if (result == null) result = caseUML2_Element(readLinkObjectEndQualifierAction);
				if (result == null) result = caseEModelElement(readLinkObjectEndQualifierAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.CREATE_LINK_OBJECT_ACTION:
			{
				CreateLinkObjectAction createLinkObjectAction = (CreateLinkObjectAction)theEObject;
				Object result = caseCreateLinkObjectAction(createLinkObjectAction);
				if (result == null) result = caseUML2_CreateLinkAction(createLinkObjectAction);
				if (result == null) result = caseUML2_WriteLinkAction(createLinkObjectAction);
				if (result == null) result = caseUML2_LinkAction(createLinkObjectAction);
				if (result == null) result = caseUML2_Action(createLinkObjectAction);
				if (result == null) result = caseUML2_ExecutableNode(createLinkObjectAction);
				if (result == null) result = caseUML2_ActivityNode(createLinkObjectAction);
				if (result == null) result = caseUML2_RedefinableElement(createLinkObjectAction);
				if (result == null) result = caseUML2_NamedElement(createLinkObjectAction);
				if (result == null) result = caseUML2_TemplateableElement(createLinkObjectAction);
				if (result == null) result = caseUML2_Element(createLinkObjectAction);
				if (result == null) result = caseEModelElement(createLinkObjectAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ACCEPT_EVENT_ACTION:
			{
				AcceptEventAction acceptEventAction = (AcceptEventAction)theEObject;
				Object result = caseAcceptEventAction(acceptEventAction);
				if (result == null) result = caseUML2_Action(acceptEventAction);
				if (result == null) result = caseUML2_ExecutableNode(acceptEventAction);
				if (result == null) result = caseUML2_ActivityNode(acceptEventAction);
				if (result == null) result = caseUML2_RedefinableElement(acceptEventAction);
				if (result == null) result = caseUML2_NamedElement(acceptEventAction);
				if (result == null) result = caseUML2_TemplateableElement(acceptEventAction);
				if (result == null) result = caseUML2_Element(acceptEventAction);
				if (result == null) result = caseEModelElement(acceptEventAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.ACCEPT_CALL_ACTION:
			{
				AcceptCallAction acceptCallAction = (AcceptCallAction)theEObject;
				Object result = caseAcceptCallAction(acceptCallAction);
				if (result == null) result = caseUML2_AcceptEventAction(acceptCallAction);
				if (result == null) result = caseUML2_Action(acceptCallAction);
				if (result == null) result = caseUML2_ExecutableNode(acceptCallAction);
				if (result == null) result = caseUML2_ActivityNode(acceptCallAction);
				if (result == null) result = caseUML2_RedefinableElement(acceptCallAction);
				if (result == null) result = caseUML2_NamedElement(acceptCallAction);
				if (result == null) result = caseUML2_TemplateableElement(acceptCallAction);
				if (result == null) result = caseUML2_Element(acceptCallAction);
				if (result == null) result = caseEModelElement(acceptCallAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.REPLY_ACTION:
			{
				ReplyAction replyAction = (ReplyAction)theEObject;
				Object result = caseReplyAction(replyAction);
				if (result == null) result = caseUML2_Action(replyAction);
				if (result == null) result = caseUML2_ExecutableNode(replyAction);
				if (result == null) result = caseUML2_ActivityNode(replyAction);
				if (result == null) result = caseUML2_RedefinableElement(replyAction);
				if (result == null) result = caseUML2_NamedElement(replyAction);
				if (result == null) result = caseUML2_TemplateableElement(replyAction);
				if (result == null) result = caseUML2_Element(replyAction);
				if (result == null) result = caseEModelElement(replyAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.RAISE_EXCEPTION_ACTION:
			{
				RaiseExceptionAction raiseExceptionAction = (RaiseExceptionAction)theEObject;
				Object result = caseRaiseExceptionAction(raiseExceptionAction);
				if (result == null) result = caseUML2_Action(raiseExceptionAction);
				if (result == null) result = caseUML2_ExecutableNode(raiseExceptionAction);
				if (result == null) result = caseUML2_ActivityNode(raiseExceptionAction);
				if (result == null) result = caseUML2_RedefinableElement(raiseExceptionAction);
				if (result == null) result = caseUML2_NamedElement(raiseExceptionAction);
				if (result == null) result = caseUML2_TemplateableElement(raiseExceptionAction);
				if (result == null) result = caseUML2_Element(raiseExceptionAction);
				if (result == null) result = caseEModelElement(raiseExceptionAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DEPLOYMENT_SPECIFICATION:
			{
				DeploymentSpecification deploymentSpecification = (DeploymentSpecification)theEObject;
				Object result = caseDeploymentSpecification(deploymentSpecification);
				if (result == null) result = caseUML2_Artifact(deploymentSpecification);
				if (result == null) result = caseUML2_Classifier(deploymentSpecification);
				if (result == null) result = caseUML2_DeployedArtifact(deploymentSpecification);
				if (result == null) result = caseUML2_Namespace(deploymentSpecification);
				if (result == null) result = caseUML2_Type(deploymentSpecification);
				if (result == null) result = caseUML2_RedefinableElement(deploymentSpecification);
				if (result == null) result = caseUML2_NamedElement(deploymentSpecification);
				if (result == null) result = caseUML2_PackageableElement(deploymentSpecification);
				if (result == null) result = caseUML2_TemplateableElement(deploymentSpecification);
				if (result == null) result = caseUML2_ParameterableElement(deploymentSpecification);
				if (result == null) result = caseUML2_Element(deploymentSpecification);
				if (result == null) result = caseEModelElement(deploymentSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.JFIGURE_CONTAINER:
			{
				J_FigureContainer j_FigureContainer = (J_FigureContainer)theEObject;
				Object result = caseJ_FigureContainer(j_FigureContainer);
				if (result == null) result = caseUML2_Element(j_FigureContainer);
				if (result == null) result = caseEModelElement(j_FigureContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.JFIGURE:
			{
				J_Figure j_Figure = (J_Figure)theEObject;
				Object result = caseJ_Figure(j_Figure);
				if (result == null) result = caseUML2_J_FigureContainer(j_Figure);
				if (result == null) result = caseUML2_Element(j_Figure);
				if (result == null) result = caseEModelElement(j_Figure);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.JPROPERTY:
			{
				J_Property j_Property = (J_Property)theEObject;
				Object result = caseJ_Property(j_Property);
				if (result == null) result = caseUML2_Element(j_Property);
				if (result == null) result = caseEModelElement(j_Property);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.JDIAGRAM:
			{
				J_Diagram j_Diagram = (J_Diagram)theEObject;
				Object result = caseJ_Diagram(j_Diagram);
				if (result == null) result = caseUML2_J_FigureContainer(j_Diagram);
				if (result == null) result = caseUML2_Element(j_Diagram);
				if (result == null) result = caseEModelElement(j_Diagram);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.JDIAGRAM_HOLDER:
			{
				J_DiagramHolder j_DiagramHolder = (J_DiagramHolder)theEObject;
				Object result = caseJ_DiagramHolder(j_DiagramHolder);
				if (result == null) result = caseUML2_Element(j_DiagramHolder);
				if (result == null) result = caseEModelElement(j_DiagramHolder);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.APPLIED_BASIC_STEREOTYPE_VALUE:
			{
				AppliedBasicStereotypeValue appliedBasicStereotypeValue = (AppliedBasicStereotypeValue)theEObject;
				Object result = caseAppliedBasicStereotypeValue(appliedBasicStereotypeValue);
				if (result == null) result = caseUML2_Element(appliedBasicStereotypeValue);
				if (result == null) result = caseEModelElement(appliedBasicStereotypeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PROPERTY_VALUE_SPECIFICATION:
			{
				PropertyValueSpecification propertyValueSpecification = (PropertyValueSpecification)theEObject;
				Object result = casePropertyValueSpecification(propertyValueSpecification);
				if (result == null) result = caseUML2_ValueSpecification(propertyValueSpecification);
				if (result == null) result = caseUML2_TypedElement(propertyValueSpecification);
				if (result == null) result = caseUML2_ParameterableElement(propertyValueSpecification);
				if (result == null) result = caseUML2_NamedElement(propertyValueSpecification);
				if (result == null) result = caseUML2_Element(propertyValueSpecification);
				if (result == null) result = caseUML2_TemplateableElement(propertyValueSpecification);
				if (result == null) result = caseEModelElement(propertyValueSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_REPLACED_CONSTITUENT:
			{
				DeltaReplacedConstituent deltaReplacedConstituent = (DeltaReplacedConstituent)theEObject;
				Object result = caseDeltaReplacedConstituent(deltaReplacedConstituent);
				if (result == null) result = caseUML2_Element(deltaReplacedConstituent);
				if (result == null) result = caseEModelElement(deltaReplacedConstituent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_DELETED_CONSTITUENT:
			{
				DeltaDeletedConstituent deltaDeletedConstituent = (DeltaDeletedConstituent)theEObject;
				Object result = caseDeltaDeletedConstituent(deltaDeletedConstituent);
				if (result == null) result = caseUML2_Element(deltaDeletedConstituent);
				if (result == null) result = caseEModelElement(deltaDeletedConstituent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_REPLACED_ATTRIBUTE:
			{
				DeltaReplacedAttribute deltaReplacedAttribute = (DeltaReplacedAttribute)theEObject;
				Object result = caseDeltaReplacedAttribute(deltaReplacedAttribute);
				if (result == null) result = caseUML2_DeltaReplacedConstituent(deltaReplacedAttribute);
				if (result == null) result = caseUML2_Element(deltaReplacedAttribute);
				if (result == null) result = caseEModelElement(deltaReplacedAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_DELETED_ATTRIBUTE:
			{
				DeltaDeletedAttribute deltaDeletedAttribute = (DeltaDeletedAttribute)theEObject;
				Object result = caseDeltaDeletedAttribute(deltaDeletedAttribute);
				if (result == null) result = caseUML2_DeltaDeletedConstituent(deltaDeletedAttribute);
				if (result == null) result = caseUML2_Element(deltaDeletedAttribute);
				if (result == null) result = caseEModelElement(deltaDeletedAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_REPLACED_PORT:
			{
				DeltaReplacedPort deltaReplacedPort = (DeltaReplacedPort)theEObject;
				Object result = caseDeltaReplacedPort(deltaReplacedPort);
				if (result == null) result = caseUML2_DeltaReplacedConstituent(deltaReplacedPort);
				if (result == null) result = caseUML2_Element(deltaReplacedPort);
				if (result == null) result = caseEModelElement(deltaReplacedPort);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_DELETED_PORT:
			{
				DeltaDeletedPort deltaDeletedPort = (DeltaDeletedPort)theEObject;
				Object result = caseDeltaDeletedPort(deltaDeletedPort);
				if (result == null) result = caseUML2_DeltaDeletedConstituent(deltaDeletedPort);
				if (result == null) result = caseUML2_Element(deltaDeletedPort);
				if (result == null) result = caseEModelElement(deltaDeletedPort);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_REPLACED_CONNECTOR:
			{
				DeltaReplacedConnector deltaReplacedConnector = (DeltaReplacedConnector)theEObject;
				Object result = caseDeltaReplacedConnector(deltaReplacedConnector);
				if (result == null) result = caseUML2_DeltaReplacedConstituent(deltaReplacedConnector);
				if (result == null) result = caseUML2_Element(deltaReplacedConnector);
				if (result == null) result = caseEModelElement(deltaReplacedConnector);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_DELETED_CONNECTOR:
			{
				DeltaDeletedConnector deltaDeletedConnector = (DeltaDeletedConnector)theEObject;
				Object result = caseDeltaDeletedConnector(deltaDeletedConnector);
				if (result == null) result = caseUML2_DeltaDeletedConstituent(deltaDeletedConnector);
				if (result == null) result = caseUML2_Element(deltaDeletedConnector);
				if (result == null) result = caseEModelElement(deltaDeletedConnector);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_REPLACED_OPERATION:
			{
				DeltaReplacedOperation deltaReplacedOperation = (DeltaReplacedOperation)theEObject;
				Object result = caseDeltaReplacedOperation(deltaReplacedOperation);
				if (result == null) result = caseUML2_DeltaReplacedConstituent(deltaReplacedOperation);
				if (result == null) result = caseUML2_Element(deltaReplacedOperation);
				if (result == null) result = caseEModelElement(deltaReplacedOperation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_DELETED_OPERATION:
			{
				DeltaDeletedOperation deltaDeletedOperation = (DeltaDeletedOperation)theEObject;
				Object result = caseDeltaDeletedOperation(deltaDeletedOperation);
				if (result == null) result = caseUML2_DeltaDeletedConstituent(deltaDeletedOperation);
				if (result == null) result = caseUML2_Element(deltaDeletedOperation);
				if (result == null) result = caseEModelElement(deltaDeletedOperation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.PORT_REMAP:
			{
				PortRemap portRemap = (PortRemap)theEObject;
				Object result = casePortRemap(portRemap);
				if (result == null) result = caseUML2_Element(portRemap);
				if (result == null) result = caseEModelElement(portRemap);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.SAVED_REFERENCE:
			{
				SavedReference savedReference = (SavedReference)theEObject;
				Object result = caseSavedReference(savedReference);
				if (result == null) result = caseUML2_PackageableElement(savedReference);
				if (result == null) result = caseUML2_NamedElement(savedReference);
				if (result == null) result = caseUML2_ParameterableElement(savedReference);
				if (result == null) result = caseUML2_TemplateableElement(savedReference);
				if (result == null) result = caseUML2_Element(savedReference);
				if (result == null) result = caseEModelElement(savedReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.REQUIREMENTS_FEATURE: {
				RequirementsFeature requirementsFeature = (RequirementsFeature)theEObject;
				Object result = caseRequirementsFeature(requirementsFeature);
				if (result == null) result = caseUML2_Type(requirementsFeature);
				if (result == null) result = caseUML2_PackageableElement(requirementsFeature);
				if (result == null) result = caseUML2_NamedElement(requirementsFeature);
				if (result == null) result = caseUML2_ParameterableElement(requirementsFeature);
				if (result == null) result = caseUML2_TemplateableElement(requirementsFeature);
				if (result == null) result = caseUML2_Element(requirementsFeature);
				if (result == null) result = caseEModelElement(requirementsFeature);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.REQUIREMENTS_FEATURE_LINK: {
				RequirementsFeatureLink requirementsFeatureLink = (RequirementsFeatureLink)theEObject;
				Object result = caseRequirementsFeatureLink(requirementsFeatureLink);
				if (result == null) result = caseUML2_Element(requirementsFeatureLink);
				if (result == null) result = caseEModelElement(requirementsFeatureLink);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_REPLACED_REQUIREMENTS_FEATURE_LINK: {
				DeltaReplacedRequirementsFeatureLink deltaReplacedRequirementsFeatureLink = (DeltaReplacedRequirementsFeatureLink)theEObject;
				Object result = caseDeltaReplacedRequirementsFeatureLink(deltaReplacedRequirementsFeatureLink);
				if (result == null) result = caseUML2_DeltaReplacedConstituent(deltaReplacedRequirementsFeatureLink);
				if (result == null) result = caseUML2_Element(deltaReplacedRequirementsFeatureLink);
				if (result == null) result = caseEModelElement(deltaReplacedRequirementsFeatureLink);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_DELETED_REQUIREMENTS_FEATURE_LINK: {
				DeltaDeletedRequirementsFeatureLink deltaDeletedRequirementsFeatureLink = (DeltaDeletedRequirementsFeatureLink)theEObject;
				Object result = caseDeltaDeletedRequirementsFeatureLink(deltaDeletedRequirementsFeatureLink);
				if (result == null) result = caseUML2_DeltaDeletedConstituent(deltaDeletedRequirementsFeatureLink);
				if (result == null) result = caseUML2_Element(deltaDeletedRequirementsFeatureLink);
				if (result == null) result = caseEModelElement(deltaDeletedRequirementsFeatureLink);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_DELETED_TRACE: {
				DeltaDeletedTrace deltaDeletedTrace = (DeltaDeletedTrace)theEObject;
				Object result = caseDeltaDeletedTrace(deltaDeletedTrace);
				if (result == null) result = caseUML2_DeltaDeletedConstituent(deltaDeletedTrace);
				if (result == null) result = caseUML2_Element(deltaDeletedTrace);
				if (result == null) result = caseEModelElement(deltaDeletedTrace);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case UML2Package.DELTA_REPLACED_TRACE: {
				DeltaReplacedTrace deltaReplacedTrace = (DeltaReplacedTrace)theEObject;
				Object result = caseDeltaReplacedTrace(deltaReplacedTrace);
				if (result == null) result = caseUML2_DeltaReplacedConstituent(deltaReplacedTrace);
				if (result == null) result = caseUML2_Element(deltaReplacedTrace);
				if (result == null) result = caseEModelElement(deltaReplacedTrace);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseElement(Element object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Multiplicity Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Multiplicity Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMultiplicityElement(MultiplicityElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Directed Relationship</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Directed Relationship</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDirectedRelationship(DirectedRelationship object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Relationship</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Relationship</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRelationship(Relationship object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNamedElement(NamedElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Namespace</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Namespace</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNamespace(Namespace object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Opaque Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Opaque Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOpaqueExpression(OpaqueExpression object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Value Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Value Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseValueSpecification(ValueSpecification object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExpression(Expression object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Comment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseComment(Comment object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseClass(org.eclipse.uml2.Class object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseType(Type object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProperty(Property object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Operation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOperation(Operation object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Typed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Typed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTypedElement(TypedElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseParameter(Parameter object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Package</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Package</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePackage(org.eclipse.uml2.Package object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Enumeration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Enumeration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEnumeration(Enumeration object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Data Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDataType(DataType object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Enumeration Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Enumeration Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEnumerationLiteral(EnumerationLiteral object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Primitive Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Primitive Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePrimitiveType(PrimitiveType object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Literal Boolean</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Literal Boolean</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLiteralBoolean(LiteralBoolean object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Literal Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Literal Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLiteralSpecification(LiteralSpecification object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Literal String</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Literal String</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLiteralString(LiteralString object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Literal Null</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Literal Null</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLiteralNull(LiteralNull object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Literal Integer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Literal Integer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLiteralInteger(LiteralInteger object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Literal Unlimited Natural</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Literal Unlimited Natural</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLiteralUnlimitedNatural(LiteralUnlimitedNatural object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConstraint(Constraint object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseClassifier(Classifier object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Feature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Feature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseFeature(Feature object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Behavioral Feature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Behavioral Feature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseBehavioralFeature(BehavioralFeature object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Structural Feature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Structural Feature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseStructuralFeature(StructuralFeature object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Redefinable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Redefinable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRedefinableElement(RedefinableElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Instance Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Instance Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInstanceSpecification(InstanceSpecification object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Slot</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Slot</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSlot(Slot object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Instance Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Instance Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInstanceValue(InstanceValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Generalization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Generalization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseGeneralization(Generalization object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Packageable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Packageable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePackageableElement(PackageableElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Element Import</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Element Import</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseElementImport(ElementImport object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Package Import</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Package Import</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePackageImport(PackageImport object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Association</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Association</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAssociation(Association object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Package Merge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Package Merge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePackageMerge(PackageMerge object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Stereotype</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Stereotype</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseStereotype(Stereotype object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Profile</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Profile</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProfile(Profile object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Profile Application</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Profile Application</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProfileApplication(ProfileApplication object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Extension</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Extension</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExtension(Extension object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Extension End</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Extension End</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExtensionEnd(ExtensionEnd object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseModel(Model object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Information Item</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Information Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInformationItem(InformationItem object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Information Flow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Information Flow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInformationFlow(InformationFlow object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Association Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Association Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAssociationClass(AssociationClass object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Permission</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Permission</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePermission(Permission object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDependency(Dependency object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Usage</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Usage</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUsage(Usage object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Abstraction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Abstraction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAbstraction(Abstraction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Realization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Realization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRealization(Realization object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Substitution</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Substitution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSubstitution(Substitution object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Behavior</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseBehavior(Behavior object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Behaviored Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Behaviored Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseBehavioredClassifier(BehavioredClassifier object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActivity(Activity object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Generalization Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Generalization Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseGeneralizationSet(GeneralizationSet object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Artifact</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Artifact</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseArtifact(Artifact object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Manifestation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Manifestation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseManifestation(Manifestation object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activity Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activity Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActivityEdge(ActivityEdge object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activity Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activity Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActivityGroup(ActivityGroup object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activity Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activity Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActivityNode(ActivityNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAction(Action object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Object Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Object Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseObjectNode(ObjectNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Control Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Control Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseControlNode(ControlNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Control Flow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Control Flow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseControlFlow(ControlFlow object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Object Flow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Object Flow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseObjectFlow(ObjectFlow object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Initial Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Initial Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInitialNode(InitialNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Final Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Final Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseFinalNode(FinalNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activity Final Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activity Final Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActivityFinalNode(ActivityFinalNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Decision Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Decision Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDecisionNode(DecisionNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Merge Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Merge Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMergeNode(MergeNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Executable Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Executable Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExecutableNode(ExecutableNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Output Pin</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Output Pin</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOutputPin(OutputPin object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Input Pin</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Input Pin</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInputPin(InputPin object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Pin</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Pin</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePin(Pin object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activity Parameter Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activity Parameter Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActivityParameterNode(ActivityParameterNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Value Pin</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Value Pin</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseValuePin(ValuePin object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interface</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interface</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInterface(Interface object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Implementation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Implementation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseImplementation(Implementation object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Actor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Actor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActor(Actor object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Extend</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Extend</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExtend(Extend object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Use Case</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Use Case</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUseCase(UseCase object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Extension Point</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Extension Point</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExtensionPoint(ExtensionPoint object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Include</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Include</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInclude(Include object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Call Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Call Trigger</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCallTrigger(CallTrigger object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Message Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Message Trigger</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMessageTrigger(MessageTrigger object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Change Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Change Trigger</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseChangeTrigger(ChangeTrigger object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Trigger</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTrigger(Trigger object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Reception</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Reception</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReception(Reception object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Signal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Signal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSignal(Signal object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Signal Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Signal Trigger</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSignalTrigger(SignalTrigger object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Time Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Time Trigger</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTimeTrigger(TimeTrigger object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Any Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Any Trigger</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAnyTrigger(AnyTrigger object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Connector End</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Connector End</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConnectorEnd(ConnectorEnd object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Connectable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Connectable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConnectableElement(ConnectableElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Connector</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Connector</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConnector(Connector object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Structured Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Structured Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseStructuredClassifier(StructuredClassifier object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseVariable(Variable object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Structured Activity Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Structured Activity Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseStructuredActivityNode(StructuredActivityNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Conditional Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Conditional Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConditionalNode(ConditionalNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Clause</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Clause</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseClause(Clause object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Loop Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Loop Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLoopNode(LoopNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>State Machine</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseStateMachine(StateMachine object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Region</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Region</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRegion(Region object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Pseudostate</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePseudostate(Pseudostate object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseState(State object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Vertex</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Vertex</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseVertex(Vertex object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Connection Point Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Connection Point Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConnectionPointReference(ConnectionPointReference object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTransition(Transition object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Final State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Final State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseFinalState(FinalState object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Expansion Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Expansion Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExpansionNode(ExpansionNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Expansion Region</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Expansion Region</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExpansionRegion(ExpansionRegion object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Exception Handler</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Exception Handler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExceptionHandler(ExceptionHandler object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Port</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Port</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePort(Port object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Encapsulated Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Encapsulated Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEncapsulatedClassifier(EncapsulatedClassifier object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Create Object Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Create Object Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCreateObjectAction(CreateObjectAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Destroy Object Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Destroy Object Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDestroyObjectAction(DestroyObjectAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Test Identity Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Test Identity Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTestIdentityAction(TestIdentityAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Read Self Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Read Self Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReadSelfAction(ReadSelfAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Structural Feature Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Structural Feature Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseStructuralFeatureAction(StructuralFeatureAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Read Structural Feature Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Read Structural Feature Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReadStructuralFeatureAction(ReadStructuralFeatureAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Write Structural Feature Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Write Structural Feature Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseWriteStructuralFeatureAction(WriteStructuralFeatureAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Clear Structural Feature Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Clear Structural Feature Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseClearStructuralFeatureAction(ClearStructuralFeatureAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Remove Structural Feature Value Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Remove Structural Feature Value Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRemoveStructuralFeatureValueAction(RemoveStructuralFeatureValueAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Add Structural Feature Value Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Add Structural Feature Value Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAddStructuralFeatureValueAction(AddStructuralFeatureValueAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Link Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Link Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLinkAction(LinkAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Link End Data</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Link End Data</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLinkEndData(LinkEndData object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Read Link Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Read Link Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReadLinkAction(ReadLinkAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Link End Creation Data</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Link End Creation Data</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLinkEndCreationData(LinkEndCreationData object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Create Link Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Create Link Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCreateLinkAction(CreateLinkAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Write Link Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Write Link Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseWriteLinkAction(WriteLinkAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Destroy Link Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Destroy Link Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDestroyLinkAction(DestroyLinkAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Clear Association Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Clear Association Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseClearAssociationAction(ClearAssociationAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Variable Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Variable Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseVariableAction(VariableAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Read Variable Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Read Variable Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReadVariableAction(ReadVariableAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Write Variable Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Write Variable Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseWriteVariableAction(WriteVariableAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Clear Variable Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Clear Variable Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseClearVariableAction(ClearVariableAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Add Variable Value Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Add Variable Value Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAddVariableValueAction(AddVariableValueAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Remove Variable Value Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Remove Variable Value Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRemoveVariableValueAction(RemoveVariableValueAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Apply Function Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Apply Function Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseApplyFunctionAction(ApplyFunctionAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Primitive Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Primitive Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePrimitiveFunction(PrimitiveFunction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Call Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Call Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCallAction(CallAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Invocation Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Invocation Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInvocationAction(InvocationAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Send Signal Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Send Signal Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSendSignalAction(SendSignalAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Broadcast Signal Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Broadcast Signal Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseBroadcastSignalAction(BroadcastSignalAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Send Object Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Send Object Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSendObjectAction(SendObjectAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Call Operation Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Call Operation Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCallOperationAction(CallOperationAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Call Behavior Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Call Behavior Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCallBehaviorAction(CallBehaviorAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Fork Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Fork Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseForkNode(ForkNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Join Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Join Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseJoinNode(JoinNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Flow Final Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Flow Final Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseFlowFinalNode(FlowFinalNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Central Buffer Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Central Buffer Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCentralBufferNode(CentralBufferNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activity Partition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activity Partition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActivityPartition(ActivityPartition object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Template Signature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Template Signature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTemplateSignature(TemplateSignature object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Template Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Template Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTemplateParameter(TemplateParameter object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Templateable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Templateable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTemplateableElement(TemplateableElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>String Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>String Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseStringExpression(StringExpression object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Parameterable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Parameterable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseParameterableElement(ParameterableElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Template Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Template Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTemplateBinding(TemplateBinding object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Template Parameter Substitution</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Template Parameter Substitution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTemplateParameterSubstitution(TemplateParameterSubstitution object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Collaboration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Collaboration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCollaboration(Collaboration object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Operation Template Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Operation Template Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOperationTemplateParameter(OperationTemplateParameter object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Classifier Template Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Classifier Template Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseClassifierTemplateParameter(ClassifierTemplateParameter object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Parameterable Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Parameterable Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseParameterableClassifier(ParameterableClassifier object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Redefinable Template Signature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Redefinable Template Signature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRedefinableTemplateSignature(RedefinableTemplateSignature object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Templateable Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Templateable Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTemplateableClassifier(TemplateableClassifier object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Connectable Element Template Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Connectable Element Template Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConnectableElementTemplateParameter(ConnectableElementTemplateParameter object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interaction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interaction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInteraction(Interaction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interaction Fragment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interaction Fragment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInteractionFragment(InteractionFragment object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Lifeline</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Lifeline</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLifeline(Lifeline object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Message</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Message</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMessage(Message object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>General Ordering</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>General Ordering</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseGeneralOrdering(GeneralOrdering object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Message End</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Message End</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseMessageEnd(MessageEnd object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Event Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Event Occurrence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEventOccurrence(EventOccurrence object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Execution Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Execution Occurrence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExecutionOccurrence(ExecutionOccurrence object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>State Invariant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>State Invariant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseStateInvariant(StateInvariant object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Stop</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Stop</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseStop(Stop object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Collaboration Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Collaboration Occurrence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCollaborationOccurrence(CollaborationOccurrence object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Data Store Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Data Store Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDataStoreNode(DataStoreNode object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interruptible Activity Region</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interruptible Activity Region</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInterruptibleActivityRegion(InterruptibleActivityRegion object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Parameter Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Parameter Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseParameterSet(ParameterSet object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Read Extent Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Read Extent Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReadExtentAction(ReadExtentAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Reclassify Object Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Reclassify Object Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReclassifyObjectAction(ReclassifyObjectAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Read Is Classified Object Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Read Is Classified Object Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReadIsClassifiedObjectAction(ReadIsClassifiedObjectAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Start Owned Behavior Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Start Owned Behavior Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseStartOwnedBehaviorAction(StartOwnedBehaviorAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Qualifier Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Qualifier Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseQualifierValue(QualifierValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Read Link Object End Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Read Link Object End Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReadLinkObjectEndAction(ReadLinkObjectEndAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Read Link Object End Qualifier Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Read Link Object End Qualifier Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReadLinkObjectEndQualifierAction(ReadLinkObjectEndQualifierAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Create Link Object Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Create Link Object Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCreateLinkObjectAction(CreateLinkObjectAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Accept Event Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Accept Event Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAcceptEventAction(AcceptEventAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Accept Call Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Accept Call Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAcceptCallAction(AcceptCallAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Reply Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Reply Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReplyAction(ReplyAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Raise Exception Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Raise Exception Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRaiseExceptionAction(RaiseExceptionAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Time Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Time Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTimeExpression(TimeExpression object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Duration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Duration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDuration(Duration object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Time Observation Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Time Observation Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTimeObservationAction(TimeObservationAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Duration Interval</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Duration Interval</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDurationInterval(DurationInterval object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interval</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interval</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInterval(Interval object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Time Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Time Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTimeConstraint(TimeConstraint object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interval Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interval Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIntervalConstraint(IntervalConstraint object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Time Interval</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Time Interval</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTimeInterval(TimeInterval object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Duration Observation Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Duration Observation Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDurationObservationAction(DurationObservationAction object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Duration Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Duration Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDurationConstraint(DurationConstraint object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Protocol Conformance</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Protocol Conformance</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProtocolConformance(ProtocolConformance object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Protocol State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Protocol State Machine</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProtocolStateMachine(ProtocolStateMachine object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Protocol Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Protocol Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProtocolTransition(ProtocolTransition object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interaction Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interaction Occurrence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInteractionOccurrence(InteractionOccurrence object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Gate</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Gate</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseGate(Gate object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Part Decomposition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Part Decomposition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePartDecomposition(PartDecomposition object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interaction Operand</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interaction Operand</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInteractionOperand(InteractionOperand object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interaction Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interaction Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInteractionConstraint(InteractionConstraint object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Combined Fragment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Combined Fragment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCombinedFragment(CombinedFragment object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Continuation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Continuation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseContinuation(Continuation object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Component</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseComponent(Component object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Deployment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Deployment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeployment(Deployment object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Deployed Artifact</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Deployed Artifact</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeployedArtifact(DeployedArtifact object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Deployment Target</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Deployment Target</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeploymentTarget(DeploymentTarget object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseNode(Node object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Device</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Device</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDevice(Device object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Execution Environment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Execution Environment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExecutionEnvironment(ExecutionEnvironment object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Communication Path</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Communication Path</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCommunicationPath(CommunicationPath object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Deployment Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Deployment Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeploymentSpecification(DeploymentSpecification object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>JFigure Container</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>JFigure Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object caseJ_FigureContainer(J_FigureContainer object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>JFigure</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>JFigure</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseJ_Figure(J_Figure object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>JProperty</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>JProperty</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseJ_Property(J_Property object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>JDiagram</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>JDiagram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseJ_Diagram(J_Diagram object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>JDiagram Holder</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>JDiagram Holder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseJ_DiagramHolder(J_DiagramHolder object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Applied Basic Stereotype Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Applied Basic Stereotype Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAppliedBasicStereotypeValue(AppliedBasicStereotypeValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Property Value Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Property Value Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePropertyValueSpecification(PropertyValueSpecification object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Replaced Constituent</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Replaced Constituent</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaReplacedConstituent(DeltaReplacedConstituent object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Deleted Constituent</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Deleted Constituent</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaDeletedConstituent(DeltaDeletedConstituent object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Replaced Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Replaced Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaReplacedAttribute(DeltaReplacedAttribute object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Deleted Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Deleted Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaDeletedAttribute(DeltaDeletedAttribute object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Replaced Port</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Replaced Port</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaReplacedPort(DeltaReplacedPort object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Deleted Port</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Deleted Port</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaDeletedPort(DeltaDeletedPort object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Replaced Connector</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Replaced Connector</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaReplacedConnector(DeltaReplacedConnector object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Deleted Connector</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Deleted Connector</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaDeletedConnector(DeltaDeletedConnector object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Replaced Operation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Replaced Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaReplacedOperation(DeltaReplacedOperation object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Deleted Operation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Deleted Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaDeletedOperation(DeltaDeletedOperation object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Port Remap</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Port Remap</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object casePortRemap(PortRemap object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Saved Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Saved Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSavedReference(SavedReference object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Requirements Feature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Requirements Feature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRequirementsFeature(RequirementsFeature object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Requirements Feature Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Requirements Feature Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRequirementsFeatureLink(RequirementsFeatureLink object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Replaced Requirements Feature Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Replaced Requirements Feature Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaReplacedRequirementsFeatureLink(DeltaReplacedRequirementsFeatureLink object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Deleted Requirements Feature Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Deleted Requirements Feature Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaDeletedRequirementsFeatureLink(DeltaDeletedRequirementsFeatureLink object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Deleted Trace</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Deleted Trace</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaDeletedTrace(DeltaDeletedTrace object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Replaced Trace</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Replaced Trace</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseDeltaReplacedTrace(DeltaReplacedTrace object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EModel Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EModel Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEModelElement(EModelElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Element(Element object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Templateable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Templateable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_TemplateableElement(TemplateableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_NamedElement(NamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Typed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Typed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_TypedElement(TypedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Parameterable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Parameterable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_ParameterableElement(ParameterableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Value Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Value Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_ValueSpecification(ValueSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Opaque Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Opaque Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_OpaqueExpression(OpaqueExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Relationship</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Relationship</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Relationship(Relationship object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Namespace</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Namespace</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Namespace(Namespace object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Packageable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Packageable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_PackageableElement(PackageableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Type(Type object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Redefinable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Redefinable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_RedefinableElement(RedefinableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Classifier(Classifier object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Behaviored Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Behaviored Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_BehavioredClassifier(BehavioredClassifier object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Structured Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Structured Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_StructuredClassifier(StructuredClassifier object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Encapsulated Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Encapsulated Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_EncapsulatedClassifier(EncapsulatedClassifier object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Feature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Feature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Feature(Feature object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Multiplicity Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Multiplicity Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_MultiplicityElement(MultiplicityElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Structural Feature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Structural Feature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_StructuralFeature(StructuralFeature object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Connectable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Connectable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_ConnectableElement(ConnectableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Deployment Target</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Deployment Target</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_DeploymentTarget(DeploymentTarget object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Behavioral Feature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Behavioral Feature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_BehavioralFeature(BehavioralFeature object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Data Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_DataType(DataType object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Deployed Artifact</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Deployed Artifact</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_DeployedArtifact(DeployedArtifact object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Instance Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Instance Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_InstanceSpecification(InstanceSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Literal Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Literal Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_LiteralSpecification(LiteralSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Directed Relationship</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Directed Relationship</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_DirectedRelationship(DirectedRelationship object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Class(org.eclipse.uml2.Class object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Package</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Package</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Package(org.eclipse.uml2.Package object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Package Import</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Package Import</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_PackageImport(PackageImport object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Association</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Association</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Association(Association object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Property(Property object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Behavior</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Behavior</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Behavior(Behavior object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Dependency(Dependency object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Abstraction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Abstraction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Abstraction(Abstraction object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Realization</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Realization</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Realization(Realization object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activity Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activity Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_ActivityNode(ActivityNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Executable Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Executable Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_ExecutableNode(ExecutableNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activity Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activity Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_ActivityEdge(ActivityEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Control Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Control Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_ControlNode(ControlNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Final Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Final Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_FinalNode(FinalNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Object Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Object Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_ObjectNode(ObjectNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Pin</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Pin</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Pin(Pin object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Input Pin</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Input Pin</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_InputPin(InputPin object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Trigger</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Trigger(Trigger object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Message Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Message Trigger</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_MessageTrigger(MessageTrigger object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Action(Action object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activity Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activity Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_ActivityGroup(ActivityGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Structured Activity Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Structured Activity Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_StructuredActivityNode(StructuredActivityNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interaction Fragment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interaction Fragment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_InteractionFragment(InteractionFragment object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Message End</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Message End</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_MessageEnd(MessageEnd object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Event Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Event Occurrence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_EventOccurrence(EventOccurrence object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Template Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Template Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_TemplateParameter(TemplateParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Template Signature</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Template Signature</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_TemplateSignature(TemplateSignature object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interaction Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interaction Occurrence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_InteractionOccurrence(InteractionOccurrence object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Constraint(Constraint object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Vertex</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Vertex</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Vertex(Vertex object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_State(State object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Structural Feature Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Structural Feature Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_StructuralFeatureAction(StructuralFeatureAction object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Write Structural Feature Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Write Structural Feature Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_WriteStructuralFeatureAction(WriteStructuralFeatureAction object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Link Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Link Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_LinkAction(LinkAction object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Link End Data</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Link End Data</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_LinkEndData(LinkEndData object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Write Link Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Write Link Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_WriteLinkAction(WriteLinkAction object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Variable Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Variable Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_VariableAction(VariableAction object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Write Variable Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Write Variable Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_WriteVariableAction(WriteVariableAction object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Invocation Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Invocation Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_InvocationAction(InvocationAction object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Call Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Call Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_CallAction(CallAction object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interval</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interval</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Interval(Interval object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Interval Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Interval Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_IntervalConstraint(IntervalConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Central Buffer Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Central Buffer Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_CentralBufferNode(CentralBufferNode object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Node(Node object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>State Machine</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_StateMachine(StateMachine object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Transition(Transition object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Create Link Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Create Link Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_CreateLinkAction(CreateLinkAction object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Accept Event Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Accept Event Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_AcceptEventAction(AcceptEventAction object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Artifact</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Artifact</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_Artifact(Artifact object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>JFigure Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>JFigure Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_J_FigureContainer(J_FigureContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Replaced Constituent</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Replaced Constituent</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_DeltaReplacedConstituent(DeltaReplacedConstituent object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Delta Deleted Constituent</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Delta Deleted Constituent</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUML2_DeltaDeletedConstituent(DeltaDeletedConstituent object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public Object defaultCase(EObject object)
	{
		return null;
	}

} //UML2Switch
