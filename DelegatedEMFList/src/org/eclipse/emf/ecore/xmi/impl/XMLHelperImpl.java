/**
 * <copyright>
 *
 * Copyright (c) 2002-2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: XMLHelperImpl.java,v 1.1 2009-03-04 23:05:15 andrew Exp $
 */
package org.eclipse.emf.ecore.xmi.impl;


import java.util.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.ecore.util.*;
import org.eclipse.emf.ecore.xmi.*;
import org.eclipse.emf.ecore.xml.type.*;
import org.eclipse.emf.ecore.xml.type.internal.*;

import com.hopstepjump.emflist.*;


/**
 * This class handles the package to use when there is no XML
 * namespace in an XML file.
 */
public class XMLHelperImpl implements XMLHelper
{
  protected static final Integer INTEGER_DATATYPE_IS_MANY = new Integer(DATATYPE_IS_MANY);
  protected static final Integer INTEGER_DATATYPE_SINGLE  = new Integer(DATATYPE_SINGLE);
  protected static final Integer INTEGER_IS_MANY_ADD      = new Integer(IS_MANY_ADD);
  protected static final Integer INTEGER_IS_MANY_MOVE     = new Integer(IS_MANY_MOVE);

  protected EPackage noNamespacePackage;
  protected XMLResource.XMLMap xmlMap;
  protected ExtendedMetaData extendedMetaData;
  protected boolean laxFeatureProcessing;
  protected EPackage.Registry packageRegistry;
  protected XMLResource resource;
  protected URI resourceURI;
  protected boolean deresolve;
  protected Map packages;
  protected Map featuresToKinds;
  protected String processDanglingHREF;
  protected DanglingHREFException danglingHREFException;
  protected EMap prefixesToURIs;
  protected Map anyPrefixesToURIs;
  protected NamespaceSupport namespaceSupport;
  protected EClass anySimpleType;
  // true if seen xmlns="" declaration
  protected boolean seenEmptyStringMapping;
  protected EPackage xmlSchemaTypePackage = XMLTypePackage.eINSTANCE;
  protected List allPrefixToURI;
  protected boolean checkForDuplicates;
  protected boolean mustHavePrefix;
  
  private EPackage previousPackage;
  private String previousNS;
  
  public static String saveString(Map options, List contents, String encoding, XMLHelper helper) throws Exception
  {
    if (helper == null)
    {
      helper = new XMIHelperImpl();
    }
    if (!options.containsKey(XMLResource.OPTION_DECLARE_XML))
    {
      options = new HashMap(options);
      options.put(XMLResource.OPTION_DECLARE_XML, Boolean.FALSE);
    }
    XMLSaveImpl save = new XMISaveImpl(options, helper, encoding);
    
    if (Boolean.TRUE.equals(options.get(XMLResource.OPTION_DEFER_IDREF_RESOLUTION)))
    {
      ((XMLHelperImpl)helper).checkForDuplicates = true;
    }
    
    ((XMLHelperImpl)helper).processDanglingHREF = (String)options.get(XMLResource.OPTION_PROCESS_DANGLING_HREF);
    save.traverse(contents);
    if (save.useCache)
    {
      ConfigurationCache.INSTANCE.releasePrinter(save.doc);
      if (save.escape != null)
      {
        ConfigurationCache.INSTANCE.releaseEscape(save.escape);
      } 
    }
    char[] chars = save.toChar();
    return new String(chars);
  }

  public XMLHelperImpl()
  {
    super();
    packages = new HashMap();
    featuresToKinds = new HashMap();
    prefixesToURIs = new BasicEMap();
    anyPrefixesToURIs = new HashMap();
    allPrefixToURI = new ArrayList();
    namespaceSupport = new NamespaceSupport();
  }

  public XMLHelperImpl(XMLResource resource)
  {
    this();
    setResource(resource);
  }
  
  public void setOptions (Map options)
  {
    laxFeatureProcessing = Boolean.TRUE.equals(options.get(XMLResource.OPTION_LAX_FEATURE_PROCESSING));
  }

  public void setNoNamespacePackage(EPackage pkg)
  {
    noNamespacePackage = pkg;
  }

  public EPackage getNoNamespacePackage()
  {
    return 
      noNamespacePackage != null ?
        noNamespacePackage :
        extendedMetaData != null ?
          extendedMetaData.getPackage(null) :
          null;
  }

  public void setXMLMap(XMLResource.XMLMap map)
  {
    xmlMap = map;
    if (map != null && map.getNoNamespacePackage() != null)
    {
      setNoNamespacePackage(map.getNoNamespacePackage());
    }
  }

  public XMLResource.XMLMap getXMLMap()
  {
    return xmlMap;
  }

