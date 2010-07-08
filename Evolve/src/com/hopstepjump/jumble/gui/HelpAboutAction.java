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
  private static final String TANARC_URL = "http://www.intrinsarc.com";
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
    
    String evolve = "Evolve";
    
    tabs.add(
        makeTextPanel(
          preferred,
          "Description", 
          evolve + " is a highly principled, component-oriented approach to system construction and evolution. "
          + "It is heavily based on the PhD and research work of Andrew McVeigh and others.<br><br>" +
          "&copy; A. McVeigh 2003-2010<br><br>On license to <a href='" + TANARC_URL + "'>Intrinsarc Lrd</a>" +
          "<br><br>This product is the culmination of work started in 2003 to make naturally extensible applications."));

    tabs.add(
        makeTextPanel(
            preferred,
          "Libraries", 
          "eclipse UML2, licensed under EPL, (url)" +
//          "<br>Objectdb1.04 -> Commercial" +
          "<br>JTattoo, commercial license, (url)" +
          "<br>Xoetrope color wheel, licensed under XXX, (url)" +
          "<br>L2fProd common components, licensed under XXX, (url)" +
          "<br>FreeHep (for SVG, EPS, WMF), licensed under XXX, (url)" +
          "<br>JSyntaxPane Created by Claude Duguay, licensed under XXX, (url)" +
          "<br>FamFamFam Silk Icons url, licensed under XXX, (url)"));

    tabs.add(
        makeTextPanel(
            preferred,
          "Credits", 
          "This work is dedicated to our families for putting up with the long years of work and the late nights." +
          "<br><br>We would like to express our deep gratitude to Professors Jeff Magee and Jeff Kramer from Imperial College.  Without their pioneering work on " +
          "Darwin and hierarchical components, formal semantics of system flattening and isomorphic factories this work would not be possible" +
          "<br><br>We would also like to acknowledge the amazing work and technical vision of ObjecTime and Bran Selic.  Twenty years later the system is still impressive."));

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
