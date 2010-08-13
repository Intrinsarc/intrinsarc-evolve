package com.intrinsarc.backbone.exceptions;

import com.intrinsarc.backbone.nodes.simple.*;

public class BBRuntimeException extends Exception
{
	private BBSimpleElement element;
	
	public BBRuntimeException(String message, BBSimpleElement element)
	{
		super(message);
		this.element = element;
	}

	public BBRuntimeException(String message, BBSimpleElement element, Throwable cause)
	{
		super(message, cause);
		this.element = element;
	}
	
	public BBSimpleElement getElement()
	{
		return element;		
	}
}
