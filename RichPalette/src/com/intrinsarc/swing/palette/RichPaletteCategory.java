package com.intrinsarc.swing.palette;

import static com.intrinsarc.swing.palette.RichPaletteCategoryMode.*;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.Timer;

import com.intrinsarc.idraw.foundation.*;

public class RichPaletteCategory implements IRichPaletteCategory
{
  /** the parent palette */
  private IRichPalette palette;
  private Icon icon;
  private String name;
  private RichPaletteCategoryMode mode = AUTOMATIC;
  private static final ImageIcon UP = loadIcon("up.png");
  private static final ImageIcon DOWN = loadIcon("down.png");
  private static final ImageIcon UP_DISABLED = loadIcon("up-disabled.png");
  private static final ImageIcon DOWN_DISABLED = loadIcon("down-disabled.png");
  private static final Icon ICON_AUTOMATIC = null;
  private static final Icon ICON_MINIMIZED = loadIcon("palette-minimized.png");
  private static final Icon ICON_MAXIMIZED = loadIcon("palette-maximized.png");
  private static final Icon ICON_RESIZED = loadIcon("palette-pin.png");
  private List<IRichPaletteEntry> entries = new ArrayList<IRichPaletteEntry>();
  private List<IRichPaletteEntry> all = new ArrayList<IRichPaletteEntry>();
  private JPanel panel;
  private RobustJViewport toolsView;
  private JLabel modeLabel;
  private int topIndex = 0;
  private int titleFullHeight;
  private int entryFullHeight;
  private boolean topScroller;
  private boolean bottomScroller;
  private JButton topScrollButton;
  private JButton bottomScrollButton;
  private JLayeredPane layer;
  private JPanel north;
  private int resizedHeight;
  private boolean headerless;
  private boolean hidden;
  private String[] focusTags;

  public RichPaletteCategory(String name, String[] focusTags)
  {
    this.name = name;
    this.focusTags = focusTags;
  }
  
  public RichPaletteCategory(Icon icon, String name, String[] focusTags)
  {
    this.icon = icon;
    this.name = name;
    this.focusTags = focusTags;
  }
  
  public RichPaletteCategory(Icon icon, String name, boolean headerless, String[] focusTags)
  {
    this.icon = icon;
    this.name = name;
    this.headerless = headerless;
    mode = MAXIMIZED;
    this.focusTags = focusTags;
  }
  
  public RichPaletteCategory(Icon icon, String name, RichPaletteCategoryMode mode, String[] focusTags)
  {
    this.icon = icon;
    this.name = name;
    this.mode = mode;
    this.focusTags = focusTags;
  }
  
  public void addEntry(IRichPaletteEntry entry)
  {
  	addEntry(entry, true);
  }
  
  public void addEntry(IRichPaletteEntry entry, boolean show)
  {
  	if (show)
  		entries.add(entry);
  	all.add(entry);
  }
  
  public void addComponent(final IRichPalette palette, Container split, final int index)
  {
    this.palette = palette;
    
    panel = new JPanel(new BorderLayout());
    modeLabel = new JLabel();
    
    // get the icon for the mode
    switchMode(mode);
    
    final JComponent title = RichPaletteUtilities.makeLine(icon, name, modeLabel, true, true);
    titleFullHeight = title.getPreferredSize().height;

    if (!headerless)
    {
      panel.add(title, BorderLayout.NORTH);
      title.addMouseListener(new MouseAdapter()
      {
        @Override
        public void mouseClicked(MouseEvent e)
        {
          if (e.getButton() == MouseEvent.BUTTON1)
          {
            // switch mode and tell the palette
            switch (mode)
            {
              case AUTOMATIC:
                switchMode(MAXIMIZED);
                break;
              case MAXIMIZED:
                switchMode(MINIMIZED);
                break;
              case MINIMIZED:
              default:
                switchMode(AUTOMATIC);
                break;
            }
            palette.workOutSizesAndAdjust();
          }
          else
          if (e.getButton() == MouseEvent.BUTTON3)
            palette.getPopupMenu().show(title, e.getX(), e.getY());
        }
      });
    }
    
    layer = new JLayeredPane();
    north = new JPanel(new BorderLayout());
    panel.add(layer, BorderLayout.CENTER);
    layer.add(north, new Integer(0));

    topScrollButton = createScrollButton(new JButton(UP), true);
    layer.add(topScrollButton, new Integer(1));
    
    bottomScrollButton = createScrollButton(new JButton(DOWN), false);
    layer.add(bottomScrollButton, new Integer(2));
    
    JPanel pl = new JPanel();
    pl.setLayout(new BoxLayout(pl, BoxLayout.Y_AXIS));
    toolsView = new RobustJViewport();
    toolsView.setIgnoreResizings(false);
    toolsView.setView(pl);
    toolsView.setIgnoreResizings(true);
    toolsView.setOpaque(false);
    for (IRichPaletteEntry entry : entries)
    {
      JComponent entryComponent = entry.makeComponent();
      pl.add(entryComponent);
      if (entryFullHeight == 0)
        entryFullHeight = entryComponent.getPreferredSize().height;
    }

    pl.setOpaque(false);
    north.add(toolsView, BorderLayout.NORTH);
    
    updateScrollers();

    split.add(panel);
  }
  
