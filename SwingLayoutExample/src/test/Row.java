package test;

import net.java.dev.designgridlayout.*;

import com.hopstepjump.backbone.runtime.api.*;

public class Row
{
// start generated code

	private java.util.List<test.IGrid> grids_IGridRequired = new java.util.ArrayList<test.IGrid>();
	public void setGrids_IGrid(test.IGrid grids_IGridRequired, int index) { PortHelper.fill(this.grids_IGridRequired, grids_IGridRequired, index); }
	private test.IRow row_IRowProvided = new IRowRowImpl();
	public test.IRow getRow_IRow(Class<?> required) { return row_IRowProvided; }
// end generated code

	private class IRowRowImpl implements test.IRow
	{
		public void addToLayout(DesignGridLayout designGrid)
		{
			IRowCreator row = designGrid.row();
			IGridRow subRow = null;
			for (IGrid grid : grids_IGridRequired)
				subRow = grid.addToLayout(row, subRow);
		}
	}
}
