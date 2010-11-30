package pc;


public class Consumer
{
// start generated code
// attributes
// required ports
// provided ports
	private I_putGetImpl get_I_putProvided = new I_putGetImpl();
// setters and getters
	public pc.I_put getGet_I_put(Class<?> required) { return get_I_putProvided; }
// end generated code


	private class I_putGetImpl implements pc.I_put
	{
		public void put(int i){
                    System.out.println("Got: "+i);
                }
	}

}
