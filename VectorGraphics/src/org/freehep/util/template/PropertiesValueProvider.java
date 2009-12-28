package org.freehep.util.template;

import java.util.*;

/**
 * A value provider that returns values based on a properties object.
 * <p>
 * Example of use:
 * 
 * <PRE>
 * Hello {v:user.name}
 * </PRE>
 * 
 * @author tonyj
 * @version $Id: PropertiesValueProvider.java,v 1.1 2006-07-03 13:33:39 amcveigh
 *          Exp $
 */

public class PropertiesValueProvider implements ValueProvider
{
  private Properties props;
  /**
   * Builds a PropertiesValueProvider which takes its values from the system
   * properties.
   * 
   * @see java.lang.System#getProperties()
   */
  public PropertiesValueProvider()
  {
    this(null);
  }
  /**
   * Builds a PropertiesValueProvider which takes its values from the specified
   * Properties object.
   * 
   * @param props
   *          The properties to use.
   */
  public PropertiesValueProvider(Properties props)
  {
    this.props = props;
  }
  public String getValue(String name)
  {
    return props == null ? System.getProperty(name) : props.getProperty(name);
  }
  public List getValues(String name)
  {
    return null;
  }
}