package com.hopstepjump.swing.palette;

import static com.hopstepjump.swing.palette.RichPaletteCategoryMode.AUTOMATIC;
import static com.hopstepjump.swing.palette.RichPaletteCategoryMode.MAXIMIZED;
import static com.hopstepjump.swing.palette.RichPaletteCategoryMode.MINIMIZED;
import static com.hopstepjump.swing.palette.RichPaletteCategoryMode.RESIZED;

import java.util.*;

import javax.swing.*;

public class RichPaletteSizer
{
  private static final int DIVIDER_HEIGHT = 2;
  
  private List<IRichPaletteCategory> categories;
  private List<JPanel> splitters;
  private Map<IRichPaletteCategory, Integer> heights = new HashMap<IRichPaletteCategory, Integer>();
  private int width;
  private int height;

  public RichPaletteSizer(
      List<IRichPaletteCategory> categories,
      List<JPanel> splitters,
      int width,
      int height)
  {
    this.categories = getShownCategories(categories);
    this.splitters = splitters;
    this.width = width;
    this.height = height;
  }

  /**
   * work out the height of different modal elements
   * @param mode the mode to consider
   * @return
   */
  private int getHeight(List<IRichPaletteCategory> shown, RichPaletteCategoryMode mode, boolean minimumOnly)
  {
    int height = 0;
    for (IRichPaletteCategory category : shown)
    {
      if (category.getMode() == mode)
      {
        if (minimumOnly)
          height += category.getMinimumHeight();
        else
        {
          if (mode == RESIZED)
            height += category.getResizedHeight();
          if (mode == MAXIMIZED || mode == AUTOMATIC)
            height += category.getIdealHeight();
          if (mode == MINIMIZED)
            height += category.getTitleOnlyHeight();
        }
      }
    }
    return height;
  }
  
  private int count(List<IRichPaletteCategory> shown, RichPaletteCategoryMode mode)
  {
    int count = 0;
    for (IRichPaletteCategory category : shown)
    {
      if (category.getMode() == mode)
        count++;
    }
    return count;
  }
  
  /**
   * calculate the total minimum height, regardless of mode
   * @return
   */
  private int getTotalMinimumHeight(List<IRichPaletteCategory> shown)
  {
    int height = 0;
    for (IRichPaletteCategory category : shown)
      height += category.getMinimumHeight();
    return height;    
  }
  
  
  public void calculateSizes()
  {
    List<IRichPaletteCategory> shown = categories;

    int resizedHeight = getHeight(shown, RESIZED, false);
    int maxHeight = getHeight(shown, MAXIMIZED, false);
    int minHeight = getHeight(shown, MINIMIZED, false);
    int autoMinHeight = getHeight(shown, AUTOMATIC, true);
    int dividerHeight = (shown.size() - 1) * DIVIDER_HEIGHT;
    
    // should we turn maximums and resized into autos
    boolean autoMaxMode = false;
    if (autoMinHeight + resizedHeight + maxHeight + minHeight > height - dividerHeight)
      autoMaxMode = true;
    
    List<IRichPaletteCategory> left = getVariableCategories(autoMaxMode);
    List<IRichPaletteCategory> others = new ArrayList<IRichPaletteCategory>(shown);
    others.removeAll(left);
    int spaceLeft =
      height - minHeight - dividerHeight -
      (autoMaxMode ? autoMinHeight : maxHeight + resizedHeight);
    
    while (!left.isEmpty())
    {
      // divide up the space
      int divided = spaceLeft / left.size();
      boolean allocated = false;
      List<IRichPaletteCategory> toRemove = new ArrayList<IRichPaletteCategory>();
      for (IRichPaletteCategory category : left)
      {
        int ideal = category.getIdealHeight();
        
        // if this is resized, the ideal is the resized height
        if (category.getMode() == RESIZED)
          ideal = category.getResizedHeight();
        
        if (divided > ideal && spaceLeft - ideal >= getMinimumRemainingHeight(left, category))
        {
          heights.put(category, ideal);
          allocated = true;
          toRemove.add(category);
          spaceLeft -= ideal;
        }
      }
      left.removeAll(toRemove);
      
      // if no allocation, break out
      if (!allocated)
        break;
    }
    
    // set the height of all autos left
    if (!left.isEmpty())
    {
      // work out how many headerless there are
      int headerless = 0;
      for (IRichPaletteCategory category : left)
        if (category.isHeaderless())
          headerless++;

      int fullTitleHeight = left.get(0).getTitleFullHeight();
      int divided = (spaceLeft + headerless * fullTitleHeight)/ left.size();
      for (IRichPaletteCategory category : left)
        heights.put(category, Math.max(category.getMinimumHeight(), category.isHeaderless() ? divided - fullTitleHeight : divided));
    }

    // handle the others
    for (IRichPaletteCategory category : others)
    {
      switch (category.getMode())
      {
        case AUTOMATIC:
          // if the automatic is here, we are in autoMaxMode
          heights.put(category, category.getMinimumHeight());
          break;
        case RESIZED:
          // if the resized is here, we have room to show it
          heights.put(category, category.getResizedHeight());
          break;
        case MAXIMIZED:
          heights.put(category, category.getIdealHeight());
          break;
        case MINIMIZED:
          heights.put(category, category.getTitleOnlyHeight());
          break;
      }
    }
    
    // space out each in turn
    int start = 0;
    int index = 0;
    int size = shown.size();
    for (IRichPaletteCategory category : shown)
    {
      int height = heights.get(category);
      category.setBounds(0, start, width, height);
      start += height;
      
      if (index < size - 1)
      {
        // put the splitter in the right spot splitter
        splitters.get(index).setBounds(0, start, width, 2);
        splitters.get(index++).setVisible(true);
        start += DIVIDER_HEIGHT;
      }
    }
    
    // if there are any splitters left then make then invisible
    int realSize = categories.size();
    while (index < realSize - 1)
      splitters.get(index++).setVisible(false);
  }

  private List<IRichPaletteCategory> getShownCategories(List<IRichPaletteCategory> categories)
  {
    List<IRichPaletteCategory> shown = new ArrayList<IRichPaletteCategory>();
    for (IRichPaletteCategory category : categories)
      if (!category.isHidden())
        shown.add(category);
    return shown;
  }

  private int getMinimumRemainingHeight(List<IRichPaletteCategory> left, IRichPaletteCategory remove)
  {
    int height = 0;
    for (IRichPaletteCategory category : left)
      if (category != remove)
        height += category.getMinimumHeight();
    return height;
  }

  private List<IRichPaletteCategory> getVariableCategories(boolean autoMaxMode)
  {
    List<IRichPaletteCategory> vars = new ArrayList<IRichPaletteCategory>();
    for (IRichPaletteCategory category : categories)
    {
      RichPaletteCategoryMode mode = category.getMode();
      if (autoMaxMode && (mode == MAXIMIZED || mode == RESIZED) || !autoMaxMode && mode == AUTOMATIC)
        vars.add(category);        
    }
    return vars;
  }
}
