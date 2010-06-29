package test;

import com.hopstepjump.backbone.runtime.api.*;

public class E
{
// start generated code
// attributes
	private Attribute<java.lang.Integer> ageX;
// required ports
	private test.IFace out;
	private com.hopstepjump.backbone.runtime.api.ICreate create;
// provided ports
	private IRunPortImpl port_IRunProvided = new IRunPortImpl();
	private IFaceInImpl in_IFaceProvided = new IFaceInImpl();
// setters and getters
	public Attribute<java.lang.Integer> getAgeX() { return ageX; }
	public void setAgeX(Attribute<java.lang.Integer> ageX) { this.ageX = ageX;}
	public void setRawAgeX(java.lang.Integer ageX) { this.ageX.set(ageX);}
	public void setOut_IFace(test.IFace out) { this.out = out; }
	public void setCreate_ICreate(com.hopstepjump.backbone.runtime.api.ICreate create) { this.create = create; }
	public com.hopstepjump.backbone.runtime.api.IRun getPort_IRun(Class<?> required) { return port_IRunProvided; }
	public test.IFace getIn_IFace(Class<?> required) { return in_IFaceProvided; }
// end generated code

	private class IFaceInImpl implements test.IFace
	{

		public void test()
		{
			System.out.println("$$ back to source!");
		}
	}

	private class IRunPortImpl implements IRun
	{

		public int run(String[] args)
		{
			System.out.println("$$ running, about to create: ageX = " + ageX.get());
			create.create(null);
			out.test();
			return 0;
		}
	}
}