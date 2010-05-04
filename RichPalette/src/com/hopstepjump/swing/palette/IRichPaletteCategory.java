package com.hopstepjump.swing.palette;

import java.awt.*;
import java.util.List;

import com.hopstepjump.idraw.foundation.*;

public interface IRichPaletteCategory
{
  public void addComponent(IRichPalette richPalette, Container container, int index);
  public void modallySelectEntry(IRichPaletteEntry entry);
  public RichPaletteCategoryMode getMode();
  public int getIdealHeight();
  public int getResizedHeight();
  public int getTitleOnlyHeight();
  public int getMinimumHeight();
  public void modallySelectFirstEntry();
  public void setBounds(int x, int y, int width, int height);
  public void handleDrag(int yDelta);
  public boolean isHeaderless();
  public int getTitleFullHeight();
  public void setHidden(boolean hidden);
  public boolean isHidden();
  public boolean isMinimized();
  public Object visitEntries(IRichPaletteEntryVisitor visitor);
	public List<ToolClassification> getToolClassifications();
	public String[] getFocusTags();
}
