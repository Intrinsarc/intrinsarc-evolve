package com.intrinsarc.backbone.exceptions;


public abstract class BBInterpreterException extends Exception
{
	public BBInterpreterException(String message)
	{
		super(message);
	}

	public BBInterpreterException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
