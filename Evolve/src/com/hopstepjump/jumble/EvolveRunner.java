package com.hopstepjump.jumble;

import java.io.*;
import java.net.*;

import com.hopstepjump.jumble.gui.*;

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
		String home;
		boolean fromJars[] = {false};
		if (args.length == 0)
			home = getHomeDirectory(fromJars);
		else
			home = args[0];
		
		Evolve.main(new String[]{
				home,
				fromJars[0] ? "logToFile" : null});
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
