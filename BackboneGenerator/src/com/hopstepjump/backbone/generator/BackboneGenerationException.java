package com.hopstepjump.backbone.generator;

public class BackboneGenerationException extends Exception
{
	private Object elementInError;
	
  public BackboneGenerationException(String message, Object elementInError)
  {
    super(message);
    this.elementInError = elementInError;
  }
  
  public Object getElementInError()
  {
  	return elementInError;
  }
}
