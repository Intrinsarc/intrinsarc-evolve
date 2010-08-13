package com.intrinsarc.carrentalgui.server;

import org.apache.tools.ant.taskdefs.condition.*;


public class CarRental
{
// start generated code
// attributes
// required ports
// provided ports
	private IRentalServiceServiceImpl service_IRentalServiceProvided = new IRentalServiceServiceImpl();
// setters and getters
	public com.intrinsarc.carrentalgui.client.IRentalService getService_IRentalService(Class<?> required) { return service_IRentalServiceProvided; }
// end generated code

	private class IRentalServiceServiceImpl implements com.intrinsarc.carrentalgui.client.IRentalService
	{
		String[] cars = {"Ford", "Mercedes", "BMW"};
		boolean[] rented = {false, false, true};
		
		public void createRentalCar(String model)
		{
		}

		public void rent(int car)
		{
			rented[car] = true;
		}

		public void returnRental(int car)
		{
			rented[car] = false;
		}

		public String[] getCars()
		{
			return cars;
		}
	}

}
