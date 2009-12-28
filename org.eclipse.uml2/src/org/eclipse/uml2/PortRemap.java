

/**
 * <copyright>
 * </copyright>
 *
 * $Id: PortRemap.java,v 1.1 2009-03-04 23:06:46 andrew Exp $
 */
package org.eclipse.uml2;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Port Remap</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.PortRemap#getOriginalPort <em>Original Port</em>}</li>
 *   <li>{@link org.eclipse.uml2.PortRemap#getNewPort <em>New Port</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getPortRemap()
 * @model
 * @generated
 */
public interface PortRemap extends Element{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Original Port</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Original Port</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Original Port</em>' reference.
	 * @see #setOriginalPort(Port)
	 * @see org.eclipse.uml2.UML2Package#getPortRemap_OriginalPort()
	 * @model
	 * @generated
	 */
  Port getOriginalPort();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.PortRemap#getOriginalPort <em>Original Port</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Original Port</em>' reference.
	 * @see #getOriginalPort()
	 * @generated
	 */
  void setOriginalPort(Port value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  Port undeleted_getOriginalPort();

	/**
	 * Returns the value of the '<em><b>New Port</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>New Port</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>New Port</em>' reference.
	 * @see #setNewPort(Port)
	 * @see org.eclipse.uml2.UML2Package#getPortRemap_NewPort()
	 * @model
	 * @generated
	 */
  Port getNewPort();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.PortRemap#getNewPort <em>New Port</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Port</em>' reference.
	 * @see #getNewPort()
	 * @generated
	 */
  void setNewPort(Port value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  Port undeleted_getNewPort();

} // PortRemap
