package taskview.base;



public class TaskViewController
{
// start generated code
// attributes
// required ports
	private taskview.base.IGrid grid;
// provided ports
	private IViewViewImpl view_IViewProvided = new IViewViewImpl();
// setters and getters
	public void setGrid_IGrid(taskview.base.IGrid grid) { this.grid = grid; }
	public taskview.base.IView getView_IView(Class<?> required) { return view_IViewProvided; }
// end generated code


	private class IViewViewImpl implements taskview.base.IView
	{
    public void display()
    {
      grid.display("Basic");
    }
	}

}
