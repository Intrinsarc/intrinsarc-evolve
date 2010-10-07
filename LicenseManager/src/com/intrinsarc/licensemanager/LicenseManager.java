package com.intrinsarc.licensemanager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import org.freehep.swing.*;

import com.jtattoo.plaf.*;
import com.jtattoo.plaf.luna.*;

public class LicenseManager extends JFrame
{
  public static final String LICENSE_KEY = "q35p-g5x9-8ftd-z1au";
  public static final ImageIcon LOCK_ICON = loadIcon("lock.png");
  public static final ImageIcon FOLDER_ICON = loadIcon("folder.png");
  public static final ImageIcon KEY_ICON = loadIcon("bullet_key.png");
  public static final ImageIcon LICENSE_ICON = loadIcon("page_white.png");
  public static final ImageIcon UP_ICON = loadIcon("arrow_up.png");
  public static final Font DEFAULT_FONT = new Font("Arial", 12, Font.PLAIN);
  public static final String UP_TO_PARENT = "..";
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
		setIconImage(LOCK_ICON.getImage());
		
		JMenuBar menuBar = new JMenuBar();
		JMenu keys = new JMenu("Keys");
		menuBar.add(keys);
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
		
		JSplitPane split = new JSplitPane();
		add(split);
		
		JPanel left = new JPanel(new BorderLayout());
		split.setLeftComponent(left);
		JPanel right = new JPanel(new BorderLayout());
		split.setRightComponent(right);
		split.setPreferredSize(new Dimension(900, 500));
		split.setDividerLocation(400);
		
		addDirectoryBrowser(left, ".");
		pack();
	}
	
	////////////////////////////////////////////////////////////////
	
	private void addDirectoryBrowser(final JPanel panel, final String current)
	{
		File cfile = new File(current);

		panel.removeAll();
		final DefaultListModel model = new DefaultListModel();
		JList list = new JList(model);
		JPanel full = new JPanel(new BorderLayout());
		JLabel dir = new JLabel(cfile.getAbsolutePath());
		dir.setBorder(BorderFactory.createEtchedBorder());
		full.add(dir, BorderLayout.NORTH);
		full.add(list, BorderLayout.CENTER);
		panel.add(full, BorderLayout.CENTER);
		
		// add the current files
		File[] all = cfile.listFiles();
		model.addElement(UP_TO_PARENT);
		for (File file : all)
		{
			if (file.isDirectory())
				model.addElement(">" + file.getName());
		}
		for (File file : all)
		{
			if (!file.isDirectory())
				model.addElement(file.getName());
		}
		
		list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if (e.getValueIsAdjusting())
					return;
				String value = (String) model.get(e.getFirstIndex());
				if (value.startsWith(">"))
				{
					addDirectoryBrowser(panel, current + "/" + value.substring(1));
				}
			}
		});
		
		list.setCellRenderer(new DefaultListCellRenderer()
		{
			@Override
			public Component getListCellRendererComponent(
					JList list,
					Object value,
					int index,
					boolean isSelected,
					boolean cellHasFocus)
			{
				Icon icon = null;
				String str = (String) value;
				if (str.endsWith(".keypair"))
					icon = KEY_ICON;
				else
				if (str.endsWith(".license"))
					icon = LICENSE_ICON;
				else
				if (str.equals(UP_TO_PARENT))
				{
					icon = UP_ICON;
				}
				if (str.startsWith(">"))
				{
					str = str.substring(1);
					icon = FOLDER_ICON;
				}
				JLabel label = (JLabel) super.getListCellRendererComponent(list, str, index, isSelected, cellHasFocus);
				if (icon != null)
					label.setIcon(icon);
				return label;
			}
		});
	}
	
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
