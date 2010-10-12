package com.intrinsarc.licensemanager;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.*;
import java.text.*;
import java.util.*;

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
			boolean isNew,
			final Runnable refresher)
	{
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("<html><b>License editor"), BorderLayout.NORTH);

		final DetailsViewer editor = new DetailsViewer(false);
		final JButton save = new JButton("Save");
		save.setEnabled(isNew);
		JComponent main = editor.makeViewer(license, new Runnable()
		{
			public void run()
			{
				save.setEnabled(true);
			}
		});
		panel.add(main, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		panel.add(buttons, BorderLayout.SOUTH);

		JPanel group = new JPanel();
		buttons.add(group, BorderLayout.WEST);

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
					if (file.exists())
					{
						int option = JOptionPane.showConfirmDialog(parent, "License exists already. Overwrite?");
						if (option != 0)
							return;
					}
					FileWriter writer = new FileWriter(file);
					BufferedWriter buf = new BufferedWriter(writer);				
					LDetails real = editor.makeDetails();
					buf.write(real.toString());
					buf.close();
					writer.close();	
					save.setEnabled(false);
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
				String machineId = editor.getField("machine-id").getText().trim();
				JTextField macs = editor.getField("macs");
				if (machineId.length() > 0)
				{
					try
					{
						String hex = "";
						String actual = LFinder.toHex(LUtils.decode(machineId));
						for (int lp = 0; lp < actual.length() / 12; lp++)
						{
							if (lp != 0)
								hex += ",";
							hex += actual.substring(lp * 12, lp * 12 + 12);
						}
						macs.setText(hex);
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog(parent, ex.getMessage(), "Cannot decoded base64 macs",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				// we must have some macs
				String macval = macs.getText().trim();
				if (macval.length() == 0)
				{
					JOptionPane.showMessageDialog(parent, "No mac addresses!", "Licensing problem",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// make a new string and encrypt it
				int expiryDays = new Integer(get(editor, "expiry-days"));
				String expiry = "never";
				if (expiryDays >= 0)
				{
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, expiryDays);
					DateFormat formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("en", "US"));
					expiry = formatter.format(cal.getTime());
				}

				String lic =
					"user=" + get(editor, "user") +
					"\nemail=" + get(editor, "email") +
					"\nnumber=" + get(editor, "number") +
					"\nfeatures=" + get(editor, "features") +
					"\nexpiry=" + expiry +
					"\nmachine-id=" + get(editor, "machine-id");

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
					panel.add(viewer.makeViewer(new LDetails(lic), null), BorderLayout.NORTH);
					JOptionPane.showConfirmDialog(
							parent,
							panel,
							"License details",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex)
				{
					JOptionPane.showMessageDialog(parent, ex.getMessage(), "Cannot decrypt license", JOptionPane.ERROR_MESSAGE);
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