package com.hopstepjump.beanimporter;

import java.util.*;

import org.objectweb.asm.*;

public class BeanField
{
	private BeanClass parent;
	private String name;
	private List<Type> types;
	private boolean writeable;
	private boolean readable;
	private boolean port;
	private int lowerBound = 1;
	private int upperBound = 1;
	private boolean valid = true;
	private boolean many;
	private boolean visuallySuppress = true;
	private boolean main;
	private boolean noName;
	private String error;
	private boolean ignore;

	public BeanField(BeanClass parent, String name, Type type)
	{
		this.parent = parent;
		this.name = name;
		types = new ArrayList<Type>();
		types.add(type);
	}
	
	public BeanField(BeanClass parent, String name, List<Type> types)
	{
		this.parent = parent;
		this.name = name;
		this.types = types;
	}

	public String getName()
	{
		return name;
	}

	public void setWriteable(boolean writeable)
	{
		this.writeable = writeable;
	}
	
	public void setReadable(boolean readable)
	{
		this.readable = readable;
	}
	
	public boolean isReadOnly()
	{
		return !writeable;
	}
	
	public boolean isWriteOnly()
	{
		return !readable;
	}

	public boolean isPort()
	{
		return port;
	}

	public void setPort(boolean port)
	{
		this.port = port;
	}

	public int getLowerBound()
	{
		return lowerBound;
	}

	public void setLowerBound(int lowerBound)
	{
		this.lowerBound = lowerBound;
	}

	public int getUpperBound()
	{
		return upperBound;
	}

	public void setUpperBound(int upperBound)
	{
		this.upperBound = upperBound;
	}

	public boolean isValid()
	{
		return valid;
	}

	public void setValid(boolean valid)
	{
		this.valid = valid;
	}

	public List<Type> getTypes()
	{
		return types;
	}
	
	public String getTypesString(BeanFinder finder)
	{
		String ret = "";
		for (Type type : types)
		{
			String name = port ?
											finder.makeInterfaceTypeName(type.getClassName()) :
											finder.makeClassTypeName(type.getClassName());
			if (ret.length() == 0)
				ret = name;
			else
				ret += " / " + name;
		}
		return ret;
	}
	
	public void setMany(boolean many)
	{
		this.many = many;
	}
	
	public boolean isMany()
	{
		return many;
	}

	public boolean isVisuallySuppress()
	{
		return visuallySuppress;
	}
	
	public void toggleVisuallySuppress()
	{
		visuallySuppress = !visuallySuppress;
	}

	public void setVisuallySuppress(boolean visuallySuppress)
	{
		this.visuallySuppress = visuallySuppress;
	}

	public void setMain(boolean main)
	{
		this.main = main;
		this.visuallySuppress = false;
	}
	
	public boolean isMain()
	{
		return main;
	}
	
	public static String lastOf(String className)
	{
		int last = className.lastIndexOf('.');
		if (last == -1)
			return className;
		return className.substring(last + 1);
	}

	public Collection<String> getTypesNeeded()
	{
		List<String> ifaces = new ArrayList<String>();
		for (Type type : types)
			ifaces.add(type.getClassName().replace("/", "."));
		return ifaces;
	}

	public void fixUpUsingFinder(BeanFinder finder)
	{
		// remove any types that are not public
		List<Type> expunged = new ArrayList<Type>(types);
		for (Type type : types)
		{
			BeanClass cls = finder.locatePossibleBeanClass(type, port);
			if (cls != null && cls.getType() == BeanTypeEnum.BAD)
				expunged.remove(type);
		}
		types = expunged;
		
		// if the field is referring to a single primitive, it should be an attribute
		if (!canTogglePortOrAttribute(finder))
		{
			if (types.size() == 1 && finder.refersToPrimitive(types.get(0)))
					port = false;
			else
				// otherwise make it a port
				port = true;
		}
	}

	public BeanClass getBeanClass()
	{
		return parent;
	}
	
	public boolean canTogglePortOrAttribute(BeanFinder finder)
	{
		// can only toggle if our size is exactly 1
		if (types.size() != 1)
			return false;		
		return finder.refersToRealInterface(types.get(0));
	}
	
	public void togglePortOrAttribute(BeanFinder finder)
	{
		if (canTogglePortOrAttribute(finder))
			port = !port;
	}
	
	public boolean canToggleBeanOrPrimitiveOfTypes(BeanFinder finder)
	{
		for (Type type : types)
		{
			BeanClass cls = finder.locatePossibleBeanClass(type, port);
			if (cls == null || !cls.canToggleBeanOrPrimitive())
				return false;
		}
		return true;
	}

	public void toggleBeanOrPrimitiveOfTypes(BeanFinder finder)
	{
		for (Type type : types)
		{
			BeanClass cls = finder.locatePossibleBeanClass(type, port);
			if (cls != null)
				cls.toggleBeanOrPrimitive();
		}
	}

	public void markErrors(BeanFinder finder)
	{
		error = null;
		if (port)
			for (Type type : types)
			{
				BeanClass cls = finder.locatePossibleBeanClass(type, port);
				if (cls != null && cls.getType() == BeanTypeEnum.PRIMITIVE)
				{
					error = "Port refers incorrectly to a primitive type";
					return;
				}
			}
	}

	public boolean isInError()
	{
		return error != null;
	}
	
	public String getError()
	{
		return error;
	}
	
	public boolean isIgnore()
	{
		return ignore;
	}

	public void toggleIgnore()
	{
		ignore = !ignore;
	}

	public void fixUpDueToInterfaces(BeanFinder finder, int[] count)
	{
		if (types.size() == 1 && !port)
		{	
			Type type = types.get(0);

			// fix up any fields that point to interfaces, and should be ports
			// if the type an interface?
			boolean iface = finder.findInterface(null, type.getClassName()) != null;
			BeanClass existing = finder.locatePossibleBeanClass(type.getClassName(), true);
			iface |= existing != null && existing.getType() == BeanTypeEnum.INTERFACE;
			
			if (types.size() == 1 && finder.refersToBean(types.get(0)))
			{
				port = true;
				++count[0];
			}
			else
			if (iface)
			{
				port = true;
				++count[0];
			}
		}
	}

	public boolean isNoName()
	{
		return noName;
	}

	public void setNoName(boolean noName)
	{
		this.noName = noName;
	}
}
