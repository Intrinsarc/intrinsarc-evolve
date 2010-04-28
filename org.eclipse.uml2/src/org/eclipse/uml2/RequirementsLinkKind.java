/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.uml2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.PersistentAbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Requirements Link Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.uml2.UML2Package#getRequirementsLinkKind()
 * @model
 * @generated
 */
public final class RequirementsLinkKind extends PersistentAbstractEnumerator {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The '<em><b>Mandatory</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Mandatory</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MANDATORY_LITERAL
	 * @model name="mandatory"
	 * @generated
	 * @ordered
	 */
	public static final int MANDATORY = 0;

	/**
	 * The '<em><b>Optional</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Optional</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OPTIONAL_LITERAL
	 * @model name="optional"
	 * @generated
	 * @ordered
	 */
	public static final int OPTIONAL = 1;

	/**
	 * The '<em><b>One of</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>One of</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ONE_OF_LITERAL
	 * @model name="one_of"
	 * @generated
	 * @ordered
	 */
	public static final int ONE_OF = 2;

	/**
	 * The '<em><b>One or more</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>One or more</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ONE_OR_MORE_LITERAL
	 * @model name="one_or_more"
	 * @generated
	 * @ordered
	 */
	public static final int ONE_OR_MORE = 3;

	/**
	 * The '<em><b>Mandatory</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MANDATORY
	 * @generated
	 * @ordered
	 */
	public static final RequirementsLinkKind MANDATORY_LITERAL = new RequirementsLinkKind(MANDATORY, "mandatory"); //$NON-NLS-1$

	/**
	 * The '<em><b>Optional</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OPTIONAL
	 * @generated
	 * @ordered
	 */
	public static final RequirementsLinkKind OPTIONAL_LITERAL = new RequirementsLinkKind(OPTIONAL, "optional"); //$NON-NLS-1$

	/**
	 * The '<em><b>One of</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ONE_OF
	 * @generated
	 * @ordered
	 */
	public static final RequirementsLinkKind ONE_OF_LITERAL = new RequirementsLinkKind(ONE_OF, "one_of"); //$NON-NLS-1$

	/**
	 * The '<em><b>One or more</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ONE_OR_MORE
	 * @generated
	 * @ordered
	 */
	public static final RequirementsLinkKind ONE_OR_MORE_LITERAL = new RequirementsLinkKind(ONE_OR_MORE, "one_or_more"); //$NON-NLS-1$

	/**
	 * An array of all the '<em><b>Requirements Link Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final RequirementsLinkKind[] VALUES_ARRAY =
		new RequirementsLinkKind[] {
			MANDATORY_LITERAL,
			OPTIONAL_LITERAL,
			ONE_OF_LITERAL,
			ONE_OR_MORE_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Requirements Link Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Requirements Link Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RequirementsLinkKind get(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			RequirementsLinkKind result = VALUES_ARRAY[i];
			if (result.toString().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Requirements Link Kind</b></em>' literal with the specified value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RequirementsLinkKind get(int value) {
		switch (value) {
			case MANDATORY: return MANDATORY_LITERAL;
			case OPTIONAL: return OPTIONAL_LITERAL;
			case ONE_OF: return ONE_OF_LITERAL;
			case ONE_OR_MORE: return ONE_OR_MORE_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private RequirementsLinkKind(int value, String name) {
		super(value, name);
	}

} //RequirementsLinkKind
