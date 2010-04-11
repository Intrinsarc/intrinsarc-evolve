package com.hopstepjump.swing.palette;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.hopstepjump.idraw.foundation.*;

public class RichPalette implements IRichPalette
{
  private JPanel split;
  private java.util.List<IRichPaletteCategory> categories = new ArrayList<IRichPaletteCategory>();
  private RichPaletteEntry currentSelection;
  private List<JPanel> splitters = new ArrayList<JPanel>();

  public RichPalette()
  {
  }

  private void makeComponent()
  {
    split = new JPanel(null);
    
    // ask each section in turn
    int index = 0;
    boolean start = true;
    IRichPaletteCategory previous = null;
    for (IRichPaletteCategory category : categories)
    {
      if (!start)
      {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1, 1));
        split.add(panel);
        splitters.add(panel);
        final int splitterIndex = index - 1;
        
        // only add a dragger if the previous panel is not headerless
        if (!previous.isHeaderless())
        {
          panel.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
          panel.addMouseMotionListener(new MouseMotionListener()
          {
            public void mouseDragged(MouseEvent e)
            {
              if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0)
              {
                // if the splitter is dragged, then the category with the same index moves to RESIZED mode
                IRichPaletteCategory category = categories.get(splitterIndex);
                category.handleDrag(e.getY());
                workOutSizesAndAdjust();
              }
            }

						public void mouseMoved(MouseEvent e) {}
          });
        }
      }
      start = false;
      
      category.addComponent(this, split, index);
      previous = category;
      index++;
    }
    
    split.addComponentListener(new ComponentAdapter()
    {
      public void componentResized(ComponentEvent e)
      {
    		workOutSizesAndAdjust();
        split.revalidate();
      }
    });
    
    split.addComponentListener(new ComponentAdapter()
    {

      @Override
      public void componentShown(ComponentEvent e)
      {
        workOutSizesAndAdjust();
      }
    });
  }
  
  public JComponent getComponent()
  {
    if (split == null)
      makeComponent();
    return split;
  }

  public void addCategory(RichPaletteCategory category)
  {
    categories.add(category);
  }

  void entrySelected(RichPaletteEntry entry)
  {
    entry.select();
    if (currentSelection != null)
      currentSelection.deselect();
    currentSelection = entry;
  }
  
  /***
   * interface methods here
   */

  public void workOutSizesAndAdjust()
  {
  	if (split == null)
  		return;
    RichPaletteSizer sizer = new RichPaletteSizer(categories, splitters, split.getWidth(), split.getHeight());
    sizer.calculateSizes();
  }
  
  public void selectEntry(IRichPaletteEntry entry)
  {
    // ask all categories to handle
    for (IRichPaletteCategory category : categories)
      category.modallySelectEntry(entry);
  }

  public int getWidth()
  {
    return split.getWidth();
  }
  
  public void setHideMinimized(boolean hide)
  {
  	if (hide)
  	{
      // look at any categories which are minimized and tell them to be hidden also
      for (IRichPaletteCategory category : categories)
      {
        if (category.isMinimized())
          category.setHidden(true);
      }
  	}
  	else
  	{
      // look at any categories which are hidden, and re-show them
      for (IRichPaletteCategory category : categories)
      {
        if (category.isHidden())
          category.setHidden(false);
      }
  	}
    workOutSizesAndAdjust();
  }
  
  public JPopupMenu getPopupMenu()
  {
    JPopupMenu popup = new JPopupMenu();
    JMenuItem hide = new JMenuItem("Hide minimized");
    JMenuItem show = new JMenuItem("Show hidden");
    popup.add(hide);
    popup.add(show);
    
    hide.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
      	setHideMinimized(true);
      } 
    });
    
    show.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
      	setHideMinimized(false);
      } 
    });
    
    return popup;
  }
  
  public IRichPaletteEntry getEntryByHotkey(final char hotkey)
  {
    return (IRichPaletteEntry)
      visitEntries(new IRichPaletteEntryVisitor()
      {
        private boolean foundCurrent;
        
        public Object visitEntry(IRichPaletteEntry entry)
        {
          if (entry.isSelected())
            foundCurrent = true;
          else
          if (entry.getHotkey() == hotkey && foundCurrent)
            return entry;
          
          return null;
        } 
      });
  }
  
  public Object visitEntries(IRichPaletteEntryVisitor visitor)
  {
    for (IRichPaletteCategory category : categories)
    {
      Object ret = category.visitEntries(visitor);
      if (ret != null)
        return ret;
    }
    return null;
  }

  public void setEnabled(boolean enabled)
  {
    if (split.isEnabled() != enabled)
      setEnabledRecursively(split, enabled);
  }
  
  private void setEnabledRecursively(Container root, boolean enabled)
  {
    root.setEnabled(enabled);
    for (Component c : root.getComponents())
    {
      if (c instanceof Container)
        setEnabledRecursively((Container) c, enabled);
      else
        c.setEnabled(enabled);
    }
  }
  
  public boolean isEnabled()
  {
    return split.isEnabled();
  }

	public List<ToolClassification> getToolClassifications()
	{
		List<ToolClassification> tools = new ArrayList<ToolClassification>();
		for (IRichPaletteCategory cat : categories)
		{
			if (cat.getMode() != RichPaletteCategoryMode.MINIMIZED)
				tools.addAll(cat.getToolClassifications());
		}
		return tools;
	}
}
