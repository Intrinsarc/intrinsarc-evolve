

/**
 * <copyright>
 * </copyright>
 *
 * $Id: DeltaReplacedConstituentItemProvider.java,v 1.2 2009-04-22 10:00:58 andrew Exp $
 */
package org.eclipse.uml2.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.uml2.DeltaReplacedConstituent;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;

/**
 * This is the item provider adapter for a {@link org.eclipse.uml2.DeltaReplacedConstituent} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DeltaReplacedConstituentItemProvider
	extends ElementItemProvider
	implements	
		IEditingDomainItemProvider,	
		IStructuredItemContentProvider,	
		ITreeItemContentProvider,	
		IItemLabelProvider,	
		IItemPropertySource {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaReplacedConstituentItemProvider(AdapterFactory adapterFactory)
	{
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getPropertyDescriptors(Object object)
	{
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addReplacedPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Replaced feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addReplacedPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_DeltaReplacedConstituent_replaced_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_DeltaReplacedConstituent_replaced_feature", "_UI_DeltaReplacedConstituent_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replaced(),
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Collection getChildrenFeatures(Object object)
	{
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement());
		}
		return childrenFeatures;
	}

	/**
	 * This returns DeltaReplacedConstituent.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getImage(Object object)
	{
		return getResourceLocator().getImage("full/obj16/DeltaReplacedConstituent"); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(Object object)
	{
		DeltaReplacedConstituent deltaReplacedConstituent = (DeltaReplacedConstituent)object;
		return getString("_UI_DeltaReplacedConstituent_type") + " " + deltaReplacedConstituent.getJ_deleted(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void notifyChanged(Notification notification)
	{
		updateChildren(notification);

		switch (notification.getFeatureID(DeltaReplacedConstituent.class))
		{
			case UML2Package.DELTA_REPLACED_CONSTITUENT__REPLACEMENT:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing all of the children that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object)
	{
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createOpaqueExpression()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExpression()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createComment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createClass()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createProperty()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPackage()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createEnumeration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDataType()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createEnumerationLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPrimitiveType()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLiteralBoolean()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLiteralString()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLiteralNull()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLiteralInteger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLiteralUnlimitedNatural()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInstanceSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSlot()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInstanceValue()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createGeneralization()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createElementImport()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPackageImport()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAssociation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPackageMerge()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStereotype()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createProfile()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createProfileApplication()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExtension()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExtensionEnd()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createActivity()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPermission()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDependency()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createUsage()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAbstraction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRealization()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSubstitution()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createGeneralizationSet()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAssociationClass()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInformationItem()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInformationFlow()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createModel()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createConnectorEnd()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createControlFlow()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createObjectFlow()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInitialNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createActivityFinalNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDecisionNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createMergeNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createOutputPin()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInputPin()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createActivityParameterNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createValuePin()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInterface()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createImplementation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createArtifact()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createManifestation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createActor()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExtend()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createUseCase()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExtensionPoint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInclude()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCollaborationOccurrence()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCollaboration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCallTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createChangeTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReception()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSignal()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSignalTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTimeTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAnyTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createVariable()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStructuredActivityNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createConditionalNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createClause()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLoopNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInteraction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLifeline()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createMessage()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createGeneralOrdering()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createEventOccurrence()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExecutionOccurrence()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStateInvariant()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStop()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTemplateSignature()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTemplateParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStringExpression()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTemplateBinding()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTemplateParameterSubstitution()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createOperationTemplateParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createClassifierTemplateParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRedefinableTemplateSignature()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createConnectableElementTemplateParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createForkNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createJoinNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createFlowFinalNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCentralBufferNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createActivityPartition()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExpansionNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExpansionRegion()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExceptionHandler()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInteractionOccurrence()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createGate()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPartDecomposition()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInteractionOperand()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInteractionConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCombinedFragment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createContinuation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStateMachine()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRegion()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPseudostate()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createState()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createConnectionPointReference()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTransition()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createFinalState()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCreateObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDestroyObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTestIdentityAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadSelfAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadStructuralFeatureAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createClearStructuralFeatureAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRemoveStructuralFeatureValueAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAddStructuralFeatureValueAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLinkEndData()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadLinkAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLinkEndCreationData()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCreateLinkAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDestroyLinkAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createClearAssociationAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadVariableAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createClearVariableAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAddVariableValueAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRemoveVariableValueAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createApplyFunctionAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPrimitiveFunction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSendSignalAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createBroadcastSignalAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSendObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCallOperationAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCallBehaviorAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTimeExpression()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDuration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTimeObservationAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDurationInterval()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInterval()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTimeConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createIntervalConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTimeInterval()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDurationObservationAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDurationConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDataStoreNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInterruptibleActivityRegion()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createParameterSet()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createComponent()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeployment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDevice()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExecutionEnvironment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCommunicationPath()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createProtocolConformance()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createProtocolStateMachine()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createProtocolTransition()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadExtentAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReclassifyObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadIsClassifiedObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStartOwnedBehaviorAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createQualifierValue()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadLinkObjectEndAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadLinkObjectEndQualifierAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCreateLinkObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAcceptEventAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAcceptCallAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReplyAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRaiseExceptionAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeploymentSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createJ_FigureContainer()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createJ_Figure()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createJ_Property()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createJ_Diagram()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createJ_DiagramHolder()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAppliedBasicStereotypeValue()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPropertyValueSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedConstituent()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedConstituent()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPortRemap()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSavedReference()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRequirementsFeature()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedTrace()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedTrace()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createOpaqueExpression()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExpression()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createComment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createClass()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createProperty()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPackage()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDataType()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createEnumeration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInstanceSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createEnumerationLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPrimitiveType()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLiteralBoolean()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLiteralString()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLiteralNull()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLiteralInteger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLiteralUnlimitedNatural()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSlot()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInstanceValue()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createGeneralization()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createElementImport()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPackageImport()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAssociation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPackageMerge()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStereotype()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createProfile()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createProfileApplication()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExtension()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExtensionEnd()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createActivity()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDependency()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPermission()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createUsage()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAbstraction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRealization()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSubstitution()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createGeneralizationSet()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAssociationClass()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInformationItem()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInformationFlow()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createModel()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createConnectorEnd()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createControlFlow()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createObjectFlow()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInitialNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createActivityFinalNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDecisionNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createMergeNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createOutputPin()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInputPin()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createActivityParameterNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createValuePin()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInterface()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createImplementation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createArtifact()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createManifestation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createActor()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExtend()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createUseCase()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExtensionPoint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInclude()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCollaborationOccurrence()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCollaboration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCallTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createChangeTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReception()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSignal()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSignalTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTimeTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAnyTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createVariable()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStructuredActivityNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createConditionalNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createClause()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLoopNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInteraction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLifeline()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createMessage()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createGeneralOrdering()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createEventOccurrence()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExecutionOccurrence()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStateInvariant()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStop()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTemplateSignature()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTemplateParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStringExpression()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTemplateBinding()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTemplateParameterSubstitution()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createOperationTemplateParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createClassifierTemplateParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRedefinableTemplateSignature()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createConnectableElementTemplateParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createForkNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createJoinNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createFlowFinalNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCentralBufferNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createActivityPartition()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExpansionNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExpansionRegion()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExceptionHandler()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInteractionOccurrence()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createGate()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPartDecomposition()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInteractionOperand()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInteractionConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCombinedFragment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createContinuation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStateMachine()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRegion()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPseudostate()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createState()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createConnectionPointReference()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTransition()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createFinalState()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCreateObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDestroyObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTestIdentityAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadSelfAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadStructuralFeatureAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createClearStructuralFeatureAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRemoveStructuralFeatureValueAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAddStructuralFeatureValueAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLinkEndData()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadLinkAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createLinkEndCreationData()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCreateLinkAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDestroyLinkAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createClearAssociationAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadVariableAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createClearVariableAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAddVariableValueAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRemoveVariableValueAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createApplyFunctionAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPrimitiveFunction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSendSignalAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createBroadcastSignalAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSendObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCallOperationAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCallBehaviorAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTimeExpression()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDuration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTimeObservationAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInterval()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDurationInterval()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createIntervalConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTimeConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createTimeInterval()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDurationObservationAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDurationConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDataStoreNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createInterruptibleActivityRegion()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createParameterSet()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createComponent()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeployment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDevice()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createExecutionEnvironment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCommunicationPath()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createProtocolConformance()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createProtocolStateMachine()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createProtocolTransition()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadExtentAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReclassifyObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadIsClassifiedObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createStartOwnedBehaviorAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createQualifierValue()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadLinkObjectEndAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReadLinkObjectEndQualifierAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createCreateLinkObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAcceptEventAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAcceptCallAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createReplyAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRaiseExceptionAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeploymentSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createJ_FigureContainer()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createJ_Figure()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createJ_Property()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createJ_Diagram()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createJ_DiagramHolder()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createAppliedBasicStereotypeValue()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPropertyValueSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedConstituent()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedConstituent()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createPortRemap()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createSavedReference()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRequirementsFeature()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaDeletedTrace()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement(),
				 UML2Factory.eINSTANCE.createDeltaReplacedTrace()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCreateChildText(Object owner, Object feature, Object child, Collection selection)
	{
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == UML2Package.eINSTANCE.getElement_OwnedComment() ||
			childFeature == UML2Package.eINSTANCE.getDeltaReplacedConstituent_Replacement() ||
			childFeature == UML2Package.eINSTANCE.getElement_AppliedBasicStereotypeValues();

		if (qualify)
		{
			return getString
				("_UI_CreateChild_text2", //$NON-NLS-1$
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceLocator getResourceLocator()
	{
		return UML2EditPlugin.INSTANCE;
	}


}
