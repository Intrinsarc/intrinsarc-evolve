package com.hopstepjump.beanimporter;

public class BeanCreatedSubjects
{
	private int madePrimitives;
	private int madeInterfaces;
	private int madeLeafInterfaces;
	private int madeLeaves;
	
	public BeanCreatedSubjects()
	{
	}
	
	public int getTotalMade()
	{
		return madePrimitives + madeInterfaces + madeLeafInterfaces + madeLeaves;
	}

	public void bumpMadePrimitives()
	{
		++madePrimitives;
	}
	
	public int getMadePrimitives()
	{
		return madePrimitives;
	}
	
	public void bumpMadeInterfaces()
	{
		++madeInterfaces;
	}

	public int getMadeInterfaces()
	{
		return madeInterfaces;
	}

	public void bumpMadeLeafInterfaces()
	{
		++madeLeafInterfaces;
	}
	
	public int getMadeLeafInterfaces()
	{
		return madeLeafInterfaces;
	}

	public void bumpMadeLeaves()
	{
		++madeLeaves;
	}
	
	public int getMadeLeaves()
	{
		return madeLeaves;
	}
}
