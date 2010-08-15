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
 * $Id: MessageImpl.java,v 1.1 2009-03-04 23:06:35 andrew Exp $
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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Connector;
import org.eclipse.uml2.Interaction;
import org.eclipse.uml2.Message;
import org.eclipse.uml2.MessageEnd;
import org.eclipse.uml2.MessageKind;
import org.eclipse.uml2.MessageSort;
import org.eclipse.uml2.NamedElement;
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.ValueSpecification;
import org.eclipse.uml2.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Message</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.MessageImpl#getMessageKind <em>Message Kind</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.MessageImpl#getMessageSort <em>Message Sort</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.MessageImpl#getReceiveEvent <em>Receive Event</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.MessageImpl#getSendEvent <em>Send Event</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.MessageImpl#getConnector <em>Connector</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.MessageImpl#getInteraction <em>Interaction</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.MessageImpl#getSignature <em>Signature</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.MessageImpl#getArguments <em>Argument</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MessageImpl extends NamedElementImpl implements Message {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The default value of the '{@link #getMessageKind() <em>Message Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageKind()
	 * @generated
	 * @ordered
	 */
	protected static final MessageKind MESSAGE_KIND_EDEFAULT = MessageKind.COMPLETE_LITERAL;

	/**
	 * The default value of the '{@link #getMessageSort() <em>Message Sort</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageSort()
	 * @generated
	 * @ordered
	 */
	protected static final MessageSort MESSAGE_SORT_EDEFAULT = MessageSort.SYNCH_CALL_LITERAL;

	/**
	 * The cached value of the '{@link #getMessageSort() <em>Message Sort</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageSort()
	 * @generated
	 * @ordered
	 */
	protected MessageSort messageSort = MESSAGE_SORT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReceiveEvent() <em>Receive Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReceiveEvent()
	 * @generated
	 * @ordered
	 */
	protected MessageEnd receiveEvent = null;

	/**
	 * The cached value of the '{@link #getSendEvent() <em>Send Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSendEvent()
	 * @generated
	 * @ordered
	 */
	protected MessageEnd sendEvent = null;

	/**
	 * The cached value of the '{@link #getConnector() <em>Connector</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnector()
	 * @generated
	 * @ordered
	 */
	protected Connector connector = null;

	/**
	 * The cached value of the '{@link #getSignature() <em>Signature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected NamedElement signature = null;

	/**
	 * The cached value of the '{@link #getArguments() <em>Argument</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArguments()
	 * @generated
	 * @ordered
	 */
	protected EList argument = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MessageImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (MessageImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getMessage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public MessageKind getMessageKind() {

		return null == getSendEvent()
			? (null == getReceiveEvent() ? MessageKind.UNKNOWN_LITERAL : MessageKind.FOUND_LITERAL)
			: (null == getReceiveEvent() ? MessageKind.LOST_LITERAL : MessageKind.COMPLETE_LITERAL);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MessageSort getMessageSort()
	{
		return messageSort;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessageSort(MessageSort newMessageSort)
	{

		MessageSort oldMessageSort = messageSort;
		messageSort = newMessageSort == null ? MESSAGE_SORT_EDEFAULT : newMessageSort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.MESSAGE__MESSAGE_SORT, oldMessageSort, messageSort));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MessageEnd getReceiveEvent()
	{
		if (receiveEvent != null && receiveEvent.eIsProxy())
		{
			MessageEnd oldReceiveEvent = receiveEvent;
			receiveEvent = (MessageEnd)eResolveProxy((InternalEObject)receiveEvent);
			if (receiveEvent != oldReceiveEvent)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.MESSAGE__RECEIVE_EVENT, oldReceiveEvent, receiveEvent));
			}
		}
		return receiveEvent;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public MessageEnd undeleted_getReceiveEvent()
	{
		MessageEnd temp = getReceiveEvent();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MessageEnd basicGetReceiveEvent()
	{
		return receiveEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReceiveEvent(MessageEnd newReceiveEvent, NotificationChain msgs)
	{

		MessageEnd oldReceiveEvent = receiveEvent;
		receiveEvent = newReceiveEvent;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.MESSAGE__RECEIVE_EVENT, oldReceiveEvent, newReceiveEvent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReceiveEvent(MessageEnd newReceiveEvent)
	{

		if (newReceiveEvent != receiveEvent)
		{
			NotificationChain msgs = null;
			if (receiveEvent != null)
				msgs = ((InternalEObject)receiveEvent).eInverseRemove(this, UML2Package.MESSAGE_END__RECEIVE_MESSAGE, MessageEnd.class, msgs);
			if (newReceiveEvent != null)
				msgs = ((InternalEObject)newReceiveEvent).eInverseAdd(this, UML2Package.MESSAGE_END__RECEIVE_MESSAGE, MessageEnd.class, msgs);
			msgs = basicSetReceiveEvent(newReceiveEvent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.MESSAGE__RECEIVE_EVENT, newReceiveEvent, newReceiveEvent));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MessageEnd getSendEvent()
	{
		if (sendEvent != null && sendEvent.eIsProxy())
		{
			MessageEnd oldSendEvent = sendEvent;
			sendEvent = (MessageEnd)eResolveProxy((InternalEObject)sendEvent);
			if (sendEvent != oldSendEvent)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.MESSAGE__SEND_EVENT, oldSendEvent, sendEvent));
			}
		}
		return sendEvent;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public MessageEnd undeleted_getSendEvent()
	{
		MessageEnd temp = getSendEvent();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MessageEnd basicGetSendEvent()
	{
		return sendEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSendEvent(MessageEnd newSendEvent, NotificationChain msgs)
	{

		MessageEnd oldSendEvent = sendEvent;
		sendEvent = newSendEvent;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.MESSAGE__SEND_EVENT, oldSendEvent, newSendEvent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSendEvent(MessageEnd newSendEvent)
	{

		if (newSendEvent != sendEvent)
		{
			NotificationChain msgs = null;
			if (sendEvent != null)
				msgs = ((InternalEObject)sendEvent).eInverseRemove(this, UML2Package.MESSAGE_END__SEND_MESSAGE, MessageEnd.class, msgs);
			if (newSendEvent != null)
				msgs = ((InternalEObject)newSendEvent).eInverseAdd(this, UML2Package.MESSAGE_END__SEND_MESSAGE, MessageEnd.class, msgs);
			msgs = basicSetSendEvent(newSendEvent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.MESSAGE__SEND_EVENT, newSendEvent, newSendEvent));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Connector getConnector()
	{
		if (connector != null && connector.eIsProxy())
		{
			Connector oldConnector = connector;
			connector = (Connector)eResolveProxy((InternalEObject)connector);
			if (connector != oldConnector)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.MESSAGE__CONNECTOR, oldConnector, connector));
			}
		}
		return connector;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Connector undeleted_getConnector()
	{
		Connector temp = getConnector();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Connector basicGetConnector()
	{
		return connector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnector(Connector newConnector)
	{

		Connector oldConnector = connector;
		connector = newConnector;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.MESSAGE__CONNECTOR, oldConnector, connector));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Interaction getInteraction()
	{
		if (eContainerFeatureID != UML2Package.MESSAGE__INTERACTION) return null;
		return (Interaction)eContainer;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Interaction undeleted_getInteraction()
	{
		Interaction temp = getInteraction();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInteraction(Interaction newInteraction)
	{

		if (newInteraction != eContainer || (eContainerFeatureID != UML2Package.MESSAGE__INTERACTION && newInteraction != null))
		{
			if (EcoreUtil.isAncestor(this, newInteraction))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newInteraction != null)
				msgs = ((InternalEObject)newInteraction).eInverseAdd(this, UML2Package.INTERACTION__MESSAGE, Interaction.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newInteraction, UML2Package.MESSAGE__INTERACTION, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.MESSAGE__INTERACTION, newInteraction, newInteraction));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedElement getSignature()
	{
		if (signature != null && signature.eIsProxy())
		{
			NamedElement oldSignature = signature;
			signature = (NamedElement)eResolveProxy((InternalEObject)signature);
			if (signature != oldSignature)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, UML2Package.MESSAGE__SIGNATURE, oldSignature, signature));
			}
		}
		return signature;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NamedElement undeleted_getSignature()
	{
		NamedElement temp = getSignature();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedElement basicGetSignature()
	{
		return signature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSignature(NamedElement newSignature)
	{

		NamedElement oldSignature = signature;
		signature = newSignature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.MESSAGE__SIGNATURE, oldSignature, signature));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getArguments()
	{
		if (argument == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		argument = new com.intrinsarc.emflist.PersistentEList(ValueSpecification.class, this, UML2Package.MESSAGE__ARGUMENT);
			 		return argument;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(ValueSpecification.class, this, UML2Package.MESSAGE__ARGUMENT);
		}      
		return argument;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getArguments()
	{
		if (argument == null)
		{
			
		
			argument = new com.intrinsarc.emflist.PersistentEList(ValueSpecification.class, this, UML2Package.MESSAGE__ARGUMENT);
		}
		return argument;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getArguments()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (argument != null)
		{
			for (Object object : argument)
			{
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
    public ValueSpecification getArgument(String name) {
		for (Iterator i = getArguments().iterator(); i.hasNext(); ) {
			ValueSpecification argument = (ValueSpecification) i.next();
			if (name.equals(argument.getName())) {
				return argument;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueSpecification createArgument(EClass eClass) {
		ValueSpecification newArgument = (ValueSpecification) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.MESSAGE__ARGUMENT, null, newArgument));
		}
		settable_getArguments().add(newArgument);
		return newArgument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Namespace basicGetNamespace()
	{
		Interaction interaction = getInteraction();			
		if (interaction != null) {
			return interaction;
		}
		return super.basicGetNamespace();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedElementsHelper(EList ownedElement)
	{
		super.getOwnedElementsHelper(ownedElement);
		if (eIsSet(UML2Package.eINSTANCE.getMessage_Argument())) {
			ownedElement.addAll(getArguments());
		}
		return ownedElement;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs)
	{
		if (featureID >= 0)
		{
			switch (eDerivedStructuralFeatureID(featureID, baseClass))
			{
				case UML2Package.MESSAGE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.MESSAGE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.MESSAGE__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.MESSAGE__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.MESSAGE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.MESSAGE__RECEIVE_EVENT:
					if (receiveEvent != null)
						msgs = ((InternalEObject)receiveEvent).eInverseRemove(this, UML2Package.MESSAGE_END__RECEIVE_MESSAGE, MessageEnd.class, msgs);
					return basicSetReceiveEvent((MessageEnd)otherEnd, msgs);
				case UML2Package.MESSAGE__SEND_EVENT:
					if (sendEvent != null)
						msgs = ((InternalEObject)sendEvent).eInverseRemove(this, UML2Package.MESSAGE_END__SEND_MESSAGE, MessageEnd.class, msgs);
					return basicSetSendEvent((MessageEnd)otherEnd, msgs);
				case UML2Package.MESSAGE__INTERACTION:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.MESSAGE__INTERACTION, msgs);
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs)
	{
		if (featureID >= 0)
		{
			switch (eDerivedStructuralFeatureID(featureID, baseClass))
			{
				case UML2Package.MESSAGE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.MESSAGE__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.MESSAGE__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.MESSAGE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.MESSAGE__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.MESSAGE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.MESSAGE__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.MESSAGE__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.MESSAGE__RECEIVE_EVENT:
					return basicSetReceiveEvent(null, msgs);
				case UML2Package.MESSAGE__SEND_EVENT:
					return basicSetSendEvent(null, msgs);
				case UML2Package.MESSAGE__INTERACTION:
					return eBasicSetContainer(null, UML2Package.MESSAGE__INTERACTION, msgs);
				case UML2Package.MESSAGE__ARGUMENT:
					return ((InternalEList)getArguments()).basicRemove(otherEnd, msgs);
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
	public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs)
	{
		if (eContainerFeatureID >= 0)
		{
			switch (eContainerFeatureID)
			{
				case UML2Package.MESSAGE__INTERACTION:
					return eContainer.eInverseRemove(this, UML2Package.INTERACTION__MESSAGE, Interaction.class, msgs);
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.MESSAGE__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.MESSAGE__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.MESSAGE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.MESSAGE__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.MESSAGE__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.MESSAGE__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.MESSAGE__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.MESSAGE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.MESSAGE__UUID:
				return getUuid();
			case UML2Package.MESSAGE__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.MESSAGE__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.MESSAGE__NAME:
				return getName();
			case UML2Package.MESSAGE__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.MESSAGE__VISIBILITY:
				return getVisibility();
			case UML2Package.MESSAGE__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.MESSAGE__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.MESSAGE__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.MESSAGE__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.MESSAGE__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.MESSAGE__MESSAGE_KIND:
				return getMessageKind();
			case UML2Package.MESSAGE__MESSAGE_SORT:
				return getMessageSort();
			case UML2Package.MESSAGE__RECEIVE_EVENT:
				if (resolve) return getReceiveEvent();
				return basicGetReceiveEvent();
			case UML2Package.MESSAGE__SEND_EVENT:
				if (resolve) return getSendEvent();
				return basicGetSendEvent();
			case UML2Package.MESSAGE__CONNECTOR:
				if (resolve) return getConnector();
				return basicGetConnector();
			case UML2Package.MESSAGE__INTERACTION:
				return getInteraction();
			case UML2Package.MESSAGE__SIGNATURE:
				if (resolve) return getSignature();
				return basicGetSignature();
			case UML2Package.MESSAGE__ARGUMENT:
				return getArguments();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.MESSAGE__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.MESSAGE__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.MESSAGE__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.MESSAGE__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.MESSAGE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.MESSAGE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.MESSAGE__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.MESSAGE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.MESSAGE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.MESSAGE__NAME:
				setName((String)newValue);
				return;
			case UML2Package.MESSAGE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.MESSAGE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.MESSAGE__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.MESSAGE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.MESSAGE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.MESSAGE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.MESSAGE__MESSAGE_SORT:
				setMessageSort((MessageSort)newValue);
				return;
			case UML2Package.MESSAGE__RECEIVE_EVENT:
				setReceiveEvent((MessageEnd)newValue);
				return;
			case UML2Package.MESSAGE__SEND_EVENT:
				setSendEvent((MessageEnd)newValue);
				return;
			case UML2Package.MESSAGE__CONNECTOR:
				setConnector((Connector)newValue);
				return;
			case UML2Package.MESSAGE__INTERACTION:
				setInteraction((Interaction)newValue);
				return;
			case UML2Package.MESSAGE__SIGNATURE:
				setSignature((NamedElement)newValue);
				return;
			case UML2Package.MESSAGE__ARGUMENT:
				getArguments().clear();
				getArguments().addAll((Collection)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.MESSAGE__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.MESSAGE__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.MESSAGE__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.MESSAGE__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.MESSAGE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.MESSAGE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.MESSAGE__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.MESSAGE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.MESSAGE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.MESSAGE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.MESSAGE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.MESSAGE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.MESSAGE__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.MESSAGE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.MESSAGE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.MESSAGE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.MESSAGE__MESSAGE_SORT:
				setMessageSort(MESSAGE_SORT_EDEFAULT);
				return;
			case UML2Package.MESSAGE__RECEIVE_EVENT:
				setReceiveEvent((MessageEnd)null);
				return;
			case UML2Package.MESSAGE__SEND_EVENT:
				setSendEvent((MessageEnd)null);
				return;
			case UML2Package.MESSAGE__CONNECTOR:
				setConnector((Connector)null);
				return;
			case UML2Package.MESSAGE__INTERACTION:
				setInteraction((Interaction)null);
				return;
			case UML2Package.MESSAGE__SIGNATURE:
				setSignature((NamedElement)null);
				return;
			case UML2Package.MESSAGE__ARGUMENT:
				getArguments().clear();
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.MESSAGE__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.MESSAGE__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.MESSAGE__OWNER:
				return basicGetOwner() != null;
			case UML2Package.MESSAGE__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.MESSAGE__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.MESSAGE__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.MESSAGE__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.MESSAGE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.MESSAGE__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.MESSAGE__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.MESSAGE__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.MESSAGE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.MESSAGE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.MESSAGE__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case UML2Package.MESSAGE__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.MESSAGE__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.MESSAGE__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.MESSAGE__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.MESSAGE__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.MESSAGE__MESSAGE_KIND:
				return getMessageKind() != MESSAGE_KIND_EDEFAULT;
			case UML2Package.MESSAGE__MESSAGE_SORT:
				return messageSort != MESSAGE_SORT_EDEFAULT;
			case UML2Package.MESSAGE__RECEIVE_EVENT:
				return receiveEvent != null;
			case UML2Package.MESSAGE__SEND_EVENT:
				return sendEvent != null;
			case UML2Package.MESSAGE__CONNECTOR:
				return connector != null;
			case UML2Package.MESSAGE__INTERACTION:
				return getInteraction() != null;
			case UML2Package.MESSAGE__SIGNATURE:
				return signature != null;
			case UML2Package.MESSAGE__ARGUMENT:
				return argument != null && !argument.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (messageSort: "); //$NON-NLS-1$
		result.append(messageSort);
		result.append(')');
		return result.toString();
	}


} //MessageImpl
