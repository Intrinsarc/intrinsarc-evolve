package com.hopstepjump.backbone.runtime.api;

public class Attribute<T>
{
	private T t;
	
	public Attribute(T t)
	{
		this.t = t;
	}
	
	public T get()
	{
		return t;
	}
	
	public void set(T t)
	{
		this.t = t;
	}
}
