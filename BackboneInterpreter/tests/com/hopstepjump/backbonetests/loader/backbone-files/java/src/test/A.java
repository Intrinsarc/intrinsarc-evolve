package test;

import com.hopstepjump.backbone.runtime.api.*;

public class A
{
// start generated code
// attributes
	private Attribute<java.lang.Integer> age;
// required ports
// provided ports
	private IRunPortImpl port_IRunProvided = new IRunPortImpl();
// setters and getters
	public Attribute<java.lang.Integer> getAge() { return age; }
	public void setAge(Attribute<java.lang.Integer> age) { this.age = age;}
	public void setRawAge(java.lang.Integer age) { this.age.set(age);}
	public com.hopstepjump.backbone.runtime.api.IRun getPort_IRun(Class<?> required) { return port_IRunProvided; }
// end generated code


	private class IRunPortImpl implements IRun
	{

		@Override
		public int run(String[] args)
		{
			System.out.println("$$ ran correctly, age = " + age.get());
			return 0;
		}
	}

}
