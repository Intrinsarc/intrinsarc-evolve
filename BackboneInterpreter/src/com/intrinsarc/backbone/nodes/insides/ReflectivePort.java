package com.intrinsarc.backbone.nodes.insides;

import java.lang.reflect.*;

import com.intrinsarc.backbone.exceptions.*;
import com.intrinsarc.backbone.nodes.simple.*;

public class ReflectivePort
{ 
  private BBSimpleElement partType;
  private BBSimplePort port;
  private Class<?> partTypeClass;
  private String portName;
  /** for things that resolve to methods */
  private Method getSingle;
  private Method getIndexed;
  private Method setSingle;
  private Method addNoIndexed;
  private Method addIndexed;
  private Method removeMany;
  private boolean isBean;
  
  public ReflectivePort(
  		BBSimpleComponent partType,
      BBSimplePort port,
      Class<?> iface,
      boolean required) throws BBImplementationInstantiationException
  {
    this.port = port;
    partTypeClass = partType.getImplementationClass();
    portName = port.getRawName();
    isBean = partType.isBean();
    if (!required)
      resolveGets(iface, port.isIndexed());
    else
      resolveSets(iface, port.isIndexed());
  }

  private void resolveGets(Class<?> iface, boolean many) throws BBImplementationInstantiationException
  {
    // main is just the object itself
    if (port.getComplexPort().isBeanMain())
      return;
    
    // look for a method if this is a bean
    if (isBean)
    {
    	if (many)
        throw new BBImplementationInstantiationException("Beans cannot support ports that provide many: " + portName + " of " + partTypeClass, partType);
    		
      String methodName = "get" + (many ? makeSingular(portName) : upperFirst(portName));
      getSingle = resolveMethod(methodName, null);
    }
    else
    {
      String methodName = "get" + upperFirst(portName) + "_" + getInterfaceName(iface);
      if (!many)
      getSingle = resolveMethod(
          methodName,
          new Class<?>[]{Class.class});
      getIndexed = resolveMethod(
          methodName,
          new Class<?>[]{Class.class, int.class});    		
    }

    if (getSingle == null && getIndexed == null)
      throw new BBImplementationInstantiationException("Cannot find method for getting port " + portName + " of " + partTypeClass, partType);
    
    if (isBean)
    {
    	if (many)
    	{
        String methodName2 = "remove" + (port.getComplexPort().isBeanNoName() ? "" : makeSingular(upperFirst(portName)));
        removeMany = resolveMethod(
            methodName2,
            new Class<?>[]{iface});
        if (removeMany == null)
          throw new BBImplementationInstantiationException("Cannot find method for removing provided port " + portName + " of " + partTypeClass, partType);
    	}
    }
    else
    {
      if (many)
      {
        String methodName2 = "remove" + upperFirst(portName) + "_" + getInterfaceName(iface);
        removeMany = resolveMethod(
            methodName2,
            new Class<?>[]{iface});
        if (removeMany == null)
          throw new BBImplementationInstantiationException("Cannot find method for removing provided port " + portName + " of " + partTypeClass, partType);
      }
    }
  }

  private void resolveSets(Class<?> iface, boolean many) throws BBImplementationInstantiationException
  {
    if (isBean)
    {
    	if (!many)
    	{
    		String methodName = "set" + upperFirst(portName);
    		setSingle = resolveMethod(
            methodName,
            new Class<?>[]{iface});
    	}
    	else
    	{
        String methodName = "add" + (port.getComplexPort().isBeanNoName() ? "" : makeSingular(upperFirst(portName)));
        addNoIndexed = resolveMethod(
            methodName,
            new Class<?>[]{iface});
        String methodName2 = "remove" + (port.getComplexPort().isBeanNoName() ? "" : makeSingular(upperFirst(portName)));
        removeMany = resolveMethod(
            methodName2,
            new Class<?>[]{iface});
    	}
    }
    else
    {
      String methodName = "set" + upperFirst(portName) + "_" + getInterfaceName(iface);
      if (!many)
      {
	      setSingle = resolveMethod(
	          methodName,
	          new Class<?>[]{iface});
      }
      else
      {
        addIndexed = resolveMethod(
            methodName,
            new Class<?>[]{iface, int.class});
        String methodName2 = "remove" + upperFirst(portName) + "_" + getInterfaceName(iface);
        removeMany = resolveMethod(
            methodName2,
            new Class<?>[]{iface});
      }
    }
 
    if (setSingle == null && addNoIndexed == null && addIndexed == null)
      throw new BBImplementationInstantiationException("Cannot find method for setting port " + portName + " of " + partTypeClass + ", bean = " + isBean, partType);      
  }

  private String makeSingular(String name)
	{
  	if (name.endsWith("s") && name.length() > 1)
  		return name.substring(0, name.length() - 1);
  	return name;
	}

	private String getInterfaceName(Class<?> iface)
  {
    return iface.getSimpleName();
  }

  private Method resolveMethod(String name, Class<?>[] paramClasses) throws BBImplementationInstantiationException
  {
    try
    {
      return partTypeClass.getMethod(name, paramClasses);
    }
    catch (SecurityException e)
    {
      throw new BBImplementationInstantiationException("Security error when getting port method " + name + " of " + partTypeClass + " via method " + name + "(...)", partType);
    }
    catch (NoSuchMethodException e)
    {
      return null;
    }
  }

