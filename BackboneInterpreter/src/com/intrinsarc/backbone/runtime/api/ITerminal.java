package com.intrinsarc.backbone.runtime.api;

public interface ITerminal
{
	boolean isCurrent();
	void moveToNextState();
	void becomeCurrent();
}
