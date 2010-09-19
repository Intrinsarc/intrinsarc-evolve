package com.intrinsarc.carrentalgui.client;


public class RentalService
// start generated code
{
	// required ports
	private com.intrinsarc.carrentalgui.client.IRentalServiceAsync required;

	// port setters and getters
	public void setRequired(com.intrinsarc.carrentalgui.client.IRentalServiceAsync required) { this.required = required; }

// end generated code
	public com.intrinsarc.carrentalgui.client.IRentalServiceAsync getService_Provided()
	{
		return required;
	}
}
