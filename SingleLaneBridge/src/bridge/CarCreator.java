package bridge;

import java.util.HashMap;
import java.util.Map;

import com.intrinsarc.backbone.runtime.api.ICreate;
import com.intrinsarc.backbone.runtime.api.ILifecycle;

public class CarCreator
// start generated code
 implements ILifecycle
{
	// attributes
	private int maxCar;

	// attribute setters and getters
	public int getMaxCar() { return maxCar; }
	public void setMaxCar(int maxCar) { this.maxCar = maxCar;}

	// required ports
	private ICreate create;

	// port setters and getters
	public void setCreate(ICreate create) { this.create = create; }

// end generated code


        private void start() {
                Map m = new HashMap();
                for (int i = 0; i<maxCar; i++) {
                   m.put("carId", i) ;
                   create.create(m);
                }             
        }

        public void afterInit() {
            start();
        }

        public void beforeDelete() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

}
