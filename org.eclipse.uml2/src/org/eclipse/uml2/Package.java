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
 * $Id: Package.java,v 1.2 2009-04-22 10:01:11 andrew Exp $
 */
package org.eclipse.uml2;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A package is a namespace for its members, and may contain other packages. Only packageable elements can be owned members of a package. By virtue of being a namespace, a package can import either individual members of other packages, or all the members of other packages. In addition a package can be merged with other packages. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.Package#getNestedPackages <em>Nested Package</em>}</li>
 *   <li>{@link org.eclipse.uml2.Package#getNestingPackage <em>Nesting Package</em>}</li>
 *   <li>{@link org.eclipse.uml2.Package#getOwnedTypes <em>Owned Type</em>}</li>
 *   <li>{@link org.eclipse.uml2.Package#getOwnedMembers <em>Owned Member</em>}</li>
 *   <li>{@link org.eclipse.uml2.Package#getPackageMerges <em>Package Merge</em>}</li>
 *   <li>{@link org.eclipse.uml2.Package#getAppliedProfiles <em>Applied Profile</em>}</li>
 *   <li>{@link org.eclipse.uml2.Package#getPackageExtensions <em>Package Extension</em>}</li>
 *   <li>{@link org.eclipse.uml2.Package#getJ_diagramHolder <em>Jdiagram Holder</em>}</li>
 *   <li>{@link org.eclipse.uml2.Package#getChildPackages <em>Child Packages</em>}</li>
 *   <li>{@link org.eclipse.uml2.Package#getParentPackage <em>Parent Package</em>}</li>
 *   <li>{@link org.eclipse.uml2.Package#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link org.eclipse.uml2.Package#getAnonymousDeletedImportPlaceholders <em>Anonymous Deleted Import Placeholders</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getPackage()
 * @model
 * @generated
 */
public interface Package extends Namespace, PackageableElement{

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Nested Package</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Package}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Package#getNestingPackage <em>Nesting Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nested Package</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * References the owned members that are Packages. Subsets Package::ownedMember.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Nested Package</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getPackage_NestedPackage()
	 * @see org.eclipse.uml2.Package#getNestingPackage
	 * @model type="org.eclipse.uml2.Package" opposite="nestingPackage" resolveProxies="false" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList getNestedPackages();


	/**
	 * Retrieves the {@link org.eclipse.uml2.Package} with the specified '<em><b>Name</b></em>' from the '<em><b>Nested Package</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Package} to retrieve.
	 * @return The {@link org.eclipse.uml2.Package} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getNestedPackages()
	 * @generated
	 */
    Package getNestedPackage(String name);




	/**
	 * Returns the value of the '<em><b>Nesting Package</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Package#getNestedPackages <em>Nested Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nesting Package</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nesting Package</em>' reference.
	 * @see org.eclipse.uml2.UML2Package#getPackage_NestingPackage()
	 * @see org.eclipse.uml2.Package#getNestedPackages
	 * @model opposite="nestedPackage" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Package getNestingPackage();





	/**
	 * Returns the value of the '<em><b>Owned Type</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Type}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Type#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Type</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * References the owned members that are Types. Subsets Package::ownedMember.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owned Type</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getPackage_OwnedType()
	 * @see org.eclipse.uml2.Type#getPackage
	 * @model type="org.eclipse.uml2.Type" opposite="package" resolveProxies="false" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList getOwnedTypes();


	/**
	 * Retrieves the {@link org.eclipse.uml2.Type} with the specified '<em><b>Name</b></em>' from the '<em><b>Owned Type</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Type} to retrieve.
	 * @return The {@link org.eclipse.uml2.Type} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOwnedTypes()
	 * @generated
	 */
    Type getOwnedType(String name);




	/**
	 * Returns the value of the '<em><b>Owned Member</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.PackageableElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Member</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies the members that are owned by this Package. Redefines Namespace::ownedMember.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Owned Member</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getPackage_OwnedMember()
	 * @model type="org.eclipse.uml2.PackageableElement" containment="true" ordered="false"
	 * @generated
	 */
	EList getOwnedMembers();


	/**
	 * Retrieves the {@link org.eclipse.uml2.PackageableElement} with the specified '<em><b>Name</b></em>' from the '<em><b>Owned Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.PackageableElement} to retrieve.
	 * @return The {@link org.eclipse.uml2.PackageableElement} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getOwnedMembers()
	 * @generated
	 */
    NamedElement getOwnedMember(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getOwnedMembers();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getOwnedMembers();


    /**
     * Creates a {@link org.eclipse.uml2.PackageableElement} and appends it to the '<em><b>Owned Member</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.PackageableElement} to create.
	 * @return The new {@link org.eclipse.uml2.PackageableElement}.
	 * @see #getOwnedMembers()
	 * @generated NOT
     */
	PackageableElement createOwnedMember(EClass eClass);

	/**
	 * Returns the value of the '<em><b>Package Merge</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.PackageMerge}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.PackageMerge#getMergingPackage <em>Merging Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package Merge</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Merge</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getPackage_PackageMerge()
	 * @see org.eclipse.uml2.PackageMerge#getMergingPackage
	 * @model type="org.eclipse.uml2.PackageMerge" opposite="mergingPackage" containment="true" ordered="false"
	 * @generated
	 */
	EList getPackageMerges();


    /**
     * Creates a {@link org.eclipse.uml2.PackageMerge} and appends it to the '<em><b>Package Merge</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.PackageMerge} to create.
	 * @return The new {@link org.eclipse.uml2.PackageMerge}.
	 * @see #getPackageMerges()
	 * @generated NOT
	 * @deprecated Use #createPackageMerge() instead.
     */
    PackageMerge createPackageMerge(EClass eClass);

	/**
	 * Creates a {@link org.eclipse.uml2.PackageMerge} and appends it to the '<em><b>Package Merge</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.PackageMerge}.
	 * @see #getPackageMerges()
	 * @generated
	 */
    PackageMerge createPackageMerge();


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getPackageMerges();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getPackageMerges();


	/**
	 * Returns the value of the '<em><b>Applied Profile</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.ProfileApplication}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Applied Profile</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Applied Profile</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getPackage_AppliedProfile()
	 * @model type="org.eclipse.uml2.ProfileApplication" resolveProxies="false" ordered="false"
	 * @generated
	 */
	EList getAppliedProfiles();



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getAppliedProfiles();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getAppliedProfiles();


	/**
	 * Returns the value of the '<em><b>Package Extension</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.PackageMerge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package Extension</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Extension</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getPackage_PackageExtension()
	 * @model type="org.eclipse.uml2.PackageMerge" containment="true" ordered="false"
	 * @generated
	 */
	EList getPackageExtensions();


    /**
     * Creates a {@link org.eclipse.uml2.PackageMerge} and appends it to the '<em><b>Package Extension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.PackageMerge} to create.
	 * @return The new {@link org.eclipse.uml2.PackageMerge}.
	 * @see #getPackageExtensions()
	 * @generated NOT
	 * @deprecated Use #createPackageExtension() instead.
     */
    PackageMerge createPackageExtension(EClass eClass);

	/**
	 * Creates a {@link org.eclipse.uml2.PackageMerge} and appends it to the '<em><b>Package Extension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.PackageMerge}.
	 * @see #getPackageExtensions()
	 * @generated
	 */
    PackageMerge createPackageExtension();


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getPackageExtensions();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getPackageExtensions();


	/**
	 * Returns the value of the '<em><b>Jdiagram Holder</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jdiagram Holder</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jdiagram Holder</em>' containment reference.
	 * @see #setJ_diagramHolder(J_DiagramHolder)
	 * @see org.eclipse.uml2.UML2Package#getPackage_J_diagramHolder()
	 * @model containment="true"
	 * @generated
	 */
	J_DiagramHolder getJ_diagramHolder();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Package#getJ_diagramHolder <em>Jdiagram Holder</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jdiagram Holder</em>' containment reference.
	 * @see #getJ_diagramHolder()
	 * @generated
	 */
	void setJ_diagramHolder(J_DiagramHolder value);


	/**
	 * Creates a {@link org.eclipse.uml2.J_DiagramHolder} and sets the '<em><b>Jdiagram Holder</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.J_DiagramHolder}.
	 * @see #getJ_diagramHolder()
	 * @generated
	 */
	J_DiagramHolder createJ_diagramHolder();



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	J_DiagramHolder undeleted_getJ_diagramHolder();

	/**
	 * Returns the value of the '<em><b>Child Packages</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Package}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Package#getParentPackage <em>Parent Package</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Child Packages</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Packages</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getPackage_ChildPackages()
	 * @see org.eclipse.uml2.Package#getParentPackage
	 * @model type="org.eclipse.uml2.Package" opposite="parentPackage" containment="true"
	 * @generated
	 */
  EList getChildPackages();


	/**
	 * Creates a {@link org.eclipse.uml2.Package} and appends it to the '<em><b>Child Packages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.Package} to create.
	 * @return The new {@link org.eclipse.uml2.Package}.
	 * @see #getChildPackages()
	 * @generated
	 */
  Package createChildPackages(EClass eClass);

	/**
	 * Creates a {@link org.eclipse.uml2.Package} and appends it to the '<em><b>Child Packages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.Package}.
	 * @see #getChildPackages()
	 * @generated
	 */
  Package createChildPackages();

	/**
	 * Retrieves the {@link org.eclipse.uml2.Package} with the specified '<em><b>Name</b></em>' from the '<em><b>Child Packages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param name The '<em><b>Name</b></em>' of the {@link org.eclipse.uml2.Package} to retrieve.
	 * @return The {@link org.eclipse.uml2.Package} with the specified '<em><b>Name</b></em>', or <code>null</code>.
	 * @see #getChildPackages()
	 * @generated
	 */
  Package getChildPackages(String name);


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getChildPackages();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getChildPackages();


	/**
	 * Returns the value of the '<em><b>Parent Package</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Package#getChildPackages <em>Child Packages</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parent Package</em>' container reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Package</em>' container reference.
	 * @see #setParentPackage(Package)
	 * @see org.eclipse.uml2.UML2Package#getPackage_ParentPackage()
	 * @see org.eclipse.uml2.Package#getChildPackages
	 * @model opposite="childPackages"
	 * @generated
	 */
  Package getParentPackage();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Package#getParentPackage <em>Parent Package</em>}' container reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Package</em>' container reference.
	 * @see #getParentPackage()
	 * @generated
	 */
  void setParentPackage(Package value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  Package undeleted_getParentPackage();

	/**
	 * Returns the value of the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Read Only</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read Only</em>' attribute.
	 * @see #setReadOnly(boolean)
	 * @see org.eclipse.uml2.UML2Package#getPackage_ReadOnly()
	 * @model dataType="org.eclipse.uml2.Boolean"
	 * @generated
	 */
	boolean isReadOnly();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Package#isReadOnly <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Only</em>' attribute.
	 * @see #isReadOnly()
	 * @generated
	 */
	void setReadOnly(boolean value);





	/**
	 * Returns the value of the '<em><b>Anonymous Deleted Import Placeholders</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Element}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Anonymous Deleted Import Placeholders</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Anonymous Deleted Import Placeholders</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getPackage_AnonymousDeletedImportPlaceholders()
	 * @model type="org.eclipse.uml2.Element" containment="true"
	 * @generated
	 */
	EList getAnonymousDeletedImportPlaceholders();


	/**
	 * Creates a {@link org.eclipse.uml2.Element} and appends it to the '<em><b>Anonymous Deleted Import Placeholders</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.Element} to create.
	 * @return The new {@link org.eclipse.uml2.Element}.
	 * @see #getAnonymousDeletedImportPlaceholders()
	 * @generated
	 */
	Element createAnonymousDeletedImportPlaceholders(EClass eClass);


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getAnonymousDeletedImportPlaceholders();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getAnonymousDeletedImportPlaceholders();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * An invariant constraint based on the following OCL expression:
	 * <code>
	 * self.ownedElements->forAll(e | e.visibility->notEmpty() implies e.visbility = #public or e.visibility = #private)
	 * </code>
	 * <!-- end-model-doc -->
	 * @model dataType="org.eclipse.uml2.Boolean"
	 * @generated
	 */
	boolean validateElementsPublicOrPrivate(DiagnosticChain diagnostics, Map context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A query based on the following OCL expression:
	 * <code>
	 * member->select( m | self.makesVisible(m))
	 * </code>
	 * <!-- end-model-doc -->
	 * @model dataType="org.eclipse.uml2.Set"
	 * @generated
	 */
	Set visibleMembers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A query based on the following OCL expression:
	 * <code>
	 * el.visibility->isEmpty() or el.visibility = #public
	 * </code>
	 * <!-- end-model-doc -->
	 * @model dataType="org.eclipse.uml2.Boolean"
	 * @generated
	 */
	boolean makesVisible(NamedElement el);

	// <!-- begin-custom-operations -->

	/**
	 * Determines whether the specified profile is applied to this package.
	 * 
	 * @param profile
	 *            The profile to test for application.
	 * @return <code>true</code> if the profile is applied to this package;
	 *         <code>false</code> otherwise.
	 */
	boolean isApplied(Profile profile);

	/**
	 * Retrieves the set of all profiles that are applied to this package,
	 * including profiles applied to its nesting package(s).
	 * 
	 * @return The profiles applied to the package.
	 */
	Set getAllAppliedProfiles();

	/**
	 * Applies the current version of the specified profile to this package; if
	 * a different version is already applied, automatically migrates any
	 * associated stereotype values on a "best effort" basis (matching
	 * classifiers and structural features by name).
	 * 
	 * @param profile
	 *            The profile to be applied.
	 * @throws IllegalArgumentException
	 *             If the profile is not defined or its current version is
	 *             already applied to this package or (one of) its nesting
	 *             package(s).
	 */
	void apply(Profile profile);

	/**
	 * Unapplies the specified profile from this package.
	 * 
	 * @param profile
	 *            The profile to be unapplied.
	 * @throws IllegalArgumentException
	 *             If the profile is not applied to this package.
	 */
	void unapply(Profile profile);

	/**
	 * Retrieves the version of the specified profile that is applied to this
	 * package or (one of) its nesting package(s).
	 * 
	 * @param profile
	 *            The profile whose applied version to retrieve.
	 * @return The version of the profile, or <code>null</code> if not
	 *         applied.
	 */
	String getAppliedVersion(Profile profile);

	/**
	 * Creates a package with the specified name as a nested package of this
	 * package.
	 * 
	 * @param name
	 *            The name for the nested package.
	 * @return The new package.
	 * @exception IllegalArgumentException
	 *                If the name is empty.
	 */
	org.eclipse.uml2.Package createNestedPackage(String name);

	/**
	 * Creates a(n) (abstract) class with the specified name as an owned member
	 * of this package.
	 * 
	 * @param name
	 *            The name for the owned class.
	 * @param isAbstract
	 *            Whether the owned class should be abstract.
	 * @return The new class.
	 * @exception IllegalArgumentException
	 *                If the name is empty.
	 */
	org.eclipse.uml2.Class createOwnedClass(String name, boolean isAbstract);

	/**
	 * Creates an enumeration with the specified name as an owned member of this
	 * package.
	 * 
	 * @param name
	 *            The name for the owned enumeration.
	 * @return The new enumeration.
	 * @exception IllegalArgumentException
	 *                If the name is empty.
	 */
	Enumeration createOwnedEnumeraton(String name);

	/**
	 * Creates a primitive type with the specified name as an owned member of
	 * this package.
	 * 
	 * @param name
	 *            The name for the owned primitive type.
	 * @return The new primitive type.
	 * @exception IllegalArgumentException
	 *                If the name is empty.
	 */
	PrimitiveType createOwnedPrimitiveType(String name);

	// <!-- end-custom-operations -->

} // Package
