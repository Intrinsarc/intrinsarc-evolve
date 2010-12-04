

/**
 * <copyright>
 * </copyright>
 *
 * $Id: J_PropertyTest.java,v 1.1 2009-03-04 23:10:22 andrew Exp $
 */
package org.eclipse.uml2.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.eclipse.uml2.J_Property;
import org.eclipse.uml2.UML2Factory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>JProperty</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class J_PropertyTest extends ElementTest {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(J_PropertyTest.class);
	}

	/**
	 * Constructs a new JProperty test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public J_PropertyTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this JProperty test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private J_Property getFixture() {
		return (J_Property)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(UML2Factory.eINSTANCE.createJ_Property());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	protected void tearDown() throws Exception {
		setFixture(null);
	}


} //J_PropertyTest