  private JButton createScrollButton(final JButton scrollButton, final boolean top)
  {
    scrollButton.setContentAreaFilled(false);
    scrollButton.setBorderPainted(false);
    scrollButton.setFocusable(false);
    scrollButton.addMouseListener(new MouseAdapter()
    {
      private Timer timer;
      
      @Override
      public void mousePressed(MouseEvent e)
      {
        timer = new Timer(100, new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            topIndex += top ? -1 : 1;
            repositionTop();
          } 
        });
        timer.setRepeats(true);
        timer.start();
      }

      @Override
      public void mouseReleased(MouseEvent e)
      {
        timer.stop();
        repositionTop();
      }

      @Override
      public void mouseClicked(MouseEvent e)
      {
        topIndex += top ? -1 : 1;
        repositionTop();
      }
    });
    
    return scrollButton;
  }

  private void repositionTop()
  {
    // see how many we can fit into the size...
    int height = panel.getHeight();
    int numEntries = entries.size();
    
    double canFit = numEntries == 0 ? 0 :
      (height - getTitleOnlyHeight()) / (double) entryFullHeight;
    if (topIndex + canFit > numEntries)
      topIndex = numEntries - (int) canFit;
    topIndex = Math.max(topIndex, 0);
    
    // position the entries
    toolsView.setIgnoreResizings(false);
    toolsView.setViewPosition(new Point(0, topIndex * entryFullHeight));
    toolsView.setIgnoreResizings(true);
    
    // do we need scrollbars?
    topScroller = topIndex > 0;
    bottomScroller = topIndex + canFit < numEntries && numEntries > 1;    
    updateScrollers();
  }
  

  private void updateScrollers()
  {
    updateScroller(topScrollButton, UP, UP_DISABLED, topScroller);
    updateScroller(bottomScrollButton, DOWN, DOWN_DISABLED, bottomScroller);
  }
  
  private void updateScroller(JButton scroller, Icon main, Icon disabled, boolean visible)
  {
    scroller.setVisible(topScroller || bottomScroller);
    scroller.setIcon(visible ? main : disabled);
  }

  private void switchMode(RichPaletteCategoryMode newMode)
  {
    mode = newMode;
    modeLabel.setIcon(getModeIcon());
  }      
  
  private Icon getModeIcon()
  {
    switch (mode)
    {
      case AUTOMATIC:
        return ICON_AUTOMATIC;
      case MINIMIZED:
        return ICON_MINIMIZED;
      case RESIZED:
        return ICON_RESIZED;
      case MAXIMIZED:
        return ICON_MAXIMIZED;
    }
    return null;
  }
  
  /***
   * interface methods here
   */

  public void modallySelectEntry(IRichPaletteEntry entry)
  {
    // iterate over each entry, either selecting or deselecting
    for (IRichPaletteEntry contained : entries)
      if (contained == entry)
        contained.select();
      else
      if (contained.isSelected())
        contained.deselect();
  }

  public int getResizedHeight()
  {
    return resizedHeight;
  }
  
  public int getIdealHeight()
  {
    return getTitleOnlyHeight() + entryFullHeight * entries.size();
  }

  public int getMinimumHeight()
  {
    return getTitleOnlyHeight() + entryFullHeight;
  }
  
  public int getTitleOnlyHeight()
  {
    return isHeaderless() ? 0 : titleFullHeight;
  }

  public RichPaletteCategoryMode getMode()
  {
    return mode;
  }

  public void modallySelectFirstEntry()
  {
    if (entries.get(0) != null)
      palette.selectEntry(entries.get(0));
  }
  
  public void handleDrag(int yDelta)
  {
    if (mode != RESIZED)
    {
      switchMode(RESIZED);
      resizedHeight = panel.getHeight();
    }
    resizedHeight += yDelta;
    resizedHeight = Math.max(resizedHeight, getTitleOnlyHeight() + entryFullHeight);
    resizedHeight = Math.min(resizedHeight, getIdealHeight());
  }
  
  public void setBounds(int x, int y, int width, int height)
  {
    panel.setBounds(x, y, width, height);
    
    // set the subcomponents also
    int titleOnly = getTitleOnlyHeight();
    topScrollButton.setBounds(width - 15, 2, 8, 6);
    bottomScrollButton.setBounds(width - 15, height - titleOnly - 10, 8, 6);
    north.setBounds(0, 0, width, height - titleOnly);
    layer.setBounds(0, titleOnly, width, height - titleOnly);
    
    repositionTop();
  }

  public boolean isHeaderless()
  {
    return headerless;
  }

  public int getTitleFullHeight()
  {
    return titleFullHeight;
  }
  
  public static final ImageIcon loadIcon(String iconName)
  {
    URL resource = ClassLoader.getSystemResource(iconName);
    if (resource == null)
      System.err.println("Cannot find image " + iconName);
    return new ImageIcon(resource);
  }

  public boolean isHidden()
  {
    return hidden;
  }

  public void setHidden(boolean hidden)
  {
    this.hidden = hidden;
    if (panel != null)
    	panel.setVisible(!hidden);
  }

  public boolean isMinimized()
  {
    return mode == MINIMIZED;
  }

  public Object visitEntries(IRichPaletteEntryVisitor visitor)
  {
    for (IRichPaletteEntry entry : entries)
    {
      Object ret = visitor.visitEntry(entry);
      if (ret != null)
        return ret;
    }
    return null;
  }

	public List<ToolClassification> getToolClassifications()
	{
		List<ToolClassification> tools = new ArrayList<ToolClassification>();
		for (IRichPaletteEntry entry : all)
			tools.add(entry.getToolClassification(name));
		return tools;
	}

	public String[] getFocusTags()
	{
		return focusTags;
	}
}
