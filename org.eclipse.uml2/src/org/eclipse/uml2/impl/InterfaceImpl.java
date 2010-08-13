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
 * $Id: InterfaceImpl.java,v 1.1 2009-03-04 23:06:39 andrew Exp $
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
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Classifier;
import org.eclipse.uml2.CollaborationOccurrence;
import org.eclipse.uml2.DeltaDeletedAttribute;
import org.eclipse.uml2.DeltaDeletedOperation;
import org.eclipse.uml2.DeltaReplacedAttribute;
import org.eclipse.uml2.DeltaReplacedOperation;
import org.eclipse.uml2.Interface;
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.Operation;
import org.eclipse.uml2.Property;
import org.eclipse.uml2.ProtocolStateMachine;
import org.eclipse.uml2.Reception;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.Type;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.internal.operation.TypeOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interface</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.InterfaceImpl#getOwnedAttributes <em>Owned Attribute</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InterfaceImpl#getOwnedOperations <em>Owned Operation</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InterfaceImpl#getRedefinedInterfaces <em>Redefined Interface</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InterfaceImpl#getNestedClassifiers <em>Nested Classifier</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InterfaceImpl#getOwnedReceptions <em>Owned Reception</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InterfaceImpl#getProtocol <em>Protocol</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InterfaceImpl#getDeltaDeletedOperations <em>Delta Deleted Operations</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InterfaceImpl#getDeltaReplacedOperations <em>Delta Replaced Operations</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InterfaceImpl#getDeltaDeletedAttributes <em>Delta Deleted Attributes</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.InterfaceImpl#getDeltaReplacedAttributes <em>Delta Replaced Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InterfaceImpl extends ClassifierImpl implements Interface {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getOwnedAttributes() <em>Owned Attribute</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList ownedAttribute = null;

	/**
	 * The cached value of the '{@link #getOwnedOperations() <em>Owned Operation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedOperations()
	 * @generated
	 * @ordered
	 */
	protected EList ownedOperation = null;

	/**
	 * The cached value of the '{@link #getRedefinedInterfaces() <em>Redefined Interface</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRedefinedInterfaces()
	 * @generated
	 * @ordered
	 */
	protected EList redefinedInterface = null;

	/**
	 * The cached value of the '{@link #getNestedClassifiers() <em>Nested Classifier</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNestedClassifiers()
	 * @generated
	 * @ordered
	 */
	protected EList nestedClassifier = null;

	/**
	 * The cached value of the '{@link #getOwnedReceptions() <em>Owned Reception</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedReceptions()
	 * @generated
	 * @ordered
	 */
	protected EList ownedReception = null;

	/**
	 * The cached value of the '{@link #getProtocol() <em>Protocol</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocol()
	 * @generated
	 * @ordered
	 */
	protected ProtocolStateMachine protocol = null;

	/**
	 * The cached value of the '{@link #getDeltaDeletedOperations() <em>Delta Deleted Operations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltaDeletedOperations()
	 * @generated
	 * @ordered
	 */
	protected EList deltaDeletedOperations = null;

	/**
	 * The cached value of the '{@link #getDeltaReplacedOperations() <em>Delta Replaced Operations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltaReplacedOperations()
	 * @generated
	 * @ordered
	 */
	protected EList deltaReplacedOperations = null;

	/**
	 * The cached value of the '{@link #getDeltaDeletedAttributes() <em>Delta Deleted Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltaDeletedAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList deltaDeletedAttributes = null;

	/**
	 * The cached value of the '{@link #getDeltaReplacedAttributes() <em>Delta Replaced Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltaReplacedAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList deltaReplacedAttributes = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InterfaceImpl() {
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (InterfaceImpl.class.equals(getClass()))
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getInterface();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedAttributes() {
		if (ownedAttribute == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		ownedAttribute = new com.intrinsarc.emflist.PersistentEList(Property.class, this, UML2Package.INTERFACE__OWNED_ATTRIBUTE);
			 		return ownedAttribute;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Property.class, this, UML2Package.INTERFACE__OWNED_ATTRIBUTE);
		}      
		return ownedAttribute;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedAttributes() {
		if (ownedAttribute == null) {
			ownedAttribute = new com.intrinsarc.emflist.PersistentEList(Property.class, this, UML2Package.INTERFACE__OWNED_ATTRIBUTE);
		}
		return ownedAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedAttributes() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedAttribute != null) {
			for (Object object : ownedAttribute) {
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
    public Property getOwnedAttribute(String name) {
		for (Iterator i = getOwnedAttributes().iterator(); i.hasNext(); ) {
			Property ownedAttribute = (Property) i.next();
			if (name.equals(ownedAttribute.getName())) {
				return ownedAttribute;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property createOwnedAttribute(EClass eClass) {
		Property newOwnedAttribute = (Property) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__OWNED_ATTRIBUTE, null, newOwnedAttribute));
		}
		settable_getOwnedAttributes().add(newOwnedAttribute);
		return newOwnedAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property createOwnedAttribute() {
		Property newOwnedAttribute = UML2Factory.eINSTANCE.createProperty();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__OWNED_ATTRIBUTE, null, newOwnedAttribute));
		}
		settable_getOwnedAttributes().add(newOwnedAttribute);
		return newOwnedAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedOperations() {
		if (ownedOperation == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		ownedOperation = new com.intrinsarc.emflist.PersistentEList(Operation.class, this, UML2Package.INTERFACE__OWNED_OPERATION);
			 		return ownedOperation;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Operation.class, this, UML2Package.INTERFACE__OWNED_OPERATION);
		}      
		return ownedOperation;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedOperations() {
		if (ownedOperation == null) {
			ownedOperation = new com.intrinsarc.emflist.PersistentEList(Operation.class, this, UML2Package.INTERFACE__OWNED_OPERATION);
		}
		return ownedOperation;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedOperations() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedOperation != null) {
			for (Object object : ownedOperation) {
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
    public Operation getOwnedOperation(String name) {
		for (Iterator i = getOwnedOperations().iterator(); i.hasNext(); ) {
			Operation ownedOperation = (Operation) i.next();
			if (name.equals(ownedOperation.getName())) {
				return ownedOperation;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createOwnedOperation() instead.
	 */
	public Operation createOwnedOperation(EClass eClass) {
		Operation newOwnedOperation = (Operation) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__OWNED_OPERATION, null, newOwnedOperation));
		}
		getOwnedOperations().add(newOwnedOperation);
		return newOwnedOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operation createOwnedOperation() {
		Operation newOwnedOperation = UML2Factory.eINSTANCE.createOperation();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__OWNED_OPERATION, null, newOwnedOperation));
		}
		settable_getOwnedOperations().add(newOwnedOperation);
		return newOwnedOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getRedefinedInterfaces() {
		if (redefinedInterface == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		redefinedInterface = new com.intrinsarc.emflist.PersistentEList(Interface.class, this, UML2Package.INTERFACE__REDEFINED_INTERFACE);
			 		return redefinedInterface;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Interface.class, this, UML2Package.INTERFACE__REDEFINED_INTERFACE);
		}      
		return redefinedInterface;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getRedefinedInterfaces() {
		if (redefinedInterface == null) {
			redefinedInterface = new com.intrinsarc.emflist.PersistentEList(Interface.class, this, UML2Package.INTERFACE__REDEFINED_INTERFACE);
		}
		return redefinedInterface;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getRedefinedInterfaces() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (redefinedInterface != null) {
			for (Object object : redefinedInterface) {
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
    public Interface getRedefinedInterface(String name) {
		for (Iterator i = getRedefinedInterfaces().iterator(); i.hasNext(); ) {
			Interface redefinedInterface = (Interface) i.next();
			if (name.equals(redefinedInterface.getName())) {
				return redefinedInterface;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getNestedClassifiers() {
		if (nestedClassifier == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		nestedClassifier = new com.intrinsarc.emflist.PersistentEList(Classifier.class, this, UML2Package.INTERFACE__NESTED_CLASSIFIER);
			 		return nestedClassifier;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Classifier.class, this, UML2Package.INTERFACE__NESTED_CLASSIFIER);
		}      
		return nestedClassifier;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getNestedClassifiers() {
		if (nestedClassifier == null) {
			nestedClassifier = new com.intrinsarc.emflist.PersistentEList(Classifier.class, this, UML2Package.INTERFACE__NESTED_CLASSIFIER);
		}
		return nestedClassifier;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getNestedClassifiers() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (nestedClassifier != null) {
			for (Object object : nestedClassifier) {
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
    public Classifier getNestedClassifier(String name) {
		for (Iterator i = getNestedClassifiers().iterator(); i.hasNext(); ) {
			Classifier nestedClassifier = (Classifier) i.next();
			if (name.equals(nestedClassifier.getName())) {
				return nestedClassifier;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Classifier createNestedClassifier(EClass eClass) {
		Classifier newNestedClassifier = (Classifier) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__NESTED_CLASSIFIER, null, newNestedClassifier));
		}
		settable_getNestedClassifiers().add(newNestedClassifier);
		return newNestedClassifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedReceptions() {
		if (ownedReception == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		ownedReception = new com.intrinsarc.emflist.PersistentEList(Reception.class, this, UML2Package.INTERFACE__OWNED_RECEPTION);
			 		return ownedReception;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Reception.class, this, UML2Package.INTERFACE__OWNED_RECEPTION);
		}      
		return ownedReception;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedReceptions() {
		if (ownedReception == null) {
			ownedReception = new com.intrinsarc.emflist.PersistentEList(Reception.class, this, UML2Package.INTERFACE__OWNED_RECEPTION);
		}
		return ownedReception;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedReceptions() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedReception != null) {
			for (Object object : ownedReception) {
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
    public Reception getOwnedReception(String name) {
		for (Iterator i = getOwnedReceptions().iterator(); i.hasNext(); ) {
			Reception ownedReception = (Reception) i.next();
			if (name.equals(ownedReception.getName())) {
				return ownedReception;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createOwnedReception() instead.
	 */
	public Reception createOwnedReception(EClass eClass) {
		Reception newOwnedReception = (Reception) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__OWNED_RECEPTION, null, newOwnedReception));
		}
		getOwnedReceptions().add(newOwnedReception);
		return newOwnedReception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Reception createOwnedReception() {
		Reception newOwnedReception = UML2Factory.eINSTANCE.createReception();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__OWNED_RECEPTION, null, newOwnedReception));
		}
		settable_getOwnedReceptions().add(newOwnedReception);
		return newOwnedReception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolStateMachine getProtocol() {
		return protocol;
	}

	
	



	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public ProtocolStateMachine undeleted_getProtocol() {
		ProtocolStateMachine temp = getProtocol();
		if (temp != null && temp.isThisDeleted())
				return null;
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProtocol(ProtocolStateMachine newProtocol, NotificationChain msgs) {
		ProtocolStateMachine oldProtocol = protocol;
		protocol = newProtocol;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, UML2Package.INTERFACE__PROTOCOL, oldProtocol, newProtocol);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}

		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProtocol(ProtocolStateMachine newProtocol) {
		if (newProtocol != protocol) {
			NotificationChain msgs = null;
			if (protocol != null)
				msgs = ((InternalEObject)protocol).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.INTERFACE__PROTOCOL, null, msgs);
			if (newProtocol != null)
				msgs = ((InternalEObject)newProtocol).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - UML2Package.INTERFACE__PROTOCOL, null, msgs);
			msgs = basicSetProtocol(newProtocol, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.INTERFACE__PROTOCOL, newProtocol, newProtocol));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createProtocol() instead.
	 */
	public ProtocolStateMachine createProtocol(EClass eClass) {
		ProtocolStateMachine newProtocol = (ProtocolStateMachine) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__PROTOCOL, null, newProtocol));
		}
		setProtocol(newProtocol);
		return newProtocol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolStateMachine createProtocol() {
		ProtocolStateMachine newProtocol = UML2Factory.eINSTANCE.createProtocolStateMachine();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__PROTOCOL, null, newProtocol));
		}
		setProtocol(newProtocol);
		return newProtocol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaDeletedOperations() {
		if (deltaDeletedOperations == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		deltaDeletedOperations = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedOperation.class, this, UML2Package.INTERFACE__DELTA_DELETED_OPERATIONS);
			 		return deltaDeletedOperations;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaDeletedOperation.class, this, UML2Package.INTERFACE__DELTA_DELETED_OPERATIONS);
		}      
		return deltaDeletedOperations;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaDeletedOperations() {
		if (deltaDeletedOperations == null) {
			deltaDeletedOperations = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedOperation.class, this, UML2Package.INTERFACE__DELTA_DELETED_OPERATIONS);
		}
		return deltaDeletedOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaDeletedOperations() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaDeletedOperations != null) {
			for (Object object : deltaDeletedOperations) {
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
	public DeltaDeletedOperation createDeltaDeletedOperations(EClass eClass) {
		DeltaDeletedOperation newDeltaDeletedOperations = (DeltaDeletedOperation) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__DELTA_DELETED_OPERATIONS, null, newDeltaDeletedOperations));
		}
		settable_getDeltaDeletedOperations().add(newDeltaDeletedOperations);
		return newDeltaDeletedOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaDeletedOperation createDeltaDeletedOperations() {
		DeltaDeletedOperation newDeltaDeletedOperations = UML2Factory.eINSTANCE.createDeltaDeletedOperation();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__DELTA_DELETED_OPERATIONS, null, newDeltaDeletedOperations));
		}
		settable_getDeltaDeletedOperations().add(newDeltaDeletedOperations);
		return newDeltaDeletedOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaReplacedOperations() {
		if (deltaReplacedOperations == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		deltaReplacedOperations = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedOperation.class, this, UML2Package.INTERFACE__DELTA_REPLACED_OPERATIONS);
			 		return deltaReplacedOperations;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaReplacedOperation.class, this, UML2Package.INTERFACE__DELTA_REPLACED_OPERATIONS);
		}      
		return deltaReplacedOperations;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaReplacedOperations() {
		if (deltaReplacedOperations == null) {
			deltaReplacedOperations = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedOperation.class, this, UML2Package.INTERFACE__DELTA_REPLACED_OPERATIONS);
		}
		return deltaReplacedOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaReplacedOperations() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaReplacedOperations != null) {
			for (Object object : deltaReplacedOperations) {
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
	public DeltaReplacedOperation createDeltaReplacedOperations(EClass eClass) {
		DeltaReplacedOperation newDeltaReplacedOperations = (DeltaReplacedOperation) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__DELTA_REPLACED_OPERATIONS, null, newDeltaReplacedOperations));
		}
		settable_getDeltaReplacedOperations().add(newDeltaReplacedOperations);
		return newDeltaReplacedOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaReplacedOperation createDeltaReplacedOperations() {
		DeltaReplacedOperation newDeltaReplacedOperations = UML2Factory.eINSTANCE.createDeltaReplacedOperation();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__DELTA_REPLACED_OPERATIONS, null, newDeltaReplacedOperations));
		}
		settable_getDeltaReplacedOperations().add(newDeltaReplacedOperations);
		return newDeltaReplacedOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaDeletedAttributes() {
		if (deltaDeletedAttributes == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		deltaDeletedAttributes = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedAttribute.class, this, UML2Package.INTERFACE__DELTA_DELETED_ATTRIBUTES);
			 		return deltaDeletedAttributes;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaDeletedAttribute.class, this, UML2Package.INTERFACE__DELTA_DELETED_ATTRIBUTES);
		}      
		return deltaDeletedAttributes;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaDeletedAttributes() {
		if (deltaDeletedAttributes == null) {
			deltaDeletedAttributes = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedAttribute.class, this, UML2Package.INTERFACE__DELTA_DELETED_ATTRIBUTES);
		}
		return deltaDeletedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaDeletedAttributes() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaDeletedAttributes != null) {
			for (Object object : deltaDeletedAttributes) {
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
	public DeltaDeletedAttribute createDeltaDeletedAttributes(EClass eClass) {
		DeltaDeletedAttribute newDeltaDeletedAttributes = (DeltaDeletedAttribute) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__DELTA_DELETED_ATTRIBUTES, null, newDeltaDeletedAttributes));
		}
		settable_getDeltaDeletedAttributes().add(newDeltaDeletedAttributes);
		return newDeltaDeletedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaDeletedAttribute createDeltaDeletedAttributes() {
		DeltaDeletedAttribute newDeltaDeletedAttributes = UML2Factory.eINSTANCE.createDeltaDeletedAttribute();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__DELTA_DELETED_ATTRIBUTES, null, newDeltaDeletedAttributes));
		}
		settable_getDeltaDeletedAttributes().add(newDeltaDeletedAttributes);
		return newDeltaDeletedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaReplacedAttributes() {
		if (deltaReplacedAttributes == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		deltaReplacedAttributes = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedAttribute.class, this, UML2Package.INTERFACE__DELTA_REPLACED_ATTRIBUTES);
			 		return deltaReplacedAttributes;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaReplacedAttribute.class, this, UML2Package.INTERFACE__DELTA_REPLACED_ATTRIBUTES);
		}      
		return deltaReplacedAttributes;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaReplacedAttributes() {
		if (deltaReplacedAttributes == null) {
			deltaReplacedAttributes = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedAttribute.class, this, UML2Package.INTERFACE__DELTA_REPLACED_ATTRIBUTES);
		}
		return deltaReplacedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaReplacedAttributes() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaReplacedAttributes != null) {
			for (Object object : deltaReplacedAttributes) {
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
	public DeltaReplacedAttribute createDeltaReplacedAttributes(EClass eClass) {
		DeltaReplacedAttribute newDeltaReplacedAttributes = (DeltaReplacedAttribute) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__DELTA_REPLACED_ATTRIBUTES, null, newDeltaReplacedAttributes));
		}
		settable_getDeltaReplacedAttributes().add(newDeltaReplacedAttributes);
		return newDeltaReplacedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaReplacedAttribute createDeltaReplacedAttributes() {
		DeltaReplacedAttribute newDeltaReplacedAttributes = UML2Factory.eINSTANCE.createDeltaReplacedAttribute();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.INTERFACE__DELTA_REPLACED_ATTRIBUTES, null, newDeltaReplacedAttributes));
		}
		settable_getDeltaReplacedAttributes().add(newDeltaReplacedAttributes);
		return newDeltaReplacedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.INTERFACE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERFACE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERFACE__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.INTERFACE__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.INTERFACE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERFACE__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERFACE__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERFACE__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERFACE__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.INTERFACE__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.INTERFACE__OWNING_PARAMETER, msgs);
				case UML2Package.INTERFACE__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERFACE__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERFACE__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicAdd(otherEnd, msgs);
				case UML2Package.INTERFACE__USE_CASE:
					return ((InternalEList)getUseCases()).basicAdd(otherEnd, msgs);
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
				case UML2Package.INTERFACE__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.INTERFACE__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.INTERFACE__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.INTERFACE__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.INTERFACE__OWNING_PARAMETER, msgs);
				case UML2Package.INTERFACE__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__OWNED_USE_CASE:
					return ((InternalEList)getOwnedUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__USE_CASE:
					return ((InternalEList)getUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__OCCURRENCE:
					return ((InternalEList)getOccurrences()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__OWNED_ATTRIBUTE:
					return ((InternalEList)getOwnedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__OWNED_OPERATION:
					return ((InternalEList)getOwnedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__NESTED_CLASSIFIER:
					return ((InternalEList)getNestedClassifiers()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__OWNED_RECEPTION:
					return ((InternalEList)getOwnedReceptions()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__PROTOCOL:
					return basicSetProtocol(null, msgs);
				case UML2Package.INTERFACE__DELTA_DELETED_OPERATIONS:
					return ((InternalEList)getDeltaDeletedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__DELTA_REPLACED_OPERATIONS:
					return ((InternalEList)getDeltaReplacedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__DELTA_DELETED_ATTRIBUTES:
					return ((InternalEList)getDeltaDeletedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.INTERFACE__DELTA_REPLACED_ATTRIBUTES:
					return ((InternalEList)getDeltaReplacedAttributes()).basicRemove(otherEnd, msgs);
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
				case UML2Package.INTERFACE__OWNING_PARAMETER:
					return eContainer.eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__OWNED_PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
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
			case UML2Package.INTERFACE__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.INTERFACE__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.INTERFACE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.INTERFACE__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.INTERFACE__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.INTERFACE__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.INTERFACE__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.INTERFACE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.INTERFACE__UUID:
				return getUuid();
			case UML2Package.INTERFACE__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.INTERFACE__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.INTERFACE__NAME:
				return getName();
			case UML2Package.INTERFACE__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.INTERFACE__VISIBILITY:
				return getVisibility();
			case UML2Package.INTERFACE__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.INTERFACE__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.INTERFACE__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.INTERFACE__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.INTERFACE__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.INTERFACE__MEMBER:
				return getMembers();
			case UML2Package.INTERFACE__OWNED_RULE:
				return getOwnedRules();
			case UML2Package.INTERFACE__IMPORTED_MEMBER:
				return getImportedMembers();
			case UML2Package.INTERFACE__ELEMENT_IMPORT:
				return getElementImports();
			case UML2Package.INTERFACE__PACKAGE_IMPORT:
				return getPackageImports();
			case UML2Package.INTERFACE__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.INTERFACE__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.INTERFACE__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.INTERFACE__PACKAGE:
				if (resolve) return getPackage();
				return basicGetPackage();
			case UML2Package.INTERFACE__IS_RETIRED:
				return isRetired() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.INTERFACE__REDEFINITION_CONTEXT:
				return getRedefinitionContexts();
			case UML2Package.INTERFACE__IS_LEAF:
				return isLeaf() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.INTERFACE__FEATURE:
				return getFeatures();
			case UML2Package.INTERFACE__IS_ABSTRACT:
				return isAbstract() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.INTERFACE__INHERITED_MEMBER:
				return getInheritedMembers();
			case UML2Package.INTERFACE__GENERAL:
				return getGenerals();
			case UML2Package.INTERFACE__GENERALIZATION:
				return getGeneralizations();
			case UML2Package.INTERFACE__ATTRIBUTE:
				return getAttributes();
			case UML2Package.INTERFACE__REDEFINED_CLASSIFIER:
				return getRedefinedClassifiers();
			case UML2Package.INTERFACE__SUBSTITUTION:
				return getSubstitutions();
			case UML2Package.INTERFACE__POWERTYPE_EXTENT:
				return getPowertypeExtents();
			case UML2Package.INTERFACE__OWNED_USE_CASE:
				return getOwnedUseCases();
			case UML2Package.INTERFACE__USE_CASE:
				return getUseCases();
			case UML2Package.INTERFACE__REPRESENTATION:
				return getRepresentation();
			case UML2Package.INTERFACE__OCCURRENCE:
				return getOccurrences();
			case UML2Package.INTERFACE__OWNED_ATTRIBUTE:
				return getOwnedAttributes();
			case UML2Package.INTERFACE__OWNED_OPERATION:
				return getOwnedOperations();
			case UML2Package.INTERFACE__REDEFINED_INTERFACE:
				return getRedefinedInterfaces();
			case UML2Package.INTERFACE__NESTED_CLASSIFIER:
				return getNestedClassifiers();
			case UML2Package.INTERFACE__OWNED_RECEPTION:
				return getOwnedReceptions();
			case UML2Package.INTERFACE__PROTOCOL:
				return getProtocol();
			case UML2Package.INTERFACE__DELTA_DELETED_OPERATIONS:
				return getDeltaDeletedOperations();
			case UML2Package.INTERFACE__DELTA_REPLACED_OPERATIONS:
				return getDeltaReplacedOperations();
			case UML2Package.INTERFACE__DELTA_DELETED_ATTRIBUTES:
				return getDeltaDeletedAttributes();
			case UML2Package.INTERFACE__DELTA_REPLACED_ATTRIBUTES:
				return getDeltaReplacedAttributes();
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
			case UML2Package.INTERFACE__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.INTERFACE__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.INTERFACE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.INTERFACE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.INTERFACE__NAME:
				setName((String)newValue);
				return;
			case UML2Package.INTERFACE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.INTERFACE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.INTERFACE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__OWNED_RULE:
				getOwnedRules().clear();
				getOwnedRules().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__ELEMENT_IMPORT:
				getElementImports().clear();
				getElementImports().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__PACKAGE_IMPORT:
				getPackageImports().clear();
				getPackageImports().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.INTERFACE__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.INTERFACE__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.INTERFACE__IS_RETIRED:
				setIsRetired(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.INTERFACE__IS_LEAF:
				setIsLeaf(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.INTERFACE__IS_ABSTRACT:
				setIsAbstract(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.INTERFACE__GENERALIZATION:
				getGeneralizations().clear();
				getGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				getRedefinedClassifiers().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__SUBSTITUTION:
				getSubstitutions().clear();
				getSubstitutions().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				getPowertypeExtents().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				getOwnedUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__USE_CASE:
				getUseCases().clear();
				getUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)newValue);
				return;
			case UML2Package.INTERFACE__OCCURRENCE:
				getOccurrences().clear();
				getOccurrences().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__OWNED_ATTRIBUTE:
				getOwnedAttributes().clear();
				getOwnedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__OWNED_OPERATION:
				getOwnedOperations().clear();
				getOwnedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__REDEFINED_INTERFACE:
				getRedefinedInterfaces().clear();
				getRedefinedInterfaces().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__NESTED_CLASSIFIER:
				getNestedClassifiers().clear();
				getNestedClassifiers().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__OWNED_RECEPTION:
				getOwnedReceptions().clear();
				getOwnedReceptions().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__PROTOCOL:
				setProtocol((ProtocolStateMachine)newValue);
				return;
			case UML2Package.INTERFACE__DELTA_DELETED_OPERATIONS:
				getDeltaDeletedOperations().clear();
				getDeltaDeletedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__DELTA_REPLACED_OPERATIONS:
				getDeltaReplacedOperations().clear();
				getDeltaReplacedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__DELTA_DELETED_ATTRIBUTES:
				getDeltaDeletedAttributes().clear();
				getDeltaDeletedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.INTERFACE__DELTA_REPLACED_ATTRIBUTES:
				getDeltaReplacedAttributes().clear();
				getDeltaReplacedAttributes().addAll((Collection)newValue);
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
			case UML2Package.INTERFACE__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.INTERFACE__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.INTERFACE__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.INTERFACE__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.INTERFACE__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.INTERFACE__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.INTERFACE__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.INTERFACE__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.INTERFACE__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.INTERFACE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.INTERFACE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.INTERFACE__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.INTERFACE__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.INTERFACE__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.INTERFACE__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.INTERFACE__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.INTERFACE__OWNED_RULE:
				getOwnedRules().clear();
				return;
			case UML2Package.INTERFACE__ELEMENT_IMPORT:
				getElementImports().clear();
				return;
			case UML2Package.INTERFACE__PACKAGE_IMPORT:
				getPackageImports().clear();
				return;
			case UML2Package.INTERFACE__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.INTERFACE__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.INTERFACE__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.INTERFACE__IS_RETIRED:
				setIsRetired(IS_RETIRED_EDEFAULT);
				return;
			case UML2Package.INTERFACE__IS_LEAF:
				setIsLeaf(IS_LEAF_EDEFAULT);
				return;
			case UML2Package.INTERFACE__IS_ABSTRACT:
				setIsAbstract(IS_ABSTRACT_EDEFAULT);
				return;
			case UML2Package.INTERFACE__GENERALIZATION:
				getGeneralizations().clear();
				return;
			case UML2Package.INTERFACE__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				return;
			case UML2Package.INTERFACE__SUBSTITUTION:
				getSubstitutions().clear();
				return;
			case UML2Package.INTERFACE__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				return;
			case UML2Package.INTERFACE__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				return;
			case UML2Package.INTERFACE__USE_CASE:
				getUseCases().clear();
				return;
			case UML2Package.INTERFACE__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)null);
				return;
			case UML2Package.INTERFACE__OCCURRENCE:
				getOccurrences().clear();
				return;
			case UML2Package.INTERFACE__OWNED_ATTRIBUTE:
				getOwnedAttributes().clear();
				return;
			case UML2Package.INTERFACE__OWNED_OPERATION:
				getOwnedOperations().clear();
				return;
			case UML2Package.INTERFACE__REDEFINED_INTERFACE:
				getRedefinedInterfaces().clear();
				return;
			case UML2Package.INTERFACE__NESTED_CLASSIFIER:
				getNestedClassifiers().clear();
				return;
			case UML2Package.INTERFACE__OWNED_RECEPTION:
				getOwnedReceptions().clear();
				return;
			case UML2Package.INTERFACE__PROTOCOL:
				setProtocol((ProtocolStateMachine)null);
				return;
			case UML2Package.INTERFACE__DELTA_DELETED_OPERATIONS:
				getDeltaDeletedOperations().clear();
				return;
			case UML2Package.INTERFACE__DELTA_REPLACED_OPERATIONS:
				getDeltaReplacedOperations().clear();
				return;
			case UML2Package.INTERFACE__DELTA_DELETED_ATTRIBUTES:
				getDeltaDeletedAttributes().clear();
				return;
			case UML2Package.INTERFACE__DELTA_REPLACED_ATTRIBUTES:
				getDeltaReplacedAttributes().clear();
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSetGen(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.INTERFACE__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.INTERFACE__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.INTERFACE__OWNER:
				return basicGetOwner() != null;
			case UML2Package.INTERFACE__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.INTERFACE__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.INTERFACE__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.INTERFACE__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.INTERFACE__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.INTERFACE__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.INTERFACE__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.INTERFACE__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.INTERFACE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.INTERFACE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.INTERFACE__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.INTERFACE__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.INTERFACE__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.INTERFACE__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.INTERFACE__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.INTERFACE__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.INTERFACE__MEMBER:
				return !getMembers().isEmpty();
			case UML2Package.INTERFACE__OWNED_RULE:
				return ownedRule != null && !ownedRule.isEmpty();
			case UML2Package.INTERFACE__IMPORTED_MEMBER:
				return !getImportedMembers().isEmpty();
			case UML2Package.INTERFACE__ELEMENT_IMPORT:
				return elementImport != null && !elementImport.isEmpty();
			case UML2Package.INTERFACE__PACKAGE_IMPORT:
				return packageImport != null && !packageImport.isEmpty();
			case UML2Package.INTERFACE__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.INTERFACE__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.INTERFACE__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.INTERFACE__PACKAGE:
				return basicGetPackage() != null;
			case UML2Package.INTERFACE__IS_RETIRED:
				return ((eFlags & IS_RETIRED_EFLAG) != 0) != IS_RETIRED_EDEFAULT;
			case UML2Package.INTERFACE__REDEFINITION_CONTEXT:
				return !getRedefinitionContexts().isEmpty();
			case UML2Package.INTERFACE__IS_LEAF:
				return ((eFlags & IS_LEAF_EFLAG) != 0) != IS_LEAF_EDEFAULT;
			case UML2Package.INTERFACE__FEATURE:
				return !getFeatures().isEmpty();
			case UML2Package.INTERFACE__IS_ABSTRACT:
				return ((eFlags & IS_ABSTRACT_EFLAG) != 0) != IS_ABSTRACT_EDEFAULT;
			case UML2Package.INTERFACE__INHERITED_MEMBER:
				return !getInheritedMembers().isEmpty();
			case UML2Package.INTERFACE__GENERAL:
				return !getGenerals().isEmpty();
			case UML2Package.INTERFACE__GENERALIZATION:
				return generalization != null && !generalization.isEmpty();
			case UML2Package.INTERFACE__ATTRIBUTE:
				return !getAttributes().isEmpty();
			case UML2Package.INTERFACE__REDEFINED_CLASSIFIER:
				return redefinedClassifier != null && !redefinedClassifier.isEmpty();
			case UML2Package.INTERFACE__SUBSTITUTION:
				return substitution != null && !substitution.isEmpty();
			case UML2Package.INTERFACE__POWERTYPE_EXTENT:
				return powertypeExtent != null && !powertypeExtent.isEmpty();
			case UML2Package.INTERFACE__OWNED_USE_CASE:
				return ownedUseCase != null && !ownedUseCase.isEmpty();
			case UML2Package.INTERFACE__USE_CASE:
				return useCase != null && !useCase.isEmpty();
			case UML2Package.INTERFACE__REPRESENTATION:
				return representation != null;
			case UML2Package.INTERFACE__OCCURRENCE:
				return occurrence != null && !occurrence.isEmpty();
			case UML2Package.INTERFACE__OWNED_ATTRIBUTE:
				return ownedAttribute != null && !ownedAttribute.isEmpty();
			case UML2Package.INTERFACE__OWNED_OPERATION:
				return ownedOperation != null && !ownedOperation.isEmpty();
			case UML2Package.INTERFACE__REDEFINED_INTERFACE:
				return redefinedInterface != null && !redefinedInterface.isEmpty();
			case UML2Package.INTERFACE__NESTED_CLASSIFIER:
				return nestedClassifier != null && !nestedClassifier.isEmpty();
			case UML2Package.INTERFACE__OWNED_RECEPTION:
				return ownedReception != null && !ownedReception.isEmpty();
			case UML2Package.INTERFACE__PROTOCOL:
				return protocol != null;
			case UML2Package.INTERFACE__DELTA_DELETED_OPERATIONS:
				return deltaDeletedOperations != null && !deltaDeletedOperations.isEmpty();
			case UML2Package.INTERFACE__DELTA_REPLACED_OPERATIONS:
				return deltaReplacedOperations != null && !deltaReplacedOperations.isEmpty();
			case UML2Package.INTERFACE__DELTA_DELETED_ATTRIBUTES:
				return deltaDeletedAttributes != null && !deltaDeletedAttributes.isEmpty();
			case UML2Package.INTERFACE__DELTA_REPLACED_ATTRIBUTES:
				return deltaReplacedAttributes != null && !deltaReplacedAttributes.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}


	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.INTERFACE__VISIBILITY:
				return false;
			case UML2Package.INTERFACE__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
		}
		return eIsSetGen(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getAttributesHelper(EList attribute) {
		super.getAttributesHelper(attribute);
		if (eIsSet(UML2Package.eINSTANCE.getInterface_OwnedAttribute())) {
			attribute.addAll(getOwnedAttributes());
		}
		return attribute;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedMembersHelper(EList ownedMember) {
		super.getOwnedMembersHelper(ownedMember);
		if (eIsSet(UML2Package.eINSTANCE.getInterface_OwnedAttribute())) {
			ownedMember.addAll(getOwnedAttributes());
		}
		if (eIsSet(UML2Package.eINSTANCE.getInterface_OwnedOperation())) {
			ownedMember.addAll(getOwnedOperations());
		}
		if (eIsSet(UML2Package.eINSTANCE.getInterface_NestedClassifier())) {
			ownedMember.addAll(getNestedClassifiers());
		}
		if (eIsSet(UML2Package.eINSTANCE.getInterface_OwnedReception())) {
			ownedMember.addAll(getOwnedReceptions());
		}
		if (eIsSet(UML2Package.eINSTANCE.getInterface_Protocol())) {
			ownedMember.add(getProtocol());
		}
		return ownedMember;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getFeaturesHelper(EList feature) {
		super.getFeaturesHelper(feature);
		if (eIsSet(UML2Package.eINSTANCE.getInterface_OwnedOperation())) {
			feature.addAll(getOwnedOperations());
		}
		if (eIsSet(UML2Package.eINSTANCE.getInterface_OwnedReception())) {
			feature.addAll(getOwnedReceptions());
		}
		return feature;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getRedefinedElementsHelper(EList redefinedElement) {
		super.getRedefinedElementsHelper(redefinedElement);
		if (eIsSet(UML2Package.eINSTANCE.getInterface_RedefinedInterface())) {
			for (Iterator i = ((InternalEList) getRedefinedInterfaces()).basicIterator(); i.hasNext(); ) {
				redefinedElement.add(i.next());
			}
		}
		return redefinedElement;
	}


	// <!-- begin-custom-operations -->

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Interface#createOwnedAttribute(java.lang.String,
	 *      org.eclipse.uml2.Type, int, int)
	 */
	public Property createOwnedAttribute(String name, Type type,
			int lowerBound, int upperBound) {
		return TypeOperations.createOwnedAttribute(this, name, type,
			lowerBound, upperBound);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Interface#createOwnedOperation(java.lang.String,
	 *      org.eclipse.uml2.Type, java.lang.String[], org.eclipse.uml2.Type[])
	 */
	public Operation createOwnedOperation(String name, Type returnType,
			String[] parameterNames, Type[] parameterTypes) {
		return TypeOperations.createOwnedOperation(this, name, returnType,
			parameterNames, parameterTypes);
	}

	// <!-- end-custom-operations -->

} //InterfaceImpl
