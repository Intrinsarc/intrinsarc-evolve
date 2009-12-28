package org.freehep.swing.popup;

import java.awt.event.*;

import javax.swing.*;

/**
 * A simple helper class for popping up popup menus. This should be added as a
 * mouse listener to the component to which the popup menu should be associated.
 * 
 * @author tonyj
 */
public class PopupListener extends MouseAdapter
{
  private JPopupMenu popup;

  public PopupListener(JPopupMenu popup)
  {
    this.popup = popup;
  }
  public void mousePressed(MouseEvent e)
  {
    maybeShowPopup(e);
  }
  public void mouseReleased(MouseEvent e)
  {
    maybeShowPopup(e);
  }
  protected void maybeShowPopup(MouseEvent e)
  {
    if (popup.isPopupTrigger(e))
    {
      popup.show(e.getComponent(), e.getX(), e.getY());
    }
  }
}
