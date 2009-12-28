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
 * $Id: SupersetReplaceCommand.java,v 1.1 2009-03-04 23:08:00 andrew Exp $
 */
package org.eclipse.uml2.common.edit.command;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.ReplaceCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * 
 */
public class SupersetReplaceCommand
		extends SupersetCommand {

	protected final Object value;

	protected final Collection collection;

	public SupersetReplaceCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, EStructuralFeature[] subsetFeatures,
			Object value, Collection collection) {

		super(domain, owner, feature, subsetFeatures, new ReplaceCommand(
			domain, owner, feature, value, collection));

		this.value = value;
		this.collection = collection;
	}

	/**
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	public void execute() {

		for (int i = 0; i < subsetFeatures.length; i++) {

			if (subsetFeatures[i].isMany()) {

				if (((EList) owner.eGet(subsetFeatures[i])).contains(value)) {
					appendAndExecute(RemoveCommand.create(domain, owner,
						subsetFeatures[i], Collections.singleton(value)));
				}
			} else {

				if (value == owner.eGet(subsetFeatures[i])) {
					EReference subsetReference = (EReference) subsetFeatures[i];

					appendAndExecute(subsetReference.isContainer()
						&& !subsetReference.getEOpposite().isMany()
						? new SetCommand(domain, owner, subsetFeatures[i], null)
						: SetCommand.create(domain, owner, subsetFeatures[i],
							null));
				}
			}
		}

		super.execute();
	}

}