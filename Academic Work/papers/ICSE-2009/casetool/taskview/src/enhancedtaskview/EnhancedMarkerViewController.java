package enhancedtaskview;


public class EnhancedMarkerViewController
{
// start generated code
	private markerview.IView view_IViewProvided = new IViewViewImpl();

	private java.util.List<markerview.IGridDataEntry> e_IGridDataEntryRequired = new java.util.ArrayList<markerview.IGridDataEntry>();

	private markerview.IGrid w_IGridRequired;
// end generated code


	private class IViewViewImpl implements markerview.IView
	{
    public void display()
    {
      w_IGridRequired.display("Enhanced");
    }
	}
}
