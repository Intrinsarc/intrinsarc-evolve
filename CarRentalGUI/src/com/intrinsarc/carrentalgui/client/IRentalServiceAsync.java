package com.intrinsarc.carrentalgui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>IRentalService</code>.
 */
public interface IRentalServiceAsync
{
	void createRentalCar(String model, AsyncCallback<Void> callback) throws IllegalArgumentException;
	void rent(int car, String renter, AsyncCallback<Void> callback) throws IllegalArgumentException;
	void returnRental(int car, AsyncCallback<Void> callback) throws IllegalArgumentException;
	void getCars(AsyncCallback<String[]> callback) throws IllegalArgumentException;
}
