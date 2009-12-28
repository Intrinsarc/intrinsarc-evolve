package com.mycompany.generator;

import java.io.*;

import com.google.gwt.core.ext.*;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.user.rebind.*;

public class TestGenerator extends Generator
{
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException
	{
        try
        {
 	       TypeOracle typeOracle = context.getTypeOracle();
        	JClassType classType = typeOracle.getType(typeName);
			
			String packageName = classType.getPackage().getName();
			String proxyClassName = classType.getSimpleSourceName() + "Dispatcher";
			String qualifiedProxyClassName = packageName + "." + proxyClassName;
			
			SourceWriter writer = getSourceWriter(logger, context, packageName, proxyClassName);
		    writeToStringMethod(proxyClassName, writer);
		    writer.commit(logger);
		    return qualifiedProxyClassName;
		}
	    catch (NotFoundException e)
	    {
	      logger.log(TreeLogger.ERROR,
	        "Class '" + typeName + "' Not Found", e);
	        throw new UnableToCompleteException();
	    }
	}
	
	  protected SourceWriter getSourceWriter(
			    TreeLogger logger, GeneratorContext context,
			    String packageName, String className)
	  {
	    PrintWriter printWriter = context.tryCreate(logger, packageName, className);
	    if (printWriter == null)
	    	return null;
	    ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(packageName, className);
	    composerFactory.addImplementedInterface("com.mycompany.client.IFace");
	    return composerFactory.createSourceWriter(context, printWriter);
	  }

	  protected void writeToStringMethod(String proxyClassName, SourceWriter writer)
	  {
	    writer.println();
	    writer.println("public String getMessage()");
	    writer.println("{");
	    writer.indent();
	    writer.println("return \"Hello, from " + proxyClassName + "\";");
	    writer.outdent();
	    writer.println("}");
	  } 
}
