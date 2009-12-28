package com.hopstepjump.backbone.gwt.generators;

import java.io.*;

import com.google.gwt.core.ext.*;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.user.rebind.*;

public class TerminalGenerator extends Generator
{
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException
	{
		try
		{
			TypeOracle typeOracle = context.getTypeOracle();
			JClassType classType = typeOracle.getType(typeName);
			JParameterizedType parameterizedType = classType.getSuperclass().isParameterized();
			JClassType T = parameterizedType.getTypeArgs()[0];
			String packageName = classType.getPackage().getName();
			String proxyClassName = classType.getSimpleSourceName() + "Dispatcher";
			String qualifiedProxyClassName = packageName + "." + proxyClassName;

			SourceWriter writer = getSourceWriter(logger, context, packageName, proxyClassName);
			if (writer != null)
			{
				writeToStringMethod(logger, proxyClassName, T, writer);
				writer.commit(logger);
			}
			return qualifiedProxyClassName;
		}
		catch (NotFoundException e)
		{
			logger.log(TreeLogger.ERROR, "Class '" + typeName + "' Not Found", e);
			throw new UnableToCompleteException();
		}
	}

	protected SourceWriter getSourceWriter(TreeLogger logger, GeneratorContext context, String packageName, String className)
	{
		PrintWriter printWriter = context.tryCreate(logger, packageName, className);
		if (printWriter == null)
			return null;
		ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(packageName, className);
		composerFactory.addImplementedInterface("com.hopstepjump.backbone.runtime.api.IStateTerminalComponent");
		composerFactory.addImport("com.hopstepjump.backbone.runtime.api.*");
		return composerFactory.createSourceWriter(context, printWriter);
	}

	protected void writeToStringMethod(TreeLogger logger, String proxyClassName, JClassType T, SourceWriter writer)
	{
		writer.println("private ITransition out_ITransitionRequired;"); 
		writer.println("public void setOut_ITransition(ITransition out) { out_ITransitionRequired = out; }");
		writer.println("private ITerminal terminal_ITerminalProvided = new ITerminalImpl();");
		writer.println("public ITerminal getTerminal_ITerminal(Class<?> required) { return terminal_ITerminalProvided; }");
		writer.println("private boolean current;");
		writer.println("private boolean fired;");
		writer.println();
		writer.println("private class ITerminalImpl implements ITerminal");
		writer.println("{");
		writer.println("	public boolean isCurrent() { return false; }");
		writer.println("	public void moveToNextState() {}");
		writer.println("}");
		writer.println("");
		writer.println("private ITransition proxy;");
		writer.println("public ITransition getIn_ITransition(Class<?> required)");
		writer.println("{");
		writer.println("  return new " + T.getName() + "()");
		writer.println("  {");
		for (JMethod method : T.getMethods())
		{
			String ret = method.getReturnType().toString();
			writer.print("    public " + ret + " " + method.getName() + "(");
			int lp = 0;
			String params = "";
			for (JParameter p : method.getParameters())
			{
				if (lp != 0)
					params += ", ";
				lp++;
				params += p.getType() + " param" + lp;
			}
			writer.println(params + ")");
			writer.println("{");
			writer.println("  if (out_ITransitionRequired != null)");
			writer.println("  {");
			writer.println("    terminal_ITerminalProvided.moveToNextState();");
			writer.print("    ");
			if (!ret.equals("void"))
				writer.print("return ");
			String cast = "(" + T.getName() + ")";
			writer.println("(" + cast + "out_ITransitionRequired)." + method.getName() + "(" + params + ");");
			writer.println("  }");
			writer.println("    return false;");
			writer.println("}");
		}
		writer.println("  };");
		writer.println("};");
	}
}
