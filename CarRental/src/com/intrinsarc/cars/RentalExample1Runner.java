package com.intrinsarc.cars;

import com.hopstepjump.backbone.runtime.api.*;

public class RentalExample1Runner
{
// start generated code
// attributes
// required ports
	private java.util.List<com.intrinsarc.cars.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.cars.IRentalCarDetails>();
	private java.util.List<com.intrinsarc.cars.IRenterDetails> renters = new java.util.ArrayList<com.intrinsarc.cars.IRenterDetails>();
// provided ports
	private IRunRunImpl run_IRunProvided = new IRunRunImpl();
// setters and getters
	public void setCars_IRentalCarDetails(com.intrinsarc.cars.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void removeCars_IRentalCarDetails(com.intrinsarc.cars.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }
	public void setRenters_IRenterDetails(com.intrinsarc.cars.IRenterDetails renters, int index) { PortHelper.fill(this.renters, renters, index); }
	public void removeRenters_IRenterDetails(com.intrinsarc.cars.IRenterDetails renters) { PortHelper.remove(this.renters, renters); }
	public com.hopstepjump.backbone.runtime.api.IRun getRun_IRun(Class<?> required) { return run_IRunProvided; }
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
