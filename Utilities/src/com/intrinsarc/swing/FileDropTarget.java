package com.intrinsarc.swing;

import java.io.*;

import javax.swing.*;

public class FileDropTarget extends TransferHandler
{
/*	private Listener listener;
	
	public interface Listener
	{
		boolean acceptFile(File file);
	}

	public FileDropTarget(JFrame frame, Listener listener)
	{
		this.listener = listener;
		frame.setTransferHandler(this);
	}
	
  public boolean canImport(TransferSupport info)
  {
    if (!info.isDrop())
      return false;
    return info.isDataFlavorSupported (java.awt.datatransfer.DataFlavor.javaFileListFlavor);
  }

  private File extractFile(TransferSupport info)
  {
		try
		{
			java.util.List fileList = (java.util.List) info.getTransferable().getTransferData(java.awt.datatransfer.DataFlavor.javaFileListFlavor);
      java.util.Iterator iterator = fileList.iterator();
      if (iterator.hasNext())
      	return (File) iterator.next(); 
		}
		catch (Exception e)
		{
		}
		return null;
  }
  
  public boolean importData(TransferHandler.TransferSupport info)
  {
    return listener.acceptFile(extractFile(info));
  }
*/}
