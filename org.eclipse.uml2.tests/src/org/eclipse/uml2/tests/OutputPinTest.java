/**
 * <copyright>
 * </copyright>
 *
 * $Id: OutputPinTest.java,v 1.1 2009-03-04 23:10:22 andrew Exp $
 */
package org.eclipse.uml2.tests;

import junit.textui.TestRunner;

import org.eclipse.uml2.OutputPin;
import org.eclipse.uml2.UML2Factory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Output Pin</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class OutputPinTest extends PinTest {
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
		TestRunner.run(OutputPinTest.class);
	}

	/**
	 * Constructs a new Output Pin test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OutputPinTest(String name)
	{
		super(name);
	}

	/**
	 * Returns the fixture for this Output Pin test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private OutputPin getFixture()
	{
		return (OutputPin)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception
	{
		setFixture(UML2Factory.eINSTANCE.createOutputPin());
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


} //OutputPinTest
