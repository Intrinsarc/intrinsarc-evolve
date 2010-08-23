package test;

import com.intrinsarc.backbone.runtime.api.*;

public class A
{
// start generated code
// attributes
	private int age;
// required ports
// provided ports
	private IRunPortImpl port_IRunProvided = new IRunPortImpl();
// setters and getters
	public int getAge() { return age; }
	public void setAge(int age) { this.age = age;}
	public com.intrinsarc.backbone.runtime.api.IRun getPort_IRun(Class<?> required) { return port_IRunProvided; }
// end generated code


	private class IRunPortImpl implements IRun
	{
		public int run(String[] args)
		{
			System.out.println("$$ ran correctly, age = " + age);
			return 0;
		}
	}

}
