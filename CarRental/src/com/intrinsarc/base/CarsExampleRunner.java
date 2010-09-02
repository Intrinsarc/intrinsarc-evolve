package com.intrinsarc.base;

import com.intrinsarc.backbone.runtime.api.*;

public class CarsExampleRunner
// start generated code
	// main port
 implements IRun, ILifecycle
{
	// required ports
	private java.util.List<com.intrinsarc.base.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.base.IRentalCarDetails>();

	// port setters and getters
	public void setCar(com.intrinsarc.base.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void addCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.fill(this.cars, cars, -1); }
	public void removeCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }

// end generated code
	
	public void run(String[] args)
	{
		System.out.println();

		for (IRentalCarDetails car : cars)
			System.out.println(
					"Car: model = " + car.getModel() +
					", purchased = " + car.getPurchased() +
					",is rented = " + car.isRented());
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
