package com.intrinsarc.carrentalgui.client;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CarRentalGUI implements EntryPoint
{
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		CarRentalFormFactory form = new CarRentalFormFactory().initialize(null, null);
		RootPanel.get("application").add(form.getPanel_Provided());
	}
}
