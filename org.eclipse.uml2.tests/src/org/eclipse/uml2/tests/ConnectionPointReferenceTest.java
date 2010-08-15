/**
 * <copyright>
 * </copyright>
 *
 * $Id: ConnectionPointReferenceTest.java,v 1.1 2009-03-04 23:10:20 andrew Exp $
 */
package org.eclipse.uml2.tests;

import junit.textui.TestRunner;

import org.eclipse.uml2.ConnectionPointReference;
import org.eclipse.uml2.UML2Factory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Connection Point Reference</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConnectionPointReferenceTest extends VertexTest {
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
	public static void main(String[] args)
	{
		TestRunner.run(ConnectionPointReferenceTest.class);
	}

	/**
	 * Constructs a new Connection Point Reference test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectionPointReferenceTest(String name)
	{
		super(name);
	}

	/**
	 * Returns the fixture for this Connection Point Reference test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ConnectionPointReference getFixture()
	{
		return (ConnectionPointReference)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception
	{
		setFixture(UML2Factory.eINSTANCE.createConnectionPointReference());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	protected void tearDown() throws Exception
	{
		setFixture(null);
	}


} //ConnectionPointReferenceTest
