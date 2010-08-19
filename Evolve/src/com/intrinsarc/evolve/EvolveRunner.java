package com.intrinsarc.evolve;

import java.io.*;
import java.net.*;

import com.intrinsarc.evolve.gui.*;

/**
 * this is to provide a jar so that evolve can be run easily
 * @author andrew
 *
 */
public class EvolveRunner
{
	public static void main(String args[])
	{
		// if no arguments, pass in the actual home directory		
		boolean fromJars[] = {false};
		String home = getHomeDirectory(fromJars);
		
		Evolve.main(new String[]{
				home,
				fromJars[0] ? "logToFile" : null,
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
