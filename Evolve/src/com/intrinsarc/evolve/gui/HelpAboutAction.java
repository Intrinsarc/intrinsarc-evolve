package com.intrinsarc.evolve.gui;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.event.HyperlinkEvent.EventType;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.swing.*;

import de.huxhorn.sulky.memorystatus.*;

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
          "License", 
          "help-documents/evolve-license.html"));
    
    tabs.add(
    		makeTextPanelFromHTMLDocument(
          preferred,
          "Libraries", 
          "help-documents/evolve-libraries.html"));
    
    tabs.add(makeSystemStatusPanel());

    coordinator.invokeAsDialog(null, "About", tabs, null, 0, null);
  }
  
  private Component makeSystemStatusPanel()
	{
  	JPanel panel = new JPanel(new BorderLayout());
  	JPanel mem = new JPanel(new BorderLayout());
  	mem.setBackground(Color.WHITE);
  	CompoundBorder compound = new CompoundBorder(new EmptyBorder(10, 10, 10, 10), BorderFactory.createEtchedBorder());
  	mem.setBorder(compound);
  	mem.add(new MemoryStatus(), BorderLayout.SOUTH);
  	panel.add(mem, BorderLayout.NORTH);
  	
  	Properties props = System.getProperties();
  	
  	String data[][] = new String[props.size()][2];
  	int lp = 0;
  	for (Enumeration obj = props.propertyNames(); obj.hasMoreElements();)
  	{
  		String name = (String) obj.nextElement();
  		data[lp][0] = name;
  		data[lp++][1] = props.getProperty(name);
  	}
  		
  	JTable table = new JTable(data, new String[] {"Name", "Property"});
  	table.setShowVerticalLines(false);
  	table.setShowHorizontalLines(false);
  	JPanel tpanel = new JPanel(new BorderLayout());
  	tpanel.add(table, BorderLayout.CENTER);
  	tpanel.setBackground(Color.WHITE);
  	tpanel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), BorderFactory.createLineBorder(Color.LIGHT_GRAY)));
  	panel.add(tpanel, BorderLayout.CENTER);
  	
  	JScrollPane scroll = new JScrollPane(panel);
  	scroll.setName("System details");  
  	scroll.setPreferredSize(new Dimension(100, 100));
  	return scroll;
	}

	private JComponent makeTextPanelFromHTMLDocument(Dimension preferred, String name, String documentName)
  {
  	return makeTextPanel(preferred, name, FileUtilities.loadFileContentsFromResource(documentName));
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
    panel.setPreferredSize(new Dimension(icon.getIconWidth()-1, icon.getIconHeight()));
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
