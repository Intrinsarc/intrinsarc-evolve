package pc;

import com.intrinsarc.backbone.runtime.api.IRun;


public class Producer
{
// start generated code
// attributes
	private int putit;
// required ports
	private pc.I_put put;
// provided ports
	private IRunRunImpl run_IRunProvided = new IRunRunImpl();
// setters and getters
	public int getPutit() { return putit; }
	public void setPutit(int putit) { this.putit = putit;}
	public void setPut_I_put(pc.I_put put) { this.put = put; }
	public IRun getRun_IRun(Class<?> required) { return run_IRunProvided; }
// end generated code


	private class IRunRunImpl implements IRun
	{
		public boolean run(String args[]) {
                           put.put(putit);
                           return false;
                }
	}

}
