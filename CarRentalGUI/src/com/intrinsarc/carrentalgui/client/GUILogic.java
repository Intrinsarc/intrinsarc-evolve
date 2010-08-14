package com.intrinsarc.carrentalgui.client;

import java.util.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
import com.intrinsarc.backbone.runtime.api.*;

public class GUILogic implements ILifecycle
{
// start generated code
// attributes
// required ports
	private com.intrinsarc.backbone.runtime.api.ICreate create;
	private com.google.gwt.user.client.ui.TextBox text;
	private com.google.gwt.user.client.ui.Panel panel;
// provided ports
	private ChangeHandlerChangeImpl change_ChangeHandlerProvided = new ChangeHandlerChangeImpl();
	private java.util.List<ClickListenerRentImpl>  rent_ClickListenerProvided = new java.util.ArrayList<ClickListenerRentImpl>();
	private java.util.List<ClickListenerReturnImpl>  return_ClickListenerProvided = new java.util.ArrayList<ClickListenerReturnImpl>();
// setters and getters
	public void setCreate_ICreate(com.intrinsarc.backbone.runtime.api.ICreate create) { this.create = create; }
	public void setText_TextBox(com.google.gwt.user.client.ui.TextBox text) { this.text = text; }
	public void setPanel_Panel(com.google.gwt.user.client.ui.Panel panel) { this.panel = panel; }
	public com.google.gwt.event.dom.client.ChangeHandler getChange_ChangeHandler(Class<?> required) { return change_ChangeHandlerProvided; }
	public com.google.gwt.user.client.ui.ClickListener getRent_ClickListener(Class<?> provided, int index) { return PortHelper.fill(rent_ClickListenerProvided, new ClickListenerRentImpl(), index); }
	public void removeRent_ClickListener(com.google.gwt.user.client.ui.ClickListener  provided) { PortHelper.remove(rent_ClickListenerProvided, provided); }
	public com.google.gwt.user.client.ui.ClickListener getReturn_ClickListener(Class<?> provided, int index) { return PortHelper.fill(return_ClickListenerProvided, new ClickListenerReturnImpl(), index); }
	public void removeReturn_ClickListener(com.google.gwt.user.client.ui.ClickListener  provided) { PortHelper.remove(return_ClickListenerProvided, provided); }
// end generated code

	private IRentalServiceAsync service;
	public void setService_IRentalServiceAsync(com.intrinsarc.carrentalgui.client.IRentalServiceAsync service) { this.service = service; refresh(); }
	private List<Object> mementos = new ArrayList<Object>();

	private class ClickListenerRentImpl implements ClickListener
	{
		public void onClick(Widget sender)
		{
			service.rent(rent_ClickListenerProvided.indexOf(this), new AsyncCallback<Void>()
			{
				public void onSuccess(Void v)
				{ refresh(); }
				
				public void onFailure(Throwable caught) {}
			});
		}
	}
	
	private class ClickListenerReturnImpl implements ClickListener
	{
		public void onClick(Widget sender)
		{
			service.returnRental(return_ClickListenerProvided.indexOf(this), new AsyncCallback<Void>()
			{
				public void onSuccess(Void v)
				{ refresh(); }
				
				public void onFailure(Throwable caught) {}
			});
		}
	}
	
	private class ChangeHandlerChangeImpl implements com.google.gwt.event.dom.client.ChangeHandler
	{
		public void onChange(ChangeEvent event)
		{
			if (event == null)
				refresh();
			
			service.createRentalCar(text.getText(), new AsyncCallback<Void>()
			{
				public void onSuccess(Void v)
				{ refresh(); }
				
				public void onFailure(Throwable caught) {}
			});
			text.setValue("");
		}
	}

	private void refresh()
	{
		service.getCars(new AsyncCallback<String[]>()
		{
			public void onSuccess(String[] cars)
			{
				for (Object m : mementos)
					create.destroy(m);
				mementos.clear();
				
				for (String car : cars)
				{
					Map<String, Object> params = new HashMap<String, Object>();
					
					boolean rented = false;
					if (car.startsWith(">"))
					{
						car = car.substring(1);
						rented = true;
					}
					params.put("model", car);
					params.put("rentEnabled", !rented);
					params.put("returnEnabled", rented);
					mementos.add(create.create(params));
				}
			}
			
			public void onFailure(Throwable caught) {}
		});
	}
	
	public void afterInit()
	{
		System.out.println("Started up GUI");
	}

	public void beforeDelete()
	{
	}
}
