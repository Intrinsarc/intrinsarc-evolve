package com.hopstepjump.jumble.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.swing.*;
import com.hopstepjump.swing.enhanced.*;

public class ConsoleLogger
{
	private OutputStream writer;
	private String logFile;
	private String version;
	private boolean modified;
	private Runnable modificationListener;
	private Logger logger = new Logger();
	
	public ConsoleLogger(String logFile, String version, Runnable modificationListener)
	{
		this.logFile = logFile;
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
	}

	private void checkWriter() throws IOException
	{
		if (writer == null)
		{
			 try
			 {
				 File log = new File(logFile);
				 log.getParentFile().mkdirs();
				 writer = new FileOutputStream(log);
				 modified = true;				 
				 modificationListener.run();
				 
				 // write the version number out
				 String eol = System.getProperty("line.separator");
				 writer.write((version + eol + eol).getBytes());
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
					pane.setText(FileUtilities.loadFileContents(new File(logFile)));
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
}
