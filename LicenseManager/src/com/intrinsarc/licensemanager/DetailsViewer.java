package com.intrinsarc.licensemanager;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.intrinsarc.lbase.*;

public class DetailsViewer
{
	private boolean readonly;
	private Map<String, JTextField> fields = new LinkedHashMap<String, JTextField>();
	
	public DetailsViewer(boolean readonly)
	{
		this.readonly = readonly;
	}
	
	public JComponent makeViewer(LDetails details)
	{
		GridBagLayout layout = new GridBagLayout();
		JPanel panel = new JPanel(layout);

		GridBagConstraints constraints = new GridBagConstraints(
				0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0);
		for (String name : details.getDetails().keySet())
		{
			String value = details.getDetails().get(name);
			JLabel nameLabel = new JLabel(name);
			JTextField field = new JTextField(value);
			fields.put(name, field);
			field.setEditable(!readonly);
			
			constraints.gridx = 0;
			constraints.fill = GridBagConstraints.NONE;
			constraints.weightx = 0;
			panel.add(nameLabel, constraints);

			constraints.gridx = 1;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.weightx = 1;
			panel.add(field, constraints);
			constraints.gridy++;
		}
		
		return panel;
	}
	
	public JTextField getField(String name)
	{
		return fields.get(name);
	}

	public LDetails makeDetails()
	{
		String str = "";
		for (String name : fields.keySet())
			str += name + "=" + fields.get(name).getText() + "\n";
		return new LDetails(str);
	}

}
