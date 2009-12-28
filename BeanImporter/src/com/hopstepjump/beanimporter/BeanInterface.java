package com.hopstepjump.beanimporter;

public class BeanInterface
{
	private String className;
	private boolean synthetic;
	private String name;

	public BeanInterface(String className, boolean synthetic)
	{
		this.className = className;
		this.synthetic = synthetic;
		int last = className.lastIndexOf('/');
		name = className.substring(last + 1);
	}

	public boolean isSynthetic()
	{
		return synthetic;
	}

	public void setSynthetic(boolean synthetic)
	{
		this.synthetic = synthetic;
	}

	public String getName()
	{
		return name;
	}
}
