package com.hopstepjump.jumble.gui;

import java.io.*;

public class ConsoleLogger extends OutputStream
{
	private BufferedOutputStream writer;
	private String logFile;
	private boolean modified;
	private Runnable modificationListener;
	
	public ConsoleLogger(String logFile, Runnable modificationListener)
	{
		this.logFile = logFile;
		this.modificationListener = modificationListener;
	}
	
	public void redirectOutputsToLog()
	{
		System.setErr(new PrintStream(this));
		System.setOut(new PrintStream(this));		
	}
	
	@Override
	public void close() throws IOException
	{
		writer.close();
		super.close();
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException
	{
		checkWriter();
		writer.write(b, off, len);
		flush();
		modified();
	}

	@Override
	public void write(byte[] b) throws IOException
	{
		checkWriter();
		writer.write(b);
		flush();
		modified();
	}

	@Override
	public void flush() throws IOException
	{
		writer.flush();
		super.flush();
		modified();
	}

	@Override
	public void write(int b) throws IOException
	{
		checkWriter();
		writer.write(b);
		flush();
		modified();
	}

	private void modified()
	{
		if (!modified)
		{
			modified = true;
			modificationListener.run();
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
				 writer = new BufferedOutputStream(new FileOutputStream(log));
			 }
			 catch (Exception ex)
			 {
				 ex.printStackTrace();
			 }
		}
	}
}
