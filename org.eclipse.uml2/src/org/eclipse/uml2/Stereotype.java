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
 * $Id: Stereotype.java,v 1.1 2009-03-04 23:06:44 andrew Exp $
 */
package org.eclipse.uml2;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Stereotype</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.Stereotype#getExtendsMetaModelElement <em>Extends Meta Model Element</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getStereotype()
 * @model
 * @generated
 */
public interface Stereotype extends org.eclipse.uml2.Class{

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Extends Meta Model Element</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extends Meta Model Element</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extends Meta Model Element</em>' attribute.
	 * @see #setExtendsMetaModelElement(String)
	 * @see org.eclipse.uml2.UML2Package#getStereotype_ExtendsMetaModelElement()
	 * @model default="" dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getExtendsMetaModelElement();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.Stereotype#getExtendsMetaModelElement <em>Extends Meta Model Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extends Meta Model Element</em>' attribute.
	 * @see #getExtendsMetaModelElement()
	 * @generated
	 */
	void setExtendsMetaModelElement(String value);





	// <!-- begin-custom-operations -->

	/**
	 * Creates a(n) (required) extension of the specified Ecore class with this
	 * stereotype.
	 * 
	 * @param eClass
	 *            The Ecore class to be extended.
	 * @param required
	 *            Whether the extension should be required.
	 * @return The new extension.
	 * @throws IllegalArgumentException
	 *             If this stereotype already extends (a subclass of) the Ecore
	 *             class.
	 * @deprecated Use createExtension(org.eclipse.uml2.Class, boolean) instead.
	 */
	Extension createExtension(EClass eClass, boolean required);

	/**
	 * Retrieves the set of Ecore classes extended by this stereotype, including
	 * the Ecore classes extended by its super(stereo)types.
	 * 
	 * @return The Ecore classes extended by this stereotype and its
	 *         super(stereo)types.
	 * @deprecated Use getAllExtendedMetaclasses() instead.
	 */
	Set getAllExtendedEClasses();

	/**
	 * Retrieves the profile that owns this stereotype.
	 * 
	 * @return The profile that owns this stereotype.
	 */
	Profile getProfile();

	/**
	 * Retrieves the localized keyword for this stereotype.
	 * 
	 * @return The localized keyword for this stereotype.
	 */
	String getKeyword();

	/**
	 * Retrieves the keyword for this stereotype, localized if indicated.
	 * 
	 * @param localize
	 *            Whether the keyword should be localized.
	 * @return The (localized) keyword for this stereotype.
	 */
	String getKeyword(boolean localize);

	/**
	 * Creates a(n) (required) extension of the specified metaclass with this
	 * stereotype.
	 * 
	 * @param metaclass
	 *            The metaclass to be extended.
	 * @param required
	 *            Whether the extension should be required.
	 * @return The new extension.
	 * @throws IllegalArgumentException
	 *             If this stereotype or any of its super(stereo)types already
	 *             extends the metaclass.
	 */
	Extension createExtension(org.eclipse.uml2.Class metaclass, boolean required);

	/**
	 * Retrieves the metaclasses extended by this stereotype.
	 * 
	 * @return The metaclasses extended by this stereotype.
	 */
	Set getExtendedMetaclasses();

	/**
	 * Retrieves the metaclasses extended by this stereotype, including the
	 * metaclasses extended by its super(stereo)types.
	 * 
	 * @return The metaclasses extended by this stereotype and its
	 *         super(stereo)types.
	 */
	Set getAllExtendedMetaclasses();

	// <!-- end-custom-operations -->

} // Stereotype
