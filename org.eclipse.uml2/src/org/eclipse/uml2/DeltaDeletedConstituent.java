

/**
 * <copyright>
 * </copyright>
 *
 * $Id: DeltaDeletedConstituent.java,v 1.1 2009-03-04 23:06:47 andrew Exp $
 */
package org.eclipse.uml2;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delta Deleted Constituent</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.DeltaDeletedConstituent#getDeleted <em>Deleted</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getDeltaDeletedConstituent()
 * @model
 * @generated
 */
public interface DeltaDeletedConstituent extends Element{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Deleted</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deleted</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deleted</em>' reference.
	 * @see #setDeleted(Element)
	 * @see org.eclipse.uml2.UML2Package#getDeltaDeletedConstituent_Deleted()
	 * @model
	 * @generated
	 */
	Element getDeleted();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.DeltaDeletedConstituent#getDeleted <em>Deleted</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deleted</em>' reference.
	 * @see #getDeleted()
	 * @generated
	 */
	void setDeleted(Element value);




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Element undeleted_getDeleted();

} // DeltaDeletedConstituent
