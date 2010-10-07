package com.intrinsarc.licensemanager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.security.*;

import javax.swing.*;
import javax.swing.event.*;

import org.freehep.swing.*;

import com.intrinsarc.lbase.*;

public class LicenseManager extends JFrame
{
  public static final String ALGORITHM = "RSA";
  public static final int KEY_BITS = 1024;

  public static final String UP_TO_PARENT = "..";
  public static final String KEYPAIR_EXTENSION = ".keypair"; 
  public static final String LICENSE_EXTENSION = ".license"; 

  public static final ImageIcon LOCK_ICON = loadIcon("lock.png");
  public static final ImageIcon FOLDER_ICON = loadIcon("folder.png");
  public static final ImageIcon KEY_ICON = loadIcon("bullet_key.png");
  public static final ImageIcon LICENSE_ICON = loadIcon("page_white.png");
  public static final ImageIcon UP_ICON = loadIcon("arrow_up.png");
  private PrivateKey privateKey;
  private PublicKey publicKey;

	public static void main(String args[]) throws Exception
	{
		LnFChanger.changeLookAndFeel();
		
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
				keyGen = KeyPairGenerator.getInstance(ALGORITHM);
				keyGen.initialize(KEY_BITS);
				KeyPair pair = keyGen.generateKeyPair();
				
				// write out to a file
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choose the file to save the keypair to...");
		    ExtensionFileFilter filter = new ExtensionFileFilter();
		    filter.addExtension(KEYPAIR_EXTENSION.substring(1));
				chooser.setFileFilter(filter);
				chooser.showSaveDialog(parent);

				File file = chooser.getSelectedFile();
				if (!file.getName().endsWith(KEYPAIR_EXTENSION))
					file = new File(file.getCanonicalPath() + KEYPAIR_EXTENSION);
				if (file != null)
				{
					FileWriter out = new FileWriter(file);
					out.write(pair.getPrivate().getFormat() + "\n");
					out.write(HexUtils.toHex(pair.getPrivate().getEncoded()) + "\n");
					out.write(pair.getPublic().getFormat() + "\n");
					out.write(HexUtils.toHex(pair.getPublic().getEncoded()) + "\n");
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
	
	public LicenseManager() throws Exception
	{
		super("Evolve license manager");
		setIconImage(LOCK_ICON.getImage());
		
		JMenuBar menuBar = new JMenuBar();
		JMenu keys = new JMenu("Keys");
		menuBar.add(keys);
		JMenuItem gen = new JMenuItem(new GenerateKeypairAction(this));
		keys.add(gen);
		
		setJMenuBar(menuBar);
		
		JSplitPane split = new JSplitPane();
		add(split);
		
		JPanel left = new JPanel(new BorderLayout());
		split.setLeftComponent(left);
		JPanel right = new JPanel(new BorderLayout());
		split.setRightComponent(right);
		split.setPreferredSize(new Dimension(900, 500));
		split.setDividerLocation(400);
		
		addDirectoryBrowser(left, right, new File(".").getCanonicalFile());
		pack();
	}
	
	////////////////////////////////////////////////////////////////
	
	private void addDirectoryBrowser(final JPanel panel, final JPanel right, final File current)
	{
		panel.removeAll();
		final DefaultListModel model = new DefaultListModel();
		JList list = new JList(model);
		JPanel full = new JPanel(new BorderLayout());
		JLabel dir = new JLabel(current.getAbsolutePath());
		dir.setBorder(BorderFactory.createEtchedBorder());
		full.add(dir, BorderLayout.NORTH);
		full.add(list, BorderLayout.CENTER);
		panel.add(full, BorderLayout.CENTER);
		
		// add the current files
		File[] all = current.listFiles();
		if (current.getParentFile() != null)
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
					addDirectoryBrowser(panel, right, new File(current, value.substring(1)));
				}
				else
				if (value.equals(UP_TO_PARENT))
				{
					addDirectoryBrowser(panel, right, current.getParentFile());					
				}
				else
				if (value.endsWith(KEYPAIR_EXTENSION))
				{
					showKeyPair(right, value);
				}
				else
				if (value.endsWith(LICENSE_EXTENSION))
				{
					showLicense(right, value);
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
				if (str.endsWith(KEYPAIR_EXTENSION))
					icon = KEY_ICON;
				else
				if (str.endsWith(LICENSE_EXTENSION))
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
	

	private void showLicense(JPanel right, String value)
	{
		// TODO Auto-generated method stub
		
	}

	private void showKeyPair(JPanel right, String value)
	{
		// TODO Auto-generated method stub
		
	}

	public static final ImageIcon loadIcon(String iconName)
	{
		URL resource = ClassLoader.getSystemResource(iconName);
		if (resource == null)
			System.err.println("Cannot find image " + iconName);
		return new ImageIcon(resource);
	}
}
