package com.hopstepjump.idraw.environment;

public interface IVisualPreferences
{
	public boolean hasUnappliedChanges();
	public void applyChanges();
	public void discardChanges();
}
