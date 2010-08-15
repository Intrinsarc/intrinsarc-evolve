

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.uml2.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.uml2.RequirementsFeature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;

/**
 * This is the item provider adapter for a {@link org.eclipse.uml2.RequirementsFeature} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class RequirementsFeatureItemProvider
	extends TypeItemProvider
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
	public RequirementsFeatureItemProvider(AdapterFactory adapterFactory)
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

		}
		return itemPropertyDescriptors;
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
			childrenFeatures.add(UML2Package.eINSTANCE.getRequirementsFeature_Subfeatures());
			childrenFeatures.add(UML2Package.eINSTANCE.getRequirementsFeature_DeltaReplacedSubfeatures());
			childrenFeatures.add(UML2Package.eINSTANCE.getRequirementsFeature_DeltaDeletedSubfeatures());
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
	 * This returns RequirementsFeature.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getImage(Object object)
	{
		return getResourceLocator().getImage("full/obj16/RequirementsFeature"); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(Object object)
	{
		String label = ((RequirementsFeature)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_RequirementsFeature_type") : //$NON-NLS-1$
			getString("_UI_RequirementsFeature_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(RequirementsFeature.class))
		{
			case UML2Package.REQUIREMENTS_FEATURE__SUBFEATURES:
			case UML2Package.REQUIREMENTS_FEATURE__DELTA_REPLACED_SUBFEATURES:
			case UML2Package.REQUIREMENTS_FEATURE__DELTA_DELETED_SUBFEATURES:
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
				(UML2Package.eINSTANCE.getRequirementsFeature_Subfeatures(),
				 UML2Factory.eINSTANCE.createRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getRequirementsFeature_Subfeatures(),
				 UML2Factory.eINSTANCE.createRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getRequirementsFeature_DeltaReplacedSubfeatures(),
				 UML2Factory.eINSTANCE.createDeltaReplacedRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getRequirementsFeature_DeltaReplacedSubfeatures(),
				 UML2Factory.eINSTANCE.createDeltaReplacedRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getRequirementsFeature_DeltaDeletedSubfeatures(),
				 UML2Factory.eINSTANCE.createDeltaDeletedRequirementsFeatureLink()));

		newChildDescriptors.add
			(createChildParameter
				(UML2Package.eINSTANCE.getRequirementsFeature_DeltaDeletedSubfeatures(),
				 UML2Factory.eINSTANCE.createDeltaDeletedRequirementsFeatureLink()));
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
