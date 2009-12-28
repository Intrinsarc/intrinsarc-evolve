/*
 * class: RecordListenerManagerNoRunTest
 *
 * Version $Id: RecordListenerManagerNoRunTest.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 *
 * Date: March 7 2003
 *
 * (c) 2003 IceCube Collaboration
 */

package org.freehep.record.loop.event.test;

import junit.framework.*;

import org.freehep.record.loop.event.*;

/**
 * This class defines the tests that any RecordListenerManager object should
 * pass.
 * 
 * @version $Id: RecordListenerManagerNoRunTest.java,v 1.1 2006-07-03 13:32:54
 *          amcveigh Exp $
 * @author patton
 */
public class RecordListenerManagerNoRunTest extends TestCase
{

  // public static final member data

  // protected static final member data

  // static final member data

  // private static final member data

  // private static member data

  // private instance member data

  /** The object being tested. */
  private RecordListenerManager testObject;

  // constructors

  /**
   * Create an instance of this class. Default constructor is declared, but
   * private, to stop accidental creation of an instance of the class.
   */
  private RecordListenerManagerNoRunTest()
  {
    this(null);
  }

  /**
   * Constructs and instance of this test.
   * 
   * @param name
   */
  public RecordListenerManagerNoRunTest(String name)
  {
    super(name);
  }

  // instance member function (alphabetic)

  /**
   * Sets the object to be tested.
   * 
   * @param testObject
   *          object to be tested.
   */
  protected void setRecordListenerManager(RecordListenerManager testObject)
  {
    this.testObject = testObject;
  }

  /**
   * Sets up the fixture, for example, open a network connection. This method is
   * called before a test is executed.
   */
  // protected void setUp()
  // {
  // }
  /**
   * Tears down the fixture, for example, close a network connection. This
   * method is called after a test is executed.
   */
  protected void tearDown()
  {
    testObject = null;
  }

  /**
   * Explanation of test.
   */
  public void testSomething()
  {
  }

  // static member functions (alphabetic)

  /**
   * Create test suite for this class.
   */
  public static Test suite()
  {
    return new TestSuite(RecordListenerManagerNoRunTest.class);
  }

  // Description of this object.
  // public String toString() {}
}
