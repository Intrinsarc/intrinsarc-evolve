/*
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - initial API and implementation
 *
 * $Id: GenModelItemProvider.java,v 1.1 2009-03-04 23:07:32 andrew Exp $
 */
package org.eclipse.uml2.codegen.ecore.genmodel.provider;

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
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.uml2.codegen.ecore.genmodel.GenModel;
import org.eclipse.uml2.codegen.ecore.genmodel.GenModelPackage;

import org.eclipse.uml2.codegen.ecore.ui.CodeGenEcoreUIPlugin;

/**
 * This is the item provider adapter for a {@link org.eclipse.uml2.codegen.ecore.genmodel.GenModel} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class GenModelItemProvider
		extends
		org.eclipse.emf.codegen.ecore.genmodel.provider.GenModelItemProvider
		implements IEditingDomainItemProvider, IStructuredItemContentProvider,
		ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

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
	public GenModelItemProvider(AdapterFactory adapterFactory) {
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

			addFactoryMethodsPropertyDescriptor(object);
			addPluralizedGettersPropertyDescriptor(object);
			addCacheAdapterSupportPropertyDescriptor(object);
			addSafeStringsPropertyDescriptor(object);
			addInvariantPrefixPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Factory Methods feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFactoryMethodsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
					.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_GenModel_factoryMethods_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_GenModel_factoryMethods_feature", "_UI_GenModel_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				GenModelPackage.eINSTANCE.getGenModel_FactoryMethods(), true,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				getString("_UI_UML2PropertyCategory"), //$NON-NLS-1$
				null));
	}

	/**
	 * This adds a property descriptor for the Pluralized Getters feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPluralizedGettersPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
					.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_GenModel_pluralizedGetters_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_GenModel_pluralizedGetters_feature", "_UI_GenModel_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				GenModelPackage.eINSTANCE.getGenModel_PluralizedGetters(),
				true, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				getString("_UI_UML2PropertyCategory"), //$NON-NLS-1$
				null));
	}

	/**
	 * This adds a property descriptor for the Cache Adapter Support feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCacheAdapterSupportPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
					.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_GenModel_cacheAdapterSupport_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_GenModel_cacheAdapterSupport_feature", "_UI_GenModel_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				GenModelPackage.eINSTANCE.getGenModel_CacheAdapterSupport(),
				true, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				getString("_UI_UML2PropertyCategory"), //$NON-NLS-1$
				null));
	}

	/**
	 * This adds a property descriptor for the Safe Strings feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSafeStringsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
					.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_GenModel_safeStrings_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_GenModel_safeStrings_feature", "_UI_GenModel_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				GenModelPackage.eINSTANCE.getGenModel_SafeStrings(), true,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				getString("_UI_UML2PropertyCategory"), //$NON-NLS-1$
				null));
	}

	/**
	 * This adds a property descriptor for the Invariant Prefix feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addInvariantPrefixPropertyDescriptor(Object object) {
		itemPropertyDescriptors
			.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory)
					.getRootAdapterFactory(),
				getResourceLocator(),
				getString("_UI_GenModel_invariantPrefix_feature"), //$NON-NLS-1$
				getString(
					"_UI_PropertyDescriptor_description", "_UI_GenModel_invariantPrefix_feature", "_UI_GenModel_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				GenModelPackage.eINSTANCE.getGenModel_InvariantPrefix(), true,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				getString("_UI_UML2PropertyCategory"), //$NON-NLS-1$
				null));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getText(Object object) {
		return super.getText(object);
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

		switch (notification.getFeatureID(GenModel.class)) {
			case GenModelPackage.GEN_MODEL__FACTORY_METHODS :
			case GenModelPackage.GEN_MODEL__PLURALIZED_GETTERS :
			case GenModelPackage.GEN_MODEL__CACHE_ADAPTER_SUPPORT :
			case GenModelPackage.GEN_MODEL__SAFE_STRINGS :
			case GenModelPackage.GEN_MODEL__INVARIANT_PREFIX :
				fireNotifyChanged(new ViewerNotification(notification,
					notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceLocator getResourceLocator() {
		return CodeGenEcoreUIPlugin.INSTANCE;
	}

}
