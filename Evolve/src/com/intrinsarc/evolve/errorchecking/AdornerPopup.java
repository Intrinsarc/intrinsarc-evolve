package com.intrinsarc.evolve.errorchecking;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.event.*;
import edu.umd.cs.jazz.util.*;

public abstract class AdornerPopup implements ZMouseListener, ZMouseMotionListener
{
  private ZTransformGroup popup;
  private int height;
  private ToolCoordinatorFacet coordinator;
  
  public AdornerPopup(ToolCoordinatorFacet coordinator)
  {
    this.coordinator = coordinator;
  }
  
  public abstract void addText(JPanel panel);
  
  public void mouseEntered(ZMouseEvent e)
  {
    showPopup(e);
  }

  public void mouseExited(ZMouseEvent e)
  {
    hidePopup();
  }

  public void mouseMoved(ZMouseEvent e)
  {
    if (popup != null)
      positionPopup(e);
  } 

  private void showPopup(ZMouseEvent e)
  {
    // format the error line
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    addText(panel);
    if (panel.getComponentCount() == 0)
      return;
    ZCanvas canvas = coordinator.getCurrentDiagramView().getCanvas();
    
    panel.setBorder(new CompoundBorder(BorderFactory.createEtchedBorder(), new EmptyBorder(0, 0, 2, 2)));
    panel.setBackground(Color.WHITE);
    ZSwing swing = new ZSwing(canvas, panel);
    ZFadeGroup fade = new ZFadeGroup(new ZVisualLeaf(swing));
    fade.setAlpha(0.85);
    popup = new ZTransformGroup(fade);
    UDimension size = new UDimension(panel.getPreferredSize());
    height = size.getIntHeight();
    positionPopup(e);
    
    canvas.getLayer().addChild(popup);
  }

  private void positionPopup(ZMouseEvent e)
  {
  	ZCanvas canvas = coordinator.getCurrentDiagramView().getCanvas();
  	double canvasWidth = canvas.getWidth();
  	double width = popup.getBounds().getWidth();
    UPoint pt = new UPoint(new UPoint(e.getPoint()).subtract(new UDimension(-8, height + 8)));
    
    // if the popup is too high, make lower
    if (pt.getY() < 0)
    	pt = new UPoint(pt.getX(), 0);
    if (pt.getX() + width > canvasWidth)
    	pt = new UPoint(pt.getX() - width - 8, pt.getY());
    
    popup.setTranslation(pt.getX(), pt.getY());
  }

  private void hidePopup()
  {
    if (popup != null)
      coordinator.getCurrentDiagramView().getCanvas().getLayer().removeChild(popup);
    popup = null;
  }

  public void mousePressed(ZMouseEvent e) {}
  public void mouseReleased(ZMouseEvent e) {}
  public void mouseDragged(ZMouseEvent e) {}
  public void mouseClicked(ZMouseEvent e) {}
};
