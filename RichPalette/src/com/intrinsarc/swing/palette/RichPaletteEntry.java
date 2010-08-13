package com.intrinsarc.swing.palette;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.intrinsarc.idraw.foundation.*;

public class RichPaletteEntry implements IRichPaletteEntry
{
  private ImageIcon icon;
  private String name;
  private IRichPaletteEntryListener listener;
  private JPanel panel;
  private boolean selected;
  private IRichPalette palette;
  private Border bevel =
    new CompoundBorder(
        new EmptyBorder(0, 0, 0, 1),
        new BevelBorder(BevelBorder.LOWERED, null, new Color(0,0,0,0), new Color(0,0,0,0), null));
  private Border empty = new EmptyBorder(2, 3, 2, 2);
  private Border current = empty;
	private String elements;
	private String hints;
	private boolean node;
  private char hotkey;
  private Object userObject;

  /**
   * use a delegating border -- if we switch the border of the panel using setBorder(), the viewport
   * mysteriously resets...  this is a workaround.
   */
  private Border delegator = new Border()
  {
    public Insets getBorderInsets(Component c)
    {
      return current.getBorderInsets(c);
    }

    public boolean isBorderOpaque()
    {
      return current.isBorderOpaque();
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
      current.paintBorder(c, g, x, y, width, height);
    }    
  };

  public RichPaletteEntry(IRichPalette palette, ImageIcon icon, String name, String elements, String hints, boolean node, Object userObject, IRichPaletteEntryListener listener)
  {
    this.icon = icon;
    this.name = extractName(name);
    this.hotkey = extractHotkey(name);
    this.listener = listener;
    this.palette = palette;
    this.elements = elements;
    this.hints = hints;
    this.node = node;
    this.userObject = userObject;
  }
  
  public RichPaletteEntry(IRichPalette palette, ImageIcon icon, String name, String elements, String hints, boolean node, Object userObject)
  {
    this(palette, icon, name, elements, hints, node, userObject, null);
  }
  
  private String extractName(String name)
  {
    return "<html>" + name.replaceAll("\\{", "<u>").replaceAll("\\}", "</u>");
  }

  private char extractHotkey(String name)
  {
    int index = name.indexOf("{");
    if (index == -1 || index + 1 == name.length())
      return (char) -1;
    return Character.toLowerCase(name.charAt(index + 1));
  }

  public JComponent makeComponent()
  {
    if (panel != null)
      return panel;
    
    panel = RichPaletteUtilities.makeLine(icon, name, null, false, false);
    panel.setOpaque(true);
    panel.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mousePressed(MouseEvent e)
      {
        if (panel.isEnabled())
        {
          if (e.getButton() == MouseEvent.BUTTON1)
            RichPaletteEntry.this.palette.selectEntry(RichPaletteEntry.this); 
            else
            if (e.getButton() == MouseEvent.BUTTON3)
              RichPaletteEntry.this.palette.getPopupMenu().show(panel, e.getX(), e.getY());
        }
      }
    });
    
    panel.setToolTipText(name);
    ToolTipManager.sharedInstance().setInitialDelay(1500);

    panel.setBorder(delegator);
    panel.setOpaque(true);
    return panel;
  }

  /****
   * interface methods here
   */
  
  public void deselect()
  {
    current = empty;
    panel.setBackground(null);
    selected = false;
    if (listener != null)
      listener.deselected();
  }

  public void select()
  {
    current = bevel;
    if (panel != null)
    	panel.setBackground(Color.WHITE);
    selected = true;
    if (listener != null)
      listener.selected();
  }

  public boolean isSelected()
  {
    return selected;
  }

  public void setListener(IRichPaletteEntryListener listener)
  {
    this.listener = listener;
  }

  public char getHotkey()
  {
    return hotkey;
  }

	public ToolClassification getToolClassification(String category)
	{
		return new ToolClassification(category, elements, hints, this, node);
	}

	public Icon getIcon()
	{
		return icon;
	}

	public String getName()
	{
		return name;
	}

	public Object getUserObject()
	{
		return userObject;
	}
}
