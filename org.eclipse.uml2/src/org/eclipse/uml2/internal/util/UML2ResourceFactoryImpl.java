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
 * $Id: UML2ResourceFactoryImpl.java,v 1.1 2009-03-04 23:06:49 andrew Exp $
 */
package org.eclipse.uml2.internal.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.uml2.util.UML2Resource;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.eclipse.uml2.internal.util.UML2ResourceImpl
 * @generated
 */
public class UML2ResourceFactoryImpl extends ResourceFactoryImpl implements UML2Resource.Factory {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Creates an instance of the resource factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UML2ResourceFactoryImpl() {
		super();
	}

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource createResourceGen(URI uri) {
		UML2Resource result = new UML2ResourceImpl(uri);
		result.setEncoding(UML2Resource.DEFAULT_ENCODING);
		return result;
	}

	public Resource createResource(URI uri) {
		UML2Resource resource = (UML2Resource) createResourceGen(uri);

		resource.getDefaultLoadOptions().put(
			XMIResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		resource.getDefaultLoadOptions().put(
			XMIResource.OPTION_LAX_FEATURE_PROCESSING, Boolean.TRUE);

		resource.getDefaultSaveOptions().put(
			XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		resource.getDefaultSaveOptions().put(XMIResource.OPTION_USE_XMI_TYPE,
			Boolean.TRUE);
		resource.getDefaultSaveOptions().put(
			XMIResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.TRUE);
		resource.getDefaultSaveOptions().put(
			XMIResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

		return resource;
	}

} //UML2ResourceFactoryImpl
