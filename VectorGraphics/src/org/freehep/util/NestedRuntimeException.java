package org.freehep.util;

/**
 * A runtime exception with an embedded (nested) exception.
 * 
 * The NestedRuntimeException is particluarly useful for converting a checked
 * exception to a non-checked (runtime) exception, without loosing the
 * information associated with the checked exception.
 * 
 * @see NestedException
 * @see HasNestedException
 * @author Tony Johnson
 * @version $Id: NestedRuntimeException.java,v 1.1 2006-07-03 13:33:20 amcveigh
 *          Exp $
 */
public class NestedRuntimeException extends RuntimeException implements HasNestedException
{
  private Throwable detail;

  /**
   * Create an exception with no message and no nested exception.
   */
  public NestedRuntimeException()
  {
    super();
  }
  /**
   * Create an exception with no nested exception
   * 
   * @param messsage
   *          The message
   */
  public NestedRuntimeException(String message)
  {
    super(message);
  }
  /**
   * Create a nested exception with the specified nested exception
   * 
   * @param detail
   *          The nested exception
   */
  public NestedRuntimeException(Throwable detail)
  {
    super();
    this.detail = detail;
  }
  /**
   * Create a remote exception with the specified message, and the nested
   * exception specified.
   * 
   * @param messsage
   *          The message
   * @param detail
   *          The nested exception
   */
  public NestedRuntimeException(String message, Throwable detail)
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
}
