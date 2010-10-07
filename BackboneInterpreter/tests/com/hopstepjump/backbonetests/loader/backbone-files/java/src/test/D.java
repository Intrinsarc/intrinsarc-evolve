package test;

import com.hopstepjump.backbone.runtime.api.*;

public class D
{
// start generated code
// attributes
	private Attribute<java.lang.Double> attr1;
	private Attribute<java.lang.String> attr2 = new Attribute<java.lang.String>("hello");
// required ports
	private test.IFace out;
// provided ports
	private IFaceInImpl in_IFaceProvided = new IFaceInImpl();
// setters and getters
	public Attribute<java.lang.Double> getAttr1() { return attr1; }
	public void setAttr1(Attribute<java.lang.Double> attr1) { this.attr1 = attr1;}
	public void setRawAttr1(java.lang.Double attr1) { this.attr1.set(attr1);}
	public Attribute<java.lang.String> getAttr2() { return attr2; }
	public void setAttr2(Attribute<java.lang.String> attr2) { this.attr2 = attr2;}
	public void setRawAttr2(java.lang.String attr2) { this.attr2.set(attr2);}
	public void setOut_IFace(test.IFace out) { this.out = out; }
	public test.IFace getIn_IFace(Class<?> required) { return in_IFaceProvided; }
// end generated code


	private class IFaceInImpl implements test.IFace
	{
		public void test()
		{
			System.out.println("$$ inside D instance: attr1 = " + attr1.get() + ", attr2 = " + attr2.get());
			out.test();
		}
	}
}
