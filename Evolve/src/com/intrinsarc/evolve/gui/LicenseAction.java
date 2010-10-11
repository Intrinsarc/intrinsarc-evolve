package com.intrinsarc.evolve.gui;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.net.*;

import javax.swing.*;

import org.freehep.graphicsio.emf.gdi.*;

import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.lbase.*;
import com.intrinsarc.swing.*;

public class LicenseAction extends AbstractAction
{
	public static final ImageIcon LOCK_ICON = IconLoader.loadIcon("world.png");
	public static final ImageIcon NOT_LICENSED_ICON = IconLoader.loadIcon("no-license.png");
	public static final ImageIcon LICENSED_ICON = IconLoader.loadIcon("good-license.png");
	private static final String URL = "http://www.intrinsarc.com"; 
  private ToolCoordinatorFacet coordinator;
  
  public LicenseAction(ToolCoordinatorFacet coordinator)
  {
    super("License details");
    this.coordinator = coordinator;
  }
  
  public void actionPerformed(ActionEvent e)
  {
  	JPanel panel = new JPanel(new BorderLayout());
    panel.setPreferredSize(new Dimension(500, 500));
  	redoPanel(panel);
    coordinator.invokeAsDialog(null, "License details", panel, null, 0, null);
  }
  
  private void redoPanel(final JPanel panel)
  {
  	panel.removeAll();
    panel.setBackground(Color.WHITE);
    
    String[] errorReason = new String[1];
    LReal license = LReal.retrieveLicense(errorReason);
    boolean readable = errorReason[0] == null;
    
    JLabel label = new JLabel(readable ? LICENSED_ICON : NOT_LICENSED_ICON);
    label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    label.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
        try
        {
  				BrowserInvoker.openBrowser(new URL(URL));
        }
        catch (MalformedURLException ex)
        {
          ex.printStackTrace();
        }
			}
		});
    
    panel.add(label, BorderLayout.CENTER);
    
    JPanel details = new JPanel(new GridBagLayout());
    
		GridBagConstraints constraints = new GridBagConstraints(
				0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0);
		
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0;
		details.add(new JLabel("Machine ID"), constraints);

		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		final String machId = MachineId.getId();
		JTextField machineId = new JTextField(machId);
		details.add(machineId, constraints);
		JButton copyToClipboard = new JButton("Copy to clipboard");
		copyToClipboard.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
		    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		    StringSelection machIdSelection = new StringSelection(machId);
		    clipboard.setContents(machIdSelection, null);
			}
		});
		
		constraints.gridx = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0;		
		details.add(copyToClipboard, constraints);
		machineId.setEditable(false);
		constraints.gridy++;

		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0;
		details.add(new JLabel("License code"), constraints);

		constraints.gridx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		final String licValue = LReal.retrieveEncryptedLicense();
		final JTextField encrypted = new JTextField(licValue);
		details.add(encrypted, constraints);
		
		JButton validate = new JButton("Enter license");
		validate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				LReal.storeLicense(encrypted.getText());
				redoPanel(panel);
			}
		});
		
		constraints.gridx = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0;		
		details.add(validate, constraints);
		
		// if the license is readable, add in the details
		if (readable)
		{
			addDetail(details, license, "user", license.getUser(),constraints);
			addDetail(details, license, "email", license.getEmail(), constraints);
			addDetail(details, license, "number", "" + license.getNumber(), constraints);
			addDetail(details, license, "expiry", license.getExpiry(), constraints);
		}		
		
		if (encrypted.getText().length() > 0)
		{
			constraints.gridy++;
			constraints.gridx = 0;
			constraints.gridwidth = 3;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.weightx = 1;
			
			if (license != null && license.isExpired())
				details.add(new JLabel("<html><div style='color:red'>License expired on " + license.getExpiry()), constraints);
			else	
			if (errorReason[0] == null)
				details.add(new JLabel("License is valid"), constraints);
			else
				details.add(new JLabel("<html><div style='color:red'>" + errorReason[0]), constraints);
		}
		
		panel.add(details, BorderLayout.SOUTH);    
    panel.revalidate();
    panel.repaint();
  }

	private void addDetail(JPanel details, LReal license, String name, String value, GridBagConstraints constraints)
	{
		constraints.gridy++;
		constraints.gridx = 0;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0;		
		details.add(new JLabel(name), constraints);
		
		constraints.gridx = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		JTextField field = new JTextField(value);
		field.setEditable(false);
		details.add(field, constraints);
	}
}
