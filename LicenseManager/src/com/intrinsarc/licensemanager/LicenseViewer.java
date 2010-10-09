package com.intrinsarc.licensemanager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.*;

import javax.swing.*;

import com.intrinsarc.lbase.*;

public class LicenseViewer
{
	public Component makeViewer(
			final JFrame parent,
			final PrivateKey privateKey,
			final PublicKey publicKey,
			LDetails license,
			final File current,
			final Runnable refresher)
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("<html><b>License editor"), BorderLayout.NORTH);

		final DetailsViewer editor = new DetailsViewer(false);
		JComponent main = editor.makeViewer(license);
		panel.add(main, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		panel.add(buttons, BorderLayout.SOUTH);

		JPanel group = new JPanel();
		buttons.add(group, BorderLayout.WEST);

		JButton save = new JButton("Save");
		buttons.add(save);
		save.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// write it locally
				try
				{
					String name = get(editor, "email") + "-" + get(editor, "number") + ".license";
					File file = new File(current, name);
					FileWriter writer = new FileWriter(file);
					BufferedWriter buf = new BufferedWriter(writer);				
					LDetails real = editor.makeDetails();
					buf.write(real.toString());
					buf.close();
					writer.close();					
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(parent, ex.getMessage(), "Problem saving license",
							JOptionPane.ERROR_MESSAGE);
				}
				
				refresher.run();
			}
		});

		JButton encrypt = new JButton("Encrypt");
		buttons.add(encrypt);
		encrypt.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// decrypt a possible encrypted set of mac addresses first using the
				// private key
				// and then turn it into a details structure and encrypt using the
				// private key
				String encmacs = editor.getField("encmacs").getText().trim();
				JTextField decmacs = editor.getField("decmacs");
				if (encmacs.length() > 0)
				{
					try
					{
						// decrypt
						editor.getField("decmacs").setText(LWork.decrypt(encmacs, null, privateKey));
					} catch (Exception ex)
					{
						JOptionPane.showMessageDialog(parent, ex.getMessage(), "Cannot decoded encrypted macs",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				// we must have some decmacs
				String macval = decmacs.getText().trim();
				if (macval.length() == 0)
				{
					JOptionPane.showMessageDialog(parent, "No decrypted mac addresses!", "Licensing problem",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// make a new string and encrypt it
				String lic = "user=" + get(editor, "user") + "\n" + "email=" + get(editor, "email") + "\n" + "number="
						+ get(editor, "number") + "\n" + "macs=" + macval;

				// encrypt it and get the bytes
				try
				{
					String encrypted = LWork.encrypt(lic, null, privateKey);
					editor.getField("encrypted").setText(encrypted);
				} catch (Exception ex)
				{
					JOptionPane.showMessageDialog(parent, ex.getMessage(), "Cannot encrypt license", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		JButton test = new JButton("Decrypt and test");
		buttons.add(test);
		test.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					String enc = editor.getField("encrypted").getText();
					String lic = LWork.decrypt(enc, publicKey, null);

					JPanel panel = new JPanel(new BorderLayout());
					DetailsViewer viewer = new DetailsViewer(true);
					panel.add(viewer.makeViewer(new LDetails(lic)), BorderLayout.NORTH);
					JOptionPane.showConfirmDialog(parent, panel, "License details", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex)
				{
					JOptionPane.showMessageDialog(parent, ex.getMessage(), "Cannot decrypt license", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton macs = new JButton("Local macs");
		buttons.add(macs);
		macs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String macs = "";
				for (String str : Finder.findAll())
					if (macs.length() == 0)
						macs += str;
					else macs += "," + str;
				try
				{
					editor.getField("encmacs").setText(LWork.encrypt(macs, publicKey, null));
				} catch (Exception ex)
				{
					JOptionPane.showMessageDialog(parent, ex.getMessage(), "Cannot encrypt macs", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		return panel;
	}

	private String get(DetailsViewer editor, String name)
	{
		return editor.getField(name).getText();
	}
}