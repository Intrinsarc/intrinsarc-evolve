package taskview.base;

import com.hopstepjump.backbone.runtime.api.*;

public class DemoController
{
// start generated code
// attributes
// required ports
	private taskview.base.IView view;
// provided ports
	private IRunRunImpl run_IRunProvided = new IRunRunImpl();
// setters and getters
	public void setView_IView(taskview.base.IView view) { this.view = view; }
	public com.hopstepjump.backbone.runtime.api.IRun getRun_IRun(Class<?> required) { return run_IRunProvided; }
// end generated code


	private class IRunRunImpl implements IRun
	{

    public int run(String[] args)
    {
      System.out.println("$$ running now!");
      view.display();
      return 0;
    }
	}
}
