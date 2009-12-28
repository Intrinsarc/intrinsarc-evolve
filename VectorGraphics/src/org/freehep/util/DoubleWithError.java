package org.freehep.util;

/**
 * A class that encapsulates a value and its error. Primarily for use with
 * ScientificFormat
 * 
 * @see ScientificFormat
 * 
 * @author Tony Johnson
 * @author Mark Donszelmann
 * @version $Id: DoubleWithError.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class DoubleWithError
{
  public DoubleWithError(double value, double error)
  {
    this.value = value;
    this.error = error;
    this.asymmetricError = false;
  }

  public DoubleWithError(double value, double plusError, double minError)
  {
    this.value = value;
    this.error = plusError;
    this.minError = minError;
    this.asymmetricError = true;
  }

  public void setError(double error)
  {
    this.error = error;
    this.asymmetricError = false;
  }

  public void setError(double plusError, double minError)
  {
    this.error = plusError;
    this.minError = minError;
    this.asymmetricError = true;
  }

  public double getError()
  {
    // FIXME: what do we return here if this has an asymmetric error
    return error;
  }

  public double getPlusError()
  {
    return error;
  }

  public double getMinError()
  {
    return (asymmetricError) ? minError : error;
  }

  public boolean hasAsymmetricError()
  {
    return asymmetricError;
  }

  public void setValue(double value)
  {
    this.value = value;
  }

  public double getValue()
  {
    return value;
  }

  public String toString()
  {
    if (asymmetricError)
    {
      return String.valueOf(value) + plus + error + minus + minError;
    }
    else
    {
      return String.valueOf(value) + plusorminus + error;
    }
  }

  public final static String plusorminus = "�";
  public final static String plus = "+";
  public final static String minus = "-";
  private double value;

  private double error;
  private boolean asymmetricError;
  private double minError;
}
