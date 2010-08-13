package test;

import com.intrinsarc.backbone.runtime.api.*;


public class Register implements ILifecycle
{
// start generated code

	private java.util.List<test.IRegister> register_IRegisterRequired = new java.util.ArrayList<test.IRegister>();
	public void setRegister_IRegister(test.IRegister register_IRegisterRequired, int index) { PortHelper.fill(this.register_IRegisterRequired, register_IRegisterRequired, index); }
// end generated code
	
	public void afterInit()
	{
		System.out.println("$$ registered clients for " + this + " are:");
		for (IRegister r : register_IRegisterRequired)
			System.out.println("  $$ r = " + r);
	}

	public void beforeDelete()
	{
	}
}
