/*
 * BadXMLException.java
 *
 * Created on February 14, 2001, 6:48 PM
 */

package org.freehep.xml.util;
import org.freehep.util.*;
import org.xml.sax.*;
/**
 * A SAXException with an optional nested exception
 * 
 * @author tonyj
 * @version $Id: BadXMLException.java,v 1.1 2009-03-04 22:46:59 andrew Exp $
 */

public class BadXMLException extends SAXException implements HasNestedException
{
  public BadXMLException(String message)
  {
    super(message);
  }
  public BadXMLException(String message, Throwable detail)
  {
    super(message);
    this.detail = detail;
  }
  public Throwable getNestedException()
  {
    return detail;
  }
  public String getMessage()
  {
    return NestedException.formatNestedException(this);
  }
  public String getSimpleMessage()
  {
    return super.getMessage();
  }
  private Throwable detail;
}
