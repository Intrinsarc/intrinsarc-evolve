package com.hopstepjump.wmf;

import java.awt.datatransfer.*;
import java.io.*;

/**
 *
 * (c) Andrew McVeigh 14-Jan-03
 *
 */
public class MetafileTransferable implements Transferable, ClipboardOwner
{
	protected InputStream text;
	
	public MetafileTransferable(InputStream text)
	{
		this.text = text;
	}
	
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
	{
		if (isDataFlavorSupported(flavor))
	  	return text;
	  throw new UnsupportedFlavorException(flavor);
	}
	
	public DataFlavor[] getTransferDataFlavors()
	{
		return new DataFlavor[]{new DataFlavor("text/rtf", null)};
	}
	
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return flavor.equals(getTransferDataFlavors()[0]);
	}
	
	public void lostOwnership(Clipboard in_cb,Transferable t)
	{
	}   
}
