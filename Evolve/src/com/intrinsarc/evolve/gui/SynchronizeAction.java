package com.intrinsarc.evolve.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import org.eclipse.emf.common.util.*;

import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;


class MyTableModel extends AbstractTableModel {
  private String[] columnNames = {"Diagram in conflict", "Discard local changes"};
  private String[] diagramNames;
  private Boolean[] discard;
  
  public MyTableModel(String[] diagramNames)
  {
  	this.diagramNames = diagramNames;
  	discard = new Boolean[diagramNames.length];
  	for (int lp = 0; lp < diagramNames.length; lp++)
  		discard[lp] = true;
  }
  
  public int getColumnCount()
  {
      return columnNames.length;
  }

  public int getRowCount()
  {
      return diagramNames.length;
  }

  public String getColumnName(int col)
  {
      return columnNames[col];
  }

  public Object getValueAt(int row, int col)
  {
  	if (col == 0)
  		return diagramNames[row];
  	return discard[row];
  }

  public Class<?> getColumnClass(int col)
  {
      return col == 0 ? String.class : Boolean.class;
  }

  public boolean isCellEditable(int row, int col)
  {
      return col != 0;
  }

  public void setValueAt(Object value, int row, int col)
  {
      discard[row] = (Boolean) value;
      fireTableCellUpdated(row, col);
  }
  
  public Boolean getDiscard(int row)
  {
  	return discard[row];
  }
}


public class SynchronizeAction extends AbstractAction
{
	public static final ImageIcon REFRESH_ICON = IconLoader.loadIcon("arrow_refresh.png");
	public static final ImageIcon ERROR_ICON = IconLoader.loadIcon("transmit_error.png");

	private ToolCoordinatorFacet coordinator;
	private PopupMakerFacet popup;
	private TransactionManagerFacet commandManager;

	public SynchronizeAction(ToolCoordinatorFacet coordinator, TransactionManagerFacet commandManager, PopupMakerFacet popup)
	{
		super("Synchronize diagrams");
		this.coordinator = coordinator;
		this.commandManager = commandManager;
		this.popup = popup;
	}

	public void actionPerformed(ActionEvent e)
	{
		// get hold of all diagrams with a conflict
		List<DiagramSaveDetails> conflicted = new ArrayList<DiagramSaveDetails>();
		for (DiagramFacet diagram : GlobalDiagramRegistry.registry.getDiagrams())
		{
			if (!diagram.isClipboard() && diagram.isModified())
			{
				DiagramSaveDetails current = diagram.getSaveDetails();
				DiagramSaveDetails remote = GlobalSubjectRepository.repository.getDiagramSaveDetails(diagram);
				if (!current.equals(remote))
					conflicted.add(remote);
			}
		}

		int size = conflicted.size();
		String[] diagramNames = new String[size];
		int count = 0;
		for (DiagramSaveDetails details : conflicted)
		{
			org.eclipse.uml2.Package pkg = (org.eclipse.uml2.Package) details.getDiagram().getLinkedObject();
			diagramNames[count++] = GlobalSubjectRepository.repository.getFullyQualifiedName(pkg, "::");
		}
		MyTableModel model = new MyTableModel(diagramNames);
		JTable table = new JTable(model);

		JScrollPane pane = new JScrollPane(table);
		int chosen = coordinator.invokeAsDialog(
				ERROR_ICON,
				"Some diagrams are in conflict: " + size,
				pane,
				new JButton[]{ new JButton("Synchronize"), new JButton("Cancel") },
				0,
				null);
		// user can cancel
		if (chosen == 1)
			return;
		
		// determine which diagrams should be preserved
		List<DiagramFacet> preserve = new ArrayList<DiagramFacet>();
		count = 0;
		for (DiagramSaveDetails details : conflicted)
		{
			if (!model.getDiscard(count++))
				preserve.add(details.getDiagram());
		}

		coordinator.startTransaction("", "");
		EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;
		GlobalSubjectRepository.ignoreUpdates = true;

		GlobalSubjectRepository.repository.refreshAll();
		coordinator.commitTransaction();

		GlobalDiagramRegistry.registry.refreshAllDiagrams(preserve);
		coordinator.clearTransactionHistory();
		popup.displayPopup(REFRESH_ICON, "Refresh",
				"Synchronized diagrams; cleared undo/redo history",
				ScreenProperties.getUndoPopupColor(), Color.black, 1500, true,
				commandManager.getTransactionPosition(),
				commandManager.getTotalTransactions());
	}
};
