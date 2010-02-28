package com.hopstepjump.backbone.parser;

public interface IMatcher
{
	public boolean matches(Token tok);
	public String getDescription();
}
