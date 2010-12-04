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
 * $Id: StructuredClassifierImpl.java,v 1.1 2009-03-04 23:06:38 andrew Exp $
 */
package org.eclipse.uml2.impl;

import java.util.Collection;
import java.util.Iterator;

import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.uml2.CollaborationOccurrence;
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
import org.eclipse.uml2.Namespace;
import org.eclipse.uml2.Property;
import org.eclipse.uml2.StringExpression;
import org.eclipse.uml2.StructuredClassifier;
import org.eclipse.uml2.TemplateParameter;
import org.eclipse.uml2.TemplateSignature;
import org.eclipse.uml2.UML2Factory;
import org.eclipse.uml2.UML2Package;
import org.eclipse.uml2.VisibilityKind;

import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.UnionEObjectEList;

import org.eclipse.uml2.internal.operation.StructuredClassifierOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Structured Classifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getOwnedAttributes <em>Owned Attribute</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getParts <em>Part</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getOwnedConnectors <em>Owned Connector</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getDeltaDeletedAttributes <em>Delta Deleted Attributes</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getDeltaReplacedAttributes <em>Delta Replaced Attributes</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getDeltaDeletedPorts <em>Delta Deleted Ports</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getDeltaReplacedPorts <em>Delta Replaced Ports</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getDeltaDeletedConnectors <em>Delta Deleted Connectors</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getDeltaReplacedConnectors <em>Delta Replaced Connectors</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getDeltaDeletedOperations <em>Delta Deleted Operations</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getDeltaReplacedOperations <em>Delta Replaced Operations</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getDeltaDeletedTraces <em>Delta Deleted Traces</em>}</li>
 *   <li>{@link org.eclipse.uml2.impl.StructuredClassifierImpl#getDeltaReplacedTraces <em>Delta Replaced Traces</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class StructuredClassifierImpl extends ClassifierImpl implements StructuredClassifier {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StructuredClassifierImpl() {
		super();
		if (eAdapters().size() == 0)
			eAdapters().add(com.intrinsarc.notifications.GlobalNotifier.getSingleton());
		if (StructuredClassifierImpl.class.equals(getClass()) && org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET)
			com.intrinsarc.notifications.GlobalNotifier.getSingleton().notifyChanged(new org.eclipse.emf.common.notify.impl.NotificationImpl(-1, null, this));
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UML2Package.eINSTANCE.getStructuredClassifier();
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
			 		ownedAttribute = new com.intrinsarc.emflist.PersistentEList(Property.class, this, UML2Package.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE);
			 		return ownedAttribute;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Property.class, this, UML2Package.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE);
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
			ownedAttribute = new com.intrinsarc.emflist.PersistentEList(Property.class, this, UML2Package.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE);
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE, null, newOwnedAttribute));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE, null, newOwnedAttribute));
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
	public EList getRoles() {
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
	protected EList getAttributesHelper(EList attribute) {
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
	protected EList getOwnedMembersHelper(EList ownedMember) {
		super.getOwnedMembersHelper(ownedMember);
		if (eIsSet(UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute())) {
			ownedMember.addAll(getOwnedAttributes());
		}
		if (eIsSet(UML2Package.eINSTANCE.getStructuredClassifier_OwnedConnector())) {
			ownedMember.addAll(getOwnedConnectors());
		}
		return ownedMember;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getMembersHelper(EList member) {
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
	protected EList getFeaturesHelper(EList feature) {
		super.getFeaturesHelper(feature);
		if (eIsSet(UML2Package.eINSTANCE.getStructuredClassifier_OwnedConnector())) {
			feature.addAll(getOwnedConnectors());
		}
		return feature;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedConnectors() {
		if (ownedConnector == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		ownedConnector = new com.intrinsarc.emflist.PersistentEList(Connector.class, this, UML2Package.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR);
			 		return ownedConnector;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(Connector.class, this, UML2Package.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR);
		}      
		return ownedConnector;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList settable_getOwnedConnectors() {
		if (ownedConnector == null) {
			ownedConnector = new com.intrinsarc.emflist.PersistentEList(Connector.class, this, UML2Package.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR);
		}
		return ownedConnector;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public java.util.ArrayList undeleted_getOwnedConnectors() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (ownedConnector != null) {
			for (Object object : ownedConnector) {
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
	public EList getDeltaDeletedAttributes() {
		if (deltaDeletedAttributes == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		deltaDeletedAttributes = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedAttribute.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES);
			 		return deltaDeletedAttributes;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaDeletedAttribute.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES);
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
			deltaDeletedAttributes = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedAttribute.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES);
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES, null, newDeltaDeletedAttributes));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES, null, newDeltaDeletedAttributes));
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
			 		deltaReplacedAttributes = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedAttribute.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES);
			 		return deltaReplacedAttributes;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaReplacedAttribute.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES);
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
			deltaReplacedAttributes = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedAttribute.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES);
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES, null, newDeltaReplacedAttributes));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES, null, newDeltaReplacedAttributes));
		}
		settable_getDeltaReplacedAttributes().add(newDeltaReplacedAttributes);
		return newDeltaReplacedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaDeletedPorts() {
		if (deltaDeletedPorts == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		deltaDeletedPorts = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedPort.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS);
			 		return deltaDeletedPorts;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaDeletedPort.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS);
		}      
		return deltaDeletedPorts;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaDeletedPorts() {
		if (deltaDeletedPorts == null) {
			deltaDeletedPorts = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedPort.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS);
		}
		return deltaDeletedPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaDeletedPorts() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaDeletedPorts != null) {
			for (Object object : deltaDeletedPorts) {
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS, null, newDeltaDeletedPorts));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS, null, newDeltaDeletedPorts));
		}
		settable_getDeltaDeletedPorts().add(newDeltaDeletedPorts);
		return newDeltaDeletedPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaReplacedPorts() {
		if (deltaReplacedPorts == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		deltaReplacedPorts = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedPort.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS);
			 		return deltaReplacedPorts;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaReplacedPort.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS);
		}      
		return deltaReplacedPorts;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaReplacedPorts() {
		if (deltaReplacedPorts == null) {
			deltaReplacedPorts = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedPort.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS);
		}
		return deltaReplacedPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaReplacedPorts() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaReplacedPorts != null) {
			for (Object object : deltaReplacedPorts) {
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS, null, newDeltaReplacedPorts));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS, null, newDeltaReplacedPorts));
		}
		settable_getDeltaReplacedPorts().add(newDeltaReplacedPorts);
		return newDeltaReplacedPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaDeletedConnectors() {
		if (deltaDeletedConnectors == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		deltaDeletedConnectors = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedConnector.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS);
			 		return deltaDeletedConnectors;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaDeletedConnector.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS);
		}      
		return deltaDeletedConnectors;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaDeletedConnectors() {
		if (deltaDeletedConnectors == null) {
			deltaDeletedConnectors = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedConnector.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS);
		}
		return deltaDeletedConnectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaDeletedConnectors() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaDeletedConnectors != null) {
			for (Object object : deltaDeletedConnectors) {
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS, null, newDeltaDeletedConnectors));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS, null, newDeltaDeletedConnectors));
		}
		settable_getDeltaDeletedConnectors().add(newDeltaDeletedConnectors);
		return newDeltaDeletedConnectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDeltaReplacedConnectors() {
		if (deltaReplacedConnectors == null) {
			 if (org.eclipse.emf.common.util.EMFOptions.CREATE_LISTS_LAZILY_FOR_GET) {
			 		// create the list lazily...
			 		deltaReplacedConnectors = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedConnector.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS);
			 		return deltaReplacedConnectors;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaReplacedConnector.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS);
		}      
		return deltaReplacedConnectors;
	}

	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList settable_getDeltaReplacedConnectors() {
		if (deltaReplacedConnectors == null) {
			deltaReplacedConnectors = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedConnector.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS);
		}
		return deltaReplacedConnectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public java.util.ArrayList undeleted_getDeltaReplacedConnectors() {
		java.util.ArrayList temp = new java.util.ArrayList();

		if (deltaReplacedConnectors != null) {
			for (Object object : deltaReplacedConnectors) {
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS, null, newDeltaReplacedConnectors));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS, null, newDeltaReplacedConnectors));
		}
		settable_getDeltaReplacedConnectors().add(newDeltaReplacedConnectors);
		return newDeltaReplacedConnectors;
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
			 		deltaDeletedOperations = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedOperation.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS);
			 		return deltaDeletedOperations;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaDeletedOperation.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS);
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
			deltaDeletedOperations = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedOperation.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS);
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS, null, newDeltaDeletedOperations));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS, null, newDeltaDeletedOperations));
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
			 		deltaReplacedOperations = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedOperation.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS);
			 		return deltaReplacedOperations;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaReplacedOperation.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS);
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
			deltaReplacedOperations = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedOperation.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS);
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS, null, newDeltaReplacedOperations));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS, null, newDeltaReplacedOperations));
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
			 		deltaDeletedTraces = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedTrace.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES);
			 		return deltaDeletedTraces;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaDeletedTrace.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES);
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
			deltaDeletedTraces = new com.intrinsarc.emflist.PersistentEList(DeltaDeletedTrace.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES);
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES, null, newDeltaDeletedTraces));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES, null, newDeltaDeletedTraces));
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
			 		deltaReplacedTraces = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedTrace.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES);
			 		return deltaReplacedTraces;
			 }
			return new com.intrinsarc.emflist.UnmodifiableEList(DeltaReplacedTrace.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES);
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
			deltaReplacedTraces = new com.intrinsarc.emflist.PersistentEList(DeltaReplacedTrace.class, this, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES);
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES, null, newDeltaReplacedTraces));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES, null, newDeltaReplacedTraces));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR, null, newOwnedConnector));
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
			eNotify(new ENotificationImpl(this, 0, UML2Package.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR, null, newOwnedConnector));
		}
		settable_getOwnedConnectors().add(newOwnedConnector);
		return newOwnedConnector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case UML2Package.STRUCTURED_CLASSIFIER__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicAdd(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicAdd(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__OWNED_TEMPLATE_SIGNATURE:
					if (ownedTemplateSignature != null)
						msgs = ((InternalEObject)ownedTemplateSignature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - UML2Package.STRUCTURED_CLASSIFIER__OWNED_TEMPLATE_SIGNATURE, null, msgs);
					return basicSetOwnedTemplateSignature((TemplateSignature)otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicAdd(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicAdd(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicAdd(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicAdd(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__TEMPLATE_PARAMETER:
					if (templateParameter != null)
						msgs = ((InternalEObject)templateParameter).eInverseRemove(this, UML2Package.TEMPLATE_PARAMETER__PARAMETERED_ELEMENT, TemplateParameter.class, msgs);
					return basicSetTemplateParameter((TemplateParameter)otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__OWNING_PARAMETER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, UML2Package.STRUCTURED_CLASSIFIER__OWNING_PARAMETER, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicAdd(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicAdd(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicAdd(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__USE_CASE:
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
				case UML2Package.STRUCTURED_CLASSIFIER__EANNOTATIONS:
					return ((InternalEList)getEAnnotations()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__OWNED_COMMENT:
					return ((InternalEList)getOwnedComments()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__APPLIED_BASIC_STEREOTYPE_VALUES:
					return ((InternalEList)getAppliedBasicStereotypeValues()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__TEMPLATE_BINDING:
					return ((InternalEList)getTemplateBindings()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__OWNED_TEMPLATE_SIGNATURE:
					return basicSetOwnedTemplateSignature(null, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__CLIENT_DEPENDENCY:
					return ((InternalEList)getClientDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__NAME_EXPRESSION:
					return basicSetNameExpression(null, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ANONYMOUS_DEPENDENCIES:
					return ((InternalEList)getOwnedAnonymousDependencies()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__OWNED_RULE:
					return ((InternalEList)getOwnedRules()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__ELEMENT_IMPORT:
					return ((InternalEList)getElementImports()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__PACKAGE_IMPORT:
					return ((InternalEList)getPackageImports()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__TEMPLATE_PARAMETER:
					return basicSetTemplateParameter(null, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__OWNING_PARAMETER:
					return eBasicSetContainer(null, UML2Package.STRUCTURED_CLASSIFIER__OWNING_PARAMETER, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__GENERALIZATION:
					return ((InternalEList)getGeneralizations()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__SUBSTITUTION:
					return ((InternalEList)getSubstitutions()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__POWERTYPE_EXTENT:
					return ((InternalEList)getPowertypeExtents()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__OWNED_USE_CASE:
					return ((InternalEList)getOwnedUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__USE_CASE:
					return ((InternalEList)getUseCases()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__OCCURRENCE:
					return ((InternalEList)getOccurrences()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE:
					return ((InternalEList)getOwnedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR:
					return ((InternalEList)getOwnedConnectors()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES:
					return ((InternalEList)getDeltaDeletedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES:
					return ((InternalEList)getDeltaReplacedAttributes()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS:
					return ((InternalEList)getDeltaDeletedPorts()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS:
					return ((InternalEList)getDeltaReplacedPorts()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS:
					return ((InternalEList)getDeltaDeletedConnectors()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS:
					return ((InternalEList)getDeltaReplacedConnectors()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS:
					return ((InternalEList)getDeltaDeletedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS:
					return ((InternalEList)getDeltaReplacedOperations()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES:
					return ((InternalEList)getDeltaDeletedTraces()).basicRemove(otherEnd, msgs);
				case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES:
					return ((InternalEList)getDeltaReplacedTraces()).basicRemove(otherEnd, msgs);
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
				case UML2Package.STRUCTURED_CLASSIFIER__OWNING_PARAMETER:
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
			case UML2Package.STRUCTURED_CLASSIFIER__EANNOTATIONS:
				return getEAnnotations();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ELEMENT:
				return getOwnedElements();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_COMMENT:
				return getOwnedComments();
			case UML2Package.STRUCTURED_CLASSIFIER__JDELETED:
				return new Integer(getJ_deleted());
			case UML2Package.STRUCTURED_CLASSIFIER__DOCUMENTATION:
				return getDocumentation();
			case UML2Package.STRUCTURED_CLASSIFIER__APPLIED_BASIC_STEREOTYPES:
				return getAppliedBasicStereotypes();
			case UML2Package.STRUCTURED_CLASSIFIER__APPLIED_BASIC_STEREOTYPE_VALUES:
				return getAppliedBasicStereotypeValues();
			case UML2Package.STRUCTURED_CLASSIFIER__UUID:
				return getUuid();
			case UML2Package.STRUCTURED_CLASSIFIER__TEMPLATE_BINDING:
				return getTemplateBindings();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_TEMPLATE_SIGNATURE:
				return getOwnedTemplateSignature();
			case UML2Package.STRUCTURED_CLASSIFIER__NAME:
				return getName();
			case UML2Package.STRUCTURED_CLASSIFIER__QUALIFIED_NAME:
				return getQualifiedName();
			case UML2Package.STRUCTURED_CLASSIFIER__VISIBILITY:
				return getVisibility();
			case UML2Package.STRUCTURED_CLASSIFIER__CLIENT_DEPENDENCY:
				return getClientDependencies();
			case UML2Package.STRUCTURED_CLASSIFIER__NAME_EXPRESSION:
				return getNameExpression();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ANONYMOUS_DEPENDENCIES:
				return getOwnedAnonymousDependencies();
			case UML2Package.STRUCTURED_CLASSIFIER__REVERSE_DEPENDENCIES:
				return getReverseDependencies();
			case UML2Package.STRUCTURED_CLASSIFIER__REVERSE_GENERALIZATIONS:
				return getReverseGeneralizations();
			case UML2Package.STRUCTURED_CLASSIFIER__MEMBER:
				return getMembers();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_RULE:
				return getOwnedRules();
			case UML2Package.STRUCTURED_CLASSIFIER__IMPORTED_MEMBER:
				return getImportedMembers();
			case UML2Package.STRUCTURED_CLASSIFIER__ELEMENT_IMPORT:
				return getElementImports();
			case UML2Package.STRUCTURED_CLASSIFIER__PACKAGE_IMPORT:
				return getPackageImports();
			case UML2Package.STRUCTURED_CLASSIFIER__TEMPLATE_PARAMETER:
				if (resolve) return getTemplateParameter();
				return basicGetTemplateParameter();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNING_PARAMETER:
				return getOwningParameter();
			case UML2Package.STRUCTURED_CLASSIFIER__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility();
			case UML2Package.STRUCTURED_CLASSIFIER__PACKAGE:
				if (resolve) return getPackage();
				return basicGetPackage();
			case UML2Package.STRUCTURED_CLASSIFIER__IS_RETIRED:
				return isRetired() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.STRUCTURED_CLASSIFIER__REDEFINITION_CONTEXT:
				return getRedefinitionContexts();
			case UML2Package.STRUCTURED_CLASSIFIER__IS_LEAF:
				return isLeaf() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.STRUCTURED_CLASSIFIER__FEATURE:
				return getFeatures();
			case UML2Package.STRUCTURED_CLASSIFIER__IS_ABSTRACT:
				return isAbstract() ? Boolean.TRUE : Boolean.FALSE;
			case UML2Package.STRUCTURED_CLASSIFIER__INHERITED_MEMBER:
				return getInheritedMembers();
			case UML2Package.STRUCTURED_CLASSIFIER__GENERAL:
				return getGenerals();
			case UML2Package.STRUCTURED_CLASSIFIER__GENERALIZATION:
				return getGeneralizations();
			case UML2Package.STRUCTURED_CLASSIFIER__ATTRIBUTE:
				return getAttributes();
			case UML2Package.STRUCTURED_CLASSIFIER__REDEFINED_CLASSIFIER:
				return getRedefinedClassifiers();
			case UML2Package.STRUCTURED_CLASSIFIER__SUBSTITUTION:
				return getSubstitutions();
			case UML2Package.STRUCTURED_CLASSIFIER__POWERTYPE_EXTENT:
				return getPowertypeExtents();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_USE_CASE:
				return getOwnedUseCases();
			case UML2Package.STRUCTURED_CLASSIFIER__USE_CASE:
				return getUseCases();
			case UML2Package.STRUCTURED_CLASSIFIER__REPRESENTATION:
				return getRepresentation();
			case UML2Package.STRUCTURED_CLASSIFIER__OCCURRENCE:
				return getOccurrences();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE:
				return getOwnedAttributes();
			case UML2Package.STRUCTURED_CLASSIFIER__PART:
				return getParts();
			case UML2Package.STRUCTURED_CLASSIFIER__ROLE:
				return getRoles();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR:
				return getOwnedConnectors();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES:
				return getDeltaDeletedAttributes();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES:
				return getDeltaReplacedAttributes();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS:
				return getDeltaDeletedPorts();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS:
				return getDeltaReplacedPorts();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS:
				return getDeltaDeletedConnectors();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS:
				return getDeltaReplacedConnectors();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS:
				return getDeltaDeletedOperations();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS:
				return getDeltaReplacedOperations();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES:
				return getDeltaDeletedTraces();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES:
				return getDeltaReplacedTraces();
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
			case UML2Package.STRUCTURED_CLASSIFIER__EANNOTATIONS:
				getEAnnotations().clear();
				getEAnnotations().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_COMMENT:
				getOwnedComments().clear();
				getOwnedComments().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__JDELETED:
				setJ_deleted(((Integer)newValue).intValue());
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				getAppliedBasicStereotypes().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				getAppliedBasicStereotypeValues().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__UUID:
				setUuid((String)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				getTemplateBindings().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__NAME:
				setName((String)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				getClientDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__NAME_EXPRESSION:
				setNameExpression((StringExpression)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				getOwnedAnonymousDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				getReverseDependencies().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				getReverseGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_RULE:
				getOwnedRules().clear();
				getOwnedRules().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__ELEMENT_IMPORT:
				getElementImports().clear();
				getElementImports().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__PACKAGE_IMPORT:
				getPackageImports().clear();
				getPackageImports().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility((VisibilityKind)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__IS_RETIRED:
				setIsRetired(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__IS_LEAF:
				setIsLeaf(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__IS_ABSTRACT:
				setIsAbstract(((Boolean)newValue).booleanValue());
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__GENERALIZATION:
				getGeneralizations().clear();
				getGeneralizations().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				getRedefinedClassifiers().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__SUBSTITUTION:
				getSubstitutions().clear();
				getSubstitutions().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				getPowertypeExtents().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				getOwnedUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__USE_CASE:
				getUseCases().clear();
				getUseCases().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OCCURRENCE:
				getOccurrences().clear();
				getOccurrences().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE:
				getOwnedAttributes().clear();
				getOwnedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR:
				getOwnedConnectors().clear();
				getOwnedConnectors().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES:
				getDeltaDeletedAttributes().clear();
				getDeltaDeletedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES:
				getDeltaReplacedAttributes().clear();
				getDeltaReplacedAttributes().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS:
				getDeltaDeletedPorts().clear();
				getDeltaDeletedPorts().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS:
				getDeltaReplacedPorts().clear();
				getDeltaReplacedPorts().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS:
				getDeltaDeletedConnectors().clear();
				getDeltaDeletedConnectors().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS:
				getDeltaReplacedConnectors().clear();
				getDeltaReplacedConnectors().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS:
				getDeltaDeletedOperations().clear();
				getDeltaDeletedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS:
				getDeltaReplacedOperations().clear();
				getDeltaReplacedOperations().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES:
				getDeltaDeletedTraces().clear();
				getDeltaDeletedTraces().addAll((Collection)newValue);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES:
				getDeltaReplacedTraces().clear();
				getDeltaReplacedTraces().addAll((Collection)newValue);
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
			case UML2Package.STRUCTURED_CLASSIFIER__EANNOTATIONS:
				getEAnnotations().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_COMMENT:
				getOwnedComments().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__JDELETED:
				setJ_deleted(JDELETED_EDEFAULT);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__APPLIED_BASIC_STEREOTYPES:
				getAppliedBasicStereotypes().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__APPLIED_BASIC_STEREOTYPE_VALUES:
				getAppliedBasicStereotypeValues().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__UUID:
				setUuid(UUID_EDEFAULT);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__TEMPLATE_BINDING:
				getTemplateBindings().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_TEMPLATE_SIGNATURE:
				setOwnedTemplateSignature((TemplateSignature)null);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__CLIENT_DEPENDENCY:
				getClientDependencies().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__NAME_EXPRESSION:
				setNameExpression((StringExpression)null);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ANONYMOUS_DEPENDENCIES:
				getOwnedAnonymousDependencies().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__REVERSE_DEPENDENCIES:
				getReverseDependencies().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__REVERSE_GENERALIZATIONS:
				getReverseGeneralizations().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_RULE:
				getOwnedRules().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__ELEMENT_IMPORT:
				getElementImports().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__PACKAGE_IMPORT:
				getPackageImports().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__TEMPLATE_PARAMETER:
				setTemplateParameter((TemplateParameter)null);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNING_PARAMETER:
				setOwningParameter((TemplateParameter)null);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__PACKAGEABLE_ELEMENT_VISIBILITY:
				setPackageableElement_visibility(PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__IS_RETIRED:
				setIsRetired(IS_RETIRED_EDEFAULT);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__IS_LEAF:
				setIsLeaf(IS_LEAF_EDEFAULT);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__IS_ABSTRACT:
				setIsAbstract(IS_ABSTRACT_EDEFAULT);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__GENERALIZATION:
				getGeneralizations().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__REDEFINED_CLASSIFIER:
				getRedefinedClassifiers().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__SUBSTITUTION:
				getSubstitutions().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__POWERTYPE_EXTENT:
				getPowertypeExtents().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_USE_CASE:
				getOwnedUseCases().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__USE_CASE:
				getUseCases().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__REPRESENTATION:
				setRepresentation((CollaborationOccurrence)null);
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OCCURRENCE:
				getOccurrences().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE:
				getOwnedAttributes().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR:
				getOwnedConnectors().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES:
				getDeltaDeletedAttributes().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES:
				getDeltaReplacedAttributes().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS:
				getDeltaDeletedPorts().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS:
				getDeltaReplacedPorts().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS:
				getDeltaDeletedConnectors().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS:
				getDeltaReplacedConnectors().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS:
				getDeltaDeletedOperations().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS:
				getDeltaReplacedOperations().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES:
				getDeltaDeletedTraces().clear();
				return;
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES:
				getDeltaReplacedTraces().clear();
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
			case UML2Package.STRUCTURED_CLASSIFIER__EANNOTATIONS:
				return eAnnotations != null && !eAnnotations.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ELEMENT:
				return !getOwnedElements().isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNER:
				return basicGetOwner() != null;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_COMMENT:
				return ownedComment != null && !ownedComment.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__JDELETED:
				return j_deleted != JDELETED_EDEFAULT;
			case UML2Package.STRUCTURED_CLASSIFIER__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case UML2Package.STRUCTURED_CLASSIFIER__APPLIED_BASIC_STEREOTYPES:
				return appliedBasicStereotypes != null && !appliedBasicStereotypes.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__APPLIED_BASIC_STEREOTYPE_VALUES:
				return appliedBasicStereotypeValues != null && !appliedBasicStereotypeValues.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__UUID:
				return UUID_EDEFAULT == null ? uuid != null : !UUID_EDEFAULT.equals(uuid);
			case UML2Package.STRUCTURED_CLASSIFIER__TEMPLATE_BINDING:
				return templateBinding != null && !templateBinding.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_TEMPLATE_SIGNATURE:
				return ownedTemplateSignature != null;
			case UML2Package.STRUCTURED_CLASSIFIER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case UML2Package.STRUCTURED_CLASSIFIER__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case UML2Package.STRUCTURED_CLASSIFIER__VISIBILITY:
				return getVisibility() != VISIBILITY_EDEFAULT;
			case UML2Package.STRUCTURED_CLASSIFIER__CLIENT_DEPENDENCY:
				return clientDependency != null && !clientDependency.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__NAME_EXPRESSION:
				return nameExpression != null;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ANONYMOUS_DEPENDENCIES:
				return ownedAnonymousDependencies != null && !ownedAnonymousDependencies.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__REVERSE_DEPENDENCIES:
				return reverseDependencies != null && !reverseDependencies.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__REVERSE_GENERALIZATIONS:
				return reverseGeneralizations != null && !reverseGeneralizations.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__MEMBER:
				return !getMembers().isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_RULE:
				return ownedRule != null && !ownedRule.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__IMPORTED_MEMBER:
				return !getImportedMembers().isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__ELEMENT_IMPORT:
				return elementImport != null && !elementImport.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__PACKAGE_IMPORT:
				return packageImport != null && !packageImport.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__TEMPLATE_PARAMETER:
				return templateParameter != null;
			case UML2Package.STRUCTURED_CLASSIFIER__OWNING_PARAMETER:
				return getOwningParameter() != null;
			case UML2Package.STRUCTURED_CLASSIFIER__PACKAGEABLE_ELEMENT_VISIBILITY:
				return getPackageableElement_visibility() != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
			case UML2Package.STRUCTURED_CLASSIFIER__PACKAGE:
				return basicGetPackage() != null;
			case UML2Package.STRUCTURED_CLASSIFIER__IS_RETIRED:
				return ((eFlags & IS_RETIRED_EFLAG) != 0) != IS_RETIRED_EDEFAULT;
			case UML2Package.STRUCTURED_CLASSIFIER__REDEFINITION_CONTEXT:
				return !getRedefinitionContexts().isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__IS_LEAF:
				return ((eFlags & IS_LEAF_EFLAG) != 0) != IS_LEAF_EDEFAULT;
			case UML2Package.STRUCTURED_CLASSIFIER__FEATURE:
				return !getFeatures().isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__IS_ABSTRACT:
				return ((eFlags & IS_ABSTRACT_EFLAG) != 0) != IS_ABSTRACT_EDEFAULT;
			case UML2Package.STRUCTURED_CLASSIFIER__INHERITED_MEMBER:
				return !getInheritedMembers().isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__GENERAL:
				return !getGenerals().isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__GENERALIZATION:
				return generalization != null && !generalization.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__ATTRIBUTE:
				return !getAttributes().isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__REDEFINED_CLASSIFIER:
				return redefinedClassifier != null && !redefinedClassifier.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__SUBSTITUTION:
				return substitution != null && !substitution.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__POWERTYPE_EXTENT:
				return powertypeExtent != null && !powertypeExtent.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_USE_CASE:
				return ownedUseCase != null && !ownedUseCase.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__USE_CASE:
				return useCase != null && !useCase.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__REPRESENTATION:
				return representation != null;
			case UML2Package.STRUCTURED_CLASSIFIER__OCCURRENCE:
				return occurrence != null && !occurrence.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE:
				return ownedAttribute != null && !ownedAttribute.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__PART:
				return !getParts().isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__ROLE:
				return !getRoles().isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR:
				return ownedConnector != null && !ownedConnector.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_ATTRIBUTES:
				return deltaDeletedAttributes != null && !deltaDeletedAttributes.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_ATTRIBUTES:
				return deltaReplacedAttributes != null && !deltaReplacedAttributes.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_PORTS:
				return deltaDeletedPorts != null && !deltaDeletedPorts.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_PORTS:
				return deltaReplacedPorts != null && !deltaReplacedPorts.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_CONNECTORS:
				return deltaDeletedConnectors != null && !deltaDeletedConnectors.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_CONNECTORS:
				return deltaReplacedConnectors != null && !deltaReplacedConnectors.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_OPERATIONS:
				return deltaDeletedOperations != null && !deltaDeletedOperations.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_OPERATIONS:
				return deltaReplacedOperations != null && !deltaReplacedOperations.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_DELETED_TRACES:
				return deltaDeletedTraces != null && !deltaDeletedTraces.isEmpty();
			case UML2Package.STRUCTURED_CLASSIFIER__DELTA_REPLACED_TRACES:
				return deltaReplacedTraces != null && !deltaReplacedTraces.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}


	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UML2Package.STRUCTURED_CLASSIFIER__VISIBILITY:
				return false;
			case UML2Package.STRUCTURED_CLASSIFIER__PACKAGEABLE_ELEMENT_VISIBILITY:
				return visibility != PACKAGEABLE_ELEMENT_VISIBILITY_EDEFAULT;
		}
		return eIsSetGen(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EList getRolesHelper(EList role) {
		if (eIsSet(UML2Package.eINSTANCE.getStructuredClassifier_OwnedAttribute())) {
			role.addAll(getOwnedAttributes());
		}
		return role;
	}

} //StructuredClassifierImpl
