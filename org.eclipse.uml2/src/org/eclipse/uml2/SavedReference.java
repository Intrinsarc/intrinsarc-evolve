

/**
 * <copyright>
 * </copyright>
 *
 * $Id: SavedReference.java,v 1.2 2009-04-22 16:26:07 andrew Exp $
 */
package org.eclipse.uml2;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Saved Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.SavedReference#getTo <em>To</em>}</li>
 *   <li>{@link org.eclipse.uml2.SavedReference#getFrom <em>From</em>}</li>
 *   <li>{@link org.eclipse.uml2.SavedReference#getToEClass <em>To EClass</em>}</li>
 *   <li>{@link org.eclipse.uml2.SavedReference#getFeature <em>Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getSavedReference()
 * @model
 * @generated
 */
public interface SavedReference extends PackageableElement{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' attribute.
	 * @see #setFrom(String)
	 * @see org.eclipse.uml2.UML2Package#getSavedReference_From()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getFrom();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.SavedReference#getFrom <em>From</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' attribute.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(String value);





	/**
	 * Returns the value of the '<em><b>To EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To EClass</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To EClass</em>' reference.
	 * @see #setToEClass(EClass)
	 * @see org.eclipse.uml2.UML2Package#getSavedReference_ToEClass()
	 * @model
	 * @generated
	 */
	EClass getToEClass();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.SavedReference#getToEClass <em>To EClass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To EClass</em>' reference.
	 * @see #getToEClass()
	 * @generated
	 */
	void setToEClass(EClass value);




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EClass undeleted_getToEClass();

	/**
	 * Returns the value of the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' reference.
	 * @see #setFeature(EReference)
	 * @see org.eclipse.uml2.UML2Package#getSavedReference_Feature()
	 * @model
	 * @generated
	 */
	EReference getFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.SavedReference#getFeature <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' reference.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(EReference value);




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EReference undeleted_getFeature();

	/**
	 * Returns the value of the '<em><b>To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' attribute.
	 * @see #setTo(String)
	 * @see org.eclipse.uml2.UML2Package#getSavedReference_To()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getTo();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.SavedReference#getTo <em>To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' attribute.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(String value);





} // SavedReference
