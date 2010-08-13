package statetest;

import net.java.dev.designgridlayout.*;

import com.intrinsarc.backbone.runtime.api.*;

public class Row
{
// start generated code
	private statetest.IRow row_IRowProvided = new IRowRowImpl();
	public statetest.IRow getRow_IRow(Class<?> required) { return row_IRowProvided; }

	private java.util.List<statetest.IGrid> grids_IGridRequired = new java.util.ArrayList<statetest.IGrid>();
	public void setGrids_IGrid(statetest.IGrid grids_IGridRequired, int index) { PortHelper.fill(this.grids_IGridRequired, grids_IGridRequired, index); }
// end generated code


	private class IRowRowImpl implements statetest.IRow
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
