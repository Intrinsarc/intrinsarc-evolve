package taskview.base;

import javax.swing.table.*;

public interface IGridColumn
{
  String getName();
	void displayData(DefaultTableModel model, int col, int rows);
}
