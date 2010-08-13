package com.intrinsarc.backbone.exceptions;

public class BBNodeNotFoundException extends RuntimeException
{
  private String location;
  
  public BBNodeNotFoundException(String message, String location)
  {
    super(message);
    this.location = location;
  }
  
  public String getLocation()
  {
    return location;
  }
}
