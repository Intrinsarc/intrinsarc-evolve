package com.hopstepjump.beanimporter;

public class TreeElementUserObject
{
	private String name;
	private BeanPackage pkg;
	private BeanClass cls;
	private boolean disabled;
	private BeanField field;
	private BeanTypeEnum type;
	
	public TreeElementUserObject(BeanClass cls, boolean leaf)
	{
		this.type = cls.getType();
		this.cls = cls;
		name = cls.getName();
	}
	
	public TreeElementUserObject(BeanClass cls)
	{
		this.type = cls.getType();
		this.cls = cls;
		name = cls.getName();
	}
	
	public TreeElementUserObject(BeanPackage pkg)
	{
		type = BeanTypeEnum.PACKAGE;
		this.pkg = pkg;
		name = pkg.getName();
	}

	public TreeElementUserObject(BeanField field, BeanTypeEnum type)
	{
		this.type = type;
		this.field = field;
		name = field.getName();
	}

	public BeanTypeEnum getType()
	{
		return type;
	}

	public String getName()
	{
		return name;
	}

	public BeanPackage getBeanPackage()
	{
		return pkg;
	}

	public BeanClass getBeanClass()
	{
		return cls;
	}
	
	public BeanField getBeanField()
	{
		return field;
	}

	@Override
	public String toString()
	{
		return name;
	}

	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}
	
	public boolean isDisabled()
	{
		return disabled;
	}
}
