package com.intrinsarc.uml2persistence;

import java.io.*;

import com.objectdb.*;

public class UML2Enhancer
{
  public static void main(String[] args) throws IOException
  {  
    // enhance the EMF shadow classes
    Enhancer.enhance("org.eclipse.emf.ecore.impl.EObjectImpl");
    Enhancer.enhance("org.eclipse.emf.ecore.impl.EModelElementImpl");
    Enhancer.enhance("org.eclipse.emf.ecore.impl.EListArrayList");
    Enhancer.enhance("org.eclipse.emf.common.util.PersistentAbstractEnumerator");

    
    // enhance the EMF replacement list classes
    String classNames[] = {
        "EList",
        "EListClone"
    };    
    for (String className : classNames)
      Enhancer.enhance("com.hopstepjump.emflist.Persistent" + className);
    
    
    // enhance the UML2 enumeration classes
    Enhancer.enhance("org.eclipse.uml2.VisibilityKind");
    Enhancer.enhance("org.eclipse.uml2.AggregationKind");
    Enhancer.enhance("org.eclipse.uml2.ParameterDirectionKind");
    Enhancer.enhance("org.eclipse.uml2.ParameterEffectKind");
    Enhancer.enhance("org.eclipse.uml2.CallConcurrencyKind");
    Enhancer.enhance("org.eclipse.uml2.ConnectorKind");
    Enhancer.enhance("org.eclipse.uml2.ObjectNodeOrderingKind");
    Enhancer.enhance("org.eclipse.uml2.MessageSort");
    Enhancer.enhance("org.eclipse.uml2.ExpansionKind");
    Enhancer.enhance("org.eclipse.uml2.InteractionOperator");
    Enhancer.enhance("org.eclipse.uml2.PseudostateKind");
    Enhancer.enhance("org.eclipse.uml2.TransitionKind");
    Enhancer.enhance("org.eclipse.uml2.ComponentKind");
    Enhancer.enhance("org.eclipse.uml2.PortKind");
    Enhancer.enhance("org.eclipse.uml2.PropertyAccessKind");
    
    
    // enhance the UML2 classes
    Enhancer.enhance("org.eclipse.uml2.impl.ElementImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.AppliedBasicStereotypeValueImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.MultiplicityElementImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.NamedElementImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.NamespaceImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.OpaqueExpressionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ValueSpecificationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ExpressionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CommentImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DirectedRelationshipImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.RelationshipImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ClassImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TypeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PropertyImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.OperationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TypedElementImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ParameterImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PackageImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.EnumerationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DataTypeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.EnumerationLiteralImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PrimitiveTypeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ClassifierImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.FeatureImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ConstraintImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.LiteralBooleanImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.LiteralSpecificationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.LiteralStringImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.LiteralNullImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.LiteralIntegerImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.LiteralUnlimitedNaturalImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.BehavioralFeatureImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.StructuralFeatureImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InstanceSpecificationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.SlotImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InstanceValueImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.RedefinableElementImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.GeneralizationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PackageableElementImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ElementImportImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PackageImportImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.AssociationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PackageMergeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.StereotypeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ProfileImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ProfileApplicationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ExtensionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ExtensionEndImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.BehaviorImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.BehavioredClassifierImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ActivityImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PermissionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DependencyImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.UsageImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.AbstractionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.RealizationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.SubstitutionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.GeneralizationSetImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.AssociationClassImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InformationItemImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InformationFlowImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ModelImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ConnectorEndImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ConnectableElementImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ConnectorImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.StructuredClassifierImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ActivityEdgeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ActivityGroupImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ActivityNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ObjectNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ControlNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ControlFlowImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ObjectFlowImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InitialNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.FinalNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ActivityFinalNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DecisionNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.MergeNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ExecutableNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.OutputPinImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InputPinImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PinImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ActivityParameterNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ValuePinImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InterfaceImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ImplementationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ArtifactImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ManifestationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ActorImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ExtendImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.UseCaseImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ExtensionPointImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.IncludeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CollaborationOccurrenceImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CollaborationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PortImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.EncapsulatedClassifierImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CallTriggerImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.MessageTriggerImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ChangeTriggerImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TriggerImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ReceptionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.SignalImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.SignalTriggerImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TimeTriggerImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.AnyTriggerImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.VariableImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.StructuredActivityNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ConditionalNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ClauseImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.LoopNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InteractionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InteractionFragmentImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.LifelineImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.MessageImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.GeneralOrderingImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.MessageEndImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.EventOccurrenceImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ExecutionOccurrenceImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.StateInvariantImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.StopImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TemplateSignatureImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TemplateParameterImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TemplateableElementImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.StringExpressionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ParameterableElementImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TemplateBindingImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TemplateParameterSubstitutionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.OperationTemplateParameterImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ClassifierTemplateParameterImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ParameterableClassifierImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.RedefinableTemplateSignatureImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TemplateableClassifierImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ConnectableElementTemplateParameterImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ForkNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.JoinNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.FlowFinalNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CentralBufferNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ActivityPartitionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ExpansionNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ExpansionRegionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ExceptionHandlerImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InteractionOccurrenceImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.GateImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PartDecompositionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InteractionOperandImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InteractionConstraintImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CombinedFragmentImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ContinuationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.StateMachineImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.RegionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PseudostateImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.StateImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.VertexImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ConnectionPointReferenceImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TransitionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.FinalStateImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CreateObjectActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DestroyObjectActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TestIdentityActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ReadSelfActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.StructuralFeatureActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ReadStructuralFeatureActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.WriteStructuralFeatureActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ClearStructuralFeatureActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.RemoveStructuralFeatureValueActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.AddStructuralFeatureValueActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.LinkActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.LinkEndDataImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ReadLinkActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.LinkEndCreationDataImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CreateLinkActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.WriteLinkActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DestroyLinkActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ClearAssociationActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.VariableActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ReadVariableActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.WriteVariableActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ClearVariableActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.AddVariableValueActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.RemoveVariableValueActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ApplyFunctionActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PrimitiveFunctionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CallActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InvocationActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.SendSignalActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.BroadcastSignalActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.SendObjectActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CallOperationActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CallBehaviorActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TimeExpressionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DurationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TimeObservationActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DurationIntervalImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.IntervalImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TimeConstraintImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.IntervalConstraintImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.TimeIntervalImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DurationObservationActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DurationConstraintImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DataStoreNodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.InterruptibleActivityRegionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ParameterSetImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ComponentImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeploymentImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeployedArtifactImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeploymentTargetImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.NodeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeviceImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ExecutionEnvironmentImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CommunicationPathImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ProtocolConformanceImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ProtocolStateMachineImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ProtocolTransitionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ReadExtentActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ReclassifyObjectActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ReadIsClassifiedObjectActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.StartOwnedBehaviorActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.QualifierValueImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ReadLinkObjectEndActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ReadLinkObjectEndQualifierActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.CreateLinkObjectActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.AcceptEventActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.AcceptCallActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.ReplyActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.RaiseExceptionActionImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeploymentSpecificationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.J_FigureContainerImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.J_FigureImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.J_PropertyImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.J_DiagramImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.J_DiagramHolderImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.PropertyValueSpecificationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeltaReplacedConstituentImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeltaDeletedConstituentImpl");

    Enhancer.enhance("org.eclipse.uml2.impl.DeltaReplacedAttributeImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeltaDeletedAttributeImpl");

    Enhancer.enhance("org.eclipse.uml2.impl.DeltaReplacedPortImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeltaDeletedPortImpl");

    Enhancer.enhance("org.eclipse.uml2.impl.DeltaReplacedConnectorImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeltaDeletedConnectorImpl");

    Enhancer.enhance("org.eclipse.uml2.impl.DeltaReplacedOperationImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeltaDeletedOperationImpl");

    Enhancer.enhance("org.eclipse.uml2.impl.PortRemapImpl");
    
    Enhancer.enhance("org.eclipse.uml2.impl.SavedReferenceImpl");

    // requirements features
    Enhancer.enhance("org.eclipse.uml2.impl.DeltaReplacedTraceImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeltaDeletedTraceImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeltaReplacedRequirementsFeatureLinkImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.DeltaDeletedRequirementsFeatureLinkImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.RequirementsFeatureImpl");
    Enhancer.enhance("org.eclipse.uml2.impl.RequirementsFeatureLinkImpl");

  }
}
