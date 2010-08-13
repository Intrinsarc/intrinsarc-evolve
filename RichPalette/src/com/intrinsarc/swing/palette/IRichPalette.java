package com.intrinsarc.swing.palette;

import javax.swing.*;

public interface IRichPalette
{
  public void selectEntry(IRichPaletteEntry entry);
  public void workOutSizesAndAdjust();
  public int getWidth();
  public JPopupMenu getPopupMenu();
}
