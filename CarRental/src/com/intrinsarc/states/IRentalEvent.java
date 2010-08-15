package com.intrinsarc.states;

import com.intrinsarc.backbone.runtime.api.*;
import com.intrinsarc.cars.*;

public interface IRentalEvent extends IEvent, IRenterDetails
{
	void rent();
	void setRenter(String name);
	public String getRenter();
	void returnRental();
}
