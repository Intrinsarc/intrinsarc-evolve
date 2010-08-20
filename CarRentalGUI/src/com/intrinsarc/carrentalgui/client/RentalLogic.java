package com.intrinsarc.carrentalgui.client;

import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.intrinsarc.backbone.runtime.api.*;

public class RentalLogic
{
// start generated code
// attributes
	private Attribute<java.lang.Integer> carNo;
// required ports
	private com.google.gwt.event.dom.client.ChangeHandler refresh;
	private com.intrinsarc.carrentalgui.client.IRentalServiceAsync service;
	private com.google.gwt.user.client.ui.TextBox renter;
// provided ports
	private ClickListenerReturnImpl return_ClickListenerProvided = new ClickListenerReturnImpl();
	private ChangeListenerRenterSetImpl renterSet_ChangeListenerProvided = new ChangeListenerRenterSetImpl();
// setters and getters
	public Attribute<java.lang.Integer> getCarNo() { return carNo; }
	public void setCarNo(Attribute<java.lang.Integer> carNo) { this.carNo = carNo;}
	public void setRawCarNo(java.lang.Integer carNo) { this.carNo.set(carNo);}
	public void setRefresh_ChangeHandler(com.google.gwt.event.dom.client.ChangeHandler refresh) { this.refresh = refresh; }
	public void setService_IRentalServiceAsync(com.intrinsarc.carrentalgui.client.IRentalServiceAsync service) { this.service = service; }
	public void setRenter_TextBox(com.google.gwt.user.client.ui.TextBox renter) { this.renter = renter; }
	public com.google.gwt.user.client.ui.ClickListener getReturn_ClickListener(Class<?> required) { return return_ClickListenerProvided; }
	public com.google.gwt.user.client.ui.ChangeListener getRenterSet_ChangeListener(Class<?> required) { return renterSet_ChangeListenerProvided; }
// end generated code


	private class ChangeListenerRenterSetImpl implements ChangeListener
	{
		public void onChange(Widget sender)
		{
			service.rent(carNo.get(), new AsyncCallback<Void>()
					{
						public void onSuccess(Void v)
						{ setRenter(renter.getValue()); }
						
						public void onFailure(Throwable caught) {}
					});
		}
	}
	
	private class ClickListenerReturnImpl implements com.google.gwt.user.client.ui.ClickListener
	{
		public void onClick(Widget sender)
		{
			service.returnRental(carNo.get(), new AsyncCallback<Void>()
					{
						public void onSuccess(Void v)
						{ refresh.onChange(null); }
						
						public void onFailure(Throwable caught) {}
					});
		}
	}
	
	private void setRenter(String renterName)
	{
		service.setRenter(carNo.get(), renterName, new AsyncCallback<Void>()
		{
			public void onSuccess(Void v)
			{
				refresh.onChange(null);
			}
			
			public void onFailure(Throwable caught) {}
		});
	}
}

