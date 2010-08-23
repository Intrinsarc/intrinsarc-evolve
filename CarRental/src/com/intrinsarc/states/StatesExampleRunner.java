package com.intrinsarc.states;

import java.util.*;

import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.base.*;

public class StatesExampleRunner
{
// start generated code
// attributes
// required ports
	private java.util.List<com.intrinsarc.base.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.base.IRentalCarDetails>();
	private java.util.List<com.intrinsarc.states.IRentalEvent> rentalStates = new java.util.ArrayList<com.intrinsarc.states.IRentalEvent>();
// provided ports
	private IRunRunImpl run_IRunProvided = new IRunRunImpl();
// setters and getters
	public void setCars_IRentalCarDetails(com.intrinsarc.base.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void removeCars_IRentalCarDetails(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }
	public void setRentalStates_IRentalEvent(com.intrinsarc.states.IRentalEvent rentalStates, int index) { PortHelper.fill(this.rentalStates, rentalStates, index); }
	public void removeRentalStates_IRentalEvent(com.intrinsarc.states.IRentalEvent rentalStates) { PortHelper.remove(this.rentalStates, rentalStates); }
	public com.intrinsarc.backbone.runtime.api.IRun getRun_IRun(Class<?> required) { return run_IRunProvided; }
// end generated code


	private class IRunRunImpl implements IRun
	{
		public int run(String[] args)
		{
			// get the first car
			IRentalCarDetails car = cars.get(0);
			IRentalEvent state = rentalStates.get(0);
			
			System.out.println("\n");
			print(car);
			System.out.println("  : " + state + "\n");
			
			System.out.println("--> Sending event: rent");
			state.rent();
			System.out.println("  : " + state + "\n");
			
			System.out.println("--> Sending event: setRenter");
			state.setRenter("Andrew");
			print(car);
			System.out.println("  : " + state + "\n");
			
			System.out.println("--> Sending event: return rental");
			state.returnRental();
			print(car);
			System.out.println("  : " + state + "\n");
			
			return 0;
		}

		private void print(IRentalCarDetails car)
		{
			System.out.println("Car: model = " + car.getModel() + ", purchased = " + car.getPurchased() + ", is rented = " + car.isRented());
		}
	}
}
