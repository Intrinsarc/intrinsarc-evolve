package com.intrinsarc.evolve;

import java.io.*;
import java.net.*;

import com.intrinsarc.evolve.gui.*;

/**
 * this is to provide a jar so that evolve can be run easily
 * @author andrew
 * 
 * todo:
 * 1. make class take impl class from name + default pkg, unless impl class set
 * 2. when importing, set impl class stereo unless it is same as auto
 * 3. remove stereo from elements in model unless it causes a problem
 * 4. apply rule to components and interfaces
 * 5. relax rule about composites, but still enforce no explicit class
 * 6. make impl class manipulator smart enough to understand auto and still alow setting
 * 7. ensure backbone still works using similar approach
 * 8. rename default-java-package to java-package
 */

public class EvolveRunner
{
	public static void main(String args[])
	{
		// if no arguments, pass in the actual home directory		
		boolean fromJars[] = {false};
		String home = getHomeDirectory(fromJars);
		
		// get the java executable directory
		String javaDir = System.getProperty("sun.boot.library.path");
		
		Evolve.main(new String[]{
				home,
				javaDir,
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
}
