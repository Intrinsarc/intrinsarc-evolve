/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentKind.java,v 1.1 2009-03-04 23:06:45 andrew Exp $
 */
package org.eclipse.uml2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.PersistentAbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Component Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.uml2.UML2Package#getComponentKind()
 * @model
 * @generated
 */
public final class ComponentKind extends PersistentAbstractEnumerator {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The '<em><b>None</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NONE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NONE_LITERAL
	 * @model name="none"
	 * @generated
	 * @ordered
	 */
	public static final int NONE = 0;

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
	public static final int NORMAL = 1;

	/**
	 * The '<em><b>Primitive</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PRIMITIVE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PRIMITIVE_LITERAL
	 * @model name="primitive"
	 * @generated
	 * @ordered
	 */
	public static final int PRIMITIVE = 2;

	/**
	 * The '<em><b>Stereotype</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>STEREOTYPE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STEREOTYPE_LITERAL
	 * @model name="stereotype"
	 * @generated
	 * @ordered
	 */
	public static final int STEREOTYPE = 3;

	/**
	 * The '<em><b>None</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE
	 * @generated
	 * @ordered
	 */
	public static final ComponentKind NONE_LITERAL = new ComponentKind(NONE, "none"); //$NON-NLS-1$

	/**
	 * The '<em><b>Normal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NORMAL
	 * @generated
	 * @ordered
	 */
	public static final ComponentKind NORMAL_LITERAL = new ComponentKind(NORMAL, "normal"); //$NON-NLS-1$

	/**
	 * The '<em><b>Primitive</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRIMITIVE
	 * @generated
	 * @ordered
	 */
	public static final ComponentKind PRIMITIVE_LITERAL = new ComponentKind(PRIMITIVE, "primitive"); //$NON-NLS-1$

	/**
	 * The '<em><b>Stereotype</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STEREOTYPE
	 * @generated
	 * @ordered
	 */
	public static final ComponentKind STEREOTYPE_LITERAL = new ComponentKind(STEREOTYPE, "stereotype"); //$NON-NLS-1$

	/**
	 * An array of all the '<em><b>Component Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ComponentKind[] VALUES_ARRAY =
		new ComponentKind[] {
			NONE_LITERAL,
			NORMAL_LITERAL,
			PRIMITIVE_LITERAL,
			STEREOTYPE_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Component Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Component Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ComponentKind get(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ComponentKind result = VALUES_ARRAY[i];
			if (result.toString().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Component Kind</b></em>' literal with the specified value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ComponentKind get(int value) {
		switch (value) {
			case NONE: return NONE_LITERAL;
			case NORMAL: return NORMAL_LITERAL;
			case PRIMITIVE: return PRIMITIVE_LITERAL;
			case STEREOTYPE: return STEREOTYPE_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ComponentKind(int value, String name) {
		super(value, name);
	}

} //ComponentKind
