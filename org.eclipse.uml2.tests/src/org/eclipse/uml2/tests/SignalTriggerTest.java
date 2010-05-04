/**
 * <copyright>
 * </copyright>
 *
 * $Id: SignalTriggerTest.java,v 1.1 2009-03-04 23:10:20 andrew Exp $
 */
package org.eclipse.uml2.tests;

import junit.textui.TestRunner;

import org.eclipse.uml2.SignalTrigger;
import org.eclipse.uml2.UML2Factory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Signal Trigger</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SignalTriggerTest extends MessageTriggerTest {
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
		TestRunner.run(SignalTriggerTest.class);
	}

	/**
	 * Constructs a new Signal Trigger test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SignalTriggerTest(String name)
	{
		super(name);
	}

	/**
	 * Returns the fixture for this Signal Trigger test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private SignalTrigger getFixture()
	{
		return (SignalTrigger)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception
	{
		setFixture(UML2Factory.eINSTANCE.createSignalTrigger());
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


} //SignalTriggerTest