  public void setExtendedMetaData(ExtendedMetaData extendedMetaData)
  {
    this.extendedMetaData = extendedMetaData;
    if (extendedMetaData != null && extendedMetaData.getPackage(null) != null)
    {
      setNoNamespacePackage(extendedMetaData.getPackage(null));
    }
  }

  public ExtendedMetaData getExtendedMetaData()
  {
    return extendedMetaData;
  }

  public XMLResource getResource()
  {
    return resource;
  }

  public void setResource(XMLResource resource)
  {
    this.resource = resource;
    if (resource == null)
    {
      resourceURI = null;
      deresolve = false;
      packageRegistry = EPackage.Registry.INSTANCE;
    }
    else
    {
      resourceURI = resource.getURI();
      deresolve = resourceURI != null && !resourceURI.isRelative() && resourceURI.isHierarchical();
      packageRegistry = resource.getResourceSet() == null ? EPackage.Registry.INSTANCE : resource.getResourceSet().getPackageRegistry();
    }
  }

  public Object getValue(EObject obj, EStructuralFeature f)
  {
    return obj.eGet(f, false);
  }

  public String getQName(EClass c)
  {
    String name = getName(c);
    if (xmlMap != null)
    {
      XMLResource.XMLInfo clsInfo = xmlMap.getInfo(c);

      if (clsInfo != null)
      {
        String targetNamespace = clsInfo.getTargetNamespace();
        return getQName(targetNamespace, name);
      }
    }

    return getQName(c.getEPackage(), name);
  }

  public void populateNameInfo(NameInfo nameInfo, EClass c)
  {
    String name = getName(c);
    nameInfo.setLocalPart(name);
    if (xmlMap != null)
    {
      XMLResource.XMLInfo clsInfo = xmlMap.getInfo(c);

      if (clsInfo != null)
      {
        String targetNamespace = clsInfo.getTargetNamespace();
        nameInfo.setNamespaceURI(targetNamespace);
        nameInfo.setQualifiedName(getQName(targetNamespace, name));
        return;
      }
    }
    getQName(nameInfo, c.getEPackage(), name);
  }

  public String getQName(EDataType c)
  {
    String name = getName(c);
    if (xmlMap != null)
    {
      XMLResource.XMLInfo clsInfo = xmlMap.getInfo(c);

      if (clsInfo != null)
      {
        String targetNamespace = clsInfo.getTargetNamespace();
        return getQName(targetNamespace, name);
      }
    }

    return getQName(c.getEPackage(), name);
  }

  public void populateNameInfo(NameInfo nameInfo, EDataType eDataType)
  {
    String name = getName(eDataType);
    nameInfo.setLocalPart(name);
    if (xmlMap != null)
    {
      XMLResource.XMLInfo clsInfo = xmlMap.getInfo(eDataType);

      if (clsInfo != null)
      {
        String targetNamespace = clsInfo.getTargetNamespace();
        nameInfo.setNamespaceURI(targetNamespace);
        nameInfo.setQualifiedName(getQName(targetNamespace, name));
        return;
      }
    }
    getQName(nameInfo, eDataType.getEPackage(), name);
  }

  public String getQName(EStructuralFeature feature)
  {
    if (extendedMetaData != null)
    {
      String namespace = extendedMetaData.getNamespace(feature);
      String name = extendedMetaData.getName(feature);
      String result = name;

      // We need to be careful that we don't end up requiring the no namespace package 
      // just because the feature is unqualified.
      //
      if (namespace != null)
      {
        // There really must be a package.
        //
        EPackage ePackage;
        if (namespace.equals(previousNS))
        {
          ePackage = previousPackage;
        }
        else
        {
          ePackage = extendedMetaData.getPackage(namespace);
          if (ePackage == null)
          {
            ePackage = extendedMetaData.demandPackage(namespace);
          }
          previousPackage = ePackage;
          previousNS = namespace;
        }
        
        result = getQName(ePackage, name);

        // We must have a qualifier for an attribute that needs qualified.
        //
        if (result.length() == name.length() && extendedMetaData.getFeatureKind(feature) == ExtendedMetaData.ATTRIBUTE_FEATURE)
        {
          result = getQName(ePackage, name, true);
        }
      }
      return result;
    }

    String name = getName(feature);
    if (xmlMap != null)
    {
      XMLResource.XMLInfo info = xmlMap.getInfo(feature);
      if (info != null)
      {
        return getQName(info.getTargetNamespace(), name);
      }
    }

    return name;
  }

