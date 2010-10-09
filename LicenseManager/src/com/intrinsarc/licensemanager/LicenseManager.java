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
  public static final ImageIcon KEY_ICON = loadIcon("key.png");
  public static final ImageIcon NO_KEY_ICON = loadIcon("key_delete.png");
  public static final ImageIcon LICENSE_ICON = loadIcon("page_white.png");
  public static final ImageIcon UP_ICON = loadIcon("arrow_up.png");
  public static final ImageIcon RED_ICON = loadIcon("bullet_red.png");
  public static final ImageIcon GREEN_ICON = loadIcon("bullet_green.png");
  private File current;
  private JPanel left;
  private JPanel right;
  private String[] keyMessage = {null};
  private PrivateKey privateKey[] = new PrivateKey[]{null};
  private PublicKey publicKey[] = new PublicKey[]{null};

	public static void main(String args[]) throws Exception
	{
		LnFChanger.changeLookAndFeel();
		
		LicenseManager mgr = new LicenseManager();
		mgr.setPreferredSize(new Dimension(500, 500));
		mgr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mgr.setVisible(true);
	}

	public LicenseManager() throws Exception
	{
		super("Evolve license manager");
		setIconImage(LOCK_ICON.getImage());
		
		JSplitPane split = new JSplitPane();
		add(split);
		
		left = new JPanel(new BorderLayout());
		split.setLeftComponent(left);
		right = new JPanel(new BorderLayout());
		split.setRightComponent(right);
		split.setPreferredSize(new Dimension(900, 500));
		split.setDividerLocation(400);
		
		current = new File(".").getCanonicalFile();
		addDirectoryBrowser();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu keys = new JMenu("Keys");
		menuBar.add(keys);
		JMenuItem gen = new JMenuItem(new GenerateKeypairAction(this));
		keys.add(gen);
		
		JMenu license = new JMenu("License");
		menuBar.add(license);
		JMenuItem lic = new JMenuItem(new GenerateNewLicenseAction(this));
		license.add(lic);
		
		setJMenuBar(menuBar);
		pack();
	}

	private class GenerateNewLicenseAction extends AbstractAction
	{
		private JFrame parent;
		
		public GenerateNewLicenseAction(JFrame parent)
		{
			super("Generate new license");
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (privateKey[0] == null)
			{
				JOptionPane.showMessageDialog(parent, "Load a keypair first!", "No keypair loaded", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// populate an LDetails
			LDetails lic = new LDetails("user=\nemail=\nnumber=1\nexpiry-days=31\nencmacs=\ndecmacs=\nencrypted=\n");
			right.removeAll();
			right.add(new LicenseViewer().makeViewer(
					parent,
					privateKey[0],
					publicKey[0],
					lic,
					current,
					new Runnable() { public void run() { addDirectoryBrowser(); } }
					), BorderLayout.NORTH);
			right.revalidate();
		}
	}
	
	private class GenerateKeypairAction extends AbstractAction
	{
		private JFrame parent;
		
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
				chooser.setCurrentDirectory(current);
				chooser.setDialogTitle("Choose the file to save the keypair to...");
		    ExtensionFileFilter filter = new ExtensionFileFilter();
		    filter.addExtension(KEYPAIR_EXTENSION.substring(1));
				chooser.setFileFilter(filter);
				chooser.showSaveDialog(parent);

				File file = chooser.getSelectedFile();
				if (file == null)
					return;
				
				if (!file.getName().endsWith(KEYPAIR_EXTENSION))
					file = new File(file.getCanonicalPath() + KEYPAIR_EXTENSION);
				if (file != null)
				{
					FileWriter out = new FileWriter(file);
					out.write("private key format=" + pair.getPrivate().getFormat() + "\n");
					out.write("private key=" + Utils.encodeBytes(pair.getPrivate().getEncoded()) + "\n");
					out.write("public key format=" + pair.getPublic().getFormat() + "\n");
					out.write("public key=" + Utils.encodeBytes(pair.getPublic().getEncoded()) + "\n");
					out.close();
					addDirectoryBrowser();
					JOptionPane.showMessageDialog(parent, "Keypair written successfully to " + file);
				}
			}
			catch (NoSuchAlgorithmException e1)
			{
				JOptionPane.showMessageDialog(parent, "Cannot find key gen algorithm: " + ALGORITHM, "Key Problem", JOptionPane.ERROR_MESSAGE);
			}
			catch (IOException ex)
			{
				JOptionPane.showMessageDialog(parent, "Cannot write to selected file", "Key Problem", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////
	
	private void addDirectoryBrowser()
	{
		left.removeAll();
		final DefaultListModel model = new DefaultListModel();
		JList list = new JList(model);
		JPanel full = new JPanel(new BorderLayout());
		JPanel top = new JPanel(new BorderLayout());
		JLabel keys = new JLabel(keyMessage[0] == null ? "No keypair loaded" : keyMessage[0], JLabel.LEFT);
		if (keyMessage[0] == null)
			keys.setIcon(RED_ICON);
		else
			keys.setIcon(GREEN_ICON);			
		
		keys.setBorder(BorderFactory.createEtchedBorder());
		top.add(keys, BorderLayout.NORTH);
		JLabel dir = new JLabel(current.getAbsolutePath());
		dir.setBorder(BorderFactory.createEtchedBorder());
		top.add(dir, BorderLayout.SOUTH);
		full.add(top, BorderLayout.NORTH);
		JScrollPane scroller = new JScrollPane(list);
		full.add(scroller, BorderLayout.CENTER);
		left.add(full, BorderLayout.CENTER);
		
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
			if (!file.isDirectory() && file.getName().endsWith(KEYPAIR_EXTENSION))
				model.addElement(file.getName());
		}
		for (File file : all)
		{
			if (!file.isDirectory() && !file.getName().endsWith(KEYPAIR_EXTENSION))
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
					current = new File(current, value.substring(1));
					addDirectoryBrowser();
				}
				else
				if (value.equals(UP_TO_PARENT))
				{
					current = current.getParentFile();
					addDirectoryBrowser();					
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
		left.revalidate();
	}
	

	private void showLicense(JPanel right, String value)
	{
		if (privateKey[0] == null)
		{
			JOptionPane.showMessageDialog(this, "Load a keypair first!", "No keypair loaded", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// populate an LDetails
		try
		{
			LDetails lic = new LDetails(readFile(new File(value)));
			right.removeAll();
			right.add(new LicenseViewer().makeViewer(
					this,
					privateKey[0],
					publicKey[0],
					lic,
					current,
					new Runnable() { public void run() { addDirectoryBrowser(); } }
					), BorderLayout.NORTH);
			right.revalidate();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Cannot read license file", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	private void showKeyPair(JPanel right, String value)
	{
		right.removeAll();
		right.add(new KeyViewer().makeViewer(
				this,
				new File(value),
				privateKey,
				publicKey,
				keyMessage,
				new Runnable() { public void run() { addDirectoryBrowser(); } }
				), BorderLayout.NORTH);
		right.revalidate();
	}

	public static final ImageIcon loadIcon(String iconName)
	{
		URL resource = ClassLoader.getSystemResource(iconName);
		if (resource == null)
			System.err.println("Cannot find image " + iconName);
		return new ImageIcon(resource);
	}
	
	private String readFile(File file) throws IOException
	{
		FileReader reader = new FileReader(file);
		BufferedReader buf = new BufferedReader(reader);
		String line = null;
		String str = "";
		while ((line = buf.readLine()) != null)
		{
			str += line + "\n";
		}
		return str;
	}
}
