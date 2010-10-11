package com.intrinsarc.licensemanager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.*;
import java.security.spec.*;

import javax.swing.*;

import com.intrinsarc.lbase.*;

public class KeyViewer
{
	public JComponent makeViewer(
			final JFrame parent,
			final File keysetFile,
			final PrivateKey[] privateKey,
			final PublicKey[] publicKey,
			final String[] keyMessage,
			final Runnable refresher)
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("<html><b>Key pair viewer"), BorderLayout.NORTH);
		
		final LDetails details = new LDetails(FileUtilities.loadFileContents(keysetFile));
		JComponent main = new DetailsViewer(true).makeViewer(details, null);
		panel.add(main, BorderLayout.CENTER);
		
		JPanel buttons = new JPanel(new BorderLayout());
		JButton load = new JButton("Load keypair");
		load.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					// load in the keys
					KeyFactory factory = KeyFactory.getInstance("RSA");
					EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(Utils.decode(details.get("private key")));
					
					privateKey[0] = factory.generatePrivate(privateSpec);
					EncodedKeySpec publicSpec = new X509EncodedKeySpec(Utils.decode(details.get("public key")));
					publicKey[0] = factory.generatePublic(publicSpec);
					keyMessage[0] = "Keypair loaded: " + keysetFile.getAbsolutePath();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(parent, ex.getMessage(), "Cannot process keys", JOptionPane.ERROR_MESSAGE);
					privateKey[0] = null;
					publicKey[0] = null;
					keyMessage[0] = null;
				}
				refresher.run();
			}
		});
		
		buttons.add(load, BorderLayout.WEST);
		panel.add(buttons, BorderLayout.SOUTH);
		
		return panel;
	}
}