  public void populateNameInfo(NameInfo nameInfo, EStructuralFeature feature)
  {
    if (extendedMetaData != null)
    {
      String namespace = extendedMetaData.getNamespace(feature);
      String name = extendedMetaData.getName(feature);
      nameInfo.setNamespaceURI(namespace);
      nameInfo.setLocalPart(name);
      nameInfo.setQualifiedName(name);

      // We need to be careful that we don't end up requiring the no namespace package 
      // just because the feature is unqualified.
      //
      if (namespace != null)
      {
        // There really must be a package.
        //
        EPackage ePackage = extendedMetaData.getPackage(namespace);
        if (ePackage == null)
        {
          ePackage = extendedMetaData.demandPackage(namespace);
        }

        String result = getQName(nameInfo, ePackage, name);

        // We must have a qualifier for an attribute that needs qualified.
        //
        if (result.length() == name.length() && extendedMetaData.getFeatureKind(feature) == ExtendedMetaData.ATTRIBUTE_FEATURE)
        {
          getQName(nameInfo, ePackage, name, true);
        }
      }
    }
    else
    {
      String name = getName(feature);
      nameInfo.setNamespaceURI(null);
      nameInfo.setLocalPart(name);
      if (xmlMap != null)
      {
        XMLResource.XMLInfo info = xmlMap.getInfo(feature);
        if (info != null)
        {
          String targetNamespace = info.getTargetNamespace();
          nameInfo.setNamespaceURI(targetNamespace);
          nameInfo.setQualifiedName(getQName(targetNamespace, name));
        }
      }
      nameInfo.setQualifiedName(name);
    }
  }
  
  protected String getQName(NameInfo nameInfo, EPackage ePackage, String name)
  {
    String qname = getQName(nameInfo, ePackage, name, mustHavePrefix);
    nameInfo.setQualifiedName(qname);
    return qname;
  }

  protected String getQName(NameInfo nameInfo, EPackage ePackage, String name, boolean mustHavePrefix)
  {
    String nsPrefix = getPrefix(ePackage, mustHavePrefix);
    nameInfo.setNamespaceURI(getNamespaceURI(nsPrefix));
    if ("".equals(nsPrefix))
    {
      return name;
    }
    else if (name.length() == 0)
    {
      return nsPrefix;
    }
    else
    {
      return nsPrefix + ":" + name;
    }
  }

  protected String getQName(EPackage ePackage, String name)
  {
    return getQName(ePackage, name, mustHavePrefix);
  }

  protected String getQName(EPackage ePackage, String name, boolean mustHavePrefix)
  {
    String nsPrefix = getPrefix(ePackage, mustHavePrefix);
    if ("".equals(nsPrefix))
    {
      return name;
    }
    else if (name.length() == 0)
    {
      return nsPrefix;
    }
    else
    {
      return nsPrefix + ":" + name;
    }
  }

  public String getPrefix(EPackage ePackage)
  {
    return getPrefix(ePackage, mustHavePrefix);
  }
  
  public String getNamespaceURI(String prefix)
  {
    String namespaceURI = namespaceSupport.getURI(prefix);
    if (namespaceURI == null)
    {
      namespaceURI = (String)prefixesToURIs.get(prefix);
    }
    return namespaceURI;
  }

  protected String getPrefix(EPackage ePackage, boolean mustHavePrefix)
  {
    String nsPrefix = (String)packages.get(ePackage);
    if (nsPrefix == null || mustHavePrefix && nsPrefix.length() == 0)
    {
      String nsURI = 
        xmlSchemaTypePackage == ePackage ?
          XMLResource.XML_SCHEMA_URI :
          extendedMetaData == null ? 
            ePackage.getNsURI() : 
            extendedMetaData.getNamespace(ePackage);

      boolean found = false;
      for (Iterator i = prefixesToURIs.entrySet().iterator(); i.hasNext(); )
      {
        Map.Entry entry = (Map.Entry)i.next();
        if (nsURI == null ? entry.getValue() == null : nsURI.equals(entry.getValue()))
        {
          nsPrefix = (String)entry.getKey();
          if (!mustHavePrefix || nsPrefix.length() > 0)
          {
            found = true;
            break;
          }
        }
      }

      if (!found)
      {
        // for any content prefix to URI mapping could be in namespace context
        nsPrefix = namespaceSupport.getPrefix(nsURI);
        if (nsPrefix != null)
        {
          return nsPrefix;
        }

        if (nsURI != null)
        {
          nsPrefix = xmlSchemaTypePackage == ePackage ? "xsd" : ePackage.getNsPrefix();
        }
        if (nsPrefix == null)
        {
          nsPrefix = mustHavePrefix ? "_" : "";
        }
  
        if (prefixesToURIs.containsKey(nsPrefix))
        {
          String currentValue = (String)prefixesToURIs.get(nsPrefix);
          if (currentValue == null ? nsURI != null : !currentValue.equals(nsURI))
          {
            int index = 1;
            while (prefixesToURIs.containsKey(nsPrefix + "_" + index))
            {
              ++index;
            }
            nsPrefix += "_" + index;
          }
        }

        prefixesToURIs.put(nsPrefix, nsURI);
      }

      if (!packages.containsKey(ePackage))
      {
        packages.put(ePackage, nsPrefix);
      }
    }

    return nsPrefix;
  }

