package com.intrinsarc.backbone.exceptions;

import com.intrinsarc.backbone.nodes.simple.*;
import com.intrinsarc.deltaengine.base.*;

public class BBImplementationInstantiationException extends BBInterpreterException
{
	private String elementName;
	private String elementUuid;
	
	public BBImplementationInstantiationException(String message, BBSimpleElement element)
	{
		super(message);
		this.elementUuid = element.getUuid();
	}

	public BBImplementationInstantiationException(String message, BBSimpleElement element, Exception cause)
	{
		super(message, cause);
		this.elementUuid = element.getUuid();
	}
	
	public BBImplementationInstantiationException(String message, DEElement element)
	{
		super(message);
		this.elementName = element.getName();
		this.elementUuid = element.getUuid();
	}

	public BBImplementationInstantiationException(String message, DEElement element, Exception cause)
	{
		super(message, cause);
		this.elementName = element.getName();
		this.elementUuid = element.getUuid();
	}
	
	public String getElementUuid()
	{
		return elementUuid;
	}

	public String getElementName()
	{
		return elementName;
	}
}
