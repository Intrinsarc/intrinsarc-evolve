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
 * $Id: GeneralOrdering.java,v 1.1 2009-03-04 23:06:46 andrew Exp $
 */
package org.eclipse.uml2;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>General Ordering</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A GeneralOrdering represents a binary relation between two Eventoccurrences, to describe that one Eventoccurrence must occur before the other in a valid trace. This mechanism provides the ability to define partial orders of EventOccurrences that may otherwise not have a specified order. A GeneralOrdering is a specialization of NamedElement. A GeneralOrdering may appear anywhere in an Interaction, but only between Eventoccurrences. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.GeneralOrdering#getBefore <em>Before</em>}</li>
 *   <li>{@link org.eclipse.uml2.GeneralOrdering#getAfter <em>After</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getGeneralOrdering()
 * @model
 * @generated
 */
public interface GeneralOrdering extends NamedElement{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Before</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.EventOccurrence#getToAfters <em>To After</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Before</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The Eventoccurrence referred comes before the Eventoccurrence referred by after
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Before</em>' reference.
	 * @see #setBefore(EventOccurrence)
	 * @see org.eclipse.uml2.UML2Package#getGeneralOrdering_Before()
	 * @see org.eclipse.uml2.EventOccurrence#getToAfters
	 * @model opposite="toAfter" required="true"
	 * @generated
	 */
	EventOccurrence getBefore();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.GeneralOrdering#getBefore <em>Before</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Before</em>' reference.
	 * @see #getBefore()
	 * @generated
	 */
	void setBefore(EventOccurrence value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EventOccurrence undeleted_getBefore();

	/**
	 * Returns the value of the '<em><b>After</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.uml2.EventOccurrence#getToBefores <em>To Before</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>After</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The Eventoccurrence referred comes after the Eventoccurrence referred by before
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>After</em>' reference.
	 * @see #setAfter(EventOccurrence)
	 * @see org.eclipse.uml2.UML2Package#getGeneralOrdering_After()
	 * @see org.eclipse.uml2.EventOccurrence#getToBefores
	 * @model opposite="toBefore" required="true"
	 * @generated
	 */
	EventOccurrence getAfter();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.GeneralOrdering#getAfter <em>After</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>After</em>' reference.
	 * @see #getAfter()
	 * @generated
	 */
	void setAfter(EventOccurrence value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  EventOccurrence undeleted_getAfter();

} // GeneralOrdering