  public List getPrefixes(EPackage ePackage)
  {
    List result = new UniqueEList();
    result.add(getPrefix(ePackage));
    String namespace = extendedMetaData == null ? ePackage.getNsURI() : extendedMetaData.getNamespace(ePackage);
    for (Iterator i = prefixesToURIs.entrySet().iterator(); i.hasNext(); )
    {
      Map.Entry entry = (Map.Entry)i.next();
      if (namespace == null ? entry.getValue() == null : namespace.equals(entry.getValue()))
      {
        result.add(entry.getKey());
      }
    }
    return result;
  }

  protected String getQName(String uri, String name)
  {
    if (uri == null)
    {
      EPackage theNoNamespacePackage = getNoNamespacePackage();
      if (theNoNamespacePackage != null)
      {
        packages.put(theNoNamespacePackage, "");
      }

      return name;
    }

    EPackage ePackage = 
      extendedMetaData == null ?
        EPackage.Registry.INSTANCE.getEPackage(uri) :
        extendedMetaData.getPackage(uri);
    if (ePackage == null)
    {
      if (extendedMetaData != null)
      {
        return getQName(extendedMetaData.demandPackage(uri), name);
      }
      else
      {
        // EATM this would be wrong.
        return name;
      }
    }
    else
    {
      return getQName(ePackage, name);
    }
  }

  public String getName(ENamedElement obj)
  {
    if (extendedMetaData != null)
    {
      return 
        obj instanceof EStructuralFeature ? 
            extendedMetaData.getName((EStructuralFeature)obj) : 
              extendedMetaData.getName((EClassifier)obj);
    }
    
    if (xmlMap != null)
    {
      XMLResource.XMLInfo info = xmlMap.getInfo(obj);
      if (info != null)
      {
         String result = info.getName();
         if (result != null)
         {
           return result;
         }
      }
    }

    return obj.getName();
  }


  public String getID(EObject obj)
  {
    return resource == null ? null : resource.getID(obj);
  }

  protected String getURIFragmentQuery(Resource containingResource, EObject object)
  {
    return null;
  }

  protected String getURIFragment(Resource containingResource, EObject object)
  {
    String result = containingResource.getURIFragment(object);
    if (result.charAt(0) != '/')
    {
      String query = getURIFragmentQuery(containingResource, object);
      if (query != null)
      {
        result += "?" + query + "?";
      }
    }
    return result;
  }

  public String getIDREF(EObject obj)
  {
    return resource == null ? null : getURIFragment(resource, obj);
  }

  protected URI handleDanglingHREF(EObject object)
  {
    if (!XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD.equals(processDanglingHREF))
    {
      DanglingHREFException exception = new DanglingHREFException(
        "The object '" + object + "' is not contained in a resource.", 
        resource.getURI() == null ? "unknown" : resource.getURI().toString(), 0, 0);
 
      if (danglingHREFException == null)
      {
        danglingHREFException = exception;
      }
   
      resource.getErrors().add(exception);
    }

    return null;
  }

  public String getHREF(EObject obj)
  {
    InternalEObject o = (InternalEObject) obj;

    URI objectURI = o.eProxyURI();
    if (objectURI == null)
    {
      Resource otherResource = obj.eResource();
      if (otherResource == null)
      {
        objectURI = handleDanglingHREF(obj);
        if (objectURI == null)
        {
          return null;
        }
      }
      else
      {
        objectURI = getHREF(otherResource, obj);
      }
    }

    objectURI = deresolve(objectURI);

    return objectURI.toString();
  }

  protected URI getHREF(Resource otherResource, EObject obj)
  {
    return otherResource.getURI().appendFragment(getURIFragment(otherResource, obj));
  }

  public URI deresolve(URI uri)
  {
    if (deresolve && !uri.isRelative())
    {
      URI deresolvedURI = uri.deresolve(resourceURI, true, true, false);
      if (deresolvedURI.hasRelativePath())
      {
        uri = deresolvedURI;
      }
    }

    return uri;
  }

  public int getFeatureKind(EStructuralFeature feature)
  {
    Integer kind = (Integer) featuresToKinds.get(feature);
    if (kind != null)
    {
      return kind.intValue();
    }
    else
    {
      computeFeatureKind(feature);
      kind = (Integer) featuresToKinds.get(feature);
      if (kind != null)
      {
        return kind.intValue();
      }
      else
      {
        return OTHER;
      }
    }
  }

