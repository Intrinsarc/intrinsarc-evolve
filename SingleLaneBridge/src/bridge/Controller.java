package bridge;


public class Controller
// start generated code
{
	// provided ports
	private I_accessRedImpl red_Provided = new I_accessRedImpl();
	private I_accessBlueImpl blue_Provided = new I_accessBlueImpl();

	// port setters and getters
	public bridge.I_access getRed_Provided() { return red_Provided; }
	public bridge.I_access getBlue_Provided() { return blue_Provided; }

// end generated code

        Bridge br = new Bridge();
        
	private class I_accessBlueImpl implements bridge.I_access
	{
            public  void enter() throws InterruptedException{
                br.blueEnter();
            }
            
            public  void leave() {
                br.blueExit();
            }
	}

	private class I_accessRedImpl implements bridge.I_access
	{
            public  void enter() throws InterruptedException{
                br.redEnter();
            }
            
            public  void leave() {
                br.redExit();
            }

	}

}
