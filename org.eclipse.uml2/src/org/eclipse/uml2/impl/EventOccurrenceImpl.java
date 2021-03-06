/*
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - initial API and implementation
 *
 * $Id: EventOccurrenceImpl.java,v 1.1 2009-03-04 23:06:37 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;

import java.util.Iterator;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

//import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.EventOccurrence;
import org.eclipse.uml2.ExecutionOccurrence;
import org.eclipse.uml2.GeneralOrdering;
import org.eclipse.uml2.Interaction;
import org.eclipse.uml2.InteractionOperand;
import org.eclipse.uml2.Lifeline;
import org.eclipse.uml2.Message;
import org.eclipse.uml2.MessageEnd;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Occurrence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.EventOccurrenceImpl#getReceiveMessage <em>Receive Message</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.EventOccurrenceImpl#getSendMessage <em>Send Message</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.EventOccurrenceImpl#getStartExecs <em>Start Exec</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.EventOccurrenceImpl#getFinishExecs <em>Finish Exec</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.EventOccurrenceImpl#getToAfters <em>To After</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.EventOccurrenceImpl#getToBefores <em>To Before</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.EventOccurrenceImpl#getCovereds <em>Covered</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventOccurrenceImpl extends InteractionFragmentImpl implements EventOccurrence {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getReceiveMessage() <em>Receive Message</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReceiveMessage()
	 * @generated
	 * @ordered
	 */
	protected Message receiveMessage = null;

	/**
	 * The cached value of the '{@link #getSendMessage() <em>Send Message</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSendMessage()
	 * @generated
	 * @ordered
	 */
	protected Message sendMessage = null;

	/**
	 * The cached value of the '{@link #getStartExecs() <em>Start Exec</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartExecs()
	 * @generated
	 * @ordered
	 */
	protected EList startExec = null;

	/**
	 * The cached value of the '{@link #getFinishExecs() <em>Finish Exec</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFinishExecs()
	 * @generated
	 * @ordered
	 */
	protected EList finishExec = null;

	/**
	 * The cached value of the '{@link #getToAfters() <em>To After</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToAfters()
	 * @generated
	 * @ordered
	 */
	protected EList toAfter = null;

	/**
	 * The cached value of the '{@link #getToBefores() <em>To Before</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToBefores()
	 * @generated
	 * @ordered
	 */
	protected EList toBefore = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventOccurrenceImpl() {
		super();
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (EventOccurrenceImpl.class.equals(getClass()) && org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getEventOccurrence();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message getReceiveMessage() {
		if (receiveMessage != null && receiveMessage.eIsProxy()) {
			Message oldReceiveMessage = receiveMessage;
			receiveMessage = (Message)eResolveProxy((InternalEObject)receiveMessage);
			if (receiveMessage != oldReceiveMessage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.EVENT_OCCURRENCE__RECEIVE_MESSAGE, oldReceiveMessage, receiveMessage));
			}
		}
		return receiveMessage;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Message undeleted_getReceiveMessage() {
		Message temp = getReceiveMessage();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message basicGetReceiveMessage() {
		return receiveMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReceiveMessage(Message newReceiveMessage, NotificationChain msgs) {
		Message oldReceiveMessage = receiveMessage;
		receiveMessage = newReceiveMessage;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.EVENT_OCCURRENCE__RECEIVE_MESSAGE, oldReceiveMessage, newReceiveMessage);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReceiveMessage(Message newReceiveMessage) {
		if (newReceiveMessage != receiveMessage) {
			NotificationChain msgs = null;
			if (receiveMessage != null)
				msgs = ((InternalEObject)receiveMessage).eInverseRemove(this, UML2Package.MESSAGE__RECEIVE_EVENT, Message.class, msgs);
			if (newReceiveMessage != null)
				msgs = ((InternalEObject)newReceiveMessage).eInverseAdd(this, UML2Package.MESSAGE__RECEIVE_EVENT, Message.class, msgs);
			msgs = basicSetReceiveMessage(newReceiveMessage, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.EVENT_OCCURRENCE__RECEIVE_MESSAGE, newReceiveMessage, newReceiveMessage));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message getSendMessage() {
		if (sendMessage != null && sendMessage.eIsProxy()) {
			Message oldSendMessage = sendMessage;
			sendMessage = (Message)eResolveProxy((InternalEObject)sendMessage);
			if (sendMessage != oldSendMessage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.EVENT_OCCURRENCE__SEND_MESSAGE, oldSendMessage, sendMessage));
			}
		}
		return sendMessage;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Message undeleted_getSendMessage() {
		Message temp = getSendMessage();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message basicGetSendMessage() {
		return sendMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSendMessage(Message newSendMessage, NotificationChain msgs) {
		Message oldSendMessage = sendMessage;
		sendMessage = newSendMessage;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.EVENT_OCCURRENCE__SEND_MESSAGE, oldSendMessage, newSendMessage);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSendMessage(Message newSendMessage) {
		if (newSendMessage != sendMessage) {
			NotificationChain msgs = null;
			if (sendMessage != null)
				msgs = ((InternalEObject)sendMessage).eInverseRemove(this, UML2Package.MESSAGE__SEND_EVENT, Message.class, msgs);
			if (newSendMessage != null)
				msgs = ((InternalEObject)newSendMessage).eInverseAdd(this, UML2Package.MESSAGE__SEND_EVENT, Message.class, msgs);
			msgs = basicSetSendMessage(newSendMessage, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.EVENT_OCCURRENCE__SEND_MESSAGE, newSendMessage, newSendMessage));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getStartExecs() {
		if (startExec == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		startExec = new com.intrinsarc.emflist.PersistentEList(ExecutionOccurrence.class, this, UML2Package.EVENT_OCCURRENCE__START_EXEC, UML2Package.EXECUTION_OCCURRENCE__START);
			 		return startExec;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(ExecutionOccurrence.class, this, UML2Package.EVENT_OCCURRENCE__START_EXEC, UML2Package.EXECUTION_OCCURRENCE__START);
		}      
		return startExec;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getStartExecs() {
		if (startExec == null) {
			startExec = new com.intrinsarc.emflist.PersistentEList(ExecutionOccurrence.class, this, UML2Package.EVENT_OCCURRENCE__START_EXEC, UML2Package.EXECUTION_OCCURRENCE__START);
		}
		return startExec;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getStartExecs() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (startExec != null) {
			for (Object object : startExec) {
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public ExecutionOccurrence getStartExec(String name) {
		for (Iterator i = getStartExecs().iterator(); i.hasNext(); ) {
			ExecutionOccurrence startExec = (ExecutionOccurrence) i.next();
			if (name.equals(startExec.getName())) {
				return startExec;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getFinishExecs() {
		if (finishExec == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		finishExec = new com.intrinsarc.emflist.PersistentEList(ExecutionOccurrence.class, this, UML2Package.EVENT_OCCURRENCE__FINISH_EXEC, UML2Package.EXECUTION_OCCURRENCE__FINISH);
			 		return finishExec;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(ExecutionOccurrence.class, this, UML2Package.EVENT_OCCURRENCE__FINISH_EXEC, UML2Package.EXECUTION_OCCURRENCE__FINISH);
		}      
		return finishExec;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getFinishExecs() {
		if (finishExec == null) {
			finishExec = new com.intrinsarc.emflist.PersistentEList(ExecutionOccurrence.class, this, UML2Package.EVENT_OCCURRENCE__FINISH_EXEC, UML2Package.EXECUTION_OCCURRENCE__FINISH);
		}
		return finishExec;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getFinishExecs() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (finishExec != null) {
			for (Object object : finishExec) {
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public ExecutionOccurrence getFinishExec(String name) {
		for (Iterator i = getFinishExecs().iterator(); i.hasNext(); ) {
			ExecutionOccurrence finishExec = (ExecutionOccurrence) i.next();
			if (name.equals(finishExec.getName())) {
				return finishExec;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getToAfters() {
		if (toAfter == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		toAfter = new com.intrinsarc.emflist.PersistentEList(GeneralOrdering.class, this, UML2Package.EVENT_OCCURRENCE__TO_AFTER, UML2Package.GENERAL_ORDERING__BEFORE);
			 		return toAfter;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(GeneralOrdering.class, this, UML2Package.EVENT_OCCURRENCE__TO_AFTER, UML2Package.GENERAL_ORDERING__BEFORE);
		}      
		return toAfter;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getToAfters() {
		if (toAfter == null) {
			toAfter = new com.intrinsarc.emflist.PersistentEList(GeneralOrdering.class, this, UML2Package.EVENT_OCCURRENCE__TO_AFTER, UML2Package.GENERAL_ORDERING__BEFORE);
		}
		return toAfter;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getToAfters() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (toAfter != null) {
			for (Object object : toAfter) {
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public GeneralOrdering getToAfter(String name) {
		for (Iterator i = getToAfters().iterator(); i.hasNext(); ) {
			GeneralOrdering toAfter = (GeneralOrdering) i.next();
			if (name.equals(toAfter.getName())) {
				return toAfter;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getToBefores() {
		if (toBefore == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		toBefore = new com.intrinsarc.emflist.PersistentEList(GeneralOrdering.class, this, UML2Package.EVENT_OCCURRENCE__TO_BEFORE, UML2Package.GENERAL_ORDERING__AFTER);
			 		return toBefore;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(GeneralOrdering.class, this, UML2Package.EVENT_OCCURRENCE__TO_BEFORE, UML2Package.GENERAL_ORDERING__AFTER);
		}      
		return toBefore;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getToBefores() {
		if (toBefore == null) {
			toBefore = new com.intrinsarc.emflist.PersistentEList(GeneralOrdering.class, this, UML2Package.EVENT_OCCURRENCE__TO_BEFORE, UML2Package.GENERAL_ORDERING__AFTER);
		}
		return toBefore;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getToBefores() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (toBefore != null) {
			for (Object object : toBefore) {
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public GeneralOrdering getToBefore(String name) {
		for (Iterator i = getToBefores().iterator(); i.hasNext(); ) {
			GeneralOrdering toBefore = (GeneralOrdering) i.next();
			if (name.equals(toBefore.getName())) {
				return toBefore;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getCovereds() {
		if (covered == null) {
			covered = new EObjectWithInverseResolvingEList.ManyInverse(Lifeline.class, this, UML2Package.EVENT_OCCURRENCE__COVERED, UML2Package.LIFELINE__COVERED_BY);
		}
		return covered;
	}


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getCovereds() {
		if (covered == null) {
			covered = new com.intrinsarc.emflist.PersistentEList(Lifeline.class, this, UML2Package.EVENT_OCCURRENCE__COVERED);
		}
		return covered;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getCovereds() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (covered != null) {
			for (Object object : covered) {
				org.eclipse.uml2.Element element = (org.eclipse.uml2.Element) object;
				if (!element.isThisDeleted())
					temp.add(element);
			}
		}
		return temp;
	}





	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public Lifeline getCovered(String name) {
		for (Iterator i = getCovereds().iterator(); i.hasNext(); ) {
			Lifeline covered = (Lifeline) i.next();
			if (name.equals(covered.getName())) {
				return covered;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.EVENT_OCCURRENCE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.EVENT_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__COVERED:
					return ((InternalEList)getCovereds()).basicAdd(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__ENCLOSING_INTERACTION:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.EVENT_OCCURRENCE__ENCLOSING_INTERACTION, msgs);
				case UML2Package.EVENT_OCCURRENCE__ENCLOSING_OPERAND:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.EVENT_OCCURRENCE__ENCLOSING_OPERAND, msgs);
				case UML2Package.EVENT_OCCURRENCE__RECEIVE_MESSAGE:
					if (receiveMessage != null)
						msgs = ((InternalEObject)receiveMessage).eInverseRemove(this, UML2Package.MESSAGE__RECEIVE_EVENT, Message.class, msgs);
					return basicSetReceiveMessage((Message)otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__SEND_MESSAGE:
					if (sendMessage != null)
						msgs = ((InternalEObject)sendMessage).eInverseRemove(this, UML2Package.MESSAGE__SEND_EVENT, Message.class, msgs);
					return basicSetSendMessage((Message)otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__START_EXEC:
					return ((InternalEList)getStartExecs()).basicAdd(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__FINISH_EXEC:
					return ((InternalEList)getFinishExecs()).basicAdd(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__TO_AFTER:
					return ((InternalEList)getToAfters()).basicAdd(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__TO_BEFORE:
					return ((InternalEList)getToBefores()).basicAdd(otherEnd, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.EVENT_OCCURRENCE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.EVENT_OCCURRENCE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.EVENT_OCCURRENCE__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__COVERED:
					return ((InternalEList)getCovereds()).basicRemove(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__GENERAL_ORDERING:
					return ((InternalEList)getGeneralOrderings()).basicRemove(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__ENCLOSING_INTERACTION:
					return eBasicSetContainer(null, UML2Package.EVENT_OCCURRENCE__ENCLOSING_INTERACTION, msgs);
				case UML2Package.EVENT_OCCURRENCE__ENCLOSING_OPERAND:
					return eBasicSetContainer(null, UML2Package.EVENT_OCCURRENCE__ENCLOSING_OPERAND, msgs);
				case UML2Package.EVENT_OCCURRENCE__RECEIVE_MESSAGE:
					return basicSetReceiveMessage(null, msgs);
				case UML2Package.EVENT_OCCURRENCE__SEND_MESSAGE:
					return basicSetSendMessage(null, msgs);
				case UML2Package.EVENT_OCCURRENCE__START_EXEC:
					return ((InternalEList)getStartExecs()).basicRemove(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__FINISH_EXEC:
					return ((InternalEList)getFinishExecs()).basicRemove(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__TO_AFTER:
					return ((InternalEList)getToAfters()).basicRemove(otherEnd, msgs);
				case UML2Package.EVENT_OCCURRENCE__TO_BEFORE:
					return ((InternalEList)getToBefores()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs) {
		if (eContainerFeatureID >= 0) {
			switch (eContainerFeatureID) {
				case UML2Package.EVENT_OCCURRENCE__ENCLOSING_INTERACTION:
					return eContainer.eInverseRemove(this, UML2Package.INTERACTION__FRAGMENT, Interaction.class, msgs);
				case UML2Package.EVENT_OCCURRENCE__ENCLOSING_OPERAND:
					return eContainer.eInverseRemove(this, UML2Package.INTERACTION_OPERAND__FRAGMENT, InteractionOperand.class, msgs);
				default:
					return eDynamicBasicRemoveFromContainer(msgs);
			}
		}
		return eContainer.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - eContainerFeatureID, null, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.EVENT_OCCURRENCE__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.EVENT_OCCURRENCE__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.EVENT_OCCURRENCE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.EVENT_OCCURRENCE__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.EVENT_OCCURRENCE__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.EVENT_OCCURRENCE__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.EVENT_OCCURRENCE__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.EVENT_OCCURRENCE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.EVENT_OCCURRENCE__UUID:
				return getUuid();
			case UML2Package.EVENT_OCCURRENCE__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.EVENT_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.EVENT_OCCURRENCE__NAME:
				return getName();
			case UML2Package.EVENT_OCCURRENCE__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.EVENT_OCCURRENCE__VISIBILITY:
				return getVisibility();
			case UML2Package.EVENT_OCCURRENCE__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.EVENT_OCCURRENCE__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.EVENT_OCCURRENCE__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.EVENT_OCCURRENCE__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.EVENT_OCCURRENCE__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.EVENT_OCCURRENCE__COVERED:
				return getCovereds();
			case UML2Package.EVENT_OCCURRENCE__GENERAL_ORDERING:
				return getGeneralOrderings();
			case UML2Package.EVENT_OCCURRENCE__ENCLOSING_INTERACTION:
				return getEnclosingInteraction();
			case UML2Package.EVENT_OCCURRENCE__ENCLOSING_OPERAND:
				return getEnclosingOperand();
			case UML2Package.EVENT_OCCURRENCE__RECEIVE_MESSAGE:
				if (resolve) return getReceiveMessage();
				return basicGetReceiveMessage();
			case UML2Package.EVENT_OCCURRENCE__SEND_MESSAGE:
				if (resolve) return getSendMessage();
				return basicGetSendMessage();
			case UML2Package.EVENT_OCCURRENCE__START_EXEC:
				return getStartExecs();
			case UML2Package.EVENT_OCCURRENCE__FINISH_EXEC:
				return getFinishExecs();
			case UML2Package.EVENT_OCCURRENCE__TO_AFTER:
				return getToAfters();
			case UML2Package.EVENT_OCCURRENCE__TO_BEFORE:
				return getToBefores();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.EVENT_OCCURRENCE__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.EVENT_OCCURRENCE__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__NAME:
				setName((String)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__COVERED:
				getCovereds().clear();
				getCovereds().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__GENERAL_ORDERING:
				getGeneralOrderings().clear();
				getGeneralOrderings().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__ENCLOSING_INTERACTION:
				setEnclosingInteraction((Interaction)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__ENCLOSING_OPERAND:
				setEnclosingOperand((InteractionOperand)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__RECEIVE_MESSAGE:
				setReceiveMessage((Message)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__SEND_MESSAGE:
				setSendMessage((Message)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__START_EXEC:
				getStartExecs().clear();
				getStartExecs().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__FINISH_EXEC:
				getFinishExecs().clear();
				getFinishExecs().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__TO_AFTER:
				getToAfters().clear();
				getToAfters().addAll((Collection)newValue);
				return;
			case UML2Package.EVENT_OCCURRENCE__TO_BEFORE:
				getToBefores().clear();
				getToBefores().addAll((Collection)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.EVENT_OCCURRENCE__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.EVENT_OCCURRENCE__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.EVENT_OCCURRENCE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.EVENT_OCCURRENCE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.EVENT_OCCURRENCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.EVENT_OCCURRENCE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.EVENT_OCCURRENCE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.EVENT_OCCURRENCE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__COVERED:
				getCovereds().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__GENERAL_ORDERING:
				getGeneralOrderings().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__ENCLOSING_INTERACTION:
				setEnclosingInteraction((Interaction)null);
				return;
			case UML2Package.EVENT_OCCURRENCE__ENCLOSING_OPERAND:
				setEnclosingOperand((InteractionOperand)null);
				return;
			case UML2Package.EVENT_OCCURRENCE__RECEIVE_MESSAGE:
				setReceiveMessage((Message)null);
				return;
			case UML2Package.EVENT_OCCURRENCE__SEND_MESSAGE:
				setSendMessage((Message)null);
				return;
			case UML2Package.EVENT_OCCURRENCE__START_EXEC:
				getStartExecs().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__FINISH_EXEC:
				getFinishExecs().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__TO_AFTER:
				getToAfters().clear();
				return;
			case UML2Package.EVENT_OCCURRENCE__TO_BEFORE:
				getToBefores().clear();
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.EVENT_OCCURRENCE__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.EVENT_OCCURRENCE__OWNER:
				return basicGetOwner() != null;
			case UML2Package.EVENT_OCCURRENCE__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.EVENT_OCCURRENCE__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.EVENT_OCCURRENCE__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.EVENT_OCCURRENCE__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.EVENT_OCCURRENCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.EVENT_OCCURRENCE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.EVENT_OCCURRENCE__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.EVENT_OCCURRENCE__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.EVENT_OCCURRENCE__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__COVERED:
				return !getCovereds().isEmpty();
			case UML2Package.EVENT_OCCURRENCE__GENERAL_ORDERING:
				return generalOrdering != null && !generalOrdering.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__ENCLOSING_INTERACTION:
				return getEnclosingInteraction() != null;
			case UML2Package.EVENT_OCCURRENCE__ENCLOSING_OPERAND:
				return getEnclosingOperand() != null;
			case UML2Package.EVENT_OCCURRENCE__RECEIVE_MESSAGE:
				return receiveMessage != null;
			case UML2Package.EVENT_OCCURRENCE__SEND_MESSAGE:
				return sendMessage != null;
			case UML2Package.EVENT_OCCURRENCE__START_EXEC:
				return startExec != null && !startExec.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__FINISH_EXEC:
				return finishExec != null && !finishExec.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__TO_AFTER:
				return toAfter != null && !toAfter.isEmpty();
			case UML2Package.EVENT_OCCURRENCE__TO_BEFORE:
				return toBefore != null && !toBefore.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class baseClass) {
		if (baseClass == MessageEnd.class) {
			switch (derivedFeatureID) {
				case UML2Package.EVENT_OCCURRENCE__RECEIVE_MESSAGE: return UML2Package.MESSAGE_END__RECEIVE_MESSAGE;
				case UML2Package.EVENT_OCCURRENCE__SEND_MESSAGE: return UML2Package.MESSAGE_END__SEND_MESSAGE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class baseClass) {
		if (baseClass == MessageEnd.class) {
			switch (baseFeatureID) {
				case UML2Package.MESSAGE_END__RECEIVE_MESSAGE: return UML2Package.EVENT_OCCURRENCE__RECEIVE_MESSAGE;
				case UML2Package.MESSAGE_END__SEND_MESSAGE: return UML2Package.EVENT_OCCURRENCE__SEND_MESSAGE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}


} //EventOccurrenceImpl
