package com.intrinsarc.states;

import java.util.*;

import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.base.*;

public class StatesExampleRunner
// start generated code

  // main port
  implements com.intrinsarc.backbone.runtime.api.IRun
{
  // required ports
	private java.util.List<com.intrinsarc.base.IRentalCarDetails> cars = new java.util.ArrayList<com.intrinsarc.base.IRentalCarDetails>();
	private java.util.List<com.intrinsarc.states.IRentalEvent> rentalStates = new java.util.ArrayList<com.intrinsarc.states.IRentalEvent>();
  // provided ports

  // port setters and getters
	public void setCars(com.intrinsarc.base.IRentalCarDetails cars, int index) { PortHelper.fill(this.cars, cars, index); }
	public void addCars(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.fill(this.cars, cars, -1); }
	public void removeCars(com.intrinsarc.base.IRentalCarDetails cars) { PortHelper.remove(this.cars, cars); }
	public void setRentalStates(com.intrinsarc.states.IRentalEvent rentalStates, int index) { PortHelper.fill(this.rentalStates, rentalStates, index); }
	public void addRentalStates(com.intrinsarc.states.IRentalEvent rentalStates) { PortHelper.fill(this.rentalStates, rentalStates, -1); }
	public void removeRentalStates(com.intrinsarc.states.IRentalEvent rentalStates) { PortHelper.remove(this.rentalStates, rentalStates); }
// end generated code


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
