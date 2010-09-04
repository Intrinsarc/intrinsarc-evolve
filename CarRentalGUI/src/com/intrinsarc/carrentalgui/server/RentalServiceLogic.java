package com.intrinsarc.carrentalgui.server;

import java.util.*;

import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.base.*;


public class RentalServiceLogic
// start generated code
	// main port
 implements com.intrinsarc.carrentalgui.client.IRentalService
{
	// required ports
	private ICreate create;
	private java.util.List<com.intrinsarc.base.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.base.IRentalCarDetails>();

	// port setters and getters
	public void setCreate(ICreate create) { this.create = create; }
	public void setCar(com.intrinsarc.base.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void addCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.fill(this.cars, cars, -1); }
	public void removeCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }

// end generated code

	public void createRentalCar(String model)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("model", model);
		params.put("purchased", new Date());
		create.create(params);
	}

	public void rent(int car, String renter)
	{
		cars.get(car).setRenterName(renter);
	}
	
	public void returnRental(int car)
	{
		cars.get(car).setRenterName("");
	}

	public String[] getCars()
	{
		String[] newCars = new String[cars.size()];
		int lp = 0;
		for (IRentalCarDetails r : cars)
		{
			newCars[lp] = (r.isRented() ? ">" : "") + r.getModel() + "|" + r.getRenterName();
			lp++;
		}
		return newCars;
	}
}
