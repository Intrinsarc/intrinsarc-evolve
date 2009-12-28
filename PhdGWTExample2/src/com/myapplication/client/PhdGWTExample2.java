package com.myapplication.client;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.*;
import com.myapplication.client.hardcoded.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PhdGWTExample2 implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
//		RootPanel.get().add(new GWTCustomerWidgetFactory().initialize(null).getPanel());
		RootPanel.get().add(new GWTCustomerWidgetFactory().initialize(null).getPanel());
	}

}
