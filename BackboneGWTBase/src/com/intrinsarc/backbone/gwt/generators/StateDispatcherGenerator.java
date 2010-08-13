package com.intrinsarc.backbone.gwt.generators;

import java.io.*;
import java.util.*;

import com.google.gwt.core.ext.*;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.user.rebind.*;

public class StateDispatcherGenerator extends Generator
{
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException
	{
		try
		{
			TypeOracle typeOracle = context.getTypeOracle();
			JClassType classType = typeOracle.getType(typeName);
			JParameterizedType parameterizedType = classType.getSuperclass().isParameterized();
			JClassType T = parameterizedType.getTypeArgs()[0];
			logger.log(TreeLogger.DEBUG, "Parameterised type = " + T.getName());

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
		composerFactory.addImplementedInterface("com.hopstepjump.backbone.runtime.api.IStateDispatcher");
		composerFactory.addImport("com.hopstepjump.backbone.runtime.api.*");
		return composerFactory.createSourceWriter(context, printWriter);
	}

	protected void writeToStringMethod(TreeLogger logger, String proxyClassName, JClassType T, SourceWriter writer)
	{
		writer.println("private java.util.List<IEvent> dDispatch_IEventRequired = new java.util.ArrayList<IEvent>();");
		writer.println("public void setDDispatch_IEvent(IEvent event, int index) { PortHelper.fill(this.dDispatch_IEventRequired, event, index); }");	
		writer.println("private ITerminal dStart_ITerminalRequired;");
		writer.println("public void setDStart_ITerminal(ITerminal start) { dStart_ITerminalRequired = start; }");
		writer.println("private java.util.List<ITerminal> dEnd_ITerminalRequired = new java.util.ArrayList<ITerminal>();");
		writer.println("public void setDEnd_ITerminal(ITerminal end, int index) { PortHelper.fill(this.dEnd_ITerminalRequired, end, index); }");
		writer.println("private boolean currentEntered;");
		writer.println("private IEvent current;");
		
		writer.println("  private void findNextState()");
		writer.println("  {");
		writer.println("    // possibly still in start or end?");
		writer.println("    com.google.gwt.core.client.GWT.log(\"States = \" + dDispatch_IEventRequired.size(), null);");
		writer.println("    if (dStart_ITerminalRequired != null && dStart_ITerminalRequired.isCurrent())");
		writer.println("      dStart_ITerminalRequired.moveToNextState(); if (dEnd_ITerminalRequired != null)");
		writer.println("    for (ITerminal end : dEnd_ITerminalRequired)");
		writer.println("      if (end.isCurrent())");
		writer.println("		     end.moveToNextState();");
		writer.println("    // find the next state");
		writer.println("    current = null;");
		writer.println("    for (IEvent e : dDispatch_IEventRequired)");
		writer.println("      if (e.isCurrent())");
		writer.println("      {");
		writer.println("		     current = e;");
		writer.println("		     break;");
		writer.println("      }");
		writer.println("  }");
		writer.println();
		
		writer.println("public IEvent getDEvents_IEvent(Class<?> required)");
		writer.println("{");
		writer.println("  return new " + T.getQualifiedSourceName() + "()");
		writer.println("  {");
		Set<JMethod> all = new LinkedHashSet<JMethod>();
		getAllMethods(all, T);
		for (JMethod method : all)
		{
			String ret = method.getReturnType().toString();
			boolean isVoid = ret.equals("void");			
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
			writer.println("  findNextState();");
			writer.println("  if (current != null)");
			writer.println("  {");
			writer.print("    ");
			if (!isVoid)
				writer.print("return ");
			String cast = "(" + T.getQualifiedSourceName() + ")";
			writer.println("(" + cast + "current)." + method.getName() + "(" + params + ");");
			writer.println("  }");
			if (ret.equals("boolean"))
				writer.println("    return false;");
			else
			if (!isVoid)
				writer.println("    return null;");
			writer.println("}");
		}
		writer.println("  };\n");

		writer.println("}\n");
	}

	private void getAllMethods(Set<JMethod> methods, JClassType t)
	{
		for (JMethod m : t.getMethods())
			methods.add(m);
		for (JClassType i : t.getImplementedInterfaces())
			getAllMethods(methods, i);
	} 
}
