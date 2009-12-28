

/**
 * <copyright>
 * </copyright>
 *
 * $Id: J_DiagramHolder.java,v 1.1 2009-03-04 23:06:44 andrew Exp $
 */
package org.eclipse.uml2;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JDiagram Holder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.J_DiagramHolder#getDiagram <em>Diagram</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_DiagramHolder#getSaveTime <em>Save Time</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_DiagramHolder#getSavedBy <em>Saved By</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getJ_DiagramHolder()
 * @model
 * @generated
 */
public interface J_DiagramHolder extends Element{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram</em>' containment reference.
	 * @see #setDiagram(J_Diagram)
	 * @see org.eclipse.uml2.UML2Package#getJ_DiagramHolder_Diagram()
	 * @model containment="true"
	 * @generated
	 */
	J_Diagram getDiagram();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_DiagramHolder#getDiagram <em>Diagram</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram</em>' containment reference.
	 * @see #getDiagram()
	 * @generated
	 */
	void setDiagram(J_Diagram value);


	/**
	 * Creates a {@link org.eclipse.uml2.J_Diagram} and sets the '<em><b>Diagram</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return The new {@link org.eclipse.uml2.J_Diagram}.
	 * @see #getDiagram()
	 * @generated
	 */
	J_Diagram createDiagram();



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	J_Diagram undeleted_getDiagram();

	/**
	 * Returns the value of the '<em><b>Save Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Save Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Save Time</em>' attribute.
	 * @see #setSaveTime(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_DiagramHolder_SaveTime()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getSaveTime();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_DiagramHolder#getSaveTime <em>Save Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Save Time</em>' attribute.
	 * @see #getSaveTime()
	 * @generated
	 */
	void setSaveTime(String value);





	/**
	 * Returns the value of the '<em><b>Saved By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Saved By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Saved By</em>' attribute.
	 * @see #setSavedBy(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_DiagramHolder_SavedBy()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getSavedBy();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_DiagramHolder#getSavedBy <em>Saved By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Saved By</em>' attribute.
	 * @see #getSavedBy()
	 * @generated
	 */
	void setSavedBy(String value);





} // J_DiagramHolder
