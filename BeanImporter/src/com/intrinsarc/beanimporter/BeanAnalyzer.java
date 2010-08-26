package com.intrinsarc.beanimporter;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.swing.*;

public class BeanAnalyzer
{
	public static final ImageIcon BEAN_ADD_ICON = IconLoader.loadIcon("bean_add.png");

	private DEStratum perspective;
	private String classpath;
	private LongRunningTaskProgressMonitorFacet monitor;
	private BeanPackage root;
	private int countSoFar;

	public BeanAnalyzer(DEStratum perspective, String classpath, LongRunningTaskProgressMonitorFacet monitor, int countSoFar)
	{
		this.perspective = perspective;
		this.classpath = classpath;
		this.monitor = monitor;
		// create the root package
		root = new BeanPackage(classpath);
		this.countSoFar = countSoFar;
	}
	
	public BeanPackage getRoot()
	{
		return root;
	}
	
	public int analyzeClasses(BeanFinder finder) throws IOException
	{
		File file = new File(classpath);
		if (!file.exists())
			return countSoFar;

		// is this a directory or a jar?
		int count[] = new int[]{countSoFar};
		if (file.isDirectory())
			analyzeDirectory(finder, file, "", count);
		if (classpath.endsWith(".jar"))
			analyzeJar(finder, file, count);
		// if no jar or directory, don't bother
		return count[0];
	}

	private void analyzeDirectory(BeanFinder finder, File dir, String prefix, int count[]) throws FileNotFoundException, IOException
	{
		// start with this directory and recurse down
		if (!dir.isDirectory())
			return;
		
		for (File file : dir.listFiles())
		{
			if (file.isDirectory())
				analyzeDirectory(finder, file, file.getName(), count);
			else
			if (file.getName().endsWith(".class"))
				analyzeClass(finder, new FileInputStream(file), count);
		}
	}

	private void analyzeClass(BeanFinder finder, InputStream input, int count[]) throws IOException
	{
		try
		{
			// possibly update the monitor
			if (count[0]++ % 100 == 0)
				monitor.displayInterimPopup(BEAN_ADD_ICON, "Analyzing...", "Examined " + (count[0] - 1) + " elements", null, -1);
			
			ClassReader reader = new ClassReader(input);
			ClassNode node = new ClassNode();
			reader.accept(node, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
			
			String pkg = "";
			String name = node.name;
			int last = name.lastIndexOf('/');
			if (last != -1)
				pkg = name.substring(0, last).replace('/', '.');
			
			// if the classname is already in the repository, don't add this
			String nm = name.replace('/', '.');
			if (finder.findClass(null, nm) != null || finder.findInterface(null, nm) != null)
				return;
	
			Element refreshed = finder.getRefreshedClass(nm);
			if (refreshed == null)
				refreshed = finder.getRefreshedInterface(nm);
			BeanClass elem = new BeanClass(node, perspective, refreshed);
			BeanPackage current = root.locateOrCreatePackage(pkg);
			if (elem.getType() == BeanTypeEnum.BAD)
				current.addHidden(elem);
			else
			{
				if (elem.isHidden())
					current.addHidden(elem);
				else
					current.addBean(elem);
				
				// make a hidden interface even for non-beans, as they can be turned into beans later
				if (elem.isLegacyBean() && (elem.getType() == BeanTypeEnum.BEAN || elem.getType() == BeanTypeEnum.PRIMITIVE))
					current.addHidden(elem.makeInterfaceForBean());
			}
		}
		finally
		{
			input.close();
		}
	}

	private void analyzeJar(BeanFinder finder, File file, int count[]) throws IOException
	{
		JarFile jar = new JarFile(file);
		try
		{
			for (Enumeration<JarEntry> e = jar.entries(); e.hasMoreElements();)
			{
				JarEntry entry = e.nextElement();
				
				String name = entry.getName();
				if (name.endsWith(".class"))
				{					
					// is this a class with setters?
					analyzeClass(finder, jar.getInputStream(entry), count);
				}				
			}
		}
		finally
		{
			jar.close();
		}
	}

}
