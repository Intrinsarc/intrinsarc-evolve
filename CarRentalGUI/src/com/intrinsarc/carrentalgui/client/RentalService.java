package com.intrinsarc.carrentalgui.client;

import com.google.gwt.core.client.*;

public class RentalService
{
	private final IRentalServiceAsync rentalService = GWT.create(IRentalService.class);
	public com.intrinsarc.carrentalgui.client.IRentalServiceAsync getService_Provided()
	{
		return rentalService;
	}
}
