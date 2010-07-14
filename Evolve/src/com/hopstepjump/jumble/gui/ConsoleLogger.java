package com.hopstepjump.jumble.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.swing.*;
import com.hopstepjump.swing.enhanced.*;

public class ConsoleLogger
{
	private OutputStream writer;
	private String version;
	private boolean modified;
	private Runnable modificationListener;
	private Logger logger = new Logger();
	private File logFile;
	
	public ConsoleLogger(String version, Runnable modificationListener)
	{
		this.version = version;
		this.modificationListener = modificationListener;
	}

	public void redirectOutputsToLog()
	{
		PrintStream out = new PrintStream(logger, true);
		System.setErr(out);
		System.setOut(out);		
	}
	
	private class Logger extends OutputStream
	{
		@Override
		public synchronized void write(int b) throws IOException
		{
			checkWriter();
			writer.write(b);
		}
		
		@Override
		public void close() throws IOException
		{
			writer.close();
			super.close();
		}
	}

	private void checkWriter() throws IOException
	{
		if (writer == null)
		{
			 try
			 {
				 logFile = File.createTempFile("evolve-log-", ".log");
				 logFile.deleteOnExit();
				 writer = new FileOutputStream(logFile);
				 modified = true;				 
				 modificationListener.run();
				 
				 // write the version number out
				 String eol = System.getProperty("line.separator");
				 writer.write((version + eol).getBytes());
				 writer.write(("Log file: " + logFile + eol).getBytes());
				 writer.write(("Time: " + new Date() + eol + eol).getBytes());
			 }
			 catch (Exception ex)
			 {
			 }
		}
	}

	public boolean isModified()
	{
		return modified;
	}

	public JMenuItem getDialogMenuItem(final ToolCoordinatorFacet coordinator)
	{
		JMenuItem item = new UpdatingJMenuItem("Log file")
		{
			public boolean update()
			{
				return modified;
			}
		};
		item.addActionListener(new ActionListener()
		{			
			public void actionPerformed(ActionEvent e)
			{
				JTextArea pane = new JTextArea();
				try
				{
					pane.setText(FileUtilities.loadFileContents(logFile));
				}
				catch (Exception ex)
				{
					pane.setText("Cannot find log file: " + logFile);
				}
				final JScrollPane scroller = new JScrollPane(pane);
				scroller.setPreferredSize(new Dimension(600, 400));
				
				coordinator.invokeAsDialog(null, "Evolve log: " + logFile, scroller, null, null);
			}
		});
		return item;
	}
	
	public void close()
	{
		if (logger != null)
			try
			{
				logger.close();
			}
			catch (IOException e)
			{
			}
		if (logFile != null)
			logFile.delete();
	}
}
