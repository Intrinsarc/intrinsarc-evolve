package com.hopstepjump.layout;

import java.lang.reflect.*;
import java.util.*;

import com.hopstepjump.geometry.*;

public class BeanBoundsSetter
{
	private Object bean;
	private HashMap<String, Method> map = new HashMap<String, Method>();
	private boolean cachedMethods;
	
	/**
	 *@param bean
	 */
	public BeanBoundsSetter(Object bean)
	{
		this.bean = bean;
	}
	
	/**
	 *@param name
	 *@param bounds
	 *@throws LayoutException
	 */
	
	public void setBounds(String name, UBounds bounds) throws LayoutException
	{
		if (name == null || name.length() == 0 || name.startsWith(">"))
			return;
			
		cacheSetterMethods();
		
		String upperName = toUpper(name);
		
		// use reflection to invoke the correct method
		Method setter = map.get("set" + upperName);
		if (setter == null)
			throw new LayoutException("Cannot find set" + upperName + " method in bean");
			
		try
		{
			setter.invoke(bean, new Object[]{bounds});
		}
		catch (InvocationTargetException ex)
		{
			throw new LayoutException("Cannot invoke setter method " + upperName + " on bean");
		}
		catch (IllegalAccessException ex)
		{
			throw new LayoutException("Cannot access setter method set" + upperName + " on bean");
		}
	}
	
	/**
	 *@param name
	 *@return 
	 */
	private String toUpper(String name)
	{
		if (name.length() == 0)
			return "";
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	private void cacheSetterMethods()
	{
		if (cachedMethods)
			return;
			
		cachedMethods = true;
		Method[] methods = bean.getClass().getMethods();
		for (int lp = 0; lp < methods.length; lp++)
		{
			Method method = methods[lp];
			String methodName = method.getName();
			
			if (methodName.startsWith("set"))
			{
				Class[] params = method.getParameterTypes();
				if (params.length == 1 && params[0] == UBounds.class)
					map.put(methodName, method);
			}
		}
	}
}
