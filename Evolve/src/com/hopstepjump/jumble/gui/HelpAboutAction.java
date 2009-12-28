package com.hopstepjump.jumble.gui;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.HyperlinkEvent.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.swing.*;

public class HelpAboutAction extends AbstractAction
{
  private static final String TANARC_URL = "http://www.tanarc.com";
	public static final ImageIcon WORLD_ICON = IconLoader.loadIcon("world.png");
  private ToolCoordinatorFacet coordinator;
  private PopupMakerFacet popup;
  
  public HelpAboutAction(ToolCoordinatorFacet coordinator, PopupMakerFacet popup)
  {
    super("About");
    this.coordinator = coordinator;
    this.popup = popup;
  }

  public void actionPerformed(ActionEvent e)
  {
    JTabbedPane tabs = new JTabbedPane();
    
    JPanel logo = getLogoPanel();
    tabs.add(logo);
    Dimension preferred = logo.getPreferredSize();
    
//    String red = "<span style=\"color: red\">";
//    String redOff = "</span>";
    String evolve = "Evolve";
    
    tabs.add(
        makeTextPanel(
          preferred,
          "Description", 
          evolve + " is programmed by Andrew McVeigh.<br>" +
          "&copy; A. McVeigh 2003-2009<br><br><a href='" + TANARC_URL + "'>Tangible Architecture</a><br><br>" +
          evolve + " is an environment for modeling, maintaining and extending systems built from components.<br><br>" +
          "The work descends from research I started in 2002/2003" + 
          " to make naturally extensible applications"));

    tabs.add(
        makeTextPanel(
            preferred,
          "Libraries", 
          "eclipse UML2 -> EPL<br>Objectdb1.04 -> Commercial<br>JTattoo -> Commercial<br>Xoetrope color wheel -> free" +
          "<br>L2fprod common components -> free<br>FreeHep (for SVG, EPS, WMF) -> free<br>JDO 1.01 -> free spec" +
          "<br>  JICon, Created by Claude Duguay, Copyright (c) 2002"));

    tabs.add(
        makeTextPanel(
            preferred,
          "Icons and Images", 
          "FamFam icons -> free"));

    tabs.add(
        makeTextPanel(
            preferred,
          "Credits", 
          "family, esp Tess<br><br>Professors<br>"));

    tabs.add(
        makeTextPanel(
            preferred,
          "Legal", 
          "Commercial license text"));

    coordinator.invokeAsDialog(null, "About", tabs, null, null);
  }
  
  private JComponent makeTextPanel(Dimension preferred, String name, String contents)
  {
    final JTextPane text = new JTextPane();
    text.setContentType("text/html");

    text.setText(contents);
    text.setEditable(false);
    
    JScrollPane scroller = new JScrollPane(text);
    scroller.setName(name);
    scroller.setPreferredSize(preferred);
    
    text.addHyperlinkListener(new HyperlinkListener()
    {
      public void hyperlinkUpdate(HyperlinkEvent e)
      {
        if (e.getEventType() == EventType.ACTIVATED)
          openBrowser(e.getURL());
      }
    });

    return scroller;
  }

  private JPanel getLogoPanel()
  {
    ImageIcon icon = IconLoader.loadIcon("evolve.png");
    final Image image = icon.getImage();
    final UDimension imageSize = new UDimension(image.getWidth(null), image.getHeight(null));
    

    JPanel panel = new JPanel()
    {

      @Override
      public void paint(Graphics g)
      {
        super.paintComponent(g);
        UDimension desktopSize = new UDimension(getSize());
        UDimension offset = desktopSize.subtract(imageSize).multiply(0.5);
        g.setColor(Color.white);
        g.fillRect(0, 0, desktopSize.getIntWidth(), desktopSize.getIntHeight());
        g.drawImage(image, offset.getIntWidth(), offset.getIntHeight(), null);
        g.drawRect(0, 0, desktopSize.getIntWidth() - 1, desktopSize.getIntHeight() - 1);
      }
    };
    panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    panel.setPreferredSize(new Dimension(600, 400));
    panel.setName("Logo");
    
    panel.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseClicked(MouseEvent e)
      {
        try
        {
          openBrowser(new URL(TANARC_URL));
        }
        catch (MalformedURLException ex)
        {
          ex.printStackTrace();
        }
      }
      
    });
    return panel;
  }

  private void openBrowser(URL url)
  {
    String error = BrowserInvoker.openBrowser(url);
    if (error != null)
      popup.displayPopup(
          WORLD_ICON,
          "HTML Browser problem...",
          error,
          ScreenProperties.getUndoPopupColor(),
          Color.black,
          3000);

  } 
}
