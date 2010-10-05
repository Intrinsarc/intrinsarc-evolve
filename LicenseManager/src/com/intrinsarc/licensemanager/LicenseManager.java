package com.intrinsarc.licensemanager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;

import javax.swing.*;

import org.freehep.swing.*;

import com.jtattoo.plaf.*;
import com.jtattoo.plaf.luna.*;

public class LicenseManager extends JFrame
{
  public static final String LICENSE_KEY = "q35p-g5x9-8ftd-z1au";
  public static final Font DEFAULT_FONT = new Font("Arial", 12, Font.PLAIN);
  public static final String ALGORITHM = "RSA";
  public static final int KEY_BITS = 1024;
  private PrivateKey privateKey;
  private PublicKey publicKey;

	public static void main(String args[]) throws Exception
	{
		changeLookAndFeel();
		
		LicenseManager mgr = new LicenseManager();
		mgr.setPreferredSize(new Dimension(500, 500));
		mgr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mgr.setVisible(true);
	}

	private class LoadKeyPairAction extends AbstractAction
	{
		JFrame parent;
		
		public LoadKeyPairAction(JFrame parent)
		{
			super("Load keypair");
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Select the keypair file to open...");
	    ExtensionFileFilter filter = new ExtensionFileFilter();
	    filter.addExtension("keypair");
			chooser.setFileFilter(filter);
			chooser.showOpenDialog(parent);

			File file = chooser.getSelectedFile();
			if (file != null)
			{
//				FileReader reader = new FileReader(file);
			}
		}
	}
	
	private class GenerateKeypairAction extends AbstractAction
	{
		JFrame parent;
		
		public GenerateKeypairAction(JFrame parent)
		{
			super("Generate keypair");
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			KeyPairGenerator keyGen;
			try
			{
				keyGen = KeyPairGenerator.getInstance("RSA");
				keyGen.initialize(1024);
				KeyPair pair = keyGen.generateKeyPair();
				
				// write out to a file
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choose the file to save the keypair to...");
		    ExtensionFileFilter filter = new ExtensionFileFilter();
		    filter.addExtension("keypair");
				chooser.setFileFilter(filter);
				chooser.showSaveDialog(parent);

				File file = chooser.getSelectedFile();
				if (!file.getName().endsWith(".keypair"))
					file = new File(file.getCanonicalPath() + ".keypair");
				if (file != null)
				{
					FileWriter out = new FileWriter(file);
					out.write(pair.getPrivate().getFormat() + "\n");
					out.write(toHex(pair.getPrivate().getEncoded()) + "\n");
					out.write(pair.getPublic().getFormat() + "\n");
					out.write(toHex(pair.getPublic().getEncoded()) + "\n");
					out.close();
					JOptionPane.showMessageDialog(parent, "Keypair written successfully to " + file);
				}
			}
			catch (NoSuchAlgorithmException e1)
			{
				JOptionPane.showMessageDialog(parent, "Cannot find key gen algorithm: " + ALGORITHM, null, JOptionPane.ERROR_MESSAGE);
			}
			catch (IOException ex)
			{
				JOptionPane.showMessageDialog(parent, "Cannot write to selected file", null, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public LicenseManager()
	{
		super("Evolve license manager");
		setIconImage(loadIcon("lock.png").getImage());
		
		JMenuBar menuBar = new JMenuBar();
		JMenu keys = new JMenu("Keys");
		menuBar.add(keys);
		JMenuItem load = new JMenuItem(new LoadKeyPairAction(this));
		keys.add(load);
		keys.addSeparator();
		JMenuItem gen = new JMenuItem(new GenerateKeypairAction(this));
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
		JDialog.setDefaultLookAndFeelDecorated(false);
		
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
	
	public static String toHex(byte[] bytes)
	{
  	String hex = "";
  	for (byte b : bytes)
  		hex += toHex(b);
  	return hex;
	}
	
	private static final String[] HEX_CHARS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
	private static String toHex(byte b)
	{
		if (b < 0)
			b += 256;
		return HEX_CHARS[(b&255)/16] + HEX_CHARS[b&15];
	}
}
