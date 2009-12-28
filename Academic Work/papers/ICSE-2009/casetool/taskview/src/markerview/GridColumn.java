package markerview;


public class GridColumn
{
// start generated code
	private String name;
	private markerview.IGridColumn g_IGridColumnProvided = new IGridColumnGImpl();
// end generated code


	private class IGridColumnGImpl implements markerview.IGridColumn
	{
    public String getName()
    {
      return name;
    }
	}
}
