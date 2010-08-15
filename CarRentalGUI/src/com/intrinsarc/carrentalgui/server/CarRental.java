package com.intrinsarc.carrentalgui.server;

import java.util.*;

import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.cars.*;


public class CarRental
{
// start generated code
// attributes
// required ports
	private com.intrinsarc.backbone.runtime.api.ICreate create;
	private java.util.List<com.intrinsarc.cars.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.cars.IRentalCarDetails>();
	private java.util.List<com.intrinsarc.states.IRentalEvent> renters = new java.util.ArrayList<com.intrinsarc.states.IRentalEvent>();
// provided ports
	private IRentalServiceServiceImpl service_IRentalServiceProvided = new IRentalServiceServiceImpl();
// setters and getters
	public void setCreate_ICreate(com.intrinsarc.backbone.runtime.api.ICreate create) { this.create = create; }
	public void setCars_IRentalCarDetails(com.intrinsarc.cars.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void removeCars_IRentalCarDetails(com.intrinsarc.cars.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }
	public void setRenters_IRentalEvent(com.intrinsarc.states.IRentalEvent renters, int index) { PortHelper.fill(this.renters, renters, index); }
	public void removeRenters_IRentalEvent(com.intrinsarc.states.IRentalEvent renters) { PortHelper.remove(this.renters, renters); }
	public com.intrinsarc.carrentalgui.client.IRentalService getService_IRentalService(Class<?> required) { return service_IRentalServiceProvided; }
// end generated code

	private class IRentalServiceServiceImpl implements com.intrinsarc.carrentalgui.client.IRentalService
	{
		public void createRentalCar(String model)
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("model", model);
			params.put("purchased", new Date());
			create.create(params);
		}

		public void rent(int car)
		{
			renters.get(car).rent();
		}
		
		public void setRenter(int car, String renter)
		{
			System.out.println("$$ setting renter for car " + car + " to " + renter);
			renters.get(car).setRenter(renter);
		}

		public void returnRental(int car)
		{
			renters.get(car).returnRental();
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

}
