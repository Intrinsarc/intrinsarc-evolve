package com.intrinsarc.licensemanager;

import java.awt.*;

import javax.swing.*;

import com.intrinsarc.lbase.*;

public class DetailsViewer
{

	public JComponent makeViewer(String str)
	{
		LDetails details = new LDetails(str);
		
		GridBagLayout layout = new GridBagLayout();
		JPanel panel = new JPanel(layout);

		GridBagConstraints constraints = new GridBagConstraints(
				0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 10, 10);
		for (String name : details.getDetails().keySet())
		{
			String value = details.getDetails().get(name);
			JLabel nameLabel = new JLabel(name);
			JTextField field = new JTextField(value);
			
			panel.add(nameLabel, constraints);
			constraints.gridx = 1;
			panel.add(field, constraints);
			constraints.gridx = 0;
			constraints.gridy++;			
		}
		
		return new JScrollPane(panel);
	}

}
