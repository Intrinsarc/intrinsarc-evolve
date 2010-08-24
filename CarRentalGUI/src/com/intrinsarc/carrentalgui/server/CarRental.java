package com.intrinsarc.carrentalgui.server;

import java.util.*;

import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.base.*;


public class CarRental
// start generated code
  // main port
  implements com.intrinsarc.carrentalgui.client.IRentalService
{
  // required ports
	private com.intrinsarc.backbone.runtime.api.ICreate create;
	private java.util.List<com.intrinsarc.base.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.base.IRentalCarDetails>();
	private java.util.List<com.intrinsarc.base.IRenterDetails> renters = new java.util.ArrayList<com.intrinsarc.base.IRenterDetails>();
  // provided ports

  // port setters and getters
	public void setCreate(com.intrinsarc.backbone.runtime.api.ICreate create) { this.create = create; }
	public void setCar(com.intrinsarc.base.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void addCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.fill(this.cars, cars, -1); }
	public void removeCar(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }
	public void setRenter(com.intrinsarc.base.IRenterDetails renters, int index) { PortHelper.fill(this.renters, renters, index); }
	public void addRenter(com.intrinsarc.base.IRenterDetails renters) { PortHelper.fill(this.renters, renters, -1); }
	public void removeRenter(com.intrinsarc.base.IRenterDetails renters) { PortHelper.remove(this.renters, renters); }
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
		renters.get(car).setRenter(renter);
	}
	
	public void returnRental(int car)
	{
		renters.get(car).setRenter(null);
	}

	public String[] getCars()
	{
		String[] newCars = new String[cars.size()];
		int lp = 0;
		for (IRentalCarDetails r : cars)
		{
			newCars[lp] = (r.isRented() ? ">" : "") + r.getModel() + "|" + renters.get(lp).getRenter();
			lp++;
		}
		return newCars;
	}
}
