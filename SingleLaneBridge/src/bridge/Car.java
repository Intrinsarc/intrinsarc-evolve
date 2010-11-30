package bridge;

import com.intrinsarc.backbone.runtime.api.*;

public class Car
// start generated code
 implements ILifecycle
{
	// attributes
	private int carNo = 0;

	// attribute setters and getters
	public int getCarNo() { return carNo; }
	public void setCarNo(int carNo) { this.carNo = carNo;}

	// required ports
	private bridge.I_move move;
	private bridge.I_access bridge;

	// port setters and getters
	public void setMove(bridge.I_move move) { this.move = move; }
	public void setBridge(bridge.I_access bridge) { this.bridge = bridge; }

// end generated code
        

    Runnable runnit = new Runnable()
    {
	    public void run() {
	      try {
	        while (true) {
	            while (!move.move(carNo));  // not on bridge
	            bridge.enter();
	            while (move.move(carNo));   // move over bridge
	           bridge.leave();
	         }
	      } catch (InterruptedException e){}
	    }
    };

    Thread car;
    
    public void afterInit() {
        car = new Thread(runnit);
        car.start();
    }

    public void beforeDelete() {
        car.interrupt();
    }
}