  public EObject createObject(EFactory eFactory, String classXMIName)
  {
    EPackage ePackage = eFactory.getEPackage();
    if (extendedMetaData != null)
    {
      EClassifier eClassifier = extendedMetaData.getType(ePackage, classXMIName);
      if (eClassifier == null)
      {
        return null;
      }
      else if (eClassifier instanceof EClass)
      {
        return eFactory.create((EClass)eClassifier);
      }
      else
      {
        SimpleAnyType result = (SimpleAnyType)EcoreUtil.create(anySimpleType);
        result.setInstanceType((EDataType)eClassifier);
        return result;
      }
    }
    else
    {
      EClass eClass = (EClass)ePackage.getEClassifier(classXMIName);
      if (eClass == null && xmlMap != null)
      {
        eClass = (EClass) xmlMap.getClassifier(ePackage.getNsURI(), classXMIName);
      }
  
      if (eClass != null)
      {
        return eFactory.create(eClass);
      }
      else
      {
        return null;
      }
    }
  }

  public EStructuralFeature getFeature(EClass eClass, String namespaceURI, String name)
  {
    EStructuralFeature feature = getFeatureWithoutMap(eClass, name);
    if (feature == null && xmlMap != null)
    {
      feature = xmlMap.getFeature(eClass, namespaceURI, name);
      if (feature != null)
      {
        computeFeatureKind(feature);
      }
    }

    return feature;
  }

  public EStructuralFeature getFeature(EClass eClass, String namespaceURI, String name, boolean isElement)
  {
    if (extendedMetaData != null)
    {
      EStructuralFeature eStructuralFeature = 
        isElement ? 
          extendedMetaData.getElement(eClass, namespaceURI, name) : 
          extendedMetaData.getAttribute(eClass, namespaceURI == "" ? null : namespaceURI, name);
      if (eStructuralFeature != null)
      {
        computeFeatureKind(eStructuralFeature);
      }
      else
      {
        eStructuralFeature = getFeature(eClass, namespaceURI, name);

        // Only if the feature kind is unspecified should we return a match.
        // Otherwise, we might return an attribute feature when an element is required, 
        // or vice versa. This also can be controlled by XMLResource.OPTION_LAX_FEATURE_PROCESSING.
        //
        if (!laxFeatureProcessing && eStructuralFeature != null && 
              extendedMetaData.getFeatureKind(eStructuralFeature) != ExtendedMetaData.UNSPECIFIED_FEATURE)
        {
          eStructuralFeature = null;
        }
      }

      return eStructuralFeature;
    }

    return getFeature(eClass, namespaceURI, name);
  }

  protected EStructuralFeature getFeatureWithoutMap(EClass eClass, String name)
  {
    EStructuralFeature feature = eClass.getEStructuralFeature(name);

    if (feature != null)
      computeFeatureKind(feature);

    return feature;
  }

  protected void computeFeatureKind(EStructuralFeature feature)
  {
    EClassifier eClassifier = feature.getEType();

    if (eClassifier instanceof EDataType)
    {
      if (feature.isMany())
      {
        featuresToKinds.put(feature, INTEGER_DATATYPE_IS_MANY);
      }
      else
      {
        featuresToKinds.put(feature, INTEGER_DATATYPE_SINGLE);
      }
    }
    else
    {
      if (feature.isMany())
      {
        EReference reference = (EReference) feature;
        EReference opposite  = reference.getEOpposite();

        if (opposite == null || opposite.isTransient() || !opposite.isMany())
          featuresToKinds.put(feature, INTEGER_IS_MANY_ADD);
        else
          featuresToKinds.put(feature, INTEGER_IS_MANY_MOVE);
      }
    }
  }

  public String getJavaEncoding(String xmlEncoding)
  {
    return xmlEncoding;
  }

  public String getXMLEncoding(String javaEncoding)
  {
    return javaEncoding;
  }

  public EPackage[] packages()
  {
    Map map = new TreeMap();

    // Sort and eliminate duplicates caused by having both a regular package and a demanded package for the same nsURI.
    //
    for (Iterator i = packages.entrySet().iterator(); i.hasNext(); )
    {
      Map.Entry entry = (Map.Entry)i.next();
      EPackage ePackage = (EPackage)entry.getKey();
      String prefix= getPrefix(ePackage);
      if (prefix == null)
      {
        prefix = "";
      }
      EPackage conflict = (EPackage)map.put(prefix, ePackage);
      if (conflict != null && conflict.eResource() != null)
      {
        map.put(prefix, conflict);
      }
    }
    EPackage[] result = new EPackage[map.size()];
    map.values().toArray(result);
    return result;
  }

