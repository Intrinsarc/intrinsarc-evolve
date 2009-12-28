/*
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - initial API and implementation
 *
 * $Id: IItemQualifiedTextProvider.java,v 1.1 2009-03-04 23:08:22 andrew Exp $
 */
package org.eclipse.uml2.provider;

/**
 * A provider of qualified text for an item.
 * 
 * @deprecated Use org.eclipse.uml2.common.edit.provider.IItemQualifiedTextProvider instead.
 */
public interface IItemQualifiedTextProvider {

	/**
	 * Fetches qualified text for the specified object instance.
	 * 
	 * @param object
	 *            The object instance for which to fetch qualified text.
	 * @return The qualified text.
	 */
	public String getQualifiedText(Object object);

}