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
 * $Id: ClassImpl.java,v 1.1 2009-03-04 23:06:41 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import java.util.*;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.Behavior;
import org.eclipse.uml2.Classifier;
import org.eclipse.uml2.CollaborationOccurrence;
import org.eclipse.uml2.ComponentKind;
import org.eclipse.uml2.ConnectableElement;
import org.eclipse.uml2.Connector;
import org.eclipse.uml2.DeltaDeletedAttribute;
import org.eclipse.uml2.DeltaDeletedConnector;
import org.eclipse.uml2.DeltaDeletedOperation;
import org.eclipse.uml2.DeltaDeletedPort;
import org.eclipse.uml2.DeltaDeletedTrace;
import org.eclipse.uml2.DeltaDeletedConstituent;
import org.eclipse.uml2.DeltaReplacedConstituent;
import org.eclipse.uml2.DeltaReplacedAttribute;
import org.eclipse.uml2.DeltaReplacedConnector;
import org.eclipse.uml2.DeltaReplacedOperation;
import org.eclipse.uml2.DeltaReplacedPort;
import org.eclipse.uml2.DeltaReplacedTrace;
import org.eclipse.uml2.EncapsulatedClassifier;
import org.eclipse.uml2.Extension;
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.Operation;
import org.eclipse.uml2.Port;
import org.eclipse.uml2.Property;
import org.eclipse.uml2.Reception;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.StructuredClassifier;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.Type;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.UnionEObjectEList;