  public void setValue(EObject object, EStructuralFeature feature, Object value, int position)
  {
    if (extendedMetaData != null)
    {
      EStructuralFeature targetFeature = extendedMetaData.getAffiliation(object.eClass(), feature);
      if (targetFeature != null && targetFeature != feature)
      {
        EStructuralFeature group = extendedMetaData.getGroup(targetFeature);
        if (group != null)
        {
          targetFeature = group;
        }
        if (targetFeature.getEType() == EcorePackage.eINSTANCE.getEFeatureMapEntry())
        {
          FeatureMap featureMap = (FeatureMap)object.eGet(targetFeature);
          EClassifier eClassifier = feature.getEType();
          if (eClassifier instanceof EDataType)
          {
            EDataType eDataType = (EDataType) eClassifier;
            EFactory eFactory = eDataType.getEPackage().getEFactoryInstance();
            value = createFromString(eFactory, eDataType, (String)value);
          }
          featureMap.add(feature, value);
          return;
        }
        else
        {
          feature = targetFeature;
        }
      }
    }

    int kind = getFeatureKind(feature);
    switch (kind)
    {
      case DATATYPE_SINGLE:
      case DATATYPE_IS_MANY:
      {
        EClassifier eClassifier = feature.getEType();
        EDataType eDataType = (EDataType) eClassifier;
        EFactory eFactory = eDataType.getEPackage().getEFactoryInstance();

        if (kind == DATATYPE_IS_MANY)
        {
          InternalEList list = (InternalEList) object.eGet(feature);
          if (position == -2)
          {
            for (StringTokenizer stringTokenizer = new StringTokenizer((String)value, " "); stringTokenizer.hasMoreTokens(); )
            {
              String token = stringTokenizer.nextToken();
              list.addUnique(createFromString(eFactory, eDataType, token));
            }

            // Make sure that the list will appear to be set to be empty.
            //
            if (list.isEmpty())
            {
              list.clear();
            }
          }
          else if (value == null)
          {
            list.addUnique(null);
          }
          else
          {
            list.addUnique(createFromString(eFactory, eDataType, (String)value));
          }
        }
        else if (value == null)
        {
          object.eSet(feature, null);
        }
        else
        {
          object.eSet(feature, createFromString(eFactory, eDataType, (String) value));
        }
        break;
      }
      case IS_MANY_ADD:
      case IS_MANY_MOVE:
      {
        // if the feature isn't an elist, wrap it in one
        // A. McV, fixed 28/7/06
        Object l = object.eGet(feature);
        if (l instanceof ArrayList)
        {
          l = new PersistentEList((ArrayList) l, null, (InternalEObject) object, feature.getFeatureID());
        }
        
        InternalEList list = (InternalEList) l;

        if (position == -1)
        {
          list.addUnique(value);
        }
        else if (position == -2)
        {
          list.clear();
        }
        else if (checkForDuplicates)
        {
          int index = list.indexOf(value);
          if (index == -1)
          {
            list.addUnique(position, value);
          }
          else
          {
            list.move(position, index);
          }
        }
        else if (kind == IS_MANY_ADD)
        {
          list.addUnique(position, value);
        }
        else
        {
          list.move(position, value);
        }
        break;
      }
      default:
      {
        object.eSet(feature, value);
        break;
      }
    }
  }

  public List setManyReference(ManyReference reference, String location)
  {
    EStructuralFeature feature = reference.getFeature();
    int kind = getFeatureKind(feature);
    InternalEList list = (InternalEList) reference.getObject().eGet(feature);
    List xmiExceptions = new BasicEList();
    Object[] values = reference.getValues();
    int[] positions = reference.getPositions();

    if (kind == IS_MANY_ADD)
    {
      for (int i = 0, l = values.length; i < l; i++)
      {
        if (values[i] != null)
        {
          try
          {
            list.addUnique(positions[i], values[i]);
          }
          catch (RuntimeException e)
          {
            xmiExceptions.add(new IllegalValueException
                                    (reference.getObject(),
                                     feature,
                                     values[i],
                                     e,
                                     location,
                                     reference.getLineNumber(),
                                     reference.getColumnNumber()
                                    ));
          }
        }
      }
    }
    else
    {
      for (int i = 0, l = values.length; i < l; i++)
      {
        if (values[i] != null)
        {
          try
          {
            list.move(positions[i], values[i]);
          }
          catch (RuntimeException e)
          {
            xmiExceptions.add(new IllegalValueException
                                    (reference.getObject(),
                                     feature,
                                     values[i],
                                     e,
                                     location,
                                     reference.getLineNumber(),
                                     reference.getColumnNumber()
                                    ));
          }
        }
      }
    }

    if (xmiExceptions.isEmpty())
    {
      return null;
    }
    else
    {
      return xmiExceptions;
    }
  }

  public void setCheckForDuplicates(boolean checkForDuplicates)
  {
    this.checkForDuplicates = checkForDuplicates;
  }

  public void setProcessDanglingHREF(String value)
  {
    processDanglingHREF = value;
  }

  public DanglingHREFException getDanglingHREFException()
  {
    return danglingHREFException;
  }

  public URI resolve(URI relative, URI base) 
  {
    return relative.resolve(base);
  }
  
  public void pushContext()
  {
    namespaceSupport.pushContext();
  }

  public void popContext()
  {
    namespaceSupport.popContext();
  }

