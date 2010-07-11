package com.hopstepjump.jumble.gui;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.HyperlinkEvent.EventType;

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
    
    tabs.add(
    		makeTextPanelFromHTMLDocument(
          preferred,
          "Description", 
          "help-documents/evolve-description.html"));

    tabs.add(
    		makeTextPanelFromHTMLDocument(
          preferred,
          "Libraries", 
          "help-documents/evolve-libraries.html"));
    
    tabs.add(
    		makeTextPanelFromHTMLDocument(
          preferred,
          "Credits", 
          "help-documents/evolve-credits.html"));
    
    tabs.add(
    		makeTextPanelFromHTMLDocument(
          preferred,
          "License", 
          "help-documents/evolve-license.html"));

    coordinator.invokeAsDialog(null, "About", tabs, null, null);
  }
  
  private JComponent makeTextPanelFromHTMLDocument(Dimension preferred, String name, String documentName)
  {
  	return makeTextPanel(preferred, name, FileLoader.loadFile(documentName));
  }
  
  private JComponent makeTextPanel(Dimension preferred, String name, String contents)
  {
    final JTextPane text = new JTextPane();
    text.setContentType("text/html");

    text.setText(contents);
    text.setEditable(false);
    text.moveCaretPosition(0);
    
    final JScrollPane scroller = new JScrollPane(text);
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
    panel.setName("Evolve");
    
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
