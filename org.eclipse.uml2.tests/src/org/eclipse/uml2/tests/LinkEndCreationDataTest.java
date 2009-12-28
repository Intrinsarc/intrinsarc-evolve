/**
 * <copyright>
 * </copyright>
 *
 * $Id: LinkEndCreationDataTest.java,v 1.1 2009-03-04 23:10:22 andrew Exp $
 */
package org.eclipse.uml2.tests;

import junit.textui.TestRunner;

import org.eclipse.uml2.LinkEndCreationData;
import org.eclipse.uml2.UML2Factory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Link End Creation Data</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class LinkEndCreationDataTest extends LinkEndDataTest {
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
		TestRunner.run(LinkEndCreationDataTest.class);
	}

	/**
	 * Constructs a new Link End Creation Data test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkEndCreationDataTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Link End Creation Data test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private LinkEndCreationData getFixture() {
		return (LinkEndCreationData)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(UML2Factory.eINSTANCE.createLinkEndCreationData());
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


} //LinkEndCreationDataTest