  public void addPrefix(String prefix, String uri) 
  {
    if (!"xml".equals(prefix) && !"xmlns".equals(prefix))
    {
      uri = (uri.length() == 0) ? null : uri;
      namespaceSupport.declarePrefix(prefix, uri);
      allPrefixToURI.add(prefix);
      allPrefixToURI.add(uri);
    }
  }
  
  public Map getAnyContentPrefixToURIMapping()
  {
    anyPrefixesToURIs.clear();
    int count = namespaceSupport.getDeclaredPrefixCount();
    int size = allPrefixToURI.size();
    Object uri, prefix = null;    
    while (count-->0)
    {         
      uri = allPrefixToURI.remove(--size);
      prefix = allPrefixToURI.remove(--size);
      anyPrefixesToURIs.put(prefix, uri);
     }
    return anyPrefixesToURIs;
  }
  

  public String getURI(String prefix) 
  {
    return 
      "xml".equals(prefix) ? 
        "http://www.w3.org/XML/1998/namespace" : 
        "xmlns".equals(prefix) ? 
          ExtendedMetaData.XMLNS_URI :
          namespaceSupport.getURI(prefix);
  }

  public EMap getPrefixToNamespaceMap()
  {
    return prefixesToURIs;
  }
  
  public void recordPrefixToURIMapping()
  {
    for (int i = 0, size = allPrefixToURI.size(); i < size;)
    {
      String prefix = (String)allPrefixToURI.get(i++);
      String uri = (String)allPrefixToURI.get(i++);
      String originalURI = (String)prefixesToURIs.get(prefix);
      if (uri == null)
      {
        // xmlns="" declaration
        // Example #1: <a><qname>q</qname><b xmlns="abc"/></a>
        // Example #2: <a xmlns="abc"><b xmlns=""/><c xmlns="abc2"/></a>
        // Example #3: <a xmlns:a="abc"><b xmlns:a="abc2"/></a>
        
        seenEmptyStringMapping = true;
        if (originalURI != null)
        {
          // since xmlns="" is default declaration, remove ""->empty_URI mapping
          prefixesToURIs.removeKey(prefix);
          addNSDeclaration(prefix, originalURI);
        }
        continue;
      }
      else if ((seenEmptyStringMapping && prefix.length() == 0))
      {
        // record default ns declaration as duplicate if seen QName (#1) or seen xmlns="" (#2)
        addNSDeclaration(prefix, uri);       
      }
      else if (originalURI != null)
      {
        if (!uri.equals(originalURI))
        {
          // record duplicate declaration for a given prefix (#3)
          addNSDeclaration(prefix, uri);
        }
      }
      else
      {
        // recording a first declaration for a given prefix
        prefixesToURIs.put(prefix, uri);
      }
    }
  }

  public void setPrefixToNamespaceMap(EMap prefixToNamespaceMap)
  {
    for (Iterator i = prefixToNamespaceMap.iterator(); i.hasNext(); )
    {
      Map.Entry entry = (Map.Entry)i.next();
      String prefix = (String)entry.getKey();
      String namespace = (String)entry.getValue();
      EPackage ePackage = null;
      if (extendedMetaData == null)
      {
        ePackage =  packageRegistry.getEPackage(namespace);
      }
      else
      {
        ePackage =  extendedMetaData.getPackage(namespace);
        if (ePackage == null && !XMLResource.XSI_URI.equals(namespace))
        {
          if (XMLResource.XML_SCHEMA_URI.equals(namespace))
          {
            ePackage = xmlSchemaTypePackage;
          }
          else
          {
            ePackage = extendedMetaData.demandPackage(namespace);
          }
        }
      }
      if (ePackage != null && !packages.containsKey(ePackage))
      {
        packages.put(ePackage, prefix);
      }
      prefixesToURIs.put(prefix, namespace);
    }
  }
  
  /** 
   * A helper to encode namespace prefix mappings.
   */
  protected static class NamespaceSupport
  {
    protected String[] namespace = new String [16 * 2];

    protected int namespaceSize = 0;

    protected int[] context = new int [8];

    protected int currentContext = -1;

    protected String[] prefixes = new String [16];

    public void pushContext()
    {
      // extend the array, if necessary
      if (currentContext + 1 == context.length)
      {
        int[] contextarray = new int [context.length * 2];
        System.arraycopy(context, 0, contextarray, 0, context.length);
        context = contextarray;
      }

      // push context
      context[++currentContext] = namespaceSize;
    } 

    public void popContext()
    {
      namespaceSize = context[currentContext--];
    } 

