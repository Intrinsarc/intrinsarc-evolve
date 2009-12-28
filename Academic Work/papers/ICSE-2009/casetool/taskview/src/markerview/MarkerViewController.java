package markerview;


public class MarkerViewController
{
// start generated code
	private markerview.IView v_IViewProvided = new IViewVImpl();

	private java.util.List<markerview.IGridDataEntry> e_IGridDataEntryRequired = new java.util.ArrayList<markerview.IGridDataEntry>();

	private markerview.IGrid w_IGridRequired;
// end generated code


	private class IViewVImpl implements markerview.IView
	{
    public void display()
    {
      w_IGridRequired.display("Basic");
    }
	}

}
