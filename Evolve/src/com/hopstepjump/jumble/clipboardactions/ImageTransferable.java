package com.hopstepjump.jumble.clipboardactions;

import java.awt.*;
import java.awt.datatransfer.*;

public class ImageTransferable extends Object implements Transferable, ClipboardOwner
{
  protected Image image;
  
  public ImageTransferable(Image image)
  {
    this.image = image;
  }
  
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
  {
    if (isDataFlavorSupported(flavor))
      return image;
    throw new UnsupportedFlavorException(flavor);
  }
  
  public DataFlavor[] getTransferDataFlavors()
  {
    return new DataFlavor[]{new DataFlavor("image/x-java-image;class=java.awt.Image", null)};
  }
  
  public boolean isDataFlavorSupported(DataFlavor flavor)
  {
    return flavor.equals(getTransferDataFlavors()[0]);
  }
  
  public void lostOwnership(Clipboard in_cb,Transferable t)
  {
  }   
}