  private String upperFirst(String str)
  {
    if (str.length() > 1)
      return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    return str.toUpperCase();
  }
  
  public Object getSingle(Class<?> required, Object target) throws BBRuntimeException
  {
    // if this is main, return the target
    if (port.getComplexPort().isBeanMain())
      return target;
    
    try
    {
      if (getSingle != null)
          return getSingle.invoke(target, required);
      else      
        throw new BBRuntimeException("No single getXXX(...) method or field for port " + portName + " in class " + partTypeClass, partType);
    }
    catch (IllegalArgumentException e)
    {
      throw new BBRuntimeException("Illegal argument error when getting port " + portName + " of " + partTypeClass, partType);
    }
    catch (IllegalAccessException e)
    {
      throw new BBRuntimeException("Illegal access exception when getting port " + portName + " of " + partTypeClass, partType);
    }
    catch (InvocationTargetException e)
    {
      throw new BBRuntimeException("Invocation target exception when getting port " + portName + " of " + partTypeClass, partType, e.getCause());
    }
  }
  
  
  public Object getIndexed(Class<?> required, Object target, int index) throws BBRuntimeException
  {
    // can't use an index on a bean
    if (isBean)
      throw new BBRuntimeException("Cannot use an index with a bean getXXX(...) method", port.getOwner());                

    try
		{
			return getIndexed.invoke(target, required, index);
		}
    catch (IllegalArgumentException e)
    {
      throw new BBRuntimeException("Illegal argument error when setting port " + portName + " of " + partTypeClass, partType);
    }
    catch (IllegalAccessException e)
    {
      throw new BBRuntimeException("Illegal access exception when setting port " + portName + " of " + partTypeClass, partType);
    }
    catch (InvocationTargetException e)
    {
      throw new BBRuntimeException("Invocation target exception when setting port " + portName + " of " + partTypeClass, partType, e.getCause());
    }
  }

  /**
   * set a single value
   * @param target
   * @param value
   * @throws BBRuntimeException
   */
  public void setSingle(Object target, Object value) throws BBRuntimeException
  {
    try
    {
      if (setSingle != null)
        setSingle.invoke(target, value);
      else
        throw new BBRuntimeException("No single setXXX(...) method or field for port " + portName + " in class " + partTypeClass, partType);
    }
    catch (IllegalArgumentException e)
    {
      throw new BBRuntimeException("Illegal argument error when setting port " + portName + " of " + partTypeClass, partType);
    }
    catch (IllegalAccessException e)
    {
      throw new BBRuntimeException("Illegal access exception when setting port " + portName + " of " + partTypeClass, partType);
    }
    catch (InvocationTargetException e)
    {
      throw new BBRuntimeException("Invocation target exception when setting port " + portName + " of " + partTypeClass, partType, e.getCause());
    }
  }
  
  public void remove(Object target, Object value) throws BBRuntimeException
  {  	
    try
    {
    	if (removeMany != null)
    		removeMany.invoke(target, value);
      else
      if (setSingle != null)
      	setSingle.invoke(target, (Object[])(null));
      else
        throw new BBRuntimeException("No removeXXX(...) method for port " + portName + " in class " + partTypeClass, partType);
    }
    catch (IllegalArgumentException e)
    {
      throw new BBRuntimeException("Illegal argument error when removing from port " + portName + " of " + partTypeClass, partType);
    }
    catch (IllegalAccessException e)
    {
      throw new BBRuntimeException("Illegal access exception when removing from port " + portName + " of " + partTypeClass, partType);
    }
    catch (InvocationTargetException e)
    {
      throw new BBRuntimeException("Invocation target exception when removing from port " + portName + " of " + partTypeClass, partType, e.getCause());
    }
  }
  
  /**
   * add a value to the list at the index provided
   * @param target
   * @param value
   * @throws BBRuntimeException
   */
  public void addIndexed(Object target, Object value, int index) throws BBRuntimeException
  {
    // can't use an index on a bean
    if (isBean && index != -1)
      throw new BBRuntimeException("Cannot use an index with a bean addXXX(...) method: index = " + index + " of port " + port.getName() + ", when upper bound is " + port.getUpperBound(), port.getOwner());

    try
    {
      if (addNoIndexed != null)
        addNoIndexed.invoke(target, value);
      else
      if (addIndexed != null)
      	addIndexed.invoke(target, value, index);
      else
        throw new BBRuntimeException("No addXXX(...) method for port " + portName + " in class " + partTypeClass, partType);
    }
    catch (IllegalArgumentException e)
    {
      throw new BBRuntimeException("Illegal argument error when adding to port " + portName + " of " + partTypeClass, partType);
    }
    catch (IllegalAccessException e)
    {
      throw new BBRuntimeException("Illegal access exception when adding to port " + portName + " of " + partTypeClass, partType);
    }
    catch (InvocationTargetException e)
    {
      throw new BBRuntimeException("Invocation target exception when adding to port " + portName + " of " + partTypeClass, partType, e.getCause());
    }
  }
}
