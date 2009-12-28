package markerview;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

public class GridWidget
{
// start generated code
	private markerview.IGrid w_IGridProvided = new IGridWImpl();

	private java.util.List<markerview.IGridColumn> r_IGridColumnRequired = new java.util.ArrayList<markerview.IGridColumn>();
// end generated code


	private class IGridWImpl implements markerview.IGrid
	{
    public void display(String title)
    {
      JFrame frame = new JFrame("Plugins versus Component Susbstitution Architectures: " + title);
      
      // make the table and add any headings
      DefaultTableModel model = new DefaultTableModel(5, 0);
      for (IGridColumn column : r_IGridColumnRequired)
        model.addColumn(column.getName());

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
