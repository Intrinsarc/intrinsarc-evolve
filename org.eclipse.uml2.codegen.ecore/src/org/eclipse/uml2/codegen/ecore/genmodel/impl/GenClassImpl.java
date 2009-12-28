/*
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - initial API and implementation
 *
 * $Id: GenClassImpl.java,v 1.1 2009-03-04 23:07:17 andrew Exp $
 */
package org.eclipse.uml2.codegen.ecore.genmodel.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenOperation;
import org.eclipse.emf.codegen.ecore.genmodel.GenProviderKind;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.uml2.codegen.ecore.CodeGenEcorePlugin;
import org.eclipse.uml2.codegen.ecore.Generator;
import org.eclipse.uml2.codegen.ecore.genmodel.GenClass;
import org.eclipse.uml2.codegen.ecore.genmodel.GenModel;
import org.eclipse.uml2.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.uml2.codegen.ecore.genmodel.util.UML2GenModelUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
/**
 * @author khussey
 *
 */
public class GenClassImpl
		extends org.eclipse.emf.codegen.ecore.genmodel.impl.GenClassImpl
		implements GenClass {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return GenModelPackage.eINSTANCE.getGenClass();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd,
			int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case GenModelPackage.GEN_CLASS__GEN_FEATURES :
					return ((InternalEList) getGenFeatures()).basicAdd(
						otherEnd, msgs);
				case GenModelPackage.GEN_CLASS__GEN_OPERATIONS :
					return ((InternalEList) getGenOperations()).basicAdd(
						otherEnd, msgs);
				default :
					return eDynamicInverseAdd(otherEnd, featureID, baseClass,
						msgs);
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case GenModelPackage.GEN_CLASS__GEN_FEATURES :
					return ((InternalEList) getGenFeatures()).basicRemove(
						otherEnd, msgs);
				case GenModelPackage.GEN_CLASS__GEN_OPERATIONS :
					return ((InternalEList) getGenOperations()).basicRemove(
						otherEnd, msgs);
				default :
					return eDynamicInverseRemove(otherEnd, featureID,
						baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case GenModelPackage.GEN_CLASS__GEN_PACKAGE :
				return getGenPackage();
			case GenModelPackage.GEN_CLASS__PROVIDER :
				return getProvider();
			case GenModelPackage.GEN_CLASS__IMAGE :
				return isImage()
					? Boolean.TRUE
					: Boolean.FALSE;
			case GenModelPackage.GEN_CLASS__ECORE_CLASS :
				if (resolve)
					return getEcoreClass();
				return basicGetEcoreClass();
			case GenModelPackage.GEN_CLASS__GEN_FEATURES :
				return getGenFeatures();
			case GenModelPackage.GEN_CLASS__GEN_OPERATIONS :
				return getGenOperations();
			case GenModelPackage.GEN_CLASS__LABEL_FEATURE :
				if (resolve)
					return getLabelFeature();
				return basicGetLabelFeature();
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
			case GenModelPackage.GEN_CLASS__PROVIDER :
				setProvider((GenProviderKind) newValue);
				return;
			case GenModelPackage.GEN_CLASS__IMAGE :
				setImage(((Boolean) newValue).booleanValue());
				return;
			case GenModelPackage.GEN_CLASS__ECORE_CLASS :
				setEcoreClass((EClass) newValue);
				return;
			case GenModelPackage.GEN_CLASS__GEN_FEATURES :
				getGenFeatures().clear();
				getGenFeatures().addAll((Collection) newValue);
				return;
			case GenModelPackage.GEN_CLASS__GEN_OPERATIONS :
				getGenOperations().clear();
				getGenOperations().addAll((Collection) newValue);
				return;
			case GenModelPackage.GEN_CLASS__LABEL_FEATURE :
				setLabelFeature((GenFeature) newValue);
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
			case GenModelPackage.GEN_CLASS__PROVIDER :
				setProvider(PROVIDER_EDEFAULT);
				return;
			case GenModelPackage.GEN_CLASS__IMAGE :
				setImage(IMAGE_EDEFAULT);
				return;
			case GenModelPackage.GEN_CLASS__ECORE_CLASS :
				setEcoreClass((EClass) null);
				return;
			case GenModelPackage.GEN_CLASS__GEN_FEATURES :
				getGenFeatures().clear();
				return;
			case GenModelPackage.GEN_CLASS__GEN_OPERATIONS :
				getGenOperations().clear();
				return;
			case GenModelPackage.GEN_CLASS__LABEL_FEATURE :
				setLabelFeature((GenFeature) null);
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
			case GenModelPackage.GEN_CLASS__GEN_PACKAGE :
				return getGenPackage() != null;
			case GenModelPackage.GEN_CLASS__PROVIDER :
				return provider != PROVIDER_EDEFAULT;
			case GenModelPackage.GEN_CLASS__IMAGE :
				return image != IMAGE_EDEFAULT;
			case GenModelPackage.GEN_CLASS__ECORE_CLASS :
				return ecoreClass != null;
			case GenModelPackage.GEN_CLASS__GEN_FEATURES :
				return genFeatures != null && !genFeatures.isEmpty();
			case GenModelPackage.GEN_CLASS__GEN_OPERATIONS :
				return genOperations != null && !genOperations.isEmpty();
			case GenModelPackage.GEN_CLASS__LABEL_FEATURE :
				return labelFeature != null;
		}
		return eDynamicIsSet(eFeature);
	}

	public void generate(IProgressMonitor progressMonitor) {
		// TODO https://bugs.eclipse.org/bugs/show_bug.cgi?id=75925

		try {

			if (!canGenerate()) {
				return;
			}

			if (UML2GenModelUtil.isOperationsClasses(getGenPackage())
				&& !getDuplicateGenOperations().isEmpty()) {

				progressMonitor.beginTask("", 1); //$NON-NLS-1$

				if (!isInterface()) {
					progressMonitor.subTask(CodeGenEcorePlugin.INSTANCE
						.getString("_UI_GeneratingJavaClass_message", //$NON-NLS-1$
							new Object[]{getQualifiedOperationsClassName()}));
					generate(new SubProgressMonitor(progressMonitor, 1),
						Generator.EMF_MODEL_PROJECT_STYLE, getGenModel()
							.getEffectiveModelPluginVariables(), getGenModel()
							.getModelDirectory(), UML2GenModelUtil
							.getOperationsPackageName(getGenPackage()),
						getOperationsClassName(), ((GenModel) getGenModel())
							.getOperationsClassEmitter());
				}
			}

			super.generate(progressMonitor);
		} finally {
			progressMonitor.done();
		}
	}

	protected List duplicateGenFeatures = null;

	public List getDuplicateGenFeatures() {
		List result = new ArrayList(getGenFeatures());

		if (duplicateGenFeatures == null) {
			duplicateGenFeatures = new ArrayList();

			for (Iterator duplicateEcoreFeatures = Generator
				.getDuplicateEcoreFeatures(getEcoreClass()).iterator(); duplicateEcoreFeatures
				.hasNext();) {

				GenFeature duplicateGenFeature = getGenModel()
					.createGenFeature();
				duplicateGenFeature
					.initialize((EStructuralFeature) duplicateEcoreFeatures
						.next());
				duplicateGenFeatures.add(duplicateGenFeature);
			}
		}

		result.addAll(duplicateGenFeatures);

		return result;
	}

	protected List collectDuplicateGenFeatures(List genClasses,
			List genFeatures, GenFeatureFilter filter) {
		List result = new ArrayList();

		if (genClasses != null) {

			for (Iterator i = genClasses.iterator(); i.hasNext();) {
				result
					.addAll(collectGenFeatures(
						null,
						UML2GenModelUtil
							.getDuplicateGenFeatures((org.eclipse.emf.codegen.ecore.genmodel.GenClass) i
								.next()), filter));
			}
		}

		if (genFeatures != null) {
			result.addAll(collectGenFeatures(null, genFeatures, filter));
		}

		return result;
	}

	protected List getAllDuplicateGenFeatures() {
		return collectDuplicateGenFeatures(getAllBaseGenClasses(),
			getDuplicateGenFeatures(), null);
	}

	public List getDeclaredGenFeatures() {
		Map declaredGenFeatures = new LinkedHashMap();

		List redefinedGenFeatures = collectGenFeatures(null,
			getRedefinedGenFeatures(), new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return !getAllGenFeatures().contains(genFeature);
				}
			});

		for (Iterator i = redefinedGenFeatures.iterator(); i.hasNext();) {
			GenFeature genFeature = (GenFeature) i.next();
			declaredGenFeatures.put(genFeature.getName(), genFeature);
		}

		List duplicateGenFeatures = collectGenFeatures(null,
			getDuplicateGenFeatures(), new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return !UML2GenModelUtil.isDuplicate(genFeature)
						|| !UML2GenModelUtil.isRedefinition(genFeature);
				}
			});

		for (Iterator i = duplicateGenFeatures.iterator(); i.hasNext();) {
			GenFeature genFeature = (GenFeature) i.next();
			declaredGenFeatures.put(genFeature.getName(), genFeature);
		}

		return new ArrayList(declaredGenFeatures.values());
	}

	protected List getImplementedGenFeatures(GenFeatureFilter filter) {
		Map implementedGenFeatures = new LinkedHashMap();

		for (Iterator i = getImplementedGenClasses().iterator(); i.hasNext();) {
			org.eclipse.emf.codegen.ecore.genmodel.GenClass genClass = (org.eclipse.emf.codegen.ecore.genmodel.GenClass) i
				.next();

			for (Iterator j = UML2GenModelUtil.getUnionGenFeatures(genClass)
				.iterator(); j.hasNext();) {

				GenFeature genFeature = (GenFeature) j.next();
				implementedGenFeatures.put(genFeature.getName(), genFeature);
			}

			for (Iterator j = UML2GenModelUtil.getSupersetGenFeatures(genClass)
				.iterator(); j.hasNext();) {

				GenFeature genFeature = (GenFeature) j.next();
				implementedGenFeatures.put(genFeature.getName(), genFeature);
			}

			for (Iterator j = UML2GenModelUtil
				.getRedefinedGenFeatures(genClass).iterator(); j.hasNext();) {

				GenFeature genFeature = (GenFeature) j.next();
				implementedGenFeatures.put(genFeature.getName(), genFeature);
			}

			for (Iterator j = UML2GenModelUtil
				.getDuplicateGenFeatures(genClass).iterator(); j.hasNext();) {

				GenFeature genFeature = (GenFeature) j.next();
				implementedGenFeatures.put(genFeature.getName(), genFeature);
			}
		}

		return collectGenFeatures(null, new ArrayList(implementedGenFeatures
			.values()), filter);
	}

	public List getDeclaredFieldGenFeatures() {
		return getImplementedGenFeatures(new GenFeatureFilter() {

			public boolean accept(GenFeature genFeature) {

				if (UML2GenModelUtil.isDuplicate(genFeature)) {

					for (Iterator i = UML2GenModelUtil.getRedefinedGenFeatures(
						genFeature).iterator(); i.hasNext();) {

						GenFeature redefinedGenFeature = (GenFeature) i.next();

						if (getExtendedGenFeatures().contains(
							redefinedGenFeature)) {
							return !UML2GenModelUtil
								.isUnion(redefinedGenFeature)
								&& !isRedefined(redefinedGenFeature);
						}
					}
				}

				return !getExtendedGenFeatures().contains(genFeature)
					&& !UML2GenModelUtil.isUnion(genFeature)
					&& !isRedefined(genFeature);
			}
		});
	}

	public List getImplementedGenFeatures() {
		return getImplementedGenFeatures(new GenFeatureFilter() {

			public boolean accept(GenFeature genFeature) {
				return !UML2GenModelUtil.isUnion(genFeature)
					&& !isRedefined(genFeature);
			}
		});
	}

	public List getExtendedGenFeatures() {
		return collectDuplicateGenFeatures(getExtendedGenClasses(), null, null);
	}

	protected List duplicateGenOperations = null;

	public List getDuplicateGenOperations() {
		List result = new ArrayList(getGenOperations());

		if (duplicateGenOperations == null) {
			duplicateGenOperations = new ArrayList();

			for (Iterator duplicateEcoreOperations = Generator
				.getDuplicateEcoreOperations(getEcoreClass()).iterator(); duplicateEcoreOperations
				.hasNext();) {

				GenOperation duplicateGenOperation = getGenModel()
					.createGenOperation();
				duplicateGenOperation
					.initialize((EOperation) duplicateEcoreOperations.next());
				duplicateGenOperations.add(duplicateGenOperation);
			}
		}

		result.addAll(duplicateGenOperations);

		return result;
	}

	protected List collectDuplicateGenOperations(List genClasses,
			List genOperations, GenOperationFilter filter) {
		List result = new ArrayList();

		if (genClasses != null) {

			for (Iterator i = genClasses.iterator(); i.hasNext();) {
				result
					.addAll(collectGenOperations(
						null,
						UML2GenModelUtil
							.getDuplicateGenOperations((org.eclipse.emf.codegen.ecore.genmodel.GenClass) i
								.next()), filter));
			}
		}

		if (genFeatures != null) {
			result.addAll(collectGenOperations(null, genOperations, filter));
		}

		return result;
	}

	protected List getAllDuplicateGenOperations() {
		return collectDuplicateGenOperations(getAllBaseGenClasses(),
			getDuplicateGenOperations(), null);
	}

	public List getDeclaredGenOperations() {
		Map declaredGenOperations = new LinkedHashMap();

		List redefinedGenOperations = collectGenOperations(null,
			getRedefinedGenOperations(), new GenOperationFilter() {

				public boolean accept(GenOperation genOperation) {
					return !getAllDuplicateGenOperations().contains(
						genOperation);
				}
			});

		for (Iterator i = redefinedGenOperations.iterator(); i.hasNext();) {
			GenOperation genOperation = (GenOperation) i.next();
			declaredGenOperations.put(genOperation.getName()
				+ genOperation.getParameterTypes(""), genOperation); //$NON-NLS-1$
		}

		List duplicateGenOperations = collectGenOperations(null,
			getDuplicateGenOperations(), new GenOperationFilter() {

				public boolean accept(GenOperation genOperation) {
					return !UML2GenModelUtil.isDuplicate(genOperation)
						|| !UML2GenModelUtil.isRedefinition(genOperation);
				}
			});

		for (Iterator i = duplicateGenOperations.iterator(); i.hasNext();) {
			GenOperation genOperation = (GenOperation) i.next();
			declaredGenOperations.put(genOperation.getName()
				+ genOperation.getParameterTypes(""), genOperation); //$NON-NLS-1$
		}

		return new ArrayList(declaredGenOperations.values());
	}

	protected List getImplementedGenOperations(GenOperationFilter filter) {
		Map implementedGenOperations = new LinkedHashMap();

		for (Iterator i = getImplementedGenClasses().iterator(); i.hasNext();) {
			org.eclipse.emf.codegen.ecore.genmodel.GenClass genClass = (org.eclipse.emf.codegen.ecore.genmodel.GenClass) i
				.next();

			for (Iterator j = UML2GenModelUtil.getRedefinedGenOperations(
				genClass).iterator(); j.hasNext();) {

				GenOperation genOperation = (GenOperation) j.next();
				implementedGenOperations.put(genOperation.getName()
					+ genOperation.getParameterTypes(""), genOperation); //$NON-NLS-1$
			}

			for (Iterator j = UML2GenModelUtil.getDuplicateGenOperations(
				genClass).iterator(); j.hasNext();) {

				GenOperation genOperation = (GenOperation) j.next();
				implementedGenOperations.put(genOperation.getName()
					+ genOperation.getParameterTypes(""), genOperation); //$NON-NLS-1$
			}
		}

		return collectGenOperations(null, new ArrayList(
			implementedGenOperations.values()), filter);
	}

	public List getImplementedGenOperations() {
		return getImplementedGenOperations(new CollidingGenOperationFilter() {

			public boolean accept(GenOperation genOperation) {
				return super.accept(genOperation) && !isRedefined(genOperation);
			}
		});
	}

	public List getExtendedGenOperations() {
		return collectDuplicateGenOperations(getExtendedGenClasses(), null,
			null);
	}

	public String getImportedOperationsClassName() {
		return getGenModel().getImportedName(getQualifiedOperationsClassName());
	}

	public String getOperationsClassName() {
		return getName() + "Operations"; //$NON-NLS-1$
	}

	public String getQualifiedOperationsClassName() {
		return UML2GenModelUtil.getOperationsPackageName(getGenPackage()) + "." //$NON-NLS-1$
			+ getOperationsClassName();
	}

	public String getOperationID(GenOperation genOperation) {

		if (genOperation.isInvariant()) {
			String prefix = UML2GenModelUtil.getInvariantPrefix(getGenModel());

			if (!isBlank(prefix)) {
				return getClassifierID()
					+ "__" //$NON-NLS-1$
					+ format(genOperation.getName(), '_', prefix, false)
						.toUpperCase();
			}
		}

		return super.getOperationID(genOperation);
	}

	public List getEInverseAddGenFeatures() {
		return collectGenFeatures(null, super.getEInverseAddGenFeatures(),
			new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return !genFeature.isDerived();
				}
			});
	}

	public List getEInverseRemoveGenFeatures() {
		return collectGenFeatures(null, super.getEInverseRemoveGenFeatures(),
			new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return !genFeature.isDerived();
				}
			});
	}

	protected String getSubsetListConstructor(GenFeature genFeature) {
		StringBuffer sb = new StringBuffer();

		String unsettable = genFeature.isUnsettable()
			? ".Unsettable" //$NON-NLS-1$
			: ""; //$NON-NLS-1$

		if (genFeature.isMapType()) {
			return super.getListConstructor(genFeature);
		} else if (genFeature.isContains()) {
			if (genFeature.isBidirectional()) {
				GenFeature reverseFeature = genFeature.getReverse();
				sb
					.append(getGenModel()
						.getImportedName(
							"org.eclipse.uml2.common.util.SubsetEObjectContainmentWithInverseEList")); //$NON-NLS-1$
				sb.append(unsettable);
				sb.append("("); //$NON-NLS-1$
				sb.append(genFeature.getListItemType());
				sb.append(".class, this, "); //$NON-NLS-1$
				sb.append(getQualifiedFeatureID(genFeature));
				sb.append(", "); //$NON-NLS-1$
				sb.append(getSupersetFeatureIDArray(genFeature));
				sb.append(", "); //$NON-NLS-1$
				sb.append(reverseFeature.getGenClass().getQualifiedFeatureID(
					reverseFeature));
				sb.append(")"); //$NON-NLS-1$
			} else {
				sb
					.append(getGenModel()
						.getImportedName(
							"org.eclipse.uml2.common.util.SubsetEObjectContainmentEList")); //$NON-NLS-1$
				sb.append(unsettable);
				sb.append("("); //$NON-NLS-1$
				sb.append(genFeature.getListItemType());
				sb.append(".class, this, "); //$NON-NLS-1$
				sb.append(getQualifiedFeatureID(genFeature));
				sb.append(", "); //$NON-NLS-1$
				sb.append(getSupersetFeatureIDArray(genFeature));
				sb.append(")"); //$NON-NLS-1$
			}
		} else if (genFeature.isReferenceType()) {
			if (genFeature.isBidirectional()) {
				GenFeature reverseFeature = genFeature.getReverse();
				if (genFeature.isResolveProxies()) {
					sb
						.append(getGenModel()
							.getImportedName(
								"org.eclipse.uml2.common.util.SubsetEObjectWithInverseResolvingEList")); //$NON-NLS-1$
				} else {
					sb
						.append(getGenModel()
							.getImportedName(
								"org.eclipse.uml2.common.util.SubsetEObjectWithInverseEList")); //$NON-NLS-1$
				}
				sb.append(unsettable);
				if (reverseFeature.isListType()) {
					sb.append(".ManyInverse"); //$NON-NLS-1$
				}
				sb.append("("); //$NON-NLS-1$
				sb.append(genFeature.getListItemType());
				sb.append(".class, this, "); //$NON-NLS-1$
				sb.append(getQualifiedFeatureID(genFeature));
				sb.append(", "); //$NON-NLS-1$
				sb.append(getSupersetFeatureIDArray(genFeature));
				sb.append(", "); //$NON-NLS-1$
				sb.append(reverseFeature.getGenClass().getQualifiedFeatureID(
					reverseFeature));
				sb.append(")"); //$NON-NLS-1$
			} else {
				if (genFeature.isResolveProxies()) {
					sb
						.append(getGenModel()
							.getImportedName(
								"org.eclipse.uml2.common.util.SubsetEObjectResolvingEList")); //$NON-NLS-1$
				} else {
					sb.append(getGenModel().getImportedName(
						"org.eclipse.uml2.common.util.SubsetEObjectEList")); //$NON-NLS-1$
				}
				sb.append(unsettable);
				sb.append("("); //$NON-NLS-1$
				sb.append(genFeature.getListItemType());
				sb.append(".class, this, "); //$NON-NLS-1$
				sb.append(getQualifiedFeatureID(genFeature));
				sb.append(", "); //$NON-NLS-1$
				sb.append(getSupersetFeatureIDArray(genFeature));
				sb.append(")"); //$NON-NLS-1$
			}
		} else if (genFeature.isFeatureMapType()) {
			return super.getListConstructor(genFeature);
		} else { // datatype
			return super.getListConstructor(genFeature);
		}
		return sb.toString();
	}

	protected String getSupersetListConstructor(GenFeature genFeature) {
		StringBuffer sb = new StringBuffer();

		String unsettable = genFeature.isUnsettable()
			? ".Unsettable" //$NON-NLS-1$
			: ""; //$NON-NLS-1$

		if (genFeature.isMapType()) {
			return super.getListConstructor(genFeature);
		} else if (genFeature.isContains()) {
			if (genFeature.isBidirectional()) {
				GenFeature reverseFeature = genFeature.getReverse();
				sb
					.append(getGenModel()
						.getImportedName(
							"org.eclipse.uml2.common.util.SupersetEObjectContainmentWithInverseEList")); //$NON-NLS-1$
				sb.append(unsettable);
				sb.append("("); //$NON-NLS-1$
				sb.append(genFeature.getListItemType());
				sb.append(".class, this, "); //$NON-NLS-1$
				sb.append(getQualifiedFeatureID(genFeature));
				sb.append(", "); //$NON-NLS-1$
				sb.append(getSubsetFeatureIDArray(genFeature));
				sb.append(", "); //$NON-NLS-1$
				sb.append(reverseFeature.getGenClass().getQualifiedFeatureID(
					reverseFeature));
				sb.append(")"); //$NON-NLS-1$
			} else {
				sb
					.append(getGenModel()
						.getImportedName(
							"org.eclipse.uml2.common.util.SupersetEObjectContainmentEList")); //$NON-NLS-1$
				sb.append(unsettable);
				sb.append("("); //$NON-NLS-1$
				sb.append(genFeature.getListItemType());
				sb.append(".class, this, "); //$NON-NLS-1$
				sb.append(getQualifiedFeatureID(genFeature));
				sb.append(", "); //$NON-NLS-1$
				sb.append(getSubsetFeatureIDArray(genFeature));
				sb.append(")"); //$NON-NLS-1$
			}
		} else if (genFeature.isReferenceType()) {
			if (genFeature.isBidirectional()) {
				GenFeature reverseFeature = genFeature.getReverse();
				if (genFeature.isResolveProxies()) {
					sb
						.append(getGenModel()
							.getImportedName(
								"org.eclipse.uml2.common.util.SupersetEObjectWithInverseResolvingEList")); //$NON-NLS-1$
				} else {
					sb
						.append(getGenModel()
							.getImportedName(
								"org.eclipse.uml2.common.util.SupersetEObjectWithInverseEList")); //$NON-NLS-1$
				}
				sb.append(unsettable);
				if (reverseFeature.isListType()) {
					sb.append(".ManyInverse"); //$NON-NLS-1$
				}
				sb.append("("); //$NON-NLS-1$
				sb.append(genFeature.getListItemType());
				sb.append(".class, this, "); //$NON-NLS-1$
				sb.append(getQualifiedFeatureID(genFeature));
				sb.append(", "); //$NON-NLS-1$
				sb.append(getSubsetFeatureIDArray(genFeature));
				sb.append(", "); //$NON-NLS-1$
				sb.append(reverseFeature.getGenClass().getQualifiedFeatureID(
					reverseFeature));
				sb.append(")"); //$NON-NLS-1$
			} else {
				if (genFeature.isResolveProxies()) {
					sb
						.append(getGenModel()
							.getImportedName(
								"org.eclipse.uml2.common.util.SupersetEObjectResolvingEList")); //$NON-NLS-1$
				} else {
					sb.append(getGenModel().getImportedName(
						"org.eclipse.uml2.common.util.SupersetEObjectEList")); //$NON-NLS-1$
				}
				sb.append(unsettable);
				sb.append("("); //$NON-NLS-1$
				sb.append(genFeature.getListItemType());
				sb.append(".class, this, "); //$NON-NLS-1$
				sb.append(getQualifiedFeatureID(genFeature));
				sb.append(", "); //$NON-NLS-1$
				sb.append(getSubsetFeatureIDArray(genFeature));
				sb.append(")"); //$NON-NLS-1$
			}
		} else if (genFeature.isFeatureMapType()) {
			return super.getListConstructor(genFeature);
		} else { // datatype
			return super.getListConstructor(genFeature);
		}
		return sb.toString();
	}

	public String getListConstructor(GenFeature genFeature) {

		if (isSuperset(genFeature)
			&& !collectGenFeatures(null, getSubsetGenFeatures(genFeature),
				new GenFeatureFilter() {

					public boolean accept(GenFeature genFeature) {
						return !genFeature.isDerived();
					}
				}).isEmpty()) {

			return getSupersetListConstructor(genFeature);
		}

		if (UML2GenModelUtil.isSubset(genFeature)
			&& !collectGenFeatures(null, getSupersetGenFeatures(genFeature),
				new GenFeatureFilter() {

					public boolean accept(GenFeature genFeature) {
						return !genFeature.isDerived();
					}
				}).isEmpty()) {

			return getSubsetListConstructor(genFeature);
		}

		return super.getListConstructor(genFeature);
	}

	public GenFeature findGenFeature(EStructuralFeature ecoreFeature) {
		org.eclipse.emf.codegen.ecore.genmodel.GenClass genClass = findGenClass(Generator
			.getEcoreContainingClass(ecoreFeature));

		for (Iterator i = UML2GenModelUtil.getDuplicateGenFeatures(genClass)
			.iterator(); i.hasNext();) {

			GenFeature genFeature = (GenFeature) i.next();

			if (ecoreFeature.getName().equals(
				genFeature.getEcoreFeature().getName())) {
				return genFeature;
			}
		}

		return null;
	}

	public GenOperation findGenOperation(EOperation ecoreOperation) {
		org.eclipse.emf.codegen.ecore.genmodel.GenClass genClass = findGenClass(Generator
			.getEcoreContainingClass(ecoreOperation));

		genOperationsLoop : for (Iterator i = UML2GenModelUtil
			.getDuplicateGenOperations(genClass).iterator(); i.hasNext();) {

			GenOperation genOperation = (GenOperation) i.next();

			if (ecoreOperation.getName().equals(
				genOperation.getEcoreOperation().getName())
				&& ecoreOperation.getEParameters().size() == genOperation
					.getEcoreOperation().getEParameters().size()) {

				for (int j = 0; j < ecoreOperation.getEParameters().size(); j++) {
					EParameter ecoreParameter = (EParameter) ecoreOperation
						.getEParameters().get(j);

					if (!ecoreParameter.getEType().getName().equals(
						((EParameter) genOperation.getEcoreOperation()
							.getEParameters().get(j)).getEType().getName())) {

						continue genOperationsLoop;
					}
				}

				return genOperation;
			}
		}

		return null;
	}

	public List getUnionGenFeatures() {
		List unionGenFeatures = new UniqueEList();

		for (Iterator i = getDuplicateGenFeatures().iterator(); i.hasNext();) {
			GenFeature genFeature = (GenFeature) i.next();

			if (UML2GenModelUtil.isUnion(genFeature)) {
				unionGenFeatures.add(genFeature);
			}

			for (Iterator j = UML2GenModelUtil.getSubsettedGenFeatures(
				genFeature).iterator(); j.hasNext();) {

				GenFeature subsettedGenFeature = (GenFeature) j.next();

				if (UML2GenModelUtil.isUnion(subsettedGenFeature)) {
					unionGenFeatures.add(subsettedGenFeature);
				}
			}
		}

		return unionGenFeatures;
	}

	public List getImplementedUnionGenFeatures() {
		return getImplementedGenFeatures(new GenFeatureFilter() {

			public boolean accept(GenFeature genFeature) {
				return UML2GenModelUtil.isUnion(genFeature)
					&& !isRedefined(genFeature);
			}
		});
	}

	public boolean isSuperset(GenFeature genFeature) {
		final EStructuralFeature ecoreFeature = genFeature.getEcoreFeature();

		return !genFeature.isDerived()
			&& !collectGenFeatures(null, getAllDuplicateGenFeatures(),
				new GenFeatureFilter() {

					public boolean accept(GenFeature genFeature) {
						return Generator.getSubsettedEcoreFeatures(
							genFeature.getEcoreFeature())
							.contains(ecoreFeature);
					}
				}).isEmpty();
	}

	public List getSupersetGenFeatures() {
		List supersetGenFeatures = new UniqueEList();

		for (Iterator i = getDuplicateGenFeatures().iterator(); i.hasNext();) {
			GenFeature genFeature = (GenFeature) i.next();

			if (isSuperset(genFeature)) {
				supersetGenFeatures.add(genFeature);
			}

			for (Iterator j = UML2GenModelUtil.getSubsettedGenFeatures(
				genFeature).iterator(); j.hasNext();) {

				GenFeature subsettedGenFeature = (GenFeature) j.next();

				if (isSuperset(subsettedGenFeature)) {
					supersetGenFeatures.add(subsettedGenFeature);
				}
			}
		}

		return supersetGenFeatures;
	}

	public List getSubsetGenFeatures(GenFeature supersetGenFeature) {
		Map subsetGenFeatures = new LinkedHashMap();

		final EStructuralFeature supersetEcoreFeature = supersetGenFeature
			.getEcoreFeature();

		for (Iterator i = getAllDuplicateGenFeatures().iterator(); i.hasNext();) {
			GenFeature genFeature = (GenFeature) i.next();

			if (Generator.getSubsettedEcoreFeatures(
				genFeature.getEcoreFeature()).contains(supersetEcoreFeature)) {

				subsetGenFeatures.put(genFeature.getName(), genFeature);
			}
		}

		return new ArrayList(subsetGenFeatures.values());
	}

	public List getSubsetGenFeatures() {
		return collectGenFeatures(null, getDuplicateGenFeatures(),
			new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return UML2GenModelUtil.isSubset(genFeature);
				}
			});
	}

	public List getImplementedSubsetGenFeatures() {
		return getImplementedGenFeatures(new GenFeatureFilter() {

			public boolean accept(GenFeature genFeature) {
				return UML2GenModelUtil.isSubset(genFeature)
					&& !collectGenFeatures(null,
						getSupersetGenFeatures(genFeature),
						new GenFeatureFilter() {

							public boolean accept(GenFeature genFeature) {
								return !genFeature.isDerived();
							}
						}).isEmpty();
			}
		});
	}

	public List getImplementedSubsetGenFeatures(final boolean listType) {
		return collectGenFeatures(null, getImplementedSubsetGenFeatures(),
			new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return listType == genFeature.isListType();
				}
			});
	}

	public String getSubsetFeatureAccessorArray(GenFeature supersetGenFeature) {
		StringBuffer sb = new StringBuffer();

		sb.append("new "); //$NON-NLS-1$
		sb.append(getGenModel().getImportedName(
			"org.eclipse.emf.ecore.EStructuralFeature")); //$NON-NLS-1$
		sb.append("[] {"); //$NON-NLS-1$

		Iterator subsetGenFeatures = collectGenFeatures(null,
			getSubsetGenFeatures(supersetGenFeature), new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return !genFeature.isDerived();
				}
			}).iterator();

		while (subsetGenFeatures.hasNext()) {
			GenFeature subsetGenFeature = (GenFeature) subsetGenFeatures.next();

			sb.append(subsetGenFeature.getQualifiedFeatureAccessorName());
			sb.append("()"); //$NON-NLS-1$

			if (subsetGenFeatures.hasNext()) {
				sb.append(", "); //$NON-NLS-1$
			}
		}

		sb.append("}"); //$NON-NLS-1$

		return sb.toString();
	}

	protected String getSubsetFeatureIDArray(GenFeature supersetGenFeature) {
		StringBuffer sb = new StringBuffer();

		sb.append("new int[] {"); //$NON-NLS-1$

		Iterator subsetGenFeatures = collectGenFeatures(null,
			getSubsetGenFeatures(supersetGenFeature), new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return !genFeature.isDerived();
				}
			}).iterator();

		while (subsetGenFeatures.hasNext()) {
			GenFeature subsetGenFeature = (GenFeature) subsetGenFeatures.next();

			sb.append(getQualifiedFeatureID(subsetGenFeature));

			if (subsetGenFeatures.hasNext()) {
				sb.append(", "); //$NON-NLS-1$
			}
		}

		sb.append("}"); //$NON-NLS-1$

		return sb.toString();
	}

	public List getSupersetGenFeatures(GenFeature subsetGenFeature) {
		return UML2GenModelUtil.getSubsettedGenFeatures(subsetGenFeature);
	}

	public List getImplementedSupersetGenFeatures() {
		return getImplementedGenFeatures(new GenFeatureFilter() {

			public boolean accept(GenFeature genFeature) {
				return isSuperset(genFeature)
					&& !collectGenFeatures(null,
						getSubsetGenFeatures(genFeature),
						new GenFeatureFilter() {

							public boolean accept(GenFeature genFeature) {
								return !genFeature.isDerived();
							}
						}).isEmpty();
			}
		});
	}

	public List getImplementedSupersetGenFeatures(final boolean listType) {
		return collectGenFeatures(null, getImplementedSupersetGenFeatures(),
			new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return listType == genFeature.isListType();
				}
			});
	}

	public String getSupersetFeatureAccessorArray(GenFeature subsetGenFeature) {
		StringBuffer sb = new StringBuffer();

		sb.append("new "); //$NON-NLS-1$
		sb.append(getGenModel().getImportedName(
			"org.eclipse.emf.ecore.EStructuralFeature")); //$NON-NLS-1$
		sb.append("[] {"); //$NON-NLS-1$

		Iterator supersetGenFeatures = collectGenFeatures(null,
			getSupersetGenFeatures(subsetGenFeature), new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return !genFeature.isDerived();
				}
			}).iterator();

		while (supersetGenFeatures.hasNext()) {
			GenFeature supersetGenFeature = (GenFeature) supersetGenFeatures
				.next();

			sb.append(supersetGenFeature.getQualifiedFeatureAccessorName());
			sb.append("()"); //$NON-NLS-1$

			if (supersetGenFeatures.hasNext()) {
				sb.append(", "); //$NON-NLS-1$
			}
		}

		sb.append("}"); //$NON-NLS-1$

		return sb.toString();
	}

	protected String getSupersetFeatureIDArray(GenFeature subsetGenFeature) {
		StringBuffer sb = new StringBuffer();

		sb.append("new int[] {"); //$NON-NLS-1$

		Iterator supersetGenFeatures = collectGenFeatures(null,
			getSupersetGenFeatures(subsetGenFeature), new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return !genFeature.isDerived();
				}
			}).iterator();

		while (supersetGenFeatures.hasNext()) {
			GenFeature supersetGenFeature = (GenFeature) supersetGenFeatures
				.next();

			sb.append(getQualifiedFeatureID(supersetGenFeature));

			if (supersetGenFeatures.hasNext()) {
				sb.append(", "); //$NON-NLS-1$
			}
		}

		sb.append("}"); //$NON-NLS-1$

		return sb.toString();
	}

	public boolean isRedefined(GenFeature genFeature) {
		final EStructuralFeature ecoreFeature = genFeature.getEcoreFeature();

		return !collectGenFeatures(null, getAllDuplicateGenFeatures(),
			new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return Generator.getRedefinedEcoreFeatures(
						genFeature.getEcoreFeature()).contains(ecoreFeature);
				}
			}).isEmpty();
	}

	public List getRedefinedGenFeatures() {
		List redefinedGenFeatures = new UniqueEList();

		for (Iterator i = getDuplicateGenFeatures().iterator(); i.hasNext();) {
			GenFeature genFeature = (GenFeature) i.next();

			if (isRedefined(genFeature)) {
				redefinedGenFeatures.add(genFeature);
			}

			for (Iterator j = UML2GenModelUtil.getRedefinedGenFeatures(
				genFeature).iterator(); j.hasNext();) {

				GenFeature redefinedGenFeature = (GenFeature) j.next();

				if (!genFeature.getName().equals(redefinedGenFeature.getName())) {

					redefinedGenFeatures.add(redefinedGenFeature);
				}
			}
		}

		return redefinedGenFeatures;
	}

	public List getImplementedRedefinedGenFeatures() {
		return getImplementedGenFeatures(new GenFeatureFilter() {

			public boolean accept(GenFeature genFeature) {
				return isRedefined(genFeature);
			}
		});
	}

	public List getRedefinitionGenFeatures(GenFeature redefinedGenFeature) {
		Map redefinitionGenFeatures = new LinkedHashMap();

		EStructuralFeature redefinedEcoreFeature = redefinedGenFeature
			.getEcoreFeature();

		for (Iterator i = getAllDuplicateGenFeatures().iterator(); i.hasNext();) {
			GenFeature genFeature = (GenFeature) i.next();

			if (Generator.getRedefinedEcoreFeatures(
				genFeature.getEcoreFeature()).contains(redefinedEcoreFeature)) {

				if (!genFeature.getName().equals(redefinedGenFeature.getName())) {
					redefinitionGenFeatures.put(genFeature.getName(),
						genFeature);
				}
			}
		}

		return new ArrayList(redefinitionGenFeatures.values());
	}

	public boolean isRedefined(GenOperation genOperation) {
		final EOperation ecoreOperation = genOperation.getEcoreOperation();

		return !collectGenOperations(null, getAllDuplicateGenOperations(),
			new GenOperationFilter() {

				public boolean accept(GenOperation genOperation) {
					return Generator.getRedefinedEcoreOperations(
						genOperation.getEcoreOperation()).contains(
						ecoreOperation);
				}
			}).isEmpty();
	}

	public List getRedefinedGenOperations() {
		List redefinedGenOperations = new UniqueEList();

		for (Iterator i = getDuplicateGenOperations().iterator(); i.hasNext();) {
			GenOperation genOperation = (GenOperation) i.next();

			if (isRedefined(genOperation)) {
				redefinedGenOperations.add(genOperation);
			}

			for (Iterator j = UML2GenModelUtil.getRedefinedGenOperations(
				genOperation).iterator(); j.hasNext();) {

				GenOperation redefinedGenOperation = (GenOperation) j.next();

				if (!(genOperation.getName() + genOperation
					.getParameterTypes("")).equals(redefinedGenOperation //$NON-NLS-1$
					.getName() + redefinedGenOperation.getParameterTypes(""))) { //$NON-NLS-1$

					redefinedGenOperations.add(redefinedGenOperation);
				}
			}
		}

		return redefinedGenOperations;
	}

	public List getImplementedRedefinedGenOperations() {
		return getImplementedGenOperations(new CollidingGenOperationFilter() {

			public boolean accept(GenOperation genOperation) {
				return super.accept(genOperation) && isRedefined(genOperation);
			}
		});
	}

	public List getRedefinitionGenOperations(GenOperation redefinedGenOperation) {
		Map redefinitionGenOperations = new LinkedHashMap();

		EOperation redefinedEcoreOperation = redefinedGenOperation
			.getEcoreOperation();

		for (Iterator i = getAllDuplicateGenOperations().iterator(); i
			.hasNext();) {

			GenOperation genOperation = (GenOperation) i.next();

			if (Generator.getRedefinedEcoreOperations(
				genOperation.getEcoreOperation()).contains(
				redefinedEcoreOperation)) {

				if (!(genOperation.getName() + genOperation
					.getParameterTypes("")).equals(redefinedGenOperation //$NON-NLS-1$
					.getName() + redefinedGenOperation.getParameterTypes(""))) { //$NON-NLS-1$

					redefinitionGenOperations.put(genOperation.getName(),
						genOperation);
				}
			}
		}

		return new ArrayList(redefinitionGenOperations.values());
	}

	public List getKeyGenFeatures() {
		return collectGenFeatures(null, getAllGenFeatures(),
			new GenFeatureFilter() {

				public boolean accept(GenFeature genFeature) {
					return UML2GenModelUtil.isKey(genFeature);
				}
			});
	}

	public boolean isESetField(GenFeature genFeature) {
		return super.isESetField(genFeature)
			&& !UML2GenModelUtil.isUnion(genFeature)
			&& !isRedefined(genFeature);
	}

	public boolean isField(GenFeature genFeature) {
		return super.isField(genFeature)
			&& !UML2GenModelUtil.isUnion(genFeature)
			&& !isRedefined(genFeature);
	}

}
