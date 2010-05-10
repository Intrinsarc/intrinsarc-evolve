/**
 * <copyright>
 * </copyright>
 *
 * $Id: PortKind.java,v 1.1 2009-03-04 23:06:46 andrew Exp $
 */
package org.eclipse.uml2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.PersistentAbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Port Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.uml2.UML2Package#getPortKind()
 * @model
 * @generated
 */
public final class PortKind extends PersistentAbstractEnumerator {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The '<em><b>Normal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NORMAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NORMAL_LITERAL
	 * @model name="normal"
	 * @generated
	 * @ordered
	 */
	public static final int NORMAL = 0;

	/**
	 * The '<em><b>Create</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CREATE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CREATE_LITERAL
	 * @model name="create"
	 * @generated
	 * @ordered
	 */
	public static final int CREATE = 1;

	/**
	 * The '<em><b>Hyperport start</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>HYPERPORT START</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HYPERPORT_START_LITERAL
	 * @model name="hyperport_start"
	 * @generated
	 * @ordered
	 */
	public static final int HYPERPORT_START = 2;

	/**
	 * The '<em><b>Hyperport end</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>HYPERPORT END</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HYPERPORT_END_LITERAL
	 * @model name="hyperport_end"
	 * @generated
	 * @ordered
	 */
	public static final int HYPERPORT_END = 3;

	/**
	 * The '<em><b>Autoconnect</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Autoconnect</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AUTOCONNECT_LITERAL
	 * @model name="autoconnect"
	 * @generated
	 * @ordered
	 */
	public static final int AUTOCONNECT = 4;

	/**
	 * The '<em><b>Normal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NORMAL
	 * @generated
	 * @ordered
	 */
	public static final PortKind NORMAL_LITERAL = new PortKind(NORMAL, "normal"); //$NON-NLS-1$

	/**
	 * The '<em><b>Create</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CREATE
	 * @generated
	 * @ordered
	 */
	public static final PortKind CREATE_LITERAL = new PortKind(CREATE, "create"); //$NON-NLS-1$

	/**
	 * The '<em><b>Hyperport start</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HYPERPORT_START
	 * @generated
	 * @ordered
	 */
	public static final PortKind HYPERPORT_START_LITERAL = new PortKind(HYPERPORT_START, "hyperport_start"); //$NON-NLS-1$

	/**
	 * The '<em><b>Hyperport end</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HYPERPORT_END
	 * @generated
	 * @ordered
	 */
	public static final PortKind HYPERPORT_END_LITERAL = new PortKind(HYPERPORT_END, "hyperport_end"); //$NON-NLS-1$

	/**
	 * The '<em><b>Autoconnect</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AUTOCONNECT
	 * @generated
	 * @ordered
	 */
	public static final PortKind AUTOCONNECT_LITERAL = new PortKind(AUTOCONNECT, "autoconnect"); //$NON-NLS-1$

	/**
	 * An array of all the '<em><b>Port Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PortKind[] VALUES_ARRAY =
		new PortKind[] {
			NORMAL_LITERAL,
			CREATE_LITERAL,
			HYPERPORT_START_LITERAL,
			HYPERPORT_END_LITERAL,
			AUTOCONNECT_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Port Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Port Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PortKind get(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PortKind result = VALUES_ARRAY[i];
			if (result.toString().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Port Kind</b></em>' literal with the specified value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PortKind get(int value) {
		switch (value) {
			case NORMAL: return NORMAL_LITERAL;
			case CREATE: return CREATE_LITERAL;
			case HYPERPORT_START: return HYPERPORT_START_LITERAL;
			case HYPERPORT_END: return HYPERPORT_END_LITERAL;
			case AUTOCONNECT: return AUTOCONNECT_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private PortKind(int value, String name) {
		super(value, name);
	}

} //PortKind
