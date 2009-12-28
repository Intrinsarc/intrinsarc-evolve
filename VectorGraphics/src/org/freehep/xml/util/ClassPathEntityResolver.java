// Copyright 2000-2003, SLAC, Stanford, California, U.S.A.
package org.freehep.xml.util;

import java.io.*;

import org.xml.sax.*;


/**
 * An implementation of an EntityResolver which can be used to locate DTD files
 * located on the current java class path
 * 
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: ClassPathEntityResolver.java,v 1.1 2006-07-03 13:32:48 amcveigh
 *          Exp $
 */
public class ClassPathEntityResolver implements EntityResolver
{
  private Class root;
  private String name;

  /**
   * Constructor
   * 
   * @param DTDName
   *          The DTDName to resolve
   * @param root
   *          A Class in the same package as the DTD
   */
  public ClassPathEntityResolver(String DTDName, Class root)
  {
    this.name = DTDName;
    this.root = root;
  }

  /*
   * Implementation of resolveEntity method
   */
  public InputSource resolveEntity(String publicId, String systemId) throws SAXException
  {
    if (systemId.endsWith(name))
    {
      InputStream in = root.getResourceAsStream(name);
      if (in == null)
        throw new SAXException(name + " not found");
      return new InputSource(in);
    }
    else
    {
      // use the default behaviour
      return null;
    }
  }
}
