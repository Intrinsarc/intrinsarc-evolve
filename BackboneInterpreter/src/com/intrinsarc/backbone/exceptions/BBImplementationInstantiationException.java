package com.intrinsarc.backbone.exceptions;

import com.intrinsarc.backbone.nodes.simple.*;

public class BBImplementationInstantiationException extends BBInterpreterException
{
	private BBSimpleElement element;
	
	public BBImplementationInstantiationException(String message, BBSimpleElement element)
	{
		super(message);
		this.element = element;
	}

	public BBImplementationInstantiationException(String message, BBSimpleElement element, Exception cause)
	{
		super(message, cause);
		this.element = element;
	}
	
	public BBSimpleElement getElement()
	{
		return element;
	}
}
