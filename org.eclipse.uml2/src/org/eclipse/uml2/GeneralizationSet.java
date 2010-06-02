/*
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - initial API and implementation
 *
 * $Id: GeneralizationSet.java,v 1.1 2009-03-04 23:06:47 andrew Exp $
 */
package org.eclipse.uml2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generalization Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Each Generalization is a binary relationship that relates a specific Classifier to a more general Classifier (i.e., a subclass). 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.GeneralizationSet#isCovering <em>Is Covering</em>}</li>
 *   <li>{@link org.eclipse.uml2.GeneralizationSet#isDisjoint <em>Is Disjoint</em>}</li>
 *   <li>{@link org.eclipse.uml2.GeneralizationSet#getPowertype <em>Powertype</em>}</li>
 *   <li>{@link org.eclipse.uml2.GeneralizationSet#getGeneralizations <em>Generalization</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getGeneralizationSet()
 * @model
 * @generated
 */
public interface GeneralizationSet extends PackageableElement{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Is Covering</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Covering</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates (via the associated Generalizations) whether or not the set of specific Classifiers are covering for a particular general classifier. When isCovering is true, every instance of a particular general Classifier is also an instance of at least one of its specific Classifiers for the GeneralizationSet. When isCovering is false, there are one or more instances of the particular general Classifier that are not instances of at least one of its specific Classifiers defined for the GeneralizationSet. For example, Person could have two Generalization relationships each with a different specific Classifier: Male Person and Female Person. This GeneralizationSet would be covering because every instance of Person would be an instance of Male Person or Female Person. In contrast, Person could have a three Generalization relationships involving three specific Classifiers: North AmericanPerson, Asian Person, and European Person. This GeneralizationSet would not be covering because there are instances of Person for which these three specific Classifiers do not apply. The first example, then, could be read: any Person would be specialized as either being a Male Person or a Female Person�and nothing else; the second could be read: any Person would be specialized as being North American Person, Asian Person, European Person, or something else.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Is Covering</em>' attribute.
	 * @see #setIsCovering(boolean)
	 * @see org.eclipse.uml2.UML2Package#getGeneralizationSet_IsCovering()
	 * @model dataType="org.eclipse.uml2.Boolean"
	 * @generated
	 */
	boolean isCovering();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.GeneralizationSet#isCovering <em>Is Covering</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Covering</em>' attribute.
	 * @see #isCovering()
	 * @generated
	 */
	void setIsCovering(boolean value);





	/**
	 * Returns the value of the '<em><b>Is Disjoint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Disjoint</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates whether or not the set of specific Classifiers in a Generalization relationship have instance in common. If isDisjoint is true, the specific Classifiers for a particular GeneralizationSet have no members in common; that is, their intersection is empty. If isDisjoint is false, the specific Classifiers in a particular GeneralizationSet have one or more members in common; that is, their intersection is not empty. For example, Person could have two Generalization relationships, each with the different specific Classifier: Manager or Staff. This would be disjoint because every instance of Person must either be a Manager or Staff. In contrast, Person could have two Generalization relationships involving two specific (and non-covering) Classifiers: Sales Person and Manager. This Generalization- Set would not be disjoint because there are instances of Person which can be a Sales Person and a Manager.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Is Disjoint</em>' attribute.
	 * @see #setIsDisjoint(boolean)
	 * @see org.eclipse.uml2.UML2Package#getGeneralizationSet_IsDisjoint()
	 * @model dataType="org.eclipse.uml2.Boolean"
	 * @generated
	 */
	boolean isDisjoint();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.GeneralizationSet#isDisjoint <em>Is Disjoint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Disjoint</em>' attribute.
	 * @see #isDisjoint()
	 * @generated
	 */
	void setIsDisjoint(boolean value);





	/**
	 * Returns the value of the '<em><b>Powertype</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Classifier#getPowertypeExtents <em>Powertype Extent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Powertype</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Powertype</em>' reference.
	 * @see #setPowertype(Classifier)
	 * @see org.eclipse.uml2.UML2Package#getGeneralizationSet_Powertype()
	 * @see org.eclipse.uml2.Classifier#getPowertypeExtents
	 * @model opposite="powertypeExtent"
	 * @generated
	 */
	Classifier getPowertype();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.GeneralizationSet#getPowertype <em>Powertype</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Powertype</em>' reference.
	 * @see #getPowertype()
	 * @generated
	 */
	void setPowertype(Classifier value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  Classifier undeleted_getPowertype();

	/**
	 * Returns the value of the '<em><b>Generalization</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.uml2.Generalization}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.Generalization#getGeneralizationSets <em>Generalization Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generalization</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generalization</em>' reference list.
	 * @see org.eclipse.uml2.UML2Package#getGeneralizationSet_Generalization()
	 * @see org.eclipse.uml2.Generalization#getGeneralizationSets
	 * @model type="org.eclipse.uml2.Generalization" opposite="generalizationSet" ordered="false"
	 * @generated
	 */
	EList getGeneralizations();



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EList settable_getGeneralizations();
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  java.util.ArrayList undeleted_getGeneralizations();


} // GeneralizationSet
