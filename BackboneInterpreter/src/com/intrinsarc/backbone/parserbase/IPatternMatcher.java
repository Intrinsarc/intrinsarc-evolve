package com.intrinsarc.backbone.parserbase;

public interface IPatternMatcher
{
	public boolean matches(Token tok);
	public String getDescription();
}
