package com.intrinsarc.carrentalgui.client;

import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.intrinsarc.backbone.runtime.api.*;

public class RentalLogic
// start generated code
	// main port
 implements com.google.gwt.user.client.ui.ClickListener
{
	// attributes
	private Integer carNo;

	// attribute setters and getters
	public Integer getCarNo() { return carNo; }
	public void setCarNo(Integer carNo) { this.carNo = carNo;}

	// required ports
	private com.google.gwt.user.client.ui.ChangeListener refresh;
	private com.intrinsarc.carrentalgui.client.IRentalServiceAsync service;
	private com.google.gwt.user.client.ui.TextBox renter;
	// provided ports
	private ChangeListenerRenterSetImpl renterSet_Provided = new ChangeListenerRenterSetImpl();

	// port setters and getters
	public void setRefresh(com.google.gwt.user.client.ui.ChangeListener refresh) { this.refresh = refresh; }
	public void setService(com.intrinsarc.carrentalgui.client.IRentalServiceAsync service) { this.service = service; }
	public void setRenter(com.google.gwt.user.client.ui.TextBox renter) { this.renter = renter; }
	public com.google.gwt.user.client.ui.ChangeListener getRenterSet_Provided() { return renterSet_Provided; }

// end generated code


	private class ChangeListenerRenterSetImpl implements ChangeListener
	{
		public void onChange(Widget sender)
		{
			service.rent(carNo, renter.getValue(), new AsyncCallback<Void>()
					{
						public void onSuccess(Void v)
						{ refresh.onChange(null); }
						
						public void onFailure(Throwable caught) {}
					});
		}
	}
	
	public void onClick(Widget sender)
	{
		service.returnRental(carNo, new AsyncCallback<Void>()
				{
					public void onSuccess(Void v)
					{ refresh.onChange(null); }
					
					public void onFailure(Throwable caught) {}
				});
	}
}

