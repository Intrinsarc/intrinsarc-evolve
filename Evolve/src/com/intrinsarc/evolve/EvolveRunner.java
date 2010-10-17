package com.intrinsarc.evolve;

import java.io.*;
import java.net.*;

import com.intrinsarc.evolve.gui.*;
import com.intrinsarc.swing.lookandfeel.*;

/**
 * this is to provide a jar so that evolve can be run easily
 * @author andrew
 */

public class EvolveRunner
{
	public static void main(String args[])
	{
		macSetup("Evolve");
		
		// if no arguments, pass in the actual home directory		
		boolean fromJars[] = {false};
		String home = getHomeDirectory(fromJars);
		
		// get the java executable directory
		Evolve.main(new String[]{
				home,
				null,
				fromJars[0] && System.getProperty("logToConsole") == null ? "logToFile" : null,
				args.length > 0 ? args[0] : null});
	}
	
	private static String getHomeDirectory(boolean fromJars[])
	{
		// relies on the fact that this jar will be deployed one level below the root level
		File start = new File(EvolveRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		fromJars[0] = start.getPath().endsWith(".jar");
		
		// parent should work for local path in Eclipse, and also when deployed as a top level jar
		start = start.getParentFile();
		return URLDecoder.decode(start.getPath());
	}
	
	private static void macSetup(String appName)
	{
	  String os = System.getProperty("os.name").toLowerCase();
	  boolean isMac = os.indexOf("mac") >= 0;   
	  if (!isMac)
	     return;
	 
	  System.setProperty("com.apple.mrj.application.apple.menu.about.name", appName);
		RegisteredGraphicalThemesPreferences.PREFER_SYSTEM = true;
	 }
}
