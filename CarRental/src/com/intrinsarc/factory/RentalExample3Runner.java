package com.intrinsarc.factory;

import java.util.*;

import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.cars.*;

public class RentalExample3Runner
{
// start generated code
// attributes
// required ports
	private java.util.List<com.intrinsarc.cars.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.cars.IRentalCarDetails>();
	private com.intrinsarc.cars.IRenterDetails renter;
	private com.intrinsarc.backbone.runtime.api.ICreate creator;
// provided ports
	private IRunRunImpl run_IRunProvided = new IRunRunImpl();
// setters and getters
	public void setCars_IRentalCarDetails(com.intrinsarc.cars.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void removeCars_IRentalCarDetails(com.intrinsarc.cars.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }
	public void setRenter_IRenterDetails(com.intrinsarc.cars.IRenterDetails renter) { this.renter = renter; }
	public void setCreator_ICreate(com.intrinsarc.backbone.runtime.api.ICreate creator) { this.creator = creator; }
	public com.intrinsarc.backbone.runtime.api.IRun getRun_IRun(Class<?> required) { return run_IRunProvided; }
// end generated code


	private class IRunRunImpl implements IRun
	{
		public int run(String[] args)
		{
			// make some cars and print them out
			Map<String, Object> params;
			
			params = new HashMap<String, Object>();
			params.put("model", "Volkswagen");
			params.put("purchased", new Date(108, 10, 10));
			Object memento1 = creator.create(params);
			printCars("After making first car:");

			params = new HashMap<String, Object>();
			params.put("model", "Saab");
			params.put("purchased", new Date(109, 5, 5));
			Object memento2 = creator.create(params);
			printCars("After making second car:");
			
			creator.destroy(memento1);
			printCars("After deleting first car:");

			creator.destroy(memento2);
			printCars("After deleting second car:");
			
			return 0;
		}

		private void printCars(String msg)
		{
			System.out.println("\n" + msg);
			for (IRentalCarDetails car : cars)
				System.out.println("  " + car.getModel() + ", purchased = " + car.getPurchased());
		}
	}
}
