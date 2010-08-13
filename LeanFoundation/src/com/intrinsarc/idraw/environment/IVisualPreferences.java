package com.intrinsarc.idraw.environment;

public interface IVisualPreferences
{
	public boolean hasUnappliedChanges();
	public void applyChanges();
	public void discardChanges();
}
