package com.intrinsarc.beanimporter;

import java.util.*;

public class BeanPackage
{
	private String name;
	private Map<String, BeanPackage> children = new HashMap<String, BeanPackage>();
	private Map<String, BeanClass> beans = new HashMap<String, BeanClass>();
	private Map<String, BeanClass> hidden = new HashMap<String, BeanClass>();
	
	public BeanPackage(String name)
	{
		this.name = name;
	}
	
	public void addChild(BeanPackage child)
	{
		children.put(child.name, child);
	}

	public void addBean(BeanClass cls)
	{
		beans.put(cls.getName(), cls);
	}
	

	public void addHidden(BeanClass cls)
	{
		hidden.put(cls.getName(), cls);
	}


	public String getName()
	{
		return isDefaultPackage() ? "(default)" : name;
	}
	
	public boolean isDefaultPackage()
	{
		return name.length() == 0;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof BeanPackage))
			return false;
		return name.equals(((BeanPackage) obj).name);
	}

	@Override
	public int hashCode()
	{
		return name.hashCode();
	}

	public BeanPackage locateOrCreatePackage(String name)
	{
		int first = name.indexOf('.');
		if (first != -1 && !name.endsWith("."))
		{
			String start = name.substring(0, first);
			String rest = name.substring(first + 1);
			BeanPackage next = locateOrCreatePackage(start);
			return next.locateOrCreatePackage(rest);
		}
		else
		{
			// create the child here
			BeanPackage next = children.get(name);
			if (next != null)
				return next;
			next = new BeanPackage(name);
			addChild(next);
			return next;
		}
	}

	public List<BeanClass> getBeanClasses(boolean includeHidden)
	{
		List<BeanClass> bs = new ArrayList<BeanClass>(beans.values());
		if (includeHidden)
			bs.addAll(hidden.values());
		
		Collections.sort(bs, new BeanClassComparator());
		return bs;
	}
	
	public List<BeanPackage> getChildren()
	{
		List<BeanPackage> pkgs = new ArrayList<BeanPackage>(children.values());
		Collections.sort(pkgs, new Comparator<BeanPackage>()
		{
			public int compare(BeanPackage o1, BeanPackage o2)
			{
				return o1.name.compareTo(o2.name);
			}	
		});
		return pkgs;
	}

	public boolean definitelyHasBeansSomewhere()
	{
		if (!beans.isEmpty())
			return true;
		
			for (BeanPackage pkg : children.values())
				if (pkg.definitelyHasBeansSomewhere())
					return true;
			return false;
	}
	
	public void fixUpUsingFinder(BeanFinder finder)
	{
		// remove bad ports which refer to non-beans
		for (BeanClass cls : beans.values())
			cls.fixUpUsingFinder(finder);
		for (BeanClass cls : hidden.values())
			cls.fixUpUsingFinder(finder);
		for (BeanPackage child : children.values())
			child.fixUpUsingFinder(finder);
	}
	
	public void fixUpBeanTypes(BeanFinder finder, int count[])
	{
		// make sure that if a bean "inherits" from another, that element should also be a bean
		for (BeanClass cls : beans.values())
			cls.fixUpBeanTypes(finder, count);
		for (BeanClass cls : hidden.values())
			cls.fixUpBeanTypes(finder, count);
		for (BeanPackage child : children.values())
			child.fixUpBeanTypes(finder, count);
	}
}
