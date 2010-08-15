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
 * $Id: ClassItemProvider.java,v 1.1 2009-03-04 23:08:21 andrew Exp $
 */
package org.eclipse.uml2.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;

/**
 * This is the item provider adapter for a {@link org.eclipse.uml2.Class} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ClassItemProvider
	extends BehavioredClassifierItemProvider
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
	public ClassItemProvider(AdapterFactory adapterFactory)
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

			addOwnedAttributePropertyDescriptor(object);
			addPartPropertyDescriptor(object);
			addRolePropertyDescriptor(object);
			addOwnedConnectorPropertyDescriptor(object);
			addOwnedPortPropertyDescriptor(object);
			addOwnedOperationPropertyDescriptor(object);
			addSuperClassPropertyDescriptor(object);
			addExtensionPropertyDescriptor(object);
			addNestedClassifierPropertyDescriptor(object);
			addIsActivePropertyDescriptor(object);
			addOwnedReceptionPropertyDescriptor(object);
			addComponentKindPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Owned Attribute feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOwnedAttributePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_StructuredClassifier_ownedAttribute_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_StructuredClassifier_ownedAttribute_feature", "_UI_StructuredClassifier_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute(),
				 true,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Part feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPartPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_StructuredClassifier_part_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_StructuredClassifier_part_feature", "_UI_StructuredClassifier_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getStructuredClassifier_Part(),
				 false,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Role feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addRolePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_StructuredClassifier_role_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_StructuredClassifier_role_feature", "_UI_StructuredClassifier_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getStructuredClassifier_Role(),
				 false,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Owned Connector feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOwnedConnectorPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_StructuredClassifier_ownedConnector_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_StructuredClassifier_ownedConnector_feature", "_UI_StructuredClassifier_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getStructuredClassifier_OwnedConnector(),
				 true,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Owned Port feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOwnedPortPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_EncapsulatedClassifier_ownedPort_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_EncapsulatedClassifier_ownedPort_feature", "_UI_EncapsulatedClassifier_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getEncapsulatedClassifier_OwnedPort(),
				 true,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Owned Operation feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOwnedOperationPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Class_ownedOperation_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Class_ownedOperation_feature", "_UI_Class_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getClass_OwnedOperation(),
				 true,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Is Active feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addIsActivePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Class_isActive_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Class_isActive_feature", "_UI_Class_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getClass_IsActive(),
				 true,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Owned Reception feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOwnedReceptionPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Class_ownedReception_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Class_ownedReception_feature", "_UI_Class_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getClass_OwnedReception(),
				 true,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Component Kind feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addComponentKindPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Class_componentKind_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Class_componentKind_feature", "_UI_Class_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getClass_ComponentKind(),
				 true,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
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
			childrenFeatures.add(UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute());
			childrenFeatures.add(UML2Package.eINSTANCE.getStructuredClassifier_OwnedConnector());
			childrenFeatures.add(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedAttributes());
			childrenFeatures.add(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedAttributes());
			childrenFeatures.add(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedPorts());
			childrenFeatures.add(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedPorts());
			childrenFeatures.add(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedConnectors());
			childrenFeatures.add(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedConnectors());
			childrenFeatures.add(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedOperations());
			childrenFeatures.add(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedOperations());
			childrenFeatures.add(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedTraces());
			childrenFeatures.add(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedTraces());
			childrenFeatures.add(UML2Package.eINSTANCE.getEncapsulatedClassifier_OwnedPort());
			childrenFeatures.add(UML2Package.eINSTANCE.getClass_OwnedOperation());
			childrenFeatures.add(UML2Package.eINSTANCE.getClass_NestedClassifier());
			childrenFeatures.add(UML2Package.eINSTANCE.getClass_OwnedReception());
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EStructuralFeature getChildFeature(Object object, Object child)
	{
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This adds a property descriptor for the Super Class feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSuperClassPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Class_superClass_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Class_superClass_feature", "_UI_Class_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getClass_SuperClass(),
				 false,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Extension feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExtensionPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Class_extension_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Class_extension_feature", "_UI_Class_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getClass_Extension(),
				 false,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Nested Classifier feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNestedClassifierPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Class_nestedClassifier_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Class_nestedClassifier_feature", "_UI_Class_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 true,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This returns Class.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getImage(Object object)
	{
		return getResourceLocator().getImage("full/obj16/Class"); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(Object object)
	{
		String label = ((org.eclipse.uml2.Class)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Class_type") : //$NON-NLS-1$
			getString("_UI_Class_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(org.eclipse.uml2.Class.class))
		{
			case UML2Package.CLASS__IS_ACTIVE:
			case UML2Package.CLASS__COMPONENT_KIND:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case UML2Package.CLASS__OWNED_ATTRIBUTE:
			case UML2Package.CLASS__OWNED_CONNECTOR:
			case UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES:
			case UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES:
			case UML2Package.CLASS__DELTA_DELETED_PORTS:
			case UML2Package.CLASS__DELTA_REPLACED_PORTS:
			case UML2Package.CLASS__DELTA_DELETED_CONNECTORS:
			case UML2Package.CLASS__DELTA_REPLACED_CONNECTORS:
			case UML2Package.CLASS__DELTA_DELETED_OPERATIONS:
			case UML2Package.CLASS__DELTA_REPLACED_OPERATIONS:
			case UML2Package.CLASS__DELTA_DELETED_TRACES:
			case UML2Package.CLASS__DELTA_REPLACED_TRACES:
			case UML2Package.CLASS__OWNED_PORT:
			case UML2Package.CLASS__OWNED_OPERATION:
			case UML2Package.CLASS__NESTED_CLASSIFIER:
			case UML2Package.CLASS__OWNED_RECEPTION:
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
				(UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute(),
				 UML2Factory.eINSTANCE.createProperty()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute(),
				 UML2Factory.eINSTANCE.createExtensionEnd()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute(),
				 UML2Factory.eINSTANCE.createPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute(),
				 UML2Factory.eINSTANCE.createProperty()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute(),
				 UML2Factory.eINSTANCE.createExtensionEnd()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute(),
				 UML2Factory.eINSTANCE.createPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_OwnedConnector(),
				 UML2Factory.eINSTANCE.createConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_OwnedConnector(),
				 UML2Factory.eINSTANCE.createConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedAttributes(),
				 UML2Factory.eINSTANCE.createDeltaDeletedAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedAttributes(),
				 UML2Factory.eINSTANCE.createDeltaDeletedAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedAttributes(),
				 UML2Factory.eINSTANCE.createDeltaReplacedAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedAttributes(),
				 UML2Factory.eINSTANCE.createDeltaReplacedAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedPorts(),
				 UML2Factory.eINSTANCE.createDeltaDeletedPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedPorts(),
				 UML2Factory.eINSTANCE.createDeltaDeletedPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedPorts(),
				 UML2Factory.eINSTANCE.createDeltaReplacedPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedPorts(),
				 UML2Factory.eINSTANCE.createDeltaReplacedPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedConnectors(),
				 UML2Factory.eINSTANCE.createDeltaDeletedConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedConnectors(),
				 UML2Factory.eINSTANCE.createDeltaDeletedConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedConnectors(),
				 UML2Factory.eINSTANCE.createDeltaReplacedConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedConnectors(),
				 UML2Factory.eINSTANCE.createDeltaReplacedConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedOperations(),
				 UML2Factory.eINSTANCE.createDeltaDeletedOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedOperations(),
				 UML2Factory.eINSTANCE.createDeltaDeletedOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedOperations(),
				 UML2Factory.eINSTANCE.createDeltaReplacedOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedOperations(),
				 UML2Factory.eINSTANCE.createDeltaReplacedOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedTraces(),
				 UML2Factory.eINSTANCE.createDeltaDeletedTrace()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaDeletedTraces(),
				 UML2Factory.eINSTANCE.createDeltaDeletedTrace()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedTraces(),
				 UML2Factory.eINSTANCE.createDeltaReplacedTrace()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getStructuredClassifier_DeltaReplacedTraces(),
				 UML2Factory.eINSTANCE.createDeltaReplacedTrace()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getEncapsulatedClassifier_OwnedPort(),
				 UML2Factory.eINSTANCE.createPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getEncapsulatedClassifier_OwnedPort(),
				 UML2Factory.eINSTANCE.createPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_OwnedOperation(),
				 UML2Factory.eINSTANCE.createOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_OwnedOperation(),
				 UML2Factory.eINSTANCE.createOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createClass()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createEnumeration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createDataType()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createPrimitiveType()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createAssociation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createStereotype()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createExtension()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createActivity()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createAssociationClass()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createInformationItem()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createInterface()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createArtifact()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createActor()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createUseCase()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createCollaboration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createSignal()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createInteraction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createStateMachine()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createComponent()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createDevice()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createExecutionEnvironment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createCommunicationPath()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createProtocolStateMachine()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createDeploymentSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createClass()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createDataType()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createEnumeration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createPrimitiveType()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createAssociation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createStereotype()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createExtension()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createActivity()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createAssociationClass()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createInformationItem()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createInterface()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createArtifact()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createActor()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createUseCase()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createCollaboration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createSignal()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createInteraction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createStateMachine()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createComponent()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createDevice()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createExecutionEnvironment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createCommunicationPath()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createProtocolStateMachine()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_NestedClassifier(),
				 UML2Factory.eINSTANCE.createDeploymentSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_OwnedReception(),
				 UML2Factory.eINSTANCE.createReception()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getClass_OwnedReception(),
				 UML2Factory.eINSTANCE.createReception()));
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
			childFeature == UML2Package.eINSTANCE.getNamedElement_OwnedAnonymousDependencies() ||
			childFeature == UML2Package.eINSTANCE.getClassifier_Substitution() ||
			childFeature == UML2Package.eINSTANCE.getBehavioredClassifier_Implementation() ||
			childFeature == UML2Package.eINSTANCE.getClassifier_OwnedUseCase() ||
			childFeature == UML2Package.eINSTANCE.getClass_NestedClassifier() ||
			childFeature == UML2Package.eINSTANCE.getBehavioredClassifier_OwnedBehavior() ||
			childFeature == UML2Package.eINSTANCE.getBehavioredClassifier_OwnedStateMachine() ||
			childFeature == UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute() ||
			childFeature == UML2Package.eINSTANCE.getEncapsulatedClassifier_OwnedPort();

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
