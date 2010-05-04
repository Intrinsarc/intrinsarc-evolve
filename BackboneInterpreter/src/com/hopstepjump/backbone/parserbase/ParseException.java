package com.hopstepjump.backbone.parserbase;

public class ParseException extends RuntimeException
{
	private int line;
	private int pos;
	
	public ParseException(String message, int line, int pos)
	{
		this(message, line, pos, null);
	}

	public ParseException(String message, int line, int pos, Exception ex)
	{
		super(message + ", line " + line + ", position " + pos, ex);
		this.line = line;
		this.pos = pos;
	}

	public int getLine()
	{
		return line;
	}

	public int getPos()
	{
		return pos;
	}
}
