package com.intrinsarc.beanimporter;

import java.util.*;

import org.objectweb.asm.*;

public class BeanField
{
	private BeanClass parent;
	private String name;
	private List<Type> providedTypes;
	private List<Type> requiredTypes;
	private boolean writeable;
	private boolean readable;
	private boolean port;
	private int lowerBound = 1;
	private int upperBound = 1;
	private boolean valid = true;
	private boolean many;
	private boolean main;
	private boolean noName;
	private String error;
	private boolean ignore;

	public BeanField(BeanClass parent, String name, Type providedType, Type requiredType)
	{
		this.parent = parent;
		this.name = name;
		providedTypes = new ArrayList<Type>();
		if (providedType != null)
			providedTypes.add(providedType);
		if (requiredType != null)
		{
			requiredTypes = new ArrayList<Type>();
			requiredTypes.add(requiredType);
		}
	}
	
	public BeanField(BeanClass parent, String name, List<Type> providedTypes, List<Type> requiredTypes)
	{
		this.parent = parent;
		this.name = name;
		this.providedTypes = providedTypes;
		this.requiredTypes = requiredTypes;
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
		List<Type> all = new ArrayList<Type>();
		all.addAll(providedTypes);
		if (requiredTypes != null)
			all.addAll(requiredTypes);
		
		// if this is not a port, then compress into a single type for attributes
		if (!port && all.size() == 2)
			all.remove(1);
		
		return all;
	}
	
	public String getTypesString(BeanFinder finder)
	{
		String ret = "";
		if (port)
		{
			if (!providedTypes.isEmpty())
			{
				ret += " provides ";
				boolean start = true;
				for (Type type : providedTypes)
				{
					String name = finder.makeInterfaceTypeName(type.getClassName());
					if (start)
						ret += name;
					else
						ret += ", " + name;
					start = false;
				}
			}
			if (requiredTypes != null && !requiredTypes.isEmpty())
			{
				ret += " requires ";
				boolean start = true;
				for (Type type : requiredTypes)
				{
					String name = finder.makeInterfaceTypeName(type.getClassName());
					if (start)
						ret += name;
					else
						ret += ", " + name;
					start = false;
				}
			}
			return ret;
		}
		else
		{
			return finder.makeClassTypeName(getTypes().get(0).getClassName());
		}
	}
	
	public void setMany(boolean many)
	{
		this.many = many;
	}
	
	public boolean isMany()
	{
		return many;
	}

	public void setMain(boolean main)
	{
		this.main = main;
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
		for (Type type : providedTypes)
			ifaces.add(type.getClassName().replace("/", "."));
		if (requiredTypes != null)
			for (Type type : requiredTypes)
				ifaces.add(type.getClassName().replace("/", "."));
		return ifaces;
	}

	public void fixUpUsingFinder(BeanFinder finder)
	{
		// remove any types that are not public
		List<Type> expunged = new ArrayList<Type>(providedTypes);		
		for (Type type : providedTypes)
		{
			BeanClass cls = finder.locatePossibleBeanClass(type, port);
			if (cls != null && cls.getType() == BeanTypeEnum.BAD)
				expunged.remove(type);
		}
		providedTypes = expunged;

		if (requiredTypes != null)
		{
			expunged = new ArrayList<Type>(requiredTypes);		
			for (Type type : requiredTypes)
			{
				BeanClass cls = finder.locatePossibleBeanClass(type, port);
				if (cls != null && cls.getType() == BeanTypeEnum.BAD)
					expunged.remove(type);
			}
			requiredTypes = expunged;
		}
		
		// if the field is referring to a single primitive, it should be an attribute
		if (!canTogglePortOrAttribute(finder))
		{
			if (getTypes().size() == 1 && finder.refersToPrimitive(getTypes().get(0)))
				port = false;
			else
				port = true;
		}
	}

	public BeanClass getBeanClass()
	{
		return parent;
	}
	
	public boolean canTogglePortOrAttribute(BeanFinder finder)
	{
		// can only toggle if provided and required are 1 or less
		// and if both are 1, then both must be the same
		// also, what they refer to must be a real interface, not a fake interface or primitive
		int pSize = providedTypes.size();
		int rSize = requiredTypes != null ? requiredTypes.size() : 0;
		
		if (pSize > 1 || rSize > 1)
			return false;
		if (pSize == 1 && rSize == 1)
			if (!providedTypes.get(0).equals(requiredTypes.get(0)))
				return false;
		return finder.refersToRealInterface(getTypes().get(0));
	}
	
	public void togglePortOrAttribute(BeanFinder finder)
	{
		if (canTogglePortOrAttribute(finder))
			port = !port;
	}
	
	public boolean canToggleBeanOrPrimitiveOfTypes(BeanFinder finder)
	{
		for (Type type : providedTypes)
		{
			BeanClass cls = finder.locatePossibleBeanClass(type, port);
			if (cls == null || !cls.canToggleBeanOrPrimitive())
				return false;
		}
		if (requiredTypes != null)
			for (Type type : requiredTypes)
			{
				BeanClass cls = finder.locatePossibleBeanClass(type, port);
				if (cls == null || !cls.canToggleBeanOrPrimitive())
					return false;
			}
		return true;
	}

	public void toggleBeanOrPrimitiveOfTypes(BeanFinder finder)
	{
		for (Type type : getTypes())
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
		{
			for (Type type : getTypes())
			{
				BeanClass cls = finder.locatePossibleBeanClass(type, port);
				if (cls != null && cls.getType() == BeanTypeEnum.PRIMITIVE)
				{
					error = "Port refers incorrectly to a primitive type";
					return;
				}
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
		if (getTypes().size() == 1 && !port)
		{	
			Type type = getTypes().get(0);

			// fix up any fields that point to interfaces, and should be ports
			// if the type an interface?
			boolean iface = finder.findInterface(null, type.getClassName()) != null;
			BeanClass existing = finder.locatePossibleBeanClass(type.getClassName(), true);
			iface |= existing != null && existing.getType() == BeanTypeEnum.INTERFACE;
			
			if (getTypes().size() == 1 && finder.refersToBean(getTypes().get(0)))
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

	public void addRequiredType(Type type)
	{
		if (requiredTypes == null)
			requiredTypes = new ArrayList<Type>();
		requiredTypes.add(type);
	}

	public void addProvidedType(Type type)
	{
		providedTypes.add(type);
	}

	public List<Type> getProvidedTypes()
	{
		return providedTypes;
	}

	public List<Type> getRequiredTypes()
	{
		if (requiredTypes == null)
			return new ArrayList<Type>();
		return requiredTypes;
	}
}
