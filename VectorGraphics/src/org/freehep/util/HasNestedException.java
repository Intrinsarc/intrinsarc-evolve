package org.freehep.util;
/**
 * An interface implemented by some exceptions to indicate that they have an
 * additional exception nested inside of them. This is very useful in order to
 * be able to add extra information to an exception without losing the original
 * information. For example:
 * 
 * <pre>
 * try
 * {
 *   readFile(fileName);
 * }
 * catch (IOException x)
 * {
 *   throw new NestedException(&quot;Error while reading &quot; + fileName, x);
 * }
 * </pre>
 * 
 * @author Tony Johnson
 * @version $Id: HasNestedException.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public interface HasNestedException
{
  /**
   * Get the exception nested inside
   * 
   * @return The nested exception
   */
  public Throwable getNestedException();
  /**
   * Get the message text, without the message text of the nested exception
   * 
   * @return The message corresponding to this exception
   */
  public String getSimpleMessage();
  /**
   * Get the message text, including the message text if the nested exception
   * 
   * @return The message corresponding to this exception
   */
  public String getMessage();
}
