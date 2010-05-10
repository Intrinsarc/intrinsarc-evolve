/**
 * <copyright>
 * </copyright>
 *
 * $Id: RemoveStructuralFeatureValueActionTest.java,v 1.1 2009-03-04 23:10:20 andrew Exp $
 */
package org.eclipse.uml2.tests;

import junit.textui.TestRunner;

import org.eclipse.uml2.RemoveStructuralFeatureValueAction;
import org.eclipse.uml2.UML2Factory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Remove Structural Feature Value Action</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class RemoveStructuralFeatureValueActionTest extends WriteStructuralFeatureActionTest {
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
		TestRunner.run(RemoveStructuralFeatureValueActionTest.class);
	}

	/**
	 * Constructs a new Remove Structural Feature Value Action test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemoveStructuralFeatureValueActionTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Remove Structural Feature Value Action test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private RemoveStructuralFeatureValueAction getFixture() {
		return (RemoveStructuralFeatureValueAction)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	protected void setUp() throws Exception {
		setFixture(UML2Factory.eINSTANCE.createRemoveStructuralFeatureValueAction());
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


} //RemoveStructuralFeatureValueActionTest
