package com.intrinsarc.carrentalgui.client;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CarRentalGUI implements EntryPoint
{
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final IRentalServiceAsync rentalService = GWT.create(IRentalService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		CarRentalFormFactory form = new CarRentalFormFactory().initialize(null, null);
		form.setService(rentalService);
		RootPanel.get("application").add(form.getPanel());
	}
}
