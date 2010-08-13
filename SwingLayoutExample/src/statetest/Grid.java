package statetest;

import javax.swing.*;

import net.java.dev.designgridlayout.*;

import com.intrinsarc.backbone.runtime.api.*;

public class Grid
{
// start generated code
	private statetest.IGrid grid_IGridProvided = new IGridGridImpl();
	public statetest.IGrid getGrid_IGrid(Class<?> required) { return grid_IGridProvided; }

	private java.util.List<javax.swing.JComponent> contents_JComponentRequired = new java.util.ArrayList<javax.swing.JComponent>();
	public void setContents_JComponent(javax.swing.JComponent contents_JComponentRequired, int index) { PortHelper.fill(this.contents_JComponentRequired, contents_JComponentRequired, index); }

	private javax.swing.JLabel label_JLabelRequired;
	public void setLabel_JLabel(javax.swing.JLabel label_JLabelRequired) { this.label_JLabelRequired = label_JLabelRequired; }
// end generated code


	private class IGridGridImpl implements statetest.IGrid
	{
		public IGridRow addToLayout(IRowCreator row, IGridRow subRow)
		{
			IGridRow grid = null;
			if (label_JLabelRequired != null)
				grid = subRow == null ? row.grid(label_JLabelRequired) : subRow.grid(label_JLabelRequired);
			else
				grid = subRow == null ? row.grid() : subRow.grid();

			for (JComponent add : contents_JComponentRequired)
				grid.add(add);
			return grid;
		}
	}
}
