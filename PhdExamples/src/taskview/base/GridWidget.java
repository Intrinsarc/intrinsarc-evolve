package taskview.base;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import com.hopstepjump.backbone.runtime.api.*;

public class GridWidget
{
// start generated code
// attributes
// required ports
	private java.util.List<taskview.base.IGridColumn> r = new java.util.ArrayList<taskview.base.IGridColumn>();
// provided ports
	private IGridGridImpl grid_IGridProvided = new IGridGridImpl();
// setters and getters
	public void setR_IGridColumn(taskview.base.IGridColumn r, int index) { PortHelper.fill(this.r, r, index); }
	public void removeR_IGridColumn(taskview.base.IGridColumn r) { PortHelper.remove(this.r, r); }
	public taskview.base.IGrid getGrid_IGrid(Class<?> required) { return grid_IGridProvided; }
// end generated code


	private class IGridGridImpl implements taskview.base.IGrid
	{
    public void display(String title)
    {
      JFrame frame = new JFrame("Backbone TaskView example");
      
      // make the table and add any headings
      int rows = 5;
      DefaultTableModel model = new DefaultTableModel(rows, 0);
      int col = 0;
      for (IGridColumn column : r)
      {
        model.addColumn(column.getName());
        r.get(col).displayData(model, col, rows);
        col++;
      }

      JTable table = new JTable(model);
      JScrollPane scroller = new JScrollPane(table);
      frame.add(scroller);
      
      frame.setPreferredSize(new Dimension(500, 300));
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
    }
	}

}
