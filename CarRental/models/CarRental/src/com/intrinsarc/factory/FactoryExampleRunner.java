package com.intrinsarc.factory;

import com.intrinsarc.backbone.runtime.api.*;

public class FactoryExampleRunner
// start generated code
	// main port
 implements IRun
{
	// required ports
	private java.util.List<com.intrinsarc.base.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.base.IRentalCarDetails>();
	private ICreate creator;

	// port setters and getters
	public void setCar(com.intrinsarc.base.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void addCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.fill(this.cars, cars, -1); }
	public void removeCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }
	public void setCreator(ICreate creator) { this.creator = creator; }

// end generated code


}
