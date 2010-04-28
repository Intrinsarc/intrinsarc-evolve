

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.uml2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Requirements Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.RequirementsFeature#getSubfeatures <em>Subfeatures</em>}</li>
 *   <li>{@link org.eclipse.uml2.RequirementsFeature#getDeltaReplacedSubfeatures <em>Delta Replaced Subfeatures</em>}</li>
 *   <li>{@link org.eclipse.uml2.RequirementsFeature#getDeltaDeletedSubfeatures <em>Delta Deleted Subfeatures</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getRequirementsFeature()
 * @model
 * @generated
 */
public interface RequirementsFeature extends NamedElement{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Subfeatures</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.RequirementsFeatureLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subfeatures</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subfeatures</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getRequirementsFeature_Subfeatures()
	 * @model type="org.eclipse.uml2.RequirementsFeatureLink" containment="true"
	 * @generated
	 */
	EList getSubfeatures();


	/**
	 * Creates a {@link org.eclipse.uml2.RequirementsFeatureLink} and appends it to the '<em><b>Subfeatures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.RequirementsFeatureLink}.
	 * @see #getSubfeatures()
	 * @generated
	 */
	RequirementsFeatureLink createSubfeatures();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getSubfeatures();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getSubfeatures();


	/**
	 * Returns the value of the '<em><b>Delta Replaced Subfeatures</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.DeltaReplacedRequirementsFeatureLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delta Replaced Subfeatures</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta Replaced Subfeatures</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getRequirementsFeature_DeltaReplacedSubfeatures()
	 * @model type="org.eclipse.uml2.DeltaReplacedRequirementsFeatureLink" containment="true"
	 * @generated
	 */
	EList getDeltaReplacedSubfeatures();


	/**
	 * Creates a {@link org.eclipse.uml2.DeltaReplacedRequirementsFeatureLink} and appends it to the '<em><b>Delta Replaced Subfeatures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.DeltaReplacedRequirementsFeatureLink}.
	 * @see #getDeltaReplacedSubfeatures()
	 * @generated
	 */
	DeltaReplacedRequirementsFeatureLink createDeltaReplacedSubfeatures();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getDeltaReplacedSubfeatures();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getDeltaReplacedSubfeatures();


	/**
	 * Returns the value of the '<em><b>Delta Deleted Subfeatures</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.uml2.DeltaDeletedRequirementsFeatureLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delta Deleted Subfeatures</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delta Deleted Subfeatures</em>' containment reference list.
	 * @see org.eclipse.uml2.UML2Package#getRequirementsFeature_DeltaDeletedSubfeatures()
	 * @model type="org.eclipse.uml2.DeltaDeletedRequirementsFeatureLink" containment="true"
	 * @generated
	 */
	EList getDeltaDeletedSubfeatures();


	/**
	 * Creates a {@link org.eclipse.uml2.DeltaDeletedRequirementsFeatureLink} and appends it to the '<em><b>Delta Deleted Subfeatures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.DeltaDeletedRequirementsFeatureLink}.
	 * @see #getDeltaDeletedSubfeatures()
	 * @generated
	 */
	DeltaDeletedRequirementsFeatureLink createDeltaDeletedSubfeatures();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EList settable_getDeltaDeletedSubfeatures();
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	java.util.ArrayList undeleted_getDeltaDeletedSubfeatures();


} // RequirementsFeature
