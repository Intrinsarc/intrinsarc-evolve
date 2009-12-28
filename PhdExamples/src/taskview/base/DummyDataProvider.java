package taskview.base;


public class DummyDataProvider
{
// start generated code
// attributes
// required ports
// provided ports
	private IGridDataProviderGImpl g_IGridDataProviderProvided = new IGridDataProviderGImpl();
// setters and getters
	public taskview.base.IGridDataProvider getG_IGridDataProvider(Class<?> required) { return g_IGridDataProviderProvided; }
// end generated code


	private class IGridDataProviderGImpl implements taskview.base.IGridDataProvider
	{
		public String getData(String column, int row)
		{
			if (row >= 3)
				return null;
			if (column.equals("Description"))
				return new String[]{"Fix misaligned component", "Correct spellings", "Retest logic"}[row];
			if (column.equals("Location"))
				return new String[]{"line 337", "line 100", "line 623"}[row];
			if (column.equals("Resource"))
				return new String[]{"gui.java", "doc.txt", "test.java"}[row];
			if (column.equals("Path"))
				return new String[]{"/src", "/docs", "/test"}[row];
			return null;
		}
		//@todo add interface methods
	}

}
