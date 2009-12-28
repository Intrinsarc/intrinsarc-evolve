

/**
 * <copyright>
 * </copyright>
 *
 * $Id: PropertyValueSpecification.java,v 1.1 2009-03-04 23:06:47 andrew Exp $
 */
package org.eclipse.uml2;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Value Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.PropertyValueSpecification#isAliased <em>Aliased</em>}</li>
 *   <li>{@link org.eclipse.uml2.PropertyValueSpecification#getProperty <em>Property</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getPropertyValueSpecification()
 * @model
 * @generated
 */
public interface PropertyValueSpecification extends ValueSpecification{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Aliased</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aliased</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Aliased</em>' attribute.
	 * @see #setAliased(boolean)
	 * @see org.eclipse.uml2.UML2Package#getPropertyValueSpecification_Aliased()
	 * @model dataType="org.eclipse.uml2.Boolean"
	 * @generated
	 */
	boolean isAliased();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.PropertyValueSpecification#isAliased <em>Aliased</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Aliased</em>' attribute.
	 * @see #isAliased()
	 * @generated
	 */
	void setAliased(boolean value);





	/**
	 * Returns the value of the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' reference.
	 * @see #setProperty(Property)
	 * @see org.eclipse.uml2.UML2Package#getPropertyValueSpecification_Property()
	 * @model
	 * @generated
	 */
	Property getProperty();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.PropertyValueSpecification#getProperty <em>Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' reference.
	 * @see #getProperty()
	 * @generated
	 */
	void setProperty(Property value);




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Property undeleted_getProperty();

} // PropertyValueSpecification
