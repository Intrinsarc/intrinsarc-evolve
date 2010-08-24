package com.intrinsarc.factory;

import java.util.*;

import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.base.*;

public class FactoryExampleRunner
// start generated code

  // main port
  implements com.intrinsarc.backbone.runtime.api.IRun
{
  // required ports
	private java.util.List<com.intrinsarc.base.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.base.IRentalCarDetails>();
	private com.intrinsarc.backbone.runtime.api.ICreate creator;
  // provided ports

  // port setters and getters
	public void setCars(com.intrinsarc.base.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void addCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.fill(this.cars, cars, -1); }
	public void removeCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }
	public void setCreator(com.intrinsarc.backbone.runtime.api.ICreate creator) { this.creator = creator; }
// end generated code


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
