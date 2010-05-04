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
 * $Id: PackageImpl.java,v 1.2 2009-04-22 10:01:15 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Element;
import org.eclipse.uml2.J_DiagramHolder;
import org.eclipse.uml2.J_Diagram;
import org.eclipse.uml2.Enumeration;
import org.eclipse.uml2.NamedElement;
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.PackageImport;
import org.eclipse.uml2.PackageMerge;
import org.eclipse.uml2.PackageableElement;
import org.eclipse.uml2.ParameterableElement;
import org.eclipse.uml2.PrimitiveType;
import org.eclipse.uml2.Profile;
import org.eclipse.uml2.ProfileApplication;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.Type;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.SubsetEObjectEList;
import org.eclipse.uml2.common.util.SupersetEObjectWithInverseResolvingEList;

import org.eclipse.uml2.common.util.SupersetEObjectContainmentWithInverseEList;

import org.eclipse.uml2.internal.operation.PackageOperations;
import org.eclipse.uml2.internal.operation.ProfileOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Package</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getTemplateParameter <em>Template Parameter</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getOwningParameter <em>Owning Parameter</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getPackageableElement_visibility <em>Packageable Element visibility</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getOwnedMembers <em>Owned Member</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getPackageImports <em>Package Import</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getNestedPackages <em>Nested Package</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getNestingPackage <em>Nesting Package</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getOwnedTypes <em>Owned Type</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getPackageMerges <em>Package Merge</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getAppliedProfiles <em>Applied Profile</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getPackageExtensions <em>Package Extension</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getJ_diagramHolder <em>Jdiagram Holder</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getChildPackages <em>Child Packages</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getParentPackage <em>Parent Package</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.PackageImpl#getAnonymousDeletedImportPlaceholders <em>Anonymous Deleted Import Placeholders</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PackageImpl extends NamespaceImpl implements org.eclipse.uml2.Package {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getTemplateParameter() <em>Template Parameter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplateParameter()
	 * @generated
	 * @ordered
	 */
	protected TemplateParameter templateParameter = null;

	/**
	 * The default value of the '{@link #getPackageableElement_visibility() <em>Packageable Element visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageableElement_visibility()
	 * @generated
	 * @ordered
	 */
	protected static final VisibilityKind PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT = VisibilityKind.PUBLIC_LITERAL;

	/**
	 * The cached value of the '{@link #getOwnedMembers() <em>Owned Member</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedMembers()
	 * @generated
	 * @ordered
	 */
	protected EList ownedMember = null;




	/**
	 * The cached value of the '{@link #getPackageMerges() <em>Package Merge</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageMerges()
	 * @generated
	 * @ordered
	 */
	protected EList packageMerge = null;

	/**
	 * The cached value of the '{@link #getAppliedProfiles() <em>Applied Profile</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppliedProfiles()
	 * @generated
	 * @ordered
	 */
	protected EList appliedProfile = null;

	/**
	 * The cached value of the '{@link #getPackageExtensions() <em>Package Extension</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageExtensions()
	 * @generated
	 * @ordered
	 */
	protected EList packageExtension = null;

	/**
	 * The cached value of the '{@link #getJ_diagramHolder() <em>Jdiagram Holder</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJ_diagramHolder()
	 * @generated
	 * @ordered
	 */
	protected J_DiagramHolder j_diagramHolder = null;

	/**
	 * The cached value of the '{@link #getChildPackages() <em>Child Packages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getChildPackages()
	 * @generated
	 * @ordered
	 */
  protected EList childPackages = null;

	/**
	 * The default value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READ_ONLY_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
	protected static final int READ_ONLY_EFLAG = 1 << 8;

	/**
	 * The cached value of the '{@link #getAnonymousDeletedImportPlaceholders() <em>Anonymous Deleted Import Placeholders</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnonymousDeletedImportPlaceholders()
	 * @generated
	 * @ordered
	 */
	protected EList anonymousDeletedImportPlaceholders = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PackageImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (PackageImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateParameter getTemplateParameter()
	{
		if (templateParameter != null && templateParameter.eIsProxy())
		{
			TemplateParameter oldTemplateParameter = templateParameter;
			templateParameter = (TemplateParameter)eResolveProxy((InternalEObject)templateParameter);
			if (templateParameter != oldTemplateParameter)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.PACKAGE__TEMPLATE_PARAMETER, oldTemplateParameter, templateParameter));
			}
		}
		return templateParameter;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public TemplateParameter undeleted_getTemplateParameter()
	{
		TemplateParameter temp = getTemplateParameter();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateParameter basicGetTemplateParameter()
	{
		return templateParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTemplateParameter(TemplateParameter newTemplateParameter, NotificationChain msgs)
	{

		TemplateParameter oldTemplateParameter = templateParameter;
		templateParameter = newTemplateParameter;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.PACKAGE__TEMPLATE_PARAMETER, oldTemplateParameter, newTemplateParameter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		if (getOwningParameter() != null && getOwningParameter() != newTemplateParameter) {
			setOwningParameter(null);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemplateParameter(TemplateParameter newTemplateParameter)
	{

		if (newTemplateParameter != templateParameter)
		{
			NotificationChain msgs = null;
			if (templateParameter != null)
				msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
			if (newTemplateParameter != null)
				msgs = ((InternalEObject)newTemplateParameter).eInverseAdd(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
			msgs = basicSetTemplateParameter(newTemplateParameter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PACKAGE__TEMPLATE_PARAMETER, newTemplateParameter, newTemplateParameter));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateParameter getOwningParameter()
	{
		if (eContainerFeatureID != UML2Package.PACKAGE__OWNING_PARAMETER) return null;
		return (TemplateParameter)eContainer;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public TemplateParameter undeleted_getOwningParameter()
	{
		TemplateParameter temp = getOwningParameter();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwningParameter(TemplateParameter newOwningParameter)
	{

		EObject oldOwningParameter = eContainer;
		if (newOwningParameter != eContainer || (eContainerFeatureID != UML2Package.PACKAGE__OWNING_PARAMETER && newOwningParameter != null))
		{
			if (EcoreUtil.isAncestor(this, newOwningParameter))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwningParameter != null)
				msgs = ((InternalEObject)newOwningParameter).eInverseAdd(this, UML2Package.TEMPLATE_PARAMETER__OWNED_PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newOwningParameter, UML2Package.PACKAGE__OWNING_PARAMETER, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PACKAGE__OWNING_PARAMETER, newOwningParameter, newOwningParameter));

		if (newOwningParameter != null || oldOwningParameter == templateParameter) {
			setTemplateParameter(newOwningParameter);
		}
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public VisibilityKind getPackageableElement_visibility() {
		return visibility;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setPackageableElement_visibility(VisibilityKind newPackageableElement_visibility) {
		VisibilityKind oldVisibility = visibility;
		visibility = newPackageableElement_visibility == null ? VISIBILITY_EDEFAULT : newPackageableElement_visibility;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PACKAGEABLE_ELEMENT__PACKAGEABLE_ELEMENT_VISIBILITY, oldVisibility, visibility));
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getNestedPackages() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			EList result = (EList) cache.get(eResource(), this,
				UML2Package.eINSTANCE.getPackage_NestedPackage());

			if (result == null) {
				EList nestedPackages = PackageOperations
					.getNestedPackages(this);
				cache.put(eResource(), this, UML2Package.eINSTANCE
					.getPackage_NestedPackage(),
					result = new EcoreEList.UnmodifiableEList(this,
						UML2Package.eINSTANCE.getPackage_NestedPackage(),
						nestedPackages.size(), nestedPackages.toArray()));
			}

			return result;
		}

		EList nestedPackages = PackageOperations.getNestedPackages(this);
		return new EcoreEList.UnmodifiableEList(this, UML2Package.eINSTANCE
			.getPackage_NestedPackage(), nestedPackages.size(), nestedPackages
			.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public org.eclipse.uml2.Package getNestedPackage(String name) {
		for (Iterator i = getNestedPackages().iterator(); i.hasNext(); ) {
			org.eclipse.uml2.Package nestedPackage = (org.eclipse.uml2.Package) i.next();
			if (name.equals(nestedPackage.getName())) {
				return nestedPackage;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.uml2.Package getNestingPackage()
	{
		org.eclipse.uml2.Package nestingPackage = basicGetNestingPackage();
		return nestingPackage == null ? null : (org.eclipse.uml2.Package)eResolveProxy((InternalEObject)nestingPackage);
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public org.eclipse.uml2.Package basicGetNestingPackage() {
		return eContainer instanceof org.eclipse.uml2.Package ? (org.eclipse.uml2.Package) eContainer : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getOwnedTypes() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			EList result = (EList) cache.get(eResource(), this,
				UML2Package.eINSTANCE.getPackage_OwnedType());

			if (result == null) {
				EList ownedTypes = PackageOperations.getOwnedTypes(this);
				cache.put(eResource(), this, UML2Package.eINSTANCE
					.getPackage_OwnedType(),
					result = new EcoreEList.UnmodifiableEList(this,
						UML2Package.eINSTANCE.getPackage_OwnedType(),
						ownedTypes.size(), ownedTypes.toArray()));
			}

			return result;
		}

		EList ownedTypes = PackageOperations.getOwnedTypes(this);
		return new EcoreEList.UnmodifiableEList(this, UML2Package.eINSTANCE
			.getPackage_OwnedType(), ownedTypes.size(), ownedTypes.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public Type getOwnedType(String name) {
		for (Iterator i = getOwnedTypes().iterator(); i.hasNext(); ) {
			Type ownedType = (Type) i.next();
			if (name.equals(ownedType.getName())) {
				return ownedType;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedMembers()
	{
		if (ownedMember == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		ownedMember = new com.hopstepjump.emflist.PersistentEList(PackageableElement.class, this, UML2Package.PACKAGE__OWNED_MEMBER);
			 		return ownedMember;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(PackageableElement.class, this, UML2Package.PACKAGE__OWNED_MEMBER);
		}      
		return ownedMember;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedMembers()
	{
		if (ownedMember == null)
		{
			
		
			ownedMember = new com.hopstepjump.emflist.PersistentEList(PackageableElement.class, this, UML2Package.PACKAGE__OWNED_MEMBER);
		}
		return ownedMember;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedMembers()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedMember != null)
		{
			for (Object object : ownedMember)
			{
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public NamedElement getOwnedMember(String name) {
		for (Iterator i = getOwnedMembers().iterator(); i.hasNext(); ) {
			PackageableElement ownedMember = (PackageableElement) i.next();
			if (name.equals(ownedMember.getName())) {
				return ownedMember;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PackageableElement createOwnedMember(EClass eClass) {
		PackageableElement newOwnedMember = (PackageableElement) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.PACKAGE__OWNED_MEMBER, null, newOwnedMember));
		}
		settable_getOwnedMembers().add(newOwnedMember);
		return newOwnedMember;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPackageMerges()
	{
		if (packageMerge == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		packageMerge = new com.hopstepjump.emflist.PersistentEList(PackageMerge.class, this, UML2Package.PACKAGE__PACKAGE_MERGE, UML2Package.PACKAGE_MERGE__MERGING_PACKAGE);
			 		return packageMerge;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(PackageMerge.class, this, UML2Package.PACKAGE__PACKAGE_MERGE, UML2Package.PACKAGE_MERGE__MERGING_PACKAGE);
		}      
		return packageMerge;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getPackageMerges()
	{
		if (packageMerge == null)
		{
			
		
			packageMerge = new com.hopstepjump.emflist.PersistentEList(PackageMerge.class, this, UML2Package.PACKAGE__PACKAGE_MERGE, UML2Package.PACKAGE_MERGE__MERGING_PACKAGE);
		}
		return packageMerge;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getPackageMerges()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (packageMerge != null)
		{
			for (Object object : packageMerge)
			{
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createPackageMerge() instead.
	 */
	public PackageMerge createPackageMerge(EClass eClass) {
		PackageMerge newPackageMerge = (PackageMerge) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.PACKAGE__PACKAGE_MERGE, null, newPackageMerge));
		}
		getPackageMerges().add(newPackageMerge);
		return newPackageMerge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PackageMerge createPackageMerge() {
		PackageMerge newPackageMerge = UML2Factory.eINSTANCE.createPackageMerge();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.PACKAGE__PACKAGE_MERGE, null, newPackageMerge));
		}
		settable_getPackageMerges().add(newPackageMerge);
		return newPackageMerge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAppliedProfiles()
	{
		if (appliedProfile == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		appliedProfile = new com.hopstepjump.emflist.PersistentEList(ProfileApplication.class, this, UML2Package.PACKAGE__APPLIED_PROFILE, new int[] {UML2Package.PACKAGE__PACKAGE_IMPORT});
			 		return appliedProfile;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(ProfileApplication.class, this, UML2Package.PACKAGE__APPLIED_PROFILE, new int[] {UML2Package.PACKAGE__PACKAGE_IMPORT});
		}      
		return appliedProfile;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getAppliedProfiles()
	{
		if (appliedProfile == null)
		{
			
		
			appliedProfile = new com.hopstepjump.emflist.PersistentEList(ProfileApplication.class, this, UML2Package.PACKAGE__APPLIED_PROFILE, new int[] {UML2Package.PACKAGE__PACKAGE_IMPORT});
		}
		return appliedProfile;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getAppliedProfiles()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (appliedProfile != null)
		{
			for (Object object : appliedProfile)
			{
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPackageExtensions()
	{
		if (packageExtension == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		packageExtension = new com.hopstepjump.emflist.PersistentEList(PackageMerge.class, this, UML2Package.PACKAGE__PACKAGE_EXTENSION);
			 		return packageExtension;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(PackageMerge.class, this, UML2Package.PACKAGE__PACKAGE_EXTENSION);
		}      
		return packageExtension;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getPackageExtensions()
	{
		if (packageExtension == null)
		{
			
		
			packageExtension = new com.hopstepjump.emflist.PersistentEList(PackageMerge.class, this, UML2Package.PACKAGE__PACKAGE_EXTENSION);
		}
		return packageExtension;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getPackageExtensions()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (packageExtension != null)
		{
			for (Object object : packageExtension)
			{
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createPackageExtension() instead.
	 */
	public PackageMerge createPackageExtension(EClass eClass) {
		PackageMerge newPackageExtension = (PackageMerge) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.PACKAGE__PACKAGE_EXTENSION, null, newPackageExtension));
		}
		getPackageExtensions().add(newPackageExtension);
		return newPackageExtension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PackageMerge createPackageExtension() {
		PackageMerge newPackageExtension = UML2Factory.eINSTANCE.createPackageMerge();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.PACKAGE__PACKAGE_EXTENSION, null, newPackageExtension));
		}
		settable_getPackageExtensions().add(newPackageExtension);
		return newPackageExtension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public J_DiagramHolder getJ_diagramHolder()
	{
		return j_diagramHolder;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public J_DiagramHolder undeleted_getJ_diagramHolder()
	{
		J_DiagramHolder temp = getJ_diagramHolder();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJ_diagramHolder(J_DiagramHolder newJ_diagramHolder, NotificationChain msgs)
	{

		J_DiagramHolder oldJ_diagramHolder = j_diagramHolder;
		j_diagramHolder = newJ_diagramHolder;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.PACKAGE__JDIAGRAM_HOLDER, oldJ_diagramHolder, newJ_diagramHolder);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJ_diagramHolder(J_DiagramHolder newJ_diagramHolder)
	{

		if (newJ_diagramHolder != j_diagramHolder)
		{
			NotificationChain msgs = null;
			if (j_diagramHolder != null)
				msgs = ((InternalEObject)j_diagramHolder).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.PACKAGE__JDIAGRAM_HOLDER, null, msgs);
			if (newJ_diagramHolder != null)
				msgs = ((InternalEObject)newJ_diagramHolder).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UML2Package.PACKAGE__JDIAGRAM_HOLDER, null, msgs);
			msgs = basicSetJ_diagramHolder(newJ_diagramHolder, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PACKAGE__JDIAGRAM_HOLDER, newJ_diagramHolder, newJ_diagramHolder));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public J_DiagramHolder createJ_diagramHolder(EClass eClass) {
		J_DiagramHolder newJ_diagramHolder = (J_DiagramHolder) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.PACKAGE__JDIAGRAM_HOLDER, null, newJ_diagramHolder));
		}
		setJ_diagramHolder(newJ_diagramHolder);
		return newJ_diagramHolder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public J_DiagramHolder createJ_diagramHolder() {
		J_DiagramHolder newJ_diagramHolder = UML2Factory.eINSTANCE.createJ_DiagramHolder();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.PACKAGE__JDIAGRAM_HOLDER, null, newJ_diagramHolder));
		}
		setJ_diagramHolder(newJ_diagramHolder);
		return newJ_diagramHolder;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList getChildPackages()
	{
		if (childPackages == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		childPackages = new com.hopstepjump.emflist.PersistentEList(org.eclipse.uml2.Package.class, this, UML2Package.PACKAGE__CHILD_PACKAGES, UML2Package.PACKAGE__PARENT_PACKAGE);
			 		return childPackages;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(org.eclipse.uml2.Package.class, this, UML2Package.PACKAGE__CHILD_PACKAGES, UML2Package.PACKAGE__PARENT_PACKAGE);
		}      
		return childPackages;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getChildPackages()
	{
		if (childPackages == null)
		{
			
		
			childPackages = new com.hopstepjump.emflist.PersistentEList(org.eclipse.uml2.Package.class, this, UML2Package.PACKAGE__CHILD_PACKAGES, UML2Package.PACKAGE__PARENT_PACKAGE);
		}
		return childPackages;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getChildPackages()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (childPackages != null)
		{
			for (Object object : childPackages)
			{
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public org.eclipse.uml2.Package createChildPackages(EClass eClass) {
		org.eclipse.uml2.Package newChildPackages = (org.eclipse.uml2.Package) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.PACKAGE__CHILD_PACKAGES, null, newChildPackages));
		}
		settable_getChildPackages().add(newChildPackages);
		return newChildPackages;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public org.eclipse.uml2.Package createChildPackages() {
		org.eclipse.uml2.Package newChildPackages = UML2Factory.eINSTANCE.createPackage();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.PACKAGE__CHILD_PACKAGES, null, newChildPackages));
		}
		settable_getChildPackages().add(newChildPackages);
		return newChildPackages;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public org.eclipse.uml2.Package getChildPackages(String name) {
		for (Iterator i = getChildPackages().iterator(); i.hasNext(); ) {
			org.eclipse.uml2.Package childPackages = (org.eclipse.uml2.Package) i.next();
			if (name.equals(childPackages.getName())) {
				return childPackages;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public org.eclipse.uml2.Package getParentPackage()
	{
		if (eContainerFeatureID != UML2Package.PACKAGE__PARENT_PACKAGE) return null;
		return (org.eclipse.uml2.Package)eContainer;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public org.eclipse.uml2.Package undeleted_getParentPackage()
	{
		org.eclipse.uml2.Package temp = getParentPackage();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setParentPackage(org.eclipse.uml2.Package newParentPackage)
	{

		if (newParentPackage != eContainer || (eContainerFeatureID != UML2Package.PACKAGE__PARENT_PACKAGE && newParentPackage != null))
		{
			if (EcoreUtil.isAncestor(this, newParentPackage))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentPackage != null)
				msgs = ((InternalEObject)newParentPackage).eInverseAdd(this, UML2Package.PACKAGE__CHILD_PACKAGES, org.eclipse.uml2.Package.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newParentPackage, UML2Package.PACKAGE__PARENT_PACKAGE, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PACKAGE__PARENT_PACKAGE, newParentPackage, newParentPackage));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReadOnly()
	{
		return (eFlags & READ_ONLY_EFLAG) != 0;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadOnly(boolean newReadOnly)
	{

		boolean oldReadOnly = (eFlags & READ_ONLY_EFLAG) != 0;
		if (newReadOnly) eFlags |= READ_ONLY_EFLAG; else eFlags &= ~READ_ONLY_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.PACKAGE__READ_ONLY, oldReadOnly, newReadOnly));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAnonymousDeletedImportPlaceholders()
	{
		if (anonymousDeletedImportPlaceholders == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		anonymousDeletedImportPlaceholders = new com.hopstepjump.emflist.PersistentEList(Element.class, this, UML2Package.PACKAGE__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS);
			 		return anonymousDeletedImportPlaceholders;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(Element.class, this, UML2Package.PACKAGE__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS);
		}      
		return anonymousDeletedImportPlaceholders;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getAnonymousDeletedImportPlaceholders()
	{
		if (anonymousDeletedImportPlaceholders == null)
		{
			
		
			anonymousDeletedImportPlaceholders = new com.hopstepjump.emflist.PersistentEList(Element.class, this, UML2Package.PACKAGE__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS);
		}
		return anonymousDeletedImportPlaceholders;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getAnonymousDeletedImportPlaceholders()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (anonymousDeletedImportPlaceholders != null)
		{
			for (Object object : anonymousDeletedImportPlaceholders)
			{
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element createAnonymousDeletedImportPlaceholders(EClass eClass) {
		Element newAnonymousDeletedImportPlaceholders = (Element) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.PACKAGE__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS, null, newAnonymousDeletedImportPlaceholders));
		}
		settable_getAnonymousDeletedImportPlaceholders().add(newAnonymousDeletedImportPlaceholders);
		return newAnonymousDeletedImportPlaceholders;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateElementsPublicOrPrivate(DiagnosticChain diagnostics, Map context)
	{
		return PackageOperations.validateElementsPublicOrPrivate(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element basicGetOwner()
	{
		TemplateParameter owningParameter = getOwningParameter();			
		if (owningParameter != null) {
			return owningParameter;
		}
		return super.basicGetOwner();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VisibilityKind getVisibility()
	{
		return getPackageableElement_visibility();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisibility(VisibilityKind newVisibility)
	{
		setPackageableElement_visibility(newVisibility);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean mustBeOwned()
	{
		return PackageOperations.mustBeOwned(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Set visibleMembers()
	{
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			Set result = (Set) cache.get(this, UML2Package.eINSTANCE.getPackage().getEOperations().get(1));
			if (result == null) {
				cache.put(this, UML2Package.eINSTANCE.getPackage().getEOperations().get(1), result = PackageOperations.visibleMembers(this));
			}
			return result;
		}
		return PackageOperations.visibleMembers(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean makesVisible(NamedElement el)
	{
		return PackageOperations.makesVisible(this, el);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Namespace basicGetNamespace()
	{
		org.eclipse.uml2.Package nestingPackage = basicGetNestingPackage();			
		if (nestingPackage != null) {
			return nestingPackage;
		}
		return super.basicGetNamespace();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected EList getOwnedElementsHelper(EList ownedElement) {
		super.getOwnedElementsHelper(ownedElement);
		if (eIsSet(UML2Package.eINSTANCE.getPackage_PackageMerge())) {
			ownedElement.addAll(getPackageMerges());
		}
		if (eIsSet(UML2Package.eINSTANCE.getPackage_PackageExtension())) {
			ownedElement.addAll(getPackageExtensions());
		}
		EList ownedMember = super.getOwnedMembers();
		if (!ownedMember.isEmpty()) {
			for (Iterator i = ((InternalEList) ownedMember).basicIterator(); i.hasNext(); ) {
				ownedElement.add(i.next());
			}
		}
		return ownedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPackageImports()
	{
		if (packageImport == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		packageImport = new com.hopstepjump.emflist.PersistentEList(PackageImport.class, this, UML2Package.PACKAGE__PACKAGE_IMPORT, new int[] {UML2Package.PACKAGE__APPLIED_PROFILE}, UML2Package.PACKAGE_IMPORT__IMPORTING_NAMESPACE);
			 		return packageImport;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(PackageImport.class, this, UML2Package.PACKAGE__PACKAGE_IMPORT, new int[] {UML2Package.PACKAGE__APPLIED_PROFILE}, UML2Package.PACKAGE_IMPORT__IMPORTING_NAMESPACE);
		}      
		return packageImport;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getPackageImports()
	{
		if (packageImport == null)
		{
			
		
			packageImport = new com.hopstepjump.emflist.PersistentEList(PackageImport.class, this, UML2Package.PACKAGE__PACKAGE_IMPORT, new int[] {UML2Package.PACKAGE__APPLIED_PROFILE}, UML2Package.PACKAGE_IMPORT__IMPORTING_NAMESPACE);
		}
		return packageImport;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getPackageImports()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (packageImport != null)
		{
			for (Object object : packageImport)
			{
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs)
	{
		if (featureID >= 0)
		{
			switch (eDerivedStructuralFeatureID(featureID, baseClass))
			{
				case UML2Package.PACKAGE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.PACKAGE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.PACKAGE__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.PACKAGE__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.PACKAGE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.PACKAGE__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicAdd(otherEnd, msgs);
				case UML2Package.PACKAGE__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicAdd(otherEnd, msgs);
				case UML2Package.PACKAGE__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicAdd(otherEnd, msgs);
				case UML2Package.PACKAGE__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.PACKAGE__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.PACKAGE__OWNING_PARAMETER, msgs);
				case UML2Package.PACKAGE__PACKAGE_MERGE:
					return ((InternalEList)getPackageMerges()).basicAdd(otherEnd, msgs);
				case UML2Package.PACKAGE__CHILD_PACKAGES:
					return ((InternalEList)getChildPackages()).basicAdd(otherEnd, msgs);
				case UML2Package.PACKAGE__PARENT_PACKAGE:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.PACKAGE__PARENT_PACKAGE, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs)
	{
		if (featureID >= 0)
		{
			switch (eDerivedStructuralFeatureID(featureID, baseClass))
			{
				case UML2Package.PACKAGE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.PACKAGE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.PACKAGE__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.PACKAGE__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.PACKAGE__OWNING_PARAMETER, msgs);
				case UML2Package.PACKAGE__OWNED_MEMBER:
					return ((InternalEList)getOwnedMembers()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__PACKAGE_MERGE:
					return ((InternalEList)getPackageMerges()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__PACKAGE_EXTENSION:
					return ((InternalEList)getPackageExtensions()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__JDIAGRAM_HOLDER:
					return basicSetJ_diagramHolder(null, msgs);
				case UML2Package.PACKAGE__CHILD_PACKAGES:
					return ((InternalEList)getChildPackages()).basicRemove(otherEnd, msgs);
				case UML2Package.PACKAGE__PARENT_PACKAGE:
					return eBasicSetContainer(null, UML2Package.PACKAGE__PARENT_PACKAGE, msgs);
				case UML2Package.PACKAGE__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS:
					return ((InternalEList)getAnonymousDeletedImportPlaceholders()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs)
	{
		if (eContainerFeatureID >= 0)
		{
			switch (eContainerFeatureID)
			{
				case UML2Package.PACKAGE__OWNING_PARAMETER:
					return eContainer.eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__OWNED_PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
				case UML2Package.PACKAGE__PARENT_PACKAGE:
					return eContainer.eInverseRemove(this, UML2Package.PACKAGE__CHILD_PACKAGES, org.eclipse.uml2.Package.class, msgs);
				default:
					return eDynamicBasicRemoveFromContainer(msgs);
			}
		}
		return eContainer.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - eContainerFeatureID, null, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.PACKAGE__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.PACKAGE__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.PACKAGE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.PACKAGE__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.PACKAGE__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.PACKAGE__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.PACKAGE__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.PACKAGE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.PACKAGE__UUID:
				return getUuid();
			case UML2Package.PACKAGE__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.PACKAGE__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.PACKAGE__NAME:
				return getName();
			case UML2Package.PACKAGE__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.PACKAGE__VISIBILITY:
				return getVisibility();
			case UML2Package.PACKAGE__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.PACKAGE__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.PACKAGE__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.PACKAGE__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.PACKAGE__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.PACKAGE__MEMBER:
				return getMembers();
			case UML2Package.PACKAGE__OWNED_RULE:
				return getOwnedRules();
			case UML2Package.PACKAGE__IMPORTED_MEMBER:
				return getImportedMembers();
			case UML2Package.PACKAGE__ELEMENT_IMPORT:
				return getElementImports();
			case UML2Package.PACKAGE__PACKAGE_IMPORT:
				return getPackageImports();
			case UML2Package.PACKAGE__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.PACKAGE__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.PACKAGE__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.PACKAGE__NESTED_PACKAGE:
				return getNestedPackages();
			case UML2Package.PACKAGE__NESTING_PACKAGE:
				if (resolve) return getNestingPackage();
				return basicGetNestingPackage();
			case UML2Package.PACKAGE__OWNED_TYPE:
				return getOwnedTypes();
			case UML2Package.PACKAGE__OWNED_MEMBER:
				return getOwnedMembers();
			case UML2Package.PACKAGE__PACKAGE_MERGE:
				return getPackageMerges();
			case UML2Package.PACKAGE__APPLIED_PROFILE:
				return getAppliedProfiles();
			case UML2Package.PACKAGE__PACKAGE_EXTENSION:
				return getPackageExtensions();
			case UML2Package.PACKAGE__JDIAGRAM_HOLDER:
				return getJ_diagramHolder();
			case UML2Package.PACKAGE__CHILD_PACKAGES:
				return getChildPackages();
			case UML2Package.PACKAGE__PARENT_PACKAGE:
				return getParentPackage();
			case UML2Package.PACKAGE__READ_ONLY:
				return isReadOnly() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.PACKAGE__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS:
				return getAnonymousDeletedImportPlaceholders();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.PACKAGE__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.PACKAGE__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.PACKAGE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.PACKAGE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.PACKAGE__NAME:
				setName((String)newValue);
				return;
			case UML2Package.PACKAGE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.PACKAGE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.PACKAGE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__OWNED_RULE:
				getOwnedRules().clear();
				getOwnedRules().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__ELEMENT_IMPORT:
				getElementImports().clear();
				getElementImports().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__PACKAGE_IMPORT:
				getPackageImports().clear();
				getPackageImports().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.PACKAGE__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.PACKAGE__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.PACKAGE__OWNED_MEMBER:
				getOwnedMembers().clear();
				getOwnedMembers().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__PACKAGE_MERGE:
				getPackageMerges().clear();
				getPackageMerges().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__APPLIED_PROFILE:
				getAppliedProfiles().clear();
				getAppliedProfiles().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__PACKAGE_EXTENSION:
				getPackageExtensions().clear();
				getPackageExtensions().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__JDIAGRAM_HOLDER:
				setJ_diagramHolder((J_DiagramHolder)newValue);
				return;
			case UML2Package.PACKAGE__CHILD_PACKAGES:
				getChildPackages().clear();
				getChildPackages().addAll((Collection)newValue);
				return;
			case UML2Package.PACKAGE__PARENT_PACKAGE:
				setParentPackage((org.eclipse.uml2.Package)newValue);
				return;
			case UML2Package.PACKAGE__READ_ONLY:
				setReadOnly(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.PACKAGE__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS:
				getAnonymousDeletedImportPlaceholders().clear();
				getAnonymousDeletedImportPlaceholders().addAll((Collection)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.PACKAGE__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.PACKAGE__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.PACKAGE__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.PACKAGE__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.PACKAGE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.PACKAGE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.PACKAGE__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.PACKAGE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.PACKAGE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.PACKAGE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.PACKAGE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.PACKAGE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.PACKAGE__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.PACKAGE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.PACKAGE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.PACKAGE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.PACKAGE__OWNED_RULE:
				getOwnedRules().clear();
				return;
			case UML2Package.PACKAGE__ELEMENT_IMPORT:
				getElementImports().clear();
				return;
			case UML2Package.PACKAGE__PACKAGE_IMPORT:
				getPackageImports().clear();
				return;
			case UML2Package.PACKAGE__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.PACKAGE__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.PACKAGE__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.PACKAGE__OWNED_MEMBER:
				getOwnedMembers().clear();
				return;
			case UML2Package.PACKAGE__PACKAGE_MERGE:
				getPackageMerges().clear();
				return;
			case UML2Package.PACKAGE__APPLIED_PROFILE:
				getAppliedProfiles().clear();
				return;
			case UML2Package.PACKAGE__PACKAGE_EXTENSION:
				getPackageExtensions().clear();
				return;
			case UML2Package.PACKAGE__JDIAGRAM_HOLDER:
				setJ_diagramHolder((J_DiagramHolder)null);
				return;
			case UML2Package.PACKAGE__CHILD_PACKAGES:
				getChildPackages().clear();
				return;
			case UML2Package.PACKAGE__PARENT_PACKAGE:
				setParentPackage((org.eclipse.uml2.Package)null);
				return;
			case UML2Package.PACKAGE__READ_ONLY:
				setReadOnly(READ_ONLY_EDEFAULT);
				return;
			case UML2Package.PACKAGE__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS:
				getAnonymousDeletedImportPlaceholders().clear();
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSetGen(EStructuralFeature eFeature)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.PACKAGE__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.PACKAGE__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.PACKAGE__OWNER:
				return basicGetOwner() != null;
			case UML2Package.PACKAGE__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.PACKAGE__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.PACKAGE__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.PACKAGE__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.PACKAGE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.PACKAGE__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.PACKAGE__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.PACKAGE__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.PACKAGE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.PACKAGE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.PACKAGE__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.PACKAGE__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.PACKAGE__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.PACKAGE__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.PACKAGE__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.PACKAGE__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.PACKAGE__MEMBER:
				return !getMembers().isEmpty();
			case UML2Package.PACKAGE__OWNED_RULE:
				return ownedRule != null && !ownedRule.isEmpty();
			case UML2Package.PACKAGE__IMPORTED_MEMBER:
				return !getImportedMembers().isEmpty();
			case UML2Package.PACKAGE__ELEMENT_IMPORT:
				return elementImport != null && !elementImport.isEmpty();
			case UML2Package.PACKAGE__PACKAGE_IMPORT:
				return packageImport != null && !packageImport.isEmpty();
			case UML2Package.PACKAGE__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.PACKAGE__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.PACKAGE__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.PACKAGE__NESTED_PACKAGE:
				return !getNestedPackages().isEmpty();
			case UML2Package.PACKAGE__NESTING_PACKAGE:
				return basicGetNestingPackage() != null;
			case UML2Package.PACKAGE__OWNED_TYPE:
				return !getOwnedTypes().isEmpty();
			case UML2Package.PACKAGE__OWNED_MEMBER:
				return ownedMember != null && !ownedMember.isEmpty();
			case UML2Package.PACKAGE__PACKAGE_MERGE:
				return packageMerge != null && !packageMerge.isEmpty();
			case UML2Package.PACKAGE__APPLIED_PROFILE:
				return appliedProfile != null && !appliedProfile.isEmpty();
			case UML2Package.PACKAGE__PACKAGE_EXTENSION:
				return packageExtension != null && !packageExtension.isEmpty();
			case UML2Package.PACKAGE__JDIAGRAM_HOLDER:
				return j_diagramHolder != null;
			case UML2Package.PACKAGE__CHILD_PACKAGES:
				return childPackages != null && !childPackages.isEmpty();
			case UML2Package.PACKAGE__PARENT_PACKAGE:
				return getParentPackage() != null;
			case UML2Package.PACKAGE__READ_ONLY:
				return ((eFlags & READ_ONLY_EFLAG) != 0) != READ_ONLY_EDEFAULT;
			case UML2Package.PACKAGE__ANONYMOUS_DELETED_IMPORT_PLACEHOLDERS:
				return anonymousDeletedImportPlaceholders != null && !anonymousDeletedImportPlaceholders.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.PACKAGE__VISIBILITY:
				return false;
			case UML2Package.PACKAGE__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
		}
		return eIsSetGen(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class baseClass)
	{
		if (baseClass == ParameterableElement.class)
		{
			switch (derivedFeatureID)
			{
				case UML2Package.PACKAGE__TEMPLATE_PARAMETER: return UML2Package.PARAMETERABLE_ELEMENT__TEMPLATE_PARAMETER;
				case UML2Package.PACKAGE__OWNING_PARAMETER: return UML2Package.PARAMETERABLE_ELEMENT__OWNING_PARAMETER;
				default: return -1;
			}
		}
		if (baseClass == PackageableElement.class)
		{
			switch (derivedFeatureID)
			{
				case UML2Package.PACKAGE__PACKAGEABLE_ELEMENT_VISIBILITY: return UML2Package.PACKAGEABLE_ELEMENT__PACKAGEABLE_ELEMENT_VISIBILITY;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class baseClass)
	{
		if (baseClass == ParameterableElement.class)
		{
			switch (baseFeatureID)
			{
				case UML2Package.PARAMETERABLE_ELEMENT__TEMPLATE_PARAMETER: return UML2Package.PACKAGE__TEMPLATE_PARAMETER;
				case UML2Package.PARAMETERABLE_ELEMENT__OWNING_PARAMETER: return UML2Package.PACKAGE__OWNING_PARAMETER;
				default: return -1;
			}
		}
		if (baseClass == PackageableElement.class)
		{
			switch (baseFeatureID)
			{
				case UML2Package.PACKAGEABLE_ELEMENT__PACKAGEABLE_ELEMENT_VISIBILITY: return UML2Package.PACKAGE__PACKAGEABLE_ELEMENT_VISIBILITY;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (readOnly: "); //$NON-NLS-1$
		result.append((eFlags & READ_ONLY_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}


	// <!-- begin-custom-operations -->
  /*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.impl.NamespaceImpl#getMembersHelper(org.eclipse.emf.common.util.EList)
	 */
	protected EList getMembersHelper(EList member) {
		super.getMembersHelper(member);
		EList ownedMember = super.getOwnedMembers();
		if (!ownedMember.isEmpty()) {
			for (Iterator i = ((InternalEList) ownedMember).basicIterator(); i.hasNext();) {
				member.add(i.next());
			}
		}
		return member;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Namespace#getNamesOfMember(org.eclipse.uml2.NamedElement)
	 */
	public Set getNamesOfMember(NamedElement element) {
		return PackageOperations.getNamesOfMember(this, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Namespace#importMembers(java.util.Set)
	 */
	public Set importMembers(Set imps) {
		return PackageOperations.importMembers(this, imps);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Package#apply(org.eclipse.uml2.Profile)
	 */
	public void apply(Profile profile) {
		ProfileOperations.apply(profile, this);
	}

	private static Method GET_ALL_APPLIED_PROFILES = null;

	static {
		try {
			GET_ALL_APPLIED_PROFILES = PackageImpl.class.getMethod(
				"getAllAppliedProfiles", null); //$NON-NLS-1$
		} catch (Exception e) {
			// ignore
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Package#getAllAppliedProfiles()
	 */
	public Set getAllAppliedProfiles() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			Set result = (Set) cache.get(eResource(), this,
				GET_ALL_APPLIED_PROFILES);

			if (result == null) {
				cache.put(eResource(), this, GET_ALL_APPLIED_PROFILES,
					result = Collections.unmodifiableSet(ProfileOperations
						.getAllAppliedProfiles(this)));
			}

			return result;
		}

		return Collections.unmodifiableSet(ProfileOperations
			.getAllAppliedProfiles(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Package#isApplied(org.eclipse.uml2.Profile)
	 */
	public boolean isApplied(Profile profile) {
		return ProfileOperations.isApplied(profile, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Package#unapply(org.eclipse.uml2.Profile)
	 */
	public void unapply(Profile profile) {
		ProfileOperations.unapply(profile, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Package#getAppliedVersion(org.eclipse.uml2.Profile)
	 */
	public String getAppliedVersion(Profile profile) {
		return ProfileOperations.getAppliedVersion(profile, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Package#createNestedPackage(java.lang.String)
	 */
	public org.eclipse.uml2.Package createNestedPackage(String name) {
		return PackageOperations.createNestedPackage(this, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Package#createOwnedClass(java.lang.String, boolean)
	 */
	public org.eclipse.uml2.Class createOwnedClass(String name,
			boolean isAbstract) {
		return PackageOperations.createOwnedClass(this, name, isAbstract);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Package#createOwnedEnumeraton(java.lang.String)
	 */
	public Enumeration createOwnedEnumeraton(String name) {
		return PackageOperations.createOwnedEnumeration(this, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Package#createOwnedPrimitiveType(java.lang.String)
	 */
	public PrimitiveType createOwnedPrimitiveType(String name) {
		return PackageOperations.createOwnedPrimitiveType(this, name);
	}

	// <!-- end-custom-operations -->

} //PackageImpl
