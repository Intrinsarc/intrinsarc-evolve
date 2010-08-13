package com.intrinsarc.carrentalgui.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rental")
public interface IRentalService extends RemoteService
{
	void createRentalCar(String model);
	void rent(int car);
	void returnRental(int car);
	String[] getCars();
}