    /**
     * @param prefix prefix to declare
     * @param uri uri that maps to the prefix
     * @return true if the prefix existed in the current context and
     * its uri has been remapped; false if prefix does not exist in the
     * current context
     */
    public boolean declarePrefix(String prefix, String uri)
    {
      // see if prefix already exists in current context
      for (int i = namespaceSize; i > context[currentContext]; i -= 2)
      {
        if (namespace[i - 2].equals(prefix))
        {
          namespace[i - 1] = uri;
          return true;
        }
      }

      // resize array, if needed
      if (namespaceSize == namespace.length)
      {
        String[] namespacearray = new String [namespaceSize * 2];
        System.arraycopy(namespace, 0, namespacearray, 0, namespaceSize);
        namespace = namespacearray;
      }

      // bind prefix to uri in current context
      namespace[namespaceSize++] = prefix;
      namespace[namespaceSize++] = uri;
      return false;
    } 

    public String getURI(String prefix)
    {
      // find prefix in current context
      for (int i = namespaceSize; i > 0; i -= 2)
      {
        if (namespace[i - 2].equals(prefix))
        {
          return namespace[i - 1];
        }
      }

      // prefix not found
      return null;
    }
    
    public String getPrefix(String uri) 
    {
      // find uri in current context
      for (int i = namespaceSize; i > 0; i -= 2) 
      {
        if (namespace[i - 1].equals(uri)) 
        {
          if (getURI(namespace[i - 2]).equals(uri))
            return namespace[i - 2];
        }
      }

      // uri not found
      return null;
    }
    
    public int getDeclaredPrefixCount()
    {
      return (namespaceSize - context[currentContext]) / 2;
    }

    public String getDeclaredPrefixAt(int index)
    {
      return namespace[context[currentContext] + index * 2];
    } // getDeclaredPrefixAt(int):String

  }// namespace context

  public void setAnySimpleType(EClass type)
  {
    anySimpleType = type;
  }
  
  public String convertToString(EFactory factory, EDataType dataType, Object value)
  {
    if (extendedMetaData != null)
    {
      if (value instanceof List)
      {
        List list = (List)value;
        for (Iterator i = list.iterator(); i.hasNext(); )
        {
          updateQNamePrefix(factory, dataType, i.next(), true);
        }
        return factory.convertToString(dataType, value);
      }
      else
      {
        return updateQNamePrefix(factory, dataType, value, false);
      }
    }
    return factory.convertToString(dataType, value);
  }
  
  protected Object createFromString(EFactory eFactory, EDataType eDataType, String value)
  {
    Object obj = eFactory.createFromString(eDataType, value);          
    if (extendedMetaData != null)
    {          
      if (obj instanceof List)
      {
         List list = (List)obj;
         for (int i=0;i<list.size();i++)
         {
           updateQNameURI(list.get(i));
         }
      }
      else
      {
       updateQNameURI(obj);
      }
    }
    return obj;
  }
  
  protected void updateQNameURI(Object value)
  {
    if (value instanceof QName)
    {
       QName qname = (QName) value;
       String namespace = getURI(qname.getPrefix());
       qname.setNamespaceURI(namespace);      
       if (qname.getPrefix().length() >0 && qname.getNamespaceURI().length() == 0)
       {          
         throw new IllegalArgumentException("The prefix '" + qname.getPrefix() + "' is not declared for the QName '"+qname.toString()+"'");
       }
       if (namespace == null)
       {
         seenEmptyStringMapping = true;
         String uri = (String)prefixesToURIs.get("");
         if (uri != null)
         {
           prefixesToURIs.put("", namespace);
           addNSDeclaration("", uri);          
         }
       }
    }
  }
  
  /**
   * @param factory
   * @param dataType
   * @param value a data value to be converted to string
   * @param list if the value is part of the list of values
   * @return if the value is not part of the list, return string corresponding to value,
   *         otherwise return null
   */
  protected String updateQNamePrefix(EFactory factory, EDataType dataType, Object value, boolean list)
  {
    if (value instanceof QName)
    {
      QName qname = (QName)value;
      String namespace = qname.getNamespaceURI();
      if (namespace.length() == 0)
      {       
        qname.setPrefix("");
        return qname.getLocalPart();
      }
      EPackage ePackage = extendedMetaData.getPackage(namespace);
      if (ePackage == null)
      {
        ePackage = extendedMetaData.demandPackage(namespace);
      }

      String  prefix = getPrefix(ePackage, true);
      if (!packages.containsKey(ePackage))
      {
        packages.put(ePackage, prefix);
      }
      qname.setPrefix(prefix);
      return (!list) ? qname.toString() : null;
    }

    return (!list) ? factory.convertToString(dataType, value) : null;
  }
  
  protected void addNSDeclaration(String prefix, String uri)
  {
    if (uri != null)
    {
      int index = 1;
      while (prefixesToURIs.containsKey(prefix + "_" + index))
      {
        ++index;
      }
      prefixesToURIs.put(prefix + "_" + index, uri);
    }
  }

  public void setMustHavePrefix(boolean mustHavePrefix)
  {
    this.mustHavePrefix = mustHavePrefix;
  }
  
}
