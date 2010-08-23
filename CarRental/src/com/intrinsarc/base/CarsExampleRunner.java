package com.intrinsarc.base;

import com.intrinsarc.backbone.runtime.api.*;

public class CarsExampleRunner
{
// start generated code
// attributes
// required ports
	private java.util.List<com.intrinsarc.base.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.base.IRentalCarDetails>();
// provided ports
	private IRunRunImpl run_IRunProvided = new IRunRunImpl();
// setters and getters
	public void setCars_IRentalCarDetails(com.intrinsarc.base.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void removeCars_IRentalCarDetails(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }
	public com.intrinsarc.backbone.runtime.api.IRun getRun_IRun(Class<?> required) { return run_IRunProvided; }
// end generated code


	private class IRunRunImpl implements IRun
	{
		@Override
		public int run(String[] args)
		{
			// print out all the cars
			System.out.println("\nRental cars we own:");
			for (IRentalCarDetails details : cars)
				System.out.println("  Car = " + details.getModel() + ", purchased = " + details.getPurchased() + ", is rented = " + details.isRented());
			return 0;
		}
	}

}
