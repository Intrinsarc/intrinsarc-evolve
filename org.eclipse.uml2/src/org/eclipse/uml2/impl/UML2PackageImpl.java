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
 * $Id: UML2PackageImpl.java,v 1.3 2009-04-22 16:26:07 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;

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
import org.eclipse.uml2.AggregationKind;
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
import org.eclipse.uml2.CallConcurrencyKind;
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
import org.eclipse.uml2.ComponentKind;
import org.eclipse.uml2.ConditionalNode;
import org.eclipse.uml2.ConnectableElement;
import org.eclipse.uml2.ConnectableElementTemplateParameter;
import org.eclipse.uml2.ConnectionPointReference;
import org.eclipse.uml2.Connector;
import org.eclipse.uml2.ConnectorEnd;
import org.eclipse.uml2.ConnectorKind;
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
import org.eclipse.emf.ecore.impl.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Enumeration;
import org.eclipse.uml2.EnumerationLiteral;
import org.eclipse.uml2.EventOccurrence;
import org.eclipse.uml2.ExceptionHandler;
import org.eclipse.uml2.ExecutableNode;
import org.eclipse.uml2.ExecutionEnvironment;
import org.eclipse.uml2.ExecutionOccurrence;
import org.eclipse.uml2.ExpansionKind;
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
import org.eclipse.uml2.InteractionOperator;
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
import org.eclipse.uml2.MessageKind;
import org.eclipse.uml2.MessageSort;
import org.eclipse.uml2.MessageTrigger;
import org.eclipse.uml2.Model;
import org.eclipse.uml2.MultiplicityElement;
import org.eclipse.uml2.NamedElement;
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.Node;
import org.eclipse.uml2.ObjectFlow;
import org.eclipse.uml2.ObjectNode;
import org.eclipse.uml2.ObjectNodeOrderingKind;
import org.eclipse.uml2.OpaqueExpression;
import org.eclipse.uml2.Operation;
import org.eclipse.uml2.OperationTemplateParameter;
import org.eclipse.uml2.OutputPin;
import org.eclipse.uml2.PackageImport;
import org.eclipse.uml2.PackageMerge;
import org.eclipse.uml2.PackageableElement;
import org.eclipse.uml2.Parameter;
import org.eclipse.uml2.ParameterDirectionKind;
import org.eclipse.uml2.ParameterEffectKind;
import org.eclipse.uml2.ParameterSet;
import org.eclipse.uml2.ParameterableClassifier;
import org.eclipse.uml2.ParameterableElement;
import org.eclipse.uml2.PartDecomposition;
import org.eclipse.uml2.Permission;
import org.eclipse.uml2.Pin;
import org.eclipse.uml2.Port;
import org.eclipse.uml2.PortKind;
import org.eclipse.uml2.PortRemap;
import org.eclipse.uml2.PrimitiveFunction;
import org.eclipse.uml2.PrimitiveType;
import org.eclipse.uml2.Profile;
import org.eclipse.uml2.ProfileApplication;
import org.eclipse.uml2.Property;
import org.eclipse.uml2.PropertyAccessKind;
import org.eclipse.uml2.PropertyValueSpecification;
import org.eclipse.uml2.ProtocolConformance;
import org.eclipse.uml2.ProtocolStateMachine;
import org.eclipse.uml2.ProtocolTransition;
import org.eclipse.uml2.Pseudostate;
import org.eclipse.uml2.PseudostateKind;
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
import org.eclipse.uml2.RequirementsLinkKind;
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
import org.eclipse.uml2.TransitionKind;
import org.eclipse.uml2.Trigger;
import org.eclipse.uml2.Type;
import org.eclipse.uml2.TypedElement;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.Usage;
import org.eclipse.uml2.UseCase;
import org.eclipse.uml2.ValuePin;
import org.eclipse.uml2.ValueSpecification;
import org.eclipse.uml2.Variable;
import org.eclipse.uml2.VariableAction;
import org.eclipse.uml2.Vertex;
import org.eclipse.uml2.VisibilityKind;
import org.eclipse.uml2.WriteLinkAction;
import org.eclipse.uml2.WriteStructuralFeatureAction;
import org.eclipse.uml2.WriteVariableAction;

import org.eclipse.uml2.util.UML2Validator;

