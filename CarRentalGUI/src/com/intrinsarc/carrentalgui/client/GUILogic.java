package com.intrinsarc.carrentalgui.client;

import java.util.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;
import com.intrinsarc.backbone.runtime.api.*;

public class GUILogic implements ILifecycle
{
// start generated code
// attributes
// required ports
	private com.intrinsarc.backbone.runtime.api.ICreate create;
	private com.google.gwt.user.client.ui.TextBox text;
// provided ports
	private ChangeHandlerChangeImpl change_ChangeHandlerProvided = new ChangeHandlerChangeImpl();
// setters and getters
	public void setCreate_ICreate(com.intrinsarc.backbone.runtime.api.ICreate create) { this.create = create; }
	public void setText_TextBox(com.google.gwt.user.client.ui.TextBox text) { this.text = text; }
	public com.google.gwt.event.dom.client.ChangeHandler getChange_ChangeHandler(Class<?> required) { return change_ChangeHandlerProvided; }
// end generated code

	private IRentalServiceAsync service;
	public void setService_IRentalServiceAsync(com.intrinsarc.carrentalgui.client.IRentalServiceAsync service) { this.service = service; refresh(); }
	private List<Object> mementos = new ArrayList<Object>();

	private class ChangeHandlerChangeImpl implements com.google.gwt.event.dom.client.ChangeHandler
	{
		public void onChange(ChangeEvent event)
		{
			if (event == null)
			{
				refresh();
				return;
			}
			
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
				
				int carNo = 0;
				for (String car : cars)
				{
					Map<String, Object> params = new HashMap<String, Object>();
					
					boolean rented = false;
					String renter = "";
					if (car.startsWith(">"))
					{
						car = car.substring(1);
						rented = true;
					}
					int index = car.indexOf('|');
					if (index != -1)
					{
						renter = car.substring(index + 1);
						car = car.substring(0, index);
					}
					params.put("model", car);
					params.put("rentEnabled", !rented);
					params.put("returnEnabled", rented);
					params.put("renter", renter);
					params.put("carNo", carNo++);
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
