/**
 * <copyright>
 * </copyright>
 *
 * $Id: PropertyAccessKind.java,v 1.1 2009-03-04 23:06:44 andrew Exp $
 */
package org.eclipse.uml2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.PersistentAbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Property Access Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.uml2.UML2Package#getPropertyAccessKind()
 * @model
 * @generated
 */
public final class PropertyAccessKind extends PersistentAbstractEnumerator {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The '<em><b>Read write</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>READ WRITE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #READ_WRITE_LITERAL
	 * @model name="read_write"
	 * @generated
	 * @ordered
	 */
	public static final int READ_WRITE = 0;

	/**
	 * The '<em><b>Read only</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>READ ONLY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #READ_ONLY_LITERAL
	 * @model name="read_only"
	 * @generated
	 * @ordered
	 */
	public static final int READ_ONLY = 1;

	/**
	 * The '<em><b>Write only</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WRITE ONLY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WRITE_ONLY_LITERAL
	 * @model name="write_only"
	 * @generated
	 * @ordered
	 */
	public static final int WRITE_ONLY = 2;

	/**
	 * The '<em><b>Read write</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #READ_WRITE
	 * @generated
	 * @ordered
	 */
	public static final PropertyAccessKind READ_WRITE_LITERAL = new PropertyAccessKind(READ_WRITE, "read_write"); //$NON-NLS-1$

	/**
	 * The '<em><b>Read only</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #READ_ONLY
	 * @generated
	 * @ordered
	 */
	public static final PropertyAccessKind READ_ONLY_LITERAL = new PropertyAccessKind(READ_ONLY, "read_only"); //$NON-NLS-1$

	/**
	 * The '<em><b>Write only</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WRITE_ONLY
	 * @generated
	 * @ordered
	 */
	public static final PropertyAccessKind WRITE_ONLY_LITERAL = new PropertyAccessKind(WRITE_ONLY, "write_only"); //$NON-NLS-1$

	/**
	 * An array of all the '<em><b>Property Access Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PropertyAccessKind[] VALUES_ARRAY =
		new PropertyAccessKind[] {
			READ_WRITE_LITERAL,
			READ_ONLY_LITERAL,
			WRITE_ONLY_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Property Access Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Property Access Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertyAccessKind get(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PropertyAccessKind result = VALUES_ARRAY[i];
			if (result.toString().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Property Access Kind</b></em>' literal with the specified value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertyAccessKind get(int value) {
		switch (value) {
			case READ_WRITE: return READ_WRITE_LITERAL;
			case READ_ONLY: return READ_ONLY_LITERAL;
			case WRITE_ONLY: return WRITE_ONLY_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private PropertyAccessKind(int value, String name) {
		super(value, name);
	}

} //PropertyAccessKind
