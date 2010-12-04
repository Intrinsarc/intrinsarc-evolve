

/**
 * <copyright>
 * </copyright>
 *
 * $Id: DeltaReplacedPortTest.java,v 1.1 2009-03-04 23:10:21 andrew Exp $
 */
package org.eclipse.uml2.tests;

import junit.textui.TestRunner;

import org.eclipse.uml2.DeltaReplacedPort;
import org.eclipse.uml2.UML2Factory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Delta Replaced Port</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DeltaReplacedPortTest extends DeltaReplacedConstituentTest {
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
		TestRunner.run(DeltaReplacedPortTest.class);
	}

	/**
	 * Constructs a new Delta Replaced Port test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaReplacedPortTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Delta Replaced Port test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private DeltaReplacedPort getFixture() {
		return (DeltaReplacedPort)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(UML2Factory.eINSTANCE.createDeltaReplacedPort());
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


} //DeltaReplacedPortTest