import org.eclipse.uml2.internal.operation.ClassOperations;
import org.eclipse.uml2.internal.operation.StructuredClassifierOperations;
import org.eclipse.uml2.internal.operation.TypeOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getOwnedAttributes <em>Owned Attribute</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getParts <em>Part</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getOwnedConnectors <em>Owned Connector</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getDeltaDeletedAttributes <em>Delta Deleted Attributes</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getDeltaReplacedAttributes <em>Delta Replaced Attributes</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getDeltaDeletedPorts <em>Delta Deleted Ports</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getDeltaReplacedPorts <em>Delta Replaced Ports</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getDeltaDeletedConnectors <em>Delta Deleted Connectors</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getDeltaReplacedConnectors <em>Delta Replaced Connectors</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getDeltaDeletedOperations <em>Delta Deleted Operations</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getDeltaReplacedOperations <em>Delta Replaced Operations</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getDeltaDeletedTraces <em>Delta Deleted Traces</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getDeltaReplacedTraces <em>Delta Replaced Traces</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getOwnedPorts <em>Owned Port</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getOwnedOperations <em>Owned Operation</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getSuperClasses <em>Super Class</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getExtensions <em>Extension</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getNestedClassifiers <em>Nested Classifier</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#isActive <em>Is Active</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getOwnedReceptions <em>Owned Reception</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#getComponentKind <em>Component Kind</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.ClassImpl#isAbstract <em>Is Abstract</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClassImpl extends BehavioredClassifierImpl implements org.eclipse.uml2.Class {
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
	 * The cached value of the '{@link #getOwnedConnectors() <em>Owned Connector</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedConnectors()
	 * @generated
	 * @ordered
	 */
	protected EList ownedConnector = null;

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
	 * The cached value of the '{@link #getDeltaDeletedPorts() <em>Delta Deleted Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltaDeletedPorts()
	 * @generated
	 * @ordered
	 */
	protected EList deltaDeletedPorts = null;

	/**
	 * The cached value of the '{@link #getDeltaReplacedPorts() <em>Delta Replaced Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltaReplacedPorts()
	 * @generated
	 * @ordered
	 */
	protected EList deltaReplacedPorts = null;

	/**
	 * The cached value of the '{@link #getDeltaDeletedConnectors() <em>Delta Deleted Connectors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltaDeletedConnectors()
	 * @generated
	 * @ordered
	 */
	protected EList deltaDeletedConnectors = null;

	/**
	 * The cached value of the '{@link #getDeltaReplacedConnectors() <em>Delta Replaced Connectors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltaReplacedConnectors()
	 * @generated
	 * @ordered
	 */
	protected EList deltaReplacedConnectors = null;

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
	 * The cached value of the '{@link #getDeltaDeletedTraces() <em>Delta Deleted Traces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltaDeletedTraces()
	 * @generated
	 * @ordered
	 */
	protected EList deltaDeletedTraces = null;

	/**
	 * The cached value of the '{@link #getDeltaReplacedTraces() <em>Delta Replaced Traces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltaReplacedTraces()
	 * @generated
	 * @ordered
	 */
	protected EList deltaReplacedTraces = null;

	/**
	 * The cached value of the '{@link #getOwnedPorts() <em>Owned Port</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedPorts()
	 * @generated
	 * @ordered
	 */
	protected EList ownedPort = null;

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
	 * The cached value of the '{@link #getNestedClassifiers() <em>Nested Classifier</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNestedClassifiers()
	 * @generated
	 * @ordered
	 */
	protected EList nestedClassifier = null;

	/**
	 * The default value of the '{@link #isActive() <em>Is Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_ACTIVE_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isActive() <em>Is Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActive()
	 * @generated
	 * @ordered
	 */
	protected static final int IS_ACTIVE_EFLAG = 1 << 11;

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
	 * The default value of the '{@link #getComponentKind() <em>Component Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentKind()
	 * @generated
	 * @ordered
	 */
	protected static final ComponentKind COMPONENT_KIND_EDEFAULT = ComponentKind.NONE_LITERAL;

	/**
	 * The cached value of the '{@link #getComponentKind() <em>Component Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentKind()
	 * @generated
	 * @ordered
	 */
	protected ComponentKind componentKind = COMPONENT_KIND_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassImpl()
	{
		super();
		
		if (eAdapters().size() == 0)
			eAdapters().add(com.hopstepjump.notifications.GlobalNotifier.getSingleton());
		if (ClassImpl.class.equals(getClass()))
			com.hopstepjump.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass()
	{
		return UML2Package.eINSTANCE.getClass_();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getOwnedAttributes() {
		if (ownedAttribute == null) {
      if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
      {
        ownedAttribute = new EObjectContainmentEList(Property.class, this, UML2Package.CLASS__OWNED_ATTRIBUTE);
      }
		}
		return ownedAttribute;
	}


	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedAttributes()
	{
		if (ownedAttribute == null)
		{
			
		
			ownedAttribute = new com.hopstepjump.emflist.PersistentEList(Property.class, this, UML2Package.CLASS__OWNED_ATTRIBUTE, UML2Package.PROPERTY__CLASS_);
		}
		return ownedAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedAttributes()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedAttribute != null)
		{
			for (Object object : ownedAttribute)
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__OWNED_ATTRIBUTE, null, newOwnedAttribute));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__OWNED_ATTRIBUTE, null, newOwnedAttribute));
		}
		settable_getOwnedAttributes().add(newOwnedAttribute);
		return newOwnedAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getParts() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			EList result = (EList) cache.get(eResource(), this,
				UML2Package.eINSTANCE.getStructuredClassifier_Part());

			if (result == null) {
				EList parts = StructuredClassifierOperations.getParts(this);
				cache.put(eResource(), this, UML2Package.eINSTANCE
					.getStructuredClassifier_Part(),
					result = new EcoreEList.UnmodifiableEList(this,
						UML2Package.eINSTANCE.getStructuredClassifier_Part(),
						parts.size(), parts.toArray()));
			}

			return result;
		}

		EList parts = StructuredClassifierOperations.getParts(this);
		return new EcoreEList.UnmodifiableEList(this, UML2Package.eINSTANCE
			.getStructuredClassifier_Part(), parts.size(), parts.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public Property getPart(String name) {
		for (Iterator i = getParts().iterator(); i.hasNext(); ) {
			Property part = (Property) i.next();
			if (name.equals(part.getName())) {
				return part;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getRoles()
	{
		CacheAdapter cache = getCacheAdapter();
		if (cache != null) {
			EList role = (EList) cache.get(eResource(), this, UML2Package.eINSTANCE.getStructuredClassifier_Role());
			if (role == null) {
				EList union = getRolesHelper(new UniqueEList());
				cache.put(eResource(), this, UML2Package.eINSTANCE.getStructuredClassifier_Role(), role = new UnionEObjectEList(this, UML2Package.eINSTANCE.getStructuredClassifier_Role(), union.size(), union.toArray()));
			}
			return role;
		}
		EList union = getRolesHelper(new UniqueEList());
		return new UnionEObjectEList(this, UML2Package.eINSTANCE.getStructuredClassifier_Role(), union.size(), union.toArray());
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public ConnectableElement getRole(String name) {
		for (Iterator i = getRoles().iterator(); i.hasNext(); ) {
			ConnectableElement role = (ConnectableElement) i.next();
			if (name.equals(role.getName())) {
				return role;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getAttributesHelper(EList attribute)
	{
		super.getAttributesHelper(attribute);
		if (eIsSet(UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute())) {
			attribute.addAll(getOwnedAttributes());
		}
		return attribute;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getOwnedMembersHelper(EList ownedMember)
	{
		super.getOwnedMembersHelper(ownedMember);
		if (eIsSet(UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute())) {
			ownedMember.addAll(getOwnedAttributes());
		}
		if (eIsSet(UML2Package.eINSTANCE.getStructuredClassifier_OwnedConnector())) {
			ownedMember.addAll(getOwnedConnectors());
		}
		if (eIsSet(UML2Package.eINSTANCE.getEncapsulatedClassifier_OwnedPort())) {
			ownedMember.addAll(getOwnedPorts());
		}
		if (eIsSet(UML2Package.eINSTANCE.getClass_OwnedOperation())) {
			ownedMember.addAll(getOwnedOperations());
		}
		if (eIsSet(UML2Package.eINSTANCE.getClass_NestedClassifier())) {
			ownedMember.addAll(getNestedClassifiers());
		}
		if (eIsSet(UML2Package.eINSTANCE.getClass_OwnedReception())) {
			ownedMember.addAll(getOwnedReceptions());
		}
		return ownedMember;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getMembersHelper(EList member)
	{
		super.getMembersHelper(member);
		EList role = getRoles();
		if (!role.isEmpty()) {
			for (Iterator i = ((InternalEList) role).basicIterator(); i.hasNext(); ) {
				member.add(i.next());
			}
		}
		return member;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getFeaturesHelper(EList feature)
	{
		super.getFeaturesHelper(feature);
		if (eIsSet(UML2Package.eINSTANCE.getStructuredClassifier_OwnedConnector())) {
			feature.addAll(getOwnedConnectors());
		}
		if (eIsSet(UML2Package.eINSTANCE.getEncapsulatedClassifier_OwnedPort())) {
			feature.addAll(getOwnedPorts());
		}
		if (eIsSet(UML2Package.eINSTANCE.getClass_OwnedOperation())) {
			feature.addAll(getOwnedOperations());
		}
		if (eIsSet(UML2Package.eINSTANCE.getClass_OwnedReception())) {
			feature.addAll(getOwnedReceptions());
		}
		return feature;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedConnectors()
	{
		if (ownedConnector == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		ownedConnector = new com.hopstepjump.emflist.PersistentEList(Connector.class, this, UML2Package.CLASS__OWNED_CONNECTOR);
			 		return ownedConnector;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(Connector.class, this, UML2Package.CLASS__OWNED_CONNECTOR);
		}      
		return ownedConnector;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedConnectors()
	{
		if (ownedConnector == null)
		{
			
		
			ownedConnector = new com.hopstepjump.emflist.PersistentEList(Connector.class, this, UML2Package.CLASS__OWNED_CONNECTOR);
		}
		return ownedConnector;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedConnectors()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedConnector != null)
		{
			for (Object object : ownedConnector)
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
    public Connector getOwnedConnector(String name) {
		for (Iterator i = getOwnedConnectors().iterator(); i.hasNext(); ) {
			Connector ownedConnector = (Connector) i.next();
			if (name.equals(ownedConnector.getName())) {
				return ownedConnector;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaDeletedAttributes()
	{
		if (deltaDeletedAttributes == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		deltaDeletedAttributes = new com.hopstepjump.emflist.PersistentEList(DeltaDeletedAttribute.class, this, UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES);
			 		return deltaDeletedAttributes;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(DeltaDeletedAttribute.class, this, UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES);
		}      
		return deltaDeletedAttributes;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaDeletedAttributes()
	{
		if (deltaDeletedAttributes == null)
		{
			
		
			deltaDeletedAttributes = new com.hopstepjump.emflist.PersistentEList(DeltaDeletedAttribute.class, this, UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES);
		}
		return deltaDeletedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaDeletedAttributes()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaDeletedAttributes != null)
		{
			for (Object object : deltaDeletedAttributes)
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
	public DeltaDeletedAttribute createDeltaDeletedAttributes(EClass eClass) {
		DeltaDeletedAttribute newDeltaDeletedAttributes = (DeltaDeletedAttribute) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES, null, newDeltaDeletedAttributes));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES, null, newDeltaDeletedAttributes));
		}
		settable_getDeltaDeletedAttributes().add(newDeltaDeletedAttributes);
		return newDeltaDeletedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaReplacedAttributes()
	{
		if (deltaReplacedAttributes == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		deltaReplacedAttributes = new com.hopstepjump.emflist.PersistentEList(DeltaReplacedAttribute.class, this, UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES);
			 		return deltaReplacedAttributes;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(DeltaReplacedAttribute.class, this, UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES);
		}      
		return deltaReplacedAttributes;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaReplacedAttributes()
	{
		if (deltaReplacedAttributes == null)
		{
			
		
			deltaReplacedAttributes = new com.hopstepjump.emflist.PersistentEList(DeltaReplacedAttribute.class, this, UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES);
		}
		return deltaReplacedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaReplacedAttributes()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaReplacedAttributes != null)
		{
			for (Object object : deltaReplacedAttributes)
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
	public DeltaReplacedAttribute createDeltaReplacedAttributes(EClass eClass) {
		DeltaReplacedAttribute newDeltaReplacedAttributes = (DeltaReplacedAttribute) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES, null, newDeltaReplacedAttributes));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES, null, newDeltaReplacedAttributes));
		}
		settable_getDeltaReplacedAttributes().add(newDeltaReplacedAttributes);
		return newDeltaReplacedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaDeletedPorts()
	{
		if (deltaDeletedPorts == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		deltaDeletedPorts = new com.hopstepjump.emflist.PersistentEList(DeltaDeletedPort.class, this, UML2Package.CLASS__DELTA_DELETED_PORTS);
			 		return deltaDeletedPorts;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(DeltaDeletedPort.class, this, UML2Package.CLASS__DELTA_DELETED_PORTS);
		}      
		return deltaDeletedPorts;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaDeletedPorts()
	{
		if (deltaDeletedPorts == null)
		{
			
		
			deltaDeletedPorts = new com.hopstepjump.emflist.PersistentEList(DeltaDeletedPort.class, this, UML2Package.CLASS__DELTA_DELETED_PORTS);
		}
		return deltaDeletedPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaDeletedPorts()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaDeletedPorts != null)
		{
			for (Object object : deltaDeletedPorts)
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
	public DeltaDeletedPort createDeltaDeletedPorts(EClass eClass) {
		DeltaDeletedPort newDeltaDeletedPorts = (DeltaDeletedPort) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_DELETED_PORTS, null, newDeltaDeletedPorts));
		}
		settable_getDeltaDeletedPorts().add(newDeltaDeletedPorts);
		return newDeltaDeletedPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaDeletedPort createDeltaDeletedPorts() {
		DeltaDeletedPort newDeltaDeletedPorts = UML2Factory.eINSTANCE.createDeltaDeletedPort();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_DELETED_PORTS, null, newDeltaDeletedPorts));
		}
		settable_getDeltaDeletedPorts().add(newDeltaDeletedPorts);
		return newDeltaDeletedPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaReplacedPorts()
	{
		if (deltaReplacedPorts == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		deltaReplacedPorts = new com.hopstepjump.emflist.PersistentEList(DeltaReplacedPort.class, this, UML2Package.CLASS__DELTA_REPLACED_PORTS);
			 		return deltaReplacedPorts;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(DeltaReplacedPort.class, this, UML2Package.CLASS__DELTA_REPLACED_PORTS);
		}      
		return deltaReplacedPorts;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaReplacedPorts()
	{
		if (deltaReplacedPorts == null)
		{
			
		
			deltaReplacedPorts = new com.hopstepjump.emflist.PersistentEList(DeltaReplacedPort.class, this, UML2Package.CLASS__DELTA_REPLACED_PORTS);
		}
		return deltaReplacedPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaReplacedPorts()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaReplacedPorts != null)
		{
			for (Object object : deltaReplacedPorts)
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
	public DeltaReplacedPort createDeltaReplacedPorts(EClass eClass) {
		DeltaReplacedPort newDeltaReplacedPorts = (DeltaReplacedPort) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_REPLACED_PORTS, null, newDeltaReplacedPorts));
		}
		settable_getDeltaReplacedPorts().add(newDeltaReplacedPorts);
		return newDeltaReplacedPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaReplacedPort createDeltaReplacedPorts() {
		DeltaReplacedPort newDeltaReplacedPorts = UML2Factory.eINSTANCE.createDeltaReplacedPort();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_REPLACED_PORTS, null, newDeltaReplacedPorts));
		}
		settable_getDeltaReplacedPorts().add(newDeltaReplacedPorts);
		return newDeltaReplacedPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaDeletedConnectors()
	{
		if (deltaDeletedConnectors == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		deltaDeletedConnectors = new com.hopstepjump.emflist.PersistentEList(DeltaDeletedConnector.class, this, UML2Package.CLASS__DELTA_DELETED_CONNECTORS);
			 		return deltaDeletedConnectors;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(DeltaDeletedConnector.class, this, UML2Package.CLASS__DELTA_DELETED_CONNECTORS);
		}      
		return deltaDeletedConnectors;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaDeletedConnectors()
	{
		if (deltaDeletedConnectors == null)
		{
			
		
			deltaDeletedConnectors = new com.hopstepjump.emflist.PersistentEList(DeltaDeletedConnector.class, this, UML2Package.CLASS__DELTA_DELETED_CONNECTORS);
		}
		return deltaDeletedConnectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaDeletedConnectors()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaDeletedConnectors != null)
		{
			for (Object object : deltaDeletedConnectors)
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
	public DeltaDeletedConnector createDeltaDeletedConnectors(EClass eClass) {
		DeltaDeletedConnector newDeltaDeletedConnectors = (DeltaDeletedConnector) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_DELETED_CONNECTORS, null, newDeltaDeletedConnectors));
		}
		settable_getDeltaDeletedConnectors().add(newDeltaDeletedConnectors);
		return newDeltaDeletedConnectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaDeletedConnector createDeltaDeletedConnectors() {
		DeltaDeletedConnector newDeltaDeletedConnectors = UML2Factory.eINSTANCE.createDeltaDeletedConnector();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_DELETED_CONNECTORS, null, newDeltaDeletedConnectors));
		}
		settable_getDeltaDeletedConnectors().add(newDeltaDeletedConnectors);
		return newDeltaDeletedConnectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaReplacedConnectors()
	{
		if (deltaReplacedConnectors == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		deltaReplacedConnectors = new com.hopstepjump.emflist.PersistentEList(DeltaReplacedConnector.class, this, UML2Package.CLASS__DELTA_REPLACED_CONNECTORS);
			 		return deltaReplacedConnectors;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(DeltaReplacedConnector.class, this, UML2Package.CLASS__DELTA_REPLACED_CONNECTORS);
		}      
		return deltaReplacedConnectors;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaReplacedConnectors()
	{
		if (deltaReplacedConnectors == null)
		{
			
		
			deltaReplacedConnectors = new com.hopstepjump.emflist.PersistentEList(DeltaReplacedConnector.class, this, UML2Package.CLASS__DELTA_REPLACED_CONNECTORS);
		}
		return deltaReplacedConnectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaReplacedConnectors()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaReplacedConnectors != null)
		{
			for (Object object : deltaReplacedConnectors)
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
	public DeltaReplacedConnector createDeltaReplacedConnectors(EClass eClass) {
		DeltaReplacedConnector newDeltaReplacedConnectors = (DeltaReplacedConnector) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_REPLACED_CONNECTORS, null, newDeltaReplacedConnectors));
		}
		settable_getDeltaReplacedConnectors().add(newDeltaReplacedConnectors);
		return newDeltaReplacedConnectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaReplacedConnector createDeltaReplacedConnectors() {
		DeltaReplacedConnector newDeltaReplacedConnectors = UML2Factory.eINSTANCE.createDeltaReplacedConnector();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_REPLACED_CONNECTORS, null, newDeltaReplacedConnectors));
		}
		settable_getDeltaReplacedConnectors().add(newDeltaReplacedConnectors);
		return newDeltaReplacedConnectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaDeletedOperations()
	{
		if (deltaDeletedOperations == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		deltaDeletedOperations = new com.hopstepjump.emflist.PersistentEList(DeltaDeletedOperation.class, this, UML2Package.CLASS__DELTA_DELETED_OPERATIONS);
			 		return deltaDeletedOperations;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(DeltaDeletedOperation.class, this, UML2Package.CLASS__DELTA_DELETED_OPERATIONS);
		}      
		return deltaDeletedOperations;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaDeletedOperations()
	{
		if (deltaDeletedOperations == null)
		{
			
		
			deltaDeletedOperations = new com.hopstepjump.emflist.PersistentEList(DeltaDeletedOperation.class, this, UML2Package.CLASS__DELTA_DELETED_OPERATIONS);
		}
		return deltaDeletedOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaDeletedOperations()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaDeletedOperations != null)
		{
			for (Object object : deltaDeletedOperations)
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
	public DeltaDeletedOperation createDeltaDeletedOperations(EClass eClass) {
		DeltaDeletedOperation newDeltaDeletedOperations = (DeltaDeletedOperation) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_DELETED_OPERATIONS, null, newDeltaDeletedOperations));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_DELETED_OPERATIONS, null, newDeltaDeletedOperations));
		}
		settable_getDeltaDeletedOperations().add(newDeltaDeletedOperations);
		return newDeltaDeletedOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaReplacedOperations()
	{
		if (deltaReplacedOperations == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		deltaReplacedOperations = new com.hopstepjump.emflist.PersistentEList(DeltaReplacedOperation.class, this, UML2Package.CLASS__DELTA_REPLACED_OPERATIONS);
			 		return deltaReplacedOperations;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(DeltaReplacedOperation.class, this, UML2Package.CLASS__DELTA_REPLACED_OPERATIONS);
		}      
		return deltaReplacedOperations;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaReplacedOperations()
	{
		if (deltaReplacedOperations == null)
		{
			
		
			deltaReplacedOperations = new com.hopstepjump.emflist.PersistentEList(DeltaReplacedOperation.class, this, UML2Package.CLASS__DELTA_REPLACED_OPERATIONS);
		}
		return deltaReplacedOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaReplacedOperations()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaReplacedOperations != null)
		{
			for (Object object : deltaReplacedOperations)
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
	public DeltaReplacedOperation createDeltaReplacedOperations(EClass eClass) {
		DeltaReplacedOperation newDeltaReplacedOperations = (DeltaReplacedOperation) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_REPLACED_OPERATIONS, null, newDeltaReplacedOperations));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_REPLACED_OPERATIONS, null, newDeltaReplacedOperations));
		}
		settable_getDeltaReplacedOperations().add(newDeltaReplacedOperations);
		return newDeltaReplacedOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaDeletedTraces() {
		if (deltaDeletedTraces == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		deltaDeletedTraces = new com.hopstepjump.emflist.PersistentEList(DeltaDeletedTrace.class, this, UML2Package.CLASS__DELTA_DELETED_TRACES);
			 		return deltaDeletedTraces;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(DeltaDeletedTrace.class, this, UML2Package.CLASS__DELTA_DELETED_TRACES);
		}      
		return deltaDeletedTraces;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaDeletedTraces() {
		if (deltaDeletedTraces == null) {
			deltaDeletedTraces = new com.hopstepjump.emflist.PersistentEList(DeltaDeletedTrace.class, this, UML2Package.CLASS__DELTA_DELETED_TRACES);
		}
		return deltaDeletedTraces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaDeletedTraces() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaDeletedTraces != null) {
			for (Object object : deltaDeletedTraces) {
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
	public DeltaDeletedTrace createDeltaDeletedTraces(EClass eClass) {
		DeltaDeletedTrace newDeltaDeletedTraces = (DeltaDeletedTrace) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_DELETED_TRACES, null, newDeltaDeletedTraces));
		}
		settable_getDeltaDeletedTraces().add(newDeltaDeletedTraces);
		return newDeltaDeletedTraces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaDeletedTrace createDeltaDeletedTraces() {
		DeltaDeletedTrace newDeltaDeletedTraces = UML2Factory.eINSTANCE.createDeltaDeletedTrace();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_DELETED_TRACES, null, newDeltaDeletedTraces));
		}
		settable_getDeltaDeletedTraces().add(newDeltaDeletedTraces);
		return newDeltaDeletedTraces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaReplacedTraces() {
		if (deltaReplacedTraces == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		deltaReplacedTraces = new com.hopstepjump.emflist.PersistentEList(DeltaReplacedTrace.class, this, UML2Package.CLASS__DELTA_REPLACED_TRACES);
			 		return deltaReplacedTraces;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(DeltaReplacedTrace.class, this, UML2Package.CLASS__DELTA_REPLACED_TRACES);
		}      
		return deltaReplacedTraces;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaReplacedTraces() {
		if (deltaReplacedTraces == null) {
			deltaReplacedTraces = new com.hopstepjump.emflist.PersistentEList(DeltaReplacedTrace.class, this, UML2Package.CLASS__DELTA_REPLACED_TRACES);
		}
		return deltaReplacedTraces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaReplacedTraces() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaReplacedTraces != null) {
			for (Object object : deltaReplacedTraces) {
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
	public DeltaReplacedTrace createDeltaReplacedTraces(EClass eClass) {
		DeltaReplacedTrace newDeltaReplacedTraces = (DeltaReplacedTrace) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_REPLACED_TRACES, null, newDeltaReplacedTraces));
		}
		settable_getDeltaReplacedTraces().add(newDeltaReplacedTraces);
		return newDeltaReplacedTraces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeltaReplacedTrace createDeltaReplacedTraces() {
		DeltaReplacedTrace newDeltaReplacedTraces = UML2Factory.eINSTANCE.createDeltaReplacedTrace();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__DELTA_REPLACED_TRACES, null, newDeltaReplacedTraces));
		}
		settable_getDeltaReplacedTraces().add(newDeltaReplacedTraces);
		return newDeltaReplacedTraces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createOwnedConnector() instead.
	 */
	public Connector createOwnedConnector(EClass eClass) {
		Connector newOwnedConnector = (Connector) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__OWNED_CONNECTOR, null, newOwnedConnector));
		}
		getOwnedConnectors().add(newOwnedConnector);
		return newOwnedConnector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Connector createOwnedConnector() {
		Connector newOwnedConnector = UML2Factory.eINSTANCE.createConnector();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__OWNED_CONNECTOR, null, newOwnedConnector));
		}
		settable_getOwnedConnectors().add(newOwnedConnector);
		return newOwnedConnector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedPorts()
	{
		if (ownedPort == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		ownedPort = new com.hopstepjump.emflist.PersistentEList(Port.class, this, UML2Package.CLASS__OWNED_PORT);
			 		return ownedPort;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(Port.class, this, UML2Package.CLASS__OWNED_PORT);
		}      
		return ownedPort;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedPorts()
	{
		if (ownedPort == null)
		{
			
		
			ownedPort = new com.hopstepjump.emflist.PersistentEList(Port.class, this, UML2Package.CLASS__OWNED_PORT);
		}
		return ownedPort;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedPorts()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedPort != null)
		{
			for (Object object : ownedPort)
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
    public Port getOwnedPort(String name) {
		for (Iterator i = getOwnedPorts().iterator(); i.hasNext(); ) {
			Port ownedPort = (Port) i.next();
			if (name.equals(ownedPort.getName())) {
				return ownedPort;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @deprecated Use #createOwnedPort() instead.
	 */
	public Port createOwnedPort(EClass eClass) {
		Port newOwnedPort = (Port) eClass.getEPackage().getEFactoryInstance().create(eClass);
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__OWNED_PORT, null, newOwnedPort));
		}
		getOwnedPorts().add(newOwnedPort);
		return newOwnedPort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port createOwnedPort() {
		Port newOwnedPort = UML2Factory.eINSTANCE.createPort();
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__OWNED_PORT, null, newOwnedPort));
		}
		settable_getOwnedPorts().add(newOwnedPort);
		return newOwnedPort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isActive()
	{
		return (eFlags & IS_ACTIVE_EFLAG) != 0;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsActive(boolean newIsActive)
	{

		boolean oldIsActive = (eFlags & IS_ACTIVE_EFLAG) != 0;
		if (newIsActive) eFlags |= IS_ACTIVE_EFLAG; else eFlags &= ~IS_ACTIVE_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.CLASS__IS_ACTIVE, oldIsActive, newIsActive));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedOperations()
	{
		if (ownedOperation == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		ownedOperation = new com.hopstepjump.emflist.PersistentEList(Operation.class, this, UML2Package.CLASS__OWNED_OPERATION, UML2Package.OPERATION__CLASS_);
			 		return ownedOperation;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(Operation.class, this, UML2Package.CLASS__OWNED_OPERATION, UML2Package.OPERATION__CLASS_);
		}      
		return ownedOperation;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedOperations()
	{
		if (ownedOperation == null)
		{
			
		
			ownedOperation = new com.hopstepjump.emflist.PersistentEList(Operation.class, this, UML2Package.CLASS__OWNED_OPERATION, UML2Package.OPERATION__CLASS_);
		}
		return ownedOperation;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedOperations()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedOperation != null)
		{
			for (Object object : ownedOperation)
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__OWNED_OPERATION, null, newOwnedOperation));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__OWNED_OPERATION, null, newOwnedOperation));
		}
		settable_getOwnedOperations().add(newOwnedOperation);
		return newOwnedOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getSuperClasses() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			EList result = (EList) cache.get(eResource(), this,
				UML2Package.eINSTANCE.getClass_SuperClass());

			if (result == null) {
				EList superClasses = ClassOperations.getSuperClasses(this);
				cache.put(eResource(), this, UML2Package.eINSTANCE
					.getClass_SuperClass(),
					result = new EcoreEList.UnmodifiableEList(this,
						UML2Package.eINSTANCE.getClass_SuperClass(),
						superClasses.size(), superClasses.toArray()));
			}

			return result;
		}

		EList superClasses = ClassOperations.getSuperClasses(this);
		return new EcoreEList.UnmodifiableEList(this, UML2Package.eINSTANCE
			.getClass_SuperClass(), superClasses.size(), superClasses.toArray());
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public org.eclipse.uml2.Class getSuperClass(String name) {
		for (Iterator i = getSuperClasses().iterator(); i.hasNext(); ) {
			org.eclipse.uml2.Class superClass = (org.eclipse.uml2.Class) i.next();
			if (name.equals(superClass.getName())) {
				return superClass;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getExtensions() {
		CacheAdapter cache = getCacheAdapter();

		if (cache != null) {
			EList result = (EList) cache.get(this, UML2Package.eINSTANCE
				.getClass_Extension());

			if (result == null) {
				Set extensions = ClassOperations.getExtensions(this);
				cache.put(this, UML2Package.eINSTANCE.getClass_Extension(),
					result = new EcoreEList.UnmodifiableEList(this,
						UML2Package.eINSTANCE.getClass_Extension(), extensions
							.size(), extensions.toArray()));
			}

			return result;
		}

		Set extensions = ClassOperations.getExtensions(this);
		return new EcoreEList.UnmodifiableEList(this, UML2Package.eINSTANCE
			.getClass_Extension(), extensions.size(), extensions.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
    public Extension getExtension(String name) {
		for (Iterator i = getExtensions().iterator(); i.hasNext(); ) {
			Extension extension = (Extension) i.next();
			if (name.equals(extension.getName())) {
				return extension;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getNestedClassifiers()
	{
		if (nestedClassifier == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		nestedClassifier = new com.hopstepjump.emflist.PersistentEList(Classifier.class, this, UML2Package.CLASS__NESTED_CLASSIFIER);
			 		return nestedClassifier;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(Classifier.class, this, UML2Package.CLASS__NESTED_CLASSIFIER);
		}      
		return nestedClassifier;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getNestedClassifiers()
	{
		if (nestedClassifier == null)
		{
			
		
			nestedClassifier = new com.hopstepjump.emflist.PersistentEList(Classifier.class, this, UML2Package.CLASS__NESTED_CLASSIFIER);
		}
		return nestedClassifier;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getNestedClassifiers()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (nestedClassifier != null)
		{
			for (Object object : nestedClassifier)
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__NESTED_CLASSIFIER, null, newNestedClassifier));
		}
		settable_getNestedClassifiers().add(newNestedClassifier);
		return newNestedClassifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedReceptions()
	{
		if (ownedReception == null)
		{
			
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			 {
			 		// create the list lazily...
			 		ownedReception = new com.hopstepjump.emflist.PersistentEList(Reception.class, this, UML2Package.CLASS__OWNED_RECEPTION);
			 		return ownedReception;
			 }
			return new com.hopstepjump.emflist.UnmodifiableEList(Reception.class, this, UML2Package.CLASS__OWNED_RECEPTION);
		}      
		return ownedReception;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedReceptions()
	{
		if (ownedReception == null)
		{
			
		
			ownedReception = new com.hopstepjump.emflist.PersistentEList(Reception.class, this, UML2Package.CLASS__OWNED_RECEPTION);
		}
		return ownedReception;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedReceptions()
	{
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedReception != null)
		{
			for (Object object : ownedReception)
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
	 * @generated
	 */
	public ComponentKind getComponentKind()
	{
		return componentKind;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentKind(ComponentKind newComponentKind)
	{

		ComponentKind oldComponentKind = componentKind;
		componentKind = newComponentKind == null ? COMPONENT_KIND_EDEFAULT : newComponentKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.CLASS__COMPONENT_KIND, oldComponentKind, componentKind));

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
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__OWNED_RECEPTION, null, newOwnedReception));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.CLASS__OWNED_RECEPTION, null, newOwnedReception));
		}
		settable_getOwnedReceptions().add(newOwnedReception);
		return newOwnedReception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Set inherit(Set inhs)
	{
		return ClassOperations.inherit(this, inhs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAbstract()
	{
		return (eFlags & IS_ABSTRACT_EFLAG) != 0;
	}

	
	




	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsAbstract(boolean newIsAbstract)
	{

		boolean oldIsAbstract = (eFlags & IS_ABSTRACT_EFLAG) != 0;
		if (newIsAbstract) eFlags |= IS_ABSTRACT_EFLAG; else eFlags &= ~IS_ABSTRACT_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UML2Package.CLASS__IS_ABSTRACT, oldIsAbstract, newIsAbstract));

	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getGenerals()
	{
		return getSuperClasses();
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
				case UML2Package.CLASS__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.CLASS__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.CLASS__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.CLASS__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicAdd(otherEnd, msgs);
				case UML2Package.CLASS__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicAdd(otherEnd, msgs);
				case UML2Package.CLASS__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicAdd(otherEnd, msgs);
				case UML2Package.CLASS__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.CLASS__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.CLASS__OWNING_PARAMETER, msgs);
				case UML2Package.CLASS__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicAdd(otherEnd, msgs);
				case UML2Package.CLASS__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicAdd(otherEnd, msgs);
				case UML2Package.CLASS__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicAdd(otherEnd, msgs);
				case UML2Package.CLASS__USE_CASE:
					return ((InternalEList)getUseCases()).basicAdd(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_BEHAVIOR:
					return ((InternalEList)getOwnedBehaviors()).basicAdd(otherEnd, msgs);
				case UML2Package.CLASS__IMPLEMENTATION:
					return ((InternalEList)getImplementations()).basicAdd(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_OPERATION:
					return ((InternalEList)getOwnedOperations()).basicAdd(otherEnd, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	public NotificationChain eDynamicInverseAdd(InternalEObject otherEnd, int featureID, Class inverseClass, NotificationChain msgs) {
		switch (eDerivedStructuralFeatureID(featureID, inverseClass)) {
			case UML2Package.CLASS__OWNED_STATE_MACHINE:
				return ((InternalEList)getOwnedStateMachines()).basicAdd(otherEnd, msgs);
			default :
				return super.eDynamicInverseAdd(otherEnd, featureID, inverseClass, msgs);
		}
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
				case UML2Package.CLASS__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.CLASS__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.CLASS__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.CLASS__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.CLASS__OWNING_PARAMETER, msgs);
				case UML2Package.CLASS__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_USE_CASE:
					return ((InternalEList)getOwnedUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__USE_CASE:
					return ((InternalEList)getUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OCCURRENCE:
					return ((InternalEList)getOccurrences()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_BEHAVIOR:
					return ((InternalEList)getOwnedBehaviors()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__IMPLEMENTATION:
					return ((InternalEList)getImplementations()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_TRIGGER:
					return ((InternalEList)getOwnedTriggers()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_STATE_MACHINE:
					return ((InternalEList)getOwnedStateMachines()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_ATTRIBUTE:
					return ((InternalEList)getOwnedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_CONNECTOR:
					return ((InternalEList)getOwnedConnectors()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES:
					return ((InternalEList)getDeltaDeletedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES:
					return ((InternalEList)getDeltaReplacedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__DELTA_DELETED_PORTS:
					return ((InternalEList)getDeltaDeletedPorts()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__DELTA_REPLACED_PORTS:
					return ((InternalEList)getDeltaReplacedPorts()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__DELTA_DELETED_CONNECTORS:
					return ((InternalEList)getDeltaDeletedConnectors()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__DELTA_REPLACED_CONNECTORS:
					return ((InternalEList)getDeltaReplacedConnectors()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__DELTA_DELETED_OPERATIONS:
					return ((InternalEList)getDeltaDeletedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__DELTA_REPLACED_OPERATIONS:
					return ((InternalEList)getDeltaReplacedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__DELTA_DELETED_TRACES:
					return ((InternalEList)getDeltaDeletedTraces()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__DELTA_REPLACED_TRACES:
					return ((InternalEList)getDeltaReplacedTraces()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_PORT:
					return ((InternalEList)getOwnedPorts()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_OPERATION:
					return ((InternalEList)getOwnedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__NESTED_CLASSIFIER:
					return ((InternalEList)getNestedClassifiers()).basicRemove(otherEnd, msgs);
				case UML2Package.CLASS__OWNED_RECEPTION:
					return ((InternalEList)getOwnedReceptions()).basicRemove(otherEnd, msgs);
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
				case UML2Package.CLASS__OWNING_PARAMETER:
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.CLASS__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.CLASS__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.CLASS__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.CLASS__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.CLASS__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.CLASS__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.CLASS__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.CLASS__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.CLASS__UUID:
				return getUuid();
			case UML2Package.CLASS__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.CLASS__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.CLASS__NAME:
				return getName();
			case UML2Package.CLASS__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.CLASS__VISIBILITY:
				return getVisibility();
			case UML2Package.CLASS__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.CLASS__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.CLASS__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.CLASS__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.CLASS__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.CLASS__MEMBER:
				return getMembers();
			case UML2Package.CLASS__OWNED_RULE:
				return getOwnedRules();
			case UML2Package.CLASS__IMPORTED_MEMBER:
				return getImportedMembers();
			case UML2Package.CLASS__ELEMENT_IMPORT:
				return getElementImports();
			case UML2Package.CLASS__PACKAGE_IMPORT:
				return getPackageImports();
			case UML2Package.CLASS__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.CLASS__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.CLASS__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.CLASS__PACKAGE:
				if (resolve) return getPackage();
				return basicGetPackage();
			case UML2Package.CLASS__IS_RETIRED:
				return isRetired() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.CLASS__REDEFINITION_CONTEXT:
				return getRedefinitionContexts();
			case UML2Package.CLASS__IS_LEAF:
				return isLeaf() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.CLASS__FEATURE:
				return getFeatures();
			case UML2Package.CLASS__IS_ABSTRACT:
				return isAbstract() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.CLASS__INHERITED_MEMBER:
				return getInheritedMembers();
			case UML2Package.CLASS__GENERAL:
				return getGenerals();
			case UML2Package.CLASS__GENERALIZATION:
				return getGeneralizations();
			case UML2Package.CLASS__ATTRIBUTE:
				return getAttributes();
			case UML2Package.CLASS__REDEFINED_CLASSIFIER:
				return getRedefinedClassifiers();
			case UML2Package.CLASS__SUBSTITUTION:
				return getSubstitutions();
			case UML2Package.CLASS__POWERTYPE_EXTENT:
				return getPowertypeExtents();
			case UML2Package.CLASS__OWNED_USE_CASE:
				return getOwnedUseCases();
			case UML2Package.CLASS__USE_CASE:
				return getUseCases();
			case UML2Package.CLASS__REPRESENTATION:
				return getRepresentation();
			case UML2Package.CLASS__OCCURRENCE:
				return getOccurrences();
			case UML2Package.CLASS__OWNED_BEHAVIOR:
				return getOwnedBehaviors();
			case UML2Package.CLASS__CLASSIFIER_BEHAVIOR:
				return getClassifierBehavior();
			case UML2Package.CLASS__IMPLEMENTATION:
				return getImplementations();
			case UML2Package.CLASS__OWNED_TRIGGER:
				return getOwnedTriggers();
			case UML2Package.CLASS__OWNED_STATE_MACHINE:
				return getOwnedStateMachines();
			case UML2Package.CLASS__OWNED_ATTRIBUTE:
				return getOwnedAttributes();
			case UML2Package.CLASS__PART:
				return getParts();
			case UML2Package.CLASS__ROLE:
				return getRoles();
			case UML2Package.CLASS__OWNED_CONNECTOR:
				return getOwnedConnectors();
			case UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES:
				return getDeltaDeletedAttributes();
			case UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES:
				return getDeltaReplacedAttributes();
			case UML2Package.CLASS__DELTA_DELETED_PORTS:
				return getDeltaDeletedPorts();
			case UML2Package.CLASS__DELTA_REPLACED_PORTS:
				return getDeltaReplacedPorts();
			case UML2Package.CLASS__DELTA_DELETED_CONNECTORS:
				return getDeltaDeletedConnectors();
			case UML2Package.CLASS__DELTA_REPLACED_CONNECTORS:
				return getDeltaReplacedConnectors();
			case UML2Package.CLASS__DELTA_DELETED_OPERATIONS:
				return getDeltaDeletedOperations();
			case UML2Package.CLASS__DELTA_REPLACED_OPERATIONS:
				return getDeltaReplacedOperations();
			case UML2Package.CLASS__DELTA_DELETED_TRACES:
				return getDeltaDeletedTraces();
			case UML2Package.CLASS__DELTA_REPLACED_TRACES:
				return getDeltaReplacedTraces();
			case UML2Package.CLASS__OWNED_PORT:
				return getOwnedPorts();
			case UML2Package.CLASS__OWNED_OPERATION:
				return getOwnedOperations();
			case UML2Package.CLASS__SUPER_CLASS:
				return getSuperClasses();
			case UML2Package.CLASS__EXTENSION:
				return getExtensions();
			case UML2Package.CLASS__NESTED_CLASSIFIER:
				return getNestedClassifiers();
			case UML2Package.CLASS__IS_ACTIVE:
				return isActive() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.CLASS__OWNED_RECEPTION:
				return getOwnedReceptions();
			case UML2Package.CLASS__COMPONENT_KIND:
				return getComponentKind();
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
			case UML2Package.CLASS__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.CLASS__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.CLASS__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.CLASS__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.CLASS__NAME:
				setName((String)newValue);
				return;
			case UML2Package.CLASS__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.CLASS__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.CLASS__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__OWNED_RULE:
				getOwnedRules().clear();
				getOwnedRules().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__ELEMENT_IMPORT:
				getElementImports().clear();
				getElementImports().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__PACKAGE_IMPORT:
				getPackageImports().clear();
				getPackageImports().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.CLASS__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.CLASS__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.CLASS__IS_RETIRED:
				setIsRetired(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.CLASS__IS_LEAF:
				setIsLeaf(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.CLASS__IS_ABSTRACT:
				setIsAbstract(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.CLASS__GENERALIZATION:
				getGeneralizations().clear();
				getGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				getRedefinedClassifiers().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__SUBSTITUTION:
				getSubstitutions().clear();
				getSubstitutions().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				getPowertypeExtents().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				getOwnedUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__USE_CASE:
				getUseCases().clear();
				getUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)newValue);
				return;
			case UML2Package.CLASS__OCCURRENCE:
				getOccurrences().clear();
				getOccurrences().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__OWNED_BEHAVIOR:
				getOwnedBehaviors().clear();
				getOwnedBehaviors().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__CLASSIFIER_BEHAVIOR:
				setClassifierBehavior((Behavior)newValue);
				return;
			case UML2Package.CLASS__IMPLEMENTATION:
				getImplementations().clear();
				getImplementations().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__OWNED_TRIGGER:
				getOwnedTriggers().clear();
				getOwnedTriggers().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__OWNED_STATE_MACHINE:
				getOwnedStateMachines().clear();
				getOwnedStateMachines().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__OWNED_ATTRIBUTE:
				getOwnedAttributes().clear();
				getOwnedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__OWNED_CONNECTOR:
				getOwnedConnectors().clear();
				getOwnedConnectors().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES:
				getDeltaDeletedAttributes().clear();
				getDeltaDeletedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES:
				getDeltaReplacedAttributes().clear();
				getDeltaReplacedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__DELTA_DELETED_PORTS:
				getDeltaDeletedPorts().clear();
				getDeltaDeletedPorts().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__DELTA_REPLACED_PORTS:
				getDeltaReplacedPorts().clear();
				getDeltaReplacedPorts().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__DELTA_DELETED_CONNECTORS:
				getDeltaDeletedConnectors().clear();
				getDeltaDeletedConnectors().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__DELTA_REPLACED_CONNECTORS:
				getDeltaReplacedConnectors().clear();
				getDeltaReplacedConnectors().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__DELTA_DELETED_OPERATIONS:
				getDeltaDeletedOperations().clear();
				getDeltaDeletedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__DELTA_REPLACED_OPERATIONS:
				getDeltaReplacedOperations().clear();
				getDeltaReplacedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__DELTA_DELETED_TRACES:
				getDeltaDeletedTraces().clear();
				getDeltaDeletedTraces().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__DELTA_REPLACED_TRACES:
				getDeltaReplacedTraces().clear();
				getDeltaReplacedTraces().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__OWNED_PORT:
				getOwnedPorts().clear();
				getOwnedPorts().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__OWNED_OPERATION:
				getOwnedOperations().clear();
				getOwnedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__NESTED_CLASSIFIER:
				getNestedClassifiers().clear();
				getNestedClassifiers().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__IS_ACTIVE:
				setIsActive(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.CLASS__OWNED_RECEPTION:
				getOwnedReceptions().clear();
				getOwnedReceptions().addAll((Collection)newValue);
				return;
			case UML2Package.CLASS__COMPONENT_KIND:
				setComponentKind((ComponentKind)newValue);
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
			case UML2Package.CLASS__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.CLASS__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.CLASS__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.CLASS__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.CLASS__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.CLASS__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.CLASS__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.CLASS__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.CLASS__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.CLASS__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.CLASS__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.CLASS__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.CLASS__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.CLASS__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.CLASS__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.CLASS__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.CLASS__OWNED_RULE:
				getOwnedRules().clear();
				return;
			case UML2Package.CLASS__ELEMENT_IMPORT:
				getElementImports().clear();
				return;
			case UML2Package.CLASS__PACKAGE_IMPORT:
				getPackageImports().clear();
				return;
			case UML2Package.CLASS__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.CLASS__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.CLASS__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.CLASS__IS_RETIRED:
				setIsRetired(IS_RETIRED_EDEFAULT);
				return;
			case UML2Package.CLASS__IS_LEAF:
				setIsLeaf(IS_LEAF_EDEFAULT);
				return;
			case UML2Package.CLASS__IS_ABSTRACT:
				setIsAbstract(IS_ABSTRACT_EDEFAULT);
				return;
			case UML2Package.CLASS__GENERALIZATION:
				getGeneralizations().clear();
				return;
			case UML2Package.CLASS__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				return;
			case UML2Package.CLASS__SUBSTITUTION:
				getSubstitutions().clear();
				return;
			case UML2Package.CLASS__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				return;
			case UML2Package.CLASS__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				return;
			case UML2Package.CLASS__USE_CASE:
				getUseCases().clear();
				return;
			case UML2Package.CLASS__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)null);
				return;
			case UML2Package.CLASS__OCCURRENCE:
				getOccurrences().clear();
				return;
			case UML2Package.CLASS__OWNED_BEHAVIOR:
				getOwnedBehaviors().clear();
				return;
			case UML2Package.CLASS__CLASSIFIER_BEHAVIOR:
				setClassifierBehavior((Behavior)null);
				return;
			case UML2Package.CLASS__IMPLEMENTATION:
				getImplementations().clear();
				return;
			case UML2Package.CLASS__OWNED_TRIGGER:
				getOwnedTriggers().clear();
				return;
			case UML2Package.CLASS__OWNED_STATE_MACHINE:
				getOwnedStateMachines().clear();
				return;
			case UML2Package.CLASS__OWNED_ATTRIBUTE:
				getOwnedAttributes().clear();
				return;
			case UML2Package.CLASS__OWNED_CONNECTOR:
				getOwnedConnectors().clear();
				return;
			case UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES:
				getDeltaDeletedAttributes().clear();
				return;
			case UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES:
				getDeltaReplacedAttributes().clear();
				return;
			case UML2Package.CLASS__DELTA_DELETED_PORTS:
				getDeltaDeletedPorts().clear();
				return;
			case UML2Package.CLASS__DELTA_REPLACED_PORTS:
				getDeltaReplacedPorts().clear();
				return;
			case UML2Package.CLASS__DELTA_DELETED_CONNECTORS:
				getDeltaDeletedConnectors().clear();
				return;
			case UML2Package.CLASS__DELTA_REPLACED_CONNECTORS:
				getDeltaReplacedConnectors().clear();
				return;
			case UML2Package.CLASS__DELTA_DELETED_OPERATIONS:
				getDeltaDeletedOperations().clear();
				return;
			case UML2Package.CLASS__DELTA_REPLACED_OPERATIONS:
				getDeltaReplacedOperations().clear();
				return;
			case UML2Package.CLASS__DELTA_DELETED_TRACES:
				getDeltaDeletedTraces().clear();
				return;
			case UML2Package.CLASS__DELTA_REPLACED_TRACES:
				getDeltaReplacedTraces().clear();
				return;
			case UML2Package.CLASS__OWNED_PORT:
				getOwnedPorts().clear();
				return;
			case UML2Package.CLASS__OWNED_OPERATION:
				getOwnedOperations().clear();
				return;
			case UML2Package.CLASS__NESTED_CLASSIFIER:
				getNestedClassifiers().clear();
				return;
			case UML2Package.CLASS__IS_ACTIVE:
				setIsActive(IS_ACTIVE_EDEFAULT);
				return;
			case UML2Package.CLASS__OWNED_RECEPTION:
				getOwnedReceptions().clear();
				return;
			case UML2Package.CLASS__COMPONENT_KIND:
				setComponentKind(COMPONENT_KIND_EDEFAULT);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSetGen(EStructuralFeature eFeature)
	{
		switch (eDerivedStructuralFeatureID(eFeature))
		{
			case UML2Package.CLASS__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.CLASS__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.CLASS__OWNER:
				return basicGetOwner() != null;
			case UML2Package.CLASS__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.CLASS__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.CLASS__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.CLASS__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.CLASS__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.CLASS__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.CLASS__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.CLASS__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.CLASS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.CLASS__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.CLASS__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.CLASS__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.CLASS__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.CLASS__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.CLASS__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.CLASS__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.CLASS__MEMBER:
				return !getMembers().isEmpty();
			case UML2Package.CLASS__OWNED_RULE:
				return ownedRule != null && !ownedRule.isEmpty();
			case UML2Package.CLASS__IMPORTED_MEMBER:
				return !getImportedMembers().isEmpty();
			case UML2Package.CLASS__ELEMENT_IMPORT:
				return elementImport != null && !elementImport.isEmpty();
			case UML2Package.CLASS__PACKAGE_IMPORT:
				return packageImport != null && !packageImport.isEmpty();
			case UML2Package.CLASS__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.CLASS__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.CLASS__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.CLASS__PACKAGE:
				return basicGetPackage() != null;
			case UML2Package.CLASS__IS_RETIRED:
				return ((eFlags & IS_RETIRED_EFLAG) != 0) != IS_RETIRED_EDEFAULT;
			case UML2Package.CLASS__REDEFINITION_CONTEXT:
				return !getRedefinitionContexts().isEmpty();
			case UML2Package.CLASS__IS_LEAF:
				return ((eFlags & IS_LEAF_EFLAG) != 0) != IS_LEAF_EDEFAULT;
			case UML2Package.CLASS__FEATURE:
				return !getFeatures().isEmpty();
			case UML2Package.CLASS__IS_ABSTRACT:
				return isAbstract() != IS_ABSTRACT_EDEFAULT;
			case UML2Package.CLASS__INHERITED_MEMBER:
				return !getInheritedMembers().isEmpty();
			case UML2Package.CLASS__GENERAL:
				return !getGenerals().isEmpty();
			case UML2Package.CLASS__GENERALIZATION:
				return generalization != null && !generalization.isEmpty();
			case UML2Package.CLASS__ATTRIBUTE:
				return !getAttributes().isEmpty();
			case UML2Package.CLASS__REDEFINED_CLASSIFIER:
				return redefinedClassifier != null && !redefinedClassifier.isEmpty();
			case UML2Package.CLASS__SUBSTITUTION:
				return substitution != null && !substitution.isEmpty();
			case UML2Package.CLASS__POWERTYPE_EXTENT:
				return powertypeExtent != null && !powertypeExtent.isEmpty();
			case UML2Package.CLASS__OWNED_USE_CASE:
				return ownedUseCase != null && !ownedUseCase.isEmpty();
			case UML2Package.CLASS__USE_CASE:
				return useCase != null && !useCase.isEmpty();
			case UML2Package.CLASS__REPRESENTATION:
				return representation != null;
			case UML2Package.CLASS__OCCURRENCE:
				return occurrence != null && !occurrence.isEmpty();
			case UML2Package.CLASS__OWNED_BEHAVIOR:
				return !getOwnedBehaviors().isEmpty();
			case UML2Package.CLASS__CLASSIFIER_BEHAVIOR:
				return classifierBehavior != null;
			case UML2Package.CLASS__IMPLEMENTATION:
				return implementation != null && !implementation.isEmpty();
			case UML2Package.CLASS__OWNED_TRIGGER:
				return ownedTrigger != null && !ownedTrigger.isEmpty();
			case UML2Package.CLASS__OWNED_STATE_MACHINE:
				return !getOwnedStateMachines().isEmpty();
			case UML2Package.CLASS__OWNED_ATTRIBUTE:
				return !getOwnedAttributes().isEmpty();
			case UML2Package.CLASS__PART:
				return !getParts().isEmpty();
			case UML2Package.CLASS__ROLE:
				return !getRoles().isEmpty();
			case UML2Package.CLASS__OWNED_CONNECTOR:
				return ownedConnector != null && !ownedConnector.isEmpty();
			case UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES:
				return deltaDeletedAttributes != null && !deltaDeletedAttributes.isEmpty();
			case UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES:
				return deltaReplacedAttributes != null && !deltaReplacedAttributes.isEmpty();
			case UML2Package.CLASS__DELTA_DELETED_PORTS:
				return deltaDeletedPorts != null && !deltaDeletedPorts.isEmpty();
			case UML2Package.CLASS__DELTA_REPLACED_PORTS:
				return deltaReplacedPorts != null && !deltaReplacedPorts.isEmpty();
			case UML2Package.CLASS__DELTA_DELETED_CONNECTORS:
				return deltaDeletedConnectors != null && !deltaDeletedConnectors.isEmpty();
			case UML2Package.CLASS__DELTA_REPLACED_CONNECTORS:
				return deltaReplacedConnectors != null && !deltaReplacedConnectors.isEmpty();
			case UML2Package.CLASS__DELTA_DELETED_OPERATIONS:
				return deltaDeletedOperations != null && !deltaDeletedOperations.isEmpty();
			case UML2Package.CLASS__DELTA_REPLACED_OPERATIONS:
				return deltaReplacedOperations != null && !deltaReplacedOperations.isEmpty();
			case UML2Package.CLASS__DELTA_DELETED_TRACES:
				return deltaDeletedTraces != null && !deltaDeletedTraces.isEmpty();
			case UML2Package.CLASS__DELTA_REPLACED_TRACES:
				return deltaReplacedTraces != null && !deltaReplacedTraces.isEmpty();
			case UML2Package.CLASS__OWNED_PORT:
				return ownedPort != null && !ownedPort.isEmpty();
			case UML2Package.CLASS__OWNED_OPERATION:
				return ownedOperation != null && !ownedOperation.isEmpty();
			case UML2Package.CLASS__SUPER_CLASS:
				return !getSuperClasses().isEmpty();
			case UML2Package.CLASS__EXTENSION:
				return !getExtensions().isEmpty();
			case UML2Package.CLASS__NESTED_CLASSIFIER:
				return nestedClassifier != null && !nestedClassifier.isEmpty();
			case UML2Package.CLASS__IS_ACTIVE:
				return ((eFlags & IS_ACTIVE_EFLAG) != 0) != IS_ACTIVE_EDEFAULT;
			case UML2Package.CLASS__OWNED_RECEPTION:
				return ownedReception != null && !ownedReception.isEmpty();
			case UML2Package.CLASS__COMPONENT_KIND:
				return componentKind != COMPONENT_KIND_EDEFAULT;
		}
		return eDynamicIsSet(eFeature);
	}

	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.CLASS__VISIBILITY:
				return false;
			case UML2Package.CLASS__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.CLASS__OWNED_BEHAVIOR:
				return ownedBehavior != null && !ownedBehavior.isEmpty();
			case UML2Package.CLASS__OWNED_STATE_MACHINE:
				return ownedStateMachine != null && !ownedStateMachine.isEmpty();
			case UML2Package.CLASS__OWNED_ATTRIBUTE:
				return ownedAttribute != null && !ownedAttribute.isEmpty();
		}
		return eIsSetGen(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class baseClass)
	{
		if (baseClass == StructuredClassifier.class)
		{
			switch (derivedFeatureID)
			{
				case UML2Package.CLASS__OWNED_ATTRIBUTE: return UML2Package.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE;
				case UML2Package.CLASS__PART: return UML2Package.STRUCTURED_CLASSIFIER__PART;
				case UML2Package.CLASS__ROLE: return UML2Package.STRUCTURED_CLASSIFIER__ROLE;
				case UML2Package.CLASS__OWNED_CONNECTOR: return UML2Package.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR;
				case UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES: return UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES;
				case UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES: return UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES;
				case UML2Package.CLASS__DELTA_DELETED_PORTS: return UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS;
				case UML2Package.CLASS__DELTA_REPLACED_PORTS: return UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS;
				case UML2Package.CLASS__DELTA_DELETED_CONNECTORS: return UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS;
				case UML2Package.CLASS__DELTA_REPLACED_CONNECTORS: return UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS;
				case UML2Package.CLASS__DELTA_DELETED_OPERATIONS: return UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS;
				case UML2Package.CLASS__DELTA_REPLACED_OPERATIONS: return UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS;
				case UML2Package.CLASS__DELTA_DELETED_TRACES: return UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES;
				case UML2Package.CLASS__DELTA_REPLACED_TRACES: return UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES;
				default: return -1;
			}
		}
		if (baseClass == EncapsulatedClassifier.class)
		{
			switch (derivedFeatureID)
			{
				case UML2Package.CLASS__OWNED_PORT: return UML2Package.ENCAPSULATED_CLASSIFIER__OWNED_PORT;
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
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class baseClass)
	{
		if (baseClass == StructuredClassifier.class)
		{
			switch (baseFeatureID)
			{
				case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE: return UML2Package.CLASS__OWNED_ATTRIBUTE;
				case UML2Package.STRUCTURED_CLASSIFIER__PART: return UML2Package.CLASS__PART;
				case UML2Package.STRUCTURED_CLASSIFIER__ROLE: return UML2Package.CLASS__ROLE;
				case UML2Package.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR: return UML2Package.CLASS__OWNED_CONNECTOR;
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES: return UML2Package.CLASS__DELTA_DELETED_ATTRIBUTES;
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES: return UML2Package.CLASS__DELTA_REPLACED_ATTRIBUTES;
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS: return UML2Package.CLASS__DELTA_DELETED_PORTS;
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS: return UML2Package.CLASS__DELTA_REPLACED_PORTS;
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS: return UML2Package.CLASS__DELTA_DELETED_CONNECTORS;
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS: return UML2Package.CLASS__DELTA_REPLACED_CONNECTORS;
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS: return UML2Package.CLASS__DELTA_DELETED_OPERATIONS;
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS: return UML2Package.CLASS__DELTA_REPLACED_OPERATIONS;
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES: return UML2Package.CLASS__DELTA_DELETED_TRACES;
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES: return UML2Package.CLASS__DELTA_REPLACED_TRACES;
				default: return -1;
			}
		}
		if (baseClass == EncapsulatedClassifier.class)
		{
			switch (baseFeatureID)
			{
				case UML2Package.ENCAPSULATED_CLASSIFIER__OWNED_PORT: return UML2Package.CLASS__OWNED_PORT;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (isActive: "); //$NON-NLS-1$
		result.append((eFlags & IS_ACTIVE_EFLAG) != 0);
		result.append(", componentKind: "); //$NON-NLS-1$
		result.append(componentKind);
		result.append(')');
		return result.toString();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getRolesHelper(EList role)
	{
		EList ownedAttribute = getOwnedAttributes();
		if (!ownedAttribute.isEmpty()) {
			role.addAll(ownedAttribute);
		}
		return role;
	}

	// <!-- begin-custom-operations -->

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Class#isMetaclass()
	 */
	public boolean isMetaclass() {
		return ClassOperations.isMetaclass(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.uml2.Class#createOwnedAttribute(java.lang.String,
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
	 * @see org.eclipse.uml2.Class#createOwnedOperation(java.lang.String,
	 *      org.eclipse.uml2.Type, java.lang.String[], org.eclipse.uml2.Type[])
	 */
	public Operation createOwnedOperation(String name, Type returnType,
			String[] parameterNames, Type[] parameterTypes) {
		return TypeOperations.createOwnedOperation(this, name, returnType,
			parameterNames, parameterTypes);
	}
	
	// <!-- end-custom-operations -->

} //ClassImpl