import org.eclipse.uml2.util.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UML2PackageImpl extends EPackageImpl implements UML2Package {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiplicityElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass packageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumerationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumerationLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass primitiveTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass opaqueExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass directedRelationshipEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relationshipEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namespaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalBooleanEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalStringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalNullEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalIntegerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalUnlimitedNaturalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structuralFeatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass behavioralFeatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instanceSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass slotEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instanceValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass generalizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass redefinableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass packageableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementImportEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass packageImportEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass packageMergeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stereotypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass profileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass profileApplicationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extensionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extensionEndEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass informationItemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass informationFlowEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass generalizationSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass behaviorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass behavioredClassifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass permissionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass usageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass realizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass substitutionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass artifactEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass manifestationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interfaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass implementationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extendEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass useCaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extensionPointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass includeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activityEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activityGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activityNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass controlNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass controlFlowEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectFlowEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass initialNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass finalNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activityFinalNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass decisionNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mergeNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass executableNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass outputPinEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inputPinEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pinEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activityParameterNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valuePinEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structuredActivityNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionalNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass clauseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loopNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectorEndEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structuredClassifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass callTriggerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass messageTriggerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass changeTriggerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass triggerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass receptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass signalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass signalTriggerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeTriggerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyTriggerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass templateSignatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass templateParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass templateableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass templateBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass templateParameterSubstitutionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass collaborationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationTemplateParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classifierTemplateParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterableClassifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass redefinableTemplateSignatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass templateableClassifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectableElementTemplateParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass forkNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass joinNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass flowFinalNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass centralBufferNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass activityPartitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass portEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass encapsulatedClassifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expansionNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expansionRegionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exceptionHandlerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interactionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interactionFragmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lifelineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass messageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass generalOrderingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass messageEndEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventOccurrenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass executionOccurrenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateInvariantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stopEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass collaborationOccurrenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createObjectActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass destroyObjectActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testIdentityActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass readSelfActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structuralFeatureActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass readStructuralFeatureActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass writeStructuralFeatureActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass clearStructuralFeatureActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeStructuralFeatureValueActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass addStructuralFeatureValueActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkEndDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass readLinkActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkEndCreationDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createLinkActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass writeLinkActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass destroyLinkActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass clearAssociationActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass readVariableActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass writeVariableActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass clearVariableActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass addVariableValueActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeVariableValueActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applyFunctionActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass primitiveFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass callActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass invocationActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sendSignalActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass broadcastSignalActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sendObjectActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass callOperationActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass callBehaviorActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateMachineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass regionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pseudostateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vertexEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectionPointReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass finalStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass readExtentActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reclassifyObjectActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass readIsClassifiedObjectActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass startOwnedBehaviorActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass qualifierValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass readLinkObjectEndActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass readLinkObjectEndQualifierActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createLinkObjectActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass acceptEventActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass acceptCallActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass replyActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass raiseExceptionActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataStoreNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interruptibleActivityRegionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interactionOccurrenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass partDecompositionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interactionOperandEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interactionConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass combinedFragmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass continuationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass durationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeObservationActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass durationIntervalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intervalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intervalConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeIntervalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass durationObservationActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass durationConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass protocolConformanceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass protocolStateMachineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass protocolTransitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deploymentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deployedArtifactEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deploymentTargetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deviceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass executionEnvironmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass communicationPathEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deploymentSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass j_FigureContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass j_DiagramEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass j_DiagramHolderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass appliedBasicStereotypeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyValueSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaReplacedConstituentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaDeletedConstituentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaReplacedAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaDeletedAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaReplacedPortEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaDeletedPortEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaReplacedConnectorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaDeletedConnectorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaReplacedOperationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaDeletedOperationEClass = null;

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass portRemapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass savedReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requirementsFeatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requirementsFeatureLinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaReplacedRequirementsFeatureLinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaDeletedRequirementsFeatureLinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaDeletedTraceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deltaReplacedTraceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass j_FigureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass j_PropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum visibilityKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum parameterDirectionKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum aggregationKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum callConcurrencyKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum expansionKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum messageKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum messageSortEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum transitionKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum pseudostateKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum objectNodeOrderingKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum interactionOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum connectorKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum componentKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum propertyAccessKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum portKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum requirementsLinkKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum parameterEffectKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType integerEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType booleanEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType stringEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType unlimitedNaturalEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType sequenceEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType setEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.uml2.UML2Package#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UML2PackageImpl() {
		super(eNS_URI, UML2Factory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UML2Package init() {
		if (isInited) return (UML2Package)EPackage.Registry.INSTANCE.getEPackage(UML2Package.eNS_URI);

		// Obtain or create and register package
		UML2PackageImpl theUML2Package = (UML2PackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof UML2PackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new UML2PackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackageImpl.init();

		// Obtain or create and register interdependencies
		UML2PackageImpl theUML2Package_1 = (UML2PackageImpl)(EPackage.Registry.INSTANCE.getEPackage(UML2Package.eNS_URI) instanceof UML2PackageImpl ? EPackage.Registry.INSTANCE.getEPackage(UML2Package.eNS_URI) : UML2Package.eINSTANCE);

		// Create package meta-data objects
		theUML2Package.createPackageContents();
		theUML2Package_1.createPackageContents();

		// Initialize created meta-data
		theUML2Package.initializePackageContents();
		theUML2Package_1.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theUML2Package, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return UML2Validator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theUML2Package.freeze();

		return theUML2Package;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElement() {
		return elementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElement_OwnedElement() {
		return (EReference)elementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElement_Owner() {
		return (EReference)elementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElement_OwnedComment() {
		return (EReference)elementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElement_J_deleted() {
		return (EAttribute)elementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElement_Documentation() {
		return (EAttribute)elementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElement_AppliedBasicStereotypes() {
		return (EReference)elementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElement_AppliedBasicStereotypeValues() {
		return (EReference)elementEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getElement_Uuid() {
		return (EAttribute)elementEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMultiplicityElement() {
		return multiplicityElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMultiplicityElement_IsOrdered() {
		return (EAttribute)multiplicityElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMultiplicityElement_IsUnique() {
		return (EAttribute)multiplicityElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMultiplicityElement_Lower() {
		return (EAttribute)multiplicityElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMultiplicityElement_Upper() {
		return (EAttribute)multiplicityElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMultiplicityElement_UpperValue() {
		return (EReference)multiplicityElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMultiplicityElement_LowerValue() {
		return (EReference)multiplicityElementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClass_() {
		return classEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClass_IsActive() {
		return (EAttribute)classEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClass_OwnedOperation() {
		return (EReference)classEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClass_SuperClass() {
		return (EReference)classEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClass_Extension() {
		return (EReference)classEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClass_NestedClassifier() {
		return (EReference)classEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClass_OwnedReception() {
		return (EReference)classEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClass_ComponentKind() {
		return (EAttribute)classEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getType() {
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getType_Package() {
		return (EReference)typeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getType_IsRetired() {
		return (EAttribute)typeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProperty() {
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Default() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_IsComposite() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_IsDerived() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_IsDerivedUnion() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Aggregation() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_Class_() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_Opposite() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_OwningAssociation() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_RedefinedProperty() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_SubsettedProperty() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_Datatype() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_Association() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_DefaultValue() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_Qualifier() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_AssociationEnd() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_OwnedAnonymousType() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_DefaultValues() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperation() {
		return operationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperation_IsQuery() {
		return (EAttribute)operationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperation_OwnedParameter() {
		return (EReference)operationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperation_Class_() {
		return (EReference)operationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperation_Datatype() {
		return (EReference)operationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperation_Precondition() {
		return (EReference)operationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperation_Postcondition() {
		return (EReference)operationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperation_RedefinedOperation() {
		return (EReference)operationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperation_BodyCondition() {
		return (EReference)operationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTypedElement() {
		return typedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTypedElement_Type() {
		return (EReference)typedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Default() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Direction() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_IsException() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_IsStream() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Effect() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameter_Operation() {
		return (EReference)parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameter_DefaultValue() {
		return (EReference)parameterEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameter_ParameterSet() {
		return (EReference)parameterEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamedElement() {
		return namedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedElement_Name() {
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedElement_QualifiedName() {
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedElement_Visibility() {
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNamedElement_ClientDependency() {
		return (EReference)namedElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNamedElement_NameExpression() {
		return (EReference)namedElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNamedElement_OwnedAnonymousDependencies() {
		return (EReference)namedElementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNamedElement_ReverseDependencies() {
		return (EReference)namedElementEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNamedElement_ReverseGeneralizations() {
		return (EReference)namedElementEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPackage() {
		return packageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackage_NestedPackage() {
		return (EReference)packageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackage_NestingPackage() {
		return (EReference)packageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackage_OwnedType() {
		return (EReference)packageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackage_OwnedMember() {
		return (EReference)packageEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackage_PackageMerge() {
		return (EReference)packageEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackage_AppliedProfile() {
		return (EReference)packageEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackage_PackageExtension() {
		return (EReference)packageEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackage_J_diagramHolder() {
		return (EReference)packageEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getPackage_ChildPackages() {
		return (EReference)packageEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getPackage_ParentPackage() {
		return (EReference)packageEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPackage_ReadOnly() {
		return (EAttribute)packageEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackage_AnonymousDeletedImportPlaceholders() {
		return (EReference)packageEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumeration() {
		return enumerationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnumeration_OwnedLiteral() {
		return (EReference)enumerationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataType() {
		return dataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataType_OwnedAttribute() {
		return (EReference)dataTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataType_OwnedOperation() {
		return (EReference)dataTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumerationLiteral() {
		return enumerationLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnumerationLiteral_Enumeration() {
		return (EReference)enumerationLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPrimitiveType() {
		return primitiveTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOpaqueExpression() {
		return opaqueExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOpaqueExpression_Body() {
		return (EAttribute)opaqueExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOpaqueExpression_Language() {
		return (EAttribute)opaqueExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOpaqueExpression_Result() {
		return (EReference)opaqueExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOpaqueExpression_Behavior() {
		return (EReference)opaqueExpressionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValueSpecification() {
		return valueSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpression() {
		return expressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExpression_Symbol() {
		return (EAttribute)expressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExpression_Operand() {
		return (EReference)expressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComment() {
		return commentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComment_Body() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComment_AnnotatedElement() {
		return (EReference)commentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComment_BodyExpression() {
		return (EReference)commentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getComment_BinaryData() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getComment_BinaryFormat() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getComment_BinaryCount() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDirectedRelationship() {
		return directedRelationshipEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDirectedRelationship_Source() {
		return (EReference)directedRelationshipEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDirectedRelationship_Target() {
		return (EReference)directedRelationshipEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRelationship() {
		return relationshipEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRelationship_RelatedElement() {
		return (EReference)relationshipEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamespace() {
		return namespaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNamespace_Member() {
		return (EReference)namespaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNamespace_OwnedRule() {
		return (EReference)namespaceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNamespace_ImportedMember() {
		return (EReference)namespaceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNamespace_ElementImport() {
		return (EReference)namespaceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNamespace_PackageImport() {
		return (EReference)namespaceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLiteralBoolean() {
		return literalBooleanEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLiteralBoolean_Value() {
		return (EAttribute)literalBooleanEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLiteralSpecification() {
		return literalSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLiteralString() {
		return literalStringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLiteralString_Value() {
		return (EAttribute)literalStringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLiteralNull() {
		return literalNullEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLiteralInteger() {
		return literalIntegerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLiteralInteger_Value() {
		return (EAttribute)literalIntegerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLiteralUnlimitedNatural() {
		return literalUnlimitedNaturalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLiteralUnlimitedNatural_Value() {
		return (EAttribute)literalUnlimitedNaturalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstraint() {
		return constraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraint_Context() {
		return (EReference)constraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraint_Namespace() {
		return (EReference)constraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraint_Specification() {
		return (EReference)constraintEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraint_ConstrainedElement() {
		return (EReference)constraintEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassifier() {
		return classifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassifier_IsAbstract() {
		return (EAttribute)classifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifier_Feature() {
		return (EReference)classifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifier_InheritedMember() {
		return (EReference)classifierEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifier_General() {
		return (EReference)classifierEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifier_Generalization() {
		return (EReference)classifierEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifier_Attribute() {
		return (EReference)classifierEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifier_RedefinedClassifier() {
		return (EReference)classifierEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifier_PowertypeExtent() {
		return (EReference)classifierEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifier_Substitution() {
		return (EReference)classifierEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifier_OwnedUseCase() {
		return (EReference)classifierEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifier_UseCase() {
		return (EReference)classifierEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifier_Representation() {
		return (EReference)classifierEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClassifier_Occurrence() {
		return (EReference)classifierEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFeature() {
		return featureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFeature_IsStatic() {
		return (EAttribute)featureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFeature_FeaturingClassifier() {
		return (EReference)featureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStructuralFeature() {
		return structuralFeatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStructuralFeature_ReadWrite() {
		return (EAttribute)structuralFeatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBehavioralFeature() {
		return behavioralFeatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBehavioralFeature_IsAbstract() {
		return (EAttribute)behavioralFeatureEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBehavioralFeature_Concurrency() {
		return (EAttribute)behavioralFeatureEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavioralFeature_Parameter() {
		return (EReference)behavioralFeatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavioralFeature_FormalParameter() {
		return (EReference)behavioralFeatureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavioralFeature_ReturnResult() {
		return (EReference)behavioralFeatureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavioralFeature_RaisedException() {
		return (EReference)behavioralFeatureEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavioralFeature_Method() {
		return (EReference)behavioralFeatureEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstanceSpecification() {
		return instanceSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstanceSpecification_Slot() {
		return (EReference)instanceSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstanceSpecification_Classifier() {
		return (EReference)instanceSpecificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstanceSpecification_Specification() {
		return (EReference)instanceSpecificationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getInstanceSpecification_PortRemap() {
		return (EReference)instanceSpecificationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSlot() {
		return slotEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlot_OwningInstance() {
		return (EReference)slotEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlot_Value() {
		return (EReference)slotEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlot_DefiningFeature() {
		return (EReference)slotEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstanceValue() {
		return instanceValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstanceValue_Instance() {
		return (EReference)instanceValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstanceValue_OwnedAnonymousInstanceValue() {
		return (EReference)instanceValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGeneralization() {
		return generalizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGeneralization_IsSubstitutable() {
		return (EAttribute)generalizationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneralization_Specific() {
		return (EReference)generalizationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneralization_General() {
		return (EReference)generalizationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneralization_GeneralizationSet() {
		return (EReference)generalizationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRedefinableElement() {
		return redefinableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRedefinableElement_IsLeaf() {
		return (EAttribute)redefinableElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRedefinableElement_RedefinitionContext() {
		return (EReference)redefinableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPackageableElement() {
		return packageableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPackageableElement_PackageableElement_visibility() {
		return (EAttribute)packageableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElementImport() {
		return elementImportEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementImport_Visibility() {
		return (EAttribute)elementImportEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementImport_Alias() {
		return (EAttribute)elementImportEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementImport_ImportedElement() {
		return (EReference)elementImportEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementImport_ImportingNamespace() {
		return (EReference)elementImportEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPackageImport() {
		return packageImportEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPackageImport_Visibility() {
		return (EAttribute)packageImportEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackageImport_ImportedPackage() {
		return (EReference)packageImportEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackageImport_ImportingNamespace() {
		return (EReference)packageImportEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssociation() {
		return associationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAssociation_IsDerived() {
		return (EAttribute)associationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssociation_OwnedEnd() {
		return (EReference)associationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssociation_EndType() {
		return (EReference)associationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssociation_MemberEnd() {
		return (EReference)associationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPackageMerge() {
		return packageMergeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackageMerge_MergingPackage() {
		return (EReference)packageMergeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPackageMerge_MergedPackage() {
		return (EReference)packageMergeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStereotype() {
		return stereotypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStereotype_ExtendsMetaModelElement() {
		return (EAttribute)stereotypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProfile() {
		return profileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProfile_OwnedStereotype() {
		return (EReference)profileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProfile_MetaclassReference() {
		return (EReference)profileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProfile_MetamodelReference() {
		return (EReference)profileEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProfileApplication() {
		return profileApplicationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProfileApplication_ImportedProfile() {
		return (EReference)profileApplicationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtension() {
		return extensionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtension_IsRequired() {
		return (EAttribute)extensionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtension_Metaclass() {
		return (EReference)extensionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtensionEnd() {
		return extensionEndEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInformationItem() {
		return informationItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInformationItem_Represented() {
		return (EReference)informationItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInformationFlow() {
		return informationFlowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInformationFlow_Realization() {
		return (EReference)informationFlowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInformationFlow_Conveyed() {
		return (EReference)informationFlowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGeneralizationSet() {
		return generalizationSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGeneralizationSet_IsCovering() {
		return (EAttribute)generalizationSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGeneralizationSet_IsDisjoint() {
		return (EAttribute)generalizationSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneralizationSet_Powertype() {
		return (EReference)generalizationSetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneralizationSet_Generalization() {
		return (EReference)generalizationSetEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssociationClass() {
		return associationClassEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModel() {
		return modelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModel_Viewpoint() {
		return (EAttribute)modelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBehavior() {
		return behaviorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBehavior_IsReentrant() {
		return (EAttribute)behaviorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavior_Context() {
		return (EReference)behaviorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavior_RedefinedBehavior() {
		return (EReference)behaviorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavior_Specification() {
		return (EReference)behaviorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavior_Parameter() {
		return (EReference)behaviorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavior_FormalParameter() {
		return (EReference)behaviorEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavior_ReturnResult() {
		return (EReference)behaviorEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavior_Precondition() {
		return (EReference)behaviorEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavior_Postcondition() {
		return (EReference)behaviorEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavior_OwnedParameterSet() {
		return (EReference)behaviorEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBehavioredClassifier() {
		return behavioredClassifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavioredClassifier_OwnedBehavior() {
		return (EReference)behavioredClassifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavioredClassifier_ClassifierBehavior() {
		return (EReference)behavioredClassifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavioredClassifier_Implementation() {
		return (EReference)behavioredClassifierEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavioredClassifier_OwnedTrigger() {
		return (EReference)behavioredClassifierEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBehavioredClassifier_OwnedStateMachine() {
		return (EReference)behavioredClassifierEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActivity() {
		return activityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActivity_Body() {
		return (EAttribute)activityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActivity_Language() {
		return (EAttribute)activityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActivity_IsReadOnly() {
		return (EAttribute)activityEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActivity_IsSingleExecution() {
		return (EAttribute)activityEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivity_Edge() {
		return (EReference)activityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivity_Group() {
		return (EReference)activityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivity_Node() {
		return (EReference)activityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivity_Action() {
		return (EReference)activityEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivity_StructuredNode() {
		return (EReference)activityEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPermission() {
		return permissionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDependency() {
		return dependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependency_Client() {
		return (EReference)dependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependency_Supplier() {
		return (EReference)dependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependency_DependencyTarget() {
		return (EReference)dependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependency_Resemblance() {
		return (EAttribute)dependencyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependency_Replacement() {
		return (EAttribute)dependencyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependency_Trace() {
		return (EAttribute)dependencyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUsage() {
		return usageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstraction() {
		return abstractionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAbstraction_Mapping() {
		return (EReference)abstractionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRealization() {
		return realizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRealization_Abstraction() {
		return (EReference)realizationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRealization_RealizingClassifier() {
		return (EReference)realizationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubstitution() {
		return substitutionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubstitution_Contract() {
		return (EReference)substitutionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubstitution_SubstitutingClassifier() {
		return (EReference)substitutionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArtifact() {
		return artifactEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifact_FileName() {
		return (EAttribute)artifactEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifact_NestedArtifact() {
		return (EReference)artifactEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifact_Manifestation() {
		return (EReference)artifactEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifact_OwnedOperation() {
		return (EReference)artifactEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArtifact_OwnedAttribute() {
		return (EReference)artifactEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getManifestation() {
		return manifestationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getManifestation_UtilizedElement() {
		return (EReference)manifestationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInterface() {
		return interfaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterface_OwnedAttribute() {
		return (EReference)interfaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterface_OwnedOperation() {
		return (EReference)interfaceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterface_RedefinedInterface() {
		return (EReference)interfaceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterface_NestedClassifier() {
		return (EReference)interfaceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterface_OwnedReception() {
		return (EReference)interfaceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterface_Protocol() {
		return (EReference)interfaceEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterface_DeltaDeletedOperations() {
		return (EReference)interfaceEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterface_DeltaReplacedOperations() {
		return (EReference)interfaceEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterface_DeltaDeletedAttributes() {
		return (EReference)interfaceEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterface_DeltaReplacedAttributes() {
		return (EReference)interfaceEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImplementation() {
		return implementationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImplementation_Contract() {
		return (EReference)implementationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImplementation_ImplementingClassifier() {
		return (EReference)implementationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActor() {
		return actorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtend() {
		return extendEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtend_ExtendedCase() {
		return (EReference)extendEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtend_Extension() {
		return (EReference)extendEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtend_Condition() {
		return (EReference)extendEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtend_ExtensionLocation() {
		return (EReference)extendEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUseCase() {
		return useCaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUseCase_Include() {
		return (EReference)useCaseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUseCase_Extend() {
		return (EReference)useCaseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUseCase_ExtensionPoint() {
		return (EReference)useCaseEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUseCase_Subject() {
		return (EReference)useCaseEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtensionPoint() {
		return extensionPointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtensionPoint_UseCase() {
		return (EReference)extensionPointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInclude() {
		return includeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInclude_IncludingCase() {
		return (EReference)includeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInclude_Addition() {
		return (EReference)includeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActivityEdge() {
		return activityEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityEdge_Activity() {
		return (EReference)activityEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityEdge_Source() {
		return (EReference)activityEdgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityEdge_Target() {
		return (EReference)activityEdgeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityEdge_InGroup() {
		return (EReference)activityEdgeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityEdge_Guard() {
		return (EReference)activityEdgeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityEdge_RedefinedElement() {
		return (EReference)activityEdgeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityEdge_InStructuredNode() {
		return (EReference)activityEdgeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityEdge_InPartition() {
		return (EReference)activityEdgeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityEdge_Weight() {
		return (EReference)activityEdgeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityEdge_Interrupts() {
		return (EReference)activityEdgeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActivityGroup() {
		return activityGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityGroup_SuperGroup() {
		return (EReference)activityGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityGroup_ActivityGroup_activity() {
		return (EReference)activityGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActivityNode() {
		return activityNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityNode_Outgoing() {
		return (EReference)activityNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityNode_Incoming() {
		return (EReference)activityNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityNode_InGroup() {
		return (EReference)activityNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityNode_Activity() {
		return (EReference)activityNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityNode_RedefinedElement() {
		return (EReference)activityNodeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityNode_InStructuredNode() {
		return (EReference)activityNodeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityNode_InPartition() {
		return (EReference)activityNodeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityNode_InInterruptibleRegion() {
		return (EReference)activityNodeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAction() {
		return actionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAction_Effect() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAction_Output() {
		return (EReference)actionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAction_Input() {
		return (EReference)actionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAction_Context() {
		return (EReference)actionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAction_LocalPrecondition() {
		return (EReference)actionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAction_LocalPostcondition() {
		return (EReference)actionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectNode() {
		return objectNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObjectNode_Ordering() {
		return (EAttribute)objectNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectNode_UpperBound() {
		return (EReference)objectNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectNode_InState() {
		return (EReference)objectNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectNode_Selection() {
		return (EReference)objectNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getControlNode() {
		return controlNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getControlFlow() {
		return controlFlowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectFlow() {
		return objectFlowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObjectFlow_IsMulticast() {
		return (EAttribute)objectFlowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObjectFlow_IsMultireceive() {
		return (EAttribute)objectFlowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectFlow_Transformation() {
		return (EReference)objectFlowEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectFlow_Selection() {
		return (EReference)objectFlowEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInitialNode() {
		return initialNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFinalNode() {
		return finalNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActivityFinalNode() {
		return activityFinalNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDecisionNode() {
		return decisionNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDecisionNode_DecisionInput() {
		return (EReference)decisionNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMergeNode() {
		return mergeNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExecutableNode() {
		return executableNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutableNode_Handler() {
		return (EReference)executableNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOutputPin() {
		return outputPinEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInputPin() {
		return inputPinEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPin() {
		return pinEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActivityParameterNode() {
		return activityParameterNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityParameterNode_Parameter() {
		return (EReference)activityParameterNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValuePin() {
		return valuePinEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getValuePin_Value() {
		return (EReference)valuePinEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVariable_Scope() {
		return (EReference)variableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStructuredActivityNode() {
		return structuredActivityNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStructuredActivityNode_MustIsolate() {
		return (EAttribute)structuredActivityNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredActivityNode_Variable() {
		return (EReference)structuredActivityNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getStructuredActivityNode_ContainedNode() {
		return (EReference)structuredActivityNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredActivityNode_ContainedEdge() {
		return (EReference)structuredActivityNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConditionalNode() {
		return conditionalNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionalNode_IsDeterminate() {
		return (EAttribute)conditionalNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionalNode_IsAssured() {
		return (EAttribute)conditionalNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConditionalNode_Clause() {
		return (EReference)conditionalNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConditionalNode_Result() {
		return (EReference)conditionalNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClause() {
		return clauseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClause_Test() {
		return (EReference)clauseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClause_Body() {
		return (EReference)clauseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClause_PredecessorClause() {
		return (EReference)clauseEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClause_SuccessorClause() {
		return (EReference)clauseEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClause_Decider() {
		return (EReference)clauseEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClause_BodyOutput() {
		return (EReference)clauseEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLoopNode() {
		return loopNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoopNode_IsTestedFirst() {
		return (EAttribute)loopNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoopNode_BodyPart() {
		return (EReference)loopNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoopNode_SetupPart() {
		return (EReference)loopNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoopNode_Decider() {
		return (EReference)loopNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoopNode_Test() {
		return (EReference)loopNodeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoopNode_Result() {
		return (EReference)loopNodeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoopNode_LoopVariable() {
		return (EReference)loopNodeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoopNode_BodyOutput() {
		return (EReference)loopNodeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoopNode_LoopVariableInput() {
		return (EReference)loopNodeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectorEnd() {
		return connectorEndEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectorEnd_DefiningEnd() {
		return (EReference)connectorEndEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectorEnd_Role() {
		return (EReference)connectorEndEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectorEnd_PartWithPort() {
		return (EReference)connectorEndEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectableElement() {
		return connectableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectableElement_End() {
		return (EReference)connectableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnector() {
		return connectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnector_Kind() {
		return (EAttribute)connectorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnector_Type() {
		return (EReference)connectorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnector_RedefinedConnector() {
		return (EReference)connectorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnector_End() {
		return (EReference)connectorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnector_Contract() {
		return (EReference)connectorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStructuredClassifier() {
		return structuredClassifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_OwnedAttribute() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_Part() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_Role() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_OwnedConnector() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_DeltaDeletedAttributes() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_DeltaReplacedAttributes() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_DeltaDeletedPorts() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_DeltaReplacedPorts() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_DeltaDeletedConnectors() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_DeltaReplacedConnectors() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_DeltaDeletedOperations() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_DeltaReplacedOperations() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_DeltaDeletedTraces() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredClassifier_DeltaReplacedTraces() {
		return (EReference)structuredClassifierEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCallTrigger() {
		return callTriggerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCallTrigger_Operation() {
		return (EReference)callTriggerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMessageTrigger() {
		return messageTriggerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChangeTrigger() {
		return changeTriggerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeTrigger_ChangeExpression() {
		return (EReference)changeTriggerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTrigger() {
		return triggerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTrigger_Port() {
		return (EReference)triggerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReception() {
		return receptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReception_Signal() {
		return (EReference)receptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSignal() {
		return signalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSignal_OwnedAttribute() {
		return (EReference)signalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSignalTrigger() {
		return signalTriggerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSignalTrigger_Signal() {
		return (EReference)signalTriggerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimeTrigger() {
		return timeTriggerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTimeTrigger_IsRelative() {
		return (EAttribute)timeTriggerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTimeTrigger_When() {
		return (EReference)timeTriggerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAnyTrigger() {
		return anyTriggerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTemplateSignature() {
		return templateSignatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateSignature_Parameter() {
		return (EReference)templateSignatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateSignature_OwnedParameter() {
		return (EReference)templateSignatureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateSignature_NestedSignature() {
		return (EReference)templateSignatureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateSignature_NestingSignature() {
		return (EReference)templateSignatureEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateSignature_Template() {
		return (EReference)templateSignatureEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTemplateParameter() {
		return templateParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateParameter_Signature() {
		return (EReference)templateParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateParameter_ParameteredElement() {
		return (EReference)templateParameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateParameter_OwnedParameteredElement() {
		return (EReference)templateParameterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateParameter_Default() {
		return (EReference)templateParameterEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateParameter_OwnedDefault() {
		return (EReference)templateParameterEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTemplateableElement() {
		return templateableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateableElement_TemplateBinding() {
		return (EReference)templateableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateableElement_OwnedTemplateSignature() {
		return (EReference)templateableElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringExpression() {
		return stringExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStringExpression_SubExpression() {
		return (EReference)stringExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStringExpression_OwningExpression() {
		return (EReference)stringExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameterableElement() {
		return parameterableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameterableElement_TemplateParameter() {
		return (EReference)parameterableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameterableElement_OwningParameter() {
		return (EReference)parameterableElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTemplateBinding() {
		return templateBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateBinding_BoundElement() {
		return (EReference)templateBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateBinding_Signature() {
		return (EReference)templateBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateBinding_ParameterSubstitution() {
		return (EReference)templateBindingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTemplateParameterSubstitution() {
		return templateParameterSubstitutionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateParameterSubstitution_Formal() {
		return (EReference)templateParameterSubstitutionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateParameterSubstitution_TemplateBinding() {
		return (EReference)templateParameterSubstitutionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateParameterSubstitution_Actual() {
		return (EReference)templateParameterSubstitutionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTemplateParameterSubstitution_OwnedActual() {
		return (EReference)templateParameterSubstitutionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCollaboration() {
		return collaborationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCollaboration_CollaborationRole() {
		return (EReference)collaborationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationTemplateParameter() {
		return operationTemplateParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassifierTemplateParameter() {
		return classifierTemplateParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassifierTemplateParameter_AllowSubstitutable() {
		return (EAttribute)classifierTemplateParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameterableClassifier() {
		return parameterableClassifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRedefinableTemplateSignature() {
		return redefinableTemplateSignatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTemplateableClassifier() {
		return templateableClassifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectableElementTemplateParameter() {
		return connectableElementTemplateParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getForkNode() {
		return forkNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJoinNode() {
		return joinNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJoinNode_IsCombineDuplicate() {
		return (EAttribute)joinNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJoinNode_JoinSpec() {
		return (EReference)joinNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFlowFinalNode() {
		return flowFinalNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCentralBufferNode() {
		return centralBufferNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActivityPartition() {
		return activityPartitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActivityPartition_IsDimension() {
		return (EAttribute)activityPartitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActivityPartition_IsExternal() {
		return (EAttribute)activityPartitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityPartition_ContainedEdge() {
		return (EReference)activityPartitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getActivityPartition_ContainedNode() {
		return (EReference)activityPartitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityPartition_Subgroup() {
		return (EReference)activityPartitionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityPartition_SuperPartition() {
		return (EReference)activityPartitionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActivityPartition_Represents() {
		return (EReference)activityPartitionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPort() {
		return portEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPort_IsBehavior() {
		return (EAttribute)portEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPort_IsService() {
		return (EAttribute)portEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPort_Required() {
		return (EReference)portEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPort_RedefinedPort() {
		return (EReference)portEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPort_Provided() {
		return (EReference)portEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPort_Protocol() {
		return (EReference)portEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPort_Kind() {
		return (EAttribute)portEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEncapsulatedClassifier() {
		return encapsulatedClassifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEncapsulatedClassifier_OwnedPort() {
		return (EReference)encapsulatedClassifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpansionNode() {
		return expansionNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExpansionNode_RegionAsOutput() {
		return (EReference)expansionNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExpansionNode_RegionAsInput() {
		return (EReference)expansionNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpansionRegion() {
		return expansionRegionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExpansionRegion_Mode() {
		return (EAttribute)expansionRegionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExpansionRegion_OutputElement() {
		return (EReference)expansionRegionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExpansionRegion_InputElement() {
		return (EReference)expansionRegionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExceptionHandler() {
		return exceptionHandlerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExceptionHandler_ProtectedNode() {
		return (EReference)exceptionHandlerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExceptionHandler_HandlerBody() {
		return (EReference)exceptionHandlerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExceptionHandler_ExceptionInput() {
		return (EReference)exceptionHandlerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExceptionHandler_ExceptionType() {
		return (EReference)exceptionHandlerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInteraction() {
		return interactionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteraction_Lifeline() {
		return (EReference)interactionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteraction_Message() {
		return (EReference)interactionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteraction_Fragment() {
		return (EReference)interactionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteraction_FormalGate() {
		return (EReference)interactionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInteractionFragment() {
		return interactionFragmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteractionFragment_Covered() {
		return (EReference)interactionFragmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteractionFragment_GeneralOrdering() {
		return (EReference)interactionFragmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteractionFragment_EnclosingInteraction() {
		return (EReference)interactionFragmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteractionFragment_EnclosingOperand() {
		return (EReference)interactionFragmentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLifeline() {
		return lifelineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLifeline_CoveredBy() {
		return (EReference)lifelineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLifeline_Represents() {
		return (EReference)lifelineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLifeline_Interaction() {
		return (EReference)lifelineEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLifeline_Selector() {
		return (EReference)lifelineEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLifeline_DecomposedAs() {
		return (EReference)lifelineEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMessage() {
		return messageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMessage_MessageKind() {
		return (EAttribute)messageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMessage_MessageSort() {
		return (EAttribute)messageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMessage_ReceiveEvent() {
		return (EReference)messageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMessage_SendEvent() {
		return (EReference)messageEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMessage_Connector() {
		return (EReference)messageEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMessage_Interaction() {
		return (EReference)messageEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMessage_Signature() {
		return (EReference)messageEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMessage_Argument() {
		return (EReference)messageEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGeneralOrdering() {
		return generalOrderingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneralOrdering_Before() {
		return (EReference)generalOrderingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneralOrdering_After() {
		return (EReference)generalOrderingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMessageEnd() {
		return messageEndEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMessageEnd_ReceiveMessage() {
		return (EReference)messageEndEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMessageEnd_SendMessage() {
		return (EReference)messageEndEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventOccurrence() {
		return eventOccurrenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventOccurrence_StartExec() {
		return (EReference)eventOccurrenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventOccurrence_FinishExec() {
		return (EReference)eventOccurrenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventOccurrence_ToAfter() {
		return (EReference)eventOccurrenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventOccurrence_ToBefore() {
		return (EReference)eventOccurrenceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExecutionOccurrence() {
		return executionOccurrenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionOccurrence_Start() {
		return (EReference)executionOccurrenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionOccurrence_Finish() {
		return (EReference)executionOccurrenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutionOccurrence_Behavior() {
		return (EReference)executionOccurrenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateInvariant() {
		return stateInvariantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateInvariant_Invariant() {
		return (EReference)stateInvariantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStop() {
		return stopEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCollaborationOccurrence() {
		return collaborationOccurrenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCollaborationOccurrence_Type() {
		return (EReference)collaborationOccurrenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCollaborationOccurrence_RoleBinding() {
		return (EReference)collaborationOccurrenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateObjectAction() {
		return createObjectActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateObjectAction_Classifier() {
		return (EReference)createObjectActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateObjectAction_Result() {
		return (EReference)createObjectActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDestroyObjectAction() {
		return destroyObjectActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDestroyObjectAction_IsDestroyLinks() {
		return (EAttribute)destroyObjectActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDestroyObjectAction_IsDestroyOwnedObjects() {
		return (EAttribute)destroyObjectActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDestroyObjectAction_Target() {
		return (EReference)destroyObjectActionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTestIdentityAction() {
		return testIdentityActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestIdentityAction_First() {
		return (EReference)testIdentityActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestIdentityAction_Second() {
		return (EReference)testIdentityActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTestIdentityAction_Result() {
		return (EReference)testIdentityActionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReadSelfAction() {
		return readSelfActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadSelfAction_Result() {
		return (EReference)readSelfActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStructuralFeatureAction() {
		return structuralFeatureActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuralFeatureAction_StructuralFeature() {
		return (EReference)structuralFeatureActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuralFeatureAction_Object() {
		return (EReference)structuralFeatureActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReadStructuralFeatureAction() {
		return readStructuralFeatureActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadStructuralFeatureAction_Result() {
		return (EReference)readStructuralFeatureActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWriteStructuralFeatureAction() {
		return writeStructuralFeatureActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWriteStructuralFeatureAction_Value() {
		return (EReference)writeStructuralFeatureActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClearStructuralFeatureAction() {
		return clearStructuralFeatureActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveStructuralFeatureValueAction() {
		return removeStructuralFeatureValueActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAddStructuralFeatureValueAction() {
		return addStructuralFeatureValueActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddStructuralFeatureValueAction_IsReplaceAll() {
		return (EAttribute)addStructuralFeatureValueActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAddStructuralFeatureValueAction_InsertAt() {
		return (EReference)addStructuralFeatureValueActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLinkAction() {
		return linkActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLinkAction_EndData() {
		return (EReference)linkActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLinkEndData() {
		return linkEndDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLinkEndData_Value() {
		return (EReference)linkEndDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLinkEndData_End() {
		return (EReference)linkEndDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLinkEndData_Qualifier() {
		return (EReference)linkEndDataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReadLinkAction() {
		return readLinkActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadLinkAction_Result() {
		return (EReference)readLinkActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLinkEndCreationData() {
		return linkEndCreationDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLinkEndCreationData_IsReplaceAll() {
		return (EAttribute)linkEndCreationDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLinkEndCreationData_InsertAt() {
		return (EReference)linkEndCreationDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateLinkAction() {
		return createLinkActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWriteLinkAction() {
		return writeLinkActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDestroyLinkAction() {
		return destroyLinkActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClearAssociationAction() {
		return clearAssociationActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClearAssociationAction_Object() {
		return (EReference)clearAssociationActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getClearAssociationAction_Association() {
		return (EReference)clearAssociationActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariableAction() {
		return variableActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVariableAction_Variable() {
		return (EReference)variableActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReadVariableAction() {
		return readVariableActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadVariableAction_Result() {
		return (EReference)readVariableActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWriteVariableAction() {
		return writeVariableActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWriteVariableAction_Value() {
		return (EReference)writeVariableActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClearVariableAction() {
		return clearVariableActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAddVariableValueAction() {
		return addVariableValueActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddVariableValueAction_IsReplaceAll() {
		return (EAttribute)addVariableValueActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAddVariableValueAction_InsertAt() {
		return (EReference)addVariableValueActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveVariableValueAction() {
		return removeVariableValueActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApplyFunctionAction() {
		return applyFunctionActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplyFunctionAction_Function() {
		return (EReference)applyFunctionActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplyFunctionAction_Argument() {
		return (EReference)applyFunctionActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplyFunctionAction_Result() {
		return (EReference)applyFunctionActionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPrimitiveFunction() {
		return primitiveFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrimitiveFunction_Body() {
		return (EAttribute)primitiveFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPrimitiveFunction_Language() {
		return (EAttribute)primitiveFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCallAction() {
		return callActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCallAction_IsSynchronous() {
		return (EAttribute)callActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCallAction_Result() {
		return (EReference)callActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInvocationAction() {
		return invocationActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInvocationAction_Argument() {
		return (EReference)invocationActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInvocationAction_OnPort() {
		return (EReference)invocationActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSendSignalAction() {
		return sendSignalActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSendSignalAction_Target() {
		return (EReference)sendSignalActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSendSignalAction_Signal() {
		return (EReference)sendSignalActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBroadcastSignalAction() {
		return broadcastSignalActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBroadcastSignalAction_Signal() {
		return (EReference)broadcastSignalActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSendObjectAction() {
		return sendObjectActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSendObjectAction_Target() {
		return (EReference)sendObjectActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSendObjectAction_Request() {
		return (EReference)sendObjectActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCallOperationAction() {
		return callOperationActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCallOperationAction_Operation() {
		return (EReference)callOperationActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCallOperationAction_Target() {
		return (EReference)callOperationActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCallBehaviorAction() {
		return callBehaviorActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCallBehaviorAction_Behavior() {
		return (EReference)callBehaviorActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateMachine() {
		return stateMachineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateMachine_Region() {
		return (EReference)stateMachineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateMachine_ConnectionPoint() {
		return (EReference)stateMachineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateMachine_ExtendedStateMachine() {
		return (EReference)stateMachineEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateMachine_StateMachine_redefinitionContext() {
		return (EReference)stateMachineEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRegion() {
		return regionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegion_Subvertex() {
		return (EReference)regionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegion_Transition() {
		return (EReference)regionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegion_StateMachine() {
		return (EReference)regionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegion_State() {
		return (EReference)regionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegion_ExtendedRegion() {
		return (EReference)regionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPseudostate() {
		return pseudostateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPseudostate_Kind() {
		return (EAttribute)pseudostateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getState() {
		return stateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_IsComposite() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_IsOrthogonal() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_IsSimple() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_IsSubmachineState() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Submachine() {
		return (EReference)stateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Connection() {
		return (EReference)stateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_RedefinedState() {
		return (EReference)stateEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_DeferrableTrigger() {
		return (EReference)stateEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Region() {
		return (EReference)stateEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Entry() {
		return (EReference)stateEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Exit() {
		return (EReference)stateEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_DoActivity() {
		return (EReference)stateEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_StateInvariant() {
		return (EReference)stateEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVertex() {
		return vertexEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVertex_Container() {
		return (EReference)vertexEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVertex_Outgoing() {
		return (EReference)vertexEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVertex_Incoming() {
		return (EReference)vertexEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectionPointReference() {
		return connectionPointReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionPointReference_Entry() {
		return (EReference)connectionPointReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionPointReference_Exit() {
		return (EReference)connectionPointReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTransition() {
		return transitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransition_Kind() {
		return (EAttribute)transitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Container() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Source() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Target() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_RedefinedTransition() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Trigger() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Guard() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Effect() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFinalState() {
		return finalStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReadExtentAction() {
		return readExtentActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadExtentAction_Result() {
		return (EReference)readExtentActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadExtentAction_Classifier() {
		return (EReference)readExtentActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReclassifyObjectAction() {
		return reclassifyObjectActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReclassifyObjectAction_IsReplaceAll() {
		return (EAttribute)reclassifyObjectActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReclassifyObjectAction_OldClassifier() {
		return (EReference)reclassifyObjectActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReclassifyObjectAction_NewClassifier() {
		return (EReference)reclassifyObjectActionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReclassifyObjectAction_Object() {
		return (EReference)reclassifyObjectActionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReadIsClassifiedObjectAction() {
		return readIsClassifiedObjectActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReadIsClassifiedObjectAction_IsDirect() {
		return (EAttribute)readIsClassifiedObjectActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadIsClassifiedObjectAction_Classifier() {
		return (EReference)readIsClassifiedObjectActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadIsClassifiedObjectAction_Result() {
		return (EReference)readIsClassifiedObjectActionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadIsClassifiedObjectAction_Object() {
		return (EReference)readIsClassifiedObjectActionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStartOwnedBehaviorAction() {
		return startOwnedBehaviorActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStartOwnedBehaviorAction_Object() {
		return (EReference)startOwnedBehaviorActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQualifierValue() {
		return qualifierValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQualifierValue_Qualifier() {
		return (EReference)qualifierValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQualifierValue_Value() {
		return (EReference)qualifierValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReadLinkObjectEndAction() {
		return readLinkObjectEndActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadLinkObjectEndAction_Object() {
		return (EReference)readLinkObjectEndActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadLinkObjectEndAction_End() {
		return (EReference)readLinkObjectEndActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadLinkObjectEndAction_Result() {
		return (EReference)readLinkObjectEndActionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReadLinkObjectEndQualifierAction() {
		return readLinkObjectEndQualifierActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadLinkObjectEndQualifierAction_Object() {
		return (EReference)readLinkObjectEndQualifierActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadLinkObjectEndQualifierAction_Result() {
		return (EReference)readLinkObjectEndQualifierActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReadLinkObjectEndQualifierAction_Qualifier() {
		return (EReference)readLinkObjectEndQualifierActionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreateLinkObjectAction() {
		return createLinkObjectActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCreateLinkObjectAction_Result() {
		return (EReference)createLinkObjectActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAcceptEventAction() {
		return acceptEventActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAcceptEventAction_Trigger() {
		return (EReference)acceptEventActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAcceptEventAction_Result() {
		return (EReference)acceptEventActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAcceptCallAction() {
		return acceptCallActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAcceptCallAction_ReturnInformation() {
		return (EReference)acceptCallActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReplyAction() {
		return replyActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReplyAction_ReplyToCall() {
		return (EReference)replyActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReplyAction_ReplyValue() {
		return (EReference)replyActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReplyAction_ReturnInformation() {
		return (EReference)replyActionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRaiseExceptionAction() {
		return raiseExceptionActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRaiseExceptionAction_Exception() {
		return (EReference)raiseExceptionActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataStoreNode() {
		return dataStoreNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInterruptibleActivityRegion() {
		return interruptibleActivityRegionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterruptibleActivityRegion_InterruptingEdge() {
		return (EReference)interruptibleActivityRegionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getInterruptibleActivityRegion_ContainedNode() {
		return (EReference)interruptibleActivityRegionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameterSet() {
		return parameterSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameterSet_Parameter() {
		return (EReference)parameterSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameterSet_Condition() {
		return (EReference)parameterSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInteractionOccurrence() {
		return interactionOccurrenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteractionOccurrence_RefersTo() {
		return (EReference)interactionOccurrenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteractionOccurrence_ActualGate() {
		return (EReference)interactionOccurrenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteractionOccurrence_Argument() {
		return (EReference)interactionOccurrenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGate() {
		return gateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPartDecomposition() {
		return partDecompositionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInteractionOperand() {
		return interactionOperandEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteractionOperand_Guard() {
		return (EReference)interactionOperandEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteractionOperand_Fragment() {
		return (EReference)interactionOperandEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInteractionConstraint() {
		return interactionConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteractionConstraint_Minint() {
		return (EReference)interactionConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInteractionConstraint_Maxint() {
		return (EReference)interactionConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCombinedFragment() {
		return combinedFragmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCombinedFragment_InteractionOperator() {
		return (EAttribute)combinedFragmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCombinedFragment_Operand() {
		return (EReference)combinedFragmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCombinedFragment_CfragmentGate() {
		return (EReference)combinedFragmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContinuation() {
		return continuationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContinuation_Setting() {
		return (EAttribute)continuationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimeExpression() {
		return timeExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTimeExpression_FirstTime() {
		return (EAttribute)timeExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTimeExpression_Event() {
		return (EReference)timeExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDuration() {
		return durationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDuration_FirstTime() {
		return (EAttribute)durationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDuration_Event() {
		return (EReference)durationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimeObservationAction() {
		return timeObservationActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTimeObservationAction_Now() {
		return (EReference)timeObservationActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDurationInterval() {
		return durationIntervalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInterval() {
		return intervalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterval_Min() {
		return (EReference)intervalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInterval_Max() {
		return (EReference)intervalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimeConstraint() {
		return timeConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntervalConstraint() {
		return intervalConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimeInterval() {
		return timeIntervalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDurationObservationAction() {
		return durationObservationActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDurationObservationAction_Duration() {
		return (EReference)durationObservationActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDurationConstraint() {
		return durationConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProtocolConformance() {
		return protocolConformanceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProtocolConformance_SpecificMachine() {
		return (EReference)protocolConformanceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProtocolConformance_GeneralMachine() {
		return (EReference)protocolConformanceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProtocolStateMachine() {
		return protocolStateMachineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProtocolStateMachine_Conformance() {
		return (EReference)protocolStateMachineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProtocolTransition() {
		return protocolTransitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProtocolTransition_PostCondition() {
		return (EReference)protocolTransitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProtocolTransition_Referred() {
		return (EReference)protocolTransitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProtocolTransition_PreCondition() {
		return (EReference)protocolTransitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponent() {
		return componentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponent_IsIndirectlyInstantiated() {
		return (EAttribute)componentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_Required() {
		return (EReference)componentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_Provided() {
		return (EReference)componentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_Realization() {
		return (EReference)componentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponent_OwnedMember() {
		return (EReference)componentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeployment() {
		return deploymentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeployment_DeployedArtifact() {
		return (EReference)deploymentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeployment_Location() {
		return (EReference)deploymentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeployment_Configuration() {
		return (EReference)deploymentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeployedArtifact() {
		return deployedArtifactEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeploymentTarget() {
		return deploymentTargetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentTarget_Deployment() {
		return (EReference)deploymentTargetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeploymentTarget_DeployedElement() {
		return (EReference)deploymentTargetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNode() {
		return nodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNode_NestedNode() {
		return (EReference)nodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDevice() {
		return deviceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExecutionEnvironment() {
		return executionEnvironmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommunicationPath() {
		return communicationPathEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeploymentSpecification() {
		return deploymentSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeploymentSpecification_DeploymentLocation() {
		return (EAttribute)deploymentSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeploymentSpecification_ExecutionLocation() {
		return (EAttribute)deploymentSpecificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJ_FigureContainer() {
		return j_FigureContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJ_FigureContainer_Figures() {
		return (EReference)j_FigureContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJ_FigureContainer_Properties() {
		return (EReference)j_FigureContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJ_Diagram() {
		return j_DiagramEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Diagram_Name() {
		return (EAttribute)j_DiagramEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJ_Diagram_LastFigureId() {
		return (EAttribute)j_DiagramEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJ_DiagramHolder() {
		return j_DiagramHolderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJ_DiagramHolder_Diagram() {
		return (EReference)j_DiagramHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_DiagramHolder_SaveTime() {
		return (EAttribute)j_DiagramHolderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_DiagramHolder_SavedBy() {
		return (EAttribute)j_DiagramHolderEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAppliedBasicStereotypeValue() {
		return appliedBasicStereotypeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAppliedBasicStereotypeValue_Property() {
		return (EReference)appliedBasicStereotypeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAppliedBasicStereotypeValue_Value() {
		return (EReference)appliedBasicStereotypeValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyValueSpecification() {
		return propertyValueSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyValueSpecification_Aliased() {
		return (EAttribute)propertyValueSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyValueSpecification_Property() {
		return (EReference)propertyValueSpecificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaReplacedConstituent() {
		return deltaReplacedConstituentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeltaReplacedConstituent_Replaced() {
		return (EReference)deltaReplacedConstituentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeltaReplacedConstituent_Replacement() {
		return (EReference)deltaReplacedConstituentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaDeletedConstituent() {
		return deltaDeletedConstituentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeltaDeletedConstituent_Deleted() {
		return (EReference)deltaDeletedConstituentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaReplacedAttribute() {
		return deltaReplacedAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaDeletedAttribute() {
		return deltaDeletedAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaReplacedPort() {
		return deltaReplacedPortEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaDeletedPort() {
		return deltaDeletedPortEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaReplacedConnector() {
		return deltaReplacedConnectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaDeletedConnector() {
		return deltaDeletedConnectorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaReplacedOperation() {
		return deltaReplacedOperationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaDeletedOperation() {
		return deltaDeletedOperationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getPortRemap() {
		return portRemapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getPortRemap_OriginalPort() {
		return (EReference)portRemapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getPortRemap_NewPort() {
		return (EReference)portRemapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSavedReference() {
		return savedReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSavedReference_From() {
		return (EAttribute)savedReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSavedReference_ToEClass() {
		return (EReference)savedReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSavedReference_Feature() {
		return (EReference)savedReferenceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequirementsFeature() {
		return requirementsFeatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRequirementsFeature_Subfeatures() {
		return (EReference)requirementsFeatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRequirementsFeature_DeltaReplacedSubfeatures() {
		return (EReference)requirementsFeatureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRequirementsFeature_DeltaDeletedSubfeatures() {
		return (EReference)requirementsFeatureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequirementsFeatureLink() {
		return requirementsFeatureLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequirementsFeatureLink_Kind() {
		return (EAttribute)requirementsFeatureLinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRequirementsFeatureLink_Type() {
		return (EReference)requirementsFeatureLinkEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaReplacedRequirementsFeatureLink() {
		return deltaReplacedRequirementsFeatureLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaDeletedRequirementsFeatureLink() {
		return deltaDeletedRequirementsFeatureLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaDeletedTrace() {
		return deltaDeletedTraceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeltaReplacedTrace() {
		return deltaReplacedTraceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSavedReference_To() {
		return (EAttribute)savedReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJ_Figure() {
		return j_FigureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJ_Figure_Id() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Recreator() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Anchor1Id() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Anchor2Id() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_ContainedName() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Text() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Name() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_VirtualPoint() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Points() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_BrOffset() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_TlOffset() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Show() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Autosized() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Icon() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Point() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Dimensions() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_SuppressAttributes() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_SuppressOperations() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_SuppressContents() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Offset() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Min() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Accessibility() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_ClassifierScope() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Figure_Type() {
		return (EAttribute)j_FigureEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJ_Figure_Subject() {
		return (EReference)j_FigureEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJ_Property() {
		return j_PropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Property_Name() {
		return (EAttribute)j_PropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJ_Property_Value() {
		return (EAttribute)j_PropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getVisibilityKind() {
		return visibilityKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getParameterDirectionKind() {
		return parameterDirectionKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getAggregationKind() {
		return aggregationKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getCallConcurrencyKind() {
		return callConcurrencyKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getExpansionKind() {
		return expansionKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMessageKind() {
		return messageKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMessageSort() {
		return messageSortEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTransitionKind() {
		return transitionKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPseudostateKind() {
		return pseudostateKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getObjectNodeOrderingKind() {
		return objectNodeOrderingKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getInteractionOperator() {
		return interactionOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getConnectorKind() {
		return connectorKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getComponentKind() {
		return componentKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPropertyAccessKind() {
		return propertyAccessKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPortKind() {
		return portKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getRequirementsLinkKind() {
		return requirementsLinkKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getParameterEffectKind() {
		return parameterEffectKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getInteger() {
		return integerEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getBoolean() {
		return booleanEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getString() {
		return stringEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getUnlimitedNatural() {
		return unlimitedNaturalEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSequence() {
		return sequenceEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSet() {
		return setEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UML2Factory getUML2Factory() {
		return (UML2Factory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		elementEClass = createEClass(ELEMENT);
		createEReference(elementEClass, ELEMENT__OWNED_ELEMENT);
		createEReference(elementEClass, ELEMENT__OWNER);
		createEReference(elementEClass, ELEMENT__OWNED_COMMENT);
		createEAttribute(elementEClass, ELEMENT__JDELETED);
		createEAttribute(elementEClass, ELEMENT__DOCUMENTATION);
		createEReference(elementEClass, ELEMENT__APPLIED_BASIC_STEREOTYPES);
		createEReference(elementEClass, ELEMENT__APPLIED_BASIC_STEREOTYPE_VALUES);
		createEAttribute(elementEClass, ELEMENT__UUID);

		multiplicityElementEClass = createEClass(MULTIPLICITY_ELEMENT);
		createEAttribute(multiplicityElementEClass, MULTIPLICITY_ELEMENT__IS_ORDERED);
		createEAttribute(multiplicityElementEClass, MULTIPLICITY_ELEMENT__IS_UNIQUE);
		createEAttribute(multiplicityElementEClass, MULTIPLICITY_ELEMENT__LOWER);
		createEAttribute(multiplicityElementEClass, MULTIPLICITY_ELEMENT__UPPER);
		createEReference(multiplicityElementEClass, MULTIPLICITY_ELEMENT__UPPER_VALUE);
		createEReference(multiplicityElementEClass, MULTIPLICITY_ELEMENT__LOWER_VALUE);

		namedElementEClass = createEClass(NAMED_ELEMENT);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__QUALIFIED_NAME);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__VISIBILITY);
		createEReference(namedElementEClass, NAMED_ELEMENT__CLIENT_DEPENDENCY);
		createEReference(namedElementEClass, NAMED_ELEMENT__NAME_EXPRESSION);
		createEReference(namedElementEClass, NAMED_ELEMENT__OWNED_ANONYMOUS_DEPENDENCIES);
		createEReference(namedElementEClass, NAMED_ELEMENT__REVERSE_DEPENDENCIES);
		createEReference(namedElementEClass, NAMED_ELEMENT__REVERSE_GENERALIZATIONS);

		namespaceEClass = createEClass(NAMESPACE);
		createEReference(namespaceEClass, NAMESPACE__MEMBER);
		createEReference(namespaceEClass, NAMESPACE__OWNED_RULE);
		createEReference(namespaceEClass, NAMESPACE__IMPORTED_MEMBER);
		createEReference(namespaceEClass, NAMESPACE__ELEMENT_IMPORT);
		createEReference(namespaceEClass, NAMESPACE__PACKAGE_IMPORT);

		opaqueExpressionEClass = createEClass(OPAQUE_EXPRESSION);
		createEAttribute(opaqueExpressionEClass, OPAQUE_EXPRESSION__BODY);
		createEAttribute(opaqueExpressionEClass, OPAQUE_EXPRESSION__LANGUAGE);
		createEReference(opaqueExpressionEClass, OPAQUE_EXPRESSION__RESULT);
		createEReference(opaqueExpressionEClass, OPAQUE_EXPRESSION__BEHAVIOR);

		valueSpecificationEClass = createEClass(VALUE_SPECIFICATION);

		expressionEClass = createEClass(EXPRESSION);
		createEAttribute(expressionEClass, EXPRESSION__SYMBOL);
		createEReference(expressionEClass, EXPRESSION__OPERAND);

		commentEClass = createEClass(COMMENT);
		createEAttribute(commentEClass, COMMENT__BODY);
		createEReference(commentEClass, COMMENT__ANNOTATED_ELEMENT);
		createEReference(commentEClass, COMMENT__BODY_EXPRESSION);
		createEAttribute(commentEClass, COMMENT__BINARY_DATA);
		createEAttribute(commentEClass, COMMENT__BINARY_FORMAT);
		createEAttribute(commentEClass, COMMENT__BINARY_COUNT);

		directedRelationshipEClass = createEClass(DIRECTED_RELATIONSHIP);
		createEReference(directedRelationshipEClass, DIRECTED_RELATIONSHIP__SOURCE);
		createEReference(directedRelationshipEClass, DIRECTED_RELATIONSHIP__TARGET);

		relationshipEClass = createEClass(RELATIONSHIP);
		createEReference(relationshipEClass, RELATIONSHIP__RELATED_ELEMENT);

		classEClass = createEClass(CLASS);
		createEReference(classEClass, CLASS__OWNED_OPERATION);
		createEReference(classEClass, CLASS__SUPER_CLASS);
		createEReference(classEClass, CLASS__EXTENSION);
		createEReference(classEClass, CLASS__NESTED_CLASSIFIER);
		createEAttribute(classEClass, CLASS__IS_ACTIVE);
		createEReference(classEClass, CLASS__OWNED_RECEPTION);
		createEAttribute(classEClass, CLASS__COMPONENT_KIND);

		typeEClass = createEClass(TYPE);
		createEReference(typeEClass, TYPE__PACKAGE);
		createEAttribute(typeEClass, TYPE__IS_RETIRED);

		propertyEClass = createEClass(PROPERTY);
		createEAttribute(propertyEClass, PROPERTY__DEFAULT);
		createEAttribute(propertyEClass, PROPERTY__IS_COMPOSITE);
		createEAttribute(propertyEClass, PROPERTY__IS_DERIVED);
		createEReference(propertyEClass, PROPERTY__CLASS_);
		createEReference(propertyEClass, PROPERTY__OPPOSITE);
		createEAttribute(propertyEClass, PROPERTY__IS_DERIVED_UNION);
		createEReference(propertyEClass, PROPERTY__OWNING_ASSOCIATION);
		createEReference(propertyEClass, PROPERTY__REDEFINED_PROPERTY);
		createEReference(propertyEClass, PROPERTY__SUBSETTED_PROPERTY);
		createEReference(propertyEClass, PROPERTY__DATATYPE);
		createEReference(propertyEClass, PROPERTY__ASSOCIATION);
		createEAttribute(propertyEClass, PROPERTY__AGGREGATION);
		createEReference(propertyEClass, PROPERTY__DEFAULT_VALUE);
		createEReference(propertyEClass, PROPERTY__QUALIFIER);
		createEReference(propertyEClass, PROPERTY__ASSOCIATION_END);
		createEReference(propertyEClass, PROPERTY__OWNED_ANONYMOUS_TYPE);
		createEReference(propertyEClass, PROPERTY__DEFAULT_VALUES);

		operationEClass = createEClass(OPERATION);
		createEReference(operationEClass, OPERATION__OWNED_PARAMETER);
		createEReference(operationEClass, OPERATION__CLASS_);
		createEAttribute(operationEClass, OPERATION__IS_QUERY);
		createEReference(operationEClass, OPERATION__DATATYPE);
		createEReference(operationEClass, OPERATION__PRECONDITION);
		createEReference(operationEClass, OPERATION__POSTCONDITION);
		createEReference(operationEClass, OPERATION__REDEFINED_OPERATION);
		createEReference(operationEClass, OPERATION__BODY_CONDITION);

		typedElementEClass = createEClass(TYPED_ELEMENT);
		createEReference(typedElementEClass, TYPED_ELEMENT__TYPE);

		parameterEClass = createEClass(PARAMETER);
		createEReference(parameterEClass, PARAMETER__OPERATION);
		createEAttribute(parameterEClass, PARAMETER__DEFAULT);
		createEAttribute(parameterEClass, PARAMETER__DIRECTION);
		createEReference(parameterEClass, PARAMETER__DEFAULT_VALUE);
		createEAttribute(parameterEClass, PARAMETER__IS_EXCEPTION);
		createEAttribute(parameterEClass, PARAMETER__IS_STREAM);
		createEAttribute(parameterEClass, PARAMETER__EFFECT);
		createEReference(parameterEClass, PARAMETER__PARAMETER_SET);

		packageEClass = createEClass(PACKAGE);
		createEReference(packageEClass, PACKAGE__NESTED_PACKAGE);
		createEReference(packageEClass, PACKAGE__NESTING_PACKAGE);
		createEReference(packageEClass, PACKAGE__OWNED_TYPE);
		createEReference(packageEClass, PACKAGE__OWNED_MEMBER);
		createEReference(packageEClass, PACKAGE__PACKAGE_MERGE);
		createEReference(packageEClass, PACKAGE__APPLIED_PROFILE);
		createEReference(packageEClass, PACKAGE__PACKAGE_EXTENSION);
		createEReference(packageEClass, PACKAGE__JDIAGRAM_HOLDER);
		createEReference(packageEClass, PACKAGE__CHILD_PACKAGES);
		createEReference(packageEClass, PACKAGE__PARENT_PACKAGE);
		createEAttribute(packageEClass, PACKAGE__READ_ONLY);
		createEReference(packageEClass, PACKAGE__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS);

		enumerationEClass = createEClass(ENUMERATION);
		createEReference(enumerationEClass, ENUMERATION__OWNED_LITERAL);

		dataTypeEClass = createEClass(DATA_TYPE);
		createEReference(dataTypeEClass, DATA_TYPE__OWNED_ATTRIBUTE);
		createEReference(dataTypeEClass, DATA_TYPE__OWNED_OPERATION);

		enumerationLiteralEClass = createEClass(ENUMERATION_LITERAL);
		createEReference(enumerationLiteralEClass, ENUMERATION_LITERAL__ENUMERATION);

		primitiveTypeEClass = createEClass(PRIMITIVE_TYPE);

		classifierEClass = createEClass(CLASSIFIER);
		createEReference(classifierEClass, CLASSIFIER__FEATURE);
		createEAttribute(classifierEClass, CLASSIFIER__IS_ABSTRACT);
		createEReference(classifierEClass, CLASSIFIER__INHERITED_MEMBER);
		createEReference(classifierEClass, CLASSIFIER__GENERAL);
		createEReference(classifierEClass, CLASSIFIER__GENERALIZATION);
		createEReference(classifierEClass, CLASSIFIER__ATTRIBUTE);
		createEReference(classifierEClass, CLASSIFIER__REDEFINED_CLASSIFIER);
		createEReference(classifierEClass, CLASSIFIER__SUBSTITUTION);
		createEReference(classifierEClass, CLASSIFIER__POWERTYPE_EXTENT);
		createEReference(classifierEClass, CLASSIFIER__OWNED_USE_CASE);
		createEReference(classifierEClass, CLASSIFIER__USE_CASE);
		createEReference(classifierEClass, CLASSIFIER__REPRESENTATION);
		createEReference(classifierEClass, CLASSIFIER__OCCURRENCE);

		featureEClass = createEClass(FEATURE);
		createEReference(featureEClass, FEATURE__FEATURING_CLASSIFIER);
		createEAttribute(featureEClass, FEATURE__IS_STATIC);

		constraintEClass = createEClass(CONSTRAINT);
		createEReference(constraintEClass, CONSTRAINT__CONTEXT);
		createEReference(constraintEClass, CONSTRAINT__NAMESPACE);
		createEReference(constraintEClass, CONSTRAINT__SPECIFICATION);
		createEReference(constraintEClass, CONSTRAINT__CONSTRAINED_ELEMENT);

		literalBooleanEClass = createEClass(LITERAL_BOOLEAN);
		createEAttribute(literalBooleanEClass, LITERAL_BOOLEAN__VALUE);

		literalSpecificationEClass = createEClass(LITERAL_SPECIFICATION);

		literalStringEClass = createEClass(LITERAL_STRING);
		createEAttribute(literalStringEClass, LITERAL_STRING__VALUE);

		literalNullEClass = createEClass(LITERAL_NULL);

		literalIntegerEClass = createEClass(LITERAL_INTEGER);
		createEAttribute(literalIntegerEClass, LITERAL_INTEGER__VALUE);

		literalUnlimitedNaturalEClass = createEClass(LITERAL_UNLIMITED_NATURAL);
		createEAttribute(literalUnlimitedNaturalEClass, LITERAL_UNLIMITED_NATURAL__VALUE);

		behavioralFeatureEClass = createEClass(BEHAVIORAL_FEATURE);
		createEReference(behavioralFeatureEClass, BEHAVIORAL_FEATURE__PARAMETER);
		createEReference(behavioralFeatureEClass, BEHAVIORAL_FEATURE__FORMAL_PARAMETER);
		createEReference(behavioralFeatureEClass, BEHAVIORAL_FEATURE__RETURN_RESULT);
		createEReference(behavioralFeatureEClass, BEHAVIORAL_FEATURE__RAISED_EXCEPTION);
		createEAttribute(behavioralFeatureEClass, BEHAVIORAL_FEATURE__IS_ABSTRACT);
		createEReference(behavioralFeatureEClass, BEHAVIORAL_FEATURE__METHOD);
		createEAttribute(behavioralFeatureEClass, BEHAVIORAL_FEATURE__CONCURRENCY);

		structuralFeatureEClass = createEClass(STRUCTURAL_FEATURE);
		createEAttribute(structuralFeatureEClass, STRUCTURAL_FEATURE__READ_WRITE);

		instanceSpecificationEClass = createEClass(INSTANCE_SPECIFICATION);
		createEReference(instanceSpecificationEClass, INSTANCE_SPECIFICATION__SLOT);
		createEReference(instanceSpecificationEClass, INSTANCE_SPECIFICATION__CLASSIFIER);
		createEReference(instanceSpecificationEClass, INSTANCE_SPECIFICATION__SPECIFICATION);
		createEReference(instanceSpecificationEClass, INSTANCE_SPECIFICATION__PORT_REMAP);

		slotEClass = createEClass(SLOT);
		createEReference(slotEClass, SLOT__OWNING_INSTANCE);
		createEReference(slotEClass, SLOT__VALUE);
		createEReference(slotEClass, SLOT__DEFINING_FEATURE);

		instanceValueEClass = createEClass(INSTANCE_VALUE);
		createEReference(instanceValueEClass, INSTANCE_VALUE__INSTANCE);
		createEReference(instanceValueEClass, INSTANCE_VALUE__OWNED_ANONYMOUS_INSTANCE_VALUE);

		redefinableElementEClass = createEClass(REDEFINABLE_ELEMENT);
		createEReference(redefinableElementEClass, REDEFINABLE_ELEMENT__REDEFINITION_CONTEXT);
		createEAttribute(redefinableElementEClass, REDEFINABLE_ELEMENT__IS_LEAF);

		generalizationEClass = createEClass(GENERALIZATION);
		createEReference(generalizationEClass, GENERALIZATION__SPECIFIC);
		createEReference(generalizationEClass, GENERALIZATION__GENERAL);
		createEAttribute(generalizationEClass, GENERALIZATION__IS_SUBSTITUTABLE);
		createEReference(generalizationEClass, GENERALIZATION__GENERALIZATION_SET);

		packageableElementEClass = createEClass(PACKAGEABLE_ELEMENT);
		createEAttribute(packageableElementEClass, PACKAGEABLE_ELEMENT__PACKAGEABLE_ELEMENT_VISIBILITY);

		elementImportEClass = createEClass(ELEMENT_IMPORT);
		createEAttribute(elementImportEClass, ELEMENT_IMPORT__VISIBILITY);
		createEAttribute(elementImportEClass, ELEMENT_IMPORT__ALIAS);
		createEReference(elementImportEClass, ELEMENT_IMPORT__IMPORTED_ELEMENT);
		createEReference(elementImportEClass, ELEMENT_IMPORT__IMPORTING_NAMESPACE);

		packageImportEClass = createEClass(PACKAGE_IMPORT);
		createEAttribute(packageImportEClass, PACKAGE_IMPORT__VISIBILITY);
		createEReference(packageImportEClass, PACKAGE_IMPORT__IMPORTED_PACKAGE);
		createEReference(packageImportEClass, PACKAGE_IMPORT__IMPORTING_NAMESPACE);

		associationEClass = createEClass(ASSOCIATION);
		createEAttribute(associationEClass, ASSOCIATION__IS_DERIVED);
		createEReference(associationEClass, ASSOCIATION__OWNED_END);
		createEReference(associationEClass, ASSOCIATION__END_TYPE);
		createEReference(associationEClass, ASSOCIATION__MEMBER_END);

		packageMergeEClass = createEClass(PACKAGE_MERGE);
		createEReference(packageMergeEClass, PACKAGE_MERGE__MERGING_PACKAGE);
		createEReference(packageMergeEClass, PACKAGE_MERGE__MERGED_PACKAGE);

		stereotypeEClass = createEClass(STEREOTYPE);
		createEAttribute(stereotypeEClass, STEREOTYPE__EXTENDS_META_MODEL_ELEMENT);

		profileEClass = createEClass(PROFILE);
		createEReference(profileEClass, PROFILE__OWNED_STEREOTYPE);
		createEReference(profileEClass, PROFILE__METACLASS_REFERENCE);
		createEReference(profileEClass, PROFILE__METAMODEL_REFERENCE);

		profileApplicationEClass = createEClass(PROFILE_APPLICATION);
		createEReference(profileApplicationEClass, PROFILE_APPLICATION__IMPORTED_PROFILE);

		extensionEClass = createEClass(EXTENSION);
		createEAttribute(extensionEClass, EXTENSION__IS_REQUIRED);
		createEReference(extensionEClass, EXTENSION__METACLASS);

		extensionEndEClass = createEClass(EXTENSION_END);

		behaviorEClass = createEClass(BEHAVIOR);
		createEAttribute(behaviorEClass, BEHAVIOR__IS_REENTRANT);
		createEReference(behaviorEClass, BEHAVIOR__CONTEXT);
		createEReference(behaviorEClass, BEHAVIOR__REDEFINED_BEHAVIOR);
		createEReference(behaviorEClass, BEHAVIOR__SPECIFICATION);
		createEReference(behaviorEClass, BEHAVIOR__PARAMETER);
		createEReference(behaviorEClass, BEHAVIOR__FORMAL_PARAMETER);
		createEReference(behaviorEClass, BEHAVIOR__RETURN_RESULT);
		createEReference(behaviorEClass, BEHAVIOR__PRECONDITION);
		createEReference(behaviorEClass, BEHAVIOR__POSTCONDITION);
		createEReference(behaviorEClass, BEHAVIOR__OWNED_PARAMETER_SET);

		behavioredClassifierEClass = createEClass(BEHAVIORED_CLASSIFIER);
		createEReference(behavioredClassifierEClass, BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR);
		createEReference(behavioredClassifierEClass, BEHAVIORED_CLASSIFIER__CLASSIFIER_BEHAVIOR);
		createEReference(behavioredClassifierEClass, BEHAVIORED_CLASSIFIER__IMPLEMENTATION);
		createEReference(behavioredClassifierEClass, BEHAVIORED_CLASSIFIER__OWNED_TRIGGER);
		createEReference(behavioredClassifierEClass, BEHAVIORED_CLASSIFIER__OWNED_STATE_MACHINE);

		activityEClass = createEClass(ACTIVITY);
		createEAttribute(activityEClass, ACTIVITY__BODY);
		createEAttribute(activityEClass, ACTIVITY__LANGUAGE);
		createEReference(activityEClass, ACTIVITY__EDGE);
		createEReference(activityEClass, ACTIVITY__GROUP);
		createEReference(activityEClass, ACTIVITY__NODE);
		createEReference(activityEClass, ACTIVITY__ACTION);
		createEReference(activityEClass, ACTIVITY__STRUCTURED_NODE);
		createEAttribute(activityEClass, ACTIVITY__IS_SINGLE_EXECUTION);
		createEAttribute(activityEClass, ACTIVITY__IS_READ_ONLY);

		permissionEClass = createEClass(PERMISSION);

		dependencyEClass = createEClass(DEPENDENCY);
		createEReference(dependencyEClass, DEPENDENCY__CLIENT);
		createEReference(dependencyEClass, DEPENDENCY__SUPPLIER);
		createEReference(dependencyEClass, DEPENDENCY__DEPENDENCY_TARGET);
		createEAttribute(dependencyEClass, DEPENDENCY__RESEMBLANCE);
		createEAttribute(dependencyEClass, DEPENDENCY__REPLACEMENT);
		createEAttribute(dependencyEClass, DEPENDENCY__TRACE);

		usageEClass = createEClass(USAGE);

		abstractionEClass = createEClass(ABSTRACTION);
		createEReference(abstractionEClass, ABSTRACTION__MAPPING);

		realizationEClass = createEClass(REALIZATION);
		createEReference(realizationEClass, REALIZATION__ABSTRACTION);
		createEReference(realizationEClass, REALIZATION__REALIZING_CLASSIFIER);

		substitutionEClass = createEClass(SUBSTITUTION);
		createEReference(substitutionEClass, SUBSTITUTION__CONTRACT);
		createEReference(substitutionEClass, SUBSTITUTION__SUBSTITUTING_CLASSIFIER);

		generalizationSetEClass = createEClass(GENERALIZATION_SET);
		createEAttribute(generalizationSetEClass, GENERALIZATION_SET__IS_COVERING);
		createEAttribute(generalizationSetEClass, GENERALIZATION_SET__IS_DISJOINT);
		createEReference(generalizationSetEClass, GENERALIZATION_SET__POWERTYPE);
		createEReference(generalizationSetEClass, GENERALIZATION_SET__GENERALIZATION);

		associationClassEClass = createEClass(ASSOCIATION_CLASS);

		informationItemEClass = createEClass(INFORMATION_ITEM);
		createEReference(informationItemEClass, INFORMATION_ITEM__REPRESENTED);

		informationFlowEClass = createEClass(INFORMATION_FLOW);
		createEReference(informationFlowEClass, INFORMATION_FLOW__REALIZATION);
		createEReference(informationFlowEClass, INFORMATION_FLOW__CONVEYED);

		modelEClass = createEClass(MODEL);
		createEAttribute(modelEClass, MODEL__VIEWPOINT);

		connectorEndEClass = createEClass(CONNECTOR_END);
		createEReference(connectorEndEClass, CONNECTOR_END__DEFINING_END);
		createEReference(connectorEndEClass, CONNECTOR_END__ROLE);
		createEReference(connectorEndEClass, CONNECTOR_END__PART_WITH_PORT);

		connectableElementEClass = createEClass(CONNECTABLE_ELEMENT);
		createEReference(connectableElementEClass, CONNECTABLE_ELEMENT__END);

		connectorEClass = createEClass(CONNECTOR);
		createEReference(connectorEClass, CONNECTOR__TYPE);
		createEReference(connectorEClass, CONNECTOR__REDEFINED_CONNECTOR);
		createEReference(connectorEClass, CONNECTOR__END);
		createEAttribute(connectorEClass, CONNECTOR__KIND);
		createEReference(connectorEClass, CONNECTOR__CONTRACT);

		structuredClassifierEClass = createEClass(STRUCTURED_CLASSIFIER);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__PART);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__ROLE);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__OWNED_CONNECTOR);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES);
		createEReference(structuredClassifierEClass, STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES);

		activityEdgeEClass = createEClass(ACTIVITY_EDGE);
		createEReference(activityEdgeEClass, ACTIVITY_EDGE__ACTIVITY);
		createEReference(activityEdgeEClass, ACTIVITY_EDGE__SOURCE);
		createEReference(activityEdgeEClass, ACTIVITY_EDGE__TARGET);
		createEReference(activityEdgeEClass, ACTIVITY_EDGE__IN_GROUP);
		createEReference(activityEdgeEClass, ACTIVITY_EDGE__GUARD);
		createEReference(activityEdgeEClass, ACTIVITY_EDGE__REDEFINED_ELEMENT);
		createEReference(activityEdgeEClass, ACTIVITY_EDGE__IN_STRUCTURED_NODE);
		createEReference(activityEdgeEClass, ACTIVITY_EDGE__IN_PARTITION);
		createEReference(activityEdgeEClass, ACTIVITY_EDGE__WEIGHT);
		createEReference(activityEdgeEClass, ACTIVITY_EDGE__INTERRUPTS);

		activityGroupEClass = createEClass(ACTIVITY_GROUP);
		createEReference(activityGroupEClass, ACTIVITY_GROUP__SUPER_GROUP);
		createEReference(activityGroupEClass, ACTIVITY_GROUP__ACTIVITY_GROUP_ACTIVITY);

		activityNodeEClass = createEClass(ACTIVITY_NODE);
		createEReference(activityNodeEClass, ACTIVITY_NODE__OUTGOING);
		createEReference(activityNodeEClass, ACTIVITY_NODE__INCOMING);
		createEReference(activityNodeEClass, ACTIVITY_NODE__IN_GROUP);
		createEReference(activityNodeEClass, ACTIVITY_NODE__ACTIVITY);
		createEReference(activityNodeEClass, ACTIVITY_NODE__REDEFINED_ELEMENT);
		createEReference(activityNodeEClass, ACTIVITY_NODE__IN_STRUCTURED_NODE);
		createEReference(activityNodeEClass, ACTIVITY_NODE__IN_PARTITION);
		createEReference(activityNodeEClass, ACTIVITY_NODE__IN_INTERRUPTIBLE_REGION);

		actionEClass = createEClass(ACTION);
		createEAttribute(actionEClass, ACTION__EFFECT);
		createEReference(actionEClass, ACTION__OUTPUT);
		createEReference(actionEClass, ACTION__INPUT);
		createEReference(actionEClass, ACTION__CONTEXT);
		createEReference(actionEClass, ACTION__LOCAL_PRECONDITION);
		createEReference(actionEClass, ACTION__LOCAL_POSTCONDITION);

		objectNodeEClass = createEClass(OBJECT_NODE);
		createEAttribute(objectNodeEClass, OBJECT_NODE__ORDERING);
		createEReference(objectNodeEClass, OBJECT_NODE__UPPER_BOUND);
		createEReference(objectNodeEClass, OBJECT_NODE__IN_STATE);
		createEReference(objectNodeEClass, OBJECT_NODE__SELECTION);

		controlNodeEClass = createEClass(CONTROL_NODE);

		controlFlowEClass = createEClass(CONTROL_FLOW);

		objectFlowEClass = createEClass(OBJECT_FLOW);
		createEAttribute(objectFlowEClass, OBJECT_FLOW__IS_MULTICAST);
		createEAttribute(objectFlowEClass, OBJECT_FLOW__IS_MULTIRECEIVE);
		createEReference(objectFlowEClass, OBJECT_FLOW__TRANSFORMATION);
		createEReference(objectFlowEClass, OBJECT_FLOW__SELECTION);

		initialNodeEClass = createEClass(INITIAL_NODE);

		finalNodeEClass = createEClass(FINAL_NODE);

		activityFinalNodeEClass = createEClass(ACTIVITY_FINAL_NODE);

		decisionNodeEClass = createEClass(DECISION_NODE);
		createEReference(decisionNodeEClass, DECISION_NODE__DECISION_INPUT);

		mergeNodeEClass = createEClass(MERGE_NODE);

		executableNodeEClass = createEClass(EXECUTABLE_NODE);
		createEReference(executableNodeEClass, EXECUTABLE_NODE__HANDLER);

		outputPinEClass = createEClass(OUTPUT_PIN);

		inputPinEClass = createEClass(INPUT_PIN);

		pinEClass = createEClass(PIN);

		activityParameterNodeEClass = createEClass(ACTIVITY_PARAMETER_NODE);
		createEReference(activityParameterNodeEClass, ACTIVITY_PARAMETER_NODE__PARAMETER);

		valuePinEClass = createEClass(VALUE_PIN);
		createEReference(valuePinEClass, VALUE_PIN__VALUE);

		interfaceEClass = createEClass(INTERFACE);
		createEReference(interfaceEClass, INTERFACE__OWNED_ATTRIBUTE);
		createEReference(interfaceEClass, INTERFACE__OWNED_OPERATION);
		createEReference(interfaceEClass, INTERFACE__REDEFINED_INTERFACE);
		createEReference(interfaceEClass, INTERFACE__NESTED_CLASSIFIER);
		createEReference(interfaceEClass, INTERFACE__OWNED_RECEPTION);
		createEReference(interfaceEClass, INTERFACE__PROTOCOL);
		createEReference(interfaceEClass, INTERFACE__DELTA_DELETED_OPERATIONS);
		createEReference(interfaceEClass, INTERFACE__DELTA_REPLACED_OPERATIONS);
		createEReference(interfaceEClass, INTERFACE__DELTA_DELETED_ATTRIBUTES);
		createEReference(interfaceEClass, INTERFACE__DELTA_REPLACED_ATTRIBUTES);

		implementationEClass = createEClass(IMPLEMENTATION);
		createEReference(implementationEClass, IMPLEMENTATION__CONTRACT);
		createEReference(implementationEClass, IMPLEMENTATION__IMPLEMENTING_CLASSIFIER);

		artifactEClass = createEClass(ARTIFACT);
		createEAttribute(artifactEClass, ARTIFACT__FILE_NAME);
		createEReference(artifactEClass, ARTIFACT__NESTED_ARTIFACT);
		createEReference(artifactEClass, ARTIFACT__MANIFESTATION);
		createEReference(artifactEClass, ARTIFACT__OWNED_OPERATION);
		createEReference(artifactEClass, ARTIFACT__OWNED_ATTRIBUTE);

		manifestationEClass = createEClass(MANIFESTATION);
		createEReference(manifestationEClass, MANIFESTATION__UTILIZED_ELEMENT);

		actorEClass = createEClass(ACTOR);

		extendEClass = createEClass(EXTEND);
		createEReference(extendEClass, EXTEND__EXTENDED_CASE);
		createEReference(extendEClass, EXTEND__EXTENSION);
		createEReference(extendEClass, EXTEND__CONDITION);
		createEReference(extendEClass, EXTEND__EXTENSION_LOCATION);

		useCaseEClass = createEClass(USE_CASE);
		createEReference(useCaseEClass, USE_CASE__INCLUDE);
		createEReference(useCaseEClass, USE_CASE__EXTEND);
		createEReference(useCaseEClass, USE_CASE__EXTENSION_POINT);
		createEReference(useCaseEClass, USE_CASE__SUBJECT);

		extensionPointEClass = createEClass(EXTENSION_POINT);
		createEReference(extensionPointEClass, EXTENSION_POINT__USE_CASE);

		includeEClass = createEClass(INCLUDE);
		createEReference(includeEClass, INCLUDE__INCLUDING_CASE);
		createEReference(includeEClass, INCLUDE__ADDITION);

		collaborationOccurrenceEClass = createEClass(COLLABORATION_OCCURRENCE);
		createEReference(collaborationOccurrenceEClass, COLLABORATION_OCCURRENCE__TYPE);
		createEReference(collaborationOccurrenceEClass, COLLABORATION_OCCURRENCE__ROLE_BINDING);

		collaborationEClass = createEClass(COLLABORATION);
		createEReference(collaborationEClass, COLLABORATION__COLLABORATION_ROLE);

		portEClass = createEClass(PORT);
		createEAttribute(portEClass, PORT__IS_BEHAVIOR);
		createEAttribute(portEClass, PORT__IS_SERVICE);
		createEReference(portEClass, PORT__REQUIRED);
		createEReference(portEClass, PORT__REDEFINED_PORT);
		createEReference(portEClass, PORT__PROVIDED);
		createEReference(portEClass, PORT__PROTOCOL);
		createEAttribute(portEClass, PORT__KIND);

		encapsulatedClassifierEClass = createEClass(ENCAPSULATED_CLASSIFIER);
		createEReference(encapsulatedClassifierEClass, ENCAPSULATED_CLASSIFIER__OWNED_PORT);

		callTriggerEClass = createEClass(CALL_TRIGGER);
		createEReference(callTriggerEClass, CALL_TRIGGER__OPERATION);

		messageTriggerEClass = createEClass(MESSAGE_TRIGGER);

		changeTriggerEClass = createEClass(CHANGE_TRIGGER);
		createEReference(changeTriggerEClass, CHANGE_TRIGGER__CHANGE_EXPRESSION);

		triggerEClass = createEClass(TRIGGER);
		createEReference(triggerEClass, TRIGGER__PORT);

		receptionEClass = createEClass(RECEPTION);
		createEReference(receptionEClass, RECEPTION__SIGNAL);

		signalEClass = createEClass(SIGNAL);
		createEReference(signalEClass, SIGNAL__OWNED_ATTRIBUTE);

		signalTriggerEClass = createEClass(SIGNAL_TRIGGER);
		createEReference(signalTriggerEClass, SIGNAL_TRIGGER__SIGNAL);

		timeTriggerEClass = createEClass(TIME_TRIGGER);
		createEAttribute(timeTriggerEClass, TIME_TRIGGER__IS_RELATIVE);
		createEReference(timeTriggerEClass, TIME_TRIGGER__WHEN);

		anyTriggerEClass = createEClass(ANY_TRIGGER);

		variableEClass = createEClass(VARIABLE);
		createEReference(variableEClass, VARIABLE__SCOPE);

		structuredActivityNodeEClass = createEClass(STRUCTURED_ACTIVITY_NODE);
		createEReference(structuredActivityNodeEClass, STRUCTURED_ACTIVITY_NODE__VARIABLE);
		createEReference(structuredActivityNodeEClass, STRUCTURED_ACTIVITY_NODE__CONTAINED_NODE);
		createEReference(structuredActivityNodeEClass, STRUCTURED_ACTIVITY_NODE__CONTAINED_EDGE);
		createEAttribute(structuredActivityNodeEClass, STRUCTURED_ACTIVITY_NODE__MUST_ISOLATE);

		conditionalNodeEClass = createEClass(CONDITIONAL_NODE);
		createEAttribute(conditionalNodeEClass, CONDITIONAL_NODE__IS_DETERMINATE);
		createEAttribute(conditionalNodeEClass, CONDITIONAL_NODE__IS_ASSURED);
		createEReference(conditionalNodeEClass, CONDITIONAL_NODE__CLAUSE);
		createEReference(conditionalNodeEClass, CONDITIONAL_NODE__RESULT);

		clauseEClass = createEClass(CLAUSE);
		createEReference(clauseEClass, CLAUSE__TEST);
		createEReference(clauseEClass, CLAUSE__BODY);
		createEReference(clauseEClass, CLAUSE__PREDECESSOR_CLAUSE);
		createEReference(clauseEClass, CLAUSE__SUCCESSOR_CLAUSE);
		createEReference(clauseEClass, CLAUSE__DECIDER);
		createEReference(clauseEClass, CLAUSE__BODY_OUTPUT);

		loopNodeEClass = createEClass(LOOP_NODE);
		createEAttribute(loopNodeEClass, LOOP_NODE__IS_TESTED_FIRST);
		createEReference(loopNodeEClass, LOOP_NODE__BODY_PART);
		createEReference(loopNodeEClass, LOOP_NODE__SETUP_PART);
		createEReference(loopNodeEClass, LOOP_NODE__DECIDER);
		createEReference(loopNodeEClass, LOOP_NODE__TEST);
		createEReference(loopNodeEClass, LOOP_NODE__RESULT);
		createEReference(loopNodeEClass, LOOP_NODE__LOOP_VARIABLE);
		createEReference(loopNodeEClass, LOOP_NODE__BODY_OUTPUT);
		createEReference(loopNodeEClass, LOOP_NODE__LOOP_VARIABLE_INPUT);

		interactionEClass = createEClass(INTERACTION);
		createEReference(interactionEClass, INTERACTION__LIFELINE);
		createEReference(interactionEClass, INTERACTION__MESSAGE);
		createEReference(interactionEClass, INTERACTION__FRAGMENT);
		createEReference(interactionEClass, INTERACTION__FORMAL_GATE);

		interactionFragmentEClass = createEClass(INTERACTION_FRAGMENT);
		createEReference(interactionFragmentEClass, INTERACTION_FRAGMENT__COVERED);
		createEReference(interactionFragmentEClass, INTERACTION_FRAGMENT__GENERAL_ORDERING);
		createEReference(interactionFragmentEClass, INTERACTION_FRAGMENT__ENCLOSING_INTERACTION);
		createEReference(interactionFragmentEClass, INTERACTION_FRAGMENT__ENCLOSING_OPERAND);

		lifelineEClass = createEClass(LIFELINE);
		createEReference(lifelineEClass, LIFELINE__COVERED_BY);
		createEReference(lifelineEClass, LIFELINE__REPRESENTS);
		createEReference(lifelineEClass, LIFELINE__INTERACTION);
		createEReference(lifelineEClass, LIFELINE__SELECTOR);
		createEReference(lifelineEClass, LIFELINE__DECOMPOSED_AS);

		messageEClass = createEClass(MESSAGE);
		createEAttribute(messageEClass, MESSAGE__MESSAGE_KIND);
		createEAttribute(messageEClass, MESSAGE__MESSAGE_SORT);
		createEReference(messageEClass, MESSAGE__RECEIVE_EVENT);
		createEReference(messageEClass, MESSAGE__SEND_EVENT);
		createEReference(messageEClass, MESSAGE__CONNECTOR);
		createEReference(messageEClass, MESSAGE__INTERACTION);
		createEReference(messageEClass, MESSAGE__SIGNATURE);
		createEReference(messageEClass, MESSAGE__ARGUMENT);

		generalOrderingEClass = createEClass(GENERAL_ORDERING);
		createEReference(generalOrderingEClass, GENERAL_ORDERING__BEFORE);
		createEReference(generalOrderingEClass, GENERAL_ORDERING__AFTER);

		messageEndEClass = createEClass(MESSAGE_END);
		createEReference(messageEndEClass, MESSAGE_END__RECEIVE_MESSAGE);
		createEReference(messageEndEClass, MESSAGE_END__SEND_MESSAGE);

		eventOccurrenceEClass = createEClass(EVENT_OCCURRENCE);
		createEReference(eventOccurrenceEClass, EVENT_OCCURRENCE__START_EXEC);
		createEReference(eventOccurrenceEClass, EVENT_OCCURRENCE__FINISH_EXEC);
		createEReference(eventOccurrenceEClass, EVENT_OCCURRENCE__TO_AFTER);
		createEReference(eventOccurrenceEClass, EVENT_OCCURRENCE__TO_BEFORE);

		executionOccurrenceEClass = createEClass(EXECUTION_OCCURRENCE);
		createEReference(executionOccurrenceEClass, EXECUTION_OCCURRENCE__START);
		createEReference(executionOccurrenceEClass, EXECUTION_OCCURRENCE__FINISH);
		createEReference(executionOccurrenceEClass, EXECUTION_OCCURRENCE__BEHAVIOR);

		stateInvariantEClass = createEClass(STATE_INVARIANT);
		createEReference(stateInvariantEClass, STATE_INVARIANT__INVARIANT);

		stopEClass = createEClass(STOP);

		templateSignatureEClass = createEClass(TEMPLATE_SIGNATURE);
		createEReference(templateSignatureEClass, TEMPLATE_SIGNATURE__PARAMETER);
		createEReference(templateSignatureEClass, TEMPLATE_SIGNATURE__OWNED_PARAMETER);
		createEReference(templateSignatureEClass, TEMPLATE_SIGNATURE__NESTED_SIGNATURE);
		createEReference(templateSignatureEClass, TEMPLATE_SIGNATURE__NESTING_SIGNATURE);
		createEReference(templateSignatureEClass, TEMPLATE_SIGNATURE__TEMPLATE);

		templateParameterEClass = createEClass(TEMPLATE_PARAMETER);
		createEReference(templateParameterEClass, TEMPLATE_PARAMETER__SIGNATURE);
		createEReference(templateParameterEClass, TEMPLATE_PARAMETER__PARAMETERED_ELEMENT);
		createEReference(templateParameterEClass, TEMPLATE_PARAMETER__OWNED_PARAMETERED_ELEMENT);
		createEReference(templateParameterEClass, TEMPLATE_PARAMETER__DEFAULT);
		createEReference(templateParameterEClass, TEMPLATE_PARAMETER__OWNED_DEFAULT);

		templateableElementEClass = createEClass(TEMPLATEABLE_ELEMENT);
		createEReference(templateableElementEClass, TEMPLATEABLE_ELEMENT__TEMPLATE_BINDING);
		createEReference(templateableElementEClass, TEMPLATEABLE_ELEMENT__OWNED_TEMPLATE_SIGNATURE);

		stringExpressionEClass = createEClass(STRING_EXPRESSION);
		createEReference(stringExpressionEClass, STRING_EXPRESSION__SUB_EXPRESSION);
		createEReference(stringExpressionEClass, STRING_EXPRESSION__OWNING_EXPRESSION);

		parameterableElementEClass = createEClass(PARAMETERABLE_ELEMENT);
		createEReference(parameterableElementEClass, PARAMETERABLE_ELEMENT__TEMPLATE_PARAMETER);
		createEReference(parameterableElementEClass, PARAMETERABLE_ELEMENT__OWNING_PARAMETER);

		templateBindingEClass = createEClass(TEMPLATE_BINDING);
		createEReference(templateBindingEClass, TEMPLATE_BINDING__BOUND_ELEMENT);
		createEReference(templateBindingEClass, TEMPLATE_BINDING__SIGNATURE);
		createEReference(templateBindingEClass, TEMPLATE_BINDING__PARAMETER_SUBSTITUTION);

		templateParameterSubstitutionEClass = createEClass(TEMPLATE_PARAMETER_SUBSTITUTION);
		createEReference(templateParameterSubstitutionEClass, TEMPLATE_PARAMETER_SUBSTITUTION__FORMAL);
		createEReference(templateParameterSubstitutionEClass, TEMPLATE_PARAMETER_SUBSTITUTION__TEMPLATE_BINDING);
		createEReference(templateParameterSubstitutionEClass, TEMPLATE_PARAMETER_SUBSTITUTION__ACTUAL);
		createEReference(templateParameterSubstitutionEClass, TEMPLATE_PARAMETER_SUBSTITUTION__OWNED_ACTUAL);

		operationTemplateParameterEClass = createEClass(OPERATION_TEMPLATE_PARAMETER);

		classifierTemplateParameterEClass = createEClass(CLASSIFIER_TEMPLATE_PARAMETER);
		createEAttribute(classifierTemplateParameterEClass, CLASSIFIER_TEMPLATE_PARAMETER__ALLOW_SUBSTITUTABLE);

		parameterableClassifierEClass = createEClass(PARAMETERABLE_CLASSIFIER);

		redefinableTemplateSignatureEClass = createEClass(REDEFINABLE_TEMPLATE_SIGNATURE);

		templateableClassifierEClass = createEClass(TEMPLATEABLE_CLASSIFIER);

		connectableElementTemplateParameterEClass = createEClass(CONNECTABLE_ELEMENT_TEMPLATE_PARAMETER);

		forkNodeEClass = createEClass(FORK_NODE);

		joinNodeEClass = createEClass(JOIN_NODE);
		createEAttribute(joinNodeEClass, JOIN_NODE__IS_COMBINE_DUPLICATE);
		createEReference(joinNodeEClass, JOIN_NODE__JOIN_SPEC);

		flowFinalNodeEClass = createEClass(FLOW_FINAL_NODE);

		centralBufferNodeEClass = createEClass(CENTRAL_BUFFER_NODE);

		activityPartitionEClass = createEClass(ACTIVITY_PARTITION);
		createEAttribute(activityPartitionEClass, ACTIVITY_PARTITION__IS_DIMENSION);
		createEAttribute(activityPartitionEClass, ACTIVITY_PARTITION__IS_EXTERNAL);
		createEReference(activityPartitionEClass, ACTIVITY_PARTITION__CONTAINED_EDGE);
		createEReference(activityPartitionEClass, ACTIVITY_PARTITION__CONTAINED_NODE);
		createEReference(activityPartitionEClass, ACTIVITY_PARTITION__SUBGROUP);
		createEReference(activityPartitionEClass, ACTIVITY_PARTITION__SUPER_PARTITION);
		createEReference(activityPartitionEClass, ACTIVITY_PARTITION__REPRESENTS);

		expansionNodeEClass = createEClass(EXPANSION_NODE);
		createEReference(expansionNodeEClass, EXPANSION_NODE__REGION_AS_OUTPUT);
		createEReference(expansionNodeEClass, EXPANSION_NODE__REGION_AS_INPUT);

		expansionRegionEClass = createEClass(EXPANSION_REGION);
		createEAttribute(expansionRegionEClass, EXPANSION_REGION__MODE);
		createEReference(expansionRegionEClass, EXPANSION_REGION__OUTPUT_ELEMENT);
		createEReference(expansionRegionEClass, EXPANSION_REGION__INPUT_ELEMENT);

		exceptionHandlerEClass = createEClass(EXCEPTION_HANDLER);
		createEReference(exceptionHandlerEClass, EXCEPTION_HANDLER__PROTECTED_NODE);
		createEReference(exceptionHandlerEClass, EXCEPTION_HANDLER__HANDLER_BODY);
		createEReference(exceptionHandlerEClass, EXCEPTION_HANDLER__EXCEPTION_INPUT);
		createEReference(exceptionHandlerEClass, EXCEPTION_HANDLER__EXCEPTION_TYPE);

		interactionOccurrenceEClass = createEClass(INTERACTION_OCCURRENCE);
		createEReference(interactionOccurrenceEClass, INTERACTION_OCCURRENCE__REFERS_TO);
		createEReference(interactionOccurrenceEClass, INTERACTION_OCCURRENCE__ACTUAL_GATE);
		createEReference(interactionOccurrenceEClass, INTERACTION_OCCURRENCE__ARGUMENT);

		gateEClass = createEClass(GATE);

		partDecompositionEClass = createEClass(PART_DECOMPOSITION);

		interactionOperandEClass = createEClass(INTERACTION_OPERAND);
		createEReference(interactionOperandEClass, INTERACTION_OPERAND__GUARD);
		createEReference(interactionOperandEClass, INTERACTION_OPERAND__FRAGMENT);

		interactionConstraintEClass = createEClass(INTERACTION_CONSTRAINT);
		createEReference(interactionConstraintEClass, INTERACTION_CONSTRAINT__MININT);
		createEReference(interactionConstraintEClass, INTERACTION_CONSTRAINT__MAXINT);

		combinedFragmentEClass = createEClass(COMBINED_FRAGMENT);
		createEAttribute(combinedFragmentEClass, COMBINED_FRAGMENT__INTERACTION_OPERATOR);
		createEReference(combinedFragmentEClass, COMBINED_FRAGMENT__OPERAND);
		createEReference(combinedFragmentEClass, COMBINED_FRAGMENT__CFRAGMENT_GATE);

		continuationEClass = createEClass(CONTINUATION);
		createEAttribute(continuationEClass, CONTINUATION__SETTING);

		stateMachineEClass = createEClass(STATE_MACHINE);
		createEReference(stateMachineEClass, STATE_MACHINE__REGION);
		createEReference(stateMachineEClass, STATE_MACHINE__CONNECTION_POINT);
		createEReference(stateMachineEClass, STATE_MACHINE__EXTENDED_STATE_MACHINE);
		createEReference(stateMachineEClass, STATE_MACHINE__STATE_MACHINE_REDEFINITION_CONTEXT);

		regionEClass = createEClass(REGION);
		createEReference(regionEClass, REGION__SUBVERTEX);
		createEReference(regionEClass, REGION__TRANSITION);
		createEReference(regionEClass, REGION__STATE_MACHINE);
		createEReference(regionEClass, REGION__STATE);
		createEReference(regionEClass, REGION__EXTENDED_REGION);

		pseudostateEClass = createEClass(PSEUDOSTATE);
		createEAttribute(pseudostateEClass, PSEUDOSTATE__KIND);

		stateEClass = createEClass(STATE);
		createEAttribute(stateEClass, STATE__IS_COMPOSITE);
		createEAttribute(stateEClass, STATE__IS_ORTHOGONAL);
		createEAttribute(stateEClass, STATE__IS_SIMPLE);
		createEAttribute(stateEClass, STATE__IS_SUBMACHINE_STATE);
		createEReference(stateEClass, STATE__SUBMACHINE);
		createEReference(stateEClass, STATE__CONNECTION);
		createEReference(stateEClass, STATE__REDEFINED_STATE);
		createEReference(stateEClass, STATE__DEFERRABLE_TRIGGER);
		createEReference(stateEClass, STATE__REGION);
		createEReference(stateEClass, STATE__ENTRY);
		createEReference(stateEClass, STATE__EXIT);
		createEReference(stateEClass, STATE__DO_ACTIVITY);
		createEReference(stateEClass, STATE__STATE_INVARIANT);

		vertexEClass = createEClass(VERTEX);
		createEReference(vertexEClass, VERTEX__CONTAINER);
		createEReference(vertexEClass, VERTEX__OUTGOING);
		createEReference(vertexEClass, VERTEX__INCOMING);

		connectionPointReferenceEClass = createEClass(CONNECTION_POINT_REFERENCE);
		createEReference(connectionPointReferenceEClass, CONNECTION_POINT_REFERENCE__ENTRY);
		createEReference(connectionPointReferenceEClass, CONNECTION_POINT_REFERENCE__EXIT);

		transitionEClass = createEClass(TRANSITION);
		createEAttribute(transitionEClass, TRANSITION__KIND);
		createEReference(transitionEClass, TRANSITION__CONTAINER);
		createEReference(transitionEClass, TRANSITION__SOURCE);
		createEReference(transitionEClass, TRANSITION__TARGET);
		createEReference(transitionEClass, TRANSITION__REDEFINED_TRANSITION);
		createEReference(transitionEClass, TRANSITION__TRIGGER);
		createEReference(transitionEClass, TRANSITION__GUARD);
		createEReference(transitionEClass, TRANSITION__EFFECT);

		finalStateEClass = createEClass(FINAL_STATE);

		createObjectActionEClass = createEClass(CREATE_OBJECT_ACTION);
		createEReference(createObjectActionEClass, CREATE_OBJECT_ACTION__CLASSIFIER);
		createEReference(createObjectActionEClass, CREATE_OBJECT_ACTION__RESULT);

		destroyObjectActionEClass = createEClass(DESTROY_OBJECT_ACTION);
		createEAttribute(destroyObjectActionEClass, DESTROY_OBJECT_ACTION__IS_DESTROY_LINKS);
		createEAttribute(destroyObjectActionEClass, DESTROY_OBJECT_ACTION__IS_DESTROY_OWNED_OBJECTS);
		createEReference(destroyObjectActionEClass, DESTROY_OBJECT_ACTION__TARGET);

		testIdentityActionEClass = createEClass(TEST_IDENTITY_ACTION);
		createEReference(testIdentityActionEClass, TEST_IDENTITY_ACTION__FIRST);
		createEReference(testIdentityActionEClass, TEST_IDENTITY_ACTION__SECOND);
		createEReference(testIdentityActionEClass, TEST_IDENTITY_ACTION__RESULT);

		readSelfActionEClass = createEClass(READ_SELF_ACTION);
		createEReference(readSelfActionEClass, READ_SELF_ACTION__RESULT);

		structuralFeatureActionEClass = createEClass(STRUCTURAL_FEATURE_ACTION);
		createEReference(structuralFeatureActionEClass, STRUCTURAL_FEATURE_ACTION__STRUCTURAL_FEATURE);
		createEReference(structuralFeatureActionEClass, STRUCTURAL_FEATURE_ACTION__OBJECT);

		readStructuralFeatureActionEClass = createEClass(READ_STRUCTURAL_FEATURE_ACTION);
		createEReference(readStructuralFeatureActionEClass, READ_STRUCTURAL_FEATURE_ACTION__RESULT);

		writeStructuralFeatureActionEClass = createEClass(WRITE_STRUCTURAL_FEATURE_ACTION);
		createEReference(writeStructuralFeatureActionEClass, WRITE_STRUCTURAL_FEATURE_ACTION__VALUE);

		clearStructuralFeatureActionEClass = createEClass(CLEAR_STRUCTURAL_FEATURE_ACTION);

		removeStructuralFeatureValueActionEClass = createEClass(REMOVE_STRUCTURAL_FEATURE_VALUE_ACTION);

		addStructuralFeatureValueActionEClass = createEClass(ADD_STRUCTURAL_FEATURE_VALUE_ACTION);
		createEAttribute(addStructuralFeatureValueActionEClass, ADD_STRUCTURAL_FEATURE_VALUE_ACTION__IS_REPLACE_ALL);
		createEReference(addStructuralFeatureValueActionEClass, ADD_STRUCTURAL_FEATURE_VALUE_ACTION__INSERT_AT);

		linkActionEClass = createEClass(LINK_ACTION);
		createEReference(linkActionEClass, LINK_ACTION__END_DATA);

		linkEndDataEClass = createEClass(LINK_END_DATA);
		createEReference(linkEndDataEClass, LINK_END_DATA__VALUE);
		createEReference(linkEndDataEClass, LINK_END_DATA__END);
		createEReference(linkEndDataEClass, LINK_END_DATA__QUALIFIER);

		readLinkActionEClass = createEClass(READ_LINK_ACTION);
		createEReference(readLinkActionEClass, READ_LINK_ACTION__RESULT);

		linkEndCreationDataEClass = createEClass(LINK_END_CREATION_DATA);
		createEAttribute(linkEndCreationDataEClass, LINK_END_CREATION_DATA__IS_REPLACE_ALL);
		createEReference(linkEndCreationDataEClass, LINK_END_CREATION_DATA__INSERT_AT);

		createLinkActionEClass = createEClass(CREATE_LINK_ACTION);

		writeLinkActionEClass = createEClass(WRITE_LINK_ACTION);

		destroyLinkActionEClass = createEClass(DESTROY_LINK_ACTION);

		clearAssociationActionEClass = createEClass(CLEAR_ASSOCIATION_ACTION);
		createEReference(clearAssociationActionEClass, CLEAR_ASSOCIATION_ACTION__OBJECT);
		createEReference(clearAssociationActionEClass, CLEAR_ASSOCIATION_ACTION__ASSOCIATION);

		variableActionEClass = createEClass(VARIABLE_ACTION);
		createEReference(variableActionEClass, VARIABLE_ACTION__VARIABLE);

		readVariableActionEClass = createEClass(READ_VARIABLE_ACTION);
		createEReference(readVariableActionEClass, READ_VARIABLE_ACTION__RESULT);

		writeVariableActionEClass = createEClass(WRITE_VARIABLE_ACTION);
		createEReference(writeVariableActionEClass, WRITE_VARIABLE_ACTION__VALUE);

		clearVariableActionEClass = createEClass(CLEAR_VARIABLE_ACTION);

		addVariableValueActionEClass = createEClass(ADD_VARIABLE_VALUE_ACTION);
		createEAttribute(addVariableValueActionEClass, ADD_VARIABLE_VALUE_ACTION__IS_REPLACE_ALL);
		createEReference(addVariableValueActionEClass, ADD_VARIABLE_VALUE_ACTION__INSERT_AT);

		removeVariableValueActionEClass = createEClass(REMOVE_VARIABLE_VALUE_ACTION);

		applyFunctionActionEClass = createEClass(APPLY_FUNCTION_ACTION);
		createEReference(applyFunctionActionEClass, APPLY_FUNCTION_ACTION__FUNCTION);
		createEReference(applyFunctionActionEClass, APPLY_FUNCTION_ACTION__ARGUMENT);
		createEReference(applyFunctionActionEClass, APPLY_FUNCTION_ACTION__RESULT);

		primitiveFunctionEClass = createEClass(PRIMITIVE_FUNCTION);
		createEAttribute(primitiveFunctionEClass, PRIMITIVE_FUNCTION__BODY);
		createEAttribute(primitiveFunctionEClass, PRIMITIVE_FUNCTION__LANGUAGE);

		callActionEClass = createEClass(CALL_ACTION);
		createEAttribute(callActionEClass, CALL_ACTION__IS_SYNCHRONOUS);
		createEReference(callActionEClass, CALL_ACTION__RESULT);

		invocationActionEClass = createEClass(INVOCATION_ACTION);
		createEReference(invocationActionEClass, INVOCATION_ACTION__ARGUMENT);
		createEReference(invocationActionEClass, INVOCATION_ACTION__ON_PORT);

		sendSignalActionEClass = createEClass(SEND_SIGNAL_ACTION);
		createEReference(sendSignalActionEClass, SEND_SIGNAL_ACTION__TARGET);
		createEReference(sendSignalActionEClass, SEND_SIGNAL_ACTION__SIGNAL);

		broadcastSignalActionEClass = createEClass(BROADCAST_SIGNAL_ACTION);
		createEReference(broadcastSignalActionEClass, BROADCAST_SIGNAL_ACTION__SIGNAL);

		sendObjectActionEClass = createEClass(SEND_OBJECT_ACTION);
		createEReference(sendObjectActionEClass, SEND_OBJECT_ACTION__TARGET);
		createEReference(sendObjectActionEClass, SEND_OBJECT_ACTION__REQUEST);

		callOperationActionEClass = createEClass(CALL_OPERATION_ACTION);
		createEReference(callOperationActionEClass, CALL_OPERATION_ACTION__OPERATION);
		createEReference(callOperationActionEClass, CALL_OPERATION_ACTION__TARGET);

		callBehaviorActionEClass = createEClass(CALL_BEHAVIOR_ACTION);
		createEReference(callBehaviorActionEClass, CALL_BEHAVIOR_ACTION__BEHAVIOR);

		timeExpressionEClass = createEClass(TIME_EXPRESSION);
		createEAttribute(timeExpressionEClass, TIME_EXPRESSION__FIRST_TIME);
		createEReference(timeExpressionEClass, TIME_EXPRESSION__EVENT);

		durationEClass = createEClass(DURATION);
		createEAttribute(durationEClass, DURATION__FIRST_TIME);
		createEReference(durationEClass, DURATION__EVENT);

		timeObservationActionEClass = createEClass(TIME_OBSERVATION_ACTION);
		createEReference(timeObservationActionEClass, TIME_OBSERVATION_ACTION__NOW);

		durationIntervalEClass = createEClass(DURATION_INTERVAL);

		intervalEClass = createEClass(INTERVAL);
		createEReference(intervalEClass, INTERVAL__MIN);
		createEReference(intervalEClass, INTERVAL__MAX);

		timeConstraintEClass = createEClass(TIME_CONSTRAINT);

		intervalConstraintEClass = createEClass(INTERVAL_CONSTRAINT);

		timeIntervalEClass = createEClass(TIME_INTERVAL);

		durationObservationActionEClass = createEClass(DURATION_OBSERVATION_ACTION);
		createEReference(durationObservationActionEClass, DURATION_OBSERVATION_ACTION__DURATION);

		durationConstraintEClass = createEClass(DURATION_CONSTRAINT);

		dataStoreNodeEClass = createEClass(DATA_STORE_NODE);

		interruptibleActivityRegionEClass = createEClass(INTERRUPTIBLE_ACTIVITY_REGION);
		createEReference(interruptibleActivityRegionEClass, INTERRUPTIBLE_ACTIVITY_REGION__INTERRUPTING_EDGE);
		createEReference(interruptibleActivityRegionEClass, INTERRUPTIBLE_ACTIVITY_REGION__CONTAINED_NODE);

		parameterSetEClass = createEClass(PARAMETER_SET);
		createEReference(parameterSetEClass, PARAMETER_SET__PARAMETER);
		createEReference(parameterSetEClass, PARAMETER_SET__CONDITION);

		componentEClass = createEClass(COMPONENT);
		createEAttribute(componentEClass, COMPONENT__IS_INDIRECTLY_INSTANTIATED);
		createEReference(componentEClass, COMPONENT__REQUIRED);
		createEReference(componentEClass, COMPONENT__PROVIDED);
		createEReference(componentEClass, COMPONENT__REALIZATION);
		createEReference(componentEClass, COMPONENT__OWNED_MEMBER);

		deploymentEClass = createEClass(DEPLOYMENT);
		createEReference(deploymentEClass, DEPLOYMENT__DEPLOYED_ARTIFACT);
		createEReference(deploymentEClass, DEPLOYMENT__LOCATION);
		createEReference(deploymentEClass, DEPLOYMENT__CONFIGURATION);

		deployedArtifactEClass = createEClass(DEPLOYED_ARTIFACT);

		deploymentTargetEClass = createEClass(DEPLOYMENT_TARGET);
		createEReference(deploymentTargetEClass, DEPLOYMENT_TARGET__DEPLOYMENT);
		createEReference(deploymentTargetEClass, DEPLOYMENT_TARGET__DEPLOYED_ELEMENT);

		nodeEClass = createEClass(NODE);
		createEReference(nodeEClass, NODE__NESTED_NODE);

		deviceEClass = createEClass(DEVICE);

		executionEnvironmentEClass = createEClass(EXECUTION_ENVIRONMENT);

		communicationPathEClass = createEClass(COMMUNICATION_PATH);

		protocolConformanceEClass = createEClass(PROTOCOL_CONFORMANCE);
		createEReference(protocolConformanceEClass, PROTOCOL_CONFORMANCE__SPECIFIC_MACHINE);
		createEReference(protocolConformanceEClass, PROTOCOL_CONFORMANCE__GENERAL_MACHINE);

		protocolStateMachineEClass = createEClass(PROTOCOL_STATE_MACHINE);
		createEReference(protocolStateMachineEClass, PROTOCOL_STATE_MACHINE__CONFORMANCE);

		protocolTransitionEClass = createEClass(PROTOCOL_TRANSITION);
		createEReference(protocolTransitionEClass, PROTOCOL_TRANSITION__POST_CONDITION);
		createEReference(protocolTransitionEClass, PROTOCOL_TRANSITION__REFERRED);
		createEReference(protocolTransitionEClass, PROTOCOL_TRANSITION__PRE_CONDITION);

		readExtentActionEClass = createEClass(READ_EXTENT_ACTION);
		createEReference(readExtentActionEClass, READ_EXTENT_ACTION__RESULT);
		createEReference(readExtentActionEClass, READ_EXTENT_ACTION__CLASSIFIER);

		reclassifyObjectActionEClass = createEClass(RECLASSIFY_OBJECT_ACTION);
		createEAttribute(reclassifyObjectActionEClass, RECLASSIFY_OBJECT_ACTION__IS_REPLACE_ALL);
		createEReference(reclassifyObjectActionEClass, RECLASSIFY_OBJECT_ACTION__OLD_CLASSIFIER);
		createEReference(reclassifyObjectActionEClass, RECLASSIFY_OBJECT_ACTION__NEW_CLASSIFIER);
		createEReference(reclassifyObjectActionEClass, RECLASSIFY_OBJECT_ACTION__OBJECT);

		readIsClassifiedObjectActionEClass = createEClass(READ_IS_CLASSIFIED_OBJECT_ACTION);
		createEAttribute(readIsClassifiedObjectActionEClass, READ_IS_CLASSIFIED_OBJECT_ACTION__IS_DIRECT);
		createEReference(readIsClassifiedObjectActionEClass, READ_IS_CLASSIFIED_OBJECT_ACTION__CLASSIFIER);
		createEReference(readIsClassifiedObjectActionEClass, READ_IS_CLASSIFIED_OBJECT_ACTION__RESULT);
		createEReference(readIsClassifiedObjectActionEClass, READ_IS_CLASSIFIED_OBJECT_ACTION__OBJECT);

		startOwnedBehaviorActionEClass = createEClass(START_OWNED_BEHAVIOR_ACTION);
		createEReference(startOwnedBehaviorActionEClass, START_OWNED_BEHAVIOR_ACTION__OBJECT);

		qualifierValueEClass = createEClass(QUALIFIER_VALUE);
		createEReference(qualifierValueEClass, QUALIFIER_VALUE__QUALIFIER);
		createEReference(qualifierValueEClass, QUALIFIER_VALUE__VALUE);

		readLinkObjectEndActionEClass = createEClass(READ_LINK_OBJECT_END_ACTION);
		createEReference(readLinkObjectEndActionEClass, READ_LINK_OBJECT_END_ACTION__OBJECT);
		createEReference(readLinkObjectEndActionEClass, READ_LINK_OBJECT_END_ACTION__END);
		createEReference(readLinkObjectEndActionEClass, READ_LINK_OBJECT_END_ACTION__RESULT);

		readLinkObjectEndQualifierActionEClass = createEClass(READ_LINK_OBJECT_END_QUALIFIER_ACTION);
		createEReference(readLinkObjectEndQualifierActionEClass, READ_LINK_OBJECT_END_QUALIFIER_ACTION__OBJECT);
		createEReference(readLinkObjectEndQualifierActionEClass, READ_LINK_OBJECT_END_QUALIFIER_ACTION__RESULT);
		createEReference(readLinkObjectEndQualifierActionEClass, READ_LINK_OBJECT_END_QUALIFIER_ACTION__QUALIFIER);

		createLinkObjectActionEClass = createEClass(CREATE_LINK_OBJECT_ACTION);
		createEReference(createLinkObjectActionEClass, CREATE_LINK_OBJECT_ACTION__RESULT);

		acceptEventActionEClass = createEClass(ACCEPT_EVENT_ACTION);
		createEReference(acceptEventActionEClass, ACCEPT_EVENT_ACTION__TRIGGER);
		createEReference(acceptEventActionEClass, ACCEPT_EVENT_ACTION__RESULT);

		acceptCallActionEClass = createEClass(ACCEPT_CALL_ACTION);
		createEReference(acceptCallActionEClass, ACCEPT_CALL_ACTION__RETURN_INFORMATION);

		replyActionEClass = createEClass(REPLY_ACTION);
		createEReference(replyActionEClass, REPLY_ACTION__REPLY_TO_CALL);
		createEReference(replyActionEClass, REPLY_ACTION__REPLY_VALUE);
		createEReference(replyActionEClass, REPLY_ACTION__RETURN_INFORMATION);

		raiseExceptionActionEClass = createEClass(RAISE_EXCEPTION_ACTION);
		createEReference(raiseExceptionActionEClass, RAISE_EXCEPTION_ACTION__EXCEPTION);

		deploymentSpecificationEClass = createEClass(DEPLOYMENT_SPECIFICATION);
		createEAttribute(deploymentSpecificationEClass, DEPLOYMENT_SPECIFICATION__DEPLOYMENT_LOCATION);
		createEAttribute(deploymentSpecificationEClass, DEPLOYMENT_SPECIFICATION__EXECUTION_LOCATION);

		j_FigureContainerEClass = createEClass(JFIGURE_CONTAINER);
		createEReference(j_FigureContainerEClass, JFIGURE_CONTAINER__FIGURES);
		createEReference(j_FigureContainerEClass, JFIGURE_CONTAINER__PROPERTIES);

		j_FigureEClass = createEClass(JFIGURE);
		createEAttribute(j_FigureEClass, JFIGURE__ID);
		createEAttribute(j_FigureEClass, JFIGURE__RECREATOR);
		createEAttribute(j_FigureEClass, JFIGURE__ANCHOR1_ID);
		createEAttribute(j_FigureEClass, JFIGURE__ANCHOR2_ID);
		createEAttribute(j_FigureEClass, JFIGURE__CONTAINED_NAME);
		createEAttribute(j_FigureEClass, JFIGURE__TEXT);
		createEAttribute(j_FigureEClass, JFIGURE__NAME);
		createEAttribute(j_FigureEClass, JFIGURE__VIRTUAL_POINT);
		createEAttribute(j_FigureEClass, JFIGURE__POINTS);
		createEAttribute(j_FigureEClass, JFIGURE__BR_OFFSET);
		createEAttribute(j_FigureEClass, JFIGURE__TL_OFFSET);
		createEAttribute(j_FigureEClass, JFIGURE__SHOW);
		createEAttribute(j_FigureEClass, JFIGURE__AUTOSIZED);
		createEAttribute(j_FigureEClass, JFIGURE__ICON);
		createEAttribute(j_FigureEClass, JFIGURE__POINT);
		createEAttribute(j_FigureEClass, JFIGURE__DIMENSIONS);
		createEAttribute(j_FigureEClass, JFIGURE__SUPPRESS_ATTRIBUTES);
		createEAttribute(j_FigureEClass, JFIGURE__SUPPRESS_OPERATIONS);
		createEAttribute(j_FigureEClass, JFIGURE__SUPPRESS_CONTENTS);
		createEAttribute(j_FigureEClass, JFIGURE__OFFSET);
		createEAttribute(j_FigureEClass, JFIGURE__MIN);
		createEAttribute(j_FigureEClass, JFIGURE__ACCESSIBILITY);
		createEAttribute(j_FigureEClass, JFIGURE__CLASSIFIER_SCOPE);
		createEAttribute(j_FigureEClass, JFIGURE__TYPE);
		createEReference(j_FigureEClass, JFIGURE__SUBJECT);

		j_PropertyEClass = createEClass(JPROPERTY);
		createEAttribute(j_PropertyEClass, JPROPERTY__NAME);
		createEAttribute(j_PropertyEClass, JPROPERTY__VALUE);

		j_DiagramEClass = createEClass(JDIAGRAM);
		createEAttribute(j_DiagramEClass, JDIAGRAM__NAME);
		createEAttribute(j_DiagramEClass, JDIAGRAM__LAST_FIGURE_ID);

		j_DiagramHolderEClass = createEClass(JDIAGRAM_HOLDER);
		createEReference(j_DiagramHolderEClass, JDIAGRAM_HOLDER__DIAGRAM);
		createEAttribute(j_DiagramHolderEClass, JDIAGRAM_HOLDER__SAVE_TIME);
		createEAttribute(j_DiagramHolderEClass, JDIAGRAM_HOLDER__SAVED_BY);

		appliedBasicStereotypeValueEClass = createEClass(APPLIED_BASIC_STEREOTYPE_VALUE);
		createEReference(appliedBasicStereotypeValueEClass, APPLIED_BASIC_STEREOTYPE_VALUE__PROPERTY);
		createEReference(appliedBasicStereotypeValueEClass, APPLIED_BASIC_STEREOTYPE_VALUE__VALUE);

		propertyValueSpecificationEClass = createEClass(PROPERTY_VALUE_SPECIFICATION);
		createEAttribute(propertyValueSpecificationEClass, PROPERTY_VALUE_SPECIFICATION__ALIASED);
		createEReference(propertyValueSpecificationEClass, PROPERTY_VALUE_SPECIFICATION__PROPERTY);

		deltaReplacedConstituentEClass = createEClass(DELTA_REPLACED_CONSTITUENT);
		createEReference(deltaReplacedConstituentEClass, DELTA_REPLACED_CONSTITUENT__REPLACED);
		createEReference(deltaReplacedConstituentEClass, DELTA_REPLACED_CONSTITUENT__REPLACEMENT);

		deltaDeletedConstituentEClass = createEClass(DELTA_DELETED_CONSTITUENT);
		createEReference(deltaDeletedConstituentEClass, DELTA_DELETED_CONSTITUENT__DELETED);

		deltaReplacedAttributeEClass = createEClass(DELTA_REPLACED_ATTRIBUTE);

		deltaDeletedAttributeEClass = createEClass(DELTA_DELETED_ATTRIBUTE);

		deltaReplacedPortEClass = createEClass(DELTA_REPLACED_PORT);

		deltaDeletedPortEClass = createEClass(DELTA_DELETED_PORT);

		deltaReplacedConnectorEClass = createEClass(DELTA_REPLACED_CONNECTOR);

		deltaDeletedConnectorEClass = createEClass(DELTA_DELETED_CONNECTOR);

		deltaReplacedOperationEClass = createEClass(DELTA_REPLACED_OPERATION);

		deltaDeletedOperationEClass = createEClass(DELTA_DELETED_OPERATION);

		portRemapEClass = createEClass(PORT_REMAP);
		createEReference(portRemapEClass, PORT_REMAP__ORIGINAL_PORT);
		createEReference(portRemapEClass, PORT_REMAP__NEW_PORT);

		savedReferenceEClass = createEClass(SAVED_REFERENCE);
		createEAttribute(savedReferenceEClass, SAVED_REFERENCE__TO);
		createEAttribute(savedReferenceEClass, SAVED_REFERENCE__FROM);
		createEReference(savedReferenceEClass, SAVED_REFERENCE__TO_ECLASS);
		createEReference(savedReferenceEClass, SAVED_REFERENCE__FEATURE);

		requirementsFeatureEClass = createEClass(REQUIREMENTS_FEATURE);
		createEReference(requirementsFeatureEClass, REQUIREMENTS_FEATURE__SUBFEATURES);
		createEReference(requirementsFeatureEClass, REQUIREMENTS_FEATURE__DELTA_REPLACED_SUBFEATURES);
		createEReference(requirementsFeatureEClass, REQUIREMENTS_FEATURE__DELTA_DELETED_SUBFEATURES);

		requirementsFeatureLinkEClass = createEClass(REQUIREMENTS_FEATURE_LINK);
		createEAttribute(requirementsFeatureLinkEClass, REQUIREMENTS_FEATURE_LINK__KIND);
		createEReference(requirementsFeatureLinkEClass, REQUIREMENTS_FEATURE_LINK__TYPE);

		deltaReplacedRequirementsFeatureLinkEClass = createEClass(DELTA_REPLACED_REQUIREMENTS_FEATURE_LINK);

		deltaDeletedRequirementsFeatureLinkEClass = createEClass(DELTA_DELETED_REQUIREMENTS_FEATURE_LINK);

		deltaDeletedTraceEClass = createEClass(DELTA_DELETED_TRACE);

		deltaReplacedTraceEClass = createEClass(DELTA_REPLACED_TRACE);

		// Create enums
		visibilityKindEEnum = createEEnum(VISIBILITY_KIND);
		parameterDirectionKindEEnum = createEEnum(PARAMETER_DIRECTION_KIND);
		aggregationKindEEnum = createEEnum(AGGREGATION_KIND);
		callConcurrencyKindEEnum = createEEnum(CALL_CONCURRENCY_KIND);
		messageKindEEnum = createEEnum(MESSAGE_KIND);
		messageSortEEnum = createEEnum(MESSAGE_SORT);
		expansionKindEEnum = createEEnum(EXPANSION_KIND);
		interactionOperatorEEnum = createEEnum(INTERACTION_OPERATOR);
		transitionKindEEnum = createEEnum(TRANSITION_KIND);
		pseudostateKindEEnum = createEEnum(PSEUDOSTATE_KIND);
		parameterEffectKindEEnum = createEEnum(PARAMETER_EFFECT_KIND);
		objectNodeOrderingKindEEnum = createEEnum(OBJECT_NODE_ORDERING_KIND);
		connectorKindEEnum = createEEnum(CONNECTOR_KIND);
		componentKindEEnum = createEEnum(COMPONENT_KIND);
		propertyAccessKindEEnum = createEEnum(PROPERTY_ACCESS_KIND);
		portKindEEnum = createEEnum(PORT_KIND);
		requirementsLinkKindEEnum = createEEnum(REQUIREMENTS_LINK_KIND);

		// Create data types
		integerEDataType = createEDataType(INTEGER);
		booleanEDataType = createEDataType(BOOLEAN);
		stringEDataType = createEDataType(STRING);
		unlimitedNaturalEDataType = createEDataType(UNLIMITED_NATURAL);
		sequenceEDataType = createEDataType(SEQUENCE);
		setEDataType = createEDataType(SET);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackageImpl theEcorePackage = (EcorePackageImpl)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		UML2PackageImpl theUML2Package_1 = (UML2PackageImpl)EPackage.Registry.INSTANCE.getEPackage(UML2Package.eNS_URI);

		// Add supertypes to classes
		elementEClass.getESuperTypes().add(theEcorePackage.getEModelElement());
		multiplicityElementEClass.getESuperTypes().add(theUML2Package_1.getElement());
		namedElementEClass.getESuperTypes().add(theUML2Package_1.getTemplateableElement());
		namespaceEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		opaqueExpressionEClass.getESuperTypes().add(theUML2Package_1.getValueSpecification());
		valueSpecificationEClass.getESuperTypes().add(theUML2Package_1.getTypedElement());
		valueSpecificationEClass.getESuperTypes().add(theUML2Package_1.getParameterableElement());
		expressionEClass.getESuperTypes().add(theUML2Package_1.getOpaqueExpression());
		commentEClass.getESuperTypes().add(theUML2Package_1.getTemplateableElement());
		directedRelationshipEClass.getESuperTypes().add(theUML2Package_1.getRelationship());
		relationshipEClass.getESuperTypes().add(theUML2Package_1.getElement());
		classEClass.getESuperTypes().add(theUML2Package_1.getBehavioredClassifier());
		classEClass.getESuperTypes().add(theUML2Package_1.getEncapsulatedClassifier());
		typeEClass.getESuperTypes().add(theUML2Package_1.getPackageableElement());
		propertyEClass.getESuperTypes().add(theUML2Package_1.getStructuralFeature());
		propertyEClass.getESuperTypes().add(theUML2Package_1.getConnectableElement());
		propertyEClass.getESuperTypes().add(theUML2Package_1.getDeploymentTarget());
		operationEClass.getESuperTypes().add(theUML2Package_1.getBehavioralFeature());
		operationEClass.getESuperTypes().add(theUML2Package_1.getTypedElement());
		operationEClass.getESuperTypes().add(theUML2Package_1.getMultiplicityElement());
		operationEClass.getESuperTypes().add(theUML2Package_1.getParameterableElement());
		typedElementEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		parameterEClass.getESuperTypes().add(theUML2Package_1.getConnectableElement());
		parameterEClass.getESuperTypes().add(theUML2Package_1.getTypedElement());
		parameterEClass.getESuperTypes().add(theUML2Package_1.getMultiplicityElement());
		packageEClass.getESuperTypes().add(theUML2Package_1.getNamespace());
		packageEClass.getESuperTypes().add(theUML2Package_1.getPackageableElement());
		enumerationEClass.getESuperTypes().add(theUML2Package_1.getDataType());
		dataTypeEClass.getESuperTypes().add(theUML2Package_1.getClassifier());
		enumerationLiteralEClass.getESuperTypes().add(theUML2Package_1.getInstanceSpecification());
		primitiveTypeEClass.getESuperTypes().add(theUML2Package_1.getDataType());
		classifierEClass.getESuperTypes().add(theUML2Package_1.getNamespace());
		classifierEClass.getESuperTypes().add(theUML2Package_1.getType());
		classifierEClass.getESuperTypes().add(theUML2Package_1.getRedefinableElement());
		featureEClass.getESuperTypes().add(theUML2Package_1.getRedefinableElement());
		constraintEClass.getESuperTypes().add(theUML2Package_1.getPackageableElement());
		literalBooleanEClass.getESuperTypes().add(theUML2Package_1.getLiteralSpecification());
		literalSpecificationEClass.getESuperTypes().add(theUML2Package_1.getValueSpecification());
		literalStringEClass.getESuperTypes().add(theUML2Package_1.getLiteralSpecification());
		literalNullEClass.getESuperTypes().add(theUML2Package_1.getLiteralSpecification());
		literalIntegerEClass.getESuperTypes().add(theUML2Package_1.getLiteralSpecification());
		literalUnlimitedNaturalEClass.getESuperTypes().add(theUML2Package_1.getLiteralSpecification());
		behavioralFeatureEClass.getESuperTypes().add(theUML2Package_1.getNamespace());
		behavioralFeatureEClass.getESuperTypes().add(theUML2Package_1.getFeature());
		structuralFeatureEClass.getESuperTypes().add(theUML2Package_1.getFeature());
		structuralFeatureEClass.getESuperTypes().add(theUML2Package_1.getTypedElement());
		structuralFeatureEClass.getESuperTypes().add(theUML2Package_1.getMultiplicityElement());
		instanceSpecificationEClass.getESuperTypes().add(theUML2Package_1.getPackageableElement());
		instanceSpecificationEClass.getESuperTypes().add(theUML2Package_1.getDeploymentTarget());
		instanceSpecificationEClass.getESuperTypes().add(theUML2Package_1.getDeployedArtifact());
		slotEClass.getESuperTypes().add(theUML2Package_1.getElement());
		instanceValueEClass.getESuperTypes().add(theUML2Package_1.getValueSpecification());
		redefinableElementEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		generalizationEClass.getESuperTypes().add(theUML2Package_1.getDirectedRelationship());
		packageableElementEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		packageableElementEClass.getESuperTypes().add(theUML2Package_1.getParameterableElement());
		elementImportEClass.getESuperTypes().add(theUML2Package_1.getDirectedRelationship());
		packageImportEClass.getESuperTypes().add(theUML2Package_1.getDirectedRelationship());
		associationEClass.getESuperTypes().add(theUML2Package_1.getClassifier());
		associationEClass.getESuperTypes().add(theUML2Package_1.getRelationship());
		packageMergeEClass.getESuperTypes().add(theUML2Package_1.getDirectedRelationship());
		stereotypeEClass.getESuperTypes().add(theUML2Package_1.getClass_());
		profileEClass.getESuperTypes().add(theUML2Package_1.getPackage());
		profileApplicationEClass.getESuperTypes().add(theUML2Package_1.getPackageImport());
		extensionEClass.getESuperTypes().add(theUML2Package_1.getAssociation());
		extensionEndEClass.getESuperTypes().add(theUML2Package_1.getProperty());
		behaviorEClass.getESuperTypes().add(theUML2Package_1.getClass_());
		behavioredClassifierEClass.getESuperTypes().add(theUML2Package_1.getClassifier());
		activityEClass.getESuperTypes().add(theUML2Package_1.getBehavior());
		permissionEClass.getESuperTypes().add(theUML2Package_1.getDependency());
		dependencyEClass.getESuperTypes().add(theUML2Package_1.getPackageableElement());
		dependencyEClass.getESuperTypes().add(theUML2Package_1.getDirectedRelationship());
		usageEClass.getESuperTypes().add(theUML2Package_1.getDependency());
		abstractionEClass.getESuperTypes().add(theUML2Package_1.getDependency());
		realizationEClass.getESuperTypes().add(theUML2Package_1.getAbstraction());
		substitutionEClass.getESuperTypes().add(theUML2Package_1.getRealization());
		generalizationSetEClass.getESuperTypes().add(theUML2Package_1.getPackageableElement());
		associationClassEClass.getESuperTypes().add(theUML2Package_1.getClass_());
		associationClassEClass.getESuperTypes().add(theUML2Package_1.getAssociation());
		informationItemEClass.getESuperTypes().add(theUML2Package_1.getClassifier());
		informationFlowEClass.getESuperTypes().add(theUML2Package_1.getPackageableElement());
		informationFlowEClass.getESuperTypes().add(theUML2Package_1.getDirectedRelationship());
		modelEClass.getESuperTypes().add(theUML2Package_1.getPackage());
		connectorEndEClass.getESuperTypes().add(theUML2Package_1.getMultiplicityElement());
		connectableElementEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		connectableElementEClass.getESuperTypes().add(theUML2Package_1.getParameterableElement());
		connectorEClass.getESuperTypes().add(theUML2Package_1.getFeature());
		structuredClassifierEClass.getESuperTypes().add(theUML2Package_1.getClassifier());
		activityEdgeEClass.getESuperTypes().add(theUML2Package_1.getRedefinableElement());
		activityGroupEClass.getESuperTypes().add(theUML2Package_1.getElement());
		activityNodeEClass.getESuperTypes().add(theUML2Package_1.getRedefinableElement());
		actionEClass.getESuperTypes().add(theUML2Package_1.getExecutableNode());
		objectNodeEClass.getESuperTypes().add(theUML2Package_1.getActivityNode());
		objectNodeEClass.getESuperTypes().add(theUML2Package_1.getTypedElement());
		controlNodeEClass.getESuperTypes().add(theUML2Package_1.getActivityNode());
		controlFlowEClass.getESuperTypes().add(theUML2Package_1.getActivityEdge());
		objectFlowEClass.getESuperTypes().add(theUML2Package_1.getActivityEdge());
		initialNodeEClass.getESuperTypes().add(theUML2Package_1.getControlNode());
		finalNodeEClass.getESuperTypes().add(theUML2Package_1.getControlNode());
		activityFinalNodeEClass.getESuperTypes().add(theUML2Package_1.getFinalNode());
		decisionNodeEClass.getESuperTypes().add(theUML2Package_1.getControlNode());
		mergeNodeEClass.getESuperTypes().add(theUML2Package_1.getControlNode());
		executableNodeEClass.getESuperTypes().add(theUML2Package_1.getActivityNode());
		outputPinEClass.getESuperTypes().add(theUML2Package_1.getPin());
		inputPinEClass.getESuperTypes().add(theUML2Package_1.getPin());
		pinEClass.getESuperTypes().add(theUML2Package_1.getObjectNode());
		pinEClass.getESuperTypes().add(theUML2Package_1.getMultiplicityElement());
		activityParameterNodeEClass.getESuperTypes().add(theUML2Package_1.getObjectNode());
		valuePinEClass.getESuperTypes().add(theUML2Package_1.getInputPin());
		interfaceEClass.getESuperTypes().add(theUML2Package_1.getClassifier());
		implementationEClass.getESuperTypes().add(theUML2Package_1.getRealization());
		artifactEClass.getESuperTypes().add(theUML2Package_1.getClassifier());
		artifactEClass.getESuperTypes().add(theUML2Package_1.getDeployedArtifact());
		manifestationEClass.getESuperTypes().add(theUML2Package_1.getAbstraction());
		actorEClass.getESuperTypes().add(theUML2Package_1.getClassifier());
		extendEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		extendEClass.getESuperTypes().add(theUML2Package_1.getDirectedRelationship());
		useCaseEClass.getESuperTypes().add(theUML2Package_1.getBehavioredClassifier());
		extensionPointEClass.getESuperTypes().add(theUML2Package_1.getRedefinableElement());
		includeEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		includeEClass.getESuperTypes().add(theUML2Package_1.getDirectedRelationship());
		collaborationOccurrenceEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		collaborationEClass.getESuperTypes().add(theUML2Package_1.getBehavioredClassifier());
		collaborationEClass.getESuperTypes().add(theUML2Package_1.getStructuredClassifier());
		portEClass.getESuperTypes().add(theUML2Package_1.getProperty());
		encapsulatedClassifierEClass.getESuperTypes().add(theUML2Package_1.getStructuredClassifier());
		callTriggerEClass.getESuperTypes().add(theUML2Package_1.getMessageTrigger());
		messageTriggerEClass.getESuperTypes().add(theUML2Package_1.getTrigger());
		changeTriggerEClass.getESuperTypes().add(theUML2Package_1.getTrigger());
		triggerEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		receptionEClass.getESuperTypes().add(theUML2Package_1.getBehavioralFeature());
		signalEClass.getESuperTypes().add(theUML2Package_1.getClassifier());
		signalTriggerEClass.getESuperTypes().add(theUML2Package_1.getMessageTrigger());
		timeTriggerEClass.getESuperTypes().add(theUML2Package_1.getTrigger());
		anyTriggerEClass.getESuperTypes().add(theUML2Package_1.getMessageTrigger());
		variableEClass.getESuperTypes().add(theUML2Package_1.getConnectableElement());
		variableEClass.getESuperTypes().add(theUML2Package_1.getTypedElement());
		variableEClass.getESuperTypes().add(theUML2Package_1.getMultiplicityElement());
		structuredActivityNodeEClass.getESuperTypes().add(theUML2Package_1.getAction());
		structuredActivityNodeEClass.getESuperTypes().add(theUML2Package_1.getNamespace());
		structuredActivityNodeEClass.getESuperTypes().add(theUML2Package_1.getActivityGroup());
		conditionalNodeEClass.getESuperTypes().add(theUML2Package_1.getStructuredActivityNode());
		clauseEClass.getESuperTypes().add(theUML2Package_1.getElement());
		loopNodeEClass.getESuperTypes().add(theUML2Package_1.getStructuredActivityNode());
		interactionEClass.getESuperTypes().add(theUML2Package_1.getBehavior());
		interactionEClass.getESuperTypes().add(theUML2Package_1.getInteractionFragment());
		interactionFragmentEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		lifelineEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		messageEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		generalOrderingEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		messageEndEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		eventOccurrenceEClass.getESuperTypes().add(theUML2Package_1.getInteractionFragment());
		eventOccurrenceEClass.getESuperTypes().add(theUML2Package_1.getMessageEnd());
		executionOccurrenceEClass.getESuperTypes().add(theUML2Package_1.getInteractionFragment());
		stateInvariantEClass.getESuperTypes().add(theUML2Package_1.getInteractionFragment());
		stopEClass.getESuperTypes().add(theUML2Package_1.getEventOccurrence());
		templateSignatureEClass.getESuperTypes().add(theUML2Package_1.getElement());
		templateParameterEClass.getESuperTypes().add(theUML2Package_1.getElement());
		templateableElementEClass.getESuperTypes().add(theUML2Package_1.getElement());
		stringExpressionEClass.getESuperTypes().add(theUML2Package_1.getTemplateableElement());
		parameterableElementEClass.getESuperTypes().add(theUML2Package_1.getElement());
		templateBindingEClass.getESuperTypes().add(theUML2Package_1.getDirectedRelationship());
		templateParameterSubstitutionEClass.getESuperTypes().add(theUML2Package_1.getElement());
		operationTemplateParameterEClass.getESuperTypes().add(theUML2Package_1.getTemplateParameter());
		classifierTemplateParameterEClass.getESuperTypes().add(theUML2Package_1.getTemplateParameter());
		parameterableClassifierEClass.getESuperTypes().add(theUML2Package_1.getClassifier());
		redefinableTemplateSignatureEClass.getESuperTypes().add(theUML2Package_1.getRedefinableElement());
		redefinableTemplateSignatureEClass.getESuperTypes().add(theUML2Package_1.getTemplateSignature());
		templateableClassifierEClass.getESuperTypes().add(theUML2Package_1.getClassifier());
		connectableElementTemplateParameterEClass.getESuperTypes().add(theUML2Package_1.getTemplateParameter());
		forkNodeEClass.getESuperTypes().add(theUML2Package_1.getControlNode());
		joinNodeEClass.getESuperTypes().add(theUML2Package_1.getControlNode());
		flowFinalNodeEClass.getESuperTypes().add(theUML2Package_1.getFinalNode());
		centralBufferNodeEClass.getESuperTypes().add(theUML2Package_1.getObjectNode());
		activityPartitionEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		activityPartitionEClass.getESuperTypes().add(theUML2Package_1.getActivityGroup());
		expansionNodeEClass.getESuperTypes().add(theUML2Package_1.getObjectNode());
		expansionRegionEClass.getESuperTypes().add(theUML2Package_1.getStructuredActivityNode());
		exceptionHandlerEClass.getESuperTypes().add(theUML2Package_1.getElement());
		interactionOccurrenceEClass.getESuperTypes().add(theUML2Package_1.getInteractionFragment());
		gateEClass.getESuperTypes().add(theUML2Package_1.getMessageEnd());
		partDecompositionEClass.getESuperTypes().add(theUML2Package_1.getInteractionOccurrence());
		interactionOperandEClass.getESuperTypes().add(theUML2Package_1.getNamespace());
		interactionOperandEClass.getESuperTypes().add(theUML2Package_1.getInteractionFragment());
		interactionConstraintEClass.getESuperTypes().add(theUML2Package_1.getConstraint());
		combinedFragmentEClass.getESuperTypes().add(theUML2Package_1.getInteractionFragment());
		continuationEClass.getESuperTypes().add(theUML2Package_1.getInteractionFragment());
		stateMachineEClass.getESuperTypes().add(theUML2Package_1.getBehavior());
		regionEClass.getESuperTypes().add(theUML2Package_1.getNamespace());
		regionEClass.getESuperTypes().add(theUML2Package_1.getRedefinableElement());
		pseudostateEClass.getESuperTypes().add(theUML2Package_1.getVertex());
		stateEClass.getESuperTypes().add(theUML2Package_1.getNamespace());
		stateEClass.getESuperTypes().add(theUML2Package_1.getRedefinableElement());
		stateEClass.getESuperTypes().add(theUML2Package_1.getVertex());
		vertexEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		connectionPointReferenceEClass.getESuperTypes().add(theUML2Package_1.getVertex());
		transitionEClass.getESuperTypes().add(theUML2Package_1.getRedefinableElement());
		finalStateEClass.getESuperTypes().add(theUML2Package_1.getState());
		createObjectActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		destroyObjectActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		testIdentityActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		readSelfActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		structuralFeatureActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		readStructuralFeatureActionEClass.getESuperTypes().add(theUML2Package_1.getStructuralFeatureAction());
		writeStructuralFeatureActionEClass.getESuperTypes().add(theUML2Package_1.getStructuralFeatureAction());
		clearStructuralFeatureActionEClass.getESuperTypes().add(theUML2Package_1.getStructuralFeatureAction());
		removeStructuralFeatureValueActionEClass.getESuperTypes().add(theUML2Package_1.getWriteStructuralFeatureAction());
		addStructuralFeatureValueActionEClass.getESuperTypes().add(theUML2Package_1.getWriteStructuralFeatureAction());
		linkActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		linkEndDataEClass.getESuperTypes().add(theUML2Package_1.getElement());
		readLinkActionEClass.getESuperTypes().add(theUML2Package_1.getLinkAction());
		linkEndCreationDataEClass.getESuperTypes().add(theUML2Package_1.getLinkEndData());
		createLinkActionEClass.getESuperTypes().add(theUML2Package_1.getWriteLinkAction());
		writeLinkActionEClass.getESuperTypes().add(theUML2Package_1.getLinkAction());
		destroyLinkActionEClass.getESuperTypes().add(theUML2Package_1.getWriteLinkAction());
		clearAssociationActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		variableActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		readVariableActionEClass.getESuperTypes().add(theUML2Package_1.getVariableAction());
		writeVariableActionEClass.getESuperTypes().add(theUML2Package_1.getVariableAction());
		clearVariableActionEClass.getESuperTypes().add(theUML2Package_1.getVariableAction());
		addVariableValueActionEClass.getESuperTypes().add(theUML2Package_1.getWriteVariableAction());
		removeVariableValueActionEClass.getESuperTypes().add(theUML2Package_1.getWriteVariableAction());
		applyFunctionActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		primitiveFunctionEClass.getESuperTypes().add(theUML2Package_1.getPackageableElement());
		callActionEClass.getESuperTypes().add(theUML2Package_1.getInvocationAction());
		invocationActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		sendSignalActionEClass.getESuperTypes().add(theUML2Package_1.getInvocationAction());
		broadcastSignalActionEClass.getESuperTypes().add(theUML2Package_1.getInvocationAction());
		sendObjectActionEClass.getESuperTypes().add(theUML2Package_1.getInvocationAction());
		callOperationActionEClass.getESuperTypes().add(theUML2Package_1.getCallAction());
		callBehaviorActionEClass.getESuperTypes().add(theUML2Package_1.getCallAction());
		timeExpressionEClass.getESuperTypes().add(theUML2Package_1.getValueSpecification());
		durationEClass.getESuperTypes().add(theUML2Package_1.getValueSpecification());
		timeObservationActionEClass.getESuperTypes().add(theUML2Package_1.getWriteStructuralFeatureAction());
		durationIntervalEClass.getESuperTypes().add(theUML2Package_1.getInterval());
		intervalEClass.getESuperTypes().add(theUML2Package_1.getValueSpecification());
		timeConstraintEClass.getESuperTypes().add(theUML2Package_1.getIntervalConstraint());
		intervalConstraintEClass.getESuperTypes().add(theUML2Package_1.getConstraint());
		timeIntervalEClass.getESuperTypes().add(theUML2Package_1.getInterval());
		durationObservationActionEClass.getESuperTypes().add(theUML2Package_1.getWriteStructuralFeatureAction());
		durationConstraintEClass.getESuperTypes().add(theUML2Package_1.getIntervalConstraint());
		dataStoreNodeEClass.getESuperTypes().add(theUML2Package_1.getCentralBufferNode());
		interruptibleActivityRegionEClass.getESuperTypes().add(theUML2Package_1.getActivityGroup());
		parameterSetEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		componentEClass.getESuperTypes().add(theUML2Package_1.getClass_());
		deploymentEClass.getESuperTypes().add(theUML2Package_1.getDependency());
		deployedArtifactEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		deploymentTargetEClass.getESuperTypes().add(theUML2Package_1.getNamedElement());
		nodeEClass.getESuperTypes().add(theUML2Package_1.getClass_());
		nodeEClass.getESuperTypes().add(theUML2Package_1.getDeploymentTarget());
		deviceEClass.getESuperTypes().add(theUML2Package_1.getNode());
		executionEnvironmentEClass.getESuperTypes().add(theUML2Package_1.getNode());
		communicationPathEClass.getESuperTypes().add(theUML2Package_1.getAssociation());
		protocolConformanceEClass.getESuperTypes().add(theUML2Package_1.getDirectedRelationship());
		protocolStateMachineEClass.getESuperTypes().add(theUML2Package_1.getStateMachine());
		protocolTransitionEClass.getESuperTypes().add(theUML2Package_1.getTransition());
		readExtentActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		reclassifyObjectActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		readIsClassifiedObjectActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		startOwnedBehaviorActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		qualifierValueEClass.getESuperTypes().add(theUML2Package_1.getElement());
		readLinkObjectEndActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		readLinkObjectEndQualifierActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		createLinkObjectActionEClass.getESuperTypes().add(theUML2Package_1.getCreateLinkAction());
		acceptEventActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		acceptCallActionEClass.getESuperTypes().add(theUML2Package_1.getAcceptEventAction());
		replyActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		raiseExceptionActionEClass.getESuperTypes().add(theUML2Package_1.getAction());
		deploymentSpecificationEClass.getESuperTypes().add(theUML2Package_1.getArtifact());
		j_FigureContainerEClass.getESuperTypes().add(theUML2Package_1.getElement());
		j_FigureEClass.getESuperTypes().add(theUML2Package_1.getJ_FigureContainer());
		j_PropertyEClass.getESuperTypes().add(theUML2Package_1.getElement());
		j_DiagramEClass.getESuperTypes().add(theUML2Package_1.getJ_FigureContainer());
		j_DiagramHolderEClass.getESuperTypes().add(theUML2Package_1.getElement());
		appliedBasicStereotypeValueEClass.getESuperTypes().add(theUML2Package_1.getElement());
		propertyValueSpecificationEClass.getESuperTypes().add(theUML2Package_1.getValueSpecification());
		deltaReplacedConstituentEClass.getESuperTypes().add(theUML2Package_1.getElement());
		deltaDeletedConstituentEClass.getESuperTypes().add(theUML2Package_1.getElement());
		deltaReplacedAttributeEClass.getESuperTypes().add(theUML2Package_1.getDeltaReplacedConstituent());
		deltaDeletedAttributeEClass.getESuperTypes().add(theUML2Package_1.getDeltaDeletedConstituent());
		deltaReplacedPortEClass.getESuperTypes().add(theUML2Package_1.getDeltaReplacedConstituent());
		deltaDeletedPortEClass.getESuperTypes().add(theUML2Package_1.getDeltaDeletedConstituent());
		deltaReplacedConnectorEClass.getESuperTypes().add(theUML2Package_1.getDeltaReplacedConstituent());
		deltaDeletedConnectorEClass.getESuperTypes().add(theUML2Package_1.getDeltaDeletedConstituent());
		deltaReplacedOperationEClass.getESuperTypes().add(theUML2Package_1.getDeltaReplacedConstituent());
		deltaDeletedOperationEClass.getESuperTypes().add(theUML2Package_1.getDeltaDeletedConstituent());
		portRemapEClass.getESuperTypes().add(theUML2Package_1.getElement());
		savedReferenceEClass.getESuperTypes().add(theUML2Package_1.getPackageableElement());
		requirementsFeatureEClass.getESuperTypes().add(theUML2Package_1.getType());
		requirementsFeatureLinkEClass.getESuperTypes().add(theUML2Package_1.getElement());
		deltaReplacedRequirementsFeatureLinkEClass.getESuperTypes().add(theUML2Package_1.getDeltaReplacedConstituent());
		deltaDeletedRequirementsFeatureLinkEClass.getESuperTypes().add(theUML2Package_1.getDeltaDeletedConstituent());
		deltaDeletedTraceEClass.getESuperTypes().add(theUML2Package_1.getDeltaDeletedConstituent());
		deltaReplacedTraceEClass.getESuperTypes().add(theUML2Package_1.getDeltaReplacedConstituent());

		// Initialize classes and features; add operations and parameters
		initEClass(elementEClass, Element.class, "Element", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getElement_OwnedElement(), theUML2Package_1.getElement(), theUML2Package_1.getElement_Owner(), "ownedElement", null, 0, -1, Element.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getElement_Owner(), theUML2Package_1.getElement(), theUML2Package_1.getElement_OwnedElement(), "owner", null, 0, 1, Element.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getElement_OwnedComment(), theUML2Package_1.getComment(), null, "ownedComment", null, 0, -1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getElement_J_deleted(), theUML2Package_1.getInteger(), "j_deleted", null, 0, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getElement_Documentation(), theUML2Package_1.getString(), "documentation", "", 0, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getElement_AppliedBasicStereotypes(), theUML2Package_1.getStereotype(), null, "appliedBasicStereotypes", null, 0, -1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getElement_AppliedBasicStereotypeValues(), theUML2Package_1.getAppliedBasicStereotypeValue(), null, "appliedBasicStereotypeValues", null, 0, -1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getElement_Uuid(), theUML2Package_1.getString(), "uuid", "", 0, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		EOperation op = addEOperation(elementEClass, theUML2Package_1.getBoolean(), "validateNotOwnSelf"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(elementEClass, theUML2Package_1.getBoolean(), "validateHasOwner"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		addEOperation(elementEClass, theUML2Package_1.getSet(), "allOwnedElements"); //$NON-NLS-1$

		addEOperation(elementEClass, theUML2Package_1.getBoolean(), "mustBeOwned"); //$NON-NLS-1$

		initEClass(multiplicityElementEClass, MultiplicityElement.class, "MultiplicityElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getMultiplicityElement_IsOrdered(), theUML2Package_1.getBoolean(), "isOrdered", "false", 0, 1, MultiplicityElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getMultiplicityElement_IsUnique(), theUML2Package_1.getBoolean(), "isUnique", "true", 0, 1, MultiplicityElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getMultiplicityElement_Lower(), theUML2Package_1.getInteger(), "lower", "1", 0, 1, MultiplicityElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getMultiplicityElement_Upper(), theUML2Package_1.getUnlimitedNatural(), "upper", "1", 0, 1, MultiplicityElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getMultiplicityElement_UpperValue(), theUML2Package_1.getValueSpecification(), null, "upperValue", null, 0, 1, MultiplicityElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMultiplicityElement_LowerValue(), theUML2Package_1.getValueSpecification(), null, "lowerValue", null, 0, 1, MultiplicityElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(multiplicityElementEClass, theUML2Package_1.getInteger(), "lowerBound"); //$NON-NLS-1$

		addEOperation(multiplicityElementEClass, theUML2Package_1.getUnlimitedNatural(), "upperBound"); //$NON-NLS-1$

		addEOperation(multiplicityElementEClass, theUML2Package_1.getBoolean(), "isMultivalued"); //$NON-NLS-1$

		op = addEOperation(multiplicityElementEClass, theUML2Package_1.getBoolean(), "includesCardinality"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getInteger(), "C"); //$NON-NLS-1$

		op = addEOperation(multiplicityElementEClass, theUML2Package_1.getBoolean(), "includesMultiplicity"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getMultiplicityElement(), "M"); //$NON-NLS-1$

		op = addEOperation(multiplicityElementEClass, theUML2Package_1.getBoolean(), "validateUpperGt0"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(multiplicityElementEClass, theUML2Package_1.getBoolean(), "validateLowerGe0"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(multiplicityElementEClass, theUML2Package_1.getBoolean(), "validateUpperGeLower"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(multiplicityElementEClass, theUML2Package_1.getBoolean(), "validateLowerEqLowerbound"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(multiplicityElementEClass, theUML2Package_1.getBoolean(), "validateUpperEqUpperbound"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		addEOperation(multiplicityElementEClass, theUML2Package_1.getInteger(), "lower"); //$NON-NLS-1$

		addEOperation(multiplicityElementEClass, theUML2Package_1.getUnlimitedNatural(), "upper"); //$NON-NLS-1$

		initEClass(namedElementEClass, NamedElement.class, "NamedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getNamedElement_Name(), theUML2Package_1.getString(), "name", "", 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getNamedElement_QualifiedName(), theUML2Package_1.getString(), "qualifiedName", "", 0, 1, NamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getNamedElement_Visibility(), theUML2Package_1.getVisibilityKind(), "visibility", null, 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getNamedElement_ClientDependency(), theUML2Package_1.getDependency(), theUML2Package_1.getDependency_Client(), "clientDependency", null, 0, -1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getNamedElement_NameExpression(), theUML2Package_1.getStringExpression(), null, "nameExpression", null, 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getNamedElement_OwnedAnonymousDependencies(), theUML2Package_1.getDependency(), null, "ownedAnonymousDependencies", null, 0, -1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getNamedElement_ReverseDependencies(), theUML2Package_1.getDependency(), null, "reverseDependencies", null, 0, -1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getNamedElement_ReverseGeneralizations(), theUML2Package_1.getGeneralization(), null, "reverseGeneralizations", null, 0, -1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(namedElementEClass, theUML2Package_1.getBoolean(), "validateNoName"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(namedElementEClass, theUML2Package_1.getBoolean(), "validateQualifiedName"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		addEOperation(namedElementEClass, theUML2Package_1.getSequence(), "allNamespaces"); //$NON-NLS-1$

		op = addEOperation(namedElementEClass, theUML2Package_1.getBoolean(), "isDistinguishableFrom"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getNamedElement(), "n"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getNamespace(), "ns"); //$NON-NLS-1$

		addEOperation(namedElementEClass, theUML2Package_1.getString(), "separator"); //$NON-NLS-1$

		addEOperation(namedElementEClass, theUML2Package_1.getString(), "qualifiedName"); //$NON-NLS-1$

		op = addEOperation(namedElementEClass, theUML2Package_1.getBoolean(), "validateVisibilityNeedsOwnership"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		initEClass(namespaceEClass, Namespace.class, "Namespace", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getNamespace_Member(), theUML2Package_1.getNamedElement(), null, "member", null, 0, -1, Namespace.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getNamespace_OwnedRule(), theUML2Package_1.getConstraint(), theUML2Package_1.getConstraint_Namespace(), "ownedRule", null, 0, -1, Namespace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getNamespace_ImportedMember(), theUML2Package_1.getPackageableElement(), null, "importedMember", null, 0, -1, Namespace.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getNamespace_ElementImport(), theUML2Package_1.getElementImport(), theUML2Package_1.getElementImport_ImportingNamespace(), "elementImport", null, 0, -1, Namespace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getNamespace_PackageImport(), theUML2Package_1.getPackageImport(), theUML2Package_1.getPackageImport_ImportingNamespace(), "packageImport", null, 0, -1, Namespace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(namespaceEClass, theUML2Package_1.getBoolean(), "validateMembersAreDistinguishable"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(namespaceEClass, theUML2Package_1.getSet(), "getNamesOfMember"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getNamedElement(), "element"); //$NON-NLS-1$

		addEOperation(namespaceEClass, theUML2Package_1.getBoolean(), "membersAreDistinguishable"); //$NON-NLS-1$

		op = addEOperation(namespaceEClass, theUML2Package_1.getBoolean(), "validateImportedMemberDerived"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		addEOperation(namespaceEClass, theUML2Package_1.getSet(), "importedMember"); //$NON-NLS-1$

		op = addEOperation(namespaceEClass, theUML2Package_1.getSet(), "importMembers"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getSet(), "imps"); //$NON-NLS-1$

		op = addEOperation(namespaceEClass, theUML2Package_1.getSet(), "excludeCollisions"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getSet(), "imps"); //$NON-NLS-1$

		initEClass(opaqueExpressionEClass, OpaqueExpression.class, "OpaqueExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getOpaqueExpression_Body(), theUML2Package_1.getString(), "body", "", 0, 1, OpaqueExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getOpaqueExpression_Language(), theUML2Package_1.getString(), "language", "", 0, 1, OpaqueExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getOpaqueExpression_Result(), theUML2Package_1.getParameter(), null, "result", null, 0, 1, OpaqueExpression.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getOpaqueExpression_Behavior(), theUML2Package_1.getBehavior(), null, "behavior", null, 0, 1, OpaqueExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(valueSpecificationEClass, ValueSpecification.class, "ValueSpecification", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		addEOperation(valueSpecificationEClass, theUML2Package_1.getBoolean(), "isComputable"); //$NON-NLS-1$

		addEOperation(valueSpecificationEClass, theUML2Package_1.getInteger(), "integerValue"); //$NON-NLS-1$

		addEOperation(valueSpecificationEClass, theUML2Package_1.getBoolean(), "booleanValue"); //$NON-NLS-1$

		addEOperation(valueSpecificationEClass, theUML2Package_1.getString(), "stringValue"); //$NON-NLS-1$

		addEOperation(valueSpecificationEClass, theUML2Package_1.getUnlimitedNatural(), "unlimitedValue"); //$NON-NLS-1$

		addEOperation(valueSpecificationEClass, theUML2Package_1.getBoolean(), "isNull"); //$NON-NLS-1$

		initEClass(expressionEClass, Expression.class, "Expression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getExpression_Symbol(), theUML2Package_1.getString(), "symbol", "", 0, 1, Expression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getExpression_Operand(), theUML2Package_1.getValueSpecification(), null, "operand", null, 0, -1, Expression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(commentEClass, Comment.class, "Comment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getComment_Body(), theUML2Package_1.getString(), "body", "", 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getComment_AnnotatedElement(), theUML2Package_1.getElement(), null, "annotatedElement", null, 0, -1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getComment_BodyExpression(), theUML2Package_1.getStringExpression(), null, "bodyExpression", null, 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getComment_BinaryData(), theEcorePackage.getEByteArray(), "binaryData", null, 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getComment_BinaryFormat(), theUML2Package_1.getString(), "binaryFormat", "", 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getComment_BinaryCount(), theUML2Package_1.getInteger(), "binaryCount", null, 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(directedRelationshipEClass, DirectedRelationship.class, "DirectedRelationship", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDirectedRelationship_Source(), theUML2Package_1.getElement(), null, "source", null, 1, -1, DirectedRelationship.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getDirectedRelationship_Target(), theUML2Package_1.getElement(), null, "target", null, 1, -1, DirectedRelationship.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(relationshipEClass, Relationship.class, "Relationship", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRelationship_RelatedElement(), theUML2Package_1.getElement(), null, "relatedElement", null, 1, -1, Relationship.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(classEClass, org.eclipse.uml2.Class.class, "Class", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getClass_OwnedOperation(), theUML2Package_1.getOperation(), theUML2Package_1.getOperation_Class_(), "ownedOperation", null, 0, -1, org.eclipse.uml2.Class.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getClass_SuperClass(), theUML2Package_1.getClass_(), null, "superClass", null, 0, -1, org.eclipse.uml2.Class.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClass_Extension(), theUML2Package_1.getExtension(), theUML2Package_1.getExtension_Metaclass(), "extension", null, 0, -1, org.eclipse.uml2.Class.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClass_NestedClassifier(), theUML2Package_1.getClassifier(), null, "nestedClassifier", null, 0, -1, org.eclipse.uml2.Class.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getClass_IsActive(), theUML2Package_1.getBoolean(), "isActive", null, 0, 1, org.eclipse.uml2.Class.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getClass_OwnedReception(), theUML2Package_1.getReception(), null, "ownedReception", null, 0, -1, org.eclipse.uml2.Class.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getClass_ComponentKind(), theUML2Package_1.getComponentKind(), "componentKind", null, 0, 1, org.eclipse.uml2.Class.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(typeEClass, Type.class, "Type", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getType_Package(), theUML2Package_1.getPackage(), theUML2Package_1.getPackage_OwnedType(), "package", null, 0, 1, Type.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getType_IsRetired(), theUML2Package_1.getBoolean(), "isRetired", null, 0, 1, Type.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(typeEClass, theUML2Package_1.getBoolean(), "conformsTo"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getType(), "other"); //$NON-NLS-1$

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getProperty_Default(), theUML2Package_1.getString(), "default", "", 0, 1, Property.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getProperty_IsComposite(), theUML2Package_1.getBoolean(), "isComposite", "false", 0, 1, Property.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getProperty_IsDerived(), theUML2Package_1.getBoolean(), "isDerived", "false", 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getProperty_Class_(), theUML2Package_1.getClass_(), null, "class_", null, 0, 1, Property.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getProperty_Opposite(), theUML2Package_1.getProperty(), null, "opposite", null, 0, 1, Property.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getProperty_IsDerivedUnion(), theUML2Package_1.getBoolean(), "isDerivedUnion", "false", 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getProperty_OwningAssociation(), theUML2Package_1.getAssociation(), theUML2Package_1.getAssociation_OwnedEnd(), "owningAssociation", null, 0, 1, Property.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getProperty_RedefinedProperty(), theUML2Package_1.getProperty(), null, "redefinedProperty", null, 0, -1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getProperty_SubsettedProperty(), theUML2Package_1.getProperty(), null, "subsettedProperty", null, 0, -1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getProperty_Datatype(), theUML2Package_1.getDataType(), theUML2Package_1.getDataType_OwnedAttribute(), "datatype", null, 0, 1, Property.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getProperty_Association(), theUML2Package_1.getAssociation(), theUML2Package_1.getAssociation_MemberEnd(), "association", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getProperty_Aggregation(), theUML2Package_1.getAggregationKind(), "aggregation", "none", 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getProperty_DefaultValue(), theUML2Package_1.getValueSpecification(), null, "defaultValue", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getProperty_Qualifier(), theUML2Package_1.getProperty(), theUML2Package_1.getProperty_AssociationEnd(), "qualifier", null, 0, -1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getProperty_AssociationEnd(), theUML2Package_1.getProperty(), theUML2Package_1.getProperty_Qualifier(), "associationEnd", null, 0, 1, Property.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getProperty_OwnedAnonymousType(), theUML2Package_1.getType(), null, "ownedAnonymousType", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getProperty_DefaultValues(), theUML2Package_1.getValueSpecification(), null, "defaultValues", null, 0, -1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(propertyEClass, theUML2Package_1.getBoolean(), "validateOppositeIsOtherEnd"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		addEOperation(propertyEClass, theUML2Package_1.getProperty(), "opposite"); //$NON-NLS-1$

		op = addEOperation(propertyEClass, theUML2Package_1.getBoolean(), "validateMultiplicityOfComposite"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(propertyEClass, theUML2Package_1.getBoolean(), "validateSubsettingContext"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(propertyEClass, theUML2Package_1.getBoolean(), "validateNavigablePropertyRedefinition"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(propertyEClass, theUML2Package_1.getBoolean(), "validateSubsettingRules"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(propertyEClass, theUML2Package_1.getBoolean(), "validateNavigableReadonly"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(propertyEClass, theUML2Package_1.getBoolean(), "validateDerivedUnionIsDerived"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		addEOperation(propertyEClass, theUML2Package_1.getSet(), "subsettingContext"); //$NON-NLS-1$

		initEClass(operationEClass, Operation.class, "Operation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getOperation_OwnedParameter(), theUML2Package_1.getParameter(), theUML2Package_1.getParameter_Operation(), "ownedParameter", null, 0, -1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getOperation_Class_(), theUML2Package_1.getClass_(), theUML2Package_1.getClass_OwnedOperation(), "class_", null, 0, 1, Operation.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getOperation_IsQuery(), theUML2Package_1.getBoolean(), "isQuery", "false", 0, 1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getOperation_Datatype(), theUML2Package_1.getDataType(), theUML2Package_1.getDataType_OwnedOperation(), "datatype", null, 0, 1, Operation.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getOperation_Precondition(), theUML2Package_1.getConstraint(), null, "precondition", null, 0, -1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getOperation_Postcondition(), theUML2Package_1.getConstraint(), null, "postcondition", null, 0, -1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getOperation_RedefinedOperation(), theUML2Package_1.getOperation(), null, "redefinedOperation", null, 0, -1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getOperation_BodyCondition(), theUML2Package_1.getConstraint(), null, "bodyCondition", null, 0, 1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(operationEClass, theUML2Package_1.getBoolean(), "validateTypeOfResult"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		addEOperation(operationEClass, theUML2Package_1.getBoolean(), "isOrdered"); //$NON-NLS-1$

		addEOperation(operationEClass, theUML2Package_1.getBoolean(), "isUnique"); //$NON-NLS-1$

		addEOperation(operationEClass, theUML2Package_1.getClassifier(), "type"); //$NON-NLS-1$

		op = addEOperation(operationEClass, theUML2Package_1.getBoolean(), "validateOnlyBodyForQuery"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		initEClass(typedElementEClass, TypedElement.class, "TypedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTypedElement_Type(), theUML2Package_1.getType(), null, "type", null, 0, 1, TypedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getParameter_Operation(), theUML2Package_1.getOperation(), theUML2Package_1.getOperation_OwnedParameter(), "operation", null, 0, 1, Parameter.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getParameter_Default(), theUML2Package_1.getString(), "default", "", 0, 1, Parameter.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getParameter_Direction(), theUML2Package_1.getParameterDirectionKind(), "direction", "in", 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getParameter_DefaultValue(), theUML2Package_1.getValueSpecification(), null, "defaultValue", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getParameter_IsException(), theUML2Package_1.getBoolean(), "isException", "false", 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getParameter_IsStream(), theUML2Package_1.getBoolean(), "isStream", "false", 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getParameter_Effect(), theUML2Package_1.getParameterEffectKind(), "effect", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getParameter_ParameterSet(), theUML2Package_1.getParameterSet(), theUML2Package_1.getParameterSet_Parameter(), "parameterSet", null, 0, -1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(packageEClass, org.eclipse.uml2.Package.class, "Package", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getPackage_NestedPackage(), theUML2Package_1.getPackage(), theUML2Package_1.getPackage_NestingPackage(), "nestedPackage", null, 0, -1, org.eclipse.uml2.Package.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackage_NestingPackage(), theUML2Package_1.getPackage(), theUML2Package_1.getPackage_NestedPackage(), "nestingPackage", null, 0, 1, org.eclipse.uml2.Package.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackage_OwnedType(), theUML2Package_1.getType(), theUML2Package_1.getType_Package(), "ownedType", null, 0, -1, org.eclipse.uml2.Package.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackage_OwnedMember(), theUML2Package_1.getPackageableElement(), null, "ownedMember", null, 0, -1, org.eclipse.uml2.Package.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackage_PackageMerge(), theUML2Package_1.getPackageMerge(), theUML2Package_1.getPackageMerge_MergingPackage(), "packageMerge", null, 0, -1, org.eclipse.uml2.Package.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackage_AppliedProfile(), theUML2Package_1.getProfileApplication(), null, "appliedProfile", null, 0, -1, org.eclipse.uml2.Package.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackage_PackageExtension(), theUML2Package_1.getPackageMerge(), null, "packageExtension", null, 0, -1, org.eclipse.uml2.Package.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackage_J_diagramHolder(), theUML2Package_1.getJ_DiagramHolder(), null, "j_diagramHolder", null, 0, 1, org.eclipse.uml2.Package.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackage_ChildPackages(), theUML2Package_1.getPackage(), theUML2Package_1.getPackage_ParentPackage(), "childPackages", null, 0, -1, org.eclipse.uml2.Package.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackage_ParentPackage(), theUML2Package_1.getPackage(), theUML2Package_1.getPackage_ChildPackages(), "parentPackage", null, 0, 1, org.eclipse.uml2.Package.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPackage_ReadOnly(), theUML2Package_1.getBoolean(), "readOnly", null, 0, 1, org.eclipse.uml2.Package.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackage_AnonymousDeletedImportPlaceholders(), theUML2Package_1.getElement(), null, "anonymousDeletedImportPlaceholders", null, 0, -1, org.eclipse.uml2.Package.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(packageEClass, theUML2Package_1.getBoolean(), "validateElementsPublicOrPrivate"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		addEOperation(packageEClass, theUML2Package_1.getSet(), "visibleMembers"); //$NON-NLS-1$

		op = addEOperation(packageEClass, theUML2Package_1.getBoolean(), "makesVisible"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getNamedElement(), "el"); //$NON-NLS-1$

		initEClass(enumerationEClass, Enumeration.class, "Enumeration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEnumeration_OwnedLiteral(), theUML2Package_1.getEnumerationLiteral(), theUML2Package_1.getEnumerationLiteral_Enumeration(), "ownedLiteral", null, 0, -1, Enumeration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(dataTypeEClass, DataType.class, "DataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDataType_OwnedAttribute(), theUML2Package_1.getProperty(), theUML2Package_1.getProperty_Datatype(), "ownedAttribute", null, 0, -1, DataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getDataType_OwnedOperation(), theUML2Package_1.getOperation(), theUML2Package_1.getOperation_Datatype(), "ownedOperation", null, 0, -1, DataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(enumerationLiteralEClass, EnumerationLiteral.class, "EnumerationLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEnumerationLiteral_Enumeration(), theUML2Package_1.getEnumeration(), theUML2Package_1.getEnumeration_OwnedLiteral(), "enumeration", null, 0, 1, EnumerationLiteral.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(primitiveTypeEClass, PrimitiveType.class, "PrimitiveType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(classifierEClass, Classifier.class, "Classifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getClassifier_Feature(), theUML2Package_1.getFeature(), theUML2Package_1.getFeature_FeaturingClassifier(), "feature", null, 0, -1, Classifier.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getClassifier_IsAbstract(), theUML2Package_1.getBoolean(), "isAbstract", "false", 0, 1, Classifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getClassifier_InheritedMember(), theUML2Package_1.getNamedElement(), null, "inheritedMember", null, 0, -1, Classifier.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClassifier_General(), theUML2Package_1.getClassifier(), null, "general", null, 0, -1, Classifier.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClassifier_Generalization(), theUML2Package_1.getGeneralization(), theUML2Package_1.getGeneralization_Specific(), "generalization", null, 0, -1, Classifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClassifier_Attribute(), theUML2Package_1.getProperty(), null, "attribute", null, 0, -1, Classifier.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClassifier_RedefinedClassifier(), theUML2Package_1.getClassifier(), null, "redefinedClassifier", null, 0, -1, Classifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClassifier_Substitution(), theUML2Package_1.getSubstitution(), theUML2Package_1.getSubstitution_SubstitutingClassifier(), "substitution", null, 0, -1, Classifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClassifier_PowertypeExtent(), theUML2Package_1.getGeneralizationSet(), theUML2Package_1.getGeneralizationSet_Powertype(), "powertypeExtent", null, 0, -1, Classifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClassifier_OwnedUseCase(), theUML2Package_1.getUseCase(), null, "ownedUseCase", null, 0, -1, Classifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClassifier_UseCase(), theUML2Package_1.getUseCase(), theUML2Package_1.getUseCase_Subject(), "useCase", null, 0, -1, Classifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClassifier_Representation(), theUML2Package_1.getCollaborationOccurrence(), null, "representation", null, 0, 1, Classifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getClassifier_Occurrence(), theUML2Package_1.getCollaborationOccurrence(), null, "occurrence", null, 0, -1, Classifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		addEOperation(classifierEClass, theUML2Package_1.getSet(), "allFeatures"); //$NON-NLS-1$

		op = addEOperation(classifierEClass, theUML2Package_1.getBoolean(), "validateNoCyclesInGeneralization"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(classifierEClass, theUML2Package_1.getBoolean(), "validateSpecializeType"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(classifierEClass, theUML2Package_1.getBoolean(), "validateInheritedMember"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		addEOperation(classifierEClass, theUML2Package_1.getSet(), "inheritedMember"); //$NON-NLS-1$

		addEOperation(classifierEClass, theUML2Package_1.getSet(), "parents"); //$NON-NLS-1$

		addEOperation(classifierEClass, theUML2Package_1.getSet(), "allParents"); //$NON-NLS-1$

		op = addEOperation(classifierEClass, theUML2Package_1.getSet(), "inheritableMembers"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getClassifier(), "c"); //$NON-NLS-1$

		op = addEOperation(classifierEClass, theUML2Package_1.getBoolean(), "hasVisibilityOf"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getNamedElement(), "n"); //$NON-NLS-1$

		op = addEOperation(classifierEClass, theUML2Package_1.getSet(), "inherit"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getSet(), "inhs"); //$NON-NLS-1$

		op = addEOperation(classifierEClass, theUML2Package_1.getBoolean(), "maySpecializeType"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getClassifier(), "c"); //$NON-NLS-1$

		addEOperation(classifierEClass, theUML2Package_1.getSet(), "general"); //$NON-NLS-1$

		op = addEOperation(classifierEClass, theUML2Package_1.getBoolean(), "validateGeneralEqualsParents"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(classifierEClass, theUML2Package_1.getBoolean(), "conformsTo"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getClassifier(), "other"); //$NON-NLS-1$

		initEClass(featureEClass, Feature.class, "Feature", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getFeature_FeaturingClassifier(), theUML2Package_1.getClassifier(), theUML2Package_1.getClassifier_Feature(), "featuringClassifier", null, 0, -1, Feature.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getFeature_IsStatic(), theUML2Package_1.getBoolean(), "isStatic", "false", 0, 1, Feature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(constraintEClass, Constraint.class, "Constraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getConstraint_Context(), theUML2Package_1.getNamespace(), null, "context", null, 0, 1, Constraint.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getConstraint_Namespace(), theUML2Package_1.getNamespace(), theUML2Package_1.getNamespace_OwnedRule(), "namespace", null, 0, 1, Constraint.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getConstraint_Specification(), theUML2Package_1.getValueSpecification(), null, "specification", null, 1, 1, Constraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getConstraint_ConstrainedElement(), theUML2Package_1.getElement(), null, "constrainedElement", null, 0, -1, Constraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(constraintEClass, theUML2Package_1.getBoolean(), "validateNotApplyToSelf"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		initEClass(literalBooleanEClass, LiteralBoolean.class, "LiteralBoolean", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getLiteralBoolean_Value(), theUML2Package_1.getBoolean(), "value", null, 0, 1, LiteralBoolean.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(literalSpecificationEClass, LiteralSpecification.class, "LiteralSpecification", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(literalStringEClass, LiteralString.class, "LiteralString", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getLiteralString_Value(), theUML2Package_1.getString(), "value", "", 0, 1, LiteralString.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(literalNullEClass, LiteralNull.class, "LiteralNull", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(literalIntegerEClass, LiteralInteger.class, "LiteralInteger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getLiteralInteger_Value(), theUML2Package_1.getInteger(), "value", null, 0, 1, LiteralInteger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(literalUnlimitedNaturalEClass, LiteralUnlimitedNatural.class, "LiteralUnlimitedNatural", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getLiteralUnlimitedNatural_Value(), theUML2Package_1.getUnlimitedNatural(), "value", null, 0, 1, LiteralUnlimitedNatural.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(behavioralFeatureEClass, BehavioralFeature.class, "BehavioralFeature", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getBehavioralFeature_Parameter(), theUML2Package_1.getParameter(), null, "parameter", null, 0, -1, BehavioralFeature.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavioralFeature_FormalParameter(), theUML2Package_1.getParameter(), null, "formalParameter", null, 0, -1, BehavioralFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavioralFeature_ReturnResult(), theUML2Package_1.getParameter(), null, "returnResult", null, 0, -1, BehavioralFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavioralFeature_RaisedException(), theUML2Package_1.getType(), null, "raisedException", null, 0, -1, BehavioralFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getBehavioralFeature_IsAbstract(), theUML2Package_1.getBoolean(), "isAbstract", null, 0, 1, BehavioralFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavioralFeature_Method(), theUML2Package_1.getBehavior(), theUML2Package_1.getBehavior_Specification(), "method", null, 0, -1, BehavioralFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getBehavioralFeature_Concurrency(), theUML2Package_1.getCallConcurrencyKind(), "concurrency", null, 0, 1, BehavioralFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(structuralFeatureEClass, StructuralFeature.class, "StructuralFeature", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getStructuralFeature_ReadWrite(), theUML2Package_1.getPropertyAccessKind(), "readWrite", null, 0, 1, StructuralFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(instanceSpecificationEClass, InstanceSpecification.class, "InstanceSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInstanceSpecification_Slot(), theUML2Package_1.getSlot(), theUML2Package_1.getSlot_OwningInstance(), "slot", null, 0, -1, InstanceSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getInstanceSpecification_Classifier(), theUML2Package_1.getClassifier(), null, "classifier", null, 0, -1, InstanceSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getInstanceSpecification_Specification(), theUML2Package_1.getValueSpecification(), null, "specification", null, 0, 1, InstanceSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInstanceSpecification_PortRemap(), theUML2Package_1.getPortRemap(), null, "portRemap", null, 0, -1, InstanceSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(instanceSpecificationEClass, theUML2Package_1.getBoolean(), "validateSlotsAreDefined"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(instanceSpecificationEClass, theUML2Package_1.getBoolean(), "validateNoDuplicateSlots"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		initEClass(slotEClass, Slot.class, "Slot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSlot_OwningInstance(), theUML2Package_1.getInstanceSpecification(), theUML2Package_1.getInstanceSpecification_Slot(), "owningInstance", null, 1, 1, Slot.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSlot_Value(), theUML2Package_1.getValueSpecification(), null, "value", null, 0, -1, Slot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSlot_DefiningFeature(), theUML2Package_1.getStructuralFeature(), null, "definingFeature", null, 1, 1, Slot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(instanceValueEClass, InstanceValue.class, "InstanceValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInstanceValue_Instance(), theUML2Package_1.getInstanceSpecification(), null, "instance", null, 1, 1, InstanceValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInstanceValue_OwnedAnonymousInstanceValue(), theUML2Package_1.getInstanceSpecification(), null, "ownedAnonymousInstanceValue", null, 0, 1, InstanceValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(redefinableElementEClass, RedefinableElement.class, "RedefinableElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRedefinableElement_RedefinitionContext(), theUML2Package_1.getClassifier(), null, "redefinitionContext", null, 0, -1, RedefinableElement.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getRedefinableElement_IsLeaf(), theUML2Package_1.getBoolean(), "isLeaf", "false", 0, 1, RedefinableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		op = addEOperation(redefinableElementEClass, theUML2Package_1.getBoolean(), "validateRedefinitionContextValid"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(redefinableElementEClass, theUML2Package_1.getBoolean(), "validateRedefinitionConsistent"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(redefinableElementEClass, theUML2Package_1.getBoolean(), "isConsistentWith"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getRedefinableElement(), "redefinee"); //$NON-NLS-1$

		op = addEOperation(redefinableElementEClass, theUML2Package_1.getBoolean(), "isRedefinitionContextValid"); //$NON-NLS-1$
		addEParameter(op, theUML2Package_1.getRedefinableElement(), "redefinable"); //$NON-NLS-1$

		initEClass(generalizationEClass, Generalization.class, "Generalization", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getGeneralization_Specific(), theUML2Package_1.getClassifier(), theUML2Package_1.getClassifier_Generalization(), "specific", null, 1, 1, Generalization.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGeneralization_General(), theUML2Package_1.getClassifier(), null, "general", null, 1, 1, Generalization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getGeneralization_IsSubstitutable(), theUML2Package_1.getBoolean(), "isSubstitutable", null, 0, 1, Generalization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGeneralization_GeneralizationSet(), theUML2Package_1.getGeneralizationSet(), theUML2Package_1.getGeneralizationSet_Generalization(), "generalizationSet", null, 0, -1, Generalization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(packageableElementEClass, PackageableElement.class, "PackageableElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPackageableElement_PackageableElement_visibility(), theUML2Package_1.getVisibilityKind(), "packageableElement_visibility", null, 0, 1, PackageableElement.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(elementImportEClass, ElementImport.class, "ElementImport", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getElementImport_Visibility(), theUML2Package_1.getVisibilityKind(), "visibility", null, 0, 1, ElementImport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getElementImport_Alias(), theUML2Package_1.getString(), "alias", "", 0, 1, ElementImport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getElementImport_ImportedElement(), theUML2Package_1.getPackageableElement(), null, "importedElement", null, 1, 1, ElementImport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getElementImport_ImportingNamespace(), theUML2Package_1.getNamespace(), theUML2Package_1.getNamespace_ElementImport(), "importingNamespace", null, 1, 1, ElementImport.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(elementImportEClass, theUML2Package_1.getBoolean(), "validateVisibilityPublicOrPrivate"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		op = addEOperation(elementImportEClass, theUML2Package_1.getBoolean(), "validateImportedElementIsPublic"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		addEOperation(elementImportEClass, theUML2Package_1.getString(), "getName"); //$NON-NLS-1$

		initEClass(packageImportEClass, PackageImport.class, "PackageImport", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPackageImport_Visibility(), theUML2Package_1.getVisibilityKind(), "visibility", null, 0, 1, PackageImport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackageImport_ImportedPackage(), theUML2Package_1.getPackage(), null, "importedPackage", null, 1, 1, PackageImport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackageImport_ImportingNamespace(), theUML2Package_1.getNamespace(), theUML2Package_1.getNamespace_PackageImport(), "importingNamespace", null, 1, 1, PackageImport.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(packageImportEClass, theUML2Package_1.getBoolean(), "validatePublicOrPrivate"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics"); //$NON-NLS-1$
		addEParameter(op, ecorePackage.getEMap(), "context"); //$NON-NLS-1$

		initEClass(associationEClass, Association.class, "Association", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAssociation_IsDerived(), theUML2Package_1.getBoolean(), "isDerived", "false", 0, 1, Association.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getAssociation_OwnedEnd(), theUML2Package_1.getProperty(), theUML2Package_1.getProperty_OwningAssociation(), "ownedEnd", null, 0, -1, Association.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAssociation_EndType(), theUML2Package_1.getType(), null, "endType", null, 1, -1, Association.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getAssociation_MemberEnd(), theUML2Package_1.getProperty(), theUML2Package_1.getProperty_Association(), "memberEnd", null, 2, -1, Association.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(packageMergeEClass, PackageMerge.class, "PackageMerge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getPackageMerge_MergingPackage(), theUML2Package_1.getPackage(), theUML2Package_1.getPackage_PackageMerge(), "mergingPackage", null, 1, 1, PackageMerge.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getPackageMerge_MergedPackage(), theUML2Package_1.getPackage(), null, "mergedPackage", null, 1, 1, PackageMerge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stereotypeEClass, Stereotype.class, "Stereotype", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getStereotype_ExtendsMetaModelElement(), theUML2Package_1.getString(), "extendsMetaModelElement", "", 0, 1, Stereotype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(profileEClass, Profile.class, "Profile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getProfile_OwnedStereotype(), theUML2Package_1.getStereotype(), null, "ownedStereotype", null, 0, -1, Profile.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getProfile_MetaclassReference(), theUML2Package_1.getElementImport(), null, "metaclassReference", null, 0, -1, Profile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getProfile_MetamodelReference(), theUML2Package_1.getPackageImport(), null, "metamodelReference", null, 0, -1, Profile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(profileApplicationEClass, ProfileApplication.class, "ProfileApplication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getProfileApplication_ImportedProfile(), theUML2Package_1.getProfile(), null, "importedProfile", null, 1, 1, ProfileApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(extensionEClass, Extension.class, "Extension", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getExtension_IsRequired(), theUML2Package_1.getBoolean(), "isRequired", null, 0, 1, Extension.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getExtension_Metaclass(), theUML2Package_1.getClass_(), theUML2Package_1.getClass_Extension(), "metaclass", null, 1, 1, Extension.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(extensionEndEClass, ExtensionEnd.class, "ExtensionEnd", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(behaviorEClass, Behavior.class, "Behavior", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getBehavior_IsReentrant(), theUML2Package_1.getBoolean(), "isReentrant", null, 0, 1, Behavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavior_Context(), theUML2Package_1.getBehavioredClassifier(), theUML2Package_1.getBehavioredClassifier_OwnedBehavior(), "context", null, 0, 1, Behavior.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavior_RedefinedBehavior(), theUML2Package_1.getBehavior(), null, "redefinedBehavior", null, 0, -1, Behavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavior_Specification(), theUML2Package_1.getBehavioralFeature(), theUML2Package_1.getBehavioralFeature_Method(), "specification", null, 0, 1, Behavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavior_Parameter(), theUML2Package_1.getParameter(), null, "parameter", null, 0, -1, Behavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavior_FormalParameter(), theUML2Package_1.getParameter(), null, "formalParameter", null, 0, -1, Behavior.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavior_ReturnResult(), theUML2Package_1.getParameter(), null, "returnResult", null, 0, -1, Behavior.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavior_Precondition(), theUML2Package_1.getConstraint(), null, "precondition", null, 0, -1, Behavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavior_Postcondition(), theUML2Package_1.getConstraint(), null, "postcondition", null, 0, -1, Behavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavior_OwnedParameterSet(), theUML2Package_1.getParameterSet(), null, "ownedParameterSet", null, 0, -1, Behavior.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(behavioredClassifierEClass, BehavioredClassifier.class, "BehavioredClassifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getBehavioredClassifier_OwnedBehavior(), theUML2Package_1.getBehavior(), theUML2Package_1.getBehavior_Context(), "ownedBehavior", null, 0, -1, BehavioredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavioredClassifier_ClassifierBehavior(), theUML2Package_1.getBehavior(), null, "classifierBehavior", null, 0, 1, BehavioredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavioredClassifier_Implementation(), theUML2Package_1.getImplementation(), theUML2Package_1.getImplementation_ImplementingClassifier(), "implementation", null, 0, -1, BehavioredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavioredClassifier_OwnedTrigger(), theUML2Package_1.getTrigger(), null, "ownedTrigger", null, 0, -1, BehavioredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getBehavioredClassifier_OwnedStateMachine(), theUML2Package_1.getStateMachine(), theUML2Package_1.getStateMachine_StateMachine_redefinitionContext(), "ownedStateMachine", null, 0, -1, BehavioredClassifier.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(activityEClass, Activity.class, "Activity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getActivity_Body(), theUML2Package_1.getString(), "body", "", 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getActivity_Language(), theUML2Package_1.getString(), "language", "", 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getActivity_Edge(), theUML2Package_1.getActivityEdge(), theUML2Package_1.getActivityEdge_Activity(), "edge", null, 0, -1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivity_Group(), theUML2Package_1.getActivityGroup(), theUML2Package_1.getActivityGroup_ActivityGroup_activity(), "group", null, 0, -1, Activity.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivity_Node(), theUML2Package_1.getActivityNode(), theUML2Package_1.getActivityNode_Activity(), "node", null, 0, -1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivity_Action(), theUML2Package_1.getAction(), null, "action", null, 0, -1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivity_StructuredNode(), theUML2Package_1.getStructuredActivityNode(), null, "structuredNode", null, 0, -1, Activity.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getActivity_IsSingleExecution(), theUML2Package_1.getBoolean(), "isSingleExecution", null, 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getActivity_IsReadOnly(), theUML2Package_1.getBoolean(), "isReadOnly", "false", 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(permissionEClass, Permission.class, "Permission", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(dependencyEClass, Dependency.class, "Dependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDependency_Client(), theUML2Package_1.getNamedElement(), theUML2Package_1.getNamedElement_ClientDependency(), "client", null, 1, -1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getDependency_Supplier(), theUML2Package_1.getNamedElement(), null, "supplier", null, 1, -1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getDependency_DependencyTarget(), theUML2Package_1.getNamedElement(), null, "dependencyTarget", null, 0, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getDependency_Resemblance(), theUML2Package_1.getBoolean(), "resemblance", null, 0, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getDependency_Replacement(), theUML2Package_1.getBoolean(), "replacement", null, 0, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getDependency_Trace(), theUML2Package_1.getBoolean(), "trace", null, 0, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(usageEClass, Usage.class, "Usage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(abstractionEClass, Abstraction.class, "Abstraction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAbstraction_Mapping(), theUML2Package_1.getOpaqueExpression(), null, "mapping", null, 0, 1, Abstraction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(realizationEClass, Realization.class, "Realization", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRealization_Abstraction(), theUML2Package_1.getComponent(), theUML2Package_1.getComponent_Realization(), "abstraction", null, 0, 1, Realization.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getRealization_RealizingClassifier(), theUML2Package_1.getClassifier(), null, "realizingClassifier", null, 1, 1, Realization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(substitutionEClass, Substitution.class, "Substitution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSubstitution_Contract(), theUML2Package_1.getClassifier(), null, "contract", null, 1, 1, Substitution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSubstitution_SubstitutingClassifier(), theUML2Package_1.getClassifier(), theUML2Package_1.getClassifier_Substitution(), "substitutingClassifier", null, 1, 1, Substitution.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(generalizationSetEClass, GeneralizationSet.class, "GeneralizationSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getGeneralizationSet_IsCovering(), theUML2Package_1.getBoolean(), "isCovering", null, 0, 1, GeneralizationSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getGeneralizationSet_IsDisjoint(), theUML2Package_1.getBoolean(), "isDisjoint", null, 0, 1, GeneralizationSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGeneralizationSet_Powertype(), theUML2Package_1.getClassifier(), theUML2Package_1.getClassifier_PowertypeExtent(), "powertype", null, 0, 1, GeneralizationSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGeneralizationSet_Generalization(), theUML2Package_1.getGeneralization(), theUML2Package_1.getGeneralization_GeneralizationSet(), "generalization", null, 0, -1, GeneralizationSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(associationClassEClass, AssociationClass.class, "AssociationClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(informationItemEClass, InformationItem.class, "InformationItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInformationItem_Represented(), theUML2Package_1.getClassifier(), null, "represented", null, 0, -1, InformationItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(informationFlowEClass, InformationFlow.class, "InformationFlow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInformationFlow_Realization(), theUML2Package_1.getRelationship(), null, "realization", null, 0, -1, InformationFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getInformationFlow_Conveyed(), theUML2Package_1.getClassifier(), null, "conveyed", null, 1, -1, InformationFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(modelEClass, Model.class, "Model", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getModel_Viewpoint(), theUML2Package_1.getString(), "viewpoint", "", 0, 1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(connectorEndEClass, ConnectorEnd.class, "ConnectorEnd", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getConnectorEnd_DefiningEnd(), theUML2Package_1.getProperty(), null, "definingEnd", null, 0, 1, ConnectorEnd.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getConnectorEnd_Role(), theUML2Package_1.getConnectableElement(), theUML2Package_1.getConnectableElement_End(), "role", null, 0, 1, ConnectorEnd.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getConnectorEnd_PartWithPort(), theUML2Package_1.getProperty(), null, "partWithPort", null, 0, 1, ConnectorEnd.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(connectableElementEClass, ConnectableElement.class, "ConnectableElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getConnectableElement_End(), theUML2Package_1.getConnectorEnd(), theUML2Package_1.getConnectorEnd_Role(), "end", null, 0, -1, ConnectableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(connectorEClass, Connector.class, "Connector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getConnector_Type(), theUML2Package_1.getAssociation(), null, "type", null, 0, 1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getConnector_RedefinedConnector(), theUML2Package_1.getConnector(), null, "redefinedConnector", null, 0, -1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getConnector_End(), theUML2Package_1.getConnectorEnd(), null, "end", null, 2, -1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getConnector_Kind(), theUML2Package_1.getConnectorKind(), "kind", null, 0, 1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getConnector_Contract(), theUML2Package_1.getBehavior(), null, "contract", null, 0, -1, Connector.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(structuredClassifierEClass, StructuredClassifier.class, "StructuredClassifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStructuredClassifier_OwnedAttribute(), theUML2Package_1.getProperty(), null, "ownedAttribute", null, 0, -1, StructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredClassifier_Part(), theUML2Package_1.getProperty(), null, "part", null, 0, -1, StructuredClassifier.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredClassifier_Role(), theUML2Package_1.getConnectableElement(), null, "role", null, 0, -1, StructuredClassifier.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredClassifier_OwnedConnector(), theUML2Package_1.getConnector(), null, "ownedConnector", null, 0, -1, StructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredClassifier_DeltaDeletedAttributes(), theUML2Package_1.getDeltaDeletedAttribute(), null, "deltaDeletedAttributes", null, 0, -1, StructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredClassifier_DeltaReplacedAttributes(), theUML2Package_1.getDeltaReplacedAttribute(), null, "deltaReplacedAttributes", "", 0, -1, StructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getStructuredClassifier_DeltaDeletedPorts(), theUML2Package_1.getDeltaDeletedPort(), null, "deltaDeletedPorts", null, 0, -1, StructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredClassifier_DeltaReplacedPorts(), theUML2Package_1.getDeltaReplacedPort(), null, "deltaReplacedPorts", null, 0, -1, StructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredClassifier_DeltaDeletedConnectors(), theUML2Package_1.getDeltaDeletedConnector(), null, "deltaDeletedConnectors", null, 0, -1, StructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredClassifier_DeltaReplacedConnectors(), theUML2Package_1.getDeltaReplacedConnector(), null, "deltaReplacedConnectors", null, 0, -1, StructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredClassifier_DeltaDeletedOperations(), theUML2Package_1.getDeltaDeletedOperation(), null, "deltaDeletedOperations", null, 0, -1, StructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredClassifier_DeltaReplacedOperations(), theUML2Package_1.getDeltaReplacedOperation(), null, "deltaReplacedOperations", null, 0, -1, StructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredClassifier_DeltaDeletedTraces(), theUML2Package_1.getDeltaDeletedTrace(), null, "deltaDeletedTraces", null, 0, -1, StructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredClassifier_DeltaReplacedTraces(), theUML2Package_1.getDeltaReplacedTrace(), null, "deltaReplacedTraces", null, 0, -1, StructuredClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(activityEdgeEClass, ActivityEdge.class, "ActivityEdge", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getActivityEdge_Activity(), theUML2Package_1.getActivity(), theUML2Package_1.getActivity_Edge(), "activity", null, 0, 1, ActivityEdge.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityEdge_Source(), theUML2Package_1.getActivityNode(), theUML2Package_1.getActivityNode_Outgoing(), "source", null, 1, 1, ActivityEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityEdge_Target(), theUML2Package_1.getActivityNode(), theUML2Package_1.getActivityNode_Incoming(), "target", null, 1, 1, ActivityEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityEdge_InGroup(), theUML2Package_1.getActivityGroup(), null, "inGroup", null, 0, -1, ActivityEdge.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityEdge_Guard(), theUML2Package_1.getValueSpecification(), null, "guard", null, 1, 1, ActivityEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityEdge_RedefinedElement(), theUML2Package_1.getActivityEdge(), null, "redefinedElement", null, 0, -1, ActivityEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityEdge_InStructuredNode(), theUML2Package_1.getStructuredActivityNode(), theUML2Package_1.getStructuredActivityNode_ContainedEdge(), "inStructuredNode", null, 0, 1, ActivityEdge.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityEdge_InPartition(), theUML2Package_1.getActivityPartition(), theUML2Package_1.getActivityPartition_ContainedEdge(), "inPartition", null, 0, -1, ActivityEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityEdge_Weight(), theUML2Package_1.getValueSpecification(), null, "weight", null, 1, 1, ActivityEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityEdge_Interrupts(), theUML2Package_1.getInterruptibleActivityRegion(), theUML2Package_1.getInterruptibleActivityRegion_InterruptingEdge(), "interrupts", null, 0, 1, ActivityEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(activityGroupEClass, ActivityGroup.class, "ActivityGroup", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getActivityGroup_SuperGroup(), theUML2Package_1.getActivityGroup(), null, "superGroup", null, 0, 1, ActivityGroup.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityGroup_ActivityGroup_activity(), theUML2Package_1.getActivity(), theUML2Package_1.getActivity_Group(), "activityGroup_activity", null, 0, 1, ActivityGroup.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(activityNodeEClass, ActivityNode.class, "ActivityNode", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getActivityNode_Outgoing(), theUML2Package_1.getActivityEdge(), theUML2Package_1.getActivityEdge_Source(), "outgoing", null, 0, -1, ActivityNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityNode_Incoming(), theUML2Package_1.getActivityEdge(), theUML2Package_1.getActivityEdge_Target(), "incoming", null, 0, -1, ActivityNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityNode_InGroup(), theUML2Package_1.getActivityGroup(), null, "inGroup", null, 0, -1, ActivityNode.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityNode_Activity(), theUML2Package_1.getActivity(), theUML2Package_1.getActivity_Node(), "activity", null, 0, 1, ActivityNode.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityNode_RedefinedElement(), theUML2Package_1.getActivityNode(), null, "redefinedElement", null, 0, -1, ActivityNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityNode_InStructuredNode(), theUML2Package_1.getStructuredActivityNode(), theUML2Package_1.getStructuredActivityNode_ContainedNode(), "inStructuredNode", null, 0, 1, ActivityNode.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityNode_InPartition(), theUML2Package_1.getActivityPartition(), theUML2Package_1.getActivityPartition_ContainedNode(), "inPartition", null, 0, -1, ActivityNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityNode_InInterruptibleRegion(), theUML2Package_1.getInterruptibleActivityRegion(), theUML2Package_1.getInterruptibleActivityRegion_ContainedNode(), "inInterruptibleRegion", null, 0, -1, ActivityNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(actionEClass, Action.class, "Action", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAction_Effect(), theUML2Package_1.getString(), "effect", "", 0, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getAction_Output(), theUML2Package_1.getOutputPin(), null, "output", null, 0, -1, Action.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAction_Input(), theUML2Package_1.getInputPin(), null, "input", null, 0, -1, Action.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAction_Context(), theUML2Package_1.getClassifier(), null, "context", null, 0, 1, Action.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAction_LocalPrecondition(), theUML2Package_1.getConstraint(), null, "localPrecondition", null, 0, -1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getAction_LocalPostcondition(), theUML2Package_1.getConstraint(), null, "localPostcondition", null, 0, -1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(objectNodeEClass, ObjectNode.class, "ObjectNode", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getObjectNode_Ordering(), theUML2Package_1.getObjectNodeOrderingKind(), "ordering", "FIFO", 0, 1, ObjectNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getObjectNode_UpperBound(), theUML2Package_1.getValueSpecification(), null, "upperBound", null, 1, 1, ObjectNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getObjectNode_InState(), theUML2Package_1.getState(), null, "inState", null, 0, -1, ObjectNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getObjectNode_Selection(), theUML2Package_1.getBehavior(), null, "selection", null, 0, 1, ObjectNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(controlNodeEClass, ControlNode.class, "ControlNode", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(controlFlowEClass, ControlFlow.class, "ControlFlow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(objectFlowEClass, ObjectFlow.class, "ObjectFlow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getObjectFlow_IsMulticast(), theUML2Package_1.getBoolean(), "isMulticast", "false", 0, 1, ObjectFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getObjectFlow_IsMultireceive(), theUML2Package_1.getBoolean(), "isMultireceive", "false", 0, 1, ObjectFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getObjectFlow_Transformation(), theUML2Package_1.getBehavior(), null, "transformation", null, 0, 1, ObjectFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getObjectFlow_Selection(), theUML2Package_1.getBehavior(), null, "selection", null, 0, 1, ObjectFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(initialNodeEClass, InitialNode.class, "InitialNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(finalNodeEClass, FinalNode.class, "FinalNode", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(activityFinalNodeEClass, ActivityFinalNode.class, "ActivityFinalNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(decisionNodeEClass, DecisionNode.class, "DecisionNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDecisionNode_DecisionInput(), theUML2Package_1.getBehavior(), null, "decisionInput", null, 0, 1, DecisionNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(mergeNodeEClass, MergeNode.class, "MergeNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(executableNodeEClass, ExecutableNode.class, "ExecutableNode", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getExecutableNode_Handler(), theUML2Package_1.getExceptionHandler(), theUML2Package_1.getExceptionHandler_ProtectedNode(), "handler", null, 0, -1, ExecutableNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(outputPinEClass, OutputPin.class, "OutputPin", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(inputPinEClass, InputPin.class, "InputPin", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(pinEClass, Pin.class, "Pin", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(activityParameterNodeEClass, ActivityParameterNode.class, "ActivityParameterNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getActivityParameterNode_Parameter(), theUML2Package_1.getParameter(), null, "parameter", null, 1, 1, ActivityParameterNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(valuePinEClass, ValuePin.class, "ValuePin", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getValuePin_Value(), theUML2Package_1.getValueSpecification(), null, "value", null, 1, 1, ValuePin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(interfaceEClass, Interface.class, "Interface", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInterface_OwnedAttribute(), theUML2Package_1.getProperty(), null, "ownedAttribute", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterface_OwnedOperation(), theUML2Package_1.getOperation(), null, "ownedOperation", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterface_RedefinedInterface(), theUML2Package_1.getInterface(), null, "redefinedInterface", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterface_NestedClassifier(), theUML2Package_1.getClassifier(), null, "nestedClassifier", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterface_OwnedReception(), theUML2Package_1.getReception(), null, "ownedReception", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterface_Protocol(), theUML2Package_1.getProtocolStateMachine(), null, "protocol", null, 0, 1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterface_DeltaDeletedOperations(), theUML2Package_1.getDeltaDeletedOperation(), null, "deltaDeletedOperations", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterface_DeltaReplacedOperations(), theUML2Package_1.getDeltaReplacedOperation(), null, "deltaReplacedOperations", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterface_DeltaDeletedAttributes(), theUML2Package_1.getDeltaDeletedAttribute(), null, "deltaDeletedAttributes", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterface_DeltaReplacedAttributes(), theUML2Package_1.getDeltaReplacedAttribute(), null, "deltaReplacedAttributes", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(implementationEClass, Implementation.class, "Implementation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getImplementation_Contract(), theUML2Package_1.getInterface(), null, "contract", null, 1, 1, Implementation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getImplementation_ImplementingClassifier(), theUML2Package_1.getBehavioredClassifier(), theUML2Package_1.getBehavioredClassifier_Implementation(), "implementingClassifier", null, 1, 1, Implementation.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(artifactEClass, Artifact.class, "Artifact", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getArtifact_FileName(), theUML2Package_1.getString(), "fileName", "", 0, 1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getArtifact_NestedArtifact(), theUML2Package_1.getArtifact(), null, "nestedArtifact", null, 0, -1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getArtifact_Manifestation(), theUML2Package_1.getManifestation(), null, "manifestation", null, 0, -1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getArtifact_OwnedOperation(), theUML2Package_1.getOperation(), null, "ownedOperation", null, 0, -1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getArtifact_OwnedAttribute(), theUML2Package_1.getProperty(), null, "ownedAttribute", null, 0, -1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(manifestationEClass, Manifestation.class, "Manifestation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getManifestation_UtilizedElement(), theUML2Package_1.getPackageableElement(), null, "utilizedElement", null, 1, 1, Manifestation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(actorEClass, Actor.class, "Actor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(extendEClass, Extend.class, "Extend", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getExtend_ExtendedCase(), theUML2Package_1.getUseCase(), null, "extendedCase", null, 1, 1, Extend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getExtend_Extension(), theUML2Package_1.getUseCase(), theUML2Package_1.getUseCase_Extend(), "extension", null, 1, 1, Extend.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getExtend_Condition(), theUML2Package_1.getConstraint(), null, "condition", null, 0, 1, Extend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getExtend_ExtensionLocation(), theUML2Package_1.getExtensionPoint(), null, "extensionLocation", null, 1, -1, Extend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(useCaseEClass, UseCase.class, "UseCase", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getUseCase_Include(), theUML2Package_1.getInclude(), theUML2Package_1.getInclude_IncludingCase(), "include", null, 0, -1, UseCase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getUseCase_Extend(), theUML2Package_1.getExtend(), theUML2Package_1.getExtend_Extension(), "extend", null, 0, -1, UseCase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getUseCase_ExtensionPoint(), theUML2Package_1.getExtensionPoint(), theUML2Package_1.getExtensionPoint_UseCase(), "extensionPoint", null, 0, -1, UseCase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getUseCase_Subject(), theUML2Package_1.getClassifier(), theUML2Package_1.getClassifier_UseCase(), "subject", null, 0, -1, UseCase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(extensionPointEClass, ExtensionPoint.class, "ExtensionPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getExtensionPoint_UseCase(), theUML2Package_1.getUseCase(), theUML2Package_1.getUseCase_ExtensionPoint(), "useCase", null, 1, 1, ExtensionPoint.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(includeEClass, Include.class, "Include", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInclude_IncludingCase(), theUML2Package_1.getUseCase(), theUML2Package_1.getUseCase_Include(), "includingCase", null, 1, 1, Include.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInclude_Addition(), theUML2Package_1.getUseCase(), null, "addition", null, 1, 1, Include.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(collaborationOccurrenceEClass, CollaborationOccurrence.class, "CollaborationOccurrence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getCollaborationOccurrence_Type(), theUML2Package_1.getCollaboration(), null, "type", null, 1, 1, CollaborationOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getCollaborationOccurrence_RoleBinding(), theUML2Package_1.getDependency(), null, "roleBinding", null, 0, -1, CollaborationOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(collaborationEClass, Collaboration.class, "Collaboration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getCollaboration_CollaborationRole(), theUML2Package_1.getConnectableElement(), null, "collaborationRole", null, 0, -1, Collaboration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(portEClass, Port.class, "Port", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPort_IsBehavior(), theUML2Package_1.getBoolean(), "isBehavior", "false", 0, 1, Port.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getPort_IsService(), theUML2Package_1.getBoolean(), "isService", "true", 0, 1, Port.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getPort_Required(), theUML2Package_1.getInterface(), null, "required", null, 0, -1, Port.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPort_RedefinedPort(), theUML2Package_1.getPort(), null, "redefinedPort", null, 0, -1, Port.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPort_Provided(), theUML2Package_1.getInterface(), null, "provided", null, 0, -1, Port.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getPort_Protocol(), theUML2Package_1.getProtocolStateMachine(), null, "protocol", null, 0, 1, Port.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPort_Kind(), theUML2Package_1.getPortKind(), "kind", null, 0, 1, Port.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(encapsulatedClassifierEClass, EncapsulatedClassifier.class, "EncapsulatedClassifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEncapsulatedClassifier_OwnedPort(), theUML2Package_1.getPort(), null, "ownedPort", null, 0, -1, EncapsulatedClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(callTriggerEClass, CallTrigger.class, "CallTrigger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getCallTrigger_Operation(), theUML2Package_1.getOperation(), null, "operation", null, 1, 1, CallTrigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(messageTriggerEClass, MessageTrigger.class, "MessageTrigger", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(changeTriggerEClass, ChangeTrigger.class, "ChangeTrigger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getChangeTrigger_ChangeExpression(), theUML2Package_1.getValueSpecification(), null, "changeExpression", null, 1, 1, ChangeTrigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(triggerEClass, Trigger.class, "Trigger", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTrigger_Port(), theUML2Package_1.getPort(), null, "port", null, 0, -1, Trigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(receptionEClass, Reception.class, "Reception", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getReception_Signal(), theUML2Package_1.getSignal(), null, "signal", null, 0, 1, Reception.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(signalEClass, Signal.class, "Signal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSignal_OwnedAttribute(), theUML2Package_1.getProperty(), null, "ownedAttribute", null, 0, -1, Signal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(signalTriggerEClass, SignalTrigger.class, "SignalTrigger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSignalTrigger_Signal(), theUML2Package_1.getSignal(), null, "signal", null, 0, -1, SignalTrigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(timeTriggerEClass, TimeTrigger.class, "TimeTrigger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getTimeTrigger_IsRelative(), theUML2Package_1.getBoolean(), "isRelative", null, 0, 1, TimeTrigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTimeTrigger_When(), theUML2Package_1.getValueSpecification(), null, "when", null, 1, 1, TimeTrigger.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(anyTriggerEClass, AnyTrigger.class, "AnyTrigger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(variableEClass, Variable.class, "Variable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getVariable_Scope(), theUML2Package_1.getStructuredActivityNode(), theUML2Package_1.getStructuredActivityNode_Variable(), "scope", null, 1, 1, Variable.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(structuredActivityNodeEClass, StructuredActivityNode.class, "StructuredActivityNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStructuredActivityNode_Variable(), theUML2Package_1.getVariable(), theUML2Package_1.getVariable_Scope(), "variable", null, 0, -1, StructuredActivityNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredActivityNode_ContainedNode(), theUML2Package_1.getActivityNode(), theUML2Package_1.getActivityNode_InStructuredNode(), "containedNode", null, 0, -1, StructuredActivityNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuredActivityNode_ContainedEdge(), theUML2Package_1.getActivityEdge(), theUML2Package_1.getActivityEdge_InStructuredNode(), "containedEdge", null, 0, -1, StructuredActivityNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getStructuredActivityNode_MustIsolate(), theUML2Package_1.getBoolean(), "mustIsolate", null, 0, 1, StructuredActivityNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(conditionalNodeEClass, ConditionalNode.class, "ConditionalNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getConditionalNode_IsDeterminate(), theUML2Package_1.getBoolean(), "isDeterminate", null, 0, 1, ConditionalNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getConditionalNode_IsAssured(), theUML2Package_1.getBoolean(), "isAssured", null, 0, 1, ConditionalNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getConditionalNode_Clause(), theUML2Package_1.getClause(), null, "clause", null, 1, -1, ConditionalNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getConditionalNode_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 0, -1, ConditionalNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(clauseEClass, Clause.class, "Clause", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getClause_Test(), theUML2Package_1.getActivityNode(), null, "test", null, 0, -1, Clause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClause_Body(), theUML2Package_1.getActivityNode(), null, "body", null, 0, -1, Clause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClause_PredecessorClause(), theUML2Package_1.getClause(), theUML2Package_1.getClause_SuccessorClause(), "predecessorClause", null, 0, -1, Clause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClause_SuccessorClause(), theUML2Package_1.getClause(), theUML2Package_1.getClause_PredecessorClause(), "successorClause", null, 0, -1, Clause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getClause_Decider(), theUML2Package_1.getOutputPin(), null, "decider", null, 1, 1, Clause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getClause_BodyOutput(), theUML2Package_1.getOutputPin(), null, "bodyOutput", null, 0, -1, Clause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(loopNodeEClass, LoopNode.class, "LoopNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getLoopNode_IsTestedFirst(), theUML2Package_1.getBoolean(), "isTestedFirst", null, 0, 1, LoopNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLoopNode_BodyPart(), theUML2Package_1.getActivityNode(), null, "bodyPart", null, 0, -1, LoopNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getLoopNode_SetupPart(), theUML2Package_1.getActivityNode(), null, "setupPart", null, 0, -1, LoopNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getLoopNode_Decider(), theUML2Package_1.getOutputPin(), null, "decider", null, 1, 1, LoopNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLoopNode_Test(), theUML2Package_1.getActivityNode(), null, "test", null, 0, -1, LoopNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getLoopNode_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 0, -1, LoopNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLoopNode_LoopVariable(), theUML2Package_1.getOutputPin(), null, "loopVariable", null, 0, -1, LoopNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLoopNode_BodyOutput(), theUML2Package_1.getOutputPin(), null, "bodyOutput", null, 0, -1, LoopNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getLoopNode_LoopVariableInput(), theUML2Package_1.getInputPin(), null, "loopVariableInput", null, 0, -1, LoopNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(interactionEClass, Interaction.class, "Interaction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInteraction_Lifeline(), theUML2Package_1.getLifeline(), theUML2Package_1.getLifeline_Interaction(), "lifeline", null, 0, -1, Interaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getInteraction_Message(), theUML2Package_1.getMessage(), theUML2Package_1.getMessage_Interaction(), "message", null, 0, -1, Interaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getInteraction_Fragment(), theUML2Package_1.getInteractionFragment(), theUML2Package_1.getInteractionFragment_EnclosingInteraction(), "fragment", null, 0, -1, Interaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInteraction_FormalGate(), theUML2Package_1.getGate(), null, "formalGate", null, 0, -1, Interaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(interactionFragmentEClass, InteractionFragment.class, "InteractionFragment", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInteractionFragment_Covered(), theUML2Package_1.getLifeline(), theUML2Package_1.getLifeline_CoveredBy(), "covered", null, 0, -1, InteractionFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInteractionFragment_GeneralOrdering(), theUML2Package_1.getGeneralOrdering(), null, "generalOrdering", null, 0, -1, InteractionFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getInteractionFragment_EnclosingInteraction(), theUML2Package_1.getInteraction(), theUML2Package_1.getInteraction_Fragment(), "enclosingInteraction", null, 0, 1, InteractionFragment.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInteractionFragment_EnclosingOperand(), theUML2Package_1.getInteractionOperand(), theUML2Package_1.getInteractionOperand_Fragment(), "enclosingOperand", null, 0, 1, InteractionFragment.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(lifelineEClass, Lifeline.class, "Lifeline", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getLifeline_CoveredBy(), theUML2Package_1.getInteractionFragment(), theUML2Package_1.getInteractionFragment_Covered(), "coveredBy", null, 0, -1, Lifeline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getLifeline_Represents(), theUML2Package_1.getConnectableElement(), null, "represents", null, 1, 1, Lifeline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLifeline_Interaction(), theUML2Package_1.getInteraction(), theUML2Package_1.getInteraction_Lifeline(), "interaction", null, 1, 1, Lifeline.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLifeline_Selector(), theUML2Package_1.getOpaqueExpression(), null, "selector", null, 0, 1, Lifeline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLifeline_DecomposedAs(), theUML2Package_1.getPartDecomposition(), null, "decomposedAs", null, 0, 1, Lifeline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(messageEClass, Message.class, "Message", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getMessage_MessageKind(), theUML2Package_1.getMessageKind(), "messageKind", null, 0, 1, Message.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getMessage_MessageSort(), theUML2Package_1.getMessageSort(), "messageSort", null, 0, 1, Message.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMessage_ReceiveEvent(), theUML2Package_1.getMessageEnd(), theUML2Package_1.getMessageEnd_ReceiveMessage(), "receiveEvent", null, 0, 1, Message.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMessage_SendEvent(), theUML2Package_1.getMessageEnd(), theUML2Package_1.getMessageEnd_SendMessage(), "sendEvent", null, 0, 1, Message.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMessage_Connector(), theUML2Package_1.getConnector(), null, "connector", null, 0, 1, Message.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMessage_Interaction(), theUML2Package_1.getInteraction(), theUML2Package_1.getInteraction_Message(), "interaction", null, 1, 1, Message.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMessage_Signature(), theUML2Package_1.getNamedElement(), null, "signature", null, 0, 1, Message.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMessage_Argument(), theUML2Package_1.getValueSpecification(), null, "argument", null, 0, -1, Message.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(generalOrderingEClass, GeneralOrdering.class, "GeneralOrdering", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getGeneralOrdering_Before(), theUML2Package_1.getEventOccurrence(), theUML2Package_1.getEventOccurrence_ToAfter(), "before", null, 1, 1, GeneralOrdering.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGeneralOrdering_After(), theUML2Package_1.getEventOccurrence(), theUML2Package_1.getEventOccurrence_ToBefore(), "after", null, 1, 1, GeneralOrdering.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(messageEndEClass, MessageEnd.class, "MessageEnd", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getMessageEnd_ReceiveMessage(), theUML2Package_1.getMessage(), theUML2Package_1.getMessage_ReceiveEvent(), "receiveMessage", null, 0, 1, MessageEnd.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getMessageEnd_SendMessage(), theUML2Package_1.getMessage(), theUML2Package_1.getMessage_SendEvent(), "sendMessage", null, 0, 1, MessageEnd.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(eventOccurrenceEClass, EventOccurrence.class, "EventOccurrence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEventOccurrence_StartExec(), theUML2Package_1.getExecutionOccurrence(), theUML2Package_1.getExecutionOccurrence_Start(), "startExec", null, 0, -1, EventOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getEventOccurrence_FinishExec(), theUML2Package_1.getExecutionOccurrence(), theUML2Package_1.getExecutionOccurrence_Finish(), "finishExec", null, 0, -1, EventOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getEventOccurrence_ToAfter(), theUML2Package_1.getGeneralOrdering(), theUML2Package_1.getGeneralOrdering_Before(), "toAfter", null, 0, -1, EventOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getEventOccurrence_ToBefore(), theUML2Package_1.getGeneralOrdering(), theUML2Package_1.getGeneralOrdering_After(), "toBefore", null, 0, -1, EventOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(executionOccurrenceEClass, ExecutionOccurrence.class, "ExecutionOccurrence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getExecutionOccurrence_Start(), theUML2Package_1.getEventOccurrence(), theUML2Package_1.getEventOccurrence_StartExec(), "start", null, 1, 1, ExecutionOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getExecutionOccurrence_Finish(), theUML2Package_1.getEventOccurrence(), theUML2Package_1.getEventOccurrence_FinishExec(), "finish", null, 1, 1, ExecutionOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getExecutionOccurrence_Behavior(), theUML2Package_1.getBehavior(), null, "behavior", null, 0, -1, ExecutionOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stateInvariantEClass, StateInvariant.class, "StateInvariant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStateInvariant_Invariant(), theUML2Package_1.getConstraint(), null, "invariant", null, 1, 1, StateInvariant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stopEClass, Stop.class, "Stop", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(templateSignatureEClass, TemplateSignature.class, "TemplateSignature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTemplateSignature_Parameter(), theUML2Package_1.getTemplateParameter(), null, "parameter", null, 1, -1, TemplateSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateSignature_OwnedParameter(), theUML2Package_1.getTemplateParameter(), theUML2Package_1.getTemplateParameter_Signature(), "ownedParameter", null, 0, -1, TemplateSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateSignature_NestedSignature(), theUML2Package_1.getTemplateSignature(), theUML2Package_1.getTemplateSignature_NestingSignature(), "nestedSignature", null, 0, -1, TemplateSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateSignature_NestingSignature(), theUML2Package_1.getTemplateSignature(), theUML2Package_1.getTemplateSignature_NestedSignature(), "nestingSignature", null, 0, 1, TemplateSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateSignature_Template(), theUML2Package_1.getTemplateableElement(), theUML2Package_1.getTemplateableElement_OwnedTemplateSignature(), "template", null, 1, 1, TemplateSignature.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(templateParameterEClass, TemplateParameter.class, "TemplateParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTemplateParameter_Signature(), theUML2Package_1.getTemplateSignature(), theUML2Package_1.getTemplateSignature_OwnedParameter(), "signature", null, 1, 1, TemplateParameter.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateParameter_ParameteredElement(), theUML2Package_1.getParameterableElement(), theUML2Package_1.getParameterableElement_TemplateParameter(), "parameteredElement", null, 1, 1, TemplateParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateParameter_OwnedParameteredElement(), theUML2Package_1.getParameterableElement(), theUML2Package_1.getParameterableElement_OwningParameter(), "ownedParameteredElement", null, 0, 1, TemplateParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateParameter_Default(), theUML2Package_1.getParameterableElement(), null, "default", null, 0, 1, TemplateParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateParameter_OwnedDefault(), theUML2Package_1.getParameterableElement(), null, "ownedDefault", null, 0, 1, TemplateParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(templateableElementEClass, TemplateableElement.class, "TemplateableElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTemplateableElement_TemplateBinding(), theUML2Package_1.getTemplateBinding(), theUML2Package_1.getTemplateBinding_BoundElement(), "templateBinding", null, 0, -1, TemplateableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateableElement_OwnedTemplateSignature(), theUML2Package_1.getTemplateSignature(), theUML2Package_1.getTemplateSignature_Template(), "ownedTemplateSignature", null, 0, 1, TemplateableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(templateableElementEClass, theUML2Package_1.getSet(), "parameterableElements"); //$NON-NLS-1$

		initEClass(stringExpressionEClass, StringExpression.class, "StringExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStringExpression_SubExpression(), theUML2Package_1.getStringExpression(), theUML2Package_1.getStringExpression_OwningExpression(), "subExpression", null, 0, -1, StringExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getStringExpression_OwningExpression(), theUML2Package_1.getStringExpression(), theUML2Package_1.getStringExpression_SubExpression(), "owningExpression", null, 0, 1, StringExpression.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(parameterableElementEClass, ParameterableElement.class, "ParameterableElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getParameterableElement_TemplateParameter(), theUML2Package_1.getTemplateParameter(), theUML2Package_1.getTemplateParameter_ParameteredElement(), "templateParameter", null, 0, 1, ParameterableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getParameterableElement_OwningParameter(), theUML2Package_1.getTemplateParameter(), theUML2Package_1.getTemplateParameter_OwnedParameteredElement(), "owningParameter", null, 0, 1, ParameterableElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(templateBindingEClass, TemplateBinding.class, "TemplateBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTemplateBinding_BoundElement(), theUML2Package_1.getTemplateableElement(), theUML2Package_1.getTemplateableElement_TemplateBinding(), "boundElement", null, 1, 1, TemplateBinding.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateBinding_Signature(), theUML2Package_1.getTemplateSignature(), null, "signature", null, 1, 1, TemplateBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateBinding_ParameterSubstitution(), theUML2Package_1.getTemplateParameterSubstitution(), theUML2Package_1.getTemplateParameterSubstitution_TemplateBinding(), "parameterSubstitution", null, 0, -1, TemplateBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(templateParameterSubstitutionEClass, TemplateParameterSubstitution.class, "TemplateParameterSubstitution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTemplateParameterSubstitution_Formal(), theUML2Package_1.getTemplateParameter(), null, "formal", null, 1, 1, TemplateParameterSubstitution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateParameterSubstitution_TemplateBinding(), theUML2Package_1.getTemplateBinding(), theUML2Package_1.getTemplateBinding_ParameterSubstitution(), "templateBinding", null, 1, 1, TemplateParameterSubstitution.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateParameterSubstitution_Actual(), theUML2Package_1.getParameterableElement(), null, "actual", null, 1, -1, TemplateParameterSubstitution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getTemplateParameterSubstitution_OwnedActual(), theUML2Package_1.getParameterableElement(), null, "ownedActual", null, 0, -1, TemplateParameterSubstitution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(operationTemplateParameterEClass, OperationTemplateParameter.class, "OperationTemplateParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(classifierTemplateParameterEClass, ClassifierTemplateParameter.class, "ClassifierTemplateParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getClassifierTemplateParameter_AllowSubstitutable(), theUML2Package_1.getBoolean(), "allowSubstitutable", "true", 0, 1, ClassifierTemplateParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(parameterableClassifierEClass, ParameterableClassifier.class, "ParameterableClassifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(redefinableTemplateSignatureEClass, RedefinableTemplateSignature.class, "RedefinableTemplateSignature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(templateableClassifierEClass, TemplateableClassifier.class, "TemplateableClassifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(connectableElementTemplateParameterEClass, ConnectableElementTemplateParameter.class, "ConnectableElementTemplateParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(forkNodeEClass, ForkNode.class, "ForkNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(joinNodeEClass, JoinNode.class, "JoinNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getJoinNode_IsCombineDuplicate(), theUML2Package_1.getBoolean(), "isCombineDuplicate", "true", 0, 1, JoinNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getJoinNode_JoinSpec(), theUML2Package_1.getValueSpecification(), null, "joinSpec", null, 1, 1, JoinNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(flowFinalNodeEClass, FlowFinalNode.class, "FlowFinalNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(centralBufferNodeEClass, CentralBufferNode.class, "CentralBufferNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(activityPartitionEClass, ActivityPartition.class, "ActivityPartition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getActivityPartition_IsDimension(), theUML2Package_1.getBoolean(), "isDimension", "false", 0, 1, ActivityPartition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getActivityPartition_IsExternal(), theUML2Package_1.getBoolean(), "isExternal", "false", 0, 1, ActivityPartition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getActivityPartition_ContainedEdge(), theUML2Package_1.getActivityEdge(), theUML2Package_1.getActivityEdge_InPartition(), "containedEdge", null, 0, -1, ActivityPartition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityPartition_ContainedNode(), theUML2Package_1.getActivityNode(), theUML2Package_1.getActivityNode_InPartition(), "containedNode", null, 0, -1, ActivityPartition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityPartition_Subgroup(), theUML2Package_1.getActivityPartition(), theUML2Package_1.getActivityPartition_SuperPartition(), "subgroup", null, 0, -1, ActivityPartition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityPartition_SuperPartition(), theUML2Package_1.getActivityPartition(), theUML2Package_1.getActivityPartition_Subgroup(), "superPartition", null, 0, 1, ActivityPartition.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getActivityPartition_Represents(), theUML2Package_1.getElement(), null, "represents", null, 0, 1, ActivityPartition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(expansionNodeEClass, ExpansionNode.class, "ExpansionNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getExpansionNode_RegionAsOutput(), theUML2Package_1.getExpansionRegion(), theUML2Package_1.getExpansionRegion_OutputElement(), "regionAsOutput", null, 0, 1, ExpansionNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getExpansionNode_RegionAsInput(), theUML2Package_1.getExpansionRegion(), theUML2Package_1.getExpansionRegion_InputElement(), "regionAsInput", null, 0, 1, ExpansionNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(expansionRegionEClass, ExpansionRegion.class, "ExpansionRegion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getExpansionRegion_Mode(), theUML2Package_1.getExpansionKind(), "mode", null, 0, 1, ExpansionRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getExpansionRegion_OutputElement(), theUML2Package_1.getExpansionNode(), theUML2Package_1.getExpansionNode_RegionAsOutput(), "outputElement", null, 0, -1, ExpansionRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getExpansionRegion_InputElement(), theUML2Package_1.getExpansionNode(), theUML2Package_1.getExpansionNode_RegionAsInput(), "inputElement", null, 1, -1, ExpansionRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(exceptionHandlerEClass, ExceptionHandler.class, "ExceptionHandler", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getExceptionHandler_ProtectedNode(), theUML2Package_1.getExecutableNode(), theUML2Package_1.getExecutableNode_Handler(), "protectedNode", null, 1, 1, ExceptionHandler.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getExceptionHandler_HandlerBody(), theUML2Package_1.getExecutableNode(), null, "handlerBody", null, 1, 1, ExceptionHandler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getExceptionHandler_ExceptionInput(), theUML2Package_1.getObjectNode(), null, "exceptionInput", null, 1, 1, ExceptionHandler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getExceptionHandler_ExceptionType(), theUML2Package_1.getClassifier(), null, "exceptionType", null, 1, -1, ExceptionHandler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(interactionOccurrenceEClass, InteractionOccurrence.class, "InteractionOccurrence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInteractionOccurrence_RefersTo(), theUML2Package_1.getInteraction(), null, "refersTo", null, 1, 1, InteractionOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInteractionOccurrence_ActualGate(), theUML2Package_1.getGate(), null, "actualGate", null, 0, -1, InteractionOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getInteractionOccurrence_Argument(), theUML2Package_1.getInputPin(), null, "argument", null, 0, -1, InteractionOccurrence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(gateEClass, Gate.class, "Gate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(partDecompositionEClass, PartDecomposition.class, "PartDecomposition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(interactionOperandEClass, InteractionOperand.class, "InteractionOperand", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInteractionOperand_Guard(), theUML2Package_1.getInteractionConstraint(), null, "guard", null, 0, 1, InteractionOperand.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInteractionOperand_Fragment(), theUML2Package_1.getInteractionFragment(), theUML2Package_1.getInteractionFragment_EnclosingOperand(), "fragment", null, 0, -1, InteractionOperand.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(interactionConstraintEClass, InteractionConstraint.class, "InteractionConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInteractionConstraint_Minint(), theUML2Package_1.getValueSpecification(), null, "minint", null, 0, 1, InteractionConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInteractionConstraint_Maxint(), theUML2Package_1.getValueSpecification(), null, "maxint", null, 0, 1, InteractionConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(combinedFragmentEClass, CombinedFragment.class, "CombinedFragment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getCombinedFragment_InteractionOperator(), theUML2Package_1.getInteractionOperator(), "interactionOperator", null, 0, 1, CombinedFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getCombinedFragment_Operand(), theUML2Package_1.getInteractionOperand(), null, "operand", null, 1, -1, CombinedFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getCombinedFragment_CfragmentGate(), theUML2Package_1.getGate(), null, "cfragmentGate", null, 0, -1, CombinedFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(continuationEClass, Continuation.class, "Continuation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getContinuation_Setting(), theUML2Package_1.getBoolean(), "setting", "True", 0, 1, Continuation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(stateMachineEClass, StateMachine.class, "StateMachine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStateMachine_Region(), theUML2Package_1.getRegion(), theUML2Package_1.getRegion_StateMachine(), "region", null, 1, -1, StateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getStateMachine_ConnectionPoint(), theUML2Package_1.getPseudostate(), null, "connectionPoint", null, 0, -1, StateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getStateMachine_ExtendedStateMachine(), theUML2Package_1.getStateMachine(), null, "extendedStateMachine", null, 0, 1, StateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getStateMachine_StateMachine_redefinitionContext(), theUML2Package_1.getBehavioredClassifier(), theUML2Package_1.getBehavioredClassifier_OwnedStateMachine(), "stateMachine_redefinitionContext", null, 0, 1, StateMachine.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(regionEClass, Region.class, "Region", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRegion_Subvertex(), theUML2Package_1.getVertex(), theUML2Package_1.getVertex_Container(), "subvertex", null, 0, -1, Region.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getRegion_Transition(), theUML2Package_1.getTransition(), theUML2Package_1.getTransition_Container(), "transition", null, 0, -1, Region.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getRegion_StateMachine(), theUML2Package_1.getStateMachine(), theUML2Package_1.getStateMachine_Region(), "stateMachine", null, 0, 1, Region.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getRegion_State(), theUML2Package_1.getState(), theUML2Package_1.getState_Region(), "state", null, 0, 1, Region.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getRegion_ExtendedRegion(), theUML2Package_1.getRegion(), null, "extendedRegion", null, 0, 1, Region.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(pseudostateEClass, Pseudostate.class, "Pseudostate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPseudostate_Kind(), theUML2Package_1.getPseudostateKind(), "kind", null, 0, 1, Pseudostate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stateEClass, State.class, "State", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getState_IsComposite(), theUML2Package_1.getBoolean(), "isComposite", null, 0, 1, State.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getState_IsOrthogonal(), theUML2Package_1.getBoolean(), "isOrthogonal", null, 0, 1, State.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getState_IsSimple(), theUML2Package_1.getBoolean(), "isSimple", null, 0, 1, State.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getState_IsSubmachineState(), theUML2Package_1.getBoolean(), "isSubmachineState", null, 0, 1, State.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getState_Submachine(), theUML2Package_1.getStateMachine(), null, "submachine", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getState_Connection(), theUML2Package_1.getConnectionPointReference(), null, "connection", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getState_RedefinedState(), theUML2Package_1.getState(), null, "redefinedState", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getState_DeferrableTrigger(), theUML2Package_1.getTrigger(), null, "deferrableTrigger", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getState_Region(), theUML2Package_1.getRegion(), theUML2Package_1.getRegion_State(), "region", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getState_Entry(), theUML2Package_1.getActivity(), null, "entry", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getState_Exit(), theUML2Package_1.getActivity(), null, "exit", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getState_DoActivity(), theUML2Package_1.getActivity(), null, "doActivity", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getState_StateInvariant(), theUML2Package_1.getConstraint(), null, "stateInvariant", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(vertexEClass, Vertex.class, "Vertex", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getVertex_Container(), theUML2Package_1.getRegion(), theUML2Package_1.getRegion_Subvertex(), "container", null, 0, 1, Vertex.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getVertex_Outgoing(), theUML2Package_1.getTransition(), theUML2Package_1.getTransition_Source(), "outgoing", null, 0, -1, Vertex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getVertex_Incoming(), theUML2Package_1.getTransition(), theUML2Package_1.getTransition_Target(), "incoming", null, 0, -1, Vertex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(connectionPointReferenceEClass, ConnectionPointReference.class, "ConnectionPointReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getConnectionPointReference_Entry(), theUML2Package_1.getPseudostate(), null, "entry", null, 0, -1, ConnectionPointReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getConnectionPointReference_Exit(), theUML2Package_1.getPseudostate(), null, "exit", null, 0, -1, ConnectionPointReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(transitionEClass, Transition.class, "Transition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getTransition_Kind(), theUML2Package_1.getTransitionKind(), "kind", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTransition_Container(), theUML2Package_1.getRegion(), theUML2Package_1.getRegion_Transition(), "container", null, 1, 1, Transition.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTransition_Source(), theUML2Package_1.getVertex(), theUML2Package_1.getVertex_Outgoing(), "source", null, 1, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTransition_Target(), theUML2Package_1.getVertex(), theUML2Package_1.getVertex_Incoming(), "target", null, 1, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTransition_RedefinedTransition(), theUML2Package_1.getTransition(), null, "redefinedTransition", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTransition_Trigger(), theUML2Package_1.getTrigger(), null, "trigger", null, 0, -1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getTransition_Guard(), theUML2Package_1.getConstraint(), null, "guard", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTransition_Effect(), theUML2Package_1.getActivity(), null, "effect", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(finalStateEClass, FinalState.class, "FinalState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(createObjectActionEClass, CreateObjectAction.class, "CreateObjectAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getCreateObjectAction_Classifier(), theUML2Package_1.getClassifier(), null, "classifier", null, 1, 1, CreateObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getCreateObjectAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 1, 1, CreateObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(destroyObjectActionEClass, DestroyObjectAction.class, "DestroyObjectAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getDestroyObjectAction_IsDestroyLinks(), theUML2Package_1.getBoolean(), "isDestroyLinks", "false", 0, 1, DestroyObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getDestroyObjectAction_IsDestroyOwnedObjects(), theUML2Package_1.getBoolean(), "isDestroyOwnedObjects", "false", 0, 1, DestroyObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getDestroyObjectAction_Target(), theUML2Package_1.getInputPin(), null, "target", null, 1, 1, DestroyObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(testIdentityActionEClass, TestIdentityAction.class, "TestIdentityAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTestIdentityAction_First(), theUML2Package_1.getInputPin(), null, "first", null, 1, 1, TestIdentityAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTestIdentityAction_Second(), theUML2Package_1.getInputPin(), null, "second", null, 1, 1, TestIdentityAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTestIdentityAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 1, 1, TestIdentityAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(readSelfActionEClass, ReadSelfAction.class, "ReadSelfAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getReadSelfAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 1, 1, ReadSelfAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(structuralFeatureActionEClass, StructuralFeatureAction.class, "StructuralFeatureAction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStructuralFeatureAction_StructuralFeature(), theUML2Package_1.getStructuralFeature(), null, "structuralFeature", null, 1, 1, StructuralFeatureAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getStructuralFeatureAction_Object(), theUML2Package_1.getInputPin(), null, "object", null, 1, 1, StructuralFeatureAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(readStructuralFeatureActionEClass, ReadStructuralFeatureAction.class, "ReadStructuralFeatureAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getReadStructuralFeatureAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 1, 1, ReadStructuralFeatureAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(writeStructuralFeatureActionEClass, WriteStructuralFeatureAction.class, "WriteStructuralFeatureAction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getWriteStructuralFeatureAction_Value(), theUML2Package_1.getInputPin(), null, "value", null, 1, 1, WriteStructuralFeatureAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(clearStructuralFeatureActionEClass, ClearStructuralFeatureAction.class, "ClearStructuralFeatureAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(removeStructuralFeatureValueActionEClass, RemoveStructuralFeatureValueAction.class, "RemoveStructuralFeatureValueAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(addStructuralFeatureValueActionEClass, AddStructuralFeatureValueAction.class, "AddStructuralFeatureValueAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAddStructuralFeatureValueAction_IsReplaceAll(), theUML2Package_1.getBoolean(), "isReplaceAll", "false", 0, 1, AddStructuralFeatureValueAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getAddStructuralFeatureValueAction_InsertAt(), theUML2Package_1.getInputPin(), null, "insertAt", null, 0, 1, AddStructuralFeatureValueAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(linkActionEClass, LinkAction.class, "LinkAction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getLinkAction_EndData(), theUML2Package_1.getLinkEndData(), null, "endData", null, 2, -1, LinkAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(linkEndDataEClass, LinkEndData.class, "LinkEndData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getLinkEndData_Value(), theUML2Package_1.getInputPin(), null, "value", null, 0, 1, LinkEndData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLinkEndData_End(), theUML2Package_1.getProperty(), null, "end", null, 1, 1, LinkEndData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getLinkEndData_Qualifier(), theUML2Package_1.getQualifierValue(), null, "qualifier", null, 0, -1, LinkEndData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(readLinkActionEClass, ReadLinkAction.class, "ReadLinkAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getReadLinkAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 1, 1, ReadLinkAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(linkEndCreationDataEClass, LinkEndCreationData.class, "LinkEndCreationData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getLinkEndCreationData_IsReplaceAll(), theUML2Package_1.getBoolean(), "isReplaceAll", "false", 0, 1, LinkEndCreationData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getLinkEndCreationData_InsertAt(), theUML2Package_1.getInputPin(), null, "insertAt", null, 0, 1, LinkEndCreationData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(createLinkActionEClass, CreateLinkAction.class, "CreateLinkAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(writeLinkActionEClass, WriteLinkAction.class, "WriteLinkAction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(destroyLinkActionEClass, DestroyLinkAction.class, "DestroyLinkAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(clearAssociationActionEClass, ClearAssociationAction.class, "ClearAssociationAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getClearAssociationAction_Object(), theUML2Package_1.getInputPin(), null, "object", null, 1, 1, ClearAssociationAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getClearAssociationAction_Association(), theUML2Package_1.getAssociation(), null, "association", null, 1, 1, ClearAssociationAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(variableActionEClass, VariableAction.class, "VariableAction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getVariableAction_Variable(), theUML2Package_1.getVariable(), null, "variable", null, 1, 1, VariableAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(readVariableActionEClass, ReadVariableAction.class, "ReadVariableAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getReadVariableAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 1, 1, ReadVariableAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(writeVariableActionEClass, WriteVariableAction.class, "WriteVariableAction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getWriteVariableAction_Value(), theUML2Package_1.getInputPin(), null, "value", null, 1, 1, WriteVariableAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(clearVariableActionEClass, ClearVariableAction.class, "ClearVariableAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(addVariableValueActionEClass, AddVariableValueAction.class, "AddVariableValueAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAddVariableValueAction_IsReplaceAll(), theUML2Package_1.getBoolean(), "isReplaceAll", "false", 0, 1, AddVariableValueAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getAddVariableValueAction_InsertAt(), theUML2Package_1.getInputPin(), null, "insertAt", null, 0, 1, AddVariableValueAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(removeVariableValueActionEClass, RemoveVariableValueAction.class, "RemoveVariableValueAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(applyFunctionActionEClass, ApplyFunctionAction.class, "ApplyFunctionAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getApplyFunctionAction_Function(), theUML2Package_1.getPrimitiveFunction(), null, "function", null, 1, 1, ApplyFunctionAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getApplyFunctionAction_Argument(), theUML2Package_1.getInputPin(), null, "argument", null, 0, -1, ApplyFunctionAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getApplyFunctionAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 0, -1, ApplyFunctionAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(primitiveFunctionEClass, PrimitiveFunction.class, "PrimitiveFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPrimitiveFunction_Body(), theUML2Package_1.getString(), "body", "", 0, 1, PrimitiveFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getPrimitiveFunction_Language(), theUML2Package_1.getString(), "language", "", 0, 1, PrimitiveFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(callActionEClass, CallAction.class, "CallAction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getCallAction_IsSynchronous(), theUML2Package_1.getBoolean(), "isSynchronous", "true", 0, 1, CallAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getCallAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 0, -1, CallAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(invocationActionEClass, InvocationAction.class, "InvocationAction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInvocationAction_Argument(), theUML2Package_1.getInputPin(), null, "argument", null, 0, -1, InvocationAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInvocationAction_OnPort(), theUML2Package_1.getPort(), null, "onPort", null, 0, 1, InvocationAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(sendSignalActionEClass, SendSignalAction.class, "SendSignalAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSendSignalAction_Target(), theUML2Package_1.getInputPin(), null, "target", null, 1, 1, SendSignalAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSendSignalAction_Signal(), theUML2Package_1.getSignal(), null, "signal", null, 1, 1, SendSignalAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(broadcastSignalActionEClass, BroadcastSignalAction.class, "BroadcastSignalAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getBroadcastSignalAction_Signal(), theUML2Package_1.getSignal(), null, "signal", null, 1, 1, BroadcastSignalAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(sendObjectActionEClass, SendObjectAction.class, "SendObjectAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSendObjectAction_Target(), theUML2Package_1.getInputPin(), null, "target", null, 1, 1, SendObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSendObjectAction_Request(), theUML2Package_1.getInputPin(), null, "request", null, 1, 1, SendObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(callOperationActionEClass, CallOperationAction.class, "CallOperationAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getCallOperationAction_Operation(), theUML2Package_1.getOperation(), null, "operation", null, 1, 1, CallOperationAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getCallOperationAction_Target(), theUML2Package_1.getInputPin(), null, "target", null, 1, 1, CallOperationAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(callBehaviorActionEClass, CallBehaviorAction.class, "CallBehaviorAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getCallBehaviorAction_Behavior(), theUML2Package_1.getBehavior(), null, "behavior", null, 1, 1, CallBehaviorAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(timeExpressionEClass, TimeExpression.class, "TimeExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getTimeExpression_FirstTime(), theUML2Package_1.getBoolean(), "firstTime", "True", 0, 1, TimeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getTimeExpression_Event(), theUML2Package_1.getNamedElement(), null, "event", null, 0, 1, TimeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(durationEClass, Duration.class, "Duration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getDuration_FirstTime(), theUML2Package_1.getBoolean(), "firstTime", "True", 0, 1, Duration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getDuration_Event(), theUML2Package_1.getNamedElement(), null, "event", null, 0, 2, Duration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(timeObservationActionEClass, TimeObservationAction.class, "TimeObservationAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTimeObservationAction_Now(), theUML2Package_1.getTimeExpression(), null, "now", null, 0, -1, TimeObservationAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(durationIntervalEClass, DurationInterval.class, "DurationInterval", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(intervalEClass, Interval.class, "Interval", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInterval_Min(), theUML2Package_1.getValueSpecification(), null, "min", null, 0, -1, Interval.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterval_Max(), theUML2Package_1.getValueSpecification(), null, "max", null, 0, -1, Interval.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(timeConstraintEClass, TimeConstraint.class, "TimeConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(intervalConstraintEClass, IntervalConstraint.class, "IntervalConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(timeIntervalEClass, TimeInterval.class, "TimeInterval", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(durationObservationActionEClass, DurationObservationAction.class, "DurationObservationAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDurationObservationAction_Duration(), theUML2Package_1.getDuration(), null, "duration", null, 0, -1, DurationObservationAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(durationConstraintEClass, DurationConstraint.class, "DurationConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(dataStoreNodeEClass, DataStoreNode.class, "DataStoreNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(interruptibleActivityRegionEClass, InterruptibleActivityRegion.class, "InterruptibleActivityRegion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInterruptibleActivityRegion_InterruptingEdge(), theUML2Package_1.getActivityEdge(), theUML2Package_1.getActivityEdge_Interrupts(), "interruptingEdge", null, 0, -1, InterruptibleActivityRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getInterruptibleActivityRegion_ContainedNode(), theUML2Package_1.getActivityNode(), theUML2Package_1.getActivityNode_InInterruptibleRegion(), "containedNode", null, 0, -1, InterruptibleActivityRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(parameterSetEClass, ParameterSet.class, "ParameterSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getParameterSet_Parameter(), theUML2Package_1.getParameter(), theUML2Package_1.getParameter_ParameterSet(), "parameter", null, 1, -1, ParameterSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getParameterSet_Condition(), theUML2Package_1.getConstraint(), null, "condition", null, 0, -1, ParameterSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(componentEClass, Component.class, "Component", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getComponent_IsIndirectlyInstantiated(), theUML2Package_1.getBoolean(), "isIndirectlyInstantiated", null, 0, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getComponent_Required(), theUML2Package_1.getInterface(), null, "required", null, 0, -1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getComponent_Provided(), theUML2Package_1.getInterface(), null, "provided", null, 0, -1, Component.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getComponent_Realization(), theUML2Package_1.getRealization(), theUML2Package_1.getRealization_Abstraction(), "realization", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getComponent_OwnedMember(), theUML2Package_1.getPackageableElement(), null, "ownedMember", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(deploymentEClass, Deployment.class, "Deployment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDeployment_DeployedArtifact(), theUML2Package_1.getDeployedArtifact(), null, "deployedArtifact", null, 0, -1, Deployment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getDeployment_Location(), theUML2Package_1.getDeploymentTarget(), theUML2Package_1.getDeploymentTarget_Deployment(), "location", null, 1, 1, Deployment.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getDeployment_Configuration(), theUML2Package_1.getDeploymentSpecification(), null, "configuration", null, 0, -1, Deployment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(deployedArtifactEClass, DeployedArtifact.class, "DeployedArtifact", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(deploymentTargetEClass, DeploymentTarget.class, "DeploymentTarget", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDeploymentTarget_Deployment(), theUML2Package_1.getDeployment(), theUML2Package_1.getDeployment_Location(), "deployment", null, 0, -1, DeploymentTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getDeploymentTarget_DeployedElement(), theUML2Package_1.getPackageableElement(), null, "deployedElement", null, 0, -1, DeploymentTarget.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(nodeEClass, Node.class, "Node", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getNode_NestedNode(), theUML2Package_1.getNode(), null, "nestedNode", null, 0, -1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(deviceEClass, Device.class, "Device", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(executionEnvironmentEClass, ExecutionEnvironment.class, "ExecutionEnvironment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(communicationPathEClass, CommunicationPath.class, "CommunicationPath", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(protocolConformanceEClass, ProtocolConformance.class, "ProtocolConformance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getProtocolConformance_SpecificMachine(), theUML2Package_1.getProtocolStateMachine(), theUML2Package_1.getProtocolStateMachine_Conformance(), "specificMachine", null, 1, 1, ProtocolConformance.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getProtocolConformance_GeneralMachine(), theUML2Package_1.getProtocolStateMachine(), null, "generalMachine", null, 1, 1, ProtocolConformance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(protocolStateMachineEClass, ProtocolStateMachine.class, "ProtocolStateMachine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getProtocolStateMachine_Conformance(), theUML2Package_1.getProtocolConformance(), theUML2Package_1.getProtocolConformance_SpecificMachine(), "conformance", null, 0, -1, ProtocolStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(protocolTransitionEClass, ProtocolTransition.class, "ProtocolTransition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getProtocolTransition_PostCondition(), theUML2Package_1.getConstraint(), null, "postCondition", null, 0, 1, ProtocolTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getProtocolTransition_Referred(), theUML2Package_1.getOperation(), null, "referred", null, 0, -1, ProtocolTransition.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getProtocolTransition_PreCondition(), theUML2Package_1.getConstraint(), null, "preCondition", null, 0, 1, ProtocolTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(readExtentActionEClass, ReadExtentAction.class, "ReadExtentAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getReadExtentAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 1, 1, ReadExtentAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getReadExtentAction_Classifier(), theUML2Package_1.getClassifier(), null, "classifier", null, 1, 1, ReadExtentAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(reclassifyObjectActionEClass, ReclassifyObjectAction.class, "ReclassifyObjectAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getReclassifyObjectAction_IsReplaceAll(), theUML2Package_1.getBoolean(), "isReplaceAll", "false", 0, 1, ReclassifyObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getReclassifyObjectAction_OldClassifier(), theUML2Package_1.getClassifier(), null, "oldClassifier", null, 0, -1, ReclassifyObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getReclassifyObjectAction_NewClassifier(), theUML2Package_1.getClassifier(), null, "newClassifier", null, 0, -1, ReclassifyObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getReclassifyObjectAction_Object(), theUML2Package_1.getInputPin(), null, "object", null, 1, 1, ReclassifyObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(readIsClassifiedObjectActionEClass, ReadIsClassifiedObjectAction.class, "ReadIsClassifiedObjectAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getReadIsClassifiedObjectAction_IsDirect(), theUML2Package_1.getBoolean(), "isDirect", "false", 0, 1, ReadIsClassifiedObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getReadIsClassifiedObjectAction_Classifier(), theUML2Package_1.getClassifier(), null, "classifier", null, 1, 1, ReadIsClassifiedObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getReadIsClassifiedObjectAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 1, 1, ReadIsClassifiedObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getReadIsClassifiedObjectAction_Object(), theUML2Package_1.getInputPin(), null, "object", null, 1, 1, ReadIsClassifiedObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(startOwnedBehaviorActionEClass, StartOwnedBehaviorAction.class, "StartOwnedBehaviorAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getStartOwnedBehaviorAction_Object(), theUML2Package_1.getInputPin(), null, "object", null, 1, 1, StartOwnedBehaviorAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(qualifierValueEClass, QualifierValue.class, "QualifierValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getQualifierValue_Qualifier(), theUML2Package_1.getProperty(), null, "qualifier", null, 1, 1, QualifierValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getQualifierValue_Value(), theUML2Package_1.getInputPin(), null, "value", null, 1, 1, QualifierValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(readLinkObjectEndActionEClass, ReadLinkObjectEndAction.class, "ReadLinkObjectEndAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getReadLinkObjectEndAction_Object(), theUML2Package_1.getInputPin(), null, "object", null, 1, 1, ReadLinkObjectEndAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getReadLinkObjectEndAction_End(), theUML2Package_1.getProperty(), null, "end", null, 1, 1, ReadLinkObjectEndAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getReadLinkObjectEndAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 1, 1, ReadLinkObjectEndAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(readLinkObjectEndQualifierActionEClass, ReadLinkObjectEndQualifierAction.class, "ReadLinkObjectEndQualifierAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getReadLinkObjectEndQualifierAction_Object(), theUML2Package_1.getInputPin(), null, "object", null, 1, 1, ReadLinkObjectEndQualifierAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getReadLinkObjectEndQualifierAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 1, 1, ReadLinkObjectEndQualifierAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getReadLinkObjectEndQualifierAction_Qualifier(), theUML2Package_1.getProperty(), null, "qualifier", null, 1, 1, ReadLinkObjectEndQualifierAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(createLinkObjectActionEClass, CreateLinkObjectAction.class, "CreateLinkObjectAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getCreateLinkObjectAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 1, 1, CreateLinkObjectAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(acceptEventActionEClass, AcceptEventAction.class, "AcceptEventAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAcceptEventAction_Trigger(), theUML2Package_1.getTrigger(), null, "trigger", null, 0, -1, AcceptEventAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAcceptEventAction_Result(), theUML2Package_1.getOutputPin(), null, "result", null, 0, -1, AcceptEventAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(acceptCallActionEClass, AcceptCallAction.class, "AcceptCallAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAcceptCallAction_ReturnInformation(), theUML2Package_1.getOutputPin(), null, "returnInformation", null, 1, 1, AcceptCallAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(replyActionEClass, ReplyAction.class, "ReplyAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getReplyAction_ReplyToCall(), theUML2Package_1.getCallTrigger(), null, "replyToCall", null, 1, 1, ReplyAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getReplyAction_ReplyValue(), theUML2Package_1.getInputPin(), null, "replyValue", null, 0, -1, ReplyAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getReplyAction_ReturnInformation(), theUML2Package_1.getInputPin(), null, "returnInformation", null, 1, 1, ReplyAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(raiseExceptionActionEClass, RaiseExceptionAction.class, "RaiseExceptionAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRaiseExceptionAction_Exception(), theUML2Package_1.getInputPin(), null, "exception", null, 1, 1, RaiseExceptionAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(deploymentSpecificationEClass, DeploymentSpecification.class, "DeploymentSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getDeploymentSpecification_DeploymentLocation(), theUML2Package_1.getString(), "deploymentLocation", "", 0, 1, DeploymentSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getDeploymentSpecification_ExecutionLocation(), theUML2Package_1.getString(), "executionLocation", "", 0, 1, DeploymentSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(j_FigureContainerEClass, J_FigureContainer.class, "J_FigureContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getJ_FigureContainer_Figures(), theUML2Package_1.getJ_Figure(), null, "figures", null, 0, -1, J_FigureContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getJ_FigureContainer_Properties(), theUML2Package_1.getJ_Property(), null, "properties", null, 0, -1, J_FigureContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(j_FigureEClass, J_Figure.class, "J_Figure", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Id(), theUML2Package_1.getString(), "id", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Recreator(), theUML2Package_1.getString(), "recreator", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Anchor1Id(), theUML2Package_1.getString(), "anchor1Id", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Anchor2Id(), theUML2Package_1.getString(), "anchor2Id", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_ContainedName(), theUML2Package_1.getString(), "containedName", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Text(), theUML2Package_1.getString(), "text", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Name(), theUML2Package_1.getString(), "name", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_VirtualPoint(), theUML2Package_1.getString(), "virtualPoint", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Points(), theUML2Package_1.getString(), "points", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_BrOffset(), theUML2Package_1.getString(), "brOffset", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_TlOffset(), theUML2Package_1.getString(), "tlOffset", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Show(), theUML2Package_1.getString(), "show", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Autosized(), theUML2Package_1.getString(), "autosized", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Icon(), theUML2Package_1.getString(), "icon", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Point(), theUML2Package_1.getString(), "point", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Dimensions(), theUML2Package_1.getString(), "dimensions", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_SuppressAttributes(), theUML2Package_1.getString(), "suppressAttributes", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_SuppressOperations(), theUML2Package_1.getString(), "suppressOperations", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_SuppressContents(), theUML2Package_1.getString(), "suppressContents", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Offset(), theUML2Package_1.getString(), "offset", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Min(), theUML2Package_1.getString(), "min", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Accessibility(), theUML2Package_1.getString(), "accessibility", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_ClassifierScope(), theUML2Package_1.getString(), "classifierScope", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Figure_Type(), theUML2Package_1.getString(), "type", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getJ_Figure_Subject(), theUML2Package_1.getElement(), null, "subject", null, 0, 1, J_Figure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(j_PropertyEClass, J_Property.class, "J_Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getJ_Property_Name(), theUML2Package_1.getString(), "name", null, 0, 1, J_Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Property_Value(), theUML2Package_1.getString(), "value", null, 0, 1, J_Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(j_DiagramEClass, J_Diagram.class, "J_Diagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getJ_Diagram_Name(), theUML2Package_1.getString(), "name", null, 0, 1, J_Diagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_Diagram_LastFigureId(), theUML2Package_1.getInteger(), "lastFigureId", null, 0, 1, J_Diagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(j_DiagramHolderEClass, J_DiagramHolder.class, "J_DiagramHolder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getJ_DiagramHolder_Diagram(), theUML2Package_1.getJ_Diagram(), null, "diagram", null, 0, 1, J_DiagramHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_DiagramHolder_SaveTime(), theUML2Package_1.getString(), "saveTime", null, 0, 1, J_DiagramHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getJ_DiagramHolder_SavedBy(), theUML2Package_1.getString(), "savedBy", null, 0, 1, J_DiagramHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(appliedBasicStereotypeValueEClass, AppliedBasicStereotypeValue.class, "AppliedBasicStereotypeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAppliedBasicStereotypeValue_Property(), theUML2Package_1.getProperty(), null, "property", null, 0, 1, AppliedBasicStereotypeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAppliedBasicStereotypeValue_Value(), theUML2Package_1.getValueSpecification(), null, "value", null, 0, 1, AppliedBasicStereotypeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(propertyValueSpecificationEClass, PropertyValueSpecification.class, "PropertyValueSpecification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPropertyValueSpecification_Aliased(), theUML2Package_1.getBoolean(), "aliased", null, 0, 1, PropertyValueSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getPropertyValueSpecification_Property(), theUML2Package_1.getProperty(), null, "property", null, 0, 1, PropertyValueSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(deltaReplacedConstituentEClass, DeltaReplacedConstituent.class, "DeltaReplacedConstituent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDeltaReplacedConstituent_Replaced(), theUML2Package_1.getElement(), null, "replaced", null, 0, 1, DeltaReplacedConstituent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getDeltaReplacedConstituent_Replacement(), theUML2Package_1.getElement(), null, "replacement", null, 0, 1, DeltaReplacedConstituent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(deltaDeletedConstituentEClass, DeltaDeletedConstituent.class, "DeltaDeletedConstituent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getDeltaDeletedConstituent_Deleted(), theUML2Package_1.getElement(), null, "deleted", null, 0, 1, DeltaDeletedConstituent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(deltaReplacedAttributeEClass, DeltaReplacedAttribute.class, "DeltaReplacedAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(deltaDeletedAttributeEClass, DeltaDeletedAttribute.class, "DeltaDeletedAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(deltaReplacedPortEClass, DeltaReplacedPort.class, "DeltaReplacedPort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(deltaDeletedPortEClass, DeltaDeletedPort.class, "DeltaDeletedPort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(deltaReplacedConnectorEClass, DeltaReplacedConnector.class, "DeltaReplacedConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(deltaDeletedConnectorEClass, DeltaDeletedConnector.class, "DeltaDeletedConnector", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(deltaReplacedOperationEClass, DeltaReplacedOperation.class, "DeltaReplacedOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(deltaDeletedOperationEClass, DeltaDeletedOperation.class, "DeltaDeletedOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(portRemapEClass, PortRemap.class, "PortRemap", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getPortRemap_OriginalPort(), theUML2Package_1.getPort(), null, "originalPort", null, 0, 1, PortRemap.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getPortRemap_NewPort(), theUML2Package_1.getPort(), null, "newPort", null, 0, 1, PortRemap.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(savedReferenceEClass, SavedReference.class, "SavedReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getSavedReference_To(), theUML2Package_1.getString(), "to", null, 0, 1, SavedReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSavedReference_From(), theUML2Package_1.getString(), "from", null, 0, 1, SavedReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSavedReference_ToEClass(), ecorePackage.getEClass(), null, "toEClass", null, 0, 1, SavedReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSavedReference_Feature(), ecorePackage.getEReference(), null, "feature", null, 0, 1, SavedReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(requirementsFeatureEClass, RequirementsFeature.class, "RequirementsFeature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRequirementsFeature_Subfeatures(), theUML2Package_1.getRequirementsFeatureLink(), null, "subfeatures", null, 0, -1, RequirementsFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getRequirementsFeature_DeltaReplacedSubfeatures(), theUML2Package_1.getDeltaReplacedRequirementsFeatureLink(), null, "deltaReplacedSubfeatures", null, 0, -1, RequirementsFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getRequirementsFeature_DeltaDeletedSubfeatures(), theUML2Package_1.getDeltaDeletedRequirementsFeatureLink(), null, "deltaDeletedSubfeatures", null, 0, -1, RequirementsFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(requirementsFeatureLinkEClass, RequirementsFeatureLink.class, "RequirementsFeatureLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getRequirementsFeatureLink_Kind(), theUML2Package_1.getRequirementsLinkKind(), "kind", null, 0, 1, RequirementsFeatureLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getRequirementsFeatureLink_Type(), theUML2Package_1.getRequirementsFeature(), null, "type", null, 1, 1, RequirementsFeatureLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(deltaReplacedRequirementsFeatureLinkEClass, DeltaReplacedRequirementsFeatureLink.class, "DeltaReplacedRequirementsFeatureLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(deltaDeletedRequirementsFeatureLinkEClass, DeltaDeletedRequirementsFeatureLink.class, "DeltaDeletedRequirementsFeatureLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(deltaDeletedTraceEClass, DeltaDeletedTrace.class, "DeltaDeletedTrace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(deltaReplacedTraceEClass, DeltaReplacedTrace.class, "DeltaReplacedTrace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(visibilityKindEEnum, VisibilityKind.class, "VisibilityKind"); //$NON-NLS-1$
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PUBLIC_LITERAL);
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PRIVATE_LITERAL);
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PROTECTED_LITERAL);
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PACKAGE_LITERAL);

		initEEnum(parameterDirectionKindEEnum, ParameterDirectionKind.class, "ParameterDirectionKind"); //$NON-NLS-1$
		addEEnumLiteral(parameterDirectionKindEEnum, ParameterDirectionKind.IN_LITERAL);
		addEEnumLiteral(parameterDirectionKindEEnum, ParameterDirectionKind.INOUT_LITERAL);
		addEEnumLiteral(parameterDirectionKindEEnum, ParameterDirectionKind.OUT_LITERAL);
		addEEnumLiteral(parameterDirectionKindEEnum, ParameterDirectionKind.RETURN_LITERAL);

		initEEnum(aggregationKindEEnum, AggregationKind.class, "AggregationKind"); //$NON-NLS-1$
		addEEnumLiteral(aggregationKindEEnum, AggregationKind.NONE_LITERAL);
		addEEnumLiteral(aggregationKindEEnum, AggregationKind.SHARED_LITERAL);
		addEEnumLiteral(aggregationKindEEnum, AggregationKind.COMPOSITE_LITERAL);

		initEEnum(callConcurrencyKindEEnum, CallConcurrencyKind.class, "CallConcurrencyKind"); //$NON-NLS-1$
		addEEnumLiteral(callConcurrencyKindEEnum, CallConcurrencyKind.SEQUENTIAL_LITERAL);
		addEEnumLiteral(callConcurrencyKindEEnum, CallConcurrencyKind.GUARDED_LITERAL);
		addEEnumLiteral(callConcurrencyKindEEnum, CallConcurrencyKind.CONCURRENT_LITERAL);

		initEEnum(messageKindEEnum, MessageKind.class, "MessageKind"); //$NON-NLS-1$
		addEEnumLiteral(messageKindEEnum, MessageKind.COMPLETE_LITERAL);
		addEEnumLiteral(messageKindEEnum, MessageKind.LOST_LITERAL);
		addEEnumLiteral(messageKindEEnum, MessageKind.FOUND_LITERAL);
		addEEnumLiteral(messageKindEEnum, MessageKind.UNKNOWN_LITERAL);

		initEEnum(messageSortEEnum, MessageSort.class, "MessageSort"); //$NON-NLS-1$
		addEEnumLiteral(messageSortEEnum, MessageSort.SYNCH_CALL_LITERAL);
		addEEnumLiteral(messageSortEEnum, MessageSort.SYNCH_SIGNAL_LITERAL);
		addEEnumLiteral(messageSortEEnum, MessageSort.ASYNCH_CALL_LITERAL);
		addEEnumLiteral(messageSortEEnum, MessageSort.ASYNCH_SIGNAL_LITERAL);

		initEEnum(expansionKindEEnum, ExpansionKind.class, "ExpansionKind"); //$NON-NLS-1$
		addEEnumLiteral(expansionKindEEnum, ExpansionKind.PARALLEL_LITERAL);
		addEEnumLiteral(expansionKindEEnum, ExpansionKind.ITERATIVE_LITERAL);
		addEEnumLiteral(expansionKindEEnum, ExpansionKind.STREAM_LITERAL);

		initEEnum(interactionOperatorEEnum, InteractionOperator.class, "InteractionOperator"); //$NON-NLS-1$
		addEEnumLiteral(interactionOperatorEEnum, InteractionOperator.SEQ_LITERAL);
		addEEnumLiteral(interactionOperatorEEnum, InteractionOperator.ALT_LITERAL);
		addEEnumLiteral(interactionOperatorEEnum, InteractionOperator.OPT_LITERAL);
		addEEnumLiteral(interactionOperatorEEnum, InteractionOperator.BREAK_LITERAL);
		addEEnumLiteral(interactionOperatorEEnum, InteractionOperator.PAR_LITERAL);
		addEEnumLiteral(interactionOperatorEEnum, InteractionOperator.STRICT_LITERAL);
		addEEnumLiteral(interactionOperatorEEnum, InteractionOperator.LOOP_LITERAL);
		addEEnumLiteral(interactionOperatorEEnum, InteractionOperator.CRITICAL_LITERAL);
		addEEnumLiteral(interactionOperatorEEnum, InteractionOperator.NEG_LITERAL);
		addEEnumLiteral(interactionOperatorEEnum, InteractionOperator.ASSERT_LITERAL);
		addEEnumLiteral(interactionOperatorEEnum, InteractionOperator.IGNORE_LITERAL);
		addEEnumLiteral(interactionOperatorEEnum, InteractionOperator.CONSIDER_LITERAL);

		initEEnum(transitionKindEEnum, TransitionKind.class, "TransitionKind"); //$NON-NLS-1$
		addEEnumLiteral(transitionKindEEnum, TransitionKind.INTERNAL_LITERAL);
		addEEnumLiteral(transitionKindEEnum, TransitionKind.LOCAL_LITERAL);
		addEEnumLiteral(transitionKindEEnum, TransitionKind.EXTERNAL_LITERAL);

		initEEnum(pseudostateKindEEnum, PseudostateKind.class, "PseudostateKind"); //$NON-NLS-1$
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.INITIAL_LITERAL);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.DEEP_HISTORY_LITERAL);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.SHALLOW_HISTORY_LITERAL);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.JOIN_LITERAL);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.FORK_LITERAL);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.JUNCTION_LITERAL);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.CHOICE_LITERAL);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.ENTRY_POINT_LITERAL);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.EXIT_POINT_LITERAL);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.TERMINATE_LITERAL);

		initEEnum(parameterEffectKindEEnum, ParameterEffectKind.class, "ParameterEffectKind"); //$NON-NLS-1$
		addEEnumLiteral(parameterEffectKindEEnum, ParameterEffectKind.CREATE_LITERAL);
		addEEnumLiteral(parameterEffectKindEEnum, ParameterEffectKind.READ_LITERAL);
		addEEnumLiteral(parameterEffectKindEEnum, ParameterEffectKind.UPDATE_LITERAL);
		addEEnumLiteral(parameterEffectKindEEnum, ParameterEffectKind.DELETE_LITERAL);

		initEEnum(objectNodeOrderingKindEEnum, ObjectNodeOrderingKind.class, "ObjectNodeOrderingKind"); //$NON-NLS-1$
		addEEnumLiteral(objectNodeOrderingKindEEnum, ObjectNodeOrderingKind.UNORDERED_LITERAL);
		addEEnumLiteral(objectNodeOrderingKindEEnum, ObjectNodeOrderingKind.ORDERED_LITERAL);
		addEEnumLiteral(objectNodeOrderingKindEEnum, ObjectNodeOrderingKind.LIFO_LITERAL);
		addEEnumLiteral(objectNodeOrderingKindEEnum, ObjectNodeOrderingKind.FIFO_LITERAL);

		initEEnum(connectorKindEEnum, ConnectorKind.class, "ConnectorKind"); //$NON-NLS-1$
		addEEnumLiteral(connectorKindEEnum, ConnectorKind.ASSEMBLY_LITERAL);
		addEEnumLiteral(connectorKindEEnum, ConnectorKind.DELEGATION_LITERAL);
		addEEnumLiteral(connectorKindEEnum, ConnectorKind.PORT_LINK_LITERAL);

		initEEnum(componentKindEEnum, ComponentKind.class, "ComponentKind"); //$NON-NLS-1$
		addEEnumLiteral(componentKindEEnum, ComponentKind.NONE_LITERAL);
		addEEnumLiteral(componentKindEEnum, ComponentKind.NORMAL_LITERAL);
		addEEnumLiteral(componentKindEEnum, ComponentKind.PRIMITIVE_LITERAL);
		addEEnumLiteral(componentKindEEnum, ComponentKind.STEREOTYPE_LITERAL);

		initEEnum(propertyAccessKindEEnum, PropertyAccessKind.class, "PropertyAccessKind"); //$NON-NLS-1$
		addEEnumLiteral(propertyAccessKindEEnum, PropertyAccessKind.READ_WRITE_LITERAL);
		addEEnumLiteral(propertyAccessKindEEnum, PropertyAccessKind.READ_ONLY_LITERAL);
		addEEnumLiteral(propertyAccessKindEEnum, PropertyAccessKind.WRITE_ONLY_LITERAL);

		initEEnum(portKindEEnum, PortKind.class, "PortKind"); //$NON-NLS-1$
		addEEnumLiteral(portKindEEnum, PortKind.NORMAL_LITERAL);
		addEEnumLiteral(portKindEEnum, PortKind.CREATE_LITERAL);
		addEEnumLiteral(portKindEEnum, PortKind.HYPERPORT_START_LITERAL);
		addEEnumLiteral(portKindEEnum, PortKind.HYPERPORT_END_LITERAL);
		addEEnumLiteral(portKindEEnum, PortKind.AUTOCONNECT_LITERAL);

		initEEnum(requirementsLinkKindEEnum, RequirementsLinkKind.class, "RequirementsLinkKind"); //$NON-NLS-1$
		addEEnumLiteral(requirementsLinkKindEEnum, RequirementsLinkKind.MANDATORY_LITERAL);
		addEEnumLiteral(requirementsLinkKindEEnum, RequirementsLinkKind.OPTIONAL_LITERAL);
		addEEnumLiteral(requirementsLinkKindEEnum, RequirementsLinkKind.ONE_OF_LITERAL);
		addEEnumLiteral(requirementsLinkKindEEnum, RequirementsLinkKind.ONE_OR_MORE_LITERAL);

		// Initialize data types
		initEDataType(integerEDataType, int.class, "Integer", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(booleanEDataType, boolean.class, "Boolean", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(stringEDataType, String.class, "String", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(unlimitedNaturalEDataType, int.class, "UnlimitedNatural", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(sequenceEDataType, List.class, "Sequence", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEDataType(setEDataType, Set.class, "Set", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create annotations
		// union
		createUnionAnnotations();
		// subsets
		createSubsetsAnnotations();
		// duplicates
		createDuplicatesAnnotations();
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
		// redefines
		createRedefinesAnnotations();
	}

	/**
	 * Initializes the annotations for <b>union</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createUnionAnnotations() {
		String source = "union"; //$NON-NLS-1$							
		addAnnotation
		  (getElement_OwnedElement(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getElement_Owner(), 
		   source, 
		   new String[] {
		   });																																																	
		addAnnotation
		  (getNamespace_Member(), 
		   source, 
		   new String[] {
		   });																																		
		addAnnotation
		  (getDirectedRelationship_Source(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getDirectedRelationship_Target(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getRelationship_RelatedElement(), 
		   source, 
		   new String[] {
		   });																																																																																																																
		addAnnotation
		  (getClassifier_Feature(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getClassifier_Attribute(), 
		   source, 
		   new String[] {
		   });											
		addAnnotation
		  (getFeature_FeaturingClassifier(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getConstraint_Context(), 
		   source, 
		   new String[] {
		   });																										
		addAnnotation
		  (getBehavioralFeature_Parameter(), 
		   source, 
		   new String[] {
		   });																																	
		addAnnotation
		  (getRedefinableElement_RedefinitionContext(), 
		   source, 
		   new String[] {
		   });																																																																																																																						
		addAnnotation
		  (getStructuredClassifier_Role(), 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (getActivityEdge_InGroup(), 
		   source, 
		   new String[] {
		   });										
		addAnnotation
		  (getActivityGroup_SuperGroup(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getActivityNode_InGroup(), 
		   source, 
		   new String[] {
		   });								
		addAnnotation
		  (getAction_Output(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getAction_Input(), 
		   source, 
		   new String[] {
		   });																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																											
	}

	/**
	 * Initializes the annotations for <b>subsets</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createSubsetsAnnotations() {
		String source = "subsets"; //$NON-NLS-1$											
		addAnnotation
		  (getElement_OwnedComment(), 
		   source, 
		   new String[] {
		   });																				
		addAnnotation
		  (getMultiplicityElement_UpperValue(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getMultiplicityElement_LowerValue(), 
		   source, 
		   new String[] {
		   });															
		addAnnotation
		  (getNamedElement_NameExpression(), 
		   source, 
		   new String[] {
		   });														
		addAnnotation
		  (getNamespace_OwnedRule(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getNamespace_ImportedMember(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getNamespace_ElementImport(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getNamespace_PackageImport(), 
		   source, 
		   new String[] {
		   });																		
		addAnnotation
		  (getExpression_Operand(), 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (getComment_BodyExpression(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getDirectedRelationship_Source(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getDirectedRelationship_Target(), 
		   source, 
		   new String[] {
		   });								
		addAnnotation
		  (getClass_OwnedOperation(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getClass_NestedClassifier(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getClass_OwnedReception(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getType_Package(), 
		   source, 
		   new String[] {
		   });															
		addAnnotation
		  (getProperty_Class_(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getProperty_OwningAssociation(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getProperty_RedefinedProperty(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getProperty_Datatype(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getProperty_DefaultValue(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getProperty_Qualifier(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getProperty_AssociationEnd(), 
		   source, 
		   new String[] {
		   });									
		addAnnotation
		  (getOperation_Class_(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getOperation_Datatype(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getOperation_Precondition(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getOperation_Postcondition(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getOperation_RedefinedOperation(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getOperation_BodyCondition(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getParameter_Operation(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getParameter_DefaultValue(), 
		   source, 
		   new String[] {
		   });								
		addAnnotation
		  (getPackage_NestedPackage(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getPackage_NestingPackage(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getPackage_OwnedType(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getPackage_PackageMerge(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getPackage_AppliedProfile(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getPackage_PackageExtension(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getEnumeration_OwnedLiteral(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getDataType_OwnedAttribute(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getDataType_OwnedOperation(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getEnumerationLiteral_Enumeration(), 
		   source, 
		   new String[] {
		   });																				
		addAnnotation
		  (getClassifier_Feature(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getClassifier_InheritedMember(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getClassifier_Generalization(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getClassifier_Attribute(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getClassifier_RedefinedClassifier(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getClassifier_Substitution(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getClassifier_OwnedUseCase(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getClassifier_Representation(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getClassifier_Occurrence(), 
		   source, 
		   new String[] {
		   });											
		addAnnotation
		  (getConstraint_Namespace(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getConstraint_Specification(), 
		   source, 
		   new String[] {
		   });																							
		addAnnotation
		  (getBehavioralFeature_Parameter(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getBehavioralFeature_FormalParameter(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getBehavioralFeature_ReturnResult(), 
		   source, 
		   new String[] {
		   });										
		addAnnotation
		  (getInstanceSpecification_Slot(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getInstanceSpecification_Specification(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getSlot_OwningInstance(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getSlot_Value(), 
		   source, 
		   new String[] {
		   });															
		addAnnotation
		  (getGeneralization_Specific(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getGeneralization_General(), 
		   source, 
		   new String[] {
		   });													
		addAnnotation
		  (getElementImport_ImportedElement(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getElementImport_ImportingNamespace(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getPackageImport_ImportedPackage(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getPackageImport_ImportingNamespace(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getAssociation_OwnedEnd(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getAssociation_EndType(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getAssociation_MemberEnd(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getPackageMerge_MergingPackage(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getPackageMerge_MergedPackage(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getProfile_OwnedStereotype(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getProfile_MetaclassReference(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getProfile_MetamodelReference(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getProfileApplication_ImportedProfile(), 
		   source, 
		   new String[] {
		   });									
		addAnnotation
		  (getBehavior_RedefinedBehavior(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getBehavior_Parameter(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getBehavior_Precondition(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getBehavior_Postcondition(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getBehavioredClassifier_OwnedBehavior(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getBehavioredClassifier_ClassifierBehavior(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getBehavioredClassifier_Implementation(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getBehavioredClassifier_OwnedTrigger(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getActivity_Edge(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getActivity_Group(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getActivity_Node(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getActivity_Action(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getActivity_StructuredNode(), 
		   source, 
		   new String[] {
		   });								
		addAnnotation
		  (getAbstraction_Mapping(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getRealization_Abstraction(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getRealization_RealizingClassifier(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getSubstitution_Contract(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getSubstitution_SubstitutingClassifier(), 
		   source, 
		   new String[] {
		   });																				
		addAnnotation
		  (getConnector_RedefinedConnector(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getConnector_End(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getStructuredClassifier_OwnedAttribute(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getStructuredClassifier_Role(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getStructuredClassifier_OwnedConnector(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getActivityEdge_Activity(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getActivityEdge_Guard(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getActivityEdge_InStructuredNode(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getActivityEdge_InPartition(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getActivityEdge_Weight(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getActivityGroup_SuperGroup(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getActivityGroup_ActivityGroup_activity(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getActivityNode_Activity(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getActivityNode_InStructuredNode(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getActivityNode_InPartition(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getActivityNode_InInterruptibleRegion(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getAction_Output(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getAction_Input(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getAction_LocalPrecondition(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getAction_LocalPostcondition(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getObjectNode_UpperBound(), 
		   source, 
		   new String[] {
		   });													
		addAnnotation
		  (getExecutableNode_Handler(), 
		   source, 
		   new String[] {
		   });											
		addAnnotation
		  (getInterface_OwnedAttribute(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getInterface_OwnedOperation(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getInterface_RedefinedInterface(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getInterface_NestedClassifier(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getInterface_OwnedReception(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getInterface_Protocol(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getImplementation_Contract(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getImplementation_ImplementingClassifier(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getArtifact_Manifestation(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getArtifact_OwnedOperation(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getArtifact_OwnedAttribute(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getManifestation_UtilizedElement(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getExtend_ExtendedCase(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getExtend_Extension(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getExtend_Condition(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getUseCase_Include(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getUseCase_Extend(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getUseCase_ExtensionPoint(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getInclude_IncludingCase(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getInclude_Addition(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getCollaborationOccurrence_RoleBinding(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getCollaboration_CollaborationRole(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getPort_RedefinedPort(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getEncapsulatedClassifier_OwnedPort(), 
		   source, 
		   new String[] {
		   });								
		addAnnotation
		  (getChangeTrigger_ChangeExpression(), 
		   source, 
		   new String[] {
		   });								
		addAnnotation
		  (getSignal_OwnedAttribute(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getTimeTrigger_When(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getVariable_Scope(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getStructuredActivityNode_Variable(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getConditionalNode_Clause(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getConditionalNode_Result(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getLoopNode_Result(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getLoopNode_LoopVariable(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getLoopNode_LoopVariableInput(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getInteraction_Lifeline(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getInteraction_Message(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getInteraction_FormalGate(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getInteractionFragment_GeneralOrdering(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getInteractionFragment_EnclosingOperand(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getLifeline_Interaction(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getLifeline_Selector(), 
		   source, 
		   new String[] {
		   });								
		addAnnotation
		  (getMessage_Interaction(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getMessage_Argument(), 
		   source, 
		   new String[] {
		   });																	
		addAnnotation
		  (getStateInvariant_Invariant(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getTemplateSignature_OwnedParameter(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getTemplateSignature_Template(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getTemplateParameter_Signature(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getTemplateParameter_OwnedParameteredElement(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getTemplateParameter_OwnedDefault(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getTemplateableElement_TemplateBinding(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getTemplateableElement_OwnedTemplateSignature(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getStringExpression_SubExpression(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getStringExpression_OwningExpression(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getParameterableElement_OwningParameter(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getTemplateBinding_BoundElement(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getTemplateBinding_Signature(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getTemplateBinding_ParameterSubstitution(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getTemplateParameterSubstitution_TemplateBinding(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getTemplateParameterSubstitution_OwnedActual(), 
		   source, 
		   new String[] {
		   });										
		addAnnotation
		  (getJoinNode_JoinSpec(), 
		   source, 
		   new String[] {
		   });										
		addAnnotation
		  (getActivityPartition_Subgroup(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getActivityPartition_SuperPartition(), 
		   source, 
		   new String[] {
		   });												
		addAnnotation
		  (getExceptionHandler_ProtectedNode(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getInteractionOccurrence_ActualGate(), 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (getInteractionOperand_Guard(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getInteractionConstraint_Minint(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getInteractionConstraint_Maxint(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getCombinedFragment_Operand(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getCombinedFragment_CfragmentGate(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getStateMachine_Region(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getStateMachine_ConnectionPoint(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getRegion_Subvertex(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getRegion_Transition(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getRegion_StateMachine(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getRegion_State(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getState_Connection(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getState_Region(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getState_Entry(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getState_Exit(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getState_DoActivity(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getState_StateInvariant(), 
		   source, 
		   new String[] {
		   });											
		addAnnotation
		  (getTransition_Container(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getTransition_Guard(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getTransition_Effect(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getCreateObjectAction_Result(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getDestroyObjectAction_Target(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getTestIdentityAction_First(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getTestIdentityAction_Second(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getTestIdentityAction_Result(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getReadSelfAction_Result(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getStructuralFeatureAction_Object(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getReadStructuralFeatureAction_Result(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getWriteStructuralFeatureAction_Value(), 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (getAddStructuralFeatureValueAction_InsertAt(), 
		   source, 
		   new String[] {
		   });									
		addAnnotation
		  (getReadLinkAction_Result(), 
		   source, 
		   new String[] {
		   });											
		addAnnotation
		  (getClearAssociationAction_Object(), 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (getReadVariableAction_Result(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getWriteVariableAction_Value(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getAddVariableValueAction_InsertAt(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getApplyFunctionAction_Argument(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getApplyFunctionAction_Result(), 
		   source, 
		   new String[] {
		   });								
		addAnnotation
		  (getCallAction_Result(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getInvocationAction_Argument(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getSendSignalAction_Target(), 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (getSendObjectAction_Target(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getCallOperationAction_Target(), 
		   source, 
		   new String[] {
		   });																																					
		addAnnotation
		  (getParameterSet_Condition(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getComponent_Realization(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getDeployment_DeployedArtifact(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getDeployment_Location(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getDeployment_Configuration(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getDeploymentTarget_Deployment(), 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (getProtocolConformance_SpecificMachine(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getProtocolConformance_GeneralMachine(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getProtocolStateMachine_Conformance(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getProtocolTransition_PostCondition(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getProtocolTransition_PreCondition(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getReadExtentAction_Result(), 
		   source, 
		   new String[] {
		   });								
		addAnnotation
		  (getReclassifyObjectAction_Object(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getReadIsClassifiedObjectAction_Result(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getReadIsClassifiedObjectAction_Object(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getStartOwnedBehaviorAction_Object(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getReadLinkObjectEndAction_Object(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getReadLinkObjectEndAction_Result(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getReadLinkObjectEndQualifierAction_Object(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getReadLinkObjectEndQualifierAction_Result(), 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (getCreateLinkObjectAction_Result(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getAcceptEventAction_Result(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getAcceptCallAction_ReturnInformation(), 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (getReplyAction_ReplyValue(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getReplyAction_ReturnInformation(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getRaiseExceptionAction_Exception(), 
		   source, 
		   new String[] {
		   });		
	}

	/**
	 * Initializes the annotations for <b>duplicates</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createDuplicatesAnnotations() {
		String source = "duplicates"; //$NON-NLS-1$																																			
		addAnnotation
		  (namedElementEClass, 
		   source, 
		   new String[] {
		   });															
		addAnnotation
		  (namespaceEClass, 
		   source, 
		   new String[] {
		   });																																																				
		addAnnotation
		  (classEClass, 
		   source, 
		   new String[] {
		   });																
		addAnnotation
		  (propertyEClass, 
		   source, 
		   new String[] {
		   });																										
		addAnnotation
		  (operationEClass, 
		   source, 
		   new String[] {
		   });																											
		addAnnotation
		  (packageEClass, 
		   source, 
		   new String[] {
		   });																			
		addAnnotation
		  (dataTypeEClass, 
		   source, 
		   new String[] {
		   });																																																						
		addAnnotation
		  (literalBooleanEClass, 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (literalStringEClass, 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (literalNullEClass, 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (literalIntegerEClass, 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (literalUnlimitedNaturalEClass, 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (behavioralFeatureEClass, 
		   source, 
		   new String[] {
		   });																													
		addAnnotation
		  (redefinableElementEClass, 
		   source, 
		   new String[] {
		   });																																																				
		addAnnotation
		  (extensionEClass, 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (extensionEndEClass, 
		   source, 
		   new String[] {
		   });																																																																																					
		addAnnotation
		  (activityGroupEClass, 
		   source, 
		   new String[] {
		   });																																																																																																																								
		addAnnotation
		  (structuredActivityNodeEClass, 
		   source, 
		   new String[] {
		   });																																																
		addAnnotation
		  (eventOccurrenceEClass, 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (stateInvariantEClass, 
		   source, 
		   new String[] {
		   });																																																																																																	
		addAnnotation
		  (regionEClass, 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (stateEClass, 
		   source, 
		   new String[] {
		   });																
		addAnnotation
		  (transitionEClass, 
		   source, 
		   new String[] {
		   });																																																						
		addAnnotation
		  (linkEndCreationDataEClass, 
		   source, 
		   new String[] {
		   });					
		addAnnotation
		  (createLinkActionEClass, 
		   source, 
		   new String[] {
		   });																																																														
		addAnnotation
		  (durationIntervalEClass, 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (timeConstraintEClass, 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (intervalConstraintEClass, 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (timeIntervalEClass, 
		   source, 
		   new String[] {
		   });						
		addAnnotation
		  (durationConstraintEClass, 
		   source, 
		   new String[] {
		   });																																																																				
		addAnnotation
		  (createLinkObjectActionEClass, 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (acceptCallActionEClass, 
		   source, 
		   new String[] {
		   });												
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$																																																																				
		addAnnotation
		  (getOpaqueExpression_Body(), 
		   source, 
		   new String[] {
			 "kind", "element" //$NON-NLS-1$ //$NON-NLS-2$
		   });																		
		addAnnotation
		  (getComment_Body(), 
		   source, 
		   new String[] {
			 "kind", "element" //$NON-NLS-1$ //$NON-NLS-2$
		   });																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																					
	}

	/**
	 * Initializes the annotations for <b>redefines</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createRedefinesAnnotations() {
		String source = "redefines"; //$NON-NLS-1$																																																																																																							
		addAnnotation
		  (getClass_SuperClass(), 
		   source, 
		   new String[] {
		   });																																																																										
		addAnnotation
		  (getPackage_OwnedMember(), 
		   source, 
		   new String[] {
		   });																																																								
		addAnnotation
		  (getConstraint_Namespace(), 
		   source, 
		   new String[] {
		   });																																																																		
		addAnnotation
		  (getPackageableElement_PackageableElement_visibility(), 
		   source, 
		   new String[] {
		   });																																																									
		addAnnotation
		  (getBehavioredClassifier_OwnedStateMachine(), 
		   source, 
		   new String[] {
		   });																																																											
		addAnnotation
		  (getActivityEdge_RedefinedElement(), 
		   source, 
		   new String[] {
		   });												
		addAnnotation
		  (getActivityGroup_ActivityGroup_activity(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getActivityNode_RedefinedElement(), 
		   source, 
		   new String[] {
		   });																																																																																																																		
		addAnnotation
		  (getStructuredActivityNode_ContainedNode(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getStructuredActivityNode_ContainedEdge(), 
		   source, 
		   new String[] {
		   });																																																																																																				
		addAnnotation
		  (getActivityPartition_ContainedEdge(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getActivityPartition_ContainedNode(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getActivityPartition_Subgroup(), 
		   source, 
		   new String[] {
		   });																																											
		addAnnotation
		  (getStateMachine_ExtendedStateMachine(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getStateMachine_StateMachine_redefinitionContext(), 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (getRegion_ExtendedRegion(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getState_RedefinedState(), 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (getVertex_Container(), 
		   source, 
		   new String[] {
		   });													
		addAnnotation
		  (getTransition_RedefinedTransition(), 
		   source, 
		   new String[] {
		   });																																																																																																	
		addAnnotation
		  (getSendObjectAction_Request(), 
		   source, 
		   new String[] {
		   });															
		addAnnotation
		  (getTimeObservationAction_Now(), 
		   source, 
		   new String[] {
		   });															
		addAnnotation
		  (getDurationObservationAction_Duration(), 
		   source, 
		   new String[] {
		   });									
		addAnnotation
		  (getInterruptibleActivityRegion_ContainedNode(), 
		   source, 
		   new String[] {
		   });							
		addAnnotation
		  (getComponent_OwnedMember(), 
		   source, 
		   new String[] {
		   });									
		addAnnotation
		  (getNode_NestedNode(), 
		   source, 
		   new String[] {
		   });																																																																		
	}

} //UML2PackageImpl
