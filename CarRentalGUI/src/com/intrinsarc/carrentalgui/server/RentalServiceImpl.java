package com.intrinsarc.carrentalgui.server;

import com.google.gwt.user.server.rpc.*;
import com.intrinsarc.carrentalgui.client.*;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RentalServiceImpl extends RemoteServiceServlet implements IRentalService
{
	private IRentalService service = new CarRentalServiceFactory().initialize(null, null).getService_Provided();
	
	public void createRentalCar(String model)
	{
		service.createRentalCar(model);
	}

	public void rent(int car, String renter)
	{
		service.rent(car, renter);
	}
	
	public void returnRental(int car)
	{
		service.returnRental(car);
	}

	public String[] getCars()
	{
		return service.getCars();
	}
}
