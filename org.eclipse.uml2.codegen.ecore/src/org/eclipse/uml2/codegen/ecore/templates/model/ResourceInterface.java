package org.eclipse.uml2.codegen.ecore.templates.model;

import org.eclipse.emf.codegen.ecore.genmodel.*;
import org.eclipse.uml2.codegen.ecore.genmodel.util.UML2GenModelUtil;

public class ResourceInterface
{
  protected static String nl;
  public static synchronized ResourceInterface create(String lineSeparator)
  {
    nl = lineSeparator;
    ResourceInterface result = new ResourceInterface();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = "/**" + NL + " * <copyright>" + NL + " * </copyright>" + NL + " *" + NL + " * ";
  protected final String TEXT_3 = "Id";
  protected final String TEXT_4 = NL + " */" + NL + "package ";
  protected final String TEXT_5 = ";" + NL;
  protected final String TEXT_6 = NL + NL + "/**" + NL + " * <!-- begin-user-doc -->" + NL + " * The resource associated with the '<em><b>";
  protected final String TEXT_7 = "</b></em>' package." + NL + " * <!-- end-user-doc -->" + NL + " * @generated" + NL + " */" + NL + "public interface ";
  protected final String TEXT_8 = " extends ";
  protected final String TEXT_9 = NL + "{";
  protected final String TEXT_10 = NL + "\t/**" + NL + "\t * <!-- begin-user-doc -->" + NL + "\t * <!-- end-user-doc -->" + NL + "\t * @generated" + NL + "\t */" + NL + "\t";
  protected final String TEXT_11 = " copyright = \"";
  protected final String TEXT_12 = "\";";
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = NL + "\t/**" + NL + "\t * <!-- begin-user-doc -->" + NL + "\t * The factory for '<em><b>";
  protected final String TEXT_15 = "</b></em>' resources." + NL + "\t * <!-- end-user-doc -->" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic interface Factory" + NL + "\t\t\textends ";
  protected final String TEXT_16 = " {" + NL + "" + NL + "\t\t/**" + NL + "\t\t * <!-- begin-user-doc -->" + NL + "\t\t * <!-- end-user-doc -->" + NL + "\t\t * @generated" + NL + "\t\t */" + NL + "\t\tpublic static final Factory INSTANCE = new ";
  protected final String TEXT_17 = "();" + NL + "" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * <!-- begin-user-doc -->" + NL + "\t * The file extension for '<em><b>";
  protected final String TEXT_18 = "</b></em>' resources." + NL + "\t * <!-- end-user-doc -->" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic static final String FILE_EXTENSION = \"";
  protected final String TEXT_19 = "\";";
  protected final String TEXT_20 = NL;
  protected final String TEXT_21 = NL + "\t/**" + NL + "\t * <!-- begin-user-doc -->" + NL + "\t * The default encoding for '<em><b>";
  protected final String TEXT_22 = "</b></em>' resources." + NL + "\t * <!-- end-user-doc -->" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic static final String DEFAULT_ENCODING = \"UTF-8\";";
  protected final String TEXT_23 = NL;
  protected final String TEXT_24 = NL + "} //";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
    
/**
 * <copyright>
 *
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - initial API and implementation
 *
 * </copyright>
 */

    GenPackage genPackage = (GenPackage)argument; GenModel genModel=genPackage.getGenModel();
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    stringBuffer.append("$");
    stringBuffer.append(TEXT_3);
    stringBuffer.append("$");
    stringBuffer.append(TEXT_4);
    stringBuffer.append(genPackage.getUtilitiesPackageName());
    stringBuffer.append(TEXT_5);
    genModel.markImportLocation(stringBuffer);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(genPackage.getPackageName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(UML2GenModelUtil.getResourceInterfaceName(genPackage));
    stringBuffer.append(TEXT_8);
    stringBuffer.append(UML2GenModelUtil.getImportedResourceBaseInterfaceName(genPackage));
    stringBuffer.append(TEXT_9);
    if (genModel.getCopyrightText() != null) {
    stringBuffer.append(TEXT_10);
    stringBuffer.append(genModel.getImportedName("java.lang.String"));
    stringBuffer.append(TEXT_11);
    stringBuffer.append(genModel.getCopyrightText());
    stringBuffer.append(TEXT_12);
    stringBuffer.append(genModel.getNonNLS());
    stringBuffer.append(TEXT_13);
    }
    stringBuffer.append(TEXT_14);
    stringBuffer.append(genPackage.getPackageName());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(UML2GenModelUtil.getImportedResourceFactoryBaseInterfaceName(genPackage));
    stringBuffer.append(TEXT_16);
    stringBuffer.append(genPackage.getImportedResourceFactoryClassName());
    stringBuffer.append(TEXT_17);
    stringBuffer.append(genPackage.getPackageName());
    stringBuffer.append(TEXT_18);
    stringBuffer.append(genPackage.getPrefix().toLowerCase());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(genModel.getNonNLS());
    stringBuffer.append(TEXT_20);
    if (UML2GenModelUtil.isXMLResource(genPackage)) {
    stringBuffer.append(TEXT_21);
    stringBuffer.append(genPackage.getPackageName());
    stringBuffer.append(TEXT_22);
    stringBuffer.append(genModel.getNonNLS());
    stringBuffer.append(TEXT_23);
    }
    stringBuffer.append(TEXT_24);
    stringBuffer.append(UML2GenModelUtil.getResourceInterfaceName(genPackage));
    genModel.emitSortedImports();
    return stringBuffer.toString();
  }
}
