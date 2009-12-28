package taskview.base;

import javax.swing.table.*;

import com.hopstepjump.backbone.runtime.api.*;


public class GridColumn
{
// start generated code
// attributes
	private Attribute<java.lang.String> name;
// required ports
	private taskview.base.IGridDataProvider provider;
// provided ports
	private IGridColumnColumnImpl column_IGridColumnProvided = new IGridColumnColumnImpl();
// setters and getters
	public Attribute<java.lang.String> getName() { return name; }
	public void setName(Attribute<java.lang.String> name) { this.name = name;}
	public void setRawName(java.lang.String name) { this.name.set(name);}
	public void setProvider_IGridDataProvider(taskview.base.IGridDataProvider provider) { this.provider = provider; }
	public taskview.base.IGridColumn getColumn_IGridColumn(Class<?> required) { return column_IGridColumnProvided; }
// end generated code


	private class IGridColumnColumnImpl implements taskview.base.IGridColumn
	{
    public String getName()
    {
      return name.get();
    }

		public void displayData(DefaultTableModel model, int column, int rows)
		{
			for (int lp = 0; lp < rows; lp++)
				model.setValueAt(provider.getData(name.get(), lp), lp, column);
		}
	}
}
