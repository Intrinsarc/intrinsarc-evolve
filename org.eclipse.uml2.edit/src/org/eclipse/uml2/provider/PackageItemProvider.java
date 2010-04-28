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
 * $Id: PackageItemProvider.java,v 1.2 2009-04-22 10:00:58 andrew Exp $
 */
package org.eclipse.uml2.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.domain.EditingDomain;

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

import org.eclipse.uml2.common.edit.command.SubsetAddCommand;
import org.eclipse.uml2.common.edit.command.SubsetReplaceCommand;
import org.eclipse.uml2.common.edit.command.SubsetSetCommand;
import org.eclipse.uml2.common.edit.command.SupersetRemoveCommand;
import org.eclipse.uml2.common.edit.command.SupersetReplaceCommand;
import org.eclipse.uml2.common.edit.command.SupersetSetCommand;

/**
 * This is the item provider adapter for a {@link org.eclipse.uml2.Package} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PackageItemProvider
	extends NamespaceItemProvider
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
	public PackageItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addTemplateParameterPropertyDescriptor(object);
			addOwningParameterPropertyDescriptor(object);
			addPackageableElement_visibilityPropertyDescriptor(object);
			addNestedPackagePropertyDescriptor(object);
			addNestingPackagePropertyDescriptor(object);
			addOwnedTypePropertyDescriptor(object);
			addOwnedMemberPropertyDescriptor(object);
			addPackageMergePropertyDescriptor(object);
			addAppliedProfilePropertyDescriptor(object);
			addPackageExtensionPropertyDescriptor(object);
			addReadOnlyPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Template Parameter feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTemplateParameterPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ParameterableElement_templateParameter_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ParameterableElement_templateParameter_feature", "_UI_ParameterableElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getParameterableElement_TemplateParameter(),
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Owning Parameter feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOwningParameterPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ParameterableElement_owningParameter_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ParameterableElement_owningParameter_feature", "_UI_ParameterableElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getParameterableElement_OwningParameter(),
				 true,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Packageable Element visibility feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPackageableElement_visibilityPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_PackageableElement_packageableElement_visibility_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_PackageableElement_packageableElement_visibility_feature", "_UI_PackageableElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getPackageableElement_PackageableElement_visibility(),
				 true,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Nested Package feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNestedPackagePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Package_nestedPackage_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Package_nestedPackage_feature", "_UI_Package_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getPackage_NestedPackage(),
				 false,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Nesting Package feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNestingPackagePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Package_nestingPackage_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Package_nestingPackage_feature", "_UI_Package_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getPackage_NestingPackage(),
				 false,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Owned Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOwnedTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Package_ownedType_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Package_ownedType_feature", "_UI_Package_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getPackage_OwnedType(),
				 false,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Owned Member feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addOwnedMemberPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Package_ownedMember_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Package_ownedMember_feature", "_UI_Package_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 true,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Package Merge feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPackageMergePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Package_packageMerge_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Package_packageMerge_feature", "_UI_Package_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getPackage_PackageMerge(),
				 true,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Applied Profile feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addAppliedProfilePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Package_appliedProfile_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Package_appliedProfile_feature", "_UI_Package_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getPackage_AppliedProfile(),
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Package Extension feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPackageExtensionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Package_packageExtension_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Package_packageExtension_feature", "_UI_Package_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getPackage_PackageExtension(),
				 true,
				 null,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert" //$NON-NLS-1$
				 }));
	}

	/**
	 * This adds a property descriptor for the Read Only feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addReadOnlyPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Package_readOnly_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_Package_readOnly_feature", "_UI_Package_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 UML2Package.eINSTANCE.getPackage_ReadOnly(),
				 true,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(UML2Package.eINSTANCE.getPackage_OwnedMember());
			childrenFeatures.add(UML2Package.eINSTANCE.getPackage_PackageMerge());
			childrenFeatures.add(UML2Package.eINSTANCE.getPackage_PackageExtension());
			childrenFeatures.add(UML2Package.eINSTANCE.getPackage_J_diagramHolder());
			childrenFeatures.add(UML2Package.eINSTANCE.getPackage_ChildPackages());
			childrenFeatures.add(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders());
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns Package.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getImage(Object object) {
		return getResourceLocator().getImage("full/obj16/Package"); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(Object object) {
		String label = ((org.eclipse.uml2.Package)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Package_type") : //$NON-NLS-1$
			getString("_UI_Package_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(org.eclipse.uml2.Package.class)) {
			case UML2Package.PACKAGE__PACKAGEABLE_ELEMENT_VISIBILITY:
			case UML2Package.PACKAGE__READ_ONLY:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case UML2Package.PACKAGE__OWNED_MEMBER:
			case UML2Package.PACKAGE__PACKAGE_MERGE:
			case UML2Package.PACKAGE__PACKAGE_EXTENSION:
			case UML2Package.PACKAGE__JDIAGRAM_HOLDER:
			case UML2Package.PACKAGE__CHILD_PACKAGES:
			case UML2Package.PACKAGE__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS:
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
	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createClass()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createPackage()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createDataType()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createEnumeration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createInstanceSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createEnumerationLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createPrimitiveType()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createAssociation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createStereotype()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createProfile()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createExtension()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createActivity()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createDependency()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createPermission()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createUsage()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createAbstraction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createRealization()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createSubstitution()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createGeneralizationSet()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createAssociationClass()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createInformationItem()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createInformationFlow()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createModel()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createInterface()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createImplementation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createArtifact()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createManifestation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createActor()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createUseCase()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createCollaboration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createSignal()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createInteraction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createInteractionConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createStateMachine()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createPrimitiveFunction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createIntervalConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createTimeConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createDurationConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createComponent()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createDeployment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createDevice()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createExecutionEnvironment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createCommunicationPath()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createProtocolStateMachine()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createDeploymentSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_OwnedMember(),
				 UML2Factory.eINSTANCE.createSavedReference()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_PackageMerge(),
				 UML2Factory.eINSTANCE.createPackageMerge()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_PackageExtension(),
				 UML2Factory.eINSTANCE.createPackageMerge()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_J_diagramHolder(),
				 UML2Factory.eINSTANCE.createJ_DiagramHolder()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_ChildPackages(),
				 UML2Factory.eINSTANCE.createPackage()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_ChildPackages(),
				 UML2Factory.eINSTANCE.createProfile()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_ChildPackages(),
				 UML2Factory.eINSTANCE.createModel()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createOpaqueExpression()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createExpression()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createComment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createClass()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createProperty()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createPackage()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDataType()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createEnumeration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInstanceSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createEnumerationLiteral()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createPrimitiveType()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createLiteralBoolean()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createLiteralString()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createLiteralNull()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createLiteralInteger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createLiteralUnlimitedNatural()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createSlot()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInstanceValue()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createGeneralization()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createElementImport()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createPackageImport()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createAssociation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createPackageMerge()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createStereotype()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createProfile()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createProfileApplication()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createExtension()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createExtensionEnd()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createActivity()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDependency()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createPermission()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createUsage()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createAbstraction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createRealization()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createSubstitution()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createGeneralizationSet()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createAssociationClass()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInformationItem()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInformationFlow()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createModel()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createConnectorEnd()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createControlFlow()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createObjectFlow()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInitialNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createActivityFinalNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDecisionNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createMergeNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createOutputPin()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInputPin()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createActivityParameterNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createValuePin()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInterface()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createImplementation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createArtifact()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createManifestation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createActor()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createExtend()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createUseCase()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createExtensionPoint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInclude()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createCollaborationOccurrence()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createCollaboration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createCallTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createChangeTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createReception()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createSignal()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createSignalTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createTimeTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createAnyTrigger()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createVariable()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createStructuredActivityNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createConditionalNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createClause()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createLoopNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInteraction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createLifeline()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createMessage()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createGeneralOrdering()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createEventOccurrence()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createExecutionOccurrence()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createStateInvariant()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createStop()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createTemplateSignature()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createTemplateParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createStringExpression()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createTemplateBinding()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createTemplateParameterSubstitution()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createOperationTemplateParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createClassifierTemplateParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createRedefinableTemplateSignature()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createConnectableElementTemplateParameter()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createForkNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createJoinNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createFlowFinalNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createCentralBufferNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createActivityPartition()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createExpansionNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createExpansionRegion()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createExceptionHandler()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInteractionOccurrence()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createGate()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createPartDecomposition()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInteractionOperand()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInteractionConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createCombinedFragment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createContinuation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createStateMachine()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createRegion()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createPseudostate()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createState()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createConnectionPointReference()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createTransition()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createFinalState()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createCreateObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDestroyObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createTestIdentityAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createReadSelfAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createReadStructuralFeatureAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createClearStructuralFeatureAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createRemoveStructuralFeatureValueAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createAddStructuralFeatureValueAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createLinkEndData()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createReadLinkAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createLinkEndCreationData()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createCreateLinkAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDestroyLinkAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createClearAssociationAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createReadVariableAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createClearVariableAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createAddVariableValueAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createRemoveVariableValueAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createApplyFunctionAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createPrimitiveFunction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createSendSignalAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createBroadcastSignalAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createSendObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createCallOperationAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createCallBehaviorAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createTimeExpression()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDuration()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createTimeObservationAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInterval()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDurationInterval()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createIntervalConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createTimeConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createTimeInterval()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDurationObservationAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDurationConstraint()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDataStoreNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createInterruptibleActivityRegion()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createParameterSet()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createComponent()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeployment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createNode()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDevice()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createExecutionEnvironment()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createCommunicationPath()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createProtocolConformance()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createProtocolStateMachine()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createProtocolTransition()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createReadExtentAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createReclassifyObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createReadIsClassifiedObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createStartOwnedBehaviorAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createQualifierValue()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createReadLinkObjectEndAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createReadLinkObjectEndQualifierAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createCreateLinkObjectAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createAcceptEventAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createAcceptCallAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createReplyAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createRaiseExceptionAction()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeploymentSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createJ_FigureContainer()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createJ_Figure()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createJ_Property()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createJ_Diagram()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createJ_DiagramHolder()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createAppliedBasicStereotypeValue()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createPropertyValueSpecification()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaReplacedConstituent()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaDeletedConstituent()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaReplacedAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaDeletedAttribute()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaReplacedPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaDeletedPort()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaReplacedConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaDeletedConnector()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaReplacedOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaDeletedOperation()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createPortRemap()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createSavedReference()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createRequirementsFeature()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaReplacedRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaDeletedRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaDeletedTrace()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders(),
				 UML2Factory.eINSTANCE.createDeltaReplacedTrace()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCreateChildText(Object owner, Object feature, Object child, Collection selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == UML2Package.eINSTANCE.getElement_OwnedComment() ||
			childFeature == UML2Package.eINSTANCE.getPackage_AnonymousDeletedImportPlaceholders() ||
			childFeature == UML2Package.eINSTANCE.getElement_AppliedBasicStereotypeValues() ||
			childFeature == UML2Package.eINSTANCE.getTemplateableElement_TemplateBinding() ||
			childFeature == UML2Package.eINSTANCE.getTemplateableElement_OwnedTemplateSignature() ||
			childFeature == UML2Package.eINSTANCE.getNamedElement_NameExpression() ||
			childFeature == UML2Package.eINSTANCE.getNamedElement_OwnedAnonymousDependencies() ||
			childFeature == UML2Package.eINSTANCE.getPackage_OwnedMember() ||
			childFeature == UML2Package.eINSTANCE.getNamespace_OwnedRule() ||
			childFeature == UML2Package.eINSTANCE.getNamespace_ElementImport() ||
			childFeature == UML2Package.eINSTANCE.getNamespace_PackageImport() ||
			childFeature == UML2Package.eINSTANCE.getPackage_ChildPackages() ||
			childFeature == UML2Package.eINSTANCE.getPackage_PackageMerge() ||
			childFeature == UML2Package.eINSTANCE.getPackage_PackageExtension() ||
			childFeature == UML2Package.eINSTANCE.getPackage_J_diagramHolder();

		if (qualify) {
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
	public ResourceLocator getResourceLocator() {
		return UML2EditPlugin.INSTANCE;
	}


	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createAddCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection, int)
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Command createAddCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Collection collection, int index) {
		if (feature == UML2Package.eINSTANCE.getPackage_AppliedProfile()) {
			return new SubsetAddCommand(domain, owner, feature, new EStructuralFeature[] {UML2Package.eINSTANCE.getNamespace_PackageImport()}, collection, index);
		}
		return super.createAddCommand(domain, owner, feature, collection, index);
	}

	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createRemoveCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Command createRemoveCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Collection collection) {
		if (feature == UML2Package.eINSTANCE.getNamespace_PackageImport()) {
			return new SupersetRemoveCommand(domain, owner, feature, new EStructuralFeature[] {UML2Package.eINSTANCE.getPackage_AppliedProfile()}, collection);
		}
		return super.createRemoveCommand(domain, owner, feature, collection);
	}

	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createReplaceCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, org.eclipse.emf.ecore.EObject, java.util.Collection)
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Command createReplaceCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, EObject value, Collection collection) {
		if (feature == UML2Package.eINSTANCE.getPackage_AppliedProfile()) {
			return new SubsetReplaceCommand(domain, owner, feature, new EStructuralFeature[] {UML2Package.eINSTANCE.getNamespace_PackageImport()}, value, collection);
		}
		if (feature == UML2Package.eINSTANCE.getNamespace_PackageImport()) {
			return new SupersetReplaceCommand(domain, owner, feature, new EStructuralFeature[] {UML2Package.eINSTANCE.getPackage_AppliedProfile()}, value, collection);
		}
		return super.createReplaceCommand(domain, owner, feature, value, collection);
	}

	/**
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createSetCommand(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Command createSetCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value) {
		if (feature == UML2Package.eINSTANCE.getParameterableElement_OwningParameter()) {
			return new SubsetSetCommand(domain, owner, feature, new EStructuralFeature[] {UML2Package.eINSTANCE.getParameterableElement_TemplateParameter()}, value);
		}
		if (feature == UML2Package.eINSTANCE.getParameterableElement_TemplateParameter()) {
			return new SupersetSetCommand(domain, owner, feature, new EStructuralFeature[] {UML2Package.eINSTANCE.getParameterableElement_OwningParameter()}, value);
		}
		return super.createSetCommand(domain, owner, feature, value);
	}

}
