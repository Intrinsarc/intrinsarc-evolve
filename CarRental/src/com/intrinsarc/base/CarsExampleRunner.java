package com.intrinsarc.base;

import com.intrinsarc.backbone.runtime.api.*;

public class CarsExampleRunner
// start generated code

  // main port
  implements com.intrinsarc.backbone.runtime.api.IRun
{
  // required ports
	private java.util.List<com.intrinsarc.base.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.base.IRentalCarDetails>();
  // provided ports

  // port setters and getters
	public void setCars(com.intrinsarc.base.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void addCars(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.fill(this.cars, cars, -1); }
	public void removeCars(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }
// end generated code
	
	public int run(String[] args)
	{
		System.out.println();
		for (IRentalCarDetails car : cars)
			System.out.println(
					"Car: model = " + car.getModel() +
					", purchased = " + car.getPurchased() +
					",is rented = " + car.isRented());
		
		return 0;
	}
}
