package com.intrinsarc.swing;

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * allows icons and descriptions to be associated with file extensions
 */
public class CustomisedFileChooser extends JFileChooser
{
	private static Map<String, Icon> icons = new HashMap<String, Icon>();
	private static Map<String, String> descriptions = new HashMap<String, String>();
	
	public static void addFileType(String extension, Icon icon, String description)
	{
		icons.put(extension, icon);
		descriptions.put(extension, description);
	}
	
	public CustomisedFileChooser()
	{
		setupFileView();
	}
	
	public CustomisedFileChooser(String startDir)
	{
		super(startDir);
		setupFileView();
	}

	public CustomisedFileChooser(File startDir)
	{
		super(startDir);
		setupFileView();
	}

	private void setupFileView()
	{
		setFileView(new FileView()
		{
			public String getDescription(File f)
			{
				String extension = getExtension(f);
				if (descriptions.containsKey(extension))
					return descriptions.get(extension);
				return super.getDescription(f);
			}

			public Icon getIcon(File f)
			{
				String extension = getExtension(f);
				if (icons.containsKey(extension))
					return icons.get(extension);
				return super.getIcon(f);
			}

			private String getExtension(File f)
			{
				int lastIndex = f.getName().lastIndexOf('.');
				if (lastIndex == -1)
					return null;
				return f.getName().substring(lastIndex + 1).toLowerCase();
			}
		});
	}
}
