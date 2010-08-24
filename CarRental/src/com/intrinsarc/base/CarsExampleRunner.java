package com.intrinsarc.base;

import com.intrinsarc.backbone.runtime.api.*;

public class CarsExampleRunner
// start generated code
  // main port
  implements com.intrinsarc.backbone.runtime.api.IRun, com.intrinsarc.backbone.runtime.api.ILifecycle
{
  // required ports
	private java.util.List<com.intrinsarc.base.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.base.IRentalCarDetails>();
	private java.util.List<com.intrinsarc.base.IRenterDetails> renters = new java.util.ArrayList<com.intrinsarc.base.IRenterDetails>();
  // provided ports

  // port setters and getters
	public void setCar(com.intrinsarc.base.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void addCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.fill(this.cars, cars, -1); }
	public void removeCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }
	public void setRenter(com.intrinsarc.base.IRenterDetails renters, int index) { PortHelper.fill(this.renters, renters, index); }
	public void addRenter(com.intrinsarc.base.IRenterDetails renters) { PortHelper.fill(this.renters, renters, -1); }
	public void removeRenter(com.intrinsarc.base.IRenterDetails renters) { PortHelper.remove(this.renters, renters); }
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
	
	public void afterInit()
	{
		System.out.println("\nAfterInit: Cars example started");
	}

	public void beforeDelete()
	{
		System.out.println("\nBeforeDelete: Cars example finished");
	}
}
