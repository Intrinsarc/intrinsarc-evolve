package org.freehep.util;
/**
 * An exception with an embedded (nested) exception.
 * 
 * @see NestedRuntimeException
 * @see HasNestedException
 * @author Tony Johnson
 * @version $Id: NestedException.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class NestedException extends Exception implements HasNestedException
{
  private Throwable detail;

  /**
   * Create a nested exception
   */
  public NestedException()
  {
  }
  /**
   * Create a nested exception
   * 
   * @param message
   *          The message associated with the exception
   */
  public NestedException(String message)
  {
    super(message);
  }
  /**
   * Create a nested exception
   * 
   * @param detail
   *          The exception nested inside this exception
   */
  public NestedException(Throwable detail)
  {
    super();
    this.detail = detail;
  }
  /**
   * Create a nested exception
   * 
   * @param message
   *          The message associated with the exception
   * @param detail
   *          The nested exception
   */
  public NestedException(String message, Throwable detail)
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
    return formatNestedException(this);
  }
  public String getSimpleMessage()
  {
    return super.getMessage();
  }
  /**
   * A static method for formating the text of any exception which implements
   * HasNestedException
   */
  public static String formatNestedException(HasNestedException t)
  {
    Throwable nest = t.getNestedException();
    if (nest == null)
      return t.getSimpleMessage();
    else
      return t.getSimpleMessage() + "; nested exception is: \n\t" + nest.toString();
  }
}
