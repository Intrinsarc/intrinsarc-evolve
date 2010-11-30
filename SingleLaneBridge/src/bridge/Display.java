package bridge;
import com.intrinsarc.backbone.runtime.api.ILifecycle;
import com.intrinsarc.backbone.runtime.api.IRun;

public class Display
// start generated code
 implements ILifecycle
{
	// attributes
	private int maxCar = 1;

	// attribute setters and getters
	public int getMaxCar() { return maxCar; }
	public void setMaxCar(int maxCar) { this.maxCar = maxCar;}

	// provided ports
	private I_moveRedmoveImpl redmove_Provided = new I_moveRedmoveImpl();
	private I_moveBluemoveImpl bluemove_Provided = new I_moveBluemoveImpl();
	private IRunRunImpl run_Provided = new IRunRunImpl();

	// port setters and getters
	public bridge.I_move getRedmove_Provided() { return redmove_Provided; }
	public bridge.I_move getBluemove_Provided() { return bluemove_Provided; }
	public IRun getRun_Provided() { return run_Provided; }

// end generated code

        Viewer f;
        BridgeCanvas canvas;
        
	private class IRunRunImpl implements IRun
	{
		public boolean run(String args[])
		{
                    return false;
                }
	}

	private class I_moveBluemoveImpl implements bridge.I_move
	{
		public boolean move(int carNo) throws InterruptedException {
                    while (canvas==null) Thread.sleep(500);
                    return canvas.moveBlue(carNo);
                }
	}
        
        
	private class I_moveRedmoveImpl implements bridge.I_move
	{
		public boolean move(int carNo) throws InterruptedException {
                    while (canvas==null) Thread.sleep(500);
                    return canvas.moveRed(carNo);
                }
	}

    public void afterInit() {
        f = new Viewer(maxCar);
        canvas = f.get().get();
    }

    public void beforeDelete()
    {
    }
}
