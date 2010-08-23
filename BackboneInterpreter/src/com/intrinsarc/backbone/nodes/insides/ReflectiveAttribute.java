package com.intrinsarc.backbone.nodes.insides;

import java.lang.reflect.*;

import com.intrinsarc.backbone.exceptions.*;
import com.intrinsarc.backbone.nodes.*;
import com.intrinsarc.backbone.nodes.simple.*;
import com.intrinsarc.backbone.runtime.api.*;

public class ReflectiveAttribute
{ 
  private BBSimpleElement type;
  private Class<?> cls;
  private String attributeName;
  private Method setMethod;
  
  public ReflectiveAttribute(BBSimpleAttribute attribute) throws BBImplementationInstantiationException
  {
    this.type = attribute.getOwner();
    this.cls = attribute.getOwner().getImplementationClass();
    attributeName = attribute.getRawName();
    Class<?> implClass = attribute.getType().getImplementationClass();
    
    if (!attribute.isReadOnly())
      resolveSets(implClass);
  }

  private void resolveSets(Class<?> implClass) throws BBImplementationInstantiationException
  {
		setMethod = resolveMethod(
				"set" + upperFirst(attributeName),
				new Class<?>[]{PrimitiveHelper.translateLongToShortPrimitive(implClass)});
    
    if (setMethod == null)
      throw new BBImplementationInstantiationException("Cannot find method for setting attribute " + attributeName + " of class " + cls, type);      
  }

  private Method resolveMethod(String name, Class<?>[] paramClasses) throws BBImplementationInstantiationException
  {
    try
    {
			return cls.getMethod(name, paramClasses);
    }
    catch (SecurityException e)
    {
      throw new BBImplementationInstantiationException("Security error when getting attribute " + attributeName + " of class " + cls + " via method " + name + "(...)", type);
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

  public void set(Object target, Object value) throws BBRuntimeException
  {
    try
    {
  		setMethod.invoke(target, value);
    }
    catch (IllegalArgumentException e)
    {
      throw new BBRuntimeException("Illegal argument error when setting attribute " + attributeName + " of class " + cls, type);
    }
    catch (IllegalAccessException e)
    {
      throw new BBRuntimeException("Illegal access exception when setting attribute " + attributeName + " of class " + cls, type);
    }
    catch (InvocationTargetException e)
    {
      throw new BBRuntimeException("Invocation target exception when setting attribute " + attributeName + " of class " + cls, type, e.getCause());
    }
  }
}
