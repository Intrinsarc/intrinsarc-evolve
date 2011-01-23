package com.intrinsarc.evolve.gui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

public class DisplayUsersAction extends AbstractAction
{
	public static final ImageIcon USER_ICON = IconLoader.loadIcon("arrow_refresh.png");
	private ToolCoordinatorFacet coordinator;

	public DisplayUsersAction(ToolCoordinatorFacet coordinator)
	{
		super("Display user history");
		this.coordinator = coordinator;
	}

	public void actionPerformed(ActionEvent e)
	{
		SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
		
		Collection<UserDetails> users = repository.retrieveAllHistoricalUsers();
		int size = users.size();
		System.out.println("$$ size = " + size);
		String[][] userNames = new String[2][size];
		int count = 0;
		for (UserDetails user : users)
		{
			userNames[0][count] = "foo" + user.getUser();
			userNames[1][count++] = "" + new Date(user.getTime());
		}
		DefaultTableModel model = new DefaultTableModel(userNames, new String[]{"User", "Last loaded"});
		JTable table = new JTable(model);

		JScrollPane pane = new JScrollPane(table);
		coordinator.invokeAsDialog(
				USER_ICON,
				"Users who have worked on this model",
				pane,
				new JButton[]{ new JButton("OK") },
				0,
				null);
	}
}
