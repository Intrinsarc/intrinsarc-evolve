package com.intrinsarc.evolve;

import java.io.*;
import java.net.*;

import com.intrinsarc.evolve.gui.*;

/**
 * this is to provide a jar so that evolve can be run easily
 * @author andrew
 * 
 * todo:
 *
 *2. add in delta mark for replacing element
 *3. remove all implementation-class values, selectively add gwt ones back and bb ones back
 *4. change default-java-package to just java-package
 *5. fix model moving bug
 *6. remove impl class manipulator
 *7. complain if stratum has leaf or interface but no java-package
 *8. error if name of leaf is not a java identifier, also interface
 *9. error on leaf when multiple names are there, also interface
 *10. error only if composite has or inherits an implementation class name
 * 2. when importing, set impl class stereo unless it is same as auto
 * 
 * 4. apply rule to components and interfaces
 * 5. relax rule about composites, but still enforce no explicit class
 * 6. make impl class manipulator smart enough to understand auto and still allow setting
 * 7. ensure backbone still works using similar approach
 * 8. rename default-java-package to java-package
 * 
 * 3. ant scripts for release
 * --------------------------------------------------------------
 * (done)add an easy way to see implementation class of a leaf
 * (done) fix appliedbasicstereotypevalue not going to deleted state when property removed
 * (done) delete model back to ctrl-D
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
