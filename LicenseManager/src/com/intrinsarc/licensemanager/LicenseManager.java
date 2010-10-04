package com.intrinsarc.licensemanager;

import java.awt.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import com.jtattoo.plaf.*;
import com.jtattoo.plaf.luna.*;

public class LicenseManager extends JFrame
{
  public static String LICENSE_KEY = "q35p-g5x9-8ftd-z1au";
  public static Font DEFAULT_FONT = new Font("Arial", 12, Font.PLAIN);

	public static void main(String args[]) throws Exception
	{
		changeLookAndFeel();
		
		LicenseManager mgr = new LicenseManager();
		mgr.setPreferredSize(new Dimension(500, 500));
		mgr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mgr.setVisible(true);
	}

	public LicenseManager()
	{
		super("Evolve license manager");
		setIconImage(loadIcon("lock.png").getImage());
		
		JMenuBar menuBar = new JMenuBar();
		JMenu keys = new JMenu("Keys");
		menuBar.add(keys);
		JMenuItem load = new JMenuItem("Load keyset");
		keys.add(load);
		keys.addSeparator();
		JMenuItem gen = new JMenuItem("Generate keyset");
		keys.add(gen);
		
		JMenu licenses = new JMenu("Licenses");
		menuBar.add(licenses);
		JMenuItem loadLicenses = new JMenuItem("Load licenses");
		licenses.add(loadLicenses);
		licenses.addSeparator();
		JMenuItem saveLicenses = new JMenuItem("Save licenses");
		licenses.add(saveLicenses);

		setJMenuBar(menuBar);
		
		JPanel panel = new JPanel(new BorderLayout());
		JTable table = new JTable();
		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll, BorderLayout.CENTER);
		add(panel);
		pack();
	}
	
	////////////////////////////////////////////////////////////////
	
	private static void changeLookAndFeel() throws Exception
	{
		UIManager.setLookAndFeel(LunaLookAndFeel.class.getName());
		// change the look and feel
		JFrame.setDefaultLookAndFeelDecorated(false);
		
    Properties props = new Properties();
    props.put("logoString", "");
    props.put("licenseKey", LICENSE_KEY);
   
    // change the fonts
    Object fs = fontString(DEFAULT_FONT, 0);
    props.put("controlTextFont", fs);
    props.put("labelTextFont", fs);
    props.put("systemTextFont", fs);
    props.put("userTextFont", fs);
    props.put("menuTextFont", fs);
    props.put("windowTitleFont", fontString(DEFAULT_FONT.deriveFont(Font.BOLD), 0));
    props.put("subTextFont", fontString(DEFAULT_FONT, 2));
    BaseTheme.setProperties(props);
	}
	
  private static Object fontString(Font font, int subtractSize)
  {
    return
      font.getName() + " " +
      (font.isBold() ? "bold " : "") + (font.isItalic() ? "italic " : "") + (font.getSize() - subtractSize);
  }

	public static final ImageIcon loadIcon(String iconName)
	{
		URL resource = ClassLoader.getSystemResource(iconName);
		if (resource == null)
			System.err.println("Cannot find image " + iconName);
		return new ImageIcon(resource);
	}
}
