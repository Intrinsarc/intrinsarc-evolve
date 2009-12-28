package taskview;

import com.hopstepjump.backbone.api.*;

public class DemoController
{
// start generated code

	private markerview.IView v_IViewRequired;
	private com.hopstepjump.backbone.api.IRun main_IRunProvided = new IRunMainImpl();
// end generated code


	private class IRunMainImpl implements IRun
	{

    public int run(String[] args)
    {
      System.out.println("$$ running now!");
      v_IViewRequired.display();
      return 0;
    }
	}
}
