

/**
 * <copyright>
 * </copyright>
 *
 * $Id: DeltaDeletedConnectorTest.java,v 1.1 2009-03-04 23:10:19 andrew Exp $
 */
package org.eclipse.uml2.tests;

import junit.textui.TestRunner;

import org.eclipse.uml2.DeltaDeletedConnector;
import org.eclipse.uml2.UML2Factory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Delta Deleted Connector</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DeltaDeletedConnectorTest extends DeltaDeletedConstituentTest {
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
		TestRunner.run(DeltaDeletedConnectorTest.class);
	}

	/**
	 * Constructs a new Delta Deleted Connector test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaDeletedConnectorTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Delta Deleted Connector test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private DeltaDeletedConnector getFixture() {
		return (DeltaDeletedConnector)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(UML2Factory.eINSTANCE.createDeltaDeletedConnector());
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


} //DeltaDeletedConnectorTest
