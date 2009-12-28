

/**
 * <copyright>
 * </copyright>
 *
 * $Id: DeltaReplacedConstituent.java,v 1.1 2009-03-04 23:06:47 andrew Exp $
 */
package org.eclipse.uml2;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delta Replaced Constituent</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.DeltaReplacedConstituent#getReplaced <em>Replaced</em>}</li>
 *   <li>{@link org.eclipse.uml2.DeltaReplacedConstituent#getReplacement <em>Replacement</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getDeltaReplacedConstituent()
 * @model
 * @generated
 */
public interface DeltaReplacedConstituent extends Element{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Replaced</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Replaced</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Replaced</em>' reference.
	 * @see #setReplaced(Element)
	 * @see org.eclipse.uml2.UML2Package#getDeltaReplacedConstituent_Replaced()
	 * @model
	 * @generated
	 */
	Element getReplaced();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.DeltaReplacedConstituent#getReplaced <em>Replaced</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Replaced</em>' reference.
	 * @see #getReplaced()
	 * @generated
	 */
	void setReplaced(Element value);




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Element undeleted_getReplaced();

	/**
	 * Returns the value of the '<em><b>Replacement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Replacement</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Replacement</em>' containment reference.
	 * @see #setReplacement(Element)
	 * @see org.eclipse.uml2.UML2Package#getDeltaReplacedConstituent_Replacement()
	 * @model containment="true"
	 * @generated
	 */
	Element getReplacement();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.DeltaReplacedConstituent#getReplacement <em>Replacement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Replacement</em>' containment reference.
	 * @see #getReplacement()
	 * @generated
	 */
	void setReplacement(Element value);


	/**
	 * Creates a {@link org.eclipse.uml2.Element} and sets the '<em><b>Replacement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param eClass The Ecore class of the {@link org.eclipse.uml2.Element} to create.
	 * @return The new {@link org.eclipse.uml2.Element}.
	 * @see #getReplacement()
	 * @generated
	 */
	Element createReplacement(EClass eClass);



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Element undeleted_getReplacement();

} // DeltaReplacedConstituent
