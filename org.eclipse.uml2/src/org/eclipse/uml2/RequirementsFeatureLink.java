

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.uml2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Requirements Feature Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.RequirementsFeatureLink#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.uml2.RequirementsFeatureLink#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getRequirementsFeatureLink()
 * @model
 * @generated
 */
public interface RequirementsFeatureLink extends Element {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.uml2.RequirementsLinkKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.uml2.RequirementsLinkKind
	 * @see #setKind(RequirementsLinkKind)
	 * @see org.eclipse.uml2.UML2Package#getRequirementsFeatureLink_Kind()
	 * @model
	 * @generated
	 */
	RequirementsLinkKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.RequirementsFeatureLink#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.uml2.RequirementsLinkKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(RequirementsLinkKind value);





	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(RequirementsFeature)
	 * @see org.eclipse.uml2.UML2Package#getRequirementsFeatureLink_Type()
	 * @model required="true"
	 * @generated
	 */
	RequirementsFeature getType();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.RequirementsFeatureLink#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(RequirementsFeature value);




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RequirementsFeature undeleted_getType();

} // RequirementsFeatureLink
