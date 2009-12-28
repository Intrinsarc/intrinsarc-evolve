package com.mycompany.client;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.*;
import com.mycompany.client.hardcoded.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyApplication implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
//		RootPanel.get().add(new GWTCustomerWidgetFactory().initialize(null).getPanel());
		RootPanel.get().add(new GWTCommercialCustomerWidgetFactory().initialize(null).getPanel());
	}

}